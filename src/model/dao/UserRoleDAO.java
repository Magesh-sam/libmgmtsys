package src.model.dao;

import src.interfaces.IUserRole;
import src.model.pojo.UserRole;
import src.utils.DBConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDAO implements IUserRole {

    @Override
    public int addUserRole(UserRole role) throws SQLException {
        String sql = "INSERT INTO userrole (name) VALUES (?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, role.getName());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
                throw new SQLException("Creating role failed, no ID obtained.");
            }
        }
    }

    @Override
    public UserRole getUserRoleById(int roleId) throws SQLException {
        String sql = "SELECT role_id, name FROM userrole WHERE role_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, roleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    @Override
    public UserRole getUserRoleByName(String roleName) throws SQLException {
        String sql = "SELECT role_id, name FROM userrole WHERE LOWER(name) = LOWER(?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, roleName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    @Override
    public List<UserRole> getAllUserRoles() throws SQLException {
        String sql = "SELECT role_id, name FROM userrole ORDER BY role_id";
        List<UserRole> roles = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) roles.add(map(rs));
        }
        return roles;
    }

    @Override
    public boolean updateUserRole(UserRole role) throws SQLException {
        String sql = "UPDATE userrole SET name = ? WHERE role_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, role.getName());
            pstmt.setInt(2, role.getRoleId());
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteUserRole(int roleId) throws SQLException {
        String sql = "DELETE FROM userrole WHERE role_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, roleId);
            return pstmt.executeUpdate() > 0;
        }
    }

    // --- Helpers ---
    public boolean roleExists(int roleId) throws SQLException {
        String sql = "SELECT 1 FROM userrole WHERE role_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, roleId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean roleExists(String roleName) throws SQLException {
        String sql = "SELECT 1 FROM userrole WHERE LOWER(name) = LOWER(?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, roleName);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }

    private UserRole map(ResultSet rs) throws SQLException {
        return new UserRole(rs.getInt("role_id"), rs.getString("name"));
    }
}
