#!/bin/bash
java -jar /app/planetary-info-planet.jar > planetlog.log 2>&1 &
echo planets api starting
sleep 5
java -jar /app/planetary-info-star-system.jar > starlog.log 2>&1 &
echo star systems api starting
sleep 13
java -jar /app/planetary-info-gateway.jar > gatewaylog.log 2>&1 &
echo gateway api starting