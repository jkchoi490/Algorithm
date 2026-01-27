package Implementation;

//Wikipedia - Fast Independent Component Analysis (FastICA)
import java.util.Arrays;
import java.util.Random;
/*

Fast Independent Component Analysis (FastICA)

Fast Independent Component Analysis란?
- 독립 성분 분석(ICA)을 빠르고 효율적인 방식으로 수행하는 알고리즘입니다.

Independent Component Analysis (독립 성분 분석)이란?
- 관측 신호들이 독립적인 성분임을 나타내는 알고리즘 입니다.
- 독립적인 성분이란 각 성분은 다른 성분들과 상관이없고 각각의 성분들은 독립적임을 뜻합니다.
- 독립 성분 분석을 통해 각각 독립적인 성분들을 나타냅니다.
- 여러 성분들을 원본 독립 성분들로 나타냅니다.
- 독립 성분들을 추출함으로써 각각의 성분들은 아무 관련이 없음을 알 수 있습니다.


* */
public class FastICA {

    // 데이터 전처리
    private double[][] centerData(double[][] data) {
        // 각 변수의 평균을 0으로 만들기
        int rows = data.length;
        int cols = data[0].length;
        double[][] centered = new double[rows][cols];

        for (int j = 0; j < cols; j++) {
            double mean = 0;
            for (int i = 0; i < rows; i++) {
                mean += data[i][j];
            }
            mean /= rows;

            for (int i = 0; i < rows; i++) {
                centered[i][j] = data[i][j] - mean;
            }
        }
        return centered;
    }

    // 화이트닝 (Whitening)
    private double[][] whitenData(double[][] data) {

        // 공분산 행렬의 고유값 분해를 통해
        // 데이터를 비상관화하고 단위 분산으로 만들기
        int m = data.length;        // 샘플 수
        int n = data[0].length;     // 특성 수

        // 공분산 행렬 계산
        double[][] covariance = computeCovarianceMatrix(data);

        EigenDecomposition eigen = eigenDecomposition(covariance);
        double[][] eigenVectors = eigen.vectors;
        double[] eigenValues = eigen.values;

        // 화이트닝 행렬 계산
        double[][] whiteningMatrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double sum = 0;
                for (int l = 0; l < n; l++) {
                    double invSqrtEigenValue = 1.0 / Math.sqrt(eigenValues[l] + 1e-10);
                    sum += eigenVectors[i][l] * invSqrtEigenValue * eigenVectors[j][l];
                }
                whiteningMatrix[i][j] = sum;
            }
        }

        // 화이트닝 적용
        double[][] whitened = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                double sum = 0;
                for (int l = 0; l < n; l++) {
                    sum += data[i][l] * whiteningMatrix[j][l];
                }
                whitened[i][j] = sum;
            }
        }

        return whitened;
    }

    //독립적인 고유값으로 나타냄을 위한 메서드
    private static class EigenDecomposition {
        double[][] vectors;  // 고유벡터
        double[] values;     // 고유값

        EigenDecomposition(double[][] vectors, double[] values) {
            this.vectors = vectors;
            this.values = values;
        }
    }

    private EigenDecomposition eigenDecomposition(double[][] matrix) {
        int n = matrix.length;

        double[][] A = new double[n][n];
        for (int i = 0; i < n; i++) {
            A[i] = Arrays.copyOf(matrix[i], n);
        }

        double[][] V = new double[n][n];
        for (int i = 0; i < n; i++) {
            V[i][i] = 1.0;
        }

        int maxIterations = 100;
        double tolerance = 1e-10;

        for (int iter = 0; iter < maxIterations; iter++) {

            double maxOffDiag = 0;
            int p = 0, q = 1;

            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (Math.abs(A[i][j]) > maxOffDiag) {
                        maxOffDiag = Math.abs(A[i][j]);
                        p = i;
                        q = j;
                    }
                }
            }

            if (maxOffDiag < tolerance) {
                break;
            }

            double theta = 0.5 * Math.atan2(2 * A[p][q], A[q][q] - A[p][p]);
            double c = Math.cos(theta);
            double s = Math.sin(theta);

            double[][] tempA = new double[n][n];
            for (int i = 0; i < n; i++) {
                tempA[i] = Arrays.copyOf(A[i], n);
            }

            for (int i = 0; i < n; i++) {
                tempA[p][i] = c * A[p][i] - s * A[q][i];
                tempA[q][i] = s * A[p][i] + c * A[q][i];
            }

            for (int i = 0; i < n; i++) {
                A[i][p] = c * tempA[i][p] - s * tempA[i][q];
                A[i][q] = s * tempA[i][p] + c * tempA[i][q];
            }

            // 고유벡터
            for (int i = 0; i < n; i++) {
                double temp = V[i][p];
                V[i][p] = c * temp - s * V[i][q];
                V[i][q] = s * temp + c * V[i][q];
            }
        }

        // 고유값 추출
        double[] eigenValues = new double[n];
        for (int i = 0; i < n; i++) {
            eigenValues[i] = A[i][i];
        }

        return new EigenDecomposition(V, eigenValues);
    }

    // 공분산 행렬 계산
    private double[][] computeCovarianceMatrix(double[][] data) {
        int m = data.length;
        int n = data[0].length;
        double[][] cov = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double sum = 0;
                for (int l = 0; l < m; l++) {
                    sum += data[l][i] * data[l][j];
                }
                cov[i][j] = sum / m;
            }
        }

        return cov;
    }


    // 비가우시안성 측정 함수
    private double g(double u) {
        // logcosh 함수 사용
        return Math.tanh(u);
    }

    private double gDerivative(double u) {
        double tanhU = Math.tanh(u);
        return 1 - tanhU * tanhU;
    }

    // FastICA 메인 알고리즘
    public double[][] fit(double[][] data, int numComponents) {
        // 센터링
        data = centerData(data);

        // 화이트닝
        data = whitenData(data);

        // 독립 성분 추출
        double[][] W = new double[numComponents][data[0].length];

        for (int p = 0; p < numComponents; p++) {
            // 가중치 벡터 초기화
            double[] w = initializeRandomVector(data[0].length);


            int maxIterations = 500;
            double tolerance = 1e-5;

            for (int iter = 0; iter < maxIterations; iter++) {
                double[] wData = w.clone();

                w = updateWeightVector(data, w);

                // 독립적인 성분들로 나타냅니다
                w = independentMethod(w, W, p);

                // 정규화
                w = normalize(w);

                if (checked(w, wData, tolerance)) {
                    break;
                }
            }

            W[p] = w;
        }

        return W;
    }

    private double[] updateWeightVector(double[][] data, double[] w) {
        int m = data.length;
        int n = data[0].length;
        double[] wData = new double[n];

        for (int i = 0; i < m; i++) {
            double wtData = dotProduct(w, data[i]);
            double gValue = g(wtData);

            for (int j = 0; j < n; j++) {
                wData[j] += data[i][j] * gValue;
            }
        }

        // 평균 계산
        for (int j = 0; j < n; j++) {
            wData[j] /= m;
        }

        double expectation = 0;
        for (int i = 0; i < m; i++) {
            double wtData = dotProduct(w, data[i]);
            expectation += gDerivative(wtData);
        }
        expectation /= m;

        for (int j = 0; j < n; j++) {
            wData[j] -= expectation * w[j];
        }

        return wData;
    }

    // 유틸리티 함수들
    private double[] initializeRandomVector(int size) {
        double[] v = new double[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            v[i] = rand.nextGaussian();
        }
        return normalize(v);
    }

    private double[] normalize(double[] v) {
        double norm = 0;
        for (double val : v) {
            norm += val * val;
        }
        norm = Math.sqrt(norm);

        double[] normalized = new double[v.length];
        for (int i = 0; i < v.length; i++) {
            normalized[i] = v[i] / norm;
        }
        return normalized;
    }

    private double dotProduct(double[] a, double[] b) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i] * b[i];
        }
        return sum;
    }

    private double[] independentMethod(double[] w, double[][] W, int p) {
        for (int i = 0; i < p; i++) {
            double projection = dotProduct(w, W[i]);
            for (int j = 0; j < w.length; j++) {
                w[j] -= projection * W[i][j];
            }
        }
        return w;
    }

    private boolean checked(double[] w, double[] W, double tolerance) {
        double diff = 0;
        for (int i = 0; i < w.length; i++) {
            diff += Math.abs(w[i] - W[i]);
        }
        return diff < tolerance;
    }
}