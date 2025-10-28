package src.service;

import src.model.dao.BookAuthorDAO;
import src.model.pojo.BookAuthor;
import src.utils.Validation;

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
    public void addBookAuthor(BookAuthor bookAuthor) throws SQLException {
        Objects.requireNonNull(bookAuthor, " bookAuthor cannot be null ");
        if (bookAuthor.getAuthorId() <= 0) {
            throw new IllegalArgumentException("authorId cannot be empty or negative");
        }
        if (bookAuthor.getBookId() <= 0) {
            throw new IllegalArgumentException("bookId cannot be empty or negative");
        }
        bookAuthorDAO.addBookAuthor(bookAuthor);
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
    public boolean updateBookByAuthor(int bookId, int authorId) throws SQLException {
        if (bookId <= 0) {
            throw new IllegalArgumentException("bookId cannot be empty or negative");
        }
        if (authorId <= 0) {
            throw new IllegalArgumentException("authorId cannot be empty or negative");
        }
        return bookAuthorDAO.updateBookByAuthor(bookId, authorId);
    }

    @Override
    public boolean updateAuthorByBook(int authorId, int bookId) throws SQLException {
        if (bookId <= 0) {
            throw new IllegalArgumentException("bookId cannot be empty or negative");
        }
        if (authorId <= 0) {
            throw new IllegalArgumentException("authorId cannot be empty or negative");
        }
        return bookAuthorDAO.updateAuthorByBook(authorId, bookId);
    }

}
