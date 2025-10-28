package src.view;

import src.controller.AppUserController;
import src.model.pojo.AppUser;
import src.utils.InputUtil;

public class MainView {
    private final AppUserController userController;

    public MainView() {
        this.userController = new AppUserController();
    }

    public void displayMainMenu() {
        while (true) {
            System.out.println("\n===== Library Management System =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = InputUtil.getIntInput();

            switch (choice) {
                case 1 -> registerUser();
                case 2 -> loginUser();
                case 3 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void registerUser() {
        System.out.println("\n--- Register ---");
        System.out.print("Enter name: ");
        String name = InputUtil.getStringInput();
        System.out.print("Enter email: ");
        String email = InputUtil.getStringInput();
        System.out.print("Enter password: ");
        String password = InputUtil.getStringInput();

        System.out.print("Enter role (1=Member, 2=Librarian, 3=Admin): ");
        int roleId = InputUtil.getIntInput();

        AppUser user = new AppUser();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        int result = userController.createUser(user, roleId);

        if (result > 0)
            System.out.println("Registration successful.");
        else
            System.out.println("Registration failed.");
    }

    private void loginUser() {
        System.out.println("\n--- Login ---");
        System.out.print("Enter email: ");
        String email = InputUtil.getStringInput();
        System.out.print("Enter password: ");
        String password = InputUtil.getStringInput();

        AppUser user = userController.login(email, password);

        if (user == null) {
            System.out.println("Invalid credentials.");
            return;
        }
        System.out.println("Login successful. Welcome, " + user.getName() + "!");

        // switch (user.getRole().getName().toLowerCase()) {
        // case "admin" -> new AdminView(user).showMenu();
        // case "librarian" -> new LibrarianView(user).showMenu();
        // case "member" -> new MemberView(user).showMenu();
        // default -> System.out.println("Unknown role.");
        // }
    }
}
