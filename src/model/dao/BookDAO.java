package src.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import src.interfaces.IBook;
import src.model.pojo.Book;
import src.utils.DBConfig;

public class BookDAO implements IBook {

    @Override
    public int createBook(Book book) throws SQLException {
        String sql = "INSERT INTO book (title, category_id, publisher_id, language) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setInt(2, book.getCategoryId());
            pstmt.setInt(3, book.getPublisherId());
            pstmt.setString(4, book.getLanguage());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next())
                    return rs.getInt(1);
                throw new SQLException("Creating book failed, no ID obtained.");
            }
        }
    }

    @Override
    public Book getBookById(int bookId) throws SQLException {
        String sql = "SELECT book_id, title, category_id, publisher_id, language FROM book WHERE book_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, bookId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next())
                    return map(rs);
            }
        }
        return null;
    }

    @Override
    public List<Book> getAllBooks() throws SQLException {
        String sql = "SELECT book_id, title, category_id, publisher_id, language FROM book ORDER BY book_id";
        List<Book> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next())
                list.add(map(rs));
        }
        return list;
    }

    @Override
    public Book getBookByTitle(String title) throws SQLException {
        String sql = "SELECT book_id, title, category_id, publisher_id, language FROM book WHERE LOWER(title) = LOWER(?)";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, title);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next())
                    return map(rs);
            }
        }
        return null;
    }

    @Override
    public List<Book> getBooksByTitle(String searchTerm) throws SQLException {
        String sql = "SELECT book_id, title, category_id, publisher_id, language FROM book WHERE LOWER(title) LIKE LOWER(?)";
        List<Book> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + searchTerm + "%");
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next())
                    list.add(map(rs));
            }
        }
        return list;
    }

    @Override
    public List<Book> getBooksByAuthorName(String authorName) throws SQLException {
        String sql = """
                SELECT DISTINCT b.book_id, b.title, b.category_id, b.publisher_id, b.language
                FROM book b
                JOIN book_author ba ON b.book_id = ba.book_id
                JOIN author a ON ba.author_id = a.author_id
                WHERE LOWER(a.name) LIKE LOWER(?)
                """;
        List<Book> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + authorName + "%");
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next())
                    list.add(map(rs));
            }
        }
        return list;
    }

    @Override
    public List<Book> getBooksByCategory(int categoryId) throws SQLException {
        String sql = "SELECT book_id, title, category_id, publisher_id, language FROM book WHERE category_id = ?";
        List<Book> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, categoryId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next())
                    list.add(map(rs));
            }
        }
        return list;
    }

    @Override
    public List<Book> getBooksByCategory(String category) throws SQLException {
        String sql = """
                SELECT b.book_id, b.title, b.category_id, b.publisher_id, b.language
                FROM book b
                JOIN category c ON b.category_id = c.category_id
                WHERE LOWER(c.name) LIKE LOWER(?)
                """;
        List<Book> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + category + "%");
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next())
                    list.add(map(rs));
            }
        }
        return list;
    }

    @Override
    public List<Book> getBooksByPublisher(int publisherId) throws SQLException {
        String sql = "SELECT book_id, title, category_id, publisher_id, language FROM book WHERE publisher_id = ?";
        List<Book> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, publisherId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next())
                    list.add(map(rs));
            }
        }
        return list;
    }

    @Override
    public List<Book> getBooksByPublisher(String publisher) throws SQLException {
        String sql = """
                SELECT b.book_id, b.title, b.category_id, b.publisher_id, b.language
                FROM book b
                JOIN publisher p ON b.publisher_id = p.publisher_id
                WHERE LOWER(p.name) LIKE LOWER(?)
                """;
        List<Book> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + publisher + "%");
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next())
                    list.add(map(rs));
            }
        }
        return list;
    }

    @Override
    public boolean updateBook(Book book) throws SQLException {
        String sql = "UPDATE book SET title = ?, category_id = ?, publisher_id = ?, language = ? WHERE book_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, book.getTitle());
            pst.setInt(2, book.getCategoryId());
            pst.setInt(3, book.getPublisherId());
            pst.setString(4, book.getLanguage());
            pst.setInt(5, book.getBookId());
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteBook(int bookId) throws SQLException {
        String sql = "DELETE FROM book WHERE book_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, bookId);
            return pst.executeUpdate() > 0;
        }
    }

    // --- Helpers ---
    public boolean bookExists(int bookId) throws SQLException {
        String sql = "SELECT 1 FROM book WHERE book_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, bookId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }

    private Book map(ResultSet rs) throws SQLException {
        Book b = new Book();
        b.setBookId(rs.getInt("book_id"));
        b.setTitle(rs.getString("title"));
        b.setCategoryId(rs.getInt("category_id"));
        b.setPublisherId(rs.getInt("publisher_id"));
        b.setLanguage(rs.getString("language"));
        return b;
    }

}
