DESCRIPTION = "A digital clock for the terminal, inspired by tty-clock and tock."
SUMMARY     = "The main difference with this version is that it is written in Python, \
                and my goal was primarily to learn as much as possible about the language \
                itself."
SECTION = "console/utils"

HOMEPAGE = "https://github.com/andrerclaudio/python3-ptock"
BUGTRACKER = "https://github.com/andrerclaudio/python3-ptock/issues"

# Specify the license for the package.
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI[md5sum] = "53ad6c28d2deffb2800241552090040f"
SRC_URI[sha256sum] = "189cb39f130bf4dc9f77baf054016b5547077bc9c183c24728367b15ba11d3e3"

# The correct module name is "python3-ptock", but it seems that the "-" well
# interpreted by the link or something.
PYPI_PACKAGE = "python3_ptock"

inherit pypi setuptools3

# Workaround to make setuptools work with TOML files.
do_configure:prepend() {
cat > ${S}/setup.py <<-EOF
from setuptools import setup

setup()
EOF
}

# Define runtime dependencies for this package.
RDEPENDS:${PN} = " \
                    ncurses \
                    python3-core \
                    python3-modules \
                    python3-threading \
                "
