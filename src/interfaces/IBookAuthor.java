package src.interfaces;

import java.sql.SQLException;
import java.util.List;

import src.model.pojo.BookAuthor;

public interface IBookAuthor {

    boolean addBookAuthor(BookAuthor bookAuthor) throws SQLException;

    List<BookAuthor> getAuthorsByBookId(int bookId) throws SQLException;

    List<BookAuthor> getBooksByAuthorId(int authorId) throws SQLException;

    boolean deleteBookAuthor(int bookId, int authorId) throws SQLException;

    boolean updateBookAuthor(int oldBookId, int oldAuthorId, int newBookId, int newAuthorId) throws SQLException;

    boolean bookAuthorExists(int bookId, int authorId) throws SQLException;

    List<BookAuthor> getAllBookAuthors() throws SQLException;

}