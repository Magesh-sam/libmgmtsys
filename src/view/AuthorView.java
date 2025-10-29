package src.view;

import src.controller.AuthorController;
import src.model.pojo.Author;
import src.utils.InputUtil;

import java.util.List;

public class AuthorView {

    private final AuthorController authorController;

    public AuthorView() {
        this.authorController = new AuthorController();
    }

    public void display() {
        int choice = -1;

        while (true) {
            System.out.println("\n===== Author Management =====");
            System.out.println("1. Add Author");
            System.out.println("2. View All Authors");
            System.out.println("3. Update Author");
            System.out.println("4. Delete Author");
            System.out.println("5. Search Author by Name");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = InputUtil.getIntInput();

            switch (choice) {
                case 1 -> addAuthor();
                case 2 -> viewAllAuthors();
                case 3 -> updateAuthor();
                case 4 -> deleteAuthor();
                case 5 -> searchAuthorByName();
                case 6 -> {
                    System.out.println("Exiting Author Management...");
                    return;
                }
                default -> System.out.println("Invalid choice! Enter between 1 to 6.");
            }
        }
    }

    private void addAuthor() {
        System.out.println("\n--- Add New Author ---");
        System.out.println("Press Enter to leave the string field unchanged.");
        System.out.print("Enter Author Name: ");
        String name = InputUtil.getStringInput();
        System.out.print("Enter Author Bio: (optional) ");
        String bio = InputUtil.readRawString();
        bio = bio.isEmpty() ? null : bio;
        Author author = new Author(name, bio);
        int authorId = authorController.createAuthor(author);

        if (authorId > 0) {
            System.out.println("Author added successfully!");
        } else {
            System.out.println("Failed to add author. Please try again.");
        }
    }

    private void updateAuthor() {
        System.out.println("\n--- Update Author ---");
        viewAllAuthors();
        System.out.print("Enter Author ID to update: ");
        int authorId = InputUtil.getIntInput();

        Author existing = authorController.getAuthorById(authorId);
        if (existing == null) {
            System.out.println("Author not found!");
            return;
        }

        System.out.println("Press Enter to leave the string field unchanged.");
        System.out.print("Enter Author Name: ");
        String name = InputUtil.readRawString();
        System.out.print("Enter Author Bio: (optional) ");
        String bio = InputUtil.readRawString();

        name = name.isEmpty() ? existing.getName() : name;
        bio = bio.isEmpty() ? existing.getBio() : bio;

        existing.setName(name);
        existing.setBio(bio);

        boolean success = authorController.updateAuthor(existing);

        if (success) {
            System.out.println("Author updated successfully!");
        } else {
            System.out.println("Author not found or could not be updated.");
        }
    }

    private void deleteAuthor() {
        System.out.println("\n--- Delete Author ---");
        viewAllAuthors();
        System.out.print("Enter Author ID to delete: ");
        int authorId = InputUtil.getIntInput();

        boolean success = authorController.deleteAuthor(authorId);

        if (success) {
            System.out.println("Author deleted successfully!");
        } else {
            System.out.println("Author not found or could not be deleted.");
        }
    }

    private void searchAuthorByName() {
        System.out.println("\n--- Search Author ---");
        System.out.print("Enter name keyword: ");
        String keyword = InputUtil.getStringInput();

        Author author = authorController.getAuthorByName(keyword);

        if (author == null) {
            System.out.println("No author found matching \"" + keyword + "\".");
        } else {
            System.out.println(author);
        }
    }

    private void viewAllAuthors() {
        System.out.println("\n--- Author List ---");
        List<Author> authors = authorController.getAllAuthors();

        if (authors.isEmpty()) {
            System.out.println("No authors found in the system. Please add some authors first.");
        }

        for (Author a : authors) {
            System.out.println(a);
        }
    }
}
