package src.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import src.interfaces.IBookCopy;
import src.model.dao.BookCopyDAO;
import src.model.dao.BookDAO;
import src.model.pojo.BookCopy;
import src.model.pojo.BookCopy.BookStatus;
import src.utils.Validation;

public class BookCopyService implements IBookCopy {
    private final IBookCopy bookCopyDAO;
    private final BookDAO bookDAO;

    public BookCopyService() {
        this.bookCopyDAO = new BookCopyDAO();
        this.bookDAO = new BookDAO();
    }

    @Override
    public int createBookCopy(BookCopy bookCopy) throws SQLException {
        Objects.requireNonNull(bookCopy, " bookCopy cannot be null ");
        if (bookCopy.getBookId() <= 0) {
            throw new IllegalArgumentException("bookId cannot be empty or negative");
        }
        // validate referenced book exists
        if (!bookDAO.bookExists(bookCopy.getBookId())) {
            throw new IllegalArgumentException("Referenced book does not exist.");
        }
        return bookCopyDAO.createBookCopy(bookCopy);
    }

    @Override
    public BookCopy getBookCopyById(int copyId) throws SQLException {
        if (copyId <= 0) {
            throw new IllegalArgumentException("copyId cannot be empty or negative");
        }
        return bookCopyDAO.getBookCopyById(copyId);
    }

    @Override
    public List<BookCopy> getBookCopiesByBookId(int bookId) throws SQLException {
        if (bookId <= 0) {
            throw new IllegalArgumentException("bookId cannot be empty or negative");
        }
        return bookCopyDAO.getBookCopiesByBookId(bookId);
    }

    @Override
    public List<BookCopy> getAvailableCopiesByBookId(int bookId) throws SQLException {
        if (bookId <= 0) {
            throw new IllegalArgumentException("bookId cannot be empty or negative");
        }
        return bookCopyDAO.getAvailableCopiesByBookId(bookId);
    }

    @Override
    public boolean updateBookCopyStatus(int copyId, BookStatus status) throws SQLException {
        if (copyId <= 0) {
            throw new IllegalArgumentException("copyId cannot be empty or negative");
        }
        Validation.requireNonEmpty(status.toString(), "Book Status");
        BookCopy bookCopy = getBookCopyById(copyId);
        if (bookCopy == null) {
            return false;
        }
        return bookCopyDAO.updateBookCopyStatus(copyId, status);
    }

    @Override
    public boolean deleteBookCopy(int copyId) throws SQLException {
        if (copyId <= 0) {
            throw new IllegalArgumentException("copyId cannot be empty or negative");
        }
        BookCopy bookCopy = getBookCopyById(copyId);
        if (bookCopy == null) {
            return false;
        }
        return bookCopyDAO.deleteBookCopy(copyId);
    }

    @Override
    public int getTotalCopiesCount(int bookId) throws SQLException {
        if (bookId <= 0) {
            throw new IllegalArgumentException("bookId cannot be empty or negative");
        }
        return bookCopyDAO.getTotalCopiesCount(bookId);
    }

    @Override
    public int getAvailableCopiesCount(int bookId) throws SQLException {
        if (bookId <= 0) {
            throw new IllegalArgumentException("bookId cannot be empty or negative");
        }
        return bookCopyDAO.getAvailableCopiesCount(bookId);
    }

    @Override
    public boolean copyExists(int copyId) throws SQLException {
        return bookCopyDAO.copyExists(copyId);
    }

}
