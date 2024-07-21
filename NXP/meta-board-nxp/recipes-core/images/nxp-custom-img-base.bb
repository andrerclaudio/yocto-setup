DESCRIPTION = "A simple and tiny image with network compatibility."

LICENSE = "MIT"

CORE_IMAGE_EXTRA_INSTALL:append     = " \
                                        packagegroup-core-boot \
                                        packagegroup-fsl-network \
                                        \
                                    "

CORE_IMAGE_EXTRA_INSTALL:append     = " \
                                        packagegroup-eval \
                                        packagegroup-dev \
                                        \
                                    "

# SPI Nor-Flash
NETWORK_TOOLS:append:fsl-lsch3      = " \
                                        mtd-utils \
                                        \
                                    "

IMAGE_FEATURES                      += "${@bb.utils.contains('CORE_IMAGE_EXTRA_INSTALL', 'packagegroup-eval', 'debug-tweaks', '', d)}"
IMAGE_FEATURES                      += "${@bb.utils.contains('CORE_IMAGE_EXTRA_INSTALL', 'packagegroup-dev', 'dev-pkgs', '', d)}"


inherit core-image