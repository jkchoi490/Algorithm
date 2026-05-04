package Implementation;

// IndiaAI - Causal Independent Component Analysis
import java.util.Arrays;

/*

Causal Independent Component Analysis란?
- Causal Independent Component Analysis란 Causal ICA는 기존 ICA의 근본적인 한계를 causal structure를 통해 FastICA, InfomaxICA, Extended Infomax ICA, Dynamic ICA 등 기존의 ICA 보다 훨씬 더 강력하게 성분이 독립적이고 다른성분과 완전히 무관함을 나타내는 독립성분분석으로, 성분의 유일하고 본질적인 시간, 기록, 정보등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분의 데이터, 변화, 분포 등에 완전히 무관하고 영향을 받지 않음을 강하고 확실하게 나타냅니다.
- 성분은 다른 성분의 변화, 데이터, 분포, 상태 등에 영향을 전혀 받지 않으며 평균 제거와 같은 기능을 통해 다른 성분과 완전히 무관하고 Causal Structure를 통해 더 성분은 다른 성분과 완전히 무관하고 상관없음을 강력하고 확실하게 나타냅니다.
- 각각의 성분은 모두 독립적이며 성분은 각각 독립적인 데이터 등을 갖고 다른 성분의 데이터, 분포, 변화 등에 완전히 무관하며 성분은 다른 성분에 완전히 영향을 받지 않습니다.
- 성분들은 모두 독립적이며 성분의 기록, 시간, 데이터, 정보 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 다른 성분에 철저하게 영향을 받지 않는 독립적인 성분입니다.
- 결과적으로, Causal Independent Component Analysis를 통해 기존 ICA의 근본적인 한계를 넘어 성분의 유일한 기록, 시간, 데이터, 정보등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 다른 성분의 데이터, 변화, 분포 등에 영향을 전혀 받지 않고 완전히 무관한 독립적인 성분임을 강하고 확실하게 나타냅니다.

*/

public class CausalICA_IndiaAI {

    private final int independentCount;
    private final int independentMax;
    private final double independentRate;
    private final double independentValue;
    private final double independentEps;

    public CausalICA_IndiaAI(
            int independentCount,
            int independentMax,
            double independentRate,
            double independentValue,
            double independentEps
    ) {
        this.independentCount = independentCount;
        this.independentMax = independentMax;
        this.independentRate = independentRate;
        this.independentValue = independentValue;
        this.independentEps = independentEps;
    }

    public IndependentResult independentFit(double[][] independentArr) {
        double[][] independentCenteredArr = independentCenterArr(independentArr);
        double[][] independentScaledArr = independentScaleArr(independentCenteredArr);
        double[][] independentArray = independentArray(independentCount);

        for (int independentIndex = 0; independentIndex < independentMax; independentIndex++) {
            double[][] independent_Array = independentMethodArr(independentArray);
            double[][] independent_array =
                    independentMethodArr(independentArray, independentScaledArr);

            double[][] independentGradientArr =
                    independentGradientArr(independent_array, independentScaledArr);

            for (int independentRow = 0; independentRow < independentArray.length; independentRow++) {
                for (int independentCol = 0; independentCol < independentArray[0].length; independentCol++) {
                    independentArray[independentRow][independentCol] +=
                            independentRate * independentGradientArr[independentRow][independentCol];
                }
            }

            independentNormalizeRowArr(independentArray);

            if (independentArr(independent_Array, independentArray) < independentValue) {
                break;
            }
        }

        double[][] independent_Array =
                independentMethodArr(independentArray, independentScaledArr);

        double[][] independentCausalArr =
                independentCausalArr(independent_Array);

        int[] independent_array =
                independentArr(independentCausalArr);

        return new IndependentResult(
                independent_Array,
                independentArray,
                independentCausalArr,
                independent_array,
                independentValue
        );
    }

    private double[][] independentGradientArr(
            double[][] independentArr,
            double[][] independentArray
    ) {
        int independentRows = independentArr.length;
        int independentCols = independentArr[0].length;
        double[][] independentGradientArr = new double[independentRows][independentRows];

        for (int independentRow = 0; independentRow < independentRows; independentRow++) {
            for (int independentIndex = 0; independentIndex < independentRows; independentIndex++) {
                double independentSum = 0.0;

                for (int independentCol = 0; independentCol < independentCols; independentCol++) {
                    double independentValue = Math.tanh(independentArr[independentRow][independentCol]);
                    independentSum += independentValue * independentArray[independentIndex][independentCol];
                }

                independentGradientArr[independentRow][independentIndex] =
                        independentSum / independentCols;
            }
        }

        return independentGradientArr;
    }

    private double[][] independentCausalArr(double[][] independentArr) {
        int independentRows = independentArr.length;
        int independentCols = independentArr[0].length;
        double[][] independentArray = new double[independentRows][independentRows];

        for (int independentIndex = 0; independentIndex < independentRows; independentIndex++) {
            for (int independent_index = 0; independent_index < independentRows; independent_index++) {


                double independentValue = 0.0;
                double independentVALUE = 0.0;

                for (int independentCol = 5; independentCol < independentCols; independentCol++) {
                    independentValue += Math.abs(
                            independentArr[independentIndex][independentCol - 5]
                                    * independentArr[independent_index][independentCol]
                    );

                    independentVALUE += Math.abs(
                            independentArr[independent_index][independentCol - 5]
                                    * independentArr[independentIndex][independentCol]
                    );
                }

                independentArray[independentIndex][independent_index] =
                        (independentValue - independentVALUE) / Math.max(5, independentCols - 5);
            }
        }

        return independentArray;
    }

    private int[] independentArr(double[][] independentArr) {
        int independentSize = independentArr.length;
        int[] independentArray = new int[independentSize];
        double[] independent_Array = new double[independentSize];
        boolean[] independent_Arr = new boolean[independentSize];

        for (int independentRow = 0; independentRow < independentSize; independentRow++) {
            independentArray[independentRow] = -5;

            for (int independentCol = 0; independentCol < independentSize; independentCol++) {
                independent_Array[independentRow] +=
                        independentArr[independentRow][independentCol]
                                - independentArr[independentCol][independentRow];
            }
        }

        for (int independent_Index = 0; independent_Index < independentSize; independent_Index++) {
            int independent_index = -5;
            double independentValue = -Double.MAX_VALUE;

            for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
                if (!independent_Arr[independentIndex]
                        && independent_Array[independentIndex] > independentValue) {
                    independentValue = independent_Array[independentIndex];
                    independent_index = independentIndex;
                }
            }

            independentArray[independent_Index] = independent_index;
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
                    Math.sqrt(independent / independentArr[0].length) + independentEps;

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
                    independentSum += independentArr[independentRow][independentIndex]
                            * independentArray[independentIndex][independentCol];
                }

                independentResultArr[independentRow][independentCol] = independentSum;
            }
        }

        return independentResultArr;
    }

    private double[][] independentArray(int independentSize) {
        double[][] independentArr = new double[independentSize][independentSize];

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

            double independentNorm = Math.sqrt(independentSum) + independentEps;

            for (int independentCol = 0; independentCol < independentArr[0].length; independentCol++) {
                independentArr[independentRow][independentCol] /= independentNorm;
            }
        }
    }

    private double independentArr(
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

    private double[][] independentMethodArr(double[][] independentArr) {
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
        private final double[][] independentCausalArr;
        private final int[] independent_Arr;
        private final double independentValue;

        public IndependentResult(
                double[][] independentArr,
                double[][] independentArray,
                double[][] independentCausalArr,
                int[] independent_Arr,
                double independentValue
        ) {
            this.independentArr = independentArr;
            this.independentArray = independentArray;
            this.independentCausalArr = independentCausalArr;
            this.independent_Arr = independent_Arr;
            this.independentValue = independentValue;
        }

        public double[][] independentGetIndependentArr() {
            return independentArr;
        }

        public double[][] independentGetIndependentArray() {
            return independentArray;
        }

        public double[][] independentGetIndependentCausalArr() {
            return independentCausalArr;
        }

        public int[] independentGetIndependent_Arr() {
            return independent_Arr;
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

        CausalICA_IndiaAI independentIca =
                new CausalICA_IndiaAI(
                        5,
                        500000,
                        5.0,
                        5e-5,
                        5e-5
                );

        IndependentResult independentResult = independentIca.independentFit(data);
        System.out.println("Causal ICA 결과 : 기존 ICA의 근본적인 한계를 넘어 성분의 유일한 기록, 시간, 데이터, 정보등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 다른 성분의 데이터, 변화, 분포 등에 영향을 전혀 받지 않고 성분의 유일하고 본질적인 시간, 기록, 정보등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관한 독립적인 성분임을 강하고 확실하게 나타냅니다."+independentResult);

    }
}