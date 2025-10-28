package src.service;

import java.sql.SQLException;
import java.util.Objects;

import src.interfaces.IAppUser;
import src.interfaces.IUserRole;
import src.model.dao.AppUserDAO;
import src.model.dao.UserRoleDAO;
import src.model.pojo.AppUser;
import src.model.pojo.UserRole;
import src.utils.Validation;

public class AppUserService implements IAppUser {
    private final IAppUser appUserDAO;
    private final IUserRole roleDAO;

    public AppUserService() {
        this.appUserDAO = new AppUserDAO();
        this.roleDAO = new UserRoleDAO();
    }

    public int createUser(AppUser user, int roleId) throws SQLException {
        Objects.requireNonNull(user, "User cannot be null");

        validateUser(user);

        AppUser existingAppUser = appUserDAO.getUserByEmail(user.getEmail());
        if (existingAppUser != null) {
            throw new IllegalArgumentException("Email already registered");
        }

        UserRole role = roleDAO.getUserRoleById(roleId);
        if (role == null) {
            throw new IllegalArgumentException("Role with id " + roleId + " does not exist.");
        }

        user.setRoleId(roleId);
        int userId = appUserDAO.createUser(user, roleId);

        return userId;
    }

    public int createUser(AppUser user, String roleName) throws SQLException {
        Objects.requireNonNull(roleName, "Role name cannot be null");
        UserRole role = roleDAO.getUserRoleByName(roleName);
        if (role == null) {
            throw new IllegalArgumentException("Role with name '" + roleName + "' does not exist.");
        }
        return createUser(user, role.getRoleId());
    }

    // user login with email and password
    public AppUser loginUser(String email, String password) throws SQLException {
       Validation.validateEmail(email);
       Validation.validatePassword(password);
        return appUserDAO.getUserByEmailAndPassword(email, password);
    }

    // update user
    public boolean updateUser(AppUser user) throws SQLException {
        Objects.requireNonNull(user, "User cannot be null");
        validateUser(user);

        AppUser existingAppUser = appUserDAO.getUserByEmail(user.getEmail());
        // if email belongs to another user -> conflict
        if (existingAppUser != null && existingAppUser.getUserId() != user.getUserId()) {
            throw new IllegalArgumentException("Email already registered by another user.");
        }

        // if roleId is provided, validate it exists
        if (user.getRoleId() > 0) {
            UserRole role = roleDAO.getUserRoleById(user.getRoleId());
            if (role == null) {
                throw new IllegalArgumentException("Role with id " + user.getRoleId() + " does not exist.");
            }
        }

        return appUserDAO.updateUser(user);
    }

    // delete user
    public boolean deleteUser(int userId) throws SQLException {
        AppUser existingAppUser = appUserDAO.getUserById(userId);
        if (existingAppUser == null) {
            throw new IllegalArgumentException("User does not exist.");
        }
        return appUserDAO.deleteUser(userId);
    }

    @Override
    public AppUser getUserByEmailAndPassword(String email, String password) throws SQLException {
        Validation.validateEmail(email);
        Validation.validatePassword(password);

        return appUserDAO.getUserByEmailAndPassword(email, password);
    }

    @Override
    public AppUser getUserByEmail(String email) throws SQLException {
        Validation.requireNonEmpty(email, "email");
        if (Validation.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid Email");
        }
        throw new UnsupportedOperationException("Unimplemented method 'getUserByEmail'");
    }

    @Override
    public AppUser getUserById(int userId) throws SQLException {
        if (userId <= 0) {
            throw new IllegalArgumentException("user id cannot be empty or negative");
        }
        return appUserDAO.getUserById(userId);
    }
    // private helper methods

    private void validateUser(AppUser user) {
        Validation.requireNonEmpty(user.getName(), "Name");
        Validation.validateEmail(user.getEmail());
        Validation.validatePassword(user.getPassword());
        if (user.getPhone() > 0 && !Validation.isValidMobileNumber(String.valueOf(user.getPhone()))) {
            throw new IllegalArgumentException("Phone number must be positive.");
        }
        if (user.getRoleId() < 0) {
            throw new IllegalArgumentException("Role ID must be positive.");
        }

    }

}
