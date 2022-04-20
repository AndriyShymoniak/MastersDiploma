FROM openjdk:11-jdk-slim-buster
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src

ENV PORT 8080
EXPOSE $PORT

CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=test", "-Dspring-boot.run.jvmArguments='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005'"]