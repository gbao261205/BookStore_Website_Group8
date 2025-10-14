// src/main/java/model/User.java
package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class User {
    private long id;                  // PK
    private long accountId;           // FK -> account.id
    private String fullName;
    private String email;
    private String phoneNumber;       // phone trong diagram
    private Gender gender;            // MALE/FEMALE
    private LocalDate dateOfBirth;    // dob
    private String avatar;            // url hoặc path ảnh
    private Long addressId;           // FK -> address.id (nullable)
    private LocalDateTime createdAt;  // thời điểm tạo
    private LocalDateTime updatedAt;  // thời điểm cập nhật gần nhất

    // ===== getters/setters =====
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getAccountId() { return accountId; }
    public void setAccountId(long accountId) { this.accountId = accountId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public Long getAddressId() { return addressId; }
    public void setAddressId(Long addressId) { this.addressId = addressId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
