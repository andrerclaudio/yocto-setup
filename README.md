# yocto-digest

`yocto-digest` is a ***work-in-progress*** project, intended to provide support
for some specifics boards (BSP files) and recipes to take advantage of those boards.


## What's inside?

This repository is composed of:

 * `meta-board-nxp`: layer with the metadata for NXP boards.
 * `tools`: folder with non-board and board related scripts to improve the hardware experience.

## How do I use it?

You must downloaded the NXP repositories and have it initialized already.
Follow the steps in the link below:

https://github.com/nxp-imx/imx-manifest/tree/imx-linux-mickledore


After applying the settings the link provided, do as below:

```bash
# Add the layer meta-board-nxp to the build
bitbake-layers add-layer path/to/layer/meta-board-nxp
# Change in the local.conf 
DISTRO ?= 'nxp-custom'
# Run your first build
bitbake -k nxp-custom-image-base

#
# Have dinner
#

# Find the output images here
ls -l place/where/you/set/NXP/files/tmp/deploy/images/imx8mn-lpddr4-evk/

# Flash the image (use the UUU tool from NXP):
sudo uuu -b emmc_all imx-boot-imx8mn-lpddr4-evk-sd.bin-flash_evk nxp-custom-image-base-imx8mn-lpddr4-evk.wic.zst
```