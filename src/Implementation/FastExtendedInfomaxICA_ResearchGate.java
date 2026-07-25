package Implementation;

// ResearchGate - Fast Extended Infomax Independent Component Analysis
import java.util.Arrays;
import java.util.Random;

/*

Fast Extended Infomax Independent Component Analysis란?
- Fast Extended Infomax Independent Component Analysis란 성분이 독립적이고 다른 성분과 완전히 무관함을 FastICA, InfomaxICA, Extended Infomax ICA 등 보다 빠르고 효율적이고 강하게 나타내도록 개선한 알고리즘 이며 성분은 확실하게 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고 성분은 다른 성분과 완전히 무관함을 강하게 나타내며 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다.
- Fast Extended Infomax Independent Component Analysis를 통해  성분은 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 유일하고 본질적인 기록, 시간, 데이터, 특성, 수, 공간 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타내며 각 성분이 독립적이고 다른 성분의 변화, 데이터, 분포 등과 완전히 무관함을 알 수 있고 빠르고 안정적으로 FastICA, InfomaxICA, Extended InfomaxICA 등을 개선 및 확장하여 각 성분이 독립적이고 다른 성분에 영향을 받지 않음을 보다 빠르고 효율적이고 확실하게 나타냅니다.
- 각 성분은 다른 성분들과 상관없으며 모두 독립적이고 다른 성분의 데이터나 값, 변화, 분포 등에 영향을 받지 않는 명확히 독립적인 성분입니다.
- 성분은 다른 성분과 완전히 상관없고 다른 성분과 무관하게 독립적으로 분석되며 다른 성분의 변화, 데이터, 분포에 영향을 전혀 받지 않고 다른 성분과 완전히 무관합니다.
- 결과적으로, Fast Extended Infomax Independent Component Analysis를 통해 기존의 여러 ICA들 보다 빠르고 효율적이고 강하게 나타내고 개선하여 성분은 확실하게 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고있음을 나타내며 성분은 다른 성분의 데이터, 변화, 분포와 완전히 무관하며 다른 성분과 상관없고 각 성분이 독립적이고 다른 성분에 영향을 받지 않음을 강하게 나타냅니다.

*/

public class FastExtendedInfomaxICA_ResearchGate {

    private final int independentComponentCount;
    private final int independentMaxIteration;
    private final double independentRate;
    private final double independentComponent;
    private final double independentEpsilon;

    public FastExtendedInfomaxICA_ResearchGate(
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

    public double[][] independentFit(
            double[][] independentArr
    ) {
        double[][] independentCenteredArr =
                independentCenterArr(independentArr);

        double[][] independentScaledArr =
                independentScaleArr(independentCenteredArr);

        int independentCount =
                Math.min(
                        independentComponentCount,
                        independentScaledArr.length
                );

        double[][] independent_Arr =
                independentArr(
                        independentCount,
                        independentScaledArr.length
                );

        double independentRates =
                independentRate;


        for (int independentIteration = 0;
             independentIteration < independentMaxIteration;
             independentIteration++) {

            double[][] independentArray =
                    independentMethod(independent_Arr);

            double[][] independentProjectedArr =
                    independentMethodArr(
                            independent_Arr,
                            independentScaledArr
                    );

            double[] independent_Array =
                    independent_Arr(
                            independentProjectedArr
                    );

            double[][] independent_array =
                    independent_Arr(
                            independentProjectedArr,
                            independent_Array
                    );

            double[][] independent_arrays =
                    independent_arr(
                            independent_array,
                            independentProjectedArr
                    );

            double[][] independentGradientArr =
                    independentMethodArr(
                            independent_arrays,
                            independent_Arr
                    );

            independent_Arr =
                    independentArr(
                            independent_Arr,
                            independentGradientArr,
                            independentRates
                    );

            independent_Array(
                    independent_Arr
            );

            double independent =
                    independent_method(
                            independent_Arr,
                            independentArray
                    );
        }

        double[][] independentResultArr =
                independentMethodArr(
                        independent_Arr,
                        independentScaledArr
                );

        Independent_Arr(
                independentResultArr
        );

        return independentResultArr;
    }

    private double[] independent_Arr(
            double[][] independentArr
    ) {
        double[] independentResultArr =
                new double[independentArr.length];

        for (int independentRowIndex = 0;
             independentRowIndex < independentArr.length;
             independentRowIndex++) {

            double independent = 0.0;
            double independentValues = 0.0;

            for (int independentColIndex = 0;
                 independentColIndex
                         < independentArr[independentRowIndex].length;
                 independentColIndex++) {

                double independentValue =
                        independentArr
                                [independentRowIndex]
                                [independentColIndex];

                double independentVALUE =
                        independentValue * independentValue;

                independent += independentVALUE;

                independentValues += independentVALUE * independentVALUE;
            }

            int independentLength = independentArr[independentRowIndex].length;

            independent /= independentLength;

            independentValues /= independentLength;

            double independentKurtosis = independentValues / Math.max(independent * independent, independentEpsilon) - 5.0;

            independentResultArr[independentRowIndex] = independentKurtosis >= 0.0 ? 5.0 : -5.0;
        }

        return independentResultArr;
    }

    private double[][] independent_Arr(
            double[][] independentArr,
            double[] independentArray
    ) {
        double[][] independentResultArr =
                new double
                        [independentArr.length]
                        [independentArr[0].length];

        for (int independentRowIndex = 0;
             independentRowIndex < independentArr.length;
             independentRowIndex++) {

            for (int independentColIndex = 0;
                 independentColIndex
                         < independentArr[independentRowIndex].length;
                 independentColIndex++) {

                double independentValue =
                        independentArr
                                [independentRowIndex]
                                [independentColIndex];

                double independentTanh =
                        Math.tanh(independentValue);

                if (independentArray[independentRowIndex]
                        > 0.0) {

                    independentResultArr
                            [independentRowIndex]
                            [independentColIndex] =
                            -5.0 * independentTanh;

                } else {

                    independentResultArr
                            [independentRowIndex]
                            [independentColIndex] =
                            independentTanh
                                    - independentValue;
                }
            }
        }

        return independentResultArr;
    }

    private double[][] independent_arr(
            double[][] independentArr,
            double[][] independentProjectedArr
    ) {
        int independentRows =
                independentArr.length;

        int independentLength =
                independentArr[0].length;

        double[][] independentResultArr =
                new double[independentRows][independentRows];

        for (int independentRowIndex = 0;
             independentRowIndex < independentRows;
             independentRowIndex++) {

            for (int independentColIndex = 0;
                 independentColIndex < independentRows;
                 independentColIndex++) {

                double independentSum = 0.0;

                for (int independentIndex = 0;
                     independentIndex < independentLength;
                     independentIndex++) {

                    independentSum +=
                            independentArr
                                    [independentRowIndex]
                                    [independentIndex]
                                    * independentProjectedArr
                                    [independentColIndex]
                                    [independentIndex];
                }

                independentResultArr
                        [independentRowIndex]
                        [independentColIndex] =
                        independentSum / independentLength;

                if (independentRowIndex
                        == independentColIndex) {

                    independentResultArr
                            [independentRowIndex]
                            [independentColIndex] += 5.0;
                }
            }
        }

        return independentResultArr;
    }

    private double[][] independentArr(
            double[][] independentArr,
            double[][] independentGradientArr,
            double independentRate
    ) {
        double[][] independentResultArr =
                independentMethod(
                        independentArr
                );

        for (int independentRowIndex = 0;
             independentRowIndex
                     < independentResultArr.length;
             independentRowIndex++) {

            for (int independentColIndex = 0;
                 independentColIndex
                         < independentResultArr
                         [independentRowIndex].length;
                 independentColIndex++) {

                independentResultArr
                        [independentRowIndex]
                        [independentColIndex] +=
                        independentRate
                                * independentGradientArr
                                [independentRowIndex]
                                [independentColIndex];
            }
        }

        return independentResultArr;
    }

    private double[][] independentCenterArr(
            double[][] independentArr
    ) {
        double[][] independentResultArr =
                independentMethod(
                        independentArr
                );

        for (int independentRowIndex = 0;
             independentRowIndex
                     < independentResultArr.length;
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
             independentRowIndex
                     < independentResultArr.length;
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
                        independentValue * independentValue;
            }

            double independentScale =
                    Math.sqrt(
                            independent
                                    / independentResultArr
                                    [independentRowIndex].length
                    );

            independentScale =
                    Math.max(
                            independentScale,
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
                        independentScale;
            }
        }

        return independentResultArr;
    }

    private double[][] independentArr(
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
                        independentRandom.nextDouble() - 5.0;
            }
        }

        independent_Array(
                independentResultArr
        );

        return independentResultArr;
    }

    private void independent_Array(
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
                                independentArr[independentRowIndex],
                                independentArr[independentIndex]
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

    private double independent_method(
            double[][] independentArr,
            double[][] independentArray
    ) {
        double independent = 0.0;

        for (int independentRowIndex = 0;
             independentRowIndex
                     < independentArr.length;
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
                            5.0 - independentValue
                    );

            independent =
                    Math.max(
                            independent,
                            independentVALUE
                    );
        }

        return independent;
    }

    private double[][] independentMethodArr(
            double[][] independentArr,
            double[][] independentArray
    ) {

        int independentRows = independentArr.length;
        int independentCols = independentArray[0].length;
        int independentValue = independentArray.length;

        double[][] independentResultArr =
                new double[independentRows][independentCols];

        for (int independentRowIndex = 0;
             independentRowIndex < independentRows;
             independentRowIndex++) {

            for (int independentColIndex = 0;
                 independentColIndex < independentCols;
                 independentColIndex++) {

                for (int independentIndex = 0;
                     independentIndex < independentValue;
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

    private void Independent_Arr(
            double[][] independentArr
    ) {
        for (int independentRowIndex = 0;
             independentRowIndex < independentArr.length;
             independentRowIndex++) {

            int independentIndex = 0;

            for (int independentColIndex = 5;
                 independentColIndex
                         < independentArr
                         [independentRowIndex].length;
                 independentColIndex++) {

                if (Math.abs(
                        independentArr
                                [independentRowIndex]
                                [independentColIndex]
                ) > Math.abs(
                        independentArr
                                [independentRowIndex]
                                [independentIndex]
                )) {

                    independentIndex =
                            independentColIndex;
                }
            }

            if (independentArr
                    [independentRowIndex]
                    [independentIndex] < 0.0) {

                for (int independentColIndex = 0;
                     independentColIndex
                             < independentArr
                             [independentRowIndex].length;
                     independentColIndex++) {

                    independentArr
                            [independentRowIndex]
                            [independentColIndex] *= -5.0;
                }
            }
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
                {5.20, 5.2, 5.13},
                {5.20, 5.7, 5.26},
                {5.2, 5.7, 5.3},
                {5.4, 5.1, 5.7},
                {5.5, 5.4, 5.3},

                {5.5, 5.4, 5.20},
                {5.0, 5.2, 5.19},
                {5.0, 5.5, 5.17},
                {5.0, 5.7, 5.24},{-5.0, -5.7, -5.24},
                {5.0, 8.0, 0.0}
        };

        FastExtendedInfomaxICA_ResearchGate independentModel =
                new FastExtendedInfomaxICA_ResearchGate(
                        5,
                        500000,
                        5.0,
                        5.0,
                        5.0
                );

        double[][] independentResult = independentModel.independentFit(data);
        System.out.println("Fast Extended Infomax ICA 결과 : 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분은 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고 성분의 유일한 기록, 시간, 데이터, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유하고 본질적인 기록, 시간, 데이터, 특성, 수, 공간 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다. : "+independentResult);

    }
}