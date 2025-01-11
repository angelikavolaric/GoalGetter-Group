FROM openjdk:17-jdk-slim
RUN mkdir /app
WORKDIR /app

#RUN mvn clean install

COPY api-belezenje/target/api-belezenje-1.0.0-SNAPSHOT.jar api-belezenje-1.0.0-SNAPSHOT.jar
VOLUME /tmp
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "api-belezenje-1.0.0-SNAPSHOT.jar"]