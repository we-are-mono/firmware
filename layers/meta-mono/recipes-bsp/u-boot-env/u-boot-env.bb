SUMMARY = "U-Boot Environment for Gateway Development Kit"
SECTION = "bootloaders"
LICENSE = "GPL-2.0-or-later"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

DEPENDS += "u-boot u-boot-tools-native"

inherit deploy

SRC_URI = "file://environment.txt"

# Since we're only using files, not unpacking source
S = "${WORKDIR}/${BP}"

COMPATIBLE_MACHINE = "gateway-dk"

# Environment size - typically 64KB or 128KB for NOR flash
ENV_SIZE = "0x2000"

do_compile() {
    mkenvimage -s ${ENV_SIZE} -o u-boot-env.bin ${UNPACKDIR}/environment.txt
}

do_deploy() {
    install -d ${DEPLOYDIR}
    install -m 644 u-boot-env.bin ${DEPLOYDIR}/u-boot-env-${MACHINE}.bin
    ln -sf u-boot-env-${MACHINE}.bin ${DEPLOYDIR}/u-boot-env.bin
}

addtask deploy after do_compile before do_build