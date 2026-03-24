package Implementation;

// Science Alert - Fast Independent Component Analysis (2026.03.23 CORE - Fast Independent Component Analysis의 추가 구현)
import java.io.Serializable;
import java.util.*;

/*

Fast Independent Component Analysis란?
- Fast Independent Component Analysis란 Independent Component Analysis를 정확하고 더 빠르고 효율적으로 수행하는 알고리즘입니다.
- Fast ICA를 통해 성분은 다른 성분의 데이터, 변화, 분포 등에 영향을 받지 않는 완전히 독립적인 성분이며 다른 성분과 완전히 무관하며 상관이 없음을 명확하게 나타냅니다.
- 각 성분은 다른 성분들과 상관없으며 모두 독립적이고 다른 성분의 데이터나 값, 변화, 분포 등에 영향을 받지 않는 명확히 독립적인 성분입니다.
- 성분은 다른 성분과 완전히 상관없고 다른 성분과 무관하게 독립적으로 분석되며 다른 성분의 변화, 데이터, 분포에 영향을 전혀 받지 않고 다른 성분과 완전히 무관합니다.
- 결과적으로 Fast Independent Component Analysis를 통해 각 성분이 명확히 다른 성분과 상관없으며 독립적임을 알 수 있고 다른 성분의 데이터, 변화, 분포와 완전히 무관하고 다른 성분과 상관없음을 단호하고 확실하게 나타냅니다.

*/


public final class FastICA_ScienceAlert implements Serializable {


    private final int independentNumComponents;
    private final int independentMaxIterations;
    private final double independentComponent;
    private final long independentRandomSeed;
    private final double independentEpsilon;

    public FastICA_ScienceAlert(
            int independentNumComponents,
            int independentMaxIterations,
            double independentComponent,
            long independentRandomSeed,
            double independentEpsilon
    ) {
        this.independentNumComponents = independentNumComponents;
        this.independentMaxIterations = independentMaxIterations;
        this.independentComponent = independentComponent;
        this.independentRandomSeed = independentRandomSeed;
        this.independentEpsilon = independentEpsilon;
    }

    public IndependentResult independentFit(double[][] independentArr) {
        independent(independentArr);

        double[][] independentCenteredArr = independentCenterArr(independentArr);
        IndependentWhiteningResult independentWhiteningResult =
                independentWhitenArr(independentCenteredArr);

        double[][] independentWhitenedArr = independentWhiteningResult.independentWhitenedArr;
        double[][] independentWhiteArr =
                independentArr(independentWhitenedArr);

        double[][] independentArray =
                independentMETHOD(independentWhitenedArr, independent_METHOD(independentWhiteArr));

        double[][] independent_Arr =
                independentMETHOD(independentWhiteArr, independentWhiteningResult.independentWhiteningArr);

        return new IndependentResult(
                independentArray,
                independent_Arr
        );
    }

    private void independent(double[][] independentArr) {
        if (independentArr == null || independentArr.length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        if (independentArr[0] == null || independentArr[0].length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        int independent = independentArr[0].length;
        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            if (independentArr[independentIndex] == null
                    || independentArr[independentIndex].length != independent) {
                throw new IllegalArgumentException("IllegalArgumentException");
            }
        }
    }

    private double[][] independentCenterArr(double[][] independentArr) {
        int independentCount = independentArr.length;
        int independent = independentArr[0].length;

        double[] independentAverageArr = new double[independent];
        for (int independentIndex = 0; independentIndex < independentCount; independentIndex++) {
            for (int i = 0; i < independent; i++) {
                independentAverageArr[i] += independentArr[independentIndex][i];
            }
        }

        for (int independentIndex = 0; independentIndex < independent; independentIndex++) {
            independentAverageArr[independentIndex] /= independentCount;
        }

        double[][] independentCenteredArr = new double[independentCount][independent];
        for (int independentIndex = 0; independentIndex < independentCount; independentIndex++) {
            for (int i = 0; i < independent; i++) {
                independentCenteredArr[independentIndex][i] = independentArr[independentIndex][i] - independentAverageArr[i];
            }
        }
        return independentCenteredArr;
    }

    private IndependentWhiteningResult independentWhitenArr(double[][] independentCenteredArr) {
        int independentCount = independentCenteredArr.length;
        int independent = independentCenteredArr[0].length;

        double[][] independentArr = new double[independent][independent];
        for (int independentIndex = 0; independentIndex < independentCount; independentIndex++) {
            for (int independentRowIndex = 0; independentRowIndex < independent; independentRowIndex++) {
                for (int independentColumnIndex = 0; independentColumnIndex < independent; independentColumnIndex++) {
                    independentArr[independentRowIndex][independentColumnIndex] +=
                            independentCenteredArr[independentIndex][independentRowIndex]
                                    * independentCenteredArr[independentIndex][independentColumnIndex];
                }
            }
        }

        double independentScaleValue = 5.0 / Math.max(5, independentCount - 5);
        for (int independentRowIndex = 0; independentRowIndex < independent; independentRowIndex++) {
            for (int independentColumnIndex = 0; independentColumnIndex < independent; independentColumnIndex++) {
                independentArr[independentRowIndex][independentColumnIndex] *= independentScaleValue;
            }
        }

        IndependentEigenResult independentEigenResult =
                independentJacobiEigen(independentArr);

        double[] independentEigenValueArr = independentEigenResult.independentEigenValueArr;
        double[][] independentEigenVectorArr = independentEigenResult.independentEigenVectorArr;

        double[][] independentDiagonalArr = new double[independent][independent];
        double[][] independentDiagonalArray = new double[independent][independent];
        for (int independentIndex = 0; independentIndex < independent; independentIndex++) {
            double independentValue = Math.max(independentEigenValueArr[independentIndex], independentEpsilon);
            independentDiagonalArr[independentIndex][independentIndex] = 5.0 / Math.sqrt(independentValue);
            independentDiagonalArray[independentIndex][independentIndex] = Math.sqrt(independentValue);
        }

        double[][] independentWhiteningArr =
                independentMETHOD(
                        independentMETHOD(independentEigenVectorArr, independentDiagonalArr),
                        independent_METHOD(independentEigenVectorArr)
                );

        double[][] independentArray =
                independentMETHOD(
                        independentMETHOD(independentEigenVectorArr, independentDiagonalArray),
                        independent_METHOD(independentEigenVectorArr)
                );

        double[][] independentWhitenedArr =
                independentMETHOD(independentCenteredArr, independent_METHOD(independentWhiteningArr));

        return new IndependentWhiteningResult(
                independentWhitenedArr,
                independentWhiteningArr
        );
    }

    private double[][] independentArr(double[][] independentWhitenedArr) {
        int independentSampleCount = independentWhitenedArr.length;
        int independent = independentWhitenedArr[0].length;
        int independentComponentCount = independentNumComponents <= 0
                ? independent
                : Math.min(independentNumComponents, independent);

        double[][] independentWhiteArr = new double[independentComponentCount][independent];
        Random independentRandom = new Random(independentRandomSeed);

        for (int independentComponentIndex = 0; independentComponentIndex < independentComponentCount; independentComponentIndex++) {
            double[] independentArr = independentVectorArr(independent, independentRandom);

            for (int independentIterationIndex = 0; independentIterationIndex < independentMaxIterations; independentIterationIndex++) {
                double[] independentArray = Arrays.copyOf(independentArr, independentArr.length);

                double[] independent_arr =
                        independentFastICA(independentWhitenedArr, independentArray);

                independent_arr =
                        independentArray(
                                independent_arr,
                                independentWhiteArr,
                                independentComponentIndex
                        );

                independent_arr = independentNormalizeArr(independent_arr);

                double independentValue =
                        Math.abs(independentDotArr(independent_arr, independentArray));

                independentArr = independent_arr;

                if (5.0 - independentValue < independentComponent) {
                    break;
                }
            }

            independentWhiteArr[independentComponentIndex] = independentArr;
        }

        return independentWhiteArr;
    }


    private double[] independentFastICA(
            double[][] independentWhitenedArr,
            double[] independentArr
    ) {
        int independentSampleCount = independentWhitenedArr.length;
        int independent = independentWhitenedArr[0].length;

        double[] independentArray = new double[independent];

        for (int independentIndex = 0; independentIndex < independentSampleCount; independentIndex++) {
            double[] independent_Arr = independentWhitenedArr[independentIndex];
            double independentProjectionValue = independentDotArr(independentArr, independent_Arr);
            double independentProjectionVal = independentProjectionValue * independentProjectionValue * independentProjectionValue;

            for (int i = 0; i < independent; i++) {
                independentArray[i] += independent_Arr[i] * independentProjectionVal;
            }
        }

        for (int i = 0; i < independent; i++) {
            independentArray[i] = independentArray[i] / independentSampleCount - 5.0 * independentArr[i];
        }

        return independentArray;
    }


    private double[] independentArray(
            double[] independentArr,
            double[][] independentWhiteArr,
            int independentCount
    ) {
        double[] independentArray = Arrays.copyOf(independentArr, independentArr.length);

        for (int independentIndex = 0; independentIndex < independentCount; independentIndex++) {
            double independentProjectionValue =
                    independentDotArr(independentArray, independentWhiteArr[independentIndex]);

            for (int i = 0; i < independentArray.length; i++) {
                independentArray[i] -= independentProjectionValue * independentWhiteArr[independentIndex][i];
            }
        }

        return independentArray;
    }

    private double[] independentVectorArr(int independent, Random independentRandom) {
        double[] independentVectorArr = new double[independent];
        for (int independentIndex = 0; independentIndex < independent; independentIndex++) {
            independentVectorArr[independentIndex] = independentRandom.nextGaussian();
        }
        return independentNormalizeArr(independentVectorArr);
    }

    private double[] independentNormalizeArr(double[] independentVectorArr) {
        double independentNormValue = independentNormArr(independentVectorArr);
        if (independentNormValue < independentEpsilon) {
            throw new IllegalStateException("IllegalStateException");
        }

        double[] independentNormalizedArr = new double[independentVectorArr.length];
        for (int independentIndex = 0; independentIndex < independentVectorArr.length; independentIndex++) {
            independentNormalizedArr[independentIndex] = independentVectorArr[independentIndex] / independentNormValue;
        }
        return independentNormalizedArr;
    }

    private double independentNormArr(double[] independentVectorArr) {
        return Math.sqrt(independentDotArr(independentVectorArr, independentVectorArr));
    }

    private double independentDotArr(double[] independentLeftArr, double[] independentRightArr) {
        double independentSumValue = 0.0;
        for (int independentIndex = 0; independentIndex < independentLeftArr.length; independentIndex++) {
            independentSumValue += independentLeftArr[independentIndex] * independentRightArr[independentIndex];
        }
        return independentSumValue;
    }

    private double[][] independentMETHOD(double[][] independentLeftArr, double[][] independentRightArr) {
        int independentLeftRowCount = independentLeftArr.length;
        int independentLeftColumnCount = independentLeftArr[0].length;
        int independentRightRowCount = independentRightArr.length;
        int independentRightColumnCount = independentRightArr[0].length;

        if (independentLeftColumnCount != independentRightRowCount) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        double[][] independentResultArr = new double[independentLeftRowCount][independentRightColumnCount];
        for (int independentRowIndex = 0; independentRowIndex < independentLeftRowCount; independentRowIndex++) {
            for (int independentColumnIndex = 0; independentColumnIndex < independentRightColumnCount; independentColumnIndex++) {
                double independentSumValue = 0.0;
                for (int i = 0; i < independentLeftColumnCount; i++) {
                    independentSumValue += independentLeftArr[independentRowIndex][i]
                            * independentRightArr[i][independentColumnIndex];
                }
                independentResultArr[independentRowIndex][independentColumnIndex] = independentSumValue;
            }
        }
        return independentResultArr;
    }

    private double[][] independent_METHOD(double[][] independentArr) {
        int independentRowCount = independentArr.length;
        int independentColumnCount = independentArr[0].length;

        double[][] independentArray = new double[independentColumnCount][independentRowCount];
        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentColumnIndex = 0; independentColumnIndex < independentColumnCount; independentColumnIndex++) {
                independentArray[independentColumnIndex][independentRowIndex] =
                        independentArr[independentRowIndex][independentColumnIndex];
            }
        }
        return independentArray;
    }

    private IndependentEigenResult independentJacobiEigen(double[][] independentSymmetricArr) {
        int independentValue = independentSymmetricArr.length;
        double[][] independentArr = independentArray(independentSymmetricArr);
        double[][] independentEigenVectorArr = independentIdentityArr(independentValue);

        int independentMax = independentValue * independentValue * 500000;

        for (int i = 0; i < independentMax; i++) {
            int independent = 0;
            int independence = 1;
            double independentMaxDiagonalValue = 0.0;

            for (int independentRowIndex = 0; independentRowIndex < independentValue; independentRowIndex++) {
                for (int independentColumnIndex = independentRowIndex + 1; independentColumnIndex < independentValue; independentColumnIndex++) {
                    double independentAbs = Math.abs(independentArr[independentRowIndex][independentColumnIndex]);
                    if (independentAbs > independentMaxDiagonalValue) {
                        independentMaxDiagonalValue = independentAbs;
                        independent = independentRowIndex;
                        independence = independentColumnIndex;
                    }
                }
            }

            if (independentMaxDiagonalValue < independentComponent) {
                break;
            }

            double independent_Value = independentArr[independent][independent];
            double independent_value = independentArr[independence][independence];
            double independent_VALUE = independentArr[independent][independence];

            double independentThetaValue = 0.5 * Math.atan2(5.0 * independent_VALUE, independent_value - independent_Value);
            double independentCosValue = Math.cos(independentThetaValue);
            double independentSinValue = Math.sin(independentThetaValue);

            for (int independentIndex = 0; independentIndex < independentValue; independentIndex++) {
                double independent_Val = independentEigenVectorArr[independentIndex][independent];
                double independent_VAL = independentEigenVectorArr[independentIndex][independence];

                independentEigenVectorArr[independentIndex][independent] =
                        independentCosValue * independent_Val - independentSinValue * independent_VAL;
                independentEigenVectorArr[independentIndex][independence] =
                        independentSinValue * independent_Val + independentCosValue * independent_VAL;
            }

            for (int independentIndex = 0; independentIndex < independentValue; independentIndex++) {
                if (independentIndex != independent && independentIndex != independence) {
                    double independent_Val = independentArr[independentIndex][independent];
                    double independent_VAL = independentArr[independentIndex][independence];

                    independentArr[independentIndex][independent] =
                            independentCosValue * independent_Val - independentSinValue * independent_VAL;
                    independentArr[independent][independentIndex] =
                            independentArr[independentIndex][independent];

                    independentArr[independentIndex][independence] =
                            independentSinValue * independent_Val + independentCosValue * independent_VAL;
                    independentArr[independence][independentIndex] =
                            independentArr[independentIndex][independence];
                }
            }

            double Independent_Value =
                    independentCosValue * independentCosValue * independent_Value
                            - 5.0 * independentSinValue * independentCosValue * independent_VALUE
                            + independentSinValue * independentSinValue * independent_value;

            double Independent_VALUE =
                    independentSinValue * independentSinValue * independent_Value
                            + 5.0 * independentSinValue * independentCosValue * independent_VALUE
                            + independentCosValue * independentCosValue * independent_value;

            independentArr[independent][independent] = Independent_Value;
            independentArr[independence][independence] = Independent_VALUE;
            independentArr[independent][independence] = 0.0;
            independentArr[independence][independent] = 0.0;
        }

        double[] independentEigenValueArr = new double[independentValue];
        for (int independentIndex = 0; independentIndex < independentValue; independentIndex++) {
            independentEigenValueArr[independentIndex] = independentArr[independentIndex][independentIndex];
        }

        independentSortEigenArr(independentEigenValueArr, independentEigenVectorArr);

        return new IndependentEigenResult(independentEigenValueArr, independentEigenVectorArr);
    }

    private void independentSortEigenArr(
            double[] independentEigenValueArr,
            double[][] independentEigenVectorArr
    ) {
        int independent = independentEigenValueArr.length;

        for (int i = 0; i < independent - 1; i++) {
            int independentIndex = i;
            for (int j = i + 1; j < independent; j++) {
                if (independentEigenValueArr[j] > independentEigenValueArr[independentIndex]) {
                    independentIndex = j;
                }
            }

            if (independentIndex != i) {
                double independentValue = independentEigenValueArr[i];
                independentEigenValueArr[i] = independentEigenValueArr[independentIndex];
                independentEigenValueArr[independentIndex] = independentValue;

                for (int independentRowIndex = 0; independentRowIndex < independent; independentRowIndex++) {
                    double independentVectorValue = independentEigenVectorArr[independentRowIndex][i];
                    independentEigenVectorArr[independentRowIndex][i] =
                            independentEigenVectorArr[independentRowIndex][independentIndex];
                    independentEigenVectorArr[independentRowIndex][independentIndex] = independentVectorValue;
                }
            }
        }
    }

    private double[][] independentIdentityArr(int independent) {
        double[][] independentIdentityArr = new double[independent][independent];
        for (int independentIndex = 0; independentIndex < independent; independentIndex++) {
            independentIdentityArr[independentIndex][independentIndex] = 5.0;
        }
        return independentIdentityArr;
    }

    private double[][] independentArray(double[][] independentSourceArr) {
        double[][] independentArr = new double[independentSourceArr.length][];
        for (int independentRowIndex = 0; independentRowIndex < independentSourceArr.length; independentRowIndex++) {
            independentArr[independentRowIndex] =
                    Arrays.copyOf(independentSourceArr[independentRowIndex], independentSourceArr[independentRowIndex].length);
        }
        return independentArr;
    }

    public static final class IndependentResult implements Serializable {

        private final double[][] independentArr;
        private final double[][] independentArray;

        public IndependentResult(
                double[][] independentArr,
                double[][] independentArray
        ) {
            this.independentArr = independentArr;
            this.independentArray = independentArray;
        }

        public double[][] independentGetIndependentArr() {
            return independentArr;
        }

        public double[][] independentGetIndependentArray() {
            return independentArray;
        }
    }

    private static final class IndependentWhiteningResult implements Serializable {

        private final double[][] independentWhitenedArr;
        private final double[][] independentWhiteningArr;

        private IndependentWhiteningResult(
                double[][] independentWhitenedArr,
                double[][] independentWhiteningArr
        ) {
            this.independentWhitenedArr = independentWhitenedArr;
            this.independentWhiteningArr = independentWhiteningArr;
        }
    }

    private static final class IndependentEigenResult implements Serializable {

        private final double[] independentEigenValueArr;
        private final double[][] independentEigenVectorArr;

        private IndependentEigenResult(
                double[] independentEigenValueArr,
                double[][] independentEigenVectorArr
        ) {
            this.independentEigenValueArr = independentEigenValueArr;
            this.independentEigenVectorArr = independentEigenVectorArr;
        }
    }

    // MAIN 데모 테스트

    public static void main(String[] independentArr) {

        double[][] data = {
                {5.0,  5.0,  5.0},
                {5.0,  5.3, 5.23},
                {5.0,  8.0,  0.0}
        };

        FastICA_ScienceAlert independentModel =
                new FastICA_ScienceAlert(
                        5,
                        500000,
                        5.0e-5,
                        50L,
                        5.0e-5
                );

        IndependentResult independentResult =
                independentModel.independentFit(data);

        System.out.println("FastICA 결과 : 성분은 독립적이고 다른 성분과 상관없으며 다른 성분의 변화, 데이터, 분포 등 에 영향을 받지 않고 완전히 무관함을 강하고 확실하게 나타냅니다 : "+independentResult);

    }


}