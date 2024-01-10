# yocto-digest

`yocto-digest` is a ***work-in-progress*** project, intended to provide support
for some specifics boards (BSP) and recipes to take advantage of those boards.


## What's inside?

This repository is composed of:

 * `.config.yaml`: a kas configuration file.
 * `meta-nxp`: layer with the metadata for NXP boards.
 * `meta-tools`: layer with non-board related scripts to improve the hardware experience.

The `.config.yaml` is the configuration file for the kas utility, which
allows to easily download all the required third-party components in the
correct place and enable them in the configuration. In this project it downloads
and enables:

 * the `bitbake` build engine.
 * the `openembedded-core` repository which contains the `meta` layer with all the "core" recipes.
 * the `meta-openembedded` repository which is a collection of layers to suppliment OE-Core with additional packages.<br>
   Note that ***meta-oe*** and ***meta-python*** are enabled in `build/conf/bblayers.conf`.
 * the `meta-arm` repository which contains the `meta-arm` and
   `meta-arm-toolchain` layers.
 * the `meta-nxp` layer, not downloaded as it is already part of this
   repository, but enabled in `build/conf/bblayers.conf`.<br>
   Please, refer to ***meta-nxp/README*** file to see more details about this layer and its goal.
 * the `meta-tools` layer, not downloaded as it is already part of this
   repository, but enabled in `build/conf/bblayers.conf`.<br>
   Please, refer to ***meta-tools/README*** file to see more details about this layer and its goal.

Using kas is not mandatory to use Yocto/OpenEmbedded, but we found it
simple and convenient. You can use another tool for your project if so you
prefer.