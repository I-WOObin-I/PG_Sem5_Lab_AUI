FROM openjdk:11

RUN apt-get update
RUN apt-get install mc -y

COPY planetary-info.jar planetary-info.jar

RUN mkdir files

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "planetary-info.jar"]