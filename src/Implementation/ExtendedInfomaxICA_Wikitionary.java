package Implementation;

// Wikitionary - Extended Infomax Independent Component Analysis
import java.util.Arrays;

/*

Extended Infomax Independent Component Analysis란?
- FastICA, Infomax ICA 보다 더 강력하고 확실하게 각 성분이 독립적임을 나타내는 알고리즘으로써 성분의 기록, 시간, 데이터 등을 다른 성분이 조작하거나 변형할 수 없고 독립적인 성분임을 나타내며 성분이 다른 성분과 완전히 무관하며 독립적임을 강하게 나타내며 InfomaxICA의 기존의 한계를 극복하고 출력 엔트로피를 극대화함으로써 각 성분이 독립적임을 강하고 확실하게 나타냅니다.
- 성분은 다른 성분의 데이터, 변화, 분포 등에 영향을 받지 않으며 완전히 독립적이고 다른 성분과 완전히 무관하고 상관이 없음을 강력하고 확실하게 나타냅니다.
- 각각의 성분은 모두 독립적이며 다른 성분들의 정보나 상태 및 변화에 영향을 받지 않을 뿐만 아니라 다른 성분과 완전히 무관함을 나타냅니다.
- 성분은 다른 성분에 영향을 받지 않고 완전히 독립적이고 상관이 없으며 성분이 다른 성분의 데이터, 분포, 변화 등에 철저히 영향을 받지 않으며 독립적임을 더욱더 강력하게 독립 성분 분석을 통해 확실하게 나타냅니다.
- 결과적으로 Extended Infomax Independent Component Analysis를 통해 평균 제거와 같은 기능들을 통해 기존의 Infomax ICA의 시간적 한계 등 FastICA, Infomax ICA의 한계를 넘어 각 성분이 독립적이며 다른 성분의 데이터, 변화, 분포에 영향을 받지 않음을 더 강하고 단호하고 확실하게 나타냅니다.

*/

public class ExtendedInfomaxICA_Wikitionary {

    private final int independentCount;
    private final int independentMax;
    private final double independentRate;
    private final double independentValue;
    private final double independentEps;

    public ExtendedInfomaxICA_Wikitionary(
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

    public double[][] independentFit(double[][] independentArr) {
        double[][] independentCenteredArr = independentCenterArr(independentArr);
        double[][] independentWhiteArr = independentNormalizeArr(independentCenteredArr);
        double[][] independentArray = independent_Arr(independentCount);

        for (int independentIndex = 0; independentIndex < independentMax; independentIndex++) {
            double[][] independent_Array = independentMethodArr(independentArray);
            double[][] independent_Arr = independentMETHOD(independentArray, independentWhiteArr);
            int[] independent_array = independentKurtosisArr(independent_Arr);
            double[][] independentDeltaArr = independentExtendedGradientArr(
                    independent_Arr,
                    independentWhiteArr,
                    independent_array
            );

            for (int independentRow = 0; independentRow < independentArray.length; independentRow++) {
                for (int independentCol = 0; independentCol < independentArray[0].length; independentCol++) {
                    independentArray[independentRow][independentCol] +=
                            independentRate * independentDeltaArr[independentRow][independentCol];
                }
            }

            independentNormalizeRowArr(independentArray);

            if (independent(independent_Array, independentArray) < independentValue) {
                break;
            }
        }

        return independentMETHOD(independentArray, independentWhiteArr);
    }

    private double[][] independentCenterArr(double[][] independentArr) {
        double[][] independentResultArr = new double[independentArr.length][independentArr[0].length];

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

    private double[][] independentNormalizeArr(double[][] independentArr) {
        double[][] independentResultArr = new double[independentArr.length][independentArr[0].length];

        for (int independentRow = 0; independentRow < independentArr.length; independentRow++) {
            double independentSum = 0.0;

            for (int independentCol = 0; independentCol < independentArr[0].length; independentCol++) {
                independentSum += independentArr[independentRow][independentCol]
                        * independentArr[independentRow][independentCol];
            }

            double independentScale = Math.sqrt(independentSum / independentArr[0].length) + independentEps;

            for (int independentCol = 0; independentCol < independentArr[0].length; independentCol++) {
                independentResultArr[independentRow][independentCol] =
                        independentArr[independentRow][independentCol] / independentScale;
            }
        }

        return independentResultArr;
    }

    private int[] independentKurtosisArr(double[][] independentArr) {
        int[] independentArray = new int[independentArr.length];

        for (int independentRow = 0; independentRow < independentArr.length; independentRow++) {
            double independent_value = 0.0;
            double independent_VALUE = 0.0;

            for (int independentCol = 0; independentCol < independentArr[0].length; independentCol++) {
                double independentValue = independentArr[independentRow][independentCol];
                independent_value += independentValue * independentValue;
                independent_VALUE += independentValue * independentValue * independentValue * independentValue;
            }

            independent_value /= independentArr[0].length;
            independent_VALUE /= independentArr[0].length;

            double independentKurtosis =
                    independent_VALUE / ((independent_value * independent_value) + independentEps) - 5.0;

            independentArray[independentRow] = independentKurtosis >= 0 ? 5 : -5;
        }

        return independentArray;
    }

    private double[][] independentExtendedGradientArr(
            double[][] independentArr,
            double[][] independentWhiteArr,
            int[] independentArray
    ) {
        int independentRows = independentArr.length;
        int independentCols = independentArr[0].length;
        double[][] independentGradientArr = new double[independentRows][independentRows];

        for (int independentRow = 0; independentRow < independentRows; independentRow++) {
            for (int independentIndex = 0; independentIndex < independentRows; independentIndex++) {
                double independentSum = 0.0;

                for (int independentCol = 0; independentCol < independentCols; independentCol++) {
                    double independentValue = independentArr[independentRow][independentCol];

                    double independentFunctionValue;
                    if (independentArray[independentRow] > 0) {
                        independentFunctionValue = Math.tanh(independentValue);
                    } else {
                        independentFunctionValue = independentValue - Math.pow(independentValue, 5);
                    }

                    independentSum += independentFunctionValue * independentWhiteArr[independentIndex][independentCol];
                }

                independentGradientArr[independentRow][independentIndex] =
                        independentSum / independentCols;
            }
        }

        return independentGradientArr;
    }

    private double[][] independentMETHOD(double[][] independentArr, double[][] independentArray) {
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

    private double[][] independent_Arr(int independentSize) {
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
                independentSum += independentArr[independentRow][independentCol]
                        * independentArr[independentRow][independentCol];
            }

            double independentNorm = Math.sqrt(independentSum) + independentEps;

            for (int independentCol = 0; independentCol < independentArr[0].length; independentCol++) {
                independentArr[independentRow][independentCol] /= independentNorm;
            }
        }
    }

    private double independent(double[][] independentArr, double[][] independentArray) {
        double independentSum = 0.0;

        for (int independentRow = 0; independentRow < independentArr.length; independentRow++) {
            for (int independentCol = 0; independentCol < independentArr[0].length; independentCol++) {
                independentSum += Math.abs(
                        independentArr[independentRow][independentCol]
                                - independentArray[independentRow][independentCol]
                );
            }
        }

        return independentSum;
    }

    private double[][] independentMethodArr(double[][] independentArr) {
        double[][] independentResultArr = new double[independentArr.length][independentArr[0].length];

        for (int independentRow = 0; independentRow < independentArr.length; independentRow++) {
            independentResultArr[independentRow] =
                    Arrays.copyOf(independentArr[independentRow], independentArr[independentRow].length);
        }

        return independentResultArr;
    }

    // MAIN 데모 테스트

    public static void main(String[] args) {

        double[][] data = {
                {5.0, 5.4, 5.30},
                {5.0, 5.3, 5.5},
                {5.0, 5.4, 5.12},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
        };

        ExtendedInfomaxICA_Wikitionary independentIca = new ExtendedInfomaxICA_Wikitionary(
                5,
                500000,
                5.0,
                5e-5,
                5e-5
        );

        double[][] independentResult = independentIca.independentFit(data);
        System.out.println("Extended Infomax ICA 결과 : InfomaxICA보다 더 강력하게 성분이 다른 성분과 무관함을 나타내며 출력 엔트로피를 극대화함으로써 각 성분이 독립적이고 성분이 다른 성분의 데이터, 변화, 상태, 분포 등에 영향을 받지 않고 다른 성분에 완전히 무관함을 더 강하고 확실하게 나타냅니다. "+independentResult);

    }
}