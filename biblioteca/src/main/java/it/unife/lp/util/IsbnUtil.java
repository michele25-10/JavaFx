package it.unife.lp.util;

public class IsbnUtil {
    // Validazione ISBN-13
    public static boolean isValidISBN(String isbn) {
        if (!isbn.matches("\\d{13}"))
            return false;

        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = isbn.charAt(i) - '0';
            sum += (i % 2 == 0) ? digit : digit * 3;
        }

        int checksum = 10 - (sum % 10);
        checksum = (checksum == 10) ? 0 : checksum;

        return checksum == (isbn.charAt(12) - '0');
    }
}
