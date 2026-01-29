SUMMARY = "Initialize MPU6050"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://mpu6050-init.sh \
           file://mpu6050-init.service"

S = "${WORKDIR}"

inherit systemd

SYSTEMD_SERVICE:${PN} = "mpu6050-init.service"

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/mpu6050-init.service ${D}${systemd_system_unitdir}

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/mpu6050-init.sh ${D}${bindir}/mpu6050-init
}

FILES:${PN} += "${systemd_system_unitdir}/mpu6050-init.service ${bindir}/mpu6050-init"

