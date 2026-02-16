FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw mvnw
COPY mvnw.cmd mvnw.cmd
RUN mvn -B -q dependency:go-offline

COPY src src
RUN mvn -B -DskipTests clean package

FROM eclipse-temurin:21-jre
WORKDIR /app

ENV SPRING_PROFILES_ACTIVE=docker
ENV SERVER_PORT=8080
ENV DB_URL=jdbc:postgresql://postgres:5432/gameadmin
ENV DB_USERNAME=postgres
ENV DB_PASSWORD=root
ENV ISSUER_URI=http://localhost:8081/realms/gameadmin-realm
ENV JWK_SET_URI=http://keycloak:8080/realms/gameadmin-realm/protocol/openid-connect/certs

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
