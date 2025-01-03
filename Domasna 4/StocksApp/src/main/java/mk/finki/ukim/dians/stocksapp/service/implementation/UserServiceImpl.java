package mk.finki.ukim.dians.stocksapp.service.implementation;

import mk.finki.ukim.dians.stocksapp.model.User;
import mk.finki.ukim.dians.stocksapp.repository.UserRepository;
import mk.finki.ukim.dians.stocksapp.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;



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
