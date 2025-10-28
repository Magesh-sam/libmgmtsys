package src.view;

import src.model.pojo.Category;
import src.service.CategoryService;
import java.util.List;
import java.util.Scanner;

public class CategoryView {
    private final Scanner sc = new Scanner(System.in);
    private final CategoryService categoryService = new CategoryService();

    public void display() {
        int choice;

        while (true) {
            System.out.println("\n===== Category Management =====");
            System.out.println("1. Add Category");
            System.out.println("2. View All Categories");
            System.out.println("3. Update Category");
            System.out.println("4. Delete Category");
            System.out.println("5. Search Category by Name");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            String input = sc.nextLine().trim();

            if (!input.matches("\\d+")) {
                System.out.println("Invalid input! Please enter a number (1–6).");
                continue;
            }

            choice = Integer.parseInt(input);

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
                default -> System.out.println("Invalid choice! Enter between 1–6.");
            }
        }
    }

    private void addCategory() {
        System.out.println("\n--- Add New Category ---");
        System.out.print("Enter Category Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Enter Description: ");
        String description = sc.nextLine().trim();

        Category category = new Category(name, description);
        boolean success = categoryService.addCategory(category);

        if (success) {
            System.out.println("Category added successfully!");
        } else {
            System.out.println("Failed to add category. Please try again.");
        }
    }

    private void viewAllCategories() {
        System.out.println("\n--- Category List ---");
        List<Category> categories = categoryService.getAllCategories();

        if (categories.isEmpty()) {
            System.out.println("No categories found in the system.");
            return;
        }

        for (Category c : categories) {
            System.out.println(c);
        }
    }

    private void updateCategory() {
        System.out.println("\n--- Update Category ---");
        System.out.print("Enter Category ID to update: ");
        String idInput = sc.nextLine().trim();

        if (!idInput.matches("\\d+")) {
            System.out.println("Invalid ID! Please enter a valid number.");
            return;
        }

        int categoryId = Integer.parseInt(idInput);

        Category existing = categoryService.getCategoryById(categoryId);
        if (existing == null) {
            System.out.println("Category not found!");
            return;
        }

        System.out.print("Enter new Name (" + existing.getName() + "): ");
        String name = sc.nextLine().trim();
        System.out.print("Enter new Description (" + existing.getDescription() + "): ");
        String description = sc.nextLine().trim();

        if (!name.isEmpty()) existing.setName(name);
        if (!description.isEmpty()) existing.setDescription(description);

        boolean success = categoryService.updateCategory(existing);

        if (success) {
            System.out.println("Category updated successfully!");
        } else {
            System.out.println("Update failed!");
        }
    }

    private void deleteCategory() {
        System.out.println("\n--- Delete Category ---");
        System.out.print("Enter Category ID to delete: ");
        String idInput = sc.nextLine().trim();

        if (!idInput.matches("\\d+")) {
            System.out.println("Invalid ID! Please enter a valid number.");
            return;
        }

        int categoryId = Integer.parseInt(idInput);

        boolean success = categoryService.deleteCategory(categoryId);

        if (success) {
            System.out.println("Category deleted successfully!");
        } else {
            System.out.println("Category not found or could not be deleted.");
        }
    }

    private void searchCategoryByName() {
        System.out.println("\n--- Search Category ---");
        System.out.print("Enter category name keyword: ");
        String keyword = sc.nextLine().trim();

        List<Category> results = categoryService.searchCategoryByName(keyword);

        if (results.isEmpty()) {
            System.out.println("No categories found matching \"" + keyword + "\".");
            return;
        }

        for (Category c : results) {
            System.out.println(c);
        }
    }
}
