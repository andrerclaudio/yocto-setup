DESCRIPTION = "A digital clock for the terminal, inspired by tty-clock and tock."
SUMMARY     = "The main difference with this version is that it is written in Python, \
                and my goal was primarily to learn as much as possible about the language \
                itself."
SECTION = "console/utils"

HOMEPAGE = "https://github.com/andrerclaudio/pTock"
BUGTRACKER = "https://github.com/andrerclaudio/pTock/issues"

# Specify the license for the package.
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# Specify the source URI and its checksum for validation.
SRC_URI = "git://github.com/andrerclaudio/pTock;protocol=https;branch=main"

SRCREV = "d6cfa9e85cf0f41897e7c45051661ff8e9ffe806"
S = "${WORKDIR}/git"

# Define runtime dependencies for this package.
RDEPENDS:${PN} = " \
                    ncurses \
                    python3-core \
                    python3-modules \
                    python3-threading \
                "

# Skip the configure and compile tasks as they are not needed for this package.
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install(){
    install -d ${D}${bindir}/pTock
    # Copy files  to package's directory
    install -m 0755 ${B}/ptock ${D}${bindir}/pTock/ptock
    install -m 0755 ${B}/font.py ${D}${bindir}/pTock/font.py
    install -m 0755 ${B}/view.py ${D}${bindir}/pTock/view.py
    install -m 0755 ${B}/mechanism.py ${D}${bindir}/pTock/mechanism.py
}

# Specify additional files that should be included in the package.
FILES:${PN} +=  " \
                    ${bindir}/pTock/* \
                "
