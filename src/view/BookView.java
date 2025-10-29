package src.view;

import src.controller.BookController;
import src.controller.CategoryController;
import src.controller.PublisherController;
import src.model.pojo.Book;
import src.model.pojo.Category;
import src.model.pojo.Publisher;
import src.utils.InputUtil;

import java.util.List;

public class BookView {

    private final BookController bookController;
    private final CategoryController categoryController;
    private final PublisherController publisherController;

    public BookView() {
        this.bookController = new BookController();
        this.categoryController = new CategoryController();
        this.publisherController = new PublisherController();

    }

    public void display() {
        int choice = -1;

        while (true) {
            System.out.println("\n===== Book Management =====");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Update Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Search Book by Title");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = InputUtil.getIntInput();

            switch (choice) {
                case 1 -> addBook();
                case 2 -> viewAllBooks();
                case 3 -> updateBook();
                case 4 -> deleteBook();
                case 5 -> searchBookByTitle();
                case 6 -> {
                    System.out.println("Exiting Book Management...");
                    return;
                }
                default -> System.out.println("Invalid choice! Enter between 1 to 6.");
            }
        }
    }

    private void addBook() {
        System.out.println("\n--- Add New Book ---");
        System.out.print("Enter Title: ");
        String title = InputUtil.getStringInput();

        printPublishers();
        System.out.print("Enter Publisher ID: ");

        int publisherId = InputUtil.getIntInput();
        printCategories();
        System.out.print("Enter Category ID: ");
        int categoryId = InputUtil.getIntInput();
        System.out.print("Enter Book Language: ");
        String language = InputUtil.getStringInput();
        System.out.print("Enter Price: ");
        double price = InputUtil.getDoubleInput();

        Book b = new Book(title, language, price);

        b.setPublisherId(publisherId);
        b.setCategoryId(categoryId);
        int bookId = bookController.createBook(b);

        if (bookId > 0) {
            System.out.println("Book added successfully!");
        } else {
            System.out.println("Failed to add book. Please try again.");
        }

    }

    private void updateBook() {
        System.out.println("\n--- Update Book ---");
        viewAllBooks();
        System.out.print("Enter Book ID to update: ");
        int bookId = InputUtil.getIntInput();

        Book existing = bookController.getBookById(bookId);
        if (existing == null) {
            System.out.println("Book not found!");
            return;
        }

        System.out.println("Press Enter to leave the string field unchanged.");
        System.out.println("Press Enter 0 to leave the number field unchanged.");

        System.out.print("Enter Title: ");
        String title = InputUtil.readRawString();
        printPublishers();

        System.out.print("Enter Publisher ID: ");

        int publisherId = InputUtil.getIntInput();
        printCategories();
        System.out.print("Enter Category ID: ");
        int categoryId = InputUtil.getIntInput();
        System.out.print("Enter Book Language: ");
        String language = InputUtil.readRawString();
        System.out.print("Enter Price: ");
        double price = InputUtil.getDoubleInput();

        title = title.isEmpty() ? existing.getTitle() : title;
        publisherId = publisherId == 0 ? existing.getPublisherId() : publisherId;
        categoryId = categoryId == 0 ? existing.getCategoryId() : categoryId;
        language = language.isEmpty() ? existing.getLanguage() : language;
        price = price <= 0 ? existing.getPrice() : price;

        existing.setTitle(title);
        existing.setPublisherId(publisherId);
        existing.setCategoryId(categoryId);
        existing.setLanguage(language);
        existing.setPrice(price);

        boolean success = bookController.updateBook(existing);

        if (success) {
            System.out.println("Book updated successfully!");
        } else {
            System.out.println("Book not found or could not be updated.");
        }
    }

    private void deleteBook() {
        System.out.println("\n--- Delete Book ---");
        System.out.print("Enter Book ID to delete: ");
        int bookId = InputUtil.getIntInput();

        boolean success = bookController.deleteBook(bookId);

        if (success) {
            System.out.println("Book deleted successfully!");
        } else {
            System.out.println("Book not found or could not be deleted.");
        }
    }

    private void searchBookByTitle() {
        System.out.println("\n--- Search Book ---");
        System.out.print("Enter title keyword: ");
        String keyword = InputUtil.getStringInput();

        List<Book> results = bookController.getBooksByTitle(keyword);

        if (results.isEmpty()) {
            System.out.println("No books found matching \"" + keyword + "\".");
            return;
        }

        for (Book b : results) {
            System.out.println(b);
        }
    }

    private void viewAllBooks() {
        System.out.println("\n--- Book List ---");
        List<Book> books = bookController.getAllBooks();

        if (books.isEmpty()) {
            System.out.println("No books found in the library.");
            return;
        }

        for (Book b : books) {
            System.out.println(b);
        }
    }

    private void printPublishers() {

        List<Publisher> publishers = publisherController.getAllPublishers();
        if (!publishers.isEmpty()) {

            System.out.println("\n--- List of Publishers ---");
            for (Publisher publisher : publishers) {
                System.out.println(publisher);
            }
        } else {
            System.out.println("No publishers found in the system. Add one first.");
        }
    }

    private void printCategories() {

        List<Category> categories = categoryController.getAllCategories();
        if (!categories.isEmpty()) {

            System.out.println("\n--- List of Categories ---");
            for (Category category : categories) {
                System.out.println(category);
            }
        } else {
            System.out.println("No categories found in the system. Add one first.");
        }
    }

}
