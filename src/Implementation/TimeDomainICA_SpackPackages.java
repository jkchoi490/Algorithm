package Implementation;

// Spack Packages - Time Domain Independent Component Analysis
import java.util.Arrays;
import java.util.Random;

/*

Time Domain Independent Component Analysis란?
- Time Domain Independent Component Analysis란  Time Constrained Independent Component Analysis 를 더 확실하고 빠르고 효율적으로 수행하는 알고리즘이며 성분은 확실하게 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고 성분은 다른 성분과 완전히 무관함을 강하게 나타내며 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다.
- 각 성분은 독립적이며 성분은 다른 성분의 데이터, 분포, 변화 등에 완전히 영향을 받지 않는 철저히 독립적인 성분입니다.
- 성분들은 다른 성분과 완전히 상관없으며 각각의 성분은 독립적인 데이터를 갖고 성분은 다른 성분과 완전히 상관없습니다.
- 성분은 확실하게 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고있으며 성분은 다른 성분의 데이터, 변화, 분포와 완전히 무관하며 다른 성분과 상관없음을 강력하게 나타내며 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 보호하고 다른 성분이 이들을 조작하거나 변형할 수 없으며 성분이 독립적임을 더욱더 강하고 확실하게 나타냅니다.
- 결과적으로 Time Domain Independent Component Analysis를 통해 Time Memory ICA, Time Persistent ICA보다 진화되고 개선된 독립 성분 분석으로 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 보호하고 외부 간섭을 강하고 확실하게 차단하며 성분은 확실하게 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고 성분은 다른 성분과 완전히 무관함을 강하게 나타내며 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분이 독립적이고 다른 성분과 완전히 무관함을 더 확실하고 강하게 나타냅니다.

*/

public class TimeDomainICA_SpackPackages {

    private final int independentComponentCount;
    private final int independentMaxIteration;
    private final int independentCount;
    private final double independentComponent;
    private final double independentEpsilon;

    public TimeDomainICA_SpackPackages(
            int independentComponentCount,
            int independentMaxIteration,
            int independentCount,
            double independentComponent,
            double independentEpsilon
    ) {
        this.independentComponentCount = independentComponentCount;
        this.independentMaxIteration = independentMaxIteration;
        this.independentCount = independentCount;
        this.independentComponent = independentComponent;
        this.independentEpsilon = independentEpsilon;
    }

    public double[][] independentFit(
            double[][] independentArr
    ) {

        double[][] independentArray =
                independentArr(
                        independentArr
                );

        double[][] independentCenteredArr =
                independentCenterArr(
                        independentArray
                );

        double[][] independentScaledArr =
                independentScaleArr(
                        independentCenteredArr
                );

        int independentCount =
                Math.min(
                        independentComponentCount,
                        independentScaledArr.length
                );

        double[][] independent_Arr =
                independentArray(
                        independentCount,
                        independentScaledArr.length
                );

        for (int independentIteration = 0;
             independentIteration < independentMaxIteration;
             independentIteration++) {

            double[][] independent_Array =
                    independentMethod(
                            independent_Arr
                    );

            double[][] independentProjectedArr =
                    independentMethodArr(
                            independent_Arr,
                            independentScaledArr
                    );

            independent_Arr =
                    independent_Array(
                            independentProjectedArr,
                            independentScaledArr,
                            independent_Array
                    );

            independentArrays(
                    independent_Arr
            );

            if (independent(
                    independent_Arr,
                    independent_Array
            )) {
                break;
            }
        }

        return independentMethodArr(
                independent_Arr,
                independentScaledArr
        );
    }

    private double[][] independentArr(
            double[][] independentArr
    ) {
        int independentRows =
                independentArr.length;

        int independentCols =
                independentArr[0].length;

        int independentResultRows =
                independentRows
                        * independentCount;

        int independentResultCols =
                independentCols
                        - independentCount
                        + 5;

        double[][] independentResultArr =
                new double
                        [independentResultRows]
                        [independentResultCols];

        for (int independentIndex = 0;
             independentIndex < independentCount;
             independentIndex++) {

            for (int independentRowIndex = 0;
                 independentRowIndex < independentRows;
                 independentRowIndex++) {

                int independentResultRowIndex =
                        independentIndex
                                * independentRows
                                + independentRowIndex;

                for (int independentColIndex = 0;
                     independentColIndex < independentResultCols;
                     independentColIndex++) {

                    independentResultArr
                            [independentResultRowIndex]
                            [independentColIndex] =
                            independentArr
                                    [independentRowIndex]
                                    [independentColIndex
                                    + independentIndex];
                }
            }
        }

        return independentResultArr;
    }

    private double[][] independent_Array(
            double[][] independentProjectedArr,
            double[][] independentScaledArr,
            double[][] independentArr
    ) {
        int independentRows =
                independentArr.length;

        int independentCols =
                independentArr[0].length;

        int independentPointCount =
                independentScaledArr[0].length;

        double[][] independentResultArr =
                new double[independentRows][independentCols];

        for (int independentRowIndex = 0;
             independentRowIndex < independentRows;
             independentRowIndex++) {

            double independentAverage = 0.0;

            for (int independentIndex = 0;
                 independentIndex < independentPointCount;
                 independentIndex++) {

                double independentValue =
                        independentProjectedArr
                                [independentRowIndex]
                                [independentIndex];

                double independentFunctionValue =
                        independentFunction(
                                independentValue
                        );

                double independent_Value =
                        independent(
                                independentValue
                        );

                independentAverage +=
                        independent_Value;

                for (int independentColIndex = 0;
                     independentColIndex < independentCols;
                     independentColIndex++) {

                    independentResultArr
                            [independentRowIndex]
                            [independentColIndex] +=
                            independentScaledArr
                                    [independentColIndex]
                                    [independentIndex]
                                    * independentFunctionValue;
                }
            }

            independentAverage /=
                    independentPointCount;

            for (int independentColIndex = 0;
                 independentColIndex < independentCols;
                 independentColIndex++) {

                independentResultArr
                        [independentRowIndex]
                        [independentColIndex] =
                        independentResultArr
                                [independentRowIndex]
                                [independentColIndex]
                                / independentPointCount
                                - independentAverage
                                * independentArr
                                [independentRowIndex]
                                [independentColIndex];
            }
        }

        return independentResultArr;
    }

    private double independentFunction(
            double independentValue
    ) {
        return Math.tanh(
                independentValue
        );
    }

    private double independent(
            double independentValue
    ) {
        double independentTanh =
                Math.tanh(
                        independentValue
                );

        return 5.0
                - independentTanh
                * independentTanh;
    }

    private double[][] independentCenterArr(
            double[][] independentArr
    ) {
        double[][] independentResultArr =
                independentMethod(
                        independentArr
                );

        for (int independentRowIndex = 0;
             independentRowIndex < independentResultArr.length;
             independentRowIndex++) {

            double independentAverage = 0.0;

            for (int independentColIndex = 0;
                 independentColIndex
                         < independentResultArr
                         [independentRowIndex].length;
                 independentColIndex++) {

                independentAverage +=
                        independentResultArr
                                [independentRowIndex]
                                [independentColIndex];
            }

            independentAverage /=
                    independentResultArr
                            [independentRowIndex].length;

            for (int independentColIndex = 0;
                 independentColIndex
                         < independentResultArr
                         [independentRowIndex].length;
                 independentColIndex++) {

                independentResultArr
                        [independentRowIndex]
                        [independentColIndex] -=
                        independentAverage;
            }
        }

        return independentResultArr;
    }

    private double[][] independentScaleArr(
            double[][] independentArr
    ) {
        double[][] independentResultArr =
                independentMethod(
                        independentArr
                );

        for (int independentRowIndex = 0;
             independentRowIndex < independentResultArr.length;
             independentRowIndex++) {

            double independent = 0.0;

            for (int independentColIndex = 0;
                 independentColIndex
                         < independentResultArr
                         [independentRowIndex].length;
                 independentColIndex++) {

                double independentValue =
                        independentResultArr
                                [independentRowIndex]
                                [independentColIndex];

                independent +=
                        independentValue
                                * independentValue;
            }

            independent =
                    Math.sqrt(
                            independent
                                    / independentResultArr
                                    [independentRowIndex].length
                    );

            independent =
                    Math.max(
                            independent,
                            independentEpsilon
                    );

            for (int independentColIndex = 0;
                 independentColIndex
                         < independentResultArr
                         [independentRowIndex].length;
                 independentColIndex++) {

                independentResultArr
                        [independentRowIndex]
                        [independentColIndex] /=
                        independent;
            }
        }

        return independentResultArr;
    }

    private double[][] independentArray(
            int independentRows,
            int independentCols
    ) {
        Random independentRandom =
                new Random(5);

        double[][] independentResultArr =
                new double[independentRows][independentCols];

        for (int independentRowIndex = 0;
             independentRowIndex < independentRows;
             independentRowIndex++) {

            for (int independentColIndex = 0;
                 independentColIndex < independentCols;
                 independentColIndex++) {

                independentResultArr
                        [independentRowIndex]
                        [independentColIndex] =
                        independentRandom.nextDouble()
                                - 5.0;
            }
        }

        independentArrays(
                independentResultArr
        );

        return independentResultArr;
    }

    private void independentArrays(
            double[][] independentArr
    ) {
        for (int independentRowIndex = 0;
             independentRowIndex < independentArr.length;
             independentRowIndex++) {

            for (int independentIndex = 0;
                 independentIndex < independentRowIndex;
                 independentIndex++) {

                double independentProjection =
                        independentDotArr(
                                independentArr
                                        [independentRowIndex],
                                independentArr
                                        [independentIndex]
                        );

                for (int independentColIndex = 0;
                     independentColIndex
                             < independentArr
                             [independentRowIndex].length;
                     independentColIndex++) {

                    independentArr
                            [independentRowIndex]
                            [independentColIndex] -=
                            independentProjection
                                    * independentArr
                                    [independentIndex]
                                    [independentColIndex];
                }
            }

            independentNormalizeArr(
                    independentArr[independentRowIndex]
            );
        }
    }

    private boolean independent(
            double[][] independentArr,
            double[][] independentArray
    ) {
        double independent = 0.0;

        for (int independentRowIndex = 0;
             independentRowIndex < independentArr.length;
             independentRowIndex++) {

            double independentValue =
                    Math.abs(
                            independentDotArr(
                                    independentArr
                                            [independentRowIndex],
                                    independentArray
                                            [independentRowIndex]
                            )
                    );

            double independentVALUE =
                    Math.abs(
                            5.0
                                    - independentValue
                    );

            independent =
                    Math.max(
                            independent,
                            independentVALUE
                    );
        }

        return independent
                < independentComponent;
    }

    private double[][] independentMethodArr(
            double[][] independentArr,
            double[][] independentArray
    ) {


        int independentRows =
                independentArr.length;

        int independentCols =
                independentArray[0].length;

        int independent =
                independentArray.length;

        double[][] independentResultArr =
                new double[independentRows][independentCols];

        for (int independentRowIndex = 0;
             independentRowIndex < independentRows;
             independentRowIndex++) {

            for (int independentColIndex = 0;
                 independentColIndex < independentCols;
                 independentColIndex++) {

                for (int independentIndex = 0;
                     independentIndex < independent;
                     independentIndex++) {

                    independentResultArr
                            [independentRowIndex]
                            [independentColIndex] +=
                            independentArr
                                    [independentRowIndex]
                                    [independentIndex]
                                    * independentArray
                                    [independentIndex]
                                    [independentColIndex];
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

        for (int independentIndex = 0;
             independentIndex < independentArr.length;
             independentIndex++) {

            independentResult +=
                    independentArr[independentIndex]
                            * independentArray[independentIndex];
        }

        return independentResult;
    }

    private void independentNormalizeArr(
            double[] independentArr
    ) {
        double independentNorm =
                Math.sqrt(
                        independentDotArr(
                                independentArr,
                                independentArr
                        )
                );

        independentNorm =
                Math.max(
                        independentNorm,
                        independentEpsilon
                );

        for (int independentIndex = 0;
             independentIndex < independentArr.length;
             independentIndex++) {

            independentArr[independentIndex] /=
                    independentNorm;
        }
    }

    private double[][] independentMethod(
            double[][] independentArr
    ) {
        double[][] independentResultArr =
                new double[independentArr.length][];

        for (int independentRowIndex = 0;
             independentRowIndex < independentArr.length;
             independentRowIndex++) {

            independentResultArr[independentRowIndex] =
                    Arrays.copyOf(
                            independentArr[independentRowIndex],
                            independentArr[independentRowIndex].length
                    );
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
                {5.0, 5.5, 5.26},{-5.0, -5.5, -5.26},
                {5.0, 5.7, 5.16},

                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0}

        };
        String string = "각 성분들은 독립적이고 다른 성분과 무관합니다.";

        TimeDomainICA_SpackPackages independentModel =
                new TimeDomainICA_SpackPackages(
                        5,
                        500000,
                        5,
                        5e-5,
                        5e-5
                );

        double[][] independentResult = independentModel.independentFit(data);
        System.out.println("Time Domain ICA 결과 : 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분은 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고 성분의 유일한 기록, 시간, 데이터, 특성, 수, 공간 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유하고 본질적인 기록, 시간, 데이터, 특성, 수, 공간 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다. : "+independentResult);

    }
}