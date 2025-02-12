package controller;

import java.util.ArrayList;
import java.util.List;

public class CategoryController {
    private static List<String> categories = new ArrayList<>();

    public static boolean addCategory(String categoryName) {
        if (!categories.contains(categoryName) && !categoryName.isEmpty()) {
            categories.add(categoryName);
            return true;
        }
        return false;
    }

    public static List<String> getCategories() {
        return categories;
    }
}