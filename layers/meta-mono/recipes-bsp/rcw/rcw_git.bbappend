# meta-freescale layer already provides RCW that is good enough,
# we only have to point to our custom repository and branch
SRC_URI = "git://github.com/we-are-mono/rcw.git;protocol=https;branch=mono-development"
SRCREV = "7dc6ed1e7e9cb2f3ed5c0c74a217370f5e09539b"
BOARD_TARGETS:gateway = "gateway_dk"
