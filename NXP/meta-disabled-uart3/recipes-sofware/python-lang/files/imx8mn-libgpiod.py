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
    """

    def __init__(self) -> None:
        self.__KEEP_RUNNING: bool = True
        self.__WAIT: bool = False

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
        work_counter (int): Tracks the number of times the LED state has toggled.
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
        self.name = f"[Output-{output_number}]"

        self.control: AppControlFlags = control
        self.lock: threading.Lock = lock
        self.condition: threading.Condition = condition
        self.led_state: Value = LED_OFF
        self.output: gpiod.LineRequest = gpio_controller
        self.output_number: int = output_number
        self.work_counter: int = 1
        self.start()  # Automatically start the thread

    def __app_is_running(self) -> bool:
        """
        Check if the application is running.

        Returns:
            bool: True if the application should continue running, False otherwise.
        """
        with self.lock:
            return self.control.keep_running

    def __waiting(self) -> None:
        """
        Pause the thread until it is notified to proceed.

        The thread waits on a condition until the wait flag is cleared.
        """
        with self.condition:
            logger.debug(f"[{self.output_number}] Waiting ...")
            while self.control.wait:
                self.condition.wait()
            self.control.wait = True

    def __release_wait_flag(self) -> None:
        """
        Release the wait flag and notify all waiting threads.
        """
        with self.condition:
            logger.debug(f"[{self.output_number}] Releasing wait flag...")
            self.control.wait = False
            self.condition.notify_all()
        logger.debug(f"[{self.output_number}] Toggled {self.work_counter} times.")
        self.work_counter += 1
        time.sleep(0.001)

    def run(self) -> None:
        """
        Main thread loop for toggling the GPIO output.

        Continuously toggles the LED state between ON and OFF at regular intervals
        until the thread is stopped.
        """
        logger.info(f"Starting GPIO thread for line {self.output_number}.")

        try:
            while self.__app_is_running():
                self.__waiting()

                # Toggle LED state
                self.led_state = LED_ON if self.led_state == LED_OFF else LED_OFF
                self.output.set_value(line=self.output_number, value=self.led_state)
                logger.debug(
                    f"LED [{self.output_number}] State changed to: {self.led_state}"
                )
                time.sleep(OUTPUT_BLINK_DELAY)

                self.__release_wait_flag()

            # Turn off LED before exiting
            self.output.set_value(line=self.output_number, value=LED_OFF)
            logger.info(f"GPIO thread for line {self.output_number} finished.")

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
        # Request LINES config and others relevant values
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

        while app_control_flags.keep_running:
            pass

        time.sleep(OUTPUT_BLINK_DELAY * 3)  # Wait a while to ensure the threads exited
        logger.info("Releasing resources ...")

    except Exception as e:
        logger.error(f"An error occurred: {e}", exc_info=True)

    finally:
        if isinstance(lines, gpiod.LineRequest):
            lines.release()
            logger.info("GPIO lines released successfully.")
