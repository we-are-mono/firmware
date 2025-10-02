# meta-freescale layer already provides RCW that is good enough,
# we only have to point to our custom repository and branch
SRC_URI = "git://github.com/we-are-mono/rcw.git;protocol=https;branch=devtool"
SRCREV = "12d276914c2fa9d3959ae9438e867a872064eec1"
BOARD_TARGETS:gateway = "gateway_dk"
