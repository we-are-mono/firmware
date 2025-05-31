SUMMARY = "Minimal BusyBox initramfs for Gateway Development Kit"

# Inherit initramfs image class (leaner) instead of core-image
inherit image

# Keep it minimal - just BusyBox and essential packages that should be
# sufficient for a rescue system; Basic networking, partitioning and compression.
IMAGE_INSTALL = "busybox base-files kmod udev udev-rules-qoriq \
                parted util-linux-fdisk util-linux-lsblk util-linux-blkid \
                e2fsprogs mmc-utils mtd-utils \
                dropbear wget curl tftp-hpa \
                gzip unzip tar \
                shadow watchdog \
                i2c-tools stressapptest"

# We don't want any root password for the rescue system
EXTRA_USERS_PARAMS = "usermod -p '' root;"

# This line ensures no root password is needed for login
IMAGE_FEATURES += "debug-tweaks"

# Remove package management and other bloat
IMAGE_FEATURES:remove = "package-management"

# We want compressed version of initramfs
IMAGE_FSTYPES = "cpio.gz"

# Optional, but if we don't set it, it has machine in the name by default
IMAGE_NAME = "${IMAGE_BASENAME}${IMAGE_NAME_SUFFIX}"
