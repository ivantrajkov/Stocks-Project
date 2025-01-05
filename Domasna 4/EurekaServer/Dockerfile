FROM eclipse-temurin

WORKDIR /usr/src/myapp

COPY target/EurekaServer-0.0.1-SNAPSHOT.jar /usr/src/myapp/eureka.jar

EXPOSE 8761

CMD ["java", "-jar", "/usr/src/myapp/eureka.jar"]
