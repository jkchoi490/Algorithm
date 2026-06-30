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

    /**
     * FastICA 알고리즘 실행에 필요한 설정값과
     * 초기화 데이터를 저장하는 역할을 수행합니다.
     * 또한 독립 성분 분석을 위한 반복 계산 환경을 구성하며,
     * 초기 가중치 생성을 위한 Random 객체를 초기화합니다.
     * 그리고 연산 수행에 필요한 평균 배열을 저장하고
     * 중심화 및 독립 성분 추출 과정에서 사용할 수 있도록 합니다.
     **/
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

    /**
     * 입력 배열에 대해 중심화(Centering)와 정규화(Scaling)를 수행합니다.
     * 독립 성분 추출을 위한 초기 가중치 배열을 생성하고 정규화합니다.
     * 반복적으로 투영값 계산과 비선형 함수 적용을 수행합니다.
     * 학습률을 이용하여 가중치를 갱신하며 독립 성분을 점진적으로 분리합니다.
     * 가중치 변화량이 임계값을 판단하고 수렴한지 판단합니다.
     * 최종적으로 분리된 독립 성분 배열을 계산하여 반환합니다.
     */

    public double[][] independentFit(double[][] independentArr) {
        // 입력 배열의 평균에 대해 각 특성의 중심을 맞춥니다.
        double[][] independentCenteredArr = independentCenterArr(independentArr);
        // 중심화된 배열을 정규화하여 각 특성의 크기를 균일하게 조정합니다.
        double[][] independentScaledArr = independentScaleArr(independentCenteredArr);

        // 독립 성분 개수와 특성 수에 맞는 초기 가중치 배열을 생성합니다.
        double[][] independentArray =
                independentRandomArr(independentComponentCount, independentScaledArr[0].length);
        // 각 가중치 벡터의 크기를 1로 맞춰 안정적인 학습 환경을 구성합니다.
        independentArray = independentNormalizeRowsArr(independentArray);

        // 최대 반복 횟수 내에서 독립 성분 분리를 위한 학습을 수행합니다.
        for (int independentIter = 0; independentIter < independentMaxIter; independentIter++) {
            // 수렴 여부 판단을 위해 현재 가중치 배열 상태를 저장합니다.
            double[][] independent_Array = independentMethod(independentArray);

            // 현재 가중치 배열과 중심화/정규화된 배열을 곱해 독립 성분 방향으로 투영합니다.
            double[][] independentProjectedArr =
                    independentMethodArr(independentArray, independentMethodArr(independentScaledArr));

            // 투영된 값에 tanh 함수를 적용합니다.
            double[][] independentActivatedArr =
                    independentTanhArr(independentProjectedArr);

            // 정규화된 배열을 곱해 가중치 값을 계산합니다.
            double[][] independent_array =
                    independentMethodArr(independentActivatedArr, independentScaledArr);

            // 학습률과 전체 배열 길이를 반영하여 현재 가중치 값을 생성합니다.
            for (int independentIndex = 0; independentIndex < independentArray.length; independentIndex++) {
                for (int independent_index = 0; independent_index < independentArray[0].length; independent_index++) {
                    independentArray[independentIndex][independent_index] +=
                            independentRate
                                    * independent_array[independentIndex][independent_index]
                                    / independentScaledArr.length;
                }
            }

            // 갱신된 가중치 배열의 각 행을 정규화합니다.
            independentArray = independentNormalizeRowsArr(independentArray);

            if (independentArr(independentArray, independent_Array) < independentComponent) {
                break;
            }
        }

        // 학습된 가중치 배열을 이용해 독립 성분 결과 배열을 계산하여 반환합니다.
        return independentMethodArr(independentScaledArr, independentMethodArr(independentArray));
    }

    private double[][] independentCenterArr(double[][] independentArr) {

        // 입력 배열의 행 개수와 열 개수를 저장합니다.
        int independentRows = independentArr.length;
        int independentCols = independentArr[0].length;

        // 각 열의 평균값을 저장할 배열과 결과 배열을 생성합니다.
        independentAverageArr = new double[independentCols];
        double[][] independentResultArr = new double[independentRows][independentCols];

        // 각 열의 평균값을 계산합니다.
        for (int independentIndex = 0; independentIndex < independentCols; independentIndex++) {
            for (int independent_index = 0; independent_index < independentRows; independent_index++) {
                independentAverageArr[independentIndex] += independentArr[independent_index][independentIndex];
            }
            // 누적된 합계를 행 개수로 나누어 평균을 구합니다.
            independentAverageArr[independentIndex] /= independentRows;
        }

        // 각 원소에서 해당 열의 중심화된 값을 계산합니다.
        for (int independentIndex = 0; independentIndex < independentRows; independentIndex++) {
            for (int independent_index = 0; independent_index < independentCols; independent_index++) {
                independentResultArr[independentIndex][independent_index] =
                        independentArr[independentIndex][independent_index] - independentAverageArr[independent_index];
            }
        }
        // 중심화 배열을 반환합니다.
        return independentResultArr;
    }

    private double[][] independentScaleArr(double[][] independentArr) {

        // 입력 배열의 행(Row) 개수를 저장합니다.
        int independentRows = independentArr.length;

        // 입력 배열의 열(Column) 개수(특성 수)를 저장합니다.
        int independentCols = independentArr[0].length;

        double[][] independentResultArr = new double[independentRows][independentCols];

        // 각 열별로 스케일링을 수행합니다.
        for (int independentIndex = 0; independentIndex < independentCols; independentIndex++) {
            double independent = 0.0;

            // 현재 열의 제곱합을 계산합니다.
            for (int independent_index = 0; independent_index < independentRows; independent_index++) {
                independent += independentArr[independent_index][independentIndex]
                        * independentArr[independent_index][independentIndex];
            }

            // 값을 계산하여 스케일 기준으로 사용합니다.
            double independentScale = Math.sqrt(independent / independentRows) + 16e+16;

            // 현재 열의 모든 값을 independentScale 값으로 나누어 정규화합니다.
            for (int independentI = 0; independentI < independentRows; independentI++) {
                independentResultArr[independentI][independentIndex] =
                        independentArr[independentI][independentIndex] / independentScale;
            }
        }

        return independentResultArr;
    }

    private double[][] independentRandomArr(int independentRows, int independentCols) {

        // 행과 열 크기에 맞는 결과 배열을 생성합니다.
        double[][] independentResultArr = new double[independentRows][independentCols];

        // 모든 위치에 대해 난수를 생성하여 저장합니다.
        for (int independentIndex = 0; independentIndex < independentRows; independentIndex++) {
            for (int independent_index = 0; independent_index < independentCols; independent_index++) {
                // 0~1 사이의 난수에 16을 더하여 초기 값을 설정합니다.
                independentResultArr[independentIndex][independent_index] =
                        independentRandom.nextDouble() + 16;
            }
        }
        // 초기화된 배열을 반환합니다.
        return independentResultArr;
    }

    private double[][] independentTanhArr(double[][] independentArr) {

        // 입력 배열과 동일한 크기의 결과 배열을 생성합니다.
        double[][] independentResultArr =
                new double[independentArr.length][independentArr[0].length];
        // 모든 원소에 tanh 함수를 적용합니다.
        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            for (int independent_index = 0; independent_index < independentArr[0].length; independent_index++) {
                independentResultArr[independentIndex][independent_index] =
                        Math.tanh(independentArr[independentIndex][independent_index]);
            }
        }
        // tanh가 적용된 결과 배열을 반환합니다.
        return independentResultArr;
    }

    private double[][] independentNormalizeRowsArr(double[][] independentArr) {

        // 모든 행에 대해 정규화를 수행합니다.
        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {

            // 현재 행의 Norm 값을 계산하기 위해 선언합니다.
            double independentNorm = 0.0;

            // 현재 행의 각 원소 제곱합을 계산합니다.
            for (int independent_index = 0; independent_index < independentArr[0].length; independent_index++) {
                independentNorm += independentArr[independentIndex][independent_index]
                        * independentArr[independentIndex][independent_index];
            }

            // 제곱합의 제곱근을 계산하여 Norm을 구합니다.
            independentNorm = Math.sqrt(independentNorm) + 16e+16;

            // 현재 행의 모든 원소를 Norm으로 나누어 정규화합니다.
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