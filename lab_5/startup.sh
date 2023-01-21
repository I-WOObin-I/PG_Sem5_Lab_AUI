#!/bin/bash
java -jar /app/planetary-info-planet.jar > /app/planetlog.log 2>&1 &
sleep 15
java -jar /app/planetary-info-star-system.jar > /app/starsystemlog.log 2>&1 &
sleep 15
java -jar /app/planetary-info-gateway.jar > /app/gatewaylog.log 2>&1 &