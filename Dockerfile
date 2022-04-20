FROM openjdk:11-jdk-slim-buster
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src

ENV PORT 8080
EXPOSE $PORT

CMD ["./mvnw", "spring-boot:run"]