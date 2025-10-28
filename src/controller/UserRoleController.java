package src.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import src.model.pojo.UserRole;
import src.service.UserRoleService;

public class UserRoleController {

    private final UserRoleService userRoleService;

    public UserRoleController() {
        this.userRoleService = new UserRoleService();
    }

    public int addUserRole(UserRole role) {
        try {
            return userRoleService.addUserRole(role);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error adding user role: " + e.getMessage());
            return 0;
        }
    }

    public UserRole getUserRoleById(int roleId) {
        try {
            return userRoleService.getUserRoleById(roleId);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error fetching role: " + e.getMessage());
            return null;
        }
    }

    public UserRole getUserRoleByName(String roleName) {
        try {
            return userRoleService.getUserRoleByName(roleName);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error fetching role by name: " + e.getMessage());
            return null;
        }
    }

    public List<UserRole> getAllUserRoles() {
        try {
            return userRoleService.getAllUserRoles();
        } catch (SQLException e) {
            System.err.println("Error fetching user roles: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public boolean updateUserRole(UserRole role) {
        try {
            return userRoleService.updateUserRole(role);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error updating user role: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUserRole(int roleId) {
        try {
            return userRoleService.deleteUserRole(roleId);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error deleting user role: " + e.getMessage());
            return false;
        }
    }
}
