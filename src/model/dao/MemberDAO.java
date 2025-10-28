package src.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import src.interfaces.IMember;
import src.model.pojo.Member;
import src.utils.DBConfig;

public class MemberDAO implements IMember {
    @Override
    public int createMember(int userId, Member member) throws SQLException {
        String sql = "INSERT INTO member (member_id, join_date) VALUES (?,?)";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, userId);
            pstmt.setDate(2, Date.valueOf(member.getJoinDate()));
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating member failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public Member getMemberById(int memberId) throws SQLException {
        String sql = "SELECT member_id, join_date FROM member WHERE member_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMember(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Member> getAllMembers() throws SQLException {
        String sql = "SELECT member_id, join_date FROM members";
        List<Member> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToMember(rs));
            }
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
        String sql = "DELETE FROM members WHERE member_id = ?";
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            return pstmt.executeUpdate() > 0;
        }
    }

    private Member mapResultSetToMember(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.setMemberId(rs.getInt("member_id"));
        member.setJoinDate(rs.getDate("join_date").toLocalDate());
        return member;
    }
}