package src.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import src.interfaces.IPublisherDAO;
import src.model.Publisher;
import src.util.DBConfig;

public class PublisherDAO implements IPublisherDAO {
    @Override
    public int createPublisher(Publisher publisher) throws SQLException {
        String sql = "INSERT INTO publisher (name, address) VALUES (?, ?)";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, publisher.getName());
            pstmt.setString(2, publisher.getAddress());
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating publisher failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public Publisher getPublisherById(int publisherId) throws SQLException {
        String sql = "SELECT publisher_id, name, address FROM publisher WHERE publisher_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, publisherId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPublisher(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Publisher> getAllPublishers() throws SQLException {
        String sql = "SELECT publisher_id, name, address FROM publishers";
        List<Publisher> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToPublisher(rs));
            }
        }
        return list;
    }

    @Override
    public boolean updatePublisher(Publisher publisher) throws SQLException {
        String sql = "UPDATE publisher SET name = ?, address = ? WHERE publisher_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, publisher.getName());
            pstmt.setString(2, publisher.getAddress());
            pstmt.setInt(3, publisher.getPublisherId());
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deletePublisher(int publisherId) throws SQLException {
        String sql = "DELETE FROM publisher WHERE publisher_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, publisherId);
            return pstmt.executeUpdate() > 0;
        }
    }

    private Publisher mapResultSetToPublisher(ResultSet rs) throws SQLException {
        Publisher publisher = new Publisher(rs.getInt("publisher_id"), rs.getString("name"));
        publisher.setAddress(rs.getString("address"));
        return publisher;
    }
}