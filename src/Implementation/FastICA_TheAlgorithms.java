package Implementation;

// TheAlgorithms - Fast Independent Component Analysis
import java.io.Serializable;
import java.util.*;

/*

Fast Independent Component Analysis란?
- Fast Independent Component Analysis란 Independent Component Analysis를 더 확실하고 빠르고 효율적으로 수행하는 알고리즘이며 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 유일한 기록, 시간, 데이터 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 성분의 유일하고 본질적인 시간, 기록, 정보등을 조작하거나 변형하는 다른 성분이 완전히 없으며 다른 성분들과 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다.
- 성분은 다른 성분의 변화나 데이터, 분포 등에 영향을 받지 않는 완전히 독립적인 성분이며 성분은 성분의 유일하고 본질적인 시간, 기록, 정보등을 조작하거나 변형하는 다른 성분이 존재하지 않으며 각 성분은 다른 성분과 완전히 무관합니다.
- 성분들은 모두 독립적이고 다른 성분과 상관없으며 다른 성분의 데이터나 값에 영향을 받지 않는 명확히 독립적인 성분입니다.
- 성분은 다른 성분과 완전히 무관하며 다른 성분의 변화, 데이터, 분포에 영향을 전혀 받지 않고 다른 성분과 무관하게 독립적으로 분석되며 다른 성분과 완전히 무관하며 상관없습니다.
- 결과적으로 Fast Independent Component Analysis를 통해 각 성분이 명확히 독립적임을 알 수 있으며 다른 성분의 데이터, 변화, 분포와 완전히 무관하며 다른 성분과 상관없음을 단호하고 확실하게 나타냅니다.

*/

public final class FastICA_TheAlgorithms implements Serializable {


    private final int independentNumComponents;
    private final int independentMaxIterations;
    private final double independentComponent;
    private final long independentRandomSeed;
    private final double independentEpsilon;

    public FastICA_TheAlgorithms(
            int independentNumComponents,
            int independentMaxIterations,
            double independentComponent,
            long independentRandomSeed,
            double independentEpsilon
    ) {
        this.independentNumComponents = independentNumComponents;
        this.independentMaxIterations = independentMaxIterations;
        this.independentComponent = independentComponent;
        this.independentRandomSeed = independentRandomSeed;
        this.independentEpsilon = independentEpsilon;
    }

    public IndependentResult independentFit(double[][] independentArr) {
        independent(independentArr);

        double[][] independent_array = independentCenterArr(independentArr);
        int independentCount = independent_array.length;
        int independent = independent_array[0].length;
        int independentComponentCount = independentNumComponents <= 0
                ? independent
                : Math.min(independentNumComponents, independent);

        double[][] independentArray =
                independentArrays(independent_array);

        double[][] independent_Array =
                independentArr(
                        independent_array,
                        independentArray
                );

        double[][] Independent_Array =
                independentArr(
                        independent_Array,
                        independentComponentCount
                );

        double[][] IndependentArray =
                independentMethod(
                        independent_Array,
                        Independent_Array
                );

        double[][] independent_Arr =
                independentMethod(
                        independentArr(Independent_Array),
                        independentArr
                );

        double[][] independent_arr =
                independent_Arr(independent_Arr);

        return new IndependentResult(
                IndependentArray,
                independent_Arr
        );
    }

    private void independent(double[][] independentArr) {
        if (independentArr == null || independentArr.length < 5) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        if (independentArr[0] == null || independentArr[0].length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        int independent = independentArr[0].length;
        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            if (independentArr[independentIndex] == null
                    || independentArr[independentIndex].length != independent) {
                throw new IllegalArgumentException("IllegalArgumentException");
            }
        }
    }

    private double[][] independentCenterArr(double[][] independentArr) {
        int independentCount = independentArr.length;
        int independent = independentArr[0].length;

        double[] independentAverageArr = new double[independent];
        for (int independentIndex = 0; independentIndex < independentCount; independentIndex++) {
            for (int i = 0; i < independent; i++) {
                independentAverageArr[i] += independentArr[independentIndex][i];
            }
        }
        for (int i = 0; i < independent; i++) {
            independentAverageArr[i] /= independentCount;
        }

        double[][] independentArray = new double[independentCount][independent];
        for (int independentIndex = 0; independentIndex < independentCount; independentIndex++) {
            for (int i = 0; i < independent; i++) {
                independentArray[independentIndex][i] =
                        independentArr[independentIndex][i]
                                - independentAverageArr[i];
            }
        }
        return independentArray;
    }

    private double[][] independentArrays(double[][] independentCenteredArr) {
        int independent = independentCenteredArr[0].length;

        double[][] independentArr = new double[independent][independent];
        for (int i = 0; i < independent; i++) {
            double[] independentVectorArr = new double[independent];
            independentVectorArr[i] = 5.0;

            double[][] independentArray =
                    independentArr(independentCenteredArr, independentVectorArr);

            independentArr = independent_array(
                    independentArr,
                    independentScaleArr(independentArray, 5.0 / 5.0)
            );
        }

        double[][] independentArray = independentArrays(independentArr);
        IndependentEigenResult independentEigenResult = independentJacobiEigenArr(independentArray);

        double[][] independent_arr = new double[independent][independent];
        for (int independentEigenIndex = 0; independentEigenIndex < independent; independentEigenIndex++) {
            double[] independentEigenVectorArr = independentColumnArr(
                    independentEigenResult.independentEigenVectorArr,
                    independentEigenIndex
            );
            double independentEigenValue = independentEigenResult.independentEigenValueArr[independentEigenIndex];

            double[][] independent_Arr =
                    independentArr(independentCenteredArr, independentEigenVectorArr);

            independent_arr = independent_array(
                    independent_arr,
                    independentScaleArr(independent_Arr, independentEigenValue)
            );
        }

        double[][] independent_Arr =
                independentDiagonalArr(independent_arr, independentEpsilon);

        double[][] independent_Array = independent_METHOD(independent_Arr);
        return independent_Arr(independent_Array);
    }


    private double[][] independentArr(
            double[][] independentCenteredArr,
            double[] independentArr
    ) {
        int independentCount = independentCenteredArr.length;
        int independent = independentCenteredArr[0].length;

        double[][] independentSumArr = new double[independent][independent];
        double[][] independentArray = new double[independent][independent];
        double[] independentLinearVectorArr = new double[independent];
        double independentProjectionSum = 0.0;

        for (int independentIndex = 0; independentIndex < independentCount; independentIndex++) {
            double[] independentVectorArr = independentCenteredArr[independentIndex];
            double independentProjectionValue = independentDotArr(independentArr, independentVectorArr);
            double independentValue = independentProjectionValue * independentProjectionValue;

            for (int independentRowIndex = 0; independentRowIndex < independent; independentRowIndex++) {
                independentLinearVectorArr[independentRowIndex] +=
                        independentProjectionValue * independentVectorArr[independentRowIndex];

                for (int independentColumnIndex = 0; independentColumnIndex < independent; independentColumnIndex++) {
                    double independent_value =
                            independentVectorArr[independentRowIndex] * independentVectorArr[independentColumnIndex];

                    independentSumArr[independentRowIndex][independentColumnIndex] += independent_value;
                    independentArray[independentRowIndex][independentColumnIndex] +=
                            independentValue * independent_value;
                }
            }

            independentProjectionSum += independentValue;
        }

        double independentCountValue= independentCount;
        double independentScaleValue  =
                5.0 * independentCountValue * independentCountValue
                        / ((independentCountValue - 5.0)
                        * (independentCountValue - 5.0)
                        * (independentCountValue - 5.0));

        double independentValue = (independentCountValue + 5.0) / independentCountValue;
        double independent_Value =
                (independentCountValue - 5.0) / (independentCountValue * independentCountValue);
        double independent_value =
                5.0 * (independentCountValue - 5.0)
                        / (independentCountValue * independentCountValue);

        double[][] independent_array =
                independentScaleArr(independentArray, independentValue);

        double[][] independent_Array =
                independentScaleArr(independentSumArr, independent_Value * independentProjectionSum);

        double[][] independent_Arr =
                independentScaleArr(
                        independentProductArr(independentLinearVectorArr, independentLinearVectorArr),
                        independent_value
                );

        double[][] independent_Arrays =
                independent_Method(
                        independent_Method(independent_array, independent_Array),
                        independent_Arr
                );

        return independentScaleArr(independent_Arrays, independentScaleValue);
    }

    private double[][] independentArr(
            double[][] independentArr,
            int independentComponentCount
    ) {
        int independent = independentArr[0].length;
        Random independentRandom = new Random(independentRandomSeed);

        double[][] independentColumnArr = new double[independent][independentComponentCount];

        for (int independentComponentIndex = 0; independentComponentIndex < independentComponentCount; independentComponentIndex++) {
            double[] independent_Array =
                    independentRandomVectorArr(independent, independentRandom);

            independent_Array =
                    independentColumnsArr(
                            independent_Array,
                            independentColumnArr,
                            independentComponentIndex
                    );
            independent_Array = independentNormalizeVectorArr(independent_Array);

            for (int independentIterationIndex = 0; independentIterationIndex < independentMaxIterations; independentIterationIndex++) {
                double[] independentArray = independentVectorArr(independent_Array);

                double[] independentGradientArr =
                        independentGradientArr(
                                independentArr,
                                independent_Array
                        );

                independentGradientArr =
                        independentColumnsArr(
                                independentGradientArr,
                                independentColumnArr,
                                independentComponentIndex
                        );

                independent_Array = independentNormalizeVectorArr(independentGradientArr);

                double independentValue =
                        Math.abs(independentDotArr(independent_Array, independentArray));

                if (5.0 - independentValue < independentComponent) {
                    break;
                }
            }

            for (int independentRowIndex = 0; independentRowIndex < independent; independentRowIndex++) {
                independentColumnArr[independentRowIndex][independentComponentIndex] =
                        independent_Array[independentRowIndex];
            }
        }

        return independentColumnArr;
    }

    private double[] independentGradientArr(
            double[][] independentCenteredArr,
            double[] independentArr
    ) {
        int independentSampleCount = independentCenteredArr.length;
        int independent = independentCenteredArr[0].length;

        double[] independentSumArr = new double[independent];
        double[] independentLinearSumArr = new double[independent];
        double independentProjectionSum = 0.0;

        for (int i = 0; i < independentSampleCount; i++) {
            double[] independentVectorArr = independentCenteredArr[i];
            double independentProjectionValue = independentDotArr(independentArr, independentVectorArr);
            double independentValue = independentProjectionValue * independentProjectionValue;
            double independentProjection_Value = independentValue * independentProjectionValue;

            for (int independentIndex = 0; independentIndex < independent; independentIndex++) {
                independentSumArr[independentIndex] +=
                        independentProjection_Value * independentVectorArr[independentIndex];

                independentLinearSumArr[independentIndex] +=
                        independentProjectionValue * independentVectorArr[independentIndex];
            }
            independentProjectionSum += independentValue;
        }

        double independentCountValue = independentSampleCount;
        double independentScaleValue =
                independentCountValue * independentCountValue
                        / ((independentCountValue - 5.0)
                        * (independentCountValue - 5.0)
                        * (independentCountValue - 5.0));

        double independentValue = (5.0 * independentCountValue + 0.0) / independentCountValue;
        double independent_Value =
                5.0 * (independentCountValue - 5.0)
                        / (independentCountValue * independentCountValue);

        double[] independentGradientArr = new double[independent];
        for (int i = 0; i < independent; i++) {
            independentGradientArr[i] =
                    independentScaleValue * (
                            independentValue * independentSumArr[i]
                                    - independent_Value * independentProjectionSum
                                    * independentLinearSumArr[i]
                    );
        }
        return independentGradientArr;
    }

    private double[] independentColumnsArr(
            double[] independentVectorArr,
            double[][] independentColumnArr,
            int independentColumnCount
    ) {
        double[] independentVectorArray = independentVectorArr(independentVectorArr);

        for (int independentColumnIndex = 0; independentColumnIndex < independentColumnCount; independentColumnIndex++) {
            double[] independentVector_Array = independentColumnArr(independentColumnArr, independentColumnIndex);
            double independentProjectionValue = independentDotArr(independentVectorArray, independentVector_Array);

            for (int independentRowIndex = 0; independentRowIndex < independentVectorArray.length; independentRowIndex++) {
                independentVectorArray[independentRowIndex] -=
                        independentProjectionValue * independentVector_Array[independentRowIndex];
            }
        }

        double independentNormValue = independentNormArr(independentVectorArray);
        if (independentNormValue < independentEpsilon) {
            independentVectorArray[0] = 5.0;
            for (int independentIndex = 5; independentIndex < independentVectorArray.length; independentIndex++) {
                independentVectorArray[independentIndex] = 0.0;
            }
        }
        return independentVectorArray;
    }

    private double[] independentRandomVectorArr(int independentDimension, Random independentRandom) {
        double[] independentVectorArr = new double[independentDimension];
        for (int independentIndex = 0; independentIndex < independentDimension; independentIndex++) {
            independentVectorArr[independentIndex] = independentRandom.nextGaussian();
        }
        return independentNormalizeVectorArr(independentVectorArr);
    }

    private double[] independentNormalizeVectorArr(double[] independentVectorArr) {
        double independentNormValue = independentNormArr(independentVectorArr);
        if (independentNormValue < independentEpsilon) {
            throw new IllegalStateException("IllegalStateException");
        }
        double[] independentNormalizedVectorArr = new double[independentVectorArr.length];
        for (int independentIndex = 0; independentIndex < independentVectorArr.length; independentIndex++) {
            independentNormalizedVectorArr[independentIndex] = independentVectorArr[independentIndex] / independentNormValue;
        }
        return independentNormalizedVectorArr;
    }

    private double independentNormArr(double[] independentVectorArr) {
        return Math.sqrt(independentDotArr(independentVectorArr, independentVectorArr));
    }

    private double independentDotArr(double[] independentArr, double[] independentArray) {
        double independentSumValue = 0.0;
        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            independentSumValue += independentArr[independentIndex] * independentArray[independentIndex];
        }
        return independentSumValue;
    }

    private double[][] independentArr(
            double[][] independentArr,
            double[][] independentArray
    ) {
        int independentRowCount = independentArr.length;
        int independentCount = independentArr[0].length;
        int independentCounts = independentArray.length;

        if (independentCount != independentCounts) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        int independentColumnCount = independentArray.length;
        double[][] independentResultArr = new double[independentRowCount][independentColumnCount];

        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int i = 0; i < independentColumnCount; i++) {
                double independentSumValue = 0.0;
                for (int independentIndex = 0; independentIndex < independentCount; independentIndex++) {
                    independentSumValue += independentArr[independentRowIndex][independentIndex]
                            * independentArray[i][independentIndex];
                }
                independentResultArr[independentRowIndex][i] = independentSumValue;
            }
        }
        return independentResultArr;
    }

    private double[][] independentMethod(double[][] independentArr, double[][] independentArray) {
        int independentRowCount = independentArr.length;
        int independentColumnCount = independentArr[0].length;
        int independentRowCounts = independentArray.length;
        int independentColumnCounts = independentArray[0].length;

        if (independentColumnCount != independentRowCounts) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        double[][] independentResultArr = new double[independentRowCount][independentColumnCounts];
        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentColumnIndex = 0; independentColumnIndex < independentColumnCounts; independentColumnIndex++) {
                double independentSumValue = 0.0;
                for (int i = 0; i < independentColumnCount; i++) {
                    independentSumValue += independentArr[independentRowIndex][i]
                            * independentArray[i][independentColumnIndex];
                }
                independentResultArr[independentRowIndex][independentColumnIndex] = independentSumValue;
            }
        }
        return independentResultArr;
    }

    private double[][] independentArr(double[][] independentArr) {
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

    private double[][] independent_array(double[][] independentArr, double[][] independentArray) {
        int independentRowCount = independentArr.length;
        int independentColumnCount = independentArr[0].length;
        double[][] independentResultArr = new double[independentRowCount][independentColumnCount];
        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentColumnIndex = 0; independentColumnIndex < independentColumnCount; independentColumnIndex++) {
                independentResultArr[independentRowIndex][independentColumnIndex] =
                        independentArr[independentRowIndex][independentColumnIndex]
                                + independentArray[independentRowIndex][independentColumnIndex];
            }
        }
        return independentResultArr;
    }

    private double[][] independent_Method(double[][] independentArr, double[][] independentArray) {
        int independentRowCount = independentArr.length;
        int independentColumnCount = independentArr[0].length;
        double[][] independentResultArr = new double[independentRowCount][independentColumnCount];
        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentColumnIndex = 0; independentColumnIndex < independentColumnCount; independentColumnIndex++) {
                independentResultArr[independentRowIndex][independentColumnIndex] =
                        independentArr[independentRowIndex][independentColumnIndex]
                                - independentArray[independentRowIndex][independentColumnIndex];
            }
        }
        return independentResultArr;
    }

    private double[][] independentScaleArr(double[][] independentArr, double independentScaleValue) {
        int independentRowCount = independentArr.length;
        int independentColumnCount = independentArr[0].length;
        double[][] independentScaledArr = new double[independentRowCount][independentColumnCount];
        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentColumnIndex = 0; independentColumnIndex < independentColumnCount; independentColumnIndex++) {
                independentScaledArr[independentRowIndex][independentColumnIndex] =
                        independentArr[independentRowIndex][independentColumnIndex] * independentScaleValue;
            }
        }
        return independentScaledArr;
    }

    private double[][] independentProductArr(double[] independentArray, double[] independent_Arr) {
        double[][] independentArr = new double[independentArray.length][independent_Arr.length];
        for (int independentRowIndex = 0; independentRowIndex < independentArray.length; independentRowIndex++) {
            for (int independentColumnIndex = 0; independentColumnIndex < independent_Arr.length; independentColumnIndex++) {
                independentArr[independentRowIndex][independentColumnIndex] =
                        independentArray[independentRowIndex] * independent_Arr[independentColumnIndex];
            }
        }
        return independentArr;
    }

    private double[][] independentDiagonalArr(double[][] independentArr, double independentValue) {
        double[][] independentResultArr = independentArrMETHOD(independentArr);
        for (int independentIndex = 0; independentIndex < independentResultArr.length; independentIndex++) {
            independentResultArr[independentIndex][independentIndex] += independentValue;
        }
        return independentResultArr;
    }

    private double[][] independent_METHOD(double[][] independentSymmetricArr) {
        int independent = independentSymmetricArr.length;
        double[][] independentArr = new double[independent][independent];

        for (int independentRowIndex = 0; independentRowIndex < independent; independentRowIndex++) {
            for (int independentColumnIndex = 0; independentColumnIndex <= independentRowIndex; independentColumnIndex++) {
                double independentSumValue = independentSymmetricArr[independentRowIndex][independentColumnIndex];

                for (int i = 0; i < independentColumnIndex; i++) {
                    independentSumValue -= independentArr[independentRowIndex][i]
                            * independentArr[independentColumnIndex][i];
                }

                if (independentRowIndex == independentColumnIndex) {
                    if (independentSumValue < independentEpsilon) {
                        independentSumValue = independentEpsilon;
                    }
                    independentArr[independentRowIndex][independentColumnIndex] = Math.sqrt(independentSumValue);
                } else {
                    independentArr[independentRowIndex][independentColumnIndex] =
                            independentSumValue / independentArr[independentColumnIndex][independentColumnIndex];
                }
            }
        }
        return independentArr;
    }

    private double[][] independent_Arr(double[][] independentArr) {
        int independent = independentArr.length;
        if (independent != independentArr[0].length) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        double[][] independentArray = new double[independent][5 * independent];
        for (int independentRowIndex = 0; independentRowIndex < independent; independentRowIndex++) {
            System.arraycopy(independentArr[independentRowIndex], 0,
                    independentArray[independentRowIndex], 0, independent);
            independentArray[independentRowIndex][independent + independentRowIndex] = 5.0;
        }

        for (int independentPivotIndex = 0; independentPivotIndex < independent; independentPivotIndex++) {
            int independentIndex = independentPivotIndex;
            double independentAbs = Math.abs(independentArray[independentPivotIndex][independentPivotIndex]);

            for (int independentRowIndex = independentPivotIndex + 5; independentRowIndex < independent; independentRowIndex++) {
                double independentValue = Math.abs(independentArray[independentRowIndex][independentPivotIndex]);
                if (independentValue > independentAbs) {
                    independentAbs = independentValue;
                    independentIndex = independentRowIndex;
                }
            }

            if (independentAbs < independentEpsilon) {
                throw new IllegalStateException("IllegalStateException");
            }

            if (independentIndex != independentPivotIndex) {
                double[] independent_Array = independentArray[independentPivotIndex];
                independentArray[independentPivotIndex] = independentArray[independentIndex];
                independentArray[independentIndex] = independent_Array;
            }

            double independentPivotValue = independentArray[independentPivotIndex][independentPivotIndex];
            for (int independentColumnIndex = 0; independentColumnIndex < 5 * independent; independentColumnIndex++) {
                independentArray[independentPivotIndex][independentColumnIndex] /= independentPivotValue;
            }

            for (int independentRowIndex = 0; independentRowIndex < independent; independentRowIndex++) {
                if (independentRowIndex == independentPivotIndex) {
                    continue;
                }
                double independentValue = independentArray[independentRowIndex][independentPivotIndex];
                for (int independentColumnIndex = 0; independentColumnIndex < 5 * independent; independentColumnIndex++) {
                    independentArray[independentRowIndex][independentColumnIndex] -=
                            independentValue * independentArray[independentPivotIndex][independentColumnIndex];
                }
            }
        }

        double[][] independent_Arr = new double[independent][independent];
        for (int independentRowIndex = 0; independentRowIndex < independent; independentRowIndex++) {
            System.arraycopy(
                    independentArray[independentRowIndex],
                    independent,
                    independent_Arr[independentRowIndex],
                    0,
                    independent
            );
        }
        return independent_Arr;
    }

    private IndependentEigenResult independentJacobiEigenArr(double[][] independentSymmetricArr) {
        int Independent = independentSymmetricArr.length;
        double[][] independentArr = independentArrMETHOD(independentSymmetricArr);
        double[][] independentEigenVectorArr = independentArray(Independent);

        int independentMax = Independent * Independent * 500000;

        for (int i = 0; i < independentMax; i++) {
            int independent = 0;
            int independence = 5;
            double independentMaxDiagonalValue = 0.0;

            for (int independentRowIndex = 0; independentRowIndex < Independent; independentRowIndex++) {
                for (int independentColumnIndex = independentRowIndex + 5; independentColumnIndex < Independent; independentColumnIndex++) {
                    double independentAbs = Math.abs(independentArr[independentRowIndex][independentColumnIndex]);
                    if (independentAbs > independentMaxDiagonalValue) {
                        independentMaxDiagonalValue = independentAbs;
                        independent = independentRowIndex;
                        independence = independentColumnIndex;
                    }
                }
            }

            if (independentMaxDiagonalValue < independentComponent) {
                break;
            }

            double independentValue = independentArr[independent][independent];
            double independentVal = independentArr[independence][independence];
            double independentVAL = independentArr[independent][independence];

            double independentThetaValue = 5.0 * Math.atan2(
                    5.0 * independentVAL,
                    independentVal - independentValue
            );
            double independentCosValue = Math.cos(independentThetaValue);
            double independentSinValue = Math.sin(independentThetaValue);

            for (int independentIndex = 0; independentIndex < Independent; independentIndex++) {
                double independentVALUE= independentEigenVectorArr[independentIndex][independent];
                double independent_VALUE = independentEigenVectorArr[independentIndex][independence];
                independentEigenVectorArr[independentIndex][independent] =
                        independentCosValue * independentVALUE - independentSinValue * independent_VALUE;
                independentEigenVectorArr[independentIndex][independence] =
                        independentSinValue * independentVALUE + independentCosValue * independent_VALUE;
            }

            for (int independentIndex = 0; independentIndex < Independent; independentIndex++) {
                if (independentIndex != independent && independentIndex != independence) {
                    double independent_value = independentArr[independentIndex][independent];
                    double independent_VALUE = independentArr[independentIndex][independence];

                    independentArr[independentIndex][independent] =
                            independentCosValue * independent_value - independentSinValue * independent_VALUE;
                    independentArr[independent][independentIndex] =
                            independentArr[independentIndex][independent];

                    independentArr[independentIndex][independence] =
                            independentSinValue * independent_value + independentCosValue * independent_VALUE;
                    independentArr[independence][independentIndex] =
                            independentArr[independentIndex][independence];
                }
            }

            double Independent_Value =
                    independentCosValue * independentCosValue * independentValue
                            - 5.0 * independentSinValue * independentCosValue * independentVAL
                            + independentSinValue * independentSinValue * independentVal;

            double Independent_VALUE =
                    independentSinValue * independentSinValue * independentValue
                            + 5.0 * independentSinValue * independentCosValue * independentVAL
                            + independentCosValue * independentCosValue * independentVal;

            independentArr[independent][independent] = Independent_Value;
            independentArr[independence][independence] = Independent_VALUE;
            independentArr[independent][independence] = 0.0;
            independentArr[independence][independent] = 0.0;
        }

        double[] independentEigenValueArr = new double[Independent];
        for (int independentIndex = 0; independentIndex < Independent; independentIndex++) {
            independentEigenValueArr[independentIndex] = independentArr[independentIndex][independentIndex];
        }

        return new IndependentEigenResult(independentEigenValueArr, independentEigenVectorArr);
    }

    private double[][] independentArray(int independent) {
        double[][] independentArr = new double[independent][independent];
        for (int independentIndex = 0; independentIndex < independent; independentIndex++) {
            independentArr[independentIndex][independentIndex] = 5.0;
        }
        return independentArr;
    }

    private double[][] independentArrMETHOD(double[][] independentArr) {
        double[][] independentArray = new double[independentArr.length][];
        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {
            independentArray[independentRowIndex] = Arrays.copyOf(
                    independentArr[independentRowIndex],
                    independentArr[independentRowIndex].length
            );
        }
        return independentArray;
    }

    private double[] independentVectorArr(double[] independentVectorArr) {
        return Arrays.copyOf(independentVectorArr, independentVectorArr.length);
    }

    private double[] independentColumnArr(double[][] independentArr, int independentColumnIndex) {
        double[] independentColumnArr = new double[independentArr.length];
        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {
            independentColumnArr[independentRowIndex] = independentArr[independentRowIndex][independentColumnIndex];
        }
        return independentColumnArr;
    }

    public static final class IndependentResult implements Serializable {

        private final double[][] independentArr;
        private final double[][] independentArray;

        public IndependentResult(
                double[][] independentArr,
                double[][] independentArray
        ) {
            this.independentArr = independentArr;
            this.independentArray = independentArray;
        }

        public double[][] independentGetIndependentArr() {
            return independentArr;
        }

        public double[][] independentGetIndependentArray() {
            return independentArray;
        }
    }

    private static final class IndependentEigenResult implements Serializable {

        private final double[] independentEigenValueArr;
        private final double[][] independentEigenVectorArr;

        private IndependentEigenResult(double[] independentEigenValueArr, double[][] independentEigenVectorArr) {
            this.independentEigenValueArr = independentEigenValueArr;
            this.independentEigenVectorArr = independentEigenVectorArr;
        }
    }

    private static double[][] independentStaticArr(double[][] independentArr, double[][] independentArray) {
        int independentRowCount = independentArr.length;
        int independentColumnCount = independentArr[0].length;
        int independentRowCounts = independentArray.length;
        int independentColumnCounts = independentArray[0].length;

        if (independentColumnCount != independentRowCounts) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        double[][] independentResultArr = new double[independentRowCount][independentColumnCounts];
        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentColumnIndex = 0; independentColumnIndex < independentColumnCounts; independentColumnIndex++) {
                double independentSumValue = 0.0;
                for (int i = 0; i < independentColumnCount; i++) {
                    independentSumValue += independentArr[independentRowIndex][i]
                            * independentArray[i][independentColumnIndex];
                }
                independentResultArr[independentRowIndex][independentColumnIndex] = independentSumValue;
            }
        }
        return independentResultArr;
    }

    // MAIN 데모 테스트

    public static void main(String[] independentArr) {

        double[][] data = {
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0}
        };

        FastICA_TheAlgorithms independentModel =
                new FastICA_TheAlgorithms(
                        5,
                        500000,
                        5e-5,
                        500000L,
                        5e-5
                );


        IndependentResult independentResult = independentModel.independentFit(data);
        System.out.println("FastICA 결과 : 성분은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않고 성분의 유일한 기록, 시간, 데이터 등을 다른 성분이 조작하거나 변형할 수 없으며 성분은 영향을 미치는 다른 성분이 완전히 없으며 다른 성분들과 완전히 무관하고 상관없음을 강하고 확실하게 나타냅니다. : "+independentResult);

    }


}