FROM openjdk:21-slim

WORKDIR /app

COPY target/*.jar /app/app.jar

EXPOSE 8080

CMD [ "java", "-jar", "app.jar" ]