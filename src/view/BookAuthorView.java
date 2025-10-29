package src.view;

import src.controller.BookAuthorController;
import src.controller.BookController;
import src.controller.AuthorController;
import src.model.pojo.Book;
import src.model.pojo.Author;
import src.model.pojo.BookAuthor;
import src.utils.InputUtil;

import java.util.List;

public class BookAuthorView {

    private final BookAuthorController bookAuthorController;
    private final BookController bookController;
    private final AuthorController authorController;

    public BookAuthorView() {
        this.bookAuthorController = new BookAuthorController();
        this.bookController = new BookController();
        this.authorController = new AuthorController();
    }

    public void display() {
        int choice = -1;

        while (true) {
            System.out.println("\n===== Book-Author Management =====");
            System.out.println("1. Link Book with Author");
            System.out.println("2. View All Book-Author Links");
            System.out.println("3. Delete Book-Author Link");
            System.out.println("4. Search Authors by Book ID");
            System.out.println("5. Search Books by Author ID");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = InputUtil.getIntInput();

            switch (choice) {
                case 1 -> linkBookWithAuthor();
                case 2 -> viewAllBookAuthors();
                case 3 -> deleteBookAuthor();
                case 4 -> searchAuthorsByBookId();
                case 5 -> searchBooksByAuthorId();
                case 6 -> {
                    System.out.println("Exiting Book-Author Management...");
                    return;
                }
                default -> System.out.println("Invalid choice! Enter between 1 to 6.");
            }
        }
    }

    private void linkBookWithAuthor() {
        System.out.println("\n--- Link Book with Author ---");

        printBooks();
        System.out.print("Enter Book ID: ");
        int bookId = InputUtil.getIntInput();

        printAuthors();
        System.out.print("Enter Author ID: ");
        int authorId = InputUtil.getIntInput();

        BookAuthor link = new BookAuthor(bookId, authorId);
        boolean success = bookAuthorController.addBookAuthor(link);

        if (success) {
            System.out.println("Book linked with author successfully!");
        } else {
            System.out.println("Failed to link book and author. Please try again.");
        }
    }

    private void deleteBookAuthor() {
        System.out.println("\n--- Delete Book-Author Link ---");
        printBooks();
        System.out.print("Enter Book ID: ");
        int bookId = InputUtil.getIntInput();

        printAuthors();
        System.out.print("Enter Author ID: ");
        int authorId = InputUtil.getIntInput();

        boolean success = bookAuthorController.deleteBookAuthor(bookId, authorId);

        if (success) {
            System.out.println("Book-Author link deleted successfully!");
        } else {
            System.out.println("Book-Author link not found or could not be deleted.");
        }
    }

    private void viewAllBookAuthors() {
        System.out.println("\n--- All Book-Author Links ---");
        List<BookAuthor> links = bookAuthorController.getAllBookAuthors();

        if (links.isEmpty()) {
            System.out.println("No book-author links found in the system.");
            return;
        }

        for (BookAuthor ba : links) {
            System.out.println(ba);
        }
    }

    private void searchAuthorsByBookId() {
        System.out.println("\n--- Search Authors by Book ID ---");
        System.out.print("Enter Book ID: ");
        int bookId = InputUtil.getIntInput();

        List<BookAuthor> authors = bookAuthorController.getAuthorsByBookId(bookId);

        if (authors.isEmpty()) {
            System.out.println("No authors found for Book ID " + bookId + ".");
            return;
        }

        System.out.println("Authors for Book ID " + bookId + ":");
        for (BookAuthor a : authors) {
            System.out.println(a);
        }
    }

    private void searchBooksByAuthorId() {
        System.out.println("\n--- Search Books by Author ID ---");
        System.out.print("Enter Author ID: ");
        int authorId = InputUtil.getIntInput();

        List<BookAuthor> books = bookAuthorController.getBooksByAuthorId(authorId);

        if (books.isEmpty()) {
            System.out.println("No books found for Author ID " + authorId + ".");
            return;
        }

        System.out.println("Books for Author ID " + authorId + ":");
        for (BookAuthor b : books) {
            System.out.println(b);
        }
    }

    private void printBooks() {
        List<Book> books = bookController.getAllBooks();
        if (!books.isEmpty()) {
            System.out.println("\n--- List of Books ---");
            for (Book b : books) {
                System.out.println(b);
            }
        } else {
            System.out.println("No books found in the system. Add one first.");
        }
    }

    private void printAuthors() {
        List<Author> authors = authorController.getAllAuthors();
        if (!authors.isEmpty()) {
            System.out.println("\n--- List of Authors ---");
            for (Author a : authors) {
                System.out.println(a);
            }
        } else {
            System.out.println("No authors found in the system. Add one first.");
        }
    }
}
