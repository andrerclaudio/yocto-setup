DESCRIPTION = "A simple and tiny image with network compatibility."

LICENSE = "MIT"

CORE_IMAGE_EXTRA_INSTALL:append     = " \
                                        packagegroup-core-boot \
                                        packagegroup-fsl-network \
                                        \
                                    "

# CORE_IMAGE_EXTRA_INSTALL:append     = " \
#                                         packagegroup-eval \
#                                         packagegroup-dev \
#                                         \
#                                     "

# CORE_IMAGE_EXTRA_INSTALL:append     = " \
#                                         packagegroup-imx-tools-audio \
#                                         \
#                                     "

# SPI Nor-Flash
NETWORK_TOOLS:append:fsl-lsch3      = " \
                                        mtd-utils \
                                        \
                                    "                        

# Remote access tool
# IMAGE_INSTALL:append = " openvpn-at-boot"

IMAGE_FEATURES  += "${@bb.utils.contains('CORE_IMAGE_EXTRA_INSTALL', 'packagegroup-imx-tools-audio', 'hwcodecs', '', d)}"
IMAGE_FEATURES  += "${@bb.utils.contains('CORE_IMAGE_EXTRA_INSTALL', 'packagegroup-eval', 'debug-tweaks', '', d)}"
IMAGE_FEATURES  += "${@bb.utils.contains('CORE_IMAGE_EXTRA_INSTALL', 'packagegroup-dev', 'dev-pkgs', '', d)}"
CORE_IMAGE_EXTRA_INSTALL  += "${@bb.utils.contains('IMAGE_INSTALL', 'openvpn-at-boot', 'debug-tweaks packagegroup-eval packagegroup-dev', '', d)}"


inherit core-image
