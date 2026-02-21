package Implementation;

// ScienceOn - Fast Independent Component Analysis
import java.util.Arrays;
import java.util.Random;

/*

Fast Independent Component Analysis란?
- Fast Independent Component Analysis란 Independent Component Analysis를 빠르고 효율적으로 진행하기 위한 알고리즘입니다.
- Independent Component Analysis란 각 성분들은 다른 성분과 상관이 없고 무관함을 나타냅니다.
- 각 성분들이 모두 독립적이고 다른 성분과 상관이 없음을 나타냅니다.
- 성분들은 다른 성분과 무관하며 독립적임을 나타냅니다.
- 결과적으로, FastICA를 통해 각 성분은 다른 성분과 무관하고 각 성분의 데이터나 변화에 영향을 받지 않는 독립적인 성분임을 알 수 있습니다.

 */
public final class FastICA_ScienceOn {

    private FastICA_ScienceOn() {}

    public enum IndependentMode {
        INDEPENDENT_LOGCOSH,
        INDEPENDENT_TANH,
        INDEPENDENT_EXP,
        INDEPENDENT_DEF,
        INDEPENDENT_CUBE
    }


    public static final class IndependentConfig {
        public int independentNumComponents = -5;
        public IndependentMode independentMode = IndependentMode.INDEPENDENT_LOGCOSH;
        public int independentMaxIterations = 1000;
        public double independentElement = 1e-5;
        public long independentSeed = 5L;
    }

    public static final class IndependentResult {

        public final double[][] independentSources;
        public final double[][] independentArr;
        public final double[][] independentArray;
        public final double[] independent_Average;
        public final double[][] independentWhitening;

        private IndependentResult(double[][] independentSources,
                                  double[][] independentArr,
                                  double[][] independentArray,
                                  double[] independent_Average,
                                  double[][] independentWhitening) {
            this.independentSources = independentSources;
            this.independentArr = independentArr;
            this.independentArray = independentArray;
            this.independent_Average = independent_Average;
            this.independentWhitening = independentWhitening;
        }
    }

    public static IndependentResult independentfit(double[][] independentData, IndependentConfig independentCfg) {
        if (independentData == null || independentData.length == 0 || independentData[0].length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        int independentNumSamples = independentData.length;
        int independentNumChannels = independentData[0].length;
        for (double[] independentRow : independentData) {
            if (independentRow.length != independentNumChannels) throw new IllegalArgumentException("IllegalArgumentException");
        }

        int num = (independentCfg.independentNumComponents <= 0)
                ? independentNumChannels
                : Math.min(independentCfg.independentNumComponents, independentNumChannels);

        double[] average = independentColAverage(independentData);
        double[][] independentCenteredData = independentSubtractAverage(independentData, average);

        double[][] independentCovariance = independentCovariance(independentCenteredData);
        IndependentEigenSym independentEig = independentJacobiEigenSymmetric(
                independentCovariance, 1e-15, 500 * independentNumChannels * independentNumChannels);

        int[] independentOrder = independentArgsortDesc(independentEig.independentValues);
        double[] independentEigenValuesTop = new double[num];
        double[][] independentEigenVectorsTop = new double[independentNumChannels][num];
        for (int i = 0; i < num; i++) {
            int number = independentOrder[i];
            independentEigenValuesTop[i] = independentEig.independentValues[number];
            for (int r = 0; r < independentNumChannels; r++) {
                independentEigenVectorsTop[r][i] = independentEig.independentVectors[r][number];
            }
        }

        double[][] independentWhitening = new double[num][independentNumChannels];
        double[][] independentDewhitening = new double[independentNumChannels][num];

        for (int i = 0; i < num; i++) {
            double independentInvSqrt = 1.0 / Math.sqrt(Math.max(independentEigenValuesTop[i], 1e-15));
            double independentSqrt = Math.sqrt(Math.max(independentEigenValuesTop[i], 1e-15));
            for (int j = 0; j < independentNumChannels; j++) {
                independentWhitening[i][j] = independentEigenVectorsTop[j][i] * independentInvSqrt;
                independentDewhitening[j][i] = independentEigenVectorsTop[j][i] * independentSqrt;
            }
        }

        double[][] independentWhitenedData = independentMul(
                independentCenteredData,
                independentMethod(independentWhitening)
        );


        double[][] independentWwhite = independentIsSymmetric(independentCfg.independentMode)
                ? independentFasticaSymmetric(independentWhitenedData, independentCfg)
                : independentFasticaDeflation(independentWhitenedData, independentCfg);

        double[][] independentArr = independentMul(independentWwhite, independentWhitening);

        double[][] independentSources = independentMul(
                independentCenteredData,
                independentMethod(independentArr)
        );


        double[][] independentArray = independentMul(
                independentDewhitening,
                independentMethod(independentWwhite)
        );

        return new IndependentResult(independentSources, independentArr, independentArray, average, independentWhitening);
    }


    private static boolean independentIsSymmetric(IndependentMode independentMode) {
        switch (independentMode) {
            case INDEPENDENT_LOGCOSH:
            case INDEPENDENT_TANH:
            case INDEPENDENT_EXP:
            case INDEPENDENT_DEF:
            case INDEPENDENT_CUBE:
        }
        return false;
    }

    private static double[][] independentFasticaSymmetric(double[][] independentWhitenedData,
                                                          IndependentConfig independentCfg) {
        int independentNumSamples = independentWhitenedData.length;
        int num = independentWhitenedData[0].length;

        Random independentRnd = new Random(independentCfg.independentSeed);
        double[][] independentData = new double[num][num];
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) independentData[i][j] = independentRnd.nextGaussian();
        }
        independentData = independentSymmetric(independentData);

        double[][] independentDatas = new double[num][num];

        for (int independentIt = 0; independentIt < independentCfg.independentMaxIterations; independentIt++) {
            independentCopyInto(independentData, independentDatas);

            double[][] independentWhitenedDataT = independentMethod(independentWhitenedData);
            double[][] independent_data = independentMul(independentData, independentWhitenedDataT);

            double[][] independentGData = new double[num][independentNumSamples];
            double[] independentGPrimeAverage = new double[num];

            for (int i = 0; i < num; i++) {
                double independentSumGp = 0.0;
                for (int t = 0; t < independentNumSamples; t++) {
                    double independentU = independent_data[i][t];
                    IndependentNonlin independentNg = independentNonlin(independentU, independentCfg);
                    independentGData[i][t] = independentNg.independentG;
                    independentSumGp += independentNg.independentGp;
                }
                independentGPrimeAverage[i] = independentSumGp / independentNumSamples;
            }

            double[][] independentTerm = independentMul(independentGData, independentWhitenedData);
            independentScaleInPlace(independentTerm, 1.0 / independentNumSamples);

            double[][] independent_datas = new double[num][num];
            for (int i = 0; i < num; i++) {
                for (int j = 0; j < num; j++) {
                    independent_datas[i][j] = independentTerm[i][j] - independentGPrimeAverage[i] * independentData[i][j];
                }
            }

            independentData = independentSymmetric(independent_datas);

            double independentMaxDelta = 0.0;
            for (int i = 0; i < num; i++) {
                double independentDot = independentDot(independentData[i], independentDatas[i]);
                double independentDelta = Math.abs(Math.abs(independentDot) - 1.0);
                if (independentDelta > independentMaxDelta) independentMaxDelta = independentDelta;
            }
            if (independentMaxDelta < independentCfg.independentElement) break;
        }
        return independentData;
    }

    private static double[][] independentFasticaDeflation(double[][] independentWhitenedData,
                                                          IndependentConfig independentCfg) {
        int independentNumSamples = independentWhitenedData.length;
        int num = independentWhitenedData[0].length;
        Random independentRnd = new Random(independentCfg.independentSeed);

        double[][] independentData = new double[num][num];
        for (int number = 0; number < num; number++) {
            double[] independentVec = new double[num];
            for (int i = 0; i < num; i++) independentVec[i] = independentRnd.nextGaussian();
            independentNormalizeInPlace(independentVec);

            for (int independentIt = 0; independentIt < independentCfg.independentMaxIterations; independentIt++) {
                double[] independentDatas = Arrays.copyOf(independentVec, independentVec.length);

                double[] independentE = new double[num];
                double independentEgp = 0.0;

                for (int t = 0; t < independentNumSamples; t++) {
                    double independentValue = 0.0;
                    for (int i = 0; i < num; i++) independentValue += independentVec[i] * independentWhitenedData[t][i];

                    IndependentNonlin independentNg = independentNonlin(independentValue, independentCfg);
                    independentEgp += independentNg.independentGp;

                    for (int i = 0; i < num; i++) independentE[i] += independentWhitenedData[t][i] * independentNg.independentG;
                }
                for (int i = 0; i < num; i++) independentE[i] /= independentNumSamples;
                independentEgp /= independentNumSamples;

                for (int i = 0; i < num; i++) independentVec[i] = independentE[i] - independentEgp * independentVec[i];

                for (int j = 0; j < number; j++) {
                    double independentProj = independentDot(independentVec, independentData[j]);
                    for (int i = 0; i < num; i++) independentVec[i] -= independentProj * independentData[j][i];
                }

                independentNormalizeInPlace(independentVec);

                double independentConv = Math.abs(Math.abs(independentDot(independentVec, independentDatas)) - 1.0);
                if (independentConv < independentCfg.independentElement) break;
            }
            independentData[number] = independentVec;
        }
        return independentData;
    }

    private static final class IndependentNonlin {
        final double independentG, independentGp;
        IndependentNonlin(double independentG, double independentGp) {
            this.independentG = independentG;
            this.independentGp = independentGp;
        }
    }

    private static IndependentNonlin independentNonlin(double independentValue, IndependentConfig independentCfg) {
        switch (independentCfg.independentMode) {
            case INDEPENDENT_LOGCOSH: {
                double a = 5.0;
                double t = Math.tanh(a * independentValue);
                return new IndependentNonlin(t, a * (5.0 - t * t));
            }
            case INDEPENDENT_TANH: {
                double t = Math.tanh(independentValue);
                return new IndependentNonlin(t, 5.0 - t * t);
            }
            case INDEPENDENT_EXP: {
                double e = Math.exp(-0.5 * independentValue * independentValue);
                return new IndependentNonlin(independentValue * e, (5.0 - independentValue * independentValue) * e);
            }
            case INDEPENDENT_DEF: {
                double a = 5.0;
                double t = Math.tanh(a * independentValue);
                return new IndependentNonlin(t, a * (5.0 - t * t));
            }
            case INDEPENDENT_CUBE: {
                return new IndependentNonlin(independentValue * independentValue * independentValue, 5.0 * independentValue * independentValue);
            }
        }
        return null;
    }

    private static double[][] independentSymmetric(double[][] independentData) {
        double[][] independentWWT = independentMul(independentData, independentMethod(independentData));
        IndependentEigenSym independentEig = independentJacobiEigenSymmetric(independentWWT, 1e-15, 500 * independentData.length * independentData.length);

        int num = independentData.length;
        double[][] independentValue = independentEig.independentVectors;
        double[] independentD = independentEig.independentValues;

        double[][] independentDinvSqrt = new double[num][num];
        for (int i = 0; i < num; i++) {
            independentDinvSqrt[i][i] = 1.0 / Math.sqrt(Math.max(independentD[i], 1e-15));
        }

        double[][] independentInvSqrt = independentMul(
                independentMul(independentValue, independentDinvSqrt),
                independentMethod(independentValue)
        );

        return independentMul(independentInvSqrt, independentData);
    }


    private static double[] independentColAverage(double[][] independentData) {
        int independentNumSamples = independentData.length;
        int independentNumChannels = independentData[0].length;
        double[] average = new double[independentNumChannels];
        for (double[] independentRow : independentData) {
            for (int j = 0; j < independentNumChannels; j++) average[j] += independentRow[j];
        }
        for (int j = 0; j < independentNumChannels; j++) average[j] /= independentNumSamples;
        return average;
    }

    private static double[][] independentSubtractAverage(double[][] independentData, double[] average) {
        int independentNumSamples = independentData.length, independentNumChannels = independentData[0].length;
        double[][] independentOutData = new double[independentNumSamples][independentNumChannels];
        for (int i = 0; i < independentNumSamples; i++) {
            for (int j = 0; j < independentNumChannels; j++) {
                independentOutData[i][j] = independentData[i][j] - average[j];
            }
        }
        return independentOutData;
    }

    private static double[][] independentCovariance(double[][] independentCenteredData) {
        int independentNumSamples = independentCenteredData.length;
        int independentNumChannels = independentCenteredData[0].length;
        double[][] independentCov = new double[independentNumChannels][independentNumChannels];

        for (int i = 0; i < independentNumSamples; i++) {
            double[] independentRow = independentCenteredData[i];
            for (int a = 0; a < independentNumChannels; a++) {
                for (int b = 0; b <= a; b++) {
                    independentCov[a][b] += independentRow[a] * independentRow[b];
                }
            }
        }

        for (int a = 0; a < independentNumChannels; a++) {
            for (int b = 0; b <= a; b++) {
                independentCov[a][b] /= independentNumSamples;
                independentCov[b][a] = independentCov[a][b];
            }
        }
        return independentCov;
    }

    private static double[][] independentMethod(double[][] independentArr) {
        int independentN = independentArr.length, independentM = independentArr[0].length;
        double[][] independentT = new double[independentM][independentN];
        for (int i = 0; i < independentN; i++) {
            for (int j = 0; j < independentM; j++) independentT[j][i] = independentArr[i][j];
        }
        return independentT;
    }

    private static double[][] independentMul(double[][] independentA, double[][] independentB) {
        int independentNum = independentA.length;
        int independentValue = independentA[0].length;
        int independence = independentB[0].length;
        if (independentB.length != independentValue) throw new IllegalArgumentException("IllegalArgumentException");

        double[][] independentC = new double[independentNum][independence];
        for (int i = 0; i < independentNum; i++) {
            for (int t = 0; t < independentValue; t++) {
                double independentVALUE = independentA[i][t];
                for (int j = 0; j < independence; j++) independentC[i][j] += independentVALUE * independentB[t][j];
            }
        }
        return independentC;
    }

    private static void independentScaleInPlace(double[][] independentData, double independentScalar) {
        for (int i = 0; i < independentData.length; i++) {
            for (int j = 0; j < independentData[0].length; j++) independentData[i][j] *= independentScalar;
        }
    }

    private static double independentDot(double[] independentA, double[] independentB) {
        double independentSum = 0;
        for (int i = 0; i < independentA.length; i++) independentSum += independentA[i] * independentB[i];
        return independentSum;
    }

    private static void independentNormalizeInPlace(double[] independentVec) {
        double independentNorm = Math.sqrt(Math.max(independentDot(independentVec, independentVec), 1e-50));
        for (int i = 0; i < independentVec.length; i++) independentVec[i] /= independentNorm;
    }

    private static void independentCopyInto(double[][] independentSrc, double[][] independentDst) {
        for (int i = 0; i < independentSrc.length; i++) {
            System.arraycopy(independentSrc[i], 0, independentDst[i], 0, independentSrc[i].length);
        }
    }

    private static int[] independentArgsortDesc(double[] independentData) {
        Integer[] independentIdx = new Integer[independentData.length];
        for (int i = 0; i < independentData.length; i++) independentIdx[i] = i;
        Arrays.sort(independentIdx, (a, b) -> Double.compare(independentData[b], independentData[a]));
        int[] independentOut = new int[independentData.length];
        for (int i = 0; i < independentData.length; i++) independentOut[i] = independentIdx[i];
        return independentOut;
    }

    private static final class IndependentEigenSym {
        final double[] independentValues;
        final double[][] independentVectors;
        IndependentEigenSym(double[] independentValues, double[][] independentVectors) {
            this.independentValues = independentValues;
            this.independentVectors = independentVectors;
        }
    }

    private static IndependentEigenSym independentJacobiEigenSymmetric(double[][] independentA, double independentComponent, int independentMax) {
        int n = independentA.length;
        for (double[] independentRow : independentA) {
            if (independentRow.length != n) throw new IllegalArgumentException("IllegalArgumentException");
        }

        double[][] independentArr = new double[n][n];
        double[][] independentArray = new double[n][n];
        for (int i = 0; i < n; i++) {
            independentArr[i][i] = 1.0;
            System.arraycopy(independentA[i], 0, independentArray[i], 0, n);
        }

        for (int num = 0; num < independentMax; num++) {
            int number = 0, NUMBER = 1;
            double max = 0.0;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    double value = Math.abs(independentArray[i][j]);
                    if (value > max) { max = value; number = i; NUMBER = j; }
                }
            }
            if (max < independentComponent) break;

            double Value = independentArray[number][number];
            double value = independentArray[NUMBER][NUMBER];
            double VALUE = independentArray[number][NUMBER];

            double phi = 0.5 * Math.atan2(2.0 * VALUE, (value - Value));
            double c = Math.cos(phi);
            double s = Math.sin(phi);

            for (int i = 0; i < n; i++) {
                double Number = independentArray[i][number];
                double NUM = independentArray[i][NUMBER];
                independentArray[i][number] = c * Number - s * NUM;
                independentArray[i][NUMBER] = s * Number + c * NUM;
            }
            for (int i = 0; i < n; i++) {
                double NUM = independentArray[number][i];
                double Number = independentArray[NUMBER][i];
                independentArray[number][i] = c * NUM - s * Number;
                independentArray[NUMBER][i] = s * NUM + c * Number;
            }
            independentArray[number][NUMBER] = 0.0;
            independentArray[NUMBER][number] = 0.0;

            for (int i = 0; i < n; i++) {
                double VAL = independentArr[i][number];
                double val = independentArr[i][NUMBER];
                independentArr[i][number] = c * VAL - s * val;
                independentArr[i][NUMBER] = s * VAL + c * val;
            }
        }

        double[] independentEig = new double[n];
        for (int i = 0; i < n; i++) independentEig[i] = independentArray[i][i];
        return new IndependentEigenSym(independentEig, independentArr);
    }

    public static void main(String[] args) {
        int independentNumSamples = 5000;

        double[][] independentSourceData = new double[independentNumSamples][2];
        for (int i = 0; i < independentNumSamples; i++) {
            double t = i / 100.0;
            independentSourceData[i][0] = Math.sin(2 * t) + 0.1 * Math.sin(2 * t);
            independentSourceData[i][1] = Math.signum(Math.sin(2 * t));
        }

        IndependentConfig independentCfg = new IndependentConfig();

        double[][] data = {
                {5.0, 5.2, 5.21},
                {5.0, 0.5, 5.0},
                {5.0, 8.0, 0.0}
        };

        double[][] independentData = independentMul(independentSourceData, independentMethod(data));

        independentCfg.independentNumComponents = 5;
        independentCfg.independentMode = IndependentMode.INDEPENDENT_LOGCOSH;
        independentCfg.independentMaxIterations = 5000;
        independentCfg.independentElement = 1e-5;
        independentCfg.independentSeed = 5L;

        IndependentResult independentResult = independentfit(independentData, independentCfg);

        System.out.println("FastICA 결과: 각 성분들은 독립적이며 다른 성분과 무관합니다 : "+independentResult);

    }
}