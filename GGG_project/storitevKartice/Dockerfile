FROM openjdk:17-jdk-slim
RUN mkdir /app
WORKDIR /app
RUN mvn clean install
COPY api-kartice/target/api-kartice-1.0.0-SNAPSHOT.jar api-kartice-1.0.0-SNAPSHOT.jar
VOLUME /tmp
ENTRYPOINT ["java", "-jar", "api-kartice-1.0.0-SNAPSHOT.jar"]
