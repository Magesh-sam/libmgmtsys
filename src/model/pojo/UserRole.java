package src.model.pojo;

public class UserRole {
    private int roleId;
    private String name;

    public UserRole(int roleId, String name) {
        this.roleId = roleId;
        this.name = name;
    }

    public UserRole(String name) {
        this.name = name;
    }

    public UserRole() {
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
