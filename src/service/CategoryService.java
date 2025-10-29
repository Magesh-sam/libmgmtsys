package src.service;

import java.sql.SQLException;
import java.util.Objects;

import src.interfaces.ICategory;
import src.model.dao.CategoryDAO;
import src.model.pojo.Category;
import src.utils.Validation;

public class CategoryService implements ICategory {
    private final ICategory categoryDAO;

    public CategoryService() {
        this.categoryDAO = new CategoryDAO();
    }

    public int createCategory(Category category) throws SQLException {
        Objects.requireNonNull(category, "Category cannot be null");
        validateCategory(category);

        return categoryDAO.createCategory(category);
    }

    public Category getCategoryById(int categoryId) throws SQLException {
        if (categoryId <= 0) {
            throw new IllegalArgumentException("Invalid Category ID");
        }
        return categoryDAO.getCategoryById(categoryId);
    }

    public java.util.List<Category> getAllCategories() throws SQLException {
        return categoryDAO.getAllCategories();
    }

    public boolean updateCategory(Category category) throws SQLException {
        Objects.requireNonNull(category, "Category cannot be null");
        if (category.getCategoryId() <= 0) {
            throw new IllegalArgumentException("Invalid Category ID");
        }
        validateCategory(category);
        Category existingCategory = categoryDAO.getCategoryById(category.getCategoryId());
        if (existingCategory == null) {
            throw new IllegalArgumentException("Category with ID " + category.getCategoryId() + " does not exist.");
        } else if (existingCategory.getName().equals(category.getName()) &&
                Objects.equals(existingCategory.getDescription(), category.getDescription())) {
            throw new IllegalArgumentException("No changes detected in the category details.");
        }
        return categoryDAO.updateCategory(category);
    }

    public boolean deleteCategory(int categoryId) throws SQLException {
        if (categoryId <= 0) {
            throw new IllegalArgumentException("Invalid Category ID");
        }
        Category existingCategory = categoryDAO.getCategoryById(categoryId);
        if (existingCategory == null) {
            throw new IllegalArgumentException("Category with ID " + categoryId + " does not exist.");
        }
        return categoryDAO.deleteCategory(categoryId);
    }

    private void validateCategory(Category category) {
        Validation.requireNonEmpty(category.getName(), "Category Name");
    }

    @Override
    public Category getCategoryByName(String name) throws SQLException {
        Validation.requireNonEmpty(name, "Name");
        return categoryDAO.getCategoryByName(name);
    }

    @Override
    public boolean categoryExists(String name) throws SQLException {
        Validation.requireNonEmpty(name, "Name");
        return categoryDAO.categoryExists(name);
    }

}
