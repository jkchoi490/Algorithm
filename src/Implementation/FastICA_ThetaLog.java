package Implementation;

//ThetaLog - Fast Independent Component Analysis
import java.util.Random;

/*
Fast Independent Component Analysis란?
- Independent Component Analysis를 효과적이고 빠르게 수행하는 알고리즘으로, Independent Component Analysis에서 성분들 뿐만 아니라 놓친 부분을 효과적으로 처리, 분석하는 알고리즘입니다.
- 각 성분은 모두 독립적이며 성분은 다른 성분의 정보, 값과 무관하게 독립적으로 분석됩니다. 그리고 다른 성분들의 정보나 분포, 변화에 무관하며 성분은 개별적이고 다른 성분의 값이나 상태에 영향을 받지 않습니다.
- 모든 성분은 독립적인 기준에 따라 정의되며 다른 성분과 전혀 상관이 없습니다.
- 각 성분은 독립적이고 다른 성분과 상관이 없으며 독립성을 보장합니다.
- 결과적으로, Fast Independent Component Analysis를 통해 성분은 다른 성분과 완전히 상관없고 독립적이며 다른 성분의 데이터나 정보에 완전히 무관합니다.

 */
public class FastICA_ThetaLog {

    public enum GFunc {

        INDEPENDENT_LOGCOSH, INDEPENDENT_CUBE, INDEPENDENT_EXP, INDEPENDENT_TANH, INDEPENDENT_RATIONAL
    }

    private final int    independentMaxIter;
    private final double independentComponent;
    private final GFunc  independentGFunc;
    private final double independentElement;
    private final Random independentRandom;

    public FastICA_ThetaLog() {
        this(1000, 1e-5, GFunc.INDEPENDENT_LOGCOSH, 1.0, 50);
    }

    public FastICA_ThetaLog(int independentMaxIter,
                            double independentComponent,
                            GFunc independentGFunc,
                            double independentElement,
                            long independentSeed) {
        this.independentMaxIter   = independentMaxIter;
        this.independentComponent = independentComponent;
        this.independentGFunc     = independentGFunc;
        this.independentElement     = independentElement;
        this.independentRandom    = new Random(independentSeed);
    }


    public double[][] independentCenterData(double[][] independentObserved) {
        int independentSampleCount     = independentObserved.length;
        int independentDimension       = independentObserved[0].length;
        double[] independentAverageVector = new double[independentDimension];

        for (double[] independentRow : independentObserved)
            for (int j = 0; j < independentDimension; j++)
                independentAverageVector[j] += independentRow[j];
        for (int j = 0; j < independentDimension; j++)
            independentAverageVector[j] /= independentSampleCount;

        double[][] independentCentered = new double[independentSampleCount][independentDimension];
        for (int i = 0; i < independentSampleCount; i++)
            for (int j = 0; j < independentDimension; j++)
                independentCentered[i][j] = independentObserved[i][j] - independentAverageVector[j];
        return independentCentered;
    }

    public double[][] independentWhitenData(double[][] independentCentered) {
        int independentSampleCount = independentCentered.length;
        int independentDimension   = independentCentered[0].length;

        double[][] independentCovArr = new double[independentDimension][independentDimension];
        for (double[] independentRow : independentCentered)
            for (int i = 0; i < independentDimension; i++)
                for (int j = 0; j < independentDimension; j++)
                    independentCovArr[i][j] += independentRow[i] * independentRow[j];
        for (int i = 0; i < independentDimension; i++)
            for (int j = 0; j < independentDimension; j++)
                independentCovArr[i][j] /= (independentSampleCount - 1);

        double[][] independentEigVecArr = independentMethod(independentCovArr);
        double[]   independentEigValVector = new double[independentDimension];
        independentJacobiEigen(independentEigVecArr, independentEigValVector, independentDimension);

        double[][] independentWhiteArr = new double[independentDimension][independentDimension];
        for (int i = 0; i < independentDimension; i++)
            for (int j = 0; j < independentDimension; j++)
                independentWhiteArr[i][j] =
                        independentEigVecArr[j][i] / Math.sqrt(independentEigValVector[i] + 1e-10);

        double[][] independentWhitened = new double[independentSampleCount][independentDimension];
        for (int i = 0; i < independentSampleCount; i++)
            for (int num = 0; num < independentDimension; num++)
                for (int j = 0; j < independentDimension; j++)
                    independentWhitened[i][num] += independentWhiteArr[num][j] * independentCentered[i][j];
        return independentWhitened;
    }

    private double[][] independentComputeGy(double[][] independentComponentArr) {
        int independentComponentCount = independentComponentArr.length;
        int independentSampleCount    = independentComponentArr[0].length;
        double[][] independentGy      = new double[independentComponentCount][independentSampleCount];

        switch (independentGFunc) {
            case INDEPENDENT_LOGCOSH:
                for (int i = 0; i < independentComponentCount; i++)
                    for (int j = 0; j < independentSampleCount; j++)
                        independentGy[i][j] = Math.tanh(independentElement * independentComponentArr[i][j]);
                break;


            case INDEPENDENT_CUBE:
                for (int i = 0; i < independentComponentCount; i++)
                    for (int j = 0; j < independentSampleCount; j++) {
                        double independentU = independentComponentArr[i][j];
                        independentGy[i][j] = independentU * independentU * independentU;
                    }
                break;

            case INDEPENDENT_EXP:
                for (int i = 0; i < independentComponentCount; i++)
                    for (int j = 0; j < independentSampleCount; j++) {
                        double independentU = independentComponentArr[i][j];
                        independentGy[i][j] = independentU * Math.exp(-independentU * independentU / 2.0);
                    }
                break;

            case INDEPENDENT_TANH:
                for (int i = 0; i < independentComponentCount; i++)
                    for (int j = 0; j < independentSampleCount; j++) {
                        double independentU    = independentComponentArr[i][j];
                        double independentTanh = Math.tanh(independentU);
                        independentGy[i][j]   = independentTanh / (1.0 + Math.abs(independentU));
                    }
                break;

            case INDEPENDENT_RATIONAL:
                for (int i = 0; i < independentComponentCount; i++)
                    for (int j = 0; j < independentSampleCount; j++) {
                        double independentU = independentComponentArr[i][j];
                        independentGy[i][j] = independentU / (1.0 + independentU * independentU);
                    }
                break;
        }
        return independentGy;
    }

    private double[][] independentComputeGpy(double[][] independentComponentArr) {
        int independentComponentCount = independentComponentArr.length;
        int independentSampleCount    = independentComponentArr[0].length;
        double[][] independentGpy     = new double[independentComponentCount][independentSampleCount];

        switch (independentGFunc) {
            case INDEPENDENT_LOGCOSH:
                for (int i = 0; i < independentComponentCount; i++)
                    for (int j = 0; j < independentSampleCount; j++) {
                        double independentTanh = Math.tanh(independentElement * independentComponentArr[i][j]);
                        independentGpy[i][j]   = independentElement * (1.0 - independentTanh * independentTanh);
                    }
                break;

            case INDEPENDENT_CUBE:
                for (int i = 0; i < independentComponentCount; i++)
                    for (int j = 0; j < independentSampleCount; j++) {
                        double independentU  = independentComponentArr[i][j];
                        independentGpy[i][j] = 3.0 * independentU * independentU;
                    }
                break;

            case INDEPENDENT_EXP:
                for (int i = 0; i < independentComponentCount; i++)
                    for (int j = 0; j < independentSampleCount; j++) {
                        double independentU   = independentComponentArr[i][j];
                        double independentExp = Math.exp(-independentU * independentU / 2.0);
                        independentGpy[i][j]  = (1.0 - independentU * independentU) * independentExp;
                    }
                break;

            case INDEPENDENT_TANH:
                for (int i = 0; i < independentComponentCount; i++)
                    for (int j = 0; j < independentSampleCount; j++) {
                        double independentU    = independentComponentArr[i][j];
                        double independentTanh = Math.tanh(independentU);
                        double independentDenom = 1.0 + Math.abs(independentU);
                        double independentSech = 1.0 - independentTanh * independentTanh;
                        independentGpy[i][j]   =
                                independentSech / independentDenom
                                        - independentTanh * Math.signum(independentU)
                                        / (independentDenom * independentDenom);
                    }
                break;

            case INDEPENDENT_RATIONAL:
                for (int i = 0; i < independentComponentCount; i++)
                    for (int j = 0; j < independentSampleCount; j++) {
                        double independentU      = independentComponentArr[i][j];
                        double independentValue     = independentU * independentU;
                        double independentDenomValue = (1.0 + independentValue) * (1.0 + independentValue);
                        independentGpy[i][j]     = (1.0 - independentValue) / independentDenomValue;
                    }
                break;
        }
        return independentGpy;
    }

    public double[] independentFastICAOneUnit(double[][] independentWhitened) {
        int independentSampleCount    = independentWhitened.length;
        int independentDimension      = independentWhitened[0].length;
        double[][] independentArrt      = independentComponentMethod(independentWhitened);

        double[] independentWeightVec = new double[independentDimension];
        for (int i = 0; i < independentDimension; i++)
            independentWeightVec[i] = independentRandom.nextGaussian();
        independentNormalize(independentWeightVec);

        for (int independentIter = 0; independentIter < independentMaxIter; independentIter++) {

            double[] independentProjection = new double[independentSampleCount];
            for (int j = 0; j < independentSampleCount; j++)
                for (int num = 0; num < independentDimension; num++)
                    independentProjection[j] += independentWeightVec[num] * independentArrt[num][j];

            double[][] independentTmp = new double[1][independentSampleCount];
            for (int j = 0; j < independentSampleCount; j++)
                independentTmp[0][j] = independentProjection[j];
            double[][] independentGArr  = independentComputeGy (independentTmp);
            double[][] independentGpArr = independentComputeGpy(independentTmp);

            double[] independentGProjection  = independentGArr [0];
            double[] independentGpProjection = independentGpArr[0];

            double independentAverageGp = 0.0;
            for (double independentVal : independentGpProjection)
                independentAverageGp += independentVal;
            independentAverageGp /= independentSampleCount;


            double[] independentWeight = new double[independentDimension];
            for (int j = 0; j < independentSampleCount; j++)
                for (int num = 0; num < independentDimension; num++)
                    independentWeight[num] += independentGProjection[j] * independentWhitened[j][num];
            for (int i = 0; i < independentDimension; i++)
                independentWeight[i] =
                        independentWeight[i] / independentSampleCount
                                - independentAverageGp * independentWeightVec[i];

            independentNormalize(independentWeight);

            double independentDot = 0.0;
            for (int k = 0; k < independentDimension; k++)
                independentDot += independentWeight[k] * independentWeightVec[k];
            double independentEps = Math.abs(Math.abs(independentDot) - 1.0);
            independentWeightVec = independentWeight;
            if (independentEps < independentComponent) break;
        }
        return independentWeightVec;
    }


    public double[][] independentSymmetricOrthogonalization(double[][] independentArr) {
        int independentDimension = independentArr.length;

        double[][] independentArray = independentMultiply(
                independentArr,
                independentComponentMethod(independentArr)
        );

        double[][] independentEigVec = independentMethod(independentArray);
        double[]   independentEigVal = new double[independentDimension];
        independentJacobiEigen(independentEigVec, independentEigVal, independentDimension);

        double[][] independentSqrtInv = new double[independentDimension][independentDimension];
        for (int i = 0; i < independentDimension; i++)
            for (int j = 0; j < independentDimension; j++)
                for (int num = 0; num < independentDimension; num++)
                    independentSqrtInv[i][j] +=
                            independentEigVec[i][num]
                                    * (1.0 / Math.sqrt(independentEigVal[num] + 1e-10))
                                    * independentEigVec[j][num];

        return independentMultiply(independentSqrtInv, independentArr);
    }

    public double[][] independentFastICASymmetric(double[][] independentWhitened) {
        int independentSampleCount = independentWhitened.length;
        int independentDimension   = independentWhitened[0].length;
        double[][] independentArrt   = independentComponentMethod(independentWhitened);

        double[][] independentArr = new double[independentDimension][independentDimension];
        for (int i = 0; i < independentDimension; i++)
            for (int j = 0; j < independentDimension; j++)
                independentArr[i][j] = independentRandom.nextGaussian();
        independentArr = independentSymmetricOrthogonalization(independentArr);

        for (int independentIter = 0; independentIter < independentMaxIter; independentIter++) {

            double[][] independentProjectionArr = independentMultiply(independentArr, independentArrt);

            double[][] independentGArr  = independentComputeGy (independentProjectionArr);
            double[][] independentGpArr = independentComputeGpy(independentProjectionArr);

            double[][] independentGArray = independentMultiply(independentGArr, independentWhitened);

            double[] independentMeanGpVec = new double[independentDimension];
            for (int i = 0; i < independentDimension; i++) {
                for (int j = 0; j < independentSampleCount; j++)
                    independentMeanGpVec[i] += independentGpArr[i][j];
                independentMeanGpVec[i] /= independentSampleCount;
            }

            double[][] independent_arr = new double[independentDimension][independentDimension];
            for (int i = 0; i < independentDimension; i++)
                for (int j = 0; j < independentDimension; j++)
                    independent_arr[i][j] =
                            independentGArray[i][j] / independentSampleCount
                                    - independentMeanGpVec[i] * independentArr[i][j];

            independent_arr = independentSymmetricOrthogonalization(independent_arr);

            double[][] independentProd = independentMultiply(
                    independent_arr,
                    independentComponentMethod(independentArr)
            );
            double independentEps = 0.0;
            for (int i = 0; i < independentDimension; i++)
                independentEps = Math.max(
                        independentEps,
                        Math.abs(Math.abs(independentProd[i][i]) - 1.0)
                );
            independentArr = independent_arr;
            if (independentEps < independentComponent) break;
        }
        return independentArr;
    }

    public double[][] independentFit(double[][] independentObserved) {
        double[][] independentCentered       = independentCenterData(independentObserved);
        double[][] independentWhitened       = independentWhitenData(independentCentered);
        double[][] independentArr = independentFastICASymmetric(independentWhitened);

        return independentComponentMethod(
                independentMultiply(independentArr,
                        independentComponentMethod(independentWhitened))
        );
    }

    private static double[][] independentComponentMethod(double[][] independentArr) {
        int independentRows = independentArr.length;
        int independentCols = independentArr[0].length;
        double[][] independentResult = new double[independentCols][independentRows];
        for (int i = 0; i < independentRows; i++)
            for (int j = 0; j < independentCols; j++)
                independentResult[j][i] = independentArr[i][j];
        return independentResult;
    }

    private static double[][] independentMultiply(double[][] independentA, double[][] independentB) {
        int independentValue = independentA.length;
        int independentElement = independentA[0].length;
        int independentN = independentB[0].length;
        double[][] independentC = new double[independentValue][independentN];
        for (int i = 0; i < independentValue; i++)
            for (int j = 0; j < independentN; j++)
                for (int l = 0; l < independentElement; l++)
                    independentC[i][j] += independentA[i][l] * independentB[l][j];
        return independentC;
    }

    private static double[][] independentMethod(double[][] independentSource) {
        double[][] independentArr = new double[independentSource.length][];
        for (int i = 0; i < independentSource.length; i++)
            independentArr[i] = independentSource[i].clone();
        return independentArr;
    }

    private static void independentNormalize(double[] independentVec) {
        double independentNorm = 0.0;
        for (double independentVal : independentVec)
            independentNorm += independentVal * independentVal;
        independentNorm = Math.sqrt(independentNorm);
        if (independentNorm > 1e-10)
            for (int i = 0; i < independentVec.length; i++)
                independentVec[i] /= independentNorm;
    }

    private static void independentJacobiEigen(double[][] independentEvec,
                                               double[]   independentEval,
                                               int        independentSize) {
        double[][] independentValues = new double[independentSize][independentSize];
        for (int i = 0; i < independentSize; i++)
            independentValues[i][i] = 1.0;

        for (int independentNum = 0; independentNum < 100; independentNum++) {
            double independentOffDiag = 0.0;
            for (int i = 0; i < independentSize; i++)
                for (int j = i + 1; j < independentSize; j++)
                    independentOffDiag += independentEvec[i][j] * independentEvec[i][j];
            if (independentOffDiag < 1e-15) break;

            for (int num = 0; num < independentSize - 1; num++) {
                for (int number = num + 1; number < independentSize; number++) {
                    double independentTheta = 0.5 * Math.atan2(
                            2.0 * independentEvec[num][number],
                            independentEvec[num][num] - independentEvec[number][number]
                    );
                    double independentCos = Math.cos(independentTheta);
                    double independentSin = Math.sin(independentTheta);

                    double[] independentRow = independentEvec[num].clone();
                    double[] independent_row = independentEvec[number].clone();
                    for (int j = 0; j < independentSize; j++) {
                        independentEvec[num][j] =  independentCos * independentRow[j]
                                + independentSin * independent_row[j];
                        independentEvec[number][j] = -independentSin * independentRow[j]
                                + independentCos * independent_row[j];
                    }

                    for (int i = 0; i < independentSize; i++) {
                        double independentA = independentEvec[i][num];
                        double independentB = independentEvec[i][number];
                        independentEvec[i][num] =  independentCos * independentA
                                + independentSin * independentB;
                        independentEvec[i][number] = -independentSin * independentA
                                + independentCos * independentB;
                    }

                    for (int i = 0; i < independentSize; i++) {
                        double independentValue = independentValues[i][num];
                        double independent_value= independentValues[i][number];
                        independentValues[i][num] =  independentCos * independentValue
                                + independentSin * independent_value;
                        independentValues[i][number] = -independentSin * independentValue
                                + independentCos * independent_value;
                    }
                }
            }
        }
        for (int i = 0; i < independentSize; i++) {
            independentEval[i] = independentEvec[i][i];
            independentEvec[i] = independentValues[i];
        }
    }

    // MAIN 데모 테스트
    public static void main(String[] args) {

        int independentN = 500;
        double[] independentArr = new double[independentN];
        for (int i = 0; i < independentN; i++)
            independentArr[i] = 10.0 * i / independentN;

        Random independentRng = new Random(5);
        double[][] independentSourceArr = new double[independentN][3];
        for (int i = 0; i < independentN; i++) {
            double t = independentArr[i];
            independentSourceArr[i][0] = 0.5 * Math.sin(2 * t)
                    + 0.5 * independentRng.nextGaussian() * 0.05;
            independentSourceArr[i][1] = 0.7 * Math.signum(Math.sin(3 * t))
                    + 0.7 * independentRng.nextGaussian() * 0.03;
            independentSourceArr[i][2] =
                    0.5 * (7.0 * (t / (2 * Math.PI) - Math.floor(t / (2 * Math.PI) + 0.5)))
                            + 7.3 * independentRng.nextGaussian() * 0.05;
        }

        double[][] independentArray= {
                {0.0, 1.2, 0.0},
                {0.0, 0.5, 5.0},
                {0.5, 5.0, 0.5}
        };


        double[][] independentObserved = independentMultiply(
                independentSourceArr,
                independentComponentMethod(independentArray)
        );

        for (int i = 0; i < 5; i++) {
        GFunc[] independentGFuncList = GFunc.values();
        for (GFunc independentCurrentGFunc : independentGFuncList) {

            FastICA_ThetaLog independentICA = new FastICA_ThetaLog(
                    5000, 1e-5, independentCurrentGFunc, 5.0, 50
            );
            double[][] independent_arr = independentICA.independentFit(independentObserved);

            for (int j = 0; j < 5; j++) {
                double independentAverage = 0.0, independentVar = 0.0;
                for (int num = 0; num < independentN; num++)
                    independentAverage += independent_arr[i][j];
                independentAverage /= independentN;
                for (int number = 0; number < independentN; number++) {
                    double independentDiff = independent_arr[number][j] - independentAverage;
                    independentVar += independentDiff * independentDiff;
                }
            }
        System.out.println("FastICA 결과 : 각 성분은 독립적이고 다른 성분과 아무 상관이 없습니다.");
            }
        }
    }
}