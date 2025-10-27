package src.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import src.model.Fine;
import src.model.Fine.FineStatus;
import src.util.DBConfig;

public class FineDAO {
    public int createFine(Fine fine) throws SQLException {
        String sql = "INSERT INTO fine (borrowed_id, amount, issue_date, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, fine.getBorrowedId());
            pstmt.setDouble(2, fine.getAmount());
            pstmt.setDate(3, Date.valueOf(fine.getIssueDate()));
            pstmt.setString(4, fine.getStatus().toString());
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating fine failed, no ID obtained.");
                }
            }
        }
    }

    public Fine getFineById(int fineId) throws SQLException {
        String sql = "SELECT fine_id, borrowed_id, amount, issue_date, status FROM fine WHERE fine_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, fineId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToFine(rs);
                }
            }
        }
        return null;
    }

    public List<Fine> getAllFines() throws SQLException {
        String sql = "SELECT fine_id, borrowed_id, amount, issue_date, status FROM fines";
        List<Fine> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToFine(rs));
            }
        }
        return list;
    }

    public boolean updateFineStatus(int fineId, FineStatus status) throws SQLException {
        String sql = "UPDATE fine SET status = ? WHERE fine_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status.toString());
            pstmt.setInt(2, fineId);
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteFine(int fineId) throws SQLException {
        String sql = "DELETE FROM fines WHERE fine_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, fineId);
            return pstmt.executeUpdate() > 0;
        }
    }

    private Fine mapResultSetToFine(ResultSet rs) throws SQLException {
        Fine fine = new Fine(
                rs.getInt("fine_id"),
                rs.getInt("borrowed_id"),
                rs.getDouble("amount"),
                rs.getDate("issue_date").toLocalDate(),
                FineStatus.valueOf(rs.getString("status")));
        return fine;
    }
}