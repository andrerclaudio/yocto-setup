DESCRIPTION = "Fastfetch is a neofetch-like tool for fetching system information and displaying them in a pretty way.\
                It is written mainly in C, with performance and customizability in mind. \
                "
SUMMARY =   "System Information. \
            This recipe is intended to take FASTFETCH tool - as is - to YOCTO projects.\
            "
HOMEPAGE = "https://github.com/fastfetch-cli/fastfetch"

# Specify the license for the package.
LICENSE = "MIT"

# Specify the source URI and its checksum for validation.
SRC_URI = "git://github.com/fastfetch-cli/fastfetch;protocol=https;branch=master"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# Versioning based on git commit information.
PV = "1.0+git${SRCPV}"
SRCREV = "e9bb761a2d83c9a2f7a969a5ea8fba2898e89318"
S = "${WORKDIR}/git"

# Inherit the cmake class for building the project.
inherit cmake

do_install () {
    # Executable
    install -d ${D}${bindir}
    cp --no-dereference --preserve=mode,links ${PN} ${D}${bindir}
    # Docs ...
    install -d ${D}${docdir}/${PN}
    cp --no-dereference --preserve=mode,links ${S}/CHANGELOG.md ${D}${docdir}/${PN}
    cp --no-dereference --preserve=mode,links ${S}/README.md ${D}${docdir}/${PN}
    # License ...
    install -d ${D}${datadir}/licenses/${PN}
    cp --no-dereference --preserve=mode,links ${S}/LICENSE ${D}${datadir}/licenses/${PN}
}

# Define and explicict the files to be included in the package.
FILES:${PN} += "${datadir}/*"
FILES:${PN} += "${docdir}/*"