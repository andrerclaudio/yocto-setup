DESCRIPTION = "A digital clock for the terminal."
SUMMARY = ""
SECTION = "console/utils"

HOMEPAGE = "https://github.com/andrerclaudio/tock"

# Specify the license for the package.
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"


# Inherit the cargo class for building the project.
inherit cargo

do_compile[network] = "1"

# Add release flag
# CARGO_BUILD_FLAGS += "--release"

# Specify the source URI and its checksum for validation.
SRC_URI = "git://github.com/andrerclaudio/tock;protocol=https;branch=master"
# Specify the revision for the source code to download.
SRCREV = "7d92d621559c5ac45f536ad987f6cd905ccc010b"
S = "${WORKDIR}/git"

SRC_URI +=  " \
            crate://crates.io/chrono/0.4.31 \
            crate://crates.io/libc/0.2.149 \
            crate://crates.io/clap/4.4.6 \
            crate://crates.io/num-traits/0.2.17 \
            crate://crates.io/android-tzdata/0.1.1 \
            crate://crates.io/iana-time-zone/0.1.57 \
            crate://crates.io/windows-targets/0.48.5 \
            crate://crates.io/clap_builder/4.4.6 \
            crate://crates.io/clap_derive/4.4.2 \
            crate://crates.io/autocfg/1.1.0 \
            crate://crates.io/core-foundation-sys/0.8.4 \
            crate://crates.io/js-sys/0.3.64 \
            crate://crates.io/wasm-bindgen/0.2.87 \
            crate://crates.io/android_system_properties/0.1.5 \
            crate://crates.io/iana-time-zone-haiku/0.1.2 \
            crate://crates.io/windows/0.48.0 \
            crate://crates.io/windows_aarch64_gnullvm/0.48.5 \
            crate://crates.io/windows_aarch64_msvc/0.48.5 \
            crate://crates.io/windows_i686_gnu/0.48.5 \
            crate://crates.io/windows_i686_msvc/0.48.5 \
            crate://crates.io/windows_x86_64_gnu/0.48.5 \
            crate://crates.io/windows_x86_64_msvc/0.48.5 \
            crate://crates.io/windows_x86_64_gnullvm/0.48.5 \
            crate://crates.io/clap_builder/4.4.6 \
            crate://crates.io/anstream/0.6.4 \
            crate://crates.io/anstyle/1.0.4 \
            crate://crates.io/clap_lex/0.5.1 \
            crate://crates.io/heck/0.4.1 \
            crate://crates.io/proc-macro2/1.0.69 \
            crate://crates.io/quote/1.0.33 \
            crate://crates.io/syn/2.0.38 \
            crate://crates.io/cfg-if/1.0.0 \
            crate://crates.io/wasm-bindgen-macro/0.2.87 \
            crate://crates.io/cc/1.0.83 \
            crate://crates.io/anstyle-parse/0.2.2 \
            crate://crates.io/anstyle-query/1.0.0 \
            crate://crates.io/anstyle-wincon/3.0.1 \
            crate://crates.io/colorchoice/1.0.0 \
            crate://crates.io/utf8parse/0.2.1 \
            crate://crates.io/unicode-ident/1.0.12 \
            crate://crates.io/wasm-bindgen-macro-support/0.2.87 \
            crate://crates.io/windows-sys/0.48.0 \
            crate://crates.io/wasm-bindgen-backend/0.2.87 \
            crate://crates.io/wasm-bindgen-shared/0.2.87 \
            crate://crates.io/bumpalo/3.14.0 \
            crate://crates.io/log/0.4.20 \
            crate://crates.io/once_cell/1.18.0 \
            crate://crates.io/panic-abort/0.3.2 \
            "

# Specify the SHA sum to verify the downloaded source code.
SRC_URI[chrono-0.4.31.sha256sum] = "7f2c685bad3eb3d45a01354cedb7d5faa66194d1d58ba6e267a8de788f79db38"
SRC_URI[libc-0.2.149.sha256sum] = "a08173bc88b7955d1b3145aa561539096c421ac8debde8cbc3612ec635fee29b"
SRC_URI[clap-4.4.6.sha256sum] = "d04704f56c2cde07f43e8e2c154b43f216dc5c92fc98ada720177362f953b956"
SRC_URI[num-traits-0.2.17.sha256sum] = "39e3200413f237f41ab11ad6d161bc7239c84dcb631773ccd7de3dfe4b5c267c"
SRC_URI[android-tzdata-0.1.1.sha256sum] = "e999941b234f3131b00bc13c22d06e8c5ff726d1b6318ac7eb276997bbb4fef0"
SRC_URI[iana-time-zone-0.1.57.sha256sum] = "2fad5b825842d2b38bd206f3e81d6957625fd7f0a361e345c30e01a0ae2dd613"
SRC_URI[windows-targets-0.48.5.sha256sum] = "9a2fa6e2155d7247be68c096456083145c183cbbbc2764150dda45a87197940c"
SRC_URI[clap_builder-4.4.6.sha256sum] = "0e231faeaca65ebd1ea3c737966bf858971cd38c3849107aa3ea7de90a804e45"
SRC_URI[clap_derive-4.4.2.sha256sum] = "0862016ff20d69b84ef8247369fabf5c008a7417002411897d40ee1f4532b873"
SRC_URI[autocfg-1.1.0.sha256sum] = "d468802bab17cbc0cc575e9b053f41e72aa36bfa6b7f55e3529ffa43161b97fa"
SRC_URI[core-foundation-sys-0.8.4.sha256sum] = "e496a50fda8aacccc86d7529e2c1e0892dbd0f898a6b5645b5561b89c3210efa"
SRC_URI[js-sys-0.3.64.sha256sum] = "c5f195fe497f702db0f318b07fdd68edb16955aed830df8363d837542f8f935a"
SRC_URI[wasm-bindgen-0.2.87.sha256sum] = "7706a72ab36d8cb1f80ffbf0e071533974a60d0a308d01a5d0375bf60499a342"
SRC_URI[android_system_properties-0.1.5.sha256sum] = "819e7219dbd41043ac279b19830f2efc897156490d7fd6ea916720117ee66311"
SRC_URI[iana-time-zone-haiku-0.1.2.sha256sum] = "f31827a206f56af32e590ba56d5d2d085f558508192593743f16b2306495269f"
SRC_URI[windows-0.48.0.sha256sum] = "e686886bc078bc1b0b600cac0147aadb815089b6e4da64016cbd754b6342700f"
SRC_URI[windows_aarch64_gnullvm-0.48.5.sha256sum] = "2b38e32f0abccf9987a4e3079dfb67dcd799fb61361e53e2882c3cbaf0d905d8"
SRC_URI[windows_aarch64_msvc-0.48.5.sha256sum] = "dc35310971f3b2dbbf3f0690a219f40e2d9afcf64f9ab7cc1be722937c26b4bc"
SRC_URI[windows_i686_gnu-0.48.5.sha256sum] = "a75915e7def60c94dcef72200b9a8e58e5091744960da64ec734a6c6e9b3743e"
SRC_URI[windows_i686_msvc-0.48.5.sha256sum] = "8f55c233f70c4b27f66c523580f78f1004e8b5a8b659e05a4eb49d4166cca406"
SRC_URI[windows_x86_64_gnu-0.48.5.sha256sum] = "53d40abd2583d23e4718fddf1ebec84dbff8381c07cae67ff7768bbf19c6718e"
SRC_URI[windows_x86_64_msvc-0.48.5.sha256sum] = "ed94fce61571a4006852b7389a063ab983c02eb1bb37b47f8272ce92d06d9538"
SRC_URI[windows_x86_64_gnullvm-0.48.5.sha256sum] = "0b7b52767868a23d5bab768e390dc5f5c55825b6d30b86c844ff2dc7414044cc"
SRC_URI[anstream-0.6.4.sha256sum] = "2ab91ebe16eb252986481c5b62f6098f3b698a45e34b5b98200cf20dd2484a44"
SRC_URI[anstyle-1.0.4.sha256sum] = "7079075b41f533b8c61d2a4d073c4676e1f8b249ff94a393b0595db304e0dd87"
SRC_URI[clap_lex-0.5.1.sha256sum] = "cd7cc57abe963c6d3b9d8be5b06ba7c8957a930305ca90304f24ef040aa6f961"
SRC_URI[heck-0.4.1.sha256sum] = "95505c38b4572b2d910cecb0281560f54b440a19336cbbcb27bf6ce6adc6f5a8"
SRC_URI[proc-macro2-1.0.69.sha256sum] = "134c189feb4956b20f6f547d2cf727d4c0fe06722b20a0eec87ed445a97f92da"
SRC_URI[quote-1.0.33.sha256sum] = "5267fca4496028628a95160fc423a33e8b2e6af8a5302579e322e4b520293cae"
SRC_URI[syn-2.0.38.sha256sum] = "e96b79aaa137db8f61e26363a0c9b47d8b4ec75da28b7d1d614c2303e232408b"
SRC_URI[cfg-if-1.0.0.sha256sum] = "baf1de4339761588bc0619e3cbc0120ee582ebb74b53b4efbf79117bd2da40fd"
SRC_URI[wasm-bindgen-macro-0.2.87.sha256sum] = "dee495e55982a3bd48105a7b947fd2a9b4a8ae3010041b9e0faab3f9cd028f1d"
SRC_URI[cc-1.0.83.sha256sum] = "f1174fb0b6ec23863f8b971027804a42614e347eafb0a95bf0b12cdae21fc4d0"
SRC_URI[anstyle-parse-0.2.2.sha256sum] = "317b9a89c1868f5ea6ff1d9539a69f45dffc21ce321ac1fd1160dfa48c8e2140"
SRC_URI[anstyle-query-1.0.0.sha256sum] = "5ca11d4be1bab0c8bc8734a9aa7bf4ee8316d462a08c6ac5052f888fef5b494b"
SRC_URI[anstyle-wincon-3.0.1.sha256sum] = "f0699d10d2f4d628a98ee7b57b289abbc98ff3bad977cb3152709d4bf2330628"
SRC_URI[colorchoice-1.0.0.sha256sum] = "acbf1af155f9b9ef647e42cdc158db4b64a1b61f743629225fde6f3e0be2a7c7"
SRC_URI[utf8parse-0.2.1.sha256sum] = "711b9620af191e0cdc7468a8d14e709c3dcdb115b36f838e601583af800a370a"
SRC_URI[unicode-ident-1.0.12.sha256sum] = "3354b9ac3fae1ff6755cb6db53683adb661634f67557942dea4facebec0fee4b"
SRC_URI[wasm-bindgen-macro-support-0.2.87.sha256sum] = "54681b18a46765f095758388f2d0cf16eb8d4169b639ab575a8f5693af210c7b"
SRC_URI[windows-sys-0.48.0.sha256sum] = "677d2418bec65e3338edb076e806bc1ec15693c5d0104683f2efe857f61056a9"
SRC_URI[wasm-bindgen-backend-0.2.87.sha256sum] = "5ef2b6d3c510e9625e5fe6f509ab07d66a760f0885d858736483c32ed7809abd"
SRC_URI[wasm-bindgen-shared-0.2.87.sha256sum] = "ca6ad05a4870b2bf5fe995117d3728437bd27d7cd5f06f13c17443ef369775a1"
SRC_URI[bumpalo-3.14.0.sha256sum] = "7f30e7476521f6f8af1a1c4c0b8cc94f0bee37d91763d0ca2665f299b6cd8aec"
SRC_URI[log-0.4.20.sha256sum] = "b5e6163cb8c49088c2c36f57875e58ccd8c87c7427f7fbd50ea6710b2f3f2e8f"
SRC_URI[once_cell-1.18.0.sha256sum] = "dd8b5dd2ae5ed71462c540258bedcb51965123ad7e7ccf4b9a8cafaa4a63576d"
SRC_URI[panic-abort-0.3.2.sha256sum] = "4e20e6499bbbc412f280b04a42346b356c6fa0753d5fd22b7bd752ff34c778ee"

CARGO_SRC_DIR = ""

do_install(){
    install -d ${D}${bindir}
    install -m 0755 ${B}/tock ${D}${bindir}
}
