package src.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import src.interfaces.IAppUser;
import src.model.pojo.AppUser;
import src.utils.DBConfig;

public class AppUserDAO implements IAppUser {

    @Override
    public int createUser(AppUser user, int roleId) throws SQLException {
        String sql = "INSERT INTO app_user (name, email, password, role_id, phone, address) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (!roleExists(conn, roleId)) throw new SQLException("Invalid role_id: " + roleId);

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setInt(4, roleId);
            pstmt.setLong(5, user.getPhone());
            pstmt.setString(6, user.getAddress());

            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
    }

    @Override
    public int createUser(AppUser user, String roleName) throws SQLException {
        String sql = "SELECT role_id FROM userrole WHERE LOWER(name) = LOWER(?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, roleName);
            try (ResultSet rs = pst.executeQuery()) {
                if (!rs.next()) throw new SQLException("Role '" + roleName + "' not found.");
                int roleId = rs.getInt("role_id");
                return createUser(user, roleId);
            }
        }
    }

    @Override
    public AppUser getUserById(int userId) throws SQLException {
        String sql = """
            SELECT u.user_id, u.name, u.email, u.password, u.phone, u.address, 
                   u.role_id, r.name AS role_name
            FROM app_user u 
            JOIN userrole r ON u.role_id = r.role_id 
            WHERE u.user_id = ?
            """;
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, userId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    @Override
    public AppUser getUserByEmail(String email) throws SQLException {
        String sql = """
            SELECT u.user_id, u.name, u.email, u.password, u.phone, u.address, 
                   u.role_id, r.name AS role_name
            FROM app_user u 
            JOIN userrole r ON u.role_id = r.role_id 
            WHERE LOWER(u.email) = LOWER(?)
            """;
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    @Override
    public AppUser getUserByEmailAndPassword(String email, String password) throws SQLException {
        String sql = """
            SELECT u.user_id, u.name, u.email, u.password, u.phone, u.address, 
                   u.role_id, r.name AS role_name
            FROM app_user u 
            JOIN userrole r ON u.role_id = r.role_id 
            WHERE LOWER(u.email) = LOWER(?) AND u.password = ?
            """;
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, email);
            pst.setString(2, password);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    @Override
    public boolean updateUser(AppUser user) throws SQLException {
        String sql = """
            UPDATE app_user 
            SET name = ?, email = ?, password = ?, role_id = ?, phone = ?, address = ? 
            WHERE user_id = ?
            """;
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, user.getName());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getPassword());
            pst.setInt(4, user.getRoleId());
            pst.setLong(5, user.getPhone());
            pst.setString(6, user.getAddress());
            pst.setInt(7, user.getUserId());
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM app_user WHERE user_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, userId);
            return pst.executeUpdate() > 0;
        }
    }

    // --- Helpers ---
    public boolean userExistsByEmail(String email) throws SQLException {
        String sql = "SELECT 1 FROM app_user WHERE LOWER(email) = LOWER(?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }

    public List<AppUser> getAllUsers() throws SQLException {
        String sql = """
            SELECT u.user_id, u.name, u.email, u.password, u.phone, u.address, 
                   u.role_id, r.name AS role_name
            FROM app_user u 
            JOIN userrole r ON u.role_id = r.role_id
            ORDER BY u.user_id
            """;
        List<AppUser> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    private boolean roleExists(Connection conn, int roleId) throws SQLException {
        String sql = "SELECT 1 FROM userrole WHERE role_id = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, roleId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }

    private AppUser map(ResultSet rs) throws SQLException {
        AppUser u = new AppUser();
        u.setUserId(rs.getInt("user_id"));
        u.setName(rs.getString("name"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setPhone(rs.getLong("phone"));
        u.setAddress(rs.getString("address"));
        u.setRoleId(rs.getInt("role_id"));
        return u;
    }
}
