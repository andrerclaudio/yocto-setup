SUMMARY = "Example recipe for using inherit useradd"
DESCRIPTION = "This recipe serves as an example for using features from useradd.bbclass"
SECTION = "examples"
PR = "r1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# Add files to user folder
SRC_URI = "file://file1 \
           file://file2"

S = "${WORKDIR}"

inherit useradd

USERADD_PACKAGES = "${PN}"
# This creates a non-root user that cannot be logged in as
USERADD_PARAM:${PN} = "--system -s /sbin/nologin -g systemuser systemuser"
GROUPADD_PARAM:${PN} = "--system systemuser"

do_install () {
	# ${datadir} means /usr/share/
    install -d -m 644 ${D}${datadir}/systemuser
    install -p -m 644 file1 ${D}${datadir}/systemuser/
    install -p -m 644 file2 ${D}${datadir}/systemuser/
    
    chown -R systemuser ${D}${datadir}/systemuser
    chgrp -R systemuser ${D}${datadir}/systemuser
}

FILES:${PN} = "${datadir}/systemuser/*"

# Prevents do_package failures with:
# debugsources.list: No such file or directory:
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"