FROM openjdk:21
EXPOSE 8090
EXPOSE 8091

#ARG JAR_FILE=target/*.jar

COPY target/bot.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
