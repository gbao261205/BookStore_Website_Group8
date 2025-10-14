// src/main/java/model/Address.java
package model;

import java.time.LocalDateTime;

public class Address {
    private long id;
    private String street;   // số nhà, đường
    private String ward;     // phường/xã
    private String district; // quận/huyện
    private String province; // tỉnh/thành
    private LocalDateTime createdAt;

    // getters/setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    public String getWard() { return ward; }
    public void setWard(String ward) { this.ward = ward; }
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    public String getProvince() { return province; }
    public void setProvince(String province) { this.province = province; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
