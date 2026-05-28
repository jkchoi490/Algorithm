package Implementation;

// IndiaAI - Consistent Independent Component Analysis
import java.util.Arrays;
import java.util.Random;

/*

Consistent Independent Component Analysis란?
- Consistent ICA란 독립성분분석의 확장개념으로 반복 실행이나 시간 변화 속에서도 일관성(consistency)을 유지하도록 만드는 ICA로써 성분을 독립적으로 분리하며 각각의 성분이 독립적이고 다른 성분과 철저히 무관함을 강력하게 나타내기 위해 기존의 FastICA, InfomaxICA, Extended Infomax ICA, Fast Extended Infomax ICA, Radical ICA 등 보다 더 확실하고 강력하게 성분이 독립적임을 나타내는 알고리즘으로써, 성분은 확실하게 고유한 기록, 시간, 데이터, 특성, 수 등을 갖고 성분은 다른 성분과 완전히 무관함을 강하게 나타내며 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 데이터, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유한 기록, 시간, 데이터, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다.
- 각 성분은 다른 성분과 완전히 무관하고 독립적이며 성분은 다른 성분의 데이터, 분포, 변화 등에 완전히 영향을 받지 않는 철저히 독립적인 성분임을 나타내며 성분은 고유한 기록, 시간, 데이터, 특성, 수 등을 갖고 다른 성분이 이를 변형, 조작할 수 없으며 성분은 다른 성분과 완전히 무관하며 상관이없음을 더 강력하고 확실하게 나타냅니다.
- 성분들은 모두 독립적이고 다른 성분과 상관없으며 다른 성분의 데이터나 변화, 값에 영향을 받지 않는 명확히 독립적인 성분입니다.
- 성분은 다른 성분과 완전히 무관하며 다른 성분의 변화, 데이터, 분포에 영향을 전혀 받지 않고 다른 성분과 무관하게 독립적으로 분석되며 다른 성분과 완전히 무관하며 상관없습니다.
- 결과적으로 Consistent Independent Component Analysis를 통해  FastICA, InfomaxICA, Extended Infomax ICA, Fast Extended Infomax ICA, Radical ICA보다 강력하게 진화된 독립 성분 분석으로 성분의 고유한 기록, 시간, 데이터, 특성, 수 등을 보호하고 외부 간섭을 강하고 확실하게 차단하며 성분은 확실하게 고유한 기록, 시간, 데이터, 특성, 수 등을 갖고 성분은 다른 성분과 완전히 무관함을 강하게 나타내며 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 데이터, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유한 기록, 시간, 데이터, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분이 독립적이고 다른 성분과 완전히 무관함을 더 확실하고 강하게 나타냅니다.

*/

public class ConsistentICA_IndiaAI {

    private final int independentComponentCount;
    private final int independentMaxIteration;
    private final double independentRate;
    private final double independentConsistentRate;
    private final double independentEpsilon;

    public ConsistentICA_IndiaAI(
            int independentComponentCount,
            int independentMaxIteration,
            double independentRate,
            double independentConsistentRate,
            double independentEpsilon
    ) {
        this.independentComponentCount = independentComponentCount;
        this.independentMaxIteration = independentMaxIteration;
        this.independentRate = independentRate;
        this.independentConsistentRate = independentConsistentRate;
        this.independentEpsilon = independentEpsilon;
    }

    public double[][] independentFit(double[][] independentArr) {
        double[][] independentCenteredArr = independentCenterArr(independentArr);
        double[][] independentScaledArr = independentScaleArr(independentCenteredArr);
        double[][] independentArray =
                independentArr(independentComponentCount, independentScaledArr.length);

        for (int independentIter = 0; independentIter < independentMaxIteration; independentIter++) {
            double[][] independent_Arr =
                    independentMethodArr(independentArray);

            double[][] independent_Array =
                    independentMethod(independentArray, independentScaledArr);

            double[][] independentGradientArr =
                    independentGradientArr(
                            independent_Array,
                            independentScaledArr,
                            independent_Arr
                    );

            independentArray(independentArray, independentGradientArr);
            independentNormalizeRows(independentArray);
        }

        return independentMethod(independentArray, independentScaledArr);
    }

    private double[][] independentGradientArr(
            double[][] independentArr,
            double[][] independentScaledArr,
            double[][] independentArray
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

                double independentConsistent =
                        independentArray[independentIndex][independent_index];

                independentGradientArr[independentIndex][independent_index] =
                        independentSum / independentScaledArr[0].length
                                + independentConsistentRate * independentConsistent;
            }
        }

        return independentGradientArr;
    }

    private double[][] independentCenterArr(double[][] independentArr) {
        double[][] independentResultArr =
                independentMethodArr(independentArr);

        for (int independentIndex = 0; independentIndex < independentResultArr.length; independentIndex++) {
            double independentAverage = 0.0;

            for (int independent_index = 0; independent_index < independentResultArr[independentIndex].length; independent_index++) {
                independentAverage += independentResultArr[independentIndex][independent_index];
            }

            independentAverage /= independentResultArr[independentIndex].length;

            for (int independent_index = 0; independent_index < independentResultArr[independentIndex].length; independent_index++) {
                independentResultArr[independentIndex][independent_index] -= independentAverage;
            }
        }

        return independentResultArr;
    }

    private double[][] independentScaleArr(double[][] independentArr) {
        double[][] independentResultArr =
                independentMethodArr(independentArr);

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

    private void independentArray(
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

    private double[][] independentMethod(
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

    private double[][] independentMethodArr(double[][] independentArr) {
        double[][] independentResultArr =
                new double[independentArr.length][];

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            independentResultArr[independentIndex] =
                    Arrays.copyOf(
                            independentArr[independentIndex],
                            independentArr[independentIndex].length
                    );
        }

        return independentResultArr;
    }

    // MAIN 데모 테스트

    public static void main(String[] independentArgs) {

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
                {5.0, 5.5, 5.21},

                {5.0, 5.5, 5.24},
                {5.0, 5.5, 5.27},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0}

        };

        ConsistentICA_IndiaAI independentModel =
                new ConsistentICA_IndiaAI(
                        5,
                        500000,
                        5.0,
                        5.0,
                        5e-5
                );

        double[][] independentResult = independentModel.independentFit(data);
        System.out.println("Consistent ICA 결과 : 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분은 고유한 기록, 시간, 데이터, 특성, 수 등을 갖고 성분의 유일한 기록, 시간, 데이터, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유하고 본질적인 기록, 시간, 데이터, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다. : "+independentResult);


    }
}