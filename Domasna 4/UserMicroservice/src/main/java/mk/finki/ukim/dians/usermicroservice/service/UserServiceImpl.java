package mk.finki.ukim.dians.usermicroservice.service;


import jakarta.annotation.PostConstruct;
import mk.finki.ukim.dians.usermicroservice.model.User;
import mk.finki.ukim.dians.usermicroservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findById(username);
    }

    @Override
    public User isLoggedIn() {
        return userRepository.findByLoggedInTrue();
    }

    @PostConstruct
    public void clearUserTable() {
        userRepository.deleteAll();
    }
}