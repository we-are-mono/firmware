SUMMARY = "Minimal BusyBox initramfs for Gateway Development Kit"
DESCRIPTION = "Initramfs-only image with BusyBox utilities"

# Inherit initramfs image class instead of core-image
inherit image

# Keep it minimal - just BusyBox and essential packages
IMAGE_INSTALL = "busybox base-files parted e2fsprogs-mke2fs \
                mtd-utils kmod util-linux-fdisk dropbear shadow \
                wget curl tftp-hpa gzip bzip2 unzip"

# We don't want any root password for the rescue system
EXTRA_USERS_PARAMS = "usermod -p '' root;"

# This line ensures no root password is needed for login
IMAGE_FEATURES += "debug-tweaks"

# Remove package management and other bloat
IMAGE_FEATURES:remove = "package-management"

# We want compressed version of initramfs
IMAGE_FSTYPES = "cpio.gz"