package src.interfaces;

import java.sql.SQLException;
import java.util.List;

import src.model.pojo.Book;

public interface IBook {

    // Create a new book
    int createBook(Book book) throws SQLException;

    // Read a book by ID
    Book getBookById(int bookId) throws SQLException;

    // Read all books
    List<Book> getAllBooks() throws SQLException;

    // Search books by title
    Book getBookByTitle(String searchTerm) throws SQLException;

    // Get books by category
    List<Book> getBooksByCategory(int categoryId) throws SQLException;

    // Get books by publisher
    List<Book> getBooksByPublisher(int publisherId) throws SQLException;

    // Update a book
    boolean updateBook(Book book) throws SQLException;

    // Delete a book
    boolean deleteBook(int bookId) throws SQLException;

    List<Book> getBooksByTitle(String searchTerm) throws SQLException;

    List<Book> getBooksByAuthorName(String authorName) throws SQLException;

    List<Book> getBooksByCategory(String category) throws SQLException;

    List<Book> getBooksByPublisher(String publisher) throws SQLException;

}