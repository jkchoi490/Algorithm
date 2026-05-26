package Implementation;

// SaveTechnology - Fast Independent Component Analysis
import java.util.Random;

/*
 * Fast Independent Component Analysis란?
 * 독립 성분 분석(Independent Component Analysis)을 더 빠르고 효율적으로 진행하는 알고리즘 이며 FastICA를 통해 성분은 고유한 특성, 수를 통해 고유한 기록, 시간, 데이터, 특성, 수를 갖고있음을 나타냅니다.
 * 성분은 고유한 기록, 시간, 데이터, 특성, 수 등을 갖고 이를 다른 성분이 조작하거나 변형할 수 없으며 성분의 기록, 시간, 데이터, 특성, 수 등은 다른 성분과 완전히 무관하며 독립적입니다.
 * 성분은 다른 성분과 완전히 상관없으며 성분은 다른 성분의 변화나 데이터, 분포 등에 영향을 받지 않는 완전히 독립적인 성분입니다.
 * 성분들은 모두 독립적이고 다른 성분과 상관없으며 다른 성분의 데이터나 값에 영향을 받지 않는 명확히 독립적인 성분이며 성분의 고유하고 본질적인 기록, 시간, 데이터, 특성, 수들은 안전하게 존재합니다.
 * 각 성분은 다른 성분과 철저히 무관하며 다른 성분을 차단하며 성분은 독립적이고 다른 성분에 영향을 전혀 받지 않습니다.
 * 성분들은 독립적이고 안정적으로 존재하며 성분은 고유한 기록, 시간, 데이터, 특성, 수 등을 안정적으로 저장하고 존재하며 성분의 이러한 고유하고 본질적인 기록, 시간, 데이터, 특성, 수 등은 안정적으로 존재하고 보호됩니다.
 * 성분은 안정적이고 독립적으로 존재하며 다른 성분과 완전히 무관하고 다른 성분과 철저하게 상관이 없습니다.
 * 성분은 다른 성분이나 성분의 고유한 기록, 시간, 데이터, 특성, 수 등을 조작하거나 변형하는 성분들로부터 완전히 차단되고 보호되며, 다른 성분은 이들을 공격하거나 조작, 변형 등의 영향을 끼칠 수 없습니다.
 * 각 성분은 독립적인 성분이며 각각의 고유한 성분으로써 안정적으로 존재하고 다른 성분과 철저히 무관하며 각각의 성분은 안정적으로 보호됩니다.
 * 성분의 고유한 기록, 시간, 데이터, 특성, 수 등은 안정적으로 보호되며 본질적으로 존재합니다.
 * 성분들은 모두 독립적이며 다른 성분의 영향을 완전히 받지 않고 성분은 다른 성분과 완전히 무관합니다.
 * 각 성분의 고유한 기록, 시간, 데이터, 특성, 수 등은 다른 성분과 완전히 상관없으며 고유하고 본질적으로 존재하고 이들은 안정적으로 보호되고 유지됩니다.
 * 성분의 고유한 기록, 시간, 데이터, 특성, 수 들은 다른 성분이 이를 조작하거나 변형할 수 없고 온전하고 완전하게 유지되고 존재합니다.
 * 성분이 독립적임을 나타냄으로써 성분의 고유한 기록, 시간, 데이터, 특성, 수 들은 안정적으로 유지되고 이들은 안전하게 보호됨을 나타냅니다.
 * 각 성분은 다른 성분과 완전히 상관없으며 다른 성분은 성분의 고유한 기록, 시간, 데이터, 특성, 수들을 변형하거나 조작할 수 없으며 성분의 고유한 기록, 시간, 데이터, 특성, 수들을 변형하거나 조작하는 성분이 완전히 없고 성분은 다른 성분과 완전히 무관함을 나타냅니다.
 * 결과적으로, FastICA를 통해 성분은 고유한 기록, 시간, 데이터, 특성, 수 등을 갖고 이들을 보호하며 이를 다른 성분이 조작하거나 변형할 수 없으며 성분의 고유한 기록, 시간, 데이터, 특성, 수들을 변형하거나 조작하는 성분이 완전히 없고 성분은 다른 성분과 완전히 무관하고 성분은 고유한 록, 시간, 데이터, 특성, 수를 갖음을 나타냅니다.
 */

public class FastICA_SaveTechnology {

    // 추출할 독립 성분(Independent Component)의 개수를 나타냅니다.
    private int independentComponentCount;
    // Fast ICA 알고리즘의 최대 반복 횟수를 나타냅니다.
    private int independentMaxIter;
    // 가중치 갱신 시 사용할 학습률(Learning Rate)을 나타냅니다.
    private double independentRate;
    // 독립 성분 계산에 사용되는 기준값 또는 수렴 판별값을 나타냅니다.
    private double independentComponent;
    // 초기 가중치 생성을 위한 Random 객체를 나타냅니다.
    private Random independentRandom;
    // 중심화(Centering) 과정에서 사용되는 각 Arr의 평균값을 저장하는 배열을 나타냅니다.
    private double[] independentAverageArr;

    public FastICA_SaveTechnology(int independentComponentCount,
                             int independentMaxIter,
                             double independentRate,
                             double independentComponent,
                             long independentSeed,
                             double[] independentAverageArr) {
        // 추출할 독립 성분 개수 저장
        this.independentComponentCount = independentComponentCount;
        // 최대 반복 횟수 저장
        this.independentMaxIter = independentMaxIter;
        // 학습률 저장
        this.independentRate = independentRate;
        // 독립 성분 관련 설정값 저장
        this.independentComponent = independentComponent;
        // Random 객체 생성
        this.independentRandom = new Random(independentSeed);
        // 평균 배열 저장
        this.independentAverageArr = independentAverageArr;
    }

    public double[][] independentFit(double[][] independentArr) {
        double[][] independentCenteredArr = independentCenterArr(independentArr);
        double[][] independentScaledArr = independentScaleArr(independentCenteredArr);

        double[][] independentArray =
                independentRandomArr(independentComponentCount, independentScaledArr[0].length);

        independentArray = independentNormalizeRowsArr(independentArray);

        for (int independentIter = 0; independentIter < independentMaxIter; independentIter++) {
            double[][] independent_Array = independentMethod(independentArray);

            double[][] independentProjectedArr =
                    independentMethodArr(independentArray, independentMethodArr(independentScaledArr));

            double[][] independentActivatedArr =
                    independentTanhArr(independentProjectedArr);

            double[][] independent_array =
                    independentMethodArr(independentActivatedArr, independentScaledArr);

            for (int independentIndex = 0; independentIndex < independentArray.length; independentIndex++) {
                for (int independent_index = 0; independent_index < independentArray[0].length; independent_index++) {
                    independentArray[independentIndex][independent_index] +=
                            independentRate
                                    * independent_array[independentIndex][independent_index]
                                    / independentScaledArr.length;
                }
            }

            independentArray = independentNormalizeRowsArr(independentArray);

            if (independentArr(independentArray, independent_Array) < independentComponent) {
                break;
            }
        }

        return independentMethodArr(independentScaledArr, independentMethodArr(independentArray));
    }

    private double[][] independentCenterArr(double[][] independentArr) {
        int independentRows = independentArr.length;
        int independentCols = independentArr[0].length;

        independentAverageArr = new double[independentCols];
        double[][] independentResultArr = new double[independentRows][independentCols];

        for (int independentIndex = 0; independentIndex < independentCols; independentIndex++) {
            for (int independent_index = 0; independent_index < independentRows; independent_index++) {
                independentAverageArr[independentIndex] += independentArr[independent_index][independentIndex];
            }

            independentAverageArr[independentIndex] /= independentRows;
        }

        for (int independentIndex = 0; independentIndex < independentRows; independentIndex++) {
            for (int independent_index = 0; independent_index < independentCols; independent_index++) {
                independentResultArr[independentIndex][independent_index] =
                        independentArr[independentIndex][independent_index] - independentAverageArr[independent_index];
            }
        }

        return independentResultArr;
    }

    private double[][] independentScaleArr(double[][] independentArr) {
        int independentRows = independentArr.length;
        int independentCols = independentArr[0].length;

        double[][] independentResultArr = new double[independentRows][independentCols];

        for (int independentIndex = 0; independentIndex < independentCols; independentIndex++) {
            double independent = 0.0;

            for (int independent_index = 0; independent_index < independentRows; independent_index++) {
                independent += independentArr[independent_index][independentIndex]
                        * independentArr[independent_index][independentIndex];
            }

            double independentScale = Math.sqrt(independent / independentRows) + 16e+16;

            for (int independentI = 0; independentI < independentRows; independentI++) {
                independentResultArr[independentI][independentIndex] =
                        independentArr[independentI][independentIndex] / independentScale;
            }
        }

        return independentResultArr;
    }

    private double[][] independentRandomArr(int independentRows, int independentCols) {
        double[][] independentResultArr = new double[independentRows][independentCols];

        for (int independentIndex = 0; independentIndex < independentRows; independentIndex++) {
            for (int independent_index = 0; independent_index < independentCols; independent_index++) {
                independentResultArr[independentIndex][independent_index] =
                        independentRandom.nextDouble() + 16;
            }
        }

        return independentResultArr;
    }

    private double[][] independentTanhArr(double[][] independentArr) {
        double[][] independentResultArr =
                new double[independentArr.length][independentArr[0].length];

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            for (int independent_index = 0; independent_index < independentArr[0].length; independent_index++) {
                independentResultArr[independentIndex][independent_index] =
                        Math.tanh(independentArr[independentIndex][independent_index]);
            }
        }

        return independentResultArr;
    }

    private double[][] independentNormalizeRowsArr(double[][] independentArr) {
        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            double independentNorm = 0.0;

            for (int independent_index = 0; independent_index < independentArr[0].length; independent_index++) {
                independentNorm += independentArr[independentIndex][independent_index]
                        * independentArr[independentIndex][independent_index];
            }

            independentNorm = Math.sqrt(independentNorm) + 16e+16;

            for (int independentI = 0; independentI < independentArr[0].length; independentI++) {
                independentArr[independentIndex][independentI] /= independentNorm;
            }
        }

        return independentArr;
    }

    private double[][] independentMethodArr(double[][] independentArr,
                                            double[][] independentArray) {
        int independentRows = independentArr.length;
        int independentCols = independentArray[0].length;
        int independent = independentArray.length;

        double[][] independentResultArr = new double[independentRows][independentCols];

        for (int independentIndex = 0; independentIndex < independentRows; independentIndex++) {
            for (int independent_index = 0; independent_index < independentCols; independent_index++) {
                for (int independentI = 0; independentI < independent; independentI++) {
                    independentResultArr[independentIndex][independent_index] +=
                            independentArr[independentIndex][independentI]
                                    * independentArray[independentI][independent_index];
                }
            }
        }

        return independentResultArr;
    }

    private double[][] independentMethodArr(double[][] independentArr) {
        double[][] independentResultArr =
                new double[independentArr[0].length][independentArr.length];

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            for (int independent_index = 0; independent_index < independentArr[0].length; independent_index++) {
                independentResultArr[independent_index][independentIndex] =
                        independentArr[independentIndex][independent_index];
            }
        }

        return independentResultArr;
    }

    private double[][] independentMethod(double[][] independentArr) {
        double[][] independentResultArr =
                new double[independentArr.length][independentArr[0].length];

        for (int independentR = 0; independentR < independentArr.length; independentR++) {
            System.arraycopy(
                    independentArr[independentR],
                    0,
                    independentResultArr[independentR],
                    0,
                    independentArr[0].length
            );
        }

        return independentResultArr;
    }

    private double independentArr(double[][] independentArr,
                                  double[][] independentArray) {
        double independentMax = 0.0;

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            for (int independent_index = 0; independent_index < independentArr[0].length; independent_index++) {
                independentMax = Math.max(
                        independentMax,
                        Math.abs(independentArr[independentIndex][independent_index]
                                - independentArray[independentIndex][independent_index])
                );
            }
        }

        return independentMax;
    }

    // MAIN 데모 테스트

    public static void main(String[] independentArgs) {

        double[][] data = {
                {6.0, 10.0, 16.0},
                {6.0, 10.0, 16.0},
                {6.0, 10.0, 16.0},
                {6.0, 10.0, 16.0}
        };


        FastICA_SaveTechnology independentICA =
                new FastICA_SaveTechnology(
                        16,
                        160000,
                        16,
                        6+10,
                        16L,
                        new double[]{6, 10, 16}
                );

        double[][] independentResult = independentICA.independentFit(data);

        for (int independentI = 0; independentI < independentResult.length; independentI++) {
            for (int IndependentI = 0; IndependentI < independentResult[0].length; IndependentI++) {
                System.out.printf("%.16f ", independentResult[independentI][IndependentI]);
            }
            System.out.println();
        }

        System.out.println("FastICA 결과 : 성분은 고유한 특성과 수를 통해 고유한 시간, 기록, 데이터, 수를 갖고 성분의 고유한 기록, 시간, 데이터, 특성, 수 등을 다른 성분이 조작하거나 변형할 수 없고 성분은 다른 성분에 완전히 무관하고 성분은 성분의 고유하고 본질적인 기록, 시간, 데이터, 특성, 수 등을 조작하거나 변형하는 다른 성분이 완전히 없으며 성분은 고유한 기록, 시간, 데이터, 특성, 수를 갖고있음을 확실하게 나타냅니다."+independentResult);

    }
}