package src.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import src.model.pojo.BookAuthor;
import src.service.BookAuthorService;

public class BookAuthorController {

    private final BookAuthorService bookAuthorService;

    public BookAuthorController() {
        this.bookAuthorService = new BookAuthorService();
    }

    public void addBookAuthor(BookAuthor bookAuthor) {
        try {
            bookAuthorService.addBookAuthor(bookAuthor);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error adding book-author mapping: " + e.getMessage());
        }
    }

    public List<BookAuthor> getAuthorsByBookId(int bookId) {
        try {
            return bookAuthorService.getAuthorsByBookId(bookId);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error fetching authors by book ID: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<BookAuthor> getBooksByAuthorId(int authorId) {
        try {
            return bookAuthorService.getBooksByAuthorId(authorId);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error fetching books by author ID: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public boolean deleteBookAuthor(int bookId, int authorId) {
        try {
            return bookAuthorService.deleteBookAuthor(bookId, authorId);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error deleting book-author mapping: " + e.getMessage());
            return false;
        }
    }

    public boolean updateBookAuthor(int oldBookId, int oldAuthorId, int newBookId, int newAuthorId) {
        try {
            return bookAuthorService.updateBookAuthor(oldBookId, oldAuthorId, newBookId, newAuthorId);
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error updating book-author mapping: " + e.getMessage());
            return false;
        }
    }

    public boolean bookAuthorExists(int bookId, int authorId) {
        try {
            return bookAuthorService.bookAuthorExists(bookId, authorId);
        } catch (SQLException e) {
            System.err.println("Error checking mapping existence: " + e.getMessage());
            return false;
        }
    }
}
