# Override to remove WiFi dependencies but keep mtcp-dpdk for minimal system
# Remove the entire ls1046a append, then add back only what we want

RDEPENDS:${PN}:remove:ls1046a = "${NXP_WIFI_PKGS} mtcp-dpdk"
RDEPENDS:${PN}:append:ls1046a = " mtcp-dpdk"
