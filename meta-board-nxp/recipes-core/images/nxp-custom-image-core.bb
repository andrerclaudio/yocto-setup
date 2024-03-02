# Build a simple, minimal root filesystem.
#
# This recipe is a simplified form of core-image-minimal.

SUMMARY = "A console-only image that fully supports the target device \
hardware."

LICENSE = "MIT"

inherit core-image

CORE_IMAGE_EXTRA_INSTALL:append = "\
    packagegroup-core-boot \
    packagegroup-core-full-cmdline \
"

IMAGE_FEATURES += " \
    tools-profile \
    debug-tweaks \
    ssh-server-openssh \
"

# SPI Nor-Flash
NETWORK_TOOLS:append:fsl-lsch3 = "\
    mtd-utils \
"

IMAGE_INSTALL += " "

IMAGE_LINGUAS = " "

# Image Timezone settings
# DEFAULT_TIMEZONE = "America/Sao_Paulo"

# Add to your layers "meta-wireless-hwinit" and change SSID and PASSWORD in "wpa_supplicant-nl80211-mlan0.conf"
# to enable board Wifi.

# Set machine Hostname
# hostname:pn-base-files = "iMX8mn-evk"

# Defines additional free disk space created in the image in Kbytes. By default, this variable is set to “0”. 
# This free disk space is added to the image after the build system determines the image size as described in IMAGE_ROOTFS_SIZE.
# IMAGE_ROOTFS_EXTRA_SPACE = "655360"

# export IMAGE_BASENAME = "agnes-imx8mn-core"
