package mk.finki.ukim.dians.stocksapp.service;

import mk.finki.ukim.dians.stocksapp.model.User;

import java.util.Optional;

public interface UserService {
    void addUser(User user);
    Optional<User> getByUsername(String username);
    User isLoggedIn();
}
