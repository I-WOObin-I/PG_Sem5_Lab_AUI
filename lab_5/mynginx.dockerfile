FROM nginx:latest

COPY nginx.conf /etc/nginx/nginx.conf
RUN apt-get update
RUN apt-get install mc -y

WORKDIR /etc/nginx/conf.d
RUN mv nginx.conf
CMD ["nginx", "-g", "daemon off;"]

#RUN nginx -t
#RUN nginx stop






