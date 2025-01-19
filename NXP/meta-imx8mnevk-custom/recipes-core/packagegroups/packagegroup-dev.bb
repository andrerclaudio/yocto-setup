DESCRIPTION = "General Development packagegroup."
SUMMARY = "Ordinary tools to develop on your image."

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} += " \
    \
    git \
    libgpiod \
    libgpiod-tools \
    libgpiod-dev \
    python3 \
    python3-pip \
    python3-gpiod \
    \
"

   