package src.model.pojo;

import java.time.LocalDate;

public class Member extends AppUser {
    private int memberId;
    private LocalDate joinDate;

    public Member() {
    }

    public Member(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public Member(int memberId) {
        this.memberId = memberId;
    }

    public Member(int memberId, LocalDate joinDate) {
        this.memberId = memberId;
        this.joinDate = joinDate;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    @Override
    public String toString() {
        return super.toString() + "Member [memberId=" + memberId + ", joinDate=" + joinDate + "]";
    }



}
