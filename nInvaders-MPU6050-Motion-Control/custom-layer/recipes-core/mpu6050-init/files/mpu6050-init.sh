#!/bin/sh
# Load necessary modules
modprobe industrialio
modprobe inv_mpu6050_i2c
modprobe inv_mpu6050

# Wait for modules to initialize
sleep 2

# Check if device already exists
if [ ! -d /sys/bus/i2c/devices/i2c-1/1-0068 ]; then
    echo "Creating MPU6050 device..."
    echo mpu6050 0x68 > /sys/bus/i2c/devices/i2c-1/new_device
else
    echo "MPU6050 device already exists"
fi

# Wait for IIO device to appear
sleep 1

# Check if IIO device is available
if [ -d /sys/bus/iio/devices/iio:device* ]; then
    echo "MPU6050 initialized successfully"
    ls /sys/bus/iio/devices/
else
    echo "Failed to initialize MPU6050"
fi

