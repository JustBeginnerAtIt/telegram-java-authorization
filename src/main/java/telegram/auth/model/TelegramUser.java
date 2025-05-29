package telegram.auth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "telegram_user")
public class TelegramUser {

    @Id
    private Long id;

    private String firstName;
    private String lastName;
    private String userName;

    private Instant authDate;

    public TelegramUser() {}

    public TelegramUser(
            Long id,
            String firstName,
            String lastName,
            String userName,
            Instant authDate
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.authDate = authDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Instant getAuthDate() {
        return authDate;
    }

    public void setAuthDate(Instant authDate) {
        this.authDate = authDate;
    }
}
