SUMMARY = "Network configuration for WLAN0 interface"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# Specify source URIs for configuration files
SRC_URI = " \
                file://51-wireless.network \
                file://wpa_supplicant-nl80211-wlan0.conf \
            "

# Inherit features_check to check required features
inherit features_check
# Specify that the image requires systemd service manager
REQUIRED_DISTRO_FEATURES = "systemd wifi"
# Enable automatic starting of the systemd service
SYSTEMD_AUTO_ENABLE = "enable"
# Append the systemd service for wpa_supplicant to start automatically
SYSTEMD_SERVICE:${PN}:append = " wpa_supplicant-nl80211@wlan0.service"
# Specify runtime dependencies
RDEPENDS:${PN} += "linux-firmware-bcm43430"

# Installation task
do_install:append () {
    # Install network configuration file for systemd
    install -d ${D}${systemd_unitdir}/network/
    install -m 0644 ${WORKDIR}/51-wireless.network ${D}${systemd_unitdir}/network/
    
    # Install wpa_supplicant configuration file
    install -d ${D}${sysconfdir}/wpa_supplicant/
    install -m 600 ${WORKDIR}/wpa_supplicant-nl80211-wlan0.conf ${D}${sysconfdir}/wpa_supplicant/

    # Create a symbolic link for systemd service
    install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants/
    ln -s ${systemd_system_unitdir}/wpa_supplicant-nl80211@wlan0.service ${D}${sysconfdir}/systemd/system/multi-user.target.wants/wpa_supplicant-nl80211@wlan0.service
}

FILES:${PN} =  "\
                ${systemd_unitdir}/network/51-wireless.network \
                ${sysconfdir}/wpa_supplicant/wpa_supplicant-nl80211-wlan0.conf \
                ${sysconfdir}/systemd/system/multi-user.target.wants/wpa_supplicant-nl80211@wlan0.service \
                \
               "