// src/main/java/dao/impl/AccountDAOImpl.java
package dao.impl;

import dao.AccountDAO;
import model.SessionUser;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class AccountDAOImpl implements AccountDAO {
    private final String url    = "jdbc:mysql://localhost:3306/WEB_BOOKSTORE?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
    private final String dbUser = "root";
    private final String dbPass = "bin123124";

    private Connection getConn() throws SQLException {
        return DriverManager.getConnection(url, dbUser, dbPass);
    }

    @Override
    public Optional<SessionUser> signIn(String username, String passwordPlain) {
        String sql = """
            SELECT u.id AS user_id, a.id AS account_id, a.username, u.full_name, u.email,
                   EXISTS(SELECT 1 FROM admin ad WHERE ad.id = u.id) AS is_admin
            FROM account a
            JOIN `user` u ON u.account_id = a.id
            WHERE a.username = ? AND a.password = ?
        """;
        try (Connection c = getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, passwordPlain); // plaintext
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SessionUser su = new SessionUser();
                    su.setUserId(rs.getLong("user_id"));
                    su.setAccountId(rs.getLong("account_id"));
                    su.setUsername(rs.getString("username"));
                    su.setFullName(rs.getString("full_name"));
                    su.setEmail(rs.getString("email"));
                    su.setAdmin(rs.getBoolean("is_admin"));
                    return Optional.of(su);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return Optional.empty();
    }

    // dao/impl/AccountDAOImpl.java (chỉ phần thay đổi)
    @Override
    public Optional<SessionUser> signUp(
            String fullName, String email, String phone, String dob, String genderCode, String avatarUrl,
            String username, String passwordPlain,
            String street, String ward, String district, String city, String province, String zipcode, String country
    ) {
        String insertAccount = "INSERT INTO account(username, password) VALUES (?,?)";
        String insertUser = """
            INSERT INTO `user`(account_id, full_name, email, phone, date_of_birth, gender_id, avatar_url)
            VALUES (?,?,?,?,?,?,?)
        """;
        String insertAddress = """
            INSERT INTO address(user_id, street, ward, district, city, province, country, zipcode)
            VALUES (?,?,?,?,?,?,?,?)
        """;
        String insertCustomer = "INSERT INTO customer(id, membership_tier_id, points) VALUES (?, ?, 0)";

        try (Connection c = getConn()) {
            c.setAutoCommit(false);

            // 1) account
            long accountId;
            try (PreparedStatement ps = c.prepareStatement(insertAccount, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, username);
                ps.setString(2, passwordPlain);
                ps.executeUpdate();
                try (ResultSet gk = ps.getGeneratedKeys()) { gk.next(); accountId = gk.getLong(1); }
            }

            // resolve gender_id từ code (MALE/FEMALE) nếu có
            Integer genderId = null;
            if (genderCode != null && !genderCode.isBlank()) {
                try (PreparedStatement ps = c.prepareStatement("SELECT id FROM gender WHERE code=?")) {
                    ps.setString(1, genderCode.trim().toUpperCase());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) genderId = rs.getInt(1);
                    }
                }
            }

            // 2) user
            long userId;
            try (PreparedStatement ps = c.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, accountId);
                ps.setString(2, fullName);
                ps.setString(3, email);
                ps.setString(4, isBlank(phone) ? null : phone);
                ps.setDate(5, isBlank(dob) ? null : Date.valueOf(dob)); // yyyy-MM-dd
                if (genderId == null) ps.setNull(6, Types.TINYINT); else ps.setInt(6, genderId);
                ps.setString(7, isBlank(avatarUrl) ? null : avatarUrl);
                ps.executeUpdate();
                try (ResultSet gk = ps.getGeneratedKeys()) { gk.next(); userId = gk.getLong(1); }
            }

            // 3) address (optional)
            boolean hasAddr = !allBlank(street, ward, district, city, province, zipcode, country);
            if (hasAddr) {
                try (PreparedStatement ps = c.prepareStatement(insertAddress)) {
                    ps.setLong(1, userId);
                    ps.setString(2, val(street));
                    ps.setString(3, val(ward));
                    ps.setString(4, val(district));
                    ps.setString(5, val(city));
                    ps.setString(6, val(province));
                    ps.setString(7, val(country, "Vietnam"));
                    ps.setString(8, val(zipcode));
                    ps.executeUpdate();
                }
            }

            // resolve id tier mặc định từ code 'MEMBER'
            Integer memberTierId = null;
            try (PreparedStatement ps = c.prepareStatement("SELECT id FROM membership_tier WHERE code='MEMBER'")) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) memberTierId = rs.getInt(1);
                }
            }

            // 4) customer
            try (PreparedStatement ps = c.prepareStatement(insertCustomer)) {
                ps.setLong(1, userId);
                if (memberTierId == null) ps.setNull(2, Types.TINYINT); else ps.setInt(2, memberTierId);
                ps.executeUpdate();
            }

            c.commit();

            SessionUser su = new SessionUser();
            su.setUserId(userId);
            su.setAccountId(accountId);
            su.setUsername(username);
            su.setFullName(fullName);
            su.setEmail(email);
            su.setAdmin(false);
            return Optional.of(su);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private static boolean isBlank(String s){ return s==null || s.isBlank(); }
    private static boolean allBlank(String... arr){
        for(String s: arr) if(!isBlank(s)) return false;
        return true;
    }


    @Override
    public boolean usernameExists(String username) {
        try (Connection c = getConn();
             PreparedStatement ps = c.prepareStatement("SELECT 1 FROM account WHERE username=?")) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    @Override
    public boolean emailExists(String email) {
        try (Connection c = getConn();
             PreparedStatement ps = c.prepareStatement("SELECT 1 FROM `user` WHERE email=?")) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private static String val(String s) { return (s == null || s.isBlank()) ? null : s.trim(); }
    private static String val(String s, String def) { return (s == null || s.isBlank()) ? def : s.trim(); }
}
