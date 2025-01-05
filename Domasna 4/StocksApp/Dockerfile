
FROM eclipse-temurin as builder

WORKDIR /usr/src/

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin

WORKDIR /usr/src/

COPY --from=builder /usr/src/target/your-main-app.jar /usr/src/your-main-app.jar

EXPOSE 8080

CMD ["java", "-jar", "StocksApp.jar"]
