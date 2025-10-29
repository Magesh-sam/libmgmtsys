package src.model.pojo;

public class Book {

    private int bookId;
    private String title;
    private int categoryId;
    private int publisherId;
    private String language;
    private double price;

    public Book(String title, String language, double price) {
        this.title = title;
        this.language = language;
        this.price = price;
    }

    public Book() {
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book [bookId=" + bookId + ", title=" + title + ", categoryId=" + categoryId + ", publisherId="
                + publisherId + ", language=" + language + ", price=" + price + "]";
    }

}
