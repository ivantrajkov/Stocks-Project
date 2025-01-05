
FROM eclipse-temurin as tmp

WORKDIR /usr/src/

COPY pom.xml .
COPY src ./src

RUN apt-get update && apt-get install -y maven
RUN mvn clean package -DskipTests

FROM eclipse-temurin

WORKDIR /usr/src/

COPY --from=tmp /usr/src/target/StocksApp-0.0.1-SNAPSHOT.jar /usr/src/StocksApp.jar

EXPOSE 8080

CMD ["java", "-jar", "StocksApp.jar"]
