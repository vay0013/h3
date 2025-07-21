FROM maven AS build
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17
WORKDIR /app
COPY --from=build /build/target/synthetic-human-core-starter-0.0.1.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"] 