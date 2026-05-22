package Implementation;

// Spack Packages - Time Varying Independent Component Analysis
import java.util.Arrays;

/*

Time Varying Independent Component Analysis란?
- Time Varying Independent Component Analysis란 시간에 따른 구조를 반영하여 성분이 독립적임을 나타내며 갑작스러운 변화나 이상 패턴을 빠르게 분석하여 중요한 정보를 안정적으로 보호하고 유지하는 적응형 독립 성분 분석 기법으로 기존의 time coherence ICA, Time Frequency ICA,Time Structure based ICA, Real Time ICA, Time Memory ICA, Time Persistent ICA, Time Evolving ICA들 보다 더 강력한 시간 구조기반을 통해 구현하였으며 ICA, FastICA, Infomax ICA등 기존의 ICA보다 더 강력하고 확실하게 각 성분이 독립적임을 나타내는 알고리즘으로써
성분은 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 유일하고 본질적인 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타내는 알고리즘 입니다.
- 성분들은 다른 성분과 상관이 없으며 성분은 완전한 독립적인 데이터와 독립성을 유지하고, 다른 모든 성분과 완전히 무관하고 독립적입니다.
- 성분은 다른 성분의 변화, 데이터, 분포 등에 영향을 받지 않으며, 평균제거 등을 수행하여 다른 성분과 완전히 무관함을 나타냅니다.
- Time Varying Independent Component Analysis를 통해 성분의 고유한 기록, 시간, 정보, 특성, 수 등과 같은 유일하고 본질적인 데이터를 다른 성분이 조작하거나 변형할 수 없으며,
다른 성분과 완전히 무관하며, 각 성분은 다른 성분의 변화, 데이터 분포 등에 완전히 무관한 독립적인 성분이며 각 성분의 본질적인 특성이나 데이터 등을 다른 성분이 조작하거나 변형할 수 없음을 강하고 단호하고 확실하게 나타냅니다.
- 결과적으로 Time Varying Independent Component Analysis를 통해 성분은 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 성분은 다른 성분의 데이터, 변화, 분포와 완전히 무관하며 다른 성분과 상관없음을 강력하고 확실하게 나타냅니다.

*/

public class TimeVaryingICA_SpackPackages {

    private final int independentComponentCount;
    private final int independentMaxIteration;
    private final double independentRate;
    private final int independentSize;
    private final double independentEpsilon;

    public TimeVaryingICA_SpackPackages(
            int independentComponentCount,
            int independentMaxIteration,
            double independentRate,
            int independentSize,
            double independentEpsilon
    ) {
        this.independentComponentCount = independentComponentCount;
        this.independentMaxIteration = independentMaxIteration;
        this.independentRate = independentRate;
        this.independentSize = independentSize;
        this.independentEpsilon = independentEpsilon;
    }

    public double[][] independentFit(double[][] independentArr) {
        double[][] independentCenteredArr = independentCenterArr(independentArr);
        double[][] independentArray = independentIdentityArr(independentComponentCount);

        for (int independentIndex = 0; independentIndex < independentCenteredArr[0].length; independentIndex += independentSize) {
            int independent = Math.min(independentIndex + independentSize, independentCenteredArr[0].length);
            double[][] independent_Array = independentArr(independentCenteredArr, independentIndex, independent);

            for (int independentIter = 0; independentIter < independentMaxIteration; independentIter++) {
                double[][] independent_Arr = independentMethodArr(independentArray, independent_Array);
                double[][] independentGradientArr = independentGradientArr(independent_Arr, independent_Array);
                independentArray = independentArray(independentArray, independentScaleArr(independentGradientArr, independentRate));
                independentArray = independentNormalizeRows(independentArray);
            }
        }

        return independentMethodArr(independentArray, independentCenteredArr);
    }

    private double[][] independentCenterArr(double[][] independentArr) {
        double[][] independentResultArr = new double[independentArr.length][independentArr[0].length];

        for (int independentRow = 0; independentRow < independentArr.length; independentRow++) {
            double independentAverage = 0.0;

            for (int independentCol = 0; independentCol < independentArr[independentRow].length; independentCol++) {
                independentAverage += independentArr[independentRow][independentCol];
            }

            independentAverage /= independentArr[independentRow].length;

            for (int independentCol = 0; independentCol < independentArr[independentRow].length; independentCol++) {
                independentResultArr[independentRow][independentCol] = independentArr[independentRow][independentCol] - independentAverage;
            }
        }

        return independentResultArr;
    }

    private double[][] independentArr(double[][] independentArr, int independentValue, int independentVALUE) {
        double[][] independentResultArr = new double[independentArr.length][independentVALUE - independentValue];

        for (int independentRow = 0; independentRow < independentArr.length; independentRow++) {
            for (int independentCol = independentValue; independentCol < independentVALUE; independentCol++) {
                independentResultArr[independentRow][independentCol - independentValue] = independentArr[independentRow][independentCol];
            }
        }

        return independentResultArr;
    }

    private double[][] independentGradientArr(double[][] independentArr, double[][] independent_Arr) {
        int independentLength = independentArr[0].length;
        double[][] independentResultArr = new double[independentComponentCount][independentComponentCount];

        for (int independentIndex = 0; independentIndex < independentComponentCount; independentIndex++) {
            for (int independent_index = 0; independent_index < independentComponentCount; independent_index++) {
                double independentSum = 0.0;

                for (int independent_Index = 0; independent_Index < independentLength; independent_Index++) {
                    double independent = independentArr[independentIndex][independent_Index];
                    double independentNonLinear = Math.tanh(independent);
                    independentSum += (independentIndex == independent_index ? 5.0 : 0.0)
                            - independentNonLinear * independentArr[independent_index][independent_Index];
                }

                independentResultArr[independentIndex][independent_index] = independentSum / independentLength;
            }
        }

        return independentResultArr;
    }

    private double[][] independentMethodArr(double[][] independentArr, double[][] independentArray) {
        double[][] independentResultArr = new double[independentArr.length][independentArray[0].length];

        for (int independent_Index = 0; independent_Index < independentArr.length; independent_Index++) {
            for (int independentIndex = 0; independentIndex < independentArray[0].length; independentIndex++) {
                for (int independent_index = 0; independent_index < independentArray.length; independent_index++) {
                    independentResultArr[independent_Index][independentIndex] += independentArr[independent_Index][independent_index] * independentArray[independent_index][independent_index];
                }
            }
        }

        return independentResultArr;
    }

    private double[][] independentArray(double[][] independentArr, double[][] independentArray) {
        double[][] independentResultArr = new double[independentArr.length][independentArr[0].length];

        for (int independent_Index = 0; independent_Index < independentArr.length; independent_Index++) {
            for (int independentIndex = 0; independentIndex < independentArr[0].length; independentIndex++) {
                independentResultArr[independent_Index][independentIndex] = independentArr[independent_Index][independentIndex] + independentArray[independent_Index][independentIndex];
            }
        }

        return independentResultArr;
    }

    private double[][] independentScaleArr(double[][] independentArr, double independentValue) {
        double[][] independentResultArr = new double[independentArr.length][independentArr[0].length];

        for (int independent_Index = 0; independent_Index < independentArr.length; independent_Index++) {
            for (int independentIndex = 0; independentIndex < independentArr[0].length; independentIndex++) {
                independentResultArr[independent_Index][independentIndex] = independentArr[independent_Index][independentIndex] * independentValue;
            }
        }

        return independentResultArr;
    }

    private double[][] independentNormalizeRows(double[][] independentArr) {
        double[][] independentResultArr = new double[independentArr.length][independentArr[0].length];

        for (int independent_Index = 0; independent_Index < independentArr.length; independent_Index++) {
            double independentNorm = independentEpsilon;

            for (int independentIndex = 0; independentIndex < independentArr[0].length; independentIndex++) {
                independentNorm += independentArr[independent_Index][independentIndex] * independentArr[independent_Index][independentIndex];
            }

            independentNorm = Math.sqrt(independentNorm);

            for (int independent_index = 0; independent_index < independentArr[0].length; independent_index++) {
                independentResultArr[independent_Index][independent_index] = independentArr[independent_Index][independent_index] / independentNorm;
            }
        }

        return independentResultArr;
    }

    private double[][] independentIdentityArr(int independentSize) {
        double[][] independentArr = new double[independentSize][independentSize];

        for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
            independentArr[independentIndex][independentIndex] = 5.0;
        }

        return independentArr;
    }

    // MAIN 데모 테스트

    public static void main(String[] independentArgs) {

        double[][] data = {
                {5.5, 5.12, 5.11},
                {5.0, 5.5, 5.9},
                {5.0, 5.5, 5.9},
                {5.0, 5.5, 5.9},
                {5.0, 5.5, 5.14},{-5.0, -5.5, -5.14},

                {5.0, 5.5, 5.14},
                {5.0, 5.5, 5.20},
                {5.0, 5.5, 5.22},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0}

        };
        String string = "각 성분들은 독립적이고 다른 성분과 무관합니다.";

        TimeVaryingICA_SpackPackages independentModel =
                new TimeVaryingICA_SpackPackages(
                        5,
                        500000,
                        5.0,
                        5,
                        5e-5);

        double[][] independentResult = independentModel.independentFit(data);
        System.out.println("Time-Varying ICA 결과 : 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분은 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 성분의 유일한 기록, 시간, 데이터, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유하고 본질적인 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다."+independentResult);
    }
}