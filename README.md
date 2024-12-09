# YOCTO-DIGEST

`yocto-digest` is a ***work-in-progress*** project, intended to provide support  
for some specifics boards (BSP files) and recipes to take advantage of those boards.

## Models that are supported by this project, so far:
* [NXP i.MX 8M Nano](https://www.nxp.com/design/design-center/development-boards-and-designs/i-mx-evaluation-and-development-boards/evaluation-kit-for-the-i-mx-8m-nano-applications-processor:8MNANOD4-EVK)

* [ST STM32MP135F](https://www.st.com/en/evaluation-tools/stm32mp135f-dk.html#st_all-features_sec-nav-tab)

* [Raspberry Pi Zero 2W](https://www.raspberrypi.com/products/raspberry-pi-zero-2-w/)

## How to flash the images to SDcard (all boards):

### ST board

Go to `scripts` folder (which is downloaded with all the other the image artifacts) and execute:

```bash
SDCARD_SIZE=1536 DEVICE=mmcblk0 ./create_sdcard_from_flashlayout.sh --force-rootfs ../flashlayout_st-custom-img-weston/optee/FlashLayout_sdcard_stm32mp135f-dk-optee.tsv
```
_Note:  
`SDCARD_SIZE` is the size you want to force the image generated. If it is less the necessary for your device, then an error will be raised in the process. ***This field is not mandatory***.  
`DEVICE` is where the SDcard you are using to write the image files.  
`st-custom-img-weston` is the name image and will follow your compilation.  

And then, follow the steps `./create_sdcard_from_flashlayout.sh` script will print for you on the screen.  

### NXP board

Go to `ìmage` folder where the images artifacts are located and execute:  

```bash
zstd -df nxp-custom-img-base-imx8mnevk.rootfs.wic.zst -o ./nxp-custom-img-base.wic  
sudo umount `lsblk --list | grep mmcblk0 | grep part | gawk '{ print $7 }' | tr '\n' ' '`
sudo dd if=nxp-custom-img-base.wic of=/dev/mmcblk0 bs=4k conv=fsync status=progress && sync
```  
_Note:  
`nxp-custom-img-base` is the name image and will follow your compilation.  
`mmcblk0` is where the SDcard you are using to write the image files.   
`nxp-custom-img-base.wic` is the output name from zstd command and must be ***.wic*** extension.  

### Raspberry Zero 2W

Go to `ìmage` folder where the images artifacts are located and execute:  

```bash
sudo umount `lsblk --list | grep mmcblk0 | grep part | gawk '{ print $7 }' | tr '\n' ' '`
bzcat rasp-custom-img-base-raspberrypi0-2w-64.rootfs.wic.bz2 | sudo dd of=/dev/mmcblk0 bs=8M conv=fdatasync status=progress  
```
_Note:  
`mmcblk0` is where the SDcard you are using to write the image files.   
`rasp-custom-img-base-raspberrypi0-2w-64.rootfs.wic.bz2` is the output name from zstd command and must be ***.wic.bz2*** extension.  
