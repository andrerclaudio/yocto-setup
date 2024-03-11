# This recipe is intended to take BTOP resource monitor - as is - to YOCTO projects.
#

DESCRIPTION = "Resource monitor that shows usage and stats for processor, memory, disks, network and processes."
HOMEPAGE = "https://github.com/aristocratos/btop"

LICENSE = "Apache-2.0"

SRC_URI = "git://github.com/aristocratos/btop.git;protocol=https;branch=main"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

PV = "1.0+git${SRCPV}"
SRCREV = "fd2a2acdad6fbaad76846cb5e802cf2ae022d670"
S = "${WORKDIR}/git"

DEPENDS = ""

inherit cmake