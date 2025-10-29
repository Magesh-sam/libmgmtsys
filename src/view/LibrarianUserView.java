package src.view;

import src.utils.InputUtil;

public class LibrarianUserView {

    public void display() {
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
            System.out.println("9. Manage BookAuhtor Links");
            System.out.println("10. Logout");
            System.out.print("Enter your choice: ");

            int choice = InputUtil.getIntInput();

            switch (choice) {
                case 1 -> new BookView().display();
                case 2 -> new BookCopyView().display();
                case 3 -> new AuthorView().display();
                case 4 -> new CategoryView().display();
                case 5 -> new PublisherView().display();
                case 6 -> new BorrowedBookView().display();
                case 7 -> new FineView().display();
                case 8 -> new MemberView().display();
                case 9 -> new BookAuthorView().display();
                case 10 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

}
