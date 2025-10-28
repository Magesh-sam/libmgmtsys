package src.view;

import src.model.pojo.Author;
import src.service.AuthorService;
import java.util.List;
import java.util.Scanner;

public class AuthorView {

    private final Scanner sc = new Scanner(System.in);
    private final AuthorService service;

    public AuthorView(AuthorService service) {
        this.service = service;
    }

    public void display() {
        while (true) {
           System.out.println("\n===== Author Management =====");
            System.out.println("1. List All Authors");
            System.out.println("2. Add New Author");
            System.out.println("3. Update Author");
            System.out.println("4. Delete Author");
            System.out.println("5. Search Author by Name");
            System.out.println("6. Exit to Main Menu");
            System.out.print("Enter your choice: ");

            String input = sc.nextLine().trim();
            if (!input.matches("\\d+")) {
                System.out.println("Invalid input. Please enter a number between 1–6.");
                continue;
            }

            int choice = Integer.parseInt(input);
            switch (choice) {
                case 1 -> listAllAuthors();
                case 2 -> addAuthor();
                case 3 -> updateAuthor();
                case 4 -> deleteAuthor();
                case 5 -> searchAuthorByName();
                case 6 -> {
                    System.out.println("Returning to main menu...");
                    return;
                }
                default -> System.out.println("Please choose a valid option (1–6).");
            }
        }
    }

    // === CRUD Methods ===
    private void listAllAuthors() {
        try {
            List<Author> authors = service.getAllAuthors();
            if (authors.isEmpty()) {
                System.out.println("No authors found.");
                return;
            }
            System.out.println("\n--- List of Authors ---");
            for (Author a : authors) {
                System.out.println(formatAuthor(a));
            }
        } catch (Exception e) {
            System.out.println("Error retrieving authors: " + e.getMessage());
        }
    }

    private void addAuthor() {
        try {
            System.out.print("Enter author name: ");
            String name = sc.nextLine().trim();
            if (name.isBlank()) {
                System.out.println("Author name cannot be empty.");
                return;
            }

            System.out.print("Enter author biography (optional): ");
            String bio = sc.nextLine().trim();

            Author author = new Author();
            author.setName(name);
            author.setBiography(bio);

            int authorId = service.addAuthor(author);
            if (authorId > 0) {
                System.out.println("Author added successfully with ID: " + authorId);
            } else {
                System.out.println("Failed to add author. Try again.");
            }
        } catch (Exception e) {
            System.out.println("Error adding author: " + e.getMessage());
        }
    }

    private void updateAuthor() {
        try {
            System.out.print("Enter Author ID to update: ");
            String id = sc.nextLine().trim();
            if (!id.matches("\\d+")) {
                System.out.println("Invalid ID format.");
                return;
            }

            Author existing = service.getAuthorById(Integer.parseInt(id));
            if (existing == null) {
                System.out.println("Author not found.");
                return;
            }

            System.out.println("Current Name: " + existing.getName());
            System.out.print("Enter new name (leave blank to keep current): ");
            String newName = sc.nextLine().trim();
            if (!newName.isEmpty()) existing.setName(newName);

            System.out.println("Current Biography: " + existing.getBiography());
            System.out.print("Enter new biography (leave blank to keep current): ");
            String newBio = sc.nextLine().trim();
            if (!newBio.isEmpty()) existing.setBiography(newBio);

            boolean success = service.updateAuthor(existing);
            if (success) {
                System.out.println("Author updated successfully!");
            } else {
                System.out.println("Failed to update author. Try again.");
            }
        } catch (Exception e) {
            System.out.println("Error updating author: " + e.getMessage());
        }
    }

    private void deleteAuthor() {
        try {
            System.out.print("Enter Author ID to delete: ");
            String id = sc.nextLine().trim();
            if (!id.matches("\\d+")) {
                System.out.println("Invalid ID format.");
                return;
            }

            boolean success = service.deleteAuthor(Integer.parseInt(id));
            if (success) {
                System.out.println("Author deleted successfully!");
            } else {
                System.out.println("Failed to delete author. Try again.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting author: " + e.getMessage());
        }
    }

    private void searchAuthorByName() {
        try {
            System.out.print("Enter author name to search: ");
            String name = sc.nextLine().trim();
            if (name.isBlank()) {
                System.out.println("Author name cannot be empty.");
                return;
            }

            List<Author> results = service.searchAuthorsByName(name);
            if (results.isEmpty()) {
                System.out.println("No authors found for name: " + name);
                return;
            }

            System.out.println("\n--- Search Results ---");
            for (Author a : results) {
                System.out.println(formatAuthor(a));
            }
        } catch (Exception e) {
            System.out.println("Error searching authors: " + e.getMessage());
        }
    }

    // === Helper ===
    private String formatAuthor(Author a) {
        return String.format("[%d] %s - %s",
                a.getAuthorId(),
                a.getName(),
                (a.getBiography() == null || a.getBiography().isEmpty()) 
                    ? "No bio available" 
                    : a.getBiography());
    }
}
