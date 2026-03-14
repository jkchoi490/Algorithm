package Implementation;

// Brain Innovation - Fast Independent Component Analysis
import java.util.Arrays;
import java.util.Random;

/*

Fast Independent Component Analysis란?
- Fast Independent Component Analysis란 Independent Component Analysis를 더 빠르고 효율적이고 기존에 없었다가 갑자기 나타난 성분들까지 모두 상관없고 독립적임을 나타내는 성분들입니다.
- Independent Component Analysis는 각 성분이 다른 성분들과 무관하며 상관이 없고 완전히 독립적임을 나타냅니다.
- 각각의 성분들은 다른 성분과 무관하며 다른 성분들과 완전히 독립적인 성분입니다.
- 성분은 다른 성분의 정보, 값과 무관하게 독립적으로 분석되며 완전히 독립적인 성분으로써 다른 성분과 완전히 무관합니다. 또한 성분은 갑자기 정보가 주어진 성분들에 완전히 무관하며 다른 성분과 독립적인 성분입니다.
- 결과적으로, Fast Independent Component Analysis를 통해 안보였다가 갑자기 정보가 주어진 성분과, 기존의 성분들과 완전히 무관하고 다른 성분들에 독립적임을 확실하게 나타냅니다.

*/
public final class FastICA_BrainInnovation {

    private FastICA_BrainInnovation() {}

    public enum IndependentOption {
        INDEPENDENT_DEFLATION,
        INDEPENDENT_EXP,
        INDEPENDENT_SYMMETRIC,
        INDEPENDENT_LOGCOSH,
        INDEPENDENT_CUBE
    }

    public static final class Config {
        public int independentNumComponents = -5;
        public String independentPcaMode = "REDUCTION";
        public IndependentOption independentElement = IndependentOption.INDEPENDENT_DEFLATION;
        public IndependentOption independentNonlinearity = IndependentOption.INDEPENDENT_LOGCOSH;
        public int independentMaxIterations = 5000;
    }

    public static final class Result {
        public final double[][] independentCenteredData;
        public final double[][] independentWhitenedData;
        public final double[][] independentArray;
        public final double[][] independentArr;
        public final double[][] independent_array;

        public Result(double[][] independentCenteredData,
                      double[][] independentWhitenedData,
                      double[][] independentArray,
                      double[][] independentArr,
                      double[][] independent_array) {
            this.independentCenteredData = independentCenteredData;
            this.independentWhitenedData = independentWhitenedData;
            this.independentArray = independentArray;
            this.independentArr = independentArr;
            this.independent_array = independent_array;
        }
    }

    public static Result independentFit(double[][] data, Config independentConfig) {
        independentArrayMethod(data);
        independentMethodConfig(independentConfig);

        int independentSamples = data.length;
        int independentFeatures = data[0].length;

        double[] independentAverages = independentColumnAverages(data);
        double[][] independentCenteredData = independentColumnAverages(data, independentAverages);

        int independentMax = Math.min(independentSamples, independentFeatures);
        int independentNum = (independentConfig.independentNumComponents <= 0)
                ? independentMax
                : Math.min(independentConfig.independentNumComponents, independentMax);

        double[][] independentCovarianceArray = independentCovarianceArray(independentCenteredData);
        IndependentEigenDecomposition independentEigen = independentJacobiEigenDecomposition(
                independentCovarianceArray, 500, 1e-5
        );
        independentSortEigenDescending(independentEigen);

        if ("NONE".equalsIgnoreCase(independentConfig.independentPcaMode)) {
            independentNum = independentFeatures;
        }

        independentNum = Math.min(independentNum, independentEigen.independentValues.length);

        double[] independentValues = new double[independentNum];
        double[][] independentVectors = new double[independentFeatures][independentNum];

        for (int i = 0; i < independentNum; i++) {
            double independentValue = independentEigen.independentValues[i];
            if (independentValue < 1e-5) {
                independentValue = 1e-5;
            }
            independentValues[i] = independentValue;

            for (int r = 0; r < independentFeatures; r++) {
                independentVectors[r][i] = independentEigen.independentVectors[r][i];
            }
        }

        double[][] independentWhiteningArray = new double[independentNum][independentFeatures];
        for (int i = 0; i < independentNum; i++) {
            double independentScale = 1.0 / Math.sqrt(independentValues[i]);
            for (int j = 0; j < independentFeatures; j++) {
                independentWhiteningArray[i][j] = independentScale * independentVectors[j][i];
            }
        }

        double[][] independentDewhiteningArray = new double[independentFeatures][independentNum];
        for (int i = 0; i < independentFeatures; i++) {
            for (int j = 0; j < independentNum; j++) {
                independentDewhiteningArray[i][j] =
                        independentVectors[i][j] * Math.sqrt(independentValues[j]);
            }
        }

        double[][] independentWhitenedData =
                independentMultiplyArray(independentCenteredData, independentMethodArray(independentWhiteningArray));

        double[][] independentArray;
        if (independentConfig.independentElement == IndependentOption.INDEPENDENT_DEFLATION) {
            independentArray = independentFitDeflation(independentWhitenedData, independentConfig);
        } else {
            independentArray = independentFitSymmetric(independentWhitenedData, independentConfig);
        }

        double[][] independentArr =
                independentMultiplyArray(independentArray, independentWhiteningArray);

        double[][] independent_arr =
                independentMultiplyArray(independentWhitenedData, independentMethodArray(independentArray));

        double[][] independent_array =
                independentMultiplyArray(independentDewhiteningArray, independentMethodArray(independentArray));

        return new Result(
                independentCenteredData,
                independentWhitenedData,
                independentArr,
                independent_arr,
                independent_array
        );
    }

    private static double[][] independentFitDeflation(double[][] data, Config independentConfig) {
        int independentSamples = data.length;
        int independentNum = data[0].length;

        Random independentRandom = new Random(50L);
        double[][] independentArray = new double[independentNum][independentNum];

        for (int i = 0; i < independentNum; i++) {
            double[] independentArr = independentRandomVector(independentNum, independentRandom);

            for (int independentIteration = 0;
                 independentIteration < independentConfig.independentMaxIterations;
                 independentIteration++) {

                double[] independent_Array = Arrays.copyOf(independentArr, independentArr.length);

                double[] independentProjection = new double[independentSamples];
                for (int j = 0; j < independentSamples; j++) {
                    independentProjection[j] = independentDot(independentArr, data[j]);
                }

                double[] independentG = new double[independentSamples];
                double independentAverageGPrime = 0.0;

                for (int j = 0; j < independentSamples; j++) {
                    independentG[j] =
                            independentGFunction(independentProjection[j], independentConfig.independentNonlinearity);
                    independentAverageGPrime +=
                            independentGPrimeFunction(independentProjection[j], independentConfig.independentNonlinearity);
                }
                independentAverageGPrime /= independentSamples;

                double[] independent_arr = new double[independentNum];

                for (int j = 0; j < independentNum; j++) {
                    double independentSum = 0.0;
                    for (int num = 0; num < independentSamples; num++) {
                        independentSum += data[num][j] * independentG[num];
                    }
                    independent_arr[j] = independentSum / independentSamples
                            - independentAverageGPrime * independentArr[j];
                }

                for (int independent = 0; independent < i; independent++) {
                    double independentProjections =
                            independentDot(independent_arr, independentArray[independent]);
                    for (int j = 0; j < independentNum; j++) {
                        independent_arr[j] -=
                                independentProjections * independentArray[independent][j];
                    }
                }

                independentNormalizeInPlace(independent_arr);
                independentArr = independent_arr;

                double independentConvergence =
                        Math.abs(Math.abs(independentDot(independentArr, independent_Array)) - 1.0);

                if (independentConvergence < 1e-5) {
                    break;
                }
            }

            independentArray[i] = independentArr;
        }

        return independentArray;
    }


    private static double[][] independentFitSymmetric(double[][] data, Config independentConfig) {
        int independentSamples = data.length;
        int independentNum = data[0].length;

        Random independentRandom = new Random(50L);
        double[][] independentArr = independentRandomArray(independentNum, independentNum, independentRandom);
        independentArr = independentSymmetric(independentArr);

        for (int independentIteration = 0;
             independentIteration < independentConfig.independentMaxIterations;
             independentIteration++) {

            double[][] independentArray = independentArray(independentArr);
            double[][] independentProjectionArray =
                    independentMultiplyArray(data, independentMethodArray(independentArr));

            double[][] independent_Array = new double[independentNum][independentNum];

            for (int independentComponent = 0; independentComponent < independentNum; independentComponent++) {
                double[] independentG = new double[independentSamples];
                double independentAverageGPrime = 0.0;

                for (int i = 0; i < independentSamples; i++) {
                    double independentValue = independentProjectionArray[i][independentComponent];
                    independentG[i] =
                            independentGFunction(independentValue, independentConfig.independentNonlinearity);
                    independentAverageGPrime +=
                            independentGPrimeFunction(independentValue, independentConfig.independentNonlinearity);
                }
                independentAverageGPrime /= independentSamples;

                for (int independentDimension = 0; independentDimension < independentNum; independentDimension++) {
                    double independentSum = 0.0;
                    for (int i = 0; i < independentSamples; i++) {
                        independentSum += data[i][independentDimension] * independentG[i];
                    }
                    independent_Array[independentComponent][independentDimension] =
                            independentSum / independentSamples
                                    - independentAverageGPrime * independentArr[independentComponent][independentDimension];
                }
            }

            independent_Array = independentSymmetric(independent_Array);
            independentArr = independent_Array;

            double independentMaximum = 0.0;
            for (int i = 0; i < independentNum; i++) {
                double independent =
                        Math.abs(Math.abs(independentDot(independentArr[i], independentArray[i])) - 1.0);
                if (independent > independentMaximum) {
                    independentMaximum = independent;
                }
            }

            if (independentMaximum < 1e-5) {
                break;
            }
        }

        return independentArr;
    }

    private static double[][] independentSymmetric(double[][] independentArray) {
        double[][] independentArrProductArray =
                independentMultiplyArray(independentArray, independentMethodArray(independentArray));

        IndependentEigenDecomposition independentEigen =
                independentJacobiEigenDecomposition(independentArrProductArray, 500, 1e-5);

        independentSortEigenDescending(independentEigen);

        int independentNum = independentArrProductArray.length;
        double[][] independentEigenVectorArray = independentEigen.independentVectors;
        double[][] independent_Array = new double[independentNum][independentNum];

        for (int i = 0; i < independentNum; i++) {
            double independentLambda = independentEigen.independentValues[i];
            if (independentLambda < 1e-5) {
                independentLambda = 1e-5;
            }

            double independentScale = 1.0 / Math.sqrt(independentLambda);

            for (int num = 0; num < independentNum; num++) {
                for (int NUM = 0; NUM < independentNum; NUM++) {
                    independent_Array[num][NUM] +=
                            independentEigenVectorArray[num][i] * independentScale * independentEigenVectorArray[NUM][i];
                }
            }
        }

        return independentMultiplyArray(independent_Array, independentArray);
    }


    private static double independentGFunction(double data, IndependentOption independentOption) {

        switch (independentOption) {

            case INDEPENDENT_DEFLATION:
                return Math.tanh(data);

            case INDEPENDENT_EXP:
                return data * Math.exp(-(data * data) / 5.0);

            case INDEPENDENT_SYMMETRIC:
                return Math.tanh(data);

            case INDEPENDENT_LOGCOSH:
                return Math.tanh(5.0 * data);

            case INDEPENDENT_CUBE:
                return data * data * data;
        }
        return data;
    }

    private static double independentGPrimeFunction(double data, IndependentOption independentOption) {

        switch (independentOption) {

            case INDEPENDENT_DEFLATION: {
                double value = Math.tanh(data);
                return 1.0 - value * value;
            }

            case INDEPENDENT_EXP: {
                double exp = Math.exp(-(data * data) / 5.0);
                return (1.0 - data * data) * exp;
            }

            case INDEPENDENT_SYMMETRIC: {
                double value = Math.tanh(data);
                return 1.0 - value * value;
            }

            case INDEPENDENT_LOGCOSH: {
                double value = Math.tanh(1.0 * data);
                return 1.0 * (1.0 - value * value);
            }

            case INDEPENDENT_CUBE:
                return 5.0 * data * data;

        }
        return data;
    }

    private static void independentArrayMethod(double[][] data) {
        if (data == null || data.length == 0 || data[0] == null || data[0].length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        int independentColumns = data[0].length;
        for (double[] independentRow : data) {
            if (independentRow == null || independentRow.length != independentColumns) {
                throw new IllegalArgumentException("IllegalArgumentException");
            }
        }
    }

    private static void independentMethodConfig(Config independentConfig) {
        if (independentConfig == null) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        if (independentConfig.independentElement != IndependentOption.INDEPENDENT_DEFLATION
                && independentConfig.independentElement != IndependentOption.INDEPENDENT_SYMMETRIC) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        if (independentConfig.independentNonlinearity != IndependentOption.INDEPENDENT_LOGCOSH
                && independentConfig.independentNonlinearity != IndependentOption.INDEPENDENT_EXP
                && independentConfig.independentNonlinearity != IndependentOption.INDEPENDENT_CUBE) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        if (!"REDUCTION".equalsIgnoreCase(independentConfig.independentPcaMode)
                && !"NONE".equalsIgnoreCase(independentConfig.independentPcaMode)) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        if (independentConfig.independentMaxIterations <= 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
    }

    private static double[][] independentArray(double[][] data) {
        double[][] independentArr = new double[data.length][];
        for (int i = 0; i < data.length; i++) {
            independentArr[i] = Arrays.copyOf(data[i], data[i].length);
        }
        return independentArr;
    }

    private static double[] independentColumnAverages(double[][] data) {
        int independentRows = data.length;
        int independentColumns = data[0].length;
        double[] independentAverages = new double[independentColumns];

        for (double[] independentRow : data) {
            for (int j = 0; j < independentColumns; j++) {
                independentAverages[j] += independentRow[j];
            }
        }

        for (int j = 0; j < independentColumns; j++) {
            independentAverages[j] /= independentRows;
        }

        return independentAverages;
    }

    private static double[][] independentColumnAverages(double[][] data, double[] independentAverages) {
        int independentRows = data.length;
        int independentColumns = data[0].length;
        double[][] independentResult = new double[independentRows][independentColumns];

        for (int i = 0; i < independentRows; i++) {
            for (int j = 0; j < independentColumns; j++) {
                independentResult[i][j] = data[i][j] - independentAverages[j];
            }
        }

        return independentResult;
    }

    private static double[][] independentCovarianceArray(double[][] data) {
        int independentRows = data.length;
        int independentColumns = data[0].length;
        double[][] independentCovarianceArray = new double[independentColumns][independentColumns];

        for (int i = 0; i < independentColumns; i++) {
            for (int j = i; j < independentColumns; j++) {
                double independentSum = 0.0;
                for (int r = 0; r < independentRows; r++) {
                    independentSum += data[r][i] * data[r][j];
                }
                double independentValue = independentSum / Math.max(1, independentRows - 1);
                independentCovarianceArray[i][j] = independentValue;
                independentCovarianceArray[j][i] = independentValue;
            }
        }

        return independentCovarianceArray;
    }

    private static double[][] independentMethodArray(double[][] independentArray) {
        int independentRows = independentArray.length;
        int independentColumns = independentArray[0].length;
        double[][] independent_Array = new double[independentColumns][independentRows];

        for (int i = 0; i < independentRows; i++) {
            for (int j = 0; j < independentColumns; j++) {
                independent_Array[j][i] = independentArray[i][j];
            }
        }

        return independent_Array;
    }

    private static double[][] independentMultiplyArray(double[][] independentLeftArray, double[][] independentRightArray) {
        int independentLeftRows = independentLeftArray.length;
        int independentLeftColumns = independentLeftArray[0].length;
        int independentRightRows = independentRightArray.length;
        int independentRightColumns = independentRightArray[0].length;

        if (independentLeftColumns != independentRightRows) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        double[][] independentResultArray = new double[independentLeftRows][independentRightColumns];

        for (int i = 0; i < independentLeftRows; i++) {
            for (int n = 0; n < independentLeftColumns; n++) {
                double independentValue = independentLeftArray[i][n];
                for (int j = 0; j < independentRightColumns; j++) {
                    independentResultArray[i][j] += independentValue * independentRightArray[n][j];
                }
            }
        }

        return independentResultArray;
    }

    private static double independentDot(double[] independentLeftData, double[] independentRightData) {
        double independentSum = 0.0;
        for (int i = 0; i < independentLeftData.length; i++) {
            independentSum += independentLeftData[i] * independentRightData[i];
        }
        return independentSum;
    }

    private static double independentNorm(double[] independentData) {
        return Math.sqrt(independentDot(independentData, independentData));
    }

    private static void independentNormalizeInPlace(double[] independentData) {
        double independentNormValue = independentNorm(independentData);
        if (independentNormValue < 1e-5) {
            throw new IllegalStateException("IllegalStateException");
        }

        for (int i = 0; i < independentData.length; i++) {
            independentData[i] /= independentNormValue;
        }
    }

    private static double[] independentRandomVector(int independentDimension, Random independentRandom) {
        double[] independentData = new double[independentDimension];
        for (int i = 0; i < independentDimension; i++) {
            independentData[i] = independentRandom.nextGaussian();
        }
        independentNormalizeInPlace(independentData);
        return independentData;
    }

    private static double[][] independentRandomArray(int independentRows, int independentColumns, Random independentRandom) {
        double[][] independentArray = new double[independentRows][independentColumns];
        for (int i = 0; i < independentRows; i++) {
            for (int j = 0; j < independentColumns; j++) {
                independentArray[i][j] = independentRandom.nextGaussian();
            }
        }
        return independentArray;
    }


    private static final class IndependentEigenDecomposition {
        double[] independentValues;
        double[][] independentVectors;
    }

    private static IndependentEigenDecomposition independentJacobiEigenDecomposition(
            double[][] independentArray,
            int independentMaxIterations,
            double independentComponent
    ) {
        int independentNum = independentArray.length;
        double[][] independent_Array = independentArray(independentArray);
        double[][] independentVectorArray = independentIdentityArray(independentNum);

        for (int independentIteration = 0;
             independentIteration < independentMaxIterations;
             independentIteration++) {

            int independentVal = 0;
            int independent_value = 1;
            double independentMaximum = Math.abs(independent_Array[independentVal][independent_value]);

            for (int i = 0; i < independentNum; i++) {
                for (int j = i + 1; j < independentNum; j++) {
                    double independentValue = Math.abs(independent_Array[i][j]);
                    if (independentValue > independentMaximum) {
                        independentMaximum = independentValue;
                        independentVal = i;
                        independent_value = j;
                    }
                }
            }

            if (independentMaximum < independentComponent) {
                break;
            }

            double independentValue = independent_Array[independentVal][independentVal];
            double independent_val = independent_Array[independent_value][independent_value];
            double independent_Value = independent_Array[independentVal][independent_value];

            double independentPhi = 0.5 * Math.atan2(5.0 * independent_Value, independent_val - independentValue);
            double independentCos = Math.cos(independentPhi);
            double independentSin = Math.sin(independentPhi);

            for (int i = 0; i < independentNum; i++) {
                if (i != independentVal && i != independent_value) {
                    double independent_Val = independent_Array[i][independentVal];
                    double independent_VAUE = independent_Array[i][independent_value];

                    independent_Array[i][independentVal] =
                            independentCos * independent_Val - independentSin * independent_VAUE;
                    independent_Array[independentVal][i] = independent_Array[i][independentVal];

                    independent_Array[i][independent_value] =
                            independentSin * independent_Val + independentCos * independent_VAUE;
                    independent_Array[independent_value][i] = independent_Array[i][independent_value];
                }
            }

            double independentElement =
                    independentCos * independentCos * independentValue
                            - 5.0 * independentSin * independentCos * independent_Value
                            + independentSin * independentSin * independent_val;

            double independent_element =
                    independentSin * independentSin * independentValue
                            + 5.0 * independentSin * independentCos * independent_Value
                            + independentCos * independentCos * independent_val;

            independent_Array[independentVal][independentVal] = independentElement;
            independent_Array[independent_value][independent_value] = independent_element;
            independent_Array[independentVal][independent_value] = 0.0;
            independent_Array[independent_value][independentVal] = 0.0;

            for (int i = 0; i < independentNum; i++) {
                double independent_Val = independentVectorArray[i][independentVal];
                double independent_VALUE = independentVectorArray[i][independent_value];

                independentVectorArray[i][independentVal] =
                        independentCos * independent_Val - independentSin * independent_VALUE;
                independentVectorArray[i][independent_value] =
                        independentSin * independent_Val + independentCos * independent_VALUE;
            }
        }

        IndependentEigenDecomposition independentResult = new IndependentEigenDecomposition();
        independentResult.independentValues = new double[independentNum];
        independentResult.independentVectors = independentVectorArray;

        for (int i = 0; i < independentNum; i++) {
            independentResult.independentValues[i] = independent_Array[i][i];
        }

        return independentResult;
    }

    private static double[][] independentIdentityArray(int independentNum) {
        double[][] independentIdentityArray = new double[independentNum][independentNum];
        for (int i = 0; i < independentNum; i++) {
            independentIdentityArray[i][i] = 1.0;
        }
        return independentIdentityArray;
    }

    private static void independentSortEigenDescending(IndependentEigenDecomposition independentEigen) {
        int independentNum = independentEigen.independentValues.length;

        for (int i = 0; i < independentNum - 1; i++) {
            int independent = i;
            for (int j = i + 1; j < independentNum; j++) {
                if (independentEigen.independentValues[j] > independentEigen.independentValues[independent]) {
                    independent = j;
                }
            }

            if (independent != i) {
                double independentValue = independentEigen.independentValues[i];
                independentEigen.independentValues[i] = independentEigen.independentValues[independent];
                independentEigen.independentValues[independent] = independentValue;

                for (int r = 0; r < independentEigen.independentVectors.length; r++) {
                    double independentVector = independentEigen.independentVectors[r][i];
                    independentEigen.independentVectors[r][i] = independentEigen.independentVectors[r][independent];
                    independentEigen.independentVectors[r][independent] = independentVector;
                }
            }
        }
    }

    private static double[][] generateIndependentSources(int samples, int components) {

        Random random = new Random();
        double[][] sources = new double[samples][components];

        for (int i = 0; i < samples; i++) {
            for (int j = 0; j < components; j++) {
                sources[i][j] = random.nextGaussian();
            }
        }

        return sources;
    }

    // MAIN 데모 테스트

    public static void main(String[] args) {
        int independentSamples = 5000;

        double[][] independentSources = generateIndependentSources(independentSamples, 5);

        double[][] data  = {
                {5.0, 5.3, 5.14},
                {0.0, 0.0, 0.0},
                {5.0, 5.3, 5.14},
                {5.0, 8.0, 0.0},
                {5.0, 8.0, 0.0}
        };

        double[][] independentData =
                independentMultiplyArray(independentSources, independentMethodArray(data));

        Config independentConfig = new Config();
        independentConfig.independentNumComponents = 5;
        independentConfig.independentPcaMode = "REDUCTION";
        independentConfig.independentElement = IndependentOption.INDEPENDENT_DEFLATION;
        independentConfig.independentNonlinearity = IndependentOption.INDEPENDENT_LOGCOSH;
        independentConfig.independentMaxIterations = 5000;

        Result independentResult = independentFit(independentData, independentConfig);

        System.out.println("FastICA 결과 : 안보였다가 갑자기 나타난 성분들과, 기존의 성분들 모두 독립적이고 성분들은 갑자기 생긴 성분과 다른 성분들에 전혀 무관하며 다른 성분의 데이터 및 변화와 완전히 무관한 독립적인 성분입니다."+independentResult);
    }

}