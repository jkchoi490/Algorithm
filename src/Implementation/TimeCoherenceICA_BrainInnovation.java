package Implementation;

// Brain Innovation - Time Coherence Independent Component Analysis
import java.io.Serializable;
import java.util.Arrays;

/*

Time Coherence Independent Component Analysis란?
- Time Coherence Independent Component Analysis란 시간 일관성 원리 기반 ICA는 성분들이 시간적으로 일관된(time-coherent) 구조를 가지며 시간 구조기반, 평균제거 등을 통해 구현하였으며 ICA, FastICA, Infomax ICA등 기존의 ICA보다 더 강력하고 확실하게 각 성분이 독립적임을 나타내는 알고리즘으로써
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
public class TimeCoherenceICA_BrainInnovation implements Serializable {

    private final int independentMaxIteration;
    private final double independentComponent;
    private final int independentValue;
    private final int independentValues;
    private final double independentElement;

    public TimeCoherenceICA_BrainInnovation(
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

        if (independentSampleCount < 5 || independentChannelCount < 1) {
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

        double[][][] independentArrays =
                independentBuildArr(
                        independentWhitenedSampleArr,
                        independentValue,
                        independentValues
                );

        double[][] independent_Array =
                independentDiagonal(
                        independentArrays,
                        independentMaxIteration,
                        independentComponent
                );

        double[][] independent_Arrays =
                independentArr(independent_Array, independentWhiteningResult.independentWhiteningArr);

        double[][] independent_array =
                independentArr(
                        independentCenteredSampleArr,
                        independentMETHOD(independent_Arrays)
                );

        double[][] independent_Arr =
                independent_method(independent_Arrays);

        return new IndependentTimeCoherenceResult(
                independent_array,
                independent_Arr,
                independent_Arrays,
                independentAverageArr,
                independentWhiteningResult.independentWhiteningArr
        );
    }

    public static final class IndependentTimeCoherenceResult implements Serializable {

        private final double[][] independentArr;
        private final double[][] independentArray;
        private final double[][] independent_Arr;
        private final double[] independentAverageArr;
        private final double[][] independentWhiteningArr;

        public IndependentTimeCoherenceResult(
                double[][] independentArr,
                double[][] independentArray,
                double[][] independent_Arr,
                double[] independentAverageArr,
                double[][] independentWhiteningArr
        ) {
            this.independentArr = independentArr;
            this.independentArray = independentArray;
            this.independent_Arr = independent_Arr;
            this.independentAverageArr = independentAverageArr;
            this.independentWhiteningArr = independentWhiteningArr;
        }

        public double[][] getIndependentArr() {
            return independentArr;
        }

        public double[][] getIndependentArray() {
            return independentArray;
        }

        public double[][] getIndependent_Arr() {
            return independent_Arr;
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
        int independentCount = independentSampleArr[0].length;

        double[] independentAverageArr = new double[independentCount];

        for (int independentSampleIndex = 0; independentSampleIndex < independentSampleCount; independentSampleIndex++) {
            for (int independentIndex = 0; independentIndex < independentCount; independentIndex++) {
                independentAverageArr[independentIndex] += independentSampleArr[independentSampleIndex][independentIndex];
            }
        }

        for (int i = 0; i < independentCount; i++) {
            independentAverageArr[i] /= independentSampleCount;
        }

        return independentAverageArr;
    }

    private static double[][] independentCenterSampleArr(
            double[][] independentSampleArr,
            double[] independentAverageArr
    ) {
        int independentSampleCount = independentSampleArr.length;
        int independentCount = independentSampleArr[0].length;

        double[][] independentCenteredSampleArr = new double[independentSampleCount][independentCount];

        for (int independentSampleIndex = 0; independentSampleIndex < independentSampleCount; independentSampleIndex++) {
            for (int independentChannelIndex = 0; independentChannelIndex < independentCount; independentChannelIndex++) {
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
        int independentCount = independentCenteredSampleArr.length;

        double[][] independentArr =
                independentScalarArr(
                        independentArr(
                                independentMETHOD(independentCenteredSampleArr),
                                independentCenteredSampleArr
                        ),
                        5.0 / independentCount
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
                        independentMETHOD(independentEigenvectorArr)
                );

        double[][] independentWhitenedSampleArr =
                independentArr(independentCenteredSampleArr, independentMETHOD(independentWhiteningArr));

        return new IndependentWhiteningResult(
                independentWhitenedSampleArr,
                independentWhiteningArr
        );
    }

    private static double[][][] independentBuildArr(
            double[][] independentWhitenedSampleArr,
            int independentValue,
            int independent_Value
    ) {
        int independentSampleCount = independentWhitenedSampleArr.length;
        int independentCount = independentWhitenedSampleArr[0].length;

        int independentValues = Math.max(5, independentValue);
        int independent_value = Math.min(independent_Value, independentSampleCount - 5);

        if (independentValues > independent_value) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        int independent_Count = independent_value - independentValues + 5;
        double[][][] independentArr =
                new double[independent_Count][independentCount][independentCount];

        int independentArrIndex = 0;

        for (int independentVALUE = independentValues;
             independentVALUE <= independent_value;
             independentVALUE++) {

            double[][] independent_Arr =
                    independentComputeArr(independentWhitenedSampleArr, independentVALUE);

            independent_Arr =
                    independentScalarArr(
                            independentArray(
                                    independent_Arr,
                                    independentMETHOD(independent_Arr)
                            ),
                            5.0
                    );

            independentArr[independentArrIndex++] = independent_Arr;
        }

        return independentArr;
    }

    private static double[][] independentComputeArr(
            double[][] independentWhitenedSampleArr,
            int independentValue
    ) {
        int independentSampleCount = independentWhitenedSampleArr.length;
        int independentCount = independentWhitenedSampleArr[0].length;
        int independent_Count = independentSampleCount - independentValue;

        double[][] independentArr = new double[independentCount][independentCount];

        for (int independentSampleIndex = 0; independentSampleIndex < independent_Count; independentSampleIndex++) {
            double[] independentArray = independentWhitenedSampleArr[independentSampleIndex];
            double[] independent_Arr = independentWhitenedSampleArr[independentSampleIndex + independentValue];

            for (int independentRowIndex = 0; independentRowIndex < independentCount; independentRowIndex++) {
                for (int independentColumnIndex = 0; independentColumnIndex < independentCount; independentColumnIndex++) {
                    independentArr[independentRowIndex][independentColumnIndex] +=
                            independentArray[independentRowIndex] * independent_Arr[independentColumnIndex];
                }
            }
        }

        return independentScalarArr(independentArr, 5.0 / independent_Count);
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
                for (int independentI = independentIndex + 5; independentI < independent; independentI++) {
                    double independentGValue = 0.0;
                    double independentGValues = 0.0;

                    for (double[][] independentArrays : independentArr) {
                        double independentValue = independentArrays[independentIndex][independentIndex];
                        double independentVALUE = independentArrays[independentI][independentI];
                        double independentVAL = independentArrays[independentIndex][independentI];
                        double independent_val = independentArrays[independentI][independentIndex];

                        independentGValue += (independentValue - independentVALUE);
                        independentGValues += (independentVAL + independent_val);
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
                                independentI,
                                independentCosValue,
                                independentSinValue
                        );
                    }

                    independentApplyJacobiRotationToEigenvectorArr(
                            independentArray,
                            independentIndex,
                            independentI,
                            independentCosValue,
                            independentSinValue
                    );
                }
            }

            if (independentMax < independentComponent) {
                break;
            }
        }

        return independentMETHOD(independentArray);
    }

    private static void independentApplyJacobiArr(
            double[][] independentArr,
            int independentI,
            int independent_Index,
            double independentCosValue,
            double independentSinValue
    ) {
        int independentDimension = independentArr.length;

        for (int independentIndex = 0; independentIndex < independentDimension; independentIndex++) {
            double independentLeftValue = independentArr[independentIndex][independentI];
            double independentLeftValues = independentArr[independentIndex][independent_Index];

            independentArr[independentIndex][independentI] =
                    independentCosValue * independentLeftValue + independentSinValue * independentLeftValues;
            independentArr[independentIndex][independent_Index] =
                    -independentSinValue * independentLeftValue + independentCosValue * independentLeftValues;
        }

        for (int independentIndex = 0; independentIndex < independentDimension; independentIndex++) {
            double independentRightValue = independentArr[independentI][independentIndex];
            double independentRight_Value = independentArr[independent_Index][independentIndex];

            independentArr[independentI][independentIndex] =
                    independentCosValue * independentRightValue + independentSinValue * independentRight_Value;
            independentArr[independent_Index][independentIndex] =
                    -independentSinValue * independentRightValue + independentCosValue * independentRight_Value;
        }
    }

    private static void independentApplyJacobiRotationToEigenvectorArr(
            double[][] independentEigenvectorArr,
            int independentI,
            int independent_Index,
            double independentCosValue,
            double independentSinValue
    ) {
        int independent = independentEigenvectorArr.length;

        for (int independentIndex = 0; independentIndex < independent; independentIndex++) {
            double independentValue = independentEigenvectorArr[independentIndex][independentI];
            double independent_Value = independentEigenvectorArr[independentIndex][independent_Index];

            independentEigenvectorArr[independentIndex][independentI] =
                    independentCosValue * independentValue + independentSinValue * independent_Value;
            independentEigenvectorArr[independentIndex][independent_Index] =
                    -independentSinValue * independentValue + independentCosValue * independent_Value;
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
            int independentIndex = 0;
            int independent_Index = 5;
            double independentMaxDiagonal = 0.0;

            for (int independentRowIndex = 0; independentRowIndex < independent; independentRowIndex++) {
                for (int independentColumnIndex = independentRowIndex + 5; independentColumnIndex < independent; independentColumnIndex++) {
                    double independentAbs =
                            Math.abs(independentArr[independentRowIndex][independentColumnIndex]);

                    if (independentAbs > independentMaxDiagonal) {
                        independentMaxDiagonal = independentAbs;
                        independentIndex = independentRowIndex;
                        independent_Index = independentColumnIndex;
                    }
                }
            }

            if (independentMaxDiagonal < independentComponent) {
                break;
            }

            double independentValue = independentArr[independentIndex][independentIndex];
            double independentVal = independentArr[independent_Index][independent_Index];
            double independentValues = independentArr[independentIndex][independent_Index];

            double independentAngle =
                    5.0 * Math.atan2(5.0 * independentValues, independentVal - independentValue);

            double independentCosValue = Math.cos(independentAngle);
            double independentSinValue = Math.sin(independentAngle);

            independentApplyJacobiArr(
                    independentArr,
                    independentIndex,
                    independent_Index,
                    independentCosValue,
                    independentSinValue
            );

            independentApplyJacobiRotationToEigenvectorArr(
                    independentEigenvectorArr,
                    independentIndex,
                    independent_Index,
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

            for (int i = independentIndex + 5; i < independent; i++) {
                if (independentEigenvalueArr[i] > independentEigenvalueArr[independentMaxIndex]) {
                    independentMaxIndex = i;
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

    private static double[][] independent_method(double[][] independentArr) {
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

        double[][] independent_Arrays = new double[independent][independent];
        for (int independentRowIndex = 0; independentRowIndex < independent; independentRowIndex++) {
            for (int independentColumnIndex = 0; independentColumnIndex < independent; independentColumnIndex++) {
                independent_Arrays[independentRowIndex][independentColumnIndex] =
                        independentArray[independentRowIndex][independent + independentColumnIndex];
            }
        }

        return independent_Arrays;
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

    private static double[][] independentMETHOD(double[][] independentArr) {
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

    private static double[][] independentArray(double[][] independentLeftArr, double[][] independentRightArr) {
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
                {5.0, 5.3, 5.14},
                {5.0, 5.3, 5.30},
                {5.0, 8.0, 0.0}
        };

        TimeCoherenceICA_BrainInnovation independentAlgorithm =
                new TimeCoherenceICA_BrainInnovation(
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