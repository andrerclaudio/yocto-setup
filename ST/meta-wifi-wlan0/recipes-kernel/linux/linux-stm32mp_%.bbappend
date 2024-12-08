FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://support-netdev.cfg file://support-netdev.scc file://0001-Enabling-Led-Blue-to-reflect-Wlan0-status.patch"

do_configure:append() {
        # Force the bitbake to append the .cfg file
        cat ../*.cfg >> ${B}/.config
}