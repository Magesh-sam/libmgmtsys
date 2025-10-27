package src.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

import src.model.dao.AppUserDAO;
import src.model.dao.LibrarianDAO;
import src.model.dao.MemberDAO;
import src.model.dao.UserRoleDAO;
import src.model.pojo.AppUser;
import src.model.pojo.Librarian;
import src.model.pojo.Member;
import src.model.pojo.UserRole;
import src.utils.Validation;

public class AppUserService {
    private final AppUserDAO appUserDAO;
    private final UserRoleDAO roleDAO;

    public AppUserService() {
        this.appUserDAO = new AppUserDAO();
        this.roleDAO = new UserRoleDAO();
    }

    public int registerUser(AppUser user, int roleId) throws SQLException {
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
        int userId = appUserDAO.createUser(user);

        if (userId > 0) {
            String roleName = role.getName();
            if ("member".equalsIgnoreCase(roleName)) {
                createMember(userId);
            } else if ("librarian".equalsIgnoreCase(roleName)) {
                createLibrarian(userId);
            }
        }

        return userId;
    }

    public int registerUser(AppUser user, String roleName) throws SQLException {
        Objects.requireNonNull(roleName, "Role name cannot be null");
        UserRole role = roleDAO.getUserRoleByName(roleName);
        if (role == null) {
            throw new IllegalArgumentException("Role with name '" + roleName + "' does not exist.");
        }
        return registerUser(user, role.getRoleId());
    }

    // user login with email and password
    public AppUser loginUser(String email, String password) throws SQLException {
        Validation.requireNonEmpty(email, "Email");
        Validation.requireNonEmpty(password, "Password");
        Validation.requireMinLength(password, 8, "Password");
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

    // private helper methods

    private void validateUser(AppUser user) throws SQLException {
        Validation.requireNonEmpty(user.getName(), "Name");
        Validation.requireNonEmpty(user.getEmail(), "Email");
        if (!Validation.isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        Validation.requireNonEmpty(user.getPassword(), "Password");
        Validation.requireMinLength(user.getPassword(), 8, "Password");
        if (user.getPhone() > 0 && !Validation.isValidMobileNumber(String.valueOf(user.getPhone()))) {
            throw new IllegalArgumentException("Phone number must be positive.");
        }
        if (user.getRoleId() <= 0) {
            throw new IllegalArgumentException("Role ID must be positive.");
        }

    }

    private void createLibrarian(int userId) throws SQLException {
        Librarian l = new Librarian();
        l.setLibrarianId(userId);
        l.setHireDate(LocalDate.now());
        LibrarianDAO librarianDAO = new LibrarianDAO();
        librarianDAO.createLibrarian(l);
    }

    private void createMember(int userId) throws SQLException {
        Member m = new Member();
        m.setMemberId(userId);
        m.setJoinDate(LocalDate.now());
        MemberDAO memberDAO = new MemberDAO();
        memberDAO.createMember(m);
    }

}
