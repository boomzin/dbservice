FROM gradle:8-jdk21 AS builder

WORKDIR /app
COPY --chown=gradle:gradle . .

RUN gradle clean bootJar --no-daemon --stacktrace --info

FROM openjdk:21-jdk-slim

WORKDIR /app
COPY --from=builder /app/build/libs/dbservice.jar dbservice.jar


ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/dbservice.jar", "--spring.config.location=file:/config/application.yaml"]
