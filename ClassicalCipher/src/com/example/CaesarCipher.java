package com.example;

public class CaesarCipher {

    public static String encrypt(String text, int shiftValue) {

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {

            char tempCh = text.charAt(i);

            // Alphabet check
            if ((tempCh >= 65 && tempCh <= 90) || (tempCh >= 97 && tempCh <= 122)) {

                if (Character.isUpperCase(tempCh)) {
                    // Encrypt Uppercase letters
                    result.append(
                            (char) ((tempCh - 65 + shiftValue) % 26 + 65));
                } else {
                    // Encrypt Lowercase letters
                    result.append(
                            (char) ((tempCh - 97 + shiftValue) % 26 + 97));
                }
            } else {
                // ignore non-Latin Alphabet characters
                result.append(tempCh);
            }
        }

        // return encrypted string
        return result.toString();
    }

    // default shift value = 3
    public static String encrypt(String text) {

        return encrypt(text, 3);
    }

    public static String decrypt(String text, int shiftValue) {

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {

            char tempCh = text.charAt(i);

            // Alphabet check
            if ((tempCh >= 65 && tempCh <= 90) || (tempCh >= 97 && tempCh <= 122)) {

                if (Character.isUpperCase(tempCh)) {
                    // Encrypt Uppercase letters
                    int x = ((tempCh - 65) - shiftValue) % 26;
                    if (x < 0) {
                        x += 26;
                    }
                    result.append(
                            (char) (x + 65));
                } else {
                    // Encrypt Lowercase letters
                    int x = ((tempCh - 97) - shiftValue) % 26;
                    if (x < 0) {
                        x += 26;
                    }
                    result.append(
                            (char) (x + 97));
                }
            } else {
                // ignore non-Latin Alphabet characters
                result.append(tempCh);
            }
        }

        // return decrypted string
        return result.toString();
    }

    // default shift value = 3
    public static String decrypt(String text) {

        return decrypt(text, 3);
    }
}
