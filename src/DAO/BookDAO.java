package src.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import src.util.DBConfig;

public class BookDAO {
    public static List<String> getAllBooks() {
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
        return bookList.isEmpty() ? null : bookList;
    }
}
