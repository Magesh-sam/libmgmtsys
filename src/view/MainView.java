package src.view;

import java.time.LocalDate;

import src.controller.AppUserController;
import src.controller.MemberController;
import src.controller.UserRoleController;
import src.model.pojo.AppUser;
import src.model.pojo.Member;
import src.model.pojo.UserRole;
import src.utils.InputUtil;

public class MainView {
    private final AppUserController userController;
    private final MemberController memberController;

    public MainView() {
        this.userController = new AppUserController();
        this.memberController = new MemberController();
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
        System.out.println("Press enter to skip optional fields");
        System.out.print("Enter name: ");
        String name = InputUtil.getStringInput();
        System.out.print("Enter email: ");
        String email = InputUtil.getStringInput();
        System.out.print("Enter password: ");
        String password = InputUtil.getStringInput();
        System.out.print("Enter address: (optional) ");
        String address = InputUtil.readRawString();
        System.out.print("Enter phone: ");
        long phone = InputUtil.getLongInput();

        address = address.isEmpty() ? null : address;

        UserRole role = new UserRoleController().getUserRoleByName("member");

        AppUser user = new AppUser();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setAddress(address);
        user.setPhone(phone);
        int userId = userController.createUser(user, role.getRoleId());

        if (userId > 0) {
            System.out.println("Registration successful.");
            int memberId = memberController.createMember(userId, new Member(LocalDate.now()));
            if (memberId > 0) {
                System.out.println("Member profile created with ID: " + memberId);
            } else {
                System.out.println("User Created. Failed to create member profile. Contact admin.");
            }
        } else {
            System.out.println("Registration failed.");
        }
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
        UserRole role = new UserRoleController().getUserRoleById(user.getRoleId());

        switch (role.getName().toLowerCase()) {
            // case "admin" -> new AdminView(user).showMenu();
            case "admin" -> new AdminUserView().display();
            case "librarian" -> new LibrarianUserView().display();
            case "member" -> new MemberUserView(user).display();
            // case "member" -> new MemberView(user).showMenu();
            default -> System.out.println("Unknown role.");
        }
    }
}
