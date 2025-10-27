package src.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import src.model.Author;
import src.util.DBConfig;

public class AuthorDAO {
    // Create a new author
    public int createAuthor(Author author) throws SQLException {
        String sql = "INSERT INTO author (name, bio) VALUES (?, ?)";

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, author.getName());
            pstmt.setString(2, author.getBio());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating author failed, no ID obtained.");
                }
            }
        }
    }

    // Read author by ID
    public Author getAuthorById(int authorId) throws SQLException {
        String sql = "SELECT author_id, name, bio FROM author WHERE author_id = ?";
  
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, authorId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAuthor(rs);
                }
                return null;
            }
        }
    }

    // Get all authors
    public List<Author> getAllAuthors() throws SQLException {
        String sql = "SELECT author_id, name, bio FROM authors";
        List<Author> authors = new ArrayList<>();

        try (Connection conn = DBConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                authors.add(mapResultSetToAuthor(rs));
            }
        }
        return authors;
    }

    // Search authors by name
    public List<Author> searchAuthorsByName(String searchTerm) throws SQLException {
        String sql = "SELECT author_id, name, bio FROM authors WHERE LOWER(name) LIKE LOWER(?)";
        List<Author> authors = new ArrayList<>();

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + searchTerm + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    authors.add(mapResultSetToAuthor(rs));
                }
            }
        }
        return authors;
    }

    // Update author
    public boolean updateAuthor(Author author) throws SQLException {
        String sql = "UPDATE author SET name = ?, bio = ? WHERE author_id = ?";

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, author.getName());
            pstmt.setString(2, author.getBio());
            pstmt.setInt(3, author.getAuthorId());

            return pstmt.executeUpdate() > 0;
        }
    }

    // Delete author
    public boolean deleteAuthor(int authorId) throws SQLException {
        String sql = "DELETE FROM author WHERE author_id = ?";

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, authorId);
            return pstmt.executeUpdate() > 0;
        }
    }

    // Helper method to map ResultSet to Author object
    private Author mapResultSetToAuthor(ResultSet rs) throws SQLException {
        Author author = new Author();
        author.setAuthorId(rs.getInt("author_id"));
        author.setName(rs.getString("name"));
        author.setBio(rs.getString("bio"));
        return author;
    }
}