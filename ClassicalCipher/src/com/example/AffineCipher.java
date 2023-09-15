package com.example;

public class AffineCipher {

    /** Encrypt string of plaintext to cipher text using Affine cipher
     *
     * @param text input plaintext
     * @param a 1st key of the cipher
     * @param b 2nd key of the cipher
     * @return a string of Affine ciphertext encrypted from input message
     */
    public static String encrypt(String text, int a, int b) {

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {

            char tempCh = text.charAt(i);

            // Alphabet check
            if ((tempCh >= 65 && tempCh <= 90) || (tempCh >= 97 && tempCh <= 122)) {

                if (Character.isUpperCase(tempCh)) {
                    // Encrypt Uppercase letters
                    result.append(
                            (char) ((a * (tempCh - 65) + b) % 26 + 65));
                } else {
                    // Encrypt Lowercase letters
                    result.append(
                            (char) ((a * (tempCh - 97) + b) % 26 + 97));
                }
            } else {
                // ignore non-Latin Alphabet characters
                result.append(tempCh);
            }
        }

        // return encrypted string
        return result.toString();
    }

    /** Decrypt Affine ciphertext to plaintext
     *
     * @param text input ciphertext
     * @param a 1st key of the cipher
     * @param b 2nd key of the cipher
     * @return a string of plaintext decrypted from input message
     */
    public static String decrypt(String text, int a, int b) {

        StringBuilder result = new StringBuilder();
        int a_inverse = UtilAlgorithms.getReverseElement(a, 26);
        for (int i = 0; i < text.length(); i++) {

            char tempCh = text.charAt(i);

            // Alphabet check
            if ((tempCh >= 65 && tempCh <= 90) || (tempCh >= 97 && tempCh <= 122)) {

                if (Character.isUpperCase(tempCh)) {
                    // Encrypt Uppercase letters
                    int x = ((tempCh - 65) - b) * a_inverse % 26;    // |x| can be greater than 26
                    while (x < 0) {
                        x += 26;
                    }
                    result.append((char) (x + 65));
                } else {
                    // Encrypt Lowercase letters
                    int x = ((tempCh - 97) - b) * a_inverse % 26;    // |x| can be greater than 26
                    while (x < 0) {
                        x += 26;
                    }
                    result.append((char) (x + 97));
                }
            } else {
                // ignore non-Latin Alphabet characters
                result.append(tempCh);
            }
        }

        // return encrypted string
        return result.toString();
    }
}
