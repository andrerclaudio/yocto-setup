# Extend the search path for files to include the custom files directory
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

# Override the original SRC_URI to include the new images and the LICENSE file
SRC_URI = "${@bb.utils.contains('MACHINE_FEATURES', 'splashscreen', 'file://${UBOOT_SPLASH_LANDSCAPE_SRC} file://${UBOOT_SPLASH_PORTRAIT_SRC}', '', d)}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# Update the U-Boot splash screen sources
UBOOT_SPLASH_LANDSCAPE_SRC = "clouds_H_rgb_rle8_480x272.bmp"
UBOOT_SPLASH_PORTRAIT_SRC = "clouds_H_rgb_rle8_272x480.bmp"
