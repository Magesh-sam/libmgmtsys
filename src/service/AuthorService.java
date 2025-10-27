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
        validateAuthor(author);
        Author existingAuthor = authorDAO.getAuthorByName(author.getName());
        if (existingAuthor != null) {
            throw new IllegalArgumentException("Author already exist.");
        }
        int author_id = authorDAO.createAuthor(author);
        return author_id;

    }

    private void validateAuthor(Author author) {
        Objects.requireNonNull(author, "User cannot be null");
        Validation.requireNonEmpty(author.getName(), "Name");

    }
}
