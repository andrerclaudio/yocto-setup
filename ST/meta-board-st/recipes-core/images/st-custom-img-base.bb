DESCRIPTION = "A simple and tiny image with network compatibility."
LICENSE = "MIT"


CORE_IMAGE_EXTRA_INSTALL:append     = " \
                                        \
                                        \
                                    "

# CORE_IMAGE_EXTRA_INSTALL:append     = " \
#                                         packagegroup-eval \
#                                         packagegroup-dev \
#                                         \
#                                     "
                   
# IMAGE_FEATURES  += "${@bb.utils.contains('CORE_IMAGE_EXTRA_INSTALL', 'packagegroup-eval', 'debug-tweaks', '', d)}"
# IMAGE_FEATURES  += "${@bb.utils.contains('CORE_IMAGE_EXTRA_INSTALL', 'packagegroup-dev', 'dev-pkgs', '', d)}"


inherit core-image
