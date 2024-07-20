DESCRIPTION = "A simple and tiny image with network compatibility."

LICENSE = "MIT"

IMAGE_INSTALL:append                = " fastfetch"

IMAGE_FEATURES:append               = " debug-tweaks"

CORE_IMAGE_EXTRA_INSTALL:append     = " \
                                        packagegroup-core-boot \
                                        packagegroup-fsl-network \
                                        \
                                    "

# CORE_IMAGE_EXTRA_INSTALL:append = "\
#     \
#     packagegroup-eval \
#     \
# "

# SPI Nor-Flash
NETWORK_TOOLS:append:fsl-lsch3      = " \
                                        mtd-utils \
                                        \
                                    "

inherit core-image