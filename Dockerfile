FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY pom.xml ./
COPY src ./src

RUN mvn clean package -DskipTests --no-transfer-progress

COPY . .
RUN mvn install
RUN mvn clean package

FROM eclipse-temurin:21

WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080

CMD [ "java", "-jar", "app.jar" , "--spring.profiles.active=prod"]