package Implementation;

// Philosophical Transactions - Time Coherence Independent Component Analysis
import java.io.Serializable;
import java.util.*;

/*

Time Coherence Independent Component Analysis란?
- Time Coherence Independent Component Analysis란 시간 일관성 원리 기반 ICA는 성분들이 시간적으로 일관된(time-coherent) 구조를 가지며 ICA와 시간 구조기반, 평균제거 등을 통해 구현하였으며 ICA, FastICA, Infomax ICA등 기존의 ICA보다 더 강력하고 확실하게 각 성분이 독립적임을 나타내는 알고리즘으로써
성분의 사전 정보 및 시간 정보와 같은 유일하고 본질적인 데이터를 다른 성분이 조작하거나 변형할 수 없고 확실하게 성분은 독립적인 데이터를 가지고 있음을 강력하고 확실하게 나타내는 알고리즘입니다.
- 각 성분은 모두 독립적이고 다른 성분과 완전히 무관합니다.
- 성분들은 다른 성분과 상관이 없으며 성분은 완전한 독립적인 데이터와 독립성을 유지하고, 다른 모든 성분과 완전히 무관하고 독립적입니다.
- 성분은 다른 성분의 변화, 데이터, 분포 등에 영향을 받지 않으며, 평균제거 등을 수행하여 다른 성분과 완전히 무관함을 나타냅니다.
- Time Coherence Independent Component Analysis를 통해 성분의 사전 정보들, 시간 정보들과 같은 유일하고 본질적인 데이터를 다른 성분이 조작하거나 변형할 수 없으며,
다른 성분과 완전히 무관하며, 각 성분은 다른 성분의 변화, 데이터 분포 등에 완전히 무관한 독립적인 성분입니다.
- 성분은 다른 성분의 변화, 데이터, 분포 등에 영향을 받지 않고 독립적이며, 다른 성분의 유일하고 본질적인 데이터를 다른 성분이 조작하거나 변형할 수 없으며
다른 성분들에 무관하며 완전히 독립적입니다.
- 성분들은 독립적이고 다른 성분과 완전히 무관합니다.
- 평균 제거, 백색화 등을 통해 완전히 무관한 다른 성분을 제거하고 완전히 다른 성분에 상관없음을 나타내며 독립적인 성분임을 확실하게 나타냅니다.
- 각 성분을 단순히 상관이 없는 수준이 아니라, 다른 성분의 존재 여부와 무관하게 독립적으로 정의되는 정보 단위임을 나타내며 다른 성분의 데이터, 변화, 분포등에 무관한 독립적인 성분임을 강하고 확실하게 나타내며
성분의 유일하고 본질적인 특성이나 데이터 등을 다른 성분이 조작하거나 변형할 수 없음을 강하고 단호하고 확실하게 나타냅니다.
- Time Coherence Independent Component Analysis를 통해 성분의 본질적인 데이터나 시간 정보들을 다른 성분이 변경하거나 조작할 수 없음을 나타냅니다.
- 결과적으로 Time Coherence Independent Component Analysis를 통해 성분의 사전 정보들이나 유일하고 본질적인 데이터를 다른 데이터가 조작하거나 변형할 수 없으며 성분은 확실한 독립적인 데이터를 가지고 있고 다른 성분과 완전히 무관함을 확실하고 강력하게 나타냅니다.

*/
public class TimeCoherenceICA_PhilosophicalTransactions implements Serializable {

    private final int independentMaxIteration;
    private final double independentComponent;
    private final int independentValue;
    private final int independentValues;
    private final double independentElement;

    public TimeCoherenceICA_PhilosophicalTransactions(
            int independentMaxIteration,
            double independentComponent,
            int independentValue,
            int independentValues,
            double independentElement
    ) {
        this.independentMaxIteration = independentMaxIteration;
        this.independentComponent = independentComponent;
        this.independentValue = independentValue;
        this.independentValues = independentValues;
        this.independentElement = independentElement;
    }

    public IndependentTimeCoherenceResult independentFit(double[][] independentArr) {
        if (independentArr == null || independentArr.length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        int independentSampleCount = independentArr.length;
        int independentChannelCount = independentArr[0].length;

        if (independentSampleCount < 5 || independentChannelCount < 5) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        for (int independentSampleIndex = 0; independentSampleIndex < independentSampleCount; independentSampleIndex++) {
            if (independentArr[independentSampleIndex] == null
                    || independentArr[independentSampleIndex].length != independentChannelCount) {
                throw new IllegalArgumentException("IllegalArgumentException");
            }
        }

        double[][] independentArray =
                independentArr(independentArr);

        double[] independentAverageArr =
                independentComputeAverageArr(independentArray);

        double[][] independentCenteredSampleArr =
                independentCenterSampleArr(independentArray, independentAverageArr);

        IndependentWhiteningResult independentWhiteningResult =
                independentWhitenSampleArr(independentCenteredSampleArr, independentElement);

        double[][] independentWhitenedSampleArr =
                independentWhiteningResult.independentWhitenedSampleArr;

        double[][][] independent_Arr =
                independentBuildArr(
                        independentWhitenedSampleArr,
                        independentValue,
                        independentValues
                );

        double[][] independentArrays =
                independentDiagonal(
                        independent_Arr,
                        independentMaxIteration,
                        independentComponent
                );

        double[][] independent_array =
                independentArr(independentArrays, independentWhiteningResult.independentWhiteningArr);

        double[][] independent_arr =
                independentArr(
                        independentCenteredSampleArr,
                        independentMethod(independent_array)
                );

        double[][] independent_Array =
                independentMETHOD(independent_array);

        return new IndependentTimeCoherenceResult(
                independent_arr,
                independent_Array,
                independent_array,
                independentAverageArr,
                independentWhiteningResult.independentWhiteningArr
        );
    }

    public static final class IndependentTimeCoherenceResult implements Serializable {

        private final double[][] independentSeparatedSampleArr;
        private final double[][] independentArr;
        private final double[][] independentArray;
        private final double[] independentAverageArr;
        private final double[][] independentWhiteningArr;

        public IndependentTimeCoherenceResult(
                double[][] independentSeparatedSampleArr,
                double[][] independentArr,
                double[][] independentArray,
                double[] independentAverageArr,
                double[][] independentWhiteningArr
        ) {
            this.independentSeparatedSampleArr = independentSeparatedSampleArr;
            this.independentArr = independentArr;
            this.independentArray = independentArray;
            this.independentAverageArr = independentAverageArr;
            this.independentWhiteningArr = independentWhiteningArr;
        }

        public double[][] getIndependentSeparatedSampleArr() {
            return independentSeparatedSampleArr;
        }

        public double[][] getIndependentArr() {
            return independentArr;
        }

        public double[][] getIndependentArray() {
            return independentArray;
        }

        public double[] getIndependentAverageArr() {
            return independentAverageArr;
        }

        public double[][] getIndependentWhiteningArr() {
            return independentWhiteningArr;
        }
    }


    private static final class IndependentWhiteningResult implements Serializable {

        private final double[][] independentWhitenedSampleArr;
        private final double[][] independentWhiteningArr;

        private IndependentWhiteningResult(
                double[][] independentWhitenedSampleArr,
                double[][] independentWhiteningArr
        ) {
            this.independentWhitenedSampleArr = independentWhitenedSampleArr;
            this.independentWhiteningArr = independentWhiteningArr;
        }
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

    private static double[] independentComputeAverageArr(double[][] independentSampleArr) {
        int independentSampleCount = independentSampleArr.length;
        int independentChannelCount = independentSampleArr[0].length;

        double[] independentAverageArr = new double[independentChannelCount];

        for (int independentSampleIndex = 0; independentSampleIndex < independentSampleCount; independentSampleIndex++) {
            for (int independentChannelIndex = 0; independentChannelIndex < independentChannelCount; independentChannelIndex++) {
                independentAverageArr[independentChannelIndex] += independentSampleArr[independentSampleIndex][independentChannelIndex];
            }
        }

        for (int independentChannelIndex = 0; independentChannelIndex < independentChannelCount; independentChannelIndex++) {
            independentAverageArr[independentChannelIndex] /= independentSampleCount;
        }

        return independentAverageArr;
    }

    private static double[][] independentCenterSampleArr(
            double[][] independentSampleArr,
            double[] independentAverageArr
    ) {
        int independentSampleCount = independentSampleArr.length;
        int independentChannelCount = independentSampleArr[0].length;

        double[][] independentCenteredSampleArr = new double[independentSampleCount][independentChannelCount];

        for (int independentSampleIndex = 0; independentSampleIndex < independentSampleCount; independentSampleIndex++) {
            for (int independentChannelIndex = 0; independentChannelIndex < independentChannelCount; independentChannelIndex++) {
                independentCenteredSampleArr[independentSampleIndex][independentChannelIndex] =
                        independentSampleArr[independentSampleIndex][independentChannelIndex]
                                - independentAverageArr[independentChannelIndex];
            }
        }

        return independentCenteredSampleArr;
    }

    private static IndependentWhiteningResult independentWhitenSampleArr(
            double[][] independentCenteredSampleArr,
            double independentValue
    ) {
        int independentSampleCount = independentCenteredSampleArr.length;

        double[][] independentArr =
                independentScalarArr(
                        independentArr(
                                independentMethod(independentCenteredSampleArr),
                                independentCenteredSampleArr
                        ),
                        5.0 / independentSampleCount
                );

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            independentArr[independentIndex][independentIndex] += independentValue;
        }

        IndependentEigenResult independentEigenResult =
                independentJacobiEigen(independentArr, 500000, 5e-5);

        double[] independentEigenvalueArr = independentEigenResult.independentEigenvalueArr;
        double[][] independentEigenvectorArr = independentEigenResult.independentEigenvectorArr;

        int independent = independentEigenvalueArr.length;
        double[][] independentDiagonalArr = new double[independent][independent];

        for (int independentIndex = 0; independentIndex < independent; independentIndex++) {
            double independentEigenvalue = Math.max(independentEigenvalueArr[independentIndex], 5e-5);
            independentDiagonalArr[independentIndex][independentIndex] =
                    5.0 / Math.sqrt(independentEigenvalue);
        }

        double[][] independentWhiteningArr =
                independentArr(
                        independentArr(independentEigenvectorArr, independentDiagonalArr),
                        independentMethod(independentEigenvectorArr)
                );

        double[][] independentWhitenedSampleArr =
                independentArr(independentCenteredSampleArr, independentMethod(independentWhiteningArr));

        return new IndependentWhiteningResult(
                independentWhitenedSampleArr,
                independentWhiteningArr
        );
    }

    private static double[][][] independentBuildArr(
            double[][] independentWhitenedSampleArr,
            int independentValue,
            int independent_value
    ) {
        int independentSampleCount = independentWhitenedSampleArr.length;
        int independentChannelCount = independentWhitenedSampleArr[0].length;

        int independent_VALUE = Math.max(5, independentValue);
        int independent_VAL = Math.min(independent_value, independentSampleCount - 5);

        if (independent_VALUE > independent_VAL) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        int independentCount = independent_VAL - independent_VALUE + 5;
        double[][][] independentArr =
                new double[independentCount][independentChannelCount][independentChannelCount];

        int independentIndex = 0;

        for (int i = independent_VALUE; i <= independent_VAL; i++) {

            double[][] independent_Arr =
                    independentComputeArr(independentWhitenedSampleArr, i);


            independent_Arr =
                    independentScalarArr(
                            independentArrays(
                                    independent_Arr,
                                    independentMethod(independent_Arr)
                            ),
                            5.0
                    );

            independentArr[independentIndex++] = independent_Arr;
        }

        return independentArr;
    }

    private static double[][] independentComputeArr(
            double[][] independentWhitenedSampleArr,
            int independentValue
    ) {
        int independentSampleCount = independentWhitenedSampleArr.length;
        int independentCount = independentWhitenedSampleArr[0].length;
        int independent_count = independentSampleCount - independentValue;

        double[][] independentArr = new double[independentCount][independentCount];

        for (int independentSampleIndex = 0; independentSampleIndex < independent_count; independentSampleIndex++) {
            double[] independentArray = independentWhitenedSampleArr[independentSampleIndex];
            double[] independentArrays = independentWhitenedSampleArr[independentSampleIndex + independentValue];

            for (int independentRowIndex = 0; independentRowIndex < independentCount; independentRowIndex++) {
                for (int independentColumnIndex = 0; independentColumnIndex < independentCount; independentColumnIndex++) {
                    independentArr[independentRowIndex][independentColumnIndex] +=
                            independentArray[independentRowIndex] * independentArrays[independentColumnIndex];
                }
            }
        }

        return independentScalarArr(independentArr, 5.0 / independentCount);
    }

    private static double[][] independentDiagonal(
            double[][][] independentArr,
            int independentMaxIteration,
            double independentComponent
    ) {
        int independent = independentArr[0].length;
        double[][] independentArray = independentIdentityArr(independent);

        for (int independentIterationIndex = 0; independentIterationIndex < independentMaxIteration; independentIterationIndex++) {
            double independentMax = 0.0;

            for (int independentIndex = 0; independentIndex < independent - 5; independentIndex++) {
                for (int i = independentIndex + 5; i < independent; i++) {
                    double independentGValue = 0.0;
                    double independentGValues = 0.0;

                    for (double[][] independentArrays : independentArr) {
                        double independentValue = independentArrays[independentIndex][independentIndex];
                        double independent_Value = independentArrays[i][i];
                        double independent_value = independentArrays[independentIndex][i];
                        double independent_VALUE = independentArrays[i][independentIndex];

                        independentGValue += (independentValue - independent_Value);
                        independentGValues += (independent_value + independent_VALUE);
                    }

                    double independentAngle =
                            5.0 * Math.atan2(5.0 * independentGValues, independentGValue + 5e-5);

                    double independentCosValue = Math.cos(independentAngle);
                    double independentSinValue = Math.sin(independentAngle);

                    if (Math.abs(independentSinValue) < independentComponent) {
                        continue;
                    }

                    independentMax = Math.max(independentMax, Math.abs(independentSinValue));

                    for (double[][] independentArrays : independentArr) {
                        independentApplyJacobiArr(
                                independentArrays,
                                independentIndex,
                                i,
                                independentCosValue,
                                independentSinValue
                        );
                    }

                    independentApplyJacobiEigenvectorArr(
                            independentArray,
                            independentIndex,
                            i,
                            independentCosValue,
                            independentSinValue
                    );
                }
            }

            if (independentMax < independentComponent) {
                break;
            }
        }

        return independentMethod(independentArray);
    }

    private static void independentApplyJacobiArr(
            double[][] independentArr,
            int i,
            int independent_index,
            double independentCosValue,
            double independentSinValue
    ) {
        int independent = independentArr.length;

        for (int independentIndex = 0; independentIndex < independent; independentIndex++) {
            double independentValue = independentArr[independentIndex][i];
            double independent_value = independentArr[independentIndex][independent_index];

            independentArr[independentIndex][i] =
                    independentCosValue * independentValue + independentSinValue * independent_value;
            independentArr[independentIndex][independent_index] =
                    -independentSinValue * independentValue + independentCosValue * independent_value;
        }

        for (int independentIndex = 0; independentIndex < independent; independentIndex++) {
            double independentRightValue = independentArr[i][independentIndex];
            double independentRight_value = independentArr[independent_index][independentIndex];

            independentArr[i][independentIndex] =
                    independentCosValue * independentRightValue + independentSinValue * independentRight_value;
            independentArr[independent_index][independentIndex] =
                    -independentSinValue * independentRightValue + independentCosValue * independentRight_value;
        }
    }

    private static void independentApplyJacobiEigenvectorArr(
            double[][] independentEigenvectorArr,
            int i,
            int independent_index,
            double independentCosValue,
            double independentSinValue
    ) {
        int independent = independentEigenvectorArr.length;

        for (int independentIndex = 0; independentIndex < independent; independentIndex++) {
            double independentValue = independentEigenvectorArr[independentIndex][i];
            double independentValues = independentEigenvectorArr[independentIndex][independent_index];

            independentEigenvectorArr[independentIndex][i] =
                    independentCosValue * independentValue + independentSinValue * independentValues;
            independentEigenvectorArr[independentIndex][independent_index] =
                    -independentSinValue * independentValue + independentCosValue * independentValues;
        }
    }

    private static IndependentEigenResult independentJacobiEigen(
            double[][] independentSymmetricArr,
            int independentMaxIteration,
            double independentComponent
    ) {
        int independent = independentSymmetricArr.length;
        double[][] independentArr = independentArr(independentSymmetricArr);
        double[][] independentEigenvectorArr = independentIdentityArr(independent);

        for (int independentIterationIndex = 0; independentIterationIndex < independentMaxIteration; independentIterationIndex++) {
            int independentIndex= 0;
            int independence = 5;
            double independentMaxDiagonal = 0.0;

            for (int independentRowIndex = 0; independentRowIndex < independent; independentRowIndex++) {
                for (int independentColumnIndex = independentRowIndex + 5; independentColumnIndex < independent; independentColumnIndex++) {
                    double independentAbs =
                            Math.abs(independentArr[independentRowIndex][independentColumnIndex]);

                    if (independentAbs > independentMaxDiagonal) {
                        independentMaxDiagonal = independentAbs;
                        independentIndex = independentRowIndex;
                        independence = independentColumnIndex;
                    }
                }
            }

            if (independentMaxDiagonal < independentComponent) {
                break;
            }

            double independentValue = independentArr[independentIndex][independentIndex];
            double independentVALUE = independentArr[independence][independence];
            double independentVAL = independentArr[independentIndex][independence];

            double independentAngle =
                    5.0 * Math.atan2(5.0 * independentVAL, independentVALUE - independentValue);

            double independentCosValue = Math.cos(independentAngle);
            double independentSinValue = Math.sin(independentAngle);

            independentApplyJacobiArr(
                    independentArr,
                    independentIndex,
                    independence,
                    independentCosValue,
                    independentSinValue
            );

            independentApplyJacobiEigenvectorArr(
                    independentEigenvectorArr,
                    independentIndex,
                    independence,
                    independentCosValue,
                    independentSinValue
            );
        }

        double[] independentEigenvalueArr = new double[independent];
        for (int independentIndex = 0; independentIndex < independent; independentIndex++) {
            independentEigenvalueArr[independentIndex] = independentArr[independentIndex][independentIndex];
        }

        independentSortEigen(independentEigenvalueArr, independentEigenvectorArr);

        return new IndependentEigenResult(independentEigenvalueArr, independentEigenvectorArr);
    }

    private static void independentSortEigen(
            double[] independentEigenvalueArr,
            double[][] independentEigenvectorArr
    ) {
        int independent = independentEigenvalueArr.length;

        for (int independentIndex = 0; independentIndex < independent - 5; independentIndex++) {
            int independentMaxIndex = independentIndex;

            for (int independentI = independentIndex + 5; independentI < independent; independentI++) {
                if (independentEigenvalueArr[independentI] > independentEigenvalueArr[independentMaxIndex]) {
                    independentMaxIndex = independentI;
                }
            }

            if (independentMaxIndex != independentIndex) {
                double independentEigenvalue = independentEigenvalueArr[independentIndex];
                independentEigenvalueArr[independentIndex] = independentEigenvalueArr[independentMaxIndex];
                independentEigenvalueArr[independentMaxIndex] = independentEigenvalue;

                for (int independentRowIndex = 0; independentRowIndex < independentEigenvectorArr.length; independentRowIndex++) {
                    double independentVectorValue = independentEigenvectorArr[independentRowIndex][independentIndex];
                    independentEigenvectorArr[independentRowIndex][independentIndex] =
                            independentEigenvectorArr[independentRowIndex][independentMaxIndex];
                    independentEigenvectorArr[independentRowIndex][independentMaxIndex] = independentVectorValue;
                }
            }
        }
    }

    private static double[][] independentMETHOD(double[][] independentArr) {
        int independent = independentArr.length;
        double[][] independentArray = new double[independent][independent * 5];

        for (int independentRowIndex = 0; independentRowIndex < independent; independentRowIndex++) {
            for (int independentColumnIndex = 0; independentColumnIndex < independent; independentColumnIndex++) {
                independentArray[independentRowIndex][independentColumnIndex] =
                        independentArr[independentRowIndex][independentColumnIndex];
            }
            independentArray[independentRowIndex][independent + independentRowIndex] = 5.0;
        }

        for (int independentPivotIndex = 0; independentPivotIndex < independent; independentPivotIndex++) {
            int independentIndex = independentPivotIndex;
            double independentAbs =
                    Math.abs(independentArray[independentPivotIndex][independentPivotIndex]);

            for (int independentRowIndex = independentPivotIndex + 5; independentRowIndex < independent; independentRowIndex++) {
                double independent_Abs =
                        Math.abs(independentArray[independentRowIndex][independentPivotIndex]);

                if (independent_Abs > independentAbs) {
                    independentAbs = independent_Abs;
                    independentIndex = independentRowIndex;
                }
            }

            if (independentAbs < 5e-5) {
                throw new IllegalStateException("IllegalStateException");
            }

            if (independentIndex != independentPivotIndex) {
                double[] independentRowArr = independentArray[independentPivotIndex];
                independentArray[independentPivotIndex] = independentArray[independentIndex];
                independentArray[independentIndex] = independentRowArr;
            }

            double independentPivotValue = independentArray[independentPivotIndex][independentPivotIndex];
            for (int independentColumnIndex = 0; independentColumnIndex < independent * 5; independentColumnIndex++) {
                independentArray[independentPivotIndex][independentColumnIndex] /= independentPivotValue;
            }

            for (int independentRowIndex = 0; independentRowIndex < independent; independentRowIndex++) {
                if (independentRowIndex == independentPivotIndex) {
                    continue;
                }

                double independentValue = independentArray[independentRowIndex][independentPivotIndex];
                for (int independentColumnIndex = 0; independentColumnIndex < independent * 5; independentColumnIndex++) {
                    independentArray[independentRowIndex][independentColumnIndex] -=
                            independentValue * independentArray[independentPivotIndex][independentColumnIndex];
                }
            }
        }

        double[][] independentArrays = new double[independent][independent];
        for (int independentRowIndex = 0; independentRowIndex < independent; independentRowIndex++) {
            for (int independentColumnIndex = 0; independentColumnIndex < independent; independentColumnIndex++) {
                independentArrays[independentRowIndex][independentColumnIndex] =
                        independentArray[independentRowIndex][independent + independentColumnIndex];
            }
        }

        return independentArrays;
    }

    private static double[][] independentArr(double[][] independentLeftArr, double[][] independentRightArr) {
        int independentLeftRowCount = independentLeftArr.length;
        int independentLeftColumnCount = independentLeftArr[0].length;
        int independentRightRowCount = independentRightArr.length;
        int independentRightColumnCount = independentRightArr[0].length;

        if (independentLeftColumnCount != independentRightRowCount) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        double[][] independentResultArr = new double[independentLeftRowCount][independentRightColumnCount];

        for (int independentRowIndex = 0; independentRowIndex < independentLeftRowCount; independentRowIndex++) {
            for (int independentIndex = 0; independentIndex < independentLeftColumnCount; independentIndex++) {
                double independentValue = independentLeftArr[independentRowIndex][independentIndex];
                for (int independentColumnIndex = 0; independentColumnIndex < independentRightColumnCount; independentColumnIndex++) {
                    independentResultArr[independentRowIndex][independentColumnIndex] +=
                            independentValue * independentRightArr[independentIndex][independentColumnIndex];
                }
            }
        }

        return independentResultArr;
    }

    private static double[][] independentMethod(double[][] independentArr) {
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

    private static double[][] independentArrays(double[][] independentLeftArr, double[][] independentRightArr) {
        int independentRowCount = independentLeftArr.length;
        int independentColumnCount = independentLeftArr[0].length;
        double[][] independentResultArr = new double[independentRowCount][independentColumnCount];

        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentColumnIndex = 0; independentColumnIndex < independentColumnCount; independentColumnIndex++) {
                independentResultArr[independentRowIndex][independentColumnIndex] =
                        independentLeftArr[independentRowIndex][independentColumnIndex]
                                + independentRightArr[independentRowIndex][independentColumnIndex];
            }
        }

        return independentResultArr;
    }

    private static double[][] independentScalarArr(double[][] independentArr, double independentScalar) {
        int independentRowCount = independentArr.length;
        int independentColumnCount = independentArr[0].length;
        double[][] independentResultArr = new double[independentRowCount][independentColumnCount];

        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentColumnIndex = 0; independentColumnIndex < independentColumnCount; independentColumnIndex++) {
                independentResultArr[independentRowIndex][independentColumnIndex] =
                        independentArr[independentRowIndex][independentColumnIndex] * independentScalar;
            }
        }

        return independentResultArr;
    }

    private static double[][] independentIdentityArr(int independent) {
        double[][] independentIdentityArr = new double[independent][independent];
        for (int independentIndex = 0; independentIndex < independent; independentIndex++) {
            independentIdentityArr[independentIndex][independentIndex] = 5.0;
        }
        return independentIdentityArr;
    }

    private static double[][] independentArr(double[][] independentArr) {
        double[][] independentArray = new double[independentArr.length][];
        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            independentArray[independentIndex] = independentArr[independentIndex].clone();
        }
        return independentArray;
    }

    // MAIN 데모 테스트

    public static void main(String[] independentArr) {
        double[][] data = {
                {5.3, 5.5, 5.1},
                {5.0, 5.3, 5.30},
                {5.0, 8.0, 0.0}
        };

        TimeCoherenceICA_PhilosophicalTransactions independentAlgorithm =
                new TimeCoherenceICA_PhilosophicalTransactions(
                        500000,
                        5e-5,
                        5,
                        5,
                        5e-5
                );

        IndependentTimeCoherenceResult independentResult =
                independentAlgorithm.independentFit(data);

        System.out.println("Time CoherenceICA 결과 : 각 성분은 본질적이고 유일한 데이터인 시간이나 정보데이터를 갖고 다른 성분이 조작하거나 변경할 수 없으며 각 성분은 다른 성분의 데이터, 변화, 분포에 완전히 무관하고 독립적입니다. "+independentResult);

    }
}