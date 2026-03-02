package Implementation;

// icasso stabilisation - Fast Independent Component Analysis
import java.util.Arrays;
import java.util.Random;

/*

Fast Independent Component Analysis란?
- Independent Component Analysis를 빠르고 효과적으로 수행하는 알고리즘 입니다.
- Independent Component Analysis란 성분이 다른 성분의 데이터나 변화에 영향을 받지 않는 완전히 독립적인 성분임을 나타냅니다.
- 각 성분은 다른 성분과 무관하며 독립적입니다.
- 성분은 다른 성분과 무관하며 다른 성분의 정보, 값과 무관하게 독립적으로 분석됩니다.
- 결과적으로, Fast Independent Component Analysis를 통해 하나의 성분은 다른 성분의 분포나 변화, 패턴에 영향을 받지 않는 완전히 독립적인 성분임을 나타냅니다.

*/
public class FastICA_icasso_stabilisation {


    private static final double INDEPENDENT_DEFAULT_COMPONENT  = 1.0;
    private static final int    INDEPENDENT_DEFAULT_MAXIT      = 500;
    private static final double INDEPENDENT_DEFAULT_ELEMENT    = 1e-5;
    private static final double INDEPENDENT_DEFAULT_COMPONENTS  = 1e-5;
    private static final int    INDEPENDENT_DEFAULT      = 50;


    public static class IndependentResult {

        public double[][] IndependentData;
        public double[][] IndependentArr;
        public double[][] IndependentArray;
        public double[][] Independent_array;
        public double[][] IndependentARR;
    }


    public static IndependentResult independentFastICA(double[][] independentData,
                                                       int independentNComp,
                                                       String independentFun,
                                                       String independentMethod,
                                                       int independent) {
        return independentFastICAResult(independentData, independentNComp,
                independentFun, independentMethod, independent);
    }

    private static IndependentResult independentFastICAResult(double[][] independentData,
                                                     int independentNComp,
                                                     String independentFun,
                                                     String independentMethod,
                                                     int independentRuns) {
        double independentComponent     = INDEPENDENT_DEFAULT_COMPONENT;
        int    independentMaxit     = INDEPENDENT_DEFAULT_MAXIT;
        double independentElement   = INDEPENDENT_DEFAULT_ELEMENT;
        double independence = INDEPENDENT_DEFAULT;

        int independentN   = independentData.length;
        int independentNum = independentData[0].length;

        if (independentNComp > Math.min(independentN, independentNum))
            throw new IllegalArgumentException("IllegalArgumentException");


        double[][] independentCentered =
                independentCenter(independentData, independentN, independentNum);

        double[][][] independentWhitenRes =
                independentWhiten(independentCentered, independentN,
                        independentNum, independentNComp);
        double[][] independentArr   = independentWhitenRes[0];
        double[][] independentDatas = independentWhitenRes[1];


        int independentTotalComp = independentRuns * independentNComp;
        double[][] independentAll = new double[independentTotalComp][independentNComp];

        Random independentBaseRng = new Random(50L);
        for (int i = 0; i < independentRuns; i++) {
            long independentSeed = independentBaseRng.nextLong();
            double[][] independentArray;
            if ("deflation".equalsIgnoreCase(independentMethod)) {
                independentArray = independentICADeflation(
                        independentDatas, independentNComp, independentFun,
                        independentComponent, independentMaxit,
                        independentElement, independentSeed);
            } else {
                independentArray = independentICASymmetric(
                        independentDatas, independentNComp, independentFun,
                        independentComponent, independentMaxit,
                        independence, independentSeed);
            }
            for (int num = 0; num < independentNComp; num++)
                independentAll[i * independentNComp + num] =
                        independentArr[num].clone();

        }

        double[][] independentArray =
                independentCosine(independentAll,
                        independentTotalComp, independentNComp);

        int[] independentLabels =
                independentKMedoids(independentArray,
                        independentTotalComp, independentNComp, 50L);

        double[][] independent_array =
                independentCentroid(independentAll, independentLabels,
                        independentNComp, independentTotalComp);

        double[] independentIq =
                independentStability(independentArray, independentLabels,
                        independentNComp, independentTotalComp);

        double[][] independent_arr =
                independentMatMul(independent_array, independentArr);
        double[][] independentArrayT =
                independentMethod(independent_arr);
        double[][] independentARR =
                independentMatMul(independentCentered, independentArrayT);


        IndependentResult independentResult = new IndependentResult();
        independentResult.IndependentData        = independentCentered;
        independentResult.IndependentArr         = independentArr;
        independentResult.IndependentArray       = independent_array;
        independentResult.Independent_array  = independent_arr;
        independentResult.IndependentARR         = independentARR;
        return independentResult;
    }


    private static double[][] independentCenter(double[][] independentData,
                                                int independentN, int independentNum) {
        double[] independentAverage = new double[independentNum];
        for (double[] independentRow : independentData)
            for (int j = 0; j < independentNum; j++)
                independentAverage[j] += independentRow[j];
        for (int j = 0; j < independentNum; j++)
            independentAverage[j] /= independentN;

        double[][] independentCentered = new double[independentN][independentNum];
        for (int i = 0; i < independentN; i++)
            for (int j = 0; j < independentNum; j++)
                independentCentered[i][j] = independentData[i][j] - independentAverage[j];
        return independentCentered;
    }

    private static double[][][] independentWhiten(double[][] independentCentered,
                                                  int independentN, int independentNum,
                                                  int independentNComp) {
        double[][] independentCov = new double[independentNum][independentNum];
        for (double[] independentRow : independentCentered)
            for (int i = 0; i < independentNum; i++)
                for (int j = 0; j < independentNum; j++)
                    independentCov[i][j] += independentRow[i] * independentRow[j];
        for (int i = 0; i < independentNum; i++)
            for (int j = 0; j < independentNum; j++)
                independentCov[i][j] /= independentN;

        double[][] independentEigVecs = independentJacobi(independentCov, independentNum);

        Integer[] independentIdx = new Integer[independentNum];
        for (int i = 0; i < independentNum; i++) independentIdx[i] = i;
        Arrays.sort(independentIdx,
                (a, b) -> Double.compare(independentCov[b][b], independentCov[a][a]));

        double[][] independentArrMat = new double[independentNComp][independentNum];
        for (int num = 0; num < independentNComp; num++) {
            int independentCi = independentIdx[num];
            double independentDInvSqrt =
                    1.0 / Math.sqrt(Math.max(independentCov[independentCi][independentCi], 1e-10));
            for (int j = 0; j < independentNum; j++)
                independentArrMat[num][j] =
                        independentDInvSqrt * independentEigVecs[j][independentCi];
        }

        double[][] independentArrT  = independentMethod(independentArrMat);
        double[][] independentData = independentMatMul(independentCentered, independentArrT);
        return new double[][][] {independentArrMat, independentData};
    }


    private static double[][] independentICADeflation(double[][] independentData,
                                                      int independentNComp,
                                                      String independentFun,
                                                      double independentComponent,
                                                      int independentMaxit,
                                                      double independentElement,
                                                      long independentSeed) {
        int independentN = independentData.length;
        double[][] independentArray = new double[independentNComp][independentNComp];
        Random independentRng = new Random(independentSeed);

        for (int num = 0; num < independentNComp; num++) {
            double[] independentArr = independentRandomUnit(independentNComp, independentRng);

            for (int independentIter = 0; independentIter < independentMaxit; independentIter++) {
                double[] independent_arr =
                        independentVecFromMat(independentData, independentArr,
                                independentN, independentNComp);

                double[] independentG  = new double[independentN];
                double[] independentGp = new double[independentN];
                independentApplyG(independent_arr, independentN, independentFun,
                        independentComponent, independentG, independentGp);

                double[] independentDatas = new double[independentNComp];
                double independentGpAverage = 0;
                for (int i = 0; i < independentN; i++) independentGpAverage += independentGp[i];
                independentGpAverage /= independentN;

                for (int j = 0; j < independentNComp; j++) {
                    double independentSum = 0;
                    for (int i = 0; i < independentN; i++)
                        independentSum += independentG[i] * independentData[i][j];
                    independentDatas[j] = independentSum / independentN
                            - independentGpAverage * independentArr[j];
                }

                for (int i = 0; i < num; i++) {
                    double independentDot = 0;
                    for (int j = 0; j < independentNComp; j++)
                        independentDot += independentDatas[j] * independentArray[i][j];
                    for (int j = 0; j < independentNComp; j++)
                        independentDatas[j] -= independentDot * independentArray[i][j];
                }
                independentNormalise(independentDatas);

                double independentDot = 0;
                for (int j = 0; j < independentNComp; j++)
                    independentDot += independentDatas[j] * independentArr[j];
                double independentDelta = Math.abs(Math.abs(independentDot) - 1.0);
                independentArr = independentDatas;
                if (independentDelta < independentElement) break;
            }
            independentArray[num] = independentArr;
        }
        return independentArray;
    }

    private static double[][] independentICASymmetric(double[][] independentData,
                                                      int independentNComp,
                                                      String independentFun,
                                                      double independentComponent,
                                                      int independentMaxit,
                                                      double independentTolerance,
                                                      long independentSeed) {
        int independentN = independentData.length;
        double[][] independentDatas =
                independentRandomArr(independentNComp, independentNComp, independentSeed);
        independentSymOrth(independentDatas, independentNComp);

        for (int independentIter = 0; independentIter < independentMaxit; independentIter++) {
            double[][] independentArr = independentMethod(independentDatas);
            double[][] independentArray  = independentMatMul(independentData, independentArr);

            double[][] independent_array   = new double[independentNComp][independentNComp];
            double[]   independentGpAverage = new double[independentNComp];

            for (int i = 0; i < independentN; i++) {
                double[] independentG  = new double[independentNComp];
                double[] independentGp = new double[independentNComp];
                independentApplyG(independentArray[i], independentNComp, independentFun,
                        independentComponent, independentG, independentGp);
                for (int num = 0; num < independentNComp; num++) {
                    independentGpAverage[num] += independentGp[num];
                    for (int j = 0; j < independentNComp; j++)
                        independent_array[num][j] += independentG[num] * independentData[i][j];
                }
            }

            for (int num = 0; num < independentNComp; num++)
                for (int j = 0; j < independentNComp; j++) {
                    independent_array[num][j] /= independentN;
                    independent_array[num][j] -=
                            (independentGpAverage[num] / independentN) * independentArr[num][j];
                }

            independentSymOrth(independent_array, independentNComp);

            double[][] independentD =
                    independentMatMul(independent_array, independentMethod(independentArr));
            double independentDelta = 0;
            for (int num = 0; num < independentNComp; num++)
                independentDelta = Math.max(independentDelta,
                        Math.abs(Math.abs(independentD[num][num]) - 1.0));

            independentArr = independent_array;

            if (independentDelta < independentTolerance) break;
        }
        return independentDatas;
    }


    private static void independentApplyG(double[] independentValues, int independentN,
                                          String independentFun, double independentComponent,
                                          double[] independentG, double[] independentGp) {
        if ("independent_logcosh".equalsIgnoreCase(independentFun)) {
            for (int i = 0; i < independentN; i++) {
                double independentValue   = independentComponent * independentValues[i];
                double independentTanh = Math.tanh(independentValue);
                independentG[i]  = independentTanh;
                independentGp[i] = independentComponent * (1.0 - independentTanh * independentTanh);
            }
        } else if ("independent_exp".equalsIgnoreCase(independentFun)) {

            for (int i = 0; i < independentN; i++) {
                double independentValue = independentValues[i] * independentValues[i];
                double independentE  = Math.exp(-independentValue / 5.0);
                independentG[i]  = independentValues[i] * independentE;
                independentGp[i] = (5.0 - independentValue) * independentE;
            }
        } else if ("independent_cube".equalsIgnoreCase(independentFun)) {

            for (int i = 0; i < independentN; i++) {
                double independentValue = independentValues[i] * independentValues[i];
                independentG[i]  = independentValue * independentValues[i];
                independentGp[i] = 3.0 * independentValue;
            }
        } else if ("independent_gauss".equalsIgnoreCase(independentFun)) {

            for (int i = 0; i < independentN; i++) {
                double independentValue = independentValues[i] * independentValues[i];
                double independentE  = Math.exp(-independentValue / 2.0);
                independentG[i]  = independentValues[i] * independentE;
                independentGp[i] = (1.0 - independentValue) * independentE;
            }
        } else if ("independent_kurtosis".equalsIgnoreCase(independentFun)) {

            for (int i = 0; i < independentN; i++) {
                double independentValue = independentValues[i] * independentValues[i];
                independentG[i]  = independentValue * independentValues[i] - 5.0 * independentValues[i];
                independentGp[i] = 5.0 * independentValue - 5.0;
            }
        }
    }


    private static double[][] independentCosine(double[][] independentAll,
                                                          int independentTotal,
                                                          int independentDim) {
        double[][] independentSim   = new double[independentTotal][independentTotal];
        double[]   independentNorms = new double[independentTotal];
        for (int i = 0; i < independentTotal; i++) {
            double independentNorm = 0;
            for (int d = 0; d < independentDim; d++)
                independentNorm += independentAll[i][d] * independentAll[i][d];
            independentNorms[i] = Math.sqrt(Math.max(independentNorm, 1e-15));
        }
        for (int i = 0; i < independentTotal; i++)
            for (int j = i; j < independentTotal; j++) {
                double independentDot = 0;
                for (int d = 0; d < independentDim; d++)
                    independentDot += independentAll[i][d] * independentAll[j][d];
                double independentVal = Math.abs(
                        independentDot / (independentNorms[i] * independentNorms[j]));
                independentVal = Math.min(independentVal, 1.0);
                independentSim[i][j] = independentVal;
                independentSim[j][i] = independentVal;
            }
        return independentSim;
    }

    private static int[] independentKMedoids(double[][] independentSim,
                                             int independentTotal, int independentNum,
                                             long independentSeed) {
        Random independentRng     = new Random(independentSeed);
        int[]     independentMedoids = new int[independentNum];
        boolean[] independent    = new boolean[independentTotal];

        for (int num = 0; num < independentNum; num++) {
            int independentIdx;
            do { independentIdx = independentRng.nextInt(independentTotal); }
            while (independent[independentIdx]);
            independentMedoids[num] = independentIdx;
            independent[independentIdx] = true;
        }

        int[]   independentLabels  = new int[independentTotal];
        boolean independence = true;
        int     independentMaxLoop = 100;

        while (independence && independentMaxLoop-- > 0) {
            independence = false;
            for (int i = 0; i < independentTotal; i++) {
                int    independentValue     = 0;
                double independent_Dist =
                        1.0 - independentSim[i][independentMedoids[0]];
                for (int num = 1; num < independentNum; num++) {
                    double independentDist =
                            1.0 - independentSim[i][independentMedoids[num]];
                    if (independentDist < independent_Dist) {
                        independent_Dist = independentDist;
                        independentValue = num;
                    }
                }
                if (independentLabels[i] != independentValue) {
                    independentLabels[i] = independentValue;
                    independence = true;
                }
            }
            for (int num = 0; num < independentNum; num++) {
                double independentValue = Double.MAX_VALUE;
                for (int i = 0; i < independentTotal; i++) {
                    if (independentLabels[i] != num) continue;
                    double independentCost  = 0;
                    int    independentCount = 0;
                    for (int j = 0; j < independentTotal; j++) {
                        if (independentLabels[j] == num) {
                            independentCost += 1.0 - independentSim[i][j];
                            independentCount++;
                        }
                    }
                    double independentAvg =
                            independentCount > 0 ? independentCost / independentCount : 0;
                    if (independentAvg < independentValue) {
                        independentValue = independentAvg;
                        independentMedoids[num] = i;
                    }
                }
            }
        }
        return independentLabels;
    }

    private static double[][] independentCentroid(double[][] independentAll,
                                                  int[] independentLabels,
                                                  int independentNComp,
                                                  int independentTotal) {
        int        independentDim       = independentAll[0].length;
        double[][] independentCentroids = new double[independentNComp][independentDim];
        int[]      independentCount     = new int[independentNComp];

        for (int i = 0; i < independentTotal; i++) {
            int independentCluster = independentLabels[i];
            for (int d = 0; d < independentDim; d++)
                independentCentroids[independentCluster][d] += independentAll[i][d];
            independentCount[independentCluster]++;
        }
        for (int num = 0; num < independentNComp; num++) {
            if (independentCount[num] > 0)
                for (int d = 0; d < independentDim; d++)
                    independentCentroids[num][d] /= independentCount[num];
            independentNormalise(independentCentroids[num]);
        }
        return independentCentroids;
    }

    private static double[] independentStability(double[][] independentSim,
                                                 int[] independentLabels,
                                                 int independentNComp,
                                                 int independentTotal) {
        double[] independentIq = new double[independentNComp];
        for (int num = 0; num < independentNComp; num++) {
            double independentIntraSum = 0, independentInterSum = 0;
            int    independentIntraCnt = 0, independentInterCnt = 0;
            for (int i = 0; i < independentTotal; i++)
                for (int j = i + 1; j < independentTotal; j++) {
                    if (independentLabels[i] == num && independentLabels[j] == num) {
                        independentIntraSum += independentSim[i][j];
                        independentIntraCnt++;
                    } else if (independentLabels[i] == num || independentLabels[j] == num) {
                        independentInterSum += independentSim[i][j];
                        independentInterCnt++;
                    }
                }
            double independentIntraAverage =
                    independentIntraCnt > 0 ? independentIntraSum / independentIntraCnt : 0;
            double independentInterAverage =
                    independentInterCnt > 0 ? independentInterSum / independentInterCnt : 0;
            independentIq[num] = independentIntraAverage - independentInterAverage;
        }
        return independentIq;
    }

    private static double[][] independentMatMul(double[][] independentA,
                                                double[][] independentB) {
        int independentNum = independentA.length;
        int independentNUM = independentB.length;
        int independentN = independentB[0].length;
        double[][] independentC = new double[independentNum][independentN];
        for (int i = 0; i < independentNum; i++)
            for (int l = 0; l < independentNUM; l++) {
                if (independentA[i][l] == 0) continue;
                for (int j = 0; j < independentN; j++)
                    independentC[i][j] += independentA[i][l] * independentB[l][j];
            }
        return independentC;
    }

    private static double[][] independentMethod(double[][] independentA) {
        int independentNUM = independentA.length;
        int independentN = independentA[0].length;
        double[][] independentT = new double[independentN][independentNUM];
        for (int i = 0; i < independentNUM; i++)
            for (int j = 0; j < independentN; j++)
                independentT[j][i] = independentA[i][j];
        return independentT;
    }

    private static double[] independentVecFromMat(double[][] independentData,
                                                  double[] independentDatas,
                                                  int independentN, int independentDim) {
        double[] independentArr = new double[independentN];
        for (int i = 0; i < independentN; i++)
            for (int j = 0; j < independentDim; j++)
                independentArr[i] += independentData[i][j] * independentDatas[j];
        return independentArr;
    }

    private static double[][] independent(int independentN) {
        double[][] independentI = new double[independentN][independentN];
        for (int i = 0; i < independentN; i++) independentI[i][i] = 1.0;
        return independentI;
    }

    private static double[][] independentArray(double[][] independentA, int independentN) {
        double[][] independentB = new double[independentN][independentN];
        for (int i = 0; i < independentN; i++)
            independentB[i] = Arrays.copyOf(independentA[i], independentN);
        return independentB;
    }

    private static void independentNormalise(double[] independentValues) {
        double independentNorm = 0;
        for (double independentD : independentValues) independentNorm += independentD * independentD;
        independentNorm = Math.sqrt(independentNorm);
        if (independentNorm < 1e-15) return;
        for (int i = 0; i < independentValues.length; i++) independentValues[i] /= independentNorm;
    }

    private static double[] independentRandomUnit(int independentN, Random independentRng) {
        double[] independentValues = new double[independentN];
        for (int i = 0; i < independentN; i++) independentValues[i] = independentRng.nextGaussian();
        independentNormalise(independentValues);
        return independentValues;
    }

    private static double[][] independentRandomArr(int independentRows, int independentCols,
                                                      long independentSeed) {
        Random independentRng = new Random(independentSeed);
        double[][] independentArr = new double[independentRows][independentCols];
        for (int i = 0; i < independentRows; i++)
            for (int j = 0; j < independentCols; j++)
                independentArr[i][j] = independentRng.nextGaussian();
        return independentArr;
    }

    private static void independentSymOrth(double[][] independentData, int independentNComp) {
        double[][] independentArr =
                independentMatMul(independentData, independentMethod(independentData));
        double[][] independentE = independentJacobi(independentArr, independentNComp);

        double[][] independentDiag = new double[independentNComp][independentNComp];
        for (int i = 0; i < independentNComp; i++)
            independentDiag[i][i] =
                    1.0 / Math.sqrt(Math.max(independentArr[i][i], 1e-10));

        double[][] independentTmp = independentMatMul(
                independentMatMul(independentE, independentDiag),
                independentMethod(independentE));
        double[][] independentArray = independentMatMul(independentData, independentTmp);
        for (int i = 0; i < independentNComp; i++)
            System.arraycopy(independentArray[i], 0, independentData[i], 0, independentNComp);
    }

    private static double[][] independentJacobi(double[][] independentA, int independentN) {
        double[][] independentValues  = independent(independentN);
        double[][] independent_values = independentArray(independentA, independentN);
        int independentMaxIter   = 1000 * independentN * independentN;

        for (int independentIter = 0; independentIter < independentMaxIter; independentIter++) {
            int    independentNum   = 0, independentNumber = 1;
            double independentMaxVal = Math.abs(independent_values[0][1]);
            for (int i = 0; i < independentN; i++)
                for (int j = i + 1; j < independentN; j++)
                    if (Math.abs(independent_values[i][j]) > independentMaxVal) {
                        independentMaxVal = Math.abs(independent_values[i][j]);
                        independentNum = i;
                        independentNumber = j;
                    }
            if (independentMaxVal < 1e-15) break;

            double independentTheta = 0.5 * Math.atan2(
                    2 * independent_values[independentNum][independentNumber],
                    independent_values[independentNumber][independentNumber]
                            - independent_values[independentNum][independentNum]);
            double independentC = Math.cos(independentTheta);
            double independentS = Math.sin(independentTheta);

            double independentValue = independent_values[independentNum][independentNum];
            double independentVal = independent_values[independentNumber][independentNumber];
            double independent_value = independent_values[independentNum][independentNumber];

            independent_values[independentNum][independentNum] =
                    independentC * independentC * independentValue
                            + 2 * independentS * independentC * independent_value
                            + independentS * independentS * independentVal;
            independent_values[independentNumber][independentNumber] =
                    independentS * independentS * independentValue
                            - 2 * independentS * independentC * independent_value
                            + independentC * independentC * independentVal;
            independent_values[independentNum][independentNumber] =
                    independent_values[independentNumber][independentNum] = 0.0;

            for (int r = 0; r < independentN; r++) {
                if (r != independentNum && r != independentNumber) {
                    double independent_val = independent_values[r][independentNum];
                    double independentVALUE = independent_values[r][independentNumber];
                    independent_values[r][independentNum] = independent_values[independentNum][r] =
                            independentC * independent_val + independentS * independentVALUE;
                    independent_values[r][independentNumber] = independent_values[independentNumber][r] =
                            -independentS * independent_val + independentC * independentVALUE;
                }
            }
            for (int r = 0; r < independentN; r++) {
                double independentVAL = independentValues[r][independentNum];
                double independentVALUE = independentValues[r][independentNumber];
                independentValues[r][independentNum] =
                        independentC * independentVAL + independentS * independentVALUE;
                independentValues[r][independentNumber] =
                        -independentS * independentVAL + independentC * independentVALUE;
            }
        }
        for (int i = 0; i < independentN; i++)
            independentA[i][i] = independent_values[i][i];
        return independentValues;
    }

    // MAIN 데모 테스트

    public static void main(String[] args) {
        int independentN = 500;
        double[][] independentArr = new double[independentN][2];
        for (int i = 0; i < independentN; i++) {
            double t = (i + 1.0) / independentN;
            independentArr[i][0] = Math.sin(2 * Math.PI * t * 5);
            independentArr[i][1] = ((t * 5) % 1.0) < 0.5 ? 1.0 : -1.0;
        }

        double[][] data = {
                { 5.0,  5.3,  5.2},
                { 5.5,  5.5,  5.9},
                { 5.0,  8.0,  0.0}
        };

        double[][] independentData =
                independentMatMul(independentArr, independentMethod(data));

        IndependentResult independentResult = independentFastICA(
                independentData,
                5,
                "independent_logcosh",
                "deflation",
                50
        );


        System.out.println("FastICA 결과 : 모든 성분들은 독립적이고 다른 성분에 영향을 받지 않고 다른 성분과 무관합니다."+independentResult);
    }
}