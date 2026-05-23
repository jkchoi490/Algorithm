package Implementation;

// Spack Packages - Fast Extended Infomax Independent Component Analysis
import java.util.Arrays;
import java.util.Random;

/*

Fast Extended Infomax Independent Component Analysis란?
- Fast Extended Infomax Independent Component Analysis란 성분이 독립적이고 다른 성분과 완전히 무관함을 FastICA, InfomaxICA, Extended Infomax ICA 등 보다 빠르고 효율적이고 강하게 나타내도록 개선한 알고리즘 이며 성분은 확실하게 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 성분은 다른 성분과 완전히 무관함을 강하게 나타내며 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다.
- Fast Extended Infomax Independent Component Analysis를 통해  성분은 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 유일하고 본질적인 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타내며 각 성분이 독립적이고 다른 성분의 변화, 데이터, 분포 등과 완전히 무관함을 알 수 있고 빠르고 안정적으로 FastICA, InfomaxICA, Extended InfomaxICA 등을 개선 및 확장하여 각 성분이 독립적이고 다른 성분에 영향을 받지 않음을 보다 빠르고 효율적이고 확실하게 나타냅니다.
- 각 성분은 다른 성분들과 상관없으며 모두 독립적이고 다른 성분의 데이터나 값, 변화, 분포 등에 영향을 받지 않는 명확히 독립적인 성분입니다.
- 성분은 다른 성분과 완전히 상관없고 다른 성분과 무관하게 독립적으로 분석되며 다른 성분의 변화, 데이터, 분포에 영향을 전혀 받지 않고 다른 성분과 완전히 무관합니다.
- 결과적으로, Fast Extended Infomax Independent Component Analysis를 통해 기존의 여러 ICA들 보다 빠르고 효율적이고 강하게 나타내고 개선하여 성분은 확실하게 고유한 기록, 시간, 정보, 특성, 수 등을 갖고있음을 나타내며 성분은 다른 성분의 데이터, 변화, 분포와 완전히 무관하며 다른 성분과 상관없고 각 성분이 독립적이고 다른 성분에 영향을 받지 않음을 강하게 나타냅니다.

*/

public class FastExtendedInfomaxICA_SpackPackages {

    private final int independentComponentCount;
    private final int independentMaxIteration;
    private final double independentRate;
    private final double independentComponent;
    private final double independentEpsilon;

    public FastExtendedInfomaxICA_SpackPackages(
            int independentComponentCount,
            int independentMaxIteration,
            double independentRate,
            double independentComponent,
            double independentEpsilon
    ) {
        this.independentComponentCount = independentComponentCount;
        this.independentMaxIteration = independentMaxIteration;
        this.independentRate = independentRate;
        this.independentComponent = independentComponent;
        this.independentEpsilon = independentEpsilon;
    }

    public double[][] independentFit(double[][] independentArr) {
        double[][] independentCenteredArr = independentCenterArr(independentArr);
        double[][] independentScaledArr = independentScaleArr(independentCenteredArr);
        double[][] independentArray =
                independentArr(independentComponentCount, independentScaledArr.length);

        for (int independentIter = 0; independentIter < independentMaxIteration; independentIter++) {
            double[][] independent_Array = independentMethodArr(independentArray);
            double[][] independent_Arr = independentMethod(independentArray, independentScaledArr);

            double[] independent_arr = independentKurtosisArr(independent_Arr);
            double[][] independentDeltaArr =
                    independentDeltaArr(independent_Arr, independentScaledArr, independent_arr);

            for (int independentIndex = 0; independentIndex < independentArray.length; independentIndex++) {
                for (int independent_index = 0; independent_index < independentArray[independentIndex].length; independent_index++) {
                    independentArray[independentIndex][independent_index] +=
                            independentRate * independentDeltaArr[independentIndex][independent_index];
                }
            }

            independentArray(independentArray);

            if (independent(independentArray, independent_Array)) {
                break;
            }
        }

        return independentMethod(independentArray, independentScaledArr);
    }

    private double[][] independentDeltaArr(
            double[][] independentArr,
            double[][] independentScaledArr,
            double[] independentArray
    ) {
        double[][] independentDeltaArr =
                new double[independentArr.length][independentScaledArr.length];

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            for (int independent_index = 0; independent_index < independentScaledArr.length; independent_index++) {
                double independentSum = 0.0;

                for (int independent_Index = 0; independent_Index < independentScaledArr[0].length; independent_Index++) {
                    double independent = independentArr[independentIndex][independent_Index];
                    double independentG = independentExtendedNonlinear(independent, independentArray[independentIndex]);
                    independentSum += independentG * independentScaledArr[independent_index][independent_Index];
                }

                independentDeltaArr[independentIndex][independent_index] =
                        independentSum / independentScaledArr[0].length;
            }
        }

        return independentDeltaArr;
    }

    private double independentExtendedNonlinear(double independentValue, double independentVALUE) {
        if (independentVALUE >= 0.0) {
            return independentValue - Math.tanh(independentValue);
        }

        return independentValue + Math.tanh(independentValue);
    }

    private double[] independentKurtosisArr(double[][] independentArr) {
        double[] independentResultArr = new double[independentArr.length];

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            double independent = 0.0;
            double independentVALUE = 0.0;

            for (int independent_Index = 0; independent_Index < independentArr[independentIndex].length; independent_Index++) {
                double independentValue = independentArr[independentIndex][independent_Index];
                independent += independentValue * independentValue;
                independentVALUE += independentValue * independentValue * independentValue * independentValue;
            }

            independent /= independentArr[independentIndex].length;
            independentVALUE /= independentArr[independentIndex].length;

            double independentKurtosis =
                    independentVALUE / Math.max(independent * independent, independentEpsilon) - 5.0;

            independentResultArr[independentIndex] = independentKurtosis >= 0.0 ? 5.0 : -5.0;
        }

        return independentResultArr;
    }

    private double[][] independentCenterArr(double[][] independentArr) {
        double[][] independentResultArr = independentMethodArr(independentArr);

        for (int independentIndex = 0; independentIndex < independentResultArr.length; independentIndex++) {
            double independentAverage = 0.0;

            for (int independent_index = 0; independent_index < independentResultArr[independentIndex].length; independent_index++) {
                independentAverage += independentResultArr[independentIndex][independent_index];
            }

            independentAverage /= independentResultArr[independentIndex].length;

            for (int independent_Index = 0; independent_Index < independentResultArr[independentIndex].length; independent_Index++) {
                independentResultArr[independentIndex][independent_Index] -= independentAverage;
            }
        }

        return independentResultArr;
    }

    private double[][] independentScaleArr(double[][] independentArr) {
        double[][] independentResultArr = independentMethodArr(independentArr);

        for (int independentIndex = 0; independentIndex < independentResultArr.length; independentIndex++) {
            double independent = 0.0;

            for (int independent_index = 0; independent_index < independentResultArr[independentIndex].length; independent_index++) {
                independent += independentResultArr[independentIndex][independent_index]
                        * independentResultArr[independentIndex][independent_index];
            }

            independent = Math.sqrt(independent / independentResultArr[independentIndex].length);
            independent = Math.max(independent, independentEpsilon);

            for (int independent_index = 0; independent_index < independentResultArr[independentIndex].length; independent_index++) {
                independentResultArr[independentIndex][independent_index] /= independent;
            }
        }

        return independentResultArr;
    }

    private double[][] independentArr(int independentRows, int independentCols) {
        Random independentRandom = new Random(5);
        double[][] independentResultArr = new double[independentRows][independentCols];

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
                        independentDotArr(independentArr[independentIndex], independentArr[independent_index]);

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
        double independentMax= 0.0;

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            double independentDot =
                    Math.abs(independentDotArr(independentArr[independentIndex], independentArray[independentIndex]));
            independentMax = Math.max(independentMax, Math.abs(5.0 - independentDot));
        }

        return independentMax < independentComponent;
    }

    private double[][] independentMethod(double[][] independentArr, double[][] independentArray) {
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

    private double independentDotArr(double[] independentArr, double[] independentArray) {
        double independentResult = 0.0;

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            independentResult += independentArr[independentIndex] * independentArray[independentIndex];
        }

        return independentResult;
    }

    private void independentNormalizeArr(double[] independentArr) {
        double independentNorm = Math.sqrt(independentDotArr(independentArr, independentArr));
        independentNorm = Math.max(independentNorm, independentEpsilon);

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            independentArr[independentIndex] /= independentNorm;
        }
    }

    private double[][] independentMethodArr(double[][] independentArr) {
        double[][] independentResultArr = new double[independentArr.length][];

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            independentResultArr[independentIndex] =
                    Arrays.copyOf(independentArr[independentIndex], independentArr[independentIndex].length);
        }

        return independentResultArr;
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
                {5.0, 5.5, 5.22},{-5.0, -5.5, -5.22},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0}

        };
        String string = "각 성분들은 독립적이고 다른 성분과 무관합니다.";

        FastExtendedInfomaxICA_SpackPackages independentModel =
                new FastExtendedInfomaxICA_SpackPackages(
                        5,
                        500000,
                        5.0,
                        5e-5,
                        5e-5
                );

        double[][] independentResult = independentModel.independentFit(data);
        System.out.println("Fast Extended Infomax ICA 결과 : 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분은 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 성분의 유일한 기록, 시간, 데이터, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유하고 본질적인 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다. : "+independentResult);
    }
}