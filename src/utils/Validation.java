package src.utils;

import java.util.regex.Pattern;

import src.exceptions.InvalidEmailException;

public class Validation {
    public static void requireNonEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty())
            throw new IllegalArgumentException(fieldName + " is required");
    }

    public static void requireMinLength(String value, int min, String fieldName) {
        if (value == null || value.length() < min)
            throw new IllegalArgumentException(fieldName + " must be at least " + min + " characters");
    }

    // Method to check if the email is valid
    public static boolean isValidEmail(String email) {

        // Regular expression to match valid email formats
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Compile the regex
        Pattern p = Pattern.compile(emailRegex);

        // Check if email matches the pattern
        return email != null && p.matcher(email).matches();
    }

    public static boolean isValidMobileNumber(String mobileNo) {
        String mobileNoRegex = "^\\d{10}$";

        Pattern p = Pattern.compile(mobileNoRegex);

        return p.matcher(mobileNoRegex).matches();
    }

    public static void validateEmail(String email) {
        requireNonEmpty(email, "email");
        if (!isValidEmail(email)) {
            throw new InvalidEmailException("Email : " + email);
        }
    }

    public static void validatePassword(String password) {
        requireNonEmpty(password, "password");
        requireMinLength(password, 8, "password");
    }

}
