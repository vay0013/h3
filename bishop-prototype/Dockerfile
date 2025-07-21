FROM maven AS build

# Сначала собираем и устанавливаем библиотеку
WORKDIR /build/starter
COPY synthetic-human-core-starter .
RUN mvn clean install -DskipTests

# Затем собираем основное приложение
WORKDIR /build/app
COPY bishop-prototype .
RUN mvn clean package -DskipTests

# Финальный образ
FROM openjdk:17
WORKDIR /app
COPY --from=build /build/app/target/bishop-prototype-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"] 