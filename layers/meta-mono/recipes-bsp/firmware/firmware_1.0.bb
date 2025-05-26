SUMMARY = "Complete firmware image for Gateway Development Kit"
DESCRIPTION = "Combines RCW+BL2, ATF+U-Boot, environment, FMAN ucode, and kernel into 32MB NOR flash image"
SECTION = "bootloaders"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

DEPENDS += "atf u-boot-env fman-ucode linux-mono"

inherit deploy

# Boot type - can be overridden at build time
BOOTTYPE ?= "qspi"

# No source needed - just assembly
SRC_URI = ""
S = "${WORKDIR}/src"

COMPATIBLE_MACHINE = "gateway-dk"

# NOR Flash layout configuration
FLASH_SIZE = "33554432"
BL2_OFFSET = "0"
# BL2_OFFSET_emmc = "4096"
FIP_OFFSET = "1048576"
ENV_OFFSET = "3145728"
FMAN_OFFSET = "4194304"
DTB_OFFSET = "5242880"
KERNEL_OFFSET = "10485760"

# Component files mapping
COMPONENT_FILES = " \
    bl2:${DEPLOY_DIR_IMAGE}/bl2_${BOOTTYPE}.pbl:${BL2_OFFSET} \
    fip:${DEPLOY_DIR_IMAGE}/fip.bin:${FIP_OFFSET} \
    env:${DEPLOY_DIR_IMAGE}/u-boot-env.bin:${ENV_OFFSET} \
    fman:${DEPLOY_DIR_IMAGE}/fman-ucode.bin:${FMAN_OFFSET} \
    dtb:${DEPLOY_DIR_IMAGE}/mono-gateway-dk-sdk.dtb:${DTB_OFFSET} \
    kernel:${DEPLOY_DIR_IMAGE}/Image.gz-initramfs-gateway-dk.bin:${KERNEL_OFFSET}"

do_compile() {
    cd ${S}
    
    # Create empty firmware image
    dd if=/dev/zero of=firmware-${BOOTTYPE}.bin bs=1 count=${FLASH_SIZE}
    
    # Process each component and bake it into the image we just created
    echo ${COMPONENT_FILES} | tr ' ' '\n' | while read component; do
        [ -z "$component" ] && continue
        
        name=$(echo $component | cut -d: -f1)
        file=$(echo $component | cut -d: -f2)  
        offset=$(echo $component | cut -d: -f3)
        size=$(stat -c%s "$file")

        dd if="$file" of=firmware-${BOOTTYPE}.bin bs=1 seek=$offset conv=notrunc
    done
}

do_deploy() {
    install -d ${DEPLOYDIR}
    install -m 644 ${S}/firmware-${BOOTTYPE}.bin ${DEPLOYDIR}/
    ln -sf firmware-${BOOTTYPE}.bin ${DEPLOYDIR}/firmware.bin
}

addtask deploy after do_compile before do_build