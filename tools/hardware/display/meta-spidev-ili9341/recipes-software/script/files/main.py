#!/usr/bin/env python3
# pylint: disable=line-too-long
# indent = tab
# tab-size = 4

"""
This script initializes and controls a TFT display using the ILI9341 driver on a iMX8mn board.
It loads and displays an image on the TFT display.

Requirements:
- Python 3
- Pillow (PIL)
- gpiod
- spidev

"""
from PIL import Image
import SpiDev_ILI9341 as TFT
import gpiod
from gpiod.line_settings import Direction, Value
import spidev
import logging
import sys 

# Create a logger
logging.basicConfig(level=logging.INFO, format="%(asctime)s [%(levelname)s]: %(message)s")

# iMX8mn configuration.
DC = 9
RST = 19
CS = 13
BKLIGHT = 8
SPI_PORT = 1
SPI_DEVICE = 0


def application(path):
    """
    Initialize and control the TFT display, load and display an image.
    """

    spi = None
    lines = None
    disp = None

    try:
        # Initialize SPI
        spi = spidev.SpiDev()
        spi.open(1, 0)
        spi.max_speed_hz = 32000000
        spi.mode = 0b00  # Set SPI to mode 0, MSB first.
        spi.no_cs = True

        # Request GPIO lines configuration
        lines = gpiod.request_lines('/dev/gpiochip4',
                                    consumer='Display',
                                    config={
                                        DC:
                                            gpiod.LineSettings(
                                                direction=Direction.OUTPUT,
                                                output_value=Value.ACTIVE,
                                            ),
                                        RST:
                                            gpiod.LineSettings(
                                                direction=Direction.OUTPUT,
                                                output_value=Value.ACTIVE,
                                            ),
                                        BKLIGHT:
                                            gpiod.LineSettings(
                                                direction=Direction.OUTPUT,
                                                output_value=Value.ACTIVE,
                                            ),
                                        CS:
                                            gpiod.LineSettings(
                                                direction=Direction.OUTPUT,
                                                output_value=Value.INACTIVE,
                                            )
                                    }
                                    )

        # Create TFT LCD class.
        disp = TFT.ILI9341(dc=DC,
                           rst=RST,
                           gpio_controller=lines,
                           spi=spi)

        # Initialize display.
        disp.begin()

        # Clear the display to a red background.
        # Can pass any tuple of red, green, blue values (from 0 to 255 each).
        disp.clear((255, 0, 0))

        # Load an image.
        logging.info(f'Loading image: {path}')
        image = Image.open(path)

        # Resize the image and rotate it so it's 240x320 pixels.
        image = image.rotate(90).resize((240, 320))

        # Draw the image on the display hardware.
        logging.info('Drawing image')
        disp.display(image)
        logging.info('Showing the image. Press "Crtl + C" to stop it.')
        while True:
            pass

    except Exception as e:
        logging.exception(e, exc_info=False)
    except KeyboardInterrupt:
        logging.exception("User has stopped the program execution!", exc_info=False)
    except gpiod.exception as e:
        logging.exception(e, exc_info=False)

    finally:
        logging.info('Closing and Cleaning ...')

        # Reset display buffer 
        if disp:
            disp.clear((0, 0, 0))

        # Cleanup: Set backlight to inactive and release GPIO lines
        if lines:
            lines.set_value(line=BKLIGHT, value=Value.INACTIVE)
            lines.release()

        # Close SPI connection
        if spi:
            spi.close()


if __name__ == "__main__":

    if len(sys.argv) != 2:
        logging.error("Usage: python3 main.py <image_path>")
        sys.exit(1)

    image_path = sys.argv[1]
    # Run the script
    application(image_path)
