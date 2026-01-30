# nInvaders with MPU6050 Motion Control
**Yocto-based Embedded Linux Project for Raspberry Pi 4**

---

## Introduction

Welcome to the **nInvaders-MPU6050** project.

This project demonstrates how to build a custom **Embedded Linux system** for the **Raspberry Pi 4** using the **Yocto Project**, capable of running the classic **nInvaders** game.  
Unlike the standard keyboard-based gameplay, this version introduces **motion-based control** using an **MPU6050 accelerometer and gyroscope sensor**.

The main objective of this project is to deepen practical understanding of **Embedded Linux system integration**, covering application cross-compilation, kernel configuration, driver enablement, source code patching, and device tree customization.

---

## Project Overview

A tailored Embedded Linux image is created specifically for the Raspberry Pi 4. The **nInvaders** game is included in the image and enhanced to support control via both:

- Standard keyboard input
- MPU6050 motion sensor input

To enable sensor-based control, patches are applied to the original nInvaders source code so that motion data from the MPU6050 can be interpreted as player input.

The project relies on the **mainline Linux MPU6050 driver**, which is enabled and built as a loadable kernel module.  
Sensor integration is achieved through a **Device Tree Overlay**, allowing the kernel to correctly detect and initialize the MPU6050 at boot time.

---

## Yocto Layers

The Linux image is built using the following Yocto layers:

- `poky`
- `meta-raspberrypi`
- `meta-openembedded`
- `custom-layer`

The **custom-layer** is created specifically for this project and contains:

- Source code patches
- Kernel configuration fragments
- Device Tree Overlays
- systemd service files

All other layers are fetched from their official upstream repositories.

---

## Implementation Details

### 1. Cross-compiling nInvaders

To allow the nInvaders application to build correctly within the Yocto environment, the following patch is applied:

- `0001-Make-it-Cross-Compile-Friendly.patch`

This patch adapts the original build system to fully support cross-compilation.

---

### 2. Adding MPU6050 Motion Control

Motion-based gameplay is enabled using the following patch:

- `0002-Make-it-MPU6050-Friendly.patch`

This patch modifies the game logic so that MPU6050 motion data is mapped to player actions:

- **Y-axis movement** → Horizontal spaceship movement
- **Z-axis movement** → Fire action

---

### 3. Device Tree Integration

The MPU6050 sensor is added via a Device Tree Overlay:

- `mpu6050-overlay.dts`

When applied on top of the default Raspberry Pi device tree, this overlay enables proper detection and initialization of the MPU6050 on the I2C bus.

---

### 4. Kernel Configuration

MPU6050 support is enabled using a kernel configuration fragment:

- `mpu6050.cfg`

This fragment:

- Enables the MPU6050 driver as a loadable kernel module
- Activates the Industrial I/O (IIO) subsystem
- Enables I2C support for the Raspberry Pi platform

---

### 5. Automatic Module Loading at Boot

To ensure the MPU6050 driver is loaded automatically during system startup, the following files are used:

- `mpu6050-init.service`
- `mpu6050-init.sh`

The systemd service runs at boot time and executes the initialization script, which loads the required kernel modules and verifies correct device creation under the IIO subsystem.

---

### 6. Yocto Image Configuration

The `local.conf` file is modified to include the required packages in the final image:

IMAGE_INSTALL:append = "
    dropbear
    ninvaders
    i2c-tools
    kernel-modules
    kmod
    ethtool
    iproute2
"
---
### 7. Build and Deployment

The final image is built using:


bitbake core-image-base

After a successful build, the image is deployed to the Raspberry Pi 4 and booted.

### 🔌 Hardware Connections

The MPU6050 module is connected to the Raspberry Pi 4 via the I2C interface:

MPU6050 VCC → Raspberry Pi Pin 1 (3.3V)  
MPU6050 SCL → Raspberry Pi Pin 5  
MPU6050 SDA → Raspberry Pi Pin 3  
MPU6050 GND → Raspberry Pi Pin 9  

⚠️ Warning  
Some MPU6050 modules are not 5V tolerant.  
For safety, the VCC pin is connected to 3.3V. Only use 5V if you are certain your module supports it.

### Source Code

The complete source code for the custom Yocto layer, along with the modified local.conf file and all related patches, is provided as part of this repository.

