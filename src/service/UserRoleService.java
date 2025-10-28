package src.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import src.interfaces.IUserRole;
import src.model.dao.UserRoleDAO;
import src.model.pojo.UserRole;
import src.utils.Validation;

public class UserRoleService implements IUserRole {

    private final IUserRole userRoleDAO;

    public UserRoleService() {
        this.userRoleDAO = new UserRoleDAO();
    }

    @Override
    public int addUserRole(UserRole role) throws SQLException {
        Objects.requireNonNull(role, "Role cannot be null");
        Validation.requireNonEmpty(role.getName(), "Role name");

        if (((UserRoleDAO) userRoleDAO).roleExists(role.getName())) {
            throw new IllegalArgumentException("Role already exists with name: " + role.getName());
        }

        return userRoleDAO.addUserRole(role);
    }

    @Override
    public UserRole getUserRoleById(int roleId) throws SQLException {
        if (roleId <= 0)
            throw new IllegalArgumentException("Invalid role ID");
        return userRoleDAO.getUserRoleById(roleId);
    }

    @Override
    public UserRole getUserRoleByName(String roleName) throws SQLException {
        Validation.requireNonEmpty(roleName, "Role name");
        return userRoleDAO.getUserRoleByName(roleName);
    }

    @Override
    public List<UserRole> getAllUserRoles() throws SQLException {
        return userRoleDAO.getAllUserRoles();
    }

    @Override
    public boolean updateUserRole(UserRole role) throws SQLException {
        Objects.requireNonNull(role, "Role cannot be null");
        Validation.requireNonEmpty(role.getName(), "Role name");

        if (!((UserRoleDAO) userRoleDAO).roleExists(role.getRoleId())) {
            throw new IllegalArgumentException("Role does not exist with id: " + role.getRoleId());
        }

        return userRoleDAO.updateUserRole(role);
    }

    @Override
    public boolean deleteUserRole(int roleId) throws SQLException {
        if (roleId <= 0)
            throw new IllegalArgumentException("Invalid role ID");

        if (!((UserRoleDAO) userRoleDAO).roleExists(roleId)) {
            throw new IllegalArgumentException("Role not found with id: " + roleId);
        }

        return userRoleDAO.deleteUserRole(roleId);
    }

}
