package src.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import src.model.Book;
import src.util.DBConfig;

public class BookDAO {
    // Create a new book
    public int createBook(Book book) throws SQLException {
        String sql = "INSERT INTO book (title, category_id, publisher_id) VALUES (?, ?, ?)";

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setInt(2, book.getCategoryId());
            pstmt.setInt(3, book.getPublisherId());
            // only set values that exist in the database
            pstmt.setInt(3, book.getPublisherId());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating book failed, no ID obtained.");
                }
            }
        }
    }

    // Read a book by ID
    public Book getBookById(int bookId) throws SQLException {
        String sql = "SELECT book_id, title, category_id, publisher_id FROM book WHERE book_id = ?";

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBook(rs);
                }
                return null;
            }
        }
    }

    // Read all books
    public List<Book> getAllBooks() throws SQLException {
        String sql = "SELECT book_id, title, category_id, publisher_id FROM book";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DBConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }
        }
        return books;
    }

    // Search books by title
    public List<Book> searchBooksByTitle(String searchTerm) throws SQLException {
        String sql = "SELECT book_id, title, category_id, publisher_id FROM book WHERE LOWER(title) LIKE LOWER(?)";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + searchTerm + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    books.add(mapResultSetToBook(rs));
                }
            }
        }
        return books;
    }

    public List<Book> getBooksByAuthorName(String authorName) throws SQLException {

        String sql = "SELECT DISTINCT b.book_id, b.title, b.category_id, b.publisher_id FROM book b  JOIN book_author ba ON b.book_id = ba.book_id  JOIN author a ON ba.author_id = a.author_id  WHERE LOWER(a.name) LIKE LOWER(?)";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + authorName + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    books.add(mapResultSetToBook(rs));
                }
            }
        }
        return books;
    }

    // Get books by category
    public List<Book> getBooksByCategory(int categoryId) throws SQLException {
        String sql = "SELECT book_id, title, category_id, publisher_id FROM book WHERE category_id = ?";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, categoryId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    books.add(mapResultSetToBook(rs));
                }
            }
        }
        return books;
    }

    // Get books by publisher
    public List<Book> getBooksByPublisher(int publisherId) throws SQLException {
        String sql = "SELECT book_id, title, category_id, publisher_id FROM book WHERE publisher_id = ?";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, publisherId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    books.add(mapResultSetToBook(rs));
                }
            }
        }
        return books;
    }

    // Update a book
    public boolean updateBook(Book book) throws SQLException {
        String sql = "UPDATE book SET title = ?, category_id = ?, publisher_id = ? WHERE book_id = ?";

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setInt(2, book.getCategoryId());
            pstmt.setInt(3, book.getPublisherId());
            pstmt.setInt(4, book.getBookId());

            return pstmt.executeUpdate() > 0;
        }
    }

    // Delete a book
    public boolean deleteBook(int bookId) throws SQLException {
        String sql = "DELETE FROM book WHERE book_id = ?";

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookId);
            return pstmt.executeUpdate() > 0;
        }
    }

    // Helper method to map ResultSet to Book object
    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setBookId(rs.getInt("book_id"));
        book.setTitle(rs.getString("title"));
        book.setCategoryId(rs.getInt("category_id"));
        book.setPublisherId(rs.getInt("publisher_id"));
        // publication_year, language and price are not fetched because the DB schema
        // doesn't have those columns. Leave model defaults in place.
        return book;
    }
}
