/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 *
 * @author POW
 */
public abstract class User implements Serializable {
    private String id;
    private String fullName;
    private String emailAddress;
    private LocalDate dateOfBirth;
    private EGender gender = EGender.UNKNOWN;
    private String phoneNumber;
    private List<Address> addresses = new ArrayList<>();
    private String avatarUrl;

    protected User() {}

    protected User(String id, String fullName, String emailAddress, LocalDate dateOfBirth,
                   EGender gender, String phoneNumber, List<Address> addresses, String avatarUrl) {
        this.id = id;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.dateOfBirth = dateOfBirth;
        this.gender = (gender == null ? EGender.UNKNOWN : gender);
        if (addresses != null) this.addresses = addresses;
        this.phoneNumber = phoneNumber;
        this.avatarUrl = avatarUrl;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public EGender getGender() { return gender; }
    public void setGender(EGender gender) { this.gender = (gender == null ? EGender.UNKNOWN : gender); }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public List<Address> getAddresses() { return addresses; }
    public void setAddresses(List<Address> addresses) {
        this.addresses = (addresses == null ? new ArrayList<>() : addresses);
    }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override public int hashCode() { return Objects.hash(id); }

    @Override public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", addresses=" + addresses +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
