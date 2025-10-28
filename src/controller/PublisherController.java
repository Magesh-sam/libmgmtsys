package src.controller;

import java.sql.SQLException;
import java.util.List;
import src.interfaces.IPublisher;
import src.model.pojo.Publisher;
import src.service.PublisherService;

public class PublisherController implements IPublisher {
    private final PublisherService publisherService;

    public PublisherController() {
        this.publisherService = new PublisherService();
    }

    @Override
    public int createPublisher(Publisher publisher) {
        try {
            return publisherService.createPublisher(publisher);
        } catch (SQLException e) {
            System.out.println("Error creating publisher: " + e.getMessage());
            e.printStackTrace();
            return -1;
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
            return -1;
        }
    }

    @Override
    public Publisher getPublisherById(int publisherId) {
        try {
            return publisherService.getPublisherById(publisherId);
        } catch (SQLException e) {
            System.out.println("Error fetching publisher by ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Publisher> getAllPublishers() {
        try {
            return publisherService.getAllPublishers();
        } catch (SQLException e) {
            System.out.println("Error fetching all publishers: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Publisher getPublisherByName(String name) {
        try {
            return publisherService.getPublisherByName(name);
        } catch (SQLException e) {
            System.out.println("Error fetching publisher by name: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean updatePublisher(Publisher publisher) {
        try {
            return publisherService.updatePublisher(publisher);
        } catch (SQLException e) {
            System.out.println("Error updating publisher: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deletePublisher(int publisherId) {
        try {
            return publisherService.deletePublisher(publisherId);
        } catch (SQLException e) {
            System.out.println("Error deleting publisher: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
            return false;
        }
    }
}