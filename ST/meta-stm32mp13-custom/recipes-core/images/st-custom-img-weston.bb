SUMMARY = "A very basic Wayland image with a terminal"

LICENSE = "MIT"

# Add Wayland features for hardware video codecs and the Weston compositor
IMAGE_FEATURES += "hwcodecs weston"
# Include screen and touchscreen support in the machine features
MACHINE_FEATURES += "screen touchscreen"

# Install custom network configuration tool (custom bb file)
IMAGE_INSTALL:append = " network-configuration"

# Install additional useful tools in the image (commented out for now)
# Uncomment the following line to install tools like procps, util-linux, nano, btop, fastfetch, and openssh
# IMAGE_INSTALL:append = " procps util-linux nano btop fastfetch openssh"

# Install timezone data package to configure timezones
IMAGE_INSTALL:append = " tzdata"
# Set the default timezone to Buenos Aires, Argentina
DEFAULT_TIMEZONE = "America/Argentina/Buenos_Aires"

# Mask the Weston-init bbappend to prevent conflicts with custom configurations
ST_ECOSYSTEM_GRAPHICAL_PATH = "STM32MPU-Ecosystem-v5.1.0/Distribution-Package/layers/meta-st/meta-st-openstlinux/recipes-graphics"
BBMASK += "${ST_ECOSYSTEM_GRAPHICAL_PATH}/wayland/weston-init.bbappend"

# Exclude the .tar.xz file format from the generated filesystem images to reduce build size
IMAGE_FSTYPES:remove = " tar.xz"

# Inherit core image features and functionality
inherit core-image
