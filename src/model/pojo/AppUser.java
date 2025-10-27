package src.model.pojo;

public class AppUser {

    private int userId;
    private String name;
    private String email;
    private int roleId;
    private long phone;
    private String address;
    private String password;

    public AppUser(int userId, String name, String email, int roleId, long phone, String address, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.roleId = roleId;
        this.phone = phone;
        this.address = address;
        this.password = password;
    }

    public AppUser(String name, String email, int roleId, long phone, String address, String password) {
        this.name = name;
        this.email = email;
        this.roleId = roleId;
        this.phone = phone;
        this.address = address;
        this.password = password;
    }

    public AppUser() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
