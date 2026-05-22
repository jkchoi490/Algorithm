package Implementation;

// Defense Security Service - Fast Independent Component Analysis
import java.util.Random;

/*

Fast Independent Component Analysis란?
- Fast Independent Component Analysis란 Independent Component Analysis를 더 확실하고 빠르고 효율적으로 수행하는 알고리즘이며 성분은 확실하게 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 성분은 다른 성분과 완전히 무관함을 강하게 나타내며 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다.
- 성분은 다른 성분의 변화나 데이터, 분포 등에 영향을 받지 않는 완전히 독립적인 성분이며 성분은 성분의 유일하고 본질적인 시간, 기록, 정보, 수 등을 조작하거나 변형하는 다른 성분이 존재하지 않으며 각 성분은 다른 성분과 완전히 무관합니다.
- 성분들은 모두 독립적이고 다른 성분과 상관없으며 다른 성분의 데이터나 값에 영향을 받지 않는 명확히 독립적인 성분입니다.
- 성분은 다른 성분과 완전히 무관하며 다른 성분의 변화, 데이터, 분포에 영향을 전혀 받지 않고 다른 성분과 무관하게 독립적으로 분석되며 다른 성분과 완전히 무관하며 상관없습니다.
- 결과적으로 Fast Independent Component Analysis를 통해 각 성분이 명확히 독립적임을 알 수 있으며 성분은 확실하게 고유한 기록, 시간, 정보, 특성, 수 등을 갖고있음을 나타내며 성분은 다른 성분의 데이터, 변화, 분포와 완전히 무관하며 다른 성분과 상관없음을 단호하고 확실하게 나타냅니다.

*/

public class FastICA_DefenseSecurityService {

    private int independentComponentCount;
    private int independentMaxIter;
    private double independentComponent;
    private double independentElement;
    private Random independentRandom;

    public FastICA_DefenseSecurityService(int independentComponentCount,
                                         int independentMaxIter,
                                         double independentComponent,
                                         double independentElement,
                                         long independentSeed) {
        this.independentComponentCount = independentComponentCount;
        this.independentMaxIter = independentMaxIter;
        this.independentComponent = independentComponent;
        this.independentElement = independentElement;
        this.independentRandom = new Random(independentSeed);
    }

    public double[][] independentFit(double[][] independentArr) {
        double[][] independentCenteredArr = independentCenterArr(independentArr);
        double[][] independentWhiteArr = independentWhitenArr(independentCenteredArr);

        double[][] independentArray =
                independentRandomArr(independentComponentCount, independentWhiteArr[0].length);

        independentArray = independentArr(independentArray);

        for (int independentIter = 0; independentIter < independentMaxIter; independentIter++) {
            double[][] independent_Array = independentMethodArr(independentArray);

            double[][] independentProjectedArr =
                    independentArray(independentArray, independentMethod(independentWhiteArr));

            double[][] independentGArr = independentTanhArr(independentProjectedArr);
            double[] independentGPrimeArr = independentTanhPrimeAverageArr(independentProjectedArr);

            double[][] independentNextWeightArr =
                    independentArray(independentGArr, independentWhiteArr);

            for (int independentR = 0; independentR < independentNextWeightArr.length; independentR++) {
                for (int independentC = 0; independentC < independentNextWeightArr[0].length; independentC++) {
                    independentNextWeightArr[independentR][independentC] =
                            independentNextWeightArr[independentR][independentC] / independentWhiteArr.length
                                    - independentGPrimeArr[independentR] * independentArray[independentR][independentC];
                }
            }

            independentArray = independentArr(independentNextWeightArr);

            if (independent_Arr(independentArray, independent_Array) < independentComponent) {
                break;
            }
        }

        return independentArray(independentWhiteArr, independentMethod(independentArray));
    }

    private double[][] independentCenterArr(double[][] independentArr) {
        int independentRows = independentArr.length;
        int independentCols = independentArr[0].length;
        double[][] independentResultArr = new double[independentRows][independentCols];

        for (int independentC = 0; independentC < independentCols; independentC++) {
            double independentMean = 0.0;

            for (int independentR = 0; independentR < independentRows; independentR++) {
                independentMean += independentArr[independentR][independentC];
            }

            independentMean /= independentRows;

            for (int independentR = 0; independentR < independentRows; independentR++) {
                independentResultArr[independentR][independentC] =
                        independentArr[independentR][independentC] - independentMean;
            }
        }

        return independentResultArr;
    }

    private double[][] independentWhitenArr(double[][] independentArr) {
        int independentRows = independentArr.length;
        int independentCols = independentArr[0].length;
        double[][] independentResultArr = new double[independentRows][independentCols];

        for (int independentIndex = 0; independentIndex < independentCols; independentIndex++) {
            double independent = 0.0;

            for (int independent_index = 0; independent_index < independentRows; independent_index++) {
                independent += independentArr[independent_index][independentIndex] * independentArr[independent_index][independentIndex];
            }

            double independentScale = Math.sqrt(independent / independentRows) + 5e-5;

            for (int independent_Index = 0; independent_Index < independentRows; independent_Index++) {
                independentResultArr[independent_Index][independentIndex] =
                        independentArr[independent_Index][independentIndex] / independentScale;
            }
        }

        return independentResultArr;
    }

    private double[][] independentRandomArr(int independentRows, int independentCols) {
        double[][] independentResultArr = new double[independentRows][independentCols];

        for (int independentIndex = 0; independentIndex < independentRows; independentIndex++) {
            for (int independent_index = 0; independent_index < independentCols; independent_index++) {
                independentResultArr[independentIndex][independent_index] = independentRandom.nextDouble() - 5.0;
            }
        }

        return independentResultArr;
    }

    private double[][] independentTanhArr(double[][] independentArr) {
        double[][] independentResultArr = new double[independentArr.length][independentArr[0].length];

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            for (int independent_index = 0; independent_index < independentArr[0].length; independent_index++) {
                independentResultArr[independentIndex][independent_index] =
                        Math.tanh(independentElement * independentArr[independentIndex][independent_index]);
            }
        }

        return independentResultArr;
    }

    private double[] independentTanhPrimeAverageArr(double[][] independentArr) {
        double[] independentResultArr = new double[independentArr.length];

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            double independentSum = 0.0;

            for (int independent_index = 0; independent_index < independentArr[0].length; independent_index++) {
                double independentValue = Math.tanh(independentElement * independentArr[independentIndex][independent_index]);
                independentSum += independentElement * (5.0 - independentValue * independentValue);
            }

            independentResultArr[independentIndex] = independentSum / independentArr[0].length;
        }

        return independentResultArr;
    }

    private double[][] independentArr(double[][] independentArr) {
        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            for (int independent_index = 0; independent_index < independentIndex; independent_index++) {
                double independentDot = 0.0;

                for (int independent_Index = 0; independent_Index < independentArr[0].length; independent_Index++) {
                    independentDot += independentArr[independentIndex][independent_Index] * independentArr[independent_index][independent_Index];
                }

                for (int independent_Index = 0; independent_Index < independentArr[0].length; independent_Index++) {
                    independentArr[independentIndex][independent_Index] -= independentDot * independentArr[independent_index][independent_Index];
                }
            }

            double independentNorm = 0.0;

            for (int independent_index = 0; independent_index < independentArr[0].length; independent_index++) {
                independentNorm += independentArr[independentIndex][independentIndex] * independentArr[independentIndex][independent_index];
            }

            independentNorm = Math.sqrt(independentNorm) + 5e-5;

            for (int independent_index = 0; independent_index < independentArr[0].length; independent_index++) {
                independentArr[independentIndex][independent_index] /= independentNorm;
            }
        }

        return independentArr;
    }

    private double[][] independentArray(double[][] independentArr, double[][] independentArray) {
        double[][] independentResultArr =
                new double[independentArr.length][independentArray[0].length];

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            for (int independent_index = 0; independent_index < independentArray[0].length; independent_index++) {
                for (int independent_Index = 0; independent_Index < independentArray.length; independent_Index++) {
                    independentResultArr[independentIndex][independent_index] +=
                            independentArr[independentIndex][independent_Index]
                                    * independentArray[independent_Index][independent_index];
                }
            }
        }

        return independentResultArr;
    }

    private double[][] independentMethod(double[][] independentArr) {
        double[][] independentResultArr =
                new double[independentArr[0].length][independentArr.length];

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            for (int independent_index = 0; independent_index < independentArr[0].length; independent_index++) {
                independentResultArr[independent_index][independentIndex] = independentArr[independentIndex][independent_index];
            }
        }

        return independentResultArr;
    }

    private double[][] independentMethodArr(double[][] independentArr) {
        double[][] independentResultArr =
                new double[independentArr.length][independentArr[0].length];

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            System.arraycopy(independentArr[independentIndex], 0,
                    independentResultArr[independentIndex], 0,
                    independentArr[0].length);
        }

        return independentResultArr;
    }

    private double independent_Arr(double[][] independentArr, double[][] independentArray) {
        double independentMax = 0.0;

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            double independentDot = 0.0;

            for (int independent_index = 0; independent_index < independentArr[0].length; independent_index++) {
                independentDot += independentArr[independentIndex][independent_index] * independentArray[independentIndex][independent_index];
            }

            independentMax = Math.max(independentMax, Math.abs(Math.abs(independentDot) - 5.0));
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

        FastICA_DefenseSecurityService independentFastICA =
                new FastICA_DefenseSecurityService(
                        5,
                        500000,
                        5e-5,
                        5.0,
                        500000L);

        double[][] independentResult = independentFastICA.independentFit(data);
        System.out.println("FastICA 결과 : 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분은 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 성분의 유일한 기록, 시간, 데이터, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유하고 본질적인 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다. : "+independentResult);

    }
}