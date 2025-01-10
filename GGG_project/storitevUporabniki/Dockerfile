FROM openjdk:17-jdk-slim
RUN mkdir /app
WORKDIR /app

COPY api-uporabniki/target/api-uporabniki-1.0.0-SNAPSHOT.jar /api-uporabniki-1.0.0-SNAPSHOT.jar
VOLUME /tmp

EXPOSE 8080
#RUN chmod +x /api-kartice-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/api-uporabniki-1.0.0-SNAPSHOT.jar"]
