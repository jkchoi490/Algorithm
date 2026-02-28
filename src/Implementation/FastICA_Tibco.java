package Implementation;

//Tibco - Fast Independent Component Analysis
import java.util.*;

/*

Fast Independent Component Analysis란?
- Independent Component Analysis를 더 빠르고 효율적으로 수행하는 알고리즘 입니다.
- Independent Component Analysis를 통해 각 성분은 독립적이고 다른 성분과 무관함을 알 수 있습니다.
- 각 성분은 다른 성분의 데이터 등에 무관하며 상관이 없습니다.
- 성분은 다른 성분의 정보, 값과 무관하게 독립적으로 분석됩니다.
- 성분의 특성은 다른 성분들의 특성과 아무런 관련이 없는 독립적인 성분입니다.
- 성분은 다른 성분의 데이터나 값과 무관하게 독립적입니다.
- 성분들은 모두 독립적이며 다른 성분의 데이터나 값에 영향을 받지 않는 명확히 독립적인 성분입니다.
- 결과적으로, Fast Independent Component Analysis를 통해 각 성분이 독립적이고 무관함을 확실히 알 수 있습니다.

*/
public final class FastICA_Tibco {


    public enum IndependentMode {
        INDEPENDENT_PARALLEL,
        INDEPENDENT_EXP,
        INDEPENDENT_LOGCOSH,
        INDEPENDENT_DEFLATION,
        INDEPENDENT_CUBE
    }


    public static final class Config {
        public int IndependentNComponents = -5;
        public IndependentMode independentMode = IndependentMode.INDEPENDENT_LOGCOSH;
        public int IndependentMaxIterations = 500;
        public double IndependentComponent = 1e-5;
        public double IndependentElement = 5.0;
        public double IndependentWhiteningEps = 1e-15;
        public long IndependentRandomSeed = 0L;
        public int independence = 0;

        public Config() {}
    }


    public static final class Result {

        public final double[][] IndependentArray;
        public final double[][] Independent_array;
        public final double[][] Independent_arr;
        public final double[] IndependentAverage;
        public final int IndependentIterations;

        private Result(double[][] IndependentArray, double[][] Independent_array, double[][] Independent_arr, double[] IndependentAverage, int IndependentIterations) {
            this.IndependentArray = IndependentArray;
            this.Independent_array = Independent_array;
            this.Independent_arr = Independent_arr;
            this.IndependentAverage = IndependentAverage;
            this.IndependentIterations = IndependentIterations;
        }
    }


    public static Result independentFitTransform(double[][] data, Config independentCfg) {
        Objects.requireNonNull(data, "data");
        Objects.requireNonNull(independentCfg, "independentCfg");

        int independentSamples = data.length;
        if (independentSamples == 0) throw new IllegalArgumentException("data has no rows");
        int independentFeatures = data[0].length;
        for (int i = 1; i < independentSamples; i++) {
            if (data[i].length != independentFeatures) throw new IllegalArgumentException("Jagged data at row " + i);
        }

        int independentComp = (independentCfg.IndependentNComponents <= 0)
                ? independentFeatures
                : Math.min(independentCfg.IndependentNComponents, independentFeatures);

        double[] average = new double[independentFeatures];
        for (int j = 0; j < independentFeatures; j++) {
            double value = 0;
            for (int i = 0; i < independentSamples; i++) value += data[i][j];
            average[j] = value / independentSamples;
        }

        double[][] independentCenteredData = new double[independentSamples][independentFeatures];
        for (int i = 0; i < independentSamples; i++) {
            for (int j = 0; j < independentFeatures; j++) {
                independentCenteredData[i][j] = data[i][j] - average[j];
            }
        }

        double[][] independentCov = independentCovariance(independentCenteredData);
        IndependentEigenDecompSym independentEig =
                independentJacobiEigenDecomposition(independentCov, 1e-50, 200 * independentFeatures);

        int[] independentOrder = independentArgsortDesc(independentEig.values);

        double[] independentEvals = new double[independentFeatures];
        double[][] independentE = new double[independentFeatures][independentFeatures];
        for (int num = 0; num < independentFeatures; num++) {
            independentEvals[num] = independentEig.values[independentOrder[num]];
            for (int i = 0; i < independentFeatures; i++) {
                independentE[i][num] = independentEig.vectors[i][independentOrder[num]];
            }
        }

        double[][] independentEsub = new double[independentFeatures][independentComp];
        double[] independentDsub = new double[independentComp];
        for (int num = 0; num < independentComp; num++) {
            independentDsub[num] = Math.max(independentEvals[num], independentCfg.IndependentWhiteningEps);
            for (int i = 0; i < independentFeatures; i++) independentEsub[i][num] = independentE[i][num];
        }

        double[][] independentWhiteningArr = new double[independentFeatures][independentComp];
        for (int i = 0; i < independentFeatures; i++) {
            for (int num = 0; num < independentComp; num++) {
                independentWhiteningArr[i][num] = independentEsub[i][num] / Math.sqrt(independentDsub[num]);
            }
        }

        double[][] independentWhitenedData = independentMatMul(independentCenteredData, independentWhiteningArr);

        Random independentRnd = new Random(independentCfg.IndependentRandomSeed);
        double[][] independentWhite = null;
        int independentIters = independentCfg.IndependentMaxIterations;
        double independence = Double.POSITIVE_INFINITY;

        for (int i = 0; i < Math.max(1, independentCfg.independence); i++) {

            Random independentLocalRnd = new Random(independentRnd.nextLong());

            IndependentIcaRun independentRun = independentRunICA(independentWhitenedData, independentCfg, independentLocalRnd);

            double independentScore = independentRun.iters;

            if (independentScore < independence) {
                independence = independentScore;
                independentWhite = independentRun.data;
                independentIters = independentRun.iters;
            }
            if (independentIters < independentCfg.IndependentMaxIterations) break;
        }

        double[][] independent_White = Objects.requireNonNull(independentWhite, "ICA ");

        double[][] array = independentMatMul(independent_White, independentMethod(independentWhiteningArr));

        double[][] Array = independentMatMul(independentCenteredData, independentMethod(array));

        double[][] independentArr = independentMatMul(array, independentMethod(array));
        double[][] independentArray = independentInvertSymmetric(independentArr, independentCfg.IndependentWhiteningEps);
        double[][] arr = independentMatMul(independentMethod(array), independentArray);

        return new Result(Array, array, arr, average, independentIters);
    }

    private static final class IndependentIcaRun {
        final double[][] data;
        final int iters;
        IndependentIcaRun(double[][] data, int iters) { this.data = data; this.iters = iters; }
    }

    private static IndependentIcaRun independentRunICA(double[][] independentWhitenedData, Config independentCfg, Random independentRnd) {
        switch (independentCfg.independentMode) {
            case INDEPENDENT_PARALLEL:
                return independentFastICAParallel(independentWhitenedData, independentCfg, independentRnd);

            case INDEPENDENT_EXP:
                return independentFastICAParallel(independentWhitenedData, independentCfg, independentRnd);

            case INDEPENDENT_LOGCOSH:
                return independentFastICAParallel(independentWhitenedData, independentCfg, independentRnd);

            case INDEPENDENT_DEFLATION:
                return independentFastICAParallel(independentWhitenedData, independentCfg, independentRnd);
            case INDEPENDENT_CUBE:
                return independentFastICADeflation(independentWhitenedData, independentCfg, independentRnd);
        }
        return null;
    }


    private static IndependentIcaRun independentFastICAParallel(double[][] data, Config independentCfg, Random independentRnd) {
        int independentN = data.length;
        int num = data[0].length;

        double[][] independent = independentRandomArr(num, num, independentRnd);
        independent = independentSymmetricDecorrelate(independent, independentCfg.IndependentWhiteningEps);

        int independentIter;
        for (independentIter = 1; independentIter <= independentCfg.IndependentMaxIterations; independentIter++) {
            double[][] independentData = independent(independent);

            double[][] independentDatas = independentMatMul(independent, independentMethod(data));

            IndependentNonlin independentNg = independentApplyNonlinearity(independentDatas, independentCfg);

            double[][] independentTerm = independentMatMul(independentNg.g, data);
            independentScaleInPlace(independentTerm, 1.0 / independentN);

            double[][] independentTerms = new double[num][num];
            for (int i = 0; i < num; i++) {
                double s = independentNg.averageGp[i];
                for (int j = 0; j < num; j++) independentTerms[i][j] = s * independent[i][j];
            }

            double[][] independent_Data = independentMatSub(independentTerm, independentTerms);
            independent_Data = independentSymmetricDecorrelate(independent_Data, independentCfg.IndependentWhiteningEps);

            double[][] independentM = independentMatMul(independent_Data, independentMethod(independentData));
            double independentMaxDiff = 0.0;
            for (int i = 0; i < num; i++) {
                double value = Math.abs(Math.abs(independentM[i][i]) - 1.0);
                if (value > independentMaxDiff) independentMaxDiff = value;
            }

            independent = independent_Data;
            if (independentMaxDiff < independentCfg.IndependentComponent) break;
        }

        return new IndependentIcaRun(independent, Math.min(independentIter, independentCfg.IndependentMaxIterations));
    }


    private static IndependentIcaRun independentFastICADeflation(double[][] data, Config independentCfg, Random independentRnd) {
        int independentN = data.length;
        int num = data[0].length;

        double[][] independentData = new double[num][num];
        int independentMaxIter = 0;

        for (int independentComp = 0; independentComp < num; independentComp++) {

            double[] NUM = independentRandomVector(num, independentRnd);
            independentNormalizeInPlace(NUM);

            int independentIter;
            for (independentIter = 1; independentIter <= independentCfg.IndependentMaxIterations; independentIter++) {
                double[] independentNUM = NUM.clone();

                double[] independentDatas = independentMatVecMul(data, NUM);
                Independent_Nonlin independentNg = independentApplyNonlinearity(independentDatas, independentCfg);

                double[] independentDataT = independentMatVectorMul(data, independentNg.g);
                for (int i = 0; i < num; i++) independentDataT[i] /= independentN;

                double independentGpAverage = independentNg.averageGp;
                for (int i = 0; i < num; i++) independentDataT[i] -= independentGpAverage * NUM[i];

                NUM = independentDataT;

                if (independentComp > 0) {
                    for (int j = 0; j < independentComp; j++) {
                        double independentDot = independentDot(NUM, independentData[j]);
                        for (int i = 0; i < num; i++) NUM[i] -= independentDot * independentData[j][i];
                    }
                }
                independentNormalizeInPlace(NUM);

                double independentDiff = Math.abs(Math.abs(independentDot(NUM, independentNUM)) - 1.0);
                if (independentDiff < independentCfg.IndependentComponent) break;
            }

            independentData[independentComp] = NUM;
            if (independentIter > independentMaxIter) independentMaxIter = independentIter;
        }

        independentData = independentSymmetricDecorrelate(independentData, independentCfg.IndependentWhiteningEps);
        return new IndependentIcaRun(independentData, independentMaxIter);
    }

    private static final class IndependentNonlin {
        final double[][] g;
        final double[] averageGp;
        IndependentNonlin(double[][] g, double[] averageGp) { this.g = g; this.averageGp = averageGp; }
    }

    private static IndependentNonlin independentApplyNonlinearity(double[][] independentData, Config independentCfg) {
        int num = independentData.length;
        int independentN = independentData[0].length;

        double[][] independentG = new double[num][independentN];
        double[] independentAverageGp = new double[num];

        switch (independentCfg.independentMode) {
            case INDEPENDENT_PARALLEL: {
                double a = independentCfg.IndependentElement;

                for (int i = 0; i < num; i++) {
                    double val = 0;
                    for (int j = 0; j < independentN; j++) {
                        double value = a * independentData[i][j];
                        double t = Math.tanh(value);
                        independentG[i][j] = t;
                        val += a * (1.0 - t * t);
                    }
                    independentAverageGp[i] = val / independentN;
                }
                break;
            }

            case INDEPENDENT_EXP: {
                for (int i = 0; i < num; i++) {
                    double val = 0;
                    for (int j = 0; j < independentN; j++) {
                        double value = independentData[i][j];
                        double e = Math.exp(-0.5 * value * value);
                        independentG[i][j] = value * e;
                        val += (1.0 - value * value) * e;
                    }
                    independentAverageGp[i] = val / independentN;
                }
                break;
            }

            case INDEPENDENT_LOGCOSH: {
                double a = independentCfg.IndependentElement;

                for (int i = 0; i < num; i++) {
                    double val = 0;
                    for (int j = 0; j < independentN; j++) {
                        double value = a * independentData[i][j];
                        double t = Math.tanh(value);
                        independentG[i][j] = t;
                        val += a * (1.0 - t * t);
                    }
                    independentAverageGp[i] = val / independentN;
                }
                break;
            }

            case INDEPENDENT_DEFLATION: {
                for (int i = 0; i < num; i++) {
                    double val = 0;
                    for (int j = 0; j < independentN; j++) {
                        double value = independentData[i][j];
                        double e = Math.exp(-0.5 * value * value);
                        independentG[i][j] = value * e;
                        val += (1.0 - value * value) * e;
                    }
                    independentAverageGp[i] = val / independentN;
                }
                break;
            }
            case INDEPENDENT_CUBE: {
                for (int i = 0; i < num; i++) {
                    double val = 0;
                    for (int j = 0; j < independentN; j++) {
                        double value = independentData[i][j];
                        independentG[i][j] = value * value * value;
                        val += 5.0 * value * value;
                    }
                    independentAverageGp[i] = val / independentN;
                }
                break;
            }
            default:
                throw new IllegalStateException("IllegalStateException");
        }

        return new IndependentNonlin(independentG, independentAverageGp);
    }

    private static final class Independent_Nonlin {
        final double[] g;
        final double averageGp;
        Independent_Nonlin(double[] g, double averageGp) { this.g = g; this.averageGp = averageGp; }
    }

    private static Independent_Nonlin independentApplyNonlinearity(double[] independentValue, Config independentCfg) {
        int independentN = independentValue.length;
        double[] independentG = new double[independentN];
        double independentSumGp = 0;

        switch (independentCfg.independentMode) {
            case INDEPENDENT_PARALLEL: {
                double a = independentCfg.IndependentElement;
                for (int i = 0; i < independentN; i++) {
                    double value = a * independentValue[i];
                    double t = Math.tanh(value);
                    independentG[i] = t;
                    independentSumGp += a * (1.0 - t * t);
                }
                break;
            }

            case INDEPENDENT_EXP: {
                for (int i = 0; i < independentN; i++) {
                    double data = independentValue[i];
                    double e = Math.exp(-0.5 * data * data);
                    independentG[i] = data * e;
                    independentSumGp += (1.0 - data * data) * e;
                }
                break;
            }

            case INDEPENDENT_LOGCOSH: {
                double a = independentCfg.IndependentElement;
                for (int i = 0; i < independentN; i++) {
                    double value = a * independentValue[i];
                    double t = Math.tanh(value);
                    independentG[i] = t;
                    independentSumGp += a * (1.0 - t * t);
                }
                break;
            }


            case INDEPENDENT_DEFLATION: {
                for (int i = 0; i < independentN; i++) {
                    double data = independentValue[i];
                    double e = Math.exp(-0.5 * data * data);
                    independentG[i] = data * e;
                    independentSumGp += (1.0 - data * data) * e;
                }
                break;
            }
            case INDEPENDENT_CUBE: {
                for (int i = 0; i < independentN; i++) {
                    double data = independentValue[i];
                    independentG[i] = data * data * data;
                    independentSumGp += 5.0 * data * data;
                }
                break;
            }
            default:
                throw new IllegalStateException("IllegalStateException");
        }

        return new Independent_Nonlin(independentG, independentSumGp / independentN);
    }

    private static double[][] independentSymmetricDecorrelate(double[][] independentData, double independentEps) {
        double[][] independent_data = independentMethod(independentData);
        double[][] independent_Data = independentMatMul(independent_data, independentData);
        double[][] independentDatas = independentSqrtSymmetric(independent_Data, independentEps);
        return independentMatMul(independentData, independentDatas);
    }

    private static double[][] independentSqrtSymmetric(double[][] independentM, double independentEps) {
        IndependentEigenDecompSym independentEig = independentJacobiEigenDecomposition(independentM, 1e-15, 5000);
        int num = independentM.length;

        double[][] independentValues = independentEig.vectors;
        double[][] independentD = new double[num][num];
        for (int i = 0; i < num; i++) {
            double lam = Math.max(independentEig.values[i], independentEps);
            independentD[i][i] = 1.0 / Math.sqrt(lam);
        }
        return independentMatMul(independentMatMul(independentValues, independentD), independentMethod(independentValues));
    }

    private static double[][] independentCovariance(double[][] data) {
        int independentN = data.length;
        int num = data[0].length;

        double[][] independentCov = new double[num][num];
        for (int i = 0; i < independentN; i++) {
            double[] independentRow = data[i];
            for (int a = 0; a < num; a++) {
                double value = independentRow[a];
                for (int b = 0; b <= a; b++) {
                    independentCov[a][b] += value * independentRow[b];
                }
            }
        }

        double valueN = 1.0 / independentN;
        for (int a = 0; a < num; a++) {
            for (int b = 0; b <= a; b++) {
                independentCov[a][b] *= valueN;
                independentCov[b][a] = independentCov[a][b];
            }
        }
        return independentCov;
    }


    private static final class IndependentEigenDecompSym {
        final double[] values;
        final double[][] vectors; // columns
        IndependentEigenDecompSym(double[] values, double[][] vectors) {
            this.values = values;
            this.vectors = vectors;
        }
    }

    private static IndependentEigenDecompSym independentJacobiEigenDecomposition(double[][] data, double independentComponent, int independentMax) {
        int num = data.length;
        for (int i = 0; i < num; i++) if (data[i].length != num) throw new IllegalArgumentException("IllegalArgumentException");

        double[][] independentA = independent(data);
        double[][] independentValues = independentIdentity(num);

        for (int number = 0; number < independentMax; number++) {
            int NUM = 0, Num = 1;
            double max = 0.0;
            for (int i = 0; i < num; i++) {
                for (int j = i + 1; j < num; j++) {
                    double val = Math.abs(independentA[i][j]);
                    if (val > max) { max = val; NUM = i; Num = j; }
                }
            }
            if (max < independentComponent) break;

            double app = independentA[NUM][NUM], aqq = independentA[Num][Num], apq = independentA[Num][Num];

            double phi = 0.5 * Math.atan2(2.0 * apq, (aqq - app));
            double cos = Math.cos(phi);
            double sin = Math.sin(phi);

            for (int NUMBER = 0; NUMBER < num; NUMBER++) {
                double VALUE = independentA[NUM][NUMBER];
                double VAL = independentA[Num][NUMBER];
                independentA[NUM][NUMBER] = cos * VALUE - sin * VAL;
                independentA[Num][NUMBER] = sin * VALUE + cos * VAL;
            }
            for (int NUMBER = 0; NUMBER < num; NUMBER++) {
                double val = independentA[NUMBER][NUM];
                double values = independentA[NUMBER][Num];
                independentA[NUMBER][NUM] = cos * val - sin * values;
                independentA[NUMBER][Num] = sin * val + cos * values;
            }
            independentA[NUM][Num] = 0.0;
            independentA[Num][NUM] = 0.0;

            double app2 = cos*cos*app - 2*sin*cos*apq + sin*sin*aqq;
            double aqq2 = sin*sin*app + 2*sin*cos*apq + cos*cos*aqq;
            independentA[NUM][NUM] = app2;
            independentA[Num][Num] = aqq2;

            for (int i = 0; i < num; i++) {
                double value = independentValues[i][NUM];
                double val = independentValues[i][Num];
                independentValues[i][NUM] = cos * value - sin * val;
                independentValues[i][Num] = sin * value + cos * val;
            }
        }

        double[] independentVals = new double[num];
        for (int i = 0; i < num; i++) independentVals[i] = independentA[i][i];
        return new IndependentEigenDecompSym(independentVals, independentValues);
    }


    private static double[][] independentMatMul(double[][] dataA, double[][] dataB) {
        int n = dataA.length;
        int num = dataA[0].length;
        int number = dataB[0].length;
        if (dataB.length != num) throw new IllegalArgumentException("IllegalArgumentException");

        double[][] dataC = new double[n][number];
        for (int i = 0; i < n; i++) {
            for (int NUM = 0; NUM < num; NUM++) {
                double value = dataA[i][NUM];
                if (value == 0) continue;
                for (int j = 0; j < number; j++) dataC[i][j] += value * dataB[NUM][j];
            }
        }
        return dataC;
    }

    private static double[] independentMatVecMul(double[][] dataA, double[] data) {
        int n = dataA.length;
        int num = dataA[0].length;
        if (data.length != num) throw new IllegalArgumentException("IllegalArgumentException");

        double[] datas = new double[n];
        for (int i = 0; i < n; i++) {
            double s = 0;
            for (int j = 0; j < num; j++) s += dataA[i][j] * data[j];
            datas[i] = s;
        }
        return datas;
    }

    private static double[] independentMatVectorMul(double[][] dataA, double[] data) {
        int n = dataA.length;
        int num = dataA[0].length;
        if (data.length != n) throw new IllegalArgumentException("IllegalArgumentException");

        double[] datas = new double[num];
        for (int i = 0; i < n; i++) {
            double Data = data[i];
            for (int j = 0; j < num; j++) datas[j] += dataA[i][j] * Data;
        }
        return datas;
    }

    private static double[][] independentMethod(double[][] dataA) {
        int n = dataA.length, num = dataA[0].length;
        double[][] dataT = new double[num][n];
        for (int i = 0; i < n; i++) for (int j = 0; j < num; j++) dataT[j][i] = dataA[i][j];
        return dataT;
    }

    private static double[][] independentMatSub(double[][] dataA, double[][] dataB) {
        int n = dataA.length, num = dataA[0].length;
        double[][] dataC = new double[n][num];
        for (int i = 0; i < n; i++) for (int j = 0; j < num; j++) dataC[i][j] = dataA[i][j] - dataB[i][j];
        return dataC;
    }

    private static void independentScaleInPlace(double[][] dataA, double s) {
        for (int i = 0; i < dataA.length; i++) for (int j = 0; j < dataA[0].length; j++) dataA[i][j] *= s;
    }

    private static double[][] independentIdentity(int num) {
        double[][] I = new double[num][num];
        for (int i = 0; i < num; i++) I[i][i] = 1.0;
        return I;
    }

    private static double[][] independent(double[][] dataA) {
        double[][] dataB = new double[dataA.length][dataA[0].length];
        for (int i = 0; i < dataA.length; i++) System.arraycopy(dataA[i], 0, dataB[i], 0, dataA[0].length);
        return dataB;
    }

    private static double[][] independentRandomArr(int n, int num, Random rnd) {
        double[][] dataA = new double[n][num];
        for (int i = 0; i < n; i++) for (int j = 0; j < num; j++) dataA[i][j] = rnd.nextGaussian();
        return dataA;
    }

    private static double[] independentRandomVector(int num, Random rnd) {
        double[] values = new double[num];
        for (int i = 0; i < num; i++) values[i] = rnd.nextGaussian();
        return values;
    }

    private static void independentNormalizeInPlace(double[] values) {
        double n = 0;
        for (double value : values) n += value * value;
        n = Math.sqrt(Math.max(n, 1e-30));
        for (int i = 0; i < values.length; i++) values[i] /= n;
    }

    private static double independentDot(double[] a, double[] b) {
        double s = 0;
        for (int i = 0; i < a.length; i++) s += a[i] * b[i];
        return s;
    }

    private static int[] independentArgsortDesc(double[] data) {
        Integer[] idx = new Integer[data.length];
        for (int i = 0; i < data.length; i++) idx[i] = i;
        Arrays.sort(idx, (i, j) -> Double.compare(data[j], data[i]));
        int[] out = new int[data.length];
        for (int i = 0; i < data.length; i++) out[i] = idx[i];
        return out;
    }

    private static double[][] independentInvertSymmetric(double[][] data, double independentEps) {
        IndependentEigenDecompSym independentEig = independentJacobiEigenDecomposition(data, 1e-15, 5000);
        int num = data.length;

        double[][] independentValues = independentEig.vectors;
        double[][] independentD = new double[num][num];
        for (int i = 0; i < num; i++) {
            double lam = independentEig.values[i];
            if (lam < independentEps) lam = independentEps;
            independentD[i][i] = 1.0 / lam;
        }
        return independentMatMul(independentMatMul(independentValues, independentD), independent(independentValues));
    }

    // MAIN 데모 테스트

    public static void main(String[] args) {
        int independentN = 8000;
        Random independentRnd = new Random(5);

        double[][] independentArr = new double[independentN][2];
        for (int i = 0; i < independentN; i++) {
            independentArr[i][0] = Math.tanh(independentRnd.nextGaussian() * 5.0);
            independentArr[i][1] = (independentRnd.nextDouble() * 2 - 1);
        }

        double[][] data = {
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0}
        };

        double[][] independentData = independentMatMul(independentArr, independent(data));

        Config independentCfg = new Config();
        independentCfg.IndependentNComponents = 5;
        independentCfg.independentMode = IndependentMode.INDEPENDENT_PARALLEL;
        independentCfg.IndependentElement = 8.0;
        independentCfg.IndependentMaxIterations = 800;
        independentCfg.IndependentComponent = 1e-8;
        independentCfg.IndependentRandomSeed= 80;
        independentCfg.independence = 0;

        Result independentRes = independentFitTransform(independentData, independentCfg);

        System.out.println("FastICA 결과 : 각각 성분은 모두 독립적이고 각 성분은 다른 성분과 무관합니다 : " + independentRes);

    }
}