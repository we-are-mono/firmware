SUMMARY = "Reset Configuration Word for Gateway DK (LS1046A)"
SECTION = "bootloaders"
LICENSE = "BSD-3-Clause"

LIC_FILES_CHKSUM = "file://LICENSE;md5=44a0d0fad189770cc022af4ac6262cbe"

DEPENDS += "tcl-native"

inherit deploy

SRC_URI = "git://github.com/we-are-mono/rcw.git;protocol=https;branch=mono-development"
SRCREV = "7dc6ed1e7e9cb2f3ed5c0c74a217370f5e09539b"

S = "${WORKDIR}/git"

export PYTHON = "${USRBINPATH}/python3"

COMPATIBLE_MACHINE = "gateway-dk"

# Directory name in the RCW repository
BOARD_TARGETS = "gateway_dk"

do_compile () {
    oe_runmake BOARDS="${BOARD_TARGETS}"
}

do_deploy () {
    install -d ${DEPLOYDIR}

    for binfile in ${S}/gateway_dk/NN_FFSSPSNP_1133_5A06/*.bin; do
        if [ -f "$binfile" ]; then
            install -m 644 "$binfile" ${DEPLOYDIR}/
        fi
    done
}

addtask deploy after do_compile