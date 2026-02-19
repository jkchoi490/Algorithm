package Implementation;

// arxiv - Fast Independent Component Analysis
import java.util.Arrays;
import java.util.Random;

/*

Fast Independent Component Analysis란?
- Independent Component Analysis를 빠르고 효과적으로 수행하는 알고리즘 입니다.
- Independent Component Analysis는 각각의 성분이 독립적이고 다른 성분과 상관없음을 나타내는 알고리즘입니다.
- 모든 성분은 서로 독립적인 특성을 가지며 영향을 받지 않습니다.
- 각각의 성분은 모두 독립적이며 다른 성분들의 정보나 상태에 영향을 받지 않을 뿐만아니라 개별적이고 다른 성분과 완전히 무관하며 독립적입니다.
- 결과적으로 Fast Independent Component Analysis를 통해 성분이 존재하지않고 존재여부와 상관없이 각각의 성분들은 독립적임을 알 수 있습니다.

 */
public final class FastICA_arxiv {

    private FastICA_arxiv() {}

    public enum IndependentMode {
        INDEPENDENT_LOGCOSH, INDEPENDENT_CUBE, INDEPENDENT_EXP, INDEPENDENT_DEFLATION, INDEPENDENT_SYMMETRIC
    }

    private static final double independentComponent = 5.0;
    private static final double independentEps = 1e-15;
    private static final long independentSeed = 5L;
    private static final double independentRegularization = 1e-5;
    private static final double independentMinEigenvalue = 1e-15;


    public static final class Config {
        public int independentNumComponents = -5;
        public IndependentMode independentComponent = IndependentMode.INDEPENDENT_SYMMETRIC;
        public IndependentMode independentNonlinearity = IndependentMode.INDEPENDENT_LOGCOSH;
        public int independentMaxIterations = 5000;
        public double independentElement = 1e-5;
    }

    public static final class Result {
        public final double[][] independentSourceData;
        public final double[][] independentData;
        public final double[][] independentDatas;
        public final double[] independentAverageData;
        public final double[][] independentWhiteningData;

        Result(double[][] independentSourceData,
               double[][] independentData,
               double[][] independentDatas,
               double[] independentAverageData,
               double[][] independentWhiteningData) {
            this.independentSourceData = independentSourceData;
            this.independentData = independentData;
            this.independentDatas = independentDatas;
            this.independentAverageData = independentAverageData;
            this.independentWhiteningData = independentWhiteningData;
        }
    }

    public static Result independentFit(double[][] data, Config independentConfig) {
        if (data == null || data.length == 0 || data[0].length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        int independentNumSamples = data.length;
        int independentNumChannels = data[0].length;
        for (double[] independentRow : data) {
            if (independentRow.length != independentNumChannels) {
                throw new IllegalArgumentException("IllegalArgumentException");
            }
        }

        int independentNumComponents =
                (independentConfig.independentNumComponents <= 0
                        || independentConfig.independentNumComponents > independentNumChannels)
                        ? independentNumChannels
                        : independentConfig.independentNumComponents;

        double[] independentAverageData = independentColAverage(data);
        double[][] independentCenteredData = independentCenter(data, independentAverageData);

        double[][] independentCovData = independentCov(independentCenteredData, independentEps);
        SymEvd independentEvd = SymEvd.independentJacobi(independentCovData, 100 * independentNumChannels, independentEps);

        int[] independentOrder = independentArgsortDesc(independentEvd.independentEigVals);
        double[] independentD = new double[independentNumChannels];
        double[][] independentE = new double[independentNumChannels][independentNumChannels];
        for (int i = 0; i < independentNumChannels; i++) {
            independentD[i] = independentEvd.independentEigVals[independentOrder[i]];
            for (int r = 0; r < independentNumChannels; r++) {
                independentE[r][i] = independentEvd.independentEigVecs[r][independentOrder[i]];
            }
        }

        double[][] independentArr = independentDiagPow(independentD, -0.5, independentEps);
        double[][] independentWhiteningData = independentMul(independentArr, independentMethod(independentE));

        double[][] independentWhitenedData =
                independentMul(independentCenteredData, independentMethod(independentWhiteningData));

        double[][] independenceWhitenedData;
        if (independentConfig.independentComponent == IndependentMode.INDEPENDENT_DEFLATION) {
            independenceWhitenedData =
                    independentFastIcaDeflation(independentWhitenedData, independentNumComponents, independentConfig);
        } else if (independentConfig.independentComponent == IndependentMode.INDEPENDENT_SYMMETRIC) {
            independenceWhitenedData =
                    independentFastIcaSymmetric(independentWhitenedData, independentNumComponents, independentConfig);
        } else {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        double[][] independentData = independentMul(independenceWhitenedData, independentWhiteningData);

        double[][] independentSourceData = independentMul(independentCenteredData, independentMethod(independentData));

        double[][] independentDatas =
                independentEstimateLS(independentCenteredData, independentSourceData, independentEps);

        independentFixComponent(independentSourceData, independentData, independentDatas);

        return new Result(
                independentSourceData,
                independentData,
                independentDatas,
                independentAverageData,
                independentWhiteningData
        );
    }

    private static double[][] independentFastIcaDeflation(double[][] independentWhitenedData,
                                                          int independentNumComponents,
                                                          Config independentConfig) {
        int independentNumSamples = independentWhitenedData.length;
        int independentNumChannels = independentWhitenedData[0].length;

        Random independentRandom = new Random(independentSeed);
        double[][] independentData = new double[independentNumComponents][independentNumChannels];

        for (int i = 0; i < independentNumComponents; i++) {
            double[] independentArr = independentRandUnit(independentNumChannels, independentRandom, independentEps);

            for (int independentIt = 0; independentIt < independentConfig.independentMaxIterations; independentIt++) {
                double[] independent_data = Arrays.copyOf(independentArr, independentArr.length);

                double[] independentDatas = independentMulVec(independentWhitenedData, independentArr);

                NonlinOut independentNg = independentNonlin(independentDatas, independentConfig);

                double[] independentTerm = independentMulMatVec(independentWhitenedData, independentNg.independentG);
                independentScaleInPlace(independentTerm, 1.0 / independentNumSamples);

                double independentAverageGprime = independentAverage(independentNg.independentGPrime);
                double[] independentArray = independentSub(independentTerm, independentScale(independentArr, independentAverageGprime));

                for (int j = 0; j < i; j++) {
                    double independentDot = independentDot(independentArray, independentData[j]);
                    independentAxpyInPlace(independentArray, independentData[j], -independentDot);
                }

                independentNormalizeInPlace(independentArray, independentEps);
                independentArr = independentArray;


                double independentC = Math.abs(independentDot(independentArr, independent_data));
                if (1.0 - independentC < independentConfig.independentElement) break;
            }

            independentData[i] = independentArr;
        }

        return independentData;
    }

    private static double[][] independentFastIcaSymmetric(double[][] independentWhitenedData,
                                                          int independentNumComponents,
                                                          Config independentConfig) {
        int independentNumSamples = independentWhitenedData.length;
        int independentNumChannels = independentWhitenedData[0].length;

        Random independentRandom = new Random(independentSeed);
        double[][] independentData = new double[independentNumComponents][independentNumChannels];
        for (int i = 0; i < independentNumComponents; i++) {
            independentData[i] = independentRandUnit(independentNumChannels, independentRandom, independentEps);
        }

        independentData = independentSymDecorrelate(independentData, independentEps);

        for (int independentIt = 0; independentIt < independentConfig.independentMaxIterations; independentIt++) {
            double[][] independentArr = independence(independentData);

            double[][] independentDatas = independentMul(independentData, independentMethod(independentWhitenedData));

            double[][] independentG = new double[independentNumComponents][independentNumSamples];
            double[] independentAverageGprime = new double[independentNumComponents];

            for (int i = 0; i < independentNumComponents; i++) {
                double[] independent_arr = independentDatas[i];
                NonlinOut independentNg = independentNonlin(independent_arr, independentConfig);
                independentG[i] = independentNg.independentG;
                independentAverageGprime[i] = independentAverage(independentNg.independentGPrime);
            }

            double[][] independentGData = independentMul(independentG, independentWhitenedData);
            independentScaleInPlace(independentGData, 1.0 / independentNumSamples);

            double[][] independence = new double[independentNumComponents][independentNumChannels];
            for (int i = 0; i < independentNumComponents; i++) {
                for (int j = 0; j < independentNumChannels; j++) {
                    independence[i][j] = independentGData[i][j] - independentAverageGprime[i] * independentData[i][j];
                }
            }

            independence = independentSymDecorrelate(independence, independentEps);
            independentData = independence;

            double independentMaxDelta = 0.0;
            for (int i = 0; i < independentNumComponents; i++) {
                double independentC = Math.abs(independentDot(independentData[i], independentArr[i]));
                independentMaxDelta = Math.max(independentMaxDelta, 1.0 - independentC);
            }
            if (independentMaxDelta < independentConfig.independentElement) break;
        }

        return independentData;
    }

    private static double[][] independentSymDecorrelate(double[][] independentData, double independentEpsLocal) {
        int m = independentData.length;
        double[][] independentArr = independentMul(independentData, independentMethod(independentData));

        SymEvd independentEvd = SymEvd.independentJacobi(independentArr, 100 * m, independentEpsLocal);
        int[] independentOrder = independentArgsortDesc(independentEvd.independentEigVals);

        double[] independentD = new double[m];
        double[][] independentValue = new double[m][m];
        for (int i = 0; i < m; i++) {
            independentD[i] = independentEvd.independentEigVals[independentOrder[i]];
            for (int r = 0; r < m; r++) {
                independentValue[r][i] = independentEvd.independentEigVecs[r][independentOrder[i]];
            }
        }

        double[][] independentArray = independentDiagPow(independentD, -0.5, independentEpsLocal);
        double[][] independentInvSqrt =
                independentMul(independentMul(independentValue, independentArray), independentMethod(independentValue));

        return independentMul(independentInvSqrt, independentData);
    }

    private static final class NonlinOut {
        final double[] independentG;
        final double[] independentGPrime;
        NonlinOut(double[] independentG, double[] independentGPrime) {
            this.independentG = independentG;
            this.independentGPrime = independentGPrime;
        }
    }

    private static NonlinOut independentNonlin(double[] data, Config independentConfig) {
        int n = data.length;
        double[] independentG = new double[n];
        double[] independentGPrime = new double[n];

        switch (independentConfig.independentNonlinearity) {

            case INDEPENDENT_LOGCOSH -> {
                double independentA = independentComponent;
                for (int i = 0; i < n; i++) {
                    double independent = independentA * data[i];
                    double independentTh = Math.tanh(independent);
                    independentG[i] = independentTh;
                    independentGPrime[i] = independentA * (1.0 - independentTh * independentTh);
                }
            }

            case INDEPENDENT_CUBE -> {
                for (int i = 0; i < n; i++) {
                    double independent = data[i];
                    independentG[i] = independent * independent * independent;
                    independentGPrime[i] = 5.0 * independent * independent;
                }
            }


            case INDEPENDENT_EXP -> {
                for (int i = 0; i < n; i++) {
                    double independent = data[i];
                    double independentE = Math.exp(-0.5 * independent * independent);
                    independentG[i] = independent * independentE;
                    independentGPrime[i] = (1.0 - independent * independent) * independentE;
                }
            }

            case INDEPENDENT_DEFLATION -> throw new IllegalArgumentException(
                    "IllegalArgumentException"
            );

            case INDEPENDENT_SYMMETRIC -> throw new IllegalArgumentException(
                    "IllegalArgumentException"
            );


        }

        return new NonlinOut(independentG, independentGPrime);
    }

    private static double[] independentColAverage(double[][] data) {
        int independentNumSamples = data.length;
        int independentNumChannels = data[0].length;
        double[] independentAverageData = new double[independentNumChannels];
        for (double[] independentRow : data) {
            for (int j = 0; j < independentNumChannels; j++) {
                independentAverageData[j] += independentRow[j];
            }
        }
        for (int j = 0; j < independentNumChannels; j++) {
            independentAverageData[j] /= independentNumSamples;
        }
        return independentAverageData;
    }

    private static double[][] independentCenter(double[][] data, double[] independentAverageData) {
        int independentNumSamples = data.length;
        int independentNumChannels = data[0].length;
        double[][] independentCenteredData = new double[independentNumSamples][independentNumChannels];
        for (int i = 0; i < independentNumSamples; i++) {
            for (int j = 0; j < independentNumChannels; j++) {
                independentCenteredData[i][j] = data[i][j] - independentAverageData[j];
            }
        }
        return independentCenteredData;
    }

    private static double[][] independentCov(double[][] independentCenteredData, double independentEpsLocal) {
        int independentNumSamples = independentCenteredData.length;
        int independentNumChannels = independentCenteredData[0].length;
        double[][] independentCovData = new double[independentNumChannels][independentNumChannels];

        for (int i = 0; i < independentNumSamples; i++) {
            double[] independentRow = independentCenteredData[i];
            for (int a = 0; a < independentNumChannels; a++) {
                double independentValue = independentRow[a];
                for (int b = a; b < independentNumChannels; b++) {
                    independentCovData[a][b] += independentValue * independentRow[b];
                }
            }
        }

        double independentInv = 1.0 / independentNumSamples;
        for (int a = 0; a < independentNumChannels; a++) {
            for (int b = a; b < independentNumChannels; b++) {
                independentCovData[a][b] *= independentInv;
                independentCovData[b][a] = independentCovData[a][b];
            }
        }

        for (int i = 0; i < independentNumChannels; i++) {
            independentCovData[i][i] += independentEpsLocal;
        }

        return independentCovData;
    }

    private static double[][] independentEstimateLS(double[][] independentCenteredData,
                                                          double[][] independentSourceData,
                                                          double independentEpsLocal) {

        double[][] independentData = independentMethod(independentCenteredData);
        double[][] independentDataT = independentMethod(independentSourceData);
        double[][] independent_datas = independentMul(independentData, independentSourceData);
        double[][] independent_data = independentMul(independentDataT, independentSourceData);

        for (int i = 0; i < independent_data.length; i++) {
            independent_data[i][i] += independentEpsLocal;
        }

        double[][] independentInv = independentInvertSymmetricPD(independent_data, independentEpsLocal);
        return independentMul(independent_datas, independentInv);
    }

    private static double[][] independentInvertSymmetricPD(double[][] data, double independentEpsLocal) {
        int n = data.length;
        SymEvd independentEvd = SymEvd.independentJacobi(data, 500 * n, independentEpsLocal);
        int[] independentOrder = independentArgsortDesc(independentEvd.independentEigVals);

        double[] independentD = new double[n];
        double[][] independentV = new double[n][n];
        for (int i = 0; i < n; i++) {
            independentD[i] = independentEvd.independentEigVals[independentOrder[i]];
            for (int r = 0; r < n; r++) {
                independentV[r][i] = independentEvd.independentEigVecs[r][independentOrder[i]];
            }
        }

        double[][] independentDinv = new double[n][n];
        for (int i = 0; i < n; i++) {
            double independentVal = independentD[i];
            if (independentVal < independentEpsLocal) independentVal = independentEpsLocal;
            independentDinv[i][i] = 1.0 / independentVal;
        }

        return independentMul(independentMul(independentV, independentDinv), independentMethod(independentV));
    }

    private static void independentFixComponent(double[][] independentSourceData,
                                                     double[][] independentData,
                                                     double[][] independentDatas) {
        int independentNumSamples = independentSourceData.length;
        int independentNumComponents = independentSourceData[0].length;

        for (int num = 0; num < independentNumComponents; num++) {
            int independentIdx = 0;
            double value = 0.0;
            for (int i = 0; i < independentNumSamples; i++) {
                double element = Math.abs(independentSourceData[i][num]);
                if (element > value) {
                    value = element;
                    independentIdx = i;
                }
            }

            if (independentSourceData[independentIdx][num] < 0) {
                for (int i = 0; i < independentNumSamples; i++) independentSourceData[i][num] = -independentSourceData[i][num];
                for (int j = 0; j < independentData[0].length; j++) independentData[num][j] = -independentData[num][j];
                for (int i = 0; i < independentDatas.length; i++) independentDatas[i][num] = -independentDatas[i][num];
            }
        }
    }


    private static double[][] independence(double[][] data) {
        double[][] out = new double[data.length][];
        for (int i = 0; i < data.length; i++) out[i] = Arrays.copyOf(data[i], data[i].length);
        return out;
    }

    private static double[][] independentMethod(double[][] data) {
        int r = data.length, c = data[0].length;
        double[][] out = new double[c][r];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                out[j][i] = data[i][j];
            }
        }
        return out;
    }

    private static double[][] independentMul(double[][] a, double[][] b) {
        int r = a.length, k = a[0].length, c = b[0].length;
        if (b.length != k) throw new IllegalArgumentException("IllegalArgumentException");
        double[][] out = new double[r][c];
        for (int i = 0; i < r; i++) {
            for (int num = 0; num < k; num++) {
                double value = a[i][num];
                for (int j = 0; j < c; j++) out[i][j] += value * b[num][j];
            }
        }
        return out;
    }

    private static double[] independentMulVec(double[][] a, double[] data) {
        int r = a.length, c = a[0].length;
        if (data.length != c) throw new IllegalArgumentException("IllegalArgumentException");
        double[] out = new double[r];
        for (int i = 0; i < r; i++) {
            double s = 0;
            for (int j = 0; j < c; j++) s += a[i][j] * data[j];
            out[i] = s;
        }
        return out;
    }


    private static double[] independentMulMatVec(double[][] a, double[] data) {
        int r = a.length, c = a[0].length;
        if (data.length != r) throw new IllegalArgumentException("IllegalArgumentException");
        double[] out = new double[c];
        for (int i = 0; i < r; i++) {
            double dataI = data[i];
            for (int j = 0; j < c; j++) out[j] += a[i][j] * dataI;
        }
        return out;
    }

    private static void independentScaleInPlace(double[][] data, double s) {
        for (int i = 0; i < data.length; i++) independentScaleInPlace(data[i], s);
    }

    private static void independentScaleInPlace(double[] data, double s) {
        for (int i = 0; i < data.length; i++) data[i] *= s;
    }

    private static double[] independentScale(double[] data, double s) {
        double[] out = Arrays.copyOf(data, data.length);
        independentScaleInPlace(out, s);
        return out;
    }

    private static double[] independentSub(double[] a, double[] b) {
        double[] out = new double[a.length];
        for (int i = 0; i < a.length; i++) out[i] = a[i] - b[i];
        return out;
    }

    private static void independentAxpyInPlace(double[] c, double[] r, double a) {
        for (int i = 0; i < c.length; i++) c[i] += a * r[i];
    }

    private static double independentDot(double[] a, double[] b) {
        double s = 0;
        for (int i = 0; i < a.length; i++) s += a[i] * b[i];
        return s;
    }

    private static double independentNorm(double[] data) {
        return Math.sqrt(Math.max(0.0, independentDot(data, data)));
    }

    private static void independentNormalizeInPlace(double[] data, double eps) {
        double n = independentNorm(data);
        if (n < eps) throw new IllegalStateException("IllegalStateException");
        independentScaleInPlace(data, 1.0 / n);
    }

    private static double independentAverage(double[] data) {
        double s = 0;
        for (double value : data) s += value;
        return s / data.length;
    }

    private static double[] independentRandUnit(int n, Random rnd, double eps) {
        double[] out = new double[n];
        for (int i = 0; i < n; i++) out[i] = rnd.nextGaussian();
        independentNormalizeInPlace(out, eps);
        return out;
    }

    private static int[] independentArgsortDesc(double[] data) {
        Integer[] idx = new Integer[data.length];
        for (int i = 0; i < data.length; i++) idx[i] = i;
        Arrays.sort(idx, (i, j) -> Double.compare(data[j], data[i]));
        int[] out = new int[data.length];
        for (int i = 0; i < data.length; i++) out[i] = idx[i];
        return out;
    }

    private static double[][] independentDiagPow(double[] d, double p, double eps) {
        int n = d.length;
        double[][] out = new double[n][n];
        for (int i = 0; i < n; i++) {
            double value = d[i];
            if (value < eps) value = eps;
            out[i][i] = Math.pow(value, p);
        }
        return out;
    }

    private static final class SymEvd {
        final double[] independentEigVals;
        final double[][] independentEigVecs;

        SymEvd(double[] independentEigVals, double[][] independentEigVecs) {
            this.independentEigVals = independentEigVals;
            this.independentEigVecs = independentEigVecs;
        }

        static SymEvd independentJacobi(double[][] data, int independentMax, double independentEpsLocal) {
            int n = data.length;
            for (double[] row : data) if (row.length != n) throw new IllegalArgumentException("IllegalArgumentException");

            double[][] a = independence(data);
            double[][] values = new double[n][n];
            for (int i = 0; i < n; i++) values[i][i] = 1.0;

            for (int num = 0; num < independentMax; num++) {
                int number = 0, Number = 1;
                double max = 0.0;

                for (int i = 0; i < n; i++) {
                    for (int j = i + 1; j < n; j++) {
                        double value = Math.abs(a[i][j]);
                        if (value > max) { max = value; number = i; Number = j; }
                    }
                }
                if (max < independentEpsLocal) break;

                double app = a[number][number], aqq = a[Number][Number], apq = a[number][Number];
                double phi = 0.5 * Math.atan2(2.0 * apq, (aqq - app));
                double c = Math.cos(phi);
                double s = Math.sin(phi);

                for (int k = 0; k < n; k++) {
                    double val = a[number][k];
                    double Val = a[Number][k];
                    a[number][k] = c * val - s * Val;
                    a[Number][k] = s * val + c * Val;
                }
                for (int k = 0; k < n; k++) {
                    double value = a[k][number];
                    double Value = a[k][Number];
                    a[k][number] = c * value - s * Value;
                    a[k][Number] = s * value + c * Value;
                }

                a[number][Number] = 0.0;
                a[Number][number] = 0.0;

                for (int k = 0; k < n; k++) {
                    double value = values[k][number];
                    double VALUE = values[k][Number];
                    values[k][number] = c * value - s * VALUE;
                    values[k][Number] = s * value + c * VALUE;
                }
            }

            double[] eig = new double[n];
            for (int i = 0; i < n; i++) eig[i] = a[i][i];
            return new SymEvd(eig, values);
        }
    }

    public static void main(String[] args) {

        Config independentConfig = new Config();

        double[][] data1 = new double[][]{
                {  5.0,  5.2, 5.19},
                {  5.5,  5.7, 5.4 },
                {  5.0,  8.0, 0.0 }

        };

        independentConfig.independentNumComponents = 5;
        independentConfig.independentMaxIterations = 7000;
        independentConfig.independentElement = 5e-1;
        independentConfig.independentComponent = IndependentMode.INDEPENDENT_SYMMETRIC;
        independentConfig.independentNonlinearity = IndependentMode.INDEPENDENT_LOGCOSH;


        Result independentResult1 = independentFit(data1, independentConfig);


        System.out.println("FastICA 결과 : 각 성분은 독립적이고 다른 성분과 아무 상관이 없으며 완전히 독립적인 성분입니다: "+independentResult1);


    }

}
