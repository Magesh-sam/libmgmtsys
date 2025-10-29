package src.model.pojo;

import java.time.LocalDate;

public class BorrowedBooks {
    private int borrowedId;
    private int memberId;
    private int copyId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public BorrowedBooks(int borrowedId, int memberId, int copyId, LocalDate borrowDate, LocalDate dueDate,
            LocalDate returnDate) {
        this.borrowedId = borrowedId;
        this.memberId = memberId;
        this.copyId = copyId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public BorrowedBooks(int memberId, int copyId, LocalDate borrowDate, LocalDate dueDate,
            LocalDate returnDate) {
        this.memberId = memberId;
        this.copyId = copyId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public BorrowedBooks() {
    }

    public int getborrowedId() {
        return borrowedId;
    }

    public void setBorrowedId(int borrowedId) {
        this.borrowedId = borrowedId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getCopyId() {
        return copyId;
    }

    public void setCopyId(int copyId) {
        this.copyId = copyId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "BorrowedBooks [borrowedId=" + borrowedId + ", memberId=" + memberId + ", copyId=" + copyId
                + ", borrowDate=" + borrowDate + ", dueDate=" + dueDate + ", returnDate=" + returnDate + "]";
    }

}
