DESCRIPTION = "A digital clock for the terminal, made in Python, inspired by tty-clock."
SUMMARY =   "It displays the time in the terminal, with several options, check -h flag to see al the options."
SECTION = "console/utils"

HOMEPAGE = "https://github.com/andrerclaudio/pTock"
BUGTRACKER = "https://github.com/andrerclaudio/pTock/issues"

# Specify the license for the package.
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# Specify the source URI and its checksum for validation.
SRC_URI = "git://github.com/andrerclaudio/pTock;protocol=https;branch=main"

SRCREV = "8ef80989583ac9849cd0be36b7140a1a276db872"
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
