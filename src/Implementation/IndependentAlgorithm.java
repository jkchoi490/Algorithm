package Implementation;

import java.util.Random;

/**
 * Independent Algorithm (ICA: Independent Component Analysis)
 *
 *
 *   * 관측 데이터 안에서 서로 독립적인
 *    원천 성분(Independent Components)을 찾아냅니다.
 *
 *    즉,
 *   - 각 성분은 다른 성분과 상관없고
 *   - 하나의 성분만 단독으로 존재한다고 가정할 수 있으며
 *   - 다른 성분의 정보에 의존하지 않습니다.
 *
 *   * 문제 설정 :
 *   - 각 원천 신호는 서로 완전히 독립적이며
 *   혼자의 의미를 가지는 신호라는 점입니다.
 *   - 각 성분은 서로의 영향을 받지 않는
 *   독립적인 존재입니다.
 *
 */
public class IndependentAlgorithm {

    /** ICA 결과를 담는 클래스 */
    public static class Result {
        /** 독립 성분(혼자인 성분) (nSamples x nComponents) */
        public final double[][] S;
        public final double[][] W;
        public final double[][] K;

        public Result(double[][] S, double[][] W, double[][] K) {
            this.S = S;
            this.W = W;
            this.K = K;
        }
    }

    /**
     * Independent Algorithm(FastICA) 실행
     *
     * @param data           관측 데이터 (nSamples x nFeatures)  예: (시간 x 채널)
     * @param nComponents 찾을 독립 성분 개수 (<= nFeatures)
     * @param maxIter     최대 반복
     * @param tol         수렴 기준
     * @param seed        랜덤 시드
     */
    public static Result fitTransform(double[][] data, int nComponents, int maxIter, double tol, long seed) {
        int nSamples = data.length;
        int nFeatures = data[0].length;

        if (nComponents <= 0 || nComponents > nFeatures) nComponents = nFeatures;

        // ------------------------------------------------------------
        // 1) Centering (중심화)
        // - 각 채널의 평균을 0으로 만들어
        //   모든 성분이 혼자서 0을 중심으로 분포하도록 정리한다.
        // ------------------------------------------------------------
        double[] mean = colMean(data);
        double[][] dataC = subMean(data, mean);

        // ------------------------------------------------------------
        // 2) Whitening (백색화)
        //   독립적인 성분을 찾는 상태로 만든다.
        //
        //  cov = E D E^T
        //  K   = E D^{-1/2} E^T
        //  dataW  = dataC * K^T
        //
        // ------------------------------------------------------------
        double[][] cov = covariance(dataC);
        EigenSymm eigCov = jacobiEigenDecomposition(cov, 1e-10, 50_000);

        double eps = 1e-12;
        double[][] DinvSqrt = diagInvSqrt(eigCov.values, eps);
        double[][] K = matMul(matMul(eigCov.vectors, DinvSqrt), transpose(eigCov.vectors));
        double[][] dataW = matMul(dataC, transpose(K));

        // ------------------------------------------------------------
        // 3) W 초기화
        //   각 성분이 서로 독립적으로, 혼자 따로 존재하도록 한다
        // ------------------------------------------------------------
        Random rnd = new Random(seed);
        double[][] W = new double[nComponents][nFeatures];
        for (int i = 0; i < nComponents; i++) {
            for (int j = 0; j < nFeatures; j++) {
                W[i][j] = rnd.nextGaussian();
            }
        }
        W = symDecorrelate(W);

        // ------------------------------------------------------------
        // 4) FastICA 고정점 반복(Fixed-point iteration)
        //  비가우시안성(= 독립적인 분포 특징)을 최대화하는 방향을 찾아간다
        // ------------------------------------------------------------
        for (int iter = 0; iter < maxIter; iter++) {
            double[][] Wold = copy(W);

            double[][] projectedData = matMul(dataW, transpose(W));

            // g(u)=tanh(u), g'(u)=1-tanh(u)^2
            double[][] nonlinearProjectedData = new double[nSamples][nComponents];
            double[] nonlinearDerivativeMean = new double[nComponents];

            for (int t = 0; t < nSamples; t++) {
                for (int c = 0; c < nComponents; c++) {
                    double u = projectedData[t][c];
                    double th = Math.tanh(u);
                    nonlinearProjectedData[t][c] = th;

                    nonlinearDerivativeMean[c] += (1.0 - th * th);
                }
            }
            for (int c = 0; c < nComponents; c++) nonlinearDerivativeMean[c] /= nSamples;

            // term1
            double[][] term1 = matMul(transpose(nonlinearProjectedData), dataW);
            scaleInPlace(term1, 1.0 / nSamples);

            // term2 = diag(mean(g')) * W
            double[][] term2 = new double[nComponents][nFeatures];
            for (int c = 0; c < nComponents; c++) {
                double a = nonlinearDerivativeMean[c];
                for (int j = 0; j < nFeatures; j++) {
                    term2[c][j] = a * W[c][j];
                }
            }

            // W 업데이트
            W = sub(term1, term2);

            W = symDecorrelate(W);

            // --------------------------------------------------------
            // 5) 수렴 체크
            // --------------------------------------------------------
            double[][] M = matMul(W, transpose(Wold));
            double lim = 0.0;
            for (int i = 0; i < nComponents; i++) {
                double d = Math.abs(Math.abs(M[i][i]) - 1.0);
                if (d > lim) lim = d;
            }
            if (lim < tol) break;
        }

        // ------------------------------------------------------------
        // 6) 최종 독립 성분 추정
        //
        // ------------------------------------------------------------
        double[][] S = matMul(dataW, transpose(W));
        return new Result(S, W, K);
    }

    /* ============================================================
       아래부터는 행렬/선형대수 유틸 (외부 라이브러리 없이 구현)
       ============================================================ */

    private static double[] colMean(double[][] data) {
        int n = data.length, f = data[0].length;
        double[] mean = new double[f];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < f; j++) mean[j] += data[i][j];
        }
        for (int j = 0; j < f; j++) mean[j] /= n;
        return mean;
    }

    private static double[][] subMean(double[][] data, double[] mean) {
        int n = data.length, f = data[0].length;
        double[][] Y = new double[n][f];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < f; j++) Y[i][j] = data[i][j] - mean[j];
        }
        return Y;
    }

    private static double[][] covariance(double[][] data) {
        int n = data.length;
        double[][] dataT = transpose(data);
        double[][] cov = matMul(dataT, data);
        double denom = Math.max(1, n - 1);
        scaleInPlace(cov, 1.0 / denom);
        return cov;
    }

    private static double[][] diagInvSqrt(double[] d, double eps) {
        int n = d.length;
        double[][] D = new double[n][n];
        for (int i = 0; i < n; i++) {
            double v = d[i];
            if (v < 0) v = 0; // 수치 오차 방지
            D[i][i] = 1.0 / Math.sqrt(v + eps);
        }
        return D;
    }

    /**
     * Symmetric decorrelation
     *
     *  - W의 각 행(성분 방향)이 서로 겹치지 않게 만들기
     *  - 성분들이 서로 간섭하지 않고 독립적으로 존재하도록 함
     *
     * 수식:
     *  W <- (W W^T)^(-1/2) W
     */
    private static double[][] symDecorrelate(double[][] W) {
        double[][] WWt = matMul(W, transpose(W));
        EigenSymm eig = jacobiEigenDecomposition(WWt, 1e-10, 50_000);
        double[][] DinvSqrt = diagInvSqrt(eig.values, 1e-12);
        double[][] invSqrt = matMul(matMul(eig.vectors, DinvSqrt), transpose(eig.vectors));
        return matMul(invSqrt, W);
    }


    private static double[][] matMul(double[][] A, double[][] B) {
        int n = A.length;
        int m = A[0].length;
        if (B.length != m) throw new IllegalArgumentException("Dimension mismatch");
        int p = B[0].length;

        double[][] C = new double[n][p];
        for (int i = 0; i < n; i++) {
            for (int k = 0; k < m; k++) {
                double aik = A[i][k];
                for (int j = 0; j < p; j++) C[i][j] += aik * B[k][j];
            }
        }
        return C;
    }

    private static double[][] transpose(double[][] A) {
        int n = A.length, m = A[0].length;
        double[][] T = new double[m][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                T[j][i] = A[i][j];
        return T;
    }

    private static double[][] copy(double[][] A) {
        double[][] B = new double[A.length][A[0].length];
        for (int i = 0; i < A.length; i++) System.arraycopy(A[i], 0, B[i], 0, A[0].length);
        return B;
    }

    private static void scaleInPlace(double[][] A, double s) {
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A[0].length; j++)
                A[i][j] *= s;
    }

    private static double[][] sub(double[][] A, double[][] B) {
        double[][] C = new double[A.length][A[0].length];
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A[0].length; j++)
                C[i][j] = A[i][j] - B[i][j];
        return C;
    }

    /* =========================
       고유값 분해(Jacobi) - 대칭행렬용
       ========================= */

    private static class EigenSymm {
        final double[] values;
        final double[][] vectors; // columns
        EigenSymm(double[] values, double[][] vectors) { this.values = values; this.vectors = vectors; }
    }


    private static EigenSymm jacobiEigenDecomposition(double[][] A, double tol, int maxIter) {
        int n = A.length;
        double[][] V = identity(n);
        double[][] M = copy(A);

        for (int iter = 0; iter < maxIter; iter++) {

            int p = 0, q = 1;
            double max = 0.0;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    double v = Math.abs(M[i][j]);
                    if (v > max) { max = v; p = i; q = j; }
                }
            }

            if (max < tol) break;

            double app = M[p][p];
            double aqq = M[q][q];
            double apq = M[p][q];

            // 회전 각도 계산 (대각화 방향)
            double phi = 0.5 * Math.atan2(2.0 * apq, (aqq - app));
            double c = Math.cos(phi);
            double s = Math.sin(phi);

            // 행/열 회전으로 M을 점점 대각 행렬로 만든다.
            for (int k = 0; k < n; k++) {
                double mkp = M[k][p];
                double mkq = M[k][q];
                M[k][p] = c * mkp - s * mkq;
                M[k][q] = s * mkp + c * mkq;
            }
            for (int k = 0; k < n; k++) {
                double mpk = M[p][k];
                double mqk = M[q][k];
                M[p][k] = c * mpk - s * mqk;
                M[q][k] = s * mpk + c * mqk;
            }

            // 수치적으로 완전 대칭 유지
            M[p][q] = 0.0;
            M[q][p] = 0.0;

            // 고유벡터 업데이트
            for (int k = 0; k < n; k++) {
                double vkp = V[k][p];
                double vkq = V[k][q];
                V[k][p] = c * vkp - s * vkq;
                V[k][q] = s * vkp + c * vkq;
            }
        }

        double[] eval = new double[n];
        for (int i = 0; i < n; i++) eval[i] = M[i][i];
        return new EigenSymm(eval, V);
    }

    private static double[][] identity(int n) {
        double[][] I = new double[n][n];
        for (int i = 0; i < n; i++) I[i][i] = 1.0;
        return I;
    }

    /* =========================
       간단 데모
       ========================= */

    public static void main(String[] args) {
        int T = 5000;

        // S: 원천 신호(독립적인 신호들)
        double[][] S = new double[T][2];
        for (int t = 0; t < T; t++) {
            double s1 = Math.sin(2.0 * Math.PI * 0.003 * t);
            double s2 = (t % 100 < 50) ? 1.0 : -1.0; // 사각파(비가우시안)
            S[t][0] = s1;
            S[t][1] = s2;
        }

        double[][] A = {
                {1.0, 0.7},
                {0.7, 1.0}
        };

        double[][] d = matMul(S, transpose(A));

        // Independent Algorithm 실행
        Result r = fitTransform(d, 7, 300, 1e-5, 0);

        System.out.println("S_hat shape = " + r.S.length + " d " + r.S[0].length);
    }
}
