package src.view;

import src.model.pojo.Publisher;
import src.service.PublisherService;
import src.controller.PublisherController;
import src.utils.InputUtil;
import java.util.List;
import java.util.Scanner;

public class PublisherView {

    private final PublisherController publisherController;

    public PublisherView() {
        this.publisherController = new PublisherController();
    }

    public void display() {
        int choice;

        while (true) {
            System.out.println("\n===== Publisher Management =====");
            System.out.println("1. Add Publisher");
            System.out.println("2. View All Publishers");
            System.out.println("3. Update Publisher");
            System.out.println("4. Delete Publisher");
            System.out.println("5. Search Publisher by Name");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = InputUtil.getIntInput();

            switch (choice) {
                case 1 -> addPublisher();
                case 2 -> viewAllPublishers();
                case 3 -> updatePublisher();
                case 4 -> deletePublisher();
                case 5 -> searchPublisherByName();
                case 6 -> {
                    System.out.println("Exiting Publisher Management...");
                    return;
                }
                default -> System.out.println("Invalid choice! Enter between 1–6.");
            }
        }
    }

    private void addPublisher() {
        System.out.println("\n--- Add New Publisher ---");
        System.out.print("Enter Publisher Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Enter Address: ");
        String address = sc.nextLine().trim();
        System.out.print("Enter Contact Number: ");
        String contact = sc.nextLine().trim();

        Publisher publisher = new Publisher(name, address, contact);
        boolean success = publisherService.addPublisher(publisher);

        if (success) {
            System.out.println("Publisher added successfully!");
        } else {
            System.out.println("Failed to add publisher. Please try again.");
        }
    }

    private void viewAllPublishers() {
        System.out.println("\n--- Publisher List ---");
        List<Publisher> publishers = publisherService.getAllPublishers();

        if (publishers.isEmpty()) {
            System.out.println("No publishers found in the system.");
            return;
        }

        for (Publisher p : publishers) {
            System.out.println(p);
        }
    }

    private void updatePublisher() {
        System.out.println("\n--- Update Publisher ---");
        System.out.print("Enter Publisher ID to update: ");
        String idInput = sc.nextLine().trim();

        if (!idInput.matches("\\d+")) {
            System.out.println("Invalid ID! Please enter a valid number.");
            return;
        }

        int publisherId = Integer.parseInt(idInput);

        Publisher existing = publisherService.getPublisherById(publisherId);
        if (existing == null) {
            System.out.println("Publisher not found!");
            return;
        }

        System.out.print("Enter new Name (" + existing.getName() + "): ");
        String name = sc.nextLine().trim();
        System.out.print("Enter new Address (" + existing.getAddress() + "): ");
        String address = sc.nextLine().trim();
        System.out.print("Enter new Contact (" + existing.getContactNumber() + "): ");
        String contact = sc.nextLine().trim();

        if (!name.isEmpty())
            existing.setName(name);
        if (!address.isEmpty())
            existing.setAddress(address);
        if (!contact.isEmpty())
            existing.setContactNumber(contact);

        boolean success = publisherService.updatePublisher(existing);

        if (success) {
            System.out.println("Publisher updated successfully!");
        } else {
            System.out.println("Update failed!");
        }
    }

    private void deletePublisher() {
        System.out.println("\n--- Delete Publisher ---");
        System.out.print("Enter Publisher ID to delete: ");
        String idInput = sc.nextLine().trim();

        if (!idInput.matches("\\d+")) {
            System.out.println("Invalid ID! Please enter a valid number.");
            return;
        }

        int publisherId = Integer.parseInt(idInput);

        boolean success = publisherService.deletePublisher(publisherId);

        if (success) {
            System.out.println("Publisher deleted successfully!");
        } else {
            System.out.println("Publisher not found or could not be deleted.");
        }
    }

    private void searchPublisherByName() {
        System.out.println("\n--- Search Publisher ---");
        System.out.print("Enter publisher name keyword: ");
        String keyword = sc.nextLine().trim();

        List<Publisher> results = publisherService.searchPublisherByName(keyword);

        if (results.isEmpty()) {
            System.out.println("No publishers found matching \"" + keyword + "\".");
            return;
        }

        for (Publisher p : results) {
            System.out.println(p);
        }
    }
}
