package src.interfaces;

import java.sql.SQLException;
import java.util.List;

import src.model.Book;

public interface IBookDAO {

    // Create a new book
    int createBook(Book book) throws SQLException;

    // Read a book by ID
    Book getBookById(int bookId) throws SQLException;

    // Read all books
    List<Book> getAllBooks() throws SQLException;

    // Search books by title
    List<Book> searchBooksByTitle(String searchTerm) throws SQLException;

    List<Book> getBooksByAuthorName(String authorName) throws SQLException;

    // Get books by category
    List<Book> getBooksByCategory(int categoryId) throws SQLException;

    // Get books by publisher
    List<Book> getBooksByPublisher(int publisherId) throws SQLException;

    // Update a book
    boolean updateBook(Book book) throws SQLException;

    // Delete a book
    boolean deleteBook(int bookId) throws SQLException;

}