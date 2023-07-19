#!/bin/bash

SEVER_PORT=8080

cd /home/ubuntu/build
nohup java -jar log-collector.jar 1>/dev/null 2>&1 &

CONTINUE=1
while [ ${CONTINUE} -eq 1 ]
do
    sleep 1
    PORT_STATUS=`netstat -an | grep LISTEN | grep ":${SERVER_PORT}" | wc -l`
    if [ ${PORT_STATUS} -eq 1 ]
    then
      CONTINUE=0
    fi
done