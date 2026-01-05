package Implementation;
/**
 * 선형 독립 알고리즘 구현(Linear Independent Algorithm)
 * 다양한 알고리즘을 사용하여 벡터 집합이 선형 독립인지 확인하는 구현:
 * 1. 가우스 소거법 (행 축약)
 * 2. 행렬식 방법 (정사각 행렬용)
 * 3. 계수(Rank) 계산
 * 4. 그람-슈미트 과정
 */

/**
 * 여러 벡터가 서로 의존하지 않고
 * 각각 혼자서의 의미를 가지고
 * 벡터들이 독립적인 존재인지 확인하는 구현, 즉 선형적으로 독립임을 확인합니다.
 */
import java.util.Arrays;

public class LinearIndependent {

    // 부동소수점 오차를 처리하기 위한 작은 값
    private static final double EPSILON = 1e-10;

    /**
     * 방법 1: 가우스 소거법을 사용한 선형 독립성 검사
     *
     * 한 벡터가 다른 벡터들에 의존하지않고
     * 혼자서도 설명 가능한지(독립적인지) 확인하기 위한 과정
     *
     *
     * 시간 복잡도: O(n^3)
     *
     * @param vectors 벡터 배열 (각 벡터는 열로 표현)
     * @return 벡터들이 선형 독립이면 true, 아니면 false
     */
    public static boolean isLinearlyIndependentGaussian(double[][] vectors) {
        if (vectors == null || vectors.length == 0) {
            return false;
        }

        int rows = vectors.length;      // 각 벡터의 성분 개수
        int cols = vectors[0].length;   // 벡터 개수

        System.out.println("가우스 소거법을 사용한 선형 독립성 검사");
        System.out.println("행렬 크기: " + rows + " x " + cols);

        // 원본을 수정하지 않기 위해 복사본 생성
        double[][] matrix = copyMatrix(vectors);

        // 가우스 소거법을 수행하여 행 간소 계단 형태로 변환
        int rank = gaussianElimination(matrix);

        System.out.println("계수(Rank): " + rank);
        System.out.println("벡터 개수: " + cols);

        // 계수가 벡터 개수와 같으면 선형 독립
        return rank == cols;
    }

    /**
     * 방법 2: 행렬식을 사용한 선형 독립성 검사
     *
     * R^n의 n개 벡터에 대해, 이 벡터들로 만든 행렬의 행렬식이
     * 0이 아니면 선형 독립이다.
     *
     * 주의: 정사각 행렬(R^n의 n개 벡터)에만 작동
     *
     * 시간 복잡도: O(n^3)
     *
     * @param vectors 벡터 배열 (각 벡터는 열로 표현)
     * @return 벡터들이 선형 독립이면 true, 아니면 false
     */
    public static boolean isLinearlyIndependentDeterminant(double[][] vectors) {
        if (vectors == null || vectors.length == 0) {
            return false;
        }

        int rows = vectors.length;
        int cols = vectors[0].length;

        // 행렬식 방법은 정사각 행렬에만 적용 가능
        if (rows != cols) {
            System.out.println("행렬식 방법은 정사각 행렬(R^n의 n개 벡터)이 필요합니다");
            return false;
        }

        System.out.println("행렬식을 사용한 선형 독립성 검사");

        double det = determinant(vectors);
        System.out.println("행렬식: " + det);

        // 행렬식이 0이 아니면 선형 독립
        return Math.abs(det) > EPSILON;
    }

    /**
     * 방법 3: 계수(Rank)를 사용한 선형 독립성 검사
     *
     * 계수가 벡터 개수와 같으면 선형 독립
     *
     * @param vectors 벡터 배열 (각 벡터는 열로 표현)
     * @return 벡터들이 선형 독립이면 true, 아니면 false
     */
    public static boolean isLinearlyIndependentRank(double[][] vectors) {
        if (vectors == null || vectors.length == 0) {
            return false;
        }

        int cols = vectors[0].length;

        System.out.println("계수(Rank)를 사용한 선형 독립성 검사");

        int rank = calculateRank(vectors);
        System.out.println("계수(Rank): " + rank);
        System.out.println("벡터 개수: " + cols);

        return rank == cols;
    }

    /**
     * 가우스 소거법을 수행하여 행 간소 계단 형태로 변환
     * 행렬의 계수(rank)를 반환
     */
    private static int gaussianElimination(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int rank = 0;

        System.out.println("\n원본 행렬:");
        printMatrix(matrix);

        for (int col = 0; col < cols && rank < rows; col++) {
            // 피벗 찾기
            int pivotRow = findPivot(matrix, rank, col);

            if (pivotRow == -1) {
                // 이 열에 피벗이 없으면 건너뛰기
                continue;
            }

            // 필요한 경우 행 교환
            if (pivotRow != rank) {
                swapRows(matrix, pivotRow, rank);
                System.out.println("\n행 " + pivotRow + "와 " + rank + " 교환 후:");
                printMatrix(matrix);
            }

            // 피벗을 1로 만들기 위해 행 스케일링
            double pivot = matrix[rank][col];
            scaleRow(matrix, rank, 1.0 / pivot);
            System.out.println("\n행 " + rank + " 스케일링 후:");
            printMatrix(matrix);

            // 피벗 아래의 항목들을 0으로 만들기
            for (int i = rank + 1; i < rows; i++) {
                double factor = matrix[i][col];
                if (Math.abs(factor) > EPSILON) {
                    addScaledRow(matrix, i, rank, -factor);
                }
            }

            System.out.println("\n열 " + col + "의 피벗 아래 소거 후:");
            printMatrix(matrix);

            rank++;
        }

        System.out.println("\n행 간소 계단 형태:");
        printMatrix(matrix);

        return rank;
    }

    /**
     * startRow부터 시작하여 주어진 열에서 피벗 행 찾기
     */
    private static int findPivot(double[][] matrix, int startRow, int col) {
        int rows = matrix.length;
        int maxRow = startRow;
        double maxVal = Math.abs(matrix[startRow][col]);

        // 가장 큰 절댓값을 가진 행 찾기 (부분 피벗팅)
        for (int i = startRow + 1; i < rows; i++) {
            double val = Math.abs(matrix[i][col]);
            if (val > maxVal) {
                maxVal = val;
                maxRow = i;
            }
        }

        // 모든 값이 거의 0이면 피벗 없음
        if (maxVal < EPSILON) {
            return -1;
        }

        return maxRow;
    }

    /**
     * 라플라스 전개를 사용한 행렬식 계산
     *
     */
    private static double determinant(double[][] matrix) {
        int n = matrix.length;

        // 기저 케이스: 1x1 행렬
        if (n == 1) {
            return matrix[0][0];
        }

        // 기저 케이스: 2x2 행렬
        if (n == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }

        double det = 0;

        // 첫 번째 행을 따라 전개
        for (int j = 0; j < n; j++) {
            double cofactor = matrix[0][j] * cofactor(matrix, 0, j);
            det += cofactor;
        }

        return det;
    }


    private static double cofactor(double[][] matrix, int row, int col) {
        double[][] minor = getMinor(matrix, row, col);
        double sign = ((row + col) % 2 == 0) ? 1 : -1;
        return sign * determinant(minor);
    }

    /**
     * 지정된 행과 열을 제거하여 소행렬(minor) 얻기
     */
    private static double[][] getMinor(double[][] matrix, int excludeRow, int excludeCol) {
        int n = matrix.length;
        double[][] minor = new double[n - 1][n - 1];

        int minorRow = 0;
        for (int i = 0; i < n; i++) {
            if (i == excludeRow) continue;

            int minorCol = 0;
            for (int j = 0; j < n; j++) {
                if (j == excludeCol) continue;
                minor[minorRow][minorCol] = matrix[i][j];
                minorCol++;
            }
            minorRow++;
        }

        return minor;
    }

    /**
     * 행렬의 계수(rank) 계산
     */
    private static int calculateRank(double[][] matrix) {
        double[][] copy = copyMatrix(matrix);
        return gaussianElimination(copy);
    }


    /**
     * 가우스 소거법 (선형 시스템 해결용)
     */
    private static void gaussianEliminationWithBackSubstitution(double[][] augmented) {
        int rows = augmented.length;
        int cols = augmented[0].length - 1; // 확대 열 제외

        // 전진 소거
        for (int col = 0; col < Math.min(rows, cols); col++) {
            int pivotRow = findPivot(augmented, col, col);

            if (pivotRow == -1) continue;

            if (pivotRow != col) {
                swapRows(augmented, pivotRow, col);
            }

            double pivot = augmented[col][col];
            scaleRow(augmented, col, 1.0 / pivot);

            for (int i = col + 1; i < rows; i++) {
                double factor = augmented[i][col];
                addScaledRow(augmented, i, col, -factor);
            }
        }

        for (int i = Math.min(rows, cols) - 1; i >= 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                double factor = augmented[j][i];
                addScaledRow(augmented, j, i, -factor);
            }
        }
    }


    private static void extractSolution(double[][] matrix, double[] solution) {
        int rows = matrix.length;
        int cols = solution.length;

        // 자유 변수는 1로 설정, 피벗 변수는 방정식에서 계산
        Arrays.fill(solution, 0);

        // 자유 변수 찾기 (피벗이 없는 열)
        boolean[] isPivotCol = new boolean[cols];
        for (int i = 0; i < rows && i < cols; i++) {
            for (int j = 0; j < cols; j++) {
                if (Math.abs(matrix[i][j]) > EPSILON) {
                    isPivotCol[j] = true;
                    break;
                }
            }
        }

        // 첫 번째 자유 변수를 1로 설정
        for (int i = 0; i < cols; i++) {
            if (!isPivotCol[i]) {
                solution[i] = 1.0;
                break;
            }
        }

        for (int i = rows - 1; i >= 0; i--) {
            int pivotCol = -1;
            for (int j = 0; j < cols; j++) {
                if (Math.abs(matrix[i][j]) > EPSILON) {
                    pivotCol = j;
                    break;
                }
            }

            if (pivotCol != -1) {
                double sum = 0;
                for (int j = pivotCol + 1; j < cols; j++) {
                    sum += matrix[i][j] * solution[j];
                }
                solution[pivotCol] = -sum;
            }
        }
    }

    // ========== 행렬 유틸리티 메서드 ==========

    private static void swapRows(double[][] matrix, int row1, int row2) {
        double[] temp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = temp;
    }

    private static void scaleRow(double[][] matrix, int row, double scale) {
        for (int j = 0; j < matrix[row].length; j++) {
            matrix[row][j] *= scale;
        }
    }

    private static void addScaledRow(double[][] matrix, int targetRow, int sourceRow, double scale) {
        for (int j = 0; j < matrix[targetRow].length; j++) {
            matrix[targetRow][j] += scale * matrix[sourceRow][j];
        }
    }


    private static double[][] copyMatrix(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[][] copy = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            System.arraycopy(matrix[i], 0, copy[i], 0, cols);
        }

        return copy;
    }

    private static void printMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            System.out.print("[ ");
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%8.4f ", matrix[i][j]);
            }
            System.out.println("]");
        }
    }

    private static void printVector(double[] vector, String name) {
        System.out.print(name + " = [ ");
        for (double v : vector) {
            System.out.printf("%.4f ", v);
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        System.out.println("=== 선형 독립 알고리즘 데모 ===\n");

    }


}