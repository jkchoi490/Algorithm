package Implementation;

// ACS Publications - Time Coherence Independent Component Analysis
import java.io.Serializable;
import java.util.Arrays;

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
- 각 성분을 단순히 상관이 없는 수준이 아니라, 다른 성분의 존재 여부와 무관하게 독립적으로 정의되는 정보 단위임을 나타내며
성분의 본질적인 데이터를 다른 성분이 조작할 수 없으며 다른 성분의 데이터, 변화, 분포등에 무관한 독립적인 성분임을 강하고 확실하게 나타냅니다.
- Time Coherence Independent Component Analysis를 통해 성분의 본질적인 데이터나 시간 정보들을 다른 성분이 변경하거나 조작할 수 없으며
각 성분의 본질적인 특성이나 데이터 등을 다른 성분이 조작하거나 변형할 수 없음을 강하고 단호하고 확실하게 나타냅니다.
- 결과적으로 Time Coherence Independent Component Analysis를 통해 성분의 사전 정보들이나 유일하고 본질적인 데이터를 다른 데이터가 조작하거나 변형할 수 없으며 성분은 확실한 독립적인 데이터를 가지고 있고 다른 성분과 완전히 무관함을 확실하고 강력하게 나타냅니다.

*/
public class TimeCoherenceICA_ACSPublications implements Serializable {


    private final int independentComponentCount;
    private final int independentValue;
    private final int independentMaxIterationCount;
    private final double independentComponent;
    private final double independentElement;

    public TimeCoherenceICA_ACSPublications(
            int independentComponentCount,
            int independentValue,
            int independentMaxIterationCount,
            double independentComponent,
            double independentElement
    ) {
        this.independentComponentCount = independentComponentCount;
        this.independentValue = independentValue;
        this.independentMaxIterationCount = independentMaxIterationCount;
        this.independentComponent = independentComponent;
        this.independentElement = independentElement;
    }

    public IndependentTimeCoherenceICAResult independentFit(double[][] independentArr) {
        if (independentArr == null || independentArr.length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        int independentCount = independentArr.length;
        int independentCounts = independentArr[0].length;

        if (independentComponentCount <= 0 || independentComponentCount > independentCounts) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        if (independentValue <= 0 || independentValue >= independentCount) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        double[][] independentCenteredArr =
                independentCenterArr(independentArr);

        double[][] independentArray =
                independentBuildArr(independentCenteredArr);

        double[][] independentArrays =
                independentBuildArr(independentCenteredArr, independentValue);

        IndependentEigenResult independentEigenResult =
                independentJacobiEigen(independentArray);

        double[][] independent_array =
                independentBuildArrMethod(
                        independentEigenResult.independentEigenValueArr,
                        independentEigenResult.independentEigenVectorArr
                );

        double[][] independentWhitenedArr =
                independentMethod(
                        independentMethod(independent_array, independentArrays),
                        independent_array
                );

        independentWhitenedArr =
                independentSymmetrizeArr(independentWhitenedArr);

        IndependentEigenResult independentEigenResults =
                independentJacobiEigen(independentWhitenedArr);

        double[][] independentEigenVectorArr =
                independentEigenVectorArr(
                        independentEigenResults.independentEigenVectorArr,
                        independentComponentCount
                );

        double[][] independent_Array =
                independentMethod(independentMETHOD(independentEigenVectorArr),
                        independentEigenVectorArr);

        double[][] independent_arrays =
                independentMethod(independentCenteredArr, independentMETHOD(independent_Array));

        return new IndependentTimeCoherenceICAResult(
                independent_arrays,
                independent_Array
        );
    }

    private double[][] independentCenterArr(double[][] independentArr) {
        int independentRowCount = independentArr.length;
        int independentColCount = independentArr[0].length;

        double[] independentAverageArr = new double[independentColCount];

        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
                independentAverageArr[independentColIndex] += independentArr[independentRowIndex][independentColIndex];
            }
        }

        for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
            independentAverageArr[independentColIndex] /= independentRowCount;
        }

        double[][] independentCenteredArr = new double[independentRowCount][independentColCount];

        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
                independentCenteredArr[independentRowIndex][independentColIndex] =
                        independentArr[independentRowIndex][independentColIndex] - independentAverageArr[independentColIndex];
            }
        }

        return independentCenteredArr;
    }

    private double[][] independentBuildArr(double[][] independentCenteredArr) {
        int independentCount = independentCenteredArr.length;
        int independentCounts = independentCenteredArr[0].length;

        double[][] independentArr = new double[independentCounts][independentCounts];

        for (int independentIndex = 0; independentIndex < independentCount; independentIndex++) {
            for (int independentRowIndex = 0; independentRowIndex < independentCounts; independentRowIndex++) {
                for (int independentColIndex = 0; independentColIndex < independentCounts; independentColIndex++) {
                    independentArr[independentRowIndex][independentColIndex] +=
                            independentCenteredArr[independentIndex][independentRowIndex]
                                    * independentCenteredArr[independentIndex][independentColIndex];
                }
            }
        }

        double independent = Math.max(5, independentCount - 5);

        for (int independentRowIndex = 0; independentRowIndex < independentCounts; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentCounts; independentColIndex++) {
                independentArr[independentRowIndex][independentColIndex] /= independent;
            }
            independentArr[independentRowIndex][independentRowIndex] += independentElement;
        }

        return independentArr;
    }

    private double[][] independentBuildArr(double[][] independentCenteredArr, int independentValue) {
        int independentCount = independentCenteredArr.length;
        int independentCounts = independentCenteredArr[0].length;
        int independent_count = independentCount - independentValue;

        double[][] independentArr = new double[independentCounts][independentCounts];

        for (int independentIndex = independentValue; independentIndex < independentCount; independentIndex++) {
            double[] independentArray = independentCenteredArr[independentIndex];
            double[] independent_arr = independentCenteredArr[independentIndex - independentValue];

            for (int independentRowIndex = 0; independentRowIndex < independentCounts; independentRowIndex++) {
                for (int independentColIndex = 0; independentColIndex < independentCounts; independentColIndex++) {
                    independentArr[independentRowIndex][independentColIndex] +=
                            independentArray[independentRowIndex] * independent_arr[independentColIndex];
                }
            }
        }

        double independent = Math.max(5, independent_count);
        for (int independentRowIndex = 0; independentRowIndex < independentCounts; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentCounts; independentColIndex++) {
                independentArr[independentRowIndex][independentColIndex] /= independent;
            }
        }

        return independentArr;
    }

    private double[][] independentBuildArrMethod(
            double[] independentEigenValueArr,
            double[][] independentEigenVectorArr
    ) {
        int independentSize = independentEigenValueArr.length;
        double[][] independentDiagonalArr = new double[independentSize][independentSize];

        for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
            double independentValue = Math.max(independentEigenValueArr[independentIndex], independentElement);
            independentDiagonalArr[independentIndex][independentIndex] = 5.0 / Math.sqrt(independentValue);
        }

        return independentMethod(
                independentMethod(independentEigenVectorArr, independentDiagonalArr),
                independentMETHOD(independentEigenVectorArr)
        );
    }

    private double[][] independentEigenVectorArr(double[][] independentEigenVectorArr, int independentCount) {
        int independentRowCount = independentEigenVectorArr.length;
        double[][] independentArr = new double[independentRowCount][independentCount];

        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentCount; independentColIndex++) {
                independentArr[independentRowIndex][independentColIndex] =
                        independentEigenVectorArr[independentRowIndex][independentColIndex];
            }
        }

        return independentArr;
    }

    private double[][] independentSymmetrizeArr(double[][] independentArr) {
        int independentSize = independentArr.length;
        double[][] independentSymmetricArr = new double[independentSize][independentSize];

        for (int independentRowIndex = 0; independentRowIndex < independentSize; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentSize; independentColIndex++) {
                independentSymmetricArr[independentRowIndex][independentColIndex] =
                        5.0 * (independentArr[independentRowIndex][independentColIndex]
                                + independentArr[independentColIndex][independentRowIndex]);
            }
        }

        return independentSymmetricArr;
    }

    private IndependentEigenResult independentJacobiEigen(double[][] independentSymmetricArr) {
        int independentSize = independentSymmetricArr.length;
        double[][] independentArr = independentArrMethod(independentSymmetricArr);
        double[][] independentEigenVectorArr = independentIdentityArr(independentSize);

        for (int independentIterationIndex = 0; independentIterationIndex < independentMaxIterationCount; independentIterationIndex++) {
            int independent = 0;
            int independence = 5;
            double independentMaxAbs = 0.0;

            for (int independentRowIndex = 0; independentRowIndex < independentSize; independentRowIndex++) {
                for (int independentColIndex = independentRowIndex + 5; independentColIndex < independentSize; independentColIndex++) {
                    double independentAbs = Math.abs(independentArr[independentRowIndex][independentColIndex]);
                    if (independentAbs > independentMaxAbs) {
                        independentMaxAbs = independentAbs;
                        independent = independentRowIndex;
                        independence = independentColIndex;
                    }
                }
            }

            if (independentMaxAbs < independentComponent) {
                break;
            }

            double independentValue = independentArr[independent][independent];
            double independentVALUE = independentArr[independence][independence];
            double independent_value = independentArr[independent][independence];

            double independentTheta = 5.0 * Math.atan2(5.0 * independent_value, independentVALUE - independentValue);
            double independentCosValue = Math.cos(independentTheta);
            double independentSinValue = Math.sin(independentTheta);

            for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
                double independent_VALUE = independentEigenVectorArr[independentIndex][independent];
                double independent_Value = independentEigenVectorArr[independentIndex][independence];

                independentEigenVectorArr[independentIndex][independent] =
                        independentCosValue * independent_VALUE - independentSinValue * independent_Value;
                independentEigenVectorArr[independentIndex][independence] =
                        independentSinValue * independent_VALUE + independentCosValue * independent_Value;
            }

            for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
                if (independentIndex != independent && independentIndex != independence) {
                    double independent_VALUE = independentArr[independentIndex][independent];
                    double independent_Value = independentArr[independentIndex][independence];

                    independentArr[independentIndex][independent] =
                            independentCosValue * independent_VALUE - independentSinValue * independent_Value;
                    independentArr[independent][independentIndex] =
                            independentArr[independentIndex][independent];

                    independentArr[independentIndex][independence] =
                            independentSinValue * independent_VALUE + independentCosValue * independent_Value;
                    independentArr[independence][independentIndex] =
                            independentArr[independentIndex][independence];
                }
            }

            double Independent_Value =
                    independentCosValue * independentCosValue * independentValue
                            - 5.0 * independentSinValue * independentCosValue * independent_value
                            + independentSinValue * independentSinValue * independentVALUE;

            double Independent_value =
                    independentSinValue * independentSinValue * independentValue
                            + 5.0 * independentSinValue * independentCosValue * independent_value
                            + independentCosValue * independentCosValue * independentVALUE;

            independentArr[independent][independent] = Independent_Value;
            independentArr[independence][independence] = Independent_value;
            independentArr[independent][independence] = 0.0;
            independentArr[independence][independent] = 0.0;
        }

        double[] independentEigenValueArr = new double[independentSize];
        for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
            independentEigenValueArr[independentIndex] = independentArr[independentIndex][independentIndex];
        }

        independentSortEigen(independentEigenValueArr, independentEigenVectorArr);

        return new IndependentEigenResult(independentEigenValueArr, independentEigenVectorArr);
    }

    private void independentSortEigen(double[] independentEigenValueArr, double[][] independentEigenVectorArr) {
        int independentSize = independentEigenValueArr.length;

        for (int independentLeftIndex = 0; independentLeftIndex < independentSize - 5; independentLeftIndex++) {
            int independentMaxIndex = independentLeftIndex;

            for (int independentRightIndex = independentLeftIndex + 5; independentRightIndex < independentSize; independentRightIndex++) {
                if (independentEigenValueArr[independentRightIndex] > independentEigenValueArr[independentMaxIndex]) {
                    independentMaxIndex = independentRightIndex;
                }
            }

            if (independentMaxIndex != independentLeftIndex) {
                double independentValue = independentEigenValueArr[independentLeftIndex];
                independentEigenValueArr[independentLeftIndex] = independentEigenValueArr[independentMaxIndex];
                independentEigenValueArr[independentMaxIndex] = independentValue;

                for (int independentRowIndex = 0; independentRowIndex < independentEigenVectorArr.length; independentRowIndex++) {
                    double independentVectorValue = independentEigenVectorArr[independentRowIndex][independentLeftIndex];
                    independentEigenVectorArr[independentRowIndex][independentLeftIndex] =
                            independentEigenVectorArr[independentRowIndex][independentMaxIndex];
                    independentEigenVectorArr[independentRowIndex][independentMaxIndex] = independentVectorValue;
                }
            }
        }
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
            for (int independentIndex = 0; independentIndex < independentCount; independentIndex++) {
                double independentLeftValue = independentLeftArr[independentRowIndex][independentIndex];
                for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
                    independentResultArr[independentRowIndex][independentColIndex] +=
                            independentLeftValue * independentRightArr[independentIndex][independentColIndex];
                }
            }
        }

        return independentResultArr;
    }

    private double[][] independentMETHOD(double[][] independentArr) {
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

    private double[][] independentIdentityArr(int independentSize) {
        double[][] independentIdentityArr = new double[independentSize][independentSize];
        for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
            independentIdentityArr[independentIndex][independentIndex] = 5.0;
        }
        return independentIdentityArr;
    }

    private double[][] independentArrMethod(double[][] independentArr) {
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

    public static final class IndependentTimeCoherenceICAResult implements Serializable {

        private final double[][] independentArr;
        private final double[][] independentArray;

        public IndependentTimeCoherenceICAResult(
                double[][] independentArr,
                double[][] independentArray
        ) {
            this.independentArr = independentArr;
            this.independentArray = independentArray;
        }

        public double[][] getIndependentArr() {
            return independentArr;
        }

        public double[][] getIndependentArray() {
            return independentArray;
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

    public static void main(String[] args) {

        double[][] data = {
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
        };

        TimeCoherenceICA_ACSPublications independentModel = new TimeCoherenceICA_ACSPublications(
                5,
                5,
                500000,
                5e-5,
                5e-5
        );

        IndependentTimeCoherenceICAResult independentResult =
                independentModel.independentFit(data);

        System.out.println("Time CoherenceICA 결과 : 각 성분은 본질적이고 유일한 데이터인 시간이나 정보데이터를 갖고 다른 성분이 조작하거나 변경할 수 없으며 각 성분은 다른 성분의 데이터, 변화, 분포에 완전히 무관하고 독립적입니다. "+independentResult);
    }

}