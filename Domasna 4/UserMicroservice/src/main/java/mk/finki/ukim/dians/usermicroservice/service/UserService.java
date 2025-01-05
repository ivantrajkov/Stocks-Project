package mk.finki.ukim.dians.usermicroservice.service;


import mk.finki.ukim.dians.usermicroservice.model.User;

import java.util.Optional;

public interface UserService {
    void addUser(User user);
    Optional<User> getByUsername(String username);
    User isLoggedIn();
}