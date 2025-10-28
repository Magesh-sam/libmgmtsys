package src.interfaces;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import src.model.pojo.BorrowedBooks;

public interface IBorrowedBooks {

    int createBorrowedBook(BorrowedBooks borrowed) throws SQLException;

    BorrowedBooks getBorrowedBookById(int borrowedId) throws SQLException;

    List<BorrowedBooks> getAllBorrowedBooks() throws SQLException;

    boolean updateReturnDate(int borrowedId, LocalDate returDate) throws SQLException;

    boolean deleteBorrowedBook(int borrowedId) throws SQLException;

    boolean borrowedBookExists(int borrowedId) throws SQLException;

    List<BorrowedBooks> getBorrowedBooksByMemberId(int memberId) throws SQLException;

    List<BorrowedBooks> getOverdueBooks(LocalDate currentDate) throws SQLException;

}