package src.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import src.interfaces.IBookCopy;
import src.model.pojo.BookCopy;
import src.model.pojo.BookCopy.BookStatus;
import src.utils.DBConfig;

public class BookCopyDAO implements IBookCopy {

    @Override
    public int createBookCopy(BookCopy copy) throws SQLException {
        String sql = "INSERT INTO book_copy (book_id, status) VALUES (?, ?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, copy.getBookId());
            pst.setString(2, copy.getStatus().name());
            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
                throw new SQLException("Creating book copy failed, no ID obtained.");
            }
        }
    }

    @Override
    public BookCopy getBookCopyById(int copyId) throws SQLException {
        String sql = "SELECT copy_id, book_id, status FROM book_copy WHERE copy_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, copyId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    @Override
    public List<BookCopy> getBookCopiesByBookId(int bookId) throws SQLException {
        String sql = "SELECT copy_id, book_id, status FROM book_copy WHERE book_id = ?";
        List<BookCopy> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, bookId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    @Override
    public List<BookCopy> getAvailableCopiesByBookId(int bookId) throws SQLException {
        String sql = "SELECT copy_id, book_id, status FROM book_copy WHERE book_id = ? AND status = 'available'";
        List<BookCopy> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, bookId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    @Override
    public boolean updateBookCopyStatus(int copyId, BookStatus status) throws SQLException {
        String sql = "UPDATE book_copy SET status = ? WHERE copy_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, status.name());
            pst.setInt(2, copyId);
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteBookCopy(int copyId) throws SQLException {
        String sql = "DELETE FROM book_copy WHERE copy_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, copyId);
            return pst.executeUpdate() > 0;
        }
    }

    // --- Helpers ---
    public boolean copyExists(int copyId) throws SQLException {
        String sql = "SELECT 1 FROM book_copy WHERE copy_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, copyId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }

    public int getTotalCopiesCount(int bookId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM book_copy WHERE book_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, bookId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    public int getAvailableCopiesCount(int bookId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM book_copy WHERE book_id = ? AND status = 'available'";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, bookId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    private BookCopy map(ResultSet rs) throws SQLException {
        BookCopy copy = new BookCopy();
        copy.setCopyId(rs.getInt("copy_id"));
        copy.setBookId(rs.getInt("book_id"));
        copy.setStatus(BookStatus.valueOf(rs.getString("status")));
        return copy;
    }
}
