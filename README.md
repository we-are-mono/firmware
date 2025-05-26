# Mono Gateway Firmware

TODO: Put an intro here that will make people believe this is the best Yocto project to have every existed.

## Getting started

After you clone this repository, make sure you have [kas](https://github.com/siemens/kas) instaled - the best option to do so is to use [pipx](https://pipx.pypa.io/stable/installation/).

Also, don't forget to copy the `site.conf.example` example to `site.conf` and edit it to suit your environment.

Once you have it installed, simply run `$ kas shell kas/gateway-dk.yml -c "bitbake firmware"`

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