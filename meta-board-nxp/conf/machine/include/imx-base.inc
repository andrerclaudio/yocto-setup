# Provides the i.MX common settings

include conf/machine/include/fsl-default-settings.inc
require conf/machine/include/utilities.inc

MACHINEOVERRIDES =. "use-${IMX_DEFAULT_BSP}-bsp:"

# UBOOT_BINARY is used inside the wks files to dynamically find the required
# U-Boot file.
UBOOT_BINARY ?= "u-boot.${UBOOT_SUFFIX}"

PREFERRED_PROVIDER_u-boot ??= "${IMX_DEFAULT_BOOTLOADER}"
PREFERRED_PROVIDER_u-boot-mfgtool ??= "${IMX_DEFAULT_BOOTLOADER}"
PREFERRED_PROVIDER_virtual/bootloader ??= "${IMX_DEFAULT_BOOTLOADER}"

PREFERRED_PROVIDER_u-boot-mxsboot-native ??= "u-boot-fslc-mxsboot-native"

UBOOT_ENTRYPOINT   ?= "0x40480000"

IMX_DEFAULT_ATF_PROVIDER ??= "imx-atf"

# FIXME: We should return INVALID here but currently only i.MX8M has support to override the UART
# base address in source code.
SOC_ATF_BOOT_UART_BASE                    = ""

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

DEFAULTTUNE   ?= "cortexa53-crypto"

INHERIT += "machine-overrides-extender"

IMX_SOC_REV                    ??= "A0"

IMX_SOC_REV_LOWER   = "${@d.getVar('IMX_SOC_REV').lower()}"
IMX_SOC_REV_UPPER   = "${@d.getVar('IMX_SOC_REV').upper()}"

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
MACHINE_SOCARCH_SUFFIX   = "-mx8mn"

MACHINE_ARCH_FILTER = "virtual/kernel"

INHERIT += "fsl-dynamic-packagearch"

# Firmware used for boot.
IMX_EXTRA_FIRMWARE                   ?= ""

# Firmware
MACHINE_FIRMWARE ?= ""
MACHINE_EXTRA_RRECOMMENDS += "${MACHINE_FIRMWARE}"

# Extra audio support
IMX_ALSA_EXTRA = ""

# Extra udev rules
MACHINE_EXTRA_RRECOMMENDS += "udev-rules-imx"

# Handle Vivante kernel driver setting:
#   0 - machine does not have Vivante GPU driver support
#   1 - machine has Vivante GPU driver support
MACHINE_HAS_VIVANTE_KERNEL_DRIVER_SUPPORT ?= "0"

# Handle default kernel
IMX_DEFAULT_KERNEL      = "linux-imx"

PREFERRED_PROVIDER_virtual/kernel ??= "${IMX_DEFAULT_KERNEL}"

SOC_DEFAULT_IMAGE_FSTYPES = "wic.bmap wic.gz wic.zst"

# Do not update fstab file when using wic images
WIC_CREATE_EXTRA_ARGS ?= "--no-fstab-update"

OPTEE_BIN_EXT ??= ""
OPTEE_BOOT_IMAGE         = "tee.bin uTee-${OPTEE_BIN_EXT}"
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

WKS_FILE_DEPENDS:append:aarch64 = "imx-boot"

SOC_DEFAULT_WKS_FILE ?= "imx-uboot-bootpart.wks.in"
SOC_DEFAULT_WKS_FILE ?= "imx-imx-boot-bootpart.wks.in"

WKS_FILE ?= "${SOC_DEFAULT_WKS_FILE}"

SERIAL_CONSOLES = "115200;ttymxc0"

KERNEL_IMAGETYPE = "zImage"
KERNEL_IMAGETYPE:aarch64 = "Image"

HOSTTOOLS_NONFATAL = " sha384sum"

# Add task to generate the mfgtool bundle for the image.
IMAGE_CLASSES += "image_types_fsl"
IMAGE_CLASSES:append = " image_populate_mfgtool"

# Allow meta-imx to add NIP devices information until upstreamed.
include conf/machine/include/imx-base-extend.inc
