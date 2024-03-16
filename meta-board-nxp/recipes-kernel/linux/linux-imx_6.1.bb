# Simple recipe for using a mainline Linux kernel

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

inherit kernel

# As we are using a fully mainline kernel there is no need to clone an
# entire git repository, which would take time and disk space. Downloading
# a tarball is enough. In real cases you might have an internal kernel
# repository with your branches, so it can make sense to fetch from
# there. Or you could be using a mainline kernel with only a few patches on
# top, and in that case downloading a tarball and applying your patches can
# be a good solution.
SRC_URI = "${KERNELORG_MIRROR}/linux/kernel/v6.x/linux-${PV}.tar.xz"
SRC_URI[sha256sum] = "2ca1f17051a430f6fed1196e4952717507171acfd97d96577212502703b25deb"
S = "${WORKDIR}/linux-${PV}"

# For most realistic a customized defconfig is preferred to the default
# architecture defconfig, to enable all the needed features and drivers
# while trimming the kernel and modules size disabling unneeded ones.
SRC_URI += "file://defconfig"