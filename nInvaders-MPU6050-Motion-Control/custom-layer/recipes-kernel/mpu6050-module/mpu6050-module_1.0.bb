SUMMARY = "MPU6050 module autoload"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://mpu6050.conf"

S = "${WORKDIR}"

inherit allarch

do_install() {
    install -d ${D}${sysconfdir}/modules-load.d
    install -m 0644 ${S}/mpu6050.conf ${D}${sysconfdir}/modules-load.d/
}

FILES:${PN} = "${sysconfdir}/modules-load.d/mpu6050.conf"
