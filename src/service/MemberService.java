package src.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import src.interfaces.IMember;
import src.model.dao.MemberDAO;
import src.model.pojo.Member;

public class MemberService implements IMember {

    private final IMember memberDAO;

    public MemberService() {
        this.memberDAO = new MemberDAO();
    }

    @Override
    public int createMember(int userId, Member member) throws SQLException {
        Objects.requireNonNull(member, "Member cannot be null");
        if (userId <= 0)
            throw new IllegalArgumentException("Invalid user ID");
        if (member.getJoinDate() == null)
            member.setJoinDate(LocalDate.now());

        if (((MemberDAO) memberDAO).memberExists(userId)) {
            throw new IllegalArgumentException("Member already exists with ID " + userId);
        }

        return memberDAO.createMember(userId, member);
    }

    @Override
    public Member getMemberById(int memberId) throws SQLException {
        if (memberId <= 0)
            throw new IllegalArgumentException("Invalid member ID");
        return memberDAO.getMemberById(memberId);
    }

    @Override
    public List<Member> getAllMembers() throws SQLException {
        return memberDAO.getAllMembers();
    }

    @Override
    public boolean updateMember(Member member) throws SQLException {
        Objects.requireNonNull(member, "Member cannot be null");
        if (member.getMemberId() <= 0)
            throw new IllegalArgumentException("Invalid member ID");

        if (!((MemberDAO) memberDAO).memberExists(member.getMemberId())) {
            throw new IllegalArgumentException("Member not found with ID: " + member.getMemberId());
        }

        return memberDAO.updateMember(member);
    }

    @Override
    public boolean deleteMember(int memberId) throws SQLException {
        if (memberId <= 0)
            throw new IllegalArgumentException("Invalid member ID");
        if (!((MemberDAO) memberDAO).memberExists(memberId)) {
            throw new IllegalArgumentException("Member not found with ID: " + memberId);
        }
        return memberDAO.deleteMember(memberId);
    }

       public List<Member> getMembersJoinedBetween(LocalDate start, LocalDate end) throws SQLException {
        Objects.requireNonNull(start);
        Objects.requireNonNull(end);
        return ((MemberDAO) memberDAO).getMembersJoinedBetween(start, end);
    }

}
