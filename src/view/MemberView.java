package src.view;

import src.model.pojo.*;
import src.service.BookService;

import java.util.List;
import java.util.Scanner;

public class MemberView {

    private final Scanner sc = new Scanner(System.in);
    private final BookService service;

    public MemberView(BookService service) {
        this.service = service;
    }

    public void display(AppUser appUser) {
        while (true) {
            System.out.println("\n----------------------------------");
            System.out.println("Welcome, " + appUser.getName());
            System.out.println("----------------------------------");
            System.out.println("1. List all Books");
            System.out.println("2. List all Authors");
            System.out.println("3. List all Categories");
            System.out.println("4. List all Publishers");
            System.out.println("5. Search Book by Title");
            System.out.println("6. Search Book by Author");
            System.out.println("7. Search Book by Category");
            System.out.println("8. Search Book by Publisher");
            System.out.println("9. Borrowed Book Details");
            System.out.println("10. Return Book");
            System.out.println("11. Log Out");
            System.out.print("Enter your choice: ");

            String input = sc.nextLine().trim();

            if (!input.matches("\\d+")) {
                System.out.println("Invalid input. Enter a number between 1-11.");
                continue;
            }

            int choice = Integer.parseInt(input);

            switch (choice) {
                case 1 -> listAllBooks();
                case 2 -> listAllAuthors();
                case 3 -> listAllCategories();
                case 4 -> listAllPublishers();
                case 5 -> searchBookByTitle();
                case 6 -> searchBookByAuthor();
                case 7 -> searchBookByCategory();
                case 8 -> searchBookByPublisher();
                case 9 -> borrowedBookDetails(appUser);
                case 10 -> returnBook(appUser);
                case 11 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Please enter a valid option (1-11).");
            }
        }
    }

    // === List Methods ===
    private void listAllBooks() {
        List<Book> books = service.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        System.out.println("\n--- List of Books ---");
        for (Book b : books) {
            System.out.println(formatBook(b));
        }
    }

    private void listAllAuthors() {
        List<Author> authors = service.getAllAuthors();
        if (authors.isEmpty()) {
            System.out.println("No authors found.");
            return;
        }
        System.out.println("\n--- List of Authors ---");
        for (Author a : authors) {
            System.out.println(formatAuthor(a));
        }
    }

    private void listAllCategories() {
        List<Category> categories = service.getAllCategories();
        if (categories.isEmpty()) {
            System.out.println("No categories found.");
            return;
        }
        System.out.println("\n--- List of Categories ---");
        for (Category c : categories) {
            System.out.println(c.getName());
        }
    }

    private void listAllPublishers() {
        List<Publisher> publishers = service.getAllPublishers();
        if (publishers.isEmpty()) {
            System.out.println("No publishers found.");
            return;
        }
        System.out.println("\n--- List of Publishers ---");
        for (Publisher p : publishers) {
            System.out.println(formatPublisher(p));
        }
    }

    // === Search Methods ===
    private void searchBookByTitle() {
        System.out.print("Enter book title: ");
        String title = sc.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("Title cannot be empty.");
            return;
        }
        List<Book> results = service.findBooksByTitle(title);
        printBookSearchResults(results, "title", title);
    }

    private void searchBookByAuthor() {
        System.out.print("Enter author name: ");
        String author = sc.nextLine().trim();
        if (author.isEmpty()) {
            System.out.println("Author name cannot be empty.");
            return;
        }
        List<Book> results = service.findBooksByAuthor(author);
        printBookSearchResults(results, "author", author);
    }

    private void searchBookByCategory() {
        System.out.print("Enter category name: ");
        String category = sc.nextLine().trim();
        if (category.isEmpty()) {
            System.out.println("Category name cannot be empty.");
            return;
        }
        List<Book> results = service.findBooksByCategory(category);
        printBookSearchResults(results, "category", category);
    }

    private void searchBookByPublisher() {
        System.out.print("Enter publisher name: ");
        String publisher = sc.nextLine().trim();
        if (publisher.isEmpty()) {
            System.out.println("Publisher name cannot be empty.");
            return;
        }
        List<Book> results = service.findBooksByPublisher(publisher);
        printBookSearchResults(results, "publisher", publisher);
    }

    private void printBookSearchResults(List<Book> results, String type, String value) {
        if (results.isEmpty()) {
            System.out.println("No books found for " + type + ": " + value);
            return;
        }
        System.out.println("\n--- Search Results (" + type + ": " + value + ") ---");
        for (Book b : results) {
            System.out.println(formatBook(b));
        }
    }

    // === Borrow/Return Methods ===
    private void borrowedBookDetails(AppUser appUser) {
        List<BorrowedBooks> borrowed = service.getBorrowRecordsForUser(appUser.getUserId());
        if (borrowed.isEmpty()) {
            System.out.println("You haven't borrowed any books.");
            return;
        }
        System.out.println("\n--- Borrowed Books ---");
        for (BorrowedBooks b : borrowed) {
            System.out.println(formatBorrowRecord(b));
        }
    }

    private void returnBook(AppUser appUser) {
        System.out.print("Enter Book ID or Borrow Record ID to return: ");
        String id = sc.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("ID cannot be empty.");
            return;
        }
        boolean success = service.returnBook(appUser.getUserId(), id);
        if (success) {
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("Failed to return the book. Check the ID and try again.");
        }
    }

    // === Format Helpers ===
    private String formatBook(Book b) {
        return String.format("[%s] %s (Available: %d)", b.getBookId(), b.getTitle(), b.getAvailableCopies());
    }

    private String formatAuthor(Author a) {
        return String.format("[%s] %s", a.getAuthorId(), a.getName());
    }

    private String formatPublisher(Publisher p) {
        return String.format("[%s] %s", p.getPublisherId(), p.getName());
    }

    private String formatBorrowRecord(BorrowedBooks r) {
        return String.format("[%s] BookID: %s | Borrowed: %s | Due: %s",
                r.getBorrowedId(), r.getCopyId(), r.getBorrowDate(), r.getDueDate());
    }
}
