package src.utils;

public class Validation {
    public static void requireNonEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty())
            throw new IllegalArgumentException(fieldName + " is required");
    }

    public static void requireMinLength(String value, int min, String fieldName) {
        if (value == null || value.length() < min)
            throw new IllegalArgumentException(fieldName + " must be at least " + min + " characters");
    }
}
