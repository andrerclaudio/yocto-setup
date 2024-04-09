DESCRIPTION = "A console-only image that fully supports the target device \
hardware in its minimun version."

LICENSE = "MIT"

inherit core-image

IMAGE_INSTALL += " \
    iperf3 \
"

IMAGE_FEATURES += " \
    debug-tweaks \
"

CORE_IMAGE_EXTRA_INSTALL:append = "\
    packagegroup-core-boot \
    packagegroup-fsl-network \
"

# SPI Nor-Flash
NETWORK_TOOLS:append:fsl-lsch3 = "\
    mtd-utils \
"
