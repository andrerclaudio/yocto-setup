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
 * the `meta-arm` repository which contains the `meta-arm` and `meta-arm-toolchain` layers.
 * the `meta-imx` repository which contains the `meta-bsp` and
   `meta-sdk` layers.
 * the `meta-nxp` layer, not downloaded as it is already part of this
   repository, but enabled in `build/conf/bblayers.conf`.<br>
   Please, refer to ***meta-nxp/README*** file to see more details about this layer and its goal.
 * the `meta-tools` layer, not downloaded as it is already part of this
   repository, but enabled in `build/conf/bblayers.conf`.<br>
   Please, refer to ***meta-tools/README*** file to see more details about this layer and its goal.

Using kas is not mandatory to use Yocto/OpenEmbedded, but we found it
simple and convenient. You can use another tool for your project if so you
prefer.

## The `meta-kiss` layer

`meta-kiss` is a layer that demonstrates how a realistic layer for a
product company can (and, in our opinion, should) look like.

It is named after the KISS principle which "states that most systems work
best if they are kept simple rather than made complicated" (source:
[Wikipedia](https://en.wikipedia.org/wiki/KISS_principle)).

Here we used "kiss" as the hypothetical name of a fictitious company. The
machine configurations in meta-kiss implement fictitious products, but
except for their name they are actual development boards and the output
images can be used on these boards. In real world use cases a layer
implementing company products can reasonably be called
`meta-<company-name>` and the machine names should be named after the
product names.

The `meta-kiss` layer provides:

 * support for two machines
 * a distro configuration
 * a few recipes, including kernel, U-Boot, a userspace application and an
   image recipe

Note that `meta-kiss` is a single layer. The `bitbake` Yocto/OE build
engine is powerful enough to handle lots of machines, recipes and even
multiple distros in a single layer. Thus using a simple layer in your
company is perfectly fine and encouraged, unless your need are so complex
that splitting it into multiple layers proves useful.

## Machines

The meta-kiss layer contains two machine configurations, called
**dogbonedark** and **stompduck**.

The **dogbonedark** machine describes a fictitious product which in reality
implements the [BeagleBone®
Black](https://www.beagleboard.org/boards/beaglebone-black). In order to
implement it we took the relevant content from [the BeagleBone machine
configuration](https://git.yoctoproject.org/meta-ti/tree/meta-ti-bsp/conf/machine/beaglebone.conf)
found in the meta-ti-bsp layer.

We could of course have used the meta-ti-bsp layer directly, however since
the hardware is very well supported by the mainline kernel and U-Boot we
only needed to write (or copy and paste!) only a small amount of code.

Several BSP layers provided by hardware vendors bring in extra complexity,
deviation from standard coding practices and even bugs and unnecessarily
complex code. In the spirit of this project, we chose to provide an example
of how you can do without them in many cases.

The **stompduck** machine describes a fictitious product which in reality
implements the
[STM32MP157A-DK1](https://www.st.com/en/evaluation-tools/stm32mp157a-dk1.html). For
the same motivations, the minimum necessary code in this case was taken
from [the STM32MP1 machine
configuration](https://github.com/STMicroelectronics/meta-st-stm32mp/blob/mickledore/conf/machine/stm32mp1.conf)
found in the meta-st-stm32mp layer.

In addition to the steps needed to implement the `dogbonedark`, for the
`stompduck` machine we additionally chose to boot it using
[TrustedFirmware-A (TF-A)](https://www.trustedfirmware.org/projects/tf-a/).
In order to build TF-A, using [the existing
recipe](https://git.yoctoproject.org/meta-arm/tree/meta-arm/recipes-bsp/trusted-firmware-a)
from the meta-arm layer looked like a good choice given the balance between
the code quality of the meta-arm layer itself and the complexity required
for a recipe to build TF-A. So we added this layer to the kas configuration
file together with the meta-arm-toolchain layer it depends on.