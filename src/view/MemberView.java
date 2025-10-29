package src.view;

import java.util.List;
import java.util.Scanner;

import src.controller.BookController;
import src.controller.AuthorController;
import src.controller.CategoryController;
import src.controller.PublisherController;
import src.controller.BorrowedBookController;

import src.model.pojo.AppUser;
import src.model.pojo.Book;
import src.model.pojo.Author;
import src.model.pojo.Category;
import src.model.pojo.Publisher;
import src.model.pojo.BorrowedBooks;

import src.utils.InputUtil;

public class MemberView {

    private final BookController bookController;
    private final AuthorController authorController;
    private final CategoryController categoryController;
    private final PublisherController publisherController;
    private final BorrowedBookController borrowedBookController;

    private final AppUser currentUser;

    public MemberView(AppUser user) {
        this.currentUser = user;
        this.bookController = new BookController();
        this.authorController = new AuthorController();
        this.categoryController = new CategoryController();
        this.publisherController = new PublisherController();
        this.borrowedBookController = new BorrowedBookController();
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n===== Member Menu =====");
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

            int choice = InputUtil.getIntInput();

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
                    System.out.println("Logging out... Goodbye, " + currentUser.getName() + "!");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // 1. List all books
    private void listAllBooks() {
        List<Book> books = bookController.getAllBooks();
        if (books == null || books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }
        System.out.println("\n--- List of Books ---");
        for (Book book : books) {
            System.out.println("Book ID: " + book.getBookId() + " | Title: " + book.getTitle() +
                    " | Language: " + book.getLanguage() + " | Price: " + book.getPrice());
        }
    }

    // 2. List all authors
    private void listAllAuthors() {
        List<Author> authors = authorController.getAllAuthors();
        if (authors == null || authors.isEmpty()) {
            System.out.println("No authors found.");
            return;
        }
        System.out.println("\n--- List of Authors ---");
        for (Author author : authors) {
            System.out.println("Author ID: " + author.getAuthorId() + " | Name: " + author.getName());
        }
    }

    // 3. List all categories
    private void listAllCategories() {
        List<Category> categories = categoryController.getAllCategories();
        if (categories == null || categories.isEmpty()) {
            System.out.println("No categories found.");
            return;
        }
        System.out.println("\n--- List of Categories ---");
        for (Category cat : categories) {
            System.out.println("Category ID: " + cat.getCategoryId() + " | Name: " + cat.getName());
        }
    }

    // 4. List all publishers
    private void listAllPublishers() {
        List<Publisher> publishers = publisherController.getAllPublishers();
        if (publishers == null || publishers.isEmpty()) {
            System.out.println("No publishers found.");
            return;
        }
        System.out.println("\n--- List of Publishers ---");
        for (Publisher pub : publishers) {
            System.out.println("Publisher ID: " + pub.getPublisherId() + " | Name: " + pub.getName());
        }
    }

    // 5. Search by title
    private void searchBookByTitle() {
        System.out.print("Enter book title: ");
        String title = InputUtil.getStringInput();
        List<Book> books = bookController.getBooksByTitle(title);
        if (books == null || books.isEmpty()) {
            System.out.println("No books found with title: " + title);
            return;
        }
        books.forEach(book -> System.out.println("Book ID: " + book.getBookId() + " | Title: " + book.getTitle()));
    }

    // 6. Search by author
    private void searchBookByAuthor() {
        System.out.print("Enter author name: ");
        String authorName = InputUtil.getStringInput();
        List<Book> books = bookController.getBooksByAuthorName(authorName);
        if (books == null || books.isEmpty()) {
            System.out.println("No books found for author: " + authorName);
            return;
        }
        books.forEach(book -> System.out.println("Book ID: " + book.getBookId() + " | Title: " + book.getTitle()));
    }

    // 7. Search by category
    private void searchBookByCategory() {
        System.out.print("Enter category name: ");
        String categoryName = InputUtil.getStringInput();
        List<Book> books = bookController.getBooksByCategory(categoryName);
        if (books == null || books.isEmpty()) {
            System.out.println("No books found in category: " + categoryName);
            return;
        }
        books.forEach(book -> System.out.println("Book ID: " + book.getBookId() + " | Title: " + book.getTitle()));
    }

    // 8. Search by publisher
    private void searchBookByPublisher() {
        System.out.print("Enter publisher name: ");
        String publisherName = InputUtil.getStringInput();
        List<Book> books = bookController.getBooksByPublisher(publisherName);
        if (books == null || books.isEmpty()) {
            System.out.println("No books found for publisher: " + publisherName);
            return;
        }
        books.forEach(book -> System.out.println("Book ID: " + book.getBookId() + " | Title: " + book.getTitle()));
    }

    // 9. Borrow book
    private void borrowBook() {
        System.out.print("Enter Book ID to borrow: ");
        int bookId = InputUtil.getIntInput();
        boolean result = borrowedBookController.createBorrowedBook(new BorrowedBooks());
        if (result)
            System.out.println("Book borrowed successfully!");
        else
            System.out.println("Failed to borrow book. It might already be borrowed.");
    }

    // 10. Return book
    private void returnBook() {
        System.out.print("Enter Borrow ID to return: ");
        int borrowId = InputUtil.getIntInput();
        boolean result = borrowedBookController.returnBook(borrowId);
        if (result)
            System.out.println("Book returned successfully!");
        else
            System.out.println("Failed to return book. Check the borrow ID again.");
    }
}
