// src/main/java/dao/AccountDAO.java
package dao;

import model.SessionUser;
import java.util.Optional;

public interface AccountDAO {
    Optional<SessionUser> signIn(String username, String passwordPlain);
    Optional<SessionUser> signUp(
        String fullName, String email, String phone, String dob, String genderCode, String avatarUrl,
        String username, String passwordPlain,
        String street, String ward, String district, String city, String province, String zipcode, String country
    );
    boolean usernameExists(String username);
    boolean emailExists(String email);
}