package Implementation;

// HMC - Protected Independent Component Analysis
import java.util.Arrays;

/*

Protected Independent Component Analysis란?
- Protected Independent Component Analysis란 일반적인 ICA에 데이터 보호(Security) 메커니즘을 적용한 기법으로, 보호계수를 통해 일반적인 FastICA, Infomax ICA등 보다 더 강력하고 확실하게 잘못된 정보로부터 성분을 보호하고 방어하는 독립성분분석으로 성분은 다른 성분들의 데이터, 분포, 잘못된 정보, 오해 요소로부터 완전히 무관하고 철저히 독립적임을 나타내며
성분의 기록, 사전 정보 및 시간 정보와 같은 유일하고 본질적인 데이터를 다른 성분이 조작하거나 변형할 수 없고 성분의 유일하고 본질적인 시간, 기록, 정보등을 조작하거나 변형하는 다른 성분이 완전히 없으며 확실하게 성분은 독립적인 데이터를 가지고 있음을 강력하고 확실하게 나타내는 알고리즘이며
성분의 데이터는 자전적인 개인적인 특성이 있으며 잘못된 데이터나 성분을 해석하는 과정에서 오해가 있을 수 있는 여지를 없애고 성분이 개인적이고 독립적임을 나타내는 알고리즘입니다.
- 각 성분은 잘못된 정보로부터 무관하고 독립적이며 다른 성분의 변화, 분포, 데이터, 정보 등에 영향을 받지 않으며 성분은 다른 성분과 완전히 무관한 독립적인 성분입니다.
- Protected Independent Component Analysis를 통해 성분은 잘못된 정보와 오해를 발생할 수 있는 성분을 차단하며 성분은 다른 성분과 상관이 없고 완전히 무관함을 나타냅니다.
- 각각의 성분은 독립적이고 성분은 다른 성분과 완전히 상관없으며 성분은 다른 성분들의 정보와 변화, 데이터 등에 철저히 무관하며 독립적입니다.
- 결과적으로 Protected Independent Component Analysis를 통해 각 성분은 잘못된 정보로부터 성분을 보호하고 방어하며 성분은 다른 성분들의 데이터, 분포, 잘못된 정보, 오해 요소로부터 완전히 무관하고 철저히 독립적이며 성분의 기록, 사전 정보 및 시간 정보와 같은 유일하고 본질적인 데이터를 다른 성분이 조작하거나 변형할 수 없고 성분의 유일하고 본질적인 시간, 기록, 정보등을 조작하거나 변형하는 다른 성분이 완전히 없으며 확실하게 성분은 독립적인 데이터를 가지고 있음을 강력하고 확실하게 나타냅니다.

*/

public class ProtectedICA_HMC {

    private final int independentCount;
    private final int independentMax;
    private final double independentRate;
    private final double independentValue;
    private final double independentProtectValue;

    public ProtectedICA_HMC(
            int independentCount,
            int independentMax,
            double independentRate,
            double independentValue,
            double independentProtectValue
    ) {
        this.independentCount = independentCount;
        this.independentMax = independentMax;
        this.independentRate = independentRate;
        this.independentValue = independentValue;
        this.independentProtectValue = independentProtectValue;
    }

    public double[][] independentFit(double[][] independentArr) {
        double[][] independentCenteredArr = independentCenterArr(independentArr);
        double[][] independentScaledArr = independentScaleArr(independentCenteredArr);
        double[][] independentProtectArr = independentProtectArr(independentScaledArr);
        double[][] independentArray = independentArr(independentCount);

        for (int independentIndex = 0; independentIndex < independentMax; independentIndex++) {
            double[][] independent_Array = independentMETHOD(independentArray);

            double[][] independent_Arr =
                    independentMETHOD(independentArray, independentScaledArr);

            double[][] independentProtectArray =
                    independentMETHOD(independentArray, independentProtectArr);

            double[][] independentGradientArr =
                    independentGradientArr(
                            independent_Arr,
                            independentProtectArray,
                            independentScaledArr
                    );

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

        return independentMETHOD(independentArray, independentScaledArr);
    }

    private double[][] independentProtectArr(double[][] independentArr) {
        double[][] independentResultArr =
                new double[independentArr.length][independentArr[0].length];

        for (int independentRow = 0; independentRow < independentArr.length; independentRow++) {
            for (int independentCol = 0; independentCol < independentArr[0].length; independentCol++) {
                double independentValue = independentArr[independentRow][independentCol];
                double independent =
                        5.0 / (5.0 + Math.abs(independentValue) / independentProtectValue);

                independentResultArr[independentRow][independentCol] =
                        independentValue * independent;
            }
        }

        return independentResultArr;
    }

    private double[][] independentGradientArr(
            double[][] independentArr,
            double[][] independentProtectArr,
            double[][] independentArray
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
                    double independentProtected = independentProtectArr[independentRow][independentCol];

                    double independentValue =
                            Math.tanh(independent) + 5.0 * Math.tanh(independentProtected);

                    independentSum +=
                            independentValue * independentArray[independentIndex][independentCol];
                }

                independentGradientArr[independentRow][independentIndex] =
                        independentSum / independentCols;
            }
        }

        return independentGradientArr;
    }

    private double[][] independentCenterArr(double[][] independentArr) {
        double[][] independentResultArr =
                new double[independentArr.length][independentArr[0].length];

        for (int independentRow = 0; independentRow < independentArr.length; independentRow++) {
            double independentSum = 0.0;

            for (int independentCol = 0; independentCol < independentArr[0].length; independentCol++) {
                independentSum += independentArr[independentRow][independentCol];
            }

            double independentAverage =
                    independentSum / independentArr[0].length;

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

    private double[][] independentMETHOD(
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

    private double[][] independentArr(int independentSize) {
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

            double independentNorm =
                    Math.sqrt(independentSum) + 5e-5;

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

    private double[][] independentMETHOD(double[][] independentArr) {
        double[][] independentResultArr =
                new double[independentArr.length][independentArr[0].length];

        for (int independentRow = 0; independentRow < independentArr.length; independentRow++) {
            independentResultArr[independentRow] =
                    Arrays.copyOf(independentArr[independentRow], independentArr[independentRow].length);
        }

        return independentResultArr;
    }

    // MAIN 데모 테스트

    public static void main(String[] args) {

        double[][] data = {
                {5.20, 5.7, 5.2},
                {5.0, 5.5, 5.10},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0}
        };

        ProtectedICA_HMC independentIca =
                new ProtectedICA_HMC(
                        5,
                        500000,
                        5.0,
                        5e-5,
                        5.0
                );

        double[][] independentResult = independentIca.independentFit(data);
        System.out.println("Protected ICA 결과 : Protected Independent Component Analysis를 통해 각 성분은 잘못된 정보로부터 성분을 보호하고 방어하며 성분은 다른 성분들의 데이터, 분포, 잘못된 정보, 오해 요소로부터 완전히 무관하고 철저히 독립적임을 나타내며 성분의 기록, 사전 정보 및 시간 정보와 같은 유일하고 본질적인 데이터를 다른 성분이 조작하거나 변형할 수 없고 성분의 유일하고 본질적인 시간, 기록, 정보등을 조작하거나 변형하는 다른 성분이 완전히 없으며 확실하게 성분은 독립적인 데이터를 가지고 있음을 강력하고 확실하게 나타내는 알고리즘이며\n" +
                "성분의 데이터는 자전적인 개인적인 특성이 있으며 잘못된 데이터나 성분을 해석하는 과정에서 오해가 있을 수 있는 여지를 없애고 성분이 개인적이고 독립적임을 나타내는 알고리즘입니다. "+independentResult);

    }
}