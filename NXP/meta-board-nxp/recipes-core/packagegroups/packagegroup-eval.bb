DESCRIPTION = "General Evaluation packagegroup."
SUMMARY = "Ordinary tools to test your image."

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} += " \
    \
    nano \
    iperf3 \
    openssh \
    util-linux \
    procps \
    btop \
    locale-setter \
    \
"
