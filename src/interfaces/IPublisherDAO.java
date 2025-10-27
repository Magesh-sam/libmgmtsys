package src.interfaces;

import java.sql.SQLException;
import java.util.List;

import src.model.pojo.Publisher;

public interface IPublisherDAO {

    int createPublisher(Publisher publisher) throws SQLException;

    Publisher getPublisherById(int publisherId) throws SQLException;

    List<Publisher> getAllPublishers() throws SQLException;

    boolean updatePublisher(Publisher publisher) throws SQLException;

    boolean deletePublisher(int publisherId) throws SQLException;

}