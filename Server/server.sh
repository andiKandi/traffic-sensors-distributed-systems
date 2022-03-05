#!/bin/sh

./wait && java -jar /usr/app/app.jar -d $database -p $rpcPort
