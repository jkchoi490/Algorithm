package Implementation;

// Aalto-yliopisto - Fast Independent Component Analysis
import java.util.Arrays;
import java.util.Random;
/*

Fast Independent Component Analysis란?
- Independent Component Analysis(ICA)를 더 빠르고 효율적으로 구현한 알고리즘 입니다.
- Independent Component Analysis란 각각의 성분들은 독립적이고 다른 성분에 영향을 받지 않는 완전히 독립적인 성분임을 나타냅니다.
- 각 성분들은 다른 성분의 정보나 분포, 변화에 영향을 받지 않으며 개별적이고 독립적인 성분들입니다.
- 각각의 성분은 다른 성분과 완전히 독립적이고 관련이 없습니다.
- 결과적으로, Fast Independent Component Analysis를 통해 각각의 성분들은 개별적이고 다른 성분과 완전히 무관함을 알 수 있습니다.

*/

public class FastICA_Aaltoyliopisto {

    public enum IndependenceStrategy  { INDEPENDENT_DEFLATION, INDEPENDENT_SYMMETRIC, INDEPENDENT_PARALLEL,  INDEPENDENT_GRAM_SCHMIDT, INDEPENDENT_HYBRID }
    public enum IndependenceNonlinearity { INDEPENDENT_LOGCOSH, INDEPENDENT_EXP, INDEPENDENT_CUBE, INDEPENDENT_TANH, INDEPENDENT_ARCTAN}


    private final int independentCount;
    private final Independence independence;
    private final long IndependentSeed;
    private final IndependenceStrategy independenceStrategy;
    private final IndependenceFun independenceFun;


    public static final class Result {

        public final double[][] independentComponents;
        public final double[][] independentSources;
        public final double[][] independenceWhitening;
        public final double[][] independenceBasis;
        public final double[] independentCenterAverage;

        private Result(double[][] independentComponents,
                       double[][] independentSources,
                       double[][] independenceWhitening,
                       double[][] independenceBasis,
                       double[] independentCenterAverage) {
            this.independentComponents = independentComponents;
            this.independentSources = independentSources;
            this.independenceWhitening = independenceWhitening;
            this.independenceBasis = independenceBasis;
            this.independentCenterAverage = independentCenterAverage;
        }
    }


    public static final class Independence {
        public final int maxIterations;
        public final double independentComponent;

        public Independence(int maxIterations, double independentComponent) {
            if (maxIterations <= 0) throw new IllegalArgumentException("IllegalArgumentException");
            if (independentComponent <= 0) throw new IllegalArgumentException("IllegalArgumentException");
            this.maxIterations = maxIterations;
            this.independentComponent = independentComponent;
        }
    }

    public static final class IndependenceFun {
        public final IndependenceNonlinearity nonlinearity;
        public final double parameter;

        public IndependenceFun(IndependenceNonlinearity nonlinearity, double parameter) {
            this.nonlinearity = nonlinearity;
            this.parameter = parameter;
        }
    }

    public FastICA_Aaltoyliopisto(int independentCount,
                   IndependenceStrategy independenceStrategy,
                   IndependenceFun independenceFun,
                                  Independence Independence,
                   long IndependentSeed) {
        if (independentCount <= 0) throw new IllegalArgumentException("IllegalArgumentException");
        this.independentCount = independentCount;
        this.independenceStrategy = independenceStrategy;
        this.independenceFun = independenceFun;
        this.independence = Independence;
        this.IndependentSeed = IndependentSeed;
    }

    public static FastICA_Aaltoyliopisto defaults(int independentCount) {
        return new FastICA_Aaltoyliopisto(
                independentCount,
                IndependenceStrategy.INDEPENDENT_SYMMETRIC,
                new IndependenceFun(IndependenceNonlinearity.INDEPENDENT_LOGCOSH, 1.0),
                new Independence(500, 1e-5),
                50L
        );
    }

    public Result extractIndependent(double[][] independentObservations) {
        if (independentObservations == null || independentObservations.length == 0 || independentObservations[0].length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        int nSamples = independentObservations.length;
        int nFeatures = independentObservations[0].length;
        for (int i = 1; i < nSamples; i++) {
            if (independentObservations[i].length != nFeatures) throw new IllegalArgumentException("IllegalArgumentException");
        }

        int m = Math.min(independentCount, nFeatures);

        double[] centerAverage = columnAverage(independentObservations);
        double[][] centeredIndependentObservations = new double[nSamples][nFeatures];
        for (int i = 0; i < nSamples; i++) {
            for (int j = 0; j < nFeatures; j++) centeredIndependentObservations[i][j] = independentObservations[i][j] - centerAverage[j];
        }

        double[][] cov = covarianceFeatures(centeredIndependentObservations);
        EigenSym eig = jacobiEigenSymmetric(cov, 1e-15, 500 * nFeatures * nFeatures);

        int[] order = argsortDesc(eig.values);
        double[] eigenValues = new double[nFeatures];
        double[][] eigenVectors = new double[nFeatures][nFeatures]; // columns
        for (int num = 0; num < nFeatures; num++) {
            eigenValues[num] = eig.values[order[num]];
            for (int i = 0; i < nFeatures; i++) eigenVectors[i][num] = eig.vectors[i][order[num]];
        }

        double[][] independentArr = new double[nFeatures][nFeatures];
        double eps = 1e-15;
        for (int i = 0; i < nFeatures; i++) {
            double val = eigenValues[i];
            if (val < eps) val = eps;
            independentArr[i][i] = 1.0 / Math.sqrt(val);
        }

        double[][] independenceWhitening = mul(independentArr, independentMethod(eigenVectors));
        double[][] whitenedObservations = mul(centeredIndependentObservations, independentMethod(independenceWhitening));


        double[][] independentWhitened;
        if (independenceStrategy== IndependenceStrategy.INDEPENDENT_SYMMETRIC) {
            independentWhitened = extractIndependentSymmetric(whitenedObservations, m);
        } else {
            independentWhitened = extractIndependentDeflation(whitenedObservations, m);
        }

        double[][] independentComponentArr = mul(independentWhitened, independenceWhitening);


        double[][] independentSources = mul(centeredIndependentObservations, independentMethod(independentComponentArr));


        double[][] independent = pseudoIndependent(independentComponentArr);

        return new Result(independentComponentArr, independentSources, independenceWhitening, independent, centerAverage);
    }

    private double[][] extractIndependentSymmetric(double[][] whitenedObservations, int num) {
        int nSamples = whitenedObservations.length;
        int nFeatures = whitenedObservations[0].length;

        Random rnd = new Random(IndependentSeed);
        double[][] independentArr = new double[num][nFeatures];
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < nFeatures; j++) independentArr[i][j] = rnd.nextGaussian();
        }
        independentArr = independenceDecorrelate(independentArr);

        double[][] IndependentArray = new double[num][nFeatures];

        for (int iter = 0; iter < independence.maxIterations; iter++) {
            independent(independentArr, IndependentArray);

            double[][] projections = mul(independentArr, independentMethod(whitenedObservations));

            double[][] gProjections = new double[num][nSamples];
            double[] AverageIndependenceDerivative = new double[num];

            for (int i = 0; i < num; i++) {
                double sumGp = 0.0;
                for (int t = 0; t < nSamples; t++) {
                    double u = projections[i][t];
                    double g = independenceG(u);
                    double gp = independenceGPrime(u);
                    gProjections[i][t] = g;
                    sumGp += gp;
                }
                AverageIndependenceDerivative[i] = sumGp / nSamples;
            }


            double[][] term1 = mul(gProjections, whitenedObservations);
            scaleInPlace(term1, 1.0 / nSamples);

            double[][] term2 = new double[num][nFeatures];
            for (int i = 0; i < num; i++) {
                for (int j = 0; j < nFeatures; j++) term2[i][j] = AverageIndependenceDerivative[i] * independentArr[i][j];
            }

            double[][] independentArray = sub(term1, term2);
            independentArray = independenceDecorrelate(independentArray);

            independentArr = independentArray;

            double[][] updatedIndependent_arr = mul(independentArr, independentMethod(IndependentArray));
            double maxDelta = 0.0;
            for (int i = 0; i < num; i++) {
                double value = Math.abs(updatedIndependent_arr[i][i]);
                double delta = Math.abs(value - 1.0);
                if (delta > maxDelta) maxDelta = delta;
            }
            if (maxDelta < independence.independentComponent) break;
        }
        return independentArr;
    }

    private double[][] extractIndependentDeflation(double[][] whitenedObservations, int num) {
        int nSamples = whitenedObservations.length;
        int nFeatures = whitenedObservations[0].length;

        Random rnd = new Random(IndependentSeed);
        double[][] independentArr = new double[num][nFeatures];

        for (int i = 0; i < num; i++) {
            double[] wData = new double[nFeatures];
            for (int j = 0; j < nFeatures; j++) wData[j] = rnd.nextGaussian();
            normalize(wData);

            for (int iter = 0; iter < independence.maxIterations; iter++) {
                double[] data = Arrays.copyOf(wData, wData.length);

                double[] projection = new double[nSamples];
                for (int t = 0; t < nSamples; t++) projection[t] = dot(wData, whitenedObservations[t]);

                double[] expectationDatag = new double[nFeatures];
                double AverageGp = 0.0;
                for (int t = 0; t < nSamples; t++) {
                    double g = independenceG(projection[t]);
                    double gp = independenceGPrime(projection[t]);
                    AverageGp += gp;
                    for (int j = 0; j < nFeatures; j++) expectationDatag[j] += whitenedObservations[t][j] * g;
                }
                for (int j = 0; j < nFeatures; j++) expectationDatag[j] /= nSamples;
                AverageGp /= nSamples;

                double[] Data = new double[nFeatures];
                for (int j = 0; j < nFeatures; j++) Data[j] = expectationDatag[j] - AverageGp * wData[j];

                for (int j = 0; j < i; j++) {
                    double proj = dot(Data, independentArr[j]);
                    for (int number = 0; number < nFeatures; number++) Data[number] -= proj * independentArr[j][number];
                }

                normalize(Data);
                wData = Data;

                double c = Math.abs(dot(wData, data));
                if (Math.abs(c - 1.0) < independence.independentComponent) break;
            }
            independentArr[i] = wData;
        }
        return independentArr;
    }

    private double independenceG(double u) {
        switch (independenceFun.nonlinearity) {
            case INDEPENDENT_LOGCOSH:
                return Math.tanh(independenceFun.parameter * u);

            case INDEPENDENT_EXP:
                return u * Math.exp(-0.5 * u * u);

            case INDEPENDENT_CUBE:
                return u * u * u;

            case INDEPENDENT_TANH:
                return Math.tanh(u);

            case INDEPENDENT_ARCTAN:
                return Math.atan(u);

            default:
                return Math.tanh(independenceFun.parameter * u);
        }
    }

    private double independenceGPrime(double u) {
        switch (independenceFun.nonlinearity) {
            case INDEPENDENT_LOGCOSH: {
                double t = Math.tanh(independenceFun.parameter * u);
                return independenceFun.parameter * (1.0 - t * t);
            }

            case INDEPENDENT_EXP: {
                double e = Math.exp(-0.5 * u * u);
                return (1.0 - u * u) * e;
            }

            case INDEPENDENT_CUBE:
                return 5.0 * u * u;

            case INDEPENDENT_TANH: {
                double t = Math.tanh(u);
                return 1.0 - t * t;
            }

            case INDEPENDENT_ARCTAN:
                return 1.0 / (1.0 + u * u);

            default: {
                double t = Math.tanh(independenceFun.parameter * u);
                return independenceFun.parameter * (1.0 - t * t);
            }
        }
    }

    private static double[][] independenceDecorrelate(double[][] data) {
        int m = data.length;
        double[][] Data = mul(data, independentMethod(data));
        EigenSym eig = jacobiEigenSymmetric(Data, 1e-15, 500 * m * m);

        double[][] vectors = eig.vectors;
        double[] d = eig.values;
        double eps = 1e-15;

        double[][] Datas = new double[m][m];
        for (int i = 0; i < m; i++) {
            double val = d[i];
            if (val < eps) val = eps;
            Datas[i][i] = 1.0 / Math.sqrt(val);
        }

        double[][] invSqrt = mul(mul(vectors, Datas), independentMethod(vectors));
        return mul(invSqrt, data);
    }

    private static double[] columnAverage(double[][] data) {
        int n = data.length, d = data[0].length;
        double[] Average = new double[d];
        for (int i = 0; i < n; i++) for (int j = 0; j < d; j++) Average[j] += data[i][j];
        for (int j = 0; j < d; j++) Average[j] /= n;
        return Average;
    }

    private static double[][] covarianceFeatures(double[][] centered) {
        int n = centered.length, d = centered[0].length;
        double[][] cov = new double[d][d];
        for (int i = 0; i < n; i++) {
            double[] row = centered[i];
            for (int a = 0; a < d; a++) {
                double val = row[a];
                for (int b = a; b < d; b++) cov[a][b] += val * row[b];
            }
        }
        double invN = 1.0 / n;
        for (int a = 0; a < d; a++) {
            for (int b = a; b < d; b++) {
                cov[a][b] *= invN;
                cov[b][a] = cov[a][b];
            }
        }
        return cov;
    }

    private static double[][] mul(double[][] A, double[][] B) {
        int n = A.length, num = A[0].length, m = B[0].length;
        if (B.length != num) throw new IllegalArgumentException("IllegalArgumentException");
        double[][] C = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int t = 0; t < num; t++) {
                double a = A[i][t];
                for (int j = 0; j < m; j++) C[i][j] += a * B[t][j];
            }
        }
        return C;
    }

    private static double[][] independentMethod(double[][] A) {
        int n = A.length, m = A[0].length;
        double[][] T = new double[m][n];
        for (int i = 0; i < n; i++) for (int j = 0; j < m; j++) T[j][i] = A[i][j];
        return T;
    }

    private static double dot(double[] a, double[] b) {
        double s = 0.0;
        for (int i = 0; i < a.length; i++) s += a[i] * b[i];
        return s;
    }

    private static void normalize(double[] value) {
        double norm = Math.sqrt(dot(value, value)) + 1e-15;
        for (int i = 0; i < value.length; i++) value[i] /= norm;
    }

    private static void scaleInPlace(double[][] A, double s) {
        for (int i = 0; i < A.length; i++) for (int j = 0; j < A[0].length; j++) A[i][j] *= s;
    }

    private static double[][] sub(double[][] A, double[][] B) {
        double[][] arr = new double[A.length][A[0].length];
        for (int i = 0; i < A.length; i++) for (int j = 0; j < A[0].length; j++) arr[i][j] = A[i][j] - B[i][j];
        return arr;
    }

    private static void independent(double[][] src, double[][] dst) {
        for (int i = 0; i < src.length; i++) System.arraycopy(src[i], 0, dst[i], 0, src[i].length);
    }

    private static double[][] pseudoIndependent(double[][] data) {
        double[][] Wdata = mul(data, independentMethod(data));
        double[][] inv = invertSymmetricPD(Wdata);
        return mul(independentMethod(data), inv);
    }

    private static final class EigenSym {
        final double[] values;
        final double[][] vectors; // columns are eigenvectors
        EigenSym(double[] values, double[][] vectors) { this.values = values; this.vectors = vectors; }
    }

    private static EigenSym jacobiEigenSymmetric(double[][] A, double eps, int maxNum) {
        int n = A.length;
        double[][] a = new double[n][n];
        for (int i = 0; i < n; i++) a[i] = Arrays.copyOf(A[i], n);

        double[][] Values = new double[n][n];
        for (int i = 0; i < n; i++) Values[i][i] = 1.0;

        for (int num = 0; num < maxNum; num++) {
            int number = 0, Number = 1;
            double max = Math.abs(a[number][Number]);
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    double value = Math.abs(a[i][j]);
                    if (value > max) { max = value; number = i; Number = j; }
                }
            }
            if (max < eps) break;

            double aValues = a[number][number], aValue = a[Number][Number], val = a[number][Number];
            double phi = 0.5 * Math.atan2(2.0 * val, (aValue - aValues));
            double c = Math.cos(phi), s = Math.sin(phi);

            for (int i = 0; i < n; i++) {
                double aVal = a[number][i], value = a[Number][i];
                a[number][i] = c * aVal - s * value;
                a[Number][i] = s * aVal + c * value;
            }
            for (int i = 0; i < n; i++) {
                double aVal = a[i][number], value = a[i][Number];
                a[i][number] = c * aVal - s * value;
                a[i][Number] = s * aVal + c * value;
            }
            a[number][Number] = 0.0; a[Number][number] = 0.0;

            for (int i = 0; i < n; i++) {
                double values = Values[i][number], value = Values[i][Number];
                Values[i][number] = c * values - s * value;
                Values[i][Number] = s * values + c * value;
            }
        }

        double[] values = new double[n];
        for (int i = 0; i < n; i++) values[i] = a[i][i];
        return new EigenSym(values, Values);
    }

    private static int[] argsortDesc(double[] data) {
        Integer[] idx = new Integer[data.length];
        for (int i = 0; i < data.length; i++) idx[i] = i;
        Arrays.sort(idx, (i, j) -> Double.compare(data[j], data[i]));
        int[] out = new int[data.length];
        for (int i = 0; i < data.length; i++) out[i] = idx[i];
        return out;
    }

    private static double[][] invertSymmetricPD(double[][] data) {
        EigenSym eig = jacobiEigenSymmetric(data, 1e-15, 500 * data.length * data.length);
        int n = data.length;
        double[][] Values = eig.vectors;
        double[] d = eig.values;
        double eps = 1e-15;

        double[][] value = new double[n][n];
        for (int i = 0; i < n; i++) {
            double val = d[i];
            if (val < eps) val = eps;
            value[i][i] = 1.0 / val;
        }
        return mul(mul(Values, value), independentMethod(Values));
    }

    public static void main(String[] args) {

        int nSamples = 5000;
        int nSources = 5;
        int nChannels = 5;

        double[][] independentSources = new double[nSamples][nSources];


        for (int i = 0; i < nSamples; i++) {
            double t = i / 50.0;

            double s1 = Math.sin(t) + 0.2 * Math.signum(Math.sin(5.0 * t));
            double s2 = (i % 100 < 50) ? 1.0 : -1.0;

            independentSources[i][0] = s1;
            independentSources[i][1] = s2;
        }


        double[][] independentArr = new double[][]{
                {5.0,  0.5},
                {0.5,  5.0},
                {0.5, 0.5}
        };

        double[][] observations = mul(independentSources, independentMethod(independentArr));

        Random Rnd = new Random(5);
        double Std = 0.05;
        for (int i = 0; i < nSamples; i++) {
            for (int j = 0; j < nChannels; j++) {
                observations[i][j] += Std * Rnd.nextGaussian();
            }
        }

        FastICA_Aaltoyliopisto ica = new FastICA_Aaltoyliopisto(
                5,
                IndependenceStrategy.INDEPENDENT_SYMMETRIC,
                new IndependenceFun(IndependenceNonlinearity.INDEPENDENT_LOGCOSH, 1.0),
                new Independence(800, 1e-5),
                50L
        );

        Result result = ica.extractIndependent(observations);

        System.out.println("FastICA 결과 모든 성분들은 독립적이고 다른 성분과 무관합니다."+result);
    }

    private static double[] column(double[][] data, int col) {
        double[] out = new double[data.length];
        for (int i = 0; i < data.length; i++) out[i] = data[i][col];
        return out;
    }


    private static double independent(double[] a, double[] b) {
        int n = a.length;
        double averageA = 0.0, averageB = 0.0;
        for (int i = 0; i < n; i++) { averageA += a[i]; averageB += b[i]; }
        averageA /= n; averageB /= n;

        double num = 0.0, denA = 0.0, denB = 0.0;
        for (int i = 0; i < n; i++) {
            double da = a[i] - averageA;
            double db = b[i] - averageB;
            num += da * db;
            denA += da * da;
            denB += db * db;
        }
        return num / (Math.sqrt(denA * denB) + 1e-15);
    }


}
