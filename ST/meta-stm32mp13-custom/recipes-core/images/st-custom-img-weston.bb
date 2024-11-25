SUMMARY = "A very basic Wayland image with a terminal"

LICENSE = "MIT"

# Add Wayland features for hardware video codecs and the Weston compositor
IMAGE_FEATURES += "hwcodecs weston"
# Include screen and touchscreen support in the machine features
MACHINE_FEATURES += "screen touchscreen"

# Install additional useful tools in the image (commented out for now)
IMAGE_INSTALL:append = " procps util-linux nano openssh btop fastfetch"

# Mask the Weston-init bbappend to prevent conflicts with custom configurations
ST_ECOSYSTEM_GRAPHICAL_PATH = "STM32MPU-Ecosystem-v6.6.0/Distribution-Package/layers/meta-st/meta-st-openstlinux/recipes-graphics"
BBMASK += "${ST_ECOSYSTEM_GRAPHICAL_PATH}/wayland/weston-init.bbappend"

# Exclude the .tar.xz file format from the generated filesystem images to reduce build size
IMAGE_FSTYPES:remove = " tar.xz"

# Inherit core image features and functionality
inherit core-image
