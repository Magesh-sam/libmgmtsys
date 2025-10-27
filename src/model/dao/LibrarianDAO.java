package src.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import src.interfaces.ILibrarianDAO;
import src.model.pojo.Librarian;
import src.utils.DBConfig;

public class LibrarianDAO implements ILibrarianDAO {
    @Override
    public int createLibrarian(Librarian librarian) throws SQLException {
        String sql = "INSERT INTO librarian (hire_date) VALUES (?)";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setDate(1, Date.valueOf(librarian.getHireDate()));
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating librarian failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public Librarian getLibrarianById(int librarianId) throws SQLException {
        String sql = "SELECT librarian_id, hire_date FROM librarian WHERE librarian_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, librarianId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToLibrarian(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Librarian> getAllLibrarians() throws SQLException {
        String sql = "SELECT librarian_id, hire_date FROM librarians";
        List<Librarian> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToLibrarian(rs));
            }
        }
        return list;
    }

    @Override
    public boolean updateLibrarian(Librarian librarian) throws SQLException {
        String sql = "UPDATE librarian SET hire_date = ? WHERE librarian_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(librarian.getHireDate()));
            pstmt.setInt(2, librarian.getLibrarianId());
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteLibrarian(int librarianId) throws SQLException {
        String sql = "DELETE FROM librarians WHERE librarian_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, librarianId);
            return pstmt.executeUpdate() > 0;
        }
    }

    private Librarian mapResultSetToLibrarian(ResultSet rs) throws SQLException {
        Librarian librarian = new Librarian();
        librarian.setLibrarianId(rs.getInt("librarian_id"));
        librarian.setHireDate(rs.getDate("hire_date").toLocalDate());
        return librarian;
    }
}