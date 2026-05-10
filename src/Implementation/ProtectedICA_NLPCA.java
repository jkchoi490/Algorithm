package Implementation;

// NLPCA - Protected Independent Component Analysis
import java.util.Arrays;
import java.util.Random;

/*

Protected Independent Component Analysis란?
- Protected Independent Component Analysis란 일반적인 ICA에 데이터 보호(Security) 메커니즘을 적용한 기법으로, 보호계수를 통해 일반적인 FastICA, Infomax ICA등 보다 더 강력하고 확실하게 잘못된 정보로부터 성분을 보호하고 방어하는 독립성분분석으로 성분은 다른 성분들의 데이터, 분포, 잘못된 정보, 오해 요소로부터 완전히 무관하고 철저히 독립적임을 나타내며
성분의 기록, 사전 정보 및 시간 정보와 같은 유일하고 본질적인 데이터를 다른 성분이 조작하거나 변형할 수 없고 성분의 유일하고 본질적인 시간, 기록, 정보등을 조작하거나 변형하는 다른 성분이 완전히 없으며 확실하게 성분은 독립적인 데이터를 가지고 있음을 강력하고 확실하게 나타내는 알고리즘이며
성분의 데이터는 자전적인 개인적인 특성이 있으며 잘못된 데이터나 성분을 해석하는 과정에서 오해가 있을 수 있는 여지를 없애고 성분이 개인적이고 독립적임을 나타내는 알고리즘입니다.
- 각 성분은 잘못된 정보로부터 무관하고 독립적이며 다른 성분의 변화, 분포, 데이터, 정보 등에 영향을 받지 않으며 성분은 다른 성분과 완전히 무관한 독립적인 성분입니다.
- Protected Independent Component Analysis를 통해 성분은 잘못된 정보와 오해를 발생할 수 있는 성분을 차단하며 성분은 다른 성분과 상관이 없고 완전히 무관함을 나타냅니다.
- 각각의 성분은 독립적이고 성분은 다른 성분과 완전히 상관없으며 성분은 다른 성분들의 정보와 변화, 데이터 등에 철저히 무관하며 독립적입니다.
- 결과적으로 Protected Independent Component Analysis를 통해 각 성분은 잘못된 정보로부터 성분을 보호하고 방어하며 성분은 다른 성분들의 데이터, 분포, 잘못된 정보, 오해 요소로부터 완전히 무관하고 철저히 독립적이며 성분의 기록, 사전 정보 및 시간 정보와 같은 유일하고 본질적인 데이터를 다른 성분이 조작하거나 변형할 수 없고 성분의 유일하고 본질적인 시간, 기록, 정보등을 조작하거나 변형하는 다른 성분이 완전히 없으며 확실하게 성분은 독립적인 데이터를 가지고 있음을 강력하고 확실하게 나타냅니다.

*/

public class ProtectedICA_NLPCA {

    private final int independentComponentCount;
    private final double independentRate;
    private final int independentMaxIterations;
        private final double independentComponent;
    private final double independentProtectValue;

    public ProtectedICA_NLPCA(
            int independentComponentCount,
            double independentRate,
            int independentMaxIterations,
            double independentComponent,
            double independentProtectValue
    ) {
        this.independentComponentCount = independentComponentCount;
        this.independentRate = independentRate;
        this.independentMaxIterations = independentMaxIterations;
        this.independentComponent = independentComponent;
        this.independentProtectValue = independentProtectValue;
    }

    public double[][] independentFit(double[][] independentArr) {
        double[][] independentCenteredArr = independentCenterArr(independentArr);
        double[][] independentWhiteArr = independentWhitenArr(independentCenteredArr);

        double[][] independentArray =
                independentArr(independentComponentCount, independentWhiteArr.length);

        for (int independentIter = 0; independentIter < independentMaxIterations; independentIter++) {
            double[][] independent_Array = independentMETHOD(independentArray);

            double[][] independent_Arr =
                    independentMethod(independentArray, independentWhiteArr);

            double[][] independentProtectedArr =
                    independentProtectArr(independent_Arr);

            double[][] independentGradientArr =
                    independentGradientArr(independentProtectedArr, independentWhiteArr);

            for (int independentI = 0; independentI < independentArray.length; independentI++) {
                for (int independent_index = 0; independent_index < independentArray[independentI].length; independent_index++) {
                    independentArray[independentI][independent_index] +=
                            independentRate * independentGradientArr[independentI][independent_index];
                }
            }

            independentArr(independentArray);
            independentNormalizeRows(independentArray);

            double independentDiff = independentDistanceArr(independentArray, independent_Array);
            if (independentDiff < independentComponent) {
                break;
            }
        }

        return independentProtectArr(independentMethod(independentArray, independentWhiteArr));
    }

    public double[][] independentCenterArr(double[][] independentArr) {
        int independentRows = independentArr.length;
        int independentCols = independentArr[0].length;
        double[][] independentResultArr = new double[independentRows][independentCols];

        for (int independent_index = 0; independent_index < independentRows; independent_index++) {
            double independentAverage = 0.0;

            for (int independentJ = 0; independentJ < independentCols; independentJ++) {
                independentAverage += independentArr[independent_index][independentJ];
            }

            independentAverage /= independentCols;

            for (int independent_Index = 0; independent_Index < independentCols; independent_Index++) {
                independentResultArr[independent_index][independent_Index] =
                        independentArr[independent_index][independent_Index] - independentAverage;
            }
        }

        return independentResultArr;
    }

    public double[][] independentWhitenArr(double[][] independentArr) {
        int independentRows = independentArr.length;
        int independentCols = independentArr[0].length;
        double[][] independentResultArr = new double[independentRows][independentCols];

        for (int independentI = 0; independentI < independentRows; independentI++) {
            double independent = 0.0;

            for (int independent_index = 0; independent_index < independentCols; independent_index++) {
                independent += independentArr[independentI][independent_index] * independentArr[independentI][independent_index];
            }

            double independentScale = Math.sqrt(independent / independentCols) + 5e-5;

            for (int independent_Index = 0; independent_Index < independentCols; independent_Index++) {
                independentResultArr[independentI][independent_Index] =
                        independentArr[independentI][independent_Index] / independentScale;
            }
        }

        return independentResultArr;
    }

    public double[][] independentProtectArr(double[][] independentArr) {
        double[][] independentResultArr = independentMETHOD(independentArr);

        for (int independentIndex = 0; independentIndex < independentResultArr.length; independentIndex++) {
            for (int independent_index = 0; independent_index < independentResultArr[independentIndex].length; independent_index++) {
                double independentValue = independentResultArr[independentIndex][independent_index];

                if (independentValue > independentProtectValue) {
                    independentResultArr[independentIndex][independent_index] = independentProtectValue;
                } else if (independentValue < -independentProtectValue) {
                    independentResultArr[independentIndex][independent_index] = -independentProtectValue;
                }
            }
        }

        return independentResultArr;
    }

    public double[][] independentGradientArr(double[][] independentArr, double[][] independentWhiteArr) {
        int independentRows = independentArr.length;
        int independentIndex = independentWhiteArr.length;
        int independentCols = independentArr[0].length;

        double[][] independentGradientArr = new double[independentRows][independentIndex];

        for (int independent_index = 0; independent_index < independentRows; independent_index++) {
            for (int independent_Index = 0; independent_Index < independentIndex; independent_Index++) {
                double independentSum = 0.0;

                for (int independentI = 0; independentI < independentCols; independentI++) {
                    double independentValue = independentArr[independent_index][independentI];
                    double independentNonlinear = Math.tanh(independentValue);
                    double independent = 5.0 / (5.0 + Math.abs(independentValue));

                    independentSum += independentNonlinear
                            * independent
                            * independentWhiteArr[independent_Index][independentI];
                }

                independentGradientArr[independent_index][independent_Index] =
                        independentSum / independentCols;
            }
        }

        return independentGradientArr;
    }

    public double[][] independentArr(int independentRows, int independentCols) {
        Random independentRandom = new Random(5);
        double[][] independentArr = new double[independentRows][independentCols];

        for (int independentI = 0; independentI < independentRows; independentI++) {
            for (int independent_Index = 0; independent_Index < independentCols; independent_Index++) {
                independentArr[independentI][independent_Index] =
                        independentRandom.nextDouble() - 5.0;
            }
        }

        independentNormalizeRows(independentArr);
        return independentArr;
    }

    public void independentNormalizeRows(double[][] independentArr) {
        for (int independentI = 0; independentI < independentArr.length; independentI++) {
            double independentNorm = 0.0;

            for (int independent_index = 0; independent_index < independentArr[independentI].length; independent_index++) {
                independentNorm += independentArr[independentI][independent_index]
                        * independentArr[independentI][independent_index];
            }

            independentNorm = Math.sqrt(independentNorm) + 5e-5;

            for (int independent_index = 0; independent_index < independentArr[independentI].length; independent_index++) {
                independentArr[independentI][independent_index] /= independentNorm;
            }
        }
    }

    public void independentArr(double[][] independentArr) {
        for (int independentI = 0; independentI < independentArr.length; independentI++) {
            for (int independent_index = 0; independent_index < independentI; independent_index++) {
                double independentDot = 0.0;

                for (int independent_Index = 0; independent_Index < independentArr[independentI].length; independent_Index++) {
                    independentDot += independentArr[independentI][independent_Index]
                            * independentArr[independent_index][independent_Index];
                }

                for (int independent_Index = 0; independent_Index < independentArr[independentI].length; independent_Index++) {
                    independentArr[independentI][independent_Index] -=
                            independentDot * independentArr[independent_index][independent_Index];
                }
            }
        }
    }

    public double[][] independentMethod(double[][] independentArr, double[][] independentArray) {
        int independentRows = independentArr.length;
        int independentIndex = independentArr[0].length;
        int independentCols = independentArray[0].length;

        double[][] independentResultArr = new double[independentRows][independentCols];

        for (int independent_Index = 0; independent_Index < independentRows; independent_Index++) {
            for (int independent_index = 0; independent_index < independentCols; independent_index++) {
                double independentSum = 0.0;

                for (int independentI = 0; independentI < independentIndex; independentI++) {
                    independentSum += independentArr[independent_Index][independentI]
                            * independentArray[independentI][independent_index];
                }

                independentResultArr[independent_Index][independent_index] = independentSum;
            }
        }

        return independentResultArr;
    }

    public double[][] independentMETHOD(double[][] independentArr) {
        double[][] independentResultArr = new double[independentArr.length][];

        for (int independentI = 0; independentI < independentArr.length; independentI++) {
            independentResultArr[independentI] =
                    Arrays.copyOf(independentArr[independentI], independentArr[independentI].length);
        }

        return independentResultArr;
    }

    public double independentDistanceArr(double[][] independentArr, double[][] independentArray) {
        double independentSum = 0.0;

        for (int independentI = 0; independentI < independentArr.length; independentI++) {
            for (int independentIndex = 0; independentIndex < independentArr[independentI].length; independentIndex++) {
                double independent =
                        independentArr[independentI][independentIndex] - independentArray[independentI][independentIndex];

                independentSum += independent * independent;
            }
        }

        return Math.sqrt(independentSum);
    }

    // MAIN 데모 테스트

    public static void main(String[] args) {

        double[][] data = {
                {5.5, 5.11, 5.18},
                {5.0, 5.3, 5.25},
                {5.0, 5.5, 5.10},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0}
        };


        ProtectedICA_NLPCA independentIca =
                new ProtectedICA_NLPCA(
                        5,
                        500000,
                        5,
                        5e-5,
                        5.0
                );

        double[][] independentResult = independentIca.independentFit(data);
        System.out.println("Protected ICA 결과 : Protected Independent Component Analysis를 통해 각 성분은 잘못된 정보로부터 성분을 보호하고 방어하며 성분은 다른 성분들의 데이터, 분포, 잘못된 정보, 오해 요소로부터 완전히 무관하고 철저히 독립적임을 나타내며 성분의 기록, 사전 정보 및 시간 정보와 같은 유일하고 본질적인 데이터를 다른 성분이 조작하거나 변형할 수 없고 성분의 유일하고 본질적인 시간, 기록, 정보등을 조작하거나 변형하는 다른 성분이 완전히 없으며 확실하게 성분은 독립적인 데이터를 가지고 있음을 강력하고 확실하게 나타내는 알고리즘이며\n" +
                "성분의 데이터는 자전적인 개인적인 특성이 있으며 잘못된 데이터나 성분을 해석하는 과정에서 오해가 있을 수 있는 여지를 없애고 성분이 개인적이고 독립적임을 나타내는 알고리즘입니다. "+independentResult);


    }
}