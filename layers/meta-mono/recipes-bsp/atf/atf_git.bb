SUMMARY = "ARM Trusted Firmware for Gateway Development Kit (LS1046A)"
SECTION = "bootloaders"
LICENSE = "BSD-3-Clause"

LIC_FILES_CHKSUM = "file://license.rst;md5=1dd070c98a281d18d9eefd938729b031"

DEPENDS += "openssl openssl-native"
DEPENDS += "rcw u-boot"
do_compile[depends] += "u-boot:do_deploy rcw:do_deploy"

ATF_VERSION ?= "2.6"
PV = "${ATF_VERSION}+git${SRCPV}"

SRC_URI = "git://github.com/we-are-mono/atf.git;protocol=https;branch=mono-development"
SRCREV = "270f20bdd33522d343b1c38a0a2fd165a8a0a16f"

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "gateway-dk"

ATF_PLATFORM = "gateway_dk"
ATF_UBOOT_FILE = "${DEPLOY_DIR_IMAGE}/u-boot.bin"

# We can also use BOOTTYPE=emmc in bitbake command to use it instead
BOOTTYPE ?= "qspi"

# requires CROSS_COMPILE set by hand as there is no configure script
export CROSS_COMPILE = "${TARGET_PREFIX}"
export ARCH = "arm64"

# Let the Makefile handle setting up the CFLAGS and LDFLAGS as it is
# a standalone application
CFLAGS[unexport] = "1"
LDFLAGS[unexport] = "1"
AS[unexport] = "1"
LD[unexport] = "1"

EXTRA_OEMAKE += "HOSTCC='${BUILD_CC} ${BUILD_CPPFLAGS} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}'"

do_compile () {
    ATF_RCW_FILE="${DEPLOY_DIR_IMAGE}/rcw_1600_${BOOTTYPE}boot.bin"

    oe_runmake distclean

    oe_runmake PLAT=${ATF_PLATFORM} \
                BOOT_MODE=${BOOTTYPE} \
                RCW=${ATF_RCW_FILE} \
                BL33=${ATF_UBOOT_FILE} \
                pbl fip

    # These normally belong to do_install, but we're building several
    # different images and each overwrites the previous one, which we
    # need to avoid, so we copy it over before that happens
    install -m 644 build/${ATF_PLATFORM}/release/bl2_${BOOTTYPE}.pbl \
                    ${DEPLOY_DIR_IMAGE}/

    install -m 644 build/${ATF_PLATFORM}/release/fip.bin \
                    ${DEPLOY_DIR_IMAGE}/
    

}