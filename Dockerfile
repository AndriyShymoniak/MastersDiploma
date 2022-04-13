FROM openjdk:11
EXPOSE 8080
ADD target/masters-app-docker.jar masters-app-docker.jar
ENTRYPOINT ["java", "-jar", "masters-app-docker.jar"]