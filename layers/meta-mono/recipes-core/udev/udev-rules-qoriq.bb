SUMMARY = "Gateway-specific udev rules for DPAA networking"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://50-fsl-dpaa-persistent-networking.rules"

S = "${UNPACKDIR}"

do_install() {
    install -d ${D}${sysconfdir}/udev/rules.d
    install -m 0644 ${S}/50-fsl-dpaa-persistent-networking.rules ${D}${sysconfdir}/udev/rules.d/
}