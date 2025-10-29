package src.model.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import src.interfaces.ILibrarian;
import src.model.pojo.Librarian;
import src.utils.DBConfig;

public class LibrarianDAO implements ILibrarian {

    @Override
    public int createLibrarian(int userId, Librarian librarian) throws SQLException {
        String sql = "INSERT INTO librarian (librarian_id, hire_date) VALUES (?, ?)";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (librarianExists(userId)) {
                throw new SQLException("Librarian with ID " + userId + " already exists.");
            }

            pstmt.setInt(1, userId);
            pstmt.setDate(2, Date.valueOf(librarian.getHireDate()));
            pstmt.executeUpdate();
            return userId;
        }
    }

    @Override
    public Librarian getLibrarianById(int librarianId) throws SQLException {
        String sql = "SELECT l.librarian_id, l.hire_date, u.name, u.email, u.password, u.address, u.phone " +
                "FROM librarian l JOIN app_user u ON u.user_id = l.librarian_id " +
                "WHERE l.librarian_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, librarianId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next())
                    return map(rs);
            }
        }
        return null;
    }

    @Override
    public List<Librarian> getAllLibrarians() throws SQLException {
        String sql = "SELECT l.librarian_id, l.hire_date, u.name, u.email, u.password, u.address, u.phone " +
                "FROM librarian l JOIN app_user u ON u.user_id = l.librarian_id " +
                "ORDER BY l.librarian_id";
        List<Librarian> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next())
                list.add(map(rs));
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
        String sql = "DELETE FROM librarian WHERE librarian_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, librarianId);
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean librarianExists(int librarianId) throws SQLException {
        String sql = "SELECT 1 FROM librarian WHERE librarian_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, librarianId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }

    public List<Librarian> getLibrariansHiredBetween(LocalDate start, LocalDate end) throws SQLException {
        String sql = "SELECT l.librarian_id, l.hire_date, u.name, u.email, u.password, u.address, u.phone " +
                "FROM librarian l JOIN app_user u ON u.user_id = l.librarian_id " +
                "WHERE l.hire_date BETWEEN ? AND ?";
        List<Librarian> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setDate(1, Date.valueOf(start));
            pst.setDate(2, Date.valueOf(end));
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next())
                    list.add(map(rs));
            }
        }
        return list;
    }

    private Librarian map(ResultSet rs) throws SQLException {
        Librarian l = new Librarian();
        l.setLibrarianId(rs.getInt("librarian_id"));
        l.setHireDate(rs.getDate("hire_date").toLocalDate());
        l.setName(rs.getString("name"));
        l.setEmail(rs.getString("email"));
        l.setPassword(rs.getString("password"));
        l.setAddress(rs.getString("address"));
        l.setPhone(rs.getLong("phone"));
        return l;
    }
}
