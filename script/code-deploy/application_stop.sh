#!/bin/bash

CURRENT_PID=$(pgrep -f log-collector)

echo "$CURRENT_PID"

if [ -z $CURRENT_PID ]; then
    echo "> no process is up or running"
else
    echo "> kill -9 $CURRENT_PID"
    kill -9 $CURRENT_PID
    sleep 5
fi