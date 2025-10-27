package src.service;

import java.sql.SQLException;
import java.util.Objects;

import src.model.dao.AuthorDAO;
import src.model.pojo.Author;
import src.utils.Validation;

public class AuthorService {
    private final AuthorDAO authorDAO;

    public AuthorService() {
        this.authorDAO = new AuthorDAO();
    }

    public int addAuthor(Author author) throws SQLException {
        Objects.requireNonNull(author);
        validateAuthor(author);
        Author existingAuthor = authorDAO.getAuthorByName(author.getName());
        if (existingAuthor != null) {
            throw new IllegalArgumentException("Author already exist.");
        }
        int author_id = authorDAO.createAuthor(author);
        return author_id;

    }

    public Author getAuthorById(int authorId) throws SQLException {
        return authorDAO.getAuthorById(authorId);
    }

    public java.util.List<Author> getAllAuthors() throws SQLException {
        return authorDAO.getAllAuthors();
    }

    public Author getAuthorByName(String name) throws SQLException {
        Validation.requireNonEmpty(name, "Name");
        return authorDAO.getAuthorByName(name);
    }

    public java.util.List<Author> searchAuthorsByName(String name) throws SQLException {
        Validation.requireNonEmpty(name, "Name");
        return authorDAO.searchAuthorsByName(name);
    }

    public boolean updateAuthor(Author author) throws SQLException {
        Objects.requireNonNull(author);
        validateAuthor(author);
        return authorDAO.updateAuthor(author);
    }

    public boolean deleteAuthor(int authorId) throws SQLException {
        return authorDAO.deleteAuthor(authorId);
    }

    private void validateAuthor(Author author) {
        Objects.requireNonNull(author, "User cannot be null");
        Validation.requireNonEmpty(author.getName(), "Name");

    }
}
