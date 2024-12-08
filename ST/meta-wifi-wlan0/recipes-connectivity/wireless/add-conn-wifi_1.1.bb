SUMMARY = "Network configuration for WLAN0 interface"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# Specify source URIs for configuration files
SRC_URI = " \
                file://51-wireless.network \
                file://set-wifi-statusled.sh \
                file://set-wifi-statusled.service \
                file://wpa_supplicant-nl80211-wlan0.conf \
            "

# Inherit features_check to check required features
inherit features_check
# Specify that the image requires systemd service manager
REQUIRED_DISTRO_FEATURES = "systemd wifi"
# Enable automatic starting of the systemd service
SYSTEMD_AUTO_ENABLE = "enable"
# Append the systemd service for wpa_supplicant to start automatically
SYSTEMD_SERVICE:${PN}:append = " wpa_supplicant-nl80211@wlan0.service set-wifi-statusled.service"

# Installation task
do_install:append () {
    # Install network configuration file for systemd
    install -d ${D}${systemd_unitdir}/network/
    install -m 0644 ${WORKDIR}/51-wireless.network ${D}${systemd_unitdir}/network/
    
    # Install wpa_supplicant configuration file
    install -d ${D}${sysconfdir}/wpa_supplicant/
    install -m 600 ${WORKDIR}/wpa_supplicant-nl80211-wlan0.conf ${D}${sysconfdir}/wpa_supplicant/

    # Install Status Led to reflect NetDev
    install -d ${D}${systemd_system_unitdir}/
    install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants/
    install -m 0644 ${WORKDIR}/set-wifi-statusled.service ${D}${systemd_system_unitdir}/
    ln -s ${systemd_system_unitdir}/set-wifi-statusled.service ${D}${sysconfdir}/systemd/system/multi-user.target.wants/set-wifi-statusled.service
    install -d ${D}${sbindir}/
    install -m 0755 ${WORKDIR}/set-wifi-statusled.sh ${D}${sbindir}/

    # Create a symbolic link for systemd service
    install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants/
    ln -s ${systemd_system_unitdir}/wpa_supplicant-nl80211@wlan0.service ${D}${sysconfdir}/systemd/system/multi-user.target.wants/wpa_supplicant-nl80211@wlan0.service
}

FILES:${PN} =  "\
                ${systemd_unitdir}/network/51-wireless.network \
                ${sysconfdir}/wpa_supplicant/wpa_supplicant-nl80211-wlan0.conf \
                ${sbindir}/set-wifi-statusled.sh \
                ${systemd_system_unitdir}/set-wifi-statusled.service \
                ${sysconfdir}/systemd/system/multi-user.target.wants/* \
                \
               "