# yocto-digest

`yocto-digest` is a ***work-in-progress*** project, intended to provide support
for some specifics boards (BSP) and recipes to take advantage of them.


## What's inside?

This repository is composed of:

 * `.config.yaml`: a kas configuration file.
 * `meta-nxp`: layer with the metadata for NXP boards.
 * `meta-tools`: layer with non-board related sripts to improve the hardware experience.

The `.config.yaml` is the configuration file for the kas utility, which
allows to easily download all the required third-party components in the
correct place and enable them in the configuration. In this project it downloads
and enables:

 * the `bitbake` build engine.
 * the `openembedded-core` repository which contains the `meta` layer
   with all the "core" recipes.
 * the `meta-arm` repository which contains the `meta-arm` and
   `meta-arm-toolchain` layers.
 * the `meta-nxp` layer, not downloaded as it is already part of this
   repository, but enabled in `build/conf/bblayers.conf`.
 * the `meta-tools` layer, not downloaded as it is already part of this
   repository, but enabled in `build/conf/bblayers.conf`.


 ### The `meta-nxp` layer

`meta-nxp` is a layer that ...

 ### The `meta-tools` layer

`meta-tools` is a layer that ...
