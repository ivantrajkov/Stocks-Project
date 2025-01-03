package mk.finki.ukim.dians.stocksapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Data
public class User {
    @Id
    private String username;
    private String password;
    private boolean loggedIn;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
