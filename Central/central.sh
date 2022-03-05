#!/bin/sh

./wait && java -jar /usr/app/app.jar -s $serverip -p $serverport -a $apiport -b $brokerip
