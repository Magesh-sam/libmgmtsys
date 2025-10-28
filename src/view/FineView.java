package src.view;

import src.model.pojo.Fine;
import src.service.FineService;
import java.util.List;
import java.util.Scanner;

public class FineView {
    private final Scanner sc = new Scanner(System.in);
    private final FineService fineService = new FineService();

    public void display() {
        int choice;

        while (true) {
            System.out.println("\n===== Fine Management =====");
            System.out.println("1. Add Fine");
            System.out.println("2. View All Fines");
            System.out.println("3. Update Fine");
            System.out.println("4. Delete Fine");
            System.out.println("5. Search Fine by Member ID");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            String input = sc.nextLine().trim();

            if (!input.matches("\\d+")) {
                System.out.println("Invalid input! Please enter a number (1–6).");
                continue;
            }

            choice = Integer.parseInt(input);

            switch (choice) {
                case 1 -> addFine();
                case 2 -> viewAllFines();
                case 3 -> updateFine();
                case 4 -> deleteFine();
                case 5 -> searchFineByMemberId();
                case 6 -> {
                    System.out.println("Exiting Fine Management...");
                    return;
                }
                default -> System.out.println("Invalid choice! Enter between 1–6.");
            }
        }
    }

    private void addFine() {
        System.out.println("\n--- Add New Fine ---");
        System.out.print("Enter Member ID: ");
        String memberIdStr = sc.nextLine().trim();
        if (!memberIdStr.matches("\\d+")) {
            System.out.println("Invalid Member ID!");
            return;
        }
        int memberId = Integer.parseInt(memberIdStr);

        System.out.print("Enter Book ID: ");
        String bookIdStr = sc.nextLine().trim();
        if (!bookIdStr.matches("\\d+")) {
            System.out.println("Invalid Book ID!");
            return;
        }
        int bookId = Integer.parseInt(bookIdStr);

        System.out.print("Enter Fine Amount: ");
        String amountStr = sc.nextLine().trim();
        if (!amountStr.matches("\\d+(\\.\\d+)?")) {
            System.out.println("Invalid amount format!");
            return;
        }
        double amount = Double.parseDouble(amountStr);

        System.out.print("Enter Fine Reason: ");
        String reason = sc.nextLine().trim();

        Fine fine = new Fine();
        fine.setMemberId(memberId);
        fine.setBookId(bookId);
        fine.setAmount(amount);
        fine.setReason(reason);

        boolean success = fineService.addFine(fine);
        if (success) {
            System.out.println("Fine added successfully!");
        } else {
            System.out.println("Failed to add fine. Try again.");
        }
    }

    private void viewAllFines() {
        System.out.println("\n--- List of Fines ---");
        List<Fine> fines = fineService.getAllFines();
        if (fines.isEmpty()) {
            System.out.println("No fines found.");
            return;
        }

        for (Fine f : fines) {
            System.out.println(formatFine(f));
        }
    }

    private void updateFine() {
        System.out.println("\n--- Update Fine ---");
        System.out.print("Enter Fine ID to update: ");
        String fineIdStr = sc.nextLine().trim();
        if (!fineIdStr.matches("\\d+")) {
            System.out.println("Invalid Fine ID!");
            return;
        }
        int fineId = Integer.parseInt(fineIdStr);

        Fine existing = fineService.getFineById(fineId);
        if (existing == null) {
            System.out.println("Fine not found!");
            return;
        }

        System.out.println("Current Amount: " + existing.getAmount());
        System.out.print("Enter new amount (leave blank to keep current): ");
        String newAmountStr = sc.nextLine().trim();
        if (!newAmountStr.isEmpty() && newAmountStr.matches("\\d+(\\.\\d+)?")) {
            existing.setAmount(Double.parseDouble(newAmountStr));
        }

        System.out.println("Current Reason: " + existing.getReason());
        System.out.print("Enter new reason (leave blank to keep current): ");
        String newReason = sc.nextLine().trim();
        if (!newReason.isEmpty()) existing.setReason(newReason);

        boolean success = fineService.updateFine(existing);
        if (success) {
            System.out.println("Fine updated successfully!");
        } else {
            System.out.println("Update failed!");
        }
    }

    private void deleteFine() {
        System.out.println("\n--- Delete Fine ---");
        System.out.print("Enter Fine ID to delete: ");
        String fineIdStr = sc.nextLine().trim();
        if (!fineIdStr.matches("\\d+")) {
            System.out.println("Invalid Fine ID!");
            return;
        }
        int fineId = Integer.parseInt(fineIdStr);

        boolean success = fineService.deleteFine(fineId);
        if (success) {
            System.out.println("Fine deleted successfully!");
        } else {
            System.out.println("Failed to delete fine. Try again.");
        }
    }

    private void searchFineByMemberId() {
        System.out.println("\n--- Search Fines by Member ID ---");
        System.out.print("Enter Member ID: ");
        String memberIdStr = sc.nextLine().trim();
        if (!memberIdStr.matches("\\d+")) {
            System.out.println("Invalid Member ID!");
            return;
        }
        int memberId = Integer.parseInt(memberIdStr);

        List<Fine> fines = fineService.getFinesByMemberId(memberId);
        if (fines.isEmpty()) {
            System.out.println("No fines found for this member.");
            return;
        }

        for (Fine f : fines) {
            System.out.println(formatFine(f));
        }
    }

    private String formatFine(Fine f) {
        return String.format("Fine ID: %d | Member ID: %d | Book ID: %d | Amount: %.2f | Reason: %s | Date: %s",
                f.getFineId(),
                f.getMemberId(),
                f.getBookId(),
                f.getAmount(),
                f.getReason(),
                f.getFineDate());
    }
}
