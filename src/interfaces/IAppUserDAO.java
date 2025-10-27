package src.interfaces;

import java.sql.SQLException;

import src.model.AppUser;

public interface IAppUserDAO {
    int createUser(AppUser user) throws SQLException;

    AppUser getUserByEmailAndPassword(String email, String password) throws SQLException;

    AppUser getUserByEmail(String email) throws SQLException;

    AppUser getUserById(int userId) throws SQLException;

    boolean updateUser(AppUser user) throws SQLException;

    boolean deleteUser(int userId) throws SQLException;

}
