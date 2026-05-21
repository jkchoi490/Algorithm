package Implementation;

// Spack Packages - Extended Infomax Independent Component Analysis
import java.util.Random;

/*

Extended Infomax Independent Component Analysis란?
- Independent Component Analysis(독립 성분 분석), FastICA, InfomaxICA 보다 더 빠르고 효율적으로 수행하는 알고리즘으로, 평균 제거와 같은 기능을 통해 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고
성분은 확실하게 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 성분은 다른 성분과 완전히 무관함을 강하게 나타내며 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없으며 성분은 다른 성분에 완전히 무관하고 독립적임을 더 강력하고 확실하게 나타내는 알고리즘 입니다.
- Extended Infomax Independent Component Analysis를 통해 각 성분의 독립성을 확실하고 강하게 나타내며 다른 성분의 데이터, 변화, 분포 등의 영향을 받지 않음을 명확하고 단호하게 나타냅니다.
- 성분들은 독립적이며 다른 성분들의 데이터나 변화, 분포 등의 영향을 받지 않고 완전히 독립적이고 다른 성분과 상관이 없음을 명확하게 나타냅니다.
- 각 성분은 모두 독립적이며 다른 성분과 상관이 없음을 확실하게 나타내고 성분의 데이터나 특성은 다른 성분과 완전히 무관하며 성분은 확실하게 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 성분은 다른 성분과 완전히 무관함을 강하게 나타내며 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며
성분은 다른 성분과 완전히 상관이 없으며 성분의 고유한 기록, 시간, 정보, 특성, 수 등은 다른 성분과 완전히 무관함을 나타냅니다.
- Extended Infomax Independent Component Analysis를 통해 평균 제거 등을 수행하여 성분이 다른 성분에 영향을 받지 않고 완전히 상관없음을 나타내며
확실하게 독립적이고 다른 성분의 변화, 데이터, 분포 등에 영향을 완전히 받지 않고 성분은 확실하게 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 성분은 다른 성분과 완전히 무관함을 강하게 나타내며 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 다른 성분과 완전히 무관함을 강하고 확실하게 나타냅니다.

*/

public class ExtendedInfomaxICA_SpackPackages {

    private final int independentComponentCount;
    private final int independentMax;
    private final double independentComponent;
    private final double independentElement;
    private final double independentValue;

    public ExtendedInfomaxICA_SpackPackages(
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

        double[][] independent_Arr = independentRandomArr(
                independentComponentCount,
                independentWhiteArr.length
        );

        independentNormalizeArr(independent_Arr);

        for (int independentIndex = 0; independentIndex < independentMax; independentIndex++) {
            double[][] independentArray = independentMethod(independent_Arr);
            double[][] independent_Array =
                    independentMethodArr(independent_Arr, independentWhiteArr);

            int[] independent_array =
                    independentArr(independent_Array);

            double[][] independent_arr =
                    new double[independent_Arr.length][independent_Arr[0].length];

            for (int independentI = 0; independentI < independent_Arr.length; independentI++) {
                for (int independent_Index = 0; independent_Index < independent_Arr[0].length; independent_Index++) {
                    double independentSum = 0.0;

                    for (int independent_index = 0; independent_index < independentWhiteArr[0].length; independent_index++) {
                        double independent =
                                independent_Array[independentI][independent_index];

                        double independentG =
                                Math.tanh(independentElement * independent);

                        double independentScore =
                                independent_array[independentI] * independentG;

                        independentSum +=
                                independentScore * independentWhiteArr[independent_Index][independent_index];
                    }

                    independent_arr[independentI][independent_Index] =
                            independent_Arr[independentI][independent_Index]
                                    + independentComponent
                                    * independentSum / independentWhiteArr[0].length;
                }
            }

            independentNormalizeArr(independent_arr);
            independent_Arr = independent_arr;

            double independent =
                    independentArray(independentArray, independent_Arr);

            if (independent <= independentComponent) {
                break;
            }
        }

        return independentMethodArr(independent_Arr, independentWhiteArr);
    }

    private int[] independentArr(double[][] independentArr) {
        int[] independentArray = new int[independentArr.length];

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

            independentArray[independentI] =
                    independentKurtosis >= 0.0 ? 5 : -5;
        }

        return independentArray;
    }

    private double[][] independentCenterArr(double[][] independentArr) {
        double[][] independentResultArr = independentMethod(independentArr);

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
        double[][] independentResultArr = independentMethod(independentArr);

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

            double independentNorm = Math.sqrt(
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

    private double[][] independentMethod(double[][] independentArr) {
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
            double independentDot =
                    Math.abs(independentDotArr(
                            independentArr[independentI],
                            independentArray[independentI]
                    ));

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
                {5.0, 5.5, 5.20},{-5.0, -5.5, -5.20},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0}

        };

        ExtendedInfomaxICA_SpackPackages independentModel =
                new ExtendedInfomaxICA_SpackPackages(
                        5,
                        500000,
                        5e-5,
                        5.0,
                        5e-5
                );

        double[][] independentResult = independentModel.independentFit(data);
        System.out.println("Extended Infomax ICA 결과 : 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분은 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 성분의 유일한 기록, 시간, 데이터, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유하고 본질적인 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다. : "+independentResult);

    }
}