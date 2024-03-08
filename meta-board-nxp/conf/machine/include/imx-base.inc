# Provides the i.MX common settings

require conf/machine/include/utilities.inc

# Machines or distros can define which BSP it should use by default. We are
# intending to default for mainline BSP by default and specific machines or
# DISTROs might change it if need.
#
# Two values are considered valid: mainline, nxp
# IMX_DEFAULT_BSP ?= "mainline"

MACHINEOVERRIDES =. "use-${IMX_DEFAULT_BSP}-bsp:"

# UBOOT_BINARY is used inside the wks files to dynamically find the required
# U-Boot file.
UBOOT_BINARY ?= "u-boot.${UBOOT_SUFFIX}"

# Using the 'IMX_DEFAULT_BOOTLOADER' the machine can support multiple bootloader
# versions. This is done for NXP reference board where we support 'u-boot-fslc'
# and 'u-boot-imx'.
#
IMX_DEFAULT_BOOTLOADER ??= "u-boot-imx"
UBOOT_SUFFIX ?= "${UBOOT_SUFFIX:pn-${IMX_DEFAULT_BOOTLOADER}}"

# We need to export the original variable to allow it to be used when generating
# wic based images.
SPL_BINARY ??= "${@get_spl_binary(d)}"

IMX_DEFAULT_MFGTOOL = "u-boot-imx-mfgtool"

PREFERRED_PROVIDER_u-boot ??= "${IMX_DEFAULT_BOOTLOADER}"
PREFERRED_PROVIDER_u-boot-mfgtool ??= "${IMX_DEFAULT_MFGTOOL}"
PREFERRED_PROVIDER_virtual/bootloader ??= "${IMX_DEFAULT_BOOTLOADER}"

UBOOT_ENTRYPOINT   = "0x40480000"

# Some derivates can utilize the boot container provided by U-Boot,
# below variable sets that those machines which have a imx-boot-container
# in their MACHINEOVERRIDES can inherit a imx-boot-container class
UBOOT_PROVIDES_BOOT_CONTAINER = "0"
# UBOOT_PROVIDES_BOOT_CONTAINER:imx-boot-container = "1"

# Trusted Firmware for Cortex-A (TF-A) can have different providers, either
# from upstream or from NXP downstream fork. Below variable defines which TF-A
# shall be taken into the build, and will be integrated into runtime image.
#
# Upstream TF-A recipe resides in the meta-arm layer and in maintained by OE
# community. Therefore, in order to add upstream TF-A - additional layer has
# to be included in the bblayers.con file. Compatible machines are added to
# this layer via dynamic-layers mechanism.
#
# NOTE: Current upstream TF-A version (v2.7) does not support HAB feature of
# i.MX8M family. If the upstream TF-A version is chosen, then HAB will not be
# available for all SoCs that are opting-in. This might change with future TF-A
# release, so this statement shall be kept here until support is added.
#
# Default TF-A provider to NXP downstream fork
IMX_DEFAULT_ATF_PROVIDER ??= "imx-atf"

# Allow setting the UART used during the boot by ATF.

# FIXME: We should return INVALID here but currently only i.MX8M has support to override the UART
# base address in source code.
SOC_ATF_BOOT_UART_BASE                    = "0x30890000"
ATF_BOOT_UART_BASE                       ?= "${SOC_ATF_BOOT_UART_BASE}"

PREFERRED_PROVIDER_virtual/xserver      = "xserver-xorg"
XSERVER_DRIVER                          = "xf86-video-fbdev"
XSERVER_DRIVER:append                   = " xf86-video-modesetting"
XSERVER = "xserver-xorg \
           xf86-input-evdev \
           ${XSERVER_DRIVER}"

# Ship kernel modules
MACHINE_EXTRA_RRECOMMENDS = "kernel-modules"

# Tunes for hard/soft float-point selection. Note that we allow building for
# thumb support giving distros the chance to enable thumb by setting
# ARM_INSTRUCTION_SET = "thumb"
#
# handled by software
# DEFAULTTUNE:mx6-generic-bsp ?= "cortexa9t-neon"
# handled by hardware
# 
DEFAULTTUNE   ?= "cortexa53-crypto"

INHERIT += "machine-overrides-extender"

#######
### NXP BSP specific overrides
#######

MACHINEOVERRIDES_EXTENDER:mx8mn:use-nxp-bsp  = "imx-generic-bsp:imx-nxp-bsp:imxdrm:imxgpu:imxgpu3d:imxvulkan:mx8-generic-bsp:mx8-nxp-bsp:mx8m-generic-bsp:mx8m-nxp-bsp:mx8mn-generic-bsp:mx8mn-nxp-bsp"

MACHINEOVERRIDES_EXTENDER_FILTER_OUT = " \
    mx8 \
    mx8m \
    mx8mn \
"

MACHINEOVERRIDES_FILTERED_OUT_QA_ERROR = "%s overrides cannot be used and need conversion to use the new BSP-specific overrides. Check 'meta-freescale/scripts/convert-bsp-specific-overrides'."

# Sub-architecture support
MACHINE_SOCARCH_SUFFIX  = "-mx8mn"

MACHINE_ARCH_FILTER = "virtual/kernel"
MACHINE_SOCARCH_FILTER:append = " \
    alsa-lib \
    gstreamer1.0 \
    weston \
"
MACHINE_SOCARCH_FILTER:append = " \
    opencl-icd-loader \
    opencl-clhpp \
    opencl-headers \
"

INHERIT += "fsl-dynamic-packagearch"

SIGGEN_EXCLUDE_SAFE_RECIPE_DEPS:append = " \
    imx-gpu-viv->kernel-module-imx-gpu-viv \
    libimxvpuapi->virtual/imxvpu \
    imx-vpuwrap->virtual/imxvpu \
    imx-codec->virtual/imxvpu \
    imx-test->virtual/imxvpu \
"

# Firmware used for boot.
IMX_EXTRA_FIRMWARE                  ?= ""
# IMX_EXTRA_FIRMWARE:mx8-generic-bsp   = "firmware-imx-8 imx-sc-firmware imx-seco"
IMX_EXTRA_FIRMWARE  = "firmware-imx-8m"
# IMX_EXTRA_FIRMWARE:mx8x-generic-bsp  = "imx-sc-firmware imx-seco"

# Firmware
MACHINE_FIRMWARE ?= ""
# MACHINE_FIRMWARE:append:mx8mn-generic-bsp  = " linux-firmware-imx-sdma-imx7d"
MACHINE_EXTRA_RRECOMMENDS += "${MACHINE_FIRMWARE}"

# Extra audio support
IMX_ALSA_EXTRA = "imx-alsa-plugins"
MACHINE_EXTRA_RRECOMMENDS:append = " ${@bb.utils.contains('DISTRO_FEATURES', 'alsa', '${IMX_ALSA_EXTRA}', '', d)}"

# Extra Cypress Wi-Fi and BTW firmware (Murata)
MACHINE_FIRMWARE:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'bcm43455', 'linux-firmware-bcm43455', '', d)}"
MACHINE_FIRMWARE:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'bcm4356', 'linux-firmware-bcm4356-pcie', '', d)}"
MACHINE_FIRMWARE:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'bcm4359', 'linux-firmware-bcm4359-pcie', '', d)}"

#Extra NXP89xx Wi-Fi and Bluetooth driver
MACHINE_FIRMWARE:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'nxp8987', 'kernel-module-nxp89xx', '', d)}"

# Extra NXP Wi-Fi and Bluetooth driver firmware and driver
MACHINE_FIRMWARE:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'nxp8987-sdio', 'firmware-nxp-wifi-nxp8987-sdio', '', d)}"
MACHINE_EXTRA_RRECOMMENDS:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'nxp8987-sdio', 'kernel-module-nxp89xx', '', d)}"

# Extra udev rules
MACHINE_EXTRA_RRECOMMENDS += "udev-rules-imx"

# Jailhouse
MACHINE_EXTRA_RRECOMMENDS += " \
    ${@bb.utils.filter('COMBINED_FEATURES', 'jailhouse', d)} \
"

# GStreamer 1.0 plugins
MACHINE_GSTREAMER_1_0_PLUGIN ?= "imx-gst1.0-plugin"

# Determines if the SoC has support for Vivante kernel driver
SOC_HAS_VIVANTE_KERNEL_DRIVER_SUPPORT = "1"

# Handle Vivante kernel driver setting:
#   0 - machine does not have Vivante GPU driver support
#   1 - machine has Vivante GPU driver support
MACHINE_HAS_VIVANTE_KERNEL_DRIVER_SUPPORT ?= "${SOC_HAS_VIVANTE_KERNEL_DRIVER_SUPPORT}"

# Handle default kernel
IMX_DEFAULT_KERNEL      = "linux-imx"
PREFERRED_PROVIDER_virtual/kernel ??= "${IMX_DEFAULT_KERNEL}"
SOC_DEFAULT_IMAGE_FSTYPES = "wic.bmap wic.gz wic.zst"

# Do not update fstab file when using wic images
WIC_CREATE_EXTRA_ARGS ?= "--no-fstab-update"

OPTEE_BIN_EXT ??= ""
OPTEE_BOOT_IMAGE:aarch64 = "tee.bin"

IMAGE_FSTYPES ?= "${SOC_DEFAULT_IMAGE_FSTYPES}"

IMAGE_BOOT_FILES ?= " \
    ${KERNEL_IMAGETYPE} \
    ${@make_dtb_boot_files(d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'optee', '${OPTEE_BOOT_IMAGE}', '', d)} \
"

### wic default support
OPTEE_WKS_FILE_DEPENDS         = "optee-os"
OPTEE_WKS_FILE_DEPENDS:aarch64 = ""

WKS_FILE_DEPENDS ?= " \
    virtual/bootloader \
    \
    e2fsprogs-native \
    bmap-tools-native \
    ${@bb.utils.contains('MACHINE_FEATURES', 'optee', '${OPTEE_WKS_FILE_DEPENDS}', '', d)} \
"

# We need to restrict the append so we don't add this for other i.MX SoC's.
# Derivatives that are not yet adopted the usage of boot container provided
# by U-Boot build are still targeted to use 'imx-boot' package provided by
# NXP.
#
# Moving those derivatives to mainline BSP would require to define an
# 'imx-boot-container' override, and test if the U-Boot built 'flash.bin'
# binary is used a replacement.
#
# NOTE: the results binary name of the boot container is set to 'imx-boot'
# for both NXP and Mainline BSP.
WKS_FILE_DEPENDS:append:aarch64 = " imx-boot"

SOC_DEFAULT_WKS_FILE ?= "imx-uboot-bootpart.wks.in"
WKS_FILE ?= "${SOC_DEFAULT_WKS_FILE}"

KERNEL_IMAGETYPE = "zImage"
KERNEL_IMAGETYPE:aarch64 = "Image"

MACHINE_FEATURES = "usbgadget usbhost vfat alsa touchscreen"

HOSTTOOLS_NONFATAL:append = " sha384sum"

# Allow meta-imx to add NIP devices information until upstreamed.
include conf/machine/include/imx-base-extend.inc