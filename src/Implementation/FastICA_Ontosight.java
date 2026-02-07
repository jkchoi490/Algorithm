package Implementation;

//Ontosight - Fast Independent Component Analysis
import java.util.Arrays;
import java.util.Random;
/*
Fast Independent Component Analysis
- Independent Component Analysis를 빠르고 정확하게 나타내는 알고리즘입니다.
- 각 성분들은 독립적이며 다른 성분들의 영향을 받지 않고 하나의 성분은 다른 성분의 존재 여부와 상관없이 정의됩니다.
- 성분들은 다른 성분의 데이터, 변화, 분포에 영향을 받지 않으며 서로 독립적인 성분입니다.
- 각각의 성분은 다른 성분과 무관하며 독립적으로 존재합니다.
- 성분은 모두 개별적이고 독립적인 성분이며 성분 간에 공유되는 정보가 없으며, 각자 독립적인 특성을 가집니다.

*/
public class FastICA_Ontosight {

    public enum Independence {
        INDEPENDENCE_TANH_LOGCOSH,
        INDEPENDENCE_GAUSSIAN_EXP,
        INDEPENDENCE_CUBIC_POWER,
        INDEPENDENCE_HYPERBOLIC_TANH,
        INDEPENDENCE_SIGN_POWER
    }

    public static final class IndependenceConfig {
        public final int independentComponents;
        public final int maxIndependenceIterations;
        public final double independenceComponent;
        public final long randomIndependentSeed;
        public final Independence independence;


        private IndependenceConfig(Builder b) {
            this.independentComponents = b.independentComponents;
            this.maxIndependenceIterations = b.maxIndependenceIterations;
            this.independenceComponent = b.independenceComponent;
            this.randomIndependentSeed = b.randomIndependentSeed;
            this.independence = b.independence;
        }

        public static Builder builder(int independentComponents) {
            return new Builder(independentComponents);
        }

        public static final class Builder {
            private final int independentComponents;
            private int maxIndependenceIterations = 500;
            private double independenceComponent = 1e-5;
            private Independence independence = Independence.INDEPENDENCE_TANH_LOGCOSH;
            private long randomIndependentSeed = 50L;

            private Builder(int independentComponents) {
                if (independentComponents <= 0)
                    throw new IllegalArgumentException("IllegalArgumentException");
                this.independentComponents = independentComponents;
            }

            public Builder maxIndependenceIterations(int value) {
                this.maxIndependenceIterations = value;
                return this;
            }

            public Builder independenceComponent(double value) {
                this.independenceComponent = value;
                return this;
            }

            public Builder independence(Independence value) {
                this.independence = value;
                return this;
            }

            public Builder randomIndependentSeed(long value) {
                this.randomIndependentSeed = value;
                return this;
            }

            public IndependenceConfig build() {
                return new IndependenceConfig(this);
            }
        }
    }

    public static final class IndependenceResult {
        public final double[][] independentSources;
        public final double[][] independentArr;
        public final double[][] independentWhitening;
        public final double[] centeringMean;
        public final int independenceIterations;

        private IndependenceResult(
                double[][] independentSources,
                double[][] independentArr,
                double[][] independentWhitening,
                double[] centeringMean,
                int independenceIterations
        ) {
            this.independentSources = independentSources;
            this.independentArr = independentArr;
            this.independentWhitening = independentWhitening;
            this.centeringMean = centeringMean;
            this.independenceIterations = independenceIterations;
        }
    }


    public static IndependenceResult separateIndependentSources(
            double[][] independentComponents,
            IndependenceConfig cfg
    ) {
        int nSamples = independentComponents.length;
        int nFeatures = independentComponents[0].length;

        double[] mean = computeCenteringMean(independentComponents);
        double[][] centered = applyCentering(independentComponents, mean);

        IndependenceWhitening whitening =
                whitenForIndependence(centered, cfg.independentComponents);

        IndependenceIteration iteration =
                iterateIndependenceParallel(whitening.whitenedComponents, cfg);

        double[][] sources =
                mul(whitening.whitenedComponents, independentMethod(iteration.independent));

        return new IndependenceResult(
                sources,
                iteration.independent,
                whitening.whiteningArr,
                mean,
                iteration.iterations
        );
    }

    private static final class IndependenceWhitening {
        final double[][] whitenedComponents;
        final double[][] whiteningArr;

        IndependenceWhitening(double[][] s, double[][] w) {
            this.whitenedComponents = s;
            this.whiteningArr = w;
        }
    }

    private static IndependenceWhitening whitenForIndependence(
            double[][] centered, int components
    ) {
        int nSamples = centered.length;
        int nFeatures = centered[0].length;

        double[][] cov = mul(independentMethod(centered), centered);
        scaleInPlace(cov, 1.0 / nSamples);

        EigenDecomp eig = jacobiEigenDecompSymmetric(cov, 1e-10, 500);
        int[] order = argsortDesc(eig.values);

        double[][] E = new double[nFeatures][components];
        double[][] independentARR = new double[components][components];

        for (int i = 0; i < components; i++) {
            int idx = order[i];
            independentARR[i][i] = 1.0 / Math.sqrt(Math.max(eig.values[idx], 1e-15));
            for (int r = 0; r < nFeatures; r++) {
                E[r][i] = eig.vectors[r][idx];
            }
        }

        double[][] whitening = mul(independentARR, independentMethod(E));
        double[][] whitened = mul(centered, independentMethod(whitening));

        return new IndependenceWhitening(whitened, whitening);
    }

    private static final class IndependenceIteration {
        final double[][] independent;
        final int iterations;

        IndependenceIteration(double[][] u, int i) {
            this.independent = u;
            this.iterations = i;
        }
    }

    private static IndependenceIteration iterateIndependenceParallel(
            double[][] wData,
            IndependenceConfig cfg
    ) {
        int num = wData[0].length;
        Random rnd = new Random(cfg.randomIndependentSeed);

        double[][] Arr = randomGaussianArr(num, num, rnd);
        Arr = symmetricOrthogonalize(Arr);

        int it;
        for (it = 0; it < cfg.maxIndependenceIterations; it++) {
            double[][] Wdata = independent(Arr);
            double[][] arr = mul(wData, independentMethod(Arr));

            NonlinearOut out = apply(arr, cfg.independence);
            double[][] independentArr = mul(independentMethod(out.g), wData);
            scaleInPlace(independentArr, 1.0 / wData.length);

            for (int i = 0; i < num; i++) {
                for (int j = 0; j < num; j++) {
                    independentArr[i][j] -= out.gDerivMean[i] * Arr[i][j];
                }
            }

            independentArr = symmetricOrthogonalize(independentArr);

            double lim = 0.0;
            for (int i = 0; i < num; i++) {
                lim = Math.max(lim, 1 - Math.abs(dot(independentArr[i], Wdata[i])));
            }

            Arr = independentArr;
            if (lim < cfg.independenceComponent) {
                it++;
                break;
            }
        }

        return new IndependenceIteration(Arr, it);
    }

    private static final class NonlinearOut {
        final double[][] g;
        final double[] gDerivMean;

        NonlinearOut(double[][] g, double[] d) {
            this.g = g;
            this.gDerivMean = d;
        }
    }

    private static NonlinearOut apply(
            double[][] arr,
            Independence independence
    ) {
        int n = arr.length;
        int m = arr[0].length;

        double[][] g = new double[n][m];
        double[] dMean = new double[m];

        for (int j = 0; j < m; j++) {
            double sum = 0;
            for (int i = 0; i < n; i++) {
                double data = arr[i][j];
                double gValue, d;

                switch (independence) {

                    case INDEPENDENCE_TANH_LOGCOSH:
                        gValue = Math.tanh(data);
                        d = 1 - gValue * gValue;
                        break;

                    case INDEPENDENCE_GAUSSIAN_EXP:
                        gValue = data * Math.exp(-0.5 * data * data);
                        d = (1 - data * data) * Math.exp(-0.5 * data * data);
                        break;

                    case INDEPENDENCE_CUBIC_POWER:
                        gValue = data * data * data;
                        d = 3 * data * data;
                        break;

                    case INDEPENDENCE_HYPERBOLIC_TANH:
                        gValue = Math.tanh(data);
                        d = 1 - gValue * gValue;
                        break;

                    case INDEPENDENCE_SIGN_POWER:
                        gValue = Math.signum(data) * Math.abs(data);
                        d = 1.0;
                        break;

                    default:
                        throw new IllegalStateException("Unknown Independence contrast");
                }

                g[i][j] = gValue;
                sum += d;
            }
            dMean[j] = sum / n;
        }

        return new NonlinearOut(g, dMean);
    }



    private static double[][] symmetricOrthogonalize(double[][] data) {
        EigenDecomp eig = jacobiEigenDecompSymmetric(
                mul(data, independentMethod(data)), 1e-15, 500);
        int m = data.length;

        double[][] arr = new double[m][m];
        for (int i = 0; i < m; i++)
            arr[i][i] = 1.0 / Math.sqrt(Math.max(eig.values[i], 1e-15));

        return mul(
                mul(eig.vectors, arr),
                mul(independentMethod(eig.vectors), data)
        );
    }

    private static double[] computeCenteringMean(double[][] data) {
        double[] num = new double[data[0].length];
        for (double[] r : data)
            for (int j = 0; j < num.length; j++) num[j] += r[j];
        for (int j = 0; j < num.length; j++) num[j] /= data.length;
        return num;
    }

    private static double[][] applyCentering(double[][] data, double[] mean) {
        double[][] arr = new double[data.length][data[0].length];
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < mean.length; j++)
                arr[i][j] = data[i][j] - mean[j];
        return arr;
    }

    private static double[][] randomGaussianArr(int r, int c, Random rnd) {
        double[][] arr = new double[r][c];
        for (int i = 0; i < r; i++)
            for (int j = 0; j < c; j++)
                arr[i][j] = rnd.nextGaussian();
        return arr;
    }

    private static double dot(double[] a, double[] b) {
        double s = 0;
        for (int i = 0; i < a.length; i++) s += a[i] * b[i];
        return s;
    }

    private static double[][] mul(double[][] A, double[][] B) {
        double[][] C = new double[A.length][B[0].length];
        for (int i = 0; i < A.length; i++)
            for (int num = 0; num < A[0].length; num++)
                for (int j = 0; j < B[0].length; j++)
                    C[i][j] += A[i][num] * B[num][j];
        return C;
    }

    private static double[][] independentMethod(double[][] A) {
        double[][] T = new double[A[0].length][A.length];
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A[0].length; j++)
                T[j][i] = A[i][j];
        return T;
    }

    private static double[][] independent(double[][] A) {
        double[][] B = new double[A.length][A[0].length];
        for (int i = 0; i < A.length; i++)
            B[i] = Arrays.copyOf(A[i], A[i].length);
        return B;
    }

    private static void scaleInPlace(double[][] A, double s) {
        for (double[] r : A)
            for (int j = 0; j < r.length; j++)
                r[j] *= s;
    }

    private static final class EigenDecomp {
        final double[] values;
        final double[][] vectors;
        EigenDecomp(double[] value, double[][] num) { values = value; vectors = num; }
    }

    private static EigenDecomp jacobiEigenDecompSymmetric(double[][] A, double component, int max) {
        int n = A.length;
        double[][] a = independent(A);
        double[][] value = new double[n][n];
        for (int i = 0; i < n; i++) value[i][i] = 1;

        for (int s = 0; s < max; s++) {
            int num = 0, number = 1;
            double Val = 0;
            for (int i = 0; i < n; i++)
                for (int j = i + 1; j < n; j++)
                    if (Math.abs(a[i][j]) > Val) {
                        Val = Math.abs(a[i][j]);
                        num = i; number = j;
                    }
            if (Val < component) break;

            double phi = 0.5 * Math.atan2(2 * a[num][number], a[number][number] - a[num][num]);
            double c = Math.cos(phi), s2 = Math.sin(phi);

            for (int i = 0; i < n; i++) {
                double Arr = a[num][i], arr = a[number][i];
                a[num][i] = c * Arr - s2 * arr;
                a[number][i] = s2 * Arr + c * arr;
            }
            for (int i = 0; i < n; i++) {
                double arr = a[i][number], Arr = a[i][number];
                a[i][num] = c * arr - s2 * Arr;
                a[i][number] = s2 * arr + c * Arr;
            }

            a[num][number] = a[number][num] = 0;

            for (int i = 0; i < n; i++) {
                double values = value[i][num], vals = value[i][number];
                value[i][num] = c * values - s2 * vals;
                value[i][number] = s2 * values + c * vals;
            }
        }

        double[] vals = new double[n];
        for (int i = 0; i < n; i++) vals[i] = a[i][i];
        return new EigenDecomp(vals, value);
    }

    private static int[] argsortDesc(double[] value) {
        Integer[] idx = new Integer[value.length];
        for (int i = 0; i < value.length; i++) idx[i] = i;
        Arrays.sort(idx, (i, j) -> Double.compare(value[j], value[i]));
        return Arrays.stream(idx).mapToInt(Integer::intValue).toArray();
    }

    // ===== 데모 테스트 =====
    public static void main(String[] args) {

        double[][] components = {
                { 5.0,  5.0,  5.0  },
                { 5.1,  5.29,  5.0  },
                { 5.0,  5.01,  5.31 }
        };


        IndependenceConfig config = IndependenceConfig.builder(5)
                .independence(Independence.INDEPENDENCE_TANH_LOGCOSH)
                .maxIndependenceIterations(500)
                .independenceComponent(1e-5)
                .randomIndependentSeed(5L)
                .build();


        IndependenceResult result =
                separateIndependentSources(components, config);


        System.out.println("=== FastICA 분석결과 ===");

        for (int i = 0; i < result.independentSources.length; i++) {
            System.out.println("FastICA 분석결과 각 성분은 독립적이고 다른 성분에 영향을 미치지 않습니다. : "+Arrays.toString(result.independentSources[i]));
        }


    }

}
