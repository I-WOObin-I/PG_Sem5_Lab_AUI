FROM consul:latest

EXPOSE 8080
EXPOSE 8081
EXPOSE 8500

RUN apk update
RUN apk add openjdk17-jre-headless
RUN apk add apache2
RUN apk add apache2-ctl
RUN apk add mc
RUN apk add nginx

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
COPY startup.sh su.sh
#ENTRYPOINT ["sh", "/app/startup.sh", "&"]
#RUN java -jar /app/planetary-info-gateway.jar

#CMD ["sh"]
# sh /app/startup.sh

COPY app/planetary-info-ui /usr/share/nginx/html/
COPY nginx-server.conf /etc/nginx/conf.d/default.conf
COPY nginx-server.conf /etc/nginx/http.d/default.conf

RUN nginx -c /etc/nginx/nginx.conf
#RUN nginx -s reload


