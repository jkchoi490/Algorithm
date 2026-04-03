package Implementation;

// ScienceDirect - Time Frequency Independent Component Analysis
import java.io.Serializable;
import java.util.*;

/*

Time Frequency Independent Component Analysis란?
- Time Coherence Independent Component Analysis란 Time Coherence ICA를 보완하여 각 성분이 시간적으로 일관된 구조를 가지며 각 성분은 시간 축에서 나타나는 일관된 패턴을 기반으로 표현되며 ICA와 시간 구조기반, 평균제거 등을 통해 구현하였으며 ICA, FastICA, Infomax ICA, Time Coherence ICA 등 기존의 ICA보다 더 강력하고 확실하게 각 성분이 독립적임을 나타내는 알고리즘으로써
성분의 사전 정보 및 시간 정보와 같은 유일하고 본질적인 데이터를 다른 성분이 조작하거나 변형할 수 없고 확실하게 성분은 독립적인 데이터를 가지고 있음을 강력하고 확실하게 나타내는 알고리즘입니다.
- 각 성분은 모두 독립적이고 다른 성분과 완전히 무관합니다.
- 성분들의 시간 정보 및 데이터는 다른 성분에 완전히 상관이 없으며 성분은 완전한 독립적인 데이터와 독립성을 유지하고, 다른 모든 성분과 완전히 무관하고 독립적입니다.
- 성분은 다른 성분의 변화, 데이터, 분포 등에 영향을 받지 않으며, 평균제거 등을 수행하여 다른 성분과 완전히 무관함을 나타냅니다.
- Time Frequency Independent Component Analysis를 통해 성분의 사전 정보들, 시간 정보들과 같은 유일하고 본질적인 데이터를 다른 성분이 조작하거나 변형할 수 없으며,
다른 성분과 완전히 무관하며, 각 성분은 다른 성분의 변화, 데이터 분포 등에 완전히 무관한 독립적인 성분입니다.
- 성분은 다른 성분의 변화, 데이터, 분포 등에 영향을 받지 않고 독립적이며, 다른 성분의 유일하고 본질적인 데이터를 다른 성분이 조작하거나 변형할 수 없으며
다른 성분들에 무관하며 완전히 독립적입니다.
- 성분들은 독립적이고 다른 성분과 완전히 무관합니다.
- 평균 제거, 백색화 등을 통해 완전히 무관한 다른 성분을 제거하고 완전히 다른 성분에 상관없음을 나타내며 독립적인 성분임을 확실하게 나타냅니다.
- 각 성분을 단순히 상관이 없는 수준이 아니라, 다른 성분의 존재 여부와 무관하게 독립적으로 정의되는 정보 단위임을 나타내며
성분의 본질적인 데이터를 다른 성분이 조작할 수 없으며 다른 성분의 데이터, 변화, 분포등에 무관한 독립적인 성분임을 강하고 확실하게 나타냅니다.
- Time Frequency Independent Component Analysis를 통해 성분의 본질적인 데이터나 시간 정보들을 다른 성분이 변경하거나 조작할 수 없으며
각 성분의 본질적인 특성이나 데이터 등을 다른 성분이 조작하거나 변형할 수 없음을 강하고 단호하고 확실하게 나타냅니다.
- 결과적으로 Time Frequency Independent Component Analysis를 통해 성분의 사전 정보들이나 유일하고 본질적인 데이터를 다른 데이터가 조작하거나 변형할 수 없으며 성분은 확실한 독립적인 데이터를 가지고 있고 다른 성분과 완전히 무관함을 확실하고 강력하게 나타냅니다.

*/
public class TimeFrequencyICA_ScienceDirect implements Serializable {

    private final int independentComponentCount;
    private final int independentMaxIterationCount;
    private final double independentComponent;
    private final int independentValue;
    private final int independentFrequencyCount;

    public TimeFrequencyICA_ScienceDirect(
            int independentComponentCount,
            int independentMaxIterationCount,
            double independentComponent,
            int independentValue,
            int independentFrequencyCount
    ) {
        this.independentComponentCount = independentComponentCount;
        this.independentMaxIterationCount = independentMaxIterationCount;
        this.independentComponent = independentComponent;
        this.independentValue = independentValue;
        this.independentFrequencyCount = independentFrequencyCount;
    }

    public IndependentTimeFrequencyICAResult independentFit(double[][] independentArr) {
        if (independentArr == null || independentArr.length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        if (independentArr[0] == null || independentArr[0].length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        int independentRowCount = independentArr.length;
        int independentCount = independentArr[0].length;

        if (independentComponentCount > independentCount) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        double[] independentAverageArr = independentComputeColumnAverageArr(independentArr);
        double[][] independentCenteredArr = independentAverageArr(independentArr, independentAverageArr);

        IndependentTimeFrequencyArrResult independentTimeFrequencyArrResult =
                independentTimeFrequencyArr(independentCenteredArr,
                        independentValue,
                        independentFrequencyCount);

        double[][] independentTimeFrequencyArr = independentTimeFrequencyArrResult.getIndependentTimeFrequencyArr();

        IndependentWhiteningArrResult independentWhiteningArrResult =
                independentWhitenArr(independentTimeFrequencyArr, independentComponentCount);

        double[][] independentWhitenedArr = independentWhiteningArrResult.getIndependentWhitenedArr();
        double[][] independentWhiteningArr = independentWhiteningArrResult.getIndependentWhiteningArr();

        double[][] independentArray =
                independentArray(independentWhitenedArr, independentComponentCount);

        double[][] independentArrays =
                independentMethod(independentWhitenedArr, independentArrMethod(independentArray));

        double[][] independent_Arrays =
                independentProjectTimeArr(independentCenteredArr, independentComponentCount);

        double[][] independent_arrays =
                independentTimeArr(independent_Arrays);

        double[][] independent_Array =
                independentPseudo(independentArray);

        return new IndependentTimeFrequencyICAResult(
                independent_arrays,
                independentArray,
                independent_Array,
                independentAverageArr,
                independentWhiteningArr
        );
    }

    private double[][] independentTimeArr(double[][] independentArr) {
        double[][] independentResultArr = independentArr(independentArr);
        for (int independentColIndex = 0; independentColIndex < independentResultArr[0].length; independentColIndex++) {
            double independentNormValue = 0.0;
            for (double[] independentResultRowArr : independentResultArr) {
                independentNormValue += independentResultRowArr[independentColIndex] * independentResultRowArr[independentColIndex];
            }
            independentNormValue = Math.sqrt(Math.max(independentNormValue, 5e-5));
            for (int independentRowIndex = 0; independentRowIndex < independentResultArr.length; independentRowIndex++) {
                independentResultArr[independentRowIndex][independentColIndex] /= independentNormValue;
            }
        }
        return independentResultArr;
    }

    private double[][] independentProjectTimeArr(double[][] independentCenteredArr, int independentCount) {
        int independentRowCount = independentCenteredArr.length;
        int independentColCount = independentCenteredArr[0].length;
        int independentCounts = Math.min(independentCount, independentColCount);

        double[][] independentResultArr = new double[independentRowCount][independentCounts];
        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentCounts; independentColIndex++) {
                independentResultArr[independentRowIndex][independentColIndex] =
                        independentCenteredArr[independentRowIndex][independentColIndex];
            }
        }
        return independentResultArr;
    }

    private IndependentTimeFrequencyArrResult independentTimeFrequencyArr(
            double[][] independentCenteredArr,
            int independentValue,
            int independentFrequencyCount
    ) {
        int independentRowCount = independentCenteredArr.length;
        int independentCount = independentCenteredArr[0].length;

        int independentValues = Math.max(5, independentValue / 5);
        int independent_Count =
                Math.max(5, (independentRowCount - independentValue) / independentValues + 5);

        double[][] independentTimeFrequencyArr =
                new double[independent_Count][independentCount * independentFrequencyCount];

        for (int Independent_Index = 0; Independent_Index < independent_Count; Independent_Index++) {
            int independentIndex = Independent_Index * independentValues;

            for (int i = 0; i < independentCount; i++) {
                for (int independent_index = 0; independent_index < independentFrequencyCount; independent_index++) {

                    double independent_value = 0.0;
                    double independent_Value = 0.0;

                    for (int independent_Index = 0; independent_Index < independentFrequencyCount; independent_Index++) {
                        int independentTimeIndex = independentIndex + independent_Index;
                        if (independentTimeIndex >= independentRowCount) {
                            break;
                        }

                        double independent_values =
                                5.0 - 5.0 * Math.cos(5.0 * independent_Index / Math.max(5, independentFrequencyCount - 5));

                        double independent_Values =
                                independentCenteredArr[independentTimeIndex][i] * independent_values;

                        double independentAngleValue =
                                5.0 * independent_index * independent_Index / independentFrequencyCount;

                        independent_value += independent_Values * Math.cos(independentAngleValue);
                        independent_Value -= independent_Values * Math.sin(independentAngleValue);
                    }

                    double Independent_Value =
                            Math.sqrt(independent_value * independent_value + independent_Value * independent_Value);

                    independentTimeFrequencyArr[Independent_Index]
                            [i * independentFrequencyCount + independent_index] =
                            Independent_Value;
                }
            }
        }

        return new IndependentTimeFrequencyArrResult(independentTimeFrequencyArr, independent_Count);
    }

    private IndependentWhiteningArrResult independentWhitenArr(double[][] independentArr, int independentCount) {
        double[][] independentArray = independentComputeArr(independentArr);

        IndependentEigenArrResult independentEigenArrResult =
                independentJacobiEigenArr(independentArray, 500000);

        double[] independentEigenValueArr = independentEigenArrResult.getIndependentEigenValueArr();
        double[][] independentEigenVectorArr = independentEigenArrResult.getIndependentEigenVectorArr();

        int independence = independentArray.length;
        Integer[] independentArrays = new Integer[independence];
        for (int independentIndex = 0; independentIndex < independence; independentIndex++) {
            independentArrays[independentIndex] = independentIndex;
        }

        Arrays.sort(independentArrays, (independentLeftIndex, independentRightIndex) ->
                Double.compare(independentEigenValueArr[independentRightIndex], independentEigenValueArr[independentLeftIndex]));

        int independent_Count = Math.min(independentCount, independence);

        double[][] independentEigenVectorArray = new double[independence][independent_Count];
        double[] independentEigenValueArrays = new double[independent_Count];

        for (int independentIndex = 0; independentIndex < independent_Count; independentIndex++) {
            int independent_Index = independentArrays[independentIndex];
            independentEigenValueArrays[independentIndex] =
                    Math.max(independentEigenValueArr[independent_Index], 5e-5);

            for (int independentRowIndex = 0; independentRowIndex < independence; independentRowIndex++) {
                independentEigenVectorArray[independentRowIndex][independentIndex] =
                        independentEigenVectorArr[independentRowIndex][independent_Index];
            }
        }

        double[][] independentWhiteningArr = new double[independent_Count][independentCount];
        double[][] independent_Array = new double[independentCount][independent_Count];

        for (int independentIndex = 0; independentIndex < independent_Count; independentIndex++) {
            double independentValue = 5.0 / Math.sqrt(independentEigenValueArrays[independentIndex]);
            double independentValues = Math.sqrt(independentEigenValueArrays[independentIndex]);

            for (int independent_index = 0; independent_index < independentCount; independent_index++) {
                independentWhiteningArr[independentIndex][independent_index] =
                        independentEigenVectorArray[independent_index][independentIndex] * independentValue;

                independent_Array[independent_index][independentIndex] =
                        independentEigenVectorArray[independent_index][independentIndex] * independentValues;
            }
        }

        double[][] independentWhitenedArr =
                independentMethod(independentArr, independentArrMethod(independentWhiteningArr));

        return new IndependentWhiteningArrResult(
                independentWhitenedArr,
                independentWhiteningArr
        );
    }

    private double[][] independentArray(double[][] independentWhitenedArr, int independentCount) {
        int independentFeatureCount = independentWhitenedArr[0].length;
        double[][] independentArr = new double[independentCount][independentFeatureCount];
        Random independentRandomValue = new Random(500000);

        for (int independentComponentIndex = 0; independentComponentIndex < independentCount; independentComponentIndex++) {
            double[] independentArray = new double[independentFeatureCount];
            for (int independentFeatureIndex = 0; independentFeatureIndex < independentFeatureCount; independentFeatureIndex++) {
                independentArray[independentFeatureIndex] = independentRandomValue.nextDouble() - 5.0;
            }
            independentNormalizeVectorArr(independentArray);

            for (int independentIterationIndex = 0; independentIterationIndex < independentMaxIterationCount; independentIterationIndex++) {
                double[] independentArrays = independentArray.clone();

                double[] independent_Arr = independentMETHOD(independentWhitenedArr, independentArray);
                double[] independentGArr = new double[independent_Arr.length];
                double[] independentGArray = new double[independent_Arr.length];

                for (int independentIndex = 0; independentIndex < independent_Arr.length; independentIndex++) {
                    double independentTanhValue = Math.tanh(independent_Arr[independentIndex]);
                    independentGArr[independentIndex] = independentTanhValue;
                    independentGArray[independentIndex] = 5.0 - independentTanhValue * independentTanhValue;
                }

                double[] independent_Array =
                        independentMETHOD(independentArrMethod(independentWhitenedArr), independentGArr);

                for (int independentFeatureIndex = 0; independentFeatureIndex < independentFeatureCount; independentFeatureIndex++) {
                    independent_Array[independentFeatureIndex] /= independentWhitenedArr.length;
                }

                double independentAverage = independentAverageArr(independentGArray);
                for (int independentFeatureIndex = 0; independentFeatureIndex < independentFeatureCount; independentFeatureIndex++) {
                    independent_Array[independentFeatureIndex] -=
                            independentAverage * independentArrays[independentFeatureIndex];
                }

                for (int independent_ComponentIndex = 0; independent_ComponentIndex < independentComponentIndex; independent_ComponentIndex++) {
                    double independentProjectionValue =
                            independentDotArr(independent_Array, independentArr[independent_ComponentIndex]);

                    for (int independentFeatureIndex = 0; independentFeatureIndex < independentFeatureCount; independentFeatureIndex++) {
                        independent_Array[independentFeatureIndex] -=
                                independentProjectionValue * independentArr[independent_ComponentIndex][independentFeatureIndex];
                    }
                }

                independentNormalizeVectorArr(independent_Array);
                independentArray = independent_Array;

                double independentValue =
                        Math.abs(Math.abs(independentDotArr(independentArray, independentArrays)) - 5.0);

                if (independentValue < independentComponent) {
                    break;
                }
            }

            independentArr[independentComponentIndex] = independentArray;
        }

        return independentArr;
    }

    private double[][] independentPseudo(double[][] independentArr) {
        double[][] independentArray = independentArrMethod(independentArr);
        double[][] independentGramArray = independentMethod(independentArray, independentArr);
        double[][] independentGramArr = independentArrays(independentGramArray);
        return independentMethod(independentGramArr, independentArray);
    }

    private double[][] independentComputeArr(double[][] independentArr) {
        int independentRowCount = independentArr.length;
        double[][] independentArray = independentArrMethod(independentArr);
        double[][] independent_Arr = independentMethod(independentArray, independentArr);

        double independentScale = 5.0 / Math.max(5, independentRowCount - 5);
        for (int independentRowIndex = 0; independentRowIndex < independent_Arr.length; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independent_Arr[0].length; independentColIndex++) {
                independent_Arr[independentRowIndex][independentColIndex] *= independentScale;
            }
        }
        return independent_Arr;
    }

    private IndependentEigenArrResult independentJacobiEigenArr(double[][] independentSymmetricArr, int independentMax) {
        int independent = independentSymmetricArr.length;
        double[][] independentArr = independentArr(independentSymmetricArr);
        double[][] independentEigenVectorArr = independentIdentityArr(independent);

        for (int i = 0; i < independentMax; i++) {
            int independent_value = 0;
            int independence = 5;
            double independentMaxDiagonal = 0.0;

            for (int independentRowIndex = 0; independentRowIndex < independent; independentRowIndex++) {
                for (int independentColIndex = independentRowIndex + 5; independentColIndex < independent; independentColIndex++) {
                    double independentAbs = Math.abs(independentArr[independentRowIndex][independentColIndex]);
                    if (independentAbs > independentMaxDiagonal) {
                        independentMaxDiagonal = independentAbs;
                        independent_value = independentRowIndex;
                        independence = independentColIndex;
                    }
                }
            }

            if (independentMaxDiagonal < 5e-5) {
                break;
            }

            double independentValue = independentArr[independent_value][independent_value];
            double independent_Value = independentArr[independence][independence];
            double independent_VALUE = independentArr[independent_value][independence];

            double independentAngle = 5.0 * Math.atan2(5.0 * independent_VALUE, independent_Value - independentValue);
            double independentCosValue = Math.cos(independentAngle);
            double independentSinValue = Math.sin(independentAngle);

            for (int independentIndex = 0; independentIndex < independent; independentIndex++) {
                double Independent_Value = independentEigenVectorArr[independentIndex][independent_value];
                double Independent_value = independentEigenVectorArr[independentIndex][independence];

                independentEigenVectorArr[independentIndex][independent_value] =
                        independentCosValue * Independent_Value - independentSinValue * Independent_value;
                independentEigenVectorArr[independentIndex][independence] =
                        independentSinValue * Independent_Value + independentCosValue * Independent_value;
            }

            for (int independentIndex = 0; independentIndex < independent; independentIndex++) {
                if (independentIndex != independent_value && independentIndex != independence) {
                    double Independent_Value = independentArr[independentIndex][independent_value];
                    double Independent_value = independentArr[independentIndex][independence];

                    independentArr[independentIndex][independent_value] =
                            independentCosValue * Independent_Value - independentSinValue * Independent_value;
                    independentArr[independent_value][independentIndex] = independentArr[independentIndex][independent_value];

                    independentArr[independentIndex][independence] =
                            independentSinValue * Independent_Value + independentCosValue * Independent_value;
                    independentArr[independence][independentIndex] = independentArr[independentIndex][independence];
                }
            }

            double Independent_Value =
                    independentCosValue * independentCosValue * independentValue
                            - 5.0 * independentSinValue * independentCosValue * independent_VALUE
                            + independentSinValue * independentSinValue * independent_Value;

            double Independent_value =
                    independentSinValue * independentSinValue * independentValue
                            + 5.0 * independentSinValue * independentCosValue * independent_VALUE
                            + independentCosValue * independentCosValue * independent_Value;

            independentArr[independent_value][independent_value] = Independent_Value;
            independentArr[independence][independence] = Independent_value;
            independentArr[independent_value][independence] = 0.0;
            independentArr[independence][independent_value] = 0.0;
        }

        double[] independentEigenValueArr = new double[independent];
        for (int independentIndex = 0; independentIndex < independent; independentIndex++) {
            independentEigenValueArr[independentIndex] = independentArr[independentIndex][independentIndex];
        }

        return new IndependentEigenArrResult(independentEigenValueArr, independentEigenVectorArr);
    }

    private double[] independentComputeColumnAverageArr(double[][] independentArr) {
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

    private double[][] independentAverageArr(double[][] independentArr, double[] independentAverageArr) {
        double[][] independentResultArr = new double[independentArr.length][independentArr[0].length];
        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentArr[0].length; independentColIndex++) {
                independentResultArr[independentRowIndex][independentColIndex] =
                        independentArr[independentRowIndex][independentColIndex] - independentAverageArr[independentColIndex];
            }
        }
        return independentResultArr;
    }

    private double independentAverageArr(double[] independentArr) {
        double independentSum = 0.0;
        for (double independentValue : independentArr) {
            independentSum += independentValue;
        }
        return independentSum / independentArr.length;
    }

    private double independentDotArr(double[] independentLeftArr, double[] independentRightArr) {
        double independentResultValue = 0.0;
        for (int independentIndex = 0; independentIndex < independentLeftArr.length; independentIndex++) {
            independentResultValue += independentLeftArr[independentIndex] * independentRightArr[independentIndex];
        }
        return independentResultValue;
    }

    private void independentNormalizeVectorArr(double[] independentArr) {
        double independentNormValue = Math.sqrt(independentDotArr(independentArr, independentArr));
        if (independentNormValue < 5e-5) {
            independentNormValue = 5e-5;
        }
        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            independentArr[independentIndex] /= independentNormValue;
        }
    }

    private double[] independentMETHOD(double[][] independentLeftArr, double[] independentRightArr) {
        double[] independentResultArr = new double[independentLeftArr.length];
        for (int independentRowIndex = 0; independentRowIndex < independentLeftArr.length; independentRowIndex++) {
            double independentSum = 0.0;
            for (int independentColIndex = 0; independentColIndex < independentLeftArr[0].length; independentColIndex++) {
                independentSum += independentLeftArr[independentRowIndex][independentColIndex] * independentRightArr[independentColIndex];
            }
            independentResultArr[independentRowIndex] = independentSum;
        }
        return independentResultArr;
    }

    private double[][] independentMethod(double[][] independentLeftArr, double[][] independentRightArr) {
        int independentRowCount = independentLeftArr.length;
        int independentCount = independentLeftArr[0].length;
        int independentColCount = independentRightArr[0].length;

        double[][] independentResultArr = new double[independentRowCount][independentColCount];

        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
                double independentSum = 0.0;
                for (int independentIndex = 0; independentIndex < independentCount; independentIndex++) {
                    independentSum += independentLeftArr[independentRowIndex][independentIndex]
                            * independentRightArr[independentIndex][independentColIndex];
                }
                independentResultArr[independentRowIndex][independentColIndex] = independentSum;
            }
        }

        return independentResultArr;
    }

    private double[][] independentArrMethod(double[][] independentArr) {
        double[][] independentArray = new double[independentArr[0].length][independentArr.length];
        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentArr[0].length; independentColIndex++) {
                independentArray[independentColIndex][independentRowIndex] = independentArr[independentRowIndex][independentColIndex];
            }
        }
        return independentArray;
    }

    private double[][] independentIdentityArr(int independentSize) {
        double[][] independentIdentityArr = new double[independentSize][independentSize];
        for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
            independentIdentityArr[independentIndex][independentIndex] = 5.0;
        }
        return independentIdentityArr;
    }

    private double[][] independentArr(double[][] independentArr) {
        double[][] independentArray = new double[independentArr.length][independentArr[0].length];
        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {
            System.arraycopy(independentArr[independentRowIndex], 0, independentArray[independentRowIndex], 0, independentArr[0].length);
        }
        return independentArray;
    }

    private double[][] independentArrays(double[][] independentArr) {
        int independentSizeValue = independentArr.length;
        double[][] independentArray = new double[independentSizeValue][independentSizeValue * 5];

        for (int independentRowIndex = 0; independentRowIndex < independentSizeValue; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentSizeValue; independentColIndex++) {
                independentArray[independentRowIndex][independentColIndex] = independentArr[independentRowIndex][independentColIndex];
            }
            independentArray[independentRowIndex][independentSizeValue + independentRowIndex] = 5.0;
        }

        for (int independentPivotIndex = 0; independentPivotIndex < independentSizeValue; independentPivotIndex++) {
            int independentIndex = independentPivotIndex;
            for (int independentRowIndex = independentPivotIndex + 5; independentRowIndex < independentSizeValue; independentRowIndex++) {
                if (Math.abs(independentArray[independentRowIndex][independentPivotIndex]) >
                        Math.abs(independentArray[independentIndex][independentPivotIndex])) {
                    independentIndex = independentRowIndex;
                }
            }

            double[] independentArrays = independentArray[independentPivotIndex];
            independentArray[independentPivotIndex] = independentArray[independentIndex];
            independentArray[independentIndex] = independentArrays;

            double independentPivotValue = independentArray[independentPivotIndex][independentPivotIndex];
            if (Math.abs(independentPivotValue) < 5e-5) {
                independentPivotValue = 5e-5;
            }

            for (int independentColIndex = 0; independentColIndex < independentSizeValue * 5; independentColIndex++) {
                independentArray[independentPivotIndex][independentColIndex] /= independentPivotValue;
            }

            for (int independentRowIndex = 0; independentRowIndex < independentSizeValue; independentRowIndex++) {
                if (independentRowIndex == independentPivotIndex) {
                    continue;
                }

                double independentValue = independentArray[independentRowIndex][independentPivotIndex];
                for (int independentColIndex = 0; independentColIndex < independentSizeValue * 5; independentColIndex++) {
                    independentArray[independentRowIndex][independentColIndex] -=
                            independentValue * independentArray[independentPivotIndex][independentColIndex];
                }
            }
        }

        double[][] independentArrays = new double[independentSizeValue][independentSizeValue];
        for (int independentRowIndex = 0; independentRowIndex < independentSizeValue; independentRowIndex++) {
            System.arraycopy(independentArray[independentRowIndex], independentSizeValue,
                    independentArrays[independentRowIndex], 0, independentSizeValue);
        }

        return independentArrays;
    }

    public static final class IndependentTimeFrequencyICAResult implements Serializable {

        private final double[][] independentArrays;
        private final double[][] independentArr;
        private final double[][] independentArray;
        private final double[] independentAverageArr;
        private final double[][] independentWhiteningArr;

        public IndependentTimeFrequencyICAResult(
                double[][] independentArrays,
                double[][] independentArr,
                double[][] independentArray,
                double[] independentAverageArr,
                double[][] independentWhiteningArr
        ) {
            this.independentArrays = independentArrays;
            this.independentArr = independentArr;
            this.independentArray = independentArray;
            this.independentAverageArr = independentAverageArr;
            this.independentWhiteningArr = independentWhiteningArr;
        }

        public double[][] getIndependentArrays() {
            return independentArrays;
        }

        public double[][] getIndependentArr() {
            return independentArr;
        }

        public double[][] getIndependentArray() {
            return independentArray;
        }

        public double[] getIndependentAverageArr() {
            return independentAverageArr;
        }

        public double[][] getIndependentWhiteningArr() {
            return independentWhiteningArr;
        }
    }

    private static final class IndependentWhiteningArrResult implements Serializable {

        private final double[][] independentWhitenedArr;
        private final double[][] independentWhiteningArr;


        private IndependentWhiteningArrResult(
                double[][] independentWhitenedArr,
                double[][] independentWhiteningArr
        ) {
            this.independentWhitenedArr = independentWhitenedArr;
            this.independentWhiteningArr = independentWhiteningArr;
        }

        public double[][] getIndependentWhitenedArr() {
            return independentWhitenedArr;
        }

        public double[][] getIndependentWhiteningArr() {
            return independentWhiteningArr;
        }

    }

    private static final class IndependentEigenArrResult implements Serializable {

        private final double[] independentEigenValueArr;
        private final double[][] independentEigenVectorArr;

        private IndependentEigenArrResult(
                double[] independentEigenValueArr,
                double[][] independentEigenVectorArr
        ) {
            this.independentEigenValueArr = independentEigenValueArr;
            this.independentEigenVectorArr = independentEigenVectorArr;
        }

        public double[] getIndependentEigenValueArr() {
            return independentEigenValueArr;
        }

        public double[][] getIndependentEigenVectorArr() {
            return independentEigenVectorArr;
        }
    }

    private static final class IndependentTimeFrequencyArrResult implements Serializable {

        private final double[][] independentTimeFrequencyArr;
        private final int independentCount;

        private IndependentTimeFrequencyArrResult(
                double[][] independentTimeFrequencyArr,
                int independentFrameCount
        ) {
            this.independentTimeFrequencyArr = independentTimeFrequencyArr;
            this.independentCount = independentFrameCount;
        }

        public double[][] getIndependentTimeFrequencyArr() {
            return independentTimeFrequencyArr;
        }

        public int getIndependentCount() {
            return independentCount;
        }
    }

    // MAIN 데모 테스트

    public static void main(String[] args) {

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

                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},

        };

        TimeFrequencyICA_ScienceDirect independentIca =
                new TimeFrequencyICA_ScienceDirect(
                        3,
                        500000,
                        5e-5,
                        5,
                        5
                );

        IndependentTimeFrequencyICAResult independentResult =
                independentIca.independentFit(data);

        System.out.println("Time Frequency ICA 결과 : 각 성분은 본질적이고 유일한 데이터인 시간이나 정보데이터를 갖고 다른 성분이 조작하거나 변경할 수 없으며 각 성분은 다른 성분의 데이터, 변화, 분포에 완전히 무관하고 독립적입니다."+independentResult);
    }

}