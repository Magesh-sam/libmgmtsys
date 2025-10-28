package src.interfaces;

import java.sql.SQLException;
import java.util.List;

import src.model.pojo.Category;

public interface ICategory {

    // Create a new category
    int createCategory(Category category) throws SQLException;

    // Read category by ID
    Category getCategoryById(int categoryId) throws SQLException;

    // Get all categories
    List<Category> getAllCategories() throws SQLException;

    // Update category
    boolean updateCategory(Category category) throws SQLException;

    // Delete category
    boolean deleteCategory(int categoryId) throws SQLException;

    Category getCategoryByName(String name) throws SQLException;

}