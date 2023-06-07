FROM ubuntu:latest
MAINTAINER "Library"
RUN apt update -y
RUN apt install -y openjdk-17-jdk
RUN apt install -y maven
RUN mkdir /app
WORKDIR /app
COPY src /app/src
COPY pom.xml /app/pom.xml
COPY users.json /app/users.json
COPY booksFile.json /app/booksFile.json
RUN mvn package


CMD ["java","-jar","./target/biblioteka-0.0.1-SNAPSHOT.jar"]