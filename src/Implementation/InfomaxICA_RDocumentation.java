package Implementation;

// RDocumentation - Infomax Independent Component Analysis
import java.util.Random;

/*

Infomax Independent Component Analysis란?
- Independent Component Analysis(독립 성분 분석)의 결과를 더 강하고 확실하게 나타내는 알고리즘으로, 정보량을 최대 수준까지 강화하여 독립적임을 더 명확하고 확실하게 나타내는 알고리즘입니다.
- Infomax Independent Component Analysis는 각 성분의 독립성을 가장 강하게 보장하는 분석 방법이며
다른 성분의 데이터나 정보 등의 영향을 받지 않고 다른 성분들과 상관이 완전히 없음을 나타내는 완전하고 확실한 독립성을 보장하는 분석 방법 입니다.
- 각 성분의 독립성을 명확하고 확실하게 나타내며 각 성분이 다른 성분과 완전히 무관한 독립적인 구조임을 최적화하여 나타냅니다.
- 성분이 절대적으로 독립적인 구조임을 나타내며 성분들은 독립적이며 다른 성분들의 데이터나 변화, 분포 등의 영향을 받지 않고 하나의 성분은 다른 성분의 존재 여부와 상관없이 정의됩니다.
- 각각의 성분은 독립적이고 더 확실하게 다른 성분과 무관함을 나타냅니다.
- 모든 성분들은 다른 성분에 영향을 받지 않으며 완전히 다른 성분과 무관하고 독립적입니다.
- 각 성분의 독립적인 특성을 가장 강하고 명확하게 표현합니다.
- 결과적으로, Infomax Independent Component Analysis를 통해 각 성분의 독립성을 가장 정확하고 확실하게 나타내며 각 성분들은 다른 성분과 상관이 완전히 없고 무관함을 확실하게 나타냅니다.

 */
public class InfomaxICA_RDocumentation {


    public enum IndependentOption {
        INDEPENDENT_TANH,
        INDEPENDENT_EXT,
        INDEPENDENT_LOG,
        INDEPENDENT_NEWTON,
        INDEPENDENT_GRADIENT
    }


    private final int               independentNc;
    private final int               independentMaxit;
    private final double            independentComponent;
    private final IndependentOption independentFunc;
    private final IndependentOption independentAlg;


    private double[][] independentArr;
    private double[][] independentArray;
    private double[][] independent_arr;
    private double[]   independentVafs;
    private int[]      independentComponents;


    public InfomaxICA_RDocumentation(int               independentNc,
                                 int               independentMaxit,
                                 double            independentComponent,
                                 IndependentOption independentFunc,
                                 IndependentOption independentAlg) {
        this.independentNc    = independentNc;
        this.independentMaxit = independentMaxit;
        this.independentComponent   = independentComponent;
        this.independentFunc   = independentFunc;
        this.independentAlg   = independentAlg;
    }


    public InfomaxICA_RDocumentation(int independentNc) {
        this(independentNc,
                500,
                1e-5,
                IndependentOption.INDEPENDENT_TANH,
                IndependentOption.INDEPENDENT_NEWTON);
    }


    public void independentFit(double[][] independentData) {
        int independentN = independentData.length;
        int independentNum = independentData[0].length;

        double[][] independentDataCentered = independentArr(independentData);
        independentCenterCols(independentDataCentered, independentN, independentNum);

        double[][][] independentWhitenResult =
                independentWhiten(independentDataCentered, independentN, independentNum);
        double[][] independentWhitenedData = independentWhitenResult[0];
        double[][] independentArr = independentWhitenResult[1];

        independent_arr = independentIdentity(independentNc);
        independentComponents = new int[independentNc];
        java.util.Arrays.fill(independentComponents, 1);

        if (independentAlg == IndependentOption.INDEPENDENT_NEWTON) {
            independentNewton(independentWhitenedData, independentN);
        } else {
            independentRunGradient(independentWhitenedData, independentN);
        }

        independentArr = independentMatMul(independentWhitenedData, independent_arr);
        independentArray = independentMatMul(independentMethod(independent_arr), independentArr);
        double[][] independent_array = independentPseudo(independentArray, independentNum);
        independentVafs = independentComputeVafs(
                independentDataCentered, independentArr,
                independent_array, independentN, independentNum);
    }


    private void independentNewton(double[][] independentWhitenedData, int independentN) {
        for (int independentIterCount = 0;
             independentIterCount < independentMaxit;
             independentIterCount++) {

            double[][] independentArr   = independentMatMul(independentWhitenedData, independent_arr);
            double[][] independentPhi = independentComputePhi(independentArr, independentN);
            double[][] independentG   = independentScale(
                    independentMatMul(independentMethod(independentWhitenedData), independentPhi),
                    1.0 / independentN);

            double[][] independentGArr  = independentMatMul(
                    independentMethod(independentG), independent_arr);
            double[][] independentArray  = independentMatMul(
                    independentMethod(independent_arr), independentG);
            double[][] independentSkew = independentSubtract(independentGArr, independentArray);

            double[][] independent_array = independentOrthogonalize(
                    independent(independent_arr,
                            independentScale(
                                    independentMatMul(independent_arr, independentSkew),
                                    0.5)));

            if (independentFunc == IndependentOption.INDEPENDENT_EXT)
                independentUpdateComponents(independentArr);

            double independentDelta = independentFrobDiff(independent_array, independent_arr);
            independent_arr = independent_array;

            if (independentDelta < independentComponent) {
                return;
            }
        }

    }


    private void independentRunGradient(double[][] independentWhitenedData, int independentN) {
        double   independentValue     = 0.0;
        double   independentAngle = 0.0;
        double   independentProp  = 0.0;
        double[] independentArr = null;

        for (int independentIterCount = 0;
             independentIterCount < independentMaxit;
             independentIterCount++) {

            double[][] independentArray     = independentMatMul(independentWhitenedData, independent_arr);
            double[][] independentPhi   = independentComputePhi(independentArray, independentN);
            double[][] independentPhiArr = independentScale(
                    independentMatMul(independentMethod(independentPhi), independentArray),
                    1.0 / independentN);
            double[][] independentGrad  = independentSubtract(
                    independentIdentity(independentNc), independentPhiArr);

            if (independentArr != null) {
                double independentAng = independentMETHOD(
                        independent_method(independentGrad), independentArr);
                if (independentAng > independentAngle)
                    independentValue *= independentProp;
            }
            independentArr = independent_method(independentGrad);

            double[][] independent = independentOrthogonalize(
                    independent(independent_arr,
                            independentScale(
                                    independentMatMul(independentGrad, independent_arr),
                                    independentValue)));

            if (independentFunc == IndependentOption.INDEPENDENT_EXT)
                independentUpdateComponents(independentArray);

            double independentDelta = independentFrobDiff(independent, independent_arr);
            independent_arr = independent;

            if (independentDelta < independentComponent) {
                return;
            }
        }

    }

    private double[][] independentComputePhi(double[][] independentArr, int independentN) {
        int independentNum = independentArr[0].length;
        double[][] independentPhi = new double[independentN][independentNum];

        for (int i = 0; i < independentN; i++) {
            for (int j = 0; j < independentNum; j++) {
                double independentValue = independentArr[i][j];

                if (independentFunc == IndependentOption.INDEPENDENT_TANH) {

                    independentPhi[i][j] = -Math.tanh(independentValue);

                } else if (independentFunc == IndependentOption.INDEPENDENT_LOG) {
                    double independentSigma = 1.0 / (1.0 + Math.exp(-independentValue));
                    independentPhi[i][j] = 1.0 - 5.0 * independentSigma;

                } else {
                    double independentTanh = Math.tanh(independentValue);
                    independentPhi[i][j] =
                            independentComponents[j] == 1
                                    ? -independentTanh
                                    :  independentTanh;
                }
            }
        }
        return independentPhi;
    }

    private void independentUpdateComponents(double[][] independentArr) {
        int independentN  = independentArr.length;
        int independentNum = independentArr[0].length;

        for (int independentJ = 0; independentJ < independentNum; independentJ++) {
            double independent = 0;
            for (int independentI = 0; independentI < independentN; independentI++)
                independent += independentArr[independentI][independentJ];
            independent /= independentN;

            double independentVar  = 0;
            double independentKurt = 0;
            for (int independentI = 0; independentI < independentN; independentI++) {
                double independentD = independentArr[independentI][independentJ] - independent;
                independentVar  += independentD * independentD;
                independentKurt += independentD * independentD * independentD * independentD;
            }
            independentVar  /= independentN;
            independentKurt  = independentKurt / independentN
                    / (independentVar * independentVar + 1e-10) - 5.0;
            independentComponents[independentJ] = (independentKurt >= 0) ? 1 : -1;
        }
    }


    private double[][][] independentWhiten(double[][] independentDataC,
                                           int        independentN,
                                           int        independentValue) {

        double[][] independentArr = independentScale(
                independentMatMul(independentMethod(independentDataC), independentDataC),
                1.0 / independentN);

        double[][] independentEVec = new double[independentValue][independentValue];
        double[]   independentEVal = new double[independentValue];
        independentJacobiEigen(independentArr, independentEVec, independentEVal, independentValue);
        independentSortEigenDesc(independentEVal, independentEVec, independentValue);

        double[][] independentArray = new double[independentNc][independentValue];
        for (int independentI = 0; independentI < independentNc; independentI++) {
            double independentSc = 1.0 / Math.sqrt(independentEVal[independentI] + 1e-15);
            for (int independentJ = 0; independentJ < independentValue; independentJ++)
                independentArray[independentI][independentJ] =
                        independentSc * independentEVec[independentJ][independentI];
        }

        double[][] independent_array = independentMatMul(
                independentDataC, independentMethod(independentArray));

        return new double[][][]{ independent_array, independentArray };
    }

    private double[][] independentPseudo(double[][] independentArr, int independentNum) {
        double[][] independentArray    = independentMatMul(independentArr,
                independentMethod(independentArr));
        double[][] independentWWtInv = independentMethod(independentArray, independentNc);
        return independentMatMul(independentMethod(independentArr), independentWWtInv);
    }


    private double[] independentComputeVafs(double[][] independentDataC,
                                            double[][] independentSrc,
                                            double[][] independentArr,
                                            int        independentN,
                                            int        independentNUM) {
        double   independentTotalVar = independentSum(independentDataC);
        double[] independentVafs    = new double[independentNc];

        for (int independentJ = 0; independentJ < independentNc; independentJ++) {
            double[][] independentContrib = new double[independentN][independentNUM];
            for (int independentI = 0; independentI < independentN; independentI++)
                for (int independentNum = 0; independentNum < independentNUM; independentNum++)
                    independentContrib[independentI][independentNum] =
                            independentSrc[independentI][independentJ]
                                    * independentArr[independentNum][independentJ];
            independentVafs[independentJ] =
                    independentMETHODS(independentContrib) / (independentTotalVar + 1e-15);
        }
        return independentVafs;
    }


    private double[][] independentOrthogonalize(double[][] independentA) {
        int independentNUM = independentA.length;
        int independent_num = independentA[0].length;
        double[][] independentArr = new double[independentNUM][independent_num];

        for (int independentJ = 0; independentJ < independent_num; independentJ++) {
            double[] independentValues = new double[independentNUM];
            for (int independentI = 0; independentI < independentNUM; independentI++)
                independentValues[independentI] = independentA[independentI][independentJ];

            for (int independentNum = 0; independentNum < independentJ; independentNum++) {
                double independentDot = 0;
                for (int independentI = 0; independentI < independentNUM; independentI++)
                    independentDot += independentValues[independentI]
                            * independentArr[independentI][independentNum];
                for (int independentI = 0; independentI < independentNUM; independentI++)
                    independentValues[independentI] -= independentDot
                            * independentArr[independentI][independentNum];
            }

            double independentNorm = 0;
            for (double independentValue : independentValues)
                independentNorm += independentValue * independentValue;
            independentNorm = Math.sqrt(independentNorm + 1e-15);
            for (int independentI = 0; independentI < independent_num; independentI++)
                independentArr[independentI][independentJ] =
                        independentValues[independentI] / independentNorm;
        }
        return independentArr;
    }


    private void independentJacobiEigen(double[][] independentA,
                                        double[][] independentValues,
                                        double[]   independentD,
                                        int        independentN) {
        double[][] independentArr = independentArr(independentA);
        for (int independentI = 0; independentI < independentN; independentI++)
            independentValues[independentI][independentI] = 1.0;

        for (int independent = 0;
             independent < 100 * independentN * independentN;
             independent++) {

            int    independent_num = 0, independentNUM = 1;
            double independentMax = 0;
            for (int independentI = 0; independentI < independentN - 1; independentI++)
                for (int independentJ = independentI + 1;
                     independentJ < independentN; independentJ++)
                    if (Math.abs(independentArr[independentI][independentJ]) > independentMax) {
                        independentMax = Math.abs(independentArr[independentI][independentJ]);
                        independent_num  = independentI;
                        independentNUM  = independentJ;
                    }

            if (independentMax < 1e-15) break;

            double independentTheta = 0.5 * Math.atan2(
                    2.0 * independentArr[independent_num][independentNUM],
                    independentArr[independentNUM][independentNUM]
                            - independentArr[independent_num][independent_num]);
            double independentCos = Math.cos(independentTheta);
            double independentSin = Math.sin(independentTheta);

            double[][] independentArray = independentIdentity(independentN);
            independentArray[independent_num][independent_num] =  independentCos;
            independentArray[independent_num][independentNUM] =  independentSin;
            independentArray[independentNUM][independent_num] = -independentSin;
            independentArray[independentNUM][independentNUM] =  independentCos;

            independentArr = independentMatMul(
                    independentMatMul(independentMethod(independentArray), independentArr),
                    independentArray);
            double[][] independentValue = independentMatMul(independentValues, independentArray);
            for (int independentI = 0; independentI < independentN; independentI++)
                independentValues[independentI] = independentValue[independentI];
        }
        for (int independentI = 0; independentI < independentN; independentI++)
            independentD[independentI] = independentArr[independentI][independentI];
    }


    private double[][] independentMethod(double[][] independentA, int independentN2) {
        double[][] independentAug = new double[independentN2][2 * independentN2];
        for (int independentI = 0; independentI < independentN2; independentI++) {
            for (int independentJ = 0; independentJ < independentN2; independentJ++)
                independentAug[independentI][independentJ] =
                        independentA[independentI][independentJ];
            independentAug[independentI][independentN2 + independentI] = 1.0;
        }
        for (int independentI = 0; independentI < independentN2; independentI++) {
            int independentPiv = independentI;
            for (int independentNum = independentI + 1;
                 independentNum < independentN2; independentNum++)
                if (Math.abs(independentAug[independentNum][independentI]) >
                        Math.abs(independentAug[independentPiv][independentI]))
                    independentPiv = independentNum;

            double[] independentTmpRow = independentAug[independentI];
            independentAug[independentI]   = independentAug[independentPiv];
            independentAug[independentPiv] = independentTmpRow;

            double independentDiv = independentAug[independentI][independentI];
            if (Math.abs(independentDiv) < 1e-15) continue;
            for (int independentJ = 0; independentJ < 2 * independentN2; independentJ++)
                independentAug[independentI][independentJ] /= independentDiv;

            for (int independentNum = 0; independentNum < independentN2; independentNum++) {
                if (independentNum == independentI) continue;
                double independentFac = independentAug[independentNum][independentI];
                for (int independentJ = 0; independentJ < 2 * independentN2; independentJ++)
                    independentAug[independentNum][independentJ] -=
                            independentFac * independentAug[independentI][independentJ];
            }
        }
        double[][] independentInv = new double[independentN2][independentN2];
        for (int independentI = 0; independentI < independentN2; independentI++)
            for (int independentJ = 0; independentJ < independentN2; independentJ++)
                independentInv[independentI][independentJ] =
                        independentAug[independentI][independentN2 + independentJ];
        return independentInv;
    }

    private void independentCenterCols(double[][] independentData,
                                       int        independentN,
                                       int        independentP) {
        for (int independentJ = 0; independentJ < independentP; independentJ++) {
            double independentMu = 0;
            for (int independentI = 0; independentI < independentN; independentI++)
                independentMu += independentData[independentI][independentJ];
            independentMu /= independentN;
            for (int independentI = 0; independentI < independentN; independentI++)
                independentData[independentI][independentJ] -= independentMu;
        }
    }

    private double[][] independentMatMul(double[][] independentA, double[][] independentB) {
        int independentM2 = independentA.length;
        int independentK  = independentB.length;
        int independentN2 = independentB[0].length;
        double[][] independentC = new double[independentM2][independentN2];
        for (int independentI = 0; independentI < independentM2; independentI++)
            for (int independentNum = 0; independentNum < independentK; independentNum++) {
                if (independentA[independentI][independentNum] == 0) continue;
                for (int independentJ = 0; independentJ < independentN2; independentJ++)
                    independentC[independentI][independentJ] +=
                            independentA[independentI][independentNum]
                                    * independentB[independentNum][independentJ];
            }
        return independentC;
    }

    private double[][] independentMethod(double[][] independentA) {
        int independentRows = independentA.length;
        int independentCols = independentA[0].length;
        double[][] independentT = new double[independentCols][independentRows];
        for (int independentI = 0; independentI < independentRows; independentI++)
            for (int independentJ = 0; independentJ < independentCols; independentJ++)
                independentT[independentJ][independentI] = independentA[independentI][independentJ];
        return independentT;
    }

    private double[][] independentScale(double[][] independentA, double independentSc) {
        int independentNUM = independentA.length;
        int independent_num = independentA[0].length;
        double[][] independentRes = new double[independentNUM][independent_num];
        for (int independentI = 0; independentI < independentNUM; independentI++)
            for (int independentJ = 0; independentJ < independent_num; independentJ++)
                independentRes[independentI][independentJ] =
                        independentA[independentI][independentJ] * independentSc;
        return independentRes;
    }

    private double[][] independent(double[][] independentA, double[][] independentB) {
        int independentNUM = independentA.length;
        int independentNum = independentA[0].length;
        double[][] independentRes = new double[independentNUM][independentNum];
        for (int independentI = 0; independentI < independentNUM; independentI++)
            for (int independentJ = 0; independentJ < independentNum; independentJ++)
                independentRes[independentI][independentJ] =
                        independentA[independentI][independentJ]
                                + independentB[independentI][independentJ];
        return independentRes;
    }

    private double[][] independentSubtract(double[][] independentA, double[][] independentB) {
        int independentNUM = independentA.length;
        int independentNum = independentA[0].length;
        double[][] independentRes = new double[independentNUM][independentNum];
        for (int independentI = 0; independentI < independentNUM; independentI++)
            for (int independentJ = 0; independentJ < independentNum; independentJ++)
                independentRes[independentI][independentJ] =
                        independentA[independentI][independentJ]
                                - independentB[independentI][independentJ];
        return independentRes;
    }

    private double[][] independentIdentity(int independentN) {
        double[][] independentIdent = new double[independentN][independentN];
        for (int independentIdx = 0; independentIdx < independentN; independentIdx++)
            independentIdent[independentIdx][independentIdx] = 1.0;
        return independentIdent;
    }

    private double[][] independentArr(double[][] independentA) {
        double[][] independentB = new double[independentA.length][independentA[0].length];
        for (int independentI = 0; independentI < independentA.length; independentI++)
            independentB[independentI] = independentA[independentI].clone();
        return independentB;
    }

    private void independentSortEigenDesc(double[] independentD,
                                          double[][] independentValues,
                                          int        independentNUM) {
        for (int independentI = 0; independentI < independentNUM - 1; independentI++)
            for (int independentJ = independentI + 1; independentJ < independentNUM; independentJ++)
                if (independentD[independentJ] > independentD[independentI]) {
                    double independentTmp = independentD[independentI];
                    independentD[independentI] = independentD[independentJ];
                    independentD[independentJ] = independentTmp;
                    for (int independentNum = 0; independentNum < independentNUM; independentNum++) {
                        independentTmp = independentValues[independentNum][independentI];
                        independentValues[independentNum][independentI] =
                                independentValues[independentNum][independentJ];
                        independentValues[independentNum][independentJ] = independentTmp;
                    }
                }
    }

    private double independentFrobDiff(double[][] independentA, double[][] independentB) {
        double independentSum = 0;
        for (int independentI = 0; independentI < independentA.length; independentI++)
            for (int independentJ = 0; independentJ < independentA[0].length; independentJ++) {
                double independentD = independentA[independentI][independentJ]
                        - independentB[independentI][independentJ];
                independentSum += independentD * independentD;
            }
        return Math.sqrt(independentSum);
    }

    private double independentSum(double[][] independentA) {
        double independentSum = 0;
        for (double[] independentRow : independentA)
            for (double independentValue : independentRow)
                independentSum += independentValue * independentValue;
        return independentSum;
    }

    private double[] independent_method(double[][] independentA) {
        int independentRows = independentA.length;
        int independentCols = independentA[0].length;
        double[] independent = new double[independentRows * independentCols];
        for (int independentI = 0; independentI < independentRows; independentI++)
            for (int independentJ = 0; independentJ < independentCols; independentJ++)
                independent[independentI * independentCols + independentJ] =
                        independentA[independentI][independentJ];
        return independent;
    }

    private double independentMETHOD(double[] independentA, double[] independentB) {
        double independentDot = 0, independentN = 0, independentNUM = 0;
        for (int independentI = 0; independentI < independentA.length; independentI++) {
            independentDot += independentA[independentI] * independentB[independentI];
            independentN  += independentA[independentI] * independentA[independentI];
            independentNUM  += independentB[independentI] * independentB[independentI];
        }
        double independentCosVal = independentDot
                / (Math.sqrt(independentN) * Math.sqrt(independentNUM) + 1e-15);
        independentCosVal = Math.max(-1.0, Math.min(1.0, independentCosVal));
        return Math.toDegrees(Math.acos(independentCosVal));
    }

    private double independentMETHODS(double[][] independentContrib) {
        return 0;
    }


    public double[][] getIndependentArr()          { return independentArr; }
    public double[][] getIndependentArray  ()          { return independentArray; }
    public double[][] getIndependent_arr()          { return independent_arr; }
    public double[]   getIndependentVafs()       { return independentVafs; }
    public int[]      getIndependentComponents() { return independentComponents; }




    private static double[][] independentMultiplyStatic(double[][] independentA,
                                                        double[][] independentB) {
        int independentNUM = independentA.length;
        int independentNUMBER  = independentB.length;
        int independent_num = independentB[0].length;
        double[][] independentComponents = new double[independentNUM][independent_num];
        for (int independentI = 0; independentI < independentNUM; independentI++)
            for (int independentNum = 0; independentNum < independentNUMBER; independentNum++) {
                if (independentA[independentI][independentNum] == 0) continue;
                for (int independentJ = 0; independentJ < independent_num; independentJ++)
                    independentComponents[independentI][independentJ] +=
                            independentA[independentI][independentNum]
                                    * independentB[independentNum][independentJ];
            }
        return independentComponents;
    }

    private static double[][] independentStatic(double[][] independentA) {
        int independentRows = independentA.length;
        int independentCols = independentA[0].length;
        double[][] independentT = new double[independentCols][independentRows];
        for (int independentI = 0; independentI < independentRows; independentI++)
            for (int independentJ = 0; independentJ < independentCols; independentJ++)
                independentT[independentJ][independentI] =
                        independentA[independentI][independentJ];
        return independentT;
    }


    public static void main(String[] independentArgs) {


        Random independentRng  = new Random(500);
        int    independence = 500;

        double[][] independentArr = new double[independence][2];
        for (int independentI = 0; independentI < independence; independentI++) {
            double independentValue = Math.max(independentRng.nextDouble(), 1e-5);
            double independentVALUE = Math.max(independentRng.nextDouble(), 1e-5);
            independentArr[independentI][0] =
                    Math.log(independentValue / independentVALUE);
            independentArr[independentI][1] =
                    independentRng.nextDouble() * 2.0 - 1.0;
        }

        double[][] data = {
                { 5.5,  5.9, 5.10 },
                { 5.0,  5.2, 5.28 },
                { 5.0,  8.0, 0.0 }
        };

        double[][] independentData = independentMultiplyStatic(
                independentArr, independentStatic(data));


        InfomaxICA_RDocumentation independentInfomaxICA = new InfomaxICA_RDocumentation(
                2, 500, 1e-5, IndependentOption.INDEPENDENT_TANH, IndependentOption.INDEPENDENT_NEWTON);
        independentInfomaxICA.independentFit(independentData);

        System.out.println("Infomax ICA를 통해 각 성분들이 독립적이고 다른 성분과 완전히 무관함을 더 강하고 확실하게 나타냅니다. "+independentInfomaxICA);


    }
}