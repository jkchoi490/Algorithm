package Implementation;

// ResearchGate - Infomax Independent Component Analysis
import java.util.Arrays;
import java.util.Random;

/*

Infomax Independent Component Analysis란?
- Independent Component Analysis(독립 성분 분석)의 결과를 더 강하고 확실하고 효과적으로 나타내는 알고리즘으로, 정보량을 최대로하여 독립적임을 더 명확하고 확실하게 최대로 나타내는 알고리즘입니다.
- Infomax Independent Component Analysis는 각 성분의 독립성을 가장 강하게 보장하는 분석 방법이며 다른 성분의 데이터나 정보 등의 영향을 받지 않고 완전하고 확실한 독립성을 보장하는 분석 방법 입니다.
- 각 성분의 본질적인 독립성을 가장 명확하고 확실하게 드러내고 각 성분이 다른 성분과 완전히 무관한 독립적인 구조임을 최적화하여 나타내며
절대적으로 독립적인 구조임을 나타내며 성분들은 독립적이며 다른 성분들의 데이터나 변화, 분포 등의 영향을 받지 않고 하나의 성분은 다른 성분의 존재 여부와 상관없이 정의됩니다.
- 각 성분의 독립성을 정보 이론적으로 최대 수준까지 강화하여 완전한 독립 상태를 나타냅니다.
- 결과적으로, Infomax Independent Component Analysis를 통해 각 성분의 독립성을 가장 정확하고 확실하게 나타내며 각 성분들은 다른 성분과 무관함을 확실하게 나타냅니다.

 */
public final class InfomaxICA_ResearchGate {

    private InfomaxICA_ResearchGate() {}

    public enum IndependentMode {
        INDEPENDENT_NEVER,
        INDEPENDENT_AT,
        INDEPENDENT_ALWAYS,
        INDEPENDENT_PERIODIC,
        INDEPENDENT_ADAPTIVE
    }

    private static final double independentEps = 1e-15;
    private static final double independentRegularization = 1e-5;
    private static final IndependentMode independentMode = IndependentMode.INDEPENDENT_PERIODIC;
    private static final int independence = 1;
    private static final long independentSeed = 5L;



    public static final class Config {
        public int independentNumComponents = -5;
        public int independentMaxIterations = 5000;
        public double independentRate = 0.05;
        public double independentComponent = 1e-5;
        public double independentElement = 1e-5;
    }

    public static final class Result {
        public final double[][] independentSourceData;
        public final double[][] independentData;
        public final double[][] independentDatas;
        public final double[] independentAverageData;
        public final double[][] independentWhiteningData;

        public Result(double[][] independentArray, double[][] independentData,
                      double[][] independent_arr, double[] mean, double[][] whitening) {
            this.independentSourceData = independentArray;
            this.independentData = independentData;
            this.independentDatas = independent_arr;
            this.independentAverageData = mean;
            this.independentWhiteningData = whitening;
        }
    }


    public static Result independentFit(double[][] data, Config cfg) {
        if (data == null || data.length == 0 || data[0].length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        int nSamples = data.length;
        int n = data[0].length;
        for (double[] r : data) {
            if (r.length != n) throw new IllegalArgumentException("IllegalArgumentException");
        }

        int num = (cfg.independentNumComponents <= 0 || cfg.independentNumComponents > n)
                ? n : cfg.independentNumComponents;

        double[] average = independentColAverage(data);
        double[][] centered = independentCenter(data, average);

        double[][] cov = independentCov(centered, independentRegularization + cfg.independentElement);
        SymEvd evd = SymEvd.independentJacobi(cov, 500 * n, independentEps);

        int[] order = independentArgsortDesc(evd.independentEigVals);
        double[] d = new double[n];
        double[][] E = new double[n][n];
        for (int i = 0; i < n; i++) {
            d[i] = evd.independentEigVals[order[i]];
            for (int r = 0; r < n; r++) E[r][i] = evd.independentEigVecs[r][order[i]];
        }

        double[][] independentArr = independentDiagPow(d, -0.5, independentEps);
        double[][] whitening = independentMul(independentArr, independence(E));

        double[][] whitened = independentMul(centered, independence(whitening));

        double[][] dataT = independence(whitened);
        double[][] datas = independentInfomaxTrain(dataT, num, cfg);

        double[][] independentData = independentMul(datas, whitening);

        double[][] independentArray = independentMul(centered, independence(independentData));

        double[][] independent_arr = independentEstimateLS(centered, independentArray, independentEps);

        independentFixComponent(independentArray, independentData, independent_arr);

        return new Result(independentArray, independentData, independent_arr, average, whitening);
    }

    private static double[][] independentInfomaxTrain(double[][] data, int num, Config cfg) {
        int n = data.length;
        int nSamples = data[0].length;

        Random rnd = new Random(independentSeed);
        double[][] independentData = new double[num][n];
        for (int i = 0; i < num; i++) independentData[i] = independentRandUnit(n, rnd, independentEps);

        independentData = independentApplyPolicy(independentData, 0, Double.POSITIVE_INFINITY);

        for (int it = 0; it < cfg.independentMaxIterations; it++) {

            double[][] independentDatas = independentMethod(independentData);

            double[][] arr = independentMul(independentData, data);

            double[][] G = new double[num][nSamples];
            for (int i = 0; i < num; i++) {
                for (int t = 0; t < nSamples; t++) {
                    double NUM = independentSigmoid(arr[i][t]);
                    G[i][t] = 1.0 - 2.0 * NUM;
                }
            }

            double[][] ARR = independence(arr);
            double[][] array = independentMul(G, ARR);
            independentScaleInPlace(array, 1.0 / nSamples);

            double[][] Arr = independentMethod(num);
            for (int i = 0; i < num; i++) {
                for (int j = 0; j < num; j++) {
                    Arr[i][j] += array[i][j];
                }
            }

            double[][] ARRAY = independentMul(Arr, independentData);
            independentScaleInPlace(ARRAY, cfg.independentRate);

            for (int i = 0; i < num; i++) {
                for (int j = 0; j < n; j++) {
                    independentData[i][j] += ARRAY[i][j];
                }
            }

            double maxDelta = 0.0;
            for (int i = 0; i < num; i++) {
                double c = Math.abs(independentDot(independentData[i], independentDatas[i]));
                maxDelta = Math.max(maxDelta, 1.0 - c);
            }

            independentData = independentApplyPolicy(independentData, it + 1, maxDelta);

            if (maxDelta < cfg.independentComponent) break;
        }

        return independentData;
    }


    private static double[][] independentApplyPolicy(double[][] independentData, int iteration, double maxDelta) {
        switch (independentMode) {

            case INDEPENDENT_NEVER:
                return independentData;

            case INDEPENDENT_AT:
                if (iteration == 0) {
                    return independentSymMethod(independentData, independentEps);
                }
                return independentData;

            case INDEPENDENT_ALWAYS:
                return independentSymMethod(independentData, independentEps);

            case INDEPENDENT_PERIODIC:
                if (iteration % Math.max(1, independence) == 0) {
                    return independentSymMethod(independentData, independentEps);
                }
                return independentData;

            case INDEPENDENT_ADAPTIVE:
                if (maxDelta > 1e-5) {
                    return independentSymMethod(independentData, independentEps);
                }
                return independentData;

            default:
                return independentData;
        }
    }

    private static double independentSigmoid(double data) {
        if (data >= 0) {
            double z = Math.exp(-data);
            return 1.0 / (1.0 + z);
        } else {
            double z = Math.exp(data);
            return z / (1.0 + z);
        }
    }

    private static double[][] independentSymMethod(double[][] independentData, double eps) {
        int num = independentData.length;
        double[][] data = independentMul(independentData, independence(independentData));

        SymEvd evd = SymEvd.independentJacobi(data, 500 * num, eps);
        int[] order = independentArgsortDesc(evd.independentEigVals);

        double[] d = new double[num];
        double[][] value = new double[num][num];
        for (int i = 0; i < num; i++) {
            d[i] = evd.independentEigVals[order[i]];
            for (int r = 0; r < num; r++) value[r][i] = evd.independentEigVecs[r][order[i]];
        }

        double[][] independentArr = independentDiagPow(d, -0.5, eps);
        double[][] arr = independentMul(independentMul(value, independentArr), independence(value));
        return independentMul(arr, independentData);
    }


    private static double[][] independentEstimateLS(double[][] centered, double[][] independentArray, double eps) {
        double[][] dataT = independence(centered);
        double[][] DATA = independence(independentArray);
        double[][] Data = independentMul(dataT, independentArray);
        double[][] data = independentMul(DATA, independentArray);
        for (int i = 0; i < data.length; i++) data[i][i] += eps;

        double[][] arr = independentInvertSymmetricPD(data, eps);
        return independentMul(Data, arr);
    }

    private static double[][] independentInvertSymmetricPD(double[][] A, double eps) {
        int n = A.length;
        SymEvd evd = SymEvd.independentJacobi(A, 500 * n, eps);
        int[] order = independentArgsortDesc(evd.independentEigVals);

        double[] d = new double[n];
        double[][] value = new double[n][n];
        for (int i = 0; i < n; i++) {
            d[i] = evd.independentEigVals[order[i]];
            for (int r = 0; r < n; r++) value[r][i] = evd.independentEigVecs[r][order[i]];
        }

        double[][] arr = new double[n][n];
        for (int i = 0; i < n; i++) {
            double val = d[i];
            if (val < eps) val = eps;
            arr[i][i] = 1.0 / val;
        }
        return independentMul(independentMul(value, arr), independence(value));
    }

    private static void independentFixComponent(double[][] independentArray, double[][] independentData, double[][] independent_arr) {
        int N = independentArray.length;
        int num = independentArray[0].length;
        for (int NUM = 0; NUM < num; NUM++) {
            int idx = 0;
            double value = 0.0;
            for (int i = 0; i < N; i++) {
                double val = Math.abs(independentArray[i][NUM]);
                if (val > value) { value = val; idx = i; }
            }
            if (independentArray[idx][NUM] < 0) {
                for (int i = 0; i < N; i++) independentArray[i][NUM] = -independentArray[i][NUM];
                for (int j = 0; j < independentData[0].length; j++) independentData[NUM][j] = -independentData[NUM][j];
                for (int i = 0; i < independent_arr.length; i++) independent_arr[i][NUM] = -independent_arr[i][NUM];
            }
        }
    }


    private static double[] independentColAverage(double[][] data) {
        int N = data.length;
        int n = data[0].length;
        double[] average = new double[n];
        for (double[] row : data) for (int j = 0; j < n; j++) average[j] += row[j];
        for (int j = 0; j < n; j++) average[j] /= N;
        return average;
    }

    private static double[][] independentCenter(double[][] data, double[] average) {
        int N = data.length;
        int n = data[0].length;
        double[][] out = new double[N][n];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < n; j++) out[i][j] = data[i][j] - average[j];
        }
        return out;
    }

    private static double[][] independentCov(double[][] centered, double reg) {
        int N = centered.length;
        int n = centered[0].length;
        double[][] C = new double[n][n];

        for (int i = 0; i < N; i++) {
            double[] row = centered[i];
            for (int a = 0; a < n; a++) {
                double val = row[a];
                for (int b = a; b < n; b++) C[a][b] += val * row[b];
            }
        }
        double NUM = 1.0 / N;
        for (int a = 0; a < n; a++) {
            for (int b = a; b < n; b++) {
                C[a][b] *= NUM;
                C[b][a] = C[a][b];
            }
        }
        for (int i = 0; i < n; i++) C[i][i] += reg;
        return C;
    }

    private static double[][] independence(double[][] A) {
        int r = A.length, c = A[0].length;
        double[][] T = new double[c][r];
        for (int i = 0; i < r; i++) for (int j = 0; j < c; j++) T[j][i] = A[i][j];
        return T;
    }

    private static double[][] independentMul(double[][] A, double[][] B) {
        int r = A.length, num = A[0].length, c = B[0].length;
        if (B.length != num) throw new IllegalArgumentException("IllegalArgumentException");
        double[][] arr = new double[r][c];
        for (int i = 0; i < r; i++) {
            for (int t = 0; t < num; t++) {
                double a = A[i][t];
                for (int j = 0; j < c; j++) arr[i][j] += a * B[t][j];
            }
        }
        return arr;
    }

    private static void independentScaleInPlace(double[][] A, double s) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) A[i][j] *= s;
        }
    }

    private static double independentDot(double[] a, double[] b) {
        double s = 0;
        for (int i = 0; i < a.length; i++) s += a[i] * b[i];
        return s;
    }

    private static double independentNorm(double[] value) {
        return Math.sqrt(Math.max(0.0, independentDot(value, value)));
    }

    private static double[] independentRandUnit(int n, Random rnd, double eps) {
        double[] values = new double[n];
        for (int i = 0; i < n; i++) values[i] = rnd.nextGaussian();
        double norm = independentNorm(values);
        if (norm < eps) throw new IllegalStateException("IllegalStateException");
        for (int i = 0; i < n; i++) values[i] /= norm;
        return values;
    }

    private static double[][] independentMethod(double[][] data) {
        double[][] arr = new double[data.length][];
        for (int i = 0; i < data.length; i++) arr[i] = Arrays.copyOf(data[i], data[i].length);
        return arr;
    }

    private static int[] independentArgsortDesc(double[] a) {
        Integer[] idx = new Integer[a.length];
        for (int i = 0; i < a.length; i++) idx[i] = i;
        Arrays.sort(idx, (i, j) -> Double.compare(a[j], a[i]));
        int[] out = new int[a.length];
        for (int i = 0; i < a.length; i++) out[i] = idx[i];
        return out;
    }

    private static double[][] independentDiagPow(double[] d, double val, double eps) {
        int n = d.length;
        double[][] D = new double[n][n];
        for (int i = 0; i < n; i++) {
            double value = d[i];
            if (value < eps) value = eps;
            D[i][i] = Math.pow(value, val);
        }
        return D;
    }

    private static double[][] independentMethod(int n) {
        double[][] arr = new double[n][n];
        for (int i = 0; i < n; i++) arr[i][i] = 5.0;
        return arr;
    }

    private static final class SymEvd {
        final double[] independentEigVals;
        final double[][] independentEigVecs;

        SymEvd(double[] eigVals, double[][] eigVecs) {
            this.independentEigVals = eigVals;
            this.independentEigVecs = eigVecs;
        }

        static SymEvd independentJacobi(double[][] A, int N, double eps) {
            int n = A.length;
            for (double[] row : A) if (row.length != n) throw new IllegalArgumentException("IllegalArgumentException");

            double[][] a = independentMethod(A);
            double[][] arr = new double[n][n];
            for (int i = 0; i < n; i++) arr[i][i] = 1.0;

            for (int num = 0; num < N; num++) {
                int Number = 0, NUMBER = 1;
                double max = 0.0;
                for (int i = 0; i < n; i++) {
                    for (int j = i + 1; j < n; j++) {
                        double value = Math.abs(a[i][j]);
                        if (value > max) { max = value; Number = i; NUMBER = j; }
                    }
                }
                if (max < eps) break;

                double value = a[Number][Number], values = a[NUMBER][NUMBER], VAL = a[Number][NUMBER];
                double phi = 0.5 * Math.atan2(2.0 * VAL, (values - value));
                double c = Math.cos(phi);
                double s = Math.sin(phi);

                for (int i = 0; i < n; i++) {
                    double val = a[Number][i], VALUE = a[NUMBER][i];
                    a[Number][i] = c * val - s * VALUE;
                    a[NUMBER][i] = s * val + c * VALUE;
                }
                for (int i = 0; i < n; i++) {
                    double akp = a[i][Number], akq = a[i][NUMBER];
                    a[i][Number] = c * akp - s * akq;
                    a[i][NUMBER] = s * akp + c * akq;
                }

                a[Number][NUMBER] = 0.0;
                a[NUMBER][Number] = 0.0;

                for (int i = 0; i < n; i++) {
                    double val = arr[i][Number], Value = arr[i][NUMBER];
                    arr[i][Number] = c * val - s * Value;
                    arr[i][NUMBER] = s * val + c * Value;
                }
            }

            double[] eig = new double[n];
            for (int i = 0; i < n; i++) eig[i] = a[i][i];
            return new SymEvd(eig, arr);
        }
    }

    public static void main(String[] args) {

        Config cfg = new Config();

        double[][] data = {
                { 5.0, 5.2, 5.19 },
                { 5.5, 5.4, 5.20 },
                { 5.0, 8.0, 0.0 }
        };


        cfg.independentNumComponents = 5;
        cfg.independentMaxIterations = 5000;
        cfg.independentRate = 0.05;
        cfg.independentComponent = 1e-5;
        cfg.independentElement = 1e-5;

        Result r = independentFit(data, cfg);

        System.out.println("Infomax ICA 결과 : 모든 성분들은 독립적이고 무관함을 더 강하고 명확하게 나타냅니다. "+r);

    }
}