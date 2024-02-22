DESCRIPTION = "\
                This script initializes and controls a TFT display using the ILI9341 driver on a iMX8mn board.\
                It loads and displays an image on the TFT display.\
            "
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = " \
                file://main.py \
                file://wave.jpg \                
                file://SpiDev_ILI9341/__init__.py \
                file://SpiDev_ILI9341/ILI9341.py \
            "

S = "${WORKDIR}"

RDEPENDS:${PN} = "python3 python3-spidev python3-numpy python3-pillow python3-gpiod python3-setuptools"

do_install () {
    # ${datadir} means /usr/share/
    install -d ${D}${datadir}/display
    install -d ${D}${datadir}/display/SpiDev_ILI9341/
    install -p -m 755 main.py ${D}${datadir}/display/
    install -p -m 644 wave.jpg ${D}${datadir}/display/
    install -p -m 644 SpiDev_ILI9341/__init__.py ${D}${datadir}/display/SpiDev_ILI9341/
    install -p -m 644 SpiDev_ILI9341/ILI9341.py ${D}${datadir}/display/SpiDev_ILI9341/
}

FILES:${PN} = "${datadir}/display/*"

# Prevents do_package failures with:
# debugsources.list: No such file or directory:
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"