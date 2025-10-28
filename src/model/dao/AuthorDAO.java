package src.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import src.interfaces.IAuthor;
import src.model.pojo.Author;
import src.utils.DBConfig;

public class AuthorDAO implements IAuthor {

    @Override
    public int createAuthor(Author author) throws SQLException {
        String sql = "INSERT INTO author (name, bio) VALUES (?, ?)";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, author.getName());
            pst.setString(2, author.getBio());
            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next())
                    return rs.getInt(1);
                throw new SQLException("Creating author failed, no ID obtained.");
            }
        }
    }

    @Override
    public Author getAuthorById(int authorId) throws SQLException {
        String sql = "SELECT author_id, name, bio FROM author WHERE author_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, authorId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next())
                    return map(rs);
            }
        }
        return null;
    }

    @Override
    public List<Author> getAllAuthors() throws SQLException {
        String sql = "SELECT author_id, name, bio FROM author ORDER BY author_id";
        List<Author> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next())
                list.add(map(rs));
        }
        return list;
    }

    @Override
    public Author getAuthorByName(String name) throws SQLException {
        String sql = "SELECT author_id, name, bio FROM author WHERE LOWER(name) = LOWER(?)";
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

    @Override
    public List<Author> searchAuthorsByName(String term) throws SQLException {
        String sql = "SELECT author_id, name, bio FROM author WHERE LOWER(name) LIKE LOWER(?)";
        List<Author> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + term + "%");
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next())
                    list.add(map(rs));
            }
        }
        return list;
    }

    @Override
    public boolean updateAuthor(Author author) throws SQLException {
        String sql = "UPDATE author SET name = ?, bio = ? WHERE author_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, author.getName());
            pst.setString(2, author.getBio());
            pst.setInt(3, author.getAuthorId());
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteAuthor(int authorId) throws SQLException {
        String sql = "DELETE FROM author WHERE author_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, authorId);
            return pst.executeUpdate() > 0;
        }
    }

    // --- Helpers ---
    public boolean authorExists(int authorId) throws SQLException {
        String sql = "SELECT 1 FROM author WHERE author_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, authorId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }

    private Author map(ResultSet rs) throws SQLException {
        Author a = new Author();
        a.setAuthorId(rs.getInt("author_id"));
        a.setName(rs.getString("name"));
        a.setBio(rs.getString("bio"));
        return a;
    }
}
