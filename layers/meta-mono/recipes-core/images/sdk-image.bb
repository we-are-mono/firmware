SUMMARY = "Minimal console-only systemd-based SDK image"
DESCRIPTION = "A minimal systemd-based root filesystem image for console/UART operation"

# Inherit core image class and user management
inherit core-image extrausers

# License
LICENSE = "MIT"

# Image features - writable rootfs for eMMC
IMAGE_FEATURES = ""

# We set root to have no password (for now)
EXTRA_USERS_PARAMS = "usermod -p '' root;"

# Image features - writable rootfs for eMMC, debug-tweaks for development
IMAGE_FEATURES = "debug-tweaks package-management"

# This is defined in machine.conf, but we override it here 
# to remove initramfs from the kernel image.
INITRAMFS_IMAGE_BUNDLE = "0"

# Essential packages
CORE_IMAGE_BASE_INSTALL = "\
    base-files \
    base-passwd \
    coreutils \
    dbus \
    init-ifupdown \
    initscripts \
    kernel-base \
    kernel-modules \
    netbase \
    os-release \
    shadow \
    systemd \
    systemd-compat-units \
    util-linux \
    "

# Additional minimal packages
IMAGE_INSTALL = "\
    ${CORE_IMAGE_BASE_INSTALL} \
    e2fsprogs \
    e2fsprogs-resize2fs \
    findutils \
    fmc \
    fmlib \
    glibc-utils \
    grep \
    gzip \
    htop \
    iperf3 \
    kernel-devicetree \
    kmod \
    less \
    packagegroup-fsl-networking-core \
    packagegroup-fsl-tools-extended \
    procps \
    psmisc \
    sed \
    systemd-analyze \
    systemd-serialgetty \
    stressapptest \
    tar \
    udev \
    udev-rules-qoriq \
    which \
    "

# Image configuration
IMAGE_LINGUAS = ""

# Remove unnecessary packages
PACKAGE_EXCLUDE = "\
    alsa-utils \
    bluez5 \
    busybox \
    pulseaudio \
    wireless-tools \
    wpa-supplicant \
    "

# Image size - generous sizing for 32GB eMMC
# Let Yocto auto-calculate based on package requirements, add 1GB extra space
IMAGE_ROOTFS_EXTRA_SPACE = "1048576"

# Image types to generate
IMAGE_FSTYPES = "tar.gz ext4"

# Ensure clean ext4 images
EXTRA_IMAGECMD:ext4 = "-F -i 4096 -J size=64"

# Disable services that aren't needed in minimal setup
SYSTEMD_DEFAULT_TARGET = "multi-user.target"

# Function to perform additional image customization
sdk_image_postprocess() {
    # Remove unnecessary systemd services
    rm -f ${IMAGE_ROOTFS}${systemd_system_unitdir}/systemd-networkd.service
    rm -f ${IMAGE_ROOTFS}${systemd_system_unitdir}/systemd-resolved.service

    # Create hugepages mount point and add to fstab
    mkdir -p ${IMAGE_ROOTFS}/mnt/hugepages
    echo "# Hugepages for DPDK" >> ${IMAGE_ROOTFS}${sysconfdir}/fstab
    echo "hugetlbfs /mnt/hugepages hugetlbfs defaults 0 0" >> ${IMAGE_ROOTFS}${sysconfdir}/fstab
}

ROOTFS_POSTPROCESS_COMMAND += "sdk_image_postprocess; "
