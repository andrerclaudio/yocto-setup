DESCRIPTION = "A GTK window to dynamically display the output of a terminal command."
SECTION = "gnome"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# Additional source file for the package.
SRC_URI +=  "\
                file://launch-gtk-sandbox.sh \
                file://script/gtk-sandbox.py \
            "

inherit features_check
REQUIRED_DISTRO_FEATURES = "wayland pam"

# Define runtime dependencies for this package.
RDEPENDS:${PN} = " \
                    gtk4 \
                    python3-core \
                    python3-modules \
                    python3-pygobject \
                "

# Skip the configure and compile tasks as they are not needed for this package.
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
    # Create necessary directories for installation.
    install -d ${D}${bindir}/gtk-sandbox/
    install -d ${D}${datadir}/weston-start-at-startup/
    # Install the Python script and the shell script to their respective locations.
    install -m 0755 ${WORKDIR}/script/gtk-sandbox.py ${D}${bindir}/gtk-sandbox/gtk-sandbox
    install -m 0755 ${WORKDIR}/launch-gtk-sandbox.sh ${D}${datadir}/weston-start-at-startup/
}

# Specify additional files that should be included in the package.
FILES:${PN} +=  " \
                    ${bindir}/gtk-sandbox/* \
                    ${datadir}/weston-start-at-startup \                    
                "
