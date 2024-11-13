#!/usr/bin/env python3
import serial
import time


time.sleep(60)

# Set up the serial connection (make sure the port and baudrate match your configuration)
ser = serial.Serial('/dev/ttyAMA0', 57600, timeout=1)

# Check if the serial port is open
if ser.is_open:
    print("Serial port opened successfully!")
else:
    print("Failed to open serial port.")

try:
    while True:
        # Send "Hello" message
        ser.write(b'Hello\n\r')
        print("Sent: Hello")
        # Wait for 1 second
        time.sleep(0.5)

except KeyboardInterrupt:
    print("Stopped by user.")

finally:
    # Close the serial port
    ser.close()
    print("Serial port closed.")
