# Provides the i.MX common settings

include conf/machine/include/fsl-default-settings.inc
include conf/machine/include/fsl-default-versions.inc

require conf/machine/include/utilities.inc

MACHINEOVERRIDES =. "use-${IMX_DEFAULT_BSP}-bsp:"

# UBOOT_BINARY is used inside the wks files to dynamically find the required
# U-Boot file.
UBOOT_BINARY ?= "u-boot.${UBOOT_SUFFIX}"

UBOOT_SUFFIX ?= "${UBOOT_SUFFIX:pn-${IMX_DEFAULT_BOOTLOADER}}"

# We need to export the original variable to allow it to be used when generating
# wic based images.
SPL_BINARY ??= "${@get_spl_binary(d)}"

IMX_DEFAULT_MFGTOOL = "${@bb.utils.contains('IMX_DEFAULT_BOOTLOADER', 'u-boot-imx','u-boot-imx-mfgtool', 'u-boot-fslc', d)}"

PREFERRED_PROVIDER_u-boot ??= "${IMX_DEFAULT_BOOTLOADER}"
PREFERRED_PROVIDER_u-boot-mfgtool ??= "${IMX_DEFAULT_MFGTOOL}"
PREFERRED_PROVIDER_virtual/bootloader ??= "${IMX_DEFAULT_BOOTLOADER}"

UBOOT_ENTRYPOINT ?= "0x40480000"

# Some derivates can utilize the boot container provided by U-Boot,
# below variable sets that those machines which have a imx-boot-container
# in their MACHINEOVERRIDES can inherit a imx-boot-container class
UBOOT_PROVIDES_BOOT_CONTAINER = "0"
UBOOT_PROVIDES_BOOT_CONTAINER:imx-boot-container = "1"

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
SOC_ATF_BOOT_UART_BASE = ""

SOC_ATF_BOOT_UART_BASE = "0x30890000"
ATF_BOOT_UART_BASE ?= "${SOC_ATF_BOOT_UART_BASE}"

# Ship kernel modules
MACHINE_EXTRA_RRECOMMENDS = "kernel-modules"

INHERIT += "machine-overrides-extender"

#######
### NXP BSP specific overrides
#######
MACHINEOVERRIDES_EXTENDER:mx8mn:use-nxp-bsp  = "imx-generic-bsp:imx-nxp-bsp:mx8-generic-bsp:mx8-nxp-bsp:mx8m-generic-bsp:mx8m-nxp-bsp:mx8mn-generic-bsp:mx8mn-nxp-bsp"
MACHINEOVERRIDES_FILTERED_OUT_QA_ERROR = "%s overrides cannot be used and need conversion to use the new BSP-specific overrides. Check 'meta-freescale/scripts/convert-bsp-specific-overrides'."

MACHINE_ARCH_FILTER = "virtual/kernel"
INHERIT += "fsl-dynamic-packagearch"

# Firmware used for boot.
IMX_EXTRA_FIRMWARE                   ?= ""

MACHINE_EXTRA_RRECOMMENDS += "${MACHINE_FIRMWARE}"

# Extra udev rules
MACHINE_EXTRA_RRECOMMENDS += "udev-rules-imx"

# Jailhouse
MACHINE_EXTRA_RRECOMMENDS += " \
    ${@bb.utils.filter('COMBINED_FEATURES', 'jailhouse', d)} \
"

# Handle default kernel
IMX_DEFAULT_KERNEL = "linux-imx"

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

SOC_DEFAULT_WKS_FILE ?= "imx-uboot-bootpart.wks.in"

WKS_FILE ?= "${SOC_DEFAULT_WKS_FILE}"

KERNEL_IMAGETYPE = "zImage"
KERNEL_IMAGETYPE:aarch64 = "Image"

HOSTTOOLS_NONFATAL:append = " sha384sum"

# Allow meta-imx to add NIP devices information until upstreamed.
include conf/machine/include/imx-base-extend.inc
