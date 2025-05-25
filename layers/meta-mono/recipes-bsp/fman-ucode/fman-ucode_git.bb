SUMMARY = "Frame Manager Microcode for LS1046A"
SECTION = "bootloaders"
# TODO: Change the license to a correct one
LICENSE = "CLOSED"

inherit deploy

# NXP's official FMAN microcode repository
SRC_URI = "git://github.com/nxp/qoriq-fm-ucode.git;protocol=https;branch=integration"
SRCREV = "83e8467a356316265c7695bdc7741f23066795aa"

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "gateway-dk"

# LS1046A uses specific FMAN microcode
FMAN_UCODE_FILE = "fsl_fman_ucode_ls1046_r1.0_108_4_9.bin"

do_deploy() {
    install -d ${DEPLOYDIR}
    
    # Deploy with a consistent name
    install -m 644 ${S}/${FMAN_UCODE_FILE} ${DEPLOYDIR}/fman-ucode-${MACHINE}.bin
    
    # Create symlink for easier reference
    ln -sf fman-ucode-${MACHINE}.bin ${DEPLOYDIR}/fman-ucode.bin
}

addtask deploy after do_install before do_build