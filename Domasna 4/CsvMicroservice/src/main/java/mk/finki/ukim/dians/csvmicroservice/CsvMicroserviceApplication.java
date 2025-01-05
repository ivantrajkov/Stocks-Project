package mk.finki.ukim.dians.csvmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CsvMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CsvMicroserviceApplication.class, args);
    }

}
