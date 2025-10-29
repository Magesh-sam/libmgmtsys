package src.view;

import src.controller.AppUserController;
import src.controller.MemberController;
import src.controller.UserRoleController;
import src.model.pojo.AppUser;
import src.model.pojo.Member;
import src.model.pojo.UserRole;
import src.utils.InputUtil;

import java.time.LocalDate;
import java.util.List;

public class MemberView {

    private final MemberController memberController;
    private final AppUserController userController;

    public MemberView() {
        this.memberController = new MemberController();
        this.userController = new AppUserController();
    }

    public void display() {
        while (true) {
            System.out.println("\n===== Member Management =====");
            System.out.println("1. Add Member");
            System.out.println("2. View All Members");
            System.out.println("3. Update Member");
            System.out.println("4. Delete Member");
            System.out.println("5. Search Member by ID");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = InputUtil.getIntInput();

            switch (choice) {
                case 1 -> addMember();
                case 2 -> viewAllMembers();
                case 3 -> updateMember();
                case 4 -> deleteMember();
                case 5 -> searchMemberById();
                case 6 -> {
                    System.out.println("Exiting Member Management...");
                    return;
                }
                default -> System.out.println("Invalid choice! Enter between 1 to 6.");
            }
        }
    }

    private void addMember() {
        System.out.println("\n--- Add Member ---");

        System.out.print("Enter full name: ");
        String name = InputUtil.getStringInput();

        System.out.print("Enter email: ");
        String email = InputUtil.getStringInput();

        System.out.print("Enter password: ");
        String password = InputUtil.getStringInput();

        System.out.print("Enter address: (optional) ");
        String address = InputUtil.readRawString();

        System.out.print("Enter phone:  ");
        long phone = InputUtil.getLongInput();

        address = address.isEmpty() ? null : address;
        LocalDate joinDate = LocalDate.now();

        UserRole role = new UserRoleController().getUserRoleByName("member");

        AppUser user = new AppUser();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setAddress(address);
        user.setRoleId(role.getRoleId());
        user.setPhone(phone);
        int userId = userController.createUser(user, role.getRoleId());

        if (userId > 0) {
            Member member = new Member();
            member.setUserId(userId);
            member.setJoinDate(joinDate);

            int memberId = memberController.createMember(userId, member);

            if (memberId > 0) {
                System.out.println("Member created successfully with ID: " + memberId);
            } else {
                System.out.println("User created but failed to create member profile. Contact admin.");
            }
        } else {
            System.out.println("Failed to create user.");
        }
    }

    private void viewAllMembers() {
        System.out.println("\n--- Member List ---");

        List<Member> members = memberController.getAllMembers();
        if (members == null || members.isEmpty()) {
            System.out.println("No members found.");
            return;
        }

        for (Member member : members) {

            System.out.println("id: " + member.getMemberId()
                    + ", Name: " + member.getName()
                    + ", Join Date: " + member.getJoinDate());

        }
    }

    private void updateMember() {
        System.out.println("\n--- Update Member ---");
        viewAllMembers();
        System.out.print("Enter Member ID to update: ");
        int memberId = InputUtil.getIntInput();
        System.out.println("memberId: " + memberId);

        Member existing = memberController.getMemberById(memberId);
        if (existing == null) {
            System.out.println("Member not found!");
            return;
        }

        AppUser existingUser = userController.getUserById(existing.getMemberId());
        if (existingUser == null) {
            System.out.println("Associated user record not found. Data might be corrupted.");
            return;
        }

        System.out.println("Press Enter/0 to skip a field.");

        System.out.print("Enter Member Name (" + existingUser.getName() + "): ");
        String name = InputUtil.readRawString();

        System.out.print("Enter Member Email (" + existingUser.getEmail() + "): ");
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
        phone = phone <= 0 ? existingUser.getPhone() : phone;
        // Update AppUser entity
        existingUser.setName(name);
        existingUser.setEmail(email);
        existingUser.setPassword(password);
        existingUser.setAddress(address);
        existingUser.setPhone(phone);

        // Persist both updates
        boolean userUpdated = userController.updateUser(existingUser);
        boolean memberUpdated = memberController.updateMember(existing);

        if (userUpdated && memberUpdated) {
            System.out.println("Member and user details updated successfully!");
        } else if (userUpdated) {
            System.out.println("User updated, but failed to update member details.");
        } else if (memberUpdated) {
            System.out.println("Member updated, but failed to update user details.");
        } else {
            System.out.println("Failed to update both user and member details.");
        }
    }

    private void deleteMember() {
        System.out.println("\n--- Delete Member ---");
        viewAllMembers();
        System.out.print("Enter Member ID to delete: ");
        int memberId = InputUtil.getIntInput();

        boolean success = memberController.deleteMember(memberId);

        if (success) {
            System.out.println("Member deleted successfully!");
        } else {
            System.out.println("Member not found or could not be deleted.");
        }
    }

    private void searchMemberById() {
        System.out.println("\n--- Search Member by ID ---");
        System.out.print("Enter Member ID: ");
        int memberId = InputUtil.getIntInput();

        Member member = memberController.getMemberById(memberId);

        if (member == null) {
            System.out.println("No member found with ID: " + memberId);
        } else {
            System.out.println("\n--- Member Details ---");
            System.out.printf("ID: %d%nName: %s%nEmail: %s%nAddress: %s%nJoin Date: %s%n",
                    member.getMemberId(),
                    member.getName(),
                    member.getEmail(),
                    member.getAddress(),
                    member.getJoinDate());
        }
    }

}
