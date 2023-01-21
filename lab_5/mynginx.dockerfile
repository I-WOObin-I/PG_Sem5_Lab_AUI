FROM nginx:latest

COPY default.conf /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/nginx.conf
RUN apt-get update
RUN apt-get install mc -y
RUN nginx -t
#RUN nginx stop






