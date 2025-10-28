package src.model.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import src.interfaces.IBorrowedBooks;
import src.model.pojo.BorrowedBooks;
import src.utils.DBConfig;

public class BorrowedBooksDAO implements IBorrowedBooks {

    @Override
    public int createBorrowedBook(BorrowedBooks borrowed) throws SQLException {
        String sql = """
                INSERT INTO borrowed_book (member_id, copy_id, borrow_date, due_date, return_date)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, borrowed.getMemberId());
            pstmt.setInt(2, borrowed.getCopyId());
            pstmt.setDate(3, Date.valueOf(borrowed.getBorrowDate()));
            pstmt.setDate(4, Date.valueOf(borrowed.getDueDate()));
            pstmt.setDate(5, borrowed.getReturnDate() != null ? Date.valueOf(borrowed.getReturnDate()) : null);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next())
                    return rs.getInt(1);
                throw new SQLException("Creating borrowed record failed, no ID obtained.");
            }
        }
    }

    @Override
    public BorrowedBooks getBorrowedBookById(int borrowedId) throws SQLException {
        String sql = "SELECT borrowed_id, member_id, copy_id, borrow_date, due_date, return_date FROM borrowed_book WHERE borrowed_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, borrowedId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next())
                    return map(rs);
            }
        }
        return null;
    }

    @Override
    public List<BorrowedBooks> getAllBorrowedBooks() throws SQLException {
        String sql = "SELECT borrowed_id, member_id, copy_id, borrow_date, due_date, return_date FROM borrowed_book ORDER BY borrowed_id";
        List<BorrowedBooks> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next())
                list.add(map(rs));
        }
        return list;
    }

    @Override
    public boolean updateReturnDate(int borrowedId, LocalDate returnDate) throws SQLException {
        String sql = "UPDATE borrowed_book SET return_date = ? WHERE borrowed_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, returnDate != null ? Date.valueOf(returnDate) : null);
            pstmt.setInt(2, borrowedId);
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteBorrowedBook(int borrowedId) throws SQLException {
        String sql = "DELETE FROM borrowed_book WHERE borrowed_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, borrowedId);
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean borrowedBookExists(int borrowedId) throws SQLException {
        String sql = "SELECT 1 FROM borrowed_book WHERE borrowed_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, borrowedId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public List<BorrowedBooks> getBorrowedBooksByMemberId(int memberId) throws SQLException {
        String sql = "SELECT borrowed_id, member_id, copy_id, borrow_date, due_date, return_date FROM borrowed_book WHERE member_id = ?";
        List<BorrowedBooks> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, memberId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next())
                    list.add(map(rs));
            }
        }
        return list;
    }

    @Override
    public List<BorrowedBooks> getOverdueBooks(LocalDate currentDate) throws SQLException {
        String sql = """
                SELECT borrowed_id, member_id, copy_id, borrow_date, due_date, return_date
                FROM borrowed_book
                WHERE due_date < ? AND return_date IS NULL
                """;
        List<BorrowedBooks> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setDate(1, Date.valueOf(currentDate));
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next())
                    list.add(map(rs));
            }
        }
        return list;
    }

    private BorrowedBooks map(ResultSet rs) throws SQLException {
        BorrowedBooks b = new BorrowedBooks();
        b.setBorrowedId(rs.getInt("borrowed_id"));
        b.setMemberId(rs.getInt("member_id"));
        b.setCopyId(rs.getInt("copy_id"));
        b.setBorrowDate(rs.getDate("borrow_date").toLocalDate());
        b.setDueDate(rs.getDate("due_date").toLocalDate());
        Date ret = rs.getDate("return_date");
        if (ret != null)
            b.setReturnDate(ret.toLocalDate());
        return b;
    }
}
