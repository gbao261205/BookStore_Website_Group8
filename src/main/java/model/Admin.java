/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author POW
 */

public class Admin {
    private long id;          // thường trùng id của User nếu bảng admin tham chiếu user.id
    private String roleName;  // tuỳ schema, có thể bỏ

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
}