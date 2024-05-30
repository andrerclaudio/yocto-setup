#!/usr/bin/env python3

"""
It is initialized as a service.
"""

import logging
# import os
import time
import requests

# Configure logging
logging.basicConfig(
    format='%(asctime)s [%(levelname)s] - %(message)s',
    level=logging.INFO
)

# Define the number of retries and other configuration options
MAX_RETRIES = 6
RETRY_DELAY_SECONDS = (60 * 10)  # Seconds * Minutes
CHECK_URL = "https://www.google.com"


def internet_check():
    # Counter the number of retries
    attempts = 0

    # The application start from here
    logging.info('The application has started!')

    # Just a delay doing nothing and making sure the connection was properly established.
    time.sleep(60)

    while True:

        # Increase the attempt count
        attempts += 1

        try:
            # Attempt to send a GET request to the configured URL
            response = requests.get(CHECK_URL, timeout=5)
            # If the response status code is 200, it means the request was successful
            if response.status_code == 200:
                attempts = 0  # Reset the attempts counter upon success
                logging.debug("Internet connection is active.")
            else:
                # Anything different means the device is not connected or something went wrong
                logging.warning("[{}] Internet connection is down or response status is not 200.".format(attempts))

        except requests.ConnectionError:
            # If an exception is raised, it means the device is not connected to the internet
            logging.error("[{}] Connection error. Internet connection is down.".format(attempts))

        finally:            
            # If we've reached the maximum number of retries without success, do something.
            if attempts >= MAX_RETRIES:
    
                # Attempt to do something ...
                try:
                    # os.system("sudo reboot")
                    # Exit the loop since the system will restart
                    # break
                    pass
    
                except Exception as e:
                    # If anything happens, it will try again after the sleep
                    logging.error(f"The last command has failed: {str(e)}", exc_info=False)

        # Sleep for a custom delay before the next attempt
        time.sleep(RETRY_DELAY_SECONDS)


if __name__ == "__main__":
    # Check for internet connection and do something when there is none.
    internet_check()