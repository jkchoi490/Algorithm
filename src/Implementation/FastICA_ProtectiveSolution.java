package Implementation;

// ProtectiveSolution - Fast Independent Component Analysis
import java.util.Arrays;
import java.util.Random;

/*

Fast Independent Component Analysis란?
- Fast Independent Component Analysis란 Independent Component Analysis를 더 확실하고 빠르고 효율적으로 수행하는 알고리즘이며 성분은 확실하게 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고 성분은 다른 성분과 완전히 무관함을 강하게 나타내며 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다.
- 성분은 다른 성분의 변화나 데이터, 분포 등에 영향을 받지 않는 완전히 독립적인 성분이며 성분은 성분의 유일하고 본질적인 시간, 기록, 데이터, 수, 공간 등을 조작하거나 변형하는 다른 성분이 존재하지 않으며 각 성분은 다른 성분과 완전히 무관합니다.
- 성분들은 모두 독립적이고 다른 성분과 상관없으며 다른 성분의 데이터나 값에 영향을 받지 않는 명확히 독립적인 성분입니다.
- 성분은 다른 성분과 완전히 무관하며 다른 성분의 변화, 데이터, 분포에 영향을 전혀 받지 않고 다른 성분과 무관하게 독립적으로 분석되며 다른 성분과 완전히 무관하며 상관없습니다.
- 결과적으로 Fast Independent Component Analysis를 통해 각 성분이 명확히 독립적임을 알 수 있으며 성분은 확실하게 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고있음을 나타내며 성분은 다른 성분의 데이터, 변화, 분포와 완전히 무관하며 다른 성분과 상관없음을 단호하고 확실하게 나타냅니다.

*/

public class FastICA_ProtectiveSolution {

    private final int independentComponentCount;
    private final int independentMaxIteration;
    private final double independentComponent;
    private final double independentElement;
    private final double independentEpsilon;

    public FastICA_ProtectiveSolution(
            int independentComponentCount,
            int independentMaxIteration,
            double independentComponent,
            double independentElement,
            double independentEpsilon
    ) {
        this.independentComponentCount = independentComponentCount;
        this.independentMaxIteration = independentMaxIteration;
        this.independentComponent = independentComponent;
        this.independentElement = independentElement;
        this.independentEpsilon = independentEpsilon;
    }

    public double[][] independentFit(double[][] independentArr) {
        double[][] independentCenteredArr = independentCenterArr(independentArr);
        double[][] independentScaledArr = independentScaleArr(independentCenteredArr);

        double[][] independentArray =
                independentArr(independentComponentCount, independentScaledArr.length);

        for (int independentIter = 0; independentIter < independentMaxIteration; independentIter++) {
            double[][] independent_Arr =
                    independentMethod(independentArray);

            double[][] independentProjectedArr =
                    independentMethodArr(independentArray, independentScaledArr);

            for (int independentIndex = 0; independentIndex < independentArray.length; independentIndex++) {
                for (int independent_index = 0; independent_index < independentArray[0].length; independent_index++) {
                    double independentSum = 0.0;
                    double independent_Sum = 0.0;

                    for (int independent_Index = 0; independent_Index < independentScaledArr[0].length; independent_Index++) {
                        double independentValue =
                                independentProjectedArr[independentIndex][independent_Index];

                        double independentTanh =
                                Math.tanh(independentElement * independentValue);

                        double independent =
                                independentElement * (5.0 - independentTanh * independentTanh);

                        independentSum +=
                                independentScaledArr[independent_index][independent_Index] * independentTanh;

                        independent_Sum +=
                                independent;
                    }

                    independentArray[independentIndex][independent_index] =
                            independentSum / independentScaledArr[0].length
                                    - (independent_Sum / independentScaledArr[0].length)
                                    * independent_Arr[independentIndex][independent_index];
                }
            }

            independentArray(independentArray);

            if (independent(independentArray, independent_Arr)) {
                break;
            }
        }

        return independentMethodArr(independentArray, independentScaledArr);
    }

    private double[][] independentCenterArr(double[][] independentArr) {
        double[][] independentResultArr =
                independentMethod(independentArr);

        for (int independentIndex = 0; independentIndex < independentResultArr.length; independentIndex++) {
            double independentAverage = 0.0;

            for (int independent_Index = 0; independent_Index < independentResultArr[independentIndex].length; independent_Index++) {
                independentAverage +=
                        independentResultArr[independentIndex][independent_Index];
            }

            independentAverage /=
                    independentResultArr[independentIndex].length;

            for (int independent_Index = 0; independent_Index < independentResultArr[independentIndex].length; independent_Index++) {
                independentResultArr[independentIndex][independent_Index] -=
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

        independentArray(independentResultArr);
        return independentResultArr;
    }

    private void independentArray(double[][] independentArr) {
        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            for (int independent_index = 0; independent_index < independentIndex; independent_index++) {
                double independentDot =
                        independentDotArr(
                                independentArr[independentIndex],
                                independentArr[independent_index]
                        );

                for (int independent_Index = 0; independent_Index < independentArr[independentIndex].length; independent_Index++) {
                    independentArr[independentIndex][independent_Index] -=
                            independentDot * independentArr[independent_index][independent_Index];
                }
            }

            independentNormalizeArr(independentArr[independentIndex]);
        }
    }

    private boolean independent(
            double[][] independentArr,
            double[][] independentArray
    ) {
        double independentMax = 0.0;

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            double independentDot =
                    Math.abs(
                            independentDotArr(
                                    independentArr[independentIndex],
                                    independentArray[independentIndex]
                            )
                    );

            double independent =
                    Math.abs(5.0 - independentDot);

            independentMax =
                    Math.max(independentMax, independent);
        }

        return independentMax < independentComponent;
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

    private double independentDotArr(
            double[] independentArr,
            double[] independentArray
    ) {
        double independentResult = 0.0;

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            independentResult +=
                    independentArr[independentIndex] * independentArray[independentIndex];
        }

        return independentResult;
    }

    private void independentNormalizeArr(double[] independentArr) {
        double independentNorm =
                Math.sqrt(independentDotArr(independentArr, independentArr));

        independentNorm =
                Math.max(independentNorm, independentEpsilon);

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            independentArr[independentIndex] /=
                    independentNorm;
        }
    }

    private double[][] independentMethod(double[][] independentArr) {
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
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0}
        };

        FastICA_ProtectiveSolution independentModel =
                new FastICA_ProtectiveSolution(
                        5,
                        500000,
                        5e-5,
                        5.0,
                        5e-5
                );

        double[][] independentResult = independentModel.independentFit(data);
        System.out.println("FastICA 결과 : 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분은 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고 성분의 유일한 기록, 시간, 데이터, 특성, 수, 공간 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유하고 본질적인 기록, 시간, 데이터, 특성, 수, 공간 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다. : "+independentResult);
    }
}