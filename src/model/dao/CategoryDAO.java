package src.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import src.interfaces.ICategory;
import src.model.pojo.Category;
import src.utils.DBConfig;

public class CategoryDAO implements ICategory {

    @Override
    public int createCategory(Category category) throws SQLException {
        String sql = "INSERT INTO category (name, description) VALUES (?, ?)";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, category.getName());
            pstmt.setString(2, category.getDescription());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next())
                    return rs.getInt(1);
                throw new SQLException("Creating category failed, no ID obtained.");
            }
        }
    }

    @Override
    public Category getCategoryById(int categoryId) throws SQLException {
        String sql = "SELECT category_id, name, description FROM category WHERE category_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, categoryId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next())
                    return map(rs);
            }
        }
        return null;
    }

    @Override
    public List<Category> getAllCategories() throws SQLException {
        String sql = "SELECT category_id, name, description FROM category ORDER BY category_id";
        List<Category> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next())
                list.add(map(rs));
        }
        return list;
    }

    @Override
    public boolean updateCategory(Category category) throws SQLException {
        String sql = "UPDATE category SET name = ?, description = ? WHERE category_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category.getName());
            pstmt.setString(2, category.getDescription());
            pstmt.setInt(3, category.getCategoryId());
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteCategory(int categoryId) throws SQLException {
        String sql = "DELETE FROM category WHERE category_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, categoryId);
            return pstmt.executeUpdate() > 0;
        }
    }

    // --- Added Functional Methods ---
    public Category getCategoryByName(String name) throws SQLException {
        String sql = "SELECT category_id, name, description FROM category WHERE LOWER(name) = LOWER(?)";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, name);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next())
                    return map(rs);
            }
        }
        return null;
    }

    public boolean categoryExists(String name) throws SQLException {
        String sql = "SELECT 1 FROM category WHERE LOWER(name) = LOWER(?)";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, name);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }

    private Category map(ResultSet rs) throws SQLException {
        Category c = new Category();
        c.setCategoryId(rs.getInt("category_id"));
        c.setName(rs.getString("name"));
        c.setDescription(rs.getString("description"));
        return c;
    }
}
