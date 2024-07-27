DESCRIPTION = "A simple and tiny image with network compatibility."
LICENSE = "MIT"

# Base this image on core-image-base
include recipes-core/images/core-image-base.bb

COMPATIBLE_MACHINE = "^rpi$"

IMAGE_INSTALL:append    = " nano \
                            htop \
                            util-linux \
                            procps \
                            bcm2835-tests \
                            raspi-gpio \
                            connman \
                            connman-client \
                            wireless-regdb-static \                        
                        "

IMAGE_FEATURES:append = " debug-tweaks"