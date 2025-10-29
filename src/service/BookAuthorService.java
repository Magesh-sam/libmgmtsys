package src.service;

import src.model.dao.BookAuthorDAO;
import src.model.pojo.BookAuthor;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import src.interfaces.IBookAuthor;

public class BookAuthorService implements IBookAuthor {

    private final IBookAuthor bookAuthorDAO;

    public BookAuthorService() {
        this.bookAuthorDAO = new BookAuthorDAO();
    }

    @Override
    public boolean addBookAuthor(BookAuthor bookAuthor) throws SQLException {
        Objects.requireNonNull(bookAuthor, " bookAuthor cannot be null ");
        if (bookAuthor.getAuthorId() <= 0) {
            throw new IllegalArgumentException("authorId cannot be empty or negative");
        }
        if (bookAuthor.getBookId() <= 0) {
            throw new IllegalArgumentException("bookId cannot be empty or negative");
        }
        if (bookAuthorDAO.bookAuthorExists(bookAuthor.getBookId(), bookAuthor.getAuthorId())) {
            throw new IllegalArgumentException("Mapping already exists");
        }
        return bookAuthorDAO.addBookAuthor(bookAuthor);
    }

    @Override
    public List<BookAuthor> getAuthorsByBookId(int bookId) throws SQLException {
        if (bookId <= 0) {
            throw new IllegalArgumentException("bookId cannot be empty or negative");
        }
        return bookAuthorDAO.getAuthorsByBookId(bookId);
    }

    @Override
    public List<BookAuthor> getBooksByAuthorId(int authorId) throws SQLException {
        if (authorId <= 0) {
            throw new IllegalArgumentException("authorId cannot be empty or negative");
        }
        return bookAuthorDAO.getBooksByAuthorId(authorId);
    }

    @Override
    public boolean deleteBookAuthor(int bookId, int authorId) throws SQLException {
        if (bookId <= 0) {
            throw new IllegalArgumentException("bookId cannot be empty or negative");
        }
        if (authorId <= 0) {
            throw new IllegalArgumentException("authorId cannot be empty or negative");
        }
        return bookAuthorDAO.deleteBookAuthor(bookId, authorId);
    }

    @Override
    public boolean updateBookAuthor(int oldBookId, int oldAuthorId, int newBookId, int newAuthorId)
            throws SQLException {
        if (oldBookId <= 0 || newBookId <= 0) {
            throw new IllegalArgumentException("bookIds cannot be empty or negative");
        }
        if (oldAuthorId <= 0 || newAuthorId <= 0) {
            throw new IllegalArgumentException("authorIds cannot be empty or negative");
        }
        return bookAuthorDAO.updateBookAuthor(oldBookId, oldAuthorId, newBookId, newAuthorId);
    }

    @Override
    public boolean bookAuthorExists(int bookId, int authorId) throws SQLException {
        return bookAuthorDAO.bookAuthorExists(bookId, authorId);
    }

    @Override
    public List<BookAuthor> getAllBookAuthors() throws SQLException {
        return bookAuthorDAO.getAllBookAuthors();
    }

}
