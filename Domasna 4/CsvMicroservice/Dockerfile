FROM eclipse-temurin

WORKDIR /usr/src/myapp

COPY target/CsvMicroservice-0.0.1-SNAPSHOT.jar /usr/src/myapp/csv-service.jar

EXPOSE 8082

CMD ["java", "-jar", "/usr/src/myapp/csv-service.jar"]
