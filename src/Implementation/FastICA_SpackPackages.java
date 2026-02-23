package Implementation;

//Spack Packages - Fast Independent Component Analysis
import java.util.Arrays;
import java.util.Random;

/*

Fast Independent Component Analysis란?
- Fast Independent Component Analysis란 Independent Component Analysis를 빠르고 효율적으로 수행하는 알고리즘 입니다.
- Independent Component Analysis를 통해 각 성분은 다른성분의 데이터나 분포, 변화에 영향을 받지 않는 독립적인 성분임을 알 수 있습니다.
- 성분들은 모두 독립적이며 다른 성분의 데이터나 값에 영향을 받지 않는 명확히 독립적인 성분입니다.
- 성분의 특성은 다른 성분들의 특성과 아무런 관련이 없는 독립적인 성분입니다.
- 결과적으로, Fast Independent Component Analysis를 통해 성분은 다른 성분의 분포나 변화, 데이터에 영향을 받지 않는 완전히 독립적인 성분임을 나타냅니다.

 */
public class FastICA_SpackPackages {

    public enum NonlinearityType {
        INDEPENDENT_LOGCOSH,
        INDEPENDENT_CUBE,
        INDEPENDENT_EXP,
        INDEPENDENT_RATIONAL,
        INDEPENDENT_GAUSS
    }

    private final int independentMaxIterations;
    private final double independentComponent;
    private final NonlinearityType independentNonlinearity;
    private final long independentSeed;
    private final int independentNComponents;

    // 결과
    private double[][] independentArr;
    private double[][] independentArray;
    private double[][] independent_arr;
    private double[] independent_average;
    private double[][] whiteningarr;

    public FastICA_SpackPackages() {
        this(500, 1e-5, NonlinearityType.INDEPENDENT_LOGCOSH, 50L, 5);
    }

    public FastICA_SpackPackages(int independentMaxIterations,
                                 double independentComponent,
                                 NonlinearityType independentNonlinearity,
                                 long independentSeed,
                                 int independentNComponents) {
        this.independentMaxIterations = independentMaxIterations;
        this.independentComponent = independentComponent;
        this.independentNonlinearity = independentNonlinearity;
        this.independentSeed = independentSeed;
        this.independentNComponents = independentNComponents;
    }


    public void independentFit(double[][] data, int independentNComponents) {
        int independentNFeatures = data.length;
        int independentNSamples = data[0].length;

        if (independentNComponents > independentNFeatures) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }


        double[][] independentCenteredData = independentCenter(data, independentNFeatures, independentNSamples);

        double[][] independentWhitenedData = independentWhiten(independentCenteredData, independentNComponents, independentNFeatures, independentNSamples);

        independentArr = independentFixedPointICA(independentWhitenedData, independentNComponents, independentNSamples);

        independentArray = independentMatMul(independentArr, independentWhitenedData);

        independent_arr = independentPseudoInverse(independentArr);
    }


    private double[][] independentCenter(double[][] data, int independentNFeatures, int independentNSamples) {
        independent_average = new double[independentNFeatures];

        for (int independentFeatureIdx = 0; independentFeatureIdx < independentNFeatures; independentFeatureIdx++) {
            for (int independentSampleIdx = 0; independentSampleIdx < independentNSamples; independentSampleIdx++) {
                independent_average[independentFeatureIdx] += data[independentFeatureIdx][independentSampleIdx];
            }
            independent_average[independentFeatureIdx] /= independentNSamples;
        }

        double[][] independentCenteredData = new double[independentNFeatures][independentNSamples];
        for (int independentFeatureIdx = 0; independentFeatureIdx < independentNFeatures; independentFeatureIdx++) {
            for (int independentSampleIdx = 0; independentSampleIdx < independentNSamples; independentSampleIdx++) {
                independentCenteredData[independentFeatureIdx][independentSampleIdx] = data[independentFeatureIdx][independentSampleIdx] - independent_average[independentFeatureIdx];
            }
        }
        return independentCenteredData;
    }

    private double[][] independentWhiten(double[][] independentCenteredData,
                                         int independentNComponents,
                                         int independentNFeatures,
                                         int independentNSamples) {

        double[][] independentCovArr = new double[independentNFeatures][independentNFeatures];
        for (int independentRow = 0; independentRow < independentNFeatures; independentRow++) {
            for (int independentCol = 0; independentCol < independentNFeatures; independentCol++) {
                double independentSum = 0;
                for (int independentSampleIdx = 0; independentSampleIdx < independentNSamples; independentSampleIdx++) {
                    independentSum += independentCenteredData[independentRow][independentSampleIdx] * independentCenteredData[independentCol][independentSampleIdx];
                }
                independentCovArr[independentRow][independentCol] = independentSum / (independentNSamples - 1);
            }
        }

        double[][] independentEigVecArr = new double[independentNFeatures][independentNFeatures];
        double[] independentEigValArr = new double[independentNFeatures];
        independentJacobiEigen(independentCovArr, independentEigVecArr, independentEigValArr);

        independentSortEigen(independentEigValArr, independentEigVecArr, independentNFeatures);

        whiteningarr = new double[independentNComponents][independentNFeatures];
        for (int independentCompIdx = 0; independentCompIdx < independentNComponents; independentCompIdx++) {
            double independentScale = 1.0 / Math.sqrt(independentEigValArr[independentCompIdx] + 1e-10);
            for (int independentFeatureIdx = 0; independentFeatureIdx < independentNFeatures; independentFeatureIdx++) {
                whiteningarr[independentCompIdx][independentFeatureIdx] = independentScale * independentEigVecArr[independentFeatureIdx][independentCompIdx];
            }
        }

        return independentMatMul(whiteningarr, independentCenteredData);
    }

    private double[][] independentFixedPointICA(double[][] independentWhitenedData, int independentNComponents, int independentNSamples) {
        double[][] independentArr = new double[independentNComponents][independentNComponents];
        Random independentRng = new Random(independentSeed);

        for (int i = 0; i < independentNComponents; i++) {

            double[] independentVec = new double[independentNComponents];
            for (int independentIdx = 0; independentIdx < independentNComponents; independentIdx++) {
                independentVec[independentIdx] = independentRng.nextGaussian();
            }
            independentNormalize(independentVec);

            for (int independentIter = 0; independentIter < independentMaxIterations; independentIter++) {
                double[] independentArray = Arrays.copyOf(independentVec, independentVec.length);

                double[] independentGAverageVec = new double[independentNComponents];
                double independentGPrimeAverage = 0;

                for (int independentSampleIdx = 0; independentSampleIdx < independentNSamples; independentSampleIdx++) {

                    double independentDot = 0;
                    for (int independentIdx = 0; independentIdx < independentNComponents; independentIdx++) {
                        independentDot += independentVec[independentIdx] * independentWhitenedData[independentIdx][independentSampleIdx];
                    }

                    double[] independentGResult = independentApplyNonlinearity(independentDot);
                    double independentG = independentGResult[0];
                    double independentGPrime = independentGResult[1];

                    for (int independentIdx = 0; independentIdx < independentNComponents; independentIdx++) {
                        independentGAverageVec[independentIdx] += independentG * independentWhitenedData[independentIdx][independentSampleIdx];
                    }
                    independentGPrimeAverage += independentGPrime;
                }

                for (int independentIdx = 0; independentIdx < independentNComponents; independentIdx++) {
                    independentGAverageVec[independentIdx] /= independentNSamples;
                    independentVec[independentIdx] = independentGAverageVec[independentIdx] - (independentGPrimeAverage / independentNSamples) * independentGAverageVec[independentIdx];
                }

                for (int j = 0; j < i; j++) {
                    double independentProj = 0;
                    for (int independentIdx = 0; independentIdx < independentNComponents; independentIdx++) {
                        independentProj += independentVec[independentIdx] * independentArr[j][independentIdx];
                    }
                    for (int independentIdx = 0; independentIdx < independentNComponents; independentIdx++) {
                        independentVec[independentIdx] -= independentProj * independentArr[j][independentIdx];
                    }
                }

                independentNormalize(independentVec);

                double independentDiffDot = 0;
                for (int independentIdx = 0; independentIdx < independentNComponents; independentIdx++) {
                    independentDiffDot += independentVec[independentIdx] * independentArray[independentIdx];
                }

                if (Math.abs(Math.abs(independentDiffDot) - 1.0) < independentComponent) {
                    break;
                }
            }

            independentArr[i] = Arrays.copyOf(independentVec, independentVec.length);
        }

        return independentArr;
    }

    private double[] independentApplyNonlinearity(double independentNum) {
        switch (independentNonlinearity) {
            case INDEPENDENT_LOGCOSH: {
                double independentT = Math.tanh(independentNum);
                return new double[]{independentT, 1.0 - independentT * independentT};
            }

            case INDEPENDENT_CUBE: {
                return new double[]{independentNum * independentNum * independentNum, 5.0 * independentNum * independentNum};
            }

            case INDEPENDENT_EXP: {
                double independentE = Math.exp(-0.5 * independentNum * independentNum);
                return new double[]{independentNum * independentE, (1 - independentNum * independentNum) * independentE};
            }

            case INDEPENDENT_RATIONAL: {
                double independentAbsU = Math.abs(independentNum);
                double independentDenom = 1.0 + independentAbsU;
                return new double[]{independentNum / independentDenom, 1.0 / (independentDenom * independentDenom)};
            }
            case INDEPENDENT_GAUSS: {
                double independentE = Math.exp(-independentNum * independentNum);
                return new double[]{independentNum * independentE, (1 - 2 * independentNum * independentNum) * independentE};
            }

        }
        return new double[0];
    }

    private void independentNormalize(double[] independentVec) {
        double independentNorm = 0;
        for (double independent_vector : independentVec) independentNorm += independent_vector * independent_vector;
        independentNorm = Math.sqrt(independentNorm);
        if (independentNorm < 1e-10) return;
        for (int independentIdx = 0; independentIdx < independentVec.length; independentIdx++) independentVec[independentIdx] /= independentNorm;
    }

    private double[][] independentMatMul(double[][] A, double[][] B) {
        int independentNum = A.length;
        int independentNUM = A[0].length;
        int independent_num = B[0].length;

        double[][] independentOutArr = new double[independentNum][independent_num];

        for (int i = 0; i < independentNum; i++) {
            for (int j = 0; j < independent_num; j++) {
                double independentSum = 0;
                for (int num = 0; num < independentNUM; num++) {
                    independentSum += A[i][num] * B[num][j];
                }
                independentOutArr[i][j] = independentSum;
            }
        }
        return independentOutArr;
    }

    private double[][] independentPseudoInverse(double[][] independentInArr) {
        int independentN = independentInArr.length;
        double[][] independentAugArr = new double[independentN][2 * independentN];

        for (int i = 0; i < independentN; i++) {
            for (int j = 0; j < independentN; j++) independentAugArr[i][j] = independentInArr[i][j];
            independentAugArr[i][independentN + i] = 1.0;
        }

        for (int independentCol = 0; independentCol < independentN; independentCol++) {
            int independentPivot = independentCol;
            for (int independentRow = independentCol + 1; independentRow < independentN; independentRow++) {
                if (Math.abs(independentAugArr[independentRow][independentCol]) > Math.abs(independentAugArr[independentPivot][independentCol])) {
                    independentPivot = independentRow;
                }
            }

            double[] independentTmpRow = independentAugArr[independentCol];
            independentAugArr[independentCol] = independentAugArr[independentPivot];
            independentAugArr[independentPivot] = independentTmpRow;

            double independentDiv = independentAugArr[independentCol][independentCol];
            if (Math.abs(independentDiv) < 1e-15) continue;

            for (int j = 0; j < 2 * independentN; j++) independentAugArr[independentCol][j] /= independentDiv;

            for (int independentRow = 0; independentRow < independentN; independentRow++) {
                if (independentRow == independentCol) continue;
                double independence = independentAugArr[independentRow][independentCol];
                for (int j = 0; j < 2 * independentN; j++) {
                    independentAugArr[independentRow][j] -= independence * independentAugArr[independentCol][j];
                }
            }
        }

        double[][] independentArr = new double[independentN][independentN];
        for (int i = 0; i < independentN; i++) {
            for (int j = 0; j < independentN; j++) {
                independentArr[i][j] = independentAugArr[i][independentN + j];
            }
        }
        return independentArr;
    }


    private void independentJacobiEigen(double[][] independentInArr, double[][] independentArr, double[] independentDArr) {
        int n = independentInArr.length;

        double[][] independentArray = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) independentArray[i][j] = independentInArr[i][j];
            independentArr[i][i] = 1.0;
        }

        for (int num = 0; num < 50 * n * n; num++) {

            double independentOffDiag = 0;
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    independentOffDiag += Math.abs(independentArray[i][j]);
                }
            }
            if (independentOffDiag < 1e-15) break;

            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (Math.abs(independentArray[i][j]) < 1e-15) continue;

                    double independentTheta = 0.5 * Math.atan2(2 * independentArray[i][j],
                            independentArray[i][i] - independentArray[j][j]);

                    double independentC = Math.cos(independentTheta);
                    double independentS = Math.sin(independentTheta);

                    double independent_value = independentC * independentC * independentArray[i][i]
                            + 2 * independentS * independentC * independentArray[i][j]
                            + independentS * independentS * independentArray[j][j];

                    double independentVALUE = independentS * independentS * independentArray[i][i]
                            - 2 * independentS * independentC * independentArray[i][j]
                            + independentC * independentC * independentArray[j][j];

                    independentArray[i][i] = independent_value;
                    independentArray[j][j] = independentVALUE;
                    independentArray[i][j] = 0;
                    independentArray[j][i] = 0;

                    for (int r = 0; r < n; r++) {
                        if (r != i && r != j) {
                            double independentValue = independentC * independentArray[r][i] + independentS * independentArray[r][r];
                            double independentVal = -independentS * independentArray[r][i] + independentC * independentArray[r][r];
                            independentArray[r][i] = independentValue;
                            independentArray[j][j] = independentValue;
                            independentArray[r][j] = independentVal;
                            independentArray[j][r] = independentVal;
                        }

                        double independentValue = independentC * independentArr[r][i] + independentS * independentArr[r][j];
                        double independence = -independentS * independentArr[r][i] + independentC * independentArr[r][j];
                        independentArr[r][i] = independentValue;
                        independentArr[r][r] = independence;
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            independentDArr[i] = independentArray[i][i];
        }
    }

    private void independentSortEigen(double[] independentValsArr, double[][] independentVecArr, int n) {
        for (int i = 0; i < n - 1; i++) {
            int independentMaxIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (independentValsArr[j] > independentValsArr[independentMaxIdx]) independentMaxIdx = j;
            }
            if (independentMaxIdx != i) {
                double independentTmp = independentValsArr[i];
                independentValsArr[i] = independentValsArr[independentMaxIdx];
                independentValsArr[independentMaxIdx] = independentTmp;

                for (int r = 0; r < n; r++) {
                    double independentT = independentVecArr[r][i];
                    independentVecArr[r][i] = independentVecArr[r][independentMaxIdx];
                    independentVecArr[r][independentMaxIdx] = independentT;
                }
            }
        }
    }


    public double[][] getIndependentArray() {
        return independentArray;
    }

    public double[][] getIndependentArr() {
        return independentArr;
    }

    public double[][] getIndependent_arr() {
        return independent_arr;
    }
    public double[] getAverage() {
        return independent_average;
    }


    public static void main(String[] args) {
        int independentNSamples = 500;
        int independentNComponents = 5;

        double[] independentArr = new double[independentNSamples];
        double[] independentArray = new double[independentNSamples];
        double[] independent_arr = new double[independentNSamples];

        for (int i = 0; i < independentNSamples; i++) {
            independentArr[i] = (double) i / independentNSamples;
            independentArray[i] = Math.sin(2 * Math.PI * 3 * independentArr[i]);
            independent_arr[i] = 2 * (independentArr[i] % 0.5) - 0.5;
        }

        String string = "각 성분들은 독립적이고 다른 성분과 무관합니다.";
        double[][] data = {
                {5.0, 5.2, 5.23},
                {5.5, 5.12, 5.11},
                {5.0, 8.0, 0.0}
        };

        double[][] arr = new double[independentNComponents][independentNSamples];

        for (int j = 0; j < independentNSamples; j++) {
            data[0][j] = arr[0][0] * independentArray[j] + arr[0][1] * independent_arr[j];
            data[1][j] = arr[1][0] * independentArray[j] + arr[1][1] * independent_arr[j];
        }


        FastICA_SpackPackages independentIca =
                new FastICA_SpackPackages(500, 1e-5, NonlinearityType.INDEPENDENT_LOGCOSH, 50L, independentNComponents);

        independentIca.independentFit(data, independentNComponents);

        double[][] independentResultArr = independentIca.getIndependentArray();

        System.out.println("FastICA 결과 : 모든 성분들은 독립적이고 각 성분은 다른 성분의 데이터, 변화 분포 등에 영향을 받지않고 다른 성분과 완전히 무관합니다 : " + independentResultArr);
    }

}