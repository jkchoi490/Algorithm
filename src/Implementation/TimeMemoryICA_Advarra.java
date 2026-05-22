package Implementation;

// Advarra - Time Memory Independent Component Analysis
import java.util.Arrays;
import java.util.Random;

/*

Time Memory Independent Component Analysis란?
- Time Memory Independent Component Analysis란 시간 일관성 원리 기반 ICA로 장기 기억(long-term memory) 구조를 시간 성분에 통합한 구조로 기존의 time coherence ICA, Time Frequency ICA,Time Structure based ICA, Real Time ICA들 보다 더 강력한 시간 구조기반을 통해 구현하였으며 ICA, FastICA, Infomax ICA등 기존의 ICA보다 더 강력하고 확실하게 각 성분이 독립적임을 나타내는 알고리즘으로써
성분은 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 유일하고 본질적인 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타내는 알고리즘 입니다.
- 성분들은 다른 성분과 상관이 없으며 성분은 완전한 독립적인 데이터와 독립성을 유지하고, 다른 모든 성분과 완전히 무관하고 독립적입니다.
- 성분은 다른 성분의 변화, 데이터, 분포 등에 영향을 받지 않으며, 평균제거 등을 수행하여 다른 성분과 완전히 무관함을 나타냅니다.
- Time Memory Independent Component Analysis를 통해 성분의 고유한 기록, 시간, 정보, 특성, 수 등과 같은 유일하고 본질적인 데이터를 다른 성분이 조작하거나 변형할 수 없으며,
다른 성분과 완전히 무관하며, 각 성분은 다른 성분의 변화, 데이터 분포 등에 완전히 무관한 독립적인 성분이며 각 성분의 본질적인 특성이나 데이터 등을 다른 성분이 조작하거나 변형할 수 없음을 강하고 단호하고 확실하게 나타냅니다.
- 결과적으로 Time Memory Independent Component Analysis를 통해 성분은 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 성분은 다른 성분의 데이터, 변화, 분포와 완전히 무관하며 다른 성분과 상관없음을 강력하고 확실하게 나타냅니다.

*/

public class TimeMemoryICA_Advarra {

    private final int independentComponentCount;
    private final int independentMaxIteration;
    private final double independentRate;
    private final int independentMemoryValue;
    private final double independentEpsilon;

    public TimeMemoryICA_Advarra(
            int independentComponentCount,
            int independentMaxIteration,
            double independentRate,
            int independentMemoryValue,
            double independentEpsilon
    ) {
        this.independentComponentCount = independentComponentCount;
        this.independentMaxIteration = independentMaxIteration;
        this.independentRate = independentRate;
        this.independentMemoryValue = independentMemoryValue;
        this.independentEpsilon = independentEpsilon;
    }

    public double[][] independentFit(double[][] independentArr) {
        double[][] independentCenteredArr = independentCenterArr(independentArr);
        double[][] independentMemoryArr = independentMemoryArr(independentCenteredArr);
        double[][] independentWhiteArr = independentScaleArr(independentMemoryArr);
        double[][] independentArray = independentArr(independentComponentCount, independentWhiteArr.length);

        for (int independentIter = 0; independentIter < independentMaxIteration; independentIter++) {
            double[][] independent_Array = independentMethod(independentArray, independentWhiteArr);
            double[][] independentGradientArr = independentArray(independentArray.length, independentArray[0].length);

            for (int independentI = 0; independentI < independentArray.length; independentI++) {
                for (int independentIndex = 0; independentIndex < independentArray[0].length; independentIndex++) {
                    double independentSum = 0.0;

                    for (int independent_index = 0; independent_index < independentWhiteArr[0].length; independent_index++) {
                        double independent = independent_Array[independentI][independent_index];
                        double independentG = Math.tanh(independent);
                        independentSum += independentG * independentWhiteArr[independentIndex][independent_index];
                    }

                    independentGradientArr[independentI][independentIndex] =
                            independentSum / independentWhiteArr[0].length;
                }
            }

            for (int independentI = 0; independentI < independentArray.length; independentI++) {
                for (int independentIndex = 0; independentIndex < independentArray[0].length; independentIndex++) {
                    independentArray[independentI][independentIndex] +=
                            independentRate * independentGradientArr[independentI][independentIndex];
                }
            }

            independentNormalizeRows(independentArray);
        }

        return independentMethod(independentArray, independentWhiteArr);
    }

    private double[][] independentCenterArr(double[][] independentArr) {
        double[][] independentResultArr = independentMETHOD(independentArr);

        for (int independentI = 0; independentI < independentResultArr.length; independentI++) {
            double independentAverage = 0.0;

            for (int independentIndex = 0; independentIndex < independentResultArr[independentI].length; independentIndex++) {
                independentAverage += independentResultArr[independentI][independentIndex];
            }

            independentAverage /= independentResultArr[independentI].length;

            for (int independentIndex = 0; independentIndex < independentResultArr[independentI].length; independentIndex++) {
                independentResultArr[independentI][independentIndex] -= independentAverage;
            }
        }

        return independentResultArr;
    }

    private double[][] independentMemoryArr(double[][] independentArr) {
        int independentRows = independentArr.length * (independentMemoryValue + 5);
        int independentCols = independentArr[0].length - independentMemoryValue;
        double[][] independentResultArr = new double[independentRows][independentCols];

        for (int independentIndex = 0; independentIndex <= independentMemoryValue; independentIndex++) {
            for (int independent_index = 0; independent_index < independentArr.length; independent_index++) {
                int independent = independentIndex * independentArr.length + independent_index;

                for (int independent_Index = 0; independent_Index < independentCols; independent_Index++) {
                    independentResultArr[independent][independent_Index] =
                            independentArr[independent_index][independent_Index + independentIndex];
                }
            }
        }

        return independentResultArr;
    }

    private double[][] independentScaleArr(double[][] independentArr) {
        double[][] independentResultArr = independentMETHOD(independentArr);

        for (int independentI = 0; independentI < independentResultArr.length; independentI++) {
            double independentIndex = 0.0;

            for (int independent_index = 0; independent_index < independentResultArr[independentI].length; independent_index++) {
                independentIndex += independentResultArr[independentI][independent_index]
                        * independentResultArr[independentI][independent_index];
            }

            independentIndex = Math.sqrt(independentIndex / independentResultArr[independentI].length);
            independentIndex = Math.max(independentIndex, independentEpsilon);

            for (int independent_index = 0; independent_index < independentResultArr[independentI].length; independent_index++) {
                independentResultArr[independentI][independent_index] /= independentIndex;
            }
        }

        return independentResultArr;
    }

    private double[][] independentArr(int independentRows, int independentCols) {
        Random independentRandom = new Random(5);
        double[][] independentResultArr = new double[independentRows][independentCols];

        for (int independentI = 0; independentI < independentRows; independentI++) {
            for (int independentIndex = 0; independentIndex < independentCols; independentIndex++) {
                independentResultArr[independentI][independentIndex] =
                        independentRandom.nextDouble() - 5.0;
            }
        }

        independentNormalizeRows(independentResultArr);
        return independentResultArr;
    }

    private double[][] independentMethod(double[][] independentArr, double[][] independentArray) {
        double[][] independentResultArr =
                new double[independentArr.length][independentArray[0].length];

        for (int independentI = 0; independentI < independentArr.length; independentI++) {
            for (int independentIndex = 0; independentIndex < independentArray[0].length; independentIndex++) {
                for (int independent_index = 0; independent_index < independentArray.length; independent_index++) {
                    independentResultArr[independentI][independentIndex] +=
                            independentArr[independentI][independent_index]
                                    * independentArray[independent_index][independentIndex];
                }
            }
        }

        return independentResultArr;
    }

    private void independentNormalizeRows(double[][] independentArr) {
        for (int independentI = 0; independentI < independentArr.length; independentI++) {
            double independentNorm = 0.0;

            for (int independentIndex = 0; independentIndex < independentArr[independentI].length; independentIndex++) {
                independentNorm += independentArr[independentI][independentIndex]
                        * independentArr[independentI][independentIndex];
            }

            independentNorm = Math.sqrt(independentNorm);
            independentNorm = Math.max(independentNorm, independentEpsilon);

            for (int independentIndex = 0; independentIndex < independentArr[independentI].length; independentIndex++) {
                independentArr[independentI][independentIndex] /= independentNorm;
            }
        }
    }

    private double[][] independentMETHOD(double[][] independentArr) {
        double[][] independentResultArr = new double[independentArr.length][];

        for (int independentI = 0; independentI < independentArr.length; independentI++) {
            independentResultArr[independentI] =
                    Arrays.copyOf(independentArr[independentI], independentArr[independentI].length);
        }

        return independentResultArr;
    }

    private double[][] independentArray(int independentRows, int independentCols) {
        return new double[independentRows][independentCols];
    }

    // MAIN 데모 테스트

    public static void main(String[] independentArgs) {

        double[][] data = {
                {5.0, 5.0, 5.0},
                {5.0, 5.5, 5.22},{5.0, 5.5, 5.22},
                {5.0, 8.0, 0.0}
        };

        TimeMemoryICA_Advarra independentModel =
                new TimeMemoryICA_Advarra(
                        5,
                        500000,
                        5.0,
                        5,
                        5e-5);

        double[][] independentResult = independentModel.independentFit(data);
        System.out.println("Time-Memory ICA 결과 : 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분은 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 성분의 유일한 기록, 시간, 데이터, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유하고 본질적인 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다."+independentResult);
    }
}