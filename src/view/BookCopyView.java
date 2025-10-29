package src.view;

import src.controller.BookController;
import src.controller.BookCopyController;
import src.model.pojo.Book;
import src.model.pojo.BookCopy;
import src.model.pojo.BookCopy.BookStatus;
import src.utils.InputUtil;

import java.util.List;

public class BookCopyView {

    private final BookCopyController bookCopyController;
    private final BookController bookController;

    public BookCopyView() {
        this.bookCopyController = new BookCopyController();
        this.bookController = new BookController();
    }

    public void display() {
        int choice = -1;

        while (true) {
            System.out.println("\n===== Book Copy Management =====");
            System.out.println("1. Add Book Copy");
            System.out.println("2. View All Book Copies");
            System.out.println("3. Update Book Copy");
            System.out.println("4. Delete Book Copy");
            System.out.println("5. Search Book Copies by Book ID");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = InputUtil.getIntInput();

            switch (choice) {
                case 1 -> addBookCopy();
                case 2 -> viewAllBookCopies();
                case 3 -> updateBookCopy();
                case 4 -> deleteBookCopy();
                case 5 -> searchBookCopiesByBookId();
                case 6 -> {
                    System.out.println("Exiting Book Copy Management...");
                    return;
                }
                default -> System.out.println("Invalid choice! Enter between 1 to 6.");
            }
        }
    }

    private void addBookCopy() {
        System.out.println("\n--- Add New Book Copy ---");
        printBooks();
        System.out.print("Enter Book ID: ");
        int bookId = InputUtil.getIntInput();

        System.out.println("Select Status: 1. available  2. borrowed  3. lost (default: available)");
        int statusChoice = InputUtil.getIntInput();
        BookStatus status = mapBookStatus(statusChoice);

        BookCopy copy = new BookCopy(bookId, status);
        int copyId = bookCopyController.createBookCopy(copy);

        if (copyId > 0) {
            System.out.println("Book copy added successfully!");
        } else {
            System.out.println("Failed to add book copy. Please try again.");
        }
    }

    private void updateBookCopy() {
        System.out.println("\n--- Update Book Copy ---");
        printBooks();
        viewAllBookCopies();
        System.out.print("Enter Copy ID to update: ");
        int copyId = InputUtil.getIntInput();

        BookCopy existing = bookCopyController.getBookCopyById(copyId);
        if (existing == null) {
            System.out.println("Book copy not found!");
            return;
        }

        printBooks();
        System.out.print("Enter Book ID (0 to keep unchanged): ");
        int bookId = InputUtil.getIntInput();

        System.out
                .println("Select Status: 1. available  2. borrowed  3. lost (default: available, 0 to keep unchanged)");
        int statusChoice = InputUtil.getIntInput();

        bookId = (bookId == 0) ? existing.getBookId() : bookId;

        BookStatus status = statusChoice == 0 ? existing.getStatus() : mapBookStatus(statusChoice);

        existing.setBookId(bookId);
        existing.setStatus(status);

        boolean success = bookCopyController.updateBookCopy(existing);

        if (success) {
            System.out.println("Book copy updated successfully!");
        } else {
            System.out.println("Book copy not found or could not be updated.");
        }
    }

    private void deleteBookCopy() {
        System.out.println("\n--- Delete Book Copy ---");
        printBooks();
        viewAllBookCopies();
        System.out.print("Enter Copy ID to delete: ");
        int copyId = InputUtil.getIntInput();

        boolean success = bookCopyController.deleteBookCopy(copyId);

        if (success) {
            System.out.println("Book copy deleted successfully!");
        } else {
            System.out.println("Book copy not found or could not be deleted.");
        }
    }

    private void searchBookCopiesByBookId() {
        System.out.println("\n--- Search Book Copies ---");
        System.out.print("Enter Book ID: ");
        int bookId = InputUtil.getIntInput();

        List<BookCopy> results = bookCopyController.getBookCopiesByBookId(bookId);

        if (results.isEmpty()) {
            System.out.println("No book copies found for book ID " + bookId + ".");
            return;
        }

        for (BookCopy bc : results) {
            System.out.println(bc);
        }
    }

    private void viewAllBookCopies() {
        System.out.println("\n--- Book Copy List ---");
        List<BookCopy> copies = bookCopyController.getAllBookCopies();

        if (copies.isEmpty()) {
            System.out.println("No book copies found in the system.");
            return;
        }

        printBooks();
        for (BookCopy bc : copies) {
            System.out.println(bc);
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

    private BookCopy.BookStatus mapBookStatus(int status) {
        switch (status) {
            case 1:
                return BookCopy.BookStatus.available;
            case 2:
                return BookCopy.BookStatus.borrowed;
            case 3:
                return BookCopy.BookStatus.lost;
            default:
                return BookCopy.BookStatus.available;
        }
    }
}
