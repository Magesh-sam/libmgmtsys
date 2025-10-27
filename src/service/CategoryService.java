package src.service;

import java.sql.SQLException;
import java.util.Objects;

import src.interfaces.ICategory;
import src.model.dao.CategoryDAO;
import src.model.pojo.Category;
import src.utils.Validation;

public class CategoryService {
    private final ICategory categoryDAO;

    public CategoryService() {
        this.categoryDAO = new CategoryDAO();
    }

    public int createCategory(Category category) throws Exception {
        Objects.requireNonNull(category, "Category cannot be null");
        validateCategory(category);

        return categoryDAO.createCategory(category);
    }

    public Category getCategoryById(int categoryId) throws Exception {
        if (categoryId <= 0) {
            throw new IllegalArgumentException("Invalid Category ID");
        }
        return categoryDAO.getCategoryById(categoryId);
    }

    public java.util.List<Category> getAllCategories() throws Exception {
        return categoryDAO.getAllCategories();
    }

    public void updateCategory(Category category) throws Exception {
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
        categoryDAO.updateCategory(category);
    }

    public void deleteCategory(int categoryId) throws SQLException {
        if (categoryId <= 0) {
            throw new IllegalArgumentException("Invalid Category ID");
        }
        categoryDAO.deleteCategory(categoryId);
    }

    private void validateCategory(Category category) {
        Validation.requireNonEmpty(category.getName(), "Category Name");
    }

}
