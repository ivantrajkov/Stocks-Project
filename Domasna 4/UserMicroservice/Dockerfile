FROM eclipse-temurin

WORKDIR /usr/src/myapp

COPY target/UserMicroservice-0.0.1-SNAPSHOT.jar /usr/src/myapp/user-service.jar

EXPOSE 8081

CMD ["java", "-jar", "/usr/src/myapp/user-service.jar"]
