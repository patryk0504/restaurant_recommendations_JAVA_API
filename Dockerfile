FROM openjdk:15-jdk-alpine
MAINTAINER https://github.com/patryk0504
COPY target/ZTI-0.0.1-SNAPSHOT.jar ZTI-0.0.1-SNAPSHOT-image.jar
EXPOSE 7777
ENTRYPOINT ["java","-jar","ZTI-0.0.1-SNAPSHOT-image.jar"]