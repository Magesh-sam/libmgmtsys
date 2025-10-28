package src.view;

import src.controller.AuthorController;
import src.controller.BookController;
import src.controller.CategoryController;
import src.controller.PublisherController;
import src.model.pojo.Book;
import src.service.BookService;
import src.utils.InputUtil;

import java.util.List;
import java.util.Scanner;

public class BookView {
    private final Scanner sc = new Scanner(System.in);

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
                default -> System.out.println("Invalid choice! Enter between 1–6.");
            }
        }
    }

    private void addBook() {
        System.out.println("\n--- Add New Book ---");
        System.out.print("Enter Title: ");
        String title = InputUtil.getStringInput();
        System.out.print("Enter Author: ");
        String author = InputUtil.getStringInput();
        System.out.print("Enter Publisher: ");
        String publisher = InputUtil.getStringInput();
        System.out.print("Enter Category: ");
        String category = InputUtil.getStringInput();
        System.out.print("Enter Book Language: ");
        String language = InputUtil.getStringInput();
        System.out.print("Enter Quantity: ");
        Double price = InputUtil.getDoubleInput();

        Book b = new Book(title, language, price);
        
        int publisherId = new PublisherController().getPublisherByName(publisher).getPublisherId();
        int categoryId = new CategoryController().getCategoryByName(category).getCategoryId();
        b.setCategoryId(categoryId);
        b.setPublisherId(publisherId);
        // if()
        int bookId = new BookController().createBook(b);

        if (bookId > 0) {
            System.out.println("Book added successfully!");
        } else {
            System.out.println("Failed to add book. Please try again.");
        }
    }

    private void viewAllBooks() {
        System.out.println("\n--- Book List ---");
        List<Book> books = bookService.getAllBooks();

        if (books.isEmpty()) {
            System.out.println("No books found in the library.");
            return;
        }

        for (Book b : books) {
            System.out.println(b);
        }
    }

    private void updateBook() {
        System.out.println("\n--- Update Book ---");
        System.out.print("Enter Book ID to update: ");
        int bookId = Integer.parseInt(sc.nextLine().trim());

        Book existing = bookService.getBookById(bookId);
        if (existing == null) {
            System.out.println("Book not found!");
            return;
        }

        System.out.print("Enter new Title (" + existing.getTitle() + "): ");
        String title = sc.nextLine().trim();
        System.out.print("Enter new Author (" + existing.getAuthor() + "): ");
        String author = sc.nextLine().trim();
        System.out.print("Enter new Publisher (" + existing.getPublisher() + "): ");
        String publisher = sc.nextLine().trim();
        System.out.print("Enter new Category (" + existing.getCategory() + "): ");
        String category = sc.nextLine().trim();
        System.out.print("Enter new Quantity (" + existing.getQuantity() + "): ");
        String quantityInput = sc.nextLine().trim();

        if (!title.isEmpty())
            existing.setTitle(title);
        if (!author.isEmpty())
            existing.setAuthor(author);
        if (!publisher.isEmpty())
            existing.setPublisher(publisher);
        if (!category.isEmpty())
            existing.setCategory(category);
        if (!quantityInput.isEmpty())
            existing.setQuantity(Integer.parseInt(quantityInput));

        boolean success = bookService.updateBook(existing);

        if (success) {
            System.out.println("Book updated successfully!");
        } else {
            System.out.println("Update failed!");
        }
    }

    private void deleteBook() {
        System.out.println("\n--- Delete Book ---");
        System.out.print("Enter Book ID to delete: ");
        int bookId = Integer.parseInt(sc.nextLine().trim());

        boolean success = bookService.deleteBook(bookId);

        if (success) {
            System.out.println("Book deleted successfully!");
        } else {
            System.out.println("Book not found or could not be deleted.");
        }
    }

    private void searchBookByTitle() {
        System.out.println("\n--- Search Book ---");
        System.out.print("Enter title keyword: ");
        String keyword = sc.nextLine().trim();

        List<Book> results = bookService.searchBookByTitle(keyword);

        if (results.isEmpty()) {
            System.out.println("No books found matching \"" + keyword + "\".");
            return;
        }

        for (Book b : results) {
            System.out.println(b);
        }
    }
}
