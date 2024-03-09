package ru.vsu.cs.sibirko_i_s.ML_TA;

import java.util.Arrays;

public class MatrixSolver {
    public static double[] matrixSolver(int[][] matrix) {
        if (matrix.length != matrix[0].length - 1) {
            throw new IllegalArgumentException("Matrix is incorrect");
        }
        return solve(matrix);
    }

    /**
     * Метод возвращает решение системы уравнений
     * @param mainMatrix изначальная расширенная матрица
     * @return Массив с решениями системы
     */
    private static double[] solve(int[][] mainMatrix) {
        double[] res = new double[mainMatrix[0].length - 1];
        int[][] helpMatrix = buildNewMatrix(mainMatrix);
        int[] constants = getConstants(mainMatrix);

        long det = DeterminantCalculator.calculateDeterminant(helpMatrix);
        if (det == 0) {
            throw new IllegalArgumentException("Уравнение не имеет решений.");
        }

        for (int i = 0; i < res.length; i++) {
            res[i] = (DeterminantCalculator.calculateDeterminant(buildMatrixForSolves(mainMatrix, i, constants)) / (double)det);
        }
        return res;
    }

    /**
     * Функция для построения квадратной матрицы из исходной расширенной
     * @param oldMatrix изначальная расширенная матрица
     * @return newMatrix - квадратная матрица для нахождения определителя
     */
    private static int[][] buildNewMatrix(int[][] oldMatrix) {
        int[][] newMatrix = new int[oldMatrix.length][oldMatrix[0].length - 1];

        for (int i = 0; i < newMatrix.length; i++) {
            for (int j = 0; j < newMatrix[0].length; j++) {
                newMatrix[i][j] = oldMatrix[i][j];
            }
        }
        return newMatrix;
    }

    private static int[][] buildMatrixForSolves(int[][] matrix, int columnNumber, int[] constants) {
        int[][] matrixForSolve = new int[matrix.length][matrix[0].length - 1];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length - 1; j++) {
                if (j == columnNumber) {
                    matrixForSolve[i][j] = constants[i];
                } else {
                    matrixForSolve[i][j] = matrix[i][j];
                }
            }
        }
        return matrixForSolve;
    }
    private static int[] getConstants(int[][] matrix) {
        int[] res = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            res[i] = matrix[i][matrix[0].length - 1];
        }
        return res;
    }
}