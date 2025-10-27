package src.interfaces;

import java.sql.SQLException;
import java.util.List;

import src.model.pojo.UserRole;

public interface IUserRole {

    int addUserRole(UserRole role) throws SQLException;

    UserRole getUserRoleById(int roleId) throws SQLException;

    UserRole getUserRoleByName(String roleName) throws SQLException;

    List<UserRole> getAllUserRoles() throws SQLException;

    boolean updateUserRole(UserRole role) throws SQLException;

    boolean deleteUserRole(int roleId) throws SQLException;

}