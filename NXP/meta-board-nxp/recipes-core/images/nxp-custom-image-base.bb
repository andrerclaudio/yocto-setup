DESCRIPTION = "A simple image with network compatibility."

LICENSE = "MIT"

IMAGE_INSTALL:append    = " btop locale-setter"

IMAGE_FEATURES:append   = " debug-tweaks dev-pkgs"

CORE_IMAGE_EXTRA_INSTALL:append = "\
    packagegroup-core-boot \
    packagegroup-fsl-network \
    packagegroup-eval \
"

# SPI Nor-Flash
NETWORK_TOOLS:append:fsl-lsch3 = "\
    mtd-utils \
"

inherit core-image