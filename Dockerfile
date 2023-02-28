FROM adoptopenjdk/openjdk11:alpine-jre
MAINTAINER Lincol Morales "lincol.morales@gmail.com"
WORKDIR /app
ARG JAR_FILE=target/storeapp-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=local","app.jar"]