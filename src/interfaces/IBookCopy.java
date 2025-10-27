package src.interfaces;

import java.sql.SQLException;
import java.util.List;

import src.model.pojo.BookCopy;
import src.model.pojo.BookCopy.BookStatus;

public interface IBookCopy {

    // Create a new book copy
    int createBookCopy(BookCopy bookCopy) throws SQLException;

    // Read a book copy by ID
    BookCopy getBookCopyById(int copyId) throws SQLException;

    // Get all copies of a specific book
    List<BookCopy> getBookCopiesByBookId(int bookId) throws SQLException;

    // Get available copies of a book
    List<BookCopy> getAvailableCopiesByBookId(int bookId) throws SQLException;

    // Update book copy status
    boolean updateBookCopyStatus(int copyId, BookStatus status) throws SQLException;

    // Delete a book copy
    boolean deleteBookCopy(int copyId) throws SQLException;

    // Get total number of copies for a book
    int getTotalCopiesCount(int bookId) throws SQLException;

    // Get count of available copies for a book
    int getAvailableCopiesCount(int bookId) throws SQLException;

}