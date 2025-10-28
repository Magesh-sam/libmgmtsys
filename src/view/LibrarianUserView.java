package src.view;

import src.controller.BookController;
import src.utils.InputUtil;

public class LibrarianUserView {

    public void displayLibrarianMenu() {
        while (true) {
            System.out.println("\n===== Librarian Menu =====");
            System.out.println("1. Manage Books");
            System.out.println("2. Manage Book Copies");
            System.out.println("3. Manage Authors");
            System.out.println("4. Manage Categories");
            System.out.println("5. Manage Publishers");
            System.out.println("6. Manage Borrowings");
            System.out.println("7. Manage Fines");
            System.out.println("8. Manage Members");
            System.out.println("9. Logout");
            System.out.print("Enter your choice: ");

            int choice = InputUtil.getIntInput();

            switch (choice) {
                case 1 -> manageBooks();
                case 2 -> System.out.println("===Book Copies Management===");
                case 3 -> manageAuthors();
                case 4 -> manageCategories();
                case 5 -> managePublishers();
                case 6 -> manageBorrowings();
                case 7 -> manageFines();
                case 8 -> manageMembers();
                case 9 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void manageBooks() {
        System.out.println("===Book Management===");
        while (true) {
            System.out.println("\n--- Book Management ---");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Update Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Back to Librarian Menu");
            System.out.print("Enter your choice: ");

            int choice = InputUtil.getIntInput();

            switch (choice) {
                case 1 -> new BookController().createBook(null);
                case 2 -> System.out.println("View Books functionality");
                case 3 -> System.out.println("Update Book functionality");
                case 4 -> System.out.println("Delete Book functionality");
                case 5 -> {
                    System.out.println("Returning to Librarian Menu...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }

    }

    private void manageAuthors() {
        System.out.println("===Author Management===");

    }

    private void manageCategories() {
        System.out.println("===Category Management===");

    }

    private void managePublishers() {
        System.out.println("===Publisher Management===");
    }

    private void manageBorrowings() {
        System.out.println("===Borrowings Management===");
    }

    private void manageFines() {
        System.out.println("===Fine Management===");
    }

    private void manageMembers() {
        System.out.println("===Member Management===");
    }

}
