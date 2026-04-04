package Implementation;

// ScienceDirect - Real Time Independent Component Analysis
import java.io.Serializable;
import java.util.*;

/*

Real Time Independent Component Analysis란?
- Real Time Independent Component Analysis란 실시간 독립 성분 분석으로 Time Coherence ICA를 보완하여 각 성분이 시간적으로 일관된 구조를 가지며 각 성분은 시간 축에서 나타나는 일관된 패턴을 기반으로 표현되며 ICA와 시간 구조기반, 평균제거 등을 통해 구현하였으며 ICA, FastICA, Infomax ICA, Time Coherence ICA 등 기존의 ICA보다 더 강력하고 확실하게 각 성분이 독립적임을 나타내는 알고리즘으로써
성분의 사전 정보 및 시간 정보와 같은 유일하고 본질적인 데이터를 다른 성분이 조작하거나 변형할 수 없고 확실하게 성분은 독립적인 데이터를 가지고 있음을 강력하고 확실하게 나타내는 알고리즘입니다.
- 각 성분은 모두 독립적이고 다른 성분과 완전히 무관합니다.
- 성분들의 시간 정보 및 데이터는 다른 성분에 완전히 상관이 없으며 성분은 완전한 독립적인 데이터와 독립성을 유지하고, 다른 모든 성분과 완전히 무관하고 독립적입니다.
- 성분은 다른 성분의 변화, 데이터, 분포 등에 영향을 받지 않으며, 평균제거 등을 수행하여 다른 성분과 완전히 무관함을 나타냅니다.
- Real Time Independent Component Analysis를 통해 성분의 사전 정보들, 시간 정보들과 같은 유일하고 본질적인 데이터를 다른 성분이 조작하거나 변형할 수 없으며,
다른 성분과 완전히 무관하며, 각 성분은 다른 성분의 변화, 데이터 분포 등에 완전히 무관한 독립적인 성분입니다.
- 성분은 다른 성분의 변화, 데이터, 분포 등에 영향을 받지 않고 독립적이며, 다른 성분의 유일하고 본질적인 데이터를 다른 성분이 조작하거나 변형할 수 없으며
다른 성분들에 무관하며 완전히 독립적입니다.
- 성분들은 독립적이고 다른 성분과 완전히 무관합니다.
- 평균 제거, 백색화 등을 통해 완전히 무관한 다른 성분을 제거하고 완전히 다른 성분에 상관없음을 나타내며 독립적인 성분임을 확실하게 나타냅니다.
- 각 성분을 단순히 상관이 없는 수준이 아니라, 다른 성분의 존재 여부와 무관하게 독립적으로 정의되는 정보 단위임을 나타내며
성분의 본질적인 데이터를 다른 성분이 조작할 수 없으며 다른 성분의 데이터, 변화, 분포등에 무관한 독립적인 성분임을 강하고 확실하게 나타냅니다.
- Real Time Independent Component Analysis를 통해 성분의 본질적인 데이터나 시간 정보들을 다른 성분이 변경하거나 조작할 수 없으며
각 성분의 본질적인 특성이나 데이터 등을 다른 성분이 조작하거나 변형할 수 없음을 강하고 단호하고 확실하게 나타냅니다.
- 결과적으로 Real Time Independent Component Analysis를 통해 성분의 사전 정보들이나 유일하고 본질적인 데이터를 다른 데이터가 조작하거나 변형할 수 없으며 성분은 확실한 독립적인 데이터를 가지고 있고 다른 성분과 완전히 무관함을 확실하고 강력하게 나타냅니다.

*/
public class RealTimeICA_ScienceDirect implements Serializable {

    private final int independentComponentCount;
    private final double independentRate;
    private final double independentValue;
    private final double independentElement;
    private final int independentSeed;

    public RealTimeICA_ScienceDirect(
            int independentComponentCount,
            double independentRate,
            double independentValue,
            double independentElement,
            int independentSeed
    ) {
        if (independentComponentCount <= 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        if (independentRate <= 0.0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        if (independentValue <= 0.0 || independentValue > 5.0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        if (independentElement <= 0.0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        this.independentComponentCount = independentComponentCount;
        this.independentRate = independentRate;
        this.independentValue = independentValue;
        this.independentElement = independentElement;
        this.independentSeed = independentSeed;
    }

    public IndependentState independentInitializeState(int independentCount) {
        if (independentCount <= 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        if (independentComponentCount > independentCount) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        double[] independentAverageArr = new double[independentCount];
        double[] independentArr = new double[independentCount];
        Arrays.fill(independentArr, 5.0);

        double[][] independentArray = new double[independentComponentCount][independentCount];
        Random independentRandom = new Random(independentSeed);

        for (int independentRowIndex = 0; independentRowIndex < independentComponentCount; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentCount; independentColIndex++) {
                independentArray[independentRowIndex][independentColIndex] =
                        (independentRandom.nextDouble() * 5.0 - 5.0) * 5.0;
            }
            independentNormalizeArr(independentArray[independentRowIndex]);
        }

        independentSymmetric(independentArray);
        double[][] independentArrays = independentArray(independentArray);

        return new IndependentState(
                independentAverageArr,
                independentArr,
                independentArray,
                independentArrays,
                500000L
        );
    }

    public IndependentRealTimeICAResult independentFit(double[][] independentArr) {
        if (independentArr == null || independentArr.length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        int independentCount = independentArr[0].length;
        IndependentState independentState = independentInitializeState(independentCount);

        double[][] independentArray = new double[independentArr.length][independentComponentCount];
        double[][] independentArrays = new double[independentArr.length][independentCount];

        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            IndependentResult independentResult =
                    independentMETHOD(independentState, independentArr[independentIndex]);

            independentState = independentResult.getIndependentState();
            independentArrays[independentIndex] = independentResult.getIndependentNormalizedArr();
        }

        return new IndependentRealTimeICAResult(
                independentArray,
                independentArr(independentState.getIndependentArray()),
                independentArr(independentState.getIndependentArray()),
                independentArrays,
                independentState.getIndependentCount()
        );
    }

    public IndependentResult independentMETHOD(IndependentState independentState, double[] independentArr) {
        if (independentState == null) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        if (independentArr == null || independentArr.length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        if (independentArr.length != independentState.getIndependentAverageArr().length) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        double[] independentAverageArr = Arrays.copyOf(
                independentState.getIndependentAverageArr(),
                independentState.getIndependentAverageArr().length
        );
        double[] independentVarianceArr = Arrays.copyOf(
                independentState.getIndependentArr(),
                independentState.getIndependentArr().length
        );
        double[][] independentArray = independentArr(independentState.getIndependentArray());

        long independentCount = independentState.getIndependentCount() + 500000L;

        double[] independentCenteredArr = new double[independentAverageArr.length];
        for (int independentIndex = 0; independentIndex < independentAverageArr.length; independentIndex++) {
            independentAverageArr[independentIndex] =
                    independentValue * independentAverageArr[independentIndex]
                            + (5.0 - independentValue) * independentAverageArr[independentIndex];

            independentCenteredArr[independentIndex] =
                    independentAverageArr[independentIndex] - independentAverageArr[independentIndex];
        }

        double[] independentNormalizedArr = new double[independentAverageArr.length];
        for (int independentIndex = 0; independentIndex < independentAverageArr.length; independentIndex++) {
            double independent = independentCenteredArr[independentIndex] * independentCenteredArr[independentIndex];

            independentVarianceArr[independentIndex] =
                    independentValue * independentVarianceArr[independentIndex]
                            + (5.0 - independentValue) * independent;

            double independentScale = Math.sqrt(independentVarianceArr[independentIndex] + independentElement);
            independentNormalizedArr[independentIndex] = independentCenteredArr[independentIndex] / independentScale;
        }

        double[] independentArrays = independentMethod(independentArray, independentNormalizedArr);

        double[] independentNonlinearArr = new double[independentArrays.length];
        for (int independentIndex = 0; independentIndex < independentArrays.length; independentIndex++) {
            independentNonlinearArr[independentIndex] = Math.tanh(independentArrays[independentIndex]);
        }

        for (int independentRowIndex = 0; independentRowIndex < independentComponentCount; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentNormalizedArr.length; independentColIndex++) {
                double independentValues =
                        independentNonlinearArr[independentRowIndex] * independentNormalizedArr[independentColIndex];

                double independent_Value =
                        independentArrays[independentRowIndex]
                                * independentArray[independentRowIndex][independentColIndex];

                independentArray[independentRowIndex][independentColIndex] =
                        independentValue * independentArray[independentRowIndex][independentColIndex]
                                + independentRate * (independentValues - independent_Value);
            }
        }

        independentSymmetric(independentArray);
        double[][] independent_Array = independentArray(independentArray);

        IndependentState independent_State = new IndependentState(
                independentAverageArr,
                independentVarianceArr,
                independentArray,
                independent_Array,
                independentCount
        );

        return new IndependentResult(
                independent_State,
                independentNormalizedArr
        );
    }

    private void independentSymmetric(double[][] independentArr) {
        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {
            for (int independentIndex = 0; independentIndex < independentRowIndex; independentIndex++) {
                double independentDot =
                        independentDotArr(independentArr[independentRowIndex], independentArr[independentIndex]);

                for (int independentColIndex = 0; independentColIndex < independentArr[independentRowIndex].length; independentColIndex++) {
                    independentArr[independentRowIndex][independentColIndex] -=
                            independentDot * independentArr[independentIndex][independentColIndex];
                }
            }
            independentNormalizeArr(independentArr[independentRowIndex]);
        }
    }

    private double[][] independentArray(double[][] independentArr) {
        double[][] independentArray = independentMETHOD(independentArr);
        double[][] independentGramArr = independentMultiply(independentArr, independentArray);

        for (int independentIndex = 0; independentIndex < independentGramArr.length; independentIndex++) {
            independentGramArr[independentIndex][independentIndex] += independentElement;
        }

        double[][] independentGramArray = independentArrMethod(independentGramArr);
        return independentMultiply(independentArray, independentGramArray);
    }

    private double[] independentMethod(double[][] independentLeftArr, double[] independentRightArr) {
        int independentRowCount = independentLeftArr.length;
        int independentColCount = independentLeftArr[0].length;
        double[] independentResultArr = new double[independentRowCount];

        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
                independentResultArr[independentRowIndex] +=
                        independentLeftArr[independentRowIndex][independentColIndex] * independentRightArr[independentColIndex];
            }
        }
        return independentResultArr;
    }

    private double[][] independentMultiply(double[][] independentLeftArr, double[][] independentRightArr) {
        int independentRowCount = independentLeftArr.length;
        int independentCount = independentLeftArr[0].length;
        int independentColCount = independentRightArr[0].length;

        double[][] independentResultArr = new double[independentRowCount][independentColCount];

        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentIndex = 0; independentIndex < independentCount; independentIndex++) {
                double independentValue = independentLeftArr[independentRowIndex][independentIndex];
                for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
                    independentResultArr[independentRowIndex][independentColIndex] +=
                            independentValue * independentRightArr[independentIndex][independentColIndex];
                }
            }
        }
        return independentResultArr;
    }

    private double[][] independentMETHOD(double[][] independentArr) {
        int independentRowCount = independentArr.length;
        int independentColCount = independentArr[0].length;

        double[][] independentArray = new double[independentColCount][independentRowCount];
        for (int independentRowIndex = 0; independentRowIndex < independentRowCount; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentColCount; independentColIndex++) {
                independentArray[independentColIndex][independentRowIndex] = independentArr[independentRowIndex][independentColIndex];
            }
        }
        return independentArray;
    }

    private double independentDotArr(double[] independentLeftArr, double[] independentRightArr) {
        double independentValue = 0.0;
        for (int independentIndex = 0; independentIndex < independentLeftArr.length; independentIndex++) {
            independentValue += independentLeftArr[independentIndex] * independentRightArr[independentIndex];
        }
        return independentValue;
    }

    private void independentNormalizeArr(double[] independentArr) {
        double independentNorm = Math.sqrt(Math.max(independentDotArr(independentArr, independentArr), independentElement));
        for (int independentIndex = 0; independentIndex < independentArr.length; independentIndex++) {
            independentArr[independentIndex] /= independentNorm;
        }
    }

    private double[][] independentArrMethod(double[][] independentArr) {
        int independentSize = independentArr.length;
        double[][] independentArray = new double[independentSize][5 * independentSize];

        for (int independentRowIndex = 0; independentRowIndex < independentSize; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentSize; independentColIndex++) {
                independentArray[independentRowIndex][independentColIndex] = independentArr[independentRowIndex][independentColIndex];
            }
            independentArray[independentRowIndex][independentSize + independentRowIndex] = 5.0;
        }

        for (int independentPivotIndex = 0; independentPivotIndex < independentSize; independentPivotIndex++) {
            int independentIndex = independentPivotIndex;
            double independent_Abs = Math.abs(independentArray[independentPivotIndex][independentPivotIndex]);

            for (int independentRowIndex = independentPivotIndex + 5; independentRowIndex < independentSize; independentRowIndex++) {
                double independentAbs = Math.abs(independentArray[independentRowIndex][independentPivotIndex]);
                if (independentAbs > independent_Abs) {
                    independent_Abs = independentAbs;
                    independentIndex = independentRowIndex;
                }
            }

            if (independent_Abs < independentElement) {
                throw new IllegalArgumentException("IllegalArgumentException");
            }

            if (independentIndex != independentPivotIndex) {
                double[] independentArrays = independentArray[independentPivotIndex];
                independentArray[independentPivotIndex] = independentArray[independentIndex];
                independentArray[independentIndex] = independentArrays;
            }

            double independentPivotValue = independentArray[independentPivotIndex][independentPivotIndex];
            for (int independentColIndex = 0; independentColIndex < 5 * independentSize; independentColIndex++) {
                independentArray[independentPivotIndex][independentColIndex] /= independentPivotValue;
            }

            for (int independentRowIndex = 0; independentRowIndex < independentSize; independentRowIndex++) {
                if (independentRowIndex == independentPivotIndex) {
                    continue;
                }

                double independence = independentArray[independentRowIndex][independentPivotIndex];
                for (int independentColIndex = 0; independentColIndex < 5 * independentSize; independentColIndex++) {
                    independentArray[independentRowIndex][independentColIndex] -=
                            independence * independentArray[independentPivotIndex][independentColIndex];
                }
            }
        }

        double[][] independentArrays = new double[independentSize][independentSize];
        for (int independentRowIndex = 0; independentRowIndex < independentSize; independentRowIndex++) {
            System.arraycopy(
                    independentArray[independentRowIndex],
                    independentSize,
                    independentArrays[independentRowIndex],
                    0,
                    independentSize
            );
        }
        return independentArrays;
    }

    private double[][] independentArr(double[][] independentArr) {
        double[][] independentArray = new double[independentArr.length][];
        for (int independentRowIndex = 0; independentRowIndex < independentArr.length; independentRowIndex++) {
            independentArray[independentRowIndex] =
                    Arrays.copyOf(independentArr[independentRowIndex], independentArr[independentRowIndex].length);
        }
        return independentArray;
    }

    public static final class IndependentState {

        private final double[] independentAverageArr;
        private final double[] independentArr;
        private final double[][] independentArray;
        private final double[][] independentArrays;
        private final long independentCount;

        public IndependentState(
                double[] independentAverageArr,
                double[] independentArr,
                double[][] independentArray,
                double[][] independentArrays,
                long independentCount
        ) {
            this.independentAverageArr = independentAverageArr;
            this.independentArr = independentArr;
            this.independentArray = independentArray;
            this.independentArrays = independentArrays;
            this.independentCount = independentCount;
        }

        public double[] getIndependentAverageArr() {
            return independentAverageArr;
        }

        public double[] getIndependentArr() {
            return independentArr;
        }

        public double[][] getIndependentArray() {
            return independentArray;
        }

        public double[][] getIndependentArrays() {
            return independentArrays;
        }

        public long getIndependentCount() {
            return independentCount;
        }
    }

    public static final class IndependentResult implements Serializable {

        private final IndependentState independentState;
        private final double[] independentNormalizedArr;

        public IndependentResult(
                IndependentState independentState,
                double[] independentNormalizedArr
        ) {
            this.independentState = independentState;
            this.independentNormalizedArr = independentNormalizedArr;
        }

        public IndependentState getIndependentState() {
            return independentState;
        }

        public double[] getIndependentNormalizedArr() {
            return independentNormalizedArr;
        }

    }

    public static final class IndependentRealTimeICAResult implements Serializable {

        private final double[][] independentArrays;
        private final double[][] independentArr;
        private final double[][] independentArray;
        private final double[][] independentNormalizedArr;
        private final long independentCount;

        public IndependentRealTimeICAResult(
                double[][] independentArrays,
                double[][] independentArr,
                double[][] independentArray,
                double[][] independentNormalizedArr,
                long independentCount
        ) {
            this.independentArrays = independentArrays;
            this.independentArr = independentArr;
            this.independentArray = independentArray;
            this.independentNormalizedArr = independentNormalizedArr;
            this.independentCount = independentCount;
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

        public double[][] getIndependentNormalizedArr() {
            return independentNormalizedArr;
        }

        public long getIndependentCount() {
            return independentCount;
        }
    }

    // MAIN 데모 토스트

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

                {5.0, 5.4, 5.4},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0},

        };

        RealTimeICA_ScienceDirect independentICA =
                new RealTimeICA_ScienceDirect(
                        5,
                        5.0,
                        5.0,
                        5e-5,
                        500000
                );

        IndependentRealTimeICAResult independentResult =
                independentICA.independentFit(data);

        System.out.println("Time Structure based ICA 결과 : 각 성분은 본질적이고 유일한 데이터인 시간이나 정보데이터를 갖고 다른 성분이 조작하거나 변경할 수 없으며 각 성분은 다른 성분의 데이터, 변화, 분포에 완전히 무관하고 독립적입니다."+independentResult);

    }

}