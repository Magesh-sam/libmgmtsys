package src.model.pojo;

public class BookAuthor {
    private int bookId;
    private int authorId;

    public BookAuthor(int bookId, int authorId) {
        this.bookId = bookId;
        this.authorId = authorId;
    }

    public BookAuthor() {
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String toString() {
        return "BookAuthor [bookId=" + bookId + ", authorId=" + authorId + "]";
    }
}
