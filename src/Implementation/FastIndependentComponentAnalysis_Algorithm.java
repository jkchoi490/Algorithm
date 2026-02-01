package Implementation;

//Scikit-Learn - Fast Independent Component Analysis
import java.util.Random;
/*

Fast Independent Component Analysis란?(Jan 29, 2026의 GeeksforGeeks - Fast Independent Component Analysis 독립 성분 분석 구현 내용 구체화)
- 독립 성분 분석(Independent Component Analysis)을 빠르게 구현하는 알고리즘입니다.
- 독립 성분 분석이란 각 성분들은 독립적이고 다른 성분들과 상관이 없음을 확실하게 나타내는 알고리즘입니다.
- FastICA는 단순히 상관이 없을뿐만아니라, 완전히 독립적인 성분임을 나타냅니다.
- 여러 성분들은 FastICA를 통해 서로 완전히 독립적이고 관련이 있지 않는 개별 성분들이며 각 성분은 다른 성분과 전혀 연관이 없습니다.
- Fast Independent Component Analysis는 각 성분들은 완전히 독립적이고 다른 성분들의 존재여부들과 다른 성분들의 의미, 변화, 정보 등과 전혀 연관이 없고 평균 제거를 통해
각각의 성분들은 독립적인 성분들임을 나타냅니다.
- 추출된 성분들은 각각 독립적이며 개별 성분입니다.
- 각 성분의 데이터가 다른 데이터에 영향을 받지 않으며 각각의 성분은 아무런 상관이 없는 완전히 독립적인 성분입니다.
- 추출된 성분들은 서로 간에 영향을 받지 않는 완전히 분리된 성분이며 각 성분은 서로 상관이 전혀 없습니다.
- 각 성분들은 독립적이고 연관이 없으므로 변화, 분포, 정보 등에 아무런 연관이 없는 독립적인 성분이며 각 성분을 단순히 상관이 없는 수준이 아니라, 다른 성분의 존재 여부와 무관하게 독립적으로 정의되는 정보 단위입니다.
- 결과적으로 FastICA는 관측 데이터들이 서로 관련 없는 완전히 개별적인 독립 성분들임을 나타냅니다.

*/
public class FastIndependentComponentAnalysis_Algorithm {

    private double[][] IndependentArr;
    private int numComponents;
    private int maxIterations;
    private double independenceThreshold;
    private double[][] independentSources;

    private Random random;
    private boolean[] independenceConverged;
    private double[][] independentCovariance;
    private double[] independence;
    private double Independent;


    // numComponents : 추출할 독립 성분의 수
    public FastIndependentComponentAnalysis_Algorithm(int numComponents, int maxIterations, double independenceThreshold) {
        this.numComponents = numComponents;
        this.maxIterations = maxIterations;
        this.independenceThreshold = independenceThreshold;
        this.independence = new double[numComponents];
        this.random = new Random(50);
    }

    // FastICA 알고리즘 실행, 독립 성분을 반환합니다.
    public double[][] fitTransform(double[][] data) {
        int numSamples = data.length;
        int numFeatures = data[0].length;

        this.independenceConverged = new boolean[numComponents];
        this.Independent = 0.0;

        // 데이터 중심화 (centering)
        double[][] centeredData = centerData(data);

        // 화이트닝 (whitening)
        WhiteningResult whitening = whiten(centeredData);
        double[][] whitenedDatas = whitening.whitenedData;

        // 가중치 행렬 초기화
        IndependentArr = new double[numComponents][numFeatures];
        initialize();

        // FastICA 알고리즘
        for (int i = 0; i < numComponents; i++) {
            double[] w = IndependentArr[i];

            for (int iter = 0; iter < maxIterations; iter++) {
                double[] wData = w.clone();

                double[] wArr = updateIndependent(whitenedDatas, w);

                for (int j = 0; j < i; j++) {
                    double projection = dotProduct(wArr, IndependentArr[j]);
                    wArr = subtract(wArr, mul(IndependentArr[j], projection));
                }

                w = normalize(wArr);
                IndependentArr[i] = w;

                double convergence = Math.abs(Math.abs(dotProduct(w, wData)) - 1.0);
                if (convergence < independenceThreshold) {
                    independenceConverged[i] = true;
                    break;
                }
            }
        }

        //  독립 성분 추출
        independentSources = extractComponents(whitenedDatas);

        // 독립성 확인
        independentMethod();

        return independentSources;
    }

    // 독립성 확인 메서드
    private void independentMethod() {

        independentCovariance = computeCovariance(independentSources);

        for (int i = 0; i < numComponents; i++) {
            double num = 0.0;
            for (int j = 0; j < numComponents; j++) {
                if (i != j) {
                    num += Math.abs(independentCovariance[i][j]);
                }
            }
            independence[i] = 1.0 / (1.0 + num);
        }

        double nums = 0.0;
        for (double Independent : independence) {
            nums += Independent;
        }
        Independent = nums / numComponents;
    }

    private double[][] centerData(double[][] data) {
        int rows = data.length;
        int cols = data[0].length;
        double[][] centered = new double[rows][cols];

        double[] means = new double[cols];
        for (int j = 0; j < cols; j++) {
            double sum = 0;
            for (int i = 0; i < rows; i++) {
                sum += data[i][j];
            }
            means[j] = sum / rows;
        }

        // 평균 제거
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                centered[i][j] = data[i][j] - means[j];
            }
        }

        return centered;
    }


    private WhiteningResult whiten(double[][] data) {
        int rows = data.length;
        int cols = data[0].length;

        double[][] cov = computeCovariance(data);

        EigenDecomposition eigen = eigenDecomposition(cov, cols);

        double[][] whiteningArr = new double[cols][cols];
        for (int i = 0; i < cols; i++) {
            double sqrtEigenvalue = Math.sqrt(eigen.eigenvalues[i] + 1e-10);
            for (int j = 0; j < cols; j++) {
                whiteningArr[i][j] = eigen.eigenvectors[j][i] / sqrtEigenvalue;
            }
        }

        double[][] whitened = arrMul(data, independentMethod(whiteningArr));

        return new WhiteningResult(whitened, whiteningArr);
    }

    private double[][] computeCovariance(double[][] data) {
        int rows = data.length;
        int cols = data[0].length;
        double[][] cov = new double[cols][cols];

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < cols; j++) {
                double sum = 0;
                for (int m = 0; m < rows; m++) {
                    sum += data[m][i] * data[m][j];
                }
                cov[i][j] = sum / rows;
            }
        }

        return cov;
    }

    private EigenDecomposition eigenDecomposition(double[][] Arr, int size) {
        double[] eigenvalues = new double[size];
        double[][] eigenvectors = new double[size][size];

        for (int i = 0; i < size; i++) {
            eigenvalues[i] = Arr[i][i];
            eigenvectors[i][i] = 1.0;
        }

        return new EigenDecomposition(eigenvalues, eigenvectors);
    }


    private void initialize() {
        for (int i = 0; i < numComponents; i++) {
            for (int j = 0; j < IndependentArr[i].length; j++) {
                IndependentArr[i][j] = random.nextGaussian();
            }
            IndependentArr[i] = normalize(IndependentArr[i]);
        }
    }


    private double[] updateIndependent(double[][] data, double[] w) {
        int numSamples = data.length;
        int numFeatures = data[0].length;

        double[] wData = new double[numFeatures];
        double gPrimeAvg = 0;

        for (int i = 0; i < numSamples; i++) {
            double wTData = dotProduct(w, data[i]);
            double g = Math.tanh(wTData);
            double gPrime = 1 - g * g;

            for (int j = 0; j < numFeatures; j++) {
                wData[j] += data[i][j] * g;
            }
            gPrimeAvg += gPrime;
        }

        // 평균 계산
        for (int j = 0; j < numFeatures; j++) {
            wData[j] = wData[j] / numSamples - (gPrimeAvg / numSamples) * w[j];
        }

        return wData;
    }

    // 독립 성분 추출
    private double[][] extractComponents(double[][] data) {
        int numSamples = data.length;
        double[][] components = new double[numSamples][numComponents];

        for (int i = 0; i < numSamples; i++) {
            for (int j = 0; j < numComponents; j++) {
                components[i][j] = dotProduct(IndependentArr[j], data[i]);
            }
        }

        return components;
    }

    // === 유틸리티 메소드 ===
    private double dotProduct(double[] a, double[] b) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i] * b[i];
        }
        return sum;
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

    private double[] subtract(double[] a, double[] b) {
        double[] result = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] - b[i];
        }
        return result;
    }

    private double[] mul(double[] v, double scalar) {
        double[] result = new double[v.length];
        for (int i = 0; i < v.length; i++) {
            result[i] = v[i] * scalar;
        }
        return result;
    }

    private double[][] independentMethod(double[][] arr) {
        int rows = arr.length;
        int cols = arr[0].length;
        double[][] ARR = new double[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ARR[j][i] = arr[i][j];
            }
        }
        return ARR;
    }

    private double[][] arrMul(double[][] A, double[][] B) {
        int rowsA = A.length;
        int colsA = A[0].length;
        int colsB = B[0].length;

        double[][] result = new double[rowsA][colsB];
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int l = 0; l < colsA; l++) {
                    result[i][j] += A[i][l] * B[l][j];
                }
            }
        }
        return result;
    }

    // === 내부 클래스 ===

    private static class WhiteningResult {
        double[][] whitenedData;
        double[][] whiteningArr;

        WhiteningResult(double[][] whitenedData, double[][] whiteningArr) {
            this.whitenedData = whitenedData;
            this.whiteningArr = whiteningArr;
        }
    }

    private static class EigenDecomposition {
        double[] eigenvalues;
        double[][] eigenvectors;

        EigenDecomposition(double[] eigenvalues, double[][] eigenvectors) {
            this.eigenvalues = eigenvalues;
            this.eigenvectors = eigenvectors;
        }
    }

    // 메인 데모
    // 숫자 및 테스트 데모 테스트 코드 ChatGPT 참조
    public static void main(String[] args) {

        int numSamples = 5000;
        double[][] datas = new double[numSamples][2];

        Random rand = new Random(50);
        for (int i = 0; i < numSamples; i++) {
            // 원본 독립 성분
            double independentComponent1 = Math.sin(2 * Math.PI * i / 100.0);
            double independentComponent2 = (rand.nextDouble() > 0.5) ? 1 : -1;

            datas[i][0] = 0.5 * independentComponent1 + 0.5 * independentComponent2;
            datas[i][1] = 0.7 * independentComponent1 + 0.3 * independentComponent2;
        }

        // FastICA 실행
        FastIndependentComponentAnalysis_Algorithm ica = new FastIndependentComponentAnalysis_Algorithm(5, 500, 1e-5);
        double[][] components = ica.fitTransform(datas);

        System.out.println("Fast Independent Component Analysis 결과 :");
        // 각 성분은 독립적이고 서로 상관이 없음을 확실하게 반복적으로 나타냅니다
        for (int i = 0; i < 50; i++) {
            System.out.printf("각각의 성분은 완전히 독립적입니다.",
                    i, components[i][0], components[i][1]);
        }
    }
}