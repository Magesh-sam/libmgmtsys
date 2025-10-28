package src.controller;

import java.sql.SQLException;
import java.util.List;
import src.interfaces.ICategory;
import src.model.pojo.Category;
import src.service.CategoryService;

public class CategoryController implements ICategory {
    private final CategoryService categoryService;

    public CategoryController() {
        this.categoryService = new CategoryService();
    }

    @Override
    public int createCategory(Category category) {
        try {
            return categoryService.createCategory(category);
        } catch (SQLException e) {
            System.out.println("Error creating category: " + e.getMessage());
            e.printStackTrace();
            return -1;
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
            return -1;
        }
    }

    @Override
    public Category getCategoryById(int categoryId) {
        try {
            return categoryService.getCategoryById(categoryId);
        } catch (SQLException e) {
            System.out.println("Error fetching category by ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
            return null;
        }
    }

    public Category getCategoryByName(String name) {
        try {
            return categoryService.getCategoryByName(name);
        } catch (SQLException e) {
            System.out.println("Error fetching category by name: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Category> getAllCategories() {
        try {
            return categoryService.getAllCategories();
        } catch (SQLException e) {
            System.out.println("Error fetching categories: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateCategory(Category category) {
        try {
            return categoryService.updateCategory(category);
        } catch (SQLException e) {
            System.out.println("Error updating category: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteCategory(int categoryId) {
        try {
            return categoryService.deleteCategory(categoryId);
        } catch (SQLException e) {
            System.out.println("Error deleting category: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
            return false;
        }
    }
}
