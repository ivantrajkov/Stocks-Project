package mk.finki.ukim.dians.stocksapp.controller;

import mk.finki.ukim.dians.stocksapp.model.User;
import mk.finki.ukim.dians.stocksapp.service.UserService;
import mk.finki.ukim.dians.stocksapp.service.microservice.UserServiceClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/authentication")
@Validated
@CrossOrigin(origins="*")
public class AuthenticationController {

    private final UserServiceClient userServiceClient;

    public AuthenticationController(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String username, @RequestParam String password) {
        return userServiceClient.registerUser(username, password);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        return userServiceClient.loginUser(username, password);
    }

    @GetMapping("/loggedIn")
    public ResponseEntity<String> getLoggedInUser() {
        return userServiceClient.getLoggedInUser();
    }
//    private final UserService userService;
//
//    public AuthenticationController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<Map<String, String>> register(
//            @RequestParam String username,
//            @RequestParam String password
//    ) {
//        User user = new User(username, password);
//        userService.addUser(user);
//        Map<String, String> response = new HashMap<>();
//        response.put("redirect", "/login");
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(
//            @RequestParam String username,
//            @RequestParam String password
//    ) {
//        Optional<User> userOpt = Optional.ofNullable(userService.isLoggedIn());
//        if(userOpt.isPresent()){
//            User user = userOpt.get();
//            user.setLoggedIn(false);
//            userService.addUser(user);
//        }
//        return userService.getByUsername(username)
//                .map(user -> {
//                    if (user.getPassword().equals(password)) {
//                        user.setLoggedIn(true);
//                        userService.addUser(user);
//                        return new ResponseEntity<>("Login successful",HttpStatus.OK);
//                    }
//                    return ResponseEntity.badRequest().body("Invalid password");
//                })
//                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"));
//    }
//
//    /**
//     * Checks if the user is logged in or its a guest, and it returns the username or guest
//     */
//    @GetMapping("/loggedIn")
//    public ResponseEntity<String> getLoggedInfo(){
//        User user = userService.isLoggedIn();
//        if(user == null){
//            return new ResponseEntity<>("Guest",HttpStatus.OK);
//        } else {
//
//        }return new ResponseEntity<>(user.getUsername(),HttpStatus.OK);
//    }
}
