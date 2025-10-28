package src.controller;

import java.sql.SQLException;
import java.util.List;

import src.interfaces.IBook;
import src.model.pojo.Book;
import src.service.BookService;

public class BookController implements IBook {
    private final IBook bookService;

    public BookController() {
        this.bookService = new BookService();
    }

    @Override
    public int createBook(Book book) {
        try {
            return bookService.createBook(book);
        } catch (SQLException e) {
            System.out.println("Error creating book: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Book getBookById(int bookId) {
        try {
            return bookService.getBookById(bookId);
        } catch (SQLException e) {
            System.out.println("Error fetching book: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Book> getAllBooks() {
        try {
            return bookService.getAllBooks();
        } catch (SQLException e) {
            System.out.println("Error fetching books: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Book getBookByTitle(String searchTerm) {
        try {
            return bookService.getBookByTitle(searchTerm);
        } catch (SQLException e) {
            System.out.println("Error fetching book by title: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Book> getBooksByCategory(int categoryId) {
        try {
            return bookService.getBooksByCategory(categoryId);
        } catch (SQLException e) {
            System.out.println("Error fetching books by category: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Book> getBooksByPublisher(int publisherId) {
        try {
            return bookService.getBooksByPublisher(publisherId);
        } catch (SQLException e) {
            System.out.println("Error fetching books by publisher: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateBook(Book book) {
        try {
            return bookService.updateBook(book);
        } catch (SQLException e) {
            System.out.println("Error updating book: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteBook(int bookId) {
        try {
            return bookService.deleteBook(bookId);
        } catch (SQLException e) {
            System.out.println("Error deleting book: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Book> getBooksByTitle(String searchTerm) {
        try {
            return bookService.getBooksByTitle(searchTerm);
        } catch (SQLException e) {
            System.out.println("Error fetching books by title: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Book> getBooksByAuthorName(String authorName) {
        try {
            return bookService.getBooksByAuthorName(authorName);
        } catch (SQLException e) {
            System.out.println("Error fetching books by author name: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Book> getBooksByCategory(String category) {
        try {
            return bookService.getBooksByCategory(category);
        } catch (SQLException e) {
            System.out.println("Error fetching books by category: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Book> getBooksByPublisher(String publisher) {
        try {
            return bookService.getBooksByPublisher(publisher);
        } catch (SQLException e) {
            System.out.println("Error fetching books by publisher: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
