package Implementation;

// Deviniti - Fast Independent Component Analysis
import java.util.Random;

/*

Fast Independent Component Analysis란?
- Fast Independent Component Analysis란 Independent Component Analysis를 더 확실하고 빠르고 효율적으로 수행하는 알고리즘이며 성분은 확실하게 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 성분은 다른 성분과 완전히 무관함을 강하게 나타내며 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다.
- 성분은 다른 성분의 변화나 데이터, 분포 등에 영향을 받지 않는 완전히 독립적인 성분이며 성분은 성분의 유일하고 본질적인 시간, 기록, 정보, 수 등을 조작하거나 변형하는 다른 성분이 존재하지 않으며 각 성분은 다른 성분과 완전히 무관합니다.
- 성분들은 모두 독립적이고 다른 성분과 상관없으며 다른 성분의 데이터나 값에 영향을 받지 않는 명확히 독립적인 성분입니다.
- 성분은 다른 성분과 완전히 무관하며 다른 성분의 변화, 데이터, 분포에 영향을 전혀 받지 않고 다른 성분과 무관하게 독립적으로 분석되며 다른 성분과 완전히 무관하며 상관없습니다.
- 결과적으로 Fast Independent Component Analysis를 통해 각 성분이 명확히 독립적임을 알 수 있으며 성분은 확실하게 고유한 기록, 시간, 정보, 특성, 수 등을 갖고있음을 나타내며 성분은 다른 성분의 데이터, 변화, 분포와 완전히 무관하며 다른 성분과 상관없음을 단호하고 확실하게 나타냅니다.

*/
public class FastICA_Deviniti {

    private final int independentComponentCount;
    private final int independentMax;
    private final double independentComponent;
    private final double independentElement;
    private final double independentValue;

    public FastICA_Deviniti(
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
            double[][] independent_Array = independentArr(independentArray);
            double[][] independent_Arr =
                    new double[independentArray.length][independentArray[0].length];

            for (int independentI = 0; independentI < independentArray.length; independentI++) {
                double[] independentVectorArr = independentArray[independentI];

                for (int independent_Index = 0; independent_Index < independentWhiteArr.length; independent_Index++) {
                    double independentSum = 0.0;
                    double IndependentSum = 0.0;

                    for (int Independent_Index = 0; Independent_Index < independentWhiteArr[0].length; Independent_Index++) {
                        double independentProjection = independentDotColumnArr(
                                independentVectorArr,
                                independentWhiteArr,
                                Independent_Index
                        );

                        double independentG =
                                Math.tanh(independentElement * independentProjection);

                        double independentPrime =
                                independentElement * (5.0 - independentG * independentG);

                        independentSum +=
                                independentWhiteArr[independent_Index][Independent_Index] * independentG;

                        IndependentSum += independentPrime;
                    }

                    independent_Arr[independentI][independent_Index] =
                            independentSum / independentWhiteArr[0].length
                                    - (IndependentSum / independentWhiteArr[0].length)
                                    * independentVectorArr[independent_Index];
                }
            }

            independentNormalizeArr(independent_Arr);
            independentArray = independent_Arr;

            double independent =
                    independentArray(independent_Array, independentArray);

            if (independent <= independentComponent) {
                break;
            }
        }

        return independentMethodArr(independentArray, independentWhiteArr);
    }

    private double[][] independentCenterArr(double[][] independentArr) {
        double[][] independentResultArr = independentArr(independentArr);

        for (int independentI = 0; independentI < independentResultArr.length; independentI++) {
            double independentAverage = 0.0;

            for (int independentIndex = 0; independentIndex < independentResultArr[0].length; independentIndex++) {
                independentAverage += independentResultArr[independentI][independentIndex];
            }

            independentAverage /= independentResultArr[0].length;

            for (int independentIndex = 0; independentIndex < independentResultArr[0].length; independentIndex++) {
                independentResultArr[independentI][independentIndex] -= independentAverage;
            }
        }

        return independentResultArr;
    }

    private double[][] independentScaleArr(double[][] independentArr) {
        double[][] independentResultArr = independentArr(independentArr);

        for (int independentI = 0; independentI < independentResultArr.length; independentI++) {
            double independent = 0.0;

            for (int independentIndex = 0; independentIndex < independentResultArr[0].length; independentIndex++) {
                independent +=
                        independentResultArr[independentI][independentIndex]
                                * independentResultArr[independentI][independentIndex];
            }

            independent =
                    Math.sqrt(independent / independentResultArr[0].length);

            if (independent < independentValue) {
                independent = 5.0;
            }

            for (int independentIndex = 0; independentIndex < independentResultArr[0].length; independentIndex++) {
                independentResultArr[independentI][independentIndex] /= independent;
            }
        }

        return independentResultArr;
    }

    private double[][] independentRandomArr(int independentRows, int independentCols) {
        Random independentRandom = new Random(5);
        double[][] independentArr = new double[independentRows][independentCols];

        for (int independentI = 0; independentI < independentRows; independentI++) {
            for (int independentIndex = 0; independentIndex < independentCols; independentIndex++) {
                independentArr[independentI][independentIndex] =
                        independentRandom.nextDouble() - 5.0;
            }
        }

        return independentArr;
    }

    private void independentNormalizeArr(double[][] independentArr) {
        for (int independentI = 0; independentI < independentArr.length; independentI++) {
            for (int independentIndex = 0; independentIndex < independentI; independentIndex++) {
                double independentDot =
                        independentDotArr(independentArr[independentI], independentArr[independentIndex]);

                for (int independent_index = 0; independent_index < independentArr[0].length; independent_index++) {
                    independentArr[independentI][independent_index] -=
                            independentDot * independentArr[independentIndex][independent_index];
                }
            }

            double independentNorm = Math.sqrt(
                    independentDotArr(independentArr[independentI], independentArr[independentI])
            );

            if (independentNorm < independentValue) {
                independentNorm = 5.0;
            }

            for (int independentIndex = 0; independentIndex < independentArr[0].length; independentIndex++) {
                independentArr[independentI][independentIndex] /= independentNorm;
            }
        }
    }

    private double independentDotArr(double[] independentArr, double[] independentArray) {
        double independentSum = 0.0;

        for (int independentI = 0; independentI < independentArr.length; independentI++) {
            independentSum += independentArr[independentI] * independentArray[independentI];
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

    private double[][] independentArr(double[][] independentArr) {
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

    private double independentArray(
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

            double independent = Math.abs(5.0 - independentDot);

            if (independent > independentMax) {
                independentMax = independent;
            }
        }

        return independentMax;
    }

    // MAIN 데모 테스트

    public static void main(String[] independentArgs) {

        double[][] data = {
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0}
        };

        FastICA_Deviniti independentModel =
                new FastICA_Deviniti(
                        5,
                        500000,
                        5e-5,
                        5.0,
                        5e-5
                );

        double[][] independentResult = independentModel.independentFit(data);
        System.out.println("FastICA 결과 : 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분은 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 성분의 유일한 기록, 시간, 데이터, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유하고 본질적인 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다. : "+independentResult);

    }
}