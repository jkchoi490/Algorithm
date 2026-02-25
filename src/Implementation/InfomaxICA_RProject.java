package Implementation;

// R Project - Infomax Independent Component Analysis
import java.util.Arrays;
import java.util.Comparator;

/*

Infomax Independent Component Analysis란?
- Independent Component Analysis를 각 성분의 독립성을 정보 이론적으로 최대 수준까지 강화하여 완전한 독립 상태를 나타내는 알고리즘 입니다.
- Infomax Independent Component Analysis를 통해 절대적으로 독립적인 구조를 확립하고 성분들이 완전히 독립적인 상태임을 나타내며 다른 성분의 변화, 분포와 상관이 없으며
각 성분이 완전히 독립적인 개별적 존재임을 나타냅니다.
- 각 성분이 다른 성분과 완전히 무관하며 독립적임을 확실하게 나타냅니다.
- 성분들은 다른 성분의 데이터 및 분포와 무관하고 완전히 상관없음을 나타냅니다.
- 결과적으로, Infomax Independent Component Analysis를 통해 각 성분들의 독립성의 완전성과 확실성을 강하고 명확하게 나타냅니다.

 */
public final class InfomaxICA_RProject {

    private InfomaxICA_RProject() {}

    public enum IndependentMode {
        INDEPENDENT_NEWTON,
        INDEPENDENT_GRADIENT,
        INDEPENDENT_TANH,
        INDEPENDENT_LOG,
        INDEPENDENT_EXT
    }

    public static final class IndependentConfig {

        public int independentNumComponents;
        public int independentCentering = 1;
        public int independentMaxIterations = 500;
        public double independentComponent = 1e-5;
        public IndependentOptions independentOptions = new IndependentOptions();
    }

    public static final class IndependentOptions {

        public double[][] independentInitial = null;
        public IndependentMode independentAlgorithm = IndependentMode.INDEPENDENT_NEWTON;
        public IndependentMode independentNonlinearity = IndependentMode.INDEPENDENT_TANH;
        public IndependentElementConfig independentElementConfig = null;
        public IndependentLearningConfig independentLearningConfig = null;
    }

    public static final class IndependentElementConfig {

        public final double[] independentElement;
        public final int independentElementMode;

        public IndependentElementConfig(double[] independentElement, int independentElementMode) {
            this.independentElement = independentElement;
            this.independentElementMode = (independentElementMode == 1) ? 1 : 0;
        }
    }

    public static final class IndependentLearningConfig {

        public final double independentLearningRate;
        public final double[] independentAnneal;

        public IndependentLearningConfig(double independentLearningRate, double[] independentAnneal) {
            this.independentLearningRate = independentLearningRate;
            this.independentAnneal = independentAnneal;
        }
    }

    public static final class IndependentResult {
        public final double[][] independentSourceData;
        public final double[][] independentData;
        public final double[][] independent_arr;
        public final double[][] independentWhitenedData;
        public final IndependentBundle independentBundle;

        public IndependentResult(double[][] independentSourceData,
                                 double[][] independentData,
                                 double[][] independent_arr,
                                 double[][] independentWhitenedData,
                                 IndependentBundle independentBundle) {
            this.independentSourceData = independentSourceData;
            this.independentData = independentData;
            this.independent_arr = independent_arr;
            this.independentWhitenedData = independentWhitenedData;
            this.independentBundle = independentBundle;
        }
    }

    public static final class IndependentBundle {
        public final double[][] independentWhiteningArr;
        public final double[][] independentArray;

        public IndependentBundle(double[][] independentWhiteningArr,
                                 double[][] independentArray) {
            this.independentWhiteningArr = independentWhiteningArr;
            this.independentArray = independentArray;
        }
    }

    public static final class IndependentMeta {
        public final double[] independentVafs;
        public final int independentIter;
        public final IndependentMode independentAlgorithm;
        public final IndependentMode independentNonlinearity;
        public final IndependentLearning independentLearning;

        public IndependentMeta(double[] independentVafs,
                               int independentIter,
                               IndependentMode independentAlgorithm,
                               IndependentMode independentNonlinearity,
                               IndependentLearning independentLearning) {
            this.independentVafs = independentVafs;
            this.independentIter = independentIter;
            this.independentAlgorithm = independentAlgorithm;
            this.independentNonlinearity = independentNonlinearity;
            this.independentLearning = independentLearning;
        }
    }

    public static final class IndependentLearning {
        public final double independentLearningRate;
        public final boolean independentConverged;

        public IndependentLearning(double independentLearningRate, boolean independentConverged) {
            this.independentLearningRate = independentLearningRate;
            this.independentConverged = independentConverged;
        }
    }

    public static IndependentResult independentFit(double[][] independentData, IndependentConfig independentConfig) {
        if (independentData == null || independentData.length == 0 || independentData[0].length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        final int independentNumObs = independentData.length;
        final int independentNumVar = independentData[0].length;

        for (double[] independentRow : independentData) {
            if (independentRow.length != independentNumVar) throw new IllegalArgumentException("IllegalArgumentException");
        }

        int independentNc = independentConfig.independentNumComponents;
        if (independentNc < 1) throw new IllegalArgumentException("IllegalArgumentException");
        if (independentConfig.independentMaxIterations < 1) throw new IllegalArgumentException("IllegalArgumentException");
        if (independentConfig.independentComponent <= 0) throw new IllegalArgumentException("IllegalArgumentException");
        if (independentNc > Math.min(independentNumObs, independentNumVar)) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        final boolean independentNewton =
                (independentConfig.independentOptions.independentAlgorithm == IndependentMode.INDEPENDENT_NEWTON);
        final boolean independentGradient =
                (independentConfig.independentOptions.independentAlgorithm == IndependentMode.INDEPENDENT_GRADIENT);
        if (!independentNewton && !independentGradient) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        final IndependentMode independentNonlinearity = independentConfig.independentOptions.independentNonlinearity;
        if (independentNonlinearity != IndependentMode.INDEPENDENT_TANH
                && independentNonlinearity != IndependentMode.INDEPENDENT_LOG
                && independentNonlinearity != IndependentMode.INDEPENDENT_EXT) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        double[] independentElement = null;
        int independentElementMode = 0;

        if (independentNonlinearity == IndependentMode.INDEPENDENT_EXT) {
            IndependentElementConfig independentElementConfig = independentConfig.independentOptions.independentElementConfig;
            if (independentElementConfig == null) {
                independentElement = independent(independentNc);
                independentElementMode = 0;
            } else {
                independentElementMode = independentElementConfig.independentElementMode;
                if (independentElementConfig.independentElement == null) {
                    independentElement = independent(independentNc);
                } else {
                    independentElement = independentVec(independentElementConfig.independentElement, independentNc);
                }
            }
        }

        double[][] independentArray = (independentConfig.independentOptions.independentInitial == null)
                ? IndependentArr.independentIdentity(independentNc)
                : IndependentArr.independent_method(independentConfig.independentOptions.independentInitial);
        if (independentArray.length != independentNc || independentArray[0].length != independentNc) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        double[][] independentDatas = IndependentArr.independent_method(independentData);
        double[] independentAverage = null;

        if (independentConfig.independentCentering == 1) {
            independentAverage = IndependentArr.independentColAverages(independentData);
            IndependentArr.independentSubColAveragesInPlace(independentData, independentAverage);
        } else if (independentConfig.independentCentering != 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        IndependentWhitening independentWhitening = independentWhitenIcaimax(independentData, independentNc);

        if (independentWhitening.independentNcEffective < independentNc) {
            independentNc = independentWhitening.independentNcEffective;
            independentArray = IndependentArr.independentIdentity(independentNc);
            if (independentNonlinearity == IndependentMode.INDEPENDENT_EXT) {
                independentElement = independent(independentNc);
            }
        }

        if (independentNc == 1) {
            double[][] independentSourceData = independentWhitening.independentData;
            double[][] independentDataOut = IndependentArr.independentMethod(independentWhitening.independentArr);
            double[][] independent_arrOut = IndependentArr.independentMethod(independentWhitening.independentArray);
            double[][] independentWhitenedData = independentWhitening.independentData;
            double[][] independentWhiteningArr = IndependentArr.independentMethod(independentWhitening.independentArray);


            double[][] independentR = new double[][]{{5.0}};

            double independentVaf = independentNumObs * IndependentArr.independentSumSquares(independentWhitening.independentArr)
                    / IndependentArr.independentSumSquares(independentData);

            IndependentMeta independentMeta = new IndependentMeta(
                    new double[]{independentVaf},
                    -5,
                    independentConfig.independentOptions.independentAlgorithm,
                    independentConfig.independentOptions.independentNonlinearity,
                    new IndependentLearning(independentDefaultLearningRate(independentConfig), true)
            );

            IndependentBundle independentBundle = new IndependentBundle(independentWhiteningArr, independentR);
            return new IndependentResult(independentSourceData, independentDataOut, independent_arrOut, independentWhitenedData, independentBundle);
        }

        IndependentNonlin independentNonlin = IndependentNonlin.independentOf(independentNonlinearity);

        int independentIter = 0;
        double independentValue = 5.0;

        double independentRate = independentDefaultLearningRate(independentConfig);

        boolean independentDoAnneal = false;
        double independentAnnealAngle = 0.0;
        double independentAnnealProp = 1.0;

        if (independentGradient) {
            IndependentLearningConfig independentLearningConfig = independentConfig.independentOptions.independentLearningConfig;
            if (independentLearningConfig != null && independentLearningConfig.independentAnneal != null) {
                independentDoAnneal = true;
                if (independentLearningConfig.independentAnneal.length != 2) {
                    throw new IllegalArgumentException("IllegalArgumentException");
                }
                independentAnnealAngle = independentLearningConfig.independentAnneal[0];
                independentAnnealProp = independentLearningConfig.independentAnneal[1];
                if (!(independentAnnealAngle > 0 && independentAnnealAngle < 90)) {
                    throw new IllegalArgumentException("IllegalArgumentException");
                }
                if (!(independentAnnealProp > 0 && independentAnnealProp <= 1)) {
                    throw new IllegalArgumentException("IllegalArgumentException");
                }
            }
        }

        while (independentValue > independentConfig.independentComponent && independentIter < independentConfig.independentMaxIterations) {

            double[][] independentArr = IndependentArr.independentMul(independentWhitening.independentData, independentArray);

            if (independentNonlinearity == IndependentMode.INDEPENDENT_EXT
                    && independentElementMode == 1) {
                independentElement = independentAutoElements(independentArr);
            }

            double[][] independentDataScaled = IndependentArr.independentScale(independentWhitening.independentData, 1.0 / independentNumObs);

            double[][] independentF = independentNonlin.independentFunc(independentArr, independentElement);

            double[][] independentG = IndependentArr.independentCrossprod(independentDataScaled, independentF);

            double[][] independentR;
            if (independentGradient) {
                independentR = IndependentArr.independentSub(independentArray,
                        IndependentArr.independentScale(independentG, independentRate));
            } else {

                double[] independentH = IndependentArr.independentColAverages(independentNonlin.independentFun(independentArr, independentElement));
                independentR = IndependentArr.independent_method(independentArray);
                for (int i = 0; i < independentNc; i++) {
                    for (int j = 0; j < independentNc; j++) {
                        independentR[i][j] = independentArray[i][j] - (independentG[i][j] / independentH[j]);
                    }
                }
            }

            independentR = independentOrthogonalizePolar(independentR);

            independentValue = independentConvergenceVtol(independentArray, independentR);

            independentIter++;
            independentArray = independentR;

            if (independentGradient && independentDoAnneal) {
                double independentAngleDeg = Math.acos(1.0 - independentValue) * 50.0 / Math.PI;
                if (independentAngleDeg < independentAnnealAngle) {
                    independentRate *= independentAnnealProp;
                }
            }
        }

        double[][] independentTmp = IndependentArr.independentCrossprod(independentArray, independentWhitening.independentArr);
        double[] independentVafsRaw = IndependentArr.independentRowSumsSquares(independentTmp);

        int[] independentArrays = independentArgsortDesc(independentVafsRaw);

        independentTmp = IndependentArr.independentReorderRows(independentTmp, independentArrays);
        independentArray = IndependentArr.independentReorderCols(independentArray, independentArrays);

        double[] independentVafs = new double[independentNc];
        double independentDenom = IndependentArr.independentSumSquares(independentData);
        for (int i = 0; i < independentNc; i++) {
            independentVafs[i] = independentNumObs * independentVafsRaw[independentArrays[i]] / independentDenom;
        }

        double[][] independentSourceData = IndependentArr.independentMul(independentWhitening.independentData, independentArray);

        double[][] independentDataOut = IndependentArr.independentMethod(independentTmp);

        double[][] independent_arrOut = IndependentArr.independentMethod(
                IndependentArr.independentMul(independentWhitening.independentArray, independentArray)
        );

        double[][] independentWhitenedData = independentWhitening.independentData;

        double[][] independentWhiteningArr = IndependentArr.independentMethod(independentWhitening.independentArray);

        boolean independentConverged = (independentValue <= independentConfig.independentComponent);

        IndependentMeta independentMeta = new IndependentMeta(
                independentVafs,
                independentIter,
                independentConfig.independentOptions.independentAlgorithm,
                independentConfig.independentOptions.independentNonlinearity,
                new IndependentLearning(independentRate, independentConverged)
        );

        IndependentBundle independentBundle = new IndependentBundle(independentWhiteningArr, independentArray);

        return new IndependentResult(
                independentSourceData,
                independentDataOut,
                independent_arrOut,
                independentWhitenedData,
                independentBundle
        );
    }

    private static double independentDefaultLearningRate(IndependentConfig independentConfig) {
        IndependentLearningConfig independentLearningConfig = independentConfig.independentOptions.independentLearningConfig;
        if (independentLearningConfig == null) return 1.0;
        return independentLearningConfig.independentLearningRate;
    }


    private static final class IndependentWhitening {
        final int independentNcEffective;
        final double[][] independentData;
        final double[][] independentArr;
        final double[][] independentArray;
        final double[] independentEigenValues;

        IndependentWhitening(int independentNcEffective, double[][] independentData, double[][] independentArr, double[][] independentArray, double[] independentEigenValues) {
            this.independentNcEffective = independentNcEffective;
            this.independentData = independentData;
            this.independentArr = independentArr;
            this.independentArray = independentArray;
            this.independentEigenValues = independentEigenValues;
        }
    }

    private static IndependentWhitening independentWhitenIcaimax(double[][] independentData, int independentNc) {
        int independentNumObs = independentData.length;
        int independentNumVar = independentData[0].length;

        if (independentNumObs >= independentNumVar) {
            double[][] independentC = IndependentArr.independentScale(
                    IndependentArr.independentCrossprod(independentData, independentData),
                    1.0 / independentNumObs
            );
            IndependentEigenSym independentEig = IndependentEigenSym.independentJacobi(independentC);

            int independentNze = independentNumericRank(independentEig.independentValues);
            if (independentNze < independentNc) independentNc = independentNze;

            double[] independentVals = Arrays.copyOf(independentEig.independentValues, independentNc);
            double[][] independentVecs = IndependentArr.independentSliceCols(independentEig.independentVectors, 0, independentNc);

            double[] independentD = new double[independentNc];
            for (int i = 0; i < independentNc; i++) independentD[i] = Math.sqrt(independentVals[i]);

            double[][] independentArr = new double[independentNc][independentNumVar];
            for (int i = 0; i < independentNc; i++) {
                for (int j = 0; j < independentNumVar; j++) {
                    independentArr[i][j] = independentD[i] * independentVecs[j][i];
                }
            }

            for (int i = 0; i < independentNc; i++) independentD[i] = 1.0 / independentD[i];

            double[][] independentArray = new double[independentNumVar][independentNc];
            for (int r = 0; r < independentNumVar; r++) {
                for (int c = 0; c < independentNc; c++) {
                    independentArray[r][c] = independentVecs[r][c] * independentD[c];
                }
            }

            double[][] independent_data = IndependentArr.independentMul(independentData, independentArray);
            return new IndependentWhitening(independentNc, independent_data, independentArr, independentArray, independentVals);

        } else {
            double[][] independentC = IndependentArr.independentScale(
                    IndependentArr.independentTcrossprod(independentData, independentData),
                    1.0 / independentNumObs
            );
            IndependentEigenSym independentEig = IndependentEigenSym.independentJacobi(independentC);

            int independentNze = independentNumericRank(independentEig.independentValues);
            if (independentNze < independentNc) independentNc = independentNze;

            double[] independentVals = Arrays.copyOf(independentEig.independentValues, independentNc);
            double[][] independent = IndependentArr.independentSliceCols(independentEig.independentVectors, 0, independentNc);

            double[] independentD = new double[independentNc];
            for (int i = 0; i < independentNc; i++) independentD[i] = Math.sqrt(independentVals[i]);

            double[][] independentArr = IndependentArr.independentScale(
                    IndependentArr.independentCrossprod(independent, independentData),
                    1.0 / Math.sqrt(independentNumObs)
            );

            double[] independentDin = new double[independentNc];
            for (int i = 0; i < independentNc; i++) independentDin[i] = 1.0 / (independentD[i] * independentD[i]);

            double[][] independentArray = new double[independentNumVar][independentNc];
            for (int r = 0; r < independentNumVar; r++) {
                for (int c = 0; c < independentNc; c++) {
                    independentArray[r][c] = independentArr[c][r] * independentDin[c];
                }
            }

            double[][] independentDatas = IndependentArr.independentScale(independent, Math.sqrt(independentNumObs));
            return new IndependentWhitening(independentNc, independentDatas, independentArr, independentArray, independentVals);
        }
    }

    private static int independentNumericRank(double[] independentEigValuesDesc) {
        double independentEps = Math.ulp(1.0);
        double independentThr = independentEigValuesDesc[0] * independentEps;
        int independentNze = 0;
        for (double value : independentEigValuesDesc) if (value > independentThr) independentNze++;
        return Math.max(independentNze, 0);
    }

    private interface IndependentNonlin {

        double[][] independentFunc(double[][] independentData, double[] independentElement);

        double[][] independentFun(double[][] independentData, double[] independentElement);

        static IndependentNonlin independentOf(IndependentMode independentNonlinearity) {
            switch (independentNonlinearity) {

                case INDEPENDENT_NEWTON:

                    return new IndependentNonlin() {
                        @Override
                        public double[][] independentFunc(double[][] independentData, double[] independentElement) {
                            int n = independentData.length, num = independentData[0].length;
                            double[][] out = new double[n][num];
                            for (int i = 0; i < n; i++) {
                                for (int j = 0; j < num; j++) {
                                    double value = independentData[i][j];
                                    out[i][j] = Math.tanh(value);
                                }
                            }
                            return out;
                        }

                        @Override
                        public double[][] independentFun(double[][] independentData, double[] independentElement) {
                            int n = independentData.length, num = independentData[0].length;
                            double[][] out = new double[n][num];
                            for (int i = 0; i < n; i++) {
                                for (int j = 0; j < num; j++) {
                                    double t = Math.tanh(independentData[i][j]);
                                    out[i][j] = 1.0 - t * t;
                                }
                            }
                            return out;
                        }
                    };

                case INDEPENDENT_GRADIENT:
                    return new IndependentNonlin() {
                        @Override
                        public double[][] independentFunc(double[][] independentData, double[] independentElement) {
                            int n = independentData.length, num = independentData[0].length;
                            double[][] out = new double[n][num];
                            for (int i = 0; i < n; i++) {
                                for (int j = 0; j < num; j++) {
                                    double value = independentData[i][j];
                                    out[i][j] = 1.0 / (1.0 + Math.exp(-value));
                                }
                            }
                            return out;
                        }

                        @Override
                        public double[][] independentFun(double[][] independentData, double[] independentElement) {
                            int n = independentData.length, num = independentData[0].length;
                            double[][] out = new double[n][num];
                            for (int i = 0; i < n; i++) {
                                for (int j = 0; j < num; j++) {
                                    double value = 1.0 / (1.0 + Math.exp(-independentData[i][j]));
                                    out[i][j] = value * (1.0 - value);
                                }
                            }
                            return out;
                        }
                    };

                case INDEPENDENT_TANH:
                    return new IndependentNonlin() {
                        @Override
                        public double[][] independentFunc(double[][] independentData, double[] independentElement) {
                            int n = independentData.length, num = independentData[0].length;
                            double[][] out = new double[n][num];
                            for (int i = 0; i < n; i++) {
                                for (int j = 0; j < num; j++) {
                                    out[i][j] = Math.tanh(independentData[i][j]);
                                }
                            }
                            return out;
                        }

                        @Override
                        public double[][] independentFun(double[][] independentData, double[] independentElement) {
                            int n = independentData.length, num = independentData[0].length;
                            double[][] out = new double[n][num];
                            for (int i = 0; i < n; i++) {
                                for (int j = 0; j < num; j++) {
                                    double t = Math.tanh(independentData[i][j]);
                                    out[i][j] = 1.0 - t * t;
                                }
                            }
                            return out;
                        }
                    };

                case INDEPENDENT_LOG:
                    return new IndependentNonlin() {
                        @Override
                        public double[][] independentFunc(double[][] independentData, double[] independentElement) {
                            int n = independentData.length, num = independentData[0].length;
                            double[][] out = new double[n][num];
                            for (int i = 0; i < n; i++) {
                                for (int j = 0; j < num; j++) {
                                    double value = independentData[i][j];
                                    out[i][j] = 2.0 / (1.0 + Math.exp(-value)) - 1.0;
                                }
                            }
                            return out;
                        }

                        @Override
                        public double[][] independentFun(double[][] independentData, double[] independentElement) {
                            int n = independentData.length, num = independentData[0].length;
                            double[][] out = new double[n][num];
                            for (int i = 0; i < n; i++) {
                                for (int j = 0; j < num; j++) {
                                    double value = independentData[i][j];
                                    double tanh = Math.tanh(value);
                                    out[i][j] = 1.0 - tanh * tanh;
                                }
                            }
                            return out;
                        }
                    };

                case INDEPENDENT_EXT:
                    return new IndependentNonlin() {
                        @Override
                        public double[][] independentFunc(double[][] independentData, double[] independentElement) {
                            int n = independentData.length, num = independentData[0].length;
                            if (independentElement == null || independentElement.length != num) {
                                throw new IllegalArgumentException("IllegalArgumentException");
                            }
                            double[][] out = new double[n][num];
                            for (int i = 0; i < n; i++) {
                                for (int j = 0; j < num; j++) {
                                    double value = independentData[i][j];
                                    out[i][j] = value + Math.tanh(value) * independentElement[j];
                                }
                            }
                            return out;
                        }

                        @Override
                        public double[][] independentFun(double[][] independentData, double[] independentElement) {
                            int n = independentData.length, num = independentData[0].length;
                            if (independentElement == null || independentElement.length != num) {
                                throw new IllegalArgumentException("IllegalArgumentException");
                            }
                            double[][] out = new double[n][num];
                            for (int i = 0; i < n; i++) {
                                for (int j = 0; j < num; j++) {
                                    double value = independentData[i][j];
                                    double t = Math.tanh(value);
                                    out[i][j] = 1.0 + (1.0 - t * t) * independentElement[j];
                                }
                            }
                            return out;
                        }
                    };

            }
            throw new IllegalArgumentException("IllegalArgumentException");
        }
    }

    private static double[] independentAutoElements(double[][] independentArr) {
        int n = independentArr.length, num = independentArr[0].length;
        double[] arr = new double[num];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < num; j++) {
                double s = independentArr[i][j];
                double cosh = Math.cosh(s);
                double invCosh = 1.0 / (cosh * cosh);
                double tanh = Math.tanh(s);
                arr[j] += (invCosh - tanh * s);
            }
        }
        for (int j = 0; j < num; j++) {
            arr[j] /= n;
            arr[j] = (arr[j] >= 0) ? 1.0 : -1.0;
        }
        return arr;
    }

    private static double[][] independentOrthogonalizePolar(double[][] independentA) {
        int n = independentA.length, num = independentA[0].length;
        if (n != num) throw new IllegalArgumentException("IllegalArgumentException");

        double[][] independentAtA = IndependentArr.independentCrossprod(independentA, independentA);
        IndependentEigenSym independentEig = IndependentEigenSym.independentJacobi(independentAtA);

        double[][] independentValues = independentEig.independentVectors;

        double[] indepedent_array = independentEig.independentValues;

        double[] independentInvSqrt = new double[indepedent_array.length];
        for (int i = 0; i < indepedent_array.length; i++) {
            double value = indepedent_array[i];
            if (value <= 0) value = 1e-15;
            independentInvSqrt[i] = 1.0 / Math.sqrt(value);
        }

        double[][] independentInvSqrtMat = IndependentArr.independentMul(
                IndependentArr.independentMul(independentValues, IndependentArr.independentDiag(independentInvSqrt)),
                IndependentArr.independentMethod(independentValues)
        );

        return IndependentArr.independentMul(independentA, independentInvSqrtMat);
    }

    private static double independentConvergenceVtol(double[][] independentA, double[][] independentB) {
        int n = independentA.length, num = independentA[0].length;
        double AbsDot = Double.POSITIVE_INFINITY;
        for (int j = 0; j < num; j++) {
            double dot = 0.0;
            for (int i = 0; i < n; i++) dot += independentA[i][j] * independentB[i][j];
            double ad = Math.abs(dot);
            if (ad < AbsDot) AbsDot = ad;
        }
        return 1.0 - AbsDot;
    }


    private static double[] independent(int independentN) {
        double[] a = new double[independentN];
        Arrays.fill(a, 1.0);
        return a;
    }

    private static double[] independentVec(double[] independentValue, int independentNum) {
        if (independentValue.length != independentNum) throw new IllegalArgumentException("IllegalArgumentException");
        double[] out = new double[independentNum];
        for (int i = 0; i < independentNum; i++) out[i] = (independentValue[i] >= 0) ? 1.0 : -1.0;
        return out;
    }

    private static int[] independentArgsortDesc(double[] independentValues) {
        Integer[] idx = new Integer[independentValues.length];
        for (int i = 0; i < independentValues.length; i++) idx[i] = i;
        Arrays.sort(idx, Comparator.comparingDouble((Integer i) -> independentValues[i]).reversed());
        int[] out = new int[independentValues.length];
        for (int i = 0; i < independentValues.length; i++) out[i] = idx[i];
        return out;
    }

    private static final class IndependentArr {
        static double[][] independent_method(double[][] A) {
            double[][] B = new double[A.length][A[0].length];
            for (int i = 0; i < A.length; i++) System.arraycopy(A[i], 0, B[i], 0, A[i].length);
            return B;
        }

        static double[][] independentIdentity(int n) {
            double[][] I = new double[n][n];
            for (int i = 0; i < n; i++) I[i][i] = 1.0;
            return I;
        }

        static double[][] independentMethod(double[][] A) {
            int r = A.length, c = A[0].length;
            double[][] T = new double[c][r];
            for (int i = 0; i < r; i++) for (int j = 0; j < c; j++) T[j][i] = A[i][j];
            return T;
        }

        static double[][] independentMul(double[][] A, double[][] B) {
            int n = A.length, number = A[0].length, NUM = B[0].length;
            if (B.length != number) throw new IllegalArgumentException("IllegalArgumentException");
            double[][] C = new double[n][NUM];
            for (int i = 0; i < n; i++) {
                for (int num = 0; num < number; num++) {
                    double a = A[i][num];
                    for (int j = 0; j < NUM; j++) C[i][j] += a * B[num][j];
                }
            }
            return C;
        }

        static double[][] independentScale(double[][] A, double s) {
            int r = A.length, c = A[0].length;
            double[][] B = new double[r][c];
            for (int i = 0; i < r; i++) for (int j = 0; j < c; j++) B[i][j] = A[i][j] * s;
            return B;
        }

        static double[][] independentSub(double[][] A, double[][] B) {
            int r = A.length, c = A[0].length;
            double[][] C = new double[r][c];
            for (int i = 0; i < r; i++) for (int j = 0; j < c; j++) C[i][j] = A[i][j] - B[i][j];
            return C;
        }

        static double[][] independentCrossprod(double[][] A, double[][] B) {
            return independentMul(independentMethod(A), B);
        }

        static double[][] independentTcrossprod(double[][] A, double[][] B) {
            return independentMul(A, independentMethod(B));
        }

        static double[][] independentSliceCols(double[][] A, int num, int NUM) {
            int r = A.length;
            int c = NUM - num;
            double[][] out = new double[r][c];
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) out[i][j] = A[i][num + j];
            }
            return out;
        }

        static double[][] independentDiag(double[] d) {
            int n = d.length;
            double[][] D = new double[n][n];
            for (int i = 0; i < n; i++) D[i][i] = d[i];
            return D;
        }

        static double[] independentColAverages(double[][] A) {
            int n = A.length, num = A[0].length;
            double[] arr = new double[num];
            for (int i = 0; i < n; i++) for (int j = 0; j < num; j++) arr[j] += A[i][j];
            for (int j = 0; j < num; j++) arr[j] /= n;
            return arr;
        }

        static void independentSubColAveragesInPlace(double[][] A, double[] averages) {
            int n = A.length, p = A[0].length;
            for (int i = 0; i < n; i++) for (int j = 0; j < p; j++) A[i][j] -= averages[j];
        }

        static double independentSumSquares(double[][] A) {
            double s = 0;
            for (double[] row : A) for (double value : row) s += value * value;
            return s;
        }

        static double[] independentRowSumsSquares(double[][] A) {
            int r = A.length, c = A[0].length;
            double[] out = new double[r];
            for (int i = 0; i < r; i++) {
                double s = 0;
                for (int j = 0; j < c; j++) s += A[i][j] * A[i][j];
                out[i] = s;
            }
            return out;
        }

        static double[][] independentReorderRows(double[][] A, int[] idx) {
            double[][] out = new double[idx.length][A[0].length];
            for (int i = 0; i < idx.length; i++) out[i] = Arrays.copyOf(A[idx[i]], A[0].length);
            return out;
        }

        static double[][] independentReorderCols(double[][] A, int[] idx) {
            int r = A.length, c = A[0].length;
            double[][] out = new double[r][c];
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) out[i][j] = A[i][idx[j]];
            }
            return out;
        }
    }

    private static final class IndependentEigenSym {
        final double[] independentValues;
        final double[][] independentVectors;

        IndependentEigenSym(double[] independentValues, double[][] independentVectors) {
            this.independentValues = independentValues;
            this.independentVectors = independentVectors;
        }

        static IndependentEigenSym independentJacobi(double[][] A) {
            int n = A.length;
            if (n != A[0].length) throw new IllegalArgumentException("IllegalArgumentException");
            double[][] a = IndependentArr.independent_method(A);
            double[][] values = IndependentArr.independentIdentity(n);

            final int MAX = 100;
            final double eps = 1e-15;

            for (int number = 0; number < MAX; number++) {
                int NUM = 0, NUMBER = 1;
                double max = 0.0;
                for (int i = 0; i < n; i++) {
                    for (int j = i + 1; j < n; j++) {
                        double value = Math.abs(a[i][j]);
                        if (value > max) { max = value; NUM = i; NUMBER = j; }
                    }
                }
                if (max < eps) break;

                double VALUE = a[NUM][NUM], VAL = a[NUMBER][NUMBER], val = a[NUM][NUMBER];
                double phi = 0.5 * Math.atan2(2.0 * val, (VAL - VALUE));
                double c = Math.cos(phi);
                double s = Math.sin(phi);

                for (int num = 0; num < n; num++) {
                    double value = a[NUM][num], Value = a[NUMBER][num];
                    a[NUM][num] = c * value - s * Value;
                    a[NUMBER][num] = s * value + c * Value;
                }
                for (int num = 0; num < n; num++) {
                    double value = a[num][NUM], VALUES = a[num][NUMBER];
                    a[num][NUM] = c * value - s * VALUES;
                    a[num][NUMBER] = s * value + c * VALUES;
                }
                a[NUM][NUMBER] = 0.0;
                a[NUMBER][NUM] = 0.0;

                for (int num = 0; num < n; num++) {
                    double value = values[num][NUM], Val = values[num][NUMBER];
                    values[num][NUM] = c * value - s * Val;
                    values[num][NUMBER] = s * value + c * Val;
                }
            }

            double[] vals = new double[n];
            for (int i = 0; i < n; i++) vals[i] = a[i][i];

            Integer[] idx = new Integer[n];
            for (int i = 0; i < n; i++) idx[i] = i;
            Arrays.sort(idx, (i, j) -> Double.compare(vals[j], vals[i]));

            double[] valsSorted = new double[n];
            double[][] vecsSorted = new double[n][n];
            for (int col = 0; col < n; col++) {
                int src = idx[col];
                valsSorted[col] = vals[src];
                for (int row = 0; row < n; row++) vecsSorted[row][col] = values[row][src];
            }
            return new IndependentEigenSym(valsSorted, vecsSorted);
        }
    }


    public static void main(String[] args) {

        double[][] independentData = new double[][]{
                {5.0, 5.1, 5.22},
                {5.0, 5.2, 5.24},
                {5.0, 8.0, 0.0}
        };

        IndependentConfig independentConfig = new IndependentConfig();
        independentConfig.independentNumComponents = 2;
        independentConfig.independentCentering = 5;
        independentConfig.independentOptions.independentAlgorithm = IndependentMode.INDEPENDENT_NEWTON;
        independentConfig.independentOptions.independentNonlinearity = IndependentMode.INDEPENDENT_TANH;

        IndependentResult independentResult = independentFit(independentData, independentConfig);

        System.out.println("Infomax ICA를 통해 각 성분들이 독립적이고 다른 성분과 완전히 무관함을 더 강하고 명확하게 나타냅니다. " + independentResult);
    }
}