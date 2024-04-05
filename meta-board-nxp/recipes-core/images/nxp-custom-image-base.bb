# Build a simple, minimal root filesystem.
#

SUMMARY = "A console-only image that fully supports the target device \
hardware in its minimun version."

LICENSE = "MIT"

inherit core-image

CORE_IMAGE_EXTRA_INSTALL:append = " "

IMAGE_FEATURES += " \
    \
    debug-tweaks \
    splash \
    \
"

IMAGE_INSTALL:append = " "