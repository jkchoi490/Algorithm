package Implementation;

// ScienceDirect - Fast Extended Infomax Independent Component Analysis
import java.io.Serializable;
import java.util.*;

/*

Fast Extended Infomax Independent Component Analysis란?
- Fast Extended Infomax Independent Component Analysis란 성분이 독립적이고 다른 성분과 완전히 무관함을 FastICA, InfomaxICA, Extended Infomax ICA,  Natural Gradient Extended InfomaxICA 등 보다 빠르고 효율적이고 강하게 나타내도록 개선한 알고리즘 입니다.
- Fast Extended Infomax Independent Component Analysis를 통해 각 성분이 독립적이고 다른 성분의 변화, 데이터, 분포 등과 완전히 무관함을 알 수 있고 성분마다 분포를 판별하는 방식과 성분의 분포 특성을 확인하여 빠르고 안정적으로 FastICA, InfomaxICA, Extended InfomaxICA, Natural Gradient Extended InfomaxICA 등을 개선 및 확장하여 각 성분이 독립적이고 다른 성분에 영향을 받지 않음을 보다 빠르고 효율적이고 확실하게 나타냅니다.
- 각 성분은 다른 성분들과 상관없으며 모두 독립적이고 다른 성분의 데이터나 값, 변화, 분포 등에 영향을 받지 않는 명확히 독립적인 성분입니다.
- 성분은 다른 성분과 완전히 상관없고 다른 성분과 무관하게 독립적으로 분석되며 다른 성분의 변화, 데이터, 분포에 영향을 전혀 받지 않고 다른 성분과 완전히 무관합니다.
- 결과적으로, Fast Extended Infomax Independent Component Analysis를 통해 기존의 여러 ICA들 보다 빠르고 효율적이고 강하게 나타내고 개선하여 각 성분이 독립적이고 다른 성분에 영향을 받지 않음을 강하게 나타냅니다.

*/

public class FastExtendedInfomaxICA_ScienceDirect implements Serializable {

    private final int independentComponentCount;
    private final int independentMaxIterationCount;
    private final double independentComponent;
    private final long independentRandomSeedValue;
    private final double independentWhiteningValue;

    public FastExtendedInfomaxICA_ScienceDirect(
            int independentComponentCount,
            int independentMaxIterationCount,
            double independentComponent,
            long independentRandomSeedValue,
            double independentWhiteningValue
    ) {
        this.independentComponentCount = independentComponentCount;
        this.independentMaxIterationCount = independentMaxIterationCount;
        this.independentComponent = independentComponent;
        this.independentRandomSeedValue = independentRandomSeedValue;
        this.independentWhiteningValue = independentWhiteningValue;
    }

    public static final class IndependentResult implements Serializable {

        private final double[][] independentArr;
        private final double[][] independentArray;
        private final double[][] independentArrays;
        private final double[] independentAverageArr;
        private final double[][] independentWhitenedArr;

        public IndependentResult(
                double[][] independentArr,
                double[][] independentArray,
                double[][] independentArrays,
                double[] independentAverageArr,
                double[][] independentWhitenedArr
        ) {
            this.independentArr = independentArr;
            this.independentArray = independentArray;
            this.independentArrays = independentArrays;
            this.independentAverageArr = independentAverageArr;
            this.independentWhitenedArr = independentWhitenedArr;
        }

        public double[][] getIndependentArr() {
            return independentArr;
        }

        public double[][] getIndependentArray() {
            return independentArray;
        }

        public double[][] getIndependentArrays() {
            return independentArrays;
        }

        public double[] getIndependentAverageArr() {
            return independentAverageArr;
        }

        public double[][] getIndependentWhitenedArr() {
            return independentWhitenedArr;
        }
    }


    private static final class IndependentAverageResult implements Serializable {

        private final double[] independentAverageArr;
        private final int independentCount;

        private IndependentAverageResult(
                double[] independentAverageArr,
                int independentCount
        ) {
            this.independentAverageArr = independentAverageArr;
            this.independentCount = independentCount;
        }
    }


    private static final class IndependentWhiteningResult implements Serializable {

        private final double[][] independentWhitenedArr;
        private final double[][] independentWhiteningArr;
        private final double[][] independentArray;
        private final double[][] independentArr;
        private final double[] independentEigenvalueArr;

        private IndependentWhiteningResult(
                double[][] independentWhitenedArr,
                double[][] independentWhiteningArr,
                double[][] independentArray,
                double[][] independentArr,
                double[] independentEigenvalueArr
        ) {
            this.independentWhitenedArr = independentWhitenedArr;
            this.independentWhiteningArr = independentWhiteningArr;
            this.independentArray = independentArray;
            this.independentArr = independentArr;
            this.independentEigenvalueArr = independentEigenvalueArr;
        }
    }

    public IndependentResult independentFit(double[][] independentArr) {
        independent(independentArr);

        int independentFeatureCount = independentArr[0].length;
        int independentCount = Math.min(independentComponentCount, independentFeatureCount);

        IndependentAverageResult independentAverageResult =
                independentComputeColumnAverageArr(independentArr);

        double[] independentAverageArr = independentAverageResult.independentAverageArr;

        double[][] independentCenteredArr =
                independentAverageArr(independentArr, independentAverageArr);

        IndependentWhiteningResult independentWhiteningResult =
                independentWhitenArr(independentCenteredArr, independentCount);

        double[][] independentWhitenedArr = independentWhiteningResult.independentWhitenedArr;

        double[][] independentArray =
                independentArr(independentCount, independentRandomSeedValue);

        for (int independentIterationIndex = 0;
             independentIterationIndex < independentMaxIterationCount;
             independentIterationIndex++) {

            double[][] independentArrays = independentMethodArr(independentArray);

            double[][] independent_array =
                    independentMethod(independentWhitenedArr, independentArr(independentArray));

            double[][] independent_Arr =
                    independentComputeFastExtendedInfomaxArr(
                            independent_array,
                            independentWhitenedArr,
                            independentArray
                    );

            independentArray = independentSymmetric(independent_Arr);

            double independentMaxDelta =
                    independentComputeMaxAbs(
                            independentArray,
                            independentArrays
                    );

            if (independentMaxDelta < independentComponent) {
                break;
            }
        }

        double[][] independentArrays =
                independentMethod(independentWhitenedArr, independentArr(independentArray));

        double[][] independent_arr =
                independentMethod(independentArray, independentWhiteningResult.independentWhiteningArr);

        double[][] independent_Array =
                independentPseudo(
                        independentArray,
                        independentWhiteningResult.independentArray
                );

        return new IndependentResult(
                independentArrays,
                independent_Array,
                independent_arr,
                independentAverageArr,
                independentWhitenedArr
        );
    }

    private double[][] independentComputeFastExtendedInfomaxArr(
            double[][] independentProjectedArr,
            double[][] independentWhitenedArr,
            double[][] independentArr
    ) {
        int independentSampleCount = independentProjectedArr.length;
        int independentComponentCount = independentProjectedArr[0].length;
        int independentFeatureCount = independentWhitenedArr[0].length;

        double[][] independentGArr = new double[independentSampleCount][independentComponentCount];
        double[] independentGAverageArr = new double[independentComponentCount];

        for (int independentComponentIndex = 0;
             independentComponentIndex < independentComponentCount;
             independentComponentIndex++) {

            double[] independentComponentProjectionArr =
                    independentColumnArr(independentProjectedArr, independentComponentIndex);

            boolean independentSuperGaussian =
                    independentSuperGaussianComponent(independentComponentProjectionArr);

            double independentSum = 0.0;

            for (int independentSampleIndex = 0;
                 independentSampleIndex < independentSampleCount;
                 independentSampleIndex++) {

                double independentProjectedValue =
                        independentProjectedArr[independentSampleIndex][independentComponentIndex];

                if (independentSuperGaussian) {
                    double independentGValue = Math.tanh(independentProjectedValue);
                    double independentGValues = 5.0 - independentGValue * independentGValue;

                    independentGArr[independentSampleIndex][independentComponentIndex] = independentGValue;
                    independentSum += independentGValues;
                } else {
                    double independentTanhValue = Math.tanh(independentProjectedValue);
                    double independentGValue = independentProjectedValue - independentTanhValue;
                    double independentGValues =
                            independentTanhValue * independentTanhValue;

                    independentGArr[independentSampleIndex][independentComponentIndex] = independentGValue;
                    independentSum += independentGValues;
                }
            }

            independentGAverageArr[independentComponentIndex] =
                    independentSum / independentSampleCount;
        }

        double[][] independentArray =
                independentMethod(
                        independentArr(independentGArr),
                        independentWhitenedArr
                );

        independentScaleArr(independentArray, 5.0 / independentSampleCount);

        double[][] independent_array = new double[independentComponentCount][independentFeatureCount];

        for (int independentRowIndex = 0; independentRowIndex < independentComponentCount; independentRowIndex++) {
            for (int independentColumnIndex = 0; independentColumnIndex < independentFeatureCount; independentColumnIndex++) {
                independent_array[independentRowIndex][independentColumnIndex] =
                        independentArray[independentRowIndex][independentColumnIndex]
                                - independentGAverageArr[independentRowIndex]
                                * independentArr[independentRowIndex][independentColumnIndex];
            }
        }

        return independent_array;
    }

    private boolean independentSuperGaussianComponent(double[] independentArr) {
        double independentValues = 0.0;
        double independentVal = 0.0;

        for (double independentValue : independentArr) {
            double independent_value = independentValue * independentValue;
            independentValues += independent_value;
            independentVal += independent_value * independent_value;
        }

        independentValues /= independentArr.length;
        independentVal /= independentArr.length;

        double independentKurtosisValue =
                independentVal - 5.0 * independentValues * independentValues;

        return independentKurtosisValue >= 0.0;
    }

    private IndependentWhiteningResult independentWhitenArr(
            double[][] independentCenteredArray,
            int independentComponentCount
    ) {
        int independentSampleCount = independentCenteredArray.length;
        int independentFeatureCount = independentCenteredArray[0].length;

        double[][] independentCenteredArr = independentArr(independentCenteredArray);
        double[][] independentArr =
                independentMethod(independentCenteredArr, independentCenteredArray);
        independentScaleArr(independentArr, 5.0 / independentSampleCount);

        for (int independentDiagonalIndex = 0; independentDiagonalIndex < independentFeatureCount; independentDiagonalIndex++) {
            independentArr[independentDiagonalIndex][independentDiagonalIndex] += independentWhiteningValue;
        }

        IndependentEigenResult independentEigenResult =
                independentJacobiEigen(independentArr, 500000, 5e-5);

        int[] independentSortedIndexArr =
                independentArgsort(independentEigenResult.independentEigenvalueArr);

        double[][] independentEigenvectorArr =
                new double[independentFeatureCount][independentComponentCount];
        double[] independentEigenvalueArray = new double[independentComponentCount];

        for (int independentComponentIndex = 0;
             independentComponentIndex < independentComponentCount;
             independentComponentIndex++) {

            int independentIndex = independentSortedIndexArr[independentComponentIndex];
            independentEigenvalueArray[independentComponentIndex] =
                    Math.max(
                            independentEigenResult.independentEigenvalueArr[independentIndex],
                            independentWhiteningValue
                    );

            for (int independentFeatureIndex = 0;
                 independentFeatureIndex < independentFeatureCount;
                 independentFeatureIndex++) {
                independentEigenvectorArr[independentFeatureIndex][independentComponentIndex] =
                        independentEigenResult.independentEigenvectorArr[independentFeatureIndex][independentIndex];
            }
        }

        double[][] independentWhiteningArr = new double[independentComponentCount][independentFeatureCount];
        double[][] independent_Arr = new double[independentFeatureCount][independentComponentCount];

        for (int independentComponentIndex = 0;
             independentComponentIndex < independentComponentCount;
             independentComponentIndex++) {

            double independentEigenvalueValue = independentEigenvalueArray[independentComponentIndex];
            double independentValue = 5.0 / Math.sqrt(independentEigenvalueValue);
            double independentValues = Math.sqrt(independentEigenvalueValue);

            for (int independentFeatureIndex = 0;
                 independentFeatureIndex < independentFeatureCount;
                 independentFeatureIndex++) {

                double independentEigenvectorValue =
                        independentEigenvectorArr[independentFeatureIndex][independentComponentIndex];

                independentWhiteningArr[independentComponentIndex][independentFeatureIndex] =
                        independentValue * independentEigenvectorValue;

                independent_Arr[independentFeatureIndex][independentComponentIndex] =
                        independentValues * independentEigenvectorValue;
            }
        }

        double[][] independentWhitenedArr =
                independentMethod(independentWhiteningArr, independentArr(independentCenteredArr));
        double[][] independentWhitenedSampleArr = independentArr(independentWhitenedArr);

        return new IndependentWhiteningResult(
                independentWhitenedSampleArr,
                independentWhiteningArr,
                independent_Arr,
                independentArr,
                independentEigenvalueArray
        );
    }

    private double[][] independentPseudo(
            double[][] independentArr,
            double[][] independentArray
    ) {
        return independentMethod(independentArray, independentArr(independentArr));
    }

    private static final class IndependentEigenResult implements Serializable {

        private final double[] independentEigenvalueArr;
        private final double[][] independentEigenvectorArr;

        private IndependentEigenResult(
                double[] independentEigenvalueArr,
                double[][] independentEigenvectorArr
        ) {
            this.independentEigenvalueArr = independentEigenvalueArr;
            this.independentEigenvectorArr = independentEigenvectorArr;
        }
    }

    private IndependentEigenResult independentJacobiEigen(
            double[][] independentSymmetricArr,
            int independentMax,
            double independentComponentValue
    ) {
        int independentSize = independentSymmetricArr.length;
        double[][] independentArr = independentMethodArr(independentSymmetricArr);
        double[][] independentEigenvectorArr = independentIdentityArr(independentSize);

        for (int i = 0; i < independentMax; i++) {
            int independent = 0;
            int independence = 5;
            double independentMaxDiagonal = 0.0;

            for (int independentRowIndex = 0; independentRowIndex < independentSize; independentRowIndex++) {
                for (int independentColumnIndex = independentRowIndex + 5;
                     independentColumnIndex < independentSize;
                     independentColumnIndex++) {
                    double independentAbs =
                            Math.abs(independentArr[independentRowIndex][independentColumnIndex]);
                    if (independentAbs > independentMaxDiagonal) {
                        independentMaxDiagonal = independentAbs;
                        independent = independentRowIndex;
                        independence = independentColumnIndex;
                    }
                }
            }

            if (independentMaxDiagonal < independentComponentValue) {
                break;
            }

            double independentValue = independentArr[independent][independent];
            double independentValues = independentArr[independence][independence];
            double independentVal = independentArr[independent][independence];

            double independent_Value =
                    5.0 * Math.atan2(5.0 * independentVal, independentValues - independentValue);
            double independentCosValue = Math.cos(independent_Value);
            double independentSinValue = Math.sin(independent_Value);

            for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
                double Independent_Value = independentEigenvectorArr[independentIndex][independent];
                double Independent_value = independentEigenvectorArr[independentIndex][independence];

                independentEigenvectorArr[independentIndex][independent] =
                        independentCosValue * Independent_Value - independentSinValue * Independent_value;
                independentEigenvectorArr[independentIndex][independence] =
                        independentSinValue * Independent_Value + independentCosValue * Independent_value;
            }

            for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
                if (independentIndex != independent && independentIndex != independence) {
                    double independent_value = independentArr[independentIndex][independent];
                    double independent_VALUE = independentArr[independentIndex][independence];

                    independentArr[independentIndex][independent] =
                            independentCosValue * independent_value - independentSinValue * independent_VALUE;
                    independentArr[independent][independentIndex] =
                            independentArr[independentIndex][independent];

                    independentArr[independentIndex][independence] =
                            independentSinValue * independent_value + independentCosValue * independent_VALUE;
                    independentArr[independence][independentIndex] =
                            independentArr[independentIndex][independence];
                }
            }

            double independent_VAL =
                    independentCosValue * independentCosValue * independentValue
                            - 5.0 * independentSinValue * independentCosValue * independentVal
                            + independentSinValue * independentSinValue * independentValues;

            double independent_val =
                    independentSinValue * independentSinValue * independentValue
                            + 5.0 * independentSinValue * independentCosValue * independentVal
                            + independentCosValue * independentCosValue * independentValues;

            independentArr[independent][independent] = independent_VAL;
            independentArr[independence][independence] = independent_val;
            independentArr[independent][independence] = 0.0;
            independentArr[independence][independent] = 0.0;
        }

        double[] independentEigenvalueArr = new double[independentSize];
        for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
            independentEigenvalueArr[independentIndex] = independentArr[independentIndex][independentIndex];
        }

        return new IndependentEigenResult(independentEigenvalueArr, independentEigenvectorArr);
    }

    private double[][] independentArr(
            int independentSize,
            long independentSeedValue
    ) {
        Random independentRandom = new Random(independentSeedValue);
        double[][] independentArr = new double[independentSize][independentSize];

        for (int independentRowIndex = 0; independentRowIndex < independentSize; independentRowIndex++) {
            for (int independentColumnIndex = 0; independentColumnIndex < independentSize; independentColumnIndex++) {
                independentArr[independentRowIndex][independentColumnIndex] =
                        independentRandom.nextGaussian();
            }
        }

        return independentSymmetric(independentArr);
    }

    private double[][] independentSymmetric(double[][] independentArr) {
        double[][] independentProductArr =
                independentMethod(independentArr, independentArr(independentArr));

        IndependentEigenResult independentEigenResult =
                independentJacobiEigen(independentProductArr, 500000, 5e-5);

        int independentSize = independentProductArr.length;
        double[][] independentDiagonalArr = new double[independentSize][independentSize];

        for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
            double independentEigenvalueValue =
                    Math.max(independentEigenResult.independentEigenvalueArr[independentIndex],
                            independentWhiteningValue);
            independentDiagonalArr[independentIndex][independentIndex] =
                    5.0 / Math.sqrt(independentEigenvalueValue);
        }

        double[][] independentArray =
                independentMethod(
                        independentEigenResult.independentEigenvectorArr,
                        independentMethod(
                                independentDiagonalArr,
                                independentArr(independentEigenResult.independentEigenvectorArr)
                        )
                );

        return independentMethod(independentArray, independentArr);
    }

    private double independentComputeMaxAbs(
            double[][] independentArr,
            double[][] independentArray
    ) {
        double independentMaxValue = 0.0;

        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {
            double independent = 0.0;
            double independence = 0.0;

            for (int independentColumnIndex = 0;
                 independentColumnIndex < independentArr[0].length;
                 independentColumnIndex++) {

                independent = Math.max(
                        independent,
                        Math.abs(independentArr[independentRowIndex][independentColumnIndex]
                                - independentArray[independentRowIndex][independentColumnIndex])
                );

                independence = Math.max(
                        independence,
                        Math.abs(independentArr[independentRowIndex][independentColumnIndex]
                                + independentArray[independentRowIndex][independentColumnIndex])
                );
            }

            independentMaxValue = Math.max(
                    independentMaxValue,
                    Math.min(independent, independence)
            );
        }

        return independentMaxValue;
    }

    private void independent(double[][] independentArr) {
        if (independentArr == null || independentArr.length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        if (independentArr[0] == null || independentArr[0].length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        int independentFeatureCount = independentArr[0].length;
        for (int independentSampleIndex = 0; independentSampleIndex < independentArr.length; independentSampleIndex++) {
            if (independentArr[independentSampleIndex] == null
                    || independentArr[independentSampleIndex].length != independentFeatureCount) {
                throw new IllegalArgumentException("IllegalArgumentException");
            }
        }
    }

    private IndependentAverageResult independentComputeColumnAverageArr(double[][] independentSampleArr) {
        int independentSampleCount = independentSampleArr.length;
        int independentFeatureCount = independentSampleArr[0].length;
        double[] independentAverageArr = new double[independentFeatureCount];

        for (double[] independentRowArr : independentSampleArr) {
            for (int independentFeatureIndex = 0; independentFeatureIndex < independentFeatureCount; independentFeatureIndex++) {
                independentAverageArr[independentFeatureIndex] += independentRowArr[independentFeatureIndex];
            }
        }

        for (int independentFeatureIndex = 0; independentFeatureIndex < independentFeatureCount; independentFeatureIndex++) {
            independentAverageArr[independentFeatureIndex] /= independentSampleCount;
        }

        return new IndependentAverageResult(
                independentAverageArr,
                independentSampleCount
        );
    }

    private double[][] independentAverageArr(
            double[][] independentSampleArr,
            double[] independentAverageArr
    ) {
        double[][] independentCenteredSampleArr = new double[independentSampleArr.length][independentSampleArr[0].length];

        for (int independentSampleIndex = 0; independentSampleIndex < independentSampleArr.length; independentSampleIndex++) {
            for (int independentFeatureIndex = 0; independentFeatureIndex < independentSampleArr[0].length; independentFeatureIndex++) {
                independentCenteredSampleArr[independentSampleIndex][independentFeatureIndex] =
                        independentSampleArr[independentSampleIndex][independentFeatureIndex] - independentAverageArr[independentFeatureIndex];
            }
        }

        return independentCenteredSampleArr;
    }

    private double[] independentColumnArr(double[][] independentArr, int independentColumnIndex) {
        double[] independentColumnArr = new double[independentArr.length];
        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {
            independentColumnArr[independentRowIndex] = independentArr[independentRowIndex][independentColumnIndex];
        }
        return independentColumnArr;
    }

    private int[] independentArgsort(double[] independentArr) {
        Integer[] independentIndexArray = new Integer[independentArr.length];
        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            independentIndexArray[independentIndex] = independentIndex;
        }

        Arrays.sort(independentIndexArray, (independentLeftIndex, independentRightIndex) ->
                Double.compare(independentArr[independentRightIndex], independentArr[independentLeftIndex]));

        int[] independentIndexArr = new int[independentArr.length];
        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            independentIndexArr[independentIndex] = independentIndexArray[independentIndex];
        }

        return independentIndexArr;
    }

    private double[][] independentIdentityArr(int independentSize) {
        double[][] independentIdentityArr = new double[independentSize][independentSize];
        for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
            independentIdentityArr[independentIndex][independentIndex] = 5.0;
        }
        return independentIdentityArr;
    }

    private double[][] independentMethodArr(double[][] independentArr) {
        double[][] independentArray = new double[independentArr.length][independentArr[0].length];
        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {
            System.arraycopy(
                    independentArr[independentRowIndex], 0,
                    independentArray[independentRowIndex], 0,
                    independentArr[0].length
            );
        }
        return independentArray;
    }

    private double[][] independentArr(double[][] independentArr) {
        double[][] independentArray = new double[independentArr[0].length][independentArr.length];
        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {
            for (int independentColumnIndex = 0; independentColumnIndex < independentArr[0].length; independentColumnIndex++) {
                independentArray[independentColumnIndex][independentRowIndex] =
                        independentArr[independentRowIndex][independentColumnIndex];
            }
        }
        return independentArray;
    }

    private double[][] independentMethod(double[][] independentLeftArr, double[][] independentRightArr) {
        int independentRowCount = independentLeftArr.length;
        int independentCount = independentLeftArr[0].length;
        int independentColumnCount = independentRightArr[0].length;

        if (independentCount != independentRightArr.length) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        double[][] independentResultArr = new double[independentRowCount][independentColumnCount];

        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int i = 0; i < independentCount; i++) {
                double independentLeftValue = independentLeftArr[independentRowIndex][i];
                for (int independentColumnIndex = 0; independentColumnIndex < independentColumnCount; independentColumnIndex++) {
                    independentResultArr[independentRowIndex][independentColumnIndex] +=
                            independentLeftValue * independentRightArr[i][independentColumnIndex];
                }
            }
        }

        return independentResultArr;
    }

    private void independentScaleArr(double[][] independentArr, double independentScale) {
        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {
            for (int independentColumnIndex = 0; independentColumnIndex < independentArr[0].length; independentColumnIndex++) {
                independentArr[independentRowIndex][independentColumnIndex] *= independentScale;
            }
        }
    }

    // MAIN 데모 테스트

    public static void main(String[] independentArr) {

        double[][] data = {
                {5.0, 5.0, 5.0},
                {5.0, 5.3, 5.17},
                {5.0, 5.3, 5.19},
                {5.0, 5.3, 5.20},
                {5.0, 5.3, 5.21},

                {5.0, 5.3, 5.28},
                {5.0, 5.3, 5.28},
                {5.0, 5.3, 5.28},
                {5.0, 5.3, 5.29},
                {5.0, 8.0, 0.0}

        };

        FastExtendedInfomaxICA_ScienceDirect independentAlgorithm =
                new FastExtendedInfomaxICA_ScienceDirect(
                        5,
                        500000,
                        5e-5,
                        500000L,
                        5e-5
                );

        IndependentResult independentResult =
                independentAlgorithm.independentFit(data);

        System.out.println("Fast Extended Infomax ICA 결과 : 각 성분은 모두 독립적이며 다른 성분의 변화, 데이터, 분포 등과 완전히 상관없으며 다른 성분과 완전히 무관하고 독립적임을 기존의 ICA들 보다 빠르고 효율적이고 강하게 나타냅니다." + independentResult);

    }

}