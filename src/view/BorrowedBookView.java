package src.view;

import src.controller.BookController;
import src.controller.BorrowedBookController;
import src.controller.MemberController;
import src.model.pojo.Book;
import src.model.pojo.BorrowedBooks;
import src.model.pojo.Member;
import src.utils.InputUtil;

import java.time.LocalDate;
import java.util.List;

public class BorrowedBookView {

    private final BorrowedBookController borrowedBookController;
    private final BookController bookController;
    private final MemberController memberController;

    public BorrowedBookView() {
        this.borrowedBookController = new BorrowedBookController();
        this.bookController = new BookController();
        this.memberController = new MemberController();
    }

    public void display() {
        int choice = -1;

        while (true) {
            System.out.println("\n===== Borrowed Book Management =====");
            System.out.println("1. Borrow a Book");
            System.out.println("2. View All Borrowed Books");
            System.out.println("3. Update Borrowed Book Details");
            System.out.println("4. Delete Borrowed Record");
            System.out.println("5. Search Borrowed Books by Member ID");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = InputUtil.getIntInput();

            switch (choice) {
                case 1 -> borrowBook();
                case 2 -> viewAllBorrowedBooks();
                case 3 -> updateBorrowedBook();
                case 4 -> deleteBorrowedBook();
                case 5 -> searchBorrowedBooksByMemberId();
                case 6 -> {
                    System.out.println("Exiting Borrowed Book Management...");
                    return;
                }
                default -> System.out.println("Invalid choice! Enter between 1 to 6.");
            }
        }
    }

    // 1. Borrow a Book
    private void borrowBook() {
        System.out.println("\n--- Borrow New Book ---");
        printMembers();
        System.out.print("Enter Member ID: ");
        int memberId = InputUtil.getIntInput();

        printBooks();
        System.out.print("Enter Book ID to Borrow: ");
        int bookId = InputUtil.getIntInput();

        LocalDate borrowDate = LocalDate.now();
        System.out.print("Enter Expected Return Date (YYYY-MM-DD): ");
        LocalDate returnDate = LocalDate.parse(InputUtil.getStringInput());

        BorrowedBooks borrowed = new BorrowedBooks();
        borrowed.setMemberId(memberId);
        borrowed.setCopyId(bookId);
        borrowed.setBorrowDate(borrowDate);
        borrowed.setReturnDate(returnDate);

        int id = borrowedBookController.createBorrowedBook(borrowed);
        if (id > 0)
            System.out.println("Book borrowed successfully! Borrow ID: " + id);
        else
            System.out.println("Failed to borrow book. Please check details.");
    }

    // 2. View All Borrowed Books
    private void viewAllBorrowedBooks() {
        System.out.println("\n--- All Borrowed Books ---");
        List<BorrowedBooks> borrowedBooks = borrowedBookController.getAllBorrowedBooks();
        if (borrowedBooks.isEmpty()) {
            System.out.println("No borrowed books found.");
            return;
        }

        for (BorrowedBooks b : borrowedBooks) {
            System.out.println(b);
        }
    }

    // 3. Update Borrowed Book Details
    private void updateBorrowedBook() {
        System.out.println("\n--- Update Borrowed Book ---");
        viewAllBorrowedBooks();
        System.out.print("Enter Borrowed ID to Update: ");
        int borrowedId = InputUtil.getIntInput();

        BorrowedBooks existing = borrowedBookController.getBorrowedBookById(borrowedId);
        if (existing == null) {
            System.out.println("Borrowed record not found!");
            return;
        }

        System.out.print("Enter New Return Date (YYYY-MM-DD) or press Enter to skip: ");
        String dateInput = InputUtil.getStringInput();
        if (!dateInput.isEmpty()) {
            existing.setReturnDate(LocalDate.parse(dateInput));
        }

        boolean success = borrowedBookController.updateReturnDate(borrowedId, existing.getReturnDate());
        if (success)
            System.out.println("Borrowed book updated successfully!");
        else
            System.out.println("Failed to update borrowed record.");
    }

    // 4. Delete Borrowed Record
    private void deleteBorrowedBook() {
        System.out.println("\n--- Delete Borrowed Record ---");
        viewAllBorrowedBooks();
        System.out.print("Enter Borrowed ID to Delete: ");
        int borrowedId = InputUtil.getIntInput();

        boolean success = borrowedBookController.deleteBorrowedBook(borrowedId);
        if (success)
            System.out.println("Borrowed record deleted successfully!");
        else
            System.out.println("Failed to delete borrowed record.");
    }

    // 5. Search Borrowed Books by Member ID
    private void searchBorrowedBooksByMemberId() {
        System.out.println("\n--- Search Borrowed Books ---");
        printMembers();
        System.out.print("Enter Member ID: ");
        int memberId = InputUtil.getIntInput();

        List<BorrowedBooks> results = borrowedBookController.getBorrowedBooksByMemberId(memberId);
        if (results == null || results.isEmpty()) {
            System.out.println("No borrowed books found for this member.");
            return;
        }

        for (BorrowedBooks b : results) {
            System.out.println(b);
        }
    }

    private void printMembers() {
        List<Member> members = memberController.getAllMembers();
        if (!members.isEmpty()) {
            System.out.println("\n--- Member List ---");
            for (Member m : members) {
                System.out.println(m);
            }
        } else {
            System.out.println("No members found.");
        }
    }

    // Helper: Print all books
    private void printBooks() {
        List<Book> books = bookController.getAllBooks();
        if (!books.isEmpty()) {
            System.out.println("\n--- Book List ---");
            for (Book b : books) {
                System.out.println(b);
            }
        } else {
            System.out.println("No books found.");
        }
    }
}
