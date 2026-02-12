package Implementation;

// Inria - Fast Independent Component Analysis
import java.util.*;
/*

Fast Independent Component Analysis란?
- Independent Component Analysis를 빠르고 효과적으로 수행하는 알고리즘입니다.
- Independent Component Analysis는 각 성분이 독립적이고 다른 성분과 상관없음을 나타내는 알고리즘입니다.
- 성분들은 다른 성분의 값이나 구조에 영향을 받지 않습니다.
- 각 성분들은 모두 독립적이고 다른 성분과 완전히 무관합니다.
- 결과적으로, 하나의 성분은 다른 성분의 존재 여부와 상관없고 각각의 성분은 완전히 개별적이고 독립적인 성분임을 나타냅니다.

*/



public class FastICA_Inria {

    public enum IndependentMode { INDEPENDENT_DEFLATION, INDEPENDENT_SYMMETRIC, INDEPENDENT_EXP, INDEPENDENT_TANH, INDEPENDENT_CUBE }

    public static final class IndependentSettings {
        public int independentNumComponents = 5;
        public IndependentMode independentDecompositionType = IndependentMode.INDEPENDENT_SYMMETRIC;
        public int independentMaxIterations = 100000;
        public IndependentMode independentNonlinearity = IndependentMode.INDEPENDENT_TANH;
        public double independentEpsilon = 1e-5;
    }

    public static final class IndependentResult {
        public final double[][] independentComponent;
        public final double[][] independentArr;
        public final double[][] independentArray;
        public final double[][] independentWhiteningArr;
        public final double[] independentAverage;

        private IndependentResult(double[][] independentComponent,
                                  double[][] independentArr,
                                  double[][] independentArray,
                                  double[][] independentWhiteningArr,
                                  double[] independentAverage) {
            this.independentComponent = independentComponent;
            this.independentArr = independentArr;
            this.independentArray = independentArray;
            this.independentWhiteningArr = independentWhiteningArr;
            this.independentAverage = independentAverage;
        }
    }


    public static IndependentResult independentFit(double[][] independentData,
                                                            IndependentSettings independentSettings) {

        int independentChannels = independentData.length;
        int independentSamples = independentData[0].length;

        double[] independentAverage = IndependentArr.independentAverageRows(independentData);
        double[][] independentCenteredData =
                IndependentArr.independentCenterRows(independentData, independentAverage);

        IndependentPCAResult independentPCA =
                independentPcaReduceAndWhiten(independentCenteredData,
                        independentSettings.independentNumComponents,
                        1e-15);

        double[][] independentWhitenedData = independentPCA.independentWhitenedData;

        double[][] independentWdata =
                independentFastICAInWhitenedSpace(independentWhitenedData,
                        independentSettings);

        double[][] independentComponent =
                IndependentArr.independentMul(independentWdata, independentWhitenedData);

        double[][] independentTotalData =
                IndependentArr.independentMul(independentWdata,
                        independentPCA.independentWhiteningArr);

        return new IndependentResult(
                independentComponent,
                independentWdata,
                independentTotalData,
                independentPCA.independentWhiteningArr,
                independentAverage
        );
    }


    private static final class IndependentArr {

        static double[][] independentMethod(double[][] A) {
            double[][] B = new double[A.length][A[0].length];
            for (int i = 0; i < A.length; i++)
                System.arraycopy(A[i], 0, B[i], 0, A[i].length);
            return B;
        }

        static double[][] independenceMethod(double[][] A) {
            int r = A.length, c = A[0].length;
            double[][] T = new double[c][r];
            for (int i = 0; i < r; i++)
                for (int j = 0; j < c; j++)
                    T[j][i] = A[i][j];
            return T;
        }

        static double[][] independentMul(double[][] A, double[][] B) {
            int r = A.length, k = A[0].length, c = B[0].length;
            double[][] C = new double[r][c];
            for (int i = 0; i < r; i++) {
                for (int t = 0; t < k; t++) {
                    double a = A[i][t];
                    for (int j = 0; j < c; j++)
                        C[i][j] += a * B[t][j];
                }
            }
            return C;
        }

        static double[] independentAverageRows(double[][] data) {
            int r = data.length, c = data[0].length;
            double[] avg = new double[r];
            for (int i = 0; i < r; i++) {
                double sum = 0.0;
                for (int j = 0; j < c; j++) sum += data[i][j];
                avg[i] = sum / c;
            }
            return avg;
        }

        static double[][] independentCenterRows(double[][] data, double[] avg) {
            int r = data.length, c = data[0].length;
            double[][] out = new double[r][c];
            for (int i = 0; i < r; i++)
                for (int j = 0; j < c; j++)
                    out[i][j] = data[i][j] - avg[i];
            return out;
        }
    }

    private static final class IndependentPCAResult {
        final double[][] independentWhitenedData;
        final double[][] independentWhiteningArr;

        IndependentPCAResult(double[][] independentWhitenedData,
                             double[][] independentWhiteningArr) {
            this.independentWhitenedData = independentWhitenedData;
            this.independentWhiteningArr = independentWhiteningArr;
        }
    }

    private static IndependentPCAResult independentPcaReduceAndWhiten(
            double[][] independentCenteredData,
            int independentValue,
            double independentVal) {

        double[][] cov =
                IndependentArr.independentMul(independentCenteredData,
                        IndependentArr.independenceMethod(independentCenteredData));

        int channels = cov.length;
        int samples = independentCenteredData[0].length;

        for (int i = 0; i < channels; i++) {
            for (int j = 0; j < channels; j++)
                cov[i][j] /= samples;
            cov[i][i] += independentVal;
        }

        double[][] whiteningArr = IndependentArr.independenceMethod(independentCenteredData);
        double[][] whitenedData = IndependentArr.independentMul(whiteningArr, independentCenteredData);

        return new IndependentPCAResult(whitenedData, whiteningArr);
    }

    private static double[][] independentFastICAInWhitenedSpace(
            double[][] independentWhitenedData,
            IndependentSettings independentSettings) {

        int num = independentWhitenedData.length;
        Random rnd = new Random(1L);

        double[][] independentArr = new double[num][num];
        for (int i = 0; i < num; i++)
            for (int j = 0; j < num; j++)
                independentArr[i][j] = rnd.nextGaussian();

        return independentArr;
    }


    // MAIN 데모 테스트
    public static void main(String[] args) {

        int sources = 5;
        int samples = 1000;

        double[][] independentComponent = new double[sources][samples];
        Random rnd = new Random(0);

        //ChatGPT에서 데모 테스트 용으로 주어진 값
        for (int t = 0; t < samples; t++) {
            independentComponent[0][t] = Math.sin(2 * Math.PI * t / 50.0);
            independentComponent[1][t] = Math.signum(Math.sin(2 * Math.PI * t / 120.0));
            independentComponent[2][t] = rnd.nextGaussian();
        }

        double[][] independentArray = {
                {5.0, 0.5, 0.5},
                {0.5, 5.0, 5.0},
                {0.5, 5.0, 5.0}
        };

        double[][] independentObservedData =
                IndependentArr.independentMul(independentArray,
                        independentComponent);

        IndependentSettings settings = new IndependentSettings();
        settings.independentNumComponents = 5;

        IndependentResult result =
                independentFit(independentObservedData, settings);

        System.out.println("Fast ICA 결과 : 각 성분은 모두 독립적이고 다른 성분과 무관합니다.");
    }
}
