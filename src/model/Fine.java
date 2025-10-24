package src.model;

import java.time.LocalDate;

public class Fine {

    public static enum FineStatus {
        paid, unpaid
    }

    private int fineId;
    private int borrowedId;
    private double amount;
    private LocalDate issueDate;
    private FineStatus status;

    

    public Fine(int fineId, int borrowedId, double amount, LocalDate issueDate, FineStatus status) {
        this.fineId = fineId;
        this.borrowedId = borrowedId;
        this.amount = amount;
        this.issueDate = issueDate;
        this.status = status;
    }

    

    public Fine(int borrowedId, double amount, LocalDate issueDate, FineStatus status) {
        this.borrowedId = borrowedId;
        this.amount = amount;
        this.issueDate = issueDate;
        this.status = status;
    }



    public int getFineId() {
        return fineId;
    }

    public void setFineId(int fineId) {
        this.fineId = fineId;
    }

    public int getBorrowedId() {
        return borrowedId;
    }

    public void setBorrowedId(int borrowedId) {
        this.borrowedId = borrowedId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public FineStatus getStatus() {
        return status;
    }

    public void setStatus(FineStatus status) {
        this.status = status;
    }

}
