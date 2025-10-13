package murach.checkout.model;

public class Address {
    private long id;
    private String userId;
    private String nation, province, district, village, detail;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
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
}
