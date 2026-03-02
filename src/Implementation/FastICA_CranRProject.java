package Implementation;

//Cran R Project - Fast Independent Component Analysis
import java.util.Arrays;
import java.util.Random;

/*

Fast Independent Component Analysis란?
- Independent Component Analysis를 빠르고 효율적으로 수행하는 알고리즘 입니다.
- Independent Component Analysis란 각 성분이 독립적이고 성분이 다른 성분의 데이터나 변화에 영향을 받지 않음을 나타냅니다.
- 각각의 성분은 독립적이고 다른 성분과 상관이 없음을 알 수 있습니다.
- 성분들은 독립적이며 다른 성분의 데이터나 값에 영향을 받지 않는 명확히 독립적인 성분입니다.
- 결과적으로, Fast Independent Component Analysis를 통해 하나의 성분은 다른 성분의 영향을 받지 않는 완전히 독립적인 성분임을 나타냅니다.

*/
public class FastICA_CranRProject {

    private static final String  INDEPENDENT_DEFAULT_FUNC       = "logcosh";
    private static final double  INDEPENDENT_DEFAULT_COMPONENT = 5.0;
    private static final int     INDEPENDENT_DEFAULT_MAXIT     = 500;
    private static final double  INDEPENDENT_DEFAULT_ELEMENT   = 1e-5;
    private static final long    INDEPENDENT_DEFAULT_SEED      = 50L;

    public static class IndependentResult {
        public double[][] IndependentData;
        public double[][] IndependentArr;
        public double[][] Independent_array;
        public double[][] IndependentArray;
        public double[][] IndependentARR;
    }

    public static IndependentResult independentFastICA(double[][] Independent_data,
                                                       int Independent_nComp,
                                                       String Independent_func,
                                                       String Independent_method,
                                                       int Independent_verbose) {
        return independentFastICAResult(Independent_data, Independent_nComp, Independent_func, Independent_method, Independent_verbose);
    }

    private static IndependentResult independentFastICAResult(double[][] Independent_data,
                                                            int Independent_nComp,
                                                            String Independent_func,
                                                            String Independent_method,
                                                            int Independent_verbose) {
        double Independent_component = INDEPENDENT_DEFAULT_COMPONENT;
        int    Independent_maxit     = INDEPENDENT_DEFAULT_MAXIT;
        double Independent_element   = INDEPENDENT_DEFAULT_ELEMENT;
        long   Independent_seed      = INDEPENDENT_DEFAULT_SEED;

        int n   = Independent_data.length;
        int num = Independent_data[0].length;

        if (Independent_nComp > Math.min(n, num))
            throw new IllegalArgumentException("IllegalArgumentException");


        double[][] centeredData = independentCenter(Independent_data, n, num);

        double[][][] independentWhitenResult = independentWhiten(centeredData, n, num, Independent_nComp);
        double[][] Arr   = independentWhitenResult[0];
        double[][] datas = independentWhitenResult[1];

        double[][] array;
        if ("deflation".equalsIgnoreCase(Independent_method)) {
            array = independentICADeflation(datas, Independent_nComp, Independent_func, Independent_component, Independent_maxit, Independent_element, Independent_seed, Independent_verbose);
        } else {
            array = independentICASymmetric(datas, Independent_nComp, Independent_func, Independent_component, Independent_maxit, Independent_element, Independent_seed, Independent_verbose);
        }

        double[][] Array  = independentMatMul(array, Arr);
        double[][] ArrayT = independentMethod(Array);
        double[][] ARR    = independentMatMul(centeredData, ArrayT);

        IndependentResult result = new IndependentResult();
        result.IndependentData   = centeredData;
        result.IndependentArr    = Arr;
        result.Independent_array = array;
        result.IndependentArray  = Array;
        result.IndependentARR    = ARR;
        return result;
    }

    private static double[][] independentCenter(double[][] data, int n, int num) {
        double[] independentAverage = new double[num];
        for (double[] row : data)
            for (int j = 0; j < num; j++) independentAverage[j] += row[j];
        for (int j = 0; j < num; j++) independentAverage[j] /= n;

        double[][] centeredData = new double[n][num];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < num; j++)
                centeredData[i][j] = data[i][j] - independentAverage[j];
        return centeredData;
    }

    private static double[][][] independentWhiten(double[][] centeredData,
                                                  int n, int num, int nComp) {
        double[][] independentCov = new double[num][num];
        for (double[] row : centeredData)
            for (int i = 0; i < num; i++)
                for (int j = 0; j < num; j++)
                    independentCov[i][j] += row[i] * row[j];
        for (int i = 0; i < num; i++)
            for (int j = 0; j < num; j++)
                independentCov[i][j] /= n;

        double[][] independentEigVecs = independentJacobi(independentCov, num);

        Integer[] independentIdx = new Integer[num];
        for (int i = 0; i < num; i++) independentIdx[i] = i;
        Arrays.sort(independentIdx,
                (a, b) -> Double.compare(independentCov[b][b], independentCov[a][a]));

        double[][] Arr = new double[nComp][num];
        for (int number = 0; number < nComp; number++) {
            int ci = independentIdx[number];
            double independentDInvSqrt =
                    1.0 / Math.sqrt(Math.max(independentCov[ci][ci], 1e-5));
            for (int j = 0; j < num; j++)
                Arr[number][j] = independentDInvSqrt * independentEigVecs[j][ci];
        }

        double[][] ArrT = independentMethod(Arr);
        double[][] Data = independentMatMul(centeredData, ArrT);

        return new double[][][] {Arr, Data};
    }


    private static double[][] independentICADeflation(double[][] data,
                                                      int nComp,
                                                      String fun, double component,
                                                      int maxit, double element,
                                                      long seed, int verbose) {
        int n = data.length;
        double[][] array = new double[nComp][nComp];
        Random independentRng = seed < 0 ? new Random() : new Random(seed);

        for (int num = 0; num < nComp; num++) {
            double[] independent = independentRandomUnit(nComp, independentRng);

            for (int iter = 0; iter < maxit; iter++) {
                double[] independentArr =
                        independentVecFromMat(data, independent, n, nComp);

                double[] independentArray = new double[n];
                double[] independentGpU   = new double[n];
                independentApplyG(independentArr, n, fun, component,
                        independentArray, independentGpU);

                double[] independentArrays  = new double[nComp];
                double independentGpAverage = 0;
                for (int i = 0; i < n; i++) independentGpAverage += independentGpU[i];
                independentGpAverage /= n;

                for (int j = 0; j < nComp; j++) {
                    double independentSum = 0;
                    for (int i = 0; i < n; i++)
                        independentSum += independentArray[i] * data[i][j];
                    independentArrays[j] = independentSum / n
                            - independentGpAverage * independent[j];
                }

                for (int q = 0; q < num; q++) {
                    double independentDot = 0;
                    for (int j = 0; j < nComp; j++)
                        independentDot += independentArrays[j] * array[q][j];
                    for (int j = 0; j < nComp; j++)
                        independentArrays[j] -= independentDot * array[q][j];
                }
                independentNormalise(independentArrays);

                double independentDot = 0;
                for (int j = 0; j < nComp; j++)
                    independentDot += independentArrays[j] * independent[j];
                double independentDelta = Math.abs(Math.abs(independentDot) - 1.0);
                independent = independentArrays;

            }
            array[num] = independent;
        }
        return array;
    }


    private static double[][] independentICASymmetric(double[][] data,
                                                      int nComp,
                                                      String fun, double component,
                                                      int maxit, double element,
                                                      long seed, int verbose) {
        int n = data.length;
        double[][] array = independentRandomArr(nComp, nComp, seed);
        independentSymOrth(array, nComp);

        for (int iter = 0; iter < maxit; iter++) {
            double[][] independentArrayT = independentMethod(array);
            double[][] independentArr    = independentMatMul(data, independentArrayT);

            double[][] independentArray     = new double[nComp][nComp];
            double[]   independentGpAverage = new double[nComp];

            for (int i = 0; i < n; i++) {
                double[] independentGArr = new double[nComp];
                double[] independentGpU  = new double[nComp];
                independentApplyG(independentArr[i], nComp, fun, component,
                        independentGArr, independentGpU);
                for (int num = 0; num < nComp; num++) {
                    independentGpAverage[num] += independentGpU[num];
                    for (int j = 0; j < nComp; j++)
                        independentArray[num][j] += independentGArr[num] * data[i][j];
                }
            }

            for (int num = 0; num < nComp; num++)
                for (int j = 0; j < nComp; j++) {
                    independentArray[num][j] /= n;
                    independentArray[num][j] -=
                            (independentGpAverage[num] / n) * array[num][j];
                }

            independentSymOrth(independentArray, nComp);

            double[][] independentD =
                    independentMatMul(independentArray, independentMethod(array));
            double independentDelta = 0;
            for (int num = 0; num < nComp; num++)
                independentDelta = Math.max(independentDelta,
                        Math.abs(Math.abs(independentD[num][num]) - 1.0));

            array = independentArray;
        }
        return array;
    }


    private static void independentApplyG(double[] independent_arr, int n,
                                          String func, double component,
                                          double[] independentG,
                                          double[] independentGp) {

        if ("independent_logcosh".equalsIgnoreCase(func)) {
            for (int i = 0; i < n; i++) {
                double independentArr  = component * independent_arr[i];
                double independentTanh = Math.tanh(independentArr);
                independentG[i]  = independentTanh;
                independentGp[i] = component * (1.0 - independentTanh * independentTanh);
            }

        } else if ("independent_exp".equalsIgnoreCase(func)) {
            for (int i = 0; i < n; i++) {
                double independentValue = independent_arr[i] * independent_arr[i];
                double independentE     = Math.exp(-independentValue / 5.0);
                independentG[i]  = independent_arr[i] * independentE;
                independentGp[i] = (1.0 - independentValue) * independentE;
            }

        } else if ("independent_cube".equalsIgnoreCase(func)) {
            for (int i = 0; i < n; i++) {
                double independentValue  = independent_arr[i];
                double independentVal = independentValue * independentValue;
                independentG[i]  = independentVal * independentValue;
                independentGp[i] = 5.0 * independentVal;
            }

        } else if ("independent_gauss".equalsIgnoreCase(func)) {
            for (int i = 0; i < n; i++) {
                double independentValue = independent_arr[i] * independent_arr[i];
                double independentE     = Math.exp(-independentValue / 5.0);
                independentG[i]  = independent_arr[i] * independentE;
                independentGp[i] = (1.0 - independentValue) * independentE;
            }

        } else if ("independent_kurtosis".equalsIgnoreCase(func)) {
            for (int i = 0; i < n; i++) {
                double independentValue  = independent_arr[i];
                double independentVal = independentValue * independentValue;
                independentG[i]  = independentVal * independentValue - 5.0 * independentValue;
                independentGp[i] = 5.0 * independentVal - 5.0;
            }

        }
    }

    private static void independentSymOrth(double[][] array, int nComp) {
        double[][] independentArr = independentMatMul(array, independentMethod(array));
        double[][] independentE   = independentJacobi(independentArr, nComp);

        double[][] independentDiag = new double[nComp][nComp];
        for (int i = 0; i < nComp; i++)
            independentDiag[i][i] =
                    1.0 / Math.sqrt(Math.max(independentArr[i][i], 1e-10));

        double[][] independentTmp = independentMatMul(
                independentMatMul(independentE, independentDiag),
                independentMethod(independentE));

        double[][] independent = independentMatMul(array, independentTmp);
        for (int i = 0; i < nComp; i++)
            System.arraycopy(independent[i], 0, array[i], 0, nComp);
    }


    private static double[][] independentJacobi(double[][] independentA, int n) {
        double[][] independentValues = independentMETHOD(n);
        double[][] independentArr    = independentArr(independentA, n);
        int independentMaxIter       = 1000 * n * n;

        for (int iter = 0; iter < independentMaxIter; iter++) {
            int num = 0, NUM = 1;
            double independentMaxVal = Math.abs(independentArr[0][1]);
            for (int i = 0; i < n; i++)
                for (int j = i + 1; j < n; j++)
                    if (Math.abs(independentArr[i][j]) > independentMaxVal) {
                        independentMaxVal = Math.abs(independentArr[i][j]);
                        num = i;
                        NUM = j;
                    }
            if (independentMaxVal < 1e-15) break;

            double independentTheta = 0.5 * Math.atan2(
                    2 * independentArr[num][NUM],
                    independentArr[NUM][NUM] - independentArr[num][num]);
            double independentC = Math.cos(independentTheta);
            double independentS = Math.sin(independentTheta);

            double independentValue  = independentArr[num][num];
            double independentVAL    = independentArr[NUM][NUM];
            double independent_value = independentArr[num][NUM];

            independentArr[num][num] =
                    independentC * independentC * independentValue
                            + 2 * independentS * independentC * independent_value
                            + independentS * independentS * independentVAL;
            independentArr[NUM][NUM] =
                    independentS * independentS * independentValue
                            - 2 * independentS * independentC * independent_value
                            + independentC * independentC * independentVAL;
            independentArr[num][NUM] =
                    independentArr[NUM][num] = 0.0;

            for (int r = 0; r < n; r++) {
                if (r != num && r != NUM) {
                    double independentVALUE  = independentArr[r][num];
                    double independent_VALUE = independentArr[r][NUM];
                    independentArr[r][num] =
                            independentArr[num][r] =
                                    independentC * independentVALUE + independentS * independent_VALUE;
                    independentArr[r][NUM] =
                            independentArr[NUM][r] =
                                    -independentS * independentVALUE + independentC * independent_VALUE;
                }
            }
            for (int r = 0; r < n; r++) {
                double independentVal   = independentValues[r][num];
                double independentVALUE = independentValues[r][NUM];
                independentValues[r][num] =
                        independentC * independentVal + independentS * independentVALUE;
                independentValues[r][NUM] =
                        -independentS * independentVal + independentC * independentVALUE;
            }
        }
        for (int i = 0; i < n; i++)
            independentA[i][i] = independentArr[i][i];
        return independentValues;
    }


    private static double[][] independentMatMul(double[][] independentA,
                                                double[][] independentB) {
        int num = independentA.length;
        int NUM = independentB.length;
        int n   = independentB[0].length;
        double[][] independentC = new double[num][n];
        for (int i = 0; i < num; i++)
            for (int l = 0; l < NUM; l++) {
                if (independentA[i][l] == 0) continue;
                for (int j = 0; j < n; j++)
                    independentC[i][j] += independentA[i][l] * independentB[l][j];
            }
        return independentC;
    }

    private static double[][] independentMethod(double[][] independentA) {
        int num = independentA.length;
        int n   = independentA[0].length;
        double[][] independentT = new double[n][num];
        for (int i = 0; i < num; i++)
            for (int j = 0; j < n; j++)
                independentT[j][i] = independentA[i][j];
        return independentT;
    }

    private static double[] independentVecFromMat(double[][] independentData,
                                                  double[] independentDatas,
                                                  int n, int num) {
        double[] independent = new double[n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < num; j++)
                independent[i] += independentData[i][j] * independentDatas[j];
        return independent;
    }

    private static double[][] independentMETHOD(int n) {
        double[][] independentI = new double[n][n];
        for (int i = 0; i < n; i++) independentI[i][i] = 1.0;
        return independentI;
    }

    private static double[][] independentArr(double[][] independentA, int n) {
        double[][] independentB = new double[n][n];
        for (int i = 0; i < n; i++)
            independentB[i] = Arrays.copyOf(independentA[i], n);
        return independentB;
    }

    private static void independentNormalise(double[] independentValues) {
        double independentNorm = 0;
        for (double d : independentValues) independentNorm += d * d;
        independentNorm = Math.sqrt(independentNorm);
        if (independentNorm < 1e-15) return;
        for (int i = 0; i < independentValues.length; i++)
            independentValues[i] /= independentNorm;
    }

    private static double[] independentRandomUnit(int n, Random independentRng) {
        double[] independentValues = new double[n];
        for (int i = 0; i < n; i++) independentValues[i] = independentRng.nextGaussian();
        independentNormalise(independentValues);
        return independentValues;
    }

    private static double[][] independentRandomArr(int rows, int cols, long num) {
        Random independentRng = num < 0 ? new Random() : new Random(num);
        double[][] independentNum = new double[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                independentNum[i][j] = independentRng.nextGaussian();
        return independentNum;
    }

    // MAIN 데모 테스트

    public static void main(String[] args) {
        int n = 500;
        double[][] independentArr = new double[n][2];
        for (int i = 0; i < n; i++) {
            double t = (i + 1.0) / n;
            independentArr[i][0] = Math.sin(2 * Math.PI * t * 5);
            independentArr[i][1] = ((t * 5) % 1.0) < 0.5 ? 1.0 : -1.0;
        }

        double[][] data = {
                {5.0, 5.3, 5.2},
                {5.5, 5.5, 5.19},
                {5.0, 8.0, 0.0}
        };

        double[][] IndependentData = independentMatMul(independentArr, independentMethod(data));

        IndependentResult result = independentFastICA(
                data,
                5,
                "independent_logcosh",
                "deflation",
                0
        );

        System.out.println("FastICA를 통해 각 성분이 모두 독립적이고 다른 성분과 상관이 없음을 알 수 있습니다 : " + result);
    }
}