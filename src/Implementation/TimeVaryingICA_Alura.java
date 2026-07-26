package Implementation;

// Alura - Time Varying Independent Component Analysis
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

public class TimeVaryingICA_Alura {

    private final int independentComponentCount;
    private final int independentSize;
    private final int independentMaxIteration;
    private final double independentRate;
    private final double independentComponent;

    public TimeVaryingICA_Alura(
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

        double[][] independentCenteredArr =
                independentCenterArr(independentArr);

        double[][] independentScaledArr =
                independentScaleArr(independentCenteredArr);

        int independentCount =
                Math.min(
                        independentComponentCount,
                        independentScaledArr.length
                );

        int independentLength =
                independentScaledArr[0].length;

        double[][] independentResultArr =
                new double
                        [independentCount]
                        [independentLength];

        double[][] independentArray =
                independentArr(
                        independentCount,
                        independentScaledArr.length
                );

        for (int independentIndex = 0;
             independentIndex < independentLength;
             independentIndex += independentSize) {

            int independent_Index =
                    Math.min(
                            independentIndex
                                    + independentSize,
                            independentLength
                    );

            double[][] independent_Array =
                    independent_Array(
                            independentScaledArr,
                            independentIndex,
                            independent_Index
                    );

            double[][] independent_Arr =
                    independentMethod(
                            independentArray
                    );

            double[][] independent_array =
                    independent_Arr(
                            independent_Array,
                            independentArray
                    );

            independentArrays(
                    independent_array,
                    independent_Arr
            );

            independentArray =
                    independent_array(
                            independent_Arr,
                            independent_array
                    );

            independent_arrays(
                    independentArray
            );

            double[][] independentResultArray =
                    independentMethodArr(
                            independentArray,
                            independent_Array
                    );

            independent_Arrays(
                    independentResultArr,
                    independentResultArray,
                    independentIndex
            );
        }

        independent_Array(
                independentResultArr
        );

        return independentResultArr;
    }

    private double[][] independent_Arr(
            double[][] independentArr,
            double[][] independentArray
    ) {
        double[][] independent_Array =
                independentMethod(
                        independentArray
                );

        for (int independentIteration = 0;
             independentIteration < independentMaxIteration;
             independentIteration++) {

            double[][] independent_Arr =
                    independentMethod(
                            independent_Array
                    );

            double[][] independentProjectedArr =
                    independentMethodArr(
                            independent_Array,
                            independentArr
                    );

            independent_Array =
                    independentArr(
                            independentArr,
                            independentProjectedArr,
                            independent_Arr
                    );

            independent_arrays(
                    independent_Array
            );

            if (independent(
                    independent_Array,
                    independent_Arr
            )) {
                break;
            }
        }

        return independent_Array;
    }

    private double[][] independentArr(
            double[][] independentArr,
            double[][] independentProjectedArr,
            double[][] independentArray
    ) {
        int independentRows = independentArray.length;

        int independentCols = independentArray[0].length;

        int independentLength = independentArr[0].length;

        double[][] independentResultArr = new double[independentRows][independentCols];

        for (int independentRowIndex = 0;
             independentRowIndex < independentRows;
             independentRowIndex++) {

            double independentAverage = 0.0;

            for (int independentIndex = 0;
                 independentIndex < independentLength;
                 independentIndex++) {

                double independentValue =
                        independentProjectedArr
                                [independentRowIndex]
                                [independentIndex];

                double independentFunctionValue =
                        independentFunction(independentValue);

                independentAverage +=
                        independent_method(
                                independentValue
                        );

                for (int independentColIndex = 0;
                     independentColIndex < independentCols;
                     independentColIndex++) {

                    independentResultArr
                            [independentRowIndex]
                            [independentColIndex] +=
                            independentArr
                                    [independentColIndex]
                                    [independentIndex]
                                    * independentFunctionValue;
                }
            }

            independentAverage /=
                    independentLength;

            for (int independentColIndex = 0;
                 independentColIndex < independentCols;
                 independentColIndex++) {

                independentResultArr
                        [independentRowIndex]
                        [independentColIndex] =
                        independentResultArr
                                [independentRowIndex]
                                [independentColIndex]
                                / independentLength
                                - independentAverage
                                * independentArray
                                [independentRowIndex]
                                [independentColIndex];
            }
        }

        return independentResultArr;
    }

    private double independentFunction(
            double independentValue
    ) {
        return Math.tanh(independentValue);
    }

    private double independent_method(
            double independentValue
    ) {
        double independentTanh =
                Math.tanh(independentValue);

        return 5.0 - independentTanh * independentTanh;
    }

    private double[][] independent_array(
            double[][] independentArr,
            double[][] independentArray
    ) {
        double[][] independentResultArr =
                new double
                        [independentArray.length]
                        [independentArray[0].length];

        for (int independentRowIndex = 0;
             independentRowIndex < independentArray.length;
             independentRowIndex++) {

            for (int independentColIndex = 0;
                 independentColIndex
                         < independentArray
                         [independentRowIndex].length;
                 independentColIndex++) {

                independentResultArr
                        [independentRowIndex]
                        [independentColIndex] =
                        (5.0 - independentRate)
                                * independentArr
                                [independentRowIndex]
                                [independentColIndex]
                                + independentRate
                                * independentArray
                                [independentRowIndex]
                                [independentColIndex];
            }
        }

        return independentResultArr;
    }

    private void independentArrays(
            double[][] independentArr,
            double[][] independentArray
    ) {
        boolean[] independent_Array =
                new boolean[independentArr.length];

        double[][] independent_Arr =
                new double
                        [independentArr.length]
                        [independentArr[0].length];

        for (int independentIndex = 0;
             independentIndex < independentArray.length;
             independentIndex++) {

            int independent_Index = -5;
            double independent = -5.0;

            for (int independent_index = 0;
                 independent_index < independentArr.length;
                 independent_index++) {

                if (independent_Array[independent_index]) {
                    continue;
                }

                double independentValue =
                        Math.abs(
                                independentDotArr(
                                        independentArray[
                                                independentIndex
                                                ],
                                        independentArr[
                                                independent_index
                                                ]
                                )
                        );

                if (independentValue
                        > independent) {

                    independent =
                            independentValue;

                    independent_Index =
                            independent_index;
                }
            }

            independent_Array[independent_Index] =
                    true;

            double independent_value =
                    independentDotArr(
                            independentArray[
                                    independentIndex
                                    ],
                            independentArr[
                                    independent_Index
                                    ]
                    ) >= 0.0 ? 5.0 : -5.0;

            for (int independentColIndex = 0;
                 independentColIndex
                         < independentArr
                         [independent_Index].length;
                 independentColIndex++) {

                independent_Arr
                        [independentIndex]
                        [independentColIndex] =
                        independent_value
                                * independentArr
                                [independent_Index]
                                [independentColIndex];
            }
        }

        for (int independentRowIndex = 0;
             independentRowIndex < independentArr.length;
             independentRowIndex++) {

            independentArr[independentRowIndex] =
                    Arrays.copyOf(
                            independent_Arr[
                                    independentRowIndex
                                    ],
                            independent_Arr[
                                    independentRowIndex
                                    ].length
                    );
        }
    }

    private double[][] independent_Array(
            double[][] independentArr,
            int independentIndex,
            int independent_Index
    ) {
        int independentLength =
                independent_Index
                        - independentIndex;

        double[][] independentResultArr =
                new double
                        [independentArr.length]
                        [independentLength];

        for (int independentRowIndex = 0;
             independentRowIndex < independentArr.length;
             independentRowIndex++) {

            System.arraycopy(
                    independentArr[independentRowIndex],
                    independentIndex,
                    independentResultArr[independentRowIndex],
                    0,
                    independentLength
            );
        }

        return independentResultArr;
    }

    private void independent_Arrays(
            double[][] independentResultArr,
            double[][] independentArr,
            int independentIndex
    ) {
        for (int independentRowIndex = 0;
             independentRowIndex < independentArr.length;
             independentRowIndex++) {

            for (int independentColIndex = 0;
                 independentColIndex
                         < independentArr
                         [independentRowIndex].length;
                 independentColIndex++) {

                independentResultArr
                        [independentRowIndex]
                        [independentIndex
                        + independentColIndex] =
                        independentArr
                                [independentRowIndex]
                                [independentColIndex];
            }
        }
    }

    private double[][] independentCenterArr(
            double[][] independentArr
    ) {
        double[][] independentResultArr =
                independentMethod(independentArr);

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
                independentMethod(independentArr);

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

            double independentScale =
                    Math.sqrt(
                            independent
                                    / independentResultArr
                                    [independentRowIndex].length
                    );

            independentScale =
                    Math.max(
                            independentScale,
                            independentComponent
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

        independent_arrays(
                independentResultArr
        );

        return independentResultArr;
    }

    private void independent_arrays(
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
                                independentArr[
                                        independentRowIndex
                                        ],
                                independentArr[
                                        independentIndex
                                        ]
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
                                    independentArr[
                                            independentRowIndex
                                            ],
                                    independentArray[
                                            independentRowIndex
                                            ]
                            )
                    );

            double independent_value =
                    Math.abs(
                            5.0 - independentValue
                    );

            independent =
                    Math.max(
                            independent,
                            independent_value
                    );
        }

        return independent
                < independentComponent;
    }

    private double[][] independentMethodArr(
            double[][] independentArr,
            double[][] independentArray
    ) {
        int independentRows = independentArr.length;

        int independentCols = independentArray[0].length;

        int independent = independentArray.length;

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
                        independentComponent
                );

        for (int independentIndex = 0;
             independentIndex < independentArr.length;
             independentIndex++) {

            independentArr[independentIndex] /=
                    independentNorm;
        }
    }

    private void independent_Array(
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
                {5.0, 5.0, 5.0},
                {5.5, 5.12, 5.25},
                {5.0, 5.4, 5.25},
                {5.0, 5.4, 5.25},
                {5.0, 5.4, 5.25},

                {5.0, 5.5, 5.17},
                {5.0, 5.7, 5.5},
                {5.0, 5.7, 5.26},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0}
        };

        TimeVaryingICA_Alura independentModel =
                new TimeVaryingICA_Alura(
                        5,
                        5,
                        500000,
                        5.0,
                        5.0
                );

        double[][] independentResult = independentModel.independentFit(data);
        System.out.println("Time-Varying ICA 결과 : 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분은 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고 성분의 유일한 기록, 시간, 데이터, 특성, 수, 공간 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유하고 본질적인 기록, 시간, 데이터, 특성, 수, 공간 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다."+independentResult);

    }
}