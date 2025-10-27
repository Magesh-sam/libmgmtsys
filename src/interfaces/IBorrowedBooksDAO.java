package src.interfaces;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import src.model.BorrowedBooks;

public interface IBorrowedBooksDAO {

    int createBorrowedBook(BorrowedBooks borrowed) throws SQLException;

    BorrowedBooks getBorrowedBookById(int borrowedId) throws SQLException;

    List<BorrowedBooks> getAllBorrowedBooks() throws SQLException;

    boolean updateReturnDate(int borrowedId, LocalDate returDate) throws SQLException;

    boolean deleteBorrowedBook(int borrowedId) throws SQLException;

}