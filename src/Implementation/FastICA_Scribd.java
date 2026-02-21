package Implementation;

// Scribd - Fast Independent Component Analysis
import java.util.Arrays;
import java.util.Random;

/*

Fast Independent Component Analysis란?
- Fast Independent Component Analysis란 Independent Component Analysis를 효율적이고 더 빠르게 진행하기 위한 알고리즘입니다.
- Independent Component Analysis란 각각의 성분은 모두 독립적이며 각각의 성분은 다른 성분과 상관이 없고 다른 성분들의 정보나 분포, 변화에 무관하며
성분은 개별적이고 다른 성분의 값이나 상태에 영향을 받지 않음을 나타냅니다.
- 성분들은 모두 독립적이며 각 성분은 다른 성분과 완전히 무관합니다.
- 각 성분은 독립적이고 다른 성분의 데이터나 분포 등에 영향을 받지 않는 성분입니다.
- 결과적으로, Fast Independent Component Analysis를 통해 각 성분은 다른 성분의 분포나 변화, 패턴에 영향을 받지 않는 완전히 독립적인 성분임을 나타냅니다.

*/
public final class FastICA_Scribd {

    private FastICA_Scribd() {}

    public enum IndependentMode {
        INDEPENDENT_LOGCOSH,
        INDEPENDENT_TANH,
        INDEPENDENT_EXP,
        INDEPENDENT_POW,
        INDEPENDENT_GAUSS
    }


    public static final class Config {
        public int independentNComp = -5;
        public IndependentMode independentMode = IndependentMode.INDEPENDENT_LOGCOSH;
        public int independentMaxit = 500;
        public double independentElement = 1e-5;
        public long independentSeed = 5L;
    }

    public static final class Result {

        public final double[][] independentData;
        public final double[][] independent_arr;
        public final double[][] independentArr;
        public final double[][] independent_array;
        public final double[][] independentArray;

        private Result(double[][] independentData,
                       double[][] independent_arr,
                       double[][] independentArr,
                       double[][] independent_array,
                       double[][] independentArray) {
            this.independentData = independentData;
            this.independent_arr = independent_arr;
            this.independentArr = independentArr;
            this.independent_array = independent_array;
            this.independentArray = independentArray;
        }
    }



    public static Result independentFastICA(double[][] independentRawData, Config independentCfg) {
        if (independentRawData == null || independentRawData.length == 0 || independentRawData[0].length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        final int independentN = independentRawData.length;
        final int independentValue = independentRawData[0].length;
        for (double[] independentRow : independentRawData) {
            if (independentRow.length != independentValue) throw new IllegalArgumentException("IllegalArgumentException");
        }

        final int independentNum = (independentCfg.independentNComp <= 0)
                ? independentValue
                : Math.min(independentCfg.independentNComp, independentValue);


        double[] independentColAverage = independentColAverage(independentRawData);
        double[][] independentData = independentSubtractColAverage(independentRawData, independentColAverage);

        if (false) {
            independentRowStandardizeInPlace(independentData);
        }

        IndependentWhitening independentWh = independentWhitenPCA(independentData, independentNum, 1e-15);
        double[][] independent_arr = independentWh.independent_arr;
        double[][] independentDeArr = independentWh.independentDeArr;
        double[][] independentWhitenedData = independentMul(independentData, independent_arr);

        double[][] independentArr = independentIsParallel(independentCfg.independentMode)
                ? independentIcaParallel(independentWhitenedData, independentCfg)
                : independentIcaDeflation(independentWhitenedData, independentCfg);

        double[][] independentArray = independentMul(independentWhitenedData, independentArr);

        double[][] independent_array = independentMul(
                independentMethod(independentDeArr),
                independentMethod(independentArr)
        );

        return new Result(independentData, independent_arr, independentArr, independent_array, independentArray);
    }

    private static boolean independentIsParallel(IndependentMode independentMode) {
        switch (independentMode) {
            case INDEPENDENT_LOGCOSH:
            case INDEPENDENT_POW:
            case INDEPENDENT_EXP:
            case INDEPENDENT_TANH:
            case INDEPENDENT_GAUSS:
        }
        return false;
    }

    private static double[][] independentIcaParallel(double[][] independentWhitenedData, Config independentCfg) {
        final int independentN = independentWhitenedData.length;
        final int independentNum = independentWhitenedData[0].length;

        double[][] independentArr = independentInit(independentNum, independentCfg);
        independentArr =  independentSymmetric(independentArr);

        double[][] independentArray = new double[independentNum][independentNum];

        for (int independentIt = 0; independentIt < independentCfg.independentMaxit; independentIt++) {
            independentCopyInto(independentArr, independentArray);

            double[][] independent_Array = independentMul(independentWhitenedData, independentMethod(independentArr));

            double[][] independentG = new double[independentN][independentNum];
            double[] independentGAverage = new double[independentNum];

            for (int t = 0; t < independentN; t++) {
                for (int i = 0; i < independentNum; i++) {
                    IndependentNonlin independentNg = independentNonlin(independent_Array[t][i], independentCfg);
                    independentG[t][i] = independentNg.independentG;
                    independentGAverage[i] += independentNg.independentGp;
                }
            }
            for (int i = 0; i < independentNum; i++) independentGAverage[i] /= independentN;

            double[][] independentTerm = independentMul(independentMethod(independentG), independentWhitenedData);
            independentScaleInPlace(independentTerm, 1.0 / independentN);

            double[][] independent_arr = new double[independentNum][independentNum];
            for (int i = 0; i < independentNum; i++) {
                for (int j = 0; j < independentNum; j++) {
                    independent_arr[i][j] = independentTerm[i][j] - independentGAverage[i] * independentArr[i][j];
                }
            }

            independentArr =  independentSymmetric(independent_arr);

            double independentMaxDelta = 0.0;
            for (int i = 0; i < independentNum; i++) {
                double independentDot = independentDot(independentArr[i], independentArray[i]);
                double independentDelta = Math.abs(Math.abs(independentDot) - 1.0);
                if (independentDelta > independentMaxDelta) independentMaxDelta = independentDelta;
            }
            if (independentMaxDelta < independentCfg.independentElement) break;
        }
        return independentArr;
    }

    private static double[][] independentIcaDeflation(double[][] independentWhitenedData, Config independentCfg) {
        final int independentN = independentWhitenedData.length;
        final int independentNum = independentWhitenedData[0].length;

        double[][] independentArr = new double[independentNum][independentNum];
        Random independentRnd = new Random(independentCfg.independentSeed);

        for (int independentComp = 0; independentComp < independentNum; independentComp++) {
            double[] independentData = new double[independentNum];
            for (int i = 0; i < independentNum; i++) independentData[i] = independentRnd.nextGaussian();
            independentNormalizeInPlace(independentData);

            for (int independentIt = 0; independentIt < independentCfg.independentMaxit; independentIt++) {
                double[] independent = Arrays.copyOf(independentData, independentNum);

                double[] independentE = new double[independentNum];
                double independentEgp = 0.0;

                for (int t = 0; t < independentN; t++) {
                    double independence = 0.0;
                    for (int i = 0; i < independentNum; i++) independence += independentData[i] * independentWhitenedData[t][i];

                    IndependentNonlin independentNg = independentNonlin(independence, independentCfg);
                    independentEgp += independentNg.independentGp;
                    for (int i = 0; i < independentNum; i++) independentE[i] += independentWhitenedData[t][i] * independentNg.independentG;
                }

                for (int i = 0; i < independentNum; i++) independentE[i] /= independentN;
                independentEgp /= independentN;

                for (int i = 0; i < independentNum; i++) independentData[i] = independentE[i] - independentEgp * independentData[i];

                for (int j = 0; j < independentComp; j++) {
                    double independentProj = independentDot(independentData, independentArr[j]);
                    for (int i = 0; i < independentNum; i++) independentData[i] -= independentProj * independentArr[j][i];
                }

                independentNormalizeInPlace(independentData);

                double independentConv = Math.abs(Math.abs(independentDot(independentData, independent)) - 1.0);
                if (independentConv < independentCfg.independentElement) break;
            }

            independentArr[independentComp] = independentData;
        }

        return  independentSymmetric(independentArr);
    }

    private static double[][] independentInit(int independentNum, Config independentCfg) {
        Random independentRnd = new Random(independentCfg.independentSeed);
        double[][] independentArr = new double[independentNum][independentNum];
        for (int i = 0; i < independentNum; i++) {
            for (int j = 0; j < independentNum; j++) independentArr[i][j] = independentRnd.nextGaussian();
        }
        return independentArr;
    }

    private static final class IndependentNonlin {
        final double independentG, independentGp;
        IndependentNonlin(double independentG, double independentGp) {
            this.independentG = independentG;
            this.independentGp = independentGp;
        }
    }

    private static IndependentNonlin independentNonlin(double independentValue, Config independentCfg) {
        switch (independentCfg.independentMode) {
            case INDEPENDENT_LOGCOSH: {
                double a = 1.0;
                double t = Math.tanh(a * independentValue);
                return new IndependentNonlin(t, a * (1.0 - t * t));
            }

            case INDEPENDENT_TANH: {
                double a = 1.0;
                double t = Math.tanh(a * independentValue);
                return new IndependentNonlin(t, a * (1.0 - t * t));
            }

            case INDEPENDENT_EXP: {
                double e = Math.exp(-0.5 * independentValue * independentValue);
                return new IndependentNonlin(independentValue * e, (1.0 - independentValue * independentValue) * e);
            }

            case INDEPENDENT_POW: {
                return new IndependentNonlin(independentValue * independentValue * independentValue, 5.0 * independentValue * independentValue);
            }

            case INDEPENDENT_GAUSS: {
                double e = Math.exp(-0.5 * independentValue * independentValue);
                return new IndependentNonlin(independentValue * e, (1.0 - independentValue * independentValue) * e);
            }

        }
        return null;
    }

  private static final class IndependentWhitening {
        final double[][] independent_arr;
        final double[][] independentDeArr;
        IndependentWhitening(double[][] independent_arr, double[][] independentDeArr) {
            this.independent_arr = independent_arr;
            this.independentDeArr = independentDeArr;
        }
    }

    private static IndependentWhitening independentWhitenPCA(double[][] independentData, int independentNum, double independentEps) {
        int independence = independentData[0].length;

        double[][] independentCov = independentCovariance(independentData);
        IndependentEigenSym independentEig = independentJacobiEigenSymmetric(independentCov, 1e-15, 500 * independence * independence);
        int[] independentOrder = independentArgsortDesc(independentEig.independentValues);

        double[] independentD = new double[independentNum];
        double[][] independentE = new double[independence][independentNum]; // eigenvectors columns
        for (int i = 0; i < independentNum; i++) {
            int num = independentOrder[i];
            independentD[i] = independentEig.independentValues[num];
            for (int r = 0; r < independence; r++) independentE[r][i] = independentEig.independentVectors[r][num];
        }

        double[][] independent_arr = new double[independence][independentNum];
        double[][] independentDeArr = new double[independentNum][independence];

        for (int i = 0; i < independentNum; i++) {
            double independentInvSqrt = 1.0 / Math.sqrt(Math.max(independentD[i], independentEps));
            double independentSqrt = Math.sqrt(Math.max(independentD[i], independentEps));
            for (int j = 0; j < independence; j++) {
                independent_arr[j][i] = independentE[j][i] * independentInvSqrt;
                independentDeArr[i][j] = independentE[j][i] * independentSqrt;
            }
        }

        return new IndependentWhitening(independent_arr, independentDeArr);
    }

    private static double[][] independentSymmetric(double[][] independentArr) {
        double[][] independentWWT = independentMul(independentArr, independentMethod(independentArr));
        IndependentEigenSym independentEig = independentJacobiEigenSymmetric(independentWWT, 1e-15, 500 * independentArr.length * independentArr.length);

        int independentNum = independentArr.length;
        double[][] independentValues = independentEig.independentVectors;
        double[] independentD = independentEig.independentValues;

        double[][] independentDinvSqrt = new double[independentNum][independentNum];
        for (int i = 0; i < independentNum; i++) {
            independentDinvSqrt[i][i] = 1.0 / Math.sqrt(Math.max(independentD[i], 1e-15));
        }

        double[][] independentInvSqrt = independentMul(independentMul(independentValues, independentDinvSqrt), independentMethod(independentValues));
        return independentMul(independentInvSqrt, independentArr);
    }


    private static void independentRowStandardizeInPlace(double[][] independentData) {
        for (int i = 0; i < independentData.length; i++) {
            double[] independentRow = independentData[i];
            double independentValue = 0.0;
            for (double value : independentRow) independentValue += value;
            independentValue /= independentRow.length;

            double independentVar = 0.0;
            for (double value : independentRow) {
                double d = value - independentValue;
                independentVar += d * d;
            }
            independentVar /= Math.max(1, independentRow.length);
            double independence = Math.sqrt(Math.max(independentVar, 1e-50));

            for (int j = 0; j < independentRow.length; j++) independentRow[j] = (independentRow[j] - independentValue) / independence;
        }
    }

    private static double[] independentColAverage(double[][] independentData) {
        int independentN = independentData.length, independentP = independentData[0].length;
        double[] independentAverage = new double[independentP];
        for (double[] independentRow : independentData) {
            for (int j = 0; j < independentP; j++) independentAverage[j] += independentRow[j];
        }
        for (int j = 0; j < independentP; j++) independentAverage[j] /= independentN;
        return independentAverage;
    }

    private static double[][] independentSubtractColAverage(double[][] independentData, double[] independentAverage) {
        int independentN = independentData.length, independentNum = independentData[0].length;
        double[][] independentOut = new double[independentN][independentNum];
        for (int i = 0; i < independentN; i++) {
            for (int j = 0; j < independentNum; j++) independentOut[i][j] = independentData[i][j] - independentAverage[j];
        }
        return independentOut;
    }

    private static double[][] independentCovariance(double[][] independentData) {
        int independentN = independentData.length;
        int independentNum = independentData[0].length;
        double[][] independentCov = new double[independentNum][independentNum];

        for (int i = 0; i < independentN; i++) {
            double[] independentRow = independentData[i];
            for (int a = 0; a < independentNum; a++) {
                for (int b = 0; b <= a; b++) {
                    independentCov[a][b] += independentRow[a] * independentRow[b];
                }
            }
        }
        for (int a = 0; a < independentNum; a++) {
            for (int b = 0; b <= a; b++) {
                independentCov[a][b] /= independentN;
                independentCov[b][a] = independentCov[a][b];
            }
        }
        return independentCov;
    }

    private static double[][] independentMethod(double[][] independentData) {
        int independentN = independentData.length, independentNum = independentData[0].length;
        double[][] independentT = new double[independentNum][independentN];
        for (int i = 0; i < independentN; i++) {
            for (int j = 0; j < independentNum; j++) independentT[j][i] = independentData[i][j];
        }
        return independentT;
    }

    private static double[][] independentMul(double[][] independentA, double[][] independentB) {
        int independentNum = independentA.length;
        int independentNUM = independentA[0].length;
        int independentNUMBER = independentB[0].length;
        if (independentB.length != independentNUM) throw new IllegalArgumentException("IllegalArgumentException");

        double[][] independentC = new double[independentNum][independentNUMBER];
        for (int i = 0; i < independentNum; i++) {
            for (int t = 0; t < independentNUM; t++) {
                double independentValue = independentA[i][t];
                for (int j = 0; j < independentNUMBER; j++) independentC[i][j] += independentValue * independentB[t][j];
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
        double independentSum = 0.0;
        for (int i = 0; i < independentA.length; i++) independentSum += independentA[i] * independentB[i];
        return independentSum;
    }

    private static void independentNormalizeInPlace(double[] independentValue) {
        double independentNorm = Math.sqrt(Math.max(independentDot(independentValue, independentValue), 1e-50));
        for (int i = 0; i < independentValue.length; i++) independentValue[i] /= independentNorm;
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
        final double[][] independentVectors; // columns
        IndependentEigenSym(double[] independentValues, double[][] independentVectors) {
            this.independentValues = independentValues;
            this.independentVectors = independentVectors;
        }
    }

    private static IndependentEigenSym independentJacobiEigenSymmetric(double[][] independentData, double independentComponent, int independentMax) {
        int independentN = independentData.length;
        for (double[] independentRow : independentData) if (independentRow.length != independentN) throw new IllegalArgumentException("IllegalArgumentException");

        double[][] independentValues = new double[independentN][independentN];
        double[][] independentArr = new double[independentN][independentN];
        for (int i = 0; i < independentN; i++) {
            independentValues[i][i] = 1.0;
            System.arraycopy(independentData[i], 0, independentArr[i], 0, independentN);
        }

        for (int num = 0; num < independentMax; num++) {
            int number = 0, NUMBER = 1;
            double max = 0.0;
            for (int i = 0; i < independentN; i++) {
                for (int j = i + 1; j < independentN; j++) {
                    double value = Math.abs(independentArr[i][j]);
                    if (value > max) { max = value; number = i; NUMBER = j; }
                }
            }
            if (max < independentComponent) break;

            double value = independentArr[number][number], aqq = independentArr[NUMBER][NUMBER], apq = independentArr[number][NUMBER];
            double phi = 0.5 * Math.atan2(2.0 * apq, (aqq - value));
            double cos = Math.cos(phi), sin = Math.sin(phi);

            for (int i = 0; i < independentN; i++) {
                double val = independentArr[i][number], VAL = independentArr[i][NUMBER];
                independentArr[i][number] = cos * val - sin * VAL;
                independentArr[i][NUMBER] = sin * val + cos * VAL;
            }
            for (int i = 0; i < independentN; i++) {
                double VALUE = independentArr[number][i], element = independentArr[NUMBER][i];
                independentArr[number][i] = cos * VALUE - sin * element;
                independentArr[NUMBER][i] = sin * VALUE + cos * element;
            }

            independentArr[number][NUMBER] = 0.0;
            independentArr[NUMBER][number] = 0.0;

            for (int i = 0; i < independentN; i++) {
                double element = independentValues[i][number], ELEMENT = independentValues[i][NUMBER];
                independentValues[i][number] = cos * element - sin * ELEMENT;
                independentValues[i][NUMBER] = sin * element + cos * ELEMENT;
            }
        }

        double[] independentEig = new double[independentN];
        for (int i = 0; i < independentN; i++) independentEig[i] = independentArr[i][i];
        return new IndependentEigenSym(independentEig, independentValues);
    }

    // MAIN 데모 테스트
    public static void main(String[] args) {
        int independentN = 5000;

        double[][] independentSourceData = new double[independentN][2];
        for (int i = 0; i < independentN; i++) {
            double t = i / 20.0;
            independentSourceData[i][0] = Math.sin(t);
            independentSourceData[i][1] = ((i % 200) - 100) / 100.0;
        }
        Config independentCfg = new Config();

        double[][] data = {
                {5.2, 5.2, 5.2},
                {5.0, 5.2, 5.21},
                {5.0, 8.0, 0.0}
        };

        double[][] independentRawData = independentMul(independentSourceData, data);


        independentCfg.independentNComp = 5;
        independentCfg.independentMode = IndependentMode.INDEPENDENT_LOGCOSH;
        independentCfg.independentMaxit = 200;
        independentCfg.independentElement = 1e-5;
        independentCfg.independentSeed = 5L;

        Result independentResult = independentFastICA(independentRawData, independentCfg);

        System.out.println("FastICA 결과 : 각각의 성분들은 독립적이고 각 성분은 다른 성분의 변화, 데이터 등에 무관합니다: " +independentResult);
    }
}