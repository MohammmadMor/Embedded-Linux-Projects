DEPENDS += "dtc-native"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://mpu6050-overlay.dts"

do_compile:append() {
    dtc -@ -I dts -O dtb -o ${WORKDIR}/mpu6050-overlay.dtbo ${WORKDIR}/mpu6050-overlay.dts
}

do_install:append() {
    # Install overlay to /boot/overlays
    install -d ${D}/boot/overlays/
    install -m 0644 ${WORKDIR}/mpu6050-overlay.dtbo ${D}/boot/overlays/

    # Append to config.txt
    if [ -f ${D}/boot/config.txt ]; then
        echo "" >> ${D}/boot/config.txt
        echo "# MPU6050 configuration" >> ${D}/boot/config.txt
        echo "dtparam=i2c1=on" >> ${D}/boot/config.txt
        echo "dtparam=i2c_arm=on" >> ${D}/boot/config.txt
        echo "dtparam=i2c_arm_baudrate=400000" >> ${D}/boot/config.txt
        echo "dtoverlay=mpu6050-overlay" >> ${D}/boot/config.txt
    fi
}

FILES:${PN} += "/boot/overlays/mpu6050-overlay.dtbo"

COMPATIBLE_MACHINE = "raspberrypi4-64|raspberrypi4"
