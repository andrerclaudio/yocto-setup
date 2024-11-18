DESCRIPTION = "A simple and tiny image with network compatibility."
LICENSE = "MIT"

# Base this image on core-image-base
include recipes-core/images/core-image-base.bb

COMPATIBLE_MACHINE = "^rpi$"

IMAGE_INSTALL:append    = " nano \
                            btop \
                            util-linux \
                            procps \
                            openssh \
                            linux-firmware-bcm43430 \
                            add-custom-script \
                        "

IMAGE_FEATURES:append = " debug-tweaks"

# Generate the images format as below
IMAGE_FSTYPES = "tar.bz2 wic.bz2"
