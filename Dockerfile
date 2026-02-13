# Stage 1: Build
# alpine appended generates smaller image
FROM gradle:9.3.1-jdk25-alpine AS build
WORKDIR /app
COPY . .
RUN gradle bootJar

# Stage 2: Run
FROM gradle:9.3.1-jdk25-alpine
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
