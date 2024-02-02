# Build a simple, minimal root filesystem.
#
# This recipe is a simplified form of core-image-minimal.

SUMMARY = "A console-only image that fully supports the target device \
hardware."

LICENSE = "MIT"

inherit core-image

IMAGE_FEATURES += " \
    debug-tweaks \
    splash \
    ssh-server-openssh \
    hwcodecs \
"

IMAGE_INSTALL += " \
    firmwared \
    packagegroup-imx-core-tools \
    packagegroup-imx-security \
"

IMAGE_LINGUAS = " "

# Set machine Hostname
hostname:pn-base-files = "iMX8mn-evk"

export IMAGE_BASENAME = "custom-imx8mn-base"
