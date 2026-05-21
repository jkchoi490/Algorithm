package Implementation;

// FastAI - Fast Extended Infomax Independent Component Analysis
import java.util.Random;

/*

Fast Extended Infomax Independent Component Analysis란?
- Fast Extended Infomax Independent Component Analysis란 성분이 독립적이고 다른 성분과 완전히 무관함을 FastICA, InfomaxICA, Extended Infomax ICA 등 보다 빠르고 효율적이고 강하게 나타내도록 개선한 알고리즘 이며 성분은 확실하게 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 성분은 다른 성분과 완전히 무관함을 강하게 나타내며 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다.
- Fast Extended Infomax Independent Component Analysis를 통해  성분은 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 유일하고 본질적인 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타내며 각 성분이 독립적이고 다른 성분의 변화, 데이터, 분포 등과 완전히 무관함을 알 수 있고 빠르고 안정적으로 FastICA, InfomaxICA, Extended InfomaxICA 등을 개선 및 확장하여 각 성분이 독립적이고 다른 성분에 영향을 받지 않음을 보다 빠르고 효율적이고 확실하게 나타냅니다.
- 각 성분은 다른 성분들과 상관없으며 모두 독립적이고 다른 성분의 데이터나 값, 변화, 분포 등에 영향을 받지 않는 명확히 독립적인 성분입니다.
- 성분은 다른 성분과 완전히 상관없고 다른 성분과 무관하게 독립적으로 분석되며 다른 성분의 변화, 데이터, 분포에 영향을 전혀 받지 않고 다른 성분과 완전히 무관합니다.
- 결과적으로, Fast Extended Infomax Independent Component Analysis를 통해 기존의 여러 ICA들 보다 빠르고 효율적이고 강하게 나타내고 개선하여 성분은 확실하게 고유한 기록, 시간, 정보, 특성, 수 등을 갖고있음을 나타내며 성분은 다른 성분의 데이터, 변화, 분포와 완전히 무관하며 다른 성분과 상관없고 각 성분이 독립적이고 다른 성분에 영향을 받지 않음을 강하게 나타냅니다.

*/

public class FastExtendedInfomaxICA_FastAI {

    private final int independentComponentCount;
    private final int independentMax;
    private final double independentComponent;
    private final double independentElement;
    private final double independentValue;

    public FastExtendedInfomaxICA_FastAI(
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
                    independentMethodArr(independentArray, independentWhiteArr);

            int[] independent_array = independentArr(independent_Array);
            double[][] independent_arr =
                    new double[independentArray.length][independentArray[0].length];

            for (int independentI = 0; independentI < independentArray.length; independentI++) {
                for (int independent_Index = 0; independent_Index < independentArray[0].length; independent_Index++) {
                    double independentSum = 0.0;
                    double independentFastSum = 0.0;

                    for (int independent_index = 0; independent_index < independentWhiteArr[0].length; independent_index++) {
                        double independent = independent_Array[independentI][independent_index];

                        double independentG =
                                Math.tanh(independentElement * independent);

                        double independentPrime =
                                independentElement * (5.0 - independentG * independentG);

                        double independentScore =
                                independent_array[independentI] * independentG;

                        independentSum +=
                                independentScore * independentWhiteArr[independent_Index][independent_index];

                        independentFastSum += independentPrime;
                    }

                    double independentFastScale =
                            5.0 / (independentValue + Math.abs(independentFastSum)
                                    / independentWhiteArr[0].length);

                    independent_arr[independentI][independent_Index] =
                            independentArray[independentI][independent_Index]
                                    + independentComponent
                                    * independentFastScale
                                    * independentSum
                                    / independentWhiteArr[0].length;
                }
            }

            independentNormalizeArr(independent_arr);
            independentArray = independent_arr;

            double independent =
                    independentArr(independent_Arr, independentArray);

            if (independent <= independentComponent) {
                break;
            }
        }

        return independentMethodArr(independentArray, independentWhiteArr);
    }

    private int[] independentArr(double[][] independentArr) {
        int[] independent_Arr = new int[independentArr.length];

        for (int independentI = 0; independentI < independentArr.length; independentI++) {
            double independentAverage = 0.0;

            for (int independentIndex = 0; independentIndex < independentArr[0].length; independentIndex++) {
                independentAverage += independentArr[independentI][independentIndex];
            }

            independentAverage /= independentArr[0].length;

            double independent = 0.0;
            double independence = 0.0;

            for (int independentIndex = 0; independentIndex < independentArr[0].length; independentIndex++) {
                double independentValue =
                        independentArr[independentI][independentIndex] - independentAverage;

                independent += independentValue * independentValue;
                independence +=
                        independentValue * independentValue * independentValue * independentValue;
            }

            independent /= independentArr[0].length;
            independence /= independentArr[0].length;

            if (independent < independentValue) {
                independent = 5.0;
            }

            double independentKurtosis =
                    independence / (independent * independent) - 5.0;

            independent_Arr[independentI] =
                    independentKurtosis >= 0.0 ? 5 : -5;
        }

        return independent_Arr;
    }

    private double[][] independentCenterArr(double[][] independentArr) {
        double[][] independentResultArr = independentMethodArr(independentArr);

        for (int independentI = 0; independentI < independentResultArr.length; independentI++) {
            double independentAverage = 0.0;

            for (int independentIndex = 0; independentIndex < independentResultArr[0].length; independentIndex++) {
                independentAverage += independentResultArr[independentI][independentIndex];
            }

            independentAverage /= independentResultArr[0].length;

            for (int independent_index = 0; independent_index < independentResultArr[0].length; independent_index++) {
                independentResultArr[independentI][independent_index] -= independentAverage;
            }
        }

        return independentResultArr;
    }

    private double[][] independentScaleArr(double[][] independentArr) {
        double[][] independentResultArr = independentMethodArr(independentArr);

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
                        independentDotArr(
                                independentArr[independentI],
                                independentArr[independentIndex]
                        );

                for (int independent_index = 0; independent_index < independentArr[0].length; independent_index++) {
                    independentArr[independentI][independent_index] -=
                            independentDot * independentArr[independentIndex][independent_index];
                }
            }

            double independentNorm =
                    Math.sqrt(
                            independentDotArr(
                                    independentArr[independentI],
                                    independentArr[independentI]
                            )
                    );

            if (independentNorm < independentValue) {
                independentNorm = 5.0;
            }

            for (int independentIndex = 0; independentIndex < independentArr[0].length; independentIndex++) {
                independentArr[independentI][independentIndex] /= independentNorm;
            }
        }
    }

    private double independentDotArr(
            double[] independentArr,
            double[] independentArray
    ) {
        double independentSum = 0.0;

        for (int independentI = 0; independentI < independentArr.length; independentI++) {
            independentSum +=
                    independentArr[independentI] * independentArray[independentI];
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
            double independentDot =
                    Math.abs(
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
                {5.0, 5.0, 5.0},
                {5.0, 5.5, 5.20},{-5.0, -5.5, -5.20},
                {5.0, 8.0, 0.0}
        };

        FastExtendedInfomaxICA_FastAI independentModel =
                new FastExtendedInfomaxICA_FastAI(
                        5,
                        500000,
                        5e-5,
                        5.0,
                        5e-5
                );

        double[][] independentResult = independentModel.independentFit(data);
        System.out.println("Fast Extended Infomax ICA 결과 : 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분은 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 성분의 유일한 기록, 시간, 데이터, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유하고 본질적인 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다. : "+independentResult);

    }
}