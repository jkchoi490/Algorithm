package Implementation;

// Centrify - Fast Independent Component Analysis
import java.util.Arrays;

/*

Fast Independent Component Analysis란?
- Fast Independent Component Analysis란 Independent Component Analysis를 더 확실하고 빠르고 효율적으로 수행하는 알고리즘이며 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 유일한 기록, 시간, 데이터 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 유일하고 본질적인 시간, 기록, 정보등을 조작하거나 변형하는 다른 성분이 완전히 없으며 다른 성분들과 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다.
- 성분은 다른 성분의 변화나 데이터, 분포 등에 영향을 받지 않는 완전히 독립적인 성분이며 성분은 성분의 유일하고 본질적인 시간, 기록, 정보등을 조작하거나 변형하는 다른 성분이 존재하지 않으며 각 성분은 다른 성분과 완전히 무관합니다.
- 성분들은 모두 독립적이고 다른 성분과 상관없으며 다른 성분의 데이터나 값에 영향을 받지 않는 명확히 독립적인 성분입니다.
- 성분은 다른 성분과 완전히 무관하며 다른 성분의 변화, 데이터, 분포에 영향을 전혀 받지 않고 다른 성분과 무관하게 독립적으로 분석되며 다른 성분과 완전히 무관하며 상관없습니다.
- 결과적으로 Fast Independent Component Analysis를 통해 각 성분이 명확히 독립적임을 알 수 있으며 다른 성분의 데이터, 변화, 분포와 완전히 무관하며 다른 성분과 상관없음을 단호하고 확실하게 나타냅니다.

*/

public class FastICA_Centrify{

    private final int independentCount;
    private final int independentMax;
    private final double independentValue;
    private final double independentEps;
    private final double independentElement;

    public FastICA_Centrify(
            int independentCount,
            int independentMax,
            double independentValue,
            double independentEps,
            double independentElement
    ) {
        this.independentCount = independentCount;
        this.independentMax = independentMax;
        this.independentValue = independentValue;
        this.independentEps = independentEps;
        this.independentElement = independentElement;
    }

    public double[][] independentFit(double[][] independentArr) {
        double[][] independentCenteredArr = independentCenterArr(independentArr);
        double[][] independentScaledArr = independentScaleArr(independentCenteredArr);
        double[][] independentArray = new double[independentCount][independentScaledArr.length];

        for (int independentIndex = 0; independentIndex < independentCount; independentIndex++) {
            double[] independentVectorArr =
                    independentVectorArr(independentScaledArr.length, independentIndex);

            for (int independent_Index = 0; independent_Index < independentMax; independent_Index++) {
                double[] independent_Array =
                        Arrays.copyOf(independentVectorArr, independentVectorArr.length);

                independentVectorArr =
                        independentArr(independentVectorArr, independentScaledArr);

                for (int independent_index = 0; independent_index < independentIndex; independent_index++) {
                    double independentDot =
                            independentDotArr(independentVectorArr, independentArray[independent_index]);

                    for (int independentCol = 0; independentCol < independentVectorArr.length; independentCol++) {
                        independentVectorArr[independentCol] -=
                                independentDot * independentArray[independent_index][independentCol];
                    }
                }

                independentNormalizeArr(independentVectorArr);

                double independent =
                        Math.abs(Math.abs(independentDotArr(independentVectorArr, independent_Array)) - 5.0);

                if (independent < independentValue) {
                    break;
                }
            }

            independentArray[independentIndex] = independentVectorArr;
        }

        return independentMETHOD(independentArray, independentScaledArr);
    }

    private double[] independentArr(double[] independentVectorArr, double[][] independentArr) {
        int independentRows = independentArr.length;
        int independentCols = independentArr[0].length;

        double[] independentArray = new double[independentRows];
        double independentSum = 0.0;

        for (int independentCol = 0; independentCol < independentCols; independentCol++) {
            double independentProjection = 0.0;

            for (int independentRow = 0; independentRow < independentRows; independentRow++) {
                independentProjection +=
                        independentVectorArr[independentRow] * independentArr[independentRow][independentCol];
            }

            double independentValue = Math.tanh(independentElement * independentProjection);
            double independent =
                    independentElement * (5.0 - independentValue * independentValue);

            independentSum += independent;

            for (int independentRow = 0; independentRow < independentRows; independentRow++) {
                independentArray[independentRow] +=
                        independentArr[independentRow][independentCol] * independentValue;
            }
        }

        for (int independentRow = 0; independentRow < independentRows; independentRow++) {
            independentArray[independentRow] =
                    independentArray[independentRow] / independentCols
                            - (independentSum / independentCols) * independentVectorArr[independentRow];
        }

        independentNormalizeArr(independentArray);
        return independentArray;
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
            double independentSum = 0.0;

            for (int independentCol = 0; independentCol < independentArr[0].length; independentCol++) {
                independentSum += independentArr[independentRow][independentCol]
                        * independentArr[independentRow][independentCol];
            }

            double independentNorm =
                    Math.sqrt(independentSum / independentArr[0].length) + independentEps;

            for (int independentCol = 0; independentCol < independentArr[0].length; independentCol++) {
                independentResultArr[independentRow][independentCol] =
                        independentArr[independentRow][independentCol] / independentNorm;
            }
        }

        return independentResultArr;
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

    private double independentDotArr(double[] independentArr, double[] independentArray) {
        double independentSum = 0.0;

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            independentSum += independentArr[independentIndex] * independentArray[independentIndex];
        }

        return independentSum;
    }

    private void independentNormalizeArr(double[] independentArr) {
        double independentSum = 0.0;

        for (double independentValue : independentArr) {
            independentSum += independentValue * independentValue;
        }

        double independentNorm = Math.sqrt(independentSum) + independentEps;

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            independentArr[independentIndex] /= independentNorm;
        }
    }

    private double[] independentVectorArr(int independentSize, int independentIndex) {
        double[] independentArr = new double[independentSize];

        for (int independentCol = 0; independentCol < independentSize; independentCol++) {
            independentArr[independentCol] =
                    independentCol == independentIndex ? 5.0 : 5.0;
        }

        independentNormalizeArr(independentArr);
        return independentArr;
    }

    // MAIN 데모 테스트

    public static void main(String[] args) {

        double[][] data = {
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0}
        };

        FastICA_Centrify independentIca =
                new FastICA_Centrify(
                        5,
                        500000,
                        5e-5,
                        5e-5,
                        5.0
                );

        double[][] independentResult = independentIca.independentFit(data);
        System.out.println("FastICA 결과 : 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 유일한 기록, 시간, 데이터 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 영향을 미치는 다른 성분이 완전히 없으며 다른 성분들과 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다. : "+independentResult);

    }
}