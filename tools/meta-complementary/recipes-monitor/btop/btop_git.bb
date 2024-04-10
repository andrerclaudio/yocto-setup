DESCRIPTION = "Resource monitor that shows usage and stats for processor, memory, disks, network and processes"
SUMMARY =   "System Monitor \
            This recipe is intended to take BTOP resource monitor - as is - to YOCTO projects.\
            "
HOMEPAGE = "https://github.com/aristocratos/btop"

# Specify the license for the package.
LICENSE = "Apache-2.0"

# Specify the source URI and its checksum for validation.
SRC_URI = "git://github.com/aristocratos/btop.git;protocol=https;branch=main"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

# Versioning based on git commit information.
PV = "1.0+git${SRCPV}"
SRCREV = "fd2a2acdad6fbaad76846cb5e802cf2ae022d670"
S = "${WORKDIR}/git"

# Specify runtime dependencies
RDEPENDS:${PN} += "bash"

# Inherit the cmake class for building the project.
inherit cmake

do_install () {
    # Executable
    install -d ${D}${bindir}
    cp --no-dereference --preserve=mode,links ${PN} ${D}${bindir}
    # Icons ...
    install -d ${D}${datadir}/${PN}/icons
    install -d ${D}${datadir}/icons/hicolor/48x48/apps
    install -d ${D}${datadir}/icons/hicolor/scalable/apps
    cp -R --no-dereference --preserve=mode,links ${S}/Img/* ${D}${datadir}/${PN}/icons
    cp --no-dereference --preserve=mode,links ${S}/Img/icon.png ${D}${datadir}/icons/hicolor/48x48/apps/btop.png
    cp --no-dereference --preserve=mode,links ${S}/Img/icon.svg ${D}${datadir}/icons/hicolor/scalable/apps/btop.svg
    # Themes ...
    install -d ${D}${datadir}/${PN}/themes
    cp -R --no-dereference --preserve=mode,links ${S}/themes/* ${D}${datadir}/${PN}/themes
    # Docs ...
    install -d ${D}${docdir}/${PN}
    cp --no-dereference --preserve=mode,links ${S}/CHANGELOG.md ${D}${docdir}/${PN}
    cp --no-dereference --preserve=mode,links ${S}/README.md ${D}${docdir}/${PN}
    # License ...
    install -d ${D}${datadir}/licenses/${PN}
    cp --no-dereference --preserve=mode,links ${S}/LICENSE ${D}${datadir}/licenses/${PN}
    # Desktop ...
    install -d ${D}${datadir}/applications
    cp --no-dereference --preserve=mode,links ${S}/btop.desktop ${D}${datadir}/applications/
}

# Define and explicict the files to be included in the package.
FILES:${PN} += "${datadir}/*"
FILES:${PN} += "${docdir}/*"