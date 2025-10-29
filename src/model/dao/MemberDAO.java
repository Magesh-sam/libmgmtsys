package src.model.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import src.interfaces.IMember;
import src.model.pojo.Member;
import src.utils.DBConfig;

public class MemberDAO implements IMember {

    @Override
    public int createMember(int userId, Member member) throws SQLException {
        // member_id is same as app_user.user_id → not auto-generated
        String sql = "INSERT INTO member (member_id, join_date) VALUES (?, ?)";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (memberExists(userId)) {
                throw new SQLException("Member with ID " + userId + " already exists.");
            }

            pstmt.setInt(1, userId);
            pstmt.setDate(2, Date.valueOf(member.getJoinDate()));
            pstmt.executeUpdate();
            return userId;
        }
    }

    @Override
    public Member getMemberById(int memberId) throws SQLException {
        String sql = """
                SELECT
                    m.member_id,
                    m.join_date,
                    u.name,
                    u.email,
                    u.phone,
                    u.address
                FROM member m
                JOIN app_user u ON m.member_id = u.user_id
                wHERE m.member_id = ?
                """;
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next())
                    return map(rs);
            }
        }
        return null;
    }

    @Override
    public List<Member> getAllMembers() throws SQLException {
        String sql = """
                SELECT
                    m.member_id,
                    m.join_date,
                    u.name,
                    u.email,
                    u.phone,
                    u.address
                FROM member m
                JOIN app_user u ON m.member_id = u.user_id
                """;

        List<Member> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next())
                list.add(map(rs));
        }
        return list;
    }

    @Override
    public boolean updateMember(Member member) throws SQLException {
        String sql = "UPDATE member SET join_date = ? WHERE member_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(member.getJoinDate()));
            pstmt.setInt(2, member.getMemberId());
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteMember(int memberId) throws SQLException {
        String sql = "DELETE FROM member WHERE member_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            return pstmt.executeUpdate() > 0;
        }
    }

    // --- Helpers ---
    public boolean memberExists(int memberId) throws SQLException {
        String sql = "SELECT 1 FROM member WHERE member_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, memberId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }

    public List<Member> getMembersJoinedBetween(LocalDate start, LocalDate end) throws SQLException {
        String sql = "SELECT member_id, join_date FROM member WHERE join_date BETWEEN ? AND ?";
        List<Member> list = new ArrayList<>();
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

    private Member map(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.setMemberId(rs.getInt("member_id"));
        member.setName(rs.getString("name"));
        member.setEmail(rs.getString("email"));
        member.setPhone(rs.getLong("phone"));
        member.setAddress(rs.getString("address"));
        member.setJoinDate(rs.getDate("join_date").toLocalDate());
        return member;
    }
}
