package src.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import src.interfaces.IBook;
import src.model.dao.BookDAO;
import src.model.pojo.Book;
import src.utils.Validation;

public class BookService implements IBook {
    private final IBook bookDAO;

    public BookService() {
        this.bookDAO = new BookDAO();
    }

    @Override
    public int createBook(Book book) throws SQLException {
        Objects.requireNonNull(book, "Book cannot be null");
        validateBook(book);
        Book existingBookById = bookDAO.getBookByTitle(book.getTitle());
        if (existingBookById != null) {
            throw new SQLException("Book with ID " + book.getBookId() + " already exists.");
        }

        return bookDAO.createBook(book);
    }

    @Override
    public Book getBookById(int bookId) throws SQLException {
        if (bookId <= 0) {
            throw new IllegalArgumentException("Book ID cannot be empty or negative");
        }
        return bookDAO.getBookById(bookId);
    }

    @Override
    public List<Book> getAllBooks() throws SQLException {
        return bookDAO.getAllBooks();
    }

    @Override
    public Book getBookByTitle(String searchTerm) throws SQLException {
        Validation.requireNonEmpty(searchTerm, "searchTerm");
        return bookDAO.getBookByTitle(searchTerm);
    }

    @Override
    public List<Book> getBooksByTitle(String searchTerm) throws SQLException {
        Validation.requireNonEmpty(searchTerm, "searchTerm");
        return bookDAO.getBooksByTitle(searchTerm);
    }

    @Override
    public boolean deleteBook(int bookId) throws SQLException {
        if (bookId <= 0) {
            throw new IllegalArgumentException("Book ID cannot be empty or negative");
        }
        return bookDAO.deleteBook(bookId);
    }

    @Override
    public boolean updateBook(Book book) throws SQLException {
        Objects.requireNonNull(book, "Book cannot be null");
        validateBook(book);

        Book existingBook = bookDAO.getBookByTitle(book.getTitle());
        if (existingBook != null && existingBook.getBookId() != book.getBookId()) {
            throw new IllegalArgumentException("Book with ID " + book.getBookId() + " already exists.");
        }
        return bookDAO.updateBook(book);
    }

    @Override
    public List<Book> getBooksByAuthorName(String authorName) throws SQLException {
        Validation.requireNonEmpty(authorName, "Author Name");
        return bookDAO.getBooksByAuthorName(authorName);
    }

    @Override
    public List<Book> getBooksByCategory(String category) throws SQLException {
        Validation.requireNonEmpty(category, "Category");
        return bookDAO.getBooksByCategory(category);
    }

    @Override
    public List<Book> getBooksByCategory(int categoryId) throws SQLException {
        if (categoryId <= 0) {
            throw new IllegalArgumentException("Category ID cannot be empty or negative");
        }
        return bookDAO.getBooksByCategory(categoryId);
    }

    @Override
    public List<Book> getBooksByPublisher(int publisherId) throws SQLException {
        if (publisherId <= 0) {
            throw new IllegalArgumentException("Publisher ID cannot be empty or negative");
        }
        return bookDAO.getBooksByPublisher(publisherId);
    }

    @Override
    public List<Book> getBooksByPublisher(String publisher) throws SQLException {
        Validation.requireNonEmpty(publisher, "Publisher");
        return bookDAO.getBooksByPublisher(publisher);
    }

    private void validateBook(Book book) {
        Validation.requireNonEmpty(book.getTitle(), "Title");
        if (book.getCategoryId() <= 0) {
            throw new IllegalArgumentException("Category ID cannot be empty or negative");
        }
        if (book.getPublisherId() <= 0) {
            throw new IllegalArgumentException("Publisher ID cannot be empty or negative");
        }
        if (book.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

    }

}
