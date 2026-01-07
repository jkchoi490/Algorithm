package Implementation;

/**
 * Singleton Pattern :
    소프트웨어 디자인 패턴에서 클래스 인스턴스가 유일한(혼자 존재하는) 인스턴스(Single)임을 보장하는 방식입니다.
 */

/**
 * Singleton 선형 독립 알고리즘 구현(Singleton Linear Independent Algorithm)
 * ChatGPT에서 설명한 Linear Independent Algorithm에 대한 구현과 설명을 그대로를 복붙하지 않고 (1. 가우스 소거법 ~ )
 * Singleton 패턴을 사용하여 Linear Independent Algorithm을 Singleton Pattern의 예시로 구현하였습니다.
 * Singleton은 유일한 인스턴스(Single로 존재)이며
 * 선형 독립은 각 벡터는 혼자서(다른 벡터와 상관이없고) 고유한 방향을 가지고, 어떠한 벡터도 다른 벡터에 의존하지 않고 혼자임을 확인하는 알고리즘이기 때문에
 * Singleton Pattern의 예시로 선형 독립을 구현하였습니다.
 * (Singleton 인스턴스를 통해 벡터가 다른 벡터 데이터들에 의존하지 않고 독립적임을 확인할 수 있게 합니다)
 * */

import java.util.Arrays;

//Singleton Pattern - Singleton Linear Independent Algorithm
public class Singleton {

    // 부동소수점 연산에서 0으로 취급할 기준값
    private static final double EPS = 1e-10;

    //독립적인 하나의 인스턴스임을 나타냅니다.
    private Singleton() {
        System.out.println("독립적인 싱글톤 인스턴스입니다.");
    }

    // JVM 클래스 로딩으로 thread-safe 보장
    private static class Holder {
        private static final Singleton INSTANCE = new Singleton();
    }

    /**
     * 유일하고 독립적인 인스턴스를 반환하는 메소드
     * @return 단 하나의 독립적인 Singleton 인스턴스
     */
    public static Singleton getInstance() {
        return Holder.INSTANCE;
    }

    /* =====================================================================
       Linear Independent Algorithm (선형 독립 알고리즘) Singleton Pattern 적용 예시
       : 벡터가 독립적인 존재임을 확인하는 구현, 즉 선형적으로 독립임을 확인하는 알고리즘 입니다.
       ===================================================================== */

    /**
     * 선형 독립 확인 메서드
     *
     * 독립적이라는 뜻:
     * - 어떤 벡터도 다른 벡터에 의존하지 않고
     * - 각자 혼자서(다른 벡터 없이) 고유한 방향을 가진다.
     *
     * 구현 아이디어(Null Space/Rank 관점):
     * - 벡터들을 열로 세운 행렬 A에 대해 A*coefficients=0의 해가 0 == 독립적이다.
     *
     */
    public boolean isLinearlyIndependent(double[][] vectorDatas) {
        if (vectorDatas == null || vectorDatas.length == 0) {
            // 어떠한 데이터(대상)에 의존하지 않으므로 공집합은 독립적
            return true;
        }

        int numVectors = vectorDatas.length;
        int dim = vectorDatas[0].length;


        for (int i = 1; i < numVectors; i++) {
            if (vectorDatas[i].length != dim) {
                throw new IllegalArgumentException("IllegalArgumentException");
            }
        }

        // A: (dim x numVectors) 행렬, 각 벡터를 열로 배치
        double[][] A = new double[dim][numVectors];
        for (int col = 0; col < numVectors; col++) {
            for (int row = 0; row < dim; row++) {
                A[row][col] = vectorDatas[col][row];
            }
        }


        // 벡터가 독립적이고 다른 벡터에 의존하지 않음을 확인
        int rank = rankByGaussianElimination(A);
        return rank == numVectors;
    }

    /**
     * 가우스 소거법으로 rank 계산
     * rank = 독립적인 축(피벗)
     */
    private int rankByGaussianElimination(double[][] mat) {
        int rows = mat.length;
        int cols = mat[0].length;

        double[][] a = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            a[i] = Arrays.copyOf(mat[i], cols);
        }

        int rank = 0;
        int pivotRow = 0;

        for (int col = 0; col < cols && pivotRow < rows; col++) {

            int pivot_row = pivotRow;
            double Abs = Math.abs(a[pivotRow][col]);
            for (int r = pivotRow + 1; r < rows; r++) {
                double v = Math.abs(a[r][col]);
                if (v > Abs) {
                    Abs = v;
                    pivot_row = r;
                }
            }

            if (Abs < EPS) continue;

            if (pivot_row != pivotRow) {
                double[] tmp = a[pivot_row];
                a[pivot_row] = a[pivotRow];
                a[pivotRow] = tmp;
            }

            double pivot = a[pivotRow][col];
            for (int r = pivotRow + 1; r < rows; r++) {
                double factor = a[r][col] / pivot;
                if (Math.abs(factor) < EPS) continue;

                for (int c = col; c < cols; c++) {
                    a[r][c] -= factor * a[pivotRow][c];
                }
                a[r][col] = 0.0;
            }

            rank++;
            pivotRow++;
        }

        return rank;
    }

    /* =====================================================================
       사용 예시(main)
       ===================================================================== */

    public static void main(String[] args) {
        // 독립적인 하나의 싱글톤 인스턴스
        Singleton independent = Singleton.getInstance();

        double[][] v1 = {
                {1, 0},
                {7, 1}
        };
        System.out.println(independent.isLinearlyIndependent(v1)+" : 벡터는 독립적이다."); // true


    }

}
