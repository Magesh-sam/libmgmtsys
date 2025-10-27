package src.interfaces;

import java.sql.SQLException;
import java.util.List;

import src.model.Member;

public interface IMemberDAO {

    int createMember(Member member) throws SQLException;

    Member getMemberById(int memberId) throws SQLException;

    List<Member> getAllMembers() throws SQLException;

    boolean updateMember(Member member) throws SQLException;

    boolean deleteMember(int memberId) throws SQLException;

}