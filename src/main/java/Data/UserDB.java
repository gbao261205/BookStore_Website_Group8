/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data;

import User.*;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.sql.Date;
/**
 *
 * @author POW
 */
public class UserDB {
    private final DataSource ds;

    public UserDB(DataSource ds) {
        this.ds = ds;
    }

    private Connection getConn() throws SQLException {
        return ds.getConnection(); // lấy từ pool
    }
    
    /* =========================
       INSERT / UPDATE
       ========================= */

    /** Tạo user (User/ Admin …) */
    public void insertUser(User u, String userType) throws SQLException {
        String sql = "INSERT INTO users(id, full_name, email, date_of_birth, gender, phone, avatar_url, user_type) " +
                     "VALUES(?,?,?,?,?,?,?,?)";
        try (Connection cn = getConn();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, u.getId());
            ps.setString(2, u.getFullName());
            ps.setString(3, u.getEmailAddress());
            if (u.getDateOfBirth() != null) {
                ps.setDate(4, Date.valueOf(u.getDateOfBirth()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            ps.setString(5, u.getGender() == null ? EGender.UNKNOWN.name() : u.getGender().name());
            ps.setString(6, u.getPhoneNumber());
            ps.setString(7, u.getAvatarUrl());
            ps.setString(8, userType); // "ADMIN", "CUSTOMER", ...
            ps.executeUpdate();
        }
    }

    /** Thêm 1 địa chỉ cho user
     * @param userId */
    public long insertAddress(String userId, Address a) throws SQLException {
        String sql = "INSERT INTO addresses(user_id, nation, province, district, village, detail) " +
                     "VALUES(?,?,?,?,?,?)";
        try (Connection cn = getConn();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, userId);
            ps.setString(2, a.getNation());
            ps.setString(3, a.getProvince());
            ps.setString(4, a.getDistrict());
            ps.setString(5, a.getVillage());
            ps.setString(6, a.getDetail());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        return -1L;
    }

    /** Tạo account liên kết user */
    public void insertAccount(Account acc) throws SQLException {
        String sql = "INSERT INTO accounts(id, username, password, user_id) VALUES(?,?,?,?)";
        try (Connection cn = getConn();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, acc.getId());
            ps.setString(2, acc.getUsername());
            ps.setString(3, acc.getPassword()); // lưu password đã hash
            ps.setString(4, acc.getUser().getId());
            ps.executeUpdate();
        }
    }

    /* =========================
       QUERY / AUTH
       ========================= */

    /** Tìm account theo username (để tự bạn kiểm tra password hash) */
    public Account findAccountByUsername(String username) throws SQLException {
        String sql = "SELECT a.id acc_id, a.username, a.password, u.* " +
                     "FROM accounts a JOIN users u ON a.user_id = u.id WHERE a.username = ?";
        try (Connection cn = getConn();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapAccountWithUser(rs);
            }
        }
        return null;
    }

    /** Xác thực (nếu bạn chưa dùng hash) – demo cho môi trường học tập */
    public Account authenticatePlain(String username, String plainPassword) throws SQLException {
        String sql = "SELECT a.id acc_id, a.username, a.password, u.* " +
                     "FROM accounts a JOIN users u ON a.user_id = u.id " +
                     "WHERE a.username = ? AND a.password = ?";
        try (Connection cn = getConn();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, plainPassword);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapAccountWithUser(rs);
            }
        }
        return null;
    }

    /** Lấy danh sách tất cả user (kèm địa chỉ) để đẩy lên view quản trị
     * @return
     * @throws java.sql.SQLException  */
    public List<User> findAllUsersWithAddresses() throws SQLException {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection cn = getConn();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User u = mapUser(rs);
                u.setAddresses(getAddressesByUserId(u.getId(), cn));
                list.add(u);
            }
        }
        return list;
    }

    /** Lấy user bằng id (kèm địa chỉ) */
    public User findUserById(String userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection cn = getConn();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = mapUser(rs);
                    u.setAddresses(getAddressesByUserId(userId, cn));
                    return u;
                }
            }
        }
        return null;
    }

    /* =========================
       MAPPING HELPERS
       ========================= */

    private Account mapAccountWithUser(ResultSet rs) throws SQLException {
        User u = mapUser(rs);
        u.setAddresses(getAddressesByUserId(u.getId(), rs.getStatement().getConnection()));

        Account acc = new Account();
        acc.setId(rs.getString("acc_id"));
        acc.setUsername(rs.getString("username"));
        acc.setPassword(rs.getString("password"));
        acc.setUser(u);
        return acc;
    }

    private User mapUser(ResultSet rs) throws SQLException {
        String userType = rs.getString("user_type");
        User u;
        if ("ADMIN".equalsIgnoreCase(userType)) {
            u = new Admin(); // nếu có loại khác, map tương tự
        } else {
            // Dùng tạm Admin như user mặc định nếu bạn chưa tạo lớp Customer/Seller
            u = new Admin();
        }
        u.setId(rs.getString("id"));
        u.setFullName(rs.getString("full_name"));
        u.setEmailAddress(rs.getString("email"));
        Date dob = rs.getDate("date_of_birth");
        if (dob != null) u.setDateOfBirth(dob.toLocalDate());
        String g = rs.getString("gender");
        u.setGender(g == null ? EGender.UNKNOWN : EGender.valueOf(g));
        u.setPhoneNumber(rs.getString("phone"));
        u.setAvatarUrl(rs.getString("avatar_url"));
        return u;
    }

    private List<Address> getAddressesByUserId(String userId, Connection cn) throws SQLException {
        String sql = "SELECT * FROM addresses WHERE user_id = ? ORDER BY id";
        List<Address> addrs = new ArrayList<>();
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Address a = new Address();
                    a.setId(rs.getLong("id"));
                    a.setNation(rs.getString("nation"));
                    a.setProvince(rs.getString("province"));
                    a.setDistrict(rs.getString("district"));
                    a.setVillage(rs.getString("village"));
                    a.setDetail(rs.getString("detail"));
                    addrs.add(a);
                }
            }
        }
        return addrs;
    }
    
    // THÊM mới: kiểm tra username đã tồn tại
    public boolean usernameExists(String username) throws SQLException {
        String sql = "SELECT 1 FROM accounts WHERE username = ? LIMIT 1";
        try (Connection cn = getConn();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    // (tuỳ chọn) kiểm tra email đã dùng
    public boolean emailExists(String email) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE email = ? LIMIT 1";
        try (Connection cn = getConn();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
    
    // Cập nhật thông tin cơ bản của User
    public void updateUserProfile(User u) throws SQLException {
        String sql = "UPDATE users SET full_name=?, email=?, date_of_birth=?, gender=?, phone=?, avatar_url=? WHERE id=?";
        try (Connection cn = getConn();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, u.getFullName());
            ps.setString(2, u.getEmailAddress());
            if (u.getDateOfBirth() != null) {
                ps.setDate(3, java.sql.Date.valueOf(u.getDateOfBirth()));
            } else {
                ps.setNull(3, java.sql.Types.DATE);
            }
            ps.setString(4, (u.getGender() == null ? EGender.UNKNOWN : u.getGender()).name());
            ps.setString(5, u.getPhoneNumber());
            ps.setString(6, u.getAvatarUrl());
            ps.setString(7, u.getId());

            ps.executeUpdate();
        }
    }

}
