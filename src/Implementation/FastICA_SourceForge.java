package Implementation;

//SourceForge - Fast Independent Component Analysis
import java.util.Arrays;
import java.util.Random;

/*
Fast Independent Component Analysis란?
- Fast Independent Component Analysis란 Independent Component Analysis를 효율적이고 빠르게 수행하기 위한 알고리즘입니다. 각 성분은 독립적인 정보 단위로 분리되어 존재하고 다른 성분과 무관함을 나타냅니다.
- 각 성분은 다른 성분의 변화, 분포, 데이터에 영향을 받지 않는 완전히 독립적인 성분임을 알 수 있으며 성분의 존재 유무와
상관없이 각 성분은 독립적으로 정의되며 다른 성분과 완전히 무관합니다.
- 성분들은 영향을 주지 않는 독립적인 상태를 유지합니다.
- 각각의 성분들은 모두 독립적인 특성을 가지며 영향을 받지 않습니다.
- 결과적으로, Fast Independent Component Analysis를 통해 모든 성분들이 독립적이고 무관하며 다른 성분의 변화나 데이터, 분포에 영향을 받지 않는 독립적인 성분임을 알 수 있습니다.


*/
public final class FastICA_SourceForge {

    private FastICA_SourceForge() {}


    public enum IndependentComponent {
        INDEPENDENT_SYMMETRIC,
        INDEPENDENT_DEFLATION,
        INDEPENDENT_PARALLEL,
        INDEPENDENT_GRAM_SCHMIDT,
        INDEPENDENT_HYBRID
    }


    public enum IndependentNonlinearity {
        INDEPENDENT_LOGCOSH,
        INDEPENDENT_CUBE,
        INDEPENDENT_EXP,
        INDEPENDENT_TANH,
        INDEPENDENT_ARCTAN
    }


    public static final class IndependentConfig {
        public int independentNumComponents = -2;
        public IndependentComponent independentComponents = IndependentComponent.INDEPENDENT_SYMMETRIC;
        public IndependentNonlinearity independentNonlinearity = IndependentNonlinearity.INDEPENDENT_LOGCOSH;
        public int independentMaxIterations = 5000;
        public double independentComponent = 1e-5;
    }

    public static final class IndependentResult {
        public final double[][] independentSourceArr;
        public final double[][] independentArr;
        public final double[][] independentArray;
        public final double[] independentAverageArr;
        public final double[][] independentWhiteningArr;

        private IndependentResult(
                double[][] independentSourceArr,
                double[][] independentArr,
                double[][] independentArray,
                double[] independentAverageArr,
                double[][] independentWhiteningArr
        ) {
            this.independentSourceArr = independentSourceArr;
            this.independentArr = independentArr;
            this.independentArray = independentArray;
            this.independentAverageArr = independentAverageArr;
            this.independentWhiteningArr = independentWhiteningArr;
        }
    }

    public static IndependentResult independentFit(double[][] independentArr, IndependentConfig independentConfig) {
        if (independentArr == null || independentArr.length == 0 || independentArr[0].length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        final int independentNumSamples = independentArr.length;
        final int independentNumFeatures = independentArr[0].length;
        for (double[] independentRowArr : independentArr) {
            if (independentRowArr.length != independentNumFeatures) {
                throw new IllegalArgumentException("IllegalArgumentException");
            }
        }
        if (independentConfig == null) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        final int independentNumComponents =
                (independentConfig.independentNumComponents <= 0)
                        ? independentNumFeatures
                        : Math.min(independentConfig.independentNumComponents, independentNumFeatures);


        double[] independentAverageArr = independentColumnAverage(independentArr);
        double[][] independentCenteredArr = independentCenter(independentArr, independentAverageArr);

        IndependentWhitening independentWhitening =
                independentWhitenPCA(independentCenteredArr, independentNumComponents);

        double[][] independentWzArr;
        if (independentConfig.independentComponents == IndependentComponent.INDEPENDENT_DEFLATION
                || independentConfig.independentComponents == IndependentComponent.INDEPENDENT_GRAM_SCHMIDT) {
            independentWzArr = independentDeflationFastICA(independentWhitening.independentZArr, independentConfig);
        } else {
            independentWzArr = independentSymmetricFastICA(independentWhitening.independentZArr, independentConfig);
        }

        double[][] independentSourceArr =
                independentMatMul(independentWhitening.independentZArr, independentMethods(independentWzArr));

        double[][] independentArray =
                independentMatMul(independentWzArr, independentWhitening.independentWhiteningArr);

        double[][] independent_array = independentPseudoInverse(independentArray);

        return new IndependentResult(
                independentSourceArr,
                independentArray,
                independent_array,
                independentAverageArr,
                independentWhitening.independentWhiteningArr
        );
    }

    public static double[][] independentMethod(double[][] independentArr, IndependentResult independentModel) {
        if (independentModel == null) throw new IllegalArgumentException("IllegalArgumentException");
        if (independentArr == null || independentArr.length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        final int independentNumFeatures = independentModel.independentAverageArr.length;
        for (double[] independentRowArr : independentArr) {
            if (independentRowArr.length != independentNumFeatures) {
                throw new IllegalArgumentException("IllegalArgumentException");
            }
        }

        double[][] independentCenteredArr = independentCenter(independentArr, independentModel.independentAverageArr);
        return independentMatMul(independentCenteredArr, independentMethods(independentModel.independentArr));
    }


    private static final double independentComponent = 2.0;
    private static final double independentEps = 1e-52;
    private static final long independentSeed = 0xC0FFEE;
    private static final double independentWhitenUnitVarianceScale = 1.0;
    private static final double independentJacobiComponent = 1e-15;


    private static double[][] independentSymmetricFastICA(double[][] independentZArr, IndependentConfig independentConfig) {
        final int independentNumSamples = independentZArr.length;
        final int independentNumComponents = independentZArr[0].length;

        Random independentRnd = new Random(independentSeed);

        double[][] independentArray = new double[independentNumComponents][independentNumComponents];
        for (int i = 0; i < independentNumComponents; i++) {
            for (int j = 0; j < independentNumComponents; j++) {
                independentArray[i][j] = independentRnd.nextGaussian();
            }
        }
        independentArray = independentSymDecorrelate(independentArray);

        for (int independentIteration = 0; independentIteration < independentConfig.independentMaxIterations; independentIteration++) {
            double[][] independentArr = independenceMethod(independentArray);

            double[][] independent_array = independentMatMul(independentZArr, independentMethods(independentArray));


            double[][] independentGArr = new double[independentNumSamples][independentNumComponents];
            double[] independentAverageGPrimeArr = new double[independentNumComponents];

            for (int i = 0; i < independentNumSamples; i++) {
                for (int k = 0; k < independentNumComponents; k++) {
                    double independence = independent_array[i][k];
                    independentGArr[i][k] = independentG(independence, independentConfig);
                    independentAverageGPrimeArr[k] += independentGPrime(independence, independentConfig);
                }
            }
            for (int k = 0; k < independentNumComponents; k++) {
                independentAverageGPrimeArr[k] /= independentNumSamples;
            }

            double[][] independent_Arr = independentMatMul(independentMethods(independentGArr), independentZArr);
            independentScaleInPlace(independent_Arr, 1.0 / independentNumSamples);

            for (int num = 0; num < independentNumComponents; num++) {
                double independentScale = independentAverageGPrimeArr[num];
                for (int j = 0; j < independentNumComponents; j++) {
                    independent_Arr[num][j] -= independentScale * independentArray[num][j];
                }
            }


            independentArray = independentSymDecorrelate(independent_Arr);


            double independentMaxDelta = 0.0;
            for (int i = 0; i < independentNumComponents; i++) {
                double independentDot = independentDot(independentArray[i], independentArr[i]);
                double independentDelta = Math.abs(Math.abs(independentDot) - 1.0);
                if (independentDelta > independentMaxDelta) independentMaxDelta = independentDelta;
            }
            if (independentMaxDelta < independentConfig.independentComponent) break;
        }

        return independentArray;
    }


    private static double[][] independentDeflationFastICA(double[][] independentZArr, IndependentConfig independentConfig) {
        final int independentNumSamples = independentZArr.length;
        final int independentNumComponents = independentZArr[0].length;

        Random independentRnd = new Random(independentSeed);

        double[][] independentArray = new double[independentNumComponents][independentNumComponents];

        for (int independentNum = 0; independentNum < independentNumComponents; independentNum++) {
            double[] independentVecArr = new double[independentNumComponents];
            for (int j = 0; j < independentNumComponents; j++) {
                independentVecArr[j] = independentRnd.nextGaussian();
            }
            independentNormalizeInPlace(independentVecArr);

            for (int independentIteration = 0; independentIteration < independentConfig.independentMaxIterations; independentIteration++) {
                double[] independentVecArray = Arrays.copyOf(independentVecArr, independentVecArr.length);

                double[] independentVectorArr = new double[independentNumSamples];
                for (int i = 0; i < independentNumSamples; i++) {
                    independentVectorArr[i] = independentDot(independentZArr[i], independentVecArr);
                }

                double[] independentVectorArray = new double[independentNumComponents];
                double independentAverageGPrime = 0.0;

                for (int i = 0; i < independentNumSamples; i++) {
                    double independence = independentVectorArr[i];
                    double independentG = independentG(independence, independentConfig);
                    double independentGPrime = independentGPrime(independence, independentConfig);
                    independentAverageGPrime += independentGPrime;

                    for (int j = 0; j < independentNumComponents; j++) {
                        independentVectorArray[j] += independentZArr[i][j] * independentG;
                    }
                }

                independentAverageGPrime /= independentNumSamples;
                for (int j = 0; j < independentNumComponents; j++) {
                    independentVectorArray[j] = independentVectorArray[j] / independentNumSamples
                            - independentAverageGPrime * independentVecArr[j];
                }

                for (int num = 0; num < independentNum; num++) {
                    double independentProj = independentDot(independentVectorArray, independentArray[num]);
                    for (int j = 0; j < independentNumComponents; j++) {
                        independentVectorArray[j] -= independentProj * independentArray[num][j];
                    }
                }

                independentNormalizeInPlace(independentVectorArray);

                double independentDelta = Math.abs(Math.abs(independentDot(independentVectorArray, independentVecArray)) - 1.0);
                independentVecArr = independentVectorArray;
                if (independentDelta < independentConfig.independentComponent) break;
            }

            independentArray[independentNum] = independentVecArr;
        }

        return independentSymDecorrelate(independentArray);
    }


    private static double independentG(double independentValue, IndependentConfig independentConfig) {
        return switch (independentConfig.independentNonlinearity) {
            case INDEPENDENT_LOGCOSH -> Math.tanh(independentComponent * independentValue);
            case INDEPENDENT_CUBE -> independentValue * independentValue * independentValue;
            case INDEPENDENT_EXP -> independentValue * Math.exp(-0.5 * independentValue * independentValue);
            case INDEPENDENT_TANH -> Math.tanh(independentValue);
            case INDEPENDENT_ARCTAN -> Math.atan(independentValue);
        };
    }

    private static double independentGPrime(double independentValue, IndependentConfig independentConfig) {
        return switch (independentConfig.independentNonlinearity) {
            case INDEPENDENT_LOGCOSH -> {
                double independentT = Math.tanh(independentComponent * independentValue);
                yield independentComponent * (1.0 - independentT * independentT);
            }
            case INDEPENDENT_CUBE -> 2.0 * independentValue * independentValue;

            case INDEPENDENT_EXP -> {
                double independentE = Math.exp(-0.5 * independentValue * independentValue);
                yield (1.0 - independentValue * independentValue) * independentE;
            }
            case INDEPENDENT_TANH -> {
                double independentT = Math.tanh(independentValue);
                yield 1.0 - independentT * independentT;
            }
            case INDEPENDENT_ARCTAN -> 1.0 / (1.0 + independentValue * independentValue);
        };
    }



    private static final class IndependentWhitening {
        final double[][] independentZArr;
        final double[][] independentWhiteningArr;
        IndependentWhitening(double[][] independentZArr, double[][] independentWhiteningArr) {
            this.independentZArr = independentZArr;
            this.independentWhiteningArr = independentWhiteningArr;
        }
    }

    private static IndependentWhitening independentWhitenPCA(double[][] independentCenteredArr, int independentNumComponents) {
        final int independentNumSamples = independentCenteredArr.length;
        final int independentNumFeatures = independentCenteredArr[0].length;

        double[][] independentCovArr = independentCovariance(independentCenteredArr);
        IndependentEigenSym independentEig = independentJacobiEigenDecomposition(independentCovArr);


        int[] independentIndexArr = independentArgsortDesc(independentEig.independentValueArr);

        double[] independentEigenValueArr = new double[independentNumComponents];
        double[][] independentEigenVectorArr = new double[independentNumFeatures][independentNumComponents];

        for (int k = 0; k < independentNumComponents; k++) {
            int i = independentIndexArr[k];
            independentEigenValueArr[k] = Math.max(independentEig.independentValueArr[i], independentEps);
            for (int r = 0; r < independentNumFeatures; r++) {
                independentEigenVectorArr[r][k] = independentEig.independentVectorArr[r][i];
            }
        }


        double[][] independentETArr = independentMethods(independentEigenVectorArr);
        double[][] independentWhiteningArr = new double[independentNumComponents][independentNumFeatures];
        for (int num = 0; num < independentNumComponents; num++) {
            double independentInvSqrt = 1.0 / Math.sqrt(independentEigenValueArr[num]);
            for (int j = 0; j < independentNumFeatures; j++) {
                independentWhiteningArr[num][j] = independentInvSqrt * independentETArr[num][j];
            }
        }


        double[][] independentArr = independentMethods(independentCenteredArr);
        double[][] independentZtArr = independentMatMul(independentWhiteningArr, independentArr);
        double[][] independentZArr = independentMethods(independentZtArr);


        if (independentWhitenUnitVarianceScale != 0.0) {
            for (int num = 0; num < independentNumComponents; num++) {
                double independentVar = 0.0;
                for (int i = 0; i < independentNumSamples; i++) {
                    independentVar += independentZArr[i][num] * independentZArr[i][num];
                }
                independentVar /= independentNumSamples;

                double independentInvStd = 1.0 / Math.sqrt(Math.max(independentVar, independentEps));


                double independentScale = 1.0 + (independentInvStd - 1.0) * independentWhitenUnitVarianceScale;

                for (int i = 0; i < independentNumSamples; i++) independentZArr[i][num] *= independentScale;
                for (int j = 0; j < independentNumFeatures; j++) independentWhiteningArr[num][j] *= independentScale;
            }
        }


        return new IndependentWhitening(independentZArr, independentWhiteningArr);
    }

    private static double[][] independentCovariance(double[][] independentCenteredArr) {
        final int independentNumSamples = independentCenteredArr.length;
        final int independentNumFeatures = independentCenteredArr[0].length;

        double[][] independentCovArr = new double[independentNumFeatures][independentNumFeatures];

        for (int i = 0; i < independentNumSamples; i++) {
            double[] independentRowArr = independentCenteredArr[i];
            for (int a = 0; a < independentNumFeatures; a++) {
                double independentValue = independentRowArr[a];
                for (int b = a; b < independentNumFeatures; b++) {
                    independentCovArr[a][b] += independentValue * independentRowArr[b];
                }
            }
        }

        double independentNum = 1.0 / independentNumSamples;
        for (int a = 0; a < independentNumFeatures; a++) {
            for (int b = a; b < independentNumFeatures; b++) {
                independentCovArr[a][b] *= independentNum;
                independentCovArr[b][a] = independentCovArr[a][b];
            }
        }
        return independentCovArr;
    }


    private static double[][] independentSymDecorrelate(double[][] independentArr) {
        int independentM = independentArr.length;

        double[][] independentWWtArr = independentMatMul(independentArr, independentMethods(independentArr));
        double[][] independentInvSqrtArr = independentInvSqrtSymmetric(independentWWtArr);

        return independentMatMul(independentInvSqrtArr, independentArr);
    }

    private static double[][] independentInvSqrtSymmetric(double[][] independentSymArr) {
        int n = independentSymArr.length;

        IndependentEigenSym independentEig = independentJacobiEigenDecomposition(independentSymArr);

        double[] independentInvSqrtEigenArr = new double[n];
        for (int i = 0; i < n; i++) {
            double independentValue = Math.max(independentEig.independentValueArr[i], independentEps);
            independentInvSqrtEigenArr[i] = 1.0 / Math.sqrt(independentValue);
        }

        double[][] independentVectorArr = independentEig.independentVectorArr; // columns
        double[][] independentVectorDiagArr = new double[n][n];
        for (int col = 0; col < n; col++) {
            double independentScale = independentInvSqrtEigenArr[col];
            for (int r = 0; r < n; r++) {
                independentVectorDiagArr[r][col] = independentVectorArr[r][col] * independentScale;
            }
        }
        return independentMatMul(independentVectorDiagArr, independentMethods(independentVectorArr));
    }



    private static double[][] independentPseudoInverse(double[][] independentArr) {
        double[][] independent_Array = independentMethods(independentArr);
        double[][] independentArray = independentMatMul(independentArr, independentMethods(independentArr));
        double[][] independentInvArr = independentInvertSymmetric(independentArray);
        return independentMatMul(independent_Array, independentInvArr);
    }

    private static double[][] independentInvertSymmetric(double[][] independentSymArr) {
        int n = independentSymArr.length;

        IndependentEigenSym independentEig = independentJacobiEigenDecomposition(independentSymArr);

        double[] independentInvEigenArr = new double[n];
        for (int i = 0; i < n; i++) {
            independentInvEigenArr[i] = 1.0 / Math.max(independentEig.independentValueArr[i], independentEps);
        }

        double[][] independentArr = independentEig.independentVectorArr;
        double[][] independentDiagArr = new double[n][n];
        for (int col = 0; col < n; col++) {
            double independentScale = independentInvEigenArr[col];
            for (int r = 0; r < n; r++) {
                independentDiagArr[r][col] = independentArr[r][col] * independentScale;
            }
        }
        return independentMatMul(independentDiagArr, independentMethods(independentArr));
    }


    private static double[] independentColumnAverage(double[][] independentArr) {
        int independentNumSamples = independentArr.length;
        int independentNumFeatures = independentArr[0].length;
        double[] independentAverageArr = new double[independentNumFeatures];

        for (double[] independentRowArr : independentArr) {
            for (int j = 0; j < independentNumFeatures; j++) {
                independentAverageArr[j] += independentRowArr[j];
            }
        }
        for (int j = 0; j < independentNumFeatures; j++) {
            independentAverageArr[j] /= independentNumSamples;
        }
        return independentAverageArr;
    }

    private static double[][] independentCenter(double[][] independentArr, double[] independentAverageArr) {
        int independentNumSamples = independentArr.length;
        int independentNumFeatures = independentArr[0].length;
        double[][] independentCenteredArr = new double[independentNumSamples][independentNumFeatures];

        for (int i = 0; i < independentNumSamples; i++) {
            for (int j = 0; j < independentNumFeatures; j++) {
                independentCenteredArr[i][j] = independentArr[i][j] - independentAverageArr[j];
            }
        }
        return independentCenteredArr;
    }

    private static double[][] independenceMethod(double[][] independentArr) {
        double[][] independentArray = new double[independentArr.length][];
        for (int i = 0; i < independentArr.length; i++) {
            independentArray[i] = Arrays.copyOf(independentArr[i], independentArr[i].length);
        }
        return independentArray;
    }

    private static void independentScaleInPlace(double[][] independentArr, double independentScale) {
        for (int i = 0; i < independentArr.length; i++) {
            for (int j = 0; j < independentArr[i].length; j++) {
                independentArr[i][j] *= independentScale;
            }
        }
    }

    private static double independentDot(double[] independentArrA, double[] independentArrB) {
        double independentSum = 0.0;
        for (int i = 0; i < independentArrA.length; i++) {
            independentSum += independentArrA[i] * independentArrB[i];
        }
        return independentSum;
    }

    private static void independentNormalizeInPlace(double[] independentVecArr) {
        double independentNorm = Math.sqrt(Math.max(independentDot(independentVecArr, independentVecArr), independentEps));
        for (int i = 0; i < independentVecArr.length; i++) {
            independentVecArr[i] /= independentNorm;
        }
    }


//!!!
    private static double[][] independentMethods(double[][] independentArr) {
        int r = independentArr.length;
        int c = independentArr[0].length;
        double[][] independentArray = new double[c][r];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                independentArray[j][i] = independentArr[i][j];
            }
        }
        return independentArray;
    }

    private static double[][] independentMatMul(double[][] independentArrA, double[][] independentArrB) {
        int rA = independentArrA.length;
        int cA = independentArrA[0].length;
        int rB = independentArrB.length;
        int cB = independentArrB[0].length;
        if (cA != rB) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        double[][] independentArrC = new double[rA][cB];
        for (int i = 0; i < rA; i++) {
            for (int num = 0; num < cA; num++) {
                double independentAik = independentArrA[i][num];
                for (int j = 0; j < cB; j++) {
                    independentArrC[i][j] += independentAik * independentArrB[num][j];
                }
            }
        }
        return independentArrC;
    }

    private static int[] independentArgsortDesc(double[] independentArr) {
        Integer[] independentIdxArr = new Integer[independentArr.length];
        for (int i = 0; i < independentArr.length; i++) independentIdxArr[i] = i;
        Arrays.sort(independentIdxArr, (a, b) -> Double.compare(independentArr[b], independentArr[a]));
        int[] independentOutArr = new int[independentArr.length];
        for (int i = 0; i < independentArr.length; i++) independentOutArr[i] = independentIdxArr[i];
        return independentOutArr;
    }


    private static final class IndependentEigenSym {
        final double[] independentValueArr;
        final double[][] independentVectorArr;
        IndependentEigenSym(double[] independentValueArr, double[][] independentVectorArr) {
            this.independentValueArr = independentValueArr;
            this.independentVectorArr = independentVectorArr;
        }
    }

    private static IndependentEigenSym independentJacobiEigenDecomposition(double[][] independentSymArr) {
        final int n = independentSymArr.length;

        double[][] independentAArr = independenceMethod(independentSymArr);

        double[][] independentVectorArr = new double[n][n];
        for (int i = 0; i < n; i++) independentVectorArr[i][i] = 1.0;

        final double independentComponent = independentJacobiComponent;

        final int independentMax = 500 * n * n;

        for (int sweep = 0; sweep < independentMax; sweep++) {

            int val = 0, element = 1;
            double max = 0.0;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    double value = Math.abs(independentAArr[i][j]);
                    if (value > max) {
                        max = value;
                        val = i;
                        element = j;
                    }
                }
            }
            if (max < independentComponent) break;

            double independentValue = independentAArr[val][val];
            double independent = independentAArr[element][element];
            double independence = independentAArr[val][element];

            double tau = (independent - independentValue) / (2.0 * independence);
            double t = Math.signum(tau) / (Math.abs(tau) + Math.sqrt(1.0 + tau * tau));
            double c = 1.0 / Math.sqrt(1.0 + t * t);
            double s = t * c;

            for (int num = 0; num < n; num++) {
                if (num == val || num == element) continue;

                double aik = independentAArr[num][val];
                double akq = independentAArr[num][element];

                double independent_val = c * aik - s * akq;
                double independent_value = s * aik + c * akq;

                independentAArr[num][val] = independentAArr[val][num] = independent_val;
                independentAArr[num][element] = independentAArr[element][num] = independent_value;
            }

            double independent_result = c * c * independentValue - 2.0 * s * c * independence + s * s * independent;
            double independentResult = s * s * independentValue + 2.0 * s * c * independence + c * c * independent;

            independentAArr[val][val] = independent_result;
            independentAArr[element][element] = independentResult;
            independentAArr[val][element] = independentAArr[element][val] = 0.0;

            for (int k = 0; k < n; k++) {
                double independentNum = independentVectorArr[k][val];
                double independenceValue = independentVectorArr[k][element];
                independentVectorArr[k][val] = c * independentNum - s * independenceValue;
                independentVectorArr[k][element] = s * independentNum + c * independenceValue;
            }
        }

        double[] independentEigenValueArr = new double[n];
        for (int i = 0; i < n; i++) independentEigenValueArr[i] = independentAArr[i][i];

        return new IndependentEigenSym(independentEigenValueArr, independentVectorArr);
    }



    public static void main(String[] args) {
        int independentNumSamples = 2000;
        IndependentConfig independentConfig = new IndependentConfig();


        double[][] independentSourceArr = new double[independentNumSamples][2];
        for (int i = 0; i < independentNumSamples; i++) {
            double t = i / 100.0;
            independentSourceArr[i][0] = Math.sin(t);
            independentSourceArr[i][1] = Math.signum(Math.sin(5 * t));
        }


        double[][] independentArr = {
                {5.0, 5.2, 5.15},
                {5.2, 5.0, 5.0},
                {5.0, 5.0, 0.0}
        };
        double[][] independentArray = independentMatMul(independentSourceArr, independentMethods(independentArr));


        independentConfig.independentNumComponents = 2;
        independentConfig.independentComponents = IndependentComponent.INDEPENDENT_SYMMETRIC;
        independentConfig.independentNonlinearity = IndependentNonlinearity.INDEPENDENT_LOGCOSH;
        independentConfig.independentMaxIterations = 5000;
        independentConfig.independentComponent = 1e-5;

        IndependentResult independentResult = independentFit(independentArray, independentConfig);
        System.out.println("FastICA 결과 모든 성분들은 독립적이고 다른 성분과 무관합니다"+independentResult);
    }
}
