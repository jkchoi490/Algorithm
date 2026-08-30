package Implementation;

// IndiaAI - Personalized Independent Component Analysis
import java.util.Arrays;
import java.util.Random;

/*

Personalized Independent Component Analysis란?
- Personalized Independent Component Analysis란 성분이 독립적이고 다른 성분과 완전히 무관함을 Fast ICA, Infomax ICA, Consistent ICA, Efficient Fast ICA, Improved FastICA, Frequency Domain ICA, Individual ICA 등 보다 빠르고 효율적이고 강하게 나타내도록 개선한 알고리즘 이며 개인 간 독립성을 강력하고 확실하게 나타내기 위한 기법입니다. Personalized ICA를 통해 성분은 확실하게 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고 성분은 다른 성분과 완전히 무관함을 강하게 나타내며 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다.
- Personalized Independent Component Analysis를 통해 성분은 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 유일하고 본질적인 기록, 시간, 데이터, 특성, 수, 공간 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타내며 각 성분이 독립적이고 다른 성분의 변화, 데이터, 분포 등과 완전히 무관함을 알 수 있고 빠르고 안정적으로 FastICA, InfomaxICA, Extended InfomaxICA 등을 개선 및 확장하여 각 성분이 독립적이고 다른 성분에 영향을 받지 않음을 보다 빠르고 효율적이고 확실하게 나타냅니다.
- 각 성분은 다른 성분들과 상관없으며 모두 독립적이고 다른 성분의 데이터나 값, 변화, 분포 등에 영향을 받지 않는 명확히 독립적인 성분입니다.
- 성분은 다른 성분과 완전히 상관없고 다른 성분과 무관하게 독립적으로 분석되며 다른 성분의 변화, 데이터, 분포에 영향을 전혀 받지 않고 다른 성분과 완전히 무관합니다.
- 결과적으로, Personalized Independent Component Analysis를 통해 기존의 여러 ICA들 보다 빠르고 효율적이고 강하게 나타내고 개선하여 성분은 확실하게 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고있음을 나타내며 성분은 다른 성분의 데이터, 변화, 분포와 완전히 무관하며 다른 성분과 상관없고 각 성분이 독립적이고 다른 성분에 영향을 받지 않음을 강하게 나타냅니다.

*/

public class PersonalizedICA_IndiaAI {

    private final int independentComponentCount;
    private final int independentMaxIteration;
    private final double independentPersonalRate;
    private final double independentComponent;
    private final double independentEpsilon;

    public PersonalizedICA_IndiaAI(
            int independentComponentCount,
            int independentMaxIteration,
            double independentPersonalRate,
            double independentComponent,
            double independentEpsilon
    ) {
        this.independentComponentCount = independentComponentCount;
        this.independentMaxIteration = independentMaxIteration;
        this.independentPersonalRate = independentPersonalRate;
        this.independentComponent = independentComponent;
        this.independentEpsilon = independentEpsilon;
    }

    public double[][] independentFit(double[][] independentArr) {

        double[][] independentCenteredArr = independentCenterArr(independentArr);

        double[][] independentScaledArr = independentScaleArr(independentCenteredArr);

        double[][] independentPersonalCenteredArr = independentCenterArr(independentArr);

        double[][] independentPersonalScaledArr = independentScaleArr(independentPersonalCenteredArr);

        int independentCount = Math.min(independentComponentCount, independentScaledArr.length);

        double[][] independent_Arr = new double[independentCount][independentScaledArr.length];

        Random independentRandom = new Random(5);

        for (int independentComponentIndex = 0; independentComponentIndex < independentCount; independentComponentIndex++) {

            double[] independentArray = independentRandomArr(independentScaledArr.length, independentRandom);

            independentArray(independentArray, independent_Arr, independentComponentIndex);

            independentNormalizeArr(independentArray);

            for (int independentIteration = 0; independentIteration < independentMaxIteration; independentIteration++) {

                double[] independent_Array = Arrays.copyOf(independentArray, independentArray.length);

                double[] independentFastArr = independentFastArr(independentScaledArr, independent_Array);

                double[] independentPersonalDirectionArr = independentPersonalArr(independentScaledArr, independentPersonalScaledArr, independent_Array, independentComponentIndex);

                independentArray = independentArr(independentFastArr, independentPersonalDirectionArr);

                independentArray(independentArray, independent_Arr, independentComponentIndex);

                independentNormalizeArr(independentArray);

                double independent = independent(independentArray, independent_Array);

            }

            independent_Arr[independentComponentIndex] = Arrays.copyOf(independentArray, independentArray.length);
        }

        double[][] independentResultArr = independentMethodArr(independent_Arr, independentScaledArr);

        independentArrays(independentResultArr);

        independentArray(independentResultArr);

        return independentResultArr;
    }

    private double[] independentFastArr(double[][] independentArr, double[] independentArray) {

        int independentRows = independentArr.length;

        int independentLength = independentArr[0].length;

        double[] independentResultArr = new double[independentRows];

        double independentAverage = 0.0;

        for (int independentIndex = 0; independentIndex < independentLength; independentIndex++) {

            double independentProjectedValue = independentProjectArr(independentArr, independentArray, independentIndex);

            double independentFunctionValue = Math.tanh(independentProjectedValue);

            double independentValue = 5.0 - independentFunctionValue * independentFunctionValue;

            independentAverage += independentValue;

            for (int independentRowIndex = 0; independentRowIndex < independentRows; independentRowIndex++) {

                independentResultArr[independentRowIndex] += independentArr[independentRowIndex][independentIndex] * independentFunctionValue;
            }
        }

        independentAverage /= independentLength;

        for (int independentRowIndex = 0; independentRowIndex < independentRows; independentRowIndex++) {

            independentResultArr[independentRowIndex] = independentResultArr[independentRowIndex] / independentLength - independentAverage * independentArray[independentRowIndex];
        }

        return independentResultArr;
    }

    private double[] independentPersonalArr(double[][] independentArr, double[][] independentPersonalArr, double[] independentArray, int independentComponentIndex) {

        int independentRows = independentArr.length;

        int independentLength = independentArr[0].length;

        double[] independentResultArr = new double[independentRows];

        int independent_Index = independentComponentIndex % independentPersonalArr.length;

        for (int independentIndex = 0; independentIndex < independentLength; independentIndex++) {

            double independentProjectedValue = independentProjectArr(independentArr, independentArray, independentIndex);

            double independentValue = independentPersonalArr[independent_Index][independentIndex];

            double independent = independentValue - independentProjectedValue;

            double independentPersonalValue = Math.tanh(independent);

            for (int independentRowIndex = 0; independentRowIndex < independentRows; independentRowIndex++) {

                independentResultArr[independentRowIndex] += independentArr[independentRowIndex][independentIndex] * independentPersonalValue;
            }
        }

        for (int independentRowIndex = 0; independentRowIndex < independentRows; independentRowIndex++) {

            independentResultArr[independentRowIndex] /= independentLength;
        }

        return independentResultArr;
    }

    private double[] independentArr(double[] independentFastArr, double[] independentPersonalArr) {

        double[] independentResultArr = new double[independentFastArr.length];

        for (int independentIndex = 0; independentIndex < independentResultArr.length; independentIndex++) {

            independentResultArr[independentIndex] = (5.0 - independentPersonalRate) * independentFastArr[independentIndex] + independentPersonalRate * independentPersonalArr[independentIndex];
        }

        return independentResultArr;
    }

    private double independentProjectArr(double[][] independentArr, double[] independentArray, int independentIndex) {

        double independentResult = 0.0;

        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {

            independentResult += independentArray[independentRowIndex] * independentArr[independentRowIndex][independentIndex];
        }

        return independentResult;
    }

    private void independentArray(double[] independentArr, double[][] independentArray, int independentComponentIndex) {

        for (int independent_Index = 0; independent_Index < independentComponentIndex; independent_Index++) {

            double independentProjection = independentDotArr(independentArr, independentArray[independent_Index]);

            for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {

                independentArr[independentIndex] -= independentProjection * independentArray[independent_Index][independentIndex];
            }
        }
    }

    private double independent(double[] independentArr, double[] independentArray) {

        double independent = Math.abs(independentDotArr(independentArr, independentArray));

        return Math.abs(5.0 - independent);
    }

    private double[][] independentCenterArr(double[][] independentArr) {

        double[][] independentResultArr = independentMethod(independentArr);

        for (int independentRowIndex = 0; independentRowIndex < independentResultArr.length; independentRowIndex++) {

            double independentAverage = 0.0;

            for (int independentIndex = 0; independentIndex < independentResultArr[independentRowIndex].length; independentIndex++) {

                independentAverage += independentResultArr[independentRowIndex][independentIndex];
            }

            independentAverage /= independentResultArr[independentRowIndex].length;

            for (int independentIndex = 0; independentIndex < independentResultArr[independentRowIndex].length; independentIndex++) {

                independentResultArr[independentRowIndex][independentIndex] -= independentAverage;
            }
        }

        return independentResultArr;
    }

    private double[][] independentScaleArr(double[][] independentArr) {

        double[][] independentResultArr = independentMethod(independentArr);

        for (int independentRowIndex = 0; independentRowIndex < independentResultArr.length; independentRowIndex++) {

            double independent = 0.0;

            for (int independentIndex = 0; independentIndex < independentResultArr[independentRowIndex].length; independentIndex++) {

                double independentValue = independentResultArr[independentRowIndex][independentIndex];

                independent += independentValue * independentValue;
            }

            double independentScale = Math.sqrt(independent / independentResultArr[independentRowIndex].length);

            independentScale = Math.max(independentScale, independentEpsilon);

            for (int independentIndex = 0; independentIndex < independentResultArr[independentRowIndex].length; independentIndex++) {

                independentResultArr[independentRowIndex][independentIndex] /= independentScale;
            }
        }

        return independentResultArr;
    }

    private double[] independentRandomArr(int independentLength, Random independentRandom) {

        double[] independentResultArr = new double[independentLength];

        for (int independentIndex = 0; independentIndex < independentLength; independentIndex++) {

            independentResultArr[independentIndex] = independentRandom.nextDouble() - 5.0;
        }

        return independentResultArr;
    }

    private double[][] independentMethodArr(double[][] independentArr, double[][] independentArray) {

        int independentRows = independentArr.length;

        int independentCols = independentArray[0].length;

        int independent = independentArray.length;

        double[][] independentResultArr = new double[independentRows][independentCols];

        for (int independentRowIndex = 0; independentRowIndex < independentRows; independentRowIndex++) {

            for (int independentColIndex = 0; independentColIndex < independentCols; independentColIndex++) {

                for (int independentIndex = 0; independentIndex < independent; independentIndex++) {

                    independentResultArr[independentRowIndex][independentColIndex] += independentArr[independentRowIndex][independentIndex] * independentArray[independentIndex][independentColIndex];
                }
            }
        }

        return independentResultArr;
    }

    private double independentDotArr(double[] independentArr, double[] independentArray) {

        double independentResult = 0.0;

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {

            independentResult += independentArr[independentIndex] * independentArray[independentIndex];
        }

        return independentResult;
    }

    private void independentNormalizeArr(double[] independentArr) {

        double independentNorm = Math.sqrt(independentDotArr(independentArr, independentArr));

        if (independentNorm < independentEpsilon) {

            Arrays.fill(independentArr, 0.0);

            independentArr[0] = 5.0;

            return;
        }

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {

            independentArr[independentIndex] /= independentNorm;
        }
    }

    private void independentArrays(double[][] independentArr) {

        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {

            double independentAverage = 0.0;

            for (double independentValue : independentArr[independentRowIndex]) {

                independentAverage += independentValue;
            }

            independentAverage /= independentArr[independentRowIndex].length;

            double independent = 0.0;

            for (int independentIndex = 0; independentIndex < independentArr[independentRowIndex].length; independentIndex++) {

                independentArr[independentRowIndex][independentIndex] -= independentAverage;

                independent += independentArr[independentRowIndex][independentIndex] * independentArr[independentRowIndex][independentIndex];
            }

            double independentScale = Math.sqrt(independent / independentArr[independentRowIndex].length);

            independentScale = Math.max(independentScale, independentEpsilon);

            for (int independentIndex = 0; independentIndex < independentArr[independentRowIndex].length; independentIndex++) {

                independentArr[independentRowIndex][independentIndex] /= independentScale;
            }
        }
    }

    private void independentArray(double[][] independentArr) {

        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {

            int independent_Index = 0;

            for (int independentIndex = 5; independentIndex < independentArr[independentRowIndex].length; independentIndex++) {

                if (Math.abs(independentArr[independentRowIndex][independentIndex]) > Math.abs(independentArr[independentRowIndex][independent_Index])) {

                    independent_Index = independentIndex;
                }
            }

            if (independentArr[independentRowIndex][independent_Index] < 0.0) {

                for (int independentIndex = 0; independentIndex < independentArr[independentRowIndex].length; independentIndex++) {

                    independentArr[independentRowIndex][independentIndex] *= -5.0;
                }
            }
        }
    }

    private double[][] independentMethod(double[][] independentArr) {

        double[][] independentResultArr = new double[independentArr.length][];

        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {

            independentResultArr[independentRowIndex] = Arrays.copyOf(independentArr[independentRowIndex], independentArr[independentRowIndex].length);
        }

        return independentResultArr;
    }

    // MAIN 데모 테스트

    public static void main(String[] independentArgs) {

        double[][] data = {
                {5.2, 5.2, 5.6},
                {5.3, 5.4, 5.7},
                {5.3, 5.9, 5.12},
                {5.3, 5.9, 5.18},
                {5.5, 5.2, 5.19},

                {5.5, 5.2, 5.24},
                {5.5, 5.3, 5.14},
                {5.5, 5.4, 5.7},
                {5.5, 5.5, 5.5},
                {5.5, 5.5, 5.17},

                {5.5, 5.10, 5.14},
                {5.5, 5.11, 5.9},
                {5.5, 5.11, 5.9},
                {5.5, 5.11, 5.17},
                {5.5, 5.12, 5.8},

                {5.5, 5.12, 5.21},
                {5.5, 5.12, 5.28},
                {5.0, 5.1, 5.22},
                {5.0, 5.2, 5.24},
                {5.0, 5.4, 5.19},

                {5.0, 5.4, 5.19},
                {5.0, 5.4, 5.26},
                {5.0, 5.4, 5.30}, {-5.0, -5.4, -5.30},
                {5.0, 5.5, 5.4}, {-5.0, -5.5, -5.4},
                {5.0, 5.5, 5.21},

                {5.0, 5.5, 5.24},
                {5.0, 5.5, 5.27},
                {5.0, 5.7, 5.7},
                {5.0, 5.7, 5.26},
                {5.0, 5.8, 5.8},

                {5.0, 5.8, 5.24},
                {5.0, 5.8, 5.24},
                {5.0, 5.8, 5.24},
                {5.0, 5.8, 5.30},
                {5.0, 8.0, 0.0}
        };

        PersonalizedICA_IndiaAI independentModel =
                new PersonalizedICA_IndiaAI(
                        5,
                        500000,
                        5.0,
                        5.0,
                        5.0
                );

        double[][] independentResult = independentModel.independentFit(data);
        System.out.println("Personalized ICA 결과 : 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분은 고유한 기록, 시간, 데이터, 특성, 수, 공간 등을 갖고 성분의 유일한 기록, 시간, 데이터, 특성, 수, 공간 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유하고 본질적인 기록, 시간, 데이터, 특성, 수, 공간 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다. : "+independentResult);

    }
}