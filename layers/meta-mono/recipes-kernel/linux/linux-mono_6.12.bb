SUMMARY = "Linux kernel for Gateway Development Kit based on NXP QorIQ Linux"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

inherit kernel

LINUX_VERSION ?= "6.12.3"
PV = "${LINUX_VERSION}+git${SRCPV}"

SRC_URI = "git://github.com/nxp-qoriq/linux.git;protocol=https;branch=lf-6.12.y"
SRCREV = "37d02f4dcbbe6677dc9f5fc17f386c05d6a7bd7a"

S = "${WORKDIR}/git"

SRC_URI += "file://defconfig"
SRC_URI += "file://mono-gateway-dk.dts"
SRC_URI += "file://mono-gateway-dk-sdk.dts"

# Force initramfs integration into the kernel
INITRAMFS_IMAGE = "recovery-image"
INITRAMFS_IMAGE_NAME = "recovery-image.rootfs"
INITRAMFS_IMAGE_BUNDLE = "1"

KERNEL_DEVICETREE = "freescale/mono-gateway-dk.dtb"
KERNEL_DEVICETREE += "freescale/mono-gateway-dk-sdk.dtb"

COMPATIBLE_MACHINE = "gateway-dk"

do_configure:prepend() {
    cp ${UNPACKDIR}/mono-gateway-dk.dts ${S}/arch/arm64/boot/dts/freescale/
    cp ${UNPACKDIR}/mono-gateway-dk-sdk.dts ${S}/arch/arm64/boot/dts/freescale/
}

