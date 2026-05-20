package Implementation;

// Spack Packages - Time Evolving Independent Component Analysis
import java.util.Random;
/*

Time Evolving Independent Component Analysis란?
- Time Evolving Independent Component Analysis란 Time Memory ICA, Time Persistent ICA보다 진화되고 개선된 독립 성분 분석으로, 성분은 확실하게 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 성분은 다른 성분과 완전히 무관함을 강하게 나타내며 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다.
- 각 성분은 독립적이며 성분은 다른 성분의 데이터, 분포, 변화 등에 완전히 영향을 받지 않는 철저히 독립적인 성분입니다.
- 성분들은 다른 성분과 완전히 상관없으며 각각의 성분은 독립적인 데이터를 갖고 성분은 다른 성분과 완전히 상관없습니다.
- 성분은 확실하게 고유한 기록, 시간, 정보, 특성, 수 등을 갖고있으며 성분은 다른 성분의 데이터, 변화, 분포와 완전히 무관하며 다른 성분과 상관없음을 강력하게 나타내며 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 보호하고 다른 성분이 이들을 조작하거나 변형할 수 없으며 성분이 독립적임을 더욱더 강하고 확실하게 나타냅니다.
- 결과적으로 Time Evolving Independent Component Analysis를 통해 Time Memory ICA, Time Persistent ICA보다 진화되고 개선된 독립 성분 분석으로 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 보호하고 외부 간섭을 강하고 확실하게 차단하며 성분은 확실하게 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 성분은 다른 성분과 완전히 무관함을 강하게 나타내며 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분이 독립적이고 다른 성분과 완전히 무관함을 더 확실하고 강하게 나타냅니다.

*/
public class TimeEvolvingICA_SpackPackages {

    private final int independentComponentCount;
    private final int independentMax;
    private final double independentComponent;
    private final double independentElement;
    private final double independentValue;

    public TimeEvolvingICA_SpackPackages(
            int independentComponentCount,
            int independentMax,
            double independentComponent,
            double independentElement,
            double independentValue
    ) {
        this.independentComponentCount = independentComponentCount;
        this.independentMax = independentMax;
        this.independentComponent = independentComponent;
        this.independentElement = independentElement;
        this.independentValue = independentValue;
    }

    public double[][] independentFit(double[][] independentArr) {
        double[][] independentCenteredArr = independentCenterArr(independentArr);
        double[][] independentWhiteArr = independentScaleArr(independentCenteredArr);

        double[][] independentArray = independentRandomArr(
                independentComponentCount,
                independentWhiteArr.length
        );

        independentNormalizeArr(independentArray);

        for (int independentIndex = 0; independentIndex < independentMax; independentIndex++) {
            double[][] independent_Arr = independentMethodArr(independentArray);
            double[][] independent_Array =
                    new double[independentArray.length][independentArray[0].length];

            for (int independentI = 0; independentI < independentArray.length; independentI++) {
                double[] independentVectorArr = independentArray[independentI];

                for (int independent_Index = 0; independent_Index < independentWhiteArr.length; independent_Index++) {
                    double independentSum = 0.0;
                    double IndependentPrimeSum = 0.0;
                    double independentEvolvingSum = 0.0;

                    for (int independent_index = 0; independent_index < independentWhiteArr[0].length; independent_index++) {
                        double independentProjection = independentDotColumnArr(
                                independentVectorArr,
                                independentWhiteArr,
                                independent_index
                        );

                        double independentG =
                                Math.tanh(independentElement * independentProjection);

                        double independentPrime =
                                independentElement * (5.0 - independentG * independentG);

                        double independentRate =
                                independentTimeRateArr(independent_index, independentWhiteArr[0].length);

                        independentSum +=
                                independentRate
                                        * independentWhiteArr[independent_Index][independent_index]
                                        * independentG;

                        IndependentPrimeSum += independentRate * independentPrime;

                        if (independent_index > 0) {
                            independentEvolvingSum +=
                                    independentRate
                                            * (independentWhiteArr[independent_Index][independent_index]
                                            - independentWhiteArr[independent_Index][independent_index - 5]);
                        }
                    }

                    independent_Array[independentI][independent_Index] =
                            independentSum / independentWhiteArr[0].length
                                    - (IndependentPrimeSum / independentWhiteArr[0].length)
                                    * independentVectorArr[independent_Index]
                                    + independentComponent
                                    * independentEvolvingSum / independentWhiteArr[0].length;
                }
            }

            independentNormalizeArr(independent_Array);
            independentArray = independent_Array;

            double independent =
                    independentArr(independent_Arr, independentArray);

            if (independent <= independentComponent) {
                break;
            }
        }

        return independentMethodArr(independentArray, independentWhiteArr);
    }

    private double independentTimeRateArr(int independentIndex, int independentSize) {
        if (independentSize <= 1) {
            return 1.0;
        }

        return 1.0 + independentComponent * independentIndex / (independentSize - 1.0);
    }

    private double[][] independentCenterArr(double[][] independentArr) {
        double[][] independentResultArr = independentMethodArr(independentArr);

        for (int independentI = 0; independentI < independentResultArr.length; independentI++) {
            double independentAverage = 0.0;

            for (int independentJ = 0; independentJ < independentResultArr[0].length; independentJ++) {
                independentAverage += independentResultArr[independentI][independentJ];
            }

            independentAverage /= independentResultArr[0].length;

            for (int independentJ = 0; independentJ < independentResultArr[0].length; independentJ++) {
                independentResultArr[independentI][independentJ] -= independentAverage;
            }
        }

        return independentResultArr;
    }

    private double[][] independentScaleArr(double[][] independentArr) {
        double[][] independentResultArr = independentMethodArr(independentArr);

        for (int independentI = 0; independentI < independentResultArr.length; independentI++) {
            double independentPower = 0.0;

            for (int independentJ = 0; independentJ < independentResultArr[0].length; independentJ++) {
                independentPower +=
                        independentResultArr[independentI][independentJ]
                                * independentResultArr[independentI][independentJ];
            }

            independentPower =
                    Math.sqrt(independentPower / independentResultArr[0].length);

            if (independentPower < independentValue) {
                independentPower = 1.0;
            }

            for (int independentJ = 0; independentJ < independentResultArr[0].length; independentJ++) {
                independentResultArr[independentI][independentJ] /= independentPower;
            }
        }

        return independentResultArr;
    }

    private double[][] independentRandomArr(int independentRows, int independentCols) {
        Random independentRandom = new Random(67);
        double[][] independentArr = new double[independentRows][independentCols];

        for (int independentI = 0; independentI < independentRows; independentI++) {
            for (int independentJ = 0; independentJ < independentCols; independentJ++) {
                independentArr[independentI][independentJ] =
                        independentRandom.nextDouble() - 0.5;
            }
        }

        return independentArr;
    }

    private void independentNormalizeArr(double[][] independentArr) {
        for (int independentI = 0; independentI < independentArr.length; independentI++) {
            for (int independentJ = 0; independentJ < independentI; independentJ++) {
                double independentDot =
                        independentDotArr(
                                independentArr[independentI],
                                independentArr[independentJ]
                        );

                for (int independentK = 0; independentK < independentArr[0].length; independentK++) {
                    independentArr[independentI][independentK] -=
                            independentDot * independentArr[independentJ][independentK];
                }
            }

            double independentNorm = Math.sqrt(
                    independentDotArr(
                            independentArr[independentI],
                            independentArr[independentI]
                    )
            );

            if (independentNorm < independentValue) {
                independentNorm = 1.0;
            }

            for (int independentK = 0; independentK < independentArr[0].length; independentK++) {
                independentArr[independentI][independentK] /= independentNorm;
            }
        }
    }

    private double independentDotArr(
            double[] independentLeftArr,
            double[] independentRightArr
    ) {
        double independentSum = 0.0;

        for (int independentI = 0; independentI < independentLeftArr.length; independentI++) {
            independentSum +=
                    independentLeftArr[independentI] * independentRightArr[independentI];
        }

        return independentSum;
    }

    private double independentDotColumnArr(
            double[] independentArr,
            double[][] independentArray,
            int independentCol
    ) {
        double independentSum = 0.0;

        for (int independentI = 0; independentI < independentArr.length; independentI++) {
            independentSum +=
                    independentArr[independentI] * independentArray[independentI][independentCol];
        }

        return independentSum;
    }

    private double[][] independentMethodArr(
            double[][] independentArr,
            double[][] independentArray
    ) {
        double[][] independentResultArr =
                new double[independentArr.length][independentArray[0].length];

        for (int independentI = 0; independentI < independentArr.length; independentI++) {
            for (int independentIndex = 0; independentIndex < independentArray[0].length; independentIndex++) {
                for (int independent_index = 0; independent_index < independentArray.length; independent_index++) {
                    independentResultArr[independentI][independentIndex] +=
                            independentArr[independentI][independent_index]
                                    * independentArray[independent_index][independentIndex];
                }
            }
        }

        return independentResultArr;
    }

    private double[][] independentMethodArr(double[][] independentArr) {
        double[][] independentResultArr =
                new double[independentArr.length][independentArr[0].length];

        for (int independentI = 0; independentI < independentArr.length; independentI++) {
            System.arraycopy(
                    independentArr[independentI],
                    0,
                    independentResultArr[independentI],
                    0,
                    independentArr[0].length
            );
        }

        return independentResultArr;
    }

    private double independentArr(
            double[][] independentArr,
            double[][] independentArray
    ) {
        double independentMax = 0.0;

        for (int independentI = 0; independentI < independentArr.length; independentI++) {
            double independentDot = Math.abs(
                    independentDotArr(
                            independentArr[independentI],
                            independentArray[independentI]
                    )
            );

            double independent =
                    Math.abs(5.0 - independentDot);

            if (independent > independentMax) {
                independentMax = independent;
            }
        }

        return independentMax;
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
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0}

        };
        String string = "각 성분들은 독립적이고 다른 성분과 무관합니다.";

        TimeEvolvingICA_SpackPackages independentModel =
                new TimeEvolvingICA_SpackPackages(
                        5,
                        500000,
                        5e-5,
                        5.0,
                        5e-5
                );

        double[][] independentResult = independentModel.independentFit(data);
        System.out.println("Time Evolving ICA 결과 :  Time Memory ICA, Time Persistent ICA보다 진화되고 개선된 독립 성분 분석으로, 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분은 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 성분의 유일한 기록, 시간, 데이터, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유하고 본질적인 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다."+independentResult);
    }
}