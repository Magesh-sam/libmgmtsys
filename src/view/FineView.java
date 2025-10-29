package src.view;

import src.controller.BorrowedBookController;
import src.controller.FineController;
import src.model.pojo.BorrowedBooks;
import src.model.pojo.Fine;
import src.model.pojo.Fine.FineStatus;
import src.utils.InputUtil;

import java.time.LocalDate;
import java.util.List;

public class FineView {

    private final FineController fineController;
    private BorrowedBookController borrowedBookController;

    public FineView() {
        this.fineController = new FineController();
        this.borrowedBookController = new BorrowedBookController();
    }

    public void display() {
        int choice = -1;

        while (true) {
            System.out.println("\n===== Fine Management =====");
            System.out.println("1. Add Fine");
            System.out.println("2. View All Fines");
            System.out.println("3. Update Fine Status");
            System.out.println("4. Delete Fine");
            System.out.println("5. Search Fine by ID");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = InputUtil.getIntInput();

            switch (choice) {
                case 1 -> addFine();
                case 2 -> viewAllFines();
                case 3 -> updateFineStatus();
                case 4 -> deleteFine();
                case 5 -> searchFineById();
                case 6 -> {
                    System.out.println("Exiting Fine Management...");
                    return;
                }
                default -> System.out.println("Invalid choice! Enter between 1 to 6.");
            }
        }
    }

    private void addFine() {
        System.out.println("\n--- Add New Fine ---");
        printBorrowedBooks();
        System.out.print("Enter Borrowed ID: ");
        int borrowedId = InputUtil.getIntInput();

        System.out.print("Enter Fine Amount: ");
        double amount = InputUtil.getDoubleInput();

        LocalDate issueDate = LocalDate.now();
        System.out.println("Fine Issue Date: " + issueDate);

        System.out.print("Enter Fine Status (paid/unpaid): (optional) ");
        String statusInput = InputUtil.getStringInput().toLowerCase();
        FineStatus status = statusInput.equals("paid") ? FineStatus.paid : FineStatus.unpaid;

        Fine fine = new Fine(borrowedId, amount, issueDate, status);
        int fineId = fineController.createFine(fine);

        if (fineId > 0) {
            System.out.println("Fine added successfully!");
        } else {
            System.out.println("Failed to add fine. Please try again.");
        }
    }

    private void viewAllFines() {
        System.out.println("\n--- Fine List ---");
        List<Fine> fines = fineController.getAllFines();

        if (fines == null || fines.isEmpty()) {
            System.out.println("No fines found in the system.");
            return;
        }

        for (Fine fine : fines) {
            System.out.println(fine);
        }
    }

    private void updateFineStatus() {
        System.out.println("\n--- Update Fine Status ---");
        viewAllFines();

        System.out.print("Enter Fine ID to update: ");
        int fineId = InputUtil.getIntInput();

        Fine existing = fineController.getFineById(fineId);
        if (existing == null) {
            System.out.println("Fine not found!");
            return;
        }

        System.out.println("Current Status: " + existing.getStatus());
        System.out.print("Enter New Status (paid/unpaid): ");
        String newStatus = InputUtil.getStringInput().toLowerCase();

        FineStatus status = newStatus.equals("paid") ? FineStatus.paid : FineStatus.unpaid;

        boolean success = fineController.updateFineStatus(fineId, status);

        if (success) {
            System.out.println("Fine status updated successfully!");
        } else {
            System.out.println("Failed to update fine status.");
        }
    }

    private void deleteFine() {
        System.out.println("\n--- Delete Fine ---");
        viewAllFines();
        System.out.print("Enter Fine ID to delete: ");
        int fineId = InputUtil.getIntInput();

        boolean success = fineController.deleteFine(fineId);

        if (success) {
            System.out.println("Fine deleted successfully!");
        } else {
            System.out.println("Fine not found or could not be deleted.");
        }
    }

    private void searchFineById() {
        System.out.println("\n--- Search Fine ---");
        System.out.print("Enter Fine ID: ");
        int fineId = InputUtil.getIntInput();

        Fine fine = fineController.getFineById(fineId);

        if (fine == null) {
            System.out.println("No fine found with ID: " + fineId);
        } else {
            System.out.println(fine);
        }
    }

    private void printBorrowedBooks() {
        List<BorrowedBooks> borrowedBooks = borrowedBookController.getAllBorrowedBooks();
        if (!borrowedBooks.isEmpty()) {
            System.out.println("\n--- Borrowed Books List ---");
            for (BorrowedBooks b : borrowedBooks) {
                System.out.println(b);
            }
        } else {
            System.out.println("No borrowed books found.");
        }
    }
}