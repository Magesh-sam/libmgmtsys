package src.controller;

import java.sql.SQLException;
import java.util.List;

import src.exceptions.InvalidEmailException;
import src.interfaces.IAppUser;
import src.model.pojo.AppUser;
import src.service.AppUserService;

public class AppUserController implements IAppUser {
    private final AppUserService appUserService;

    public AppUserController() {
        this.appUserService = new AppUserService();
    }

    @Override
    public int createUser(AppUser user, int roleId) {
        try {

            return appUserService.createUser(user, roleId);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error creating user: " + e.getMessage());
            e.printStackTrace();
            return -1;
        } catch (InvalidEmailException e) {
            System.out.println("Invalid email: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }

    }

    @Override
    public int createUser(AppUser user, String roleName) {
        try {

            return appUserService.createUser(user, roleName);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error creating user: " + e.getMessage());
            e.printStackTrace();
            return -1;
        } catch (InvalidEmailException e) {
            System.out.println("Invalid email: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public AppUser getUserByEmailAndPassword(String email, String password) {
        try {

            return appUserService.getUserByEmailAndPassword(email, password);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error fetching user: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (InvalidEmailException e) {
            System.out.println("Invalid email: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public AppUser getUserByEmail(String email) {
        try {

            return appUserService.getUserByEmail(email);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error fetching user: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (InvalidEmailException e) {
            System.out.println("Invalid email: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public AppUser getUserById(int userId) {
        try {

            return appUserService.getUserById(userId);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error fetching user: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateUser(AppUser user) {
        try {

            return appUserService.updateUser(user);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error updating user: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (InvalidEmailException e) {
            System.out.println("Invalid email: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUser(int userId) {
        try {

            return appUserService.deleteUser(userId);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error deleting user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<AppUser> getAllUsers() {
        try {

            return appUserService.getAllUsers();
        } catch (SQLException e) {
            System.out.println("Error fetching users: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
