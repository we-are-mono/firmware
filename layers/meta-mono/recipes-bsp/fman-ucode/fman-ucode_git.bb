SUMMARY = "Frame Manager Microcode for LS1046A"
SECTION = "bootloaders"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://NXP-Binary-EULA.txt;md5=12e248d404ce1ea8bed0148fcf127e69"

inherit deploy

# NXP's official FMAN microcode repository
SRC_URI = "git://github.com/nxp/qoriq-fm-ucode.git;protocol=https;branch=integration"
SRCREV = "83e8467a356316265c7695bdc7741f23066795aa"

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "gateway-dk"

FMAN_UCODE_FILE = "fsl_fman_ucode_ls1046_r1.0_108_4_9.bin"

do_deploy() {
    install -d ${DEPLOYDIR}
    install -m 644 ${S}/${FMAN_UCODE_FILE} ${DEPLOYDIR}/fman-ucode-${MACHINE}.bin
    ln -sf fman-ucode-${MACHINE}.bin ${DEPLOYDIR}/fman-ucode.bin
}

addtask deploy after do_install before do_build