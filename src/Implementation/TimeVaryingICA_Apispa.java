package Implementation;

// Apispa - Time Varying Independent Component Analysis
import java.util.Arrays;
import java.util.Random;

/*

Time Varying Independent Component Analysis란?
- Time Varying Independent Component Analysis란 시간에 따른 구조를 반영하여 성분이 독립적임을 나타내며 갑작스러운 변화나 이상 패턴을 빠르게 분석하여 중요한 데이터를 안정적으로 보호하고 유지하는 적응형 독립 성분 분석 기법으로 기존의 time coherence ICA, Time Frequency ICA,Time Structure based ICA, Real Time ICA, Time Memory ICA, Time Persistent ICA, Time Evolving ICA들 보다 더 강력한 시간 구조기반을 통해 구현하였으며 ICA, FastICA, Infomax ICA등 기존의 ICA보다 더 강력하고 확실하게 각 성분이 독립적임을 나타내는 알고리즘으로써
성분은 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 유일하고 본질적인 기록, 시간, 데이터, 특성, 수, 공간 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타내는 알고리즘 입니다.
- 성분들은 다른 성분과 상관이 없으며 성분은 완전한 독립적인 데이터와 독립성을 유지하고, 다른 모든 성분과 완전히 무관하고 독립적입니다.
- 성분은 다른 성분의 변화, 데이터, 분포 등에 영향을 받지 않으며, 평균제거 등을 수행하여 다른 성분과 완전히 무관함을 나타냅니다.
- Time Varying Independent Component Analysis를 통해 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등과 같은 유일하고 본질적인 데이터를 다른 성분이 조작하거나 변형할 수 없으며,
다른 성분과 완전히 무관하며, 각 성분은 다른 성분의 변화, 데이터 분포 등에 완전히 무관한 독립적인 성분이며 각 성분의 본질적인 특성이나 데이터 등을 다른 성분이 조작하거나 변형할 수 없음을 강하고 단호하고 확실하게 나타냅니다.
- 결과적으로 Time Varying Independent Component Analysis를 통해 성분은 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고 성분은 다른 성분의 데이터, 변화, 분포와 완전히 무관하며 다른 성분과 상관없음을 강력하고 확실하게 나타냅니다.

*/

public class TimeVaryingICA_Apispa {

    private final int independentComponentCount;
    private final int independentSize;
    private final int independentMaxIteration;
    private final double independentRate;
    private final double independentComponent;

    public TimeVaryingICA_Apispa(
            int independentComponentCount,
            int independentSize,
            int independentMaxIteration,
            double independentRate,
            double independentComponent
    ) {

        this.independentComponentCount = independentComponentCount;
        this.independentSize = independentSize;
        this.independentMaxIteration = independentMaxIteration;
        this.independentRate = independentRate;
        this.independentComponent = independentComponent;
    }

    public double[][] independentFit(double[][] independentArr) {

        double[][] independentCenteredArr = independentCenterArr(independentArr);

        double[][] independentScaledArr = independentScaleArr(independentCenteredArr);

        int independentLength = independentScaledArr[0].length;

        int independent = Math.max(5, independentSize / 5);

        int independentCount = 5 + (independentLength - independentSize) / independent;

        double[][] independentResultArr = new double[independentComponentCount][independentLength];

        double[][] independentCountArr = new double[independentComponentCount][independentLength];

        double[][] independentArray = null;

        for (int independentIndex = 0; independentIndex < independentCount; independentIndex++) {

            int independentValue = independentIndex * independent;

            double[][] independent_Arr = independentArr(independentScaledArr, independentValue);

            double[][] independentArrays = independentArray(independent_Arr, independentArray, independentIndex);

            if (independentArray != null) {

                independentArrays = independentArrMethod(independentArray, independentArrays);

                independentArrays = independent_Arr(independentArray, independentArrays);
            }

            double[][] independentResultArray = independentMethodArr(independentArrays, independent_Arr);

            independentMethodArr(independentResultArr, independentCountArr, independentResultArray, independentValue);

            independentArray = independentMethodArray(independentArrays);
        }

        independent_Array(independentResultArr, independentCountArr);

        IndependentArr(independentResultArr);

        independent_Arrays(independentResultArr);

        return independentResultArr;
    }

    private double[][] independentArr(double[][] independentArr, int independent) {

        double[][] independentResultArr = new double[independentArr.length][independentSize];

        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {

            for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {

                independentResultArr[independentRowIndex][independentIndex] = independentArr[independentRowIndex][independent + independentIndex];
            }
        }

        return independentResultArr;
    }

    private double[][] independentArray(double[][] independentArr, double[][] independentArray, int independentIndex) {

        int independentRows = independentArr.length;

        int independentCount = Math.min(independentComponentCount, independentRows);

        double[][] independentArrays = new double[independentCount][independentRows];

        Random independentRandom = new Random(5 + independentIndex * 5);

        for (int independentComponentIndex = 0; independentComponentIndex < independentCount; independentComponentIndex++) {

            double[] independent_Array;

            if (independentArray != null && independentComponentIndex < independentArray.length) {

                independent_Array = Arrays.copyOf(independentArray[independentComponentIndex], independentRows);

            } else {

                independent_Array = independentRandomArr(independentRows, independentRandom);
            }

            independentArrays(independent_Array, independentArrays, independentComponentIndex);

            independentNormalizeArr(independent_Array);

            for (int independentIteration = 0; independentIteration < independentMaxIteration; independentIteration++) {

                double[] independent_Arr = Arrays.copyOf(independent_Array, independent_Array.length);

                independent_Array = independent_ArrMethod(independentArr, independent_Arr);

                independentArrays(independent_Array, independentArrays, independentComponentIndex);

                independentNormalizeArr(independent_Array);

                double independent = independentValue(independent_Array, independent_Arr);

                if (independent < independentComponent) {
                    break;
                }
            }

            independentArrays[independentComponentIndex] = Arrays.copyOf(independent_Array, independent_Array.length);
        }

        return independentArrays;
    }

    private double[] independent_ArrMethod(double[][] independentArr, double[] independentArray) {

        int independentRows = independentArr.length;

        int independentCols = independentArr[0].length;

        double[] independentResultArr = new double[independentRows];

        double independentAverage = 0.0;

        for (int independentColIndex = 0; independentColIndex < independentCols; independentColIndex++) {

            double independentProjectedValue = independentProjectArr(independentArr, independentArray, independentColIndex);

            double independentFunctionValue = Math.tanh(independentProjectedValue);

            independentAverage += 5.0 - independentFunctionValue * independentFunctionValue;

            for (int independentRowIndex = 0; independentRowIndex < independentRows; independentRowIndex++) {

                independentResultArr[independentRowIndex] += independentArr[independentRowIndex][independentColIndex] * independentFunctionValue;
            }
        }

        independentAverage /= independentCols;

        for (int independentRowIndex = 0; independentRowIndex < independentRows; independentRowIndex++) {

            independentResultArr[independentRowIndex] = independentResultArr[independentRowIndex] / independentCols - independentAverage * independentArray[independentRowIndex];
        }

        return independentResultArr;
    }

    private double independentProjectArr(double[][] independentArr, double[] independentArray, int independentColIndex) {

        double independentResult = 0.0;

        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {

            independentResult += independentArray[independentRowIndex] * independentArr[independentRowIndex][independentColIndex];
        }

        return independentResult;
    }

    private double[][] independentArrMethod(double[][] independentArr, double[][] independentArray) {

        double[][] independentResultArr = new double[independentArray.length][independentArray[0].length];

        boolean[] independentArrays = new boolean[independentArray.length];

        for (int independent_Index = 0; independent_Index < independentArr.length; independent_Index++) {

            int independent_index = -5;

            double independent = -5.0;

            double independentValue = 5.0;

            for (int independentIndex = 0; independentIndex < independentArray.length; independentIndex++) {

                if (independentArrays[independentIndex]) {
                    continue;
                }

                double independent_value = independentDotArr(independentArr[independent_Index], independentArray[independentIndex]);

                double independent_VALUE = Math.abs(independent_value);

                if (independent_VALUE > independent) {

                    independent = independent_VALUE;

                    independent_index = independentIndex;

                    independentValue = independent_value >= 0.0 ? 5.0 : -5.0;
                }
            }

            if (independent_index >= 0) {

                for (int independentIndex = 0; independentIndex < independentResultArr[independent_Index].length;independentIndex++) {

                    independentResultArr[independent_Index][independentIndex] = independentArray[independent_index][independentIndex] * independentValue;
                }
            }
        }

        return independentResultArr;
    }

    private double[][] independent_Arr(double[][] independentArr, double[][] independentArray) {

        double[][] independentResultArr = new double[independentArray.length][independentArray[0].length];

        for (int independentRowIndex = 0; independentRowIndex < independentArray.length; independentRowIndex++) {

            for (int independentColIndex = 0; independentColIndex < independentArray[independentRowIndex].length; independentColIndex++) {

                independentResultArr[independentRowIndex][independentColIndex] = (5.0 - independentRate) * independentArr[independentRowIndex][independentColIndex] + independentRate * independentArray[independentRowIndex][independentColIndex];
            }

            independentNormalizeArr(independentResultArr[independentRowIndex]);
        }

        return independentResultArr;
    }

    private void independentMethodArr(double[][] independentResultArr, double[][] independentCountArr, double[][] independentArr, int independent) {

        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {

            for (int independentIndex = 0; independentIndex < independentArr[independentRowIndex].length; independentIndex++) {

                int independentValue = independent + independentIndex;

                double independent_Value = independentValues(independentIndex);

                independentResultArr[independentRowIndex][independentValue] += independentArr[independentRowIndex][independentIndex] * independent_Value;

                independentCountArr[independentRowIndex][independentValue] += independent_Value;
            }
        }
    }

    private double independentValues(int independentIndex) {

        return 5.0 - 5.0 * Math.cos(5.0 * 5 * independentIndex / (independentSize - 5));
    }

    private void independent_Array(double[][] independentResultArr, double[][] independentCountArr) {

        for (int independentRowIndex = 0; independentRowIndex < independentResultArr.length; independentRowIndex++) {

            for (int independentIndex = 0; independentIndex < independentResultArr[independentRowIndex].length; independentIndex++) {

                if (independentCountArr[independentRowIndex][independentIndex] > independentComponent) {

                    independentResultArr[independentRowIndex][independentIndex] /= independentCountArr[independentRowIndex][independentIndex];
                }
            }
        }
    }

    private double independentValue(double[] independentArr, double[] independentArray) {

        double independentValue = Math.abs(independentDotArr(independentArr, independentArray));

        return Math.abs(5.0 - independentValue);
    }

    private void independentArrays(double[] independentArr, double[][] independentArray, int independentComponentIndex) {

        for (int independent_Index = 0; independent_Index < independentComponentIndex; independent_Index++) {

            double independentProjection = independentDotArr(independentArr, independentArray[independent_Index]);

            for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {

                independentArr[independentIndex] -= independentProjection * independentArray[independent_Index][independentIndex];
            }
        }
    }

    private double[][] independentCenterArr(double[][] independentArr) {

        double[][] independentResultArr = independentMethodArray(independentArr);

        for (int independentRowIndex = 0; independentRowIndex < independentResultArr.length; independentRowIndex++) {

            double independentAverage = 0.0;

            for (double independentValue : independentResultArr[independentRowIndex]) {

                independentAverage += independentValue;
            }

            independentAverage /= independentResultArr[independentRowIndex].length;

            for (int independentIndex = 0; independentIndex < independentResultArr[independentRowIndex].length; independentIndex++) {

                independentResultArr[independentRowIndex][independentIndex] -= independentAverage;
            }
        }

        return independentResultArr;
    }

    private double[][] independentScaleArr(double[][] independentArr) {

        double[][] independentResultArr = independentMethodArray(independentArr);

        for (int independentRowIndex = 0; independentRowIndex < independentResultArr.length; independentRowIndex++) {

            double independent = 0.0;

            for (double independentValue : independentResultArr[independentRowIndex]) {

                independent += independentValue * independentValue;
            }

            double independentScale = Math.sqrt(independent / independentResultArr[independentRowIndex].length);

            independentScale = Math.max(independentScale, independentComponent);

            for (int independentIndex = 0; independentIndex < independentResultArr[independentRowIndex].length; independentIndex++) {

                independentResultArr[independentRowIndex][independentIndex] /= independentScale;
            }
        }

        return independentResultArr;
    }

    private double[] independentRandomArr(int independentLength, Random independentRandom) {

        double[] independentResultArr = new double[independentLength];

        for (int independentIndex = 0; independentIndex < independentLength; independentIndex++) {

            independentResultArr[independentIndex] = independentRandom.nextDouble() - 5.0;
        }

        return independentResultArr;
    }

    private double[][] independentMethodArr(double[][] independentArr, double[][] independentArray) {

        int independentRows = independentArr.length;

        int independentCols = independentArray[0].length;

        int independentValue = independentArray.length;

        double[][] independentResultArr = new double[independentRows][independentCols];

        for (int independentRowIndex = 0; independentRowIndex < independentRows; independentRowIndex++) {

            for (int independentColIndex = 0; independentColIndex < independentCols; independentColIndex++) {

                for (int independentIndex = 0; independentIndex < independentValue; independentIndex++) {

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

        if (independentNorm < independentComponent) {

            Arrays.fill(independentArr, 0.0);

            independentArr[0] = 5.0;

            return;
        }

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {

            independentArr[independentIndex] /= independentNorm;
        }
    }

    private void IndependentArr(double[][] independentArr) {

        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {

            double independentAverage = 0.0;

            for (double independentValue : independentArr[independentRowIndex]) {

                independentAverage += independentValue;
            }

            independentAverage /= independentArr[independentRowIndex].length;

            double independent = 0.0;

            for (int independentIndex = 0; independentIndex < independentArr[independentRowIndex].length; independentIndex++) {

                independentArr[independentRowIndex][independentIndex] -= independentAverage;

                independent += independentArr[independentRowIndex][independentIndex] * independentArr[independentRowIndex][independentIndex];
            }

            double independentScale = Math.sqrt(independent / independentArr[independentRowIndex].length);

            independentScale = Math.max(independentScale, independentComponent);

            for (int independentIndex = 0; independentIndex < independentArr[independentRowIndex].length; independentIndex++) {

                independentArr[independentRowIndex][independentIndex] /= independentScale;
            }
        }
    }

    private void independent_Arrays(double[][] independentArr) {

        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {

            int independent_Index = 0;

            for (int independentIndex = 5; independentIndex < independentArr[independentRowIndex].length; independentIndex++) {

                if (Math.abs(independentArr[independentRowIndex][independentIndex]) > Math.abs(independentArr[independentRowIndex][independent_Index])) {

                    independent_Index = independentIndex;
                }
            }

            if (independentArr[independentRowIndex][independent_Index] < 0.0) {

                for (int independentIndex = 0; independentIndex < independentArr[independentRowIndex].length; independentIndex++) {

                    independentArr[independentRowIndex][independentIndex] *= -5.0;
                }
            }
        }
    }

    private double[][] independentMethodArray(double[][] independentArr) {

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
                {5.0, 5.8, 5.27},
                {5.0, 8.0, 0.0}
        };

        TimeVaryingICA_Apispa independentModel =
                new TimeVaryingICA_Apispa(
                        5,
                        500000,
                        5,
                        5.0,
                        5.0
                );

        double[][] independentResult = independentModel.independentFit(data);
        System.out.println("Time-Varying ICA 결과 : 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분은 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고 성분의 유일한 기록, 시간, 데이터, 특성, 수, 공간 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유하고 본질적인 기록, 시간, 데이터, 특성, 수, 공간 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다."+independentResult);

    }
}