DESCRIPTION = "A simple image with network compatibility."

LICENSE = "MIT"

IMAGE_INSTALL:append    = " "

IMAGE_FEATURES:append   = " "

CORE_IMAGE_EXTRA_INSTALL:append = "\
    packagegroup-core-boot \
    packagegroup-eval \
"

inherit core-image