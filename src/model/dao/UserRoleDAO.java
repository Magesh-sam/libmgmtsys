package src.model.dao;

import src.model.pojo.UserRole;
import src.utils.DBConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserRoleDAO {

    public int addUserRole(UserRole role) throws SQLException {
        String sql = "INSERT INTO userrole (name) VALUES (?) ";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, role.toString());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating user role failed, no ID obtained.");
                }
            }
        }
    }

    public UserRole getUserRoleById(int roleId) throws SQLException {
        String sql = "SELECT role_id, name FROM userrole WHERE role_id = ?";

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, roleId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUserRole(rs);

                }
                return null;
            }
        }
    }

    public UserRole getUserRoleByName(String roleName) throws SQLException {
        String sql = "SELECT role_id, name FROM userrole WHERE name = ?";

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, roleName);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUserRole(rs);
                }
                return null;
            }
        }
    }

    public List<UserRole> getAllUserRoles() throws SQLException {
        String sql = "SELECT role_id, name FROM userrole";
        List<UserRole> roles = new java.util.ArrayList<>();

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                roles.add(mapResultSetToUserRole(rs));
            }
        }
        return roles;
    }

    public boolean updateUserRole(UserRole role) throws SQLException {
        String sql = "UPDATE userrole SET name = ? WHERE role_id = ?";

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, role.getName());
            pstmt.setInt(2, role.getRoleId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean deleteUserRole(int roleId) throws SQLException {
        String sql = "DELETE FROM userrole WHERE role_id = ?";

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, roleId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    private UserRole mapResultSetToUserRole(ResultSet rs) throws SQLException {
        int roleId = rs.getInt("role_id");
        String name = rs.getString("name");
        return new UserRole(roleId, name);
    }
}
