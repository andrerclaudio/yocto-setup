# yocto-digest

`yocto-digest` is a ***work-in-progress*** project, intended to provide support
for some specifics boards (BSP files) and recipes to take advantage of those boards.


## What's inside?

This repository is composed of:

 * `.config.yaml`: a kas configuration file.
 * `meta-boards-nxp`: layer with the metadata for NXP boards.
 * `meta-tools`: layer with non-board related scripts to improve the hardware experience.

The `.config.yaml` is the configuration file for the kas utility, which
allows to easily download all the required third-party components in the
correct place and enable them in the configuration. In this project it downloads
and enables:

 * the `bitbake` build engine.
 * the `openembedded-core` repository which contains the `meta` layer with all the "core" recipes.
 * the `meta-openembedded` repository which is a collection of layers to suppliment OE-Core with additional packages.<br>
   Note that ***meta-oe*** and ***meta-python*** are enabled in `build/conf/bblayers.conf`.
 * the `meta-arm` repository which contains the `meta-arm` and `meta-arm-toolchain` layers.
 * the `meta-imx` repository which contains the `meta-bsp` and
   `meta-sdk` layers.
 * the `meta-boards-nxp` layer, not downloaded as it is already part of this
   repository, but enabled in `build/conf/bblayers.conf`.<br>
   Please, refer to ***meta-boards-nxp/README*** file to see more details about this layer and its goals.
 * the `meta-tools` layer, not downloaded as it is already part of this
   repository, but enabled in `build/conf/bblayers.conf`.<br>
   Please, refer to ***meta-tools/README*** file to see more details about this layer and its goals.

Using kas is not mandatory to use Yocto/OpenEmbedded, but i found it
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
. openembedded-core/oe-init-build-env

# Run your first build
bitbake nxp-custom-image

# Have dinner

# Find the output images here
ls -l tmp-glibc/deploy/images/imx8mn-lpddr4-evk/

# Flash the image (use your uSD card device instead of XYZ!):
sudo bmaptool copy tmp-glibc/deploy/images/imx8mn-lpddr4-evk/nxp-custom-image-imx8mn-lpddr4-evk.wic /dev/XYZ
```
