SUMMARY = "OpenVPN tunnel for a given Token"
DESCRIPTION = "This OpenVPN tunnel creates a secure VPN connection to the specified target and forwards traffic through it."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# Specify source URIs for configuration files
SRC_URI =   "file://token.conf \
            "

# Inherit features_check to check required features
inherit features_check
# Specify that the image requires systemd service manager
REQUIRED_DISTRO_FEATURES = "systemd"
# Specify runtime dependencies
RDEPENDS:${PN} += "openvpn"
# Enable automatic starting of the systemd service
SYSTEMD_AUTO_ENABLE = "enable"
# Append the systemd service for openvpn-client to start automatically
SYSTEMD_SERVICE:${PN}:append = " openvpn-client@token.service"

# Installation task
do_install:append () {    
    # Install Client Token configuration file
    install -d ${D}/etc/openvpn/client/
    install -D -m 600 ${WORKDIR}/token.conf ${D}/etc/openvpn/client/

    # Create a symbolic link for systemd service
    install -d ${D}/etc/systemd/system/multi-user.target.wants/
    ln -s /lib/systemd/system/openvpn-client@.service ${D}/etc/systemd/system/multi-user.target.wants/openvpn-client@token.service 
}

FILES:${PN} =   "/etc/openvpn/client/token.conf \
                /etc/systemd/system/multi-user.target.wants/openvpn-client@token.service \
                "