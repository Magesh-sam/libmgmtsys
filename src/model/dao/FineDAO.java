package src.model.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import src.interfaces.IFine;
import src.model.pojo.Fine;
import src.model.pojo.Fine.FineStatus;
import src.utils.DBConfig;

public class FineDAO implements IFine {

    @Override
    public int createFine(Fine fine) throws SQLException {
        String sql = "INSERT INTO fine (borrowed_id, amount, issue_date, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, fine.getBorrowedId());
            pstmt.setDouble(2, fine.getAmount());
            pstmt.setDate(3, Date.valueOf(fine.getIssueDate()));
            pstmt.setString(4, fine.getStatus().name());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
                throw new SQLException("Creating fine failed, no ID obtained.");
            }
        }
    }

    @Override
    public Fine getFineById(int fineId) throws SQLException {
        String sql = "SELECT fine_id, borrowed_id, amount, issue_date, status FROM fine WHERE fine_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, fineId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    @Override
    public List<Fine> getAllFines() throws SQLException {
        String sql = "SELECT fine_id, borrowed_id, amount, issue_date, status FROM fine ORDER BY fine_id";
        List<Fine> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    @Override
    public boolean updateFineStatus(int fineId, FineStatus status) throws SQLException {
        String sql = "UPDATE fine SET status = ? WHERE fine_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status.name());
            pstmt.setInt(2, fineId);
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteFine(int fineId) throws SQLException {
        String sql = "DELETE FROM fine WHERE fine_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, fineId);
            return pstmt.executeUpdate() > 0;
        }
    }

    // --- Extended Functionality (Same pattern as BookDAO expansion) ---
    public List<Fine> getFinesByStatus(String statusName) throws SQLException {
        String sql = "SELECT fine_id, borrowed_id, amount, issue_date, status FROM fine WHERE LOWER(status) = LOWER(?)";
        List<Fine> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, statusName);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public List<Fine> getFinesIssuedBetween(LocalDate start, LocalDate end) throws SQLException {
        String sql = "SELECT fine_id, borrowed_id, amount, issue_date, status FROM fine WHERE issue_date BETWEEN ? AND ?";
        List<Fine> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setDate(1, Date.valueOf(start));
            pst.setDate(2, Date.valueOf(end));
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public boolean fineExists(int fineId) throws SQLException {
        String sql = "SELECT 1 FROM fine WHERE fine_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, fineId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }

    private Fine map(ResultSet rs) throws SQLException {
        return new Fine(
            rs.getInt("fine_id"),
            rs.getInt("borrowed_id"),
            rs.getDouble("amount"),
            rs.getDate("issue_date").toLocalDate(),
            FineStatus.valueOf(rs.getString("status"))
        );
    }
}
