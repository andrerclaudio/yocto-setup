DESCRIPTION = "Check the status of a remote server."
HOMEPAGE = "https://github.com/andrerclaudio/rmtstats"
SECTION = "console"
LICENSE = "MIT"

# Specify the source URI and its checksum for validation.
SRC_URI = "git://github.com/andrerclaudio/rmtstats;protocol=https;branch=main"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# Additional source file for the package.
SRC_URI += "file://launch-rmtstats-gtk.sh"

# Set version based on the Git commit information.
PV = "1.0+git${SRCPV}"
SRCREV = "e7c11f5ff2aca3e2181c2c9d806048fbef64e372"
S = "${WORKDIR}/git"

# Define runtime dependencies for this package.
RDEPENDS:${PN} = "python3-core python3-paramiko python3-pygobject gtk+3 python3-resource python3-threading"

# Skip the configure and compile tasks as they are not needed for this package.
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
    # Create necessary directories for installation.
    install -d ${D}${bindir}
    install -d ${D}${datadir}/weston-start-at-startup/

    # Install the Python script and the shell script to their respective locations.
    install -m 0755 ${S}/rmtstats.py ${D}${bindir}/rmtstats
    install -m 0755 ${WORKDIR}/launch-rmtstats-gtk.sh ${D}${datadir}/weston-start-at-startup/
}

# Specify additional files that should be included in the package.
FILES:${PN} += " ${datadir}/weston-start-at-startup"