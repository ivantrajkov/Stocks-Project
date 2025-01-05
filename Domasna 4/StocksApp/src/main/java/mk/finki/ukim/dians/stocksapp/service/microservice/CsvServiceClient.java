package mk.finki.ukim.dians.stocksapp.service.microservice;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CsvServiceClient {

    private final RestTemplate restTemplate;

    private static final String URL = "http://csv-microservice:8082/csv";

    public CsvServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public byte[] downloadCSV() {
        ResponseEntity<byte[]> response = restTemplate.getForEntity(URL, byte[].class);
        return response.getBody();
    }
}