package src.view;

import java.util.List;
import src.controller.UserRoleController;
import src.model.pojo.UserRole;
import src.utils.InputUtil;

public class UserRoleView {

    private final UserRoleController userRoleController = new UserRoleController();

    public void display() {
        while (true) {
            System.out.println("\n===== Role Management =====");
            System.out.println("1. Add Role");
            System.out.println("2. View All Roles");
            System.out.println("3. Update Role");
            System.out.println("4. Delete Role");
            System.out.println("5. Back");
            System.out.print("Enter your choice: ");

            int choice = InputUtil.getIntInput();

            switch (choice) {
                case 1 -> addRole();
                case 2 -> viewRoles();
                case 3 -> updateRole();
                case 4 -> deleteRole();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void addRole() {
        System.out.print("Enter role name: ");
        String roleName = InputUtil.getStringInput();
        UserRole role = new UserRole();
        role.setName(roleName);
        int id = userRoleController.addUserRole(role);
        if (id > 0) {
            System.out.println("Role added successfully with ID: " + id);
        } else {
            System.out.println("Failed to add role.");
        }
    }

    private void viewRoles() {
        List<UserRole> roles = userRoleController.getAllUserRoles();
        if (roles.isEmpty()) {
            System.out.println("No roles found.");
        } else {
            System.out.println("\n--- All Roles ---");
            roles.forEach(r -> System.out.println("ID: " + r.getRoleId() + " | Name: " + r.getName()));
        }
    }

    private void updateRole() {
        viewRoles();
        System.out.print("Enter role ID to update: ");
        int roleId = InputUtil.getIntInput();
        System.out.print("Enter new role name: ");
        String roleName = InputUtil.getStringInput();

        UserRole role = new UserRole(roleId, roleName);
        boolean updated = userRoleController.updateUserRole(role);
        System.out.println(updated ? "Role updated successfully." : "Failed to update role.");
    }

    private void deleteRole() {
        System.out.print("Enter role ID to delete: ");
        int roleId = InputUtil.getIntInput();
        boolean deleted = userRoleController.deleteUserRole(roleId);
        System.out.println(deleted ? "Role deleted successfully." : "Failed to delete role.");
    }
}