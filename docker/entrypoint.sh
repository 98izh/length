#!/bin/bash
java --version
cd /home/proxidize/app/
echo "Starting app length"
FNAME=$(ls -1 /home/aya/app/length.jar)

java -javaagent:/home/aya/jmx_prometheus_javaagent.jar=9090:/home/aya/config.yaml -jar \
-Dspring.profiles.active=$ENVIRONMENT $JAVAOPS $FNAME