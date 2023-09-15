package com.example;

import java.io.*;
import java.util.Scanner;

public class Main {

    private static StringBuilder inputFile = new StringBuilder();

    public static void main(String[] args) {

        // encrypt
        encryptFile();
        // decrypt
//        decryptFile();
    }

    private static void loadData(String filePath) throws IOException {

        // read a file
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String input;
            while ((input = br.readLine()) != null) {
                inputFile.append(input);

                // check end of file
                int currentChar = br.read();
                if (currentChar == -1) {
                    break;
                }
                inputFile.append("\n");
                inputFile.append((char) currentChar);
            }
            System.out.println("File loaded successfully!");
        }
    }

    private static void encryptFile() {

        // write to a file
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("ciphertext.txt"))) {
            // read file
            loadData("plaintext.txt");

            Scanner scanner = new Scanner(System.in);
            System.out.println("""
                    Please choose encryption option:
                    C - Caesar cipher
                    A - Affine cipher
                    H - Hill cipher
                    V - Vigenere cipher""");
            String option = scanner.nextLine().toUpperCase();
            switch (option) {
                case "C", "CAESAR" -> {
                    System.out.println("Enter shift value: ");
                    bw.write(CaesarCipher.encrypt(inputFile.toString(), scanner.nextInt()));
                }
                case "A", "AFFINE" -> {
                    System.out.println("Enter key K - (a, b): ");
                    int a = scanner.nextInt();
                    int b = scanner.nextInt();
                    bw.write(AffineCipher.encrypt(inputFile.toString(), a, b));
                }
                case "H", "HILL" -> {
                    System.out.println("Enter size of key matrix - n: ");
                    int n = scanner.nextInt();
                    int[][] key = new int[n][n];
                    System.out.println("Enter key's elements:");
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < n; j++) {
                            scanner = new Scanner(System.in);
                            System.out.format("K[%d][%d] = ", i, j);
                            key[i][j] = scanner.nextInt();
                        }
                    }
                    scanner = new Scanner(System.in);
                    System.out.println("""
                            Select Encryption Formula:
                                1 - C = P*K mod N
                                2 - C = K*P mod N""");
                    if (scanner.nextInt() == 2) {
                        bw.write(HillCipher
                                .encrypt(inputFile.toString(), key, HillCipher.BlockPattern.COLUMN_VECTOR));
                    } else {
                        bw.write(HillCipher
                                .encrypt(inputFile.toString(), key));
                    }
                }
                case "V", "VIGENERE" -> {
                    System.out.println("Enter key string: ");
                    bw.write(VigenereCipher.encrypt(inputFile.toString(), scanner.nextLine()));
                }
                default -> {
                    System.out.println("Invalid encryption option! Failed to encrypt\nShutdown Program.");
                    return;
                }
            }
            System.out.println("Encrypt succeeded to ciphertext.txt!");
        } catch (IOException e) {
            System.out.println();
            e.printStackTrace();
        }
    }

    private static void decryptFile() {

        // write to a file
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("plaintext2.txt"))) {
            // read file
            loadData("ciphertext.txt");

            Scanner scanner = new Scanner(System.in);
            System.out.println("""
                    Please choose decryption option:
                    C - Caesar cipher
                    A - Affine cipher
                    H - Hill cipher
                    V - Vigenere cipher""");
            String option = scanner.nextLine().toUpperCase();
            switch (option) {
                case "C", "CAESAR" -> {
                    System.out.println("Enter shift value: ");
                    bw.write(CaesarCipher.decrypt(inputFile.toString(), scanner.nextInt()));
                }
                case "A", "AFFINE" -> {
                    System.out.println("Enter key K - (a, b): ");
                    int a = scanner.nextInt();
                    int b = scanner.nextInt();
                    bw.write(AffineCipher.decrypt(inputFile.toString(), a, b));
                }
                case "H", "HILL" -> {
                    System.out.println("Enter size of key matrix - n: ");
                    int n = scanner.nextInt();
                    int[][] key = new int[n][n];
                    System.out.println("Enter key's elements:");
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < n; j++) {
                            scanner = new Scanner(System.in);
                            System.out.format("K[%d][%d] = ", i, j);
                            key[i][j] = scanner.nextInt();
                        }
                    }
                    scanner = new Scanner(System.in);
                    System.out.println("""
                            Select Decryption Formula:
                                1 - P = C * K^(-1) mod N
                                2 - P = K^(-1) * C mod N""");
                    if (scanner.nextInt() == 2) {
                        bw.write(HillCipher
                                .decrypt(inputFile.toString(), key, HillCipher.BlockPattern.COLUMN_VECTOR));
                    } else {
                        bw.write(HillCipher
                                .decrypt(inputFile.toString(), key));
                    }
                }
                case "V", "VIGENERE" -> {
                    System.out.println("Enter key string: ");
                    bw.write(VigenereCipher.decrypt(inputFile.toString(), scanner.nextLine()));
                }
                default -> {
                    System.out.println("Invalid decryption option! Failed to decrypt\nShutdown Program.");
                    return;
                }
            }
            System.out.println("Decrypt succeeded to plaintext2.txt!");
        } catch (IOException e) {
            System.out.println();
            e.printStackTrace();
        }
    }
}
