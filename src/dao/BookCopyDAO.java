package src.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import src.interfaces.IBookCopyDAO;
import src.model.BookCopy;
import src.model.BookCopy.BookStatus;
import src.util.DBConfig;

public class BookCopyDAO implements IBookCopyDAO {
    // Create a new book copy
    @Override
    public int createBookCopy(BookCopy bookCopy) throws SQLException {
        String sql = "INSERT INTO book_copy (book_id, status) VALUES (?, ?)";

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, bookCopy.getBookId());
            pstmt.setString(2, bookCopy.getStatus().toString());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating book copy failed, no ID obtained.");
                }
            }
        }
    }

    // Read a book copy by ID
    @Override
    public BookCopy getBookCopyById(int copyId) throws SQLException {
        String sql = "SELECT copy_id, book_id, status FROM book_copy WHERE copy_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, copyId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBookCopy(rs);
                }
                return null;
            }
        }
    }

    // Get all copies of a specific book
    @Override
    public List<BookCopy> getBookCopiesByBookId(int bookId) throws SQLException {
        String sql = "SELECT copy_id, book_id, status FROM book_copy WHERE book_id = ?";
        List<BookCopy> copies = new ArrayList<>();

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    copies.add(mapResultSetToBookCopy(rs));
                }
            }
        }
        return copies;
    }

    // Get available copies of a book
    @Override
    public List<BookCopy> getAvailableCopiesByBookId(int bookId) throws SQLException {
        String sql = "SELECT copy_id, book_id, status FROM book_copy WHERE book_id = ? AND status = ?";
        List<BookCopy> copies = new ArrayList<>();

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookId);
            pstmt.setString(2, BookStatus.available.toString());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    copies.add(mapResultSetToBookCopy(rs));
                }
            }
        }
        return copies;
    }

    // Update book copy status
    @Override
    public boolean updateBookCopyStatus(int copyId, BookStatus status) throws SQLException {
        String sql = "UPDATE book_copy SET status = ? WHERE copy_id = ?";

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status.toString());
            pstmt.setInt(2, copyId);

            return pstmt.executeUpdate() > 0;
        }
    }

    // Delete a book copy
    @Override
    public boolean deleteBookCopy(int copyId) throws SQLException {
        String sql = "DELETE FROM book_copy WHERE copy_id = ?";

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, copyId);
            return pstmt.executeUpdate() > 0;
        }
    }

    // Get total number of copies for a book
    @Override
    public int getTotalCopiesCount(int bookId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM book_copy WHERE book_id = ?";

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        }
    }

    // Get count of available copies for a book
    @Override
    public int getAvailableCopiesCount(int bookId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM book_copy WHERE book_id = ? AND status = ?";

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookId);
            pstmt.setString(2, BookStatus.available.toString());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        }
    }

    // Helper method to map ResultSet to BookCopy object
    private BookCopy mapResultSetToBookCopy(ResultSet rs) throws SQLException {
        BookCopy copy = new BookCopy();
        copy.setCopyId(rs.getInt("copy_id"));
        copy.setBookId(rs.getInt("book_id"));
        copy.setStatus(BookStatus.valueOf(rs.getString("status")));
        return copy;
    }
}