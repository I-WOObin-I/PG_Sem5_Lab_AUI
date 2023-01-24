FROM nginx:1.23-alpine

COPY nginx.conf /etc/nginx/nginx.conf
COPY default.conf etc/nginx/conf.d/default.conf
#RUN mv nginx.conf default.conf
CMD ["nginx", "-g", "daemon off;"]