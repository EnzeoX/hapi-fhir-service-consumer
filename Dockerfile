FROM adoptopenjdk/openjdk11:latest

MAINTAINER Nikolay Boiko

COPY ./target/app-consumer.jar /usr/app/

WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "app-consumer.jar"]