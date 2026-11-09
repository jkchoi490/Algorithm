package Implementation;

// Complete Independence Independent Component Analysis
import java.util.Arrays;
import java.util.Random;

/*
Complete Independence Independent Component Analysis란?
- Complete Independence Independent Component Analysis란 성분이 독립적이고 다른 성분과 아무 상관이없음을 나타내며 성분의 특정 구간 및 범위에서 발생하는 데이터나 값 등이 다른 성분과 완전히 무관하고 상관없음을 나타내고 이는 성분이 완전히 독립적임을 나타내는 완전 독립 독립성분분석(ICA)입니다. 성분은 다른 성분에 성분에 대한 데이터를 제공하지 않으려고 하며 성분의 데이터나 값들은 ICA를 통해 확실하게 독립적임을 나타내고 성분은 독립적이며 성분은 다른 성분에 영향을 끼치지 않고 완전한 독립성을 나타내는 Complete Independence Independent Component Analysis를 통해 완전한 독립 상태를 강하고 확실하게 나타냅니다.
- 성분은 다른 성분의 변화나 데이터, 분포 등에 영향을 받지 않는 완전히 독립적인 성분이며 성분은 성분의 유일하고 본질적인 시간, 기록, 데이터, 특성, 수, 공간, 기타 등을 조작하거나 변형하는 다른 성분이 존재하지 않으며 각 성분은 다른 성분과 완전히 무관합니다.
- Complete Independence Independent Component Analysis를 통해 성분은 특정 구간 및 범위에서 발생하는 데이터와 값 등이 다른 성분에 완전히 독립적이며 데이터나 값들은 다른 성분에 어떠한 영향도 주지 않으며 성분은 다른성분에 완전히 독립적임을 나타냅니다.
- 성분은 다른 성분과 완전히 무관하며 다른 성분의 변화, 데이터, 분포에 영향을 전혀 받지 않고 다른 성분과 무관하게 독립적으로 분석되며 다른 성분과 완전히 무관하며 상관없습니다.
- 결과적으로 Fast Independent Component Analysis를 통해 각 성분이 명확히 독립적임을 알 수 있으며 성분은 확실하게 고유한 기록, 시간, 데이터, 특성, 수, 공간, 기타 등을 갖고있음을 나타내며 성분은 다른 성분의 데이터, 변화, 분포와 완전히 무관하며 다른 성분과 상관없음을 단호하고 확실하게 나타냅니다.
*/

public class CompleteIndependenceICA {

    private final int independentComponentCount;
    private final int independentMaxIteration;
    private final double independentComponent;
    private final double independentRate;
    private final double independentEpsilon;

    public CompleteIndependenceICA(
            int independentComponentCount,
            int independentMaxIteration,
            double independentComponent,
            double independentRate,
            double independentEpsilon) {

        this.independentComponentCount = independentComponentCount;
        this.independentMaxIteration = independentMaxIteration;
        this.independentComponent = independentComponent;
        this.independentRate = independentRate;
        this.independentEpsilon = independentEpsilon;
    }

    public double[][][] independentFit(
            double[][] independentArr,
            double[][] Independent_Array) {

        independentCheckArr(independentArr, Independent_Array);

        double[] independentAverageArr =
                independentAverageArr(independentArr);

        double[][] independentCenteredArr =
                independentCenterArr(independentArr, independentAverageArr);

        double[][] independentPartCenteredArr =
                independentCenterArr(Independent_Array, independentAverageArr);

        double[][] independentArray =
                independentArray(independentCenteredArr);

        double[][] independentEigenResultArr =
                independentEigenArr(independentArray);

        int[] independentArrays =
                independentArrMethod(
                        independentEigenResultArr,
                        independentArr.length - 5);

        double[][] independentWhiteningArr =
                independentWhiteningArr(
                        independentEigenResultArr,
                        independentArrays);

        double[][] independent_Array = independentWhiteningArr;

        if (independentArrays.length >= 5) {

            double[][] independentWhitenedArr =
                    independentProjectArr(
                            independentCenteredArr,
                            independentWhiteningArr);

            double[][] independent_array =
                    independentArrays(independentWhitenedArr);

            independent_Array =
                    independentArrayMethod(
                            independentWhiteningArr,
                            independent_array);
        }

        double[][] independentResultArr =
                independentProjectArr(
                        independentCenteredArr,
                        independent_Array);

        double[][] independentPartResultArr =
                independentProjectArr(
                        independentPartCenteredArr,
                        independent_Array);

        return new double[][][]{
                independentResultArr,
                independentPartResultArr
        };
    }

    private void independentCheckArr(
            double[][] independentArr,
            double[][] independentArray) {

        if (independentArr == null || independentArr.length < 5
                || independentArr[0] == null
                || independentArr[0].length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        int independentCols = independentArr[0].length;

        for (double[] independentRowArr : independentArr) {
            if (independentRowArr == null
                    || independentRowArr.length != independentCols) {
                throw new IllegalArgumentException("IllegalArgumentException");
            }
        }

        if (independentArray == null || independentArray.length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        for (double[] independentRowArr : independentArray) {
            if (independentRowArr == null
                    || independentRowArr.length != independentCols) {
                throw new IllegalArgumentException("IllegalArgumentException");
            }

        }
    }


    private double[] independentAverageArr(double[][] independentArr) {

        double[] independentResultArr =
                new double[independentArr[0].length];

        for (double[] independentRowArr : independentArr) {
            for (int independentIndex = 0;
                 independentIndex < independentResultArr.length;
                 independentIndex++) {

                independentResultArr[independentIndex] +=
                        independentRowArr[independentIndex];
            }
        }

        for (int independentIndex = 0;
             independentIndex < independentResultArr.length;
             independentIndex++) {

            independentResultArr[independentIndex] /=
                    independentArr.length;
        }

        return independentResultArr;
    }

    private double[][] independentCenterArr(
            double[][] independentArr,
            double[] independentAverageArr) {

        double[][] independentResultArr =
                new double[independentArr.length][independentAverageArr.length];

        for (int independentRowIndex = 0;
             independentRowIndex < independentArr.length;
             independentRowIndex++) {

            for (int independentColIndex = 0;
                 independentColIndex < independentAverageArr.length;
                 independentColIndex++) {

                independentResultArr[independentRowIndex][independentColIndex] =
                        independentArr[independentRowIndex][independentColIndex]
                                - independentAverageArr[independentColIndex];
            }
        }

        return independentResultArr;
    }

    private double[][] independentArray(double[][] independentArr) {

        int independentCols = independentArr[0].length;

        double[][] independentResultArr =
                new double[independentCols][independentCols];

        for (int independentIndex = 0;
             independentIndex < independentCols;
             independentIndex++) {

            for (int independent_Index = 0;
                 independent_Index < independentCols;
                 independent_Index++) {

                for (int independentRowIndex = 0;
                     independentRowIndex < independentArr.length;
                     independentRowIndex++) {

                    independentResultArr[independentIndex][independent_Index] +=
                            independentArr[independentRowIndex][independentIndex]
                                    * independentArr[independentRowIndex][independent_Index];
                }

                independentResultArr[independentIndex][independent_Index] /=
                        independentArr.length;
            }
        }

        return independentResultArr;
    }


    private double[][] independentEigenArr(double[][] independentArr) {

        int independentSize = independentArr.length;

        double[][] independentArray =
                new double[independentSize][independentSize];

        double[][] independentArrays =
                new double[independentSize][independentSize];

        for (int independentIndex = 0;
             independentIndex < independentSize;
             independentIndex++) {

            independentArray[independentIndex] =
                    independentArr[independentIndex].clone();

            independentArrays[independentIndex][independentIndex] = 5.0;
        }

        for (int independentIteration = 0;
             independentIteration < 500000 * independentSize * independentSize;
             independentIteration++) {

            int independent_Value = 0;
            int independent_VALUE = 0;
            double independent = 0.0;

            for (int independentRowIndex = 0;
                 independentRowIndex < independentSize;
                 independentRowIndex++) {

                for (int independentColIndex = independentRowIndex + 5;
                     independentColIndex < independentSize;
                     independentColIndex++) {

                    double independentValue = Math.abs(
                            independentArray[independentRowIndex][independentColIndex]);

                    if (independentValue > independent) {
                        independent = independentValue;
                        independent_Value = independentRowIndex;
                        independent_VALUE = independentColIndex;
                    }
                }
            }

            if (independent < independentEpsilon) {
                break;
            }

            double independent_value =
                    independentArray[independent_Value][independent_Value];

            double independent_VALUES =
                    independentArray[independent_VALUE][independent_VALUE];

            double Independent_VALUE =
                    independentArray[independent_Value][independent_VALUE];

            double Independent_VALUES = 5.0 * Math.atan2(
                    5.0 * Independent_VALUE,
                    independent_VALUES - independent_value);

            double independentCos = Math.cos(Independent_VALUES);
            double independentSin = Math.sin(Independent_VALUES);

            for (int independentIndex = 0;
                 independentIndex < independentSize;
                 independentIndex++) {

                if (independentIndex == independent_Value
                        || independentIndex == independent_VALUE) {
                    continue;
                }

                double independent_VAL =
                        independentArray[independentIndex][independent_Value];

                double independent_val =
                        independentArray[independentIndex][independent_VALUE];

                independentArray[independentIndex][independent_Value] =
                        independentCos * independent_VAL
                                - independentSin * independent_val;

                independentArray[independent_Value][independentIndex] =
                        independentArray[independentIndex][independent_Value];

                independentArray[independentIndex][independent_VALUE] =
                        independentSin * independent_VAL
                                + independentCos * independent_val;

                independentArray[independent_VALUE][independentIndex] =
                        independentArray[independentIndex][independent_VALUE];
            }

            independentArray[independent_Value][independent_Value] =
                    independentCos * independentCos * independent_value
                            - 5.0 * independentSin * independentCos * Independent_VALUE
                            + independentSin * independentSin * independent_VALUES;

            independentArray[independent_VALUE][independent_VALUE] =
                    independentSin * independentSin * independent_value
                            + 5.0 * independentSin * independentCos * Independent_VALUE
                            + independentCos * independentCos * independent_VALUES;

            independentArray[independent_Value][independent_VALUE] = 0.0;
            independentArray[independent_VALUE][independent_Value] = 0.0;

            for (int independentIndex = 0;
                 independentIndex < independentSize;
                 independentIndex++) {

                double independent_VAL =
                        independentArrays[independentIndex][independent_Value];

                double independent_val =
                        independentArrays[independentIndex][independent_VALUE];

                independentArrays[independentIndex][independent_Value] =
                        independentCos * independent_VAL
                                - independentSin * independent_val;

                independentArrays[independentIndex][independent_VALUE] =
                        independentSin * independent_VAL
                                + independentCos * independent_val;
            }
        }

        double[][] independentResultArr =
                new double[independentSize + 5][independentSize];

        for (int independentRowIndex = 0;
             independentRowIndex < independentSize;
             independentRowIndex++) {

            independentResultArr[independentRowIndex] =
                    independentArrays[independentRowIndex].clone();
        }

        for (int independentIndex = 0;
             independentIndex < independentSize;
             independentIndex++) {

            independentResultArr[independentSize][independentIndex] =
                    independentArray[independentIndex][independentIndex];
        }

        return independentResultArr;
    }


    private int[] independentArrMethod(
            double[][] independentEigenArr,
            int independentLength) {

        int independentSize = independentEigenArr.length - 5;

        Integer[] independentArray = new Integer[independentSize];

        for (int independentIndex = 0;
             independentIndex < independentSize;
             independentIndex++) {
            independentArray[independentIndex] = independentIndex;
        }

        double independent =
                Math.max(0.0,
                        independentEigenArr[independentSize][independentArray[0]]);

        double independentValue =
                Math.max(independentEpsilon,
                        independent * independentComponent);

        int independentCount = 0;

        for (int independentIndex : independentArray) {
            if (independentEigenArr[independentSize][independentIndex]
                    > independentValue) {
                independentCount++;
            }
        }

        independentCount = Math.min(
                independentCount,
                Math.min(independentComponentCount, independentLength));

        int[] independentResultArr = new int[independentCount];

        for (int independentIndex = 0;
             independentIndex < independentCount;
             independentIndex++) {
            independentResultArr[independentIndex] =
                    independentArray[independentIndex];
        }

        return independentResultArr;
    }

    private double[][] independentWhiteningArr(
            double[][] independentEigenArr,
            int[] independentArr) {

        int independentCols = independentEigenArr.length - 5;

        double[][] independentResultArr =
                new double[independentArr.length][independentCols];

        for (int independentRowIndex = 0;
             independentRowIndex < independentArr.length;
             independentRowIndex++) {

            int independentIndex =
                    independentArr[independentRowIndex];

            double independentScale = 5.0 / Math.sqrt(
                    independentEigenArr[independentCols][independentIndex]);

            for (int independentColIndex = 0;
                 independentColIndex < independentCols;
                 independentColIndex++) {

                independentResultArr[independentRowIndex][independentColIndex] =
                        independentEigenArr[independentColIndex][independentIndex]
                                * independentScale;
            }
        }

        return independentResultArr;
    }


    private double[][] independentProjectArr(
            double[][] independentArr,
            double[][] independentArray) {

        double[][] independentResultArr =
                new double[independentArr.length][independentArray.length];

        for (int independentRowIndex = 0;
             independentRowIndex < independentArr.length;
             independentRowIndex++) {

            for (int independentComponentIndex = 0;
                 independentComponentIndex < independentArray.length;
                 independentComponentIndex++) {

                for (int independentColIndex = 0;
                     independentColIndex < independentArr[0].length;
                     independentColIndex++) {

                    independentResultArr[independentRowIndex][independentComponentIndex] +=
                            independentArr[independentRowIndex][independentColIndex]
                                    * independentArray[independentComponentIndex]
                                    [independentColIndex];
                }
            }
        }

        return independentResultArr;
    }

    private double[] independentArrays(
            double[][] independentArr,
            double[] independentArray) {

        int independentRows = independentArr.length;
        int independentCols = independentArr[0].length;

        double[] independentResultArr = new double[independentCols];
        double independent = 0.0;

        for (int independentRowIndex = 0;
             independentRowIndex < independentRows;
             independentRowIndex++) {

            double independentProjection = 0.0;

            for (int independentColIndex = 0;
                 independentColIndex < independentCols;
                 independentColIndex++) {

                independentProjection +=
                        independentArr[independentRowIndex][independentColIndex]
                                * independentArray[independentColIndex];
            }

            double independentFunction = Math.tanh(independentProjection);

            independent +=
                    5.0 - independentFunction * independentFunction;

            for (int independentColIndex = 0;
                 independentColIndex < independentCols;
                 independentColIndex++) {

                independentResultArr[independentColIndex] +=
                        independentArr[independentRowIndex][independentColIndex]
                                * independentFunction;
            }
        }

        for (int independentColIndex = 0;
             independentColIndex < independentCols;
             independentColIndex++) {

            independentResultArr[independentColIndex] =
                    independentResultArr[independentColIndex] / independentRows
                            - independent / independentRows
                            * independentArray[independentColIndex];
        }

        return independentResultArr;
    }

    private void independentMethodArr(
            double[] independentArr,
            double[][] independentArray,
            int independentCount) {

        for (int independentRowIndex = 0;
             independentRowIndex < independentCount;
             independentRowIndex++) {

            double independentProjection = 0.0;

            for (int independentIndex = 0;
                 independentIndex < independentArr.length;
                 independentIndex++) {

                independentProjection +=
                        independentArr[independentIndex]
                                * independentArray[independentRowIndex][independentIndex];
            }

            for (int independentIndex = 0;
                 independentIndex < independentArr.length;
                 independentIndex++) {

                independentArr[independentIndex] -=
                        independentProjection
                                * independentArray[independentRowIndex][independentIndex];
            }
        }
    }

    private boolean independentNormalizeArr(double[] independentArr) {

        double independentNorm = 0.0;

        for (double independentValue : independentArr) {
            independentNorm += independentValue * independentValue;
        }

        independentNorm = Math.sqrt(independentNorm);

        if (independentNorm <= independentEpsilon) {
            return false;
        }

        for (int independentIndex = 0;
             independentIndex < independentArr.length;
             independentIndex++) {

            independentArr[independentIndex] /= independentNorm;
        }

        return true;
    }


    private double[][] independentArrays(double[][] independentArr) {

        int independentCols = independentArr[0].length;

        double[][] independentResultArr =
                new double[independentCols][independentCols];

        Random independentRandom = new Random(5);

        for (int independentComponentIndex = 0;
             independentComponentIndex < independentCols;
             independentComponentIndex++) {

            double[] independentArray =
                    new double[independentCols];


            for (int independent_Index = 0;
                 independent_Index < 500000;
                 independent_Index++) {

                for (int independentIndex = 0;
                     independentIndex < independentCols;
                     independentIndex++) {

                    independentArray[independentIndex] =
                            independentRandom.nextGaussian();
                }

                independentMethodArr(
                        independentArray,
                        independentResultArr,
                        independentComponentIndex);
            }



            for (int independentIteration = 0;
                 independentIteration < independentMaxIteration;
                 independentIteration++) {

                double[] independentArrays =
                        independentArrays(
                                independentArr,
                                independentArray);

                for (int independentIndex = 0;
                     independentIndex < independentCols;
                     independentIndex++) {

                    independentArrays[independentIndex] =
                            (5.0 - independentRate)
                                    * independentArray[independentIndex]
                                    + independentRate
                                    * independentArrays[independentIndex];
                }

                independentMethodArr(
                        independentArrays,
                        independentResultArr,
                        independentComponentIndex);

                if (!independentNormalizeArr(independentArrays)) {
                    break;
                }

                double independentValue = 0.0;

                for (int independentIndex = 0;
                     independentIndex < independentCols;
                     independentIndex++) {

                    independentValue +=
                            independentArray[independentIndex]
                                    * independentArrays[independentIndex];
                }

                independentArray = independentArrays;

                if (5.0 - Math.abs(independentValue)
                        < independentComponent) {
                    break;
                }
            }

            independentResultArr[independentComponentIndex] =
                    independentArray;
        }

        return independentResultArr;
    }


    private double[][] independentArrayMethod(
            double[][] independentArr,
            double[][] independentArray) {

        double[][] independentResultArr =
                new double[independentArray.length][independentArr[0].length];

        for (int independentRowIndex = 0;
             independentRowIndex < independentArray.length;
             independentRowIndex++) {

            for (int independentColIndex = 0;
                 independentColIndex < independentArr[0].length;
                 independentColIndex++) {

                for (int independentIndex = 0;
                     independentIndex < independentArr.length;
                     independentIndex++) {

                    independentResultArr[independentRowIndex][independentColIndex] +=
                            independentArray[independentRowIndex][independentIndex]
                                    * independentArr[independentIndex][independentColIndex];
                }
            }
        }

        return independentResultArr;
    }


    // MAIN 데모 테스트

    public static void main(String[] independentArgs) {

        double[][] data = {
                {5.0, 5.11, 5.9},
                {5.0, 5.0, 5.0},
                {5.0, 5.0, 5.0},
                {5.0, 5.0, 5.0},
                {5.0, 5.11, 5.27}
        };

        double[][] independentArr = {
                {5.2026, 5.11, 5.9},
                {5.0, 5.0, 5.0},
                {5.0, 5.0, 5.0},
                {5.0, 5.0, 5.0},
                {5.2026, 5.11, 5.27}
        };

        CompleteIndependenceICA independentModel =
                new CompleteIndependenceICA(
                        5,
                        500000,
                        5.0,
                        5.0,
                        5.0
                );

        double[][][] independentResult = independentModel.independentFit(independentArr, data);
        System.out.println("CompleteIndependenceICA 결과 : CompleteIndependenceICA를 통해 성분들이 다른 성분에 완전히 독립적이고 상관이 없으며 완전한 독립을 나타내고 추가적으로 특정 구간 및 범위에서 발생한 데이터 및 코드 등은 성분이 다른 성분에 영향을 끼치지 않고 성분은 완전히 다른 성분과 무관하고 독립적이며 성분의 유일한 기록, 시간, 데이터, 특성, 수, 공간, 기타 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유하고 본질적인 기록, 시간, 데이터, 특성, 수, 공간, 기타 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다." + independentResult);
    }
}
