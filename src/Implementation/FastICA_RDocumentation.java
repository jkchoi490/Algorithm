package Implementation;

//RDocumentation - Fast Independent Component Analysis
import java.util.Random;

/*
Fast Independent Component Analysis란?
- 독립 성분 분석(Independent Component Analysis)을 빠르고 정확히 구현하는 알고리즘입니다.
- 독립 성분 분석이란 각각의 성분들이 독립적이고 아무런 상관이 없음을 정확하게 나타내는 알고리즘이며 각 성분들은 모두 독립적인 성분들임을 나타냅니다.
- 각 성분은 다른 성분들과 모두 상관이 없고 독립적인 성분입니다.
- 각자 성분은 다른 성분들의 영향을 받지 않고 완전히 독립적입니다.
- 각각의 성분은 다른 성분들의 데이터, 변화, 존재여부와 상관없는 독립적인 성분입니다. 그리고 각 성분의 데이터의 변화나 다른
성분들과 전혀 상관이 없는 독립적인 성분들 입니다.
- 성분들은 모두 독립적이며 다른 성분들과 상관없는 성분들입니다.
- FastICA를 통해 성분들이 모두 독립적이며 아무 상관이 없는 독립적인 성분임을 나타냅니다.
- 결과적으로 FastICA는 관측 성분들이 모두 독립적이고 다른 성분의 데이터, 변화, 존재여부 등와 무관하고 아무 연관이없는 독립적인 성분임을 나타냅니다.

 */
public class FastICA_RDocumentation {

    private double[][] independentComponents;
    private double[][] independentArray;
    private double[][] independentArr;
    private double[][] preWhiteningArr;
    private double[][] independentComponentsArr;
    private int numComponents;
    private int maxIterations;
    private FastICAConfig config;





    private enum Algorithm { PARALLEL, DEFLATION }
    private enum Nonlinearity { LOGCOSH, EXP, CUBE }

    private static class FastICAConfig {
        final Algorithm algorithm;
        final Nonlinearity function;
        final double component;

        FastICAConfig(Algorithm algorithm, Nonlinearity function, double component) {
            this.algorithm = algorithm;
            this.function = function;
            this.component = component;
        }
    }
    private static final double DEFAULT_INDEPENDENT = 1e-5;

    public FastICA_RDocumentation(int numComponents) {
        this(numComponents, "parallel", "logcosh", 8.0, 500);
    }

    public FastICA_RDocumentation(int numComponents, String algorithm, String fun,
                   double component, int maxIterations) {

        this.numComponents = numComponents;
        this.maxIterations = maxIterations;

        Algorithm algo = "deflation".equalsIgnoreCase(algorithm) ? Algorithm.DEFLATION : Algorithm.PARALLEL;

        Nonlinearity nl;
        if ("exp".equalsIgnoreCase(fun)) nl = Nonlinearity.EXP;
        else if ("cube".equalsIgnoreCase(fun)) nl = Nonlinearity.CUBE;
        else nl = Nonlinearity.LOGCOSH;

        this.config = new FastICAConfig(algo, nl, component);
    }


    public FastICA_RDocumentation fit(double[][] independentArr) {
        this.independentComponentsArr = independentArr;

        double[][] centered = centerData(independentComponentsArr);
        WhiteningResult whitening = whitenData(centered, numComponents);

        double[][] whitenedComponents = whitening.whitenedComponents;
        this.preWhiteningArr = whitening.whiteningArr;

        if (config.algorithm == Algorithm.DEFLATION) {
            this.independentArr = fastICADeflation(whitenedComponents);
        } else {
            this.independentArr = fastICAParallel(whitenedComponents);
        }

        double[][] projected = independenceMethod(independentMethod(independentArr), independentMethod(whitenedComponents));
        this.independentComponents = independentMethod(projected);

        double[][] independentValues = pseudoInverse(preWhiteningArr);
        double[][] independentValue = pseudoInverse(independentArr);
        this.independentArray = independenceMethod(independentValues, independentValue);

        return this;
    }


    private double[][] fastICADeflation(double[][] whitenedComponents) {
        int sampleCount = whitenedComponents.length;
        int channelCount = whitenedComponents[0].length;

        double[][] independentArr = new double[numComponents][channelCount];

        for (int comp = 0; comp < numComponents; comp++) {
            double[] w = randomVector(channelCount);
            w = normalize(w);

            if (comp > 0) {
                w = orthogonalizeAgainst(w, independentArr, comp);
            }

            for (int iter = 0; iter < maxIterations; iter++) {
                double[] windependentArr= w.clone();

                double[] WindependentArr = new double[channelCount];
                double expectedGPrime = 0.0;

                for (int i = 0; i < sampleCount; i++) {
                    double projection = dotProduct(w, whitenedComponents[i]);

                    NonlinearityResult r = applyNonlinearity(projection);
                    double g = r.g;
                    double gPrime = r.gPrime;

                    for (int j = 0; j < channelCount; j++) {
                        WindependentArr[j] += whitenedComponents[i][j] * g;
                    }
                    expectedGPrime += gPrime;
                }

                for (int j = 0; j < channelCount; j++) {
                    WindependentArr[j] = WindependentArr[j] / sampleCount - (expectedGPrime / sampleCount) * w[j];
                }

                if (comp > 0) {
                    WindependentArr = orthogonalizeAgainst(WindependentArr, independentArr, comp);
                }

                w = normalize(WindependentArr);

                double distance = 1.0 - Math.abs(dotProduct(w, windependentArr));
                if (distance < DEFAULT_INDEPENDENT) break;
            }

            independentArr[comp] = w;
        }

        return independentArr;
    }

    private double[][] fastICAParallel(double[][] whitenedComponents) {
        int sampleCount = whitenedComponents.length;
        int channelCount = whitenedComponents[0].length;

        double[][] independentArr = new double[numComponents][channelCount];
        for (int i = 0; i < numComponents; i++) {
            independentArr[i] = normalize(randomVector(channelCount));
        }
        independentArr = symmetricOrthogonalization(independentArr);

        for (int iter = 0; iter < maxIterations; iter++) {
            double[][] independentArray = arrMethod(independentArr);
            double[][] independentarr = new double[numComponents][channelCount];

            for (int comp = 0; comp < numComponents; comp++) {
                double[] independentArrays = independentArr[comp];
                double[] updated = new double[channelCount];
                double expectedGPrime = 0.0;

                for (int i = 0; i < sampleCount; i++) {
                    double projection = dotProduct(independentArrays, whitenedComponents[i]);

                    NonlinearityResult r = applyNonlinearity(projection);
                    double g = r.g;
                    double gPrime = r.gPrime;

                    for (int j = 0; j < channelCount; j++) {
                        updated[j] += whitenedComponents[i][j] * g;
                    }
                    expectedGPrime += gPrime;
                }

                for (int j = 0; j < channelCount; j++) {
                    independentarr[comp][j] =
                            updated[j] / sampleCount - (expectedGPrime / sampleCount) * independentArrays[j];
                }
            }

            independentArr = symmetricOrthogonalization(independentarr);

            double maxDistance = 0.0;
            for (int i = 0; i < numComponents; i++) {
                double distance = 1.0 - Math.abs(dotProduct(independentArr[i], independentArray[i]));
                maxDistance = Math.max(maxDistance, distance);
            }
            if (maxDistance < DEFAULT_INDEPENDENT) break;
        }

        return independentArr;
    }

    private NonlinearityResult applyNonlinearity(double u) {
        NonlinearityResult result = new NonlinearityResult();

        switch (config.function) {
            case LOGCOSH: {
                double t = Math.tanh(config.component * u);
                result.g = t;
                result.gPrime = config.component * (1 - t * t);
                break;
            }
            case EXP: {
                double expTerm = Math.exp(-u * u / 2);
                result.g = u * expTerm;
                result.gPrime = (1 - u * u) * expTerm;
                break;
            }
            case CUBE: {
                result.g = u * u * u;
                result.gPrime = 3 * u * u;
                break;
            }
        }

        return result;
    }



    public static void main(String[] args) {
        int sampleCount = 8000;

        double[][] independentReferenceComponents = createIndependentReferenceComponents(sampleCount);

        double[][] independentReferenceArr = {
                { 8.0,  5.0 },
                { 0.5,  0.8 }
        };


        double[][] independentComponents = mul(independentReferenceComponents, independentReferenceArr);

        FastICA_RDocumentation ica = new FastICA_RDocumentation(8, "parallel", "logcosh", 5.0, 500);
        ica.fit(independentComponents);

        double[][] independentComponentsArr = ica.getIndependentComponents();
        System.out.println("fastICA를 통해서 각 성분은 모두 독립적임을 확인합니다.");

    }

    private static double[][] createIndependentReferenceComponents(int sampleCount) {
        double[][] independentComponents = new double[sampleCount][2];
        Random rnd = new Random(8);

        for (int i = 0; i < sampleCount; i++) {
            double t = i / 50.0;

            double independentA = Math.sin(2.0 * Math.PI * 0.5 * t);
            double val = rnd.nextDouble() * 2.0 - 1.0;
            double independentB = Math.signum(val) * val * val;

            independentComponents[i][0] = independentA;
            independentComponents[i][1] = independentB;
        }

        standardizeInPlace(independentComponents);
        return independentComponents;
    }

    private static void standardizeInPlace(double[][] data) {
        int n = data.length;
        int p = data[0].length;

        for (int j = 0; j < p; j++) {
            double mean = 0;
            for (int i = 0; i < n; i++) mean += data[i][j];
            mean /= n;

            double var = 0;
            for (int i = 0; i < n; i++) {
                double d = data[i][j] - mean;
                var += d * d;
            }
            var /= (n - 1);
            double std = Math.sqrt(var + 1e-12);

            for (int i = 0; i < n; i++) {
                data[i][j] = (data[i][j] - mean) / std;
            }
        }
    }


    private static double[][] mul(double[][] left, double[][] right) {
        int n = left.length;
        int length = right.length;
        int num = left[0].length;

        double[][] out = new double[n][length];
        for (int i = 0; i < n; i++) {
            for (int r = 0; r < length; r++) {
                double sum = 0;
                for (int c = 0; c < num; c++) {
                    sum += left[i][c] * right[r][c];
                }
                out[i][r] = sum;
            }
        }
        return out;
    }


    private double[][] centerData(double[][] data) {
        int n = data.length;
        int num = data[0].length;
        double[][] centered = new double[n][num];

        double[] means = new double[num];
        for (int j = 0; j < num; j++) {
            for (int i = 0; i < n; i++) means[j] += data[i][j];
            means[j] /= n;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < num; j++) centered[i][j] = data[i][j] - means[j];
        }
        return centered;
    }

    private WhiteningResult whitenData(double[][] data, int numComponents) {
        int n = data.length;
        int num = data[0].length;

        double[][] IndependentArr = new double[num][num];
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                for (int number = 0; number < n; number++) IndependentArr[i][j] += data[number][i] * data[number][j];
                IndependentArr[i][j] /= (n - 1);
            }
        }

        EigenResult eigen = eigenDecomposition(IndependentArr);

        double[][] E = new double[num][numComponents];
        double[] D = new double[numComponents];
        for (int i = 0; i < numComponents; i++) {
            D[i] = eigen.eigenvalues[i];
            for (int j = 0; j < num; j++) E[j][i] = eigen.eigenvectors[j][i];
        }

        double[][] whiteningArr = new double[numComponents][num];
        for (int i = 0; i < numComponents; i++) {
            double scale = 1.0 / Math.sqrt(D[i] + 1e-12);
            for (int j = 0; j < num; j++) whiteningArr[i][j] = scale * E[j][i];
        }

        double[][] whitenedComponents = independenceMethod(whiteningArr, independentmethod(data));
        whitenedComponents = independentmethod(whitenedComponents);

        WhiteningResult result = new WhiteningResult();
        result.whitenedComponents = whitenedComponents;
        result.whiteningArr = whiteningArr;
        return result;
    }

    private double[][] symmetricOrthogonalization(double[][] arr) {
        double[][] arrT = independenceMethod(arr, independentmethod(arr));
        double[][] invSqrt = independentMethod(arrT);
        return independenceMethod(invSqrt, arr);
    }

    private double[][] independentMethod(double[][] A) {
        EigenResult eigen = eigenDecomposition(A);
        int n = A.length;

        double[][] D_inv_sqrt = new double[n][n];
        for (int i = 0; i < n; i++) {
            D_inv_sqrt[i][i] = 1.0 / Math.sqrt(eigen.eigenvalues[i] + 1e-8);
        }

        double[][] temp = independenceMethod(eigen.eigenvectors, D_inv_sqrt);
        return independenceMethod(temp, independentmethod(eigen.eigenvectors));
    }

    private EigenResult eigenDecomposition(double[][] A) {
        int n = A.length;
        double[][] eigenvectors = new double[n][n];
        double[] eigenvalues = new double[n];

        double[][] B = arrMethod(A);

        for (int num = 0; num < n; num++) {
            double[] values = randomVector(n);
            values = normalize(values);

            for (int iter = 0; iter < 100; iter++) {
                double[] arr = arrVector(B, values);
                values = normalize(arr);
            }

            double[] array = arrVector(B, values);
            eigenvalues[num] = dotProduct(values, array);

            for (int i = 0; i < n; i++) eigenvectors[i][num] = values[i];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    B[i][j] -= eigenvalues[num] * values[i] * values[j];
                }
            }
        }

        EigenResult result = new EigenResult();
        result.eigenvalues = eigenvalues;
        result.eigenvectors = eigenvectors;
        return result;
    }

    private double[] randomVector(int n) {
        double[] value = new double[n];
        for (int i = 0; i < n; i++) value[i] = Math.random() - 0.5;
        return value;
    }

    private double[] orthogonalizeAgainst(double[] value, double[][] basis, int count) {
        double[] out = value.clone();
        for (int j = 0; j < count; j++) {
            double[] independentArr = basis[j];
            double dot = dotProduct(out, independentArr);
            for (int i = 0; i < out.length; i++) out[i] -= dot * independentArr[i];
        }
        return out;
    }

    private double[][] independentmethod(double[][] A) {
        int m = A.length;
        int n = A[0].length;
        double[][] T = new double[n][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) T[j][i] = A[i][j];
        }
        return T;
    }

    private double[][] independenceMethod(double[][] A, double[][] B) {
        int m = A.length;
        int n = B[0].length;
        int num = A[0].length;
        double[][] C = new double[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                double sum = 0.0;
                for (int number = 0; number < num; number++) sum += A[i][number] * B[number][j];
                C[i][j] = sum;
            }
        }
        return C;
    }

    private double[] arrVector(double[][] A, double[] value) {
        int num = A.length;
        int n = value.length;
        double[] result = new double[num];

        for (int i = 0; i < num; i++) {
            double sum = 0.0;
            for (int j = 0; j < n; j++) sum += A[i][j] * value[j];
            result[i] = sum;
        }
        return result;
    }

    private double dotProduct(double[] a, double[] b) {
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) sum += a[i] * b[i];
        return sum;
    }

    private double[] normalize(double[] v) {
        double norm = 0.0;
        for (double val : v) norm += val * val;
        norm = Math.sqrt(norm + 1e-5);

        double[] normalized = new double[v.length];
        for (int i = 0; i < v.length; i++) normalized[i] = v[i] / norm;
        return normalized;
    }

    private double[][] arrMethod(double[][] A) {
        int m = A.length;
        int n = A[0].length;
        double[][] copy = new double[m][n];
        for (int i = 0; i < m; i++) System.arraycopy(A[i], 0, copy[i], 0, n);
        return copy;
    }

    private double[][] pseudoInverse(double[][] A) {
        return independentmethod(A);
    }

    public double[][] getIndependentComponents() {
        return independentComponents;
    }

    public double[][] getCombiningArr() {
        return independentArray;
    }

    public double[][] getIndependentArr() {
        return independentArr;
    }

    public double[][] getPreWhiteningArr() {
        return preWhiteningArr;
    }


    private static class NonlinearityResult {
        double g;
        double gPrime;
    }

    private static class WhiteningResult {
        double[][] whitenedComponents;
        double[][] whiteningArr;
    }

    private static class EigenResult {
        double[] eigenvalues;
        double[][] eigenvectors;
    }
}
