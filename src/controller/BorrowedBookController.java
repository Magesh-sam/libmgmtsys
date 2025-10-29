package src.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import src.interfaces.IBorrowedBooks;
import src.model.pojo.BorrowedBooks;
import src.service.BorrowedBookService;

public class BorrowedBookController implements IBorrowedBooks {

    private final BorrowedBookService borrowedBookService;

    public BorrowedBookController() {
        this.borrowedBookService = new BorrowedBookService();
    }

    public int createBorrowedBook(BorrowedBooks borrowed) {
        try {
            return borrowedBookService.createBorrowedBook(borrowed);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error creating borrowed book: " + e.getMessage());
            return 0;
        }
    }

    public BorrowedBooks getBorrowedBookById(int borrowedId) {
        try {
            return borrowedBookService.getBorrowedBookById(borrowedId);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error fetching borrowed book: " + e.getMessage());
            return null;
        }
    }

    public List<BorrowedBooks> getAllBorrowedBooks() {
        try {
            return borrowedBookService.getAllBorrowedBooks();
        } catch (SQLException e) {
            System.err.println("Error fetching borrowed books: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public boolean updateReturnDate(int borrowedId, LocalDate returnDate) {
        try {
            return borrowedBookService.updateReturnDate(borrowedId, returnDate);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error updating return date: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteBorrowedBook(int borrowedId) {
        try {
            return borrowedBookService.deleteBorrowedBook(borrowedId);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error deleting borrowed record: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean borrowedBookExists(int borrowedId) {
        try {
            return getBorrowedBookById(borrowedId) != null;
        } catch (IllegalArgumentException e) {
            System.err.println("Error checking borrowed book existence: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<BorrowedBooks> getBorrowedBooksByMemberId(int memberId) {
        try {
            return borrowedBookService.getBorrowedBooksByMemberId(memberId);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error fetching borrowed books by member ID: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<BorrowedBooks> getOverdueBooks(LocalDate currentDate) {
        try {
            return borrowedBookService.getOverdueBooks(currentDate);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error fetching overdue books: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
