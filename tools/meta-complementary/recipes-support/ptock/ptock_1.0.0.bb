DESCRIPTION = " "
SUMMARY =   " "
SECTION = "console/utils"

HOMEPAGE = "https://github.com/andrerclaudio/pTock"
BUGTRACKER = "https://github.com/andrerclaudio/pTock/issues"

# Specify the license for the package.
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# Specify the source URI and its checksum for validation.
SRC_URI = "git://github.com/andrerclaudio/pTock;protocol=https;branch=main"

SRCREV = "b793ed070ea495bf9ac290539ed65dff26b76ce2"
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
    install -d ${D}${bindir}
    install -m 0755 ${B}/ptock.py ${D}${bindir}/ptock
}
