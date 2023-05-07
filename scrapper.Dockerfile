FROM openjdk:21-oracle
ARG JAR_FILE=target/scrapper-1.0-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} scrapper.jar
ENTRYPOINT ["java","-jar","scrapper.jar"]