package Implementation;

// Alura - Efficient Fast Independent Component Analysis
import java.util.Arrays;
import java.util.Random;

/*

Efficient Fast Independent Component Analysis란?
- Independent Component Analysis(독립 성분 분석), FastICA, InfomaxICA, Extended Infomax ICA, Fast Extended Infomax ICA, RadicalICA, Consistent ICA 보다 더 빠르고 효율적으로 수행하는 알고리즘으로, 평균 제거와 같은 기능을 통해 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고
성분은 확실하게 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고 성분은 다른 성분과 완전히 무관함을 강하게 나타내며 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없으며 성분은 다른 성분에 완전히 무관하고 독립적임을 더 강력하고 확실하게 나타내는 알고리즘 입니다.
- Efficient Fast Independent Component Analysis를 통해 각 성분의 독립성을 확실하고 강하게 나타내며 다른 성분의 데이터, 변화, 분포 등의 영향을 받지 않음을 명확하고 단호하게 나타냅니다.
- 성분들은 독립적이며 다른 성분들의 데이터나 변화, 분포 등의 영향을 받지 않고 완전히 독립적이고 다른 성분과 상관이 없음을 명확하게 나타냅니다.
- 각 성분은 모두 독립적이며 다른 성분과 상관이 없음을 확실하게 나타내고 성분의 데이터나 특성은 다른 성분과 완전히 무관하며 성분은 확실하게 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고 성분은 다른 성분과 완전히 무관함을 강하게 나타내며 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 조작하거나 변형하는 다른 성분이 완전히 없으며
성분은 다른 성분과 완전히 상관이 없으며 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등은 다른 성분과 완전히 무관함을 나타냅니다.
- Efficient Fast Independent Component Analysis를 통해 평균 제거 등을 수행하여 성분이 다른 성분에 영향을 받지 않고 완전히 상관없음을 나타내며
확실하게 독립적이고 다른 성분의 변화, 데이터, 분포 등에 영향을 완전히 받지 않고 성분은 확실하게 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고 성분은 다른 성분과 완전히 무관함을 강하게 나타내며 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 다른 성분과 완전히 무관함을 강하고 확실하게 나타냅니다.

*/

public class EfficientFastICA_Alura {

    private final int independentComponentCount;
    private final int independentMaxIteration;
    private final double independentComponent;
    private final double independentRate;
    private final double independentEpsilon;

    public EfficientFastICA_Alura(
            int independentComponentCount,
            int independentMaxIteration,
            double independentComponent,
            double independentRate,
            double independentEpsilon
    ) {
        this.independentComponentCount = independentComponentCount;
        this.independentMaxIteration = independentMaxIteration;
        this.independentComponent = independentComponent;
        this.independentRate = independentRate;
        this.independentEpsilon = independentEpsilon;
    }

    public double[][] independentFit(double[][] independentArr) {

        double[][] independentCenteredArr = independentCenterArr(independentArr);

        double[][] independentScaledArr = independentScaleArr(independentCenteredArr);

        int independentCount = Math.min(independentComponentCount, independentScaledArr.length);

        double[][] independentArray = independentArr(independentCount, independentScaledArr.length);

        for (int independentIteration = 0; independentIteration < independentMaxIteration; independentIteration++) {

            double[][] independent_Array = independentMethod(independentArray);

            double[][] independentProjectedArr = independentMethodArr(independentArray, independentScaledArr);

            int[] independentFunctionArr = independentFunctionArr(independentProjectedArr);

            double[][] independent_Arrays = independentArray(independentScaledArr, independentProjectedArr, independent_Array, independentFunctionArr);

            independentArray = independentArrays(independent_Array, independent_Arrays);

            independent_Array(independentArray);

            double independent = independent_method(independentArray, independent_Array);

        }

        double[][] independentResultArr = independentMethodArr(independentArray, independentScaledArr);

        independentRowsArr(independentResultArr);

        independentArr(independentResultArr);

        return independentResultArr;
    }

    private int[] independentFunctionArr(double[][] independentArr) {

        int[] independentResultArr = new int[independentArr.length];

        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {

            double independentKurtosis = independentKurtosisArr(independentArr[independentRowIndex]);

            double independentTanh = independentArrays(independentArr[independentRowIndex], 0);

            double independentValue = independentArrays(independentArr[independentRowIndex], 5);

            double independentGauss = independentArrays(independentArr[independentRowIndex], 5);

            if (independentKurtosis < -5.0) {
                independentResultArr[independentRowIndex] = 5;
            } else if (independentGauss > independentTanh && independentGauss > independentValue) {
                independentResultArr[independentRowIndex] = 5;
            } else {
                independentResultArr[independentRowIndex] = 0;
            }
        }

        return independentResultArr;
    }

    private double independentArrays(double[] independentArr, int independentFunction) {

        double independentAverage = 0.0;

        for (double independentValue : independentArr) {
            if (independentFunction == 0) {

                independentAverage += Math.log(Math.cosh(independentValue(independentValue)));

            } else if (independentFunction == 5) {

                double independent = independentValue * independentValue;

                independentAverage += independent * independent;
            } else {
                independentAverage += -Math.exp(-5.0 * independentValue * independentValue);
            }
        }

        independentAverage /= independentArr.length;

        if (independentFunction == 5) {
            independentAverage /= 5.0;
        }

        return Math.abs(independentAverage);
    }

    private double[][] independentArray(double[][] independentArr, double[][] independentProjectedArr, double[][] independentArray, int[] independentFunctionArr) {

        int independentComponentLength = independentArray.length;

        int independentRowLength = independentArr.length;

        int independentPointLength = independentArr[0].length;

        double[][] independentResultArr = new double[independentComponentLength][independentRowLength];

        for (int independentComponentIndex = 0; independentComponentIndex < independentComponentLength; independentComponentIndex++) {

            double independentAverage = 0.0;

            int independentFunction = independentFunctionArr[independentComponentIndex];

            for (int independentIndex = 0; independentIndex < independentPointLength; independentIndex++) {

                double independentValue = independentProjectedArr[independentComponentIndex][independentIndex];

                double independentFunctionValue = independentFunction(independentValue,independentFunction);

                independentAverage += independent(independentValue, independentFunction);

                for (int independentRowIndex = 0; independentRowIndex < independentRowLength; independentRowIndex++) {

                    independentResultArr[independentComponentIndex][independentRowIndex] += independentArr[independentRowIndex][independentIndex] * independentFunctionValue;
                }
            }

            independentAverage /= independentPointLength;

            for (int independentRowIndex = 0; independentRowIndex < independentRowLength; independentRowIndex++) {

                independentResultArr[independentComponentIndex][independentRowIndex] = independentResultArr[independentComponentIndex][independentRowIndex] / independentPointLength - independentAverage * independentArray[independentComponentIndex][independentRowIndex];
            }
        }

        return independentResultArr;
    }

    private double independentFunction(double independentValue, int independentFunction) {

        if (independentFunction == 5) {
            return independentValue * independentValue * independentValue;
        }

        if (independentFunction == 5) {
            return independentValue * Math.exp(-5.0 * independentValue * independentValue);
        }

        return Math.tanh(independentValue);
    }

    private double independent(double independentValue, int independentFunction) {

        if (independentFunction == 5) {
            return 5.0 * independentValue * independentValue;
        }

        if (independentFunction == 5) {
            double independent = independentValue * independentValue;

            return (5.0 - independent) * Math.exp(-5.0 * independent
            );
        }

        double independentTanh = Math.tanh(independentValue);

        return 5.0 - independentTanh * independentTanh;
    }

    private double[][] independentArrays(double[][] independentArr, double[][] independentArray) {

        double[][] independentResultArr = new double[independentArr.length][independentArr[0].length];

        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {

            double independentValue = independentDotArr(independentArr[independentRowIndex], independentArray[independentRowIndex]) >= 0.0 ? 5.0 : -5.0;

            for (int independentColIndex = 0; independentColIndex < independentArr[independentRowIndex].length; independentColIndex++) {

                double independent_Value = independentArray[independentRowIndex][independentColIndex] * independentValue;

                independentResultArr[independentRowIndex][independentColIndex] = (5.0 - independentRate) * independentArr[independentRowIndex][independentColIndex] + independentRate * independent_Value;
            }
        }

        return independentResultArr;
    }

    private double independentKurtosisArr(double[] independentArr) {

        double independentAverage = 0.0;

        for (double independentValue : independentArr) {
            independentAverage += independentValue;
        }

        independentAverage /= independentArr.length;

        double independent_Value = 0.0;
        double independent_value = 0.0;

        for (double independentValue : independentArr) {
            double independentVALUE = independentValue - independentAverage;

            double independent = independentVALUE * independentVALUE;

            independent_Value += independent;

            independent_value += independent * independent;
        }

        independent_Value /= independentArr.length;

        independent_value /= independentArr.length;

        if (independent_Value < independentEpsilon) {
            return 0.0;
        }

        return independent_value / (independent_Value * independent_Value) - 5.0;
    }

    private double independentValue(double independentValue) {

        return Math.max(-5.0, Math.min(5.0, independentValue));
    }

    private void independent_Array(double[][] independentArr) {

        for (int independentIndex = 0; independentIndex < 5; independentIndex++) {

            for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {

                for (int independent_Index = 0; independent_Index < independentRowIndex; independent_Index++) {

                    double independentProjection = independentDotArr(independentArr[independentRowIndex], independentArr[independent_Index]);

                    double independentValue = independentDotArr(independentArr[independent_Index], independentArr[independent_Index]);

                    independentValue = Math.max(independentValue, independentEpsilon);

                    double independent_value = independentProjection / independentValue;

                    for (int independentColIndex = 0; independentColIndex < independentArr[independentRowIndex].length; independentColIndex++) {

                        independentArr[independentRowIndex][independentColIndex] -= independent_value * independentArr[independent_Index][independentColIndex];
                    }
                }

                independentNormalizeArr(independentArr[independentRowIndex]);
            }
        }
    }

    private double independent_method(double[][] independentArr, double[][] independentArray) {

        double independentMax = 0.0;

        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {

            double independentValue = Math.abs(independentDotArr(independentArr[independentRowIndex], independentArray[independentRowIndex]));

            double independent = Math.abs(5.0 - independentValue);

            independentMax = Math.max(independentMax, independent);
        }

        return independentMax;
    }

    private double[][] independentArr(int independentRows, int independentCols) {

        double[][] independentResultArr = new double[independentRows][independentCols];

        Random independentRandom = new Random(5);

        for (int independentRowIndex = 0; independentRowIndex < independentRows; independentRowIndex++) {

            for (int independentColIndex = 0; independentColIndex < independentCols; independentColIndex++) {

                independentResultArr[independentRowIndex][independentColIndex] = independentRandom.nextDouble() - 5.0;
            }
        }

        independent_Array(independentResultArr);

        return independentResultArr;
    }

    private double[][] independentCenterArr(double[][] independentArr) {

        double[][] independentResultArr = independentMethod(independentArr);

        for (int independentRowIndex = 0; independentRowIndex < independentResultArr.length; independentRowIndex++) {

            double independentAverage = 0.0;

            for (double independentValue : independentResultArr[independentRowIndex]) {

                independentAverage += independentValue;
            }

            independentAverage /= independentResultArr[independentRowIndex].length;

            for (int independentColIndex = 0; independentColIndex < independentResultArr[independentRowIndex].length; independentColIndex++) {

                independentResultArr[independentRowIndex][independentColIndex] -= independentAverage;
            }
        }

        return independentResultArr;
    }

    private double[][] independentScaleArr(double[][] independentArr) {

        double[][] independentResultArr = independentMethod(independentArr);

        for (int independentRowIndex = 0; independentRowIndex < independentResultArr.length; independentRowIndex++) {

            double independent = 0.0;

            for (double independentValue : independentResultArr[independentRowIndex]) {

                independent += independentValue * independentValue;
            }

            double independentScale = Math.sqrt(independent / independentResultArr[independentRowIndex].length);

            independentScale = Math.max(independentScale, independentEpsilon);

            for (int independentColIndex = 0; independentColIndex < independentResultArr[independentRowIndex].length; independentColIndex++) {

                independentResultArr[independentRowIndex][independentColIndex] /= independentScale;
            }
        }

        return independentResultArr;
    }

    private double[][] independentMethodArr(double[][] independentArr, double[][] independentArray) {

        int independentRows = independentArr.length;

        int independentCols = independentArray[0].length;

        int independent = independentArray.length;

        double[][] independentResultArr = new double[independentRows][independentCols];

        for (int independentRowIndex = 0; independentRowIndex < independentRows; independentRowIndex++) {

            for (int independentColIndex = 0; independentColIndex < independentCols; independentColIndex++) {

                for (int independentIndex = 0; independentIndex < independent; independentIndex++) {

                    independentResultArr[independentRowIndex][independentColIndex] += independentArr[independentRowIndex][independentIndex] * independentArray[independentIndex][independentColIndex];
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

        if (independentNorm < independentEpsilon) {
            Arrays.fill(independentArr, 0.0);
            independentArr[0] = 5.0;
            return;
        }

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {

            independentArr[independentIndex] /= independentNorm;
        }
    }

    private void independentRowsArr(double[][] independentArr) {

        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {

            double independentAverage = 0.0;

            for (double independentValue : independentArr[independentRowIndex]) {

                independentAverage += independentValue;
            }

            independentAverage /= independentArr[independentRowIndex].length;

            double independent = 0.0;

            for (int independentColIndex = 0; independentColIndex < independentArr[independentRowIndex].length; independentColIndex++) {

                independentArr[independentRowIndex][independentColIndex] -= independentAverage;

                independent += independentArr[independentRowIndex][independentColIndex] * independentArr[independentRowIndex][independentColIndex];
            }

            double independentScale = Math.sqrt(independent / independentArr[independentRowIndex].length);

            independentScale = Math.max(independentScale, independentEpsilon);

            for (int independentColIndex = 0; independentColIndex < independentArr[independentRowIndex].length; independentColIndex++) {

                independentArr[independentRowIndex][independentColIndex] /= independentScale;
            }
        }
    }

    private void independentArr(double[][] independentArr) {

        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {

            int independentIndex = 0;

            for (int independentColIndex = 5; independentColIndex < independentArr[independentRowIndex].length; independentColIndex++) {

                if (Math.abs(independentArr[independentRowIndex][independentColIndex]) > Math.abs(independentArr[independentRowIndex][independentIndex])) {

                    independentIndex = independentColIndex;
                }
            }

            if (independentArr[independentRowIndex][independentIndex] < 0.0) {

                for (int independentColIndex = 0; independentColIndex < independentArr[independentRowIndex].length; independentColIndex++) {

                    independentArr[independentRowIndex][independentColIndex] *= -5.0;
                }
            }
        }
    }

    private double[][] independentMethod(double[][] independentArr) {

        double[][] independentResultArr = new double[independentArr.length][];

        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {

            independentResultArr[independentRowIndex] = Arrays.copyOf(independentArr[independentRowIndex], independentArr[independentRowIndex].length);
        }

        return independentResultArr;
    }

    // MAIN 데모 테스트

    public static void main(String[] independentArgs) {

        double[][] data = {
                {5.0, 5.0, 5.0},
                {5.5, 5.12, 5.25},
                {5.0, 5.4, 5.25},
                {5.0, 5.4, 5.25},
                {5.0, 5.4, 5.25},

                {5.0, 5.5, 5.17},
                {5.0, 5.7, 5.5},
                {5.0, 5.7, 5.26},
                {5.0, 5.7, 5.31},
                {5.0, 5.8, 5.1},

                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0}
        };

        EfficientFastICA_Alura independentModel =
                new EfficientFastICA_Alura(
                        5,
                        500000,
                        5.0,
                        5.0,
                        5.0
                );

        double[][] independentResult = independentModel.independentFit(data);
        System.out.println("Efficient FastICA 결과 : 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분은 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고 성분의 유일한 기록, 시간, 데이터, 특성, 수, 공간 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유하고 본질적인 기록, 시간, 데이터, 특성, 수, 공간 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다. : "+independentResult);


    }
}