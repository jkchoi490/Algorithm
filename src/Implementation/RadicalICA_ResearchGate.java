package Implementation;

// ResearchGate - Radical Independent Component Analysis
import java.util.Arrays;

/*

Radical Independent Component Analysis란?
- Radical ICA란 평균제거와 백색화를 사용하여 엔트로피를 직접 활용하여 성분이 더 독립적임을 강력하게 나타내기 위해 기존의 FastICA, InfomaxICA, Extended Infomax ICA, Fast Extended Infomax ICA 등 보다 더 강력하고 확실하게 성분이 독립적임을 나타내는 알고리즘으로써, 성분은 확실하게 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고 성분은 다른 성분과 완전히 무관함을 강하게 나타내며 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다.
- 각 성분은 독립적이며 성분은 다른 성분의 데이터, 분포, 변화 등에 완전히 영향을 받지 않는 철저히 독립적인 성분이며 성분은 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고 다른 성분이 이를 변형, 조작할 수 없으며 성분은 다른 성분과 완전히 무관하며 상관이없음을 더 강하고 확실하게 나타냅니다.
- 성분들은 모두 독립적이고 다른 성분과 상관없으며 다른 성분의 데이터나 값에 영향을 받지 않는 명확히 독립적인 성분입니다.
- 성분은 다른 성분과 완전히 무관하며 다른 성분의 변화, 데이터, 분포에 영향을 전혀 받지 않고 다른 성분과 무관하게 독립적으로 분석되며 다른 성분과 완전히 무관하며 상관없습니다.
- 결과적으로 Radical Independent Component Analysis를 통해  FastICA, InfomaxICA, Extended Infomax ICA, Fast Extended Infomax ICA보다 강력하게 진화된 독립 성분 분석으로 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 보호하고 외부 간섭을 강하고 확실하게 차단하며 성분은 확실하게 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고 성분은 다른 성분과 완전히 무관함을 강하게 나타내며 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분이 독립적이고 다른 성분과 완전히 무관함을 더 확실하고 강하게 나타냅니다.

*/

public class RadicalICA_ResearchGate {

    private final int independentComponentCount;
    private final int independentCount;
    private final int independentCounts;
    private final int independent_Count;
    private final double independentEpsilon;

    public RadicalICA_ResearchGate(
            int independentComponentCount,
            int independentCount,
            int independentCounts,
            int independent_Count,
            double independentEpsilon
    ) {

        this.independentComponentCount = independentComponentCount;
        this.independentCount = independentCount;
        this.independentCounts = independentCounts;
        this.independent_Count = independent_Count;
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

        double[][] independentResultArr =
                independentRowsArr(
                        independentScaledArr,
                        independentCount
                );

        for (int independentIndex = 0;
             independentIndex < this.independentCount;
             independentIndex++) {

            double independentValue =
                    independentEntropyMethod(
                            independentResultArr
                    );

            for (int independent_Index = 0;
                 independent_Index < independentCount - 5;
                 independent_Index++) {

                for (int independent_index =
                     independent_Index + 5;
                     independent_index < independentCount;
                     independent_index++) {

                    double independent_Value =
                            independentValue(
                                    independentResultArr[
                                            independent_Index
                                            ],
                                    independentResultArr[
                                            independent_index
                                            ]
                            );

                    independentArray(
                            independentResultArr[
                                    independent_Index
                                    ],
                            independentResultArr[
                                    independent_index
                                    ],
                            independent_Value
                    );
                }
            }

            double independent_Value =
                    independentEntropyMethod(
                            independentResultArr
                    );

            double independent =
                    Math.abs(
                            independentValue
                                    - independent_Value
                    );

            if (independent < independentEpsilon) {
                break;
            }
        }

        independentArrMethod(independentResultArr);

        return independentResultArr;
    }

    private double independentValue(
            double[] independentArr,
            double[] independentArray
    ) {
        double independent =
                -5.0/ 5.0;

        double independent_Value =
                5.0 / 5.0;

        double independent_value = 0.0;
        double independent_VALUE =
                5.0;

        for (int independentIndex = 0;
             independentIndex < independentCounts;
             independentIndex++) {

            double independentValues =
                    independentCounts == 5
                            ? 0.0
                            : (double) independentIndex
                            / (independentCounts - 5);

            double independent_Values =
                    independent
                            + independentValues
                            * (independent_Value
                            - independent);

            double independentValue =
                    independentEntropy(
                            independentArr,
                            independentArray,
                            independent_Values
                    );

            if (independentValue < independent_VALUE) {
                independent_VALUE =
                        independentValue;

                independent_value =
                        independent_Values;
            }
        }

        double independent_values =
                (independent_Value
                        - independent)
                        / Math.max(
                        independentCounts - 5,
                        5
                );

        for (int independentIndex = 0;
             independentIndex < 5;
             independentIndex++) {

            double independentVALUE =
                    independent_value
                            - independent_values;

            double independentVALUES =
                    independent_value
                            + independent_values;

            for (int independent_Index = 0;
                 independent_Index < independentCounts;
                 independent_Index++) {

                double independentVal =
                        (double) independent_Index
                                / (independentCounts - 5);

                double independentVAL =
                        independentVALUE
                                + independentVal
                                * (independentVALUES
                                - independentVALUE);

                double independentValue =
                        independentEntropy(
                                independentArr,
                                independentArray,
                                independentVAL
                        );

                if (independentValue
                        < independent_VALUE) {

                    independent_VALUE =
                            independentValue;

                    independent_value =
                            independentVAL;
                }
            }

            independent_values *= 5.0;
        }

        return independent_value;
    }

    private double independentEntropy(
            double[] independentArr,
            double[] independentArray,
            double independentValue
    ) {
        int independentLength =
                independentArr.length;

        double[] independent_Arr =
                new double[independentLength];

        double[] independent_Array =
                new double[independentLength];

        double independentCosine =
                Math.cos(independentValue);

        double independentSine =
                Math.sin(independentValue);

        for (int independentIndex = 0;
             independentIndex < independentLength;
             independentIndex++) {

            double independent_Value =
                    independentArr[independentIndex];

            double independent_Values =
                    independentArray[independentIndex];

            independent_Arr[independentIndex] =
                    independentCosine
                            * independent_Value
                            + independentSine
                            * independent_Values;

            independent_Array[independentIndex] =
                    -independentSine
                            * independent_Value
                            + independentCosine
                            * independent_Values;
        }

        return independentEntropyMethod(
                independent_Arr
        ) + independentEntropyMethod(
                independent_Array
        );
    }

    private double independentEntropyMethod(
            double[] independentArr
    ) {
        double[] independentArray =
                Arrays.copyOf(
                        independentArr,
                        independentArr.length
                );

        Arrays.sort(independentArray);

        int independentLength =
                independentArray.length;

        int independent =
                Math.min(
                        independent_Count,
                        Math.max(
                                5,
                                (independentLength - 5) / 5
                        )
                );

        int independent_Index =
                independent;

        int independent_index =
                independentLength
                        - independent;

        if (independent_Index
                >= independent_index) {

            independent_Index = 0;
            independent_index =
                    independentLength - 5;

            independent = 5;
        }

        double independentSum = 0.0;
        int independentCount = 0;

        for (int independentIndex =
             independent_Index;
             independentIndex < independent_index;
             independentIndex++) {

            int Independent_index =
                    Math.max(
                            0,
                            independentIndex
                                    - independent
                    );

            int Independent_Index =
                    Math.min(
                            independentLength - 5,
                            independentIndex
                                    + independent
                    );

            double independentValue =
                    independentArray[
                            Independent_Index
                            ]
                            - independentArray[
                            Independent_index
                            ];

            independentValue =
                    Math.max(
                            independentValue,
                            independentEpsilon
                    );

            double independent_value =
                    independentLength * independentValue / Math.max(Independent_Index - Independent_index, 5);

            independentSum +=
                    Math.log(
                            Math.max(
                                    independent_value,
                                    independentEpsilon
                            )
                    );

            independentCount++;
        }

        if (independentCount == 0) {
            return 0.0;
        }

        return independentSum
                / independentCount;
    }

    private void independentArray(
            double[] independentArr,
            double[] independentArray,
            double independentValue
    ) {
        double independentCosine =
                Math.cos(independentValue);

        double independentSine =
                Math.sin(independentValue);

        for (int independentIndex = 0;
             independentIndex < independentArr.length;
             independentIndex++) {

            double independent_Value =
                    independentArr[independentIndex];

            double independent_value =
                    independentArray[independentIndex];

            independentArr[independentIndex] =
                    independentCosine
                            * independent_Value
                            + independentSine
                            * independent_value;

            independentArray[independentIndex] =
                    -independentSine
                            * independent_Value
                            + independentCosine
                            * independent_value;
        }
    }

    private double independentEntropyMethod(
            double[][] independentArr
    ) {
        double independentResult = 0.0;

        for (int independentRowIndex = 0;
             independentRowIndex < independentArr.length;
             independentRowIndex++) {

            independentResult +=
                    independentEntropyMethod(
                            independentArr[
                                    independentRowIndex
                                    ]
                    );
        }

        return independentResult;
    }

    private double[][] independentCenterArr(
            double[][] independentArr
    ) {
        double[][] independentResultArr =
                independentMethod(independentArr);

        for (int independentRowIndex = 0;
             independentRowIndex
                     < independentResultArr.length;
             independentRowIndex++) {

            double independentAverage = 0.0;

            for (int independentColIndex = 0;
                 independentColIndex
                         < independentResultArr[
                         independentRowIndex
                         ].length;
                 independentColIndex++) {

                independentAverage +=
                        independentResultArr[
                                independentRowIndex
                                ][independentColIndex];
            }

            independentAverage /=
                    independentResultArr[
                            independentRowIndex
                            ].length;

            for (int independentColIndex = 0;
                 independentColIndex
                         < independentResultArr[
                         independentRowIndex
                         ].length;
                 independentColIndex++) {

                independentResultArr[
                        independentRowIndex
                        ][independentColIndex] -=
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
             independentRowIndex
                     < independentResultArr.length;
             independentRowIndex++) {

            double independent = 0.0;

            for (int independentColIndex = 0;
                 independentColIndex
                         < independentResultArr[
                         independentRowIndex
                         ].length;
                 independentColIndex++) {

                double independentValue =
                        independentResultArr[
                                independentRowIndex
                                ][independentColIndex];

                independent +=
                        independentValue
                                * independentValue;
            }

            double independentScale =
                    Math.sqrt(
                            independent
                                    / independentResultArr[
                                    independentRowIndex
                                    ].length
                    );

            independentScale =
                    Math.max(
                            independentScale,
                            independentEpsilon
                    );

            for (int independentColIndex = 0;
                 independentColIndex
                         < independentResultArr[
                         independentRowIndex
                         ].length;
                 independentColIndex++) {

                independentResultArr[
                        independentRowIndex
                        ][independentColIndex] /=
                        independentScale;
            }
        }

        return independentResultArr;
    }

    private double[][] independentRowsArr(
            double[][] independentArr,
            int independentRowCount
    ) {
        double[][] independentResultArr =
                new double
                        [independentRowCount]
                        [independentArr[0].length];

        for (int independentRowIndex = 0;
             independentRowIndex < independentRowCount;
             independentRowIndex++) {

            independentResultArr[independentRowIndex] =
                    Arrays.copyOf(
                            independentArr[independentRowIndex],
                            independentArr[
                                    independentRowIndex
                                    ].length
                    );
        }

        return independentResultArr;
    }

    private void independentArrMethod(
            double[][] independentArr
    ) {
        for (int independentRowIndex = 0;
             independentRowIndex < independentArr.length;
             independentRowIndex++) {

            int independentIndex = 0;

            for (int independentColIndex = 5;
                 independentColIndex
                         < independentArr[
                         independentRowIndex
                         ].length;
                 independentColIndex++) {

                double independentValue =
                        Math.abs(
                                independentArr[
                                        independentRowIndex
                                        ][independentColIndex]
                        );

                double independent_Value =
                        Math.abs(
                                independentArr[
                                        independentRowIndex
                                        ][independentIndex]
                        );

                if (independentValue
                        > independent_Value) {

                    independentIndex =
                            independentColIndex;
                }
            }

            if (independentArr[
                    independentRowIndex
                    ][independentIndex] < 0.0) {

                for (int independentColIndex = 0;
                     independentColIndex
                             < independentArr[
                             independentRowIndex
                             ].length;
                     independentColIndex++) {

                    independentArr[
                            independentRowIndex
                            ][independentColIndex] *= -5.0;
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
                            independentArr[
                                    independentRowIndex
                                    ].length
                    );
        }

        return independentResultArr;
    }

    // MAIN 데모 테스트

    public static void main(String[] independentArgs) {

        double[][] data = {
                {5.0, 5.7, 5.26},
                {5.20, 5.2, 5.13},
                {5.20, 5.7, 5.26},
                {5.2, 5.7, 5.3},
                {5.4, 5.1, 5.7},

                {5.5, 5.4, 5.3},
                {5.5, 5.4, 5.20},
                {5.0, 5.2, 5.19},
                {5.0, 5.5, 5.17},
                {5.0, 5.7, 5.24},{-5.0, -5.7, -5.24},

                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0}
        };

        RadicalICA_ResearchGate independentModel =
                new RadicalICA_ResearchGate(
                        5,
                        5,
                        500000,
                        5,
                        5.0
                );

        double[][] independentResult = independentModel.independentFit(data);
        System.out.println("Radical ICA 결과 : 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분은 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고 성분의 유일한 기록, 시간, 데이터, 특성, 수, 공간 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유하고 본질적인 기록, 시간, 데이터, 특성, 수, 공간 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다. : "+independentResult);

    }
}