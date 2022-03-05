#!/bin/sh

java -jar /usr/app/app.jar -b $brokerIp -p $port -t $sensortype -i $sendInterval
