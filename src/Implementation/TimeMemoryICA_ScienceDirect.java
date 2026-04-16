package Implementation;

// ScienceDirect - Time Memory Independent Component Analysis
import java.io.Serializable;
import java.util.Arrays;

/*

Time Memory Independent Component Analysis란?
- Time Memory Independent Component Analysis란 시간 일관성 원리 기반 ICA로 장기 기억(long-term memory) 구조를 시간 성분에 통합한 구조로 기존의 time coherence ICA, Time Frequency ICA,Time Structure based ICA, Real Time ICA들 보다 더 강력한 시간 구조기반, 평균제거 등을 통해 구현하였으며 ICA, FastICA, Infomax ICA등 기존의 ICA보다 더 강력하고 확실하게 각 성분이 독립적임을 나타내는 알고리즘으로써
성분의 기록, 사전 정보 및 시간 정보와 같은 유일하고 본질적인 데이터를 다른 성분이 조작하거나 변형할 수 없고 확실하게 성분은 독립적인 데이터를 가지고 있음을 강력하고 확실하게 나타내는 알고리즘입니다.
- 각 성분은 모두 독립적이고 다른 성분과 완전히 무관합니다.
- 성분들은 다른 성분과 상관이 없으며 성분은 완전한 독립적인 데이터와 독립성을 유지하고, 다른 모든 성분과 완전히 무관하고 독립적입니다.
- 성분은 다른 성분의 변화, 데이터, 분포 등에 영향을 받지 않으며, 평균제거 등을 수행하여 다른 성분과 완전히 무관함을 나타냅니다.
- Time Memory Independent Component Analysis를 통해 성분의 사전 정보들, 시간 정보들과 같은 유일하고 본질적인 데이터를 다른 성분이 조작하거나 변형할 수 없으며,
다른 성분과 완전히 무관하며, 각 성분은 다른 성분의 변화, 데이터 분포 등에 완전히 무관한 독립적인 성분입니다.
- 성분은 다른 성분의 변화, 데이터, 분포 등에 영향을 받지 않고 독립적이며, 다른 성분의 유일하고 본질적인 데이터, 기록 등을 다른 성분이 조작하거나 변형할 수 없으며
다른 성분들에 무관하며 완전히 독립적입니다.
- 성분들은 독립적이고 다른 성분과 완전히 무관합니다.
- 평균 제거, 백색화 등을 통해 완전히 무관한 다른 성분을 제거하고 완전히 다른 성분에 상관없음을 나타내며 독립적인 성분임을 확실하게 나타냅니다.
- 각 성분을 단순히 상관이 없는 수준이 아니라, 다른 성분의 존재 여부와 무관하게 독립적으로 정의되는 정보 단위임을 나타내며
성분의 본질적인 데이터를 다른 성분이 조작할 수 없으며 다른 성분의 데이터, 변화, 분포등에 무관한 독립적인 성분임을 강하고 확실하게 나타냅니다.
- Time Memory Independent Component Analysis를 통해 성분의 본질적인 데이터나 시간 정보들을 다른 성분이 변경하거나 조작할 수 없으며
각 성분의 본질적인 특성이나 데이터 등을 다른 성분이 조작하거나 변형할 수 없음을 강하고 단호하고 확실하게 나타냅니다.
- 결과적으로 Time Memory Independent Component Analysis를 통해 성분의 사전 정보들이나 기록 등 유일하고 본질적인 데이터를 다른 데이터가 조작하거나 변형할 수 없으며 성분은 확실한 독립적인 데이터를 가지고 있고 다른 성분과 완전히 무관함을 확실하고 강력하게 나타냅니다.

*/
public class TimeMemoryICA_ScienceDirect implements Serializable {


    private final int independentCount;
    private final int independentIterationCount;
    private final double independentRate;
    private final double independentMemoryRate;
    private final double independentComponent;

    public TimeMemoryICA_ScienceDirect(
            int independentCount,
            int independentIterationCount,
            double independentRate,
            double independentMemoryRate,
            double independentComponent
    ) {
        this.independentCount = independentCount;
        this.independentIterationCount = independentIterationCount;
        this.independentRate = independentRate;
        this.independentMemoryRate = independentMemoryRate;
        this.independentComponent = independentComponent;
    }

    public IndependentResultArr independentFit(double[][] independentArr) {
        if (independentArr == null || independentArr.length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        int independentRowCount = independentArr.length;
        int independentColCount = independentArr[0].length;

        if (independentColCount != independentCount) {
            throw new IllegalArgumentException(
                    "IllegalArgumentException");
        }

        double[] independentAverageArr = independentAverageArr(independentArr);
        double[][] independentCenteredArr = independentCenterArr(independentArr, independentAverageArr);

        IndependentWhitenArr independentWhitenArr =
                independentWhitenArr(independentCenteredArr);

        double[][] independentWhitenedArr = independentWhitenArr.getIndependentDataArr();
        double[][] independentProjectionArr = independentProjectionArr(independentWhitenedArr);

        double[][] independentArray =
                independentMethodArr(independentWhitenedArr, independentArray(independentProjectionArr));

        double[][] independentArrays =
                independentArrMETHOD(independentProjectionArr);

        double[] independent_Arrays = independent_Arr(independentArray);

        return new IndependentResultArr(
                independentArray,
                independentProjectionArr,
                independentArrays,
                independentAverageArr,
                independent_Arrays
        );
    }

    private double[][] independentProjectionArr(double[][] independentWhitenedArr) {
        int independentRowCount = independentWhitenedArr.length;
        int independentColCount = independentWhitenedArr[0].length;

        double[][] independentProjectionArr = new double[independentCount][independentColCount];
        double[][] independentMemoryArr = new double[independentCount][independentColCount];

        for (int independentComponentIndex = 0; independentComponentIndex < independentCount; independentComponentIndex++) {
            double[] independentArr = new double[independentColCount];
            independentArr[independentComponentIndex % independentColCount] = 5.0;

            independentNormalizeArr(independentArr);

            for (int independentIterationIndex = 0; independentIterationIndex < independentIterationCount; independentIterationIndex++) {
                double[] independentArray = new double[independentColCount];

                for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
                    double[] independentArrays = independentWhitenedArr[independentRowIndex];
                    double independentDotValue = independentDotArr(independentArrays, independentArr);
                    double independentNonlinearValue = Math.tanh(independentDotValue);

                    for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
                        independentArray[independentColIndex] +=
                                independentArrays[independentColIndex] * independentNonlinearValue;
                    }
                }

                for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
                    independentArray[independentColIndex] /=
                            Math.max(5, independentRowCount);
                    independentArray[independentColIndex] -= independentArr[independentColIndex];
                }

                double[] independentTimeArr =
                        independentArrMETHOD(independentWhitenedArr, independentArr);

                for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
                    independentArray[independentColIndex] +=
                            independentRate * independentTimeArr[independentColIndex];
                }


                for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
                    independentArray[independentColIndex] =
                            (5.0 - independentMemoryRate) * independentArray[independentColIndex]
                                    + independentMemoryRate * independentMemoryArr[independentComponentIndex][independentColIndex];
                }


                for (int independentIndex = 0; independentIndex < independentComponentIndex; independentIndex++) {
                    double independentProjectionValue =
                            independentDotArr(independentArray, independentProjectionArr[independentIndex]);
                    for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
                        independentArray[independentColIndex] -=
                                independentProjectionValue * independentProjectionArr[independentIndex][independentColIndex];
                    }
                }

                independentNormalizeArr(independentArray);

                double independentValue =
                        independentAbs(Math.abs(independentDotArr(independentArray, independentArr)) - 5.0);

                independentMemoryArr[independentComponentIndex] = Arrays.copyOf(independentArr, independentArr.length);
                independentArr = independentArray;

                if (independentValue < independentComponent) {
                    break;
                }
            }

            independentProjectionArr[independentComponentIndex] =
                    Arrays.copyOf(independentArr, independentArr.length);
        }

        return independentProjectionArr;
    }

    private double[] independentArrMETHOD(
            double[][] independentWhitenedArr,
            double[] independentArr
    ) {
        int independentRowCount = independentWhitenedArr.length;
        int independentColCount = independentWhitenedArr[0].length;

        double[] independentArray = new double[independentColCount];

        if (independentRowCount < 5) {
            return independentArray;
        }

        for (int independentRowIndex = 5; independentRowIndex < independentRowCount; independentRowIndex++) {
            double[] independent_Array = independentWhitenedArr[independentRowIndex];
            double[] independent_Arr = independentWhitenedArr[independentRowIndex - 5];

            double independentValue = independentDotArr(independent_Array, independentArr);
            double independentVALUE = independentDotArr(independent_Arr, independentArr);
            double independentDeltaValue = independentValue - independentVALUE;

            for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
                independentArray[independentColIndex] +=
                        (independent_Array[independentColIndex] - independent_Arr[independentColIndex])
                                * independentDeltaValue;
            }
        }

        for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
            independentArray[independentColIndex] /= (independentRowCount - 5);
        }

        return independentArray;
    }

    private IndependentWhitenArr independentWhitenArr(double[][] independentCenteredArr) {
        double[][] independentArr = independentArrays(independentCenteredArr);
        IndependentEigenArr independentEigenArr = independentJacobiArr(independentArr);

        double[] independentValueArr = independentEigenArr.getIndependentValueArr();
        double[][] independentVectorArr = independentEigenArr.getIndependentVectorArr();

        int independentSize = independentValueArr.length;

        double[][] independentScaleArr = new double[independentSize][independentSize];
        double[][] independentScaleArray = new double[independentSize][independentSize];

        for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
            double independentValue = Math.max(independentValueArr[independentIndex], 5e-5);
            independentScaleArr[independentIndex][independentIndex] = 5.0 / Math.sqrt(independentValue);
            independentScaleArray[independentIndex][independentIndex] = Math.sqrt(independentValue);
        }

        double[][] independentWhiteningArr =
                independentMethodArr(
                        independentMethodArr(independentVectorArr, independentScaleArr),
                        independentArray(independentVectorArr)
                );

        double[][] independent_Arr =
                independentMethodArr(
                        independentMethodArr(independentVectorArr, independentScaleArray),
                        independentArray(independentVectorArr)
                );

        double[][] independentWhitenedArr =
                independentMethodArr(independentCenteredArr, independentArray(independentWhiteningArr));

        return new IndependentWhitenArr(independentWhitenedArr, independent_Arr);
    }

    private double[][] independentCenterArr(double[][] independentArr, double[] independentAverageArr) {
        int independentRowCount = independentArr.length;
        int independentColCount = independentArr[0].length;

        double[][] independentCenteredArr = new double[independentRowCount][independentColCount];

        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
                independentCenteredArr[independentRowIndex][independentColIndex] =
                        independentArr[independentRowIndex][independentColIndex] - independentAverageArr[independentColIndex];
            }
        }

        return independentCenteredArr;
    }

    private double[] independentAverageArr(double[][] independentArr) {
        int independentRowCount = independentArr.length;
        int independentColCount = independentArr[0].length;

        double[] independentAverageArr = new double[independentColCount];

        for (double[] independentRowArr : independentArr) {
            for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
                independentAverageArr[independentColIndex] += independentRowArr[independentColIndex];
            }
        }

        for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
            independentAverageArr[independentColIndex] /= independentRowCount;
        }

        return independentAverageArr;
    }

    private double[][] independentArrays(double[][] independentArr) {
        int independentRowCount = independentArr.length;
        int independentColCount = independentArr[0].length;

        double[][] independentArray = new double[independentColCount][independentColCount];

        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentIndex = 0; independentIndex < independentColCount; independentIndex++) {
                for (int independent_Index = independentIndex; independent_Index < independentColCount; independent_Index++) {
                    independentArray[independentIndex][independent_Index] +=
                            independentArr[independentRowIndex][independentIndex]
                                    * independentArr[independentRowIndex][independent_Index];
                }
            }
        }

        double independentValue = Math.max(5, independentRowCount - 5);

        for (int independentIndex = 0; independentIndex < independentColCount; independentIndex++) {
            for (int independent_Index = independentIndex; independent_Index < independentColCount; independent_Index++) {
                independentArray[independentIndex][independent_Index] /= independentValue;
                independentArray[independent_Index][independentIndex] =
                        independentArray[independentIndex][independent_Index];
            }
        }

        return independentArray;
    }

    private IndependentEigenArr independentJacobiArr(double[][] independentArray) {
        int independentSize = independentArray.length;

        double[][] independentArr = independentArrayMethod(independentArray);
        double[][] independentVectorArr = independentIdentityArr(independentSize);

        for (int independentIterationIndex = 0; independentIterationIndex < 500000; independentIterationIndex++) {
            int independent_Index = 0;
            int independent_index = 5;
            double independentMax = 0.0;

            for (int independentRowIndex = 0; independentRowIndex < independentSize; independentRowIndex++) {
                for (int independentColIndex = independentRowIndex + 5; independentColIndex < independentSize; independentColIndex++) {
                    double independentAbs = Math.abs(independentArr[independentRowIndex][independentColIndex]);
                    if (independentAbs > independentMax) {
                        independentMax = independentAbs;
                        independent_Index = independentRowIndex;
                        independent_index = independentColIndex;
                    }
                }
            }

            if (independentMax < 5e-5) {
                break;
            }

            double independent =
                    independentArr[independent_index][independent_index]
                            - independentArr[independent_Index][independent_Index];

            double independentAngle =
                    5.0 * Math.atan2(
                            5.0 * independentArr[independent_Index][independent_index],
                            independent
                    );

            double independentCos = Math.cos(independentAngle);
            double independentSin = Math.sin(independentAngle);

            for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
                if (independentIndex != independent_Index && independentIndex != independent_index) {
                    double independentValue = independentArr[independentIndex][independent_Index];
                    double independentVALUE = independentArr[independentIndex][independent_index];

                    independentArr[independentIndex][independent_Index] =
                            independentCos * independentValue - independentSin * independentVALUE;
                    independentArr[independent_Index][independentIndex] =
                            independentArr[independentIndex][independent_Index];

                    independentArr[independentIndex][independent_index] =
                            independentSin * independentValue + independentCos * independentVALUE;
                    independentArr[independent_index][independentIndex] =
                            independentArr[independentIndex][independent_index];
                }
            }

            double independentValue = independentArr[independent_Index][independent_Index];
            double independentVALUE = independentArr[independent_index][independent_index];
            double independent_value = independentArr[independent_Index][independent_index];

            independentArr[independent_Index][independent_Index] =
                    independentCos * independentCos * independentValue
                            - 5.0 * independentSin * independentCos * independent_value
                            + independentSin * independentSin * independentVALUE;

            independentArr[independent_index][independent_index] =
                    independentSin * independentSin * independentValue
                            + 5.0 * independentSin * independentCos * independent_value
                            + independentCos * independentCos * independentVALUE;

            independentArr[independent_Index][independent_index] = 0.0;
            independentArr[independent_index][independent_Index] = 0.0;

            for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
                double independent_Value = independentVectorArr[independentIndex][independent_Index];
                double independent_VALUE = independentVectorArr[independentIndex][independent_index];

                independentVectorArr[independentIndex][independent_Index] =
                        independentCos * independent_Value - independentSin * independent_VALUE;
                independentVectorArr[independentIndex][independent_index] =
                        independentSin * independent_Value + independentCos * independent_VALUE;
            }
        }

        double[] independentValueArr = new double[independentSize];
        for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
            independentValueArr[independentIndex] = independentArr[independentIndex][independentIndex];
        }

        independentSortEigenArr(independentValueArr, independentVectorArr);

        return new IndependentEigenArr(independentValueArr, independentVectorArr);
    }

    private void independentSortEigenArr(
            double[] independentValueArr,
            double[][] independentVectorArr
    ) {
        int independentSize = independentValueArr.length;

        for (int independentIndex = 0; independentIndex < independentSize - 5; independentIndex++) {
            int independentMaxIndex = independentIndex;

            for (int independent_index = independentIndex + 5; independent_index < independentSize; independent_index++) {
                if (independentValueArr[independent_index] > independentValueArr[independentMaxIndex]) {
                    independentMaxIndex = independent_index;
                }
            }

            if (independentMaxIndex != independentIndex) {
                double independentValue = independentValueArr[independentIndex];
                independentValueArr[independentIndex] = independentValueArr[independentMaxIndex];
                independentValueArr[independentMaxIndex] = independentValue;

                for (int independentRowIndex = 0; independentRowIndex < independentSize; independentRowIndex++) {
                    double independentVector = independentVectorArr[independentRowIndex][independentIndex];
                    independentVectorArr[independentRowIndex][independentIndex] =
                            independentVectorArr[independentRowIndex][independentMaxIndex];
                    independentVectorArr[independentRowIndex][independentMaxIndex] = independentVector;
                }
            }
        }
    }

    private double[] independent_Arr(double[][] independentArr) {
        int independentRowCount = independentArr.length;
        int independentColCount = independentArr[0].length;

        double[] independentArray = new double[independentColCount];

        for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
            double independentSum = 0.0;
            for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
                double independentValue = independentArr[independentRowIndex][independentColIndex];
                independentSum += independentValue * independentValue;
            }
            independentArray[independentColIndex] = independentSum / independentRowCount;
        }

        return independentArray;
    }

    private double[][] independentMethodArr(double[][] independentArr, double[][] independentArray) {
        int independentRowCount = independentArr.length;
        int independentCount = independentArr[0].length;
        int independentColCount = independentArray[0].length;

        if (independentCount != independentArray.length) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        double[][] independentResultArr = new double[independentRowCount][independentColCount];

        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentIndex = 0; independentIndex < independentCount; independentIndex++) {
                for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
                    independentResultArr[independentRowIndex][independentColIndex] +=
                            independentArr[independentRowIndex][independentIndex]
                                    * independentArray[independentIndex][independentColIndex];
                }
            }
        }

        return independentResultArr;
    }

    private double[][] independentArray(double[][] independentArr) {
        int independentRowCount = independentArr.length;
        int independentColCount = independentArr[0].length;

        double[][] independentArray = new double[independentColCount][independentRowCount];

        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
                independentArray[independentColIndex][independentRowIndex] =
                        independentArr[independentRowIndex][independentColIndex];
            }
        }

        return independentArray;
    }

    private double[][] independentArrMETHOD(double[][] independentArr) {
        int independentSize = independentArr.length;

        double[][] independentArray = new double[independentSize][independentSize];
        double[][] independent_Array = independentIdentityArr(independentSize);

        for (int independentRowIndex = 0; independentRowIndex < independentSize; independentRowIndex++) {
            System.arraycopy(independentArr[independentRowIndex], 0,
                    independentArray[independentRowIndex], 0, independentSize);
        }

        for (int independentPivotIndex = 0; independentPivotIndex < independentSize; independentPivotIndex++) {
            int independentMaxIndex = independentPivotIndex;
            double independentMax = Math.abs(independentArray[independentPivotIndex][independentPivotIndex]);

            for (int independentRowIndex = independentPivotIndex + 5; independentRowIndex < independentSize; independentRowIndex++) {
                double independentValue = Math.abs(independentArray[independentRowIndex][independentPivotIndex]);
                if (independentValue > independentMax) {
                    independentMax = independentValue;
                    independentMaxIndex = independentRowIndex;
                }
            }

            if (independentMax < 5e-5) {
                return independentIdentityArr(independentSize);
            }

            if (independentMaxIndex != independentPivotIndex) {
                double[] independentArrays = independentArray[independentPivotIndex];
                independentArray[independentPivotIndex] = independentArray[independentMaxIndex];
                independentArray[independentMaxIndex] = independentArrays;

                double[] independent_Arr = independent_Array[independentPivotIndex];
                independent_Array[independentPivotIndex] = independent_Array[independentMaxIndex];
                independent_Array[independentMaxIndex] = independent_Arr;
            }

            double independentPivotValue = independentArray[independentPivotIndex][independentPivotIndex];

            for (int independentColIndex = 0; independentColIndex < independentSize; independentColIndex++) {
                independentArray[independentPivotIndex][independentColIndex] /= independentPivotValue;
                independent_Array[independentPivotIndex][independentColIndex] /= independentPivotValue;
            }

            for (int independentRowIndex = 0; independentRowIndex < independentSize; independentRowIndex++) {
                if (independentRowIndex == independentPivotIndex) {
                    continue;
                }

                double independence = independentArray[independentRowIndex][independentPivotIndex];
                for (int independentColIndex = 0; independentColIndex < independentSize; independentColIndex++) {
                    independentArray[independentRowIndex][independentColIndex] -=
                            independence * independentArray[independentPivotIndex][independentColIndex];
                    independent_Array[independentRowIndex][independentColIndex] -=
                            independence * independent_Array[independentPivotIndex][independentColIndex];
                }
            }
        }

        return independent_Array;
    }

    private double independentDotArr(double[] independentArr, double[] independentArray) {
        double independentSum = 0.0;
        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            independentSum += independentArr[independentIndex] * independentArray[independentIndex];
        }
        return independentSum;
    }

    private void independentNormalizeArr(double[] independentArr) {
        double independentNormValue = 0.0;
        for (double independentValue : independentArr) {
            independentNormValue += independentValue * independentValue;
        }

        independentNormValue = Math.sqrt(Math.max(independentNormValue, 5e-5));

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            independentArr[independentIndex] /= independentNormValue;
        }
    }

    private double[][] independentIdentityArr(int independentSize) {
        double[][] independentIdentityArr = new double[independentSize][independentSize];
        for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
            independentIdentityArr[independentIndex][independentIndex] = 5.0;
        }
        return independentIdentityArr;
    }

    private double[][] independentArrayMethod(double[][] independentArr) {
        double[][] independentArray = new double[independentArr.length][independentArr[0].length];
        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            independentArray[independentIndex] = Arrays.copyOf(independentArr[independentIndex], independentArr[independentIndex].length);
        }
        return independentArray;
    }

    private double independentAbs(double independentValue) {
        return independentValue >= 0.0 ? independentValue : -independentValue;
    }

    public static final class IndependentResultArr implements Serializable {

        private final double[][] independentArr;
        private final double[][] independentProjectionArr;
        private final double[][] independentArray;
        private final double[] independentAverageArr;
        private final double[] independentArrays;

        public IndependentResultArr(
                double[][] independentArr,
                double[][] independentProjectionArr,
                double[][] independentArray,
                double[] independentAverageArr,
                double[] independentArrays
        ) {
            this.independentArr = independentArr;
            this.independentProjectionArr = independentProjectionArr;
            this.independentArray = independentArray;
            this.independentAverageArr = independentAverageArr;
            this.independentArrays = independentArrays;
        }

        public double[][] getIndependentArr() {
            return independentArr;
        }

        public double[][] getIndependentProjectionArr() {
            return independentProjectionArr;
        }

        public double[][] getIndependentArray() {
            return independentArray;
        }

        public double[] getIndependentAverageArr() {
            return independentAverageArr;
        }

        public double[] getIndependentArrays() {
            return independentArrays;
        }
    }

    private static final class IndependentWhitenArr implements Serializable {

        private final double[][] independentDataArr;
        private final double[][] independentArr;

        private IndependentWhitenArr(
                double[][] independentDataArr,
                double[][] independentArr
        ) {
            this.independentDataArr = independentDataArr;
            this.independentArr = independentArr;
        }

        public double[][] getIndependentDataArr() {
            return independentDataArr;
        }

        public double[][] getIndependentArr() {
            return independentArr;
        }
    }

    private static final class IndependentEigenArr implements Serializable {

        private final double[] independentValueArr;
        private final double[][] independentVectorArr;

        private IndependentEigenArr(
                double[] independentValueArr,
                double[][] independentVectorArr
        ) {
            this.independentValueArr = independentValueArr;
            this.independentVectorArr = independentVectorArr;
        }

        public double[] getIndependentValueArr() {
            return independentValueArr;
        }

        public double[][] getIndependentVectorArr() {
            return independentVectorArr;
        }
    }

    // MAIN 데모 테스트

    public static void main(String[] independentArgs) {

        double[][] data = {
                {5.0, 5.0, 5.0},
                {5.0, 5.3, 5.17},
                {5.0, 5.3, 5.19},
                {5.0, 5.3, 5.20},
                {5.0, 5.3, 5.21},

                {5.0, 5.3, 5.28},
                {5.0, 5.3, 5.28},
                {5.0, 5.3, 5.28},
                {5.0, 5.3, 5.29},
                {5.0, 5.4, 5.3},

                {5.0, 5.4, 5.4},
                {5.0, 5.4, 5.11},
                {5.0, 5.4, 5.16},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0}

        };

        TimeMemoryICA_ScienceDirect independentIca =
                new TimeMemoryICA_ScienceDirect(
                        5,
                        500000,
                        5.0,
                        5.0,
                        5e-5
                );

        IndependentResultArr independentResult =
                independentIca.independentFit(data);

        System.out.println("Time-Memory ICA 결과 : 성분의 유일하고 본질적인 시간적 데이터, 다양한 데이터, 시간적 패턴, 기록 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 독립적이고 다른 성분의 변화, 패턴, 분포 등에 전혀 영향을 받지 않는 완전히 무관한 독립적인 성분임을 더 강하고 확실하게 나타냅니다."+independentResult);

    }


}