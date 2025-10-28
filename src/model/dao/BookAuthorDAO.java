package src.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import src.interfaces.IBookAuthor;
import src.model.pojo.BookAuthor;
import src.utils.DBConfig;

public class BookAuthorDAO implements IBookAuthor {
    @Override
    public void addBookAuthor(BookAuthor bookAuthor) throws SQLException {
        String sql = "INSERT INTO book_author (book_id, author_id) VALUES (?, ?)";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookAuthor.getBookId());
            pstmt.setInt(2, bookAuthor.getAuthorId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<BookAuthor> getAuthorsByBookId(int bookId) throws SQLException {
        String sql = "SELECT book_id, author_id, a.name author FROM book_author  ba join author a on ba.author_id = a.author_id WHERE book_id = ?";
        List<BookAuthor> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    BookAuthor ba = new BookAuthor();
                    ba.setBookId(rs.getInt("book_id"));
                    ba.setAuthorId(rs.getInt("author_id"));
                    list.add(ba);
                }
            }
        }
        return list;
    }

    @Override
    public List<BookAuthor> getBooksByAuthorId(int authorId) throws SQLException {
        String sql = "SELECT book_id, author_id, b.title title FROM book_authors join book b on ba.book_id = b.book_id WHERE author_id = ?";
        List<BookAuthor> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, authorId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    BookAuthor ba = new BookAuthor();
                    ba.setBookId(rs.getInt("book_id"));
                    ba.setAuthorId(rs.getInt("author_id"));
                    list.add(ba);
                }
            }
        }
        return list;
    }

    @Override
    public boolean updateBookByAuthor(int bookId, int authorId) throws SQLException {
        String sql = "UPDATE book_author SET book_id = ? WHERE author_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            pstmt.setInt(2, authorId);
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateAuthorByBook(int authorId, int bookId) throws SQLException {
        String sql = "UPDATE book_author SET author_id = ? WHERE book_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, authorId);
            pstmt.setInt(2, bookId);
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteBookAuthor(int bookId, int authorId) throws SQLException {
        String sql = "DELETE FROM book_author WHERE book_id = ? AND author_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            pstmt.setInt(2, authorId);
            return pstmt.executeUpdate() > 0;
        }
    }
}