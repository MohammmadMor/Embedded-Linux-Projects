SUMMARY = "nInvaders ncurses-based game with MPU6050 user-space input support"
DESCRIPTION = "Space Invaders clone modified to support MPU6050 input in user space"
HOMEPAGE = "http://ninvaders.sourceforge.net"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://gpl.txt;md5=393a5ca445f6965873eca0259a17f833"

SRC_URI = "https://downloads.sourceforge.net/project/ninvaders/ninvaders/${PV}/ninvaders-${PV}.tar.gz \
           file://0001-Make-it-Cross-Compile-Friendly.patch \
           file://0002-Make-it-MPU6050Friendly.patch \
           "

SRC_URI[md5sum] = "97b2c3fb082241ab5c56ab728522622b"
SRC_URI[sha1sum] = "5ab825694b108cbfa988377ca216188fa9a76e89"
SRC_URI[sha256sum] = "bfbc5c378704d9cf5e7fed288dac88859149bee5ed0850175759d310b61fd30b"
SRC_URI[sha384sum] = "10fd9fe45e480b2ea3adec717ed3b396caa7e6fc773f21f24f804b3698efb53e5747b7c2715bdba69f80507da21efa0c"
SRC_URI[sha512sum] = "7987063084d9a9f042b419e7552debf3f173b82ba3832c2575ae8461fb5f787d6a476c305d5c2d591b4574748c6ba50e42391796c384f225685c22f044553087"

DEPENDS = " ncurses"

do_configure () {
    :
}

do_compile () {
    oe_runmake
}

do_install () {
    install -d ${D}${bindir}
    install -m 0755 nInvaders ${D}${bindir}/nInvaders
}

