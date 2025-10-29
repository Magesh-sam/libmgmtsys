package src.view;

import src.controller.AppUserController;
import src.controller.LibrarianController;
import src.controller.UserRoleController;
import src.model.pojo.AppUser;
import src.model.pojo.Librarian;
import src.model.pojo.UserRole;
import src.utils.InputUtil;

import java.time.LocalDate;
import java.util.List;

public class LibrarianView {

    private final LibrarianController librarianController;
    private final AppUserController userController;

    public LibrarianView() {
        this.librarianController = new LibrarianController();
        this.userController = new AppUserController();
    }

    public void display() {
        while (true) {
            System.out.println("\n===== Librarian Management =====");
            System.out.println("1. Add Librarian");
            System.out.println("2. View All Librarians");
            System.out.println("3. Update Librarian");
            System.out.println("4. Delete Librarian");
            System.out.println("5. Search Librarian by ID");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = InputUtil.getIntInput();

            switch (choice) {
                case 1 -> addLibrarian();
                case 2 -> viewAllLibrarians();
                case 3 -> updateLibrarian();
                case 4 -> deleteLibrarian();
                case 5 -> searchLibrarianById();
                case 6 -> {
                    System.out.println("Exiting Librarian Management...");
                    return;
                }
                default -> System.out.println("Invalid choice! Enter between 1 to 6.");
            }
        }
    }

    private void addLibrarian() {
        System.out.println("\n--- Add Librarian ---");

        System.out.print("Enter full name: ");
        String name = InputUtil.getStringInput();

        System.out.print("Enter email: ");
        String email = InputUtil.getStringInput();

        System.out.print("Enter password: ");
        String password = InputUtil.getStringInput();

        System.out.print("Enter address: (optional) ");
        String address = InputUtil.readRawString();

        System.out.print("Enter phone:");
        long phone = InputUtil.getLongInput();

        address = address.isEmpty() ? null : address;

        LocalDate hireDate = LocalDate.now();
        UserRole role = new UserRoleController().getUserRoleByName("librarian");

        AppUser user = new AppUser();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setAddress(address);
        user.setRoleId(role.getRoleId());
        user.setPhone(phone);

        int userId = userController.createUser(user, role.getRoleId());

        if (userId > 0) {
            Librarian librarian = new Librarian();
            librarian.setUserId(userId);
            librarian.setHireDate(hireDate);

            int librarianId = librarianController.createLibrarian(userId, librarian);

            if (librarianId > 0) {
                System.out.println("Librarian created successfully with ID: " + librarianId);
            } else {
                System.out.println("User created but failed to create librarian profile. Contact admin.");
            }
        } else {
            System.out.println("Failed to create user.");
        }
    }

    private void viewAllLibrarians() {
        System.out.println("\n--- Librarian List ---");

        List<Librarian> librarians = librarianController.getAllLibrarians();
        if (librarians == null || librarians.isEmpty()) {
            System.out.println("No librarians found.");
            return;
        }

        for (Librarian librarian : librarians) {
            System.out.println("ID: " + librarian.getLibrarianId()
                    + ", Name: " + librarian.getName()
                    + ", Hire Date: " + librarian.getHireDate());
        }
    }

    private void updateLibrarian() {
        System.out.println("\n--- Update Librarian ---");
        viewAllLibrarians();

        System.out.print("Enter Librarian ID to update: ");
        int librarianId = InputUtil.getIntInput();

        Librarian existing = librarianController.getLibrarianById(librarianId);
        if (existing == null) {
            System.out.println("Librarian not found!");
            return;
        }

        AppUser existingUser = userController.getUserById(existing.getLibrarianId());
        if (existingUser == null) {
            System.out.println("Associated user record not found. Data might be corrupted.");
            return;
        }

        System.out.println("Press Enter to skip a field.");

        System.out.print("Enter Librarian Name (" + existingUser.getName() + "): ");
        String name = InputUtil.readRawString();

        System.out.print("Enter Librarian Email (" + existingUser.getEmail() + "): ");
        String email = InputUtil.readRawString();

        System.out.print("Enter Address (" + existingUser.getAddress() + "): ");
        String address = InputUtil.readRawString();

        System.out.print("Enter Phone (" + existingUser.getPhone() + "): ");

        long phone = InputUtil.getLongInput();

        System.out.print("Enter new Password (leave blank to keep existing): ");
        String password = InputUtil.readRawString();

        name = name.isEmpty() ? existingUser.getName() : name;
        email = email.isEmpty() ? existingUser.getEmail() : email;
        password = password.isEmpty() ? existingUser.getPassword() : password;
        address = address.isEmpty() ? existingUser.getAddress() : address;
        phone = phone <= 0 ? existing.getPhone() : phone;

        existingUser.setName(name);
        existingUser.setEmail(email);
        existingUser.setPassword(password);
        existingUser.setAddress(address);

        boolean userUpdated = userController.updateUser(existingUser);
        boolean librarianUpdated = librarianController.updateLibrarian(existing);

        if (userUpdated && librarianUpdated) {
            System.out.println("Librarian and user details updated successfully!");
        } else if (userUpdated) {
            System.out.println("User updated, but failed to update librarian details.");
        } else if (librarianUpdated) {
            System.out.println("Librarian updated, but failed to update user details.");
        } else {
            System.out.println("Failed to update both librarian and user details.");
        }
    }

    private void deleteLibrarian() {
        System.out.println("\n--- Delete Librarian ---");
        viewAllLibrarians();

        System.out.print("Enter Librarian ID to delete: ");
        int librarianId = InputUtil.getIntInput();

        boolean success = librarianController.deleteLibrarian(librarianId);

        if (success) {
            System.out.println("Librarian deleted successfully!");
        } else {
            System.out.println("Librarian not found or could not be deleted.");
        }
    }

    private void searchLibrarianById() {
        System.out.println("\n--- Search Librarian by ID ---");
        System.out.print("Enter Librarian ID: ");
        int librarianId = InputUtil.getIntInput();

        Librarian librarian = librarianController.getLibrarianById(librarianId);

        if (librarian == null) {
            System.out.println("No librarian found with ID: " + librarianId);
        } else {
            System.out.println("\n--- Librarian Details ---");
            System.out.printf("ID: %d%nName: %s%nEmail: %s%nAddress: %s%nHire Date: %s%n",
                    librarian.getLibrarianId(),
                    librarian.getName(),
                    librarian.getEmail(),
                    librarian.getAddress(),
                    librarian.getHireDate());
        }
    }
}
