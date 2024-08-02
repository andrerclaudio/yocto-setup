DESCRIPTION = "A simple and tiny image with network compatibility."
LICENSE = "MIT"




include recipes-st/images/st-image.inc

inherit core-image

IMAGE_LINGUAS = "en-us"

IMAGE_FEATURES += "\
    package-management  \
    ssh-server-dropbear \
    "

#
# INSTALL addons
#
CORE_IMAGE_EXTRA_INSTALL += " \
    resize-helper \
    \
    packagegroup-framework-core-base    \
    packagegroup-framework-tools-base   \
    \
    ${@bb.utils.contains('COMBINED_FEATURES', 'optee', 'packagegroup-optee-core', '', d)}   \
    ${@bb.utils.contains('COMBINED_FEATURES', 'optee', 'packagegroup-optee-test', '', d)}   \
    tcpdump \
    "





# CORE_IMAGE_EXTRA_INSTALL:append     = " \
#                                         \
#                                         \
#                                     "

# CORE_IMAGE_EXTRA_INSTALL:append     = " \
#                                         packagegroup-eval \
#                                         packagegroup-dev \
#                                         \
#                                     "
                   
# IMAGE_FEATURES  += "${@bb.utils.contains('CORE_IMAGE_EXTRA_INSTALL', 'packagegroup-eval', 'debug-tweaks', '', d)}"
# IMAGE_FEATURES  += "${@bb.utils.contains('CORE_IMAGE_EXTRA_INSTALL', 'packagegroup-dev', 'dev-pkgs', '', d)}"


# inherit core-image
