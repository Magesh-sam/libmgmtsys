package src.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import src.interfaces.IAuthor;
import src.model.dao.AuthorDAO;
import src.model.pojo.Author;
import src.utils.Validation;

public class AuthorService implements IAuthor {
    private final IAuthor authorDAO;

    public AuthorService() {
        this.authorDAO = new AuthorDAO();
    }

    @Override
    public int createAuthor(Author author) throws SQLException {
        Objects.requireNonNull(author);
        validateAuthor(author);
        Author existingAuthor = authorDAO.getAuthorByName(author.getName());
        if (existingAuthor != null) {
            throw new IllegalArgumentException("Author already exist.");
        }
        return authorDAO.createAuthor(author);
    }

    @Override
    public Author getAuthorById(int authorId) throws SQLException {
        if (authorId <= 0)
            throw new IllegalArgumentException("authorId must be positive");
        return authorDAO.getAuthorById(authorId);
    }

    @Override
    public List<Author> getAllAuthors() throws SQLException {
        return authorDAO.getAllAuthors();
    }

    @Override
    public Author getAuthorByName(String name) throws SQLException {
        Validation.requireNonEmpty(name, "Name");
        return authorDAO.getAuthorByName(name);
    }

    @Override
    public List<Author> searchAuthorsByName(String name) throws SQLException {
        Validation.requireNonEmpty(name, "Name");
        return authorDAO.searchAuthorsByName(name);
    }

    @Override
    public boolean updateAuthor(Author author) throws SQLException {
        Objects.requireNonNull(author);
        validateAuthor(author);
        if (author.getAuthorId() <= 0)
            throw new IllegalArgumentException("authorId must be positive");
        if (!authorDAO.authorExists(author.getAuthorId())) {
            throw new IllegalArgumentException("Author does not exist.");
        }
        return authorDAO.updateAuthor(author);
    }

    @Override
    public boolean deleteAuthor(int authorId) throws SQLException {
        if (authorId <= 0)
            throw new IllegalArgumentException("authorId must be positive");
        if (!authorDAO.authorExists(authorId)) {
            throw new IllegalArgumentException("Author does not exist.");
        }
        return authorDAO.deleteAuthor(authorId);
    }

    @Override
    public boolean authorExists(String name) throws SQLException {
        Validation.requireNonEmpty(name, "Name");
        return authorDAO.getAuthorByName(name) != null;
    }

    @Override
    public boolean authorExists(int authorId) throws SQLException {
        if (authorId <= 0)
            throw new IllegalArgumentException("authorId must be positive");
        return authorDAO.authorExists(authorId);
    }

    private void validateAuthor(Author author) {
        Objects.requireNonNull(author, "Author cannot be null");
        Validation.requireNonEmpty(author.getName(), "Name");
    }

}
