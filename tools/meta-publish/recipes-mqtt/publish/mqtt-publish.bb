DESCRIPTION = "Add python script to image."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "file://mqtt-publish.py"

S = "${WORKDIR}"

RDEPENDS:${PN} = "python3 python3-paho-mqtt"

do_install () {
    install -d ${D}${bindir}
    # Adding and Renaming the file
    install -p -m 755 mqtt-publish.py ${D}${bindir}/mqtt-publish
}
