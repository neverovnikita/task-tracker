# Этап сборки
FROM gradle:8.4-jdk17 AS build
WORKDIR /app

# Копируем только файлы, необходимые для сборки (оптимизация кэширования)
COPY build.gradle .
COPY settings.gradle .
COPY src ./src

# Собираем приложение
RUN gradle clean bootJar -x test

# Этап запуска
FROM openjdk:17-jdk-slim
WORKDIR /app

# Копируем собранный JAR
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]