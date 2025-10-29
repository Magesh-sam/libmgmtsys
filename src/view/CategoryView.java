package src.view;

import src.controller.CategoryController;
import src.model.pojo.Category;
import src.utils.InputUtil;

import java.util.List;

public class CategoryView {

    private final CategoryController categoryController;

    public CategoryView() {
        this.categoryController = new CategoryController();
    }

    public void display() {
        int choice = -1;

        while (true) {
            System.out.println("\n===== Category Management =====");
            System.out.println("1. Add Category");
            System.out.println("2. View All Categories");
            System.out.println("3. Update Category");
            System.out.println("4. Delete Category");
            System.out.println("5. Search Category by Name");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = InputUtil.getIntInput();

            switch (choice) {
                case 1 -> addCategory();
                case 2 -> viewAllCategories();
                case 3 -> updateCategory();
                case 4 -> deleteCategory();
                case 5 -> searchCategoryByName();
                case 6 -> {
                    System.out.println("Exiting Category Management...");
                    return;
                }
                default -> System.out.println("Invalid choice! Enter between 1 to 6.");
            }
        }
    }

    private void addCategory() {
        System.out.println("\n--- Add New Category ---");
        System.out.println("Press Enter to leave the string field unchanged.");
        System.out.print("Enter Category Name: ");
        String name = InputUtil.getStringInput();
        System.out.print("Enter Category Description: (optional) ");
        String description = InputUtil.readRawString();
        description = description.isEmpty() ? null : description;
        Category category = new Category(name, description);

        int categoryId = categoryController.createCategory(category);

        if (categoryId > 0) {
            System.out.println("Category added successfully!");
        } else {
            System.out.println("Failed to add category. Please try again.");
        }
    }

    private void updateCategory() {
        System.out.println("\n--- Update Category ---");
        viewAllCategories();
        System.out.print("Enter Category ID to update: ");
        int categoryId = InputUtil.getIntInput();

        Category existing = categoryController.getCategoryById(categoryId);
        if (existing == null) {
            System.out.println("Category not found!");
            return;
        }

        System.out.println("Press Enter to leave the string field unchanged.");
        System.out.print("Enter Category Name: ");
        String name = InputUtil.readRawString();
        System.out.print("Enter Category Description: (optional) ");
        String description = InputUtil.readRawString();

        name = name.isEmpty() ? existing.getName() : name;
        description = description.isEmpty() ? existing.getDescription() : description;

        existing.setName(name);
        existing.setDescription(description);

        boolean success = categoryController.updateCategory(existing);

        if (success) {
            System.out.println("Category updated successfully!");
        } else {
            System.out.println("Category not found or could not be updated.");
        }
    }

    private void deleteCategory() {
        System.out.println("\n--- Delete Category ---");
        viewAllCategories();
        System.out.print("Enter Category ID to delete: ");
        int categoryId = InputUtil.getIntInput();

        boolean success = categoryController.deleteCategory(categoryId);

        if (success) {
            System.out.println("Category deleted successfully!");
        } else {
            System.out.println("Category not found or could not be deleted.");
        }
    }

    private void searchCategoryByName() {
        System.out.println("\n--- Search Category ---");
        System.out.print("Enter name keyword: ");
        String keyword = InputUtil.getStringInput();

        Category category = categoryController.getCategoryByName(keyword);

        if (category == null) {
            System.out.println("No category found matching \"" + keyword + "\".");
        } else {
            System.out.println(category);
        }
    }

    private void viewAllCategories() {
        System.out.println("\n--- Category List ---");
        List<Category> categories = categoryController.getAllCategories();

        if (categories.isEmpty()) {
            System.out.println("No categories found in the system. Add one first.");
            return;
        }

        for (Category c : categories) {
            System.out.println(c);
        }
    }
}
