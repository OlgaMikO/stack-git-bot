FROM openjdk:21-oracle
ARG JAR_FILE=./bot/target/bot-1.0-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} bot.jar
ENTRYPOINT ["java","-jar","bot.jar"]