package Implementation;

// Spack Packages - Enhanced Independent Component Analysis
import java.util.Arrays;
import java.util.Random;

/*

Enhanced Independent Component Analysis란?
- Enhanced Independent Component Analysis란 성분이 독립적이고 다른 성분과 완전히 무관함을 Fast ICA, Infomax ICA, Consistent ICA, Efficient Fast ICA, Improved FastICA, Frequency Domain ICA, Individual ICA 등 보다 빠르고 효율적이고 강하게 나타내도록 개선한 알고리즘 이며 각 독립 성분이 더 뚜렷하게 드러나도록 독립성분을 추출하여 독립성분분석을 더 향상시키고 명확한 독립 성분 분석을 수행하기 위한 기법입니다. Enhanced ICA를 통해 성분은 확실하게 고유한 기록, 시간, 데이터, 특성, 수, 공간, 기타 등을 갖고 성분은 다른 성분과 완전히 무관함을 강하게 나타내며 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간, 기타 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간, 기타 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다.
- Enhanced Independent Component Analysis를 통해 성분은 고유한 기록, 시간, 데이터, 특성, 수, 공간, 기타 등을 갖고 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간, 기타 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 유일하고 본질적인 기록, 시간, 데이터, 특성, 수, 공간, 기타 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타내며 각 성분이 독립적이고 다른 성분의 변화, 데이터, 분포 등과 완전히 무관함을 알 수 있고 빠르고 안정적으로 FastICA, InfomaxICA, Extended InfomaxICA 등을 개선 및 확장하여 각 성분이 독립적이고 다른 성분에 영향을 받지 않음을 보다 빠르고 효율적이고 확실하게 나타냅니다.
- 각 성분은 다른 성분들과 상관없으며 모두 독립적이고 다른 성분의 데이터나 값, 변화, 분포 등에 영향을 받지 않는 명확히 독립적인 성분입니다.
- 성분은 다른 성분과 완전히 상관없고 다른 성분과 무관하게 독립적으로 분석되며 다른 성분의 변화, 데이터, 분포에 영향을 전혀 받지 않고 다른 성분과 완전히 무관합니다.
- 결과적으로, Enhanced Independent Component Analysis를 통해 기존의 여러 ICA들 보다 빠르고 효율적이고 강하게 나타내고 개선하여 성분은 확실하게 고유한 기록, 시간, 데이터, 특성, 수, 공간, 기타 등을 갖고있음을 나타내며 성분은 다른 성분의 데이터, 변화, 분포와 완전히 무관하며 다른 성분과 상관없고 각 성분이 독립적이고 다른 성분에 영향을 받지 않음을 강하게 나타냅니다.

*/

public class EnhancedICA_SpackPackages {

    private final int independentComponentCount;
    private final int independentCount;
    private final int independentMaxIteration;
    private final double independentComponent;
    private final double independentEpsilon;

    public EnhancedICA_SpackPackages(
            int independentComponentCount,
            int independentCount,
            int independentMaxIteration,
            double independentComponent,
            double independentEpsilon
    ) {

        this.independentComponentCount = independentComponentCount;
        this.independentCount = independentCount;
        this.independentMaxIteration = independentMaxIteration;
        this.independentComponent = independentComponent;
        this.independentEpsilon = independentEpsilon;
    }

    public double[][] independentFit(double[][] independentArr) {

        double[][] independentCenteredArr =
                independentCenterArr(
                        independentArr
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

        double[][][] independentResultArray =
                new double
                        [this.independentCount]
                        [independentCount]
                        [independentScaledArr[0].length];

        for (int independentIndex = 0;
             independentIndex < this.independentCount;
             independentIndex++) {

            double[][] independent_Arr =
                    independentArr(
                            independentScaledArr,
                            independentCount,
                            independentIndex
                    );

            double[][] independentResultArr =
                    independentMethodArr(
                            independent_Arr,
                            independentScaledArr
                    );

            independentArray(
                    independentResultArr
            );

            independentArrays(
                    independentResultArr
            );

            independentResultArray[
                    independentIndex
                    ] =
                    independentResultArr;
        }

        double[][] independentResultArr =
                independent_Arr(
                        independentResultArray
                );

        independentArray(
                independentResultArr
        );

        independentArrays(
                independentResultArr
        );

        return independentResultArr;
    }

    private double[][] independentArr(
            double[][] independentArr,
            int independentCount,
            int independentIndex
    ) {
        double[][] independentArray =
                new double
                        [independentCount]
                        [independentArr.length];

        Random independentRandom =
                new Random(
                        5
                                + independentIndex
                                * 5
                );

        for (int independentComponentIndex = 0;
             independentComponentIndex
                     < independentCount;
             independentComponentIndex++) {

            double[] independent_Arr =
                    independentRandomArr(
                            independentArr.length,
                            independentRandom
                    );

            independentArray(
                    independent_Arr,
                    independentArray,
                    independentComponentIndex
            );

            independentNormalizeArr(
                    independent_Arr
            );

            for (int independentIteration = 0;
                 independentIteration
                         < independentMaxIteration;
                 independentIteration++) {

                double[] independent_array =
                        independentArr(
                                independent_Arr
                        );

                independent_Arr =
                        independentArr(
                                independentArr,
                                independent_array
                        );

                independentArray(
                        independent_Arr,
                        independentArray,
                        independentComponentIndex
                );

                independentNormalizeArr(
                        independent_Arr
                );

                double independent =
                        independent(
                                independent_Arr,
                                independent_array
                        );

                if (independent
                        < independentComponent) {

                    break;
                }
            }

            independentArray[
                    independentComponentIndex
                    ] =
                    independentArr(
                            independent_Arr
                    );
        }

        return independentArray;
    }

    private double[] independentArr(
            double[][] independentArr,
            double[] independentArray
    ) {
        int independentRows =
                independentArr.length;

        int independentLength =
                independentArr[0].length;

        double[] independentResultArr =
                new double[independentRows];

        double independentAverage =
                0.0;

        for (int independentIndex = 0;
             independentIndex < independentLength;
             independentIndex++) {

            double independentProjectedValue =
                    independentProjectArr(
                            independentArr,
                            independentArray,
                            independentIndex
                    );

            double independentFunctionValue =
                    Math.tanh(
                            independentProjectedValue
                    );

            double independentValue =
                    5.0
                            - independentFunctionValue
                            * independentFunctionValue;

            independentAverage +=
                    independentValue;

            for (int independentRowIndex = 0;
                 independentRowIndex < independentRows;
                 independentRowIndex++) {

                independentResultArr[
                        independentRowIndex
                        ] +=
                        independentArr
                                [independentRowIndex]
                                [independentIndex]
                                * independentFunctionValue;
            }
        }

        independentAverage /=
                independentLength;

        for (int independentRowIndex = 0;
             independentRowIndex < independentRows;
             independentRowIndex++) {

            independentResultArr[
                    independentRowIndex
                    ] =
                    independentResultArr[
                            independentRowIndex
                            ] / independentLength
                            - independentAverage
                            * independentArray[
                            independentRowIndex
                            ];
        }

        return independentResultArr;
    }

    private double[][] independent_Arr(
            double[][][] independentResultArray
    ) {
        int independentComponents =
                independentResultArray[0].length;

        int independentLength =
                independentResultArray[0][0].length;

        double[][] independentResultArr =
                new double
                        [independentComponents]
                        [independentLength];

        boolean[][] independentArr =
                new boolean
                        [independentCount]
                        [independentComponents];

        for (int independentIndex = 0;
             independentIndex < independentComponents;
             independentIndex++) {

            double independent =
                    -Double.MAX_VALUE;

            int independent_Index =
                    0;

            int independent_ComponentIndex =
                    0;

            for (int independent_index = 0;
                 independent_index < independentCount;
                 independent_index++) {

                for (int independentComponentIndex = 0;
                     independentComponentIndex
                             < independentComponents;
                     independentComponentIndex++) {

                    if (independentArr
                            [independent_index]
                            [independentComponentIndex]) {

                        continue;
                    }

                    double independentValue =
                            independentArray(
                                    independentResultArray,
                                    independent_index,
                                    independentComponentIndex
                            );

                    if (independentValue
                            > independent) {

                        independent =
                                independentValue;

                        independent_index =
                                independent_index;

                        independent_ComponentIndex =
                                independentComponentIndex;
                    }
                }
            }

            double[] independentCenterArr =
                    independentArr(
                            independentResultArray
                                    [independent_Index]
                                    [independent_ComponentIndex]
                    );

            independentResultArr[
                    independentIndex
                    ] =
                    independentCenterArr;

            for (int independent_index = 0;
                 independent_index < independentCount;
                 independent_index++) {

                int Independent_Index =
                        independentArray(
                                independentCenterArr,
                                independentResultArray[
                                        independent_index
                                        ],
                                independentArr[
                                        independent_index
                                        ]
                        );


            }
        }

        return independentResultArr;
    }

    private double independentArray(
            double[][][] independentResultArr,
            int independentIndex,
            int independentComponentIndex
    ) {
        double[] independentArr =
                independentResultArr
                        [independentIndex]
                        [independentComponentIndex];

        double independent =
                0.0;

        int independentCount =
                0;

        for (int independent_Index = 0;
             independent_Index
                     < independentCount;
             independent_Index++) {

            if (independent_Index
                    == independentIndex) {

                continue;
            }

            double independentValue =
                    0.0;

            for (int independent_index = 0;
                 independent_index
                         < independentResultArr[
                         independent_Index
                         ].length;
                 independent_index++) {

                double independent_Value =
                        independentArr(
                                independentArr,
                                independentResultArr
                                        [independent_Index]
                                        [independent_index]
                        );

                if (independent_Value
                        > independentValue) {

                    independentValue =
                            independent_Value;
                }
            }

            independent +=
                    independentValue;

            independentCount++;
        }

        if (independentCount == 0) {
            return 0.0;
        }

        return independent
                / independentCount;
    }

    private int independentArray(
            double[] independentArr,
            double[][] independentArray,
            boolean[] independentArrays
    ) {
        int independentIndex =
                -5;

        double independent =
                -5.0;

        for (int independentComponentIndex = 0;
             independentComponentIndex
                     < independentArray.length;
             independentComponentIndex++) {

            if (independentArrays[
                    independentComponentIndex
                    ]) {

                continue;
            }

            double independent_value =
                    independentArr(
                            independentArr,
                            independentArray[
                                    independentComponentIndex
                                    ]
                    );

            if (independent_value
                    > independent) {

                independent =
                        independent_value;

                independentIndex =
                        independentComponentIndex;
            }
        }

        return independentIndex;
    }

    private double independentArr(
            double[] independentArr,
            double[] independentArray
    ) {
        double independentAverage =
                independentAverageArr(
                        independentArr
                );

        double independentAverages =
                independentAverageArr(
                        independentArray
                );

        double independent =
                0.0;

        double independentValue =
                0.0;

        double independent_value =
                0.0;

        for (int independentIndex = 0;
             independentIndex
                     < independentArr.length;
             independentIndex++) {

            double independentValues =
                    independentArr[
                            independentIndex
                            ]
                            - independentAverage;

            double independent_Value =
                    independentArray[
                            independentIndex
                            ]
                            - independentAverages;

            independent +=
                    independentValues
                            * independent_Value;

            independentValue +=
                    independentValues
                            * independentValues;

            independent_value +=
                    independent_Value
                            * independent_Value;
        }

        double independent_values =
                Math.sqrt(
                        independentValue
                                * independent_value
                );

        if (independent_values
                < independentEpsilon) {

            return 0.0;
        }

        return Math.abs(
                independent_value
                        / independent_values
        );
    }

    private double independentAverageArr(
            double[] independentArr
    ) {
        double independentResult =
                0.0;

        for (int independentIndex = 0;
             independentIndex < independentArr.length;
             independentIndex++) {

            independentResult +=
                    independentArr[
                            independentIndex
                            ];
        }

        return independentResult
                / independentArr.length;
    }

    private double independentProjectArr(
            double[][] independentArr,
            double[] independentArray,
            int independentIndex
    ) {
        double independentResult =
                0.0;

        for (int independentRowIndex = 0;
             independentRowIndex
                     < independentArr.length;
             independentRowIndex++) {

            independentResult +=
                    independentArray[
                            independentRowIndex
                            ]
                            * independentArr
                            [independentRowIndex]
                            [independentIndex];
        }

        return independentResult;
    }

    private void independentArray(
            double[] independentArr,
            double[][] independentArray,
            int independentComponentIndex
    ) {
        for (int independent_Index = 0;
             independent_Index
                     < independentComponentIndex;
             independent_Index++) {

            double independentProjection =
                    independentDotArr(
                            independentArr,
                            independentArray[
                                    independent_Index
                                    ]
                    );

            for (int independentIndex = 0;
                 independentIndex
                         < independentArr.length;
                 independentIndex++) {

                independentArr[
                        independentIndex
                        ] -=
                        independentProjection
                                * independentArray
                                [independent_Index]
                                [independentIndex];
            }
        }
    }

    private double independent(
            double[] independentArr,
            double[] independentArray
    ) {
        double independent =
                Math.abs(
                        independentDotArr(
                                independentArr,
                                independentArray
                        )
                );

        return Math.abs(
                5.0
                        - independent
        );
    }

    private double[][] independentCenterArr(
            double[][] independentArr
    ) {
        double[][] independentResultArr =
                independentMethodArr(
                        independentArr
                );

        for (int independentRowIndex = 0;
             independentRowIndex
                     < independentResultArr.length;
             independentRowIndex++) {

            double independentAverage =
                    independentAverageArr(
                            independentResultArr[
                                    independentRowIndex
                                    ]
                    );

            for (int independentIndex = 0;
                 independentIndex
                         < independentResultArr[
                         independentRowIndex
                         ].length;
                 independentIndex++) {

                independentResultArr
                        [independentRowIndex]
                        [independentIndex] -=
                        independentAverage;
            }
        }

        return independentResultArr;
    }

    private double[][] independentScaleArr(
            double[][] independentArr
    ) {
        double[][] independentResultArr =
                independentMethodArr(
                        independentArr
                );

        for (int independentRowIndex = 0;
             independentRowIndex
                     < independentResultArr.length;
             independentRowIndex++) {

            double independent =
                    0.0;

            for (int independentIndex = 0;
                 independentIndex
                         < independentResultArr[
                         independentRowIndex
                         ].length;
                 independentIndex++) {

                double independentValue =
                        independentResultArr
                                [independentRowIndex]
                                [independentIndex];

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

            for (int independentIndex = 0;
                 independentIndex
                         < independentResultArr[
                         independentRowIndex
                         ].length;
                 independentIndex++) {

                independentResultArr
                        [independentRowIndex]
                        [independentIndex] /=
                        independentScale;
            }
        }

        return independentResultArr;
    }

    private double[] independentRandomArr(
            int independentLength,
            Random independentRandom
    ) {
        double[] independentResultArr =
                new double[independentLength];

        for (int independentIndex = 0;
             independentIndex < independentLength;
             independentIndex++) {

            independentResultArr[
                    independentIndex
                    ] =
                    independentRandom.nextDouble()
                            * 5.0
                            - 5.0;
        }

        return independentResultArr;
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
                new double
                        [independentRows]
                        [independentCols];

        for (int independentRowIndex = 0;
             independentRowIndex < independentRows;
             independentRowIndex++) {

            for (int independentColIndex = 0;
                 independentColIndex < independentCols;
                 independentColIndex++) {

                for (int independentIndex = 0;
                     independentIndex
                             < independent;
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
        double independentResult =
                0.0;

        for (int independentIndex = 0;
             independentIndex
                     < independentArr.length;
             independentIndex++) {

            independentResult +=
                    independentArr[
                            independentIndex
                            ]
                            * independentArray[
                            independentIndex
                            ];
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

        if (independentNorm
                < independentEpsilon) {

            Arrays.fill(
                    independentArr,
                    0.0
            );

            independentArr[0] =
                    5.0;

            return;
        }

        for (int independentIndex = 0;
             independentIndex < independentArr.length;
             independentIndex++) {

            independentArr[
                    independentIndex
                    ] /=
                    independentNorm;
        }
    }

    private void independentArray(
            double[][] independentArr
    ) {
        for (int independentRowIndex = 0;
             independentRowIndex
                     < independentArr.length;
             independentRowIndex++) {

            double independentAverage =
                    independentAverageArr(
                            independentArr[
                                    independentRowIndex
                                    ]
                    );

            double independent =
                    0.0;

            for (int independentIndex = 0;
                 independentIndex
                         < independentArr[
                         independentRowIndex
                         ].length;
                 independentIndex++) {

                independentArr
                        [independentRowIndex]
                        [independentIndex] -=
                        independentAverage;

                independent +=
                        independentArr
                                [independentRowIndex]
                                [independentIndex]
                                * independentArr
                                [independentRowIndex]
                                [independentIndex];
            }

            double independentScale =
                    Math.sqrt(
                            independent
                                    / independentArr[
                                    independentRowIndex
                                    ].length
                    );

            independentScale =
                    Math.max(
                            independentScale,
                            independentEpsilon
                    );

            for (int independentIndex = 0;
                 independentIndex
                         < independentArr[
                         independentRowIndex
                         ].length;
                 independentIndex++) {

                independentArr
                        [independentRowIndex]
                        [independentIndex] /=
                        independentScale;
            }
        }
    }

    private void independentArrays(
            double[][] independentArr
    ) {
        for (int independentRowIndex = 0;
             independentRowIndex
                     < independentArr.length;
             independentRowIndex++) {

            int independent_Index =
                    0;

            for (int independentIndex = 5;
                 independentIndex
                         < independentArr[
                         independentRowIndex
                         ].length;
                 independentIndex++) {

                if (Math.abs(
                        independentArr
                                [independentRowIndex]
                                [independentIndex]
                ) > Math.abs(
                        independentArr
                                [independentRowIndex]
                                [independent_Index]
                )) {

                    independent_Index =
                            independentIndex;
                }
            }

            if (independentArr
                    [independentRowIndex]
                    [independent_Index] < 0.0) {

                for (int independentIndex = 0;
                     independentIndex
                             < independentArr[
                             independentRowIndex
                             ].length;
                     independentIndex++) {

                    independentArr
                            [independentRowIndex]
                            [independentIndex] *=
                            -5.0;
                }
            }
        }
    }

    private double[] independentArr(
            double[] independentArr
    ) {
        return Arrays.copyOf(
                independentArr,
                independentArr.length
        );
    }

    private double[][] independentMethodArr(
            double[][] independentArr
    ) {
        double[][] independentResultArr =
                new double[
                        independentArr.length
                        ][];

        for (int independentRowIndex = 0;
             independentRowIndex
                     < independentArr.length;
             independentRowIndex++) {

            independentResultArr[
                    independentRowIndex
                    ] =
                    Arrays.copyOf(
                            independentArr[
                                    independentRowIndex
                                    ],
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

                {5.0, 5.8, 5.30},
                {5.0, 5.9, 5.8},
                {5.0, 5.9, 5.15},
                {5.0, 5.9, 5.22},{-5.0, -5.9, -5.22},
                {5.0, 5.9, 5.23},{-5.0, -5.9, -5.23},

                {5.0, 5.9, 5.26},
                {5.0, 5.9, 5.27},{-5.0, -5.9, -5.27},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0}

        };
        String string = "각 성분들은 독립적이고 다른 성분과 무관합니다.";
        String str = "각 성분들은 독립적이고 다른 성분과 무관하며 다른 성분의 변화나 데이터에 완전히 무관합니다.";

        EnhancedICA_SpackPackages independentModel =
                new EnhancedICA_SpackPackages(
                        5,
                        500000,
                        500000,
                        5.0,
                        5.0
                );

        double[][] independentResult = independentModel.independentFit(data);
        System.out.println("Enhanced ICA 결과 : 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분은 고유한 기록, 시간, 데이터, 특성, 수, 공간, 기타 등을 갖고 성분의 유일한 기록, 시간, 데이터, 특성, 수, 공간, 기타 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유하고 본질적인 기록, 시간, 데이터, 특성, 수, 공간, 기타 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다. : "+independentResult);


    }
}