#!/bin/bash
java --version
cd /home/proxidize/app/
echo "Starting app length"
FNAME=$(ls -1 /home/proxidize/app/length.jar)

java -javaagent:/home/proxidize/jmx_prometheus_javaagent.jar=9091:/home/proxidize/config.yaml -jar \
-Dspring.profiles.active=$ENVIRONMENT $JAVAOPS $FNAME