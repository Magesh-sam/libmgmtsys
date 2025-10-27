package src.interfaces;

import java.sql.SQLException;
import java.util.List;

import src.model.BookAuthor;

public interface IBookAuthorDAO {

    void addBookAuthor(BookAuthor bookAuthor) throws SQLException;

    List<BookAuthor> getAuthorsByBookId(int bookId) throws SQLException;

    List<BookAuthor> getBooksByAuthorId(int authorId) throws SQLException;

    boolean deleteBookAuthor(int bookId, int authorId) throws SQLException;

}