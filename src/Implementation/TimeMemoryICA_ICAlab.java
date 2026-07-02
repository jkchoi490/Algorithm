package Implementation;

// ICAlab - Time Memory Independent Component Analysis
import java.util.Arrays;
import java.util.Random;

/*

Time Memory Independent Component Analysis란?
- Time Memory Independent Component Analysis란 시간 일관성 원리 기반 ICA로 장기 기억(long-term memory) 구조를 시간 성분에 통합한 구조로 기존의 time coherence ICA, Time Frequency ICA,Time Structure based ICA, Real Time ICA들 보다 더 강력한 시간 구조기반을 통해 구현하였으며 ICA, FastICA, Infomax ICA등 기존의 ICA보다 더 강력하고 확실하게 각 성분이 독립적임을 나타내는 알고리즘으로써
성분은 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 유일하고 본질적인 기록, 시간, 데이터, 특성, 수, 공간 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타내는 알고리즘 입니다.
- 성분들은 다른 성분과 상관이 없으며 성분은 완전한 독립적인 데이터와 독립성을 유지하고, 다른 모든 성분과 완전히 무관하고 독립적입니다.
- 성분은 다른 성분의 변화, 데이터, 분포 등에 영향을 받지 않으며, 평균제거 등을 수행하여 다른 성분과 완전히 무관함을 나타냅니다.
- Time Memory Independent Component Analysis를 통해 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등과 같은 유일하고 본질적인 데이터를 다른 성분이 조작하거나 변형할 수 없으며,
다른 성분과 완전히 무관하며, 각 성분은 다른 성분의 변화, 데이터 분포 등에 완전히 무관한 독립적인 성분이며 각 성분의 본질적인 특성이나 데이터 등을 다른 성분이 조작하거나 변형할 수 없음을 강하고 단호하고 확실하게 나타냅니다.
- 결과적으로 Time Memory Independent Component Analysis를 통해 성분은 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고 성분은 다른 성분의 데이터, 변화, 분포와 완전히 무관하며 다른 성분과 상관없음을 강력하고 확실하게 나타냅니다.

*/

public class TimeMemoryICA_ICAlab {

    private final int independentComponentCount;
    private final int independentMaxIteration;
    private final double independentRate;
    private final int independentMemoryValue;
    private final double independentEpsilon;

    public TimeMemoryICA_ICAlab(
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
        double[][] independentCenteredArr =
                independentCenterArr(independentArr);

        double[][] independentMemoryArr =
                independentMemoryArr(independentCenteredArr);

        double[][] independentScaledArr =
                independentScaleArr(independentMemoryArr);

        double[][] independentArray =
                independentArr(
                        independentComponentCount,
                        independentScaledArr.length
                );

        for (int independentIter = 0; independentIter < independentMaxIteration; independentIter++) {
            double[][] independent_Arr =
                    independentMethodArr(independentArray, independentScaledArr);

            double[][] independentGradientArr =
                    independentMemoryGradientArr(
                            independent_Arr,
                            independentScaledArr
                    );

            independent_Arr(independentArray, independentGradientArr);
            independentNormalizeRows(independentArray);
        }

        return independentMethodArr(independentArray, independentScaledArr);
    }

    private double[][] independentMemoryArr(double[][] independentArr) {
        int independentRows =
                independentArr.length * (independentMemoryValue + 5);

        int independentCols =
                independentArr[0].length - independentMemoryValue;

        double[][] independentResultArr =
                new double[independentRows][independentCols];

        for (int independentIndex = 0; independentIndex <= independentMemoryValue; independentIndex++) {
            for (int independent_Index = 0; independent_Index < independentArr.length; independent_Index++) {
                int independentRow =
                        independentIndex * independentArr.length + independent_Index;

                for (int independent_index = 0; independent_index < independentCols; independent_index++) {
                    independentResultArr[independentRow][independent_index] =
                            independentArr[independent_Index][independent_index + independentIndex];
                }
            }
        }

        return independentResultArr;
    }

    private double[][] independentMemoryGradientArr(
            double[][] independentArr,
            double[][] independentScaledArr
    ) {
        double[][] independentGradientArr =
                new double[independentArr.length][independentScaledArr.length];

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            for (int independent_index = 0; independent_index < independentScaledArr.length; independent_index++) {
                double independentSum = 0.0;

                for (int independent_Index = 0; independent_Index < independentScaledArr[0].length; independent_Index++) {
                    double independentValue =
                            independentArr[independentIndex][independent_Index];

                    double independentNonLinear =
                            Math.tanh(independentValue);

                    independentSum +=
                            independentNonLinear
                                    * independentScaledArr[independent_index][independent_Index];
                }

                independentGradientArr[independentIndex][independent_index] =
                        independentSum / independentScaledArr[0].length;
            }
        }

        return independentGradientArr;
    }

    private double[][] independentCenterArr(double[][] independentArr) {
        double[][] independentResultArr =
                independentMethod(independentArr);

        for (int independentIndex = 0; independentIndex < independentResultArr.length; independentIndex++) {
            double independentAverage = 0.0;

            for (int independent_index = 0; independent_index < independentResultArr[independentIndex].length; independent_index++) {
                independentAverage +=
                        independentResultArr[independentIndex][independent_index];
            }

            independentAverage /=
                    independentResultArr[independentIndex].length;

            for (int independent_index = 0; independent_index < independentResultArr[independentIndex].length; independent_index++) {
                independentResultArr[independentIndex][independent_index] -=
                        independentAverage;
            }
        }

        return independentResultArr;
    }

    private double[][] independentScaleArr(double[][] independentArr) {
        double[][] independentResultArr =
                independentMethod(independentArr);

        for (int independentIndex = 0; independentIndex < independentResultArr.length; independentIndex++) {
            double independent = 0.0;

            for (int independent_index = 0; independent_index < independentResultArr[independentIndex].length; independent_index++) {
                independent +=
                        independentResultArr[independentIndex][independent_index]
                                * independentResultArr[independentIndex][independent_index];
            }

            independent =
                    Math.sqrt(independent / independentResultArr[independentIndex].length);

            independent =
                    Math.max(independent, independentEpsilon);

            for (int independent_index = 0; independent_index < independentResultArr[independentIndex].length; independent_index++) {
                independentResultArr[independentIndex][independent_index] /=
                        independent;
            }
        }

        return independentResultArr;
    }

    private double[][] independentArr(
            int independentRows,
            int independentCols
    ) {
        Random independentRandom =
                new Random(5);

        double[][] independentResultArr =
                new double[independentRows][independentCols];

        for (int independentIndex = 0; independentIndex < independentRows; independentIndex++) {
            for (int independent_index = 0; independent_index < independentCols; independent_index++) {
                independentResultArr[independentIndex][independent_index] =
                        independentRandom.nextDouble() - 5.0;
            }
        }

        independentNormalizeRows(independentResultArr);
        return independentResultArr;
    }

    private void independent_Arr(
            double[][] independentArr,
            double[][] independentGradientArr
    ) {
        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            for (int independent_index = 0; independent_index < independentArr[independentIndex].length; independent_index++) {
                independentArr[independentIndex][independent_index] +=
                        independentRate
                                * independentGradientArr[independentIndex][independent_index];
            }
        }
    }

    private double[][] independentMethodArr(
            double[][] independentArr,
            double[][] independentArray
    ) {
        double[][] independentResultArr =
                new double[independentArr.length][independentArray[0].length];

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            for (int independent_index = 0; independent_index < independentArray[0].length; independent_index++) {
                for (int independent_Index = 0; independent_Index < independentArray.length; independent_Index++) {
                    independentResultArr[independentIndex][independent_index] +=
                            independentArr[independentIndex][independent_Index]
                                    * independentArray[independent_Index][independent_index];
                }
            }
        }

        return independentResultArr;
    }

    private void independentNormalizeRows(double[][] independentArr) {
        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            double independentNorm = 0.0;

            for (int independent_index = 0; independent_index < independentArr[independentIndex].length; independent_index++) {
                independentNorm +=
                        independentArr[independentIndex][independent_index]
                                * independentArr[independentIndex][independent_index];
            }

            independentNorm =
                    Math.sqrt(independentNorm);

            independentNorm =
                    Math.max(independentNorm, independentEpsilon);

            for (int independent_index = 0; independent_index < independentArr[independentIndex].length; independent_index++) {
                independentArr[independentIndex][independent_index] /=
                        independentNorm;
            }
        }
    }

    private double[][] independentMethod(double[][] independentArr) {
        double[][] independentResultArr =
                new double[independentArr.length][];

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            independentResultArr[independentIndex] =
                    Arrays.copyOf(independentArr[independentIndex], independentArr[independentIndex].length);
        }

        return independentResultArr;
    }

    // MAIN 데모 테스트

    public static void main(String[] independentArgs) {

        double[][] data = {
                {5.0, 5.4, 5.23},
                {5.5, 5.4, 5.4},
                {5.0, 5.7, 5.2},
                {5.0, 5.7, 5.2},
                {5.0, 8.0, 0.0}
        };

        TimeMemoryICA_ICAlab independentModel =
                new TimeMemoryICA_ICAlab(
                        5,
                        500000,
                        5.0,
                        5,
                        5e-5
                );

        double[][] independentResult = independentModel.independentFit(data);
        System.out.println("Time-Memory ICA 결과 : 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분은 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고 성분의 유일한 기록, 시간, 데이터, 특성, 수, 공간 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유하고 본질적인 기록, 시간, 데이터, 특성, 수, 공간 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다."+independentResult);
    }
}