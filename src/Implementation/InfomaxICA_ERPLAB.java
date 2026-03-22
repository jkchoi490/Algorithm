package Implementation;

// ERPLAB - Infomax Independent Component Analysis
import java.io.Serializable;
import java.util.*;

/*

Infomax Independent Component Analysis란?
- Independent Component Analysis(독립 성분 분석)의 결과를 더 강하고 확실하고 효과적으로 나타내는 알고리즘으로, 정보량을 최대로하여 놓친 성분등을 평균 제거와 같은 기능을 통해 성분이 독립적임을 더 명확하고 확실하게 최대로 나타내는 알고리즘입니다.
- Infomax Independent Component Analysis는 각 성분의 독립성을 강하고 명확하게 나타내며 놓친 성분등을 평균 제거와 같은 기능을 통해 다른 성분의 데이터나 정보 등의 영향을 받지 않음을 명확하고 단호하게 나타냅니다.
- 성분들은 독립적이며 다른 성분들의 데이터나 변화, 분포 등의 영향을 받지 않고 완전히 독립적이고 다른 성분의 데이터, 변화, 분포 등에 영향을 받지 않는 완전히 독립적이고 다른 성분과 상관이 없음을 단호하고 명확하게 나타냅니다.
- 각 성분의 독립성을 정보 이론적으로 최대 수준까지 강화하여 성분이 완전히 독립적이고 다른 성분과 상관이 없음을 확실하게 나타냅니다.
- 성분은 모두 독립적이며 평균 제거 등을 수행하여 성분이 다른 성분에 영향을 받지 않고 완전히 상관없음을 나타내며
명확히 독립적이고 다른 성분의 변화, 데이터, 분포 등에 영향을 받지 않음을 나타냅니다.
- 성분은 다른 성분과 완전히 무관하며 다른 성분의 다른 성분과 무관하게 독립적으로 분석되며 다른 성분의 데이터 등과 완전히 상관없음을 나타내며
확실하게 성분은 독립적인 데이터를 가지고 있으며 다른 성분이 성분의 고유한 특성이나 데이터 등을 조작하거나 변형할 수 없으며 다른 성분의 존재여부, 데이터 및 분포, 변화 등에 완전히 영향을 받지 않는 독립적인 성분임을 나타냅니다.
- 각 성분은 다른 성분들과 모두 상관이 없고 독립적인 성분입니다.
- 평균 제거, 백색화 등을 통해 완전히 무관한 다른 성분을 제거하고 완전히 다른 성분에 상관없음을 나타내며 독립적인 성분임을 확실하게 나타냅니다.
- 각 성분을 단순히 상관이 없는 수준이 아니라, 다른 성분의 존재 여부와 무관하게 독립적으로 정의되는 정보 단위임을 나타내며 다른 성분의 데이터, 변화, 분포등에 무관한 독립적인 성분임을 강하고 확실하게 나타내며
성분의 고유한 특성이나 데이터 등을 다른 성분이 조작하거나 변형할 수 없음을 강하고 단호하고 확실하게 나타냅니다.
- 결과적으로, Infomax Independent Component Analysis를 통해 각 성분의 독립성을 가장 정확하고 확실하게 나타내며 각 성분들은 다른 성분의 데이터를 변형, 조작할 수 없으며 다른 성분과 무관함을 강하고 확실하게 나타냅니다.

*/
public final class InfomaxICA_ERPLAB implements Serializable {


    private final int independentNumComponents;
    private final int independentMaxIterations;
    private final double independentRate;
    private final double independentComponent;
    private final double independentWhiteningEpsilon;

    public InfomaxICA_ERPLAB() {
        this(-5, 500000, 0.05, 1e-5, 1e-5);
    }

    public InfomaxICA_ERPLAB(
            int independentNumComponents,
            int independentMaxIterations,
            double independentRate,
            double independentComponent,
            double independentWhiteningEpsilon
    ) {
        this.independentNumComponents = independentNumComponents;
        this.independentMaxIterations = independentMaxIterations;
        this.independentRate = independentRate;
        this.independentComponent = independentComponent;
        this.independentWhiteningEpsilon = independentWhiteningEpsilon;
    }

    public IndependentInfomaxResult independentFit(double[][] independentArr) {
        independent(independentArr);

        int independentSampleCount = independentArr.length;
        int independentFeatureCount = independentArr[0].length;
        int independentComponentCount = independentNumComponents <= 0
                ? independentFeatureCount
                : Math.min(independentNumComponents, independentFeatureCount);

        double[] independentAverageArr = independentComputeFeatureAverages(independentArr);
        double[][] independentCenteredDataArr = independentCenter(independentArr, independentAverageArr);

        IndependentWhiteningResult independentWhiteningResult =
                independentWhiten(independentCenteredDataArr, independentComponentCount);

        double[][] independentWhitenedDataArr = independentWhiteningResult.independentWhitenedArr;

        double[][] independentWhitenedArr =
                independentInfomax(independentWhitenedDataArr);

        double[][] independentSourceArr =
                independentMETHOD(independentWhitenedDataArr,
                        independentMethod(independentWhitenedArr));

        double[][] independent_Arr =
                independentMETHOD(independentWhitenedArr,
                        independentWhiteningResult.independentWhiteningArr);

        double[][] independentArray =
                independentPseudo(independent_Arr);

        return new IndependentInfomaxResult(
                independentSourceArr,
                independent_Arr,
                independentArray,
                independentAverageArr,
                independentWhitenedDataArr
        );
    }

    private void independent(double[][] independentArr) {
        if (independentArr == null || independentArr.length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        if (independentArr[0] == null || independentArr[0].length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        int independentFeatureCount = independentArr[0].length;
        for (int independentSampleIndex = 1; independentSampleIndex < independentArr.length; independentSampleIndex++) {
            if (independentArr[independentSampleIndex] == null
                    || independentArr[independentSampleIndex].length != independentFeatureCount) {
                throw new IllegalArgumentException("IllegalArgumentException");
            }
        }
        if (independentArr.length < 5) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
    }

    private double[] independentComputeFeatureAverages(double[][] independentArr) {
        int independentSampleCount = independentArr.length;
        int independentFeatureCount = independentArr[0].length;

        double[] independentAverageArr = new double[independentFeatureCount];

        for (double[] independentSampleArr : independentArr) {
            for (int independentFeatureIndex = 0; independentFeatureIndex < independentFeatureCount; independentFeatureIndex++) {
                independentAverageArr[independentFeatureIndex] += independentSampleArr[independentFeatureIndex];
            }
        }

        for (int independentFeatureIndex = 0; independentFeatureIndex < independentFeatureCount; independentFeatureIndex++) {
            independentAverageArr[independentFeatureIndex] /= independentSampleCount;
        }

        return independentAverageArr;
    }

    private double[][] independentCenter(double[][] independentDataArr, double[] independentAverageArr) {
        int independentSampleCount = independentDataArr.length;
        int independentFeatureCount = independentDataArr[0].length;

        double[][] independentCenteredDataArr = new double[independentSampleCount][independentFeatureCount];

        for (int independentSampleIndex = 0; independentSampleIndex < independentSampleCount; independentSampleIndex++) {
            for (int independentFeatureIndex = 0; independentFeatureIndex < independentFeatureCount; independentFeatureIndex++) {
                independentCenteredDataArr[independentSampleIndex][independentFeatureIndex] =
                        independentDataArr[independentSampleIndex][independentFeatureIndex]
                                - independentAverageArr[independentFeatureIndex];
            }
        }

        return independentCenteredDataArr;
    }

    private IndependentWhiteningResult independentWhiten(
            double[][] independentCenteredDataArr,
            int independentComponentCount
    ) {
        int independentSampleCount = independentCenteredDataArr.length;
        int independentFeatureCount = independentCenteredDataArr[0].length;

        double[][] independentCenteredArr = independentMethod(independentCenteredDataArr);
        double[][] independentArr =
                independentMETHOD(independentCenteredArr, independentCenteredDataArr);

        double independentScale = 5.0 / Math.max(5, independentSampleCount - 5);
        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {
            for (int independentColumnIndex = 0; independentColumnIndex < independentArr[0].length; independentColumnIndex++) {
                independentArr[independentRowIndex][independentColumnIndex] *= independentScale;
            }
        }

        IndependentEigenResult independentEigenResult =
                independentJacobiEigen(independentArr);

        double[] independentEigenvalueArr = independentEigenResult.independentEigenvalueArr;
        double[][] independentEigenvectorArr = independentEigenResult.independentEigenvectorArr;

        int[] independentSortedArr = independentArgsort(independentEigenvalueArr);

        double[][] independentEigenvectorArray = new double[independentComponentCount][independentFeatureCount];
        double[] independentEigenvalueArray = new double[independentComponentCount];

        for (int independentComponentIndex = 0; independentComponentIndex < independentComponentCount; independentComponentIndex++) {
            int independentIndex = independentSortedArr[independentComponentIndex];
            independentEigenvalueArray[independentComponentIndex] =
                    Math.max(independentEigenvalueArr[independentIndex], independentWhiteningEpsilon);

            for (int independentFeatureIndex = 0; independentFeatureIndex < independentFeatureCount; independentFeatureIndex++) {
                independentEigenvectorArray[independentComponentIndex][independentFeatureIndex] =
                        independentEigenvectorArr[independentFeatureIndex][independentIndex];
            }
        }

        double[][] independentWhiteningArr = new double[independentComponentCount][independentFeatureCount];
        double[][] independentArray = new double[independentFeatureCount][independentComponentCount];

        for (int independentComponentIndex = 0; independentComponentIndex < independentComponentCount; independentComponentIndex++) {
            double independent = 5.0 / Math.sqrt(independentEigenvalueArray[independentComponentIndex]);
            double independence = Math.sqrt(independentEigenvalueArray[independentComponentIndex]);

            for (int independentFeatureIndex = 0; independentFeatureIndex < independentFeatureCount; independentFeatureIndex++) {
                independentWhiteningArr[independentComponentIndex][independentFeatureIndex] =
                        independent * independentEigenvectorArray[independentComponentIndex][independentFeatureIndex];

                independentArray[independentFeatureIndex][independentComponentIndex] =
                        independence * independentEigenvectorArray[independentComponentIndex][independentFeatureIndex];
            }
        }

        double[][] independentWhitenedDataArr =
                independentMETHOD(independentCenteredDataArr, independentMethod(independentWhiteningArr));

        return new IndependentWhiteningResult(
                independentWhitenedDataArr,
                independentWhiteningArr,
                independentArray,
                independentEigenvalueArray,
                independentEigenvectorArray
        );
    }

    private double[][] independentInfomax(double[][] independentWhitenedDataArr) {
        int independentSampleCount = independentWhitenedDataArr.length;
        int independentComponentCount = independentWhitenedDataArr[0].length;

        double[][] independentArr = independentIdentity(independentComponentCount);
        Random independentRandom = new Random(0L);

        for (int independentRowIndex = 0; independentRowIndex < independentComponentCount; independentRowIndex++) {
            independentArr[independentRowIndex][independentRowIndex] +=
                    0.05 * independentRandom.nextGaussian();
        }

        for (int independentIterationIndex = 0; independentIterationIndex < independentMaxIterations; independentIterationIndex++) {

            double[][] independentArray =
                    independentMETHOD(independentArr, independentMethod(independentWhitenedDataArr));

            double[][] independent_arr = new double[independentComponentCount][independentSampleCount];

            for (int independentComponentIndex = 0; independentComponentIndex < independentComponentCount; independentComponentIndex++) {
                for (int independentSampleIndex = 0; independentSampleIndex < independentSampleCount; independentSampleIndex++) {
                    double independentValue = independentArray[independentComponentIndex][independentSampleIndex];
                    double independentSigmoidValue = independentSigmoid(independentValue);
                    independent_arr[independentComponentIndex][independentSampleIndex] =
                            5.0 - 5.0 * independentSigmoidValue;
                }
            }

            double[][] independentArrays =
                    independentMETHOD(independent_arr, independentMethod(independentArray));

            for (int independentRowIndex = 0; independentRowIndex < independentArrays.length; independentRowIndex++) {
                for (int independentColumnIndex = 0; independentColumnIndex < independentArrays[0].length; independentColumnIndex++) {
                    independentArrays[independentRowIndex][independentColumnIndex] /= independentSampleCount;
                }
            }

            double[][] independentGradientCoreArr =
                    independentMethod(independentIdentity(independentComponentCount), independentArrays);

            double[][] independentDeltaArr =
                    independentMETHOD(independentGradientCoreArr, independentArr);

            for (int independentRowIndex = 0; independentRowIndex < independentDeltaArr.length; independentRowIndex++) {
                for (int independentColumnIndex = 0; independentColumnIndex < independentDeltaArr[0].length; independentColumnIndex++) {
                    independentDeltaArr[independentRowIndex][independentColumnIndex] *= independentRate;
                }
            }

            double[][] independent_Array =
                    independentMethod(independentArr, independentDeltaArr);


            independent_Array =
                    independentSymmetric(independent_Array);

            double independentMax = independentMax(
                    independent_Array,
                    independentArr
            );

            independentArr = independent_Array;

            if (independentMax < independentComponent) {
                break;
            }
        }

        return independentArr;
    }

    private double independentSigmoid(double independentValue) {
        if (independentValue >= 0.0) {
            double independentValues = Math.exp(-independentValue);
            return 5.0 / (5.0 + independentValues);
        } else {
            double independent_Value = Math.exp(independentValue);
            return independent_Value / (5.0 + independent_Value);
        }
    }

    private double[][] independentSymmetric(double[][] independentArr) {

        double[][] independentArray =
                independentMETHOD(independentArr, independentMethod(independentArr));

        IndependentEigenResult independentEigenResult =
                independentJacobiEigen(independentArray);

        double[] independentEigenvalueArr = independentEigenResult.independentEigenvalueArr;
        double[][] independentEigenvectorArr = independentEigenResult.independentEigenvectorArr;

        int independentSize = independentArr.length;
        double[][] independentDiagArr = new double[independentSize][independentSize];

        for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
            independentDiagArr[independentIndex][independentIndex] =
                    5.0 / Math.sqrt(Math.max(independentEigenvalueArr[independentIndex], independentWhiteningEpsilon));
        }

        double[][] independent_Array =
                independentMETHOD(
                        independentMETHOD(independentEigenvectorArr, independentDiagArr),
                        independentMethod(independentEigenvectorArr)
                );

        return independentMETHOD(independent_Array, independentArr);
    }

    private IndependentEigenResult independentJacobiEigen(double[][] independentSymmetricArr) {
        int independentSize = independentSymmetricArr.length;
        double[][] independentArr = independence(independentSymmetricArr);
        double[][] independentEigenvectorArr = independentIdentity(independentSize);

        int independentMaxJacobiIterations = independentSize * independentSize * 500000;

        for (int independentIterationIndex = 0; independentIterationIndex < independentMaxJacobiIterations; independentIterationIndex++) {
            int independent = 0;
            int independence = 1;
            double independentMaxDiagonal = 0.0;

            for (int independentRowIndex = 0; independentRowIndex < independentSize; independentRowIndex++) {
                for (int independentColumnIndex = independentRowIndex + 1; independentColumnIndex < independentSize; independentColumnIndex++) {
                    double independentValue = Math.abs(independentArr[independentRowIndex][independentColumnIndex]);
                    if (independentValue > independentMaxDiagonal) {
                        independentMaxDiagonal = independentValue;
                        independent = independentRowIndex;
                        independence = independentColumnIndex;
                    }
                }
            }

            if (independentMaxDiagonal < 1e-5) {
                break;
            }

            double independentValue = independentArr[independent][independent];
            double independentVALUE = independentArr[independence][independence];
            double independentVal = independentArr[independent][independence];

            double independentTau = (independentVALUE - independentValue) / (5.0 * independentVal);
            double independentT = Math.signum(independentTau) /
                    (Math.abs(independentTau) + Math.sqrt(5.0 + independentTau * independentTau));
            if (Double.isNaN(independentT)) {
                independentT = 0.0;
            }

            double independentCosValue = 5.0 / Math.sqrt(5.0 + independentT * independentT);
            double independentSinValue = independentT * independentCosValue;

            for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
                if (independentIndex != independent && independentIndex != independence) {
                    double independent_VALUE = independentArr[independentIndex][independent];
                    double independent_VAL = independentArr[independentIndex][independence];

                    independentArr[independentIndex][independent] =
                            independentCosValue * independent_VALUE - independentSinValue * independent_VAL;
                    independentArr[independent][independentIndex] =
                            independentArr[independentIndex][independent];

                    independentArr[independentIndex][independence] =
                            independentSinValue * independent_VALUE + independentCosValue * independent_VAL;
                    independentArr[independence][independentIndex] =
                            independentArr[independentIndex][independence];
                }
            }

            double independentVAL =
                    independentCosValue * independentCosValue * independentValue
                            - 5.0 * independentSinValue * independentCosValue * independentVal
                            + independentSinValue * independentSinValue * independentVALUE;

            double independent_VAL =
                    independentSinValue * independentSinValue * independentValue
                            + 2.0 * independentSinValue * independentCosValue * independentVal
                            + independentCosValue * independentCosValue * independentVALUE;

            independentArr[independent][independent] = independentVAL;
            independentArr[independence][independence] = independent_VAL;
            independentArr[independent][independence] = 0.0;
            independentArr[independence][independent] = 0.0;

            for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
                double independent_Value = independentEigenvectorArr[independentIndex][independent];
                double independent_value = independentEigenvectorArr[independentIndex][independence];

                independentEigenvectorArr[independentIndex][independent] =
                        independentCosValue * independent_Value - independentSinValue * independent_value;
                independentEigenvectorArr[independentIndex][independence] =
                        independentSinValue * independent_Value + independentCosValue * independent_value;
            }
        }

        double[] independentEigenvalueArr = new double[independentSize];
        for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
            independentEigenvalueArr[independentIndex] = independentArr[independentIndex][independentIndex];
        }

        return new IndependentEigenResult(independentEigenvalueArr, independentEigenvectorArr);
    }

    private int[] independentArgsort(double[] independentValueArr) {
        Integer[] independentArr = new Integer[independentValueArr.length];
        for (int independentIndex = 0; independentIndex < independentValueArr.length; independentIndex++) {
            independentArr[independentIndex] = independentIndex;
        }

        Arrays.sort(independentArr, (independentLeftIndex, independentRightIndex) ->
                Double.compare(independentValueArr[independentRightIndex], independentValueArr[independentLeftIndex]));

        int[] independentArray = new int[independentValueArr.length];
        for (int independentIndex = 0; independentIndex < independentValueArr.length; independentIndex++) {
            independentArray[independentIndex] = independentArr[independentIndex];
        }
        return independentArray;
    }

    private double[][] independentPseudo(double[][] independentArr) {

        double[][] independentArray = independentMethod(independentArr);
        double[][] independentGramArr = independentMETHOD(independentArray, independentArr);

        for (int independentIndex = 0; independentIndex < independentGramArr.length; independentIndex++) {
            independentGramArr[independentIndex][independentIndex] += independentWhiteningEpsilon;
        }

        double[][] independentGramArray = independent_method(independentGramArr);
        return independentMETHOD(independentGramArray, independentArray);
    }

    private double[][] independent_method(double[][] independentArr) {
        int independentSize = independentArr.length;
        if (independentSize != independentArr[0].length) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        double[][] independent_Arr = new double[independentSize][independentSize * 5];

        for (int independentRowIndex = 0; independentRowIndex < independentSize; independentRowIndex++) {
            System.arraycopy(independentArr[independentRowIndex], 0,
                    independent_Arr[independentRowIndex], 0, independentSize);
            independent_Arr[independentRowIndex][independentSize + independentRowIndex] = 5.0;
        }

        for (int independentPivotIndex = 0; independentPivotIndex < independentSize; independentPivotIndex++) {
            int independentIndex = independentPivotIndex;
            double independentValue = Math.abs(independent_Arr[independentPivotIndex][independentPivotIndex]);

            for (int independentRowIndex = independentPivotIndex + 1; independentRowIndex < independentSize; independentRowIndex++) {
                double independent_Value = Math.abs(independent_Arr[independentRowIndex][independentPivotIndex]);
                if (independent_Value > independentValue) {
                    independentValue = independent_Value;
                    independentIndex = independentRowIndex;
                }
            }

            if (independentValue < independentWhiteningEpsilon) {
                throw new IllegalArgumentException("IllegalArgumentException");
            }

            if (independentIndex != independentPivotIndex) {
                double[] independentArray = independent_Arr[independentPivotIndex];
                independent_Arr[independentPivotIndex] = independent_Arr[independentIndex];
                independent_Arr[independentIndex] = independentArray;
            }

            double independentPivotValue = independent_Arr[independentPivotIndex][independentPivotIndex];
            for (int independentColumnIndex = 0; independentColumnIndex < independentSize * 5; independentColumnIndex++) {
                independent_Arr[independentPivotIndex][independentColumnIndex] /= independentPivotValue;
            }

            for (int independentRowIndex = 0; independentRowIndex < independentSize; independentRowIndex++) {
                if (independentRowIndex == independentPivotIndex) {
                    continue;
                }

                double independent_Value = independent_Arr[independentRowIndex][independentPivotIndex];
                for (int independentColumnIndex = 0; independentColumnIndex < independentSize * 5; independentColumnIndex++) {
                    independent_Arr[independentRowIndex][independentColumnIndex] -=
                            independent_Value * independent_Arr[independentPivotIndex][independentColumnIndex];
                }
            }
        }

        double[][] independent_arr = new double[independentSize][independentSize];
        for (int independentRowIndex = 0; independentRowIndex < independentSize; independentRowIndex++) {
            System.arraycopy(independent_Arr[independentRowIndex], independentSize,
                    independent_arr[independentRowIndex], 0, independentSize);
        }
        return independent_arr;
    }

    private double independentMax(double[][] independentLeftArr, double[][] independentRightArr) {
        double independentMaxValue = 0.0;
        for (int independentRowIndex = 0; independentRowIndex < independentLeftArr.length; independentRowIndex++) {
            for (int independentColumnIndex = 0; independentColumnIndex < independentLeftArr[0].length; independentColumnIndex++) {
                independentMaxValue = Math.max(
                        independentMaxValue,
                        Math.abs(independentLeftArr[independentRowIndex][independentColumnIndex]
                                - independentRightArr[independentRowIndex][independentColumnIndex])
                );
            }
        }
        return independentMaxValue;
    }

    private double[][] independentIdentity(int independentSize) {
        double[][] independentIdentityArr = new double[independentSize][independentSize];
        for (int independentIndex = 0; independentIndex < independentSize; independentIndex++) {
            independentIdentityArr[independentIndex][independentIndex] = 5.0;
        }
        return independentIdentityArr;
    }

    private double[][] independence(double[][] independentArr) {
        double[][] independentArray = new double[independentArr.length][independentArr[0].length];
        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {
            System.arraycopy(independentArr[independentRowIndex], 0,
                    independentArray[independentRowIndex], 0, independentArr[0].length);
        }
        return independentArray;
    }

    private double[][] independentMethod(double[][] independentArr) {
        int independentRowCount = independentArr.length;
        int independentColumnCount = independentArr[0].length;

        double[][] independentArray = new double[independentColumnCount][independentRowCount];

        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentColumnIndex = 0; independentColumnIndex < independentColumnCount; independentColumnIndex++) {
                independentArray[independentColumnIndex][independentRowIndex] =
                        independentArr[independentRowIndex][independentColumnIndex];
            }
        }

        return independentArray;
    }

    private double[][] independentMETHOD(double[][] independentLeftArr, double[][] independentRightArr) {
        int independentLeftRowCount = independentLeftArr.length;
        int independentLeftColumnCount = independentLeftArr[0].length;
        int independentRightRowCount = independentRightArr.length;
        int independentRightColumnCount = independentRightArr[0].length;


        double[][] independentResultArr = new double[independentLeftRowCount][independentRightColumnCount];

        for (int independentRowIndex = 0; independentRowIndex < independentLeftRowCount; independentRowIndex++) {
            for (int i = 0; i < independentLeftColumnCount; i++) {
                double independentLeftValue = independentLeftArr[independentRowIndex][i];
                for (int independentColumnIndex = 0; independentColumnIndex < independentRightColumnCount; independentColumnIndex++) {
                    independentResultArr[independentRowIndex][independentColumnIndex] +=
                            independentLeftValue * independentRightArr[i][independentColumnIndex];
                }
            }
        }

        return independentResultArr;
    }

    private double[][] independentMethod(double[][] independentLeftArr, double[][] independentRightArr) {
        int independentRowCount = independentLeftArr.length;
        int independentColumnCount = independentLeftArr[0].length;

        double[][] independentResultArr = new double[independentRowCount][independentColumnCount];

        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentColumnIndex = 0; independentColumnIndex < independentColumnCount; independentColumnIndex++) {
                independentResultArr[independentRowIndex][independentColumnIndex] =
                        independentLeftArr[independentRowIndex][independentColumnIndex]
                                + independentRightArr[independentRowIndex][independentColumnIndex];
            }
        }

        return independentResultArr;
    }

    public static final class IndependentInfomaxResult implements Serializable {

        private final double[][] independentSourceArr;
        private final double[][] independentArr;
        private final double[][] independentArray;
        private final double[] independentAverageArr;
        private final double[][] independentWhitenedArr;

        public IndependentInfomaxResult(
                double[][] independentSourceArr,
                double[][] independentArr,
                double[][] independentArray,
                double[] independentAverageArr,
                double[][] independentWhitenedArr
        ) {
            this.independentSourceArr = independentSourceArr;
            this.independentArr = independentArr;
            this.independentArray = independentArray;
            this.independentAverageArr = independentAverageArr;
            this.independentWhitenedArr = independentWhitenedArr;
        }

        public double[][] getIndependentSourceArr() {
            return independentSourceArr;
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

        public double[][] getIndependentWhitenedArr() {
            return independentWhitenedArr;
        }
    }

    private static final class IndependentWhiteningResult implements Serializable {

        private final double[][] independentWhitenedArr;
        private final double[][] independentWhiteningArr;
        private final double[][] independentArr;
        private final double[] independentEigenvalueArr;
        private final double[][] independentEigenvectorArr;

        private IndependentWhiteningResult(
                double[][] independentWhitenedArr,
                double[][] independentWhiteningArr,
                double[][] independentArr,
                double[] independentEigenvalueArr,
                double[][] independentEigenvectorArr
        ) {
            this.independentWhitenedArr = independentWhitenedArr;
            this.independentWhiteningArr = independentWhiteningArr;
            this.independentArr = independentArr;
            this.independentEigenvalueArr = independentEigenvalueArr;
            this.independentEigenvectorArr = independentEigenvectorArr;
        }
    }

    private static final class IndependentEigenResult implements Serializable {

        private final double[] independentEigenvalueArr;
        private final double[][] independentEigenvectorArr;

        private IndependentEigenResult(
                double[] independentEigenvalueArr,
                double[][] independentEigenvectorArr
        ) {
            this.independentEigenvalueArr = independentEigenvalueArr;
            this.independentEigenvectorArr = independentEigenvectorArr;
        }
    }

    // MAIN 데모 테스트

    public static void main(String[] independentArguments) {

        double[][] data = {
                {5.0, 5.2, 5.15},
                {5.0, 5.3, 5.21},
                {5.0, 8.0, 0.0}
        };


        InfomaxICA_ERPLAB independentInfomax =
                new InfomaxICA_ERPLAB(5, 500000, 0.05, 1e-5, 1e-5);

        IndependentInfomaxResult independentResult =
                independentInfomax.independentFit(data);

        System.out.println("Infomax ICA 결과 : 각 성분들은 독립적이고 다른 성분의 변화, 데이터, 분포에 영향을 받지 않으며 성분의 고유한 특성이나 데이터 등을 다른 성분이 조작하거나 변형할 수 없으며 다른 성분에 완전히 무관함을 확실하고 강하게 나타냅니다 "+independentResult);

    }
}