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
import java.time.LocalDate;
import java.util.List;

public class Admin extends User implements Serializable {

    public Admin() {
        super();
    }

    /** 
     * Constructor đầy đủ: dùng khi bạn đã có đủ thông tin để cấp phát toàn bộ.
     */
    public Admin(String id,
                 String fullName,
                 String emailAddress,
                 LocalDate dateOfBirth,
                 EGender gender,
                 String phoneNumber,
                 List<Address> addresses,
                 String avatarUrl) {
        super(id, fullName, emailAddress, dateOfBirth, gender, phoneNumber, addresses, avatarUrl);
    }

    /**
     * Constructor tiện lợi: chỉ với các trường tối thiểu, các trường khác có thể set sau.
     */
    public Admin(String id, String fullName, String emailAddress) {
        super();
        setId(id);
        setFullName(fullName);
        setEmailAddress(emailAddress);
    }

    @Override
    public String toString() {
        return "Admin{" + super.toString() + "}";
    }
}