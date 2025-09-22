FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY ${JAR_FILE} app.jar
EXPOSE 12001
ENTRYPOINT ["java", "-jar", "app.jar"]