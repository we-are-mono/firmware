# Mono Gateway Firmware

TODO: Put an intro here that will make people believe this is the best Yocto project to have every existed.

## Getting started

After you clone this repository, make sure you have [kas](https://github.com/siemens/kas) instaled - the best option to do so is to use [pipx](https://pipx.pypa.io/stable/installation/).

Also, don't forget to copy the `site.conf.example` to `site.conf` and edit it to suit your environment.

Once you have it installed, simply run `$ kas build kas/gateway-dk.yml` and once it's done building (it may take hours on the first attempt!), you can find everything in `build/tmp/deploy/images/gateway-dk/`.

If you want to test it locally, make sure you have [qemu](https://www.qemu.org/) installed, then run:

```
$ qemu-system-aarch64 \
  -machine virt \
  -m 2048 \
  -cpu cortex-a72 \
  -kernel ./build/tmp/deploy/images/gateway-dk/Image.gz-initramfs-gateway-dk.bin \
  -append "console=ttyAMA0 root=/dev/ram0" \
  -nographic
```

When prompted, just enter `root` for the user, no password needed. To exit qemu, press `Ctrl+a`, followed by `x`.

## Firmware image memory map

The final firmware image has the following memory map:

```
0x000000    - RCW + BL2
0x100000    - BL3 (u-boot) 
0x300000    - u-boot environment
0x400000    - Frame manager microcode
0x500000    - Recovery system device tree
0xA00000    - Kernel + initramfs
```

### Rationale

The very first thing NXP QoriIQ family of CPUs need to access while booting is the reset configuration word (RCW), which is why it needs to be positioned at offset 0x0. It gets automatically bundled with ATF's BL2 image.

Next, we have BL3, part of which is u-boot, and just like the RCW, it gets bundled in ATF, this time in BL3 and placed at a 1 MB offset (`0x100000` in hex).

Because Bl3 ends up around 1 MB in size, we give it 2 MB, just to make sure to have enough buffer and there's no overlap with u-boot environment, which then gets placed at 3 MB offset (`0x300000` in hex).

Frame manager gets placed at 4 MB offset and is a binary that's provided by [NXP](https://github.com/nxp-qoriq/qoriq-fm-ucode). This microcode is necessary because the CPU supports Hardware Offloading of networking and will not work without it.

We also have a Linux recovery system as part of the firmware image, and we need two components for it to work properly: a) a device tree, which is placed at 5 MB offset (`0x500000` in hex) and b) a kernel, which is placed at 10 MB offset (`0xA00000` in hex). Why 10 MB? Because the kernel image also includes a basic recovery *initramfs* and combined, they amount to ~19 MB. Since we're aiming at 32 MB firmware image, placing it at 10 MB makes it easier organizationally as well as leaves some buffer towards the end of the image in case we need to add something crucial to either the kernel or initramfs.

This does indeed leave a 4 MB gap between the device tree and the kernel image, and that's fine, we can find other uses for it in the future or leave it empty.

