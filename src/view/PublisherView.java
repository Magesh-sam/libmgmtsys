package src.view;

import src.controller.PublisherController;
import src.model.pojo.Publisher;
import src.utils.InputUtil;

import java.util.List;

public class PublisherView {

    private final PublisherController publisherController;

    public PublisherView() {
        this.publisherController = new PublisherController();
    }

    public void display() {
        int choice = -1;

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
                default -> System.out.println("Invalid choice! Enter between 1 to 6.");
            }
        }
    }

    private void addPublisher() {
        System.out.println("\n--- Add New Publisher ---");
        System.out.print("Enter Publisher Name: ");
        String name = InputUtil.getStringInput();

        System.out.print("Enter Publisher Address: (optional) ");
        String address = InputUtil.readRawString();

        address = address.isEmpty() ? null : address;
        Publisher publisher = new Publisher(name, address);

        int publisherId = publisherController.createPublisher(publisher);

        if (publisherId > 0) {
            System.out.println("Publisher added successfully!");
        } else {
            System.out.println("Failed to add publisher. Please try again.");
        }
    }

    private void viewAllPublishers() {
        System.out.println("\n--- Publisher List ---");
        List<Publisher> publishers = publisherController.getAllPublishers();

        if (publishers == null || publishers.isEmpty()) {
            System.out.println("No publishers found.");
            return;
        }

        for (Publisher p : publishers) {
            System.out.println(p);
        }
    }

    private void updatePublisher() {
        System.out.println("\n--- Update Publisher ---");
        viewAllPublishers();
        System.out.print("Enter Publisher ID to update: ");
        int publisherId = InputUtil.getIntInput();

        Publisher existing = publisherController.getPublisherById(publisherId);
        if (existing == null) {
            System.out.println("Publisher not found!");
            return;
        }

        System.out.println("Press Enter to leave a field unchanged.");
        System.out.print("Enter New Name: ");
        String name = InputUtil.readRawString();
        System.out.print("Enter New Address: ");
        String address = InputUtil.readRawString();

        if (!name.isEmpty())
            existing.setName(name);
        if (!address.isEmpty())
            existing.setAddress(address);

        boolean success = publisherController.updatePublisher(existing);

        if (success) {
            System.out.println("Publisher updated successfully!");
        } else {
            System.out.println("Failed to update publisher.");
        }
    }

    private void deletePublisher() {
        System.out.println("\n--- Delete Publisher ---");
        viewAllPublishers();
        System.out.print("Enter Publisher ID to delete: ");
        int publisherId = InputUtil.getIntInput();

        boolean success = publisherController.deletePublisher(publisherId);

        if (success) {
            System.out.println("Publisher deleted successfully!");
        } else {
            System.out.println("Publisher not found or could not be deleted.");
        }
    }

    private void searchPublisherByName() {
        System.out.println("\n--- Search Publisher ---");
        System.out.print("Enter Publisher Name: ");
        String name = InputUtil.getStringInput();

        Publisher publisher = publisherController.getPublisherByName(name);

        if (publisher == null) {
            System.out.println("No publisher found with name \"" + name + "\".");
        } else {
            System.out.println(publisher);
        }
    }
}