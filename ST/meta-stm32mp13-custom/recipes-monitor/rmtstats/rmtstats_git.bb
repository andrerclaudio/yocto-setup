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
SRCREV = "ccaf7b772205988fb555b8298182d07cdeeee7a3"
S = "${WORKDIR}/git"

inherit features_check
REQUIRED_DISTRO_FEATURES = "wayland pam"

# Define runtime dependencies for this package.
RDEPENDS:${PN} = " \
                    gtk4 \
                    python3-core \
                    python3-modules \
                    python3-paramiko \
                    python3-pygobject \
                    python3-threading \
                    python3-psutil \
                "

# Skip the configure and compile tasks as they are not needed for this package.
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
    # Create necessary directories for installation.
    install -d ${D}${bindir}/rmtstats/
    install -d ${D}${datadir}/weston-start-at-startup/
    # Install the Python script and the shell script to their respective locations.
    install -m 0755 ${S}/rmtstats.py ${D}${bindir}/rmtstats/rmtstats
    install -m 0755 ${S}/commands.py ${D}${bindir}/rmtstats/
    install -m 0755 ${S}/widget.py ${D}${bindir}/rmtstats/
    install -m 0755 ${S}/core.py ${D}${bindir}/rmtstats/
    install -m 0755 ${WORKDIR}/launch-rmtstats-gtk.sh ${D}${datadir}/weston-start-at-startup/
}

# Specify additional files that should be included in the package.
FILES:${PN} +=  " \
                    ${datadir}/weston-start-at-startup \
                    ${bindir}/rmtstats/* \
                "
