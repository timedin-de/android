#!/bin/bash

#
# SPDX-FileCopyrightText: 2021 Ralf Kistner <ralf@embarkmobile.com>
# SPDX-License-Identifier: CC0-1.0
#

# Originally written by Ralf Kistner <ralf@embarkmobile.com>, but placed in the public domain

bootanim=""
failcounter=0
checkcounter=0

until [[ "$bootanim" =~ "stopped" ]]; do
   bootanim=`adb -e shell getprop init.svc.bootanim 2>&1`
   echo "($checkcounter) $bootanim"
   if [[ "$bootanim" =~ "not found" || "$bootanim" =~ "error" ]]; then
      let "failcounter += 1"
      if [[ $failcounter -gt 3 ]]; then
        echo "Failed to start emulator"
        exit 1
      fi
   fi
   let "checkcounter += 1"
   sleep 5
done
echo "($checkcounter) Done"
adb -e shell input keyevent 82
echo "($checkcounter) Unlocked emulator screen"
