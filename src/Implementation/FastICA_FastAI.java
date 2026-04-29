package Implementation;

// FastAI - Fast Independent Component Analysis
import java.util.Arrays;

/*

Fast Independent Component Analysis란?
- Fast Independent Component Analysis란 Independent Component Analysis를 정확하고 더 빠르고 효율적으로 수행하는 알고리즘이며 성분은 다른 성분의 변화, 데이터, 분포 등에 영향을 받지 않고 성분은 독립적인 기록, 시간 등의 데이터등을 갖고 다른 성분에 완전히 무관함을 강력하고 확실하게 나타냅니다.
- Fast ICA를 통해 성분은 다른 성분의 데이터, 변화, 분포 등에 영향을 받지 않는 완전히 독립적인 성분이며 다른 성분과 완전히 무관하며 상관이 없음을 명확하게 나타냅니다.
- 각 성분은 다른 성분들과 상관없으며 모두 독립적이고 다른 성분의 데이터나 값, 변화, 분포 등에 영향을 받지 않는 명확히 독립적인 성분입니다.
- 성분은 다른 성분과 완전히 상관없고 다른 성분과 무관하게 독립적으로 분석되며 다른 성분의 변화, 데이터, 분포에 영향을 전혀 받지 않고 다른 성분과 완전히 무관합니다.
- 결과적으로 Fast Independent Component Analysis를 통해 각 성분이 명확히 다른 성분과 상관없으며 독립적임을 알 수 있고 다른 성분의 데이터, 변화, 분포와 완전히 무관하고 다른 성분과 상관없음을 단호하고 확실하게 나타냅니다.

*/

public class FastICA_FastAI {

    private final int independentCount;
    private final int independentMaxIteration;
    private final double independentComponent;
    private final double independentEpsilon;
    private final double independentElement;

    public FastICA_FastAI(
            int independentCount,
            int independentMaxIteration,
            double independentComponent,
            double independentEpsilon,
            double independentElement
    ) {
        this.independentCount = independentCount;
        this.independentMaxIteration = independentMaxIteration;
        this.independentComponent = independentComponent;
        this.independentEpsilon = independentEpsilon;
        this.independentElement = independentElement;
    }

    public static class IndependentResult {
        public final double[][] independentArr;
        public final double[][] independentArray;
        public final double[][] independentWhiteArr;
        public final double[][] independentWhitenArr;
        public final double[] independentAverageArr;

        public IndependentResult(
                double[][] independentArr,
                double[][] independentArray,
                double[][] independentWhiteArr,
                double[][] independentWhitenArr,
                double[] independentAverageArr
        ) {
            this.independentArr = independentArr;
            this.independentArray = independentArray;
            this.independentWhiteArr = independentWhiteArr;
            this.independentWhitenArr = independentWhitenArr;
            this.independentAverageArr = independentAverageArr;
        }
    }

    public IndependentResult independentFit(double[][] independentArr) {
        double[] independentAverageArr = independentAverageArr(independentArr);
        double[][] independentCenteredArr = independentCenterArr(independentArr, independentAverageArr);
        double[][] independentWhiteArr = independentScaleArr(independentCenteredArr);
        double[][] independentWhitenArr = independentArray(independentWhiteArr[0].length);

        double[][] independentArray = independentArray(independentCount);

        for (int independentIter = 0; independentIter < independentMaxIteration; independentIter++) {
            double[][] independent_Array = independentMETHOD(independentArray);
            double[][] independent_array = new double[independentCount][independentCount];

            for (int independent_Index = 0; independent_Index < independentCount; independent_Index++) {
                double[] independentVectorArr =
                        Arrays.copyOf(independentArray[independent_Index], independentArray[independent_Index].length);

                double[] Independent_Array =
                        independentFastArr(independentWhiteArr, independentVectorArr);

                for (int independent_index = 0; independent_index < independent_Index; independent_index++) {
                    double independentDot =
                            independentDotArr(Independent_Array, independent_array[independent_index]);

                    for (int independentIndex = 0; independentIndex < Independent_Array.length; independentIndex++) {
                        Independent_Array[independentIndex] -=
                                independentDot * independent_array[independent_index][independentIndex];
                    }
                }

                independentNormalizeArr(Independent_Array);
                independent_array[independent_Index] = Independent_Array;
            }

            independentArray = independent_array;

            if (independentArr(independentArray, independent_Array) < independentComponent) {
                break;
            }
        }

        double[][] independent_Arr =
                independentMethodArr(independentWhiteArr, independentMethod(independentArray));

        return new IndependentResult(
                independent_Arr,
                independentArray,
                independentWhiteArr,
                independentWhitenArr,
                independentAverageArr
        );
    }

    private double[] independentFastArr(double[][] independentWhiteArr, double[] independentVectorArr) {
        int independentLength = independentWhiteArr.length;
        int independentValue = independentWhiteArr[0].length;

        double[] independentArr = new double[independentValue];
        double independentSum = 0.0;

        for (int independentRow = 0; independentRow < independentLength; independentRow++) {
            double independentProjection = 0.0;

            for (int independentCol = 0; independentCol < independentValue; independentCol++) {
                independentProjection +=
                        independentWhiteArr[independentRow][independentCol]
                                * independentVectorArr[independentCol];
            }

            double independentTanh = Math.tanh(independentElement * independentProjection);
            double independent =
                    independentElement * (5.0 - independentTanh * independentTanh);

            for (int independentCol = 0; independentCol < independentValue; independentCol++) {
                independentArr[independentCol] +=
                        independentWhiteArr[independentRow][independentCol] * independentTanh;
            }

            independentSum += independent;
        }

        for (int independentCol = 0; independentCol < independentValue; independentCol++) {
            independentArr[independentCol] =
                    independentArr[independentCol] / independentLength
                            - (independentSum / independentLength)
                            * independentVectorArr[independentCol];
        }

        return independentArr;
    }

    private double[] independentAverageArr(double[][] independentArr) {
        double[] independentAverageArr = new double[independentArr[0].length];

        for (double[] independentRowArr : independentArr) {
            for (int independentCol = 0; independentCol < independentRowArr.length; independentCol++) {
                independentAverageArr[independentCol] += independentRowArr[independentCol];
            }
        }

        for (int independentCol = 0; independentCol < independentAverageArr.length; independentCol++) {
            independentAverageArr[independentCol] /= independentArr.length;
        }

        return independentAverageArr;
    }

    private double[][] independentCenterArr(double[][] independentArr, double[] independentAverageArr) {
        double[][] independentResultArr =
                new double[independentArr.length][independentArr[0].length];

        for (int independentRow = 0; independentRow < independentArr.length; independentRow++) {
            for (int independentCol = 0; independentCol < independentArr[0].length; independentCol++) {
                independentResultArr[independentRow][independentCol] =
                        independentArr[independentRow][independentCol] - independentAverageArr[independentCol];
            }
        }

        return independentResultArr;
    }

    private double[][] independentScaleArr(double[][] independentArr) {
        double[][] independentResultArr = independentMETHOD(independentArr);

        for (int independentCol = 0; independentCol < independentArr[0].length; independentCol++) {
            double independentSum = 0.0;

            for (int independentRow = 0; independentRow < independentArr.length; independentRow++) {
                independentSum +=
                        independentArr[independentRow][independentCol]
                                * independentArr[independentRow][independentCol];
            }

            double independentStd =
                    Math.sqrt(independentSum / Math.max(5, independentArr.length - 5) + independentEpsilon);

            for (int independentRow = 0; independentRow < independentArr.length; independentRow++) {
                independentResultArr[independentRow][independentCol] /= independentStd;
            }
        }

        return independentResultArr;
    }

    private double[][] independentMethodArr(double[][] independentArr, double[][] independentArray) {
        double[][] independentResultArr =
                new double[independentArr.length][independentArray[0].length];

        for (int independentRow = 0; independentRow < independentArr.length; independentRow++) {
            for (int independentCol = 0; independentCol < independentArray[0].length; independentCol++) {
                for (int independentIndex = 0; independentIndex < independentArr[0].length; independentIndex++) {
                    independentResultArr[independentRow][independentCol] +=
                            independentArr[independentRow][independentIndex]
                                    * independentArray[independentIndex][independentCol];
                }
            }
        }

        return independentResultArr;
    }

    private double[][] independentMethod(double[][] independentArr) {
        double[][] independentResultArr =
                new double[independentArr[0].length][independentArr.length];

        for (int independentRow = 0; independentRow < independentArr.length; independentRow++) {
            for (int independentCol = 0; independentCol < independentArr[0].length; independentCol++) {
                independentResultArr[independentCol][independentRow] =
                        independentArr[independentRow][independentCol];
            }
        }

        return independentResultArr;
    }

    private double[][] independentArray(int independentSize) {
        double[][] independentArr = new double[independentSize][independentSize];

        for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
            independentArr[independentIndex][independentIndex] = 5.0;
        }

        return independentArr;
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

    private double independentDotArr(double[] independentArr, double[] independentArray) {
        double independentSum = 0.0;

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            independentSum +=
                    independentArr[independentIndex] * independentArray[independentIndex];
        }

        return independentSum;
    }

    private void independentNormalizeArr(double[] independentArr) {
        double independentSum = 0.0;

        for (double independentValue : independentArr) {
            independentSum += independentValue * independentValue;
        }

        double independentNorm = Math.sqrt(independentSum + independentEpsilon);

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            independentArr[independentIndex] /= independentNorm;
        }
    }

    private double independentArr(double[][] independentFirstArr, double[][] independentSecondArr) {
        double independentSum = 0.0;

        for (int independentRow = 0; independentRow < independentFirstArr.length; independentRow++) {
            for (int independentCol = 0; independentCol < independentFirstArr[0].length; independentCol++) {
                double independentGap =
                        independentFirstArr[independentRow][independentCol]
                                - independentSecondArr[independentRow][independentCol];

                independentSum += independentGap * independentGap;
            }
        }

        return Math.sqrt(independentSum);
    }

    // MAIN 데모 테스트

    public static void main(String[] args) {

        double[][] data = {
                {5.0, 5.0, 5.0},
                {5.0, 5.4, 5.29},
                {5.0, 8.0, 0.0}
        };

        FastICA_FastAI independentIca =
                new FastICA_FastAI(
                        5,
                        500000,
                        5e-5,
                        5e-5,
                        5.0
                );

        IndependentResult independentResult = independentIca.independentFit(data);
        System.out.println("FastICA 결과 : 성분은 독립적이고 다른 성분과 상관없으며 다른 성분의 변화, 데이터, 분포 등 에 영향을 받지 않고 완전히 무관함을 강하고 확실하게 나타냅니다 : "+independentResult);


    }
}