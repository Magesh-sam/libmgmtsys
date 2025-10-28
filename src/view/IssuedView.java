package src.view;

import src.model.pojo.IssuedBook;
import src.service.IssuedService;
import java.util.List;
import java.util.Scanner;

public class IssuedView {

    private final Scanner sc = new Scanner(System.in);
    private final IssuedService issuedService = new IssuedService();

    public void display() {
        int choice;

        while (true) {
            System.out.println("\n===== Issued Book Management =====");
            System.out.println("1. Issue a Book");
            System.out.println("2. View All Issued Books");
            System.out.println("3. Update Issued Book Details");
            System.out.println("4. Delete Issued Record");
            System.out.println("5. Search Issued Books by Member ID");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            String input = sc.nextLine().trim();

            if (!input.matches("\\d+")) {
                System.out.println("Invalid input! Please enter a number between 1–6.");
                continue;
            }

            choice = Integer.parseInt(input);
            switch (choice) {
                case 1 -> issueBook();
                case 2 -> viewAllIssuedBooks();
                case 3 -> updateIssuedBook();
                case 4 -> deleteIssuedRecord();
                case 5 -> searchIssuedByMemberId();
                case 6 -> {
                    System.out.println("Exiting Issued Book Management...");
                    return;
                }
                default -> System.out.println("Invalid choice! Enter between 1–6.");
            }
        }
    }

    // === CRUD Methods ===

    private void issueBook() {
        System.out.println("\n--- Issue a Book ---");
        System.out.print("Enter Member ID: ");
        String memberStr = sc.nextLine().trim();
        if (!memberStr.matches("\\d+")) {
            System.out.println("Invalid Member ID!");
            return;
        }
        int memberId = Integer.parseInt(memberStr);

        System.out.print("Enter Book Copy ID: ");
        String copyStr = sc.nextLine().trim();
        if (!copyStr.matches("\\d+")) {
            System.out.println("Invalid Book Copy ID!");
            return;
        }
        int copyId = Integer.parseInt(copyStr);

        System.out.print("Enter Issue Date (YYYY-MM-DD): ");
        String issueDate = sc.nextLine().trim();

        System.out.print("Enter Due Date (YYYY-MM-DD): ");
        String dueDate = sc.nextLine().trim();

        IssuedBook issue = new IssuedBook();
        issue.setMemberId(memberId);
        issue.setCopyId(copyId);
        issue.setIssueDate(issueDate);
        issue.setDueDate(dueDate);

        boolean success = issuedService.issueBook(issue);
        if (success) {
            System.out.println("Book issued successfully!");
        } else {
            System.out.println("Failed to issue book. Try again.");
        }
    }

    private void viewAllIssuedBooks() {
        System.out.println("\n--- List of Issued Books ---");
        List<IssuedBook> issuedList = issuedService.getAllIssuedBooks();
        if (issuedList.isEmpty()) {
            System.out.println("No issued records found.");
            return;
        }

        for (IssuedBook i : issuedList) {
            System.out.println(formatIssuedBook(i));
        }
    }

    private void updateIssuedBook() {
        System.out.println("\n--- Update Issued Book ---");
        System.out.print("Enter Issued ID to update: ");
        String idStr = sc.nextLine().trim();
        if (!idStr.matches("\\d+")) {
            System.out.println("Invalid Issued ID!");
            return;
        }
        int issueId = Integer.parseInt(idStr);

        IssuedBook existing = issuedService.getIssuedBookById(issueId);
        if (existing == null) {
            System.out.println("Issued record not found!");
            return;
        }

        System.out.println("Current Due Date: " + existing.getDueDate());
        System.out.print("Enter new due date (YYYY-MM-DD, leave blank to keep current): ");
        String newDue = sc.nextLine().trim();
        if (!newDue.isEmpty()) existing.setDueDate(newDue);

        System.out.println("Current Return Date: " + existing.getReturnDate());
        System.out.print("Enter new return date (YYYY-MM-DD, leave blank to keep current): ");
        String newReturn = sc.nextLine().trim();
        if (!newReturn.isEmpty()) existing.setReturnDate(newReturn);

        boolean success = issuedService.updateIssuedBook(existing);
        if (success) {
            System.out.println("Issued record updated successfully!");
        } else {
            System.out.println("Failed to update record!");
        }
    }

    private void deleteIssuedRecord() {
        System.out.println("\n--- Delete Issued Record ---");
        System.out.print("Enter Issued ID to delete: ");
        String idStr = sc.nextLine().trim();
        if (!idStr.matches("\\d+")) {
            System.out.println("Invalid Issued ID!");
            return;
        }
        int issueId = Integer.parseInt(idStr);

        boolean success = issuedService.deleteIssuedBook(issueId);
        if (success) {
            System.out.println("Issued record deleted successfully!");
        } else {
            System.out.println("Failed to delete record. Try again.");
        }
    }

    private void searchIssuedByMemberId() {
        System.out.println("\n--- Search Issued Books by Member ID ---");
        System.out.print("Enter Member ID: ");
        String memberStr = sc.nextLine().trim();
        if (!memberStr.matches("\\d+")) {
            System.out.println("Invalid Member ID!");
            return;
        }
        int memberId = Integer.parseInt(memberStr);

        List<IssuedBook> issuedList = issuedService.getIssuedBooksByMemberId(memberId);
        if (issuedList.isEmpty()) {
            System.out.println("No issued books found for this member.");
            return;
        }

        for (IssuedBook i : issuedList) {
            System.out.println(formatIssuedBook(i));
        }
    }

    // === Helper Method ===
    private String formatIssuedBook(IssuedBook i) {
        return String.format("Issued ID: %d | Member ID: %d | Copy ID: %d | Issue Date: %s | Due Date: %s | Return Date: %s",
                i.getIssuedId(),
                i.getMemberId(),
                i.getCopyId(),
                i.getIssueDate(),
                i.getDueDate(),
                (i.getReturnDate() == null || i.getReturnDate().isEmpty()) ? "Not Returned" : i.getReturnDate());
    }
}
