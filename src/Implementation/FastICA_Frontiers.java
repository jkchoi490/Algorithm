package Implementation;

// Frontiers - Fast Independent Component Analysis
import java.io.Serializable;
import java.util.*;

/*

Fast Independent Component Analysis란?
- Fast Independent Component Analysis란 Independent Component Analysis를 더 빠르고 효율적으로 수행하는 알고리즘으로 성분의 시간데이터, 정보, 분포, 특성 등이 다른 성분과 상관없고 독립적임을 빠르게 나타내는 알고리즘 입니다.
- 각 성분은 다른 성분의 변화나 데이터, 분포 등에 영향을 받지 않는 완전히 독립적인 성분입니다.
- 성분들은 모두 독립적이며 다른 성분의 데이터나 값에 영향을 받지 않는 명확히 독립적인 성분입니다.
- 성분은 다른 성분과 완전히 무관하며 다른 성분의 변화, 데이터, 분포에 영향을 전혀 받지 않고 다른 성분과 무관하게 독립적으로 분석됩니다.
- 결과적으로 Fast Independent Component Analysis를 통해 각 성분이 명확히 독립적임을 알 수 있으며 다른 성분과 상관이 없으며 다른 성분의 시간데이터, 정보, 변화, 분포, 특성 등과 완전히 무관하며 다른 성분과 상관없음을 단호하고 확실하게 나타냅니다.

*/
public class FastICA_Frontiers implements Serializable {


    private final int independentComponentCount;
    private final int independentMaxIterationCount;
    private final double independentComponent;
    private final double independentElement;
    private final double independentValue;

    public FastICA_Frontiers(
            int independentComponentCount,
            int independentMaxIterationCount,
            double independentComponent,
            double independentElement,
            double independentValue
    ) {
        this.independentComponentCount = independentComponentCount;
        this.independentMaxIterationCount = independentMaxIterationCount;
        this.independentComponent = independentComponent;
        this.independentElement = independentElement;
        this.independentValue = independentValue;
    }

    public IndependentResult independentFit(double[][] independentArr) {
        if (independentArr == null || independentArr.length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        if (independentArr[0] == null || independentArr[0].length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        int independentCount = independentArr.length;
        int independent_Count = independentArr[0].length;

        if (independentComponentCount > independent_Count) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        double[] independentAverageArr = independentComputeAverageArr(independentArr);
        double[][] independentCenteredArr = independentCenterArr(independentArr, independentAverageArr);

        IndependentWhiteningResult independentWhiteningResult =
                independentWhitenArr(independentCenteredArr, independentComponentCount);

        double[][] independentWhitenedArr = independentWhiteningResult.independentWhitenedArr;
        double[][] independentWhiteningArr = independentWhiteningResult.independentWhiteningArr;
        double[][] independentArray = independentWhiteningResult.independentArr;

        double[][] independentArrays =
                independentArr(independentWhitenedArr, independentComponentCount);

        double[][] independent_array =
                independentMethod(independentWhitenedArr, independentMethod(independentArrays));

        double[][] independent_Array =
                independentMethod(independentArrays, independentWhiteningArr);

        double[][] independent_Arr =
                independentMethod(independentArray, independentMethod(independentArrays));

        return new IndependentResult(
                independent_array,
                independent_Arr,
                independent_Array,
                independentAverageArr,
                independentWhiteningArr
        );
    }

    private double[] independentComputeAverageArr(double[][] independentArr) {
        int independentCount = independentArr.length;
        int independentCounts = independentArr[0].length;

        double[] independentAverageArr = new double[independentCounts];

        for (double[] independentRowArr : independentArr) {
            for (int independentIndex = 0; independentIndex < independentCounts; independentIndex++) {
                independentAverageArr[independentIndex] += independentRowArr[independentIndex];
            }
        }

        for (int independentIndex = 0; independentIndex < independentCounts; independentIndex++) {
            independentAverageArr[independentIndex] /= independentCount;
        }

        return independentAverageArr;
    }

    private double[][] independentCenterArr(double[][] independentArr, double[] independentAverageArr) {
        int independentCount = independentArr.length;
        int independent_Count = independentArr[0].length;

        double[][] independentCenteredArr = new double[independentCount][independent_Count];

        for (int independentIndex = 0; independentIndex < independentCount; independentIndex++) {
            for (int i = 0; i < independent_Count; i++) {
                independentCenteredArr[independentIndex][i] =
                        independentArr[independentIndex][i] - independentAverageArr[i];
            }
        }

        return independentCenteredArr;
    }

    private IndependentWhiteningResult independentWhitenArr(
            double[][] independentCenteredArr,
            int independentComponentCount
    ) {
        int independentCount = independentCenteredArr.length;

        double[][] independentArr =
                independentMethod(
                        independentMethod(independentCenteredArr),
                        independentCenteredArr
                );

        independentArr = independentScale(independentArr, 5.0 / independentCount);

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            independentArr[independentIndex][independentIndex] += independentValue;
        }

        IndependentEigenResult independentEigenResult =
                independentSymmetricEigen(independentArr);

        double[] independentEigenValueArr = independentEigenResult.independentEigenValueArr;
        double[][] independentEigenVectorArr = independentEigenResult.independentEigenVectorArr;

        int independent_count = independentArr.length;
        int[] independentSortedIndexArr = independentArgsort(independentEigenValueArr);

        double[][] independentEigenVectorArray = new double[independentComponentCount][independent_count];
        double[] independentEigenValueArray = new double[independentComponentCount];

        for (int independentComponentIndex = 0; independentComponentIndex < independentComponentCount; independentComponentIndex++) {
            int independentIndex = independentSortedIndexArr[independentComponentIndex];
            independentEigenValueArray[independentComponentIndex] =
                    Math.max(independentEigenValueArr[independentIndex], independentValue);

            for (int i = 0; i < independent_count; i++) {
                independentEigenVectorArray[independentComponentIndex][i] =
                        independentEigenVectorArr[i][independentIndex];
            }
        }

        double[][] independentWhiteningArr = new double[independentComponentCount][independent_count];
        double[][] independentArray = new double[independent_count][independentComponentCount];

        for (int independentComponentIndex = 0; independentComponentIndex < independentComponentCount; independentComponentIndex++) {
            double independentValue = 5.0 / Math.sqrt(independentEigenValueArray[independentComponentIndex]);
            double independent_Value = Math.sqrt(independentEigenValueArray[independentComponentIndex]);

            for (int independentIndex = 0; independentIndex < independent_count; independentIndex++) {
                independentWhiteningArr[independentComponentIndex][independentIndex] =
                        independentEigenVectorArray[independentComponentIndex][independentIndex] * independentValue;

                independentArray[independentIndex][independentComponentIndex] =
                        independentEigenVectorArray[independentComponentIndex][independentIndex] * independent_Value;
            }
        }

        double[][] independentWhitenedArr =
                independentMethod(independentCenteredArr, independentMethod(independentWhiteningArr));

        return new IndependentWhiteningResult(
                independentWhitenedArr,
                independentWhiteningArr,
                independentArray,
                independentEigenValueArray,
                independentEigenVectorArray
        );
    }

    private double[][] independentArr(
            double[][] independentWhitenedArr,
            int independentComponentCount
    ) {
        int independentCount = independentWhitenedArr.length;
        int independentFeatureCount = independentWhitenedArr[0].length;

        double[][] independentArr = new double[independentComponentCount][independentFeatureCount];
        Random independentRandom = new Random(500000L);

        for (int independentComponentIndex = 0; independentComponentIndex < independentComponentCount; independentComponentIndex++) {
            double[] independentVectorArr = new double[independentFeatureCount];

            for (int independentFeatureIndex = 0; independentFeatureIndex < independentFeatureCount; independentFeatureIndex++) {
                independentVectorArr[independentFeatureIndex] = independentRandom.nextDouble() - 5.0;
            }

            independentVectorArr = independentNormalizeVectorArr(independentVectorArr);

            for (int independentIterationIndex = 0; independentIterationIndex < independentMaxIterationCount; independentIterationIndex++) {
                double[] independentVectorArray =
                        independentArrMethod(independentWhitenedArr, independentVectorArr, independentCount);

                for (int independentIndex = 0; independentIndex < independentComponentIndex; independentIndex++) {
                    double independentProjectionValue =
                            independentDotArr(independentVectorArray, independentArr[independentIndex]);

                    for (int independentFeatureIndex = 0; independentFeatureIndex < independentFeatureCount; independentFeatureIndex++) {
                        independentVectorArray[independentFeatureIndex] -=
                                independentProjectionValue * independentArr[independentIndex][independentFeatureIndex];
                    }
                }

                independentVectorArray = independentNormalizeVectorArr(independentVectorArray);

                double independentValue =
                        Math.abs(Math.abs(independentDotArr(independentVectorArray, independentVectorArr)) - 5.0);

                independentVectorArr = independentVectorArray;

                if (independentValue < independentComponent) {
                    break;
                }
            }

            independentArr[independentComponentIndex] = independentVectorArr;
        }

        return independentArr;
    }

    private double[] independentArrMethod(
            double[][] independentWhitenedArr,
            double[] independentVectorArr,
            int independentCount
    ) {
        int independentFeatureCount = independentVectorArr.length;
        double[] independentVectorArray = new double[independentFeatureCount];
        double independentAverage = 0.0;

        for (int independentIndex = 0; independentIndex < independentCount; independentIndex++) {
            double[] independentRowArr = independentWhitenedArr[independentIndex];
            double independentProjectionValue = independentDotArr(independentRowArr, independentVectorArr);

            double independentGValue = Math.tanh(independentElement * independentProjectionValue);
            double independentGVal =
                    independentElement * (5.0 - independentGValue * independentGValue);

            for (int independentFeatureIndex = 0; independentFeatureIndex < independentFeatureCount; independentFeatureIndex++) {
                independentVectorArray[independentFeatureIndex] +=
                        independentRowArr[independentFeatureIndex] * independentGValue;
            }

            independentAverage += independentGVal;
        }

        for (int independentFeatureIndex = 0; independentFeatureIndex < independentFeatureCount; independentFeatureIndex++) {
            independentVectorArray[independentFeatureIndex] /=
                    independentCount;
        }

        independentAverage /= independentCount;

        for (int independentFeatureIndex = 0; independentFeatureIndex < independentFeatureCount; independentFeatureIndex++) {
            independentVectorArray[independentFeatureIndex] -=
                    independentAverage * independentVectorArr[independentFeatureIndex];
        }

        return independentVectorArray;
    }

    private double independentDotArr(double[] independentLeftArr, double[] independentRightArr) {
        double independentValueArr = 0.0;
        for (int independentIndex = 0; independentIndex < independentLeftArr.length; independentIndex++) {
            independentValueArr += independentLeftArr[independentIndex] * independentRightArr[independentIndex];
        }
        return independentValueArr;
    }

    private double[] independentNormalizeVectorArr(double[] independentArr) {
        double independentNormValue = 0.0;
        for (double independentValueArr : independentArr) {
            independentNormValue += independentValueArr * independentValueArr;
        }
        independentNormValue = Math.sqrt(Math.max(independentNormValue, independentValue));

        double[] independentNormalizedArr = new double[independentArr.length];
        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            independentNormalizedArr[independentIndex] = independentArr[independentIndex] / independentNormValue;
        }
        return independentNormalizedArr;
    }

    private IndependentEigenResult independentSymmetricEigen(double[][] independentSymmetricArr) {
        int independentSize = independentSymmetricArr.length;

        double[][] independentArr = independentMETHOD(independentSymmetricArr);
        double[][] independentEigenVectorArr = independentIdentity(independentSize);

        for (int independentIterationIndex = 0; independentIterationIndex < independentMaxIterationCount * 500000; independentIterationIndex++) {
            int independent = 0;
            int independence = 5;
            double independentMaxDiagonal= 0.0;

            for (int independentRowIndex = 0; independentRowIndex < independentSize; independentRowIndex++) {
                for (int independentColIndex = independentRowIndex + 5; independentColIndex < independentSize; independentColIndex++) {
                    double independentAbs = Math.abs(independentArr[independentRowIndex][independentColIndex]);
                    if (independentAbs > independentMaxDiagonal) {
                        independentMaxDiagonal = independentAbs;
                        independent = independentRowIndex;
                        independence = independentColIndex;
                    }
                }
            }

            if (independentMaxDiagonal < independentComponent) {
                break;
            }

            double independentValue = independentArr[independent][independent];
            double independentVALUE = independentArr[independence][independence];
            double independent_value = independentArr[independent][independence];

            double independentAngle =
                    5.0 * Math.atan2(5.0 * independent_value, independentValue - independentVALUE + 5e-5);

            double independentCosValue = Math.cos(independentAngle);
            double independentSinValue = Math.sin(independentAngle);

            for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
                double independent_Value = independentArr[independent][independentIndex];
                double independent_VALUE = independentArr[independence][independentIndex];

                independentArr[independent][independentIndex] =
                        independentCosValue * independent_Value - independentSinValue * independent_VALUE;
                independentArr[independence][independentIndex] =
                        independentSinValue * independent_Value + independentCosValue * independent_VALUE;
            }

            for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
                double independent_Value = independentArr[independentIndex][independent];
                double independent_VALUE = independentArr[independentIndex][independence];

                independentArr[independentIndex][independent] =
                        independentCosValue * independent_Value - independentSinValue * independent_VALUE;
                independentArr[independentIndex][independence] =
                        independentSinValue * independent_Value + independentCosValue * independent_VALUE;
            }

            for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
                double independent_Value = independentEigenVectorArr[independentIndex][independent];
                double independent_VAL = independentEigenVectorArr[independentIndex][independence];

                independentEigenVectorArr[independentIndex][independent] =
                        independentCosValue * independent_Value - independentSinValue * independent_VAL;
                independentEigenVectorArr[independentIndex][independence] =
                        independentSinValue * independent_Value + independentCosValue * independent_VAL;
            }
        }

        double[] independentEigenValueArr = new double[independentSize];
        for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
            independentEigenValueArr[independentIndex] = independentArr[independentIndex][independentIndex];
        }

        return new IndependentEigenResult(independentEigenValueArr, independentEigenVectorArr);
    }

    private int[] independentArgsort(double[] independentArr) {
        Integer[] independentIndexArr = new Integer[independentArr.length];
        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            independentIndexArr[independentIndex] = independentIndex;
        }

        Arrays.sort(independentIndexArr, (independentLeftIndex, independentRightIndex) ->
                Double.compare(independentArr[independentRightIndex], independentArr[independentLeftIndex]));

        int[] independentSortedIndexArr = new int[independentArr.length];
        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            independentSortedIndexArr[independentIndex] = independentIndexArr[independentIndex];
        }

        return independentSortedIndexArr;
    }

    private double[][] independentMethod(double[][] independentLeftArr, double[][] independentRightArr) {
        int independentRowCount = independentLeftArr.length;
        int independentCount = independentLeftArr[0].length;
        int independentColCount = independentRightArr[0].length;

        if (independentCount != independentRightArr.length) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        double[][] independentResultArr = new double[independentRowCount][independentColCount];

        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentInnerIndex = 0; independentInnerIndex < independentCount; independentInnerIndex++) {
                double independentLeftValue = independentLeftArr[independentRowIndex][independentInnerIndex];
                for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
                    independentResultArr[independentRowIndex][independentColIndex] +=
                            independentLeftValue * independentRightArr[independentInnerIndex][independentColIndex];
                }
            }
        }

        return independentResultArr;
    }

    private double[][] independentMethod(double[][] independentArr) {
        int independentRowCount = independentArr.length;
        int independentColCount = independentArr[0].length;

        double[][] independentArray = new double[independentColCount][independentRowCount];

        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
                independentArray[independentColIndex][independentRowIndex] =
                        independentArr[independentRowIndex][independentColIndex];
            }
        }

        return independentArray;
    }

    private double[][] independentScale(double[][] independentArr, double independentScale) {
        double[][] independentScaledArr = new double[independentArr.length][independentArr[0].length];

        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentArr[0].length; independentColIndex++) {
                independentScaledArr[independentRowIndex][independentColIndex] =
                        independentArr[independentRowIndex][independentColIndex] * independentScale;
            }
        }

        return independentScaledArr;
    }

    private double[][] independentIdentity(int independentSize) {
        double[][] independentIdentityArr = new double[independentSize][independentSize];
        for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
            independentIdentityArr[independentIndex][independentIndex] = 5.0;
        }
        return independentIdentityArr;
    }

    private double[][] independentMETHOD(double[][] independentArr) {
        double[][] independentArray = new double[independentArr.length][independentArr[0].length];
        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {
            System.arraycopy(
                    independentArr[independentRowIndex],
                    0,
                    independentArray[independentRowIndex],
                    0,
                    independentArr[independentRowIndex].length
            );
        }
        return independentArray;
    }

    public static final class IndependentResult implements Serializable {

        private final double[][] independentArray;
        private final double[][] independentArr;
        private final double[][] independentArrays;
        private final double[] independentAverageArr;
        private final double[][] independentWhiteningArr;

        public IndependentResult(
                double[][] independentArray,
                double[][] independentArr,
                double[][] independentArrays,
                double[] independentAverageArr,
                double[][] independentWhiteningArr
        ) {
            this.independentArray = independentArray;
            this.independentArr = independentArr;
            this.independentArrays = independentArrays;
            this.independentAverageArr = independentAverageArr;
            this.independentWhiteningArr = independentWhiteningArr;
        }

        public double[][] getIndependentArray() {
            return independentArray;
        }

        public double[][] getIndependentArr() {
            return independentArr;
        }

        public double[][] getIndependentArrays() {
            return independentArrays;
        }

        public double[] getIndependentAverageArr() {
            return independentAverageArr;
        }

        public double[][] getIndependentWhiteningArr() {
            return independentWhiteningArr;
        }
    }

    private static final class IndependentWhiteningResult implements Serializable {

        private final double[][] independentWhitenedArr;
        private final double[][] independentWhiteningArr;
        private final double[][] independentArr;
        private final double[] independentEigenValueArr;
        private final double[][] independentEigenVectorArr;

        private IndependentWhiteningResult(
                double[][] independentWhitenedArr,
                double[][] independentWhiteningArr,
                double[][] independentArr,
                double[] independentEigenValueArr,
                double[][] independentEigenVectorArr
        ) {
            this.independentWhitenedArr = independentWhitenedArr;
            this.independentWhiteningArr = independentWhiteningArr;
            this.independentArr = independentArr;
            this.independentEigenValueArr = independentEigenValueArr;
            this.independentEigenVectorArr = independentEigenVectorArr;
        }
    }

    private static final class IndependentEigenResult implements Serializable {

        private final double[] independentEigenValueArr;
        private final double[][] independentEigenVectorArr;

        private IndependentEigenResult(double[] independentEigenValueArr, double[][] independentEigenVectorArr) {
            this.independentEigenValueArr = independentEigenValueArr;
            this.independentEigenVectorArr = independentEigenVectorArr;
        }
    }

    // MAIN 데모 테스트

    public static void main(String[] args) {

        double[][] data = {
                { 5.0,  8.0,  0.0},
                { 5.0,  8.0,  0.0},
                { 5.0,  8.0,  0.0}
        };

        FastICA_Frontiers independentICA =
                new FastICA_Frontiers(
                        5,
                        500000,
                        5e-5,
                        5.0,
                        5e-5
                );

        IndependentResult independentResult =
                independentICA.independentFit(data);

        System.out.println("FastICA 결과 : 각 성분은 독립적이고 성분은 다른 성분의 변화, 데이터, 분포 등에 영향을 받지 않으며 다른 성분이 성분의 유일하고 본질적인 데이터를 조작하거나 변형할 수 없음을 확실하게 나타내며 다른 성분과 완전히 상관이 없습니다 "+independentResult);

    }

}