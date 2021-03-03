#!/bin/bash

RED=`tput setaf 1`
GREEN=`tput setaf 2`
YELLOW=`tput setaf 3`
BLUE=`tput setaf 4`
PURPLE=`tput setaf 5`
LIGHTBLUE=`tput setaf 6`
RESET=`tput sgr0`

PREFIX="New Icons"
LOCAL="/usr/share/icons/Papirus"

echo -e "Welcome to ${RED}P${RED}${RESET}${YELLOW}a${YELLOW}${RESET}${GREEN}p${GREEN}${RESET}${BLUE}i${BLUE}${RESET}${PURPLE}r${PURPLE}${RESET}${RED}u${RED}${RESET}${LIGHTBLUE}s${LIGHTBLUE}${RESET} Folder Icon installer!"
echo -e "${YELLOW}WARNING${YELLOW}${RESET}: For better usage, run this script using root user!\n"

if [ ! $USER = "root" ]
then
    if [ ! -d /home/$USER/.icons/Papirus ]
    then
        echo -e "${RED}Install first the papirus icon!${RED}${RESET}\n"
        exit 0
    else
        echo -e "Papirus icon already installed!\n"
        LOCAL = "/home/$USER/.icons/Papirus"
    fi
else
    if [ ! -d $LOCAL ]
    then
        echo -e "${RED}Install first the papirus icon!${RED}${RESET}\n"
        exit 0
    else
        if [ ! -f /usr/bin/papirus-folders ]
        then
            echo "Papirus-folders script not is installed!"
            cp papirus-folders /usr/bin/
            echo -e "Papirus-folders script installed!\n"
        else
            echo "Papirus-folders already installed!"
            echo "Appling patch in papirus-folders..."
            diff -Naur /usr/bin/papirus-folders papirus-folders > Patch.patch
            patch /usr/bin/papirus-folders < Patch.patch
            rm -f Patch.patch
            chmod 744 /usr/bin/papirus-folders
            echo -e "Patch applied!\n"
        fi
    fi
fi

echo "Instaling icons..."
cp $PREFIX/64x64/* $LOCAL/64x64/places
cp $PREFIX/48x48/* $LOCAL/48x48/places
cp $PREFIX/32x32/* $LOCAL/32x32/places
cp $PREFIX/24x24/* $LOCAL/24x24/places
cp $PREFIX/22x22/* $LOCAL/22x22/places
echo "Icons installed!"
