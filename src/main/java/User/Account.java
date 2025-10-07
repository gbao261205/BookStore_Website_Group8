/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User;
import java.io.Serializable;
import java.util.Objects;
/**
 *
 * @author POW
 */
public class Account implements Serializable {
    private String id;
    private String username;
    private String password;

    /** Quan hệ 1-1 với User theo sơ đồ (tùy bạn map ORM sau) */
    private User user;

    public Account() {}

    public Account(String id, String username, String password, User user) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.user = user;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override public int hashCode() { return Objects.hash(id); }

    @Override public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", user=" + (user != null ? user.getId() : null) +
                '}';
    }
}
