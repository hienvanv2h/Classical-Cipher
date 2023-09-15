package com.example;

public class HillCipher {

    private static final int numbOfChar = 26;       // Alphabet: A - Z

    /** Define how each block of n letters
     *  (considered as an n-component vector) is multiplied by an invertible n × n matrix (key),
     *   again modulus number of characters
     *
     */
    public enum BlockPattern {
        ROW_VECTOR, COLUMN_VECTOR
    }

    /** Encrypt string of plaintext to cipher text using Hill cipher
     *
     * @param text input plaintext
     * @param key square matrix key (2x2 / 3x3)
     * @return a string of Hill ciphertext encrypted from input message
     */
    public static String encrypt(String text, int[][] key, BlockPattern pattern) {

        // encrypt
        int len = key.length;
        String[] strArr = stringToSubstringArr(text, len);
        for (int i = 0; i < strArr.length; i++) {
            int[] intArr = stringToIntArr(strArr[i]);

            // get cipher values
            // reuse this intArr (since result is a vector with the same dimension)

            // check encrypt method
            if (pattern == BlockPattern.COLUMN_VECTOR) {
                intArr = UtilAlgorithms.modulo(
                        UtilAlgorithms.multiplyMatrixByVector(key, intArr), numbOfChar);
            } else {
                intArr = UtilAlgorithms.modulo(
                        UtilAlgorithms.multiplyVectorByMatrix(key, intArr), numbOfChar);
            }


            // convert back to string
            String temp = intArrToString(intArr, strArr[i]);

            // save string to strArr
            strArr[i] = temp;
        }

        // build ciphertext
        String result = buildString(text, strArr);

        // return encrypted string
        return result;
    }

    // default encrypt method is C = P*K mod 26
    public static String encrypt(String text, int[][] key) {

        return encrypt(text, key, BlockPattern.ROW_VECTOR);
    }

    /** Decrypt Hill ciphertext to plaintext
     *
     * @param text ciphertext
     * @param key square matrix key (2x2 / 3x3)
     * @return decrypted message
     */
    public static String decrypt(String text, int[][] key, BlockPattern pattern) {

        int len = key.length;

        // Find key inverse: K^-1 = det(K)^-1 * adj(K) mod 26

        // find det(K)^-1
        int det = UtilAlgorithms.modulo(
                UtilAlgorithms.det(key), numbOfChar);   // get det(K)
        det = UtilAlgorithms.getReverseElement(
                det, numbOfChar);    // get det(K)^-1 - satisfy: det(K)^-1 * det(K) mod 26 = 1

        det = UtilAlgorithms.modulo(det, numbOfChar);     // det(K)^-1 mod 26 should return positive number
        // find adj(K)
        int[][] adjK = UtilAlgorithms.modulo(
                UtilAlgorithms.adjointOf(key), numbOfChar);
        int[][] key_inv = UtilAlgorithms.modulo(
                UtilAlgorithms.multiply(adjK, det), numbOfChar);   // key inverse

        // decrypt
        String[] strArr = stringToSubstringArr(text, len);
        for (int i = 0; i < strArr.length; i++) {
            int[] intArr = stringToIntArr(strArr[i]);

            // get decipher values
            // reuse this intArr (since result is a vector with the same dimension)
            if (pattern == BlockPattern.COLUMN_VECTOR) {
                intArr = UtilAlgorithms.modulo(
                        UtilAlgorithms.multiplyMatrixByVector(key_inv, intArr), numbOfChar);
            } else {
                intArr = UtilAlgorithms.modulo(
                        UtilAlgorithms.multiplyVectorByMatrix(key_inv, intArr), numbOfChar);
            }


            // convert back to string
            String temp = intArrToString(intArr, strArr[i]);

            // save string to strArr
            strArr[i] = temp;
        }

        // build string
        String result = buildString(text, strArr);

        return result;
    }

    // default decrypt: P = C*K^-1 mod 26
    public static String decrypt(String text, int[][] key) {

        return decrypt(text, key, BlockPattern.ROW_VECTOR);
    }

    // Function method(s)

    private static String[] stringToSubstringArr(String text, int length) {

        StringBuilder sb = new StringBuilder();
        int count = 0;
        while (count < text.length()) {
            for (int i = 0; i < length; i++) {

                if (count >= text.length()) {
                    sb.append("Z");
                    continue;
                }

                char tempCh = text.charAt(count);

                if (tempCh < 65 || (tempCh > 90 && tempCh < 97) || tempCh > 122) {
                    i--;
                    count++;
                    continue;
                }
                sb.append(tempCh);
                count++;
            }
            if (count < text.length())
                sb.append(" ");
        }

        return sb.toString().split(" ");
    }

    private static int[] stringToIntArr(String str) {

        int[] result = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            char tempCh = str.charAt(i);
            result[i] = (Character.isUpperCase(tempCh)) ? (tempCh - 65) : (tempCh - 97);
        }

        return result;
    }

    private static String intArrToString(int[] arr, String str) {

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            // check case-sensitive... comparing with input string
            if (Character.isUpperCase(str.charAt(i))) {
                result.append((char) (arr[i] + 65));
            } else {
                result.append((char) (arr[i] + 97));
            }
        }

        return result.toString();
    }

    private static String buildString(String text, String[] strArr) {

        StringBuilder result = new StringBuilder();
        int count = 0;
        for (String s : strArr) {
            for (int j = 0; j < s.length(); j++) {
                if (count == text.length()) {
                    result.append(s.charAt(j));
                    continue;
                }

                char tempCh = text.charAt(count);
                if (tempCh == ' ' || tempCh == '\t' || tempCh == '\n') {
                    result.append(tempCh);
                    j--;
                    count++;
                    continue;
                }
                result.append(s.charAt(j));
                count++;
            }
        }

        return result.toString();
    }
}
