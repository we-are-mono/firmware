ATF_BRANCH = "mono-development"
ATF_SRC = "git://github.com/we-are-mono/atf.git;protocol=https"
SRC_URI = "${ATF_SRC};branch=${ATF_BRANCH}"
SRCREV = "270f20bdd33522d343b1c38a0a2fd165a8a0a16f"

# To get rid of UEFI requirement that comes with QorIQ meta layer
DEPENDS += "rcw u-boot"
do_compile[depends] = "u-boot:do_deploy rcw:do_deploy"

BOOTTYPE = "qspi emmc"
RCW_FOLDER = "gateway_dk"
PLATFORM:gateway = "gateway_dk"
UBOOT_BINARY = "${DEPLOY_DIR_IMAGE}/u-boot.bin"
