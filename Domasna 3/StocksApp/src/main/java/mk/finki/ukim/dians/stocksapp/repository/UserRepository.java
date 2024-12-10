package mk.finki.ukim.dians.stocksapp.repository;

import mk.finki.ukim.dians.stocksapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
}
