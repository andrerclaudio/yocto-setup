# Build a simple, minimal root filesystem.
#

SUMMARY = "A console-only image that fully supports the target device \
hardware in its minimun version."

LICENSE = "MIT"

CORE_IMAGE_EXTRA_INSTALL:append = " "

IMAGE_FEATURES += " \
    \
    debug-tweaks \
    splash \
    \
"

IMAGE_INSTALL = "packagegroup-core-boot"
IMAGE_INSTALL += " ${@bb.utils.contains("IMAGE_FEATURES", "debug-tweaks", "packagegroup-eval", "", d)}"
# IMAGE_INSTALL += " ${@bb.utils.contains("DISTRO_FEATURES", "wifi", "packagegroup-fsl-network", "", d)}"

inherit core-image
