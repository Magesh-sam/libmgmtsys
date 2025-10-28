package src.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import src.interfaces.IBookAuthor;
import src.model.pojo.BookAuthor;
import src.utils.DBConfig;

public class BookAuthorDAO implements IBookAuthor {

    @Override
    public void addBookAuthor(BookAuthor ba) throws SQLException {
        String sql = "INSERT INTO book_author (book_id, author_id) VALUES (?, ?)";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, ba.getBookId());
            pst.setInt(2, ba.getAuthorId());
            pst.executeUpdate();
        }
    }

    @Override
    public List<BookAuthor> getAuthorsByBookId(int bookId) throws SQLException {
        String sql = """
                SELECT ba.book_id, ba.author_id, a.name
                FROM book_author ba
                JOIN author a ON ba.author_id = a.author_id
                WHERE ba.book_id = ?
                """;
        List<BookAuthor> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, bookId);
            try (ResultSet rs = pst.executeQuery()) {
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
        String sql = """
                SELECT ba.book_id, ba.author_id, b.title
                FROM book_author ba
                JOIN book b ON ba.book_id = b.book_id
                WHERE ba.author_id = ?
                """;
        List<BookAuthor> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, authorId);
            try (ResultSet rs = pst.executeQuery()) {
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
    public boolean updateBookAuthor(int oldBookId, int oldAuthorId, int newBookId, int newAuthorId)
            throws SQLException {
        String sql = "UPDATE book_author SET book_id = ?, author_id = ? WHERE book_id = ? AND author_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, newBookId);
            pst.setInt(2, newAuthorId);
            pst.setInt(3, oldBookId);
            pst.setInt(4, oldAuthorId);
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteBookAuthor(int bookId, int authorId) throws SQLException {
        String sql = "DELETE FROM book_author WHERE book_id = ? AND author_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, bookId);
            pst.setInt(2, authorId);
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public boolean bookAuthorExists(int bookId, int authorId) throws SQLException {
        String sql = "SELECT 1 FROM book_author WHERE book_id = ? AND author_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, bookId);
            pst.setInt(2, authorId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }

    public List<BookAuthor> getAllBookAuthors() throws SQLException {
        String sql = "SELECT book_id, author_id FROM book_author ORDER BY book_id, author_id";
        List<BookAuthor> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                BookAuthor ba = new BookAuthor();
                ba.setBookId(rs.getInt("book_id"));
                ba.setAuthorId(rs.getInt("author_id"));
                list.add(ba);
            }
        }
        return list;
    }
}
