package com.example;

public class UtilAlgorithms {

    /** Find Greatest Common Divisor of 2 numbers - gcd(a,b) using Euclidean Algorithms
     *
     * @param a 1st number
     * @param b 2nd number
     * @return value of greatest common divisor of a and b
     */
    public static int gcd(int a, int b) {

        // zero check
        if (a == 0 || b == 0) {
            return Math.max(Math.abs(a), Math.abs(b));      // gcd(n, 0) = |n|
        }

        // gcd{a,b}=gcd{|a|,b}=gcd{a,|b|}=gcd{|a|,|b|}
        if (a < 0) a = Math.abs(a);
        if (b < 0) b = Math.abs(b);

        int result = Math.min(a, b);
        int remainder = (a > b) ? (a % b) : (b % a);

        // recursive solution:
//        if (remainder != 0) {
//            result = gcd(result, remainder);
//        }

        int temp;
        while (remainder != 0) {
            temp = remainder;
            remainder = result % remainder;
            result = temp;
        }

        return result;
    }

    /** Find inverse element - using Extended Euclidean Algorithms.
     * <pre>
     *      Input: a, n - (a,n) = 1, n > a > 0
     *      Output: a^(-1) - modular multiplicative inverse of a modulo n
     *
     *      it satisfies the equation: a * a^-1 = 1 (mod n)
     * </pre>
     */
    public static int getReverseElement(int a, int n) {

        // validation
        if (gcd(a, n) != 1 || n == 0 || a == 0) {
            System.out.println("Inverse element does not exist");
            return -1;
        }

        int y, temp;

        // anonymous int arrays
        int[] g = new int[]{n, a};      // g_0 = n, g_1 = a
        int[] v = new int[]{0, 1};      // v_0 = 0, v_1 = 1

        // loop
        do {
            y = g[0] / g[1];        // y - quotient

            temp = g[1];
            g[1] = g[0] % g[1];
            g[0] = temp;

            temp = v[1];
            v[1] = v[0] - y * v[1];
            v[0] = temp;
        } while (g[1] > 1);

        return v[1];
    }


    /** FAST EXPONENTIATION ALGORITHM
     * <pre>
     *      Input: a, n, p
     *      Output: a^n mod p
     * </pre>
     */
    public static int fastExponentiationAlgorithm(int a, int n, int p) {

        // validation
        if (p <= 0) {
            System.out.println("Invalid input value - p should greater than 0");
            return -1;
        }

        // convert decimal number to  binary string
        String binaryNumber = Integer.toBinaryString(n);

        // get reversed string
        binaryNumber = new StringBuilder(binaryNumber).reverse().toString();

        int result = 1;
        int x = a;
        for (int i = 0; i < binaryNumber.length(); i++) {

            int m = Character.getNumericValue(binaryNumber.charAt(i));  // get int value from char at index i
            if (m == 1) {
                result = (result * x) % p;
            }
            x = (x * x) % p;
        }

        return result;
    }

    ////////////////////////////////////////////
    // LINEAR ALGEBRA

    /** Find determinant of a square (n x n) matrix
     * NOTE: This method can only work with 2x2 or 3x3 matrix
     *
     * @param m
     * @return determinant value of this matrix
     */
    public static int det(int[][] m) {

        // check if m is not a square matrix
        if (m.length != m[0].length) {
            System.out.println("The determinant only exists for square matrices (2×2, 3×3, ... n×n)");
            return -1;
        }

        int result = 0;

        // For a 2x2 matrix: det(M) = ad - bc
        if (m.length == 2) {
            result = m[0][0] * m[1][1] - m[0][1] * m[1][0];
        }

        // For a 3x3 matrix: det(M) = a * (e*i - f*h) - b * (d*i - f*g) + c * (d*h - e*g)
        if (m.length == 3) {
            result = m[0][0]*(m[1][1]*m[2][2] - m[1][2]*m[2][1])
                    - m[0][1]*(m[1][0]*m[2][2] - m[1][2]*m[2][0])
                    + m[0][2]*(m[1][0]*m[2][1] - m[1][1]*m[2][0]);
        }

        return result;
    }

    /** Find adjoint of a given matrix
     * NOTE: This method should work with 2x2 and 3x3 matrix
     *
     * @param m
     * @return an adjoint matrix of m
     */
    public static int[][] adjointOf(int[][] m) {

        int[][] result = cofactorOf(m);
        result = transposeOf(result);

        return result;
    }

    /** Replace each element of a square matrix with appropriate cofactor element
     *  and return its adjugate matrix
     *
     * @param m
     * @return a adjugate matrix from this matrix - m
     */
    public static int[][] cofactorOf(int[][] m) {

        int row = m.length, col = m[0].length;
        int[][] result = new int[row][col];
        if (col == row) {

            // 2x2 matrix
            if (col == 2) {
                result[0][0] = m[1][1];
                result[0][1] = -m[1][0];
                result[1][0] = -m[0][1];
                result[1][1] = m[0][0];
            }

            // 3x3 matrix
            if (col == 3) {
                // Cofactor of the element a_ij = (-1)^(i+j) * det(M_ij)
                // det(M_ij) is called minor of a_ij
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        result[i][j] = (int) Math.pow(-1, i+j+2) * minorOf(m, i, j);
                    }
                }
            }
        }

        return result;
    }

    /** Transpose the matrix. Can work with non-square matrix
     *
     * @param m matrix
     * @return a transpose of this matrix
     */
    public static int[][] transposeOf(int[][] m) {

        // If m is MxN matrix, then result should be NxM matrix
        int[][] result = new int[m[0].length][m.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                result[j][i] = m[i][j];
            }
        }

        return result;
    }

    // Multiply method(s)

    /** Multiply a row vector by a matrix
     *
     * @param matrix matrix
     * @param rowVector row vector (1xN)
     * @return Row vector (1xM) x Matrix (MxN) = Row vector (1xN)
     */
    public static int[] multiplyVectorByMatrix(int[][] matrix, int[] rowVector) {

        int row = matrix.length;
        int col = matrix[0].length;

        // Row vector (1xM) x matrix (MxN) = row vector (1xN)
        // Matrix (MxN) x column vector (Nx1) = column vector (Mx1)
        int[] result = new int[rowVector.length];

        // check and return if not match dimension
        if (row != rowVector.length) {
            System.out.println("Dimension not match: (1xM) x (MxN) = (1xN)");
            return result;
        }

        for (int i = 0; i < row; i++) {
            int sum = 0;
            for (int j = 0; j < col; j++) {
                sum += rowVector[j] * matrix[j][i];
            }
            result[i] = sum;
        }

        return result;
    }

    /** Multiply a matrix by a column vector
     *
     * @param matrix matrix
     * @param columnVector column vector (1xN)
     * @return Matrix (MxN) x Column vector (Nx1) = Column vector (Mx1)
     */
    public static int[] multiplyMatrixByVector(int[][] matrix, int[] columnVector) {

        int row = matrix.length;
        int col = matrix[0].length;

        // Row vector (1xM) x matrix (MxN) = row vector (1xN)
        // Matrix (MxN) x column vector (Nx1) = column vector (Mx1)
        int[] result = new int[columnVector.length];

        // check and return if not match dimension
        if (col != columnVector.length) {
            System.out.println("Dimension not match: (MxN) x (Nx1) = (Mx1)");
            return result;
        }

        for (int i = 0; i < row; i++) {
            int sum = 0;
            for (int j = 0; j < col; j++) {
                sum += matrix[i][j] * columnVector[j];
            }
            result[i] = sum;
        }

        return result;
    }

    /** Multiply a matrix by a number
     *
     * @param matrix matrix
     * @param n number
     * @return a new matrix which has its elements multiplied with n
     */
    public static int[][] multiply(int[][] matrix, int n) {

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] *= n;
            }
        }

        return matrix;
    }

    // Modulo
    public static int[][] modulo(int[][] m, int divisor) {

        int[][] result = new int[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                result[i][j] = (m[i][j] >= 0) ? (m[i][j] % divisor) : (m[i][j] % divisor + divisor);
            }
        }

        return result;
    }

    public static int[] modulo(int[] v, int divisor) {

        int[] result = new int[v.length];
        for (int i = 0; i < v.length; i++) {
            result[i] = (v[i] >= 0) ? (v[i] % divisor) : (v[i] % divisor + divisor);
        }

        return result;
    }

    public static int modulo(int n, int divisor) {

        return Math.floorMod(n, divisor);
    }

    // Private function method(s)

    /** Find det(M_ij) - minor of element a_ij in matrix M
     * NOTE: This method only work with 3x3 matrix
     *
     * @param matrix
     * @param a row index (start at 0)
     * @param b column index (start at 0)
     * @return det(M_ij)
     */
    private static int minorOf(int[][] matrix, int a, int b) {

        int len = matrix.length - 1;
        int[][] temp = new int[len][len];

        int k = 0;
        // iterate input matrix
        for (int i = 0; i < matrix.length; i++) {
            int l = 0;
            if (i == a) continue;

            for (int j = 0; j < matrix.length; j++) {
                if (j == b) continue;

                temp[k][l] = matrix[i][j];
                l++;
            }
            k++;
        }

        int result = det(temp);
        return result;
    }
}
