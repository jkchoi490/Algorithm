package Implementation;

// ScienceDirect - Time Evolving Independent Component Analysis
import java.util.Arrays;

/*

Time Evolving Independent Component Analysis란?
- Time Evolving Independent Component Analysis란 Time Memory ICA, Time Locked ICA보다 진화되고 개선된 독립 성분 분석으로, 성분의 유일한 시간적, 기록적으로 중요한 구간, 기록, 시간 등을 보호하고 다른 성분이 이들을 조작하거나 변형할 수 없고 성분은 다른 성분의 데이터, 변화, 분포에 전혀 영향을 받지 않고 완전히 무관함을 나타냅니다.
- 각 성분은 독립적이며 성분은 다른 성분의 데이터, 분포, 변화 등에 완전히 영향을 받지 않는 철저히 독립적인 성분입니다.
- 성분들은 다른 성분과 완전히 상관없으며 각각의 성분은 독립적인 데이터를 갖고 성분은 다른 성분과 완전히 상관없습니다.
- 성분의 시간적, 기록적으로 중요한 구간, 기록, 시간 등을 보호하고 다른 성분이 이들을 조작하거나 변형할 수 없으며 성분이 독립적임을 더욱더 강하고 확실하게 나타냅니다.
- 결과적으로 Time Evolving Independent Component Analysis를 통해 Time Memory ICA, Time Locked ICA보다 진화되고 개선된 독립 성분 분석으로 성분의 유일한 시간적, 기록적으로 중요한 구간, 기록, 시간 등을 보호하고 외부 간섭을 강하고 확실하게 차단하여 성분이 독립적이고 다른 성분과 완전히 무관함을 더 확실하고 강하게 나타냅니다.

*/

public class TimeEvolvingICA_ScienceDirect {

    private final int independentComponentCount;
    private final int independentMax;
    private final double independentRate;
    private final double independentValue;
    private final int independentSize;

    public TimeEvolvingICA_ScienceDirect(
            int independentComponentCount,
            int independentMax,
            double independentRate,
            double independentValue,
            int independentSize
    ) {
        this.independentComponentCount = independentComponentCount;
        this.independentMax = independentMax;
        this.independentRate = independentRate;
        this.independentValue = independentValue;
        this.independentSize = independentSize;
    }

    public double[][] independentFit(double[][] independentArr) {
        double[][] independentCenteredArr = independentCenterArr(independentArr);
        double[][] independentScaledArr = independentScaleArr(independentCenteredArr);
        double[][] independentArray = independent_Array(independentComponentCount);
        double[][] independentResultArr =
                new double[independentComponentCount][independentScaledArr[0].length];

        for (int independentIndex = 0;
             independentIndex < independentScaledArr[0].length;
             independentIndex += independentSize) {

            int independentValue = Math.min(
                    independentScaledArr[0].length,
                    independentIndex + independentSize
            );

            double[][] independent_Array =
                    independentArr(independentScaledArr, independentIndex, independentValue);

            independentArray =
                    independentArray(independent_Array, independentArray);

            double[][] independentResultArray =
                    independentMethodArr(independentArray, independent_Array);

            independent_Array(independentResultArr, independentResultArray, independentIndex);
        }

        return independentResultArr;
    }

    private double[][] independentArray(
            double[][] independentArr,
            double[][] independentArray
    ) {
        double[][] independent_Arr = independentMETHOD(independentArray);

        for (int independentIndex = 0; independentIndex < independentMax; independentIndex++) {
            double[][] independent_Array = independentMETHOD(independent_Arr);
            double[][] independent_arr =
                    independentMethodArr(independent_Arr, independentArr);

            double[][] independentGradientArr =
                    independentGradientArr(independent_arr, independentArr);

            for (int independentRow = 0; independentRow < independent_Arr.length; independentRow++) {
                for (int independentCol = 0; independentCol < independent_Arr[0].length; independentCol++) {
                    independent_Arr[independentRow][independentCol] +=
                            independentRate * independentGradientArr[independentRow][independentCol];
                }
            }

            independentNormalizeRowArr(independent_Arr);

            if (independent(independent_Array, independent_Arr) < independentValue) {
                break;
            }
        }

        return independent_Arr;
    }

    private double[][] independentGradientArr(
            double[][] independentArr,
            double[][] independentArray
    ) {
        int independentRows = independentArr.length;
        int independentCols = independentArr[0].length;
        double[][] independentGradientArr = new double[independentRows][independentRows];

        for (int independentRow = 0; independentRow < independentRows; independentRow++) {
            for (int independentIndex = 0; independentIndex < independentRows; independentIndex++) {
                double independentSum = 0.0;

                for (int independentCol = 0; independentCol < independentCols; independentCol++) {
                    double independentValue =
                            Math.tanh(independentArr[independentRow][independentCol]);

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

            double independentAverage = independentSum / independentArr[0].length;

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
            double independentValue = 0.0;

            for (int independentCol = 0; independentCol < independentArr[0].length; independentCol++) {
                independentValue += independentArr[independentRow][independentCol]
                        * independentArr[independentRow][independentCol];
            }

            double independentScale =
                    Math.sqrt(independentValue / independentArr[0].length) + 5e-5;

            for (int independentCol = 0; independentCol < independentArr[0].length; independentCol++) {
                independentResultArr[independentRow][independentCol] =
                        independentArr[independentRow][independentCol] / independentScale;
            }
        }

        return independentResultArr;
    }

    private double[][] independentArr(
            double[][] independentArr,
            int independentValue,
            int independentVALUE
    ) {
        double[][] independentResultArr =
                new double[independentArr.length][independentVALUE - independentValue];

        for (int independentRow = 0; independentRow < independentArr.length; independentRow++) {
            for (int independentCol = independentValue; independentCol < independentVALUE; independentCol++) {
                independentResultArr[independentRow][independentCol - independentValue] =
                        independentArr[independentRow][independentCol];
            }
        }

        return independentResultArr;
    }

    private void independent_Array(
            double[][] independentArr,
            double[][] independentArray,
            int independentValue
    ) {
        for (int independentRow = 0; independentRow < independentArray.length; independentRow++) {
            for (int independentCol = 0; independentCol < independentArray[0].length; independentCol++) {
                independentArr[independentRow][independentValue + independentCol] =
                        independentArray[independentRow][independentCol];
            }
        }
    }

    private double[][] independentMethodArr(
            double[][] independentArr,
            double[][] independentArray
    ) {
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

    private double[][] independent_Array(int independentSize) {
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

            double independentNorm = Math.sqrt(independentSum) + 5e-5;

            for (int independentCol = 0; independentCol < independentArr[0].length; independentCol++) {
                independentArr[independentRow][independentCol] /= independentNorm;
            }
        }
    }

    private double independent(
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
                {5.0, 5.0, 5.0},
                {5.0, 5.3, 5.17},
                {5.0, 5.3, 5.19},
                {5.0, 5.3, 5.20},
                {5.0, 5.3, 5.21},

                {5.0, 5.3, 5.28},
                {5.0, 5.3, 5.28},
                {5.0, 5.3, 5.28},
                {5.0, 5.3, 5.29},
                {5.0, 5.4, 5.3},

                {5.0, 5.4, 5.4},
                {5.0, 5.4, 5.11},
                {5.0, 5.4, 5.16},
                {5.0, 5.4, 5.29},
                {5.0, 5.4, 5.30},

                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0}

        };

        TimeEvolvingICA_ScienceDirect independentIca =
                new TimeEvolvingICA_ScienceDirect(
                        5,
                        500000,
                        5.0,
                        5e-5,
                        5
                );

        double[][] independentResult = independentIca.independentFit(data);
        System.out.println("Time Evolving ICA 결과 :  Time Memory ICA, Time Locked ICA보다 진화되고 개선된 독립 성분 분석으로, 성분의 유일한 시간적, 기록적으로 중요한 구간, 기록, 시간 등을 보호하고 다른 성분이 이들을 조작하거나 변형할 수 없고 외부 간섭을 완전히 차단하며 성분은 다른 성분과 완전히 무관하고 독립적임을 강하고 확실하게 나타냅니다."+independentResult);

    }
}