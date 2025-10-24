package src;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import src.util.DBConfig;

public class App {

    private static void prinrBooks() {
        String query = "SELECT * FROM book;";
        List<String> bookList = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("book_id");
                String title = rs.getString("title");
                bookList.add(id + " - " + title);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (bookList.size() > 0) {
            for (String book : bookList) {
                System.out.println(book);
            }
        }
    }

    private static void printAuthors() {
        String query = "SELECT * FROM author";
        List<String> authorList = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("author_id");
                String name = rs.getString("name");
                authorList.add(id + " - " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!authorList.isEmpty()) {
            for (String author : authorList) {
                System.out.println(author);
            }
        }
    }

    private static void printCategories() {
        String query = "SELECT * FROM category";
        List<String> categoryList = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("category_id");
                String name = rs.getString("name");
                categoryList.add(id + " - " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!categoryList.isEmpty()) {
            for (String category : categoryList) {
                System.out.println(category);
            }
        }
    }

    private static void printBookAuthor() {
        String query = "SELECT\n" +
                "    b.book_id,\n" +
                "    b.title AS \"Book Title\",\n" +
                "    STRING_AGG(a.name, ', ' ORDER BY a.name) AS \"Authors\"\n" +
                "FROM\n" +
                "    book b\n" +
                "JOIN\n" +
                "    book_author ba ON b.book_id = ba.book_id\n" +
                "JOIN\n" +
                "    author a ON ba.author_id = a.author_id\n" +
                "GROUP BY\n" +
                "    b.book_id, b.title\n" +
                "ORDER BY\n" +
                "    b.title;";

        List<String> bookList = new ArrayList<>();

        try (Connection conn = DBConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("book_id");
                String title = rs.getString("Book Title");
                String authors = rs.getString("Authors");

                bookList.add(id + " - " + title + " (Authors: " + authors + ")");
            }

        } catch (SQLException e) {
            System.err.println("Database error while retrieving books and authors:");
            e.printStackTrace();
        }

        if (bookList.isEmpty()) {
            System.out.println("No books and authors found.");
        } else {
            System.out.println("--- Book List with Authors ---");
            for (String book : bookList) {
                System.out.println(book);
            }
        }
    }

    public static void main(String[] args) {
        prinrBooks();
        printAuthors();
        printCategories();
        printBookAuthor();
    }
}