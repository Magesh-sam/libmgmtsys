package src.interfaces;

import java.sql.SQLException;

import src.model.pojo.AppUser;

public interface IAppUser {

    // Create user (roleId must be present in user.getRoleId() and must exist in
    // userrole table)
    int createUser(AppUser user, int roleId) throws SQLException;

    // Create user by role name (look up role_id first)
    int createUser(AppUser user, String roleName) throws SQLException;

    // Get by email + password
    AppUser getUserByEmailAndPassword(String email, String password) throws SQLException;

    // Get by email only
    AppUser getUserByEmail(String email) throws SQLException;

    // Get by ID
    AppUser getUserById(int userId) throws SQLException;

    // Update user
    boolean updateUser(AppUser user) throws SQLException;

    // Delete user
    boolean deleteUser(int userId) throws SQLException;

}