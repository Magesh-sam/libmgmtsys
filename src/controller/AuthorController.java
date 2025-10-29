package src.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import src.interfaces.IAuthor;
import src.model.pojo.Author;
import src.service.AuthorService;

public class AuthorController implements IAuthor {

    private final AuthorService authorService;

    public AuthorController() {
        this.authorService = new AuthorService();
    }

    public int createAuthor(Author author) {
        try {
            return authorService.createAuthor(author);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error creating author: " + e.getMessage());
            return 0;
        }
    }

    public Author getAuthorById(int authorId) {
        try {
            return authorService.getAuthorById(authorId);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error retrieving author: " + e.getMessage());
            return null;
        }
    }

    public List<Author> getAllAuthors() {
        try {
            return authorService.getAllAuthors();
        } catch (SQLException e) {
            System.err.println("Error fetching authors: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public Author getAuthorByName(String name) {
        try {
            return authorService.getAuthorByName(name);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error fetching author: " + e.getMessage());
            return null;
        }
    }

    public List<Author> searchAuthorsByName(String name) {
        try {
            return authorService.searchAuthorsByName(name);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error searching authors: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public boolean updateAuthor(Author author) {
        try {
            return authorService.updateAuthor(author);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error updating author: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteAuthor(int authorId) {
        try {
            return authorService.deleteAuthor(authorId);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error deleting author: " + e.getMessage());
            return false;
        }
    }

    public boolean authorExists(String name) {
        try {
            return authorService.authorExists(name);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error checking author existence: " + e.getMessage());
            return false;
        }
    }

    public boolean authorExists(int authorId) {
        try {
            return authorService.authorExists(authorId);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error checking author existence: " + e.getMessage());
            return false;
        }
    }
}
