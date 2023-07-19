#!/bin/bash

cd /home/ubuntu/build
nohup java -jar log-collector.jar 1>/dev/null 2>&1 &