SUMMARY = "Mono Gateway main OS for eMMC"
LICENSE = "MIT"

# Uses the full-featured distro
# COMPATIBLE_MACHINE = "mono-gateway"

inherit core-image

# Full feature set for main OS
CORE_IMAGE_EXTRA_INSTALL:append = "\
    packagegroup-core-boot \
    packagegroup-core-full-cmdline \
"

IMAGE_FSTYPES = "ext4"