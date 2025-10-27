package src.interfaces;

import java.sql.SQLException;
import java.util.List;

import src.model.pojo.Author;

public interface IAuthor {

    // Create a new author
    int createAuthor(Author author) throws SQLException;

    // Read author by ID
    Author getAuthorById(int authorId) throws SQLException;

    // Get all authors
    List<Author> getAllAuthors() throws SQLException;

    Author getAuthorByName(String authorName) throws SQLException;

    // Search authors by name
    List<Author> searchAuthorsByName(String searchTerm) throws SQLException;

    // Update author
    boolean updateAuthor(Author author) throws SQLException;

    // Delete author
    boolean deleteAuthor(int authorId) throws SQLException;

}