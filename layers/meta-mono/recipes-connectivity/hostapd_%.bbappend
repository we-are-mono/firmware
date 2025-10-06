FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://hostapd.conf"

do_install:append() {
    # Override the default hostapd.conf with our custom one
    install -m 0644 ${UNPACKDIR}/hostapd.conf ${D}${sysconfdir}/hostapd.conf
}