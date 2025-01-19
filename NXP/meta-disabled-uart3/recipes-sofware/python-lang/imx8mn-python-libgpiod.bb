DESCRIPTION = "Play with GPIO pins."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "file://imx8mn-libgpiod.py"

DEPENDS += "python3-gpiod"
RDEPENDS:${PN} += "python3 python3-gpiod"

do_install(){
  install -d -m 755 ${D}/${datadir}/scripts
  install -p -m 644 ${WORKDIR}/imx8mn-libgpiod.py ${D}/${datadir}/scripts/imx8mn-libgpiod.py
  chown -R root:root ${D}${datadir}/scripts
}

FILES:${PN} += "${datadir}/scripts/"
