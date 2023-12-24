# yocto-setup

`yocto-setup` is a ***work-in-progress*** project, intended to provide support
to some specifics boards (BSP) and recipes to take advantage of them.


## What's inside?

This repository is composed of:

 * `.config.yaml`: a kas configuration file
 * `meta-nxp`: the layer with the metadata for NXP boards

The `.config.yaml` is the configuration file for the kas utility, which
allows to easily download all the required third-party components in the
correct place and enable them in the configuration. In this project it downloads
and enables:

 * the `bitbake` build engine
 * the `openembedded-core` repository which contains the `meta` layer
   with all the "core" recipes
 * the `meta-arm` repository which contains the `meta-arm` and
   `meta-arm-toolchain` layers
 * the `meta-nxp` layer, not downloaded as it is already part of this
   repository, but enabled in `build/conf/bblayers.conf`


 ### The `meta-nxp` layer

`meta-nxp` is a layer that demonstrates how a realistic layer for a
product company can (and, in our opinion, should) look like.


