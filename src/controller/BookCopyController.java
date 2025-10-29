package src.controller;

import java.sql.SQLException;
import java.util.List;

import src.interfaces.IBookCopy;
import src.model.pojo.BookCopy;
import src.model.pojo.BookCopy.BookStatus;

public class BookCopyController implements IBookCopy {
    private final IBookCopy bookCopyService;

    public BookCopyController() {
        this.bookCopyService = new src.service.BookCopyService();
    }

    @Override
    public int createBookCopy(BookCopy bookCopy) {
        try {
            return bookCopyService.createBookCopy(bookCopy);
        } catch (SQLException e) {
            System.out.println("Error creating book copy: " + e.getMessage());
            return -1;
        }

    }

    @Override
    public BookCopy getBookCopyById(int copyId) {
        try {
            return bookCopyService.getBookCopyById(copyId);
        } catch (SQLException e) {
            System.out.println("Error fetching book copy: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<BookCopy> getBookCopiesByBookId(int bookId) {
        try {
            return bookCopyService.getBookCopiesByBookId(bookId);
        } catch (SQLException e) {
            System.out.println("Error fetching book copies: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<BookCopy> getAvailableCopiesByBookId(int bookId) {
        try {
            return bookCopyService.getAvailableCopiesByBookId(bookId);
        } catch (SQLException e) {
            System.out.println("Error fetching available book copies: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean updateBookCopyStatus(int copyId, BookStatus status) {
        try {
            return bookCopyService.updateBookCopyStatus(copyId, status);
        } catch (SQLException e) {
            System.out.println("Error updating book copy status: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteBookCopy(int copyId) {
        try {
            return bookCopyService.deleteBookCopy(copyId);
        } catch (SQLException e) {
            System.out.println("Error deleting book copy: " + e.getMessage());
            return false;
        }
    }

    @Override
    public int getTotalCopiesCount(int bookId) {
        try {
            return bookCopyService.getTotalCopiesCount(bookId);
        } catch (SQLException e) {
            System.out.println("Error fetching total copies count: " + e.getMessage());
            return -1;
        }
    }

    @Override
    public int getAvailableCopiesCount(int bookId) {
        try {
            return bookCopyService.getAvailableCopiesCount(bookId);
        } catch (SQLException e) {
            System.out.println("Error fetching available copies count: " + e.getMessage());
            return -1;
        }

    }

    @Override
    public boolean copyExists(int copyId) {
        try {
            return bookCopyService.copyExists(copyId);
        } catch (SQLException e) {
            System.out.println("Error checking if copy exists: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateBookCopy(BookCopy copy) {
        try {
            return bookCopyService.updateBookCopy(copy);
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error updating book copy: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<BookCopy> getAllBookCopies() {
        try {
            return bookCopyService.getAllBookCopies();
        } catch (SQLException e) {
            System.out.println("Error fetching all book copies: " + e.getMessage());
            return null;
        }
    }

}
