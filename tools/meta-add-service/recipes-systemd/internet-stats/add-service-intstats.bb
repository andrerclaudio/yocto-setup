DESCRIPTION = "Add a service to check on internet connection."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = " file://internet-check.service \
            file://internet-check.py \
            file://__init__.py \
        "

S = "${WORKDIR}"

RDEPENDS:${PN} = "python3 python3-setuptools python3-requests"
DEPENDS += "add-systemuser"

inherit systemd
inherit features_check

# ---------------- Systemd -------------------
SYSTEMD_AUTO_ENABLE${PN} = "enable"
SYSTEMD_SERVICE:${PN} = "internet-check.service"

do_install () {
	# ${datadir} means /usr/share/
    install -d -m 755 ${D}${datadir}/internet-check
    install -p -m 644 internet-check.py ${D}${datadir}/internet-check/
    install -p -m 644 __init__.py ${D}${datadir}/internet-check/
    chown -R systemuser:systemuser ${D}${datadir}/internet-check
    # Install the service file to the systemd folders
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/internet-check.service ${D}${systemd_system_unitdir}/
    chown systemuser:systemuser ${D}${systemd_system_unitdir}/internet-check.service
}

FILES:${PN} = "${datadir}/internet-check/*"
FILES:${PN} += "${systemd_system_unitdir}/"

# Prevents do_package failures with:
# debugsources.list: No such file or directory:
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
# Tell the image you want to use Systemd service manager
REQUIRED_DISTRO_FEATURES= " systemd"