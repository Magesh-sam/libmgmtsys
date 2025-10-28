package src.controller;

import java.sql.SQLException;
import java.util.List;

import src.interfaces.IMember;
import src.model.pojo.Member;
import src.service.MemberService;

public class MemberController implements IMember {
    private final MemberService memberService;

    public MemberController() {
        this.memberService = new MemberService();
    }

    @Override
    public int createMember(int userId, Member member) {
        try {
            if (member == null) {
                throw new IllegalArgumentException("Member data cannot be null.");
            }
            if (userId <= 0) {
                throw new IllegalArgumentException("Invalid user ID.");
            }
            return memberService.createMember(userId, member);
        } 
        catch (IllegalArgumentException e) {
            System.out.println("Validation Error: " + e.getMessage());
            return -1;
        } 
        catch (SQLException e) {
            System.out.println("Error creating member: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Member getMemberById(int memberId) {
        try {
            if (memberId <= 0) {
                throw new IllegalArgumentException("Invalid member ID.");
            }
            return memberService.getMemberById(memberId);
        } 
        catch (IllegalArgumentException e) {
            System.out.println("Validation Error: " + e.getMessage());
            return null;
        } 
        catch (SQLException e) {
            System.out.println("Error fetching member by ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Member> getAllMembers() {
        try {
            return memberService.getAllMembers();
        } 
        catch (IllegalArgumentException e) {
            System.out.println("Validation Error: " + e.getMessage());
            return null;
        } 
        catch (SQLException e) {
            System.out.println("Error fetching all members: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateMember(Member member) {
        try {
            if (member == null) {
                throw new IllegalArgumentException("Member object cannot be null.");
            }
            return memberService.updateMember(member);
        } 
        catch (IllegalArgumentException e) {
            System.out.println("Validation Error: " + e.getMessage());
            return false;
        } 
        catch (SQLException e) {
            System.out.println("Error updating member: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteMember(int memberId) {
        try {
            if (memberId <= 0) {
                throw new IllegalArgumentException("Invalid member ID.");
            }
            return memberService.deleteMember(memberId);
        } 
        catch (IllegalArgumentException e) {
            System.out.println("Validation Error: " + e.getMessage());
            return false;
        } 
        catch (SQLException e) {
            System.out.println("Error deleting member: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
