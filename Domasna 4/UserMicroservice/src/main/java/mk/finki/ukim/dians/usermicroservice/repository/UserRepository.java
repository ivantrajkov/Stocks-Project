package mk.finki.ukim.dians.usermicroservice.repository;

import mk.finki.ukim.dians.usermicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    User findByLoggedInTrue();
}