FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/barber-app-0.0.1-SNAPSHOT.jar /app/barber.jar
ENTRYPOINT ["java", "-jar", "barber.jar"]