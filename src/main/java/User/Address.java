/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User;

/**
 *
 * @author POW
 */
import java.io.Serializable;
import java.util.Objects;

public class Address implements Serializable {
    private long id;
    private String nation;
    private String province;
    private String district;
    private String village;
    private String detail;

    public Address() {}

    public Address(long id, String nation, String province, String district, String village, String detail) {
        this.id = id;
        this.nation = nation;
        this.province = province;
        this.district = district;
        this.village = village;
        this.detail = detail;
    }

    // Getters & Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNation() { return nation; }
    public void setNation(String nation) { this.nation = nation; }

    public String getProvince() { return province; }
    public void setProvince(String province) { this.province = province; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getVillage() { return village; }
    public void setVillage(String village) { this.village = village; }

    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return id == address.id;
    }

    @Override public int hashCode() { return Objects.hash(id); }

    @Override public String toString() {
        return "Address{" +
                "id=" + id +
                ", nation='" + nation + '\'' +
                ", province='" + province + '\'' +
                ", district='" + district + '\'' +
                ", village='" + village + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
