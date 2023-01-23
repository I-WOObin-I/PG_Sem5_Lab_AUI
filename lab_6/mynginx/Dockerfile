FROM nginx:1.23-alpine


#RUN apk update
#RUN apk add mc

WORKDIR /etc/nginx/conf.d
COPY nginx.conf /etc/nginx/conf.d/
RUN mv nginx.conf default.conf
CMD ["nginx", "-g", "daemon off;"]

#RUN nginx -t
#RUN nginx stop