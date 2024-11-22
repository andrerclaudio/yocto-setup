#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import sys
import signal
import logging
from functools import partial

import gi

gi.require_version("Gtk", "4.0")
from gi.repository import Gtk, GLib

# Configure logging with a specific format and level
logging.basicConfig(
    level=logging.DEBUG, format="%(asctime)s - %(levelname)s - %(message)s"
)


class BoxedLabel(Gtk.Window):
    """
    A GTK window to dynamically display the output of a terminal command.
    """

    def __init__(self):
        """
        Initialize the BoxedLabel window and set up its UI components.
        """
        super().__init__(title="--- GTK SandBox ---")

        # Create a new Gtk application instance
        self.app = Gtk.Application()

        # Connect the "close-request" signal to handle window close events
        self.connect("close-request", self.on_close)

        # Create a box to hold the label
        self.box = Gtk.Box(orientation=Gtk.Orientation.VERTICAL, spacing=6)
        self.set_child(self.box)

        # Dynamic CSS string for window and label appearance
        self.css = """
        window {
            background-color: black;
        }

        label {
            color: white;
            font-size: 31px;
            font-family: monospace;
        }
        """

        # Load and apply the defined CSS
        self.load_css()

        # Remove title bar and make the window full-screen
        self.set_decorated(False)
        self.fullscreen()

        # Label to display the output of the terminal command
        self.label = Gtk.Label()
        self.label.set_markup("<span>Loading...</span>")
        self.box.append(self.label)

    def load_css(self) -> None:
        """
        Load CSS data and apply it to the window.

        This method creates a CSS provider, loads the CSS from a string,
        and applies it to the current display.
        """
        css_provider = Gtk.CssProvider()

        # Load CSS data safely with error handling
        try:
            css_provider.load_from_data(self.css.encode("utf-8"))
            display = self.get_display()
            Gtk.StyleContext.add_provider_for_display(
                display,
                css_provider,
                Gtk.STYLE_PROVIDER_PRIORITY_APPLICATION,
            )
            logging.info("CSS loaded and applied successfully.")

        except Exception as e:
            logging.error(f"Failed to load CSS: {e}")

    def update_output(self, text: str) -> None:
        """
        Update the label with the command's output.

        Args:
            text (str): The output text from the command.

        This method escapes the text to prevent XSS attacks or rendering issues.
        """
        safe_text = GLib.markup_escape_text(text)
        self.label.set_markup(f"<span>{safe_text}</span>")

    def on_close(self, widget) -> bool:
        """
        Handle the window close event and perform cleanup before exiting.

        Args:
            widget (Gtk.Widget): The widget triggering the event.

        Returns:
            bool: Return False to allow the window to close.

        This method logs cleanup operations before quitting the application.
        """
        logging.info("Performing cleanup before exiting the application.")

        # Ensure that we quit the GTK application gracefully
        self.app.quit()

        return False  # Returning False allows the window to close


def on_activate(application: Gtk.Application, window: BoxedLabel) -> None:
    """
    Signal handler for the 'activate' signal of the Gtk.Application.

    This function is called when the application is started. It sets
    the application to the provided `Gtk.Window` window and presents
    it to the user.

    Args:
         application (Gtk.Application): The main Gtk application instance.
         window (BoxedLabel): The window that displays output.
    """
    window.set_application(application)
    window.present()


def app_signal_handler(signum: int, frame, window: BoxedLabel) -> None:
    """
    Handle termination signals to gracefully stop the GTK application.

    Args:
         signum (int): The signal number (e.g., SIGINT, SIGTERM).
         frame: The current stack frame (not used).
         window (BoxedLabel): The GTK application instance.

    This method logs received signals and triggers cleanup before shutdown.
    """
    logging.info(f"Received signal {signum}. Application is being shutdown.")

    # Trigger cleanup by calling on_close method of BoxedLabel instance
    window.on_close(widget=None)


def sandbox() -> None:
    """
    Main function to run the GTK application.

    This function initializes logging, creates a BoxedLabel instance,
    connects signals, and starts the GTK main loop. It handles exceptions
    gracefully and logs errors if they occur.
    """
    try:
        logging.info("The SandBox is running!")

        # Create an instance of BoxedLabel for displaying output
        window = BoxedLabel()

        # Connect 'activate' signal to on_activate handler
        window.app.connect("activate", partial(on_activate, window=window))

        # Handle termination signals for graceful shutdown
        signal.signal(
            signal.SIGINT, lambda sig, frame: app_signal_handler(sig, frame, window)
        )

        signal.signal(
            signal.SIGTERM, lambda sig, frame: app_signal_handler(sig, frame, window)
        )

        # Run the GTK application main loop
        window.app.run(None)

    except Exception as e:
        logging.error(f"An error occurred: {e}")
        # Exit with a non-zero status code indicating an error occurred
        sys.exit(1)

    logging.info("Application finished successfully.")
    # Exit with zero status code indicating success
    sys.exit(0)


if __name__ == "__main__":
    sandbox()
