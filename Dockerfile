# ---------- Stage 1: Build ----------
FROM gradle:9.3.1-jdk25-alpine AS build
WORKDIR /app

# Copy only gradle files first (better caching)
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle settings.gradle ./

RUN chmod +x gradlew
RUN gradle dependencies --no-daemon

# Copy source code
COPY src ./src

RUN gradle bootJar --no-daemon


# ---------- Stage 2: Run ----------
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
