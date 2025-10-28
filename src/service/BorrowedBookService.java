package src.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import src.interfaces.IBorrowedBooks;
import src.model.dao.BorrowedBooksDAO;
import src.model.pojo.BorrowedBooks;

public class BorrowedBookService implements IBorrowedBooks {

    private final IBorrowedBooks borrowedDAO;

    public BorrowedBookService() {
        this.borrowedDAO = new BorrowedBooksDAO();
    }

    @Override
    public int createBorrowedBook(BorrowedBooks borrowed) throws SQLException {
        Objects.requireNonNull(borrowed, "Borrowed book entry cannot be null");
        validateBorrowedBook(borrowed);

        if (borrowed.getDueDate() != null &&
                borrowed.getBorrowDate() != null &&
                borrowed.getDueDate().isBefore(borrowed.getBorrowDate())) {
            throw new IllegalArgumentException("Due date cannot be before borrow date.");
        }

        return borrowedDAO.createBorrowedBook(borrowed);
    }

    @Override
    public BorrowedBooks getBorrowedBookById(int borrowedId) throws SQLException {
        if (borrowedId <= 0) {
            throw new IllegalArgumentException("Borrowed ID cannot be empty or negative");
        }
        return borrowedDAO.getBorrowedBookById(borrowedId);
    }

    @Override
    public List<BorrowedBooks> getAllBorrowedBooks() throws SQLException {
        return borrowedDAO.getAllBorrowedBooks();
    }

    @Override
    public boolean updateReturnDate(int borrowedId, LocalDate returnDate) throws SQLException {
        if (borrowedId <= 0) {
            throw new IllegalArgumentException("Borrowed ID cannot be empty or negative");
        }
        if (returnDate == null) {
            throw new IllegalArgumentException("Return date cannot be null");
        }

        BorrowedBooks borrowed = borrowedDAO.getBorrowedBookById(borrowedId);
        if (borrowed == null) {
            throw new IllegalArgumentException("Borrowed record does not exist with ID: " + borrowedId);
        }

        if (borrowed.getBorrowDate() != null && returnDate.isBefore(borrowed.getBorrowDate())) {
            throw new IllegalArgumentException("Return date cannot be before borrow date.");
        }

        return borrowedDAO.updateReturnDate(borrowedId, returnDate);
    }

    @Override
    public boolean deleteBorrowedBook(int borrowedId) throws SQLException {
        if (borrowedId <= 0) {
            throw new IllegalArgumentException("Borrowed ID cannot be empty or negative");
        }

        BorrowedBooks borrowed = borrowedDAO.getBorrowedBookById(borrowedId);
        if (borrowed == null) {
            throw new IllegalArgumentException("Borrowed record not found with ID: " + borrowedId);
        }

        return borrowedDAO.deleteBorrowedBook(borrowedId);
    }

    private void validateBorrowedBook(BorrowedBooks borrowed) {
        if (borrowed.getMemberId() <= 0) {
            throw new IllegalArgumentException("Member ID cannot be empty or negative");
        }
        if (borrowed.getCopyId() <= 0) {
            throw new IllegalArgumentException("Copy ID cannot be empty or negative");
        }

        if (borrowed.getBorrowDate() == null) {
            borrowed.setBorrowDate(LocalDate.now());
        }

        if (borrowed.getDueDate() == null) {
            borrowed.setDueDate(borrowed.getBorrowDate().plusDays(14));
        }

    }

    @Override
    public List<BorrowedBooks> getBorrowedBooksByMemberId(int memberId) throws SQLException {
        if (memberId <= 0)
            throw new IllegalArgumentException("memberId must be positive");
        return borrowedDAO.getBorrowedBooksByMemberId(memberId);
    }

    @Override
    public List<BorrowedBooks> getOverdueBooks(LocalDate currentDate) throws SQLException {
        if (currentDate == null)
            currentDate = LocalDate.now();
        return borrowedDAO.getOverdueBooks(currentDate);
    }

    @Override
    public boolean borrowedBookExists(int borrowedId) throws SQLException {
        if (borrowedId <= 0) {
            throw new IllegalArgumentException("Borrowed ID cannot be empty or negative");
        }
        return borrowedDAO.borrowedBookExists(borrowedId);
    }

}
