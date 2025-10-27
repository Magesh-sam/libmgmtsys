package src.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

import src.model.dao.AppUserDAO;
import src.model.dao.LibrarianDAO;
import src.model.dao.MemberDAO;
import src.model.pojo.AppUser;
import src.model.pojo.Librarian;
import src.model.pojo.Member;
import src.model.pojo.AppUser.UserRole;
import src.utils.Validation;

public class AppUserService {
    private final AppUserDAO appUserDAO;

    public AppUserService(AppUserDAO appUserDAO) {
        this.appUserDAO = new AppUserDAO();

    }

    // register a user
    public int register(AppUser user) throws SQLException, IllegalArgumentException {
        validateUser(user);
        AppUser existingAppUser = appUserDAO.getUserByEmail(user.getEmail());
        if (existingAppUser != null) {
            throw new IllegalArgumentException("Email already registered");
        }
        int userId = appUserDAO.createUser(user);
        if (userId > 0 && user.getRole() == UserRole.member) {
            createMember(userId);
        } else if (userId > 0 && user.getRole() == UserRole.librarian) {
            createLibrarian(userId);

        }
        return userId;
    }

    // user login with email and password
    public AppUser login(String email, String password) throws SQLException {
        Validation.requireNonEmpty(email, "Email");
        Validation.requireNonEmpty(password, "Password");
        Validation.requireMinLength(password, 8, "Password");
        return appUserDAO.getUserByEmailAndPassword(email, password);
    }

    // update user
    public boolean update(AppUser user) throws SQLException {
        validateUser(user);
        AppUser existingAppUser = appUserDAO.getUserByEmail(user.getEmail());
        if (existingAppUser != null) {
            throw new IllegalArgumentException("User does not exist.");
        }
        return appUserDAO.updateUser(user);
    }

    // delete user

    public boolean delete(int userId) throws SQLException {
        AppUser existingAppUser = appUserDAO.getUserById(userId);
        if (existingAppUser == null) {
            throw new IllegalArgumentException("User does not exist.");
        }
        return appUserDAO.deleteUser(userId);

    }

    // private helper methods

    private void validateUser(AppUser user) throws SQLException, IllegalArgumentException {
        Objects.requireNonNull(user, "User cannot be null");
        Validation.requireNonEmpty(user.getName(), "Name");
        Validation.requireNonEmpty(user.getEmail(), "Email");
        Validation.requireNonEmpty(user.getPassword(), "Password");
        Validation.requireMinLength(user.getPassword(), 8, "Password");

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
