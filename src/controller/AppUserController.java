package src.controller;

import java.sql.SQLException;
import java.util.List;

import src.interfaces.IAppUser;
import src.model.pojo.AppUser;
import src.service.AppUserService;

public class AppUserController implements IAppUser {
    private final AppUserService appUserService;

    public AppUserController() {
        this.appUserService = new AppUserService();
    }

    @Override
    public int createUser(AppUser user, int roleId) throws SQLException {
        return appUserService.createUser(user, roleId);
    }

    @Override
    public int createUser(AppUser user, String roleName) throws SQLException {
        return appUserService.createUser(user, roleName);
    }

    @Override
    public AppUser getUserByEmailAndPassword(String email, String password) throws SQLException {
        return appUserService.getUserByEmailAndPassword(email, password);
    }

    @Override
    public AppUser getUserByEmail(String email) throws SQLException {
        return appUserService.getUserByEmail(email);
    }

    @Override
    public AppUser getUserById(int userId) throws SQLException {
        return appUserService.getUserById(userId);
    }

    @Override
    public boolean updateUser(AppUser user) throws SQLException {
        return appUserService.updateUser(user);
    }

    @Override
    public boolean deleteUser(int userId) throws SQLException {
        return appUserService.deleteUser(userId);
    }

    @Override
    public List<AppUser> getAllUsers() throws SQLException {
        return appUserService.getAllUsers();
    }
}
