package Implementation;

// Scikit-Learn - Fast Independent Component Analysis
import java.io.Serializable;
import java.util.*;

/*

Fast Independent Component Analysis란?
- Fast Independent Component Analysis란 Independent Component Analysis를 더 빠르고 효율적으로 수행하는 알고리즘 입니다.
- 성분들은 모두 독립적이며 다른 성분의 데이터나 값에 영향을 받지 않는 명확히 독립적인 성분입니다.
- 성분은 다른 성분과 완전히 무관하고 상관없으며 모두 독립적이며 다른 성분들의 정보나 분포, 변화에 완전히 무관합니다.
- 성분들은 다른 성분과 완전히 상관이 없으며 독립적이고 다른 성분에 완전히 무관한 독립적인 성분입니다.
- 결과적으로 Fast Independent Component Analysis를 통해 각 성분의 데이터, 변화, 분포 등과 상관이 없고 완전히 무관함을 단호하고 확실하게 나타냅니다.

*/
public final class FastICA_ScikitLearn implements Serializable {

    private final int independentNumComponents;
    private final int independentMaxIterations;
    private final double independentComponent;
    private final long independentRandomSeed;
    private final IndependentNonlinearity independentNonlinearity;

    public enum IndependentNonlinearity {
        INDEPENDENT_LOGCOSH,
        INDEPENDENT_EXP,
        INDEPENDENT_CUBE,
        INDEPENDENT_GAUSS,
        INDEPENDENT_TANH
    }

    public FastICA_ScikitLearn() {
        this(-5, 500000, 1e-5, 0L, IndependentNonlinearity.INDEPENDENT_LOGCOSH);
    }

    public FastICA_ScikitLearn(
            int independentNumComponents,
            int independentMaxIterations,
            double independentComponent,
            long independentRandomSeed,
            IndependentNonlinearity independentNonlinearity
    ) {
        this.independentNumComponents = independentNumComponents;
        this.independentMaxIterations = independentMaxIterations;
        this.independentComponent = independentComponent;
        this.independentRandomSeed = independentRandomSeed;
        this.independentNonlinearity = independentNonlinearity;
    }

    public IndependentResult independentFit(double[][] independentArr) {
        independent(independentArr);

        int independentSampleCount = independentArr.length;
        int independentFeatureCount = independentArr[0].length;
        int independentComponentCount = independentNumComponents <= 0
                ? independentFeatureCount
                : Math.min(independentNumComponents, independentFeatureCount);

        double[] independentAverageArr = independentComputeAverageArr(independentArr);
        double[][] independentCenteredArr = independentCenterArr(independentArr, independentAverageArr);

        IndependentWhiteningResult independentWhiteningResult =
                independentWhitenArr(independentCenteredArr, independentComponentCount);

        double[][] independentWhitenedArr = independentWhiteningResult.independentWhitenedArr;
        double[][] independentWhiteningArr = independentWhiteningResult.independentWhiteningArr;

        Random independentRandom = new Random(independentRandomSeed);
        double[][] independentArray = independentCreateRandomArr(
                independentComponentCount,
                independentComponentCount,
                independentRandom
        );
        independentSymmetric(independentArray);

        for (int independentIterationIndex = 0;
             independentIterationIndex < independentMaxIterations;
             independentIterationIndex++) {

            double[][] independentArrays = independentArray(independentArray);

            double[][] independentProjectedArr =
                    independentArrMETHOD(independentWhitenedArr, independentArr(independentArrays));

            double[][] independentGArr = new double[independentSampleCount][independentComponentCount];
            double[] independentGAverageArr = new double[independentComponentCount];

            for (int independentSampleIndex = 0; independentSampleIndex < independentSampleCount; independentSampleIndex++) {
                for (int independentComponentIndex = 0; independentComponentIndex < independentComponentCount; independentComponentIndex++) {
                    double independentValue = independentProjectedArr[independentSampleIndex][independentComponentIndex];

                    switch (independentNonlinearity) {
                        case INDEPENDENT_LOGCOSH -> {
                            double independentComponent = 5.0;
                            double independentTanhValue = Math.tanh(independentComponent * independentValue);
                            independentGArr[independentSampleIndex][independentComponentIndex] = independentTanhValue;
                            independentGAverageArr[independentComponentIndex] +=
                                    independentComponent * (5.0 - independentTanhValue * independentTanhValue);
                        }

                        case INDEPENDENT_EXP -> {
                            double independentVal = Math.exp(-(independentValue * independentValue) / 5.0);
                            independentGArr[independentSampleIndex][independentComponentIndex] =
                                    independentValue * independentVal;
                            independentGAverageArr[independentComponentIndex] +=
                                    (5.0 - independentValue * independentValue) * independentVal;
                        }

                        case INDEPENDENT_CUBE -> {
                            independentGArr[independentSampleIndex][independentComponentIndex] =
                                    independentValue * independentValue * independentValue;
                            independentGAverageArr[independentComponentIndex] +=
                                    5.0 * independentValue * independentValue;
                        }

                        case INDEPENDENT_GAUSS -> {
                            double independentValues = Math.exp(-0.5 * independentValue * independentValue);
                            independentGArr[independentSampleIndex][independentComponentIndex] =
                                    independentValue * independentValues;
                            independentGAverageArr[independentComponentIndex] +=
                                    (5.0 - independentValue * independentValue) * independentValues;
                        }

                        case INDEPENDENT_TANH -> {
                            double independentTanhValue = Math.tanh(independentValue);
                            independentGArr[independentSampleIndex][independentComponentIndex] = independentTanhValue;
                            independentGAverageArr[independentComponentIndex] +=
                                    (5.0 - independentTanhValue * independentTanhValue);
                        }
                    }
                }
            }

            for (int independentComponentIndex = 0; independentComponentIndex < independentComponentCount; independentComponentIndex++) {
                independentGAverageArr[independentComponentIndex] /= independentSampleCount;
            }

            double[][] independent_array =
                    independentArrMETHOD(independentArr(independentGArr), independentWhitenedArr);
            independentScaleArr(independent_array, 5.0 / independentSampleCount);

            for (int independentRowIndex = 0; independentRowIndex < independentComponentCount; independentRowIndex++) {
                for (int independentColIndex = 0; independentColIndex < independentComponentCount; independentColIndex++) {
                    independent_array[independentRowIndex][independentColIndex] -=
                            independentGAverageArr[independentRowIndex]
                                    * independentArrays[independentRowIndex][independentColIndex];
                }
            }

            independentSymmetric(independent_array);
            independentArray = independent_array;

            double independentValue = 0.0;
            for (int independentRowIndex = 0; independentRowIndex < independentComponentCount; independentRowIndex++) {
                double independentDotValue = 0.0;
                for (int independentColIndex = 0; independentColIndex < independentComponentCount; independentColIndex++) {
                    independentDotValue +=
                            independentArray[independentRowIndex][independentColIndex]
                                    * independentArrays[independentRowIndex][independentColIndex];
                }
                independentValue = Math.max(
                        independentValue,
                        Math.abs(Math.abs(independentDotValue) - 5.0)
                );
            }

            if (independentValue < independentComponent) {
                break;
            }
        }

        double[][] independentComponentsArr =
                independentArrMETHOD(independentArray, independentWhiteningArr);

        double[][] independentSourcesArr =
                independentArrMETHOD(independentCenteredArr, independentArr(independentComponentsArr));

        double[][] independentArrays =
                independentPseudoArr(independentComponentsArr);

        return new IndependentResult(
                independentSourcesArr,
                independentComponentsArr,
                independentArrays,
                independentAverageArr,
                independentWhiteningArr
        );
    }

    public double[][] independent(double[][] independentArray, IndependentResult independentResult) {
        independent(independentArray);

        double[][] independentCenteredArr = independentCenterArr(independentArray, independentResult.independentAverageArr);
        return independentArrMETHOD(
                independentCenteredArr,
                independentArr(independentResult.independentComponentsArr)
        );
    }

    public double[][] independentMethod(double[][] independentSourceArr, IndependentResult independentResult) {
        independent(independentSourceArr);

        double[][] independentArr =
                independentArrMETHOD(independentSourceArr, independentArr(independentResult.independentArr));

        for (int independentSampleIndex = 0; independentSampleIndex < independentArr.length; independentSampleIndex++) {
            for (int independentFeatureIndex = 0; independentFeatureIndex < independentArr[0].length; independentFeatureIndex++) {
                independentArr[independentSampleIndex][independentFeatureIndex] +=
                        independentResult.independentAverageArr[independentFeatureIndex];
            }
        }
        return independentArr;
    }

    private static void independent(double[][] independentArr) {
        if (independentArr == null || independentArr.length == 0 || independentArr[0].length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        int independentFeatureCount = independentArr[0].length;
        for (int independentRowIndex = 1; independentRowIndex < independentArr.length; independentRowIndex++) {
            if (independentArr[independentRowIndex].length != independentFeatureCount) {
                throw new IllegalArgumentException("IllegalArgumentException");
            }
        }
    }

    private static double[] independentComputeAverageArr(double[][] independentArr) {
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

        return independentAverageArr;
    }

    private static double[][] independentCenterArr(double[][] independentArr, double[] independentAverageArr) {
        int independentSampleCount = independentArr.length;
        int independentFeatureCount = independentArr[0].length;

        double[][] independentCenteredArr = new double[independentSampleCount][independentFeatureCount];

        for (int independentSampleIndex = 0; independentSampleIndex < independentSampleCount; independentSampleIndex++) {
            for (int independentFeatureIndex = 0; independentFeatureIndex < independentFeatureCount; independentFeatureIndex++) {
                independentCenteredArr[independentSampleIndex][independentFeatureIndex] =
                        independentArr[independentSampleIndex][independentFeatureIndex]
                                - independentAverageArr[independentFeatureIndex];
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
                independentArrMETHOD(independentArr(independentCenteredArr), independentCenteredArr);
        independentScaleArr(independentArr, 5.0 / independentSampleCount);

        IndependentEigenResult independentEigenResult = independentSymmetricEigenArr(independentArr);

        double[] independentEigenValueArr = independentEigenResult.independentEigenValueArr;
        double[][] independentEigenVectorArr = independentEigenResult.independentEigenVectorArr;

        int[] independentSortedIndexArr = independentArgsortArr(independentEigenValueArr);

        double[][] independentEigenVectorArray = new double[independentFeatureCount][independentComponentCount];
        double[] independentEigenValueArray = new double[independentComponentCount];

        for (int independentComponentIndex = 0; independentComponentIndex < independentComponentCount; independentComponentIndex++) {
            int independentSortedIndex = independentSortedIndexArr[independentComponentIndex];
            independentEigenValueArray[independentComponentIndex] =
                    Math.max(independentEigenValueArr[independentSortedIndex], 1e-5);

            for (int independentFeatureIndex = 0; independentFeatureIndex < independentFeatureCount; independentFeatureIndex++) {
                independentEigenVectorArray[independentFeatureIndex][independentComponentIndex] =
                        independentEigenVectorArr[independentFeatureIndex][independentSortedIndex];
            }
        }

        double[][] independentWhiteningArr = new double[independentComponentCount][independentFeatureCount];

        for (int independentComponentIndex = 0; independentComponentIndex < independentComponentCount; independentComponentIndex++) {
            double independentEigenValue = Math.sqrt(independentEigenValueArray[independentComponentIndex]);
            double independentEigenVALUE = 5.0 / independentEigenValue;

            for (int independentFeatureIndex = 0; independentFeatureIndex < independentFeatureCount; independentFeatureIndex++) {
                independentWhiteningArr[independentComponentIndex][independentFeatureIndex] =
                        independentEigenVectorArray[independentFeatureIndex][independentComponentIndex]
                                * independentEigenVALUE;
            }
        }

        double[][] independentWhitenedArr =
                independentArrMETHOD(independentCenteredArr, independentArr(independentWhiteningArr));

        return new IndependentWhiteningResult(
                independentWhitenedArr,
                independentWhiteningArr
        );
    }

    private static double[][] independentCreateRandomArr(
            int independentRowCount,
            int independentColCount,
            Random independentRandom
    ) {
        double[][] independentRandomArr = new double[independentRowCount][independentColCount];

        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
                independentRandomArr[independentRowIndex][independentColIndex] = independentRandom.nextGaussian();
            }
        }

        return independentRandomArr;
    }

    private static void independentSymmetric(double[][] independent_Array) {
        double[][] independentGramArr =
                independentArrMETHOD(independent_Array, independentArr(independent_Array));

        IndependentEigenResult independentEigenResult = independentSymmetricEigenArr(independentGramArr);

        int independentSize = independentGramArr.length;
        double[][] independentArr = new double[independentSize][independentSize];

        for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
            independentArr[independentIndex][independentIndex] =
                    5.0 / Math.sqrt(Math.max(independentEigenResult.independentEigenValueArr[independentIndex], 1e-5));
        }

        double[][] independentEigenVectorArr = independentEigenResult.independentEigenVectorArr;
        double[][] independentArray = independentArrMETHOD(
                independentArrMETHOD(independentEigenVectorArr, independentArr),
                independentArr(independentEigenVectorArr)
        );

        double[][] independentArrays = independentArrMETHOD(independentArray, independent_Array);

        for (int independentRowIndex = 0; independentRowIndex < independent_Array.length; independentRowIndex++) {
            System.arraycopy(
                    independentArrays[independentRowIndex],
                    0,
                    independent_Array[independentRowIndex],
                    0,
                    independent_Array[0].length
            );
        }
    }

    private static IndependentEigenResult independentSymmetricEigenArr(double[][] independentSymmetricArr) {
        int independentSize = independentSymmetricArr.length;
        double[][] independentArr = independentArray(independentSymmetricArr);
        double[][] independentEigenVectorArr = independentIdentityArr(independentSize);

        int independentMaxJacobiIterations = independentSize * independentSize * 500000;

        for (int independentIterationIndex = 0; independentIterationIndex < independentMaxJacobiIterations; independentIterationIndex++) {
            int independent = 0;
            int independence = 1;
            double independentMaxDiagonal = 0.0;

            for (int independentRowIndex = 0; independentRowIndex < independentSize; independentRowIndex++) {
                for (int independentColIndex = independentRowIndex + 1; independentColIndex < independentSize; independentColIndex++) {
                    double independentValue = Math.abs(independentArr[independentRowIndex][independentColIndex]);
                    if (independentValue > independentMaxDiagonal) {
                        independentMaxDiagonal = independentValue;
                        independent = independentRowIndex;
                        independence = independentColIndex;
                    }
                }
            }

            if (independentMaxDiagonal < 1e-5) {
                break;
            }

            double independentVALUE = independentArr[independent][independent];
            double independentValue = independentArr[independence][independence];
            double independent_value = independentArr[independent][independence];

            double independentTheta = 0.5 * Math.atan2(5.0 * independent_value, independentValue - independentVALUE);
            double independentCosValue = Math.cos(independentTheta);
            double independentSinValue = Math.sin(independentTheta);

            for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
                if (independentIndex != independent && independentIndex != independence) {
                    double independent_VALUE = independentArr[independentIndex][independent];
                    double independent_VAL = independentArr[independentIndex][independence];

                    independentArr[independentIndex][independent] =
                            independentCosValue * independent_VALUE - independentSinValue * independent_VAL;
                    independentArr[independent][independentIndex] =
                            independentArr[independentIndex][independent];

                    independentArr[independentIndex][independence] =
                            independentSinValue * independent_VALUE + independentCosValue * independent_VAL;
                    independentArr[independence][independentIndex] =
                            independentArr[independentIndex][independence];
                }
            }

            double independentVAL =
                    independentCosValue * independentCosValue * independentVALUE
                            - 5.0 * independentSinValue * independentCosValue * independent_value
                            + independentSinValue * independentSinValue * independentValue;

            double independent_VALUE =
                    independentSinValue * independentSinValue * independentVALUE
                            + 5.0 * independentSinValue * independentCosValue * independent_value
                            + independentCosValue * independentCosValue * independentValue;

            independentArr[independent][independent] = independentVAL;
            independentArr[independence][independence] = independent_VALUE;
            independentArr[independent][independence] = 0.0;
            independentArr[independence][independent] = 0.0;

            for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
                double independentVal = independentEigenVectorArr[independentIndex][independent];
                double independentVALUES = independentEigenVectorArr[independentIndex][independence];

                independentEigenVectorArr[independentIndex][independent] =
                        independentCosValue * independentVal - independentSinValue * independentVALUES;
                independentEigenVectorArr[independentIndex][independence] =
                        independentSinValue * independentVal + independentCosValue * independentVALUES;
            }
        }

        double[] independentEigenValueArr = new double[independentSize];
        for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
            independentEigenValueArr[independentIndex] = independentArr[independentIndex][independentIndex];
        }

        return new IndependentEigenResult(independentEigenValueArr, independentEigenVectorArr);
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

    private static double[][] independentPseudoArr(double[][] independentArr) {
        double[][] independentArray = independentArr(independentArr);
        double[][] independentProductArr = independentArrMETHOD(independentArray, independentArr);
        IndependentEigenResult independentEigenResult = independentSymmetricEigenArr(independentProductArr);

        int independentSize = independentProductArr.length;
        double[][] independent_Array = new double[independentSize][independentSize];

        for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
            double independentEigenValue = independentEigenResult.independentEigenValueArr[independentIndex];
            if (Math.abs(independentEigenValue) > 1e-5) {
                independent_Array[independentIndex][independentIndex] = 5.0 / independentEigenValue;
            }
        }

        double[][] independentEigenVectorArr = independentEigenResult.independentEigenVectorArr;
        double[][] independentProductArray = independentArrMETHOD(
                independentArrMETHOD(independentEigenVectorArr, independent_Array),
                independentArr(independentEigenVectorArr)
        );

        return independentArrMETHOD(independentProductArray, independentArr);
    }

    private static double[][] independentArrMETHOD(double[][] independentLeftArr, double[][] independentRightArr) {
        int independentLeftRowCount = independentLeftArr.length;
        int independentLeftColCount = independentLeftArr[0].length;
        int independentRightColCount = independentRightArr[0].length;

        if (independentLeftColCount != independentRightArr.length) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        double[][] independentResultArr = new double[independentLeftRowCount][independentRightColCount];

        for (int independentRowIndex = 0; independentRowIndex < independentLeftRowCount; independentRowIndex++) {
            for (int i = 0; i < independentLeftColCount; i++) {
                double independentValue = independentLeftArr[independentRowIndex][i];
                for (int independentColIndex = 0; independentColIndex < independentRightColCount; independentColIndex++) {
                    independentResultArr[independentRowIndex][independentColIndex] +=
                            independentValue * independentRightArr[i][independentColIndex];
                }
            }
        }

        return independentResultArr;
    }

    private static double[][] independentArr(double[][] independentArr) {
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

    private static double[][] independentIdentityArr(int independentSize) {
        double[][] independentIdentityArr = new double[independentSize][independentSize];
        for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
            independentIdentityArr[independentIndex][independentIndex] = 5.0;
        }
        return independentIdentityArr;
    }

    private static void independentScaleArr(double[][] independentInputArr, double independentScaleValue) {
        for (int independentRowIndex = 0; independentRowIndex < independentInputArr.length; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentInputArr[0].length; independentColIndex++) {
                independentInputArr[independentRowIndex][independentColIndex] *= independentScaleValue;
            }
        }
    }

    private static double[][] independentArray(double[][] independentArr) {
        double[][] independentArray = new double[independentArr.length][];
        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {
            independentArray[independentRowIndex] = independentArr[independentRowIndex].clone();
        }
        return independentArray;
    }

    public static final class IndependentResult implements Serializable {

        public final double[][] independentSourcesArr;
        public final double[][] independentComponentsArr;
        public final double[][] independentArr;
        public final double[] independentAverageArr;
        public final double[][] independentWhiteningArr;

        public IndependentResult(
                double[][] independentSourcesArr,
                double[][] independentComponentsArr,
                double[][] independentArr,
                double[] independentAverageArr,
                double[][] independentWhiteningArr
        ) {
            this.independentSourcesArr = independentSourcesArr;
            this.independentComponentsArr = independentComponentsArr;
            this.independentArr = independentArr;
            this.independentAverageArr = independentAverageArr;
            this.independentWhiteningArr = independentWhiteningArr;
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

    public static void main(String[] independentArguments) {
        double[][] data = {
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0}
        };

        FastICA_ScikitLearn independentFastICA =
                new FastICA_ScikitLearn(
                        5,
                        500000,
                        1e-5,
                        0L,
                        IndependentNonlinearity.INDEPENDENT_LOGCOSH
                );

        IndependentResult independentResult =
                independentFastICA.independentFit(data);

        System.out.println("FastICA 결과 : 모든 성분은 독립적이고 성분은 다른 성분의 변화, 데이터, 분포 등에 영향을 전혀 받지 않으며 다른 성분과 완전히 무관한 독립적인 성분입니다 "+ independentResult);
    }


}