DESCRIPTION = "A simple and tiny image with network compatibility."
LICENSE = "MIT"

# Base this image on core-image-base
include recipes-core/images/core-image-base.bb

COMPATIBLE_MACHINE = "^rpi$"

IMAGE_INSTALL:append    = " nano \
                            htop \
                            btop \
                            fastfetch \
                            util-linux \
                            procps \
                            openssh \
                            linux-firmware-bcm43430 \
                            raspi-gpio \
                            connman \
                            connman-client \
                            wireless-regdb-static \   
                            bluez5 \
                            i2c-tools \
                            python3-smbus \
                            bridge-utils \
                            hostapd \
                            dhcpcd \
                            dnsmasq \
                            iptables \
                            wpa-supplicant \
                        "

IMAGE_FEATURES:append = " debug-tweaks"

# Generate the images format as below
IMAGE_FSTYPES = "tar.bz2 wic.bz2"
