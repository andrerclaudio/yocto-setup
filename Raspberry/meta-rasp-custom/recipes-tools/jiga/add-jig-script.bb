SUMMARY = "Add a service to start the Jig script related."
VERSION = "1.0"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# Specify source URIs for configuration files
SRC_URI = " file://jig-script.service \
            file://script/ \
        "

# Inherit features_check to check required features
inherit features_check
# Specify that the image requires systemd service manager
REQUIRED_DISTRO_FEATURES = "systemd"
# Enable automatic starting of the systemd service
SYSTEMD_AUTO_ENABLE = "enable"
# Append the systemd service for jig-script to start automatically
SYSTEMD_SERVICE:${PN}:append = " jig-script.service"
# Script dependencies
RDEPENDS:${PN}    = "\
                        python3 \
                        python3-modules \
                        python3-threading \
                        rpi-gpio \
                        python3-pyserial \
                        python3-evdev \
                        python3-requests \
                    "

# Installation task
do_install:append () {    
    # Install script files 
    install -d -m 755 ${D}${datadir}/jig-script        
    install -p -m 644 ${WORKDIR}/script/* ${D}${datadir}/jig-script/
    chown -R root:root ${D}${datadir}/jig-script

    # Install the service file to the systemd folders
    install -d ${D}${systemd_system_unitdir}
    install -p -m 0644 ${WORKDIR}/jig-script.service ${D}${systemd_system_unitdir}/jig-script.service

    # Create a symbolic link for systemd service
    install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants/
    ln -s ${systemd_system_unitdir}/jig-script.service ${D}${sysconfdir}/systemd/system/multi-user.target.wants/jig-script.service

    #
    # Disable serial service to avoid conflict with Serial port and the script We are going to run
    #
    ln -sf /dev/null ${D}${systemd_system_unitdir}/serial-getty@ttyS0.service
}

FILES:${PN} =  "\
                ${datadir}/jig-script/* \
                ${systemd_system_unitdir}/jig-script.service \
                ${sysconfdir}/systemd/system/multi-user.target.wants/jig-script.service \
                ${systemd_system_unitdir}/serial-getty@ttyS0.service \
                \
               "

# Prevents do_package failures with:
# debugsources.list: No such file or directory:
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"