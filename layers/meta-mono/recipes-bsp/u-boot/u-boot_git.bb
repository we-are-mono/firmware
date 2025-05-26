SUMMARY = "U-Boot for Gateway Development Kit (LS1046A)"
SECTION = "bootloaders"
LICENSE = "GPL-2.0-or-later"

LIC_FILES_CHKSUM = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"

DEPENDS += "libgcc virtual/${TARGET_PREFIX}gcc flex-native bison-native dtc-native"

inherit deploy

PV = "lf_v2024.04+git${SRCPV}"
SRC_URI = "git://github.com/we-are-mono/u-boot.git;protocol=https;branch=mono-development"
SRCREV = "32c730df0886c766c0b0fbda3a15196069f5fb31"
S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "gateway-dk"

# U-Boot configuration
UBOOT_CONFIG = "mono_gateway_dk_defconfig"
UBOOT_BINARY = "u-boot.bin"

WRAP_TARGET_PREFIX ?= "${TARGET_PREFIX}"
EXTRA_OEMAKE = 'CROSS_COMPILE=${WRAP_TARGET_PREFIX} LD="${WRAP_TARGET_PREFIX}ld"'
EXTRA_OEMAKE += 'CC="${WRAP_TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS}"'
EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}"'
EXTRA_OEMAKE += 'STAGING_INCDIR=${STAGING_INCDIR_NATIVE}'
EXTRA_OEMAKE += 'STAGING_LIBDIR=${STAGING_LIBDIR_NATIVE} V=1'

do_configure () {
    oe_runmake ${UBOOT_CONFIG}
}

do_compile () {
    oe_runmake
}

do_deploy () {
    install -d ${DEPLOYDIR}
    install -m 644 ${S}/${UBOOT_BINARY} ${DEPLOYDIR}/u-boot-${MACHINE}.bin
    ln -sf u-boot-${MACHINE}.bin ${DEPLOYDIR}/u-boot.bin
}

addtask deploy after do_compile before do_build