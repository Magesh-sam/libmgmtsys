package src.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import src.model.BorrowedBooks;
import src.util.DBConfig;

public class BorrowedBooksDAO {
    public int createBorrowedBook(BorrowedBooks borrowed) throws SQLException {
        String sql = "INSERT INTO borrowed_book (member_id, copy_id, borrow_date, due_date, return_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, borrowed.getMemberId());
            pstmt.setInt(2, borrowed.getCopyId());
            pstmt.setDate(3, Date.valueOf(borrowed.getBorrowDate()));
            pstmt.setDate(4, Date.valueOf(borrowed.getDueDate()));
            pstmt.setDate(5, borrowed.getReturnDate() != null ? Date.valueOf(borrowed.getReturnDate()) : null);
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating borrowed book failed, no ID obtained.");
                }
            }
        }
    }

    public BorrowedBooks getBorrowedBookById(int borrowedId) throws SQLException {
        String sql = "SELECT borrowed_id, member_id, copy_id, borrow_date, due_date, return_date FROM borrowed_book WHERE borrowed_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, borrowedId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBorrowedBooks(rs);
                }
            }
        }
        return null;
    }

    public List<BorrowedBooks> getAllBorrowedBooks() throws SQLException {
        String sql = "SELECT borrowed_id, member_id, copy_id, borrow_date, due_date, return_date FROM borrowed_book";
        List<BorrowedBooks> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToBorrowedBooks(rs));
            }
        }
        return list;
    }

    public boolean updateReturnDate(int borrowedId, LocalDate returDate) throws SQLException {
        String sql = "UPDATE borrowed_book SET return_date = ? WHERE borrowed_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, returDate != null ? Date.valueOf(returDate) : null);
            pstmt.setInt(2, borrowedId);
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteBorrowedBook(int borrowedId) throws SQLException {
        String sql = "DELETE FROM borrowed_books WHERE borrowed_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, borrowedId);
            return pstmt.executeUpdate() > 0;
        }
    }

    private BorrowedBooks mapResultSetToBorrowedBooks(ResultSet rs) throws SQLException {
        BorrowedBooks b = new BorrowedBooks();
        b.setborrowedId(rs.getInt("borrowed_id"));
        b.setMemberId(rs.getInt("member_id"));
        b.setCopyId(rs.getInt("copy_id"));
        b.setBorrowDate(rs.getDate("borrow_date").toLocalDate());
        b.setDueDate(rs.getDate("due_date").toLocalDate());
        if (rs.getDate("return_date") != null) {
            b.setReturnDate(rs.getDate("return_date").toLocalDate());
        }
        return b;
    }
}