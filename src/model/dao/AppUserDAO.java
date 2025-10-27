package src.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import src.interfaces.IAppUserDAO;
import src.model.pojo.AppUser;
import src.model.pojo.AppUser.UserRole;
import src.utils.DBConfig;

public class AppUserDAO implements IAppUserDAO {

    public int createUser(AppUser user) throws SQLException {
        String sql = "INSERT INTO app_user (name, email, password, role, phone, address) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setObject(4, user.getRole() != null ? user.getRole().toString() : UserRole.member.toString(),
                    java.sql.Types.OTHER);
            pstmt.setLong(5, user.getPhone());
            pstmt.setString(6, user.getAddress());
            pstmt.executeUpdate();
            try (ResultSet gen = pstmt.getGeneratedKeys()) {
                if (gen.next())
                    return gen.getInt(1);
            }
        }
        return -1;
    }

    public AppUser getUserByEmailAndPassword(String email, String password) throws SQLException {
        String sql = "SELECT user_id, name, email, password, role, phone, address FROM app_user WHERE email = ? AND password = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next())
                    return mapResultSetToAppUser(rs);
            }
        }
        return null;
    }

    public AppUser getUserByEmail(String email) throws SQLException {
        String sql = "SELECT user_id, name, email, password, role, phone, address FROM app_user WHERE email = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next())
                    return mapResultSetToAppUser(rs);
            }
        }
        return null;
    }

    public AppUser getUserById(int userId) throws SQLException {
        String sql = "SELECT user_id, name, email, password, role, phone, address FROM app_user WHERE user_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next())
                    return mapResultSetToAppUser(rs);
            }
        }
        return null;
    }

    public boolean updateUser(AppUser user) throws SQLException {
        String sql = "UPDATE app_user SET name = ?, email = ?, password = ?, role = ?, phone = ?, address = ? WHERE user_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setObject(4, user.getRole() != null ? user.getRole().toString() : UserRole.member.toString(),
                    java.sql.Types.OTHER);
            pstmt.setLong(5, user.getPhone());
            pstmt.setString(6, user.getAddress());
            pstmt.setInt(7, user.getUserId());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM app_user WHERE user_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            return pstmt.executeUpdate() > 0;
        }
    }

    private AppUser mapResultSetToAppUser(ResultSet rs) throws SQLException {
        AppUser u = new AppUser();
        u.setUserId(rs.getInt("user_id"));
        u.setName(rs.getString("name"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        String role = rs.getString("role");
        if (role != null)
            u.setRole(UserRole.valueOf(role));
        u.setPhone(rs.getLong("phone"));
        u.setAddress(rs.getString("address"));
        return u;
    }

}
