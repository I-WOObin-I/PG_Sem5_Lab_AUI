FROM consul:latest

RUN apk update
RUN apk add openjdk17-jre-headless
RUN apk add apache2
RUN apk add apache2-ctl
RUN apk add mc

COPY index.html /var/www/localhost/htdocs
COPY httpd.conf /etc/apache2/httpd.conf
COPY ports.conf /etc/apache2/ports.conf

RUN /usr/sbin/apachectl -k graceful
RUN cd /etc/apache2
#WORKDIR /etc/apache2/sites-enabled
#COPY 000-default.conf /etc/apache2/sites-enabled/000-default.conf

COPY app/planetary-info-planet.jar /app/planetary-info-planet.jar
COPY app/planetary-info-star-system.jar /app/planetary-info-star-system.jar
COPY app/planetary-info-gateway.jar /app/planetary-info-gateway.jar
COPY startup.sh app/startup.sh
#CMD ["/bin/bash","-c","/app/startup.sh"]
#RUN java -jar /app/planetary-info-gateway.jar

#CMD ["sh"]






