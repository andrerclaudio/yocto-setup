SUMMARY = "Network configuration for mlan0 interface"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# Specify source URIs for configuration files
SRC_URI = "file://51-wireless.network \
           file://wpa_supplicant-nl80211-mlan0.conf"

# Specify runtime dependencies
RDEPENDS:${PN} += "add-service-wireless"
# Inherit features_check to check required features
inherit features_check
# Specify that the image requires systemd service manager
REQUIRED_DISTRO_FEATURES = "systemd"
# Enable automatic starting of the systemd service
SYSTEMD_AUTO_ENABLE = "enable"
# Append the systemd service for wpa_supplicant to start automatically
SYSTEMD_SERVICE:${PN}:append = " wpa_supplicant-nl80211@mlan0.service"

# Installation task
do_install:append () {
    # Install network configuration file for systemd
    install -d ${D}/usr/systemd/network/
    install -m 0644 ${WORKDIR}/51-wireless.network ${D}/usr/systemd/network/
    
    # Install wpa_supplicant configuration file
    install -d ${D}/etc/wpa_supplicant/
    install -D -m 600 ${WORKDIR}/wpa_supplicant-nl80211-mlan0.conf ${D}/etc/wpa_supplicant/

    # Create a symbolic link for systemd service
    install -d ${D}/etc/systemd/system/multi-user.target.wants/
    ln -s /usr/system/wpa_supplicant@.service ${D}/etc/systemd/system/multi-user.target.wants/wpa_supplicant-nl80211@mlan0.service
}

FILES:${PN} =  "/usr/systemd/network/51-wireless.network \
                /etc/wpa_supplicant/wpa_supplicant-nl80211-mlan0.conf \
                /etc/systemd/system/multi-user.target.wants/wpa_supplicant-nl80211@mlan0.service \
               "