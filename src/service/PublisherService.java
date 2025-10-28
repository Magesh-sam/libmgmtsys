package src.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import src.interfaces.IPublisher;
import src.model.dao.PublisherDAO;
import src.model.pojo.Publisher;
import src.utils.Validation;

public class PublisherService implements IPublisher {

    private final IPublisher publisherDAO;

    public PublisherService() {
        this.publisherDAO = new PublisherDAO();
    }

    @Override
    public int createPublisher(Publisher publisher) throws SQLException {
        Objects.requireNonNull(publisher, "Publisher cannot be null");
        validatePublisher(publisher);

        List<Publisher> all = publisherDAO.getAllPublishers();
        for (Publisher p : all) {
            if (p.getName().equalsIgnoreCase(publisher.getName())) {
                throw new IllegalArgumentException("Publisher already exists with name: " + publisher.getName());
            }
        }

        return publisherDAO.createPublisher(publisher);
    }

    @Override
    public Publisher getPublisherById(int publisherId) throws SQLException {
        if (publisherId <= 0)
            throw new IllegalArgumentException("Invalid publisher ID");
        return publisherDAO.getPublisherById(publisherId);
    }

    @Override
    public List<Publisher> getAllPublishers() throws SQLException {
        return publisherDAO.getAllPublishers();
    }

    @Override
    public boolean updatePublisher(Publisher publisher) throws SQLException {
        Objects.requireNonNull(publisher, "Publisher cannot be null");
        if (publisher.getPublisherId() <= 0)
            throw new IllegalArgumentException("Invalid publisher ID");
        validatePublisher(publisher);

        Publisher existing = publisherDAO.getPublisherById(publisher.getPublisherId());
        if (existing == null)
            throw new IllegalArgumentException("Publisher does not exist with ID " + publisher.getPublisherId());

        return publisherDAO.updatePublisher(publisher);
    }

    @Override
    public boolean deletePublisher(int publisherId) throws SQLException {
        if (publisherId <= 0)
            throw new IllegalArgumentException("Invalid publisher ID");
        Publisher existing = publisherDAO.getPublisherById(publisherId);
        if (existing == null)
            throw new IllegalArgumentException("Publisher does not exist with ID " + publisherId);
        return publisherDAO.deletePublisher(publisherId);
    }

    @Override
    public Publisher getPublisherByName(String name) throws SQLException {
        Validation.requireNonEmpty(name, "Publisher name");
        for (Publisher p : publisherDAO.getAllPublishers()) {
            if (p.getName().equalsIgnoreCase(name))
                return p;
        }
        return null;
    }

    private void validatePublisher(Publisher publisher) {
        Validation.requireNonEmpty(publisher.getName(), "Publisher name");
        Validation.requireNonEmpty(publisher.getAddress(), "Publisher address");
    }
}
