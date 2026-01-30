nInvaders-MPU6050 Project
Introduction

Welcome to the nInvaders-MPU6050 project.

This project demonstrates how to build a custom Embedded Linux system for the RaspberryPi-4 using the Yocto Project, capable of running the classic nInvaders game. Unlike the standard keyboard-based gameplay, this version introduces motion-based control using an MPU6050 accelerometer and gyroscope sensor.

The primary goal of this project is to deepen understanding of Embedded Linux system integration by combining several key topics, including application cross-compilation, kernel configuration, driver enablement, source code patching, and device tree customization.

Project Overview

In this project, a tailored Embedded Linux image is created specifically for the RaspberryPi-4. The nInvaders game is included in the image and enhanced to support control via both the keyboard and an MPU6050 motion sensor.

To enable sensor-based control, patches are applied to the original nInvaders source code so that motion data from the MPU6050 can be interpreted as player input. The project uses the standard Linux kernel MPU6050 driver, which requires proper kernel configuration to be enabled and built as a loadable module.

The MPU6050 sensor itself is integrated into the system through a Device Tree Overlay, allowing the kernel to correctly detect and initialize the sensor during system boot.

Yocto Layers Used

The Linux image is built using the following Yocto layers:

poky

meta-raspberrypi

meta-openembedded

custom-layer

The custom-layer is created specifically for this project. All patches, kernel configuration fragments, device tree overlays, and service files related to this project are located within this layer. The other layers are obtained from their official upstream repositories.

Implementation Steps
1. Enable cross-compilation for nInvaders

To allow the nInvaders application to be built correctly inside the Yocto environment, the following patch is applied to the original source code:

0001-Make-it-Cross-Compile-Friendly.patch


This patch adapts the build system to support cross-compilation.

2. Add MPU6050-based control to nInvaders

To enable motion-based gameplay, the following patch is applied:

0002-Make-it-MPU6050-Friendly.patch


This patch modifies the game logic so that data from the MPU6050 sensor is interpreted as player input:

Movement along the Y-axis controls the horizontal movement of the spaceship.

Movement along the Z-axis is used to trigger the firing action.

3. Add the MPU6050 to the Device Tree

The MPU6050 sensor is added to the system using a Device Tree Overlay file:

mpu6050-overlay.dts


When applied on top of the default Raspberry Pi device tree, this overlay enables the kernel to detect and initialize the MPU6050 sensor on the I2C bus.

4. Kernel configuration for MPU6050 support

To include MPU6050 support as a kernel module, a kernel configuration fragment is used:

mpu6050.cfg


This configuration fragment performs the following actions:

Enables the MPU6050 kernel driver as a loadable module

Activates the Industrial I/O (IIO) subsystem, which is the standard Linux framework for sensor devices

Enables I2C support specific to the Raspberry Pi platform

5. Automatic loading of the MPU6050 module at boot

To ensure that the MPU6050 module is loaded automatically after the system boots, the following files are provided:

mpu6050-init.service

mpu6050-init.sh

The mpu6050-init.service file is a systemd service that runs during boot and executes the mpu6050-init.sh script.
The script loads the required MPU6050 kernel modules and, if necessary, manually registers the sensor device on the I2C bus. It also verifies that the device is correctly created under the IIO subsystem.

6. Yocto image configuration

The local.conf file is modified to include the required packages in the final image. This is done by appending the following line:

IMAGE_INSTALL:append = " \
    dropbear \
    ninvaders \
    i2c-tools \
    kernel-modules \
    kmod \
    ethtool \
    iproute2 \
"

7. Build and deploy the image

The final image is built using the following command:

bitbake core-image-base


After the build process is complete, the generated image is deployed to the Raspberry Pi 4 and booted.

8. Hardware connections

The MPU6050 module is connected to the Raspberry Pi 4 via the I2C interface as follows:

MPU6050 VCC  -> Raspberry Pi Pin 1
MPU6050 SCL  -> Raspberry Pi Pin 5
MPU6050 SDA  -> Raspberry Pi Pin 3
MPU6050 GND  -> Raspberry Pi Pin 9


Some MPU6050 modules are not 5V tolerant. For this reason, the VCC pin of the module is connected to Pin 1 (3.3V) on the Raspberry Pi.
If you are certain that your specific MPU6050 module supports 5V input, you may alternatively connect VCC to Pin 2 or Pin 4.

Source Code Availability

The complete source code for the custom-layer, along with the modified local.conf file, is provided as part of this project.