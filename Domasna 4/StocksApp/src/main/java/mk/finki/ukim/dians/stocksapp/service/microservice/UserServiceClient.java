package mk.finki.ukim.dians.stocksapp.service.microservice;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceClient {

    private final RestTemplate restTemplate;

    private static final String USER_SERVICE_URL = "http://localhost:8081/authentication";

    public UserServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> registerUser(String username, String password) {
        String registerUrl = USER_SERVICE_URL + "/register?username=" + username + "&password=" + password;
        return restTemplate.postForEntity(registerUrl, null, String.class);
    }

    public ResponseEntity<String> loginUser(String username, String password) {
        String loginUrl = USER_SERVICE_URL + "/login?username=" + username + "&password=" + password;
        return restTemplate.postForEntity(loginUrl, null, String.class);
    }

    public ResponseEntity<String> getLoggedInUser() {
        String loggedInUrl = USER_SERVICE_URL + "/loggedIn";
        return restTemplate.getForEntity(loggedInUrl, String.class);
    }
}