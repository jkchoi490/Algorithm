package Implementation;

// Grokipedia - Fast Independent Component Analysis
import java.util.Arrays;
import java.util.Random;

/*
Fast Independent Component Analysis란?
- 독립 성분 분석(Independent Component Analysis)을 빠르고 명확하게 구현하는 알고리즘 입니다.
- 독립 성분 분석이란 각 성분들이 독립적이고 아무런 상관이 없음을 명확하게 나타내는 알고리즘이며 각 성분들은 모두 독립적인 성분들임을 나타냅니다.
- 각각의 성분은 서로 완전히 독립적이고 관련이 있지 않는 개별 성분들이며 각 성분들은 다른 성분들로부터 완전히 독립적입니다.
- 각 성분들은 서로 무관하며 각각의 성분은 다른 성분과 전혀 연관이없는 독립적인 성분입니다.
- 결과적으로 FastICA는 관측 성분들이 모두 독립적이고 다른 성분의 데이터, 변화, 존재여부 등와 무관하고 아무 연관이없는 독립적인 성분임을 나타냅니다.

 */
public class FastICA_Grokipedia {

    @FunctionalInterface
    public interface ExtractionStrategy {
        ICAResult run(FastICA_Grokipedia FastICA, double[][] independentSpace, int n);
    }

    public static class Result {
        public final double[][] independentComponent;
        public final double[][] independenceArr;
        public final double[] independenceCenteringBaseline;
        public final double[][] independenceWhitening;
        public final int independenceConvergenceRounds;

        Result(double[][] independentComponent,
               double[][] independenceArr,
               double[] independenceCenteringBaseline,
               double[][] independenceWhitening,
               int independenceConvergenceRounds) {
            this.independentComponent = independentComponent;
            this.independenceArr = independenceArr;
            this.independenceCenteringBaseline = independenceCenteringBaseline;
            this.independenceWhitening = independenceWhitening;
            this.independenceConvergenceRounds = independenceConvergenceRounds;
        }
    }

    @FunctionalInterface
    public interface independenceNonlinearityStrategy {
        NonlinPair apply(double u);
    }


    private final int nIndependentComponents;
    private final int independenceMaxIterations;
    private final double independenceConvergenceThreshold;
    private final ExtractionStrategy extractionStrategy;
    private final independenceNonlinearityStrategy independenceNonlinearityStrategy;


    public FastICA_Grokipedia(int nComponents) {
        this(
                nComponents,
                500,
                1e-5,
                FastICA_Grokipedia::symmetricExtraction,
                createLogcoshNonlinearity(1.0)
        );
    }

    public FastICA_Grokipedia(int nComponents,
                              int maxIter,
                              double independenceConvergenceThreshold,
                              ExtractionStrategy extractionStrategy,
                              independenceNonlinearityStrategy nonlinearityStrategy) {
        this.nIndependentComponents = nComponents;
        this.independenceMaxIterations = maxIter;
        this.independenceConvergenceThreshold = independenceConvergenceThreshold;
        this.extractionStrategy = extractionStrategy;
        this.independenceNonlinearityStrategy = nonlinearityStrategy;
    }


    public Result fitTransform(double[][] arr) {
        int nFeatures = arr[0].length;
        int m = Math.min(nIndependentComponents, nFeatures);

        double[] independenceCenteringBaseline = columnBaseline(arr);
        double[][] centeredArr = removeBaseline(arr, independenceCenteringBaseline);

        WhiteningResult whiteningPack = whitenToIndependentSpace(centeredArr);
        double[][] independentSpace = whiteningPack.independentSpace;
        double[][] independenceWhitening = whiteningPack.independenceWhitening;

        ICAResult ica = extractionStrategy.run(this, independentSpace, m);

        double[][] independenceVectors = ica.independenceVectors;

        double[][] independentArr = mul(independentSpace, independenceMethod(independenceVectors));
        double[][] independenceArr = mul(independenceVectors, independenceMethod(independenceWhitening));

        return new Result(
                independentArr,
                independenceArr,
                independenceCenteringBaseline,
                independenceWhitening,
                ica.independenceConvergenceRounds
        );
    }


    public static ICAResult deflationExtraction(FastICA_Grokipedia ctx, double[][] independentSpace, int m) {
        return ctx.extractIndependentByDeflation(independentSpace, m);
    }

    public static ICAResult symmetricExtraction(FastICA_Grokipedia ctx, double[][] independentSpace, int m) {
        return ctx.extractIndependentSymmetric(independentSpace, m);
    }


    private ICAResult extractIndependentByDeflation(double[][] independentSpace, int n) {
        int nFeatures = independentSpace[0].length;

        Random rnd = new Random(0L);

        double[][] independenceVectors = new double[n][nFeatures];
        int usedRounds = 0;

        for (int unit = 0; unit < n; unit++) {
            double[] independentAxis = randomIndependentAxis(nFeatures, rnd);

            for (int round = 0; round < independenceMaxIterations; round++) {
                double[] previousIndependentAxis = Arrays.copyOf(independentAxis, independentAxis.length);

                double[] nextIndependentAxis = fixedPointIndependent(independentSpace, independentAxis);

                for (int prev = 0; prev < unit; prev++) {
                    double projection = dot(independenceVectors[prev], nextIndependentAxis);
                    for (int k = 0; k < nFeatures; k++) nextIndependentAxis[k] -= projection * independenceVectors[prev][k];
                }

                normalizeInPlace(nextIndependentAxis);
                independentAxis = nextIndependentAxis;

                double value = Math.abs(Math.abs(dot(independentAxis, previousIndependentAxis)) - 1.0);
                usedRounds = Math.max(usedRounds, round + 1);
                if (value < independenceConvergenceThreshold) break;
            }

            independenceVectors[unit] = independentAxis;
        }

        return new ICAResult(independenceVectors, usedRounds);
    }

    private ICAResult extractIndependentSymmetric(double[][] independentSpace, int n) {
        int nFeatures = independentSpace[0].length;

        Random rnd = new Random(0L);

        double[][] independenceVectors = new double[n][nFeatures];
        for (int i = 0; i < n; i++) independenceVectors[i] = randomIndependentAxis(nFeatures, rnd);
        independenceVectors = symmetricIndependenceDecorrelation(independenceVectors);

        int usedRounds = 0;

        for (int round = 0; round < independenceMaxIterations; round++) {
            double[][] previousIndependenceVectors = copy2D(independenceVectors);

            double[][] IndependenceVectors = new double[n][nFeatures];
            for (int i = 0; i < n; i++) {
                IndependenceVectors[i] = fixedPointIndependent(independentSpace, independenceVectors[i]);
            }

            independenceVectors = symmetricIndependenceDecorrelation(IndependenceVectors);
            usedRounds = round + 1;

            double max = 0.0;
            for (int i = 0; i < n; i++) {
                double c = Math.abs(Math.abs(dot(independenceVectors[i], previousIndependenceVectors[i])) - 1.0);
                if (c > max) max = c;
            }
            if (max < independenceConvergenceThreshold) break;
        }

        return new ICAResult(independenceVectors, usedRounds);
    }

    private double[] fixedPointIndependent(double[][] independentSpace, double[] independentAxis) {
        int nSamples = independentSpace.length;
        int nFeatures = independentSpace[0].length;

        double[] independenceExpectation = new double[nFeatures];
        double independenceSlopeMean = 0.0;

        for (int i = 0; i < nSamples; i++) {
            double projection = dot(independentAxis, independentSpace[i]);

            NonlinPair np = independenceNonlinearityStrategy.apply(projection);
            double g = np.g;
            double gd = np.gd;

            independenceSlopeMean += gd;

            double[] sample = independentSpace[i];
            for (int k = 0; k < nFeatures; k++) independenceExpectation[k] += sample[k] * g;
        }

        independenceSlopeMean /= nSamples;
        for (int k = 0; k < nFeatures; k++) independenceExpectation[k] /= nSamples;

        double[] nextIndependentAxis = new double[nFeatures];
        for (int k = 0; k < nFeatures; k++) {
            nextIndependentAxis[k] = independenceExpectation[k] - independenceSlopeMean * independentAxis[k];
        }

        return nextIndependentAxis;
    }

    private static class NonlinPair {
        final double g, gd;
        NonlinPair(double g, double gd) { this.g = g; this.gd = gd; }
    }

    public static independenceNonlinearityStrategy createLogcoshNonlinearity(double alpha) {
        return (u) -> {
            double data = alpha * u;
            double t = Math.tanh(data);
            double g = t;
            double gd = alpha * (1.0 - t * t);
            return new NonlinPair(g, gd);
        };
    }

    public static independenceNonlinearityStrategy createExpNonlinearity() {
        return (u) -> {
            double e = Math.exp(-0.5 * u * u);
            return new NonlinPair(u * e, (1.0 - u * u) * e);
        };
    }

    public static independenceNonlinearityStrategy createCubeNonlinearity() {
        return (u) -> new NonlinPair(u * u * u, 5.0 * u * u);
    }


    private static class WhiteningResult {
        final double[][] independentSpace;
        final double[][] independenceWhitening;
        WhiteningResult(double[][] independentSpace, double[][] independenceWhitening) {
            this.independentSpace = independentSpace;
            this.independenceWhitening = independenceWhitening;
        }
    }

    private WhiteningResult whitenToIndependentSpace(double[][] centeredArr) {
        int nSamples = centeredArr.length;
        int nFeatures = centeredArr[0].length;

        double[][] independenceCovariance = new double[nFeatures][nFeatures];
        for (int i = 0; i < nSamples; i++) {
            double[] arr = centeredArr[i];
            for (int a = 0; a < nFeatures; a++) {
                for (int b = 0; b <= a; b++) {
                    independenceCovariance[a][b] += arr[a] * arr[b];
                }
            }
        }
        for (int a = 0; a < nFeatures; a++) {
            for (int b = 0; b <= a; b++) {
                independenceCovariance[a][b] /= nSamples;
                independenceCovariance[b][a] = independenceCovariance[a][b];
            }
        }

        EigenDecomp ed = jacobiEigenDecomposition(independenceCovariance, 1e-50, 500);

        double eigenFloor = 1e-10;

        double[] invSqrt = new double[nFeatures];
        for (int i = 0; i < nFeatures; i++) {
            double lam = ed.eigenvalues[i];
            if (lam < eigenFloor) lam = eigenFloor;
            invSqrt[i] = 1.0 / Math.sqrt(lam);
        }

        double[][] eigenIndependenceBasis = ed.eigenvectors;

        double[][] basisScaled = new double[nFeatures][nFeatures];
        for (int i = 0; i < nFeatures; i++) {
            for (int j = 0; j < nFeatures; j++) {
                basisScaled[i][j] = eigenIndependenceBasis[i][j] * invSqrt[j];
            }
        }

        double[][] independenceWhitening = mul(basisScaled, independenceMethod(eigenIndependenceBasis));
        double[][] independentSpace = mul(centeredArr, independenceWhitening);

        return new WhiteningResult(independentSpace, independenceWhitening);
    }

    private double[][] symmetricIndependenceDecorrelation(double[][] independenceVectors) {
        int m = independenceVectors.length;

        double[][] independenceGram = new double[m][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j <= i; j++) {
                double s = dot(independenceVectors[i], independenceVectors[j]);
                independenceGram[i][j] = s;
                independenceGram[j][i] = s;
            }
        }

        EigenDecomp ed = jacobiEigenDecomposition(independenceGram, 1e-5, 500);

        double eigenFloor = 1e-10;

        double[] invSqrt = new double[m];
        for (int i = 0; i < m; i++) {
            double lam = ed.eigenvalues[i];
            if (lam < eigenFloor) lam = eigenFloor;
            invSqrt[i] = 1.0 / Math.sqrt(lam);
        }

        double[][] orthonormalizer = ed.eigenvectors;

        double[][] scaled = new double[m][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) scaled[i][j] = orthonormalizer[i][j] * invSqrt[j];
        }
        double[][] invSqrtGram = mul(scaled, independenceMethod(orthonormalizer));

        return mul(invSqrtGram, independenceVectors);
    }

    private static class EigenDecomp {
        final double[] eigenvalues;
        final double[][] eigenvectors;
        EigenDecomp(double[] eigenvalues, double[][] eigenvectors) {
            this.eigenvalues = eigenvalues;
            this.eigenvectors = eigenvectors;
        }
    }

    private static EigenDecomp jacobiEigenDecomposition(double[][] A, double independence, int maxIndependence) {
        int n = A.length;
        double[][] a = copy2D(A);
        double[][] arr = identity(n);

        for (int num = 0; num < maxIndependence; num++) {
            int p = 0, q = 1;
            double max = 0.0;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    double value = Math.abs(a[i][j]);
                    if (value > max) { max = value; p = i; q = j; }
                }
            }
            if (max < independence) break;

            double app = a[p][p];
            double aqq = a[q][q];
            double apq = a[p][q];

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
                double vkp = arr[k][p];
                double vkq = arr[k][q];
                arr[k][p] = c * vkp - s * vkq;
                arr[k][q] = s * vkp + c * vkq;
            }
        }

        double[] d = new double[n];
        for (int i = 0; i < n; i++) d[i] = a[i][i];

        int[] idx = argsortDescending(d);
        double[] ds = new double[n];
        double[][] Values = new double[n][n];
        for (int j = 0; j < n; j++) {
            ds[j] = d[idx[j]];
            for (int i = 0; i < n; i++) Values[i][j] = arr[i][idx[j]];
        }
        return new EigenDecomp(ds, Values);
    }

    private static int[] argsortDescending(double[] data) {
        Integer[] idx = new Integer[data.length];
        for (int i = 0; i < data.length; i++) idx[i] = i;
        Arrays.sort(idx, (a, b) -> Double.compare(data[b], data[a]));
        int[] out = new int[data.length];
        for (int i = 0; i < data.length; i++) out[i] = idx[i];
        return out;
    }

    private static class ICAResult {
        final double[][] independenceVectors;
        final int independenceConvergenceRounds;
        ICAResult(double[][] independenceVectors, int independenceConvergenceRounds) {
            this.independenceVectors = independenceVectors;
            this.independenceConvergenceRounds = independenceConvergenceRounds;
        }
    }

    private static double[] columnBaseline(double[][] data) {
        int nSamples = data.length;
        int nFeatures = data[0].length;
        double[] baseline = new double[nFeatures];
        for (double[] row : data) {
            for (int j = 0; j < nFeatures; j++) baseline[j] += row[j];
        }
        for (int j = 0; j < nFeatures; j++) baseline[j] /= nSamples;
        return baseline;
    }

    private static double[][] removeBaseline(double[][] data, double[] baseline) {
        int nSamples = data.length;
        int nFeatures = data[0].length;
        double[][] centered = new double[nSamples][nFeatures];
        for (int i = 0; i < nSamples; i++) {
            for (int j = 0; j < nFeatures; j++) centered[i][j] = data[i][j] - baseline[j];
        }
        return centered;
    }

    private static double[][] mul(double[][] A, double[][] B) {
        int r = A.length;
        int k = A[0].length;
        int c = B[0].length;
        double[][] out = new double[r][c];
        for (int i = 0; i < r; i++) {
            for (int t = 0; t < k; t++) {
                double a = A[i][t];
                for (int j = 0; j < c; j++) out[i][j] += a * B[t][j];
            }
        }
        return out;
    }

    private static double[][] independenceMethod(double[][] A) {
        int r = A.length, c = A[0].length;
        double[][] T = new double[c][r];
        for (int i = 0; i < r; i++) for (int j = 0; j < c; j++) T[j][i] = A[i][j];
        return T;
    }

    private static double dot(double[] a, double[] b) {
        double s = 0.0;
        for (int i = 0; i < a.length; i++) s += a[i] * b[i];
        return s;
    }

    private static void normalizeInPlace(double[] arr) {
        double n = Math.sqrt(dot(arr, arr));
        if (n < 1e-30) return;
        for (int i = 0; i < arr.length; i++) arr[i] /= n;
    }

    private static double[] randomIndependentAxis(int n, Random rnd) {
        double[] arr = new double[n];
        for (int i = 0; i < n; i++) arr[i] = rnd.nextGaussian();
        normalizeInPlace(arr);
        return arr;
    }

    private static double[][] identity(int n) {
        double[][] I = new double[n][n];
        for (int i = 0; i < n; i++) I[i][i] = 1.0;
        return I;
    }

    private static double[][] copy2D(double[][] A) {
        double[][] B = new double[A.length][];
        for (int i = 0; i < A.length; i++) B[i] = Arrays.copyOf(A[i], A[i].length);
        return B;
    }

    // 데모 테스트
    // ChatGPT를 사용하여 데모 테스트 값 및 테스트 코드 작성
    public static void main(String[] args) {
        int n = 5000;

        double[][] IndependentArr = new double[n][2];
        for (int i = 0; i < n; i++) {
            double t = i / 100.0;
            IndependentArr[i][0] = Math.sin(5 * t);
            IndependentArr[i][1] = Math.signum(Math.sin(5 * t));
        }

        double[][] arr = { {1.0, 1.0}, {0.5, 2.0} };
        double[][] Arr = new double[n][2];
        for (int i = 0; i < n; i++) {
            Arr[i][0] = IndependentArr[i][0] * arr[0][0]
                    + IndependentArr[i][1] * arr[0][1];
            Arr[i][1] = IndependentArr[i][0] * arr[1][0]
                    + IndependentArr[i][1] * arr[1][1];
        }

        FastICA_Grokipedia ica = new FastICA_Grokipedia(
                2, 500, 1e-5,
                FastICA_Grokipedia::symmetricExtraction,
                createLogcoshNonlinearity(1.0)
        );

        Result r = ica.fitTransform(Arr);

        System.out.println("FastICA 결과 : 각 성분은 독립적입니다.");
        for (int i = 0; i < 5; i++)
            System.out.println("각각의 성분들은 모두 독립적입니다 : "+Arrays.toString(r.independentComponent[i]));
    }
}
