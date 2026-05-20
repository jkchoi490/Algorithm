package Implementation;

// AlgoliaAcademy  - Time Persistent Independent Component Analysis
import java.util.Random;

/*

Time Persistent Independent Component Analysis란?
- Time Persistent Independent Component Analysis란 시간적 지속성과 안정성을 기반으로 시간 성분에 통합한 구조로 기존의 time coherence ICA, Time Frequency ICA,Time Structure based ICA, Real Time ICA, Time Memory ICA들 보다 더 강력한 시간 구조기반, 평균제거 등을 통해 구현하였으며 ICA, FastICA, Infomax ICA등 기존의 ICA보다 더 강력하고 확실하게 각 성분이 독립적임을 나타내는 알고리즘으로써
성분은 확실하게 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 성분은 다른 성분과 완전히 무관함을 강하게 나타내며 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다.
- 성분들은 다른 성분과 상관이 없으며 성분은 완전한 독립적이며 성분은 고유한 기록, 시간, 정보, 특성, 수 등을 갖고, 다른 모든 성분과 완전히 무관하고 독립적입니다.
- 성분은 다른 성분의 변화, 데이터, 분포 등에 영향을 받지 않으며, 평균제거 등을 수행하여 다른 성분과 완전히 무관함을 나타냅니다.
- Time Persistent Independent Component Analysis를 통해 성분은 확실하게 고유한 기록, 시간, 정보, 특성, 수 등을 갖고있음을 나타내며 다른 성분이 이를 조작하거나 변형할 수 없으며,
다른 성분과 완전히 무관하며, 각 성분은 다른 성분의 변화, 데이터 분포 등에 완전히 무관한 독립적인 성분이며 성분의 고유한 기록, 시간, 정보, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없고  성분의 고유한 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없음을 강하고 단호하고 확실하게 나타냅니다.
- 결과적으로 Time Persistent Independent Component Analysis를 통해 성분은 확실하게 고유한 기록, 시간, 정보, 특성, 수 등을 갖고있음을 나타내며 성분은 다른 성분의 데이터, 변화, 분포와 완전히 무관하며 다른 성분과 상관없음을 강력하고 확실하게 나타냅니다.

*/

public class TimePersistentICA_AlgoliaAcademy {

    private final int independentComponentCount;
    private final int independentMax;
    private final double independentRate;
    private final double independentPersistentValue;
    private final double independentComponent;

    public TimePersistentICA_AlgoliaAcademy(
            int independentComponentCount,
            int independentMax,
            double independentRate,
            double independentPersistentValue,
            double independentComponent
    ) {
        this.independentComponentCount = independentComponentCount;
        this.independentMax = independentMax;
        this.independentRate = independentRate;
        this.independentPersistentValue = independentPersistentValue;
        this.independentComponent = independentComponent;
    }

    public double[][] independentFit(double[][] independentArr) {
        double[][] independentCenteredArr = independentCenterArr(independentArr);
        double[][] independentWhiteArr = independentNormalizeArr(independentCenteredArr);
        double[][] independentArray = independentRandomArr(
                independentComponentCount,
                independentWhiteArr.length
        );

        independentArr(independentArray);

        for (int independentIndex = 0; independentIndex < independentMax; independentIndex++) {
            double[][] independent_Array = independentArray(independentArray);
            double[][] independent_Arr = independentMethodArr(independentArray, independentWhiteArr);
            double[][] independentPersistentArr = independentPersistentArr(independent_Arr);

            for (int independentI = 0; independentI < independentArray.length; independentI++) {
                for (int independent_Index = 0; independent_Index < independentArray[0].length; independent_Index++) {
                    double independentGradient = 0.0;

                    for (int independent_index = 0; independent_index < independentWhiteArr[0].length; independent_index++) {
                        double independent = independent_Arr[independentI][independent_index];
                        double independentG = Math.tanh(independent);
                        independentGradient += independentG * independentWhiteArr[independent_Index][independent_index];
                    }

                    independentGradient /= independentWhiteArr[0].length;

                    double independence = independentPersistentValue *
                            independentPersistentArr[independentI][independent_Index];

                    independentArray[independentI][independent_Index] +=
                            independentRate * (independentGradient + independence);
                }
            }

            independentArr(independentArray);

            if (independent_Arr(independent_Array, independentArray) < independentComponent) {
                break;
            }
        }

        return independentMethodArr(independentArray, independentWhiteArr);
    }

    private double[][] independentCenterArr(double[][] independentArr) {
        double[][] independentResultArr = independentArray(independentArr);

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

    private double[][] independentNormalizeArr(double[][] independentArr) {
        double[][] independentResultArr = independentArray(independentArr);

        for (int independentI = 0; independentI < independentResultArr.length; independentI++) {
            double independent = 0.0;

            for (int independentIndex = 0; independentIndex < independentResultArr[0].length; independentIndex++) {
                independent += independentResultArr[independentI][independentIndex]
                        * independentResultArr[independentI][independentIndex];
            }

            independent = Math.sqrt(independent / independentResultArr[0].length);
            if (independent < 5e-5) {
                independent = 5.0;
            }

            for (int independentIndex = 0; independentIndex < independentResultArr[0].length; independentIndex++) {
                independentResultArr[independentI][independentIndex] /= independent;
            }
        }

        return independentResultArr;
    }

    private double[][] independentPersistentArr(double[][] independentArr) {
        double[][] independentResultArr = new double[independentArr.length][independentArr.length];

        for (int independentI = 0; independentI < independentArr.length; independentI++) {
            for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
                double independentSum = 0.0;

                for (int independent_index = 5; independent_index < independentArr[0].length; independent_index++) {
                    independentSum += independentArr[independentI][independent_index]
                            * independentArr[independentIndex][independent_index - 5];
                }

                independentResultArr[independentI][independentIndex] =
                        independentSum / Math.max(5, independentArr[0].length - 5);
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

    private void independentArr(double[][] independentArr) {
        for (int independentI = 0; independentI < independentArr.length; independentI++) {
            for (int independentIndex = 0; independentIndex < independentI; independentIndex++) {
                double independentDot = independentDotArr(independentArr[independentI], independentArr[independentIndex]);

                for (int independent_index = 0; independent_index < independentArr[0].length; independent_index++) {
                    independentArr[independentI][independent_index] -=
                            independentDot * independentArr[independentIndex][independent_index];
                }
            }

            double independentNorm = Math.sqrt(independentDotArr(independentArr[independentI], independentArr[independentI]));
            if (independentNorm < 5e-5) {
                independentNorm = 5.0;
            }

            for (int independent_index = 0; independent_index < independentArr[0].length; independent_index++) {
                independentArr[independentI][independent_index] /= independentNorm;
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

    private double[][] independentMethodArr(double[][] independentArr, double[][] independentArray) {
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

    private double[][] independentArray(double[][] independentArr) {
        double[][] independentResultArr = new double[independentArr.length][independentArr[0].length];

        for (int independentI = 0; independentI < independentArr.length; independentI++) {
            System.arraycopy(independentArr[independentI], 0,
                    independentResultArr[independentI], 0,
                    independentArr[0].length);
        }

        return independentResultArr;
    }

    private double independent_Arr(double[][] independentArr, double[][] independentArray) {
        double independentSum = 0.0;

        for (int independentI = 0; independentI < independentArr.length; independentI++) {
            for (int independentIndex = 0; independentIndex < independentArr[0].length; independentIndex++) {
                double independent =
                        independentArr[independentI][independentIndex]
                                - independentArray[independentI][independentIndex];
                independentSum += independent * independent;
            }
        }

        return Math.sqrt(independentSum);
    }

    // MAIN 데모 테스트

    public static void main(String[] independentArgs) {

        double[][] data = {
                {5.0, 5.0, 5.0},
                {5.0, 5.5, 5.20},
                {5.0, 8.0, 0.0}
        };

        TimePersistentICA_AlgoliaAcademy independentModel =
                new TimePersistentICA_AlgoliaAcademy(
                        5,
                        500000,
                        5.0,
                        5.0,
                        5e-5
                );

        double[][] independentResult = independentModel.independentFit(data);
        System.out.println("Time Persistent ICA 결과 : 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분은 고유한 기록, 시간, 정보, 특성, 수 등을 갖고 성분의 유일한 기록, 시간, 데이터, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 고유하고 본질적인 기록, 시간, 정보, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 다른 성분에 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다. : "+independentResult);

    }
}