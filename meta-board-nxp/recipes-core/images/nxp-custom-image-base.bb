# Build a simple, minimal root filesystem.
#
# This recipe is a simplified form of core-image-minimal.

SUMMARY = "A console-only image that fully supports the target device \
hardware."

LICENSE = "MIT"

inherit core-image

IMAGE_FEATURES += " \
    debug-tweaks \
    dev-pkgs \
    splash \
    ssh-server-openssh \
    hwcodecs \
    tools-profile \
    tools-sdk \
    tools-debug \
    hwcodecs \
"

IMAGE_INSTALL += " \
    firmwared \
    packagegroup-imx-core-tools \
    packagegroup-imx-security \
    libgpiod \
    python3 \
    python3-pip \
    python3-modules \
    python3-misc \
    python3-spidev \
    spitools \
    spidev-test \
    python3-numpy \
    python3-pillow \
"

IMAGE_LINGUAS = " "
