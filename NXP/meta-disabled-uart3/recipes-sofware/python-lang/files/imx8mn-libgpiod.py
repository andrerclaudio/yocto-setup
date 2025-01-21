#!/usr/bin/env python3
"""
GPIO LED Blinking Application

This script demonstrates how to control GPIO lines on a Linux system using the
`gpiod` library. It utilizes threading to manage multiple LEDs independently,
with control flags for synchronized operation. The application gracefully
handles shutdown on receiving a SIGINT (Ctrl+C).
"""

import logging
import signal
import threading
import time
from functools import partial
from typing import Dict

import gpiod
from gpiod.line_settings import Direction, Value

# Initialize logger configuration
logging.basicConfig(
    level=logging.DEBUG, format="%(asctime)s [%(levelname)s]: %(message)s"
)
logger = logging.getLogger(__name__)

# Constants
CONSUMER_NAME: str = "App"  # Identifier for GPIO line consumers
LED_1: int = 9  # GPIO line number for LED 1
LED_2: int = 8  # GPIO line number for LED 2
OUTPUT_BLINK_DELAY: float = 0.2  # Delay between LED state changes in seconds

# GPIO value definitions
LED_ON: Value = Value.ACTIVE
LED_OFF: Value = Value.INACTIVE

# Path to the GPIO chip
PATH: str = "/dev/gpiochip4"


class AppControlFlags:
    """
    Holds application-wide control flags to manage thread execution and synchronization.

    Attributes:
        keep_running (bool): Indicates if the application should continue running.
        wait (bool): Synchronization flag for controlling thread operations.
        __WORK_COUNTER (Dict[str, int]): Tracks counters for different operations by name.

    Methods:
        get_counter(name: str) -> int: Retrieves the counter value for a given name.
        increment_counter(name: str) -> None: Increments the counter value for a given name.
        add_counter(name: str) -> None: Initializes a counter for a given name.
        min_counter() -> str: Returns the name of the operation with the minimum counter value.
    """

    def __init__(self) -> None:
        """
        Initializes the control flags and counter dictionary.
        """
        self.__KEEP_RUNNING: bool = True
        self.__WAIT: bool = True
        self.__WORK_COUNTER: Dict[str, int] = {}

    @property
    def keep_running(self) -> bool:
        """Get the keep_running flag."""
        return self.__KEEP_RUNNING

    @keep_running.setter
    def keep_running(self, value: bool) -> None:
        """Set the keep_running flag."""
        self.__KEEP_RUNNING = value

    @property
    def wait(self) -> bool:
        """Get the wait flag."""
        return self.__WAIT

    @wait.setter
    def wait(self, value: bool) -> None:
        """Set the wait flag."""
        self.__WAIT = value

    def add_counter(self, name: str) -> None:
        """
        Initializes a counter for a given operation name.

        Args:
            name (str): Name of the operation.
        """
        self.__WORK_COUNTER[name] = 0

    def get_counter(self, name: str) -> int:
        """
        Retrieves the counter value for a given operation name.

        Args:
            name (str): Name of the operation.

        Returns:
            int: Counter value for the operation.
        """
        return self.__WORK_COUNTER[name]

    def increment_counter(self, name: str) -> None:
        """
        Increments the counter value for a given operation name.

        Args:
            name (str): Name of the operation.
        """
        self.__WORK_COUNTER[name] += 1

    def min_counter(self) -> str:
        """
        Returns the name of the operation with the minimum counter value.

        Returns:
            str: Name of the operation with the minimum counter.
        """
        return min(self.__WORK_COUNTER, key=self.__WORK_COUNTER.get)


class GpioOutput(threading.Thread):
    """
    Manages a GPIO output line in a separate thread, toggling its state asynchronously.

    This class allows independent control of GPIO lines, with synchronized
    start/stop operations and LED state toggling.

    Attributes:
        control (AppControlFlags): Shared control flags for application state.
        lock (threading.Lock): Synchronization lock for thread-safe operations.
        condition (threading.Condition): Synchronization condition for wait/notify operations.
        gpio_controller (gpiod.LineRequest): GPIO controller for managing the specified line.
        output_number (int): GPIO line number assigned to this thread.
        led_state (Value): Current state of the LED (ON or OFF).

    Methods:
        run() -> None: Main thread loop for toggling the GPIO output.
    """

    def __init__(
        self,
        control: AppControlFlags,
        lock: threading.Lock,
        condition: threading.Condition,
        gpio_controller: gpiod.LineRequest,
        output_number: int,
    ) -> None:
        """
        Initialize a GpioOutput thread.

        Args:
            control (AppControlFlags): Application-wide control flags.
            lock (threading.Lock): Synchronization lock.
            condition (threading.Condition): Thread condition for wait/notify.
            gpio_controller (gpiod.LineRequest): GPIO line controller.
            output_number (int): GPIO line number.
        """
        threading.Thread.__init__(self)
        self.name = f"OUT-{output_number}"  # Thread name based on output number

        self._control: AppControlFlags = control
        self._lock: threading.Lock = lock
        self._condition: threading.Condition = condition
        self._led_state: Value = LED_OFF  # Initial LED state is OFF
        self._output: gpiod.LineRequest = gpio_controller
        self._output_number: int = output_number

        # Initialize counter for this thread
        self._control.add_counter(name=self.name)
        self.start()  # Automatically start the thread

    def __app_is_running(self) -> bool:
        """
        Check if the application is running.

        Returns:
            bool: True if the application should continue running, False otherwise.
        """
        with self._lock:
            return self._control.keep_running

    def __waiting(self) -> None:
        """
        Pause the thread until it is notified to proceed.

        The thread waits on a condition until it is its turn to proceed based on priority.
        """
        with self._condition:
            while True:
                # Check if the thread has the priority to proceed
                got_priority = self.name == self._control.min_counter()

                if not self._control.wait and got_priority:
                    # If the global wait flag is cleared and this thread has priority, exit loop
                    break

                if not got_priority:
                    logger.debug(f"[{self.name}] Waiting for priority...")
                else:
                    logger.debug(
                        f"[{self.name}] Waiting for global wait flag to be cleared..."
                    )

                # Wait for a notification
                self._condition.wait()

            # Once the thread has priority, update the state
            logger.debug(f"[{self.name}] Good to go!")
            self._control.increment_counter(name=self.name)
            self._control.wait = True  # Re-enable the global wait flag

    def __release_wait_flag(self) -> None:
        """
        Release the wait flag and notify all waiting threads.
        """
        with self._condition:
            logger.debug(f"[{self.name}] Releasing wait flag...")
            self._control.wait = False
            self._condition.notify_all()
        logger.debug(
            f"[{self.name}] Toggled {self._control.get_counter(self.name)} times."
        )

    def run(self) -> None:
        """
        Main thread loop for toggling the GPIO output.

        Continuously toggles the LED state between ON and OFF at regular intervals
        until the thread is stopped.
        """
        logger.info(f"Starting GPIO thread for line {self.name}.")

        try:
            while self.__app_is_running():
                # Wait for the thread's turn to proceed
                self.__waiting()

                # Toggle LED state
                self._led_state = LED_ON if self._led_state == LED_OFF else LED_OFF
                self._output.set_value(line=self._output_number, value=self._led_state)
                logger.debug(f"LED [{self.name}] State changed to: {self._led_state}")

                # Wait for the blink delay
                time.sleep(OUTPUT_BLINK_DELAY)

                # Release the wait flag and notify other threads
                self.__release_wait_flag()

            # Turn off LED before exiting
            self._output.set_value(line=self._output_number, value=LED_OFF)
            logger.info(f"GPIO thread for line {self.name} finished.")

        except Exception as e:
            logger.error(f"Error in thread: {e}", exc_info=True)


def handle_sigint(
    app_control_flags: AppControlFlags, lock_flag: threading.Lock, sig: int, frame
) -> None:
    """
    Handle SIGINT (Ctrl+C) signal for graceful shutdown.

    Args:
        app_control_flags (AppControlFlags): Control flags for application state.
        lock_flag (threading.Lock): Synchronization lock.
        sig (int): Received signal number.
        frame (FrameType): Current stack frame.
    """
    logger.info("Ctrl+C detected, shutting down...")
    with lock_flag:
        app_control_flags.keep_running = False


if __name__ == "__main__":
    # Register the signal handler for Ctrl+C (SIGINT)
    logger.info("Starting the application!")

    lines: gpiod.LineRequest | None = None

    try:
        # Request GPIO lines with initial configuration
        lines = gpiod.request_lines(
            PATH,
            consumer=CONSUMER_NAME,
            config={
                LED_1: gpiod.LineSettings(
                    direction=Direction.OUTPUT,
                    output_value=LED_OFF,
                ),
                LED_2: gpiod.LineSettings(
                    direction=Direction.OUTPUT,
                    output_value=LED_OFF,
                ),
            },
        )

        # General Application control flags
        app_control_flags = AppControlFlags()

        # Define a lock for synchronization
        lock_flag: threading.Lock = threading.Lock()
        condition_flag: threading.Condition = threading.Condition(lock=lock_flag)

        # Create a partial function to handle SIGINT with control_flags and lock_flag
        sigint_handler = partial(handle_sigint, app_control_flags, lock_flag)
        signal.signal(signal.SIGINT, sigint_handler)

        # Create threads for each GPIO output
        GpioOutput(
            control=app_control_flags,
            lock=lock_flag,
            condition=condition_flag,
            gpio_controller=lines,
            output_number=LED_1,
        )

        GpioOutput(
            control=app_control_flags,
            lock=lock_flag,
            condition=condition_flag,
            gpio_controller=lines,
            output_number=LED_2,
        )

        # Signalize the threads after they are all ready to start working
        time.sleep(1)
        with condition_flag:
            app_control_flags.wait = False
            condition_flag.notify_all()

        # Keep the main thread running until the application is stopped
        while app_control_flags.keep_running:
            pass

        # Wait a bit to ensure threads exit cleanly
        time.sleep(OUTPUT_BLINK_DELAY * 3)
        logger.info("Releasing resources ...")

    except Exception as e:
        logger.error(f"An error occurred: {e}", exc_info=True)

    finally:
        if isinstance(lines, gpiod.LineRequest):
            lines.release()
            logger.info("GPIO lines released successfully.")
