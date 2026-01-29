package Implementation;

//GeeksforGeeks - Fast Independent Component Analysis
import java.util.Random;
import java.util.Arrays;
/*

Fast Independent Component Analysis란?
- 독립 성분 분석(Independent Component Analysis)을 더 빠르게 수행하는 알고리즘 입니다.
- 독립 성분 분석이란 각 성분들이 독립적이고 다른 성분들과 상관이 없음을 나타내는 알고리즘입니다.
- 여러 성분들을 원본 독립 성분들로 나타냅니다.
- 각 성분은 독립적이고 다른 성분과 관련이 없는 각각의 독립적인 정보들을 나타냅니다.
- 평균 제거를 통해 각각의 성분이 독립적임을 빠르고 정확하게 확인하며
각 성분은 다른 성분의 존재 여부와 무관하게 정의됩니다.
- 각각의 성분들이 독립적임을 더 효율적으로 확인할 수 있습니다.
- 독립 성분들을 추출함으로써 각각의 성분들은 아무 관련이 없음을 알 수 있습니다.
- 각 성분은 다른 성분의 값, 분포, 변화 등에 어떠한 영향도 받지 않습니다.
- Fast Independent Component Analysis에서 각 성분을 단순히 상관이 없는 수준이 아니라, 다른 성분의 존재 여부와 무관하게 독립적으로 정의되는 정보 단위임을 나타냅니다.
- 결과적으로 각 성분은 서로 완전히 분리된 독립 정보 단위로 존재합니다.

 */
public class FastIndependentComponentAnalysis {

    public static class Result {
        // 독립 성분
        public final double[][] independentComponents;

        // 평균이 제거된 관측치에서 독립 성분을 나타내는 분리 행렬
        public final double[][] independentArr;

        // 평균 제거에 사용되는 성분
        public final double[] mean;

        // 독립성임을 나타내기 위한 행렬(whitening 역할)
        public final double[][] independenceProjector;

        // 독립성 확인을 위한 횟수
        public final int iterations;

        Result(double[][] independentComponents,
               double[][] independentArr,
               double[] mean,
               double[][] independenceProjector,
               int iterations) {
            this.independentComponents = independentComponents;
            this.independentArr = independentArr;
            this.mean = mean;
            this.independenceProjector = independenceProjector;
            this.iterations = iterations;
        }
    }

    private final int nComponents;
    private final int maxIter;
    private final double tol;
    private final long seed;
    private final double independenceEps;

    public FastIndependentComponentAnalysis(int nComponents, int maxIter, double tol, long seed, double independenceEps) {
        this.nComponents = nComponents;
        this.maxIter = maxIter;
        this.tol = tol;
        this.seed = seed;
        this.independenceEps = independenceEps;
    }

    // 독립 성분 추출
    public Result extractIndependentComponents(double[][] data) {
        if (data == null || data.length == 0 || data[0].length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        int nSamples = data.length;
        int nComponent = data[0].length;

        if (nComponents <= 0 || nComponents > nComponent) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        // 평균 제거 (센터링)
        double[] removedMean = meanRemoval(data);
        double[][] meanRemoved = removeMean(data, removedMean);

        // 독립성 확보를 위한 투영
        double[][] matrix = Matrix.matrixMethod(meanRemoved);
        double[][] cov = Matrix.mul(matrix, meanRemoved);
        Matrix.scaleInPlace(cov, 1.0 / nSamples);

        JacobiEigen.Decomp eig = JacobiEigen.decomposeSymmetric(cov, independenceEps, 500);

        double[][] invSqrtEig = Matrix.diagInvSqrt(eig.values, independenceEps);

        double[][] independenceProjector = Matrix.mul(eig.vectors, invSqrtEig);

        double[][] independentSpace = Matrix.mul(meanRemoved, independenceProjector);

        Random rnd = new Random(seed);
        double[][] independentBasis = independentBasis(rnd, nComponents, nComponent);

        double[][] independentSpaceT = Matrix.matrixMethod(independentSpace);
        int iters = 0;

        for (int iter = 0; iter < maxIter; iter++) {
            iters = iter + 1;

            double[][] independentComponents = Matrix.copy(independentBasis);

            double[][] independentProj = Matrix.mul(independentBasis, independentSpaceT);

            double[][] arr = Matrix.copy(independentProj);
            double[] mean = new double[nComponents];

            for (int i = 0; i < nComponents; i++) {
                double value = 0.0;
                for (int j = 0; j < nSamples; j++) {
                    double u = arr[i][j];
                    double t = Math.tanh(u);
                    arr[i][j] = t;
                    double gp = 1.0 - t * t;
                    value += gp;
                }
                mean[i] = value / nSamples;
            }

            double[][] independentSpaceArr = Matrix.mul(arr, independentSpace);
            Matrix.scaleInPlace(independentSpaceArr, 1.0 / nSamples);

            double[][] independentBasisArr = Matrix.copy(independentBasis);
            for (int i = 0; i < nComponents; i++) {
                for (int k = 0; k < nComponent; k++) {
                    independentBasisArr[i][k] *= mean[i];
                }
            }

            double[][] result = Matrix.sub(independentSpaceArr, independentBasisArr);

            // 독립성임을 확인
            independentBasis = independenceMethod(result, independenceEps);

            double maxDelta = 0.0;
            for (int i = 0; i < nComponents; i++) {
                double dot = Matrix.dot(independentBasis[i], independentComponents[i]);
                double delta = 1.0 - Math.abs(dot);
                if (delta > maxDelta) maxDelta = delta;
            }
            if (maxDelta < tol) break;
        }

        double[][] independentComponents = Matrix.mul(independentSpace, Matrix.matrixMethod(independentBasis));

        double[][] invSqrtEt = Matrix.mul(invSqrtEig, Matrix.matrixMethod(eig.vectors));
        double[][] independent = Matrix.mul(independentBasis, invSqrtEt);

        return new Result(independentComponents, independent, removedMean, independenceProjector, iters);
    }

    // 평균 제거 (mean removal)
    private static double[] meanRemoval(double[][] data) {
        int n = data.length, p = data[0].length;
        double[] mean = new double[p];
        for (int i = 0; i < n; i++) {
            if (data[i].length != p) throw new IllegalArgumentException("IllegalArgumentException");
            for (int j = 0; j < p; j++) mean[j] += data[i][j];
        }
        for (int j = 0; j < p; j++) mean[j] /= n;
        return mean;
    }

    private static double[][] removeMean(double[][] data, double[] mean) {
        int n = data.length, p = data[0].length;
        double[][] out = new double[n][p];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < p; j++) out[i][j] = data[i][j] - mean[j];
        }
        return out;
    }

    // Independent Basis Method
    private static double[][] independentBasis(Random rnd, int rows, int cols) {
        double[][] W = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) W[i][j] = rnd.nextGaussian();
        }

        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < i; k++) {
                double proj = Matrix.dot(W[i], W[k]);
                for (int l = 0; l < cols; l++) W[i][l] -= proj * W[k][l];
            }
            Matrix.normalizeInPlace(W[i], 1e-5);
        }
        return W;
    }

    // Independent Method
    private static double[][] independenceMethod(double[][] W, double eps) {
        int c = W.length;
        double[][] WWt = Matrix.mul(W, Matrix.matrixMethod(W));
        JacobiEigen.Decomp eig = JacobiEigen.decomposeSymmetric(WWt, eps, 500);

        double[][] invSqrt = Matrix.diagInvSqrt(eig.values, eps);

        double[][] E = eig.vectors;
        double[][] temp = Matrix.mul(E, invSqrt);
        double[][] invSqrtWWt = Matrix.mul(temp, Matrix.matrixMethod(E));

        return Matrix.mul(invSqrtWWt, W);
    }


    static class Matrix {
        static double[][] copy(double[][] A) {
            double[][] B = new double[A.length][];
            for (int i = 0; i < A.length; i++) B[i] = Arrays.copyOf(A[i], A[i].length);
            return B;
        }

        static double[][] matrixMethod(double[][] A) {
            int n = A.length, m = A[0].length;
            double[][] T = new double[m][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) T[j][i] = A[i][j];
            }
            return T;
        }

        static double[][] mul(double[][] A, double[][] B) {
            int n = A.length, num = A[0].length;
            int number = B.length, m = B[0].length;
            if (num != number) throw new IllegalArgumentException("IllegalArgumentException");
            double[][] C = new double[n][m];
            for (int i = 0; i < n; i++) {
                for (int t = 0; t < num; t++) {
                    double a = A[i][t];
                    for (int c = 0; c < m; c++) {
                        C[i][c] += a * B[t][c];
                    }
                }
            }
            return C;
        }

        static double[][] sub(double[][] A, double[][] B) {
            int n = A.length, m = A[0].length;
            double[][] C = new double[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) C[i][j] = A[i][j] - B[i][j];
            }
            return C;
        }

        static void scaleInPlace(double[][] A, double s) {
            for (int i = 0; i < A.length; i++) {
                for (int j = 0; j < A[i].length; j++) A[i][j] *= s;
            }
        }

        static double dot(double[] a, double[] b) {
            double s = 0.0;
            for (int i = 0; i < a.length; i++) s += a[i] * b[i];
            return s;
        }

        static void normalizeInPlace(double[] v, double eps) {
            double n = dot(v, v);
            double norm = Math.sqrt(Math.max(n, eps));
            for (int i = 0; i < v.length; i++) v[i] /= norm;
        }

        static double[][] diagInvSqrt(double[] d, double eps) {
            int n = d.length;
            double[][] D = new double[n][n];
            for (int i = 0; i < n; i++) {
                double val = d[i];
                if (val < eps) val = eps;
                D[i][i] = 1.0 / Math.sqrt(val);
            }
            return D;
        }
    }

    static class JacobiEigen {
        static class Decomp {
            final double[] values;
            final double[][] vectors;
            Decomp(double[] values, double[][] vectors) {
                this.values = values;
                this.vectors = vectors;
            }
        }

        static Decomp decomposeSymmetric(double[][] A, double tol, int num) {
            int n = A.length;
            for (int i = 0; i < n; i++) {
                if (A[i].length != n) throw new IllegalArgumentException("IllegalArgumentException");
            }

            double[][] a = Matrix.copy(A);
            double[][] values = new double[n][n];
            for (int i = 0; i < n; i++) values[i][i] = 1.0;

            for (int m = 0; m < num; m++) {
                int p = 0, q = 1;
                double max = 0.0;
                for (int i = 0; i < n; i++) {
                    for (int j = i + 1; j < n; j++) {
                        double val = Math.abs(a[i][j]);
                        if (val > max) { max = val; p = i; q = j; }
                    }
                }
                if (max < tol) break;

                double app = a[p][p], aqq = a[q][q], apq = a[p][q];
                double phi = 0.5 * Math.atan2(2.0 * apq, (aqq - app));
                double c = Math.cos(phi);
                double s = Math.sin(phi);

                for (int k = 0; k < n; k++) {
                    double aik = a[p][k];
                    double aqk = a[q][k];
                    a[p][k] = c * aik - s * aqk;
                    a[q][k] = s * aik + c * aqk;
                }
                for (int k = 0; k < n; k++) {
                    double akp = a[k][p];
                    double akq = a[k][q];
                    a[k][p] = c * akp - s * akq;
                    a[k][q] = s * akp + c * akq;
                }

                a[p][q] = 0.0;
                a[q][p] = 0.0;

                for (int k = 0; k < n; k++) {
                    double vkp = values[k][p];
                    double vkq = values[k][q];
                    values[k][p] = c * vkp - s * vkq;
                    values[k][q] = s * vkp + c * vkq;
                }
            }

            double[] vals = new double[n];
            for (int i = 0; i < n; i++) vals[i] = a[i][i];

            int[] idx = argsortDesc(vals);
            double[] valsArr = new double[n];
            double[][] valArr = new double[n][n];
            for (int col = 0; col < n; col++) {
                int j = idx[col];
                valsArr[col] = vals[j];
                for (int row = 0; row < n; row++) valArr[row][col] = values[row][j];
            }
            return new Decomp(valsArr, valArr);
        }

        private static int[] argsortDesc(double[] a) {
            Integer[] idx = new Integer[a.length];
            for (int i = 0; i < a.length; i++) idx[i] = i;
            Arrays.sort(idx, (i, j) -> Double.compare(a[j], a[i]));
            int[] out = new int[a.length];
            for (int i = 0; i < a.length; i++) out[i] = idx[i];
            return out;
        }
    }

    // ---------------- 데모 테스트 ----------------
    public static void main(String[] args) {

        //ChatGPT를 참고하여 데모 테스트 값 설정
        int nSamples = 5000;
        double[][] IndependentArr = new double[nSamples][3];

        Random rnd = new Random(0);
        for (int i = 0; i < nSamples; i++) {
            double t = 5.0 * i / (nSamples - 1.0);
            double s1 = Math.sin(2 * t);
            double s2 = Math.signum(Math.sin(3 * t));
            double s3 = rnd.nextGaussian();
            IndependentArr[i][0] = s1;
            IndependentArr[i][1] = s2;
            IndependentArr[i][2] = s3;
        }

        double[][] matrix = {
                {1, 1, 1},
                {0.5, 5, 1.0},
                {0.5, 1.0, 5.0}
        };

        double[][] arr = Matrix.mul(IndependentArr, Matrix.matrixMethod(matrix));

        FastIndependentComponentAnalysis ica = new FastIndependentComponentAnalysis(5, 500, 1e-5, 50, 1e-5);
        Result r = ica.extractIndependentComponents(arr);

        System.out.println("Fast ICA 결과: 결과값 Result의 " + r.independentComponents.length + "과" + r.independentComponents[0].length
                        +"을 통해 각 성분은 독립적이고 아무 상관이 없는 성분들임을 알 수 있습니다.");

    }
}
