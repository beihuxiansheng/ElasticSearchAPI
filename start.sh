#!/bin/bash
rm -f tpid
nohup /data/jdk1.8.0_112/bin/java -XX:-UseGCOverheadLimit -Xms4g -Xmx4g -Xmn1536m -server -jar ElasticSearchAPI.jar --spring.profiles.active=pro > /dev/null 2>&1 &
echo $! > tpid
echo Start Success!