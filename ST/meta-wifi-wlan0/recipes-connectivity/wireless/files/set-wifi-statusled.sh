#!/bin/sh

# Set the device name for the LED
echo wlan0 > /sys/class/leds/blue:status/device_name
# Set the link status
echo 1 > /sys/class/leds/blue:status/link
# Set the Rx event
echo 1 > /sys/class/leds/blue:status/rx
# Set the Tx event
echo 1 > /sys/class/leds/blue:status/tx