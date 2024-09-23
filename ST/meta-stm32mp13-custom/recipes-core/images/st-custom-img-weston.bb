SUMMARY = "A very basic Wayland image with a terminal"

IMAGE_FEATURES += "hwcodecs weston"

LICENSE = "MIT"

# Install some tools
IMAGE_INSTALL:append = " network-configuration rmtstats"

# Complementary 
IMAGE_INSTALL:append = " procps util-linux nano"

# Adjust Time and Zone
IMAGE_INSTALL:append = " tzdata"
# Set timezone area
DEFAULT_TIMEZONE = "America/Argentina/Buenos_Aires"

# Masked files 
ST_ECOSYSTEM_GRAPHICAL_PATH = "STM32MPU-Ecosystem-v5.1.0/Distribution-Package/layers/meta-st/meta-st-openstlinux/recipes-graphics"
BBMASK += "${ST_ECOSYSTEM_GRAPHICAL_PATH}/wayland/weston-init.bbappend"

# Avoid generating the Fstypes .tar.xz
IMAGE_FSTYPES:remove = " tar.xz"

inherit core-image
