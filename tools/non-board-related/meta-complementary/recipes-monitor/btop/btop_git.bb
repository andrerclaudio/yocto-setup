# This recipe is intended to take BTOP resource monitor - as is - to YOCTO projects.
*

DESCRIPTION = "Resource monitor that shows usage and stats for processor, memory, disks, network and processes."
HOMEPAGE = "https://github.com/aristocratos/btop"

LICENSE = "Apache-2.0"

SRC_URI = "git://github.com/aristocratos/btop.git;protocol=https;branch=master"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=e3fc50a88d0a364313df4b21ef20c29e"

PV = "1.0+git${SRCPV}"
SRCREV = "fd2a2acdad6fbaad76846cb5e802cf2ae022d670"
S = "${WORKDIR}/git"

DEPENDS = "ncurses"

# Work around the broken Makefile which hard-codes the CC variable
EXTRA_OEMAKE = "-e"

# Work around QA error due to the over-simplistic Makefile:
#   [...] QA Issue: File /usr/bin/sl in package sl doesn't have GNU_HASH (didn't pass LDFLAGS?) [ldflags]
INSANE_SKIP:${PN} += "ldflags"

# Using meson, CMake or other build systems makes life easier also for
# recipe writers and can avoid the need of do_install entirely. This does
# not happen for sl, unfortunately.
do_install () {
	install -d ${D}${bindir}
	install -m 755 sl ${D}${bindir}/
}
