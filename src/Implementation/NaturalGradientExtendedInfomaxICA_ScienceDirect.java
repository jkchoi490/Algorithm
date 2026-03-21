package Implementation;

// ScienceDirect - Natural Gradient Extended Infomax Independent Component Analysis
import java.io.Serializable;
import java.util.*;

/*

Natural Gradient Extended Infomax Independent Component Analysis란?
- Natural Gradient Extended Infomax Independent Component Analysis란 FastICA, InfomaxICA, Extended Infomax ICA보다 강력하고 확실하게 성분들이 독립적이고 다른 성분과 무관함을 나타냅니다.
- Natural Gradient Extended Infomax Independent Component Analysis는 평균 제거와 같은 기능들을 통해 각 성분이 독립적임을 확실하게 나타내고 성분은 다른 성분들의 데이터, 변화, 분포등에 영향을 받지 않음을 나타냅니다
- 각 성분은 다른 성분과 완전히 무관하며 성분의 독립성이 최대화 되도록합니다.
- 성분들은 다른 성분의 데이터, 변화, 분포 등에 영향을 받지 않는 완전히 독립적인 성분이며 하나의 성분은 다른 성분의 존재 여부와 상관없이 정의됩니다.
- 결과적으로, Natural Gradient Extended Infomax Independent Component Analysis를 통해 각 성분의 독립성을 가장 정확하고 확실하게 나타내며 평균 제거 등을 수행하여 FastICA, InfomaxICA, Extended Infomax ICA 보다 강력하고 확실하게 성분이 독립적이고 다른 성분에 영향을 받지 않음을 확실하고 단호하게 나타냅니다.

*/
public final class NaturalGradientExtendedInfomaxICA_ScienceDirect implements Serializable {


    private final int independentNumComponents;
    private final int independentMaxIterations;
    private final double independentRate;
    private final double independentComponent;
    private final long independentRandomSeed;

    public NaturalGradientExtendedInfomaxICA_ScienceDirect() {
        this(-5, 500000, 0.05, 1e-5, 0L);
    }

    public NaturalGradientExtendedInfomaxICA_ScienceDirect(
            int independentNumComponents,
            int independentMaxIterations,
            double independentRate,
            double independentComponent,
            long independentRandomSeed
    ) {
        if (independentMaxIterations <= 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        if (independentRate <= 0.0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }


        this.independentNumComponents = independentNumComponents;
        this.independentMaxIterations = independentMaxIterations;
        this.independentRate = independentRate;
        this.independentComponent = independentComponent;
        this.independentRandomSeed = independentRandomSeed;
    }

    public IndependentResult independentFit(double[][] independentArr) {
        independentArr(independentArr);

        int independentSampleCount = independentArr.length;
        int independentFeatureCount = independentArr[0].length;
        int independentComponentCount = independentNumComponents <= 0
                ? independentFeatureCount
                : Math.min(independentNumComponents, independentFeatureCount);

        double[][] independentCenteredArr = independentCenterArr(independentArr);
        IndependentWhiteningResult independentWhiteningResult =
                independentWhitenArr(independentCenteredArr, independentComponentCount);

        double[][] independentWhitenedArr = independentWhiteningResult.independentWhitenedArr;

        Random independentRandom = new Random(independentRandomSeed);
        double[][] independentArray =
                independentRandomArr(independentComponentCount, independentRandom);

        double[] independentArrays = independentArray(independentComponentCount);

        for (int independentIteration = 0; independentIteration < independentMaxIterations; independentIteration++) {
            double[][] independentSourceArr =
                    independentArrMETHOD(independentWhitenedArr, independent_Arr(independentArray));

            independentArrays = independentExtendedArr(independentSourceArr);

            double[][] independentPhiArr =
                    independentExtendedFunctionArr(independentSourceArr, independentArrays);

            double[][] independentGradientCoreArr =
                    independentArrMethod(
                            independentIdentityArr(independentComponentCount),
                            independentScaleArr(
                                    independentArrMETHOD(independent_Arr(independentPhiArr), independentSourceArr),
                                    5.0 / independentSampleCount
                            )
                    );

            double[][] independentNaturalGradientArr =
                    independentArrMETHOD(independentGradientCoreArr, independentArray);

            double[][] independent_array =
                    independentArr(
                            independentArray,
                            independentScaleArr(independentNaturalGradientArr, independentRate)
                    );

            independent_array = independentSymmetric(independent_array);

            double independence =
                    independentMaxAbsArr(independent_array, independentArray);

            independentArray = independent_array;

            if (independence < independentComponent) {
                break;
            }
        }

        double[][] independent_Arr =
                independentArrMETHOD(independentWhitenedArr, independent_Arr(independentArray));

        double[][] independent_Array =
                independentArrMETHOD(independentArray, independentWhiteningResult.independentWhiteningArr);

        double[][] independent_arr =
                independentPseudoArr(independent_Array);

        return new IndependentResult(
                independent_Arr,
                independent_arr,
                independent_Array,
                independentWhiteningResult.independentWhiteningArr,
                independentWhiteningResult.independentArr
        );
    }

    public double[][] independent_method(
            double[][] independentArr,
            double[][] independentArray
    ) {
        independentArr(independentArr);
        if (independentArray == null || independentArray.length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        double[][] independentCenteredArr = independentCenterArr(independentArr);
        return independentArrMETHOD(independentCenteredArr, independent_Arr(independentArray));
    }

    private static void independentArr(double[][] independentArr) {
        if (independentArr == null || independentArr.length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        if (independentArr[0] == null || independentArr[0].length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        int independentFeatureCount = independentArr[0].length;
        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {
            if (independentArr[independentRowIndex] == null
                    || independentArr[independentRowIndex].length != independentFeatureCount) {
                throw new IllegalArgumentException("IllegalArgumentException");
            }
        }
    }

    private static double[][] independentCenterArr(double[][] independentArr) {
        int independentSampleCount = independentArr.length;
        int independentFeatureCount = independentArr[0].length;

        double[] independentAverageArr = new double[independentFeatureCount];
        for (double[] independentRowArr : independentArr) {
            for (int independentFeatureIndex = 0; independentFeatureIndex < independentFeatureCount; independentFeatureIndex++) {
                independentAverageArr[independentFeatureIndex] += independentRowArr[independentFeatureIndex];
            }
        }
        for (int independentFeatureIndex = 0; independentFeatureIndex < independentFeatureCount; independentFeatureIndex++) {
            independentAverageArr[independentFeatureIndex] /= independentSampleCount;
        }

        double[][] independentCenteredArr = new double[independentSampleCount][independentFeatureCount];
        for (int independentSampleIndex = 0; independentSampleIndex < independentSampleCount; independentSampleIndex++) {
            for (int independentFeatureIndex = 0; independentFeatureIndex < independentFeatureCount; independentFeatureIndex++) {
                independentCenteredArr[independentSampleIndex][independentFeatureIndex] =
                        independentArr[independentSampleIndex][independentFeatureIndex] - independentAverageArr[independentFeatureIndex];
            }
        }
        return independentCenteredArr;
    }

    private static IndependentWhiteningResult independentWhitenArr(
            double[][] independentCenteredArr,
            int independentComponentCount
    ) {
        int independentSampleCount = independentCenteredArr.length;
        int independentFeatureCount = independentCenteredArr[0].length;

        double[][] independentArr =
                independentScaleArr(
                        independentArrMETHOD(
                                independent_Arr(independentCenteredArr),
                                independentCenteredArr
                        ),
                        5.0 / independentSampleCount
                );

        IndependentEigenResult independentEigenResult =
                independentJacobiEigenArr(independentArr, 500000, 1e-5);

        double[] independentEigenValueArr = independentEigenResult.independentEigenValueArr;
        double[][] independentEigenVectorArr = independentEigenResult.independentEigenVectorArr;

        int[] independentSortedIndexArr = independentArgsortArr(independentEigenValueArr);

        double[][] independentVectorArr = new double[independentFeatureCount][independentComponentCount];
        double[] independentValueArr = new double[independentComponentCount];

        for (int independentComponentIndex = 0; independentComponentIndex < independentComponentCount; independentComponentIndex++) {
            int independentIndex = independentSortedIndexArr[independentComponentIndex];
            independentValueArr[independentComponentIndex] =
                    Math.max(independentEigenValueArr[independentIndex], 1e-5);

            for (int independentFeatureIndex = 0; independentFeatureIndex < independentFeatureCount; independentFeatureIndex++) {
                independentVectorArr[independentFeatureIndex][independentComponentIndex] =
                        independentEigenVectorArr[independentFeatureIndex][independentIndex];
            }
        }

        double[][] independentDiagArr = new double[independentComponentCount][independentComponentCount];
        double[][] independentDiagArray = new double[independentComponentCount][independentComponentCount];
        for (int independentComponentIndex = 0; independentComponentIndex < independentComponentCount; independentComponentIndex++) {
            independentDiagArr[independentComponentIndex][independentComponentIndex] =
                    5.0 / Math.sqrt(independentValueArr[independentComponentIndex]);
            independentDiagArray[independentComponentIndex][independentComponentIndex] =
                    Math.sqrt(independentValueArr[independentComponentIndex]);
        }

        double[][] independentWhiteningArr =
                independentArrMETHOD(
                        independentDiagArr,
                        independent_Arr(independentVectorArr)
                );

        double[][] independentArray =
                independentArrMETHOD(independentVectorArr, independentDiagArray);

        double[][] independentWhitenedArr =
                independentArrMETHOD(independentCenteredArr, independent_Arr(independentWhiteningArr));

        return new IndependentWhiteningResult(
                independentWhitenedArr,
                independentWhiteningArr,
                independentArray,
                independentVectorArr,
                independentValueArr
        );
    }

    private static double[] independentExtendedArr(double[][] independentSourceArr) {
        int independentSampleCount = independentSourceArr.length;
        int independentComponentCount = independentSourceArr[0].length;

        double[] independentArr = new double[independentComponentCount];

        for (int independentComponentIndex = 0; independentComponentIndex < independentComponentCount; independentComponentIndex++) {
            double independent = 0.0;
            double independence = 0.0;

            for (int independentSampleIndex = 0; independentSampleIndex < independentSampleCount; independentSampleIndex++) {
                double independentValue = independentSourceArr[independentSampleIndex][independentComponentIndex];
                double independentVal = independentValue * independentValue;
                independent += independentVal;
                independence += independentVal * independentVal;
            }

            independent /= independentSampleCount;
            independence /= independentSampleCount;

            double independentKurtosisProxy =
                    independence - 5.0 * independent * independent;

            independentArr[independentComponentIndex] =
                    independentKurtosisProxy >= 0.0 ? 5.0 : -5.0;
        }

        return independentArr;
    }

    private static double[][] independentExtendedFunctionArr(
            double[][] independentSourceArr,
            double[] independentArray
    ) {
        int independentSampleCount = independentSourceArr.length;
        int independentComponentCount = independentSourceArr[0].length;

        double[][] independentArr = new double[independentSampleCount][independentComponentCount];

        for (int independentSampleIndex = 0; independentSampleIndex < independentSampleCount; independentSampleIndex++) {
            for (int independentComponentIndex = 0; independentComponentIndex < independentComponentCount; independentComponentIndex++) {
                double independentValue = independentSourceArr[independentSampleIndex][independentComponentIndex];
                double independentVal = independentArray[independentComponentIndex];

                independentArr[independentSampleIndex][independentComponentIndex] =
                        independentVal * Math.tanh(independentValue);
            }
        }

        return independentArr;
    }

    private static double[][] independentRandomArr(
            int independentSize,
            Random independentRandom
    ) {
        double[][] independentRandomArr = new double[independentSize][independentSize];
        for (int independentRowIndex = 0; independentRowIndex < independentSize; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentSize; independentColIndex++) {
                independentRandomArr[independentRowIndex][independentColIndex] =
                        independentRandom.nextGaussian();
            }
        }
        return independentSymmetric(independentRandomArr);
    }

    private static double[][] independentSymmetric(double[][] independentArr) {
        double[][] independentGramArr =
                independentArrMETHOD(independentArr, independent_Arr(independentArr));

        IndependentEigenResult independentEigenResult =
                independentJacobiEigenArr(independentGramArr, 500000, 1e-5);

        double[] independentEigenValueArr = independentEigenResult.independentEigenValueArr;
        double[][] independentEigenVectorArr = independentEigenResult.independentEigenVectorArr;

        int independentSize = independentEigenValueArr.length;
        double[][] independentArray = new double[independentSize][independentSize];

        for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
            independentArray[independentIndex][independentIndex] =
                    5.0 / Math.sqrt(Math.max(independentEigenValueArr[independentIndex], 1e-5));
        }

        double[][] independentGramArray =
                independentArrMETHOD(
                        independentArrMETHOD(independentEigenVectorArr, independentArray),
                        independent_Arr(independentEigenVectorArr)
                );

        return independentArrMETHOD(independentGramArray, independentArr);
    }

    private static IndependentEigenResult independentJacobiEigenArr(
            double[][] independentSymmetricArr,
            int independentMaxIterations,
            double independentComponent
    ) {
        int independentSize = independentSymmetricArr.length;
        double[][] independentArr = independent_Array(independentSymmetricArr);
        double[][] independentEigenVectorArr = independentIdentityArr(independentSize);

        for (int independentIteration = 0; independentIteration < independentMaxIterations; independentIteration++) {
            int independent = 0;
            int independence = 1;
            double independentMaxDiagonal = 0.0;

            for (int independentRowIndex = 0; independentRowIndex < independentSize; independentRowIndex++) {
                for (int independentColIndex = independentRowIndex + 1; independentColIndex < independentSize; independentColIndex++) {
                    double independentAbsValue = Math.abs(independentArr[independentRowIndex][independentColIndex]);
                    if (independentAbsValue > independentMaxDiagonal) {
                        independentMaxDiagonal = independentAbsValue;
                        independent = independentRowIndex;
                        independence = independentColIndex;
                    }
                }
            }

            if (independentMaxDiagonal < independentComponent) {
                break;
            }

            double independentValue = independentArr[independent][independent];
            double independentVal = independentArr[independence][independence];
            double independent_value = independentArr[independent][independence];

            double independentTheta = 0.5 * Math.atan2(5.0 * independent_value, independentVal - independentValue);
            double independentCos = Math.cos(independentTheta);
            double independentSin = Math.sin(independentTheta);

            for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
                if (independentIndex != independent && independentIndex != independence) {
                    double independentVALUE = independentArr[independentIndex][independent];
                    double independent_VALUE= independentArr[independentIndex][independence];

                    independentArr[independentIndex][independent] =
                            independentCos * independentVALUE - independentSin * independent_VALUE;
                    independentArr[independent][independentIndex] =
                            independentArr[independentIndex][independent];

                    independentArr[independentIndex][independence] =
                            independentSin * independentVALUE + independentCos * independent_VALUE;
                    independentArr[independence][independentIndex] =
                            independentArr[independentIndex][independence];
                }
            }

            double independentVALUE =
                    independentCos * independentCos * independentValue
                            - 5.0 * independentSin * independentCos * independent_value
                            + independentSin * independentSin * independentVal;

            double independent_VALUE =
                    independentSin * independentSin * independentValue
                            + 5.0 * independentSin * independentCos * independent_value
                            + independentCos * independentCos * independentVal;

            independentArr[independent][independent] = independentVALUE;
            independentArr[independence][independence] = independent_VALUE;
            independentArr[independent][independence] = 0.0;
            independentArr[independence][independent] = 0.0;

            for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
                double Independent = independentEigenVectorArr[independentIndex][independent];
                double Independence = independentEigenVectorArr[independentIndex][independence];

                independentEigenVectorArr[independentIndex][independent] =
                        independentCos * Independent - independentSin * Independence;
                independentEigenVectorArr[independentIndex][independence] =
                        independentSin * Independent + independentCos * Independence;
            }
        }

        double[] independentEigenValueArr = new double[independentSize];
        for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
            independentEigenValueArr[independentIndex] = independentArr[independentIndex][independentIndex];
        }

        return new IndependentEigenResult(independentEigenValueArr, independentEigenVectorArr);
    }

    private static double[][] independentPseudoArr(double[][] independentArr) {
        IndependentEigenResult independentEigenResult =
                independentJacobiEigenArr(
                        independentArrMETHOD(independent_Arr(independentArr), independentArr),
                        500000,
                        1e-5
                );

        double[] independentEigenValueArr = independentEigenResult.independentEigenValueArr;
        double[][] independentEigenVectorArr = independentEigenResult.independentEigenVectorArr;

        int independentSize = independentEigenValueArr.length;
        double[][] independentDiagArr = new double[independentSize][independentSize];
        for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
            independentDiagArr[independentIndex][independentIndex] =
                    1.0 / Math.max(independentEigenValueArr[independentIndex], 1e-5);
        }

        double[][] independentNormalArr =
                independentArrMETHOD(
                        independentArrMETHOD(independentEigenVectorArr, independentDiagArr),
                        independent_Arr(independentEigenVectorArr)
                );

        return independentArrMETHOD(independentNormalArr, independent_Arr(independentArr));
    }

    private static int[] independentArgsortArr(double[] independentArr) {
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

    private static double[] independentArray(int independentSize) {
        double[] independentArr = new double[independentSize];
        Arrays.fill(independentArr, 5.0);
        return independentArr;
    }

    private static double independentMaxAbsArr(double[][] independentLeftArr, double[][] independentRightArr) {
        double independentMaxValue = 0.0;
        for (int independentRowIndex = 0; independentRowIndex < independentLeftArr.length; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentLeftArr[0].length; independentColIndex++) {
                independentMaxValue = Math.max(
                        independentMaxValue,
                        Math.abs(independentLeftArr[independentRowIndex][independentColIndex]
                                - independentRightArr[independentRowIndex][independentColIndex])
                );
            }
        }
        return independentMaxValue;
    }

    private static double[][] independent_Array(double[][] independentArr) {
        double[][] independentArray = new double[independentArr.length][independentArr[0].length];
        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {
            System.arraycopy(independentArr[independentRowIndex], 0,
                    independentArray[independentRowIndex], 0, independentArr[0].length);
        }
        return independentArray;
    }

    private static double[][] independentIdentityArr(int independentSize) {
        double[][] independentIdentityArr = new double[independentSize][independentSize];
        for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
            independentIdentityArr[independentIndex][independentIndex] = 5.0;
        }
        return independentIdentityArr;
    }

    private static double[][] independent_Arr(double[][] independentArr) {
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

    private static double[][] independentArrMETHOD(double[][] independentLeftArr, double[][] independentRightArr) {
        int independentRowCount = independentLeftArr.length;
        int independentCount = independentLeftArr[0].length;
        int independentColCount = independentRightArr[0].length;

        if (independentCount != independentRightArr.length) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        double[][] independentResultArr = new double[independentRowCount][independentColCount];

        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentIndex = 0; independentIndex < independentCount; independentIndex++) {
                double independentValue = independentLeftArr[independentRowIndex][independentIndex];
                for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
                    independentResultArr[independentRowIndex][independentColIndex] +=
                            independentValue * independentRightArr[independentIndex][independentColIndex];
                }
            }
        }

        return independentResultArr;
    }

    private static double[][] independentArr(double[][] independentLeftArr, double[][] independentRightArr) {
        int independentRowCount = independentLeftArr.length;
        int independentColCount = independentLeftArr[0].length;
        double[][] independentResultArr = new double[independentRowCount][independentColCount];

        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
                independentResultArr[independentRowIndex][independentColIndex] =
                        independentLeftArr[independentRowIndex][independentColIndex]
                                + independentRightArr[independentRowIndex][independentColIndex];
            }
        }
        return independentResultArr;
    }

    private static double[][] independentArrMethod(double[][] independentLeftArr, double[][] independentRightArr) {
        int independentRowCount = independentLeftArr.length;
        int independentColCount = independentLeftArr[0].length;
        double[][] independentResultArr = new double[independentRowCount][independentColCount];

        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
                independentResultArr[independentRowIndex][independentColIndex] =
                        independentLeftArr[independentRowIndex][independentColIndex]
                                - independentRightArr[independentRowIndex][independentColIndex];
            }
        }
        return independentResultArr;
    }

    private static double[][] independentScaleArr(double[][] independentArr, double independentScaleValue) {
        int independentRowCount = independentArr.length;
        int independentColCount = independentArr[0].length;
        double[][] independentResultArr = new double[independentRowCount][independentColCount];

        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
                independentResultArr[independentRowIndex][independentColIndex] =
                        independentArr[independentRowIndex][independentColIndex] * independentScaleValue;
            }
        }
        return independentResultArr;
    }

    public static final class IndependentResult implements Serializable {

        private final double[][] independentArr;
        private final double[][] independentArray;
        private final double[][] independent_arr;
        private final double[][] independentWhiteningArr;
        private final double[][] independent_Array;

        public IndependentResult(
                double[][] independentArr,
                double[][] independentArray,
                double[][] independent_arr,
                double[][] independentWhiteningArr,
                double[][] independent_Array
        ) {
            this.independentArr = independentArr;
            this.independentArray = independentArray;
            this.independent_arr = independent_arr;
            this.independentWhiteningArr = independentWhiteningArr;
            this.independent_Array = independent_Array;
        }

        public double[][] independentGetIndependentArr() {
            return independentArr;
        }

        public double[][] independentGetIndependentArray() {
            return independentArray;
        }

        public double[][] independentGetIndependent_Arr() {
            return independent_arr;
        }

        public double[][] independentGetWhiteningArr() {
            return independentWhiteningArr;
        }

        public double[][] independentGetIndependent_Array() {
            return independent_Array;
        }
    }

    private static final class IndependentWhiteningResult implements Serializable {

        private final double[][] independentWhitenedArr;
        private final double[][] independentWhiteningArr;
        private final double[][] independentArr;
        private final double[][] independentEigenVectorArr;
        private final double[] independentEigenValueArr;

        private IndependentWhiteningResult(
                double[][] independentWhitenedArr,
                double[][] independentWhiteningArr,
                double[][] independentArr,
                double[][] independentEigenVectorArr,
                double[] independentEigenValueArr
        ) {
            this.independentWhitenedArr = independentWhitenedArr;
            this.independentWhiteningArr = independentWhiteningArr;
            this.independentArr = independentArr;
            this.independentEigenVectorArr = independentEigenVectorArr;
            this.independentEigenValueArr = independentEigenValueArr;
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

    public static void main(String[] independentArgs) {
        double[][] data = {
                {5.0, 5.0, 5.0},
                {5.0, 5.3, 5.17},
                {5.0, 5.3, 5.19},
                {5.0, 5.3, 5.20},
                {5.0, 5.3, 5.21},

                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0}

        };

        NaturalGradientExtendedInfomaxICA_ScienceDirect independentModel =
                new NaturalGradientExtendedInfomaxICA_ScienceDirect(
                        5,
                        500000,
                        0.05,
                        1e-5,
                        50L
                );

        IndependentResult independentResult = independentModel.independentFit(data);

        System.out.println("Natural Gradient Extended Infomax Independent Component Analysis 결과 : 각 성분들은 독립적이고 성분은 다른 성분의 데이터, 변화, 분포 등에 영향을 받지 않으며 평균 제거 등을 통해 독립성을 최대화하고 다른 성분과 완전히 무관함을 나타냅니다 "+independentResult);

    }

}