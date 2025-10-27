package src.model.pojo;

public class AppUser {

    public static enum UserRole {
        member, librarian
    }

    private int userId;
    private String name;
    private String email;
    private UserRole role;
    private long phone;
    private String address;
    private String password;

    public AppUser(int userId, String name, String email, UserRole role, long phone, String address, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.phone = phone;
        this.address = address;
        this.password = password;
    }

    public AppUser(String name, String email, UserRole role, long phone, String address) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.phone = phone;
        this.address = address;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
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
