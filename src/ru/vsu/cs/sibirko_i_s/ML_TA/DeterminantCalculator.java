package ru.vsu.cs.sibirko_i_s.ML_TA;

public class DeterminantCalculator {
    public static long calculateDeterminant(int[][] matrix) {
        if (matrix.length != matrix[0].length) {
            throw new IllegalArgumentException("Матрица должна быть квадратной");
        }
        return determinant(matrix);
    }

    private static long determinant(int[][] matrix) {
        if (matrix.length == 1) {
            return matrix[0][0];
        }

        if (matrix.length == 2) {
            return (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]);
        }

        long res = 0;
        for (int i = 0; i < matrix[0].length; i++) {
            res += (Math.pow(-1, i) * matrix[0][i] * determinant(buildSubMatrix(matrix, 0, i)));
        }

        return res;
    }

    private static int[][] buildSubMatrix(int[][] matrix, int rowToRemove, int colToRemove) {
        int[][] newMatrix = new int[matrix.length - 1][matrix[0].length - 1];
        int newRow = 0;
        int newCol;
        for (int i = 1; i < matrix.length; i++) {
            newCol = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                if (j == colToRemove) {
                    continue;
                }
                newMatrix[newRow][newCol] = matrix[i][j];
                newCol++;
            }
            newRow++;
        }
        return newMatrix;
    }
}