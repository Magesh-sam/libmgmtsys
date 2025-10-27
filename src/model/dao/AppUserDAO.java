package src.model.dao;

import java.sql.*;

import src.interfaces.IAppUser;
import src.model.pojo.AppUser;
import src.utils.DBConfig;

public class AppUserDAO implements IAppUser {

    // Create user (roleId must be present in user.getRoleId() and must exist in
    // userrole table)
    @Override
    public int createUser(AppUser user) throws SQLException {
        String sql = "INSERT INTO app_user (name, email, password, role_id, phone, address) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConfig.getConnection()) {

            // validate role exists
            if (!roleExists(conn, user.getRoleId())) {
                throw new SQLException("Role with id " + user.getRoleId() + " does not exist.");
            }

            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, user.getName());
                pstmt.setString(2, user.getEmail());
                pstmt.setString(3, user.getPassword());
                pstmt.setInt(4, user.getRoleId());
                pstmt.setLong(5, user.getPhone());
                pstmt.setString(6, user.getAddress());

                int affected = pstmt.executeUpdate();
                if (affected == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try (ResultSet gen = pstmt.getGeneratedKeys()) {
                    if (gen.next())
                        return gen.getInt(1);
                }
            }
        }
        return -1;
    }

    // Create user by role name (look up role_id first)
    @Override
    public int createUser(AppUser user, String roleName) throws SQLException {
        String lookup = "SELECT role_id FROM userrole WHERE name = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(lookup)) {

            pst.setString(1, roleName);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    user.setRoleId(rs.getInt("role_id"));
                } else {
                    throw new SQLException("Role with name '" + roleName + "' does not exist.");
                }
            }
        }
        return createUser(user); // will validate role again and insert
    }

    // helper to check role existence using provided connection
    private boolean roleExists(Connection conn, int roleId) throws SQLException {
        if (roleId <= 0)
            return false;
        String sql = "SELECT 1 FROM userrole WHERE role_id = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, roleId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Get by email + password
    @Override
    public AppUser getUserByEmailAndPassword(String email, String password) throws SQLException {
        String sql = """
                SELECT u.user_id, u.name, u.email, u.password, u.phone, u.address, u.role_id,
                       r.name AS role_name
                FROM app_user u
                JOIN userrole r ON u.role_id = r.role_id
                WHERE u.email = ? AND u.password = ?
                """;

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

    // Get by email only
    @Override
    public AppUser getUserByEmail(String email) throws SQLException {
        String sql = """
                SELECT u.user_id, u.name, u.email, u.password, u.phone, u.address, u.role_id,
                       r.name AS role_name
                FROM app_user u
                JOIN userrole r ON u.role_id = r.role_id
                WHERE u.email = ?
                """;
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

    // Get by ID
    @Override
    public AppUser getUserById(int userId) throws SQLException {
        String sql = """
                SELECT u.user_id, u.name, u.email, u.password, u.phone, u.address, u.role_id,
                       r.name AS role_name
                FROM app_user u
                JOIN userrole r ON u.role_id = r.role_id
                WHERE u.user_id = ?
                """;
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

    // Update user
    @Override
    public boolean updateUser(AppUser user) throws SQLException {
        String sql = "UPDATE app_user SET name = ?, email = ?, password = ?, role_id = ?, phone = ?, address = ? WHERE user_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setInt(4, user.getRoleId());
            pstmt.setLong(5, user.getPhone());
            pstmt.setString(6, user.getAddress());
            pstmt.setInt(7, user.getUserId());
            return pstmt.executeUpdate() > 0;
        }
    }

    // Delete user
    @Override
    public boolean deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM app_user WHERE user_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            return pstmt.executeUpdate() > 0;
        }
    }

    // Map rs → AppUser
    private AppUser mapResultSetToAppUser(ResultSet rs) throws SQLException {
        AppUser u = new AppUser();
        u.setUserId(rs.getInt("user_id"));
        u.setName(rs.getString("name"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setPhone(rs.getLong("phone"));
        u.setAddress(rs.getString("address"));
        u.setRoleId(rs.getInt("role_id")); // ensure role_id is selected in queries
        return u;
    }
}
