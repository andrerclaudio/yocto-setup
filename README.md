# yocto-digest

`yocto-digest` is a ***work-in-progress*** project, intended to provide support
for some specifics boards (BSP files) and recipes to take advantage of those boards.


## What's inside?

This repository is composed of:

 * `.config.yaml`: a kas configuration file.
 * `meta-board-nxp`: layer with the metadata for NXP boards.
 * `tools`: folder with non-board and board related scripts to improve the hardware experience.

The `.config.yaml` is the configuration file for the kas utility, which
allows to easily download all the required third-party components in the
correct place and enable them in the configuration. In this project it downloads
and enables:

 * the `poky` repository which contains the `meta` layer with all the "core" recipes and `bitbake` build engine.
 * the `meta-openembedded` repository which is a collection of layers to suppliment OE-Core with additional packages.<br>
   Note that ***meta-oe***, ***meta-python***, ***meta-multimedia*** and ***meta-networking*** are enabled in `build/conf/bblayers.conf`.
 * the `meta-board-nxp` layer, not downloaded as it is already part of this
   repository, but enabled in `build/conf/bblayers.conf`.<br>
   Please, refer to ***meta-board-nxp/README*** file to see more details about this layer and its goals.
 * the `tools` folder, not downloaded as it is already part of this repository.<br>
   Please, refer to ***tools/README*** file to see more details about this folder and its goals.

Using kas is not mandatory to use Yocto/OpenEmbedded, but it is
simple and convenient. You can use another tool for your project if so you
prefer.

## How do I use it?

According to the kas configuration file, after cloning this repository the
`imx8mn-lpddr4-evk` board is configured by default. Here's how you can have a
working image for that board in a few steps:

```bash
# If you don't have kas yet (needed once only):
pip install kas

# Use kas to download the third-party repositories needed
# (required the first time, or after changes to .config.yaml)
kas checkout

# Initialize the build environment
source poky/oe-init-build-env build

# Run your first build
bitbake -k nxp-custom-image-base

#
# Have dinner
#

# Find the output images here
ls -l tmp-glibc/deploy/images/imx8mn-lpddr4-evk/

# Flash the image (use the UUU tool from NXP):
sudo uuu -b emmc_all imx-boot-imx8mn-lpddr4-evk-sd.bin-flash_evk nxp-custom-image-base-imx8mn-lpddr4-evk.wic.zst
```