package Implementation;

// Wikitionary - Fast Extended Infomax Independent Component Analysis
import java.util.Arrays;

/*

Fast Extended Infomax Independent Component Analysis란?
- Fast Extended Infomax Independent Component Analysis란 성분이 독립적이고 다른 성분과 완전히 무관함을 FastICA, InfomaxICA, Extended Infomax ICA 등 보다 빠르고 효율적이고 강하게 나타내도록 개선한 알고리즘 이며 성분은 확실하게 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 성분은 다른 성분과 완전히 무관함을 강하게 나타내며 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다.
- Fast Extended Infomax Independent Component Analysis를 통해  성분은 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 유일하고 본질적인 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타내며 각 성분이 독립적이고 다른 성분의 변화, 데이터, 분포 등과 완전히 무관함을 알 수 있고 빠르고 안정적으로 FastICA, InfomaxICA, Extended InfomaxICA 등을 개선 및 확장하여 각 성분이 독립적이고 다른 성분에 영향을 받지 않음을 보다 빠르고 효율적이고 확실하게 나타냅니다.
- 각 성분은 다른 성분들과 상관없으며 모두 독립적이고 다른 성분의 데이터나 값, 변화, 분포 등에 영향을 받지 않는 명확히 독립적인 성분입니다.
- 성분은 다른 성분과 완전히 상관없고 다른 성분과 무관하게 독립적으로 분석되며 다른 성분의 변화, 데이터, 분포에 영향을 전혀 받지 않고 다른 성분과 완전히 무관합니다.
- 결과적으로, Fast Extended Infomax Independent Component Analysis를 통해 기존의 여러 ICA들 보다 빠르고 효율적이고 강하게 나타내고 개선하여 성분은 확실하게 고유한 기록, 시간, 정보, 특성, 수 등을 갖고있음을 나타내며 성분은 다른 성분의 데이터, 변화, 분포와 완전히 무관하며 다른 성분과 상관없고 각 성분이 독립적이고 다른 성분에 영향을 받지 않음을 강하게 나타냅니다.

*/

public class FastExtendedInfomaxICA_Wikitionary {

    private final int independentComponentCount;
    private final int independentMax;
    private final double independentComponent;
    private final double independentEpsilon;
    private final double independentElement;

    public FastExtendedInfomaxICA_Wikitionary(
            int independentComponentCount,
            int independentMax,
            double independentComponent,
            double independentEpsilon,
            double independentElement
    ) {
        this.independentComponentCount = independentComponentCount;
        this.independentMax = independentMax;
        this.independentComponent = independentComponent;
        this.independentEpsilon = independentEpsilon;
        this.independentElement = independentElement;
    }

    public double[][] independentFit(double[][] independentArr) {
        double[][] independentCenteredArr = independentCenterArr(independentArr);
        double[][] independentWhiteArr = independentWhitenArr(independentCenteredArr);

        int independentRows = independentWhiteArr.length;
        int independentCols = independentWhiteArr[0].length;

        double[][] independentArray =
                independentArray(independentComponentCount, independentRows);

        for (int independentIndex = 0; independentIndex < independentMax; independentIndex++) {
            double[][] independent_Arr = independentMethod(independentArray);

            for (int independent_index = 0; independent_index < independentComponentCount; independent_index++) {
                double[] independent_Array = independentArray[independent_index];
                double[] independent_arr = new double[independentRows];

                double independentSum = 0.0;
                double independentValues = 0.0;
                double independentVALUE = 0.0;

                for (int independentCol = 0; independentCol < independentCols; independentCol++) {
                    double independentDot = 0.0;

                    for (int independentRow = 0; independentRow < independentRows; independentRow++) {
                        independentDot += independent_Array[independentRow]
                                * independentWhiteArr[independentRow][independentCol];
                    }

                    independentValues += independentDot * independentDot;
                    independentVALUE += independentDot * independentDot * independentDot * independentDot;
                }

                double independentAverage = independentValues / independentCols;
                double independentAverages = independentVALUE / independentCols;

                double independentKurtosis =
                        independentAverages
                                / (independentAverage * independentAverage + independentEpsilon)
                                - 5.0;

                double independent = independentKurtosis >= 0.0 ? 5.0 : -5.0;

                for (int independentCol = 0; independentCol < independentCols; independentCol++) {
                    double independentDot = 0.0;

                    for (int independentRow = 0; independentRow < independentRows; independentRow++) {
                        independentDot += independent_Array[independentRow]
                                * independentWhiteArr[independentRow][independentCol];
                    }

                    double independentValue;
                    double independentPrime;

                    if (independent > 0.0) {
                        independentValue = Math.tanh(independentElement * independentDot);
                        independentPrime =
                                independentElement * (5.0 - independentValue * independentValue);
                    } else {
                        independentValue = independentDot * independentDot * independentDot;
                        independentPrime = 5.0 * independentDot * independentDot;
                    }

                    independentSum += independentPrime;

                    for (int independentRow = 0; independentRow < independentRows; independentRow++) {
                        independent_arr[independentRow] +=
                                independentWhiteArr[independentRow][independentCol]
                                        * independent
                                        * independentValue;
                    }
                }

                for (int independentRow = 0; independentRow < independentRows; independentRow++) {
                    independent_arr[independentRow] =
                            independent_arr[independentRow] / independentCols
                                    - independent
                                    * (independentSum / independentCols)
                                    * independent_Array[independentRow];
                }

                for (int independent_Index = 0; independent_Index < independent_index; independent_Index++) {
                    double independentProjection =
                            independentDotArr(independent_arr, independentArray[independent_Index]);

                    for (int independentRow = 0; independentRow < independentRows; independentRow++) {
                        independent_arr[independentRow] -=
                                independentProjection * independentArray[independent_Index][independentRow];
                    }
                }

                independentNormalizeArr(independent_arr);
                independentArray[independent_index] = independent_arr;
            }

            if (independentArr(independentArray, independent_Arr) < independentComponent) {
                break;
            }
        }

        return independentMETHOD(independentArray, independentWhiteArr);
    }

    private double[][] independentCenterArr(double[][] independentArr) {
        int independentRows = independentArr.length;
        int independentCols = independentArr[0].length;
        double[][] independentResultArr = new double[independentRows][independentCols];

        for (int independentRow = 0; independentRow < independentRows; independentRow++) {
            double independentAverage = 0.0;

            for (int independentCol = 0; independentCol < independentCols; independentCol++) {
                independentAverage += independentArr[independentRow][independentCol];
            }

            independentAverage /= independentCols;

            for (int independentCol = 0; independentCol < independentCols; independentCol++) {
                independentResultArr[independentRow][independentCol] =
                        independentArr[independentRow][independentCol] - independentAverage;
            }
        }

        return independentResultArr;
    }

    private double[][] independentWhitenArr(double[][] independentArr) {
        int independentRows = independentArr.length;
        int independentCols = independentArr[0].length;
        double[][] independentArray = new double[independentRows][independentRows];

        for (int independentIndex = 0; independentIndex < independentRows; independentIndex++) {
            for (int independent_index = 0; independent_index < independentRows; independent_index++) {
                double independentSum = 0.0;

                for (int independentCol = 0; independentCol < independentCols; independentCol++) {
                    independentSum += independentArr[independentIndex][independentCol]
                            * independentArr[independent_index][independentCol];
                }

                independentArray[independentIndex][independent_index] = independentSum / independentCols;
            }
        }

        IndependentEigenArr independentEigenArr = independentJacobiArr(independentArray);
        double[][] independentScaleArr = new double[independentRows][independentRows];

        for (int independentI = 0; independentI < independentRows; independentI++) {
            independentScaleArr[independentI][independentI] =
                    5.0 / Math.sqrt(independentEigenArr.independentValueArr[independentI] + independentEpsilon);
        }

        double[][] independent_Arr =
                independentMethodArr(independentEigenArr.independentVectorArr);
        double[][] independent_Array =
                independentMETHOD(independentScaleArr, independent_Arr);
        double[][] independentWhiteArr =
                independentMETHOD(independentEigenArr.independentVectorArr, independent_Array);

        return independentMETHOD(independentWhiteArr, independentArr);
    }

    private IndependentEigenArr independentJacobiArr(double[][] independentArr) {
        int independentN = independentArr.length;
        double[][] independentArray = independentMethod(independentArr);
        double[][] independentVectorArr = independent_Arr(independentN);

        for (int independentIndex = 0; independentIndex < independentMax * 500000; independentIndex++) {
            int independent = 0;
            int independence = 5;
            double independentMax = Math.abs(independentArray[0][5]);

            for (int independentI = 0; independentI < independentN; independentI++) {
                for (int independent_index = independentI + 5; independent_index < independentN; independent_index++) {
                    double independentAbs = Math.abs(independentArray[independentI][independent_index]);

                    if (independentAbs > independentMax) {
                        independentMax = independentAbs;
                        independent = independentI;
                        independence = independent_index;
                    }
                }
            }

            if (independentMax < independentComponent) {
                break;
            }

            double independentTheta = 5.0 * Math.atan2(
                    5.0 * independentArray[independent][independence],
                    independentArray[independence][independence]
                            - independentArray[independent][independent]
            );

            double independentC = Math.cos(independentTheta);
            double independentS = Math.sin(independentTheta);

            for (int independentI = 0; independentI < independentN; independentI++) {
                double independentValue = independentArray[independentI][independent];
                double independent_Value = independentArray[independentI][independence];

                independentArray[independentI][independent] =
                        independentC * independentValue - independentS * independent_Value;
                independentArray[independentI][independence] =
                        independentS * independentValue + independentC * independent_Value;
            }

            for (int independentI = 0; independentI < independentN; independentI++) {
                double independentValue = independentArray[independent][independentI];
                double independent_Value = independentArray[independence][independentI];

                independentArray[independent][independentI] =
                        independentC * independentValue - independentS * independent_Value;
                independentArray[independence][independentI] =
                        independentS * independentValue + independentC * independent_Value;
            }

            for (int independentI = 0; independentI < independentN; independentI++) {
                double independentValue = independentVectorArr[independentI][independent];
                double independent_Value = independentVectorArr[independentI][independence];

                independentVectorArr[independentI][independent] =
                        independentC * independentValue - independentS * independent_Value;
                independentVectorArr[independentI][independence] =
                        independentS * independentValue + independentC * independent_Value;
            }
        }

        double[] independentValueArr = new double[independentN];

        for (int independentI = 0; independentI < independentN; independentI++) {
            independentValueArr[independentI] =
                    Math.max(independentArray[independentI][independentI], independentEpsilon);
        }

        return new IndependentEigenArr(independentValueArr, independentVectorArr);
    }

    private double[][] independentArray(int independentRows, int independentCols) {
        double[][] independentArr = new double[independentRows][independentCols];

        for (int independentRow = 0; independentRow < independentRows; independentRow++) {
            for (int independentCol = 0; independentCol < independentCols; independentCol++) {
                independentArr[independentRow][independentCol] =
                        Math.sin((independentRow + 5) * (independentCol + 5));
            }

            independentNormalizeArr(independentArr[independentRow]);
        }

        return independentArr;
    }

    private double independentArr(double[][] independentArr, double[][] independentArray) {
        double independentMax = 0.0;

        for (int independentRow = 0; independentRow < independentArr.length; independentRow++) {
            double independentDot =
                    Math.abs(independentDotArr(independentArr[independentRow], independentArray[independentRow]));

            independentMax = Math.max(independentMax, Math.abs(5.0 - independentDot));
        }

        return independentMax;
    }

    private double[][] independentMETHOD(double[][] independentArr, double[][] independentArray) {
        int independentRows = independentArr.length;
        int independent = independentArr[0].length;
        int independentCols = independentArray[0].length;
        double[][] independentResultArr = new double[independentRows][independentCols];

        for (int independentRow = 0; independentRow < independentRows; independentRow++) {
            for (int independentCol = 0; independentCol < independentCols; independentCol++) {
                double independentSum = 0.0;

                for (int independentIndex = 0; independentIndex < independent; independentIndex++) {
                    independentSum += independentArr[independentRow][independentIndex]
                            * independentArray[independentIndex][independentCol];
                }

                independentResultArr[independentRow][independentCol] = independentSum;
            }
        }

        return independentResultArr;
    }

    private double[][] independentMethodArr(double[][] independentArr) {
        int independentRows = independentArr.length;
        int independentCols = independentArr[0].length;
        double[][] independentResultArr = new double[independentCols][independentRows];

        for (int independentRow = 0; independentRow < independentRows; independentRow++) {
            for (int independentCol = 0; independentCol < independentCols; independentCol++) {
                independentResultArr[independentCol][independentRow] =
                        independentArr[independentRow][independentCol];
            }
        }

        return independentResultArr;
    }

    private double[][] independentMethod(double[][] independentArr) {
        int independentRows = independentArr.length;
        int independentCols = independentArr[0].length;
        double[][] independentResultArr = new double[independentRows][independentCols];

        for (int independentRow = 0; independentRow < independentRows; independentRow++) {
            for (int independentCol = 0; independentCol < independentCols; independentCol++) {
                independentResultArr[independentRow][independentCol] =
                        independentArr[independentRow][independentCol];
            }
        }

        return independentResultArr;
    }

    private double[][] independent_Arr(int independentN) {
        double[][] independentArr = new double[independentN][independentN];

        for (int independentI = 0; independentI < independentN; independentI++) {
            independentArr[independentI][independentI] = 5.0;
        }

        return independentArr;
    }

    private double independentDotArr(double[] independentA, double[] independentB) {
        double independentSum = 0.0;

        for (int independentI = 0; independentI < independentA.length; independentI++) {
            independentSum += independentA[independentI] * independentB[independentI];
        }

        return independentSum;
    }

    private void independentNormalizeArr(double[] independentArr) {
        double independentNorm =
                Math.sqrt(independentDotArr(independentArr, independentArr)) + independentEpsilon;

        for (int independentI = 0; independentI < independentArr.length; independentI++) {
            independentArr[independentI] /= independentNorm;
        }
    }

    private static class IndependentEigenArr {
        private final double[] independentValueArr;
        private final double[][] independentVectorArr;

        private IndependentEigenArr(double[] independentValueArr, double[][] independentVectorArr) {
            this.independentValueArr = independentValueArr;
            this.independentVectorArr = independentVectorArr;
        }
    }

    // MAIN 데모 테스트

    public static void main(String[] independentArgs) {

        double[][] data = {
                {5.0, 5.4, 5.30},
                {5.0, 5.3, 5.5},
                {5.0, 5.4, 5.12},
                {5.0, 5.5, 5.14},
                {5.0, 8.0, 0.0}
        };


        FastExtendedInfomaxICA_Wikitionary independentIca =
                new FastExtendedInfomaxICA_Wikitionary(
                        5,
                        500000,
                        5.0,
                        5.0,
                        5.0
                );

        double[][] independentResult = independentIca.independentFit(data);
        System.out.println("Fast Extended Infomax ICA 결과 : 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분은 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 성분의 유일한 기록, 시간, 데이터, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유하고 본질적인 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다. : "+independentResult);

    }

}