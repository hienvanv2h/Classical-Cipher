package com.example;

public class VigenereCipher {

    public static String encrypt(String text, String key) {

        StringBuilder sb = new StringBuilder();

        // build key stream to match the length of plaintext
        int count = 0;
        while (count < text.length()) {
            for (int i = 0; i < key.length(); i++) {
                if (count >= text.length()) {
                    break;
                }
                char tempCh1 = text.charAt(count);
                char tempCh2 = key.charAt(i);

                if ((tempCh1 >= 65 && tempCh1 <= 90) || (tempCh1 >= 97 && tempCh1 <= 122)) {
                    // ignore spaces in key
                    if (tempCh2 == ' ' || tempCh2 == '\t' || tempCh2 == '\n') {
                        continue;
                    }

                    sb.append(tempCh2);
                } else {
                    // ignore non-alphabet char from text
                    i--;
                }
                count++;
            }
        }

        String key_stream = sb.toString().toUpperCase();    // convert all to upper case letter

        // encrypt
        sb = new StringBuilder();
        char temp1, temp2;
        count = 0;
        for (int i = 0; i < text.length(); i++) {

            temp1 = text.charAt(i);

            // Alphabet check
            if ((temp1 >= 65 && temp1 <= 90) || (temp1 >= 97 && temp1 <= 122)) {
                temp2 = key_stream.charAt(count);
                if (Character.isUpperCase(temp1)) {
                    sb.append(
                            (char) (((temp1 - 65) + (temp2 - 65)) % 26 + 65));
                } else {
                    sb.append(
                            (char) (((temp1 - 97) + (temp2 - 65)) % 26 + 97));
                }

                count++;
            } else {
                sb.append(temp1);
            }
        }

        return sb.toString();
    }

    public static String decrypt(String text, String key) {

        StringBuilder sb = new StringBuilder();

        // build key stream to match the length of ciphertext
        int count = 0;
        while (count < text.length()) {
            for (int i = 0; i < key.length(); i++) {
                if (count == text.length()) {
                    break;
                }
                char tempCh1 = text.charAt(count);
                char tempCh2 = key.charAt(i);

                // ignore spaces
                if ((tempCh1 >= 65 && tempCh1 <= 90) || (tempCh1 >= 97 && tempCh1 <= 122)) {
                    // ignore spaces in key
                    if (tempCh2 == ' ' || tempCh2 == '\t' || tempCh2 == '\n') {
                        continue;
                    }

                    sb.append(tempCh2);
                } else {
                    // ignore non-alphabet char from text
                    i--;
                }
                count++;
            }
        }

        String key_stream = sb.toString().toUpperCase();    // convert all to upper case letter

        // decrypt
        sb = new StringBuilder();
        char temp1, temp2;
        count = 0;
        for (int i = 0; i < text.length(); i++) {

            temp1 = text.charAt(i);

            // Alphabet check
            if ((temp1 >= 65 && temp1 <= 90) || (temp1 >= 97 && temp1 <= 122)) {
                temp2 = key_stream.charAt(count);
                if (Character.isUpperCase(temp1)) {
                    int x = ((temp1 - 65) - (temp2 - 65)) % 26;
                    if (x < 0) {
                        x += 26;
                    }

                    sb.append(
                            (char) (x + 65));
                } else {
                    int x = ((temp1 - 97) - (temp2 - 65)) % 26;
                    if (x < 0) {
                        x += 26;
                    }

                    sb.append(
                            (char) (x + 97));
                }

                count++;
            } else {
                sb.append(temp1);
            }

        }
        return sb.toString();
    }
}
