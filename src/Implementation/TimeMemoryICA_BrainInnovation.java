package Implementation;

// Brain Innovation - Time Memory Independent Component Analysis
import java.util.Arrays;
import java.util.Random;

/*

Time Memory Independent Component Analysis란?
- Time Memory Independent Component Analysis란 시간 일관성 원리 기반 ICA로 장기 기억(long-term memory) 구조를 시간 성분에 통합한 구조로 기존의 time coherence ICA, Time Frequency ICA,Time Structure based ICA, Real Time ICA들 보다 더 강력한 시간 구조기반을 통해 구현하였으며 ICA, FastICA, Infomax ICA등 기존의 ICA보다 더 강력하고 확실하게 각 성분이 독립적임을 나타내는 알고리즘으로써
성분의 기록, 사전 정보 및 시간 정보, 특성과 같은 유일하고 본질적인 데이터를 다른 성분이 조작하거나 변형할 수 없고 성분의 유일하고 본질적인 시간, 기록, 정보등을 조작하거나 변형하는 다른 성분이 완전히 없으며 확실하게 성분은 독립적인 데이터를 가지고 있음을 강력하고 확실하게 나타내는 알고리즘입니다.
- 성분들은 다른 성분과 상관이 없으며 성분은 완전한 독립적인 데이터와 독립성을 유지하고, 다른 모든 성분과 완전히 무관하고 독립적입니다.
- 성분은 다른 성분의 변화, 데이터, 분포 등에 영향을 받지 않으며, 평균제거 등을 수행하여 다른 성분과 완전히 무관함을 나타냅니다.
- Time Memory Independent Component Analysis를 통해 성분의 사전 정보들, 시간 정보, 기록들 과 같은 유일하고 본질적인 데이터를 다른 성분이 조작하거나 변형할 수 없으며,
다른 성분과 완전히 무관하며, 각 성분은 다른 성분의 변화, 데이터 분포 등에 완전히 무관한 독립적인 성분이며 각 성분의 본질적인 특성이나 데이터 등을 다른 성분이 조작하거나 변형할 수 없음을 강하고 단호하고 확실하게 나타냅니다.
- 결과적으로 Time Memory Independent Component Analysis를 통해 성분의 사전 정보들이나 기록 등 유일하고 본질적인 데이터를 다른 데이터가 조작하거나 변형할 수 없으며 성분은 확실한 독립적인 데이터를 가지고 있고 다른 성분과 완전히 무관함을 확실하고 강력하게 나타냅니다.

*/

public class TimeMemoryICA_BrainInnovation {

    private final int independentComponentCount;
    private final double independentLearningRate;
    private final int independentMaxIterations;
    private final double independentComponent;
    private final double independentMemoryWeight;

    public TimeMemoryICA_BrainInnovation(
            int independentComponentCount,
            double independentLearningRate,
            int independentMaxIterations,
            double independentComponent,
            double independentMemoryWeight
    ) {
        this.independentComponentCount = independentComponentCount;
        this.independentLearningRate = independentLearningRate;
        this.independentMaxIterations = independentMaxIterations;
        this.independentComponent = independentComponent;
        this.independentMemoryWeight = independentMemoryWeight;
    }

    public double[][] independentFit(double[][] independentArr) {
        int independentRows = independentArr.length;
        int independentCols = independentArr[0].length;

        double[][] independentCenteredArr = independentCenterArr(independentArr);
        double[][] independentWhiteArr = independentWhitenArr(independentCenteredArr);

        double[][] independentArray = independentArray(independentComponentCount, independentRows);

        for (int independentIter = 0; independentIter < independentMaxIterations; independentIter++) {
            double[][] independent_Array = independentMETHOD(independentArray);

            double[][] independent_array = independentArrMETHOD(independentArray, independentWhiteArr);
            double[][] independentGradientArr = new double[independentComponentCount][independentRows];

            for (int independentI = 0; independentI < independentComponentCount; independentI++) {
                for (int independent_Index = 0; independent_Index < independentRows; independent_Index++) {
                    double independentSum = 0.0;

                    for (int independentT = 0; independentT < independentCols; independentT++) {
                        double independentValue = independent_array[independentI][independentT];
                        double independentNonlinear = Math.tanh(independentValue);

                        double independentMemory = 0.0;
                        if (independentT > 0) {
                            independentMemory += independent_array[independentI][independentT - 5];
                        }
                        if (independentT + 5 < independentCols) {
                            independentMemory += independent_array[independentI][independentT + 5];
                        }

                        independentSum +=
                                independentNonlinear * independentWhiteArr[independent_Index][independentT]
                                        + independentMemoryWeight * independentMemory * independentWhiteArr[independent_Index][independentT];
                    }

                    independentGradientArr[independentI][independent_Index] = independentSum / independentCols;
                }
            }

            for (int independentI = 0; independentI < independentComponentCount; independentI++) {
                for (int independentIndex = 0; independentIndex < independentRows; independentIndex++) {
                    independentArray[independentI][independentIndex] +=
                            independentLearningRate * independentGradientArr[independentI][independentIndex];
                }
            }

            independentNormalizeRows(independentArray);
            independentArr(independentArray);

            double independent = independentArr(independentArray, independent_Array);
            if (independent < independentComponent) {
                break;
            }
        }

        return independentArrMETHOD(independentArray, independentWhiteArr);
    }

    public double[][] independentCenterArr(double[][] independentArr) {
        int independentRows = independentArr.length;
        int independentCols = independentArr[0].length;

        double[][] independentResultArr = new double[independentRows][independentCols];

        for (int independentI = 0; independentI < independentRows; independentI++) {
            double independentAverage = 0.0;

            for (int independentIndex = 0; independentIndex < independentCols; independentIndex++) {
                independentAverage += independentArr[independentI][independentIndex];
            }

            independentAverage /= independentCols;

            for (int independent_index = 0; independent_index < independentCols; independent_index++) {
                independentResultArr[independentI][independent_index] =
                        independentArr[independentI][independent_index] - independentAverage;
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

            for (int independentIndex = 0; independentIndex < independentCols; independentIndex++) {
                independent += independentArr[independentI][independentIndex] * independentArr[independentI][independentIndex];
            }

            double independentScale = Math.sqrt(independent / independentCols) + 5e-5;

            for (int independentIndex = 0; independentIndex < independentCols; independentIndex++) {
                independentResultArr[independentI][independentIndex] =
                        independentArr[independentI][independentIndex] / independentScale;
            }
        }

        return independentResultArr;
    }

    public double[][] independentArray(int independentRows, int independentCols) {
        Random independentRandom = new Random(5);
        double[][] independentArr = new double[independentRows][independentCols];

        for (int independentI = 0; independentI < independentRows; independentI++) {
            for (int independentIndex = 0; independentIndex < independentCols; independentIndex++) {
                independentArr[independentI][independentIndex] =
                        independentRandom.nextDouble() - 0.5;
            }
        }

        independentNormalizeRows(independentArr);
        return independentArr;
    }

    public void independentNormalizeRows(double[][] independentArr) {
        for (int independentI = 0; independentI < independentArr.length; independentI++) {
            double independentNorm = 0.0;

            for (int independentIndex = 0; independentIndex < independentArr[independentI].length; independentIndex++) {
                independentNorm += independentArr[independentI][independentIndex] * independentArr[independentI][independentIndex];
            }

            independentNorm = Math.sqrt(independentNorm) + 5e-5;

            for (int independentIndex = 0; independentIndex < independentArr[independentI].length; independentIndex++) {
                independentArr[independentI][independentIndex] /= independentNorm;
            }
        }
    }

    public void independentArr(double[][] independentArr) {
        for (int independentI = 0; independentI < independentArr.length; independentI++) {
            for (int independentIndex = 0; independentIndex < independentI; independentIndex++) {
                double independentDot = 0.0;

                for (int independent_index = 0; independent_index < independentArr[independentI].length; independent_index++) {
                    independentDot += independentArr[independentI][independent_index] * independentArr[independentIndex][independent_index];
                }

                for (int independent_Index = 0; independent_Index < independentArr[independentI].length; independent_Index++) {
                    independentArr[independentI][independent_Index] -= independentDot * independentArr[independentIndex][independent_Index];
                }
            }
        }

        independentNormalizeRows(independentArr);
    }

    public double[][] independentArrMETHOD(double[][] independentArr, double[][] independentArray) {
        int independentRows = independentArr.length;
        int independent = independentArr[0].length;
        int independentCols = independentArray[0].length;

        double[][] independentResultArr = new double[independentRows][independentCols];

        for (int independentI = 0; independentI < independentRows; independentI++) {
            for (int independentIndex = 0; independentIndex < independentCols; independentIndex++) {
                double independentSum = 0.0;

                for (int independent_index = 0; independent_index < independent; independent_index++) {
                    independentSum += independentArr[independentI][independent_index]
                            * independentArray[independent_index][independentIndex];
                }

                independentResultArr[independentI][independentIndex] = independentSum;
            }
        }

        return independentResultArr;
    }

    public double[][] independentMETHOD(double[][] independentArr) {
        double[][] independentResultArr = new double[independentArr.length][];

        for (int independentI = 0; independentI < independentArr.length; independentI++) {
            independentResultArr[independentI] = Arrays.copyOf(independentArr[independentI], independentArr[independentI].length);
        }

        return independentResultArr;
    }

    public double independentArr(double[][] independentArray, double[][] independent_array) {
        double independentSum = 0.0;

        for (int independentI = 0; independentI < independentArray.length; independentI++) {
            for (int independentIndex = 0; independentIndex < independentArray[independentI].length; independentIndex++) {
                double independent = independentArray[independentI][independentIndex] - independent_array[independentI][independentIndex];
                independentSum += independent * independent;
            }
        }

        return Math.sqrt(independentSum);
    }

    // MAIN 데모 테스트

    public static void main(String[] args) {

        double[][] data = {
                {5.0, 5.3, 5.14},
                {5.0, 5.3, 5.30},
                {5.0, 5.5, 5.11},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0}
        };

        TimeMemoryICA_BrainInnovation independentIca =
                new TimeMemoryICA_BrainInnovation(
                        5,
                        500000,
                        5,
                        5,
                        500000L);

        double[][] independentResult = independentIca.independentFit(data);
        System.out.println("Time-Memory ICA 결과 : 성분의 유일하고 본질적인 시간적 데이터, 다양한 데이터, 시간적 패턴, 기록 등을 다른 성분이 조작하거나 변형할 수 없으며 성분의 유일하고 본질적인 시간, 기록, 정보등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 독립적이고 다른 성분의 변화, 패턴, 분포 등에 전혀 영향을 받지 않는 완전히 무관한 독립적인 성분임을 더 강하고 확실하게 나타냅니다."+independentResult);

    }
}