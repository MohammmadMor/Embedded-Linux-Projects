FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += " file://mpu6050.cfg "

KERNEL_CONFIG_FRAGMENTS += "${THISDIR}/files/mpu6050.cfg"

COMPATIBLE_MACHINE = "raspberrypi4-64|raspberrypi4"

