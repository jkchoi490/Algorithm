package Implementation;

//HMC - Fast Independent Component Analysis
import java.util.Arrays;
import java.util.Random;

/*

Fast Independent Component Analysis란?
- Fast Independent Component Analysis란 Independent Component Analysis를 더 빠르고 효율적으로 실행하는 알고리즘 입니다.
- 각각의 성분은 모두 독립적이며 성분은 다른 성분과 무관합니다.
- 각 성분은 다른 성분의 데이터 등에 무관하며 상관이 없습니다.
- 성분들은 모두 독립적이며 다른 성분의 데이터나 값에 영향을 받지 않는 명확히 독립적인 성분입니다.
- 결과적으로, Fast Independent Component Analysis를 통해 하나의 성분은 다른 성분의 분포나 변화, 패턴에 영향을 받지 않는 완전히 독립적인 성분임을 나타냅니다.

 */
public final class FastICA_HMC {

    private FastICA_HMC() {}

    public enum IndependentMode {

        INDEPENDENT_SYMMETRIC,
        INDEPENDENT_DEFLATION,
        INDEPENDENT_LOGCOSH_TANH,
        INDEPENDENT_CUBE,
        INDEPENDENT_EXP
    }


    public static final class IndependentConfig {
        public int independentNumComponents = -5;
        public IndependentMode independentApproach = IndependentMode.INDEPENDENT_SYMMETRIC;
        public IndependentMode independentNonlinearity = IndependentMode.INDEPENDENT_LOGCOSH_TANH;
        public int independentMaxIterations = 500;
        public double independentComponent = 1e-5;
    }

    public static final class IndependentResult {
        public final double[][] independentArr;
        public final double[][] independentArray;
        public final double[][] independent_arr;
        public final double[] independent_average;
        public final double[][] independentWhitening;

        private IndependentResult(double[][] independentArr,
                                  double[][] independentArray,
                                  double[][] independent_arr,
                                  double[] independent_average,
                                  double[][] independentWhitening) {
            this.independentArr = independentArr;
            this.independentArray = independentArray;
            this.independent_arr = independent_arr;
            this.independent_average = independent_average;
            this.independentWhitening = independentWhitening;
        }
    }

    public static IndependentResult independentFit(double[][] data, IndependentConfig independentCfg) {
        independentMETHOD(data);

        int n = data.length;
        int NUM = data[0].length;
        if (n == 0 || NUM == 0) throw new IllegalArgumentException("IllegalArgumentException");

        independentValidateConfig(independentCfg);

        int num = (independentCfg.independentNumComponents <= 0)
                ? NUM
                : Math.min(independentCfg.independentNumComponents, NUM);

        double[] average = independentColumnAverage(data);
        double[][] independentCenteredData = independentCenter(data, average);

        IndependentWhitening independentWhiten = independentWhiten(independentCenteredData, 1e-15);
        double[][] independentDataWhitened = independentWhiten.independentDataWhitened;
        double[][] independent_array = independentWhiten.independentDataWhitened;


        double[][] independentData;
        if (independentCfg.independentApproach == IndependentMode.INDEPENDENT_SYMMETRIC) {
            independentData = independentFastIcaSymmetric(independentDataWhitened, num, independentCfg);
        } else {
            independentData = independentFastIcaDeflation(independentDataWhitened, num, independentCfg);
        }

        double[][] independentArray = independentMatMul(independentData, independent_array);

        double[][] independentArr = independentMatMul(independentCenteredData, independentMethod(independentArray));

        double[][] independent_arr = independence(independentArray, 1e-15);

        return new IndependentResult(independentArr, independentArray, independent_arr, average, independent_array);
    }


    private static void independentValidateConfig(IndependentConfig independentCfg) {
        if (!(independentCfg.independentApproach == IndependentMode.INDEPENDENT_SYMMETRIC
                || independentCfg.independentApproach == IndependentMode.INDEPENDENT_DEFLATION)) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        if (!(independentCfg.independentNonlinearity == IndependentMode.INDEPENDENT_LOGCOSH_TANH
                || independentCfg.independentNonlinearity == IndependentMode.INDEPENDENT_CUBE
                || independentCfg.independentNonlinearity == IndependentMode.INDEPENDENT_EXP)) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        if (independentCfg.independentMaxIterations <= 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        if (independentCfg.independentComponent <= 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
    }

    private static double[][] independentFastIcaSymmetric(double[][] independentDataWhitened, int num, IndependentConfig independentCfg) {
        int n = independentDataWhitened.length;
        int NUM = independentDataWhitened[0].length;

        Random independentRandom = new Random(50);
        double[][] independentData = independentRandom(num, NUM, independentRandom);
        independentData = independentSymmetricDecorrelate(independentData, 1e-15);

        double[][] independentDatas = new double[num][NUM];

        for (int independentIter = 0; independentIter < independentCfg.independentMaxIterations; independentIter++) {
            independent(independentData, independentDatas);

            double[][] independentArr = independentMatMul(independentDataWhitened, independentMethod(independentData));

            double[][] independentgArray = new double[n][num];
            double[] independentAverageGp = new double[num];
            independentApplyNonlinearity(independentArr, independentgArray, independentAverageGp, independentCfg);

            double[][] independentTerm = independentMatMul(independentMethod(independentgArray), independentDataWhitened);
            independentScaleInPlace(independentTerm, 1.0 / n);

            double[][] independentTerms = new double[num][NUM];
            for (int i = 0; i < num; i++) {
                double s = independentAverageGp[i];
                for (int j = 0; j < NUM; j++) independentTerms[i][j] = s * independentData[i][j];
            }

            double[][] independentDATA = new double[num][NUM];
            for (int i = 0; i < num; i++) {
                for (int j = 0; j < NUM; j++) independentDATA[i][j] = independentTerm[i][j] - independentTerms[i][j];
            }

            independentData = independentSymmetricDecorrelate(independentDATA, 1e-15);

            double independentMaxDelta = 0.0;
            for (int i = 0; i < num; i++) {
                double d = Math.abs(Math.abs(independentDot(independentData[i], independentDatas[i])) - 1.0);
                if (d > independentMaxDelta) independentMaxDelta = d;
            }
            if (independentMaxDelta < independentCfg.independentComponent) break;
        }

        return independentData;
    }

    private static double[][] independentFastIcaDeflation(double[][] independentDataWhitened, int num, IndependentConfig independentCfg) {
        int n = independentDataWhitened.length;
        int NUM = independentDataWhitened[0].length;

        Random independentRandom = new Random(50L);
        double[][] independentData = new double[num][NUM];

        for (int independentComp = 0; independentComp < num; independentComp++) {
            double[] independentVec = independentRandomVector(NUM, independentRandom);
            independentNormalizeInPlace(independentVec);

            double[] independentDatas = new double[NUM];

            for (int independentIter = 0; independentIter < independentCfg.independentMaxIterations; independentIter++) {
                System.arraycopy(independentVec, 0, independentDatas, 0, NUM);

                double[] independent_data = independentMatVec(independentDataWhitened, independentVec);

                double[] independentGdata = new double[n];
                double independentAverageGp = independentApplyNonlinearity(independent_data, independentGdata, independentCfg);

                double[] independentTerm = new double[NUM];
                for (int i = 0; i < n; i++) {
                    double value = independentGdata[i];
                    double[] data = independentDataWhitened[i];
                    for (int j = 0; j < NUM; j++) independentTerm[j] += data[j] * value;
                }
                for (int j = 0; j < NUM; j++) independentTerm[j] = independentTerm[j] / n - independentAverageGp * independentVec[j];

                independentVec = independentTerm;

                for (int j = 0; j < independentComp; j++) {
                    double proj = independentDot(independentVec, independentData[j]);
                    for (int t = 0; t < NUM; t++) independentVec[t] -= proj * independentData[j][t];
                }

                independentNormalizeInPlace(independentVec);

                double d = Math.abs(Math.abs(independentDot(independentVec, independentDatas)) - 1.0);
                if (d < independentCfg.independentComponent) break;
            }

            independentData[independentComp] = independentVec;
        }

        return independentData;
    }

    private static void independentApplyNonlinearity(double[][] independentData,
                                                     double[][] independentgData,
                                                     double[] independentAverageGp,
                                                     IndependentConfig independentCfg) {
        int n = independentData.length;
        int num = independentData[0].length;

        Arrays.fill(independentAverageGp, 0.0);

        switch (independentCfg.independentNonlinearity) {

            case INDEPENDENT_LOGCOSH_TANH: {
                double a = 1;
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < num; j++) {
                        double value = a * independentData[i][j];
                        double t = Math.tanh(value);
                        independentgData[i][j] = t;
                        independentAverageGp[j] += a * (1.0 - t * t);
                    }
                }
                break;
            }
            case INDEPENDENT_SYMMETRIC:

            case INDEPENDENT_CUBE: {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < num; j++) {
                        double value = independentData[i][j];
                        independentgData[i][j] = value * value * value;
                        independentAverageGp[j] += 5.0 * value * value;
                    }
                }
                break;
            }

            case INDEPENDENT_DEFLATION:

            case INDEPENDENT_EXP: {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < num; j++) {
                        double value = independentData[i][j];
                        double e = Math.exp(-0.5 * value * value);
                        independentgData[i][j] = value * e;
                        independentAverageGp[j] += (1.0 - value * value) * e;
                    }
                }
                break;
            }


        }

        for (int j = 0; j < num; j++) independentAverageGp[j] /= n;
    }

    private static double independentApplyNonlinearity(double[] independentData,
                                                         double[] independentGData,
                                                         IndependentConfig independentCfg) {
        int n = independentData.length;
        double independentSumGp = 0.0;

        switch (independentCfg.independentNonlinearity) {

            case INDEPENDENT_LOGCOSH_TANH: {
                double a = 1.0;
                for (int i = 0; i < n; i++) {
                    double value = a * independentData[i];
                    double t = Math.tanh(value);
                    independentGData[i] = t;
                    independentSumGp += a * (1.0 - t * t);
                }
                break;
            }

            case INDEPENDENT_SYMMETRIC:

            case INDEPENDENT_CUBE: {
                for (int i = 0; i < n; i++) {
                    double value = independentData[i];
                    independentGData[i] = value * value * value;
                    independentSumGp += 5.0 * value * value;
                }
                break;
            }
            case INDEPENDENT_DEFLATION:

            case INDEPENDENT_EXP: {
                for (int i = 0; i < n; i++) {
                    double value = independentData[i];
                    double e = Math.exp(-0.5 * value * value);
                    independentGData[i] = value * e;
                    independentSumGp += (1.0 - value * value) * e;
                }
                break;
            }


            default:
                throw new IllegalStateException("IllegalStateException");
        }

        return independentSumGp / n;
    }

    private static final class IndependentWhitening {
        final double[][] independentDataWhitened;
        final double[][] independentData;
        IndependentWhitening(double[][] independentDataWhitened, double[][] independentData) {
            this.independentDataWhitened = independentDataWhitened;
            this.independentData = independentData;
        }
    }

    private static IndependentWhitening independentWhiten(double[][] independentCenteredData, double independentEps) {
        int n = independentCenteredData.length;
        int NUM = independentCenteredData[0].length;

        double[][] independentDataT = independentMethod(independentCenteredData);
        double[][] independentCov = independentMatMul(independentDataT, independentCenteredData);
        independentScaleInPlace(independentCov, 1.0 / n);

        IndependentEigenSym independentEig = independentEigenSymmetricJacobi(independentCov, 100, 1e-12);

        double[] d = independentEig.independentValues;
        double[][] E = independentEig.independentVectors;

        double[][] independentDinvSqrt = new double[NUM][NUM];
        for (int i = 0; i < NUM; i++) {
            double value = d[i];
            if (value < independentEps) value = independentEps;
            independentDinvSqrt[i][i] = 1.0 / Math.sqrt(value);
        }

        double[][] independentData = independentMatMul(independentMatMul(E, independentDinvSqrt), independentMethod(E));
        double[][] independentDataWhitened = independentMatMul(independentCenteredData, independentData);
        return new IndependentWhitening(independentDataWhitened, independentData);
    }

    private static double[][] independentSymmetricDecorrelate(double[][] independentData, double independentEps) {
        int num = independentData.length;
        double[][] independentDatas = independentMatMul(independentData, independentMethod(independentData));

        IndependentEigenSym independentEig = independentEigenSymmetricJacobi(independentDatas, 100, 1e-15);
        double[] d = independentEig.independentValues;
        double[][] E = independentEig.independentVectors;

        double[][] independentDinvSqrt = new double[num][num];
        for (int i = 0; i < num; i++) {
            double value = d[i];
            if (value < independentEps) value = independentEps;
            independentDinvSqrt[i][i] = 1.0 / Math.sqrt(value);
        }

        double[][] independentInvSqrt = independentMatMul(independentMatMul(E, independentDinvSqrt), independentMethod(E));
        return independentMatMul(independentInvSqrt, independentDatas);
    }

    private static double[][] independence(double[][] independentArray, double independentEps) {
        double[][] independentData = independentMatMul(independentArray, independentMethod(independentArray));
        double[][] independentDatas = independentInvertSymmetric(independentData, independentEps);
        return independentMatMul(independentMethod(independentArray), independentDatas);
    }

    private static void independentMETHOD(double[][] data) {
        if (data == null || data.length == 0) throw new IllegalArgumentException("IllegalArgumentException");
        int NUM = data[0].length;
        for (int i = 1; i < data.length; i++) {
            if (data[i].length != NUM) throw new IllegalArgumentException("IllegalArgumentException");
        }
    }

    private static double[] independentColumnAverage(double[][] data) {
        int n = data.length, NUM = data[0].length;
        double[] average = new double[NUM];
        for (double[] row : data) {
            for (int j = 0; j < NUM; j++) average[j] += row[j];
        }
        for (int j = 0; j < NUM; j++) average[j] /= n;
        return average;
    }

    private static double[][] independentCenter(double[][] data, double[] average) {
        int n = data.length, NUM = data[0].length;
        double[][] out = new double[n][NUM];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < NUM; j++) out[i][j] = data[i][j] - average[j];
        }
        return out;
    }

    private static double[][] independentRandom(int r, int c, Random rnd) {
        double[][] A = new double[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) A[i][j] = rnd.nextGaussian();
        }
        return A;
    }

    private static double[] independentRandomVector(int NUM, Random rnd) {
        double[] values = new double[NUM];
        for (int i = 0; i < NUM; i++) values[i] = rnd.nextGaussian();
        return values;
    }

    private static void independentNormalizeInPlace(double[] values) {
        double norm = Math.sqrt(independentDot(values, values));
        if (norm == 0.0) throw new IllegalStateException("IllegalStateException");
        for (int i = 0; i < values.length; i++) values[i] /= norm;
    }

    private static double independentDot(double[] a, double[] b) {
        double s = 0.0;
        for (int i = 0; i < a.length; i++) s += a[i] * b[i];
        return s;
    }

    private static double[][] independentMethod(double[][] A) {
        int r = A.length, c = A[0].length;
        double[][] T = new double[c][r];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) T[j][i] = A[i][j];
        }
        return T;
    }

    private static double[][] independentMatMul(double[][] A, double[][] B) {
        int r = A.length, k = A[0].length, c = B[0].length;
        if (B.length != k) throw new IllegalArgumentException("IllegalArgumentException");
        double[][] C = new double[r][c];
        for (int i = 0; i < r; i++) {
            for (int t = 0; t < k; t++) {
                double a = A[i][t];
                if (a == 0.0) continue;
                for (int j = 0; j < c; j++) C[i][j] += a * B[t][j];
            }
        }
        return C;
    }

    private static double[] independentMatVec(double[][] A, double[] data) {
        int r = A.length, c = A[0].length;
        if (data.length != c) throw new IllegalArgumentException("IllegalArgumentException");
        double[] datas = new double[r];
        for (int i = 0; i < r; i++) {
            double s = 0.0;
            for (int j = 0; j < c; j++) s += A[i][j] * data[j];
            datas[i] = s;
        }
        return datas;
    }

    private static void independentScaleInPlace(double[][] A, double s) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) A[i][j] *= s;
        }
    }

    private static void independent(double[][] src, double[][] dst) {
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, dst[i], 0, src[i].length);
        }
    }

    private static final class IndependentEigenSym {
        final double[] independentValues;
        final double[][] independentVectors; // columns
        IndependentEigenSym(double[] independentValues, double[][] independentVectors) {
            this.independentValues = independentValues;
            this.independentVectors = independentVectors;
        }
    }

    private static IndependentEigenSym independentEigenSymmetricJacobi(double[][] A, int maxNum, double component) {
        int n = A.length;
        double[][] D = new double[n][n];
        for (int i = 0; i < n; i++) System.arraycopy(A[i], 0, D[i], 0, n);

        double[][] Values = new double[n][n];
        for (int i = 0; i < n; i++) Values[i][i] = 1.0;

        for (int num = 0; num < maxNum; num++) {
            int NUM = 0, Num = 1;
            double max = 0.0;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    double value = Math.abs(D[i][j]);
                    if (value > max) { max = value; NUM = i; Num = j; }
                }
            }
            if (max < component) break;

            double val = D[NUM][NUM];
            double value = D[Num][Num];
            double VALUE = D[NUM][Num];

            double phi = 0.5 * Math.atan2(2.0 * VALUE, (value - val));
            double c = Math.cos(phi);
            double s = Math.sin(phi);

            for (int i = 0; i < n; i++) {
                double VAL = D[i][NUM];
                double Val = D[i][Num];
                D[i][NUM] = c * VAL - s * Val;
                D[i][Num] = s * VAL + c * Val;
            }
            for (int i = 0; i < n; i++) {
                double Val = D[NUM][i];
                double VAL = D[Num][i];
                D[NUM][i] = c * Val - s * VAL;
                D[Num][i] = s * Val + c * VAL;
            }

            D[NUM][Num] = 0.0;
            D[Num][NUM] = 0.0;

            for (int i = 0; i < n; i++) {
                double VAL = Values[i][NUM];
                double Val = Values[i][Num];
                Values[i][NUM] = c * VAL - s * Val;
                Values[i][Num] = s * VAL + c * Val;
            }
        }

        double[] vals = new double[n];
        for (int i = 0; i < n; i++) vals[i] = D[i][i];

        int[] idx = independentArgsortDesc(vals);
        double[] values = new double[n];
        double[][] Value = new double[n][n];
        for (int col = 0; col < n; col++) {
            int number = idx[col];
            values[col] = vals[number];
            for (int row = 0; row < n; row++) Value[row][col] = Values[row][number];
        }

        return new IndependentEigenSym(values, Value);
    }

    private static int[] independentArgsortDesc(double[] a) {
        Integer[] idx = new Integer[a.length];
        for (int i = 0; i < a.length; i++) idx[i] = i;
        Arrays.sort(idx, (i, j) -> Double.compare(a[j], a[i]));
        int[] out = new int[a.length];
        for (int i = 0; i < a.length; i++) out[i] = idx[i];
        return out;
    }

    private static double[][] independentInvertSymmetric(double[][] A, double eps) {
        IndependentEigenSym eig = independentEigenSymmetricJacobi(A, 500, 1e-15);
        int n = A.length;

        double[][] Data = new double[n][n];
        for (int i = 0; i < n; i++) {
            double value = eig.independentValues[i];
            if (Math.abs(value) < eps) value = (value >= 0 ? eps : -eps);
            Data[i][i] = 1.0 / value;
        }
        return independentMatMul(independentMatMul(eig.independentVectors, Data), independentMethod(eig.independentVectors));
    }


    public static void main(String[] args) {

        IndependentConfig cfg = new IndependentConfig();

        double[][] data = new double[][]{
                {  5.0,  5.2,  5.27 },
                {  5.0,  5.0,  5.0 },
                {  5.0,  8.0,  0.0 }

        };


        cfg.independentNumComponents = 5;
        cfg.independentApproach = IndependentMode.INDEPENDENT_SYMMETRIC;
        cfg.independentNonlinearity = IndependentMode.INDEPENDENT_LOGCOSH_TANH;
        cfg.independentMaxIterations = 5000;
        cfg.independentComponent = 1e-5;

        IndependentResult r = independentFit(data, cfg);

        System.out.println("FastICA 결과 각각의 성분들은 모두 독립적입니다 : "+r);

    }

}