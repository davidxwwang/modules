
FROM java:8

COPY target/*.jar  /app.jar

CMD ["--server.port=8009"]

EXPOSE 8009

ENTRYPOINT ["java", "-jar", "/app.jar"]