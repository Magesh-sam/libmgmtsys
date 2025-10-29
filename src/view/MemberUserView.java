package src.view;

import src.controller.BookController;
import src.controller.BookCopyController;
import src.controller.AuthorController;
import src.controller.CategoryController;
import src.controller.PublisherController;
import src.controller.BorrowedBookController;
import src.model.pojo.*;
import src.utils.InputUtil;

import java.time.LocalDate;
import java.util.List;

public class MemberUserView {

    private final BookController bookController;
    private final AuthorController authorController;
    private final CategoryController categoryController;
    private final PublisherController publisherController;
    private final BorrowedBookController borrowedBookController;
    private final BookCopyController bookCopyController;
    private final AppUser currentUser;

    public MemberUserView(AppUser user) {
        this.bookController = new BookController();
        this.authorController = new AuthorController();
        this.categoryController = new CategoryController();
        this.publisherController = new PublisherController();
        this.borrowedBookController = new BorrowedBookController();
        this.bookCopyController = new BookCopyController();
        this.currentUser = user;
    }

    public void display() {
        int choice = -1;
        while (true) {
            System.out.println("\n===== Member Dashboard =====");
            System.out.println("1. List all Books");
            System.out.println("2. List all Authors");
            System.out.println("3. List all Categories");
            System.out.println("4. List all Publishers");
            System.out.println("5. Search Book by Title");
            System.out.println("6. Search Book by Author");
            System.out.println("7. Search Book by Category");
            System.out.println("8. Search Book by Publisher");
            System.out.println("9. Borrow Book");
            System.out.println("10. Return Book");
            System.out.println("11. Log Out");
            System.out.print("Enter your choice: ");

            choice = InputUtil.getIntInput();

            switch (choice) {
                case 1 -> listAllBooks();
                case 2 -> listAllAuthors();
                case 3 -> listAllCategories();
                case 4 -> listAllPublishers();
                case 5 -> searchBookByTitle();
                case 6 -> searchBookByAuthor();
                case 7 -> searchBookByCategory();
                case 8 -> searchBookByPublisher();
                case 9 -> borrowBook();
                case 10 -> returnBook();
                case 11 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice! Please enter between 1–11.");
            }
        }
    }

    // 1. List all Books
    private void listAllBooks() {
        List<Book> books = bookController.getAllBooks();
        if (books.isEmpty())
            System.out.println("No books available.");
        else
            books.forEach(System.out::println);
    }

    // 2. List all Authors
    private void listAllAuthors() {
        List<Author> authors = authorController.getAllAuthors();
        if (authors.isEmpty())
            System.out.println("No authors found.");
        else
            authors.forEach(System.out::println);
    }

    // 3. List all Categories
    private void listAllCategories() {
        List<Category> categories = categoryController.getAllCategories();
        if (categories.isEmpty())
            System.out.println("No categories found.");
        else
            categories.forEach(System.out::println);
    }

    // 4. List all Publishers
    private void listAllPublishers() {
        List<Publisher> publishers = publisherController.getAllPublishers();
        if (publishers.isEmpty())
            System.out.println("No publishers found.");
        else
            publishers.forEach(System.out::println);
    }

    // 5. Search by Title
    private void searchBookByTitle() {
        System.out.print("Enter Book Title: ");
        String title = InputUtil.getStringInput();
        List<Book> results = bookController.getBooksByTitle(title);
        if (results.isEmpty())
            System.out.println("No book found with that title.");
        else
            results.forEach(System.out::println);
    }

    // 6. Search by Author
    private void searchBookByAuthor() {
        System.out.print("Enter Author Name: ");
        String authorName = InputUtil.getStringInput();
        List<Book> results = bookController.getBooksByAuthorName(authorName);
        if (results.isEmpty())
            System.out.println("No books found for this author.");
        else
            results.forEach(System.out::println);
    }

    // 7. Search by Category
    private void searchBookByCategory() {
        System.out.print("Enter Category Name: ");
        String category = InputUtil.getStringInput();
        List<Book> results = bookController.getBooksByCategory(category);
        if (results.isEmpty())
            System.out.println("No books found in this category.");
        else
            results.forEach(System.out::println);
    }

    // 8. Search by Publisher
    private void searchBookByPublisher() {
        System.out.print("Enter Publisher Name: ");
        String publisher = InputUtil.getStringInput();
        List<Book> results = bookController.getBooksByPublisher(publisher);
        if (results.isEmpty())
            System.out.println("No books found for this publisher.");
        else
            results.forEach(System.out::println);
    }

    // 9. Borrow Book
    private void borrowBook() {
        viewAllBookCopies();
        System.out.print("Enter Book ID to borrow: ");
        int bookId = InputUtil.getIntInput();

        BorrowedBooks borrowed = new BorrowedBooks();
        borrowed.setCopyId(bookId);
        borrowed.setMemberId(currentUser.getUserId());
        borrowed.setBorrowDate(LocalDate.now());
        borrowed.setReturnDate(null);

        int id = borrowedBookController.createBorrowedBook(borrowed);
        if (id > 0)
            System.out.println("Book borrowed successfully! Borrow ID: " + id);
        else
            System.out.println("Failed to borrow book. It may not be available.");
    }

    // 10. Return Book
    private void returnBook() {
        System.out.print("Enter Borrowed ID to return: ");
        int borrowedId = InputUtil.getIntInput();

        boolean success = borrowedBookController.updateReturnDate(borrowedId, LocalDate.now());
        if (success)
            System.out.println("Book returned successfully!");
        else
            System.out.println("Invalid Borrowed ID or already returned.");
    }

    private void viewAllBookCopies() {
        System.out.println("\n--- Book Copy List ---");
        List<BookCopy> copies = bookCopyController.getAllBookCopies();

        if (copies.isEmpty()) {
            System.out.println("No book copies found in the system.");
            return;
        }

        listAllBooks();
        for (BookCopy bc : copies) {
            System.out.println(bc);
        }
    }
}
