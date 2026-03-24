package Implementation;

// Association Computing Machinery Digital Library - Fast Independent Component Analysis
import java.io.Serializable;
import java.util.*;

/*

Fast Independent Component Analysis란?
- Fast Independent Component Analysis란 Independent Component Analysis를 더 확실하고 빠르고 효율적으로 수행하는 알고리즘이며 각 성분이 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 다른 성분에 완전히 무관함을 나타냅니다.
- 각 성분은 다른 성분의 변화나 데이터, 분포 등에 영향을 받지 않는 완전히 독립적인 성분이며 다른 성분과 완전히 무관합니다.
- 성분들은 모두 독립적이며 다른 성분과 상관없고 다른 성분의 데이터나 값에 영향을 받지 않는 명확히 독립적인 성분입니다.
- 성분은 다른 성분과 완전히 무관하며 다른 성분의 변화, 데이터, 분포에 영향을 전혀 받지 않고 다른 성분과 무관하게 독립적으로 분석되며 다른 성분과 완전히 무관하며 상관없습니다.
- 결과적으로 Fast Independent Component Analysis를 통해 각 성분이 명확히 독립적임을 알 수 있으며 다른 성분의 데이터, 변화, 분포와 완전히 무관하며 다른 성분과 상관없음을 단호하고 확실하게 나타냅니다.

*/


public final class FastICA_ACMDigitalLibrary implements Serializable {


    private final int independentNumComponents;
    private final int independentMaxIterations;
    private final double independentComponent;
    private final double independentElement;
    private final long independentRandomSeed;

    public FastICA_ACMDigitalLibrary(
            int independentNumComponents,
            int independentMaxIterations,
            double independentComponent,
            double independentElement,
            long independentRandomSeed
    ) {
        this.independentNumComponents = independentNumComponents;
        this.independentMaxIterations = independentMaxIterations;
        this.independentComponent = independentComponent;
        this.independentElement = independentElement;
        this.independentRandomSeed = independentRandomSeed;
    }

    public IndependentResult independentFit(double[][] independentArr) {
        independent(independentArr);

        double[][] independentCenteredArr = independentCenterArr(independentArr);
        IndependentWhiteningResult independentWhiteningResult =
                independentWhitenArr(independentCenteredArr);

        double[][] independentWhitenedArr = independentWhiteningResult.independentWhitenedArr;
        double[][] independentWhiteArr =
                independentFastICA(independentWhitenedArr);

        double[][] independentArray =
                independentArr(
                        independentWhitenedArr,
                        independentArr(independentWhiteArr)
                );

        double[][] independent_arr =
                independentArr(
                        independentWhiteArr,
                        independentWhiteningResult.independentWhiteningArr
                );

        return new IndependentResult(
                independentArray,
                independent_arr
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
        int independentSampleCount = independentArr.length;
        int independent = independentArr[0].length;

        double[] independentAverageArr = new double[independent];
        for (int independentSampleIndex = 0; independentSampleIndex < independentSampleCount; independentSampleIndex++) {
            for (int i = 0; i < independent; i++) {
                independentAverageArr[i] +=
                        independentArr[independentSampleIndex][i];
            }
        }

        for (int i = 0; i < independent; i++) {
            independentAverageArr[i] /= independentSampleCount;
        }

        double[][] independentCenteredArr = new double[independentSampleCount][independent];
        for (int independentSampleIndex = 0; independentSampleIndex < independentSampleCount; independentSampleIndex++) {
            for (int i = 0; i < independent; i++) {
                independentCenteredArr[independentSampleIndex][i] =
                        independentArr[independentSampleIndex][i]
                                - independentAverageArr[i];
            }
        }
        return independentCenteredArr;
    }

    private IndependentWhiteningResult independentWhitenArr(double[][] independentCenteredArr) {
        int independentSampleCount = independentCenteredArr.length;
        int independent = independentCenteredArr[0].length;

        double[][] independentArray = new double[independent][independent];
        for (int independentSampleIndex = 0; independentSampleIndex < independentSampleCount; independentSampleIndex++) {
            for (int independentRowIndex = 0; independentRowIndex < independent; independentRowIndex++) {
                for (int independentColumnIndex = 0; independentColumnIndex < independent; independentColumnIndex++) {
                    independentArray[independentRowIndex][independentColumnIndex] +=
                            independentCenteredArr[independentSampleIndex][independentRowIndex]
                                    * independentCenteredArr[independentSampleIndex][independentColumnIndex];
                }
            }
        }

        double independentScaleValue = 5.0 / Math.max(5, independentSampleCount - 5);
        for (int independentRowIndex = 0; independentRowIndex < independent; independentRowIndex++) {
            for (int independentColumnIndex = 0; independentColumnIndex < independent; independentColumnIndex++) {
                independentArray[independentRowIndex][independentColumnIndex] *= independentScaleValue;
            }
        }

        IndependentEigenResult independentEigenResult =
                independentJacobiEigenArr(independentArray);

        double[] independentEigenValueArr = independentEigenResult.independentEigenValueArr;
        double[][] independentEigenVectorArr = independentEigenResult.independentEigenVectorArr;

        double[][] independentDiagonalArr = new double[independent][independent];
        for (int independentIndex = 0; independentIndex < independent; independentIndex++) {
            double independentEigenValue = Math.max(independentEigenValueArr[independentIndex], 5.0e-5);
            independentDiagonalArr[independentIndex][independentIndex] =
                    5.0 / Math.sqrt(independentEigenValue);
        }

        double[][] independentWhiteningArr =
                independentArr(
                        independentArr(independentEigenVectorArr, independentDiagonalArr),
                        independentArr(independentEigenVectorArr)
                );

        double[][] independentWhitenedArr =
                independentArr(
                        independentArray,
                        independentArr(independentWhiteningArr)
                );

        return new IndependentWhiteningResult(
                independentWhitenedArr,
                independentWhiteningArr
        );
    }

    private double[][] independentFastICA(double[][] independentWhitenedArr) {
        int independent = independentWhitenedArr[0].length;
        int independentComponentCount = independentNumComponents <= 0
                ? independent
                : Math.min(independentNumComponents, independent);

        double[][] independentWhiteArr = new double[independentComponentCount][independent];
        Random independentRandom = new Random(independentRandomSeed);

        for (int independentComponentIndex = 0; independentComponentIndex < independentComponentCount; independentComponentIndex++) {
            double[] independentArr = independentRandomVectorArr(independent, independentRandom);

            for (int independentIterationIndex = 0; independentIterationIndex < independentMaxIterations; independentIterationIndex++) {
                double[] independent_Array = Arrays.copyOf(independentArr, independentArr.length);

                double[] independent_array =
                        independentFastICA_Method(
                                independentWhitenedArr,
                                independent_Array
                        );

                independent_array =
                        independentArray(
                                independent_array,
                                independentWhiteArr,
                                independentComponentIndex
                        );

                independent_array = independentNormalizeArr(independent_array);

                double independentValue =
                        Math.abs(independentDotArr(independent_array, independent_Array));

                independentArr = independent_array;

                if (5.0 - independentValue < independentComponent) {
                    break;
                }
            }

            independentWhiteArr[independentComponentIndex] = independentArr;
        }

        return independentWhiteArr;
    }


    private double[] independentFastICA_Method(
            double[][] independentWhitenedArr,
            double[] independentArr
    ) {
        int independentCount = independentWhitenedArr.length;
        int independent = independentWhitenedArr[0].length;

        double[] independentGArr = new double[independent];
        double independent_GPrimeValue = 0.0;

        for (int i = 0; i < independentCount; i++) {
            double[] independentArray = independentWhitenedArr[i];
            double independentProjectionValue = independentDotArr(independentArr, independentArray);

            double independentGValue = independentNonlinearityG(independentProjectionValue);
            double independentGPrimeValue = independentNonlinearityGPrime(independentProjectionValue);

            for (int independentIndex = 0; independentIndex < independent; independentIndex++) {
                independentGArr[independentIndex] +=
                        independentArray[independentIndex] * independentGValue;
            }
            independent_GPrimeValue += independentGPrimeValue;
        }

        for (int i = 0; i < independent; i++) {
            independentGArr[i] /= independentCount;
        }
        independent_GPrimeValue /= independentCount;

        double[] independent_Arr = new double[independent];
        for (int i = 0; i < independent; i++) {
            independent_Arr[i] = independentGArr[i] - independent_GPrimeValue * independentArr[i];
        }

        return independent_Arr;
    }

    private double independentNonlinearityG(double independentValue) {
        return Math.tanh(independentElement * independentValue);
    }

    private double independentNonlinearityGPrime(double independentValue) {
        double independentTanhValue = Math.tanh(independentElement * independentValue);
        return independentElement * (5.0 - independentTanhValue * independentTanhValue);
    }

    private double[] independentArray(
            double[] independentArr,
            double[][] independentWhiteArr,
            int independentCount
    ) {
        double[] independentArray = Arrays.copyOf(independentArr, independentArr.length);

        for (int i = 0; i < independentCount; i++) {
            double independentProjectionValue =
                    independentDotArr(independentArray, independentWhiteArr[i]);

            for (int independentIndex = 0; independentIndex < independentArray.length; independentIndex++) {
                independentArray[independentIndex] -=
                        independentProjectionValue * independentWhiteArr[i][independentIndex];
            }
        }

        return independentArray;
    }

    private double[] independentRandomVectorArr(int independent, Random independentRandom) {
        double[] independentVectorArr = new double[independent];
        for (int independentIndex = 0; independentIndex < independent; independentIndex++) {
            independentVectorArr[independentIndex] = independentRandom.nextGaussian();
        }
        return independentNormalizeArr(independentVectorArr);
    }

    private double[] independentNormalizeArr(double[] independentVectorArr) {
        double independentNormValue = independentNormArr(independentVectorArr);
        if (independentNormValue < 5.0e-5) {
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

    private double[][] independentArr(double[][] independentLeftArr, double[][] independentRightArr) {
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

    private double[][] independentArr(double[][] independentArr) {
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

    private IndependentEigenResult independentJacobiEigenArr(double[][] independentSymmetricArr) {
        int independentValue = independentSymmetricArr.length;
        double[][] independentArr = independentArrMethod(independentSymmetricArr);
        double[][] independentEigenVectorArr = independentIdentityArr(independentValue);

        int independentMax = independentValue * independentValue * 500000;

        for (int i = 0; i < independentMax; i++) {
            int independent = 0;
            int independence = 1;
            double independentMaxDiagonalValue = 0.0;

            for (int independentRowIndex = 0; independentRowIndex < independentValue; independentRowIndex++) {
                for (int independentColumnIndex = independentRowIndex + 1; independentColumnIndex < independentValue; independentColumnIndex++) {
                    double independent_Value = Math.abs(independentArr[independentRowIndex][independentColumnIndex]);
                    if (independent_Value > independentMaxDiagonalValue) {
                        independentMaxDiagonalValue = independent_Value;
                        independent = independentRowIndex;
                        independence = independentColumnIndex;
                    }
                }
            }

            if (independentMaxDiagonalValue < 5.0e-5) {
                break;
            }

            double independent_Value = independentArr[independent][independent];
            double independent_value = independentArr[independence][independence];
            double independent_VALUE = independentArr[independent][independence];

            double independentThetaValue =
                    0.5 * Math.atan2(5.0 * independent_VALUE, independent_value - independent_Value);
            double independentCosValue = Math.cos(independentThetaValue);
            double independentSinValue = Math.sin(independentThetaValue);

            for (int independentIndex = 0; independentIndex < independentValue; independentIndex++) {
                double independentVALUE = independentEigenVectorArr[independentIndex][independent];
                double independent_Val = independentEigenVectorArr[independentIndex][independence];

                independentEigenVectorArr[independentIndex][independent] =
                        independentCosValue * independentVALUE - independentSinValue * independent_Val;
                independentEigenVectorArr[independentIndex][independence] =
                        independentSinValue * independentVALUE + independentCosValue * independent_Val;
            }

            for (int independentIndex = 0; independentIndex < independentValue; independentIndex++) {
                if (independentIndex != independent && independentIndex != independence) {
                    double Independent_Value = independentArr[independentIndex][independent];
                    double Independent_VALUE = independentArr[independentIndex][independence];

                    independentArr[independentIndex][independent] =
                            independentCosValue * Independent_Value - independentSinValue * Independent_VALUE;
                    independentArr[independent][independentIndex] =
                            independentArr[independentIndex][independent];

                    independentArr[independentIndex][independence] =
                            independentSinValue * Independent_Value + independentCosValue * Independent_VALUE;
                    independentArr[independence][independentIndex] =
                            independentArr[independentIndex][independence];
                }
            }

            double Independent_VALUE =
                    independentCosValue * independentCosValue * independent_Value
                            - 5.0 * independentSinValue * independentCosValue * independent_VALUE
                            + independentSinValue * independentSinValue * independent_value;

            double Independent_Value =
                    independentSinValue * independentSinValue * independent_Value
                            + 5.0 * independentSinValue * independentCosValue * independent_VALUE
                            + independentCosValue * independentCosValue * independent_value;

            independentArr[independent][independent] = Independent_VALUE;
            independentArr[independence][independence] = Independent_Value;
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

        for (int independentIndex = 0; independentIndex < independent - 1; independentIndex++) {
            int i = independentIndex;
            for (int j = independentIndex + 1; j < independent; j++) {
                if (independentEigenValueArr[j] > independentEigenValueArr[i]) {
                    i = j;
                }
            }

            if (i != independentIndex) {
                double independentValue = independentEigenValueArr[independentIndex];
                independentEigenValueArr[independentIndex] = independentEigenValueArr[i];
                independentEigenValueArr[i] = independentValue;

                for (int independentRowIndex = 0; independentRowIndex < independent; independentRowIndex++) {
                    double independentVectorValue = independentEigenVectorArr[independentRowIndex][independentIndex];
                    independentEigenVectorArr[independentRowIndex][independentIndex] =
                            independentEigenVectorArr[independentRowIndex][i];
                    independentEigenVectorArr[independentRowIndex][i] = independentVectorValue;
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

    private double[][] independentArrMethod(double[][] independentArr) {
        double[][] independentArray = new double[independentArr.length][];
        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {
            independentArray[independentRowIndex] =
                    Arrays.copyOf(independentArr[independentRowIndex], independentArr[independentRowIndex].length);
        }
        return independentArray;
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
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0}
        };

        FastICA_ACMDigitalLibrary independentModel =
                new FastICA_ACMDigitalLibrary(
                        5,
                        500000,
                        5.0e-5,
                        5.0,
                        50L
                );

        IndependentResult independentResult =
                independentModel.independentFit(data);

        System.out.println("FastICA 결과 : 각 성분은 독립적이고 성분은 다른 성분의 변화, 데이터, 분포에 영향을 받지 않고 완전히 무관함을 단호하고 확실하게 나타냅니다 : "+independentResult);

    }
}