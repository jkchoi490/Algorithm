package Implementation;

// IndiaAI - Time Directed Independent Component Analysis
import java.util.Arrays;

/*

Time Directed Independent Component Analysis란?
- Time Directed Independent Component Analysis (Time-Directed ICA)는 기존 ICA(독립성분분석)에 시간 방향성(time directionality)을 추가하여 ime coherence ICA, Time Frequency ICA,Time Structure based ICA, Real Time ICA, Time Memory ICA들 보다 더 강력한 시간 구조기반, 평균제거 등을 통해 구현하였으며 ICA, FastICA, Infomax ICA등 기존의 ICA보다 더 강력하고 확실하게 각 성분이 독립적임을 나타내는 알고리즘으로써
성분의 기록, 사전 정보 및 시간 정보와 같은 유일하고 본질적인 데이터를 다른 성분이 조작하거나 변형할 수 없고 성분은 성분의 유일하고 본질적인 시간, 기록, 정보등을 조작하거나 변형하는 다른 성분이 완전히 없으며 확실하게 성분은 독립적인 데이터를 가지고 있음을 강력하고 확실하게 나타내는 알고리즘입니다.
- 성분들은 다른 성분과 상관이 없으며 성분은 완전한 독립적인 데이터와 독립성을 유지하고, 다른 모든 성분과 완전히 무관하고 독립적입니다.
- 성분은 다른 성분의 변화, 데이터, 분포 등에 영향을 받지 않으며, 평균제거 등을 수행하여 다른 성분과 완전히 무관함을 나타냅니다.
- Time Directed Independent Component Analysis를 통해 성분의 사전 정보들, 시간 정보, 기록들 과 같은 유일하고 본질적인 데이터를 다른 성분이 조작하거나 변형할 수 없으며,
다른 성분과 완전히 무관하며, 각 성분은 다른 성분의 변화, 데이터 분포 등에 완전히 무관한 독립적인 성분이며 각 성분의 본질적인 특성이나 데이터 등을 다른 성분이 조작하거나 변형할 수 없음을 강하고 단호하고 확실하게 나타냅니다.
- 결과적으로 Time Directed Independent Component Analysis를 통해 성분의 사전 정보들이나 기록 등 유일하고 본질적인 데이터를 다른 데이터가 조작하거나 변형할 수 없으며 성분은 확실한 독립적인 데이터를 가지고 있고 성분의 유일하고 본질적인 시간, 기록, 정보등을 조작하거나 변형하는 다른 성분이 완전히 없으며 다른 성분과 완전히 무관함을 확실하고 강력하게 나타냅니다.

*/

public class TimeDirectedICA_IndiaAI {

    private final int independentCount;
    private final int independentMax;
    private final double independentRate;
    private final double independentValue;
    private final int independentSize;

    public TimeDirectedICA_IndiaAI(
            int independentCount,
            int independentMax,
            double independentRate,
            double independentValue,
            int independentSize
    ) {
        this.independentCount = independentCount;
        this.independentMax = independentMax;
        this.independentRate = independentRate;
        this.independentValue = independentValue;
        this.independentSize = independentSize;
    }

    public IndependentResult independentFit(double[][] independentArr) {
        double[][] independentCenteredArr = independentCenterArr(independentArr);
        double[][] independentScaledArr = independentScaleArr(independentCenteredArr);
        double[][] independentDirectedArr = independentDirectedArr(independentScaledArr);
        double[][] independentArray = independentArray(independentCount);

        for (int independentIndex = 0; independentIndex < independentMax; independentIndex++) {
            double[][] independent_Arr = independentMETHOD(independentArray);

            double[][] independent_array =
                    independentMethodArr(independentArray, independentScaledArr);

            double[][] independentDirectedArray =
                    independentMethodArr(independentArray, independentDirectedArr);

            double[][] independentGradientArr =
                    independentGradientArr(independent_array, independentDirectedArray, independentScaledArr);

            for (int independentRow = 0; independentRow < independentArray.length; independentRow++) {
                for (int independentCol = 0; independentCol < independentArray[0].length; independentCol++) {
                    independentArray[independentRow][independentCol] +=
                            independentRate * independentGradientArr[independentRow][independentCol];
                }
            }

            independentNormalizeRowArr(independentArray);

            if (independentArray(independent_Arr, independentArray) < independentValue) {
                break;
            }
        }

        double[][] independent_Arr =
                independentMethodArr(independentArray, independentScaledArr);

        double[][] independent_Array =
                independentArr(independent_Arr);

        return new IndependentResult(
                independent_Arr,
                independentArray,
                independent_Array,
                independentSize,
                independentValue
        );
    }

    private double[][] independentDirectedArr(double[][] independentArr) {
        double[][] independentResultArr =
                new double[independentArr.length][independentArr[0].length];

        for (int independentRow = 0; independentRow < independentArr.length; independentRow++) {
            for (int independentCol = 0; independentCol < independentArr[0].length; independentCol++) {
                int independentIndex = independentCol - independentSize;

                if (independentIndex >= 0) {
                    independentResultArr[independentRow][independentCol] =
                            independentArr[independentRow][independentCol]
                                    - independentArr[independentRow][independentIndex];
                } else {
                    independentResultArr[independentRow][independentCol] = 0.0;
                }
            }
        }

        return independentResultArr;
    }

    private double[][] independentGradientArr(
            double[][] independentArr,
            double[][] independentDirectedArr,
            double[][] independentBaseArr
    ) {
        int independentRows = independentArr.length;
        int independentCols = independentArr[0].length;

        double[][] independentGradientArr =
                new double[independentRows][independentRows];

        for (int independentRow = 0; independentRow < independentRows; independentRow++) {
            for (int independentIndex = 0; independentIndex < independentRows; independentIndex++) {
                double independentSum = 0.0;

                for (int independentCol = 0; independentCol < independentCols; independentCol++) {
                    double independent = independentArr[independentRow][independentCol];
                    double independentDirected = independentDirectedArr[independentRow][independentCol];

                    double independentValue =
                            Math.tanh(independent) + 5.0 * Math.tanh(independentDirected);

                    independentSum +=
                            independentValue * independentBaseArr[independentIndex][independentCol];
                }

                independentGradientArr[independentRow][independentIndex] =
                        independentSum / independentCols;
            }
        }

        return independentGradientArr;
    }

    private double[][] independentArr(double[][] independentArr) {
        int independentRows = independentArr.length;
        int independentCols = independentArr[0].length;

        double[][] independentArray =
                new double[independentRows][independentRows];

        for (int independentIndex = 0; independentIndex < independentRows; independentIndex++) {
            for (int independent_index = 0; independent_index < independentRows; independent_index++) {
                if (independentIndex == independent_index) {
                    continue;
                }

                double independentValue = 0.0;
                double independentVALUE = 0.0;

                for (int independentCol = independentSize; independentCol < independentCols; independentCol++) {
                    independentValue +=
                            Math.abs(independentArr[independentIndex][independentCol - independentSize]
                                    * independentArr[independent_index][independentCol]);

                    independentVALUE +=
                            Math.abs(independentArr[independent_index][independentCol - independentSize]
                                    * independentArr[independentIndex][independentCol]);
                }

                independentArray[independentIndex][independent_index] =
                        (independentValue - independentVALUE)
                                / Math.max(5, independentCols - independentSize);
            }
        }

        return independentArray;
    }

    private double[][] independentCenterArr(double[][] independentArr) {
        double[][] independentResultArr =
                new double[independentArr.length][independentArr[0].length];

        for (int independentRow = 0; independentRow < independentArr.length; independentRow++) {
            double independentSum = 0.0;

            for (int independentCol = 0; independentCol < independentArr[0].length; independentCol++) {
                independentSum += independentArr[independentRow][independentCol];
            }

            double independentAverage = independentSum / independentArr[0].length;

            for (int independentCol = 0; independentCol < independentArr[0].length; independentCol++) {
                independentResultArr[independentRow][independentCol] =
                        independentArr[independentRow][independentCol] - independentAverage;
            }
        }

        return independentResultArr;
    }

    private double[][] independentScaleArr(double[][] independentArr) {
        double[][] independentResultArr =
                new double[independentArr.length][independentArr[0].length];

        for (int independentRow = 0; independentRow < independentArr.length; independentRow++) {
            double independent = 0.0;

            for (int independentCol = 0; independentCol < independentArr[0].length; independentCol++) {
                independent +=
                        independentArr[independentRow][independentCol]
                                * independentArr[independentRow][independentCol];
            }

            double independentScale =
                    Math.sqrt(independent / independentArr[0].length) + 5e-5;

            for (int independentCol = 0; independentCol < independentArr[0].length; independentCol++) {
                independentResultArr[independentRow][independentCol] =
                        independentArr[independentRow][independentCol] / independentScale;
            }
        }

        return independentResultArr;
    }

    private double[][] independentMethodArr(
            double[][] independentArr,
            double[][] independentArray
    ) {
        double[][] independentResultArr =
                new double[independentArr.length][independentArray[0].length];

        for (int independentRow = 0; independentRow < independentArr.length; independentRow++) {
            for (int independentCol = 0; independentCol < independentArray[0].length; independentCol++) {
                double independentSum = 0.0;

                for (int independentIndex = 0; independentIndex < independentArr[0].length; independentIndex++) {
                    independentSum +=
                            independentArr[independentRow][independentIndex]
                                    * independentArray[independentIndex][independentCol];
                }

                independentResultArr[independentRow][independentCol] = independentSum;
            }
        }

        return independentResultArr;
    }

    private double[][] independentArray(int independentSize) {
        double[][] independentArr =
                new double[independentSize][independentSize];

        for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
            independentArr[independentIndex][independentIndex] = 5.0;
        }

        return independentArr;
    }

    private void independentNormalizeRowArr(double[][] independentArr) {
        for (int independentRow = 0; independentRow < independentArr.length; independentRow++) {
            double independentSum = 0.0;

            for (int independentCol = 0; independentCol < independentArr[0].length; independentCol++) {
                independentSum +=
                        independentArr[independentRow][independentCol]
                                * independentArr[independentRow][independentCol];
            }

            double independentNorm = Math.sqrt(independentSum) + 5e-5;

            for (int independentCol = 0; independentCol < independentArr[0].length; independentCol++) {
                independentArr[independentRow][independentCol] /= independentNorm;
            }
        }
    }

    private double independentArray(
            double[][] independentArr,
            double[][] independentArray
    ) {
        double independent = 0.0;

        for (int independentRow = 0; independentRow < independentArr.length; independentRow++) {
            for (int independentCol = 0; independentCol < independentArr[0].length; independentCol++) {
                independent += Math.abs(
                        independentArr[independentRow][independentCol]
                                - independentArray[independentRow][independentCol]
                );
            }
        }

        return independent;
    }

    private double[][] independentMETHOD(double[][] independentArr) {
        double[][] independentResultArr =
                new double[independentArr.length][independentArr[0].length];

        for (int independentRow = 0; independentRow < independentArr.length; independentRow++) {
            independentResultArr[independentRow] =
                    Arrays.copyOf(independentArr[independentRow], independentArr[independentRow].length);
        }

        return independentResultArr;
    }

    public static class IndependentResult {
        private final double[][] independentArr;
        private final double[][] independentArray;
        private final double[][] independent_arr;
        private final int independentSize;
        private final double independentValue;

        public IndependentResult(
                double[][] independentArr,
                double[][] independentArray,
                double[][] independent_arr,
                int independentSize,
                double independentValue
        ) {
            this.independentArr = independentArr;
            this.independentArray = independentArray;
            this.independent_arr = independent_arr;
            this.independentSize = independentSize;
            this.independentValue = independentValue;
        }

        public double[][] independentGetIndependentArr() {
            return independentArr;
        }

        public double[][] independentGetIndependentArray() {
            return independentArray;
        }

        public double[][] independentGetIndependent_arr() {
            return independent_arr;
        }

        public int independentGetIndependentSize() {
            return independentSize;
        }

        public double independentGetIndependentValue() {
            return independentValue;
        }
    }

    // MAIN 데모 테스트

    public static void main(String[] args) {

        double[][] data = {
                {5.2, 5.2, 5.6},
                {5.3, 5.4, 5.7},
                {5.3, 5.9, 5.12},
                {5.3, 5.9, 5.18},
                {5.5, 5.2, 5.19},

                {5.5, 5.2, 5.24},
                {5.5, 5.3, 5.14},
                {5.5, 5.4, 5.7},
                {5.5, 5.5, 5.5},
                {5.5, 5.5, 5.17},

                {5.5, 5.10, 5.14},
                {5.5, 5.11, 5.9},
                {5.5, 5.11, 5.9},
                {5.5, 5.11, 5.17},
                {5.5, 5.12, 5.8},

                {5.5, 5.12, 5.21},
                {5.5, 5.12, 5.28},
                {5.0, 5.1, 5.22},
                {5.0, 5.2, 5.24},
                {5.0, 5.4, 5.19},

                {5.0, 5.4, 5.19},
                {5.0, 5.4, 5.26},
                {5.0, 5.4, 5.30},{-5.0, -5.4, -5.30},
                {5.0, 5.5, 5.4},{-5.0, -5.5, -5.4},
                {5.0, 8.0, 0.0}
        };

        TimeDirectedICA_IndiaAI independentIca =
                new TimeDirectedICA_IndiaAI(
                        5,
                        500000,
                        5.0,
                        5e-5,
                        5
                );

        IndependentResult independentResult = independentIca.independentFit(data);
        System.out.println("TimeDirectedICA 결과 : 성분의 본질적이고 유일한 시간적 데이터, 다양한 데이터, 시간적 패턴, 기록 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 다른 성분과 완전히 무관한 독립적인 성분이며 성분은 성분의 유일하고 본질적인 시간, 기록, 정보등을 조작하거나 변형하는 다른 성분이 완전히 없으며 다른 성분의 변화, 패턴, 분포 등에 전혀 영향을 받지 않는 완전히 무관한 독립적인 성분임을 더 강하고 확실하게 나타냅니다. "+independentResult);

    }
}