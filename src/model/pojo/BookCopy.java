package src.model.pojo;

public class BookCopy {

    public static enum BookStatus {
        available, borrowed, lost
    }

    private int copyId;
    private int bookId;
    private BookStatus status;

    public int getCopyId() {
        return copyId;
    }

    public void setCopyId(int copyId) {
        this.copyId = copyId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BookCopy [copyId=" + copyId + ", bookId=" + bookId + ", status=" + status + "]";
    }

}
