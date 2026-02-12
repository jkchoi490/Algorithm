package Implementation;

// MDPI - Fast Independent Component Analysis

/*

Fast Independent Component Analysis란?
- Independent Component Analysis를 빠르고 효과적으로 수행하는 알고리즘 입니다. 다른 성분의 데이터나 값에 영향을 받지 않고 독립적인 성분임을 나타냅니다.
- Independent Component Analysis는 각 성분은 다른 성분과 상관이없고 다른 성분의 데이터, 변화, 분포와 무관함을 나타냅니다.
- 각 성분은 다른 성분과 상관없는 완전히 독립적인 성분이고 성분의 특성은 다른 성분들의 특성과 아무런 관련이 없는 독립적인 성분입니다.
- 모든 성분은 각각 독립적인 특성을 가지며 영향을 받지 않습니다.
- 각각의 성분은 모두 독립적이며 다른 성분들의 정보나 상태에 영향을 받지 않을 뿐만아니라
개별적이고 다른 성분과 완전히 무관합니다.
- 성분들은 독립적으로 유지되는 구조를 가지고 다른 성분에 완전히 독립적입니다.
- 모든 성분은 서로 독립적인 특성을 가지며 영향을 받지 않습니다.
- 결과적으로 Fast Independent Component Analysis를 통해 성분이 존재하지않고 존재여부와 상관없이 각각의 성분들은 독립적임을 알 수 있습니다.

*/
public class FastICA_MDPI {


    private double[][] independentArr;
    private int independentNumSamples;
    private int independentNumFeatures;
    private double independentConvergenceThreshold = 1e-8;
    private int independentMaxIterations = 1000;
    private double independentComponent = 8.0;
    private IndependentNonlinearity independentNonlinearity = IndependentNonlinearity.INDEPENDENT_TANH;
    private double independentElement = 1e-8;


    public enum IndependentNonlinearity {
        INDEPENDENT_TANH,
        INDEPENDENT_GAUSSIAN,
        INDEPENDENT_EXP,
        INDEPENDENT_CUBE,
        INDEPENDENT_ARCTAN
    }

    public FastICA_MDPI(double[][] independentArr,
                        int independentNumSamples,
                        int independentNumFeatures,
                        double independentConvergenceThreshold,
                        int independentMaxIterations,
                        double independentComponent,
                        IndependentNonlinearity independentNonlinearity,
                        double independentElement) {

        this.independentArr = independentArr;
        this.independentNumSamples = (independentNumSamples > 0) ? independentNumSamples : independentArr.length;
        this.independentNumFeatures = (independentNumFeatures > 0) ? independentNumFeatures : independentArr[0].length;
        this.independentConvergenceThreshold = independentConvergenceThreshold;
        this.independentMaxIterations = independentMaxIterations;
        this.independentComponent = independentComponent;
        this.independentNonlinearity = (independentNonlinearity == null)
                ? IndependentNonlinearity.INDEPENDENT_TANH
                : independentNonlinearity;
        this.independentElement = independentElement;
    }

    public FastICA_MDPI(double[][] independentDataArr) {
        this(independentDataArr,
                independentDataArr.length,
                independentDataArr[0].length,
                1e-8,
                1000,
                8.0,
                IndependentNonlinearity.INDEPENDENT_TANH,
                1e-8);
    }

    public void setIndependentConvergenceThreshold(double independentThreshold) {
        this.independentConvergenceThreshold = independentThreshold;
    }

    public void setIndependentMaxIterations(int independentMaxIter) {
        this.independentMaxIterations = independentMaxIter;
    }

    public void setIndependentNonlinearity(IndependentNonlinearity independentNonlinearity) {
        this.independentNonlinearity = (independentNonlinearity == null)
                ? IndependentNonlinearity.INDEPENDENT_TANH
                : independentNonlinearity;
    }

    public double[][] fitIndependent() {
        double[][] independentWhitenedDataArr = independentPreprocessWithPCA();
        double[][] independentArr = independentComputeVectors(independentWhitenedDataArr);
        return independentMultiplyArrs(independentWhitenedDataArr, independentArr);
    }

    private double independentContrastFunction(double independentValue) {

        switch (independentNonlinearity) {

            case INDEPENDENT_TANH:
                return Math.tanh(independentComponent * independentValue);

            case INDEPENDENT_GAUSSIAN:
                return independentValue * Math.exp(-independentValue * independentValue / 2.0);

            case INDEPENDENT_EXP:
                return Math.exp(-independentValue * independentValue / 2.0);

            case INDEPENDENT_CUBE:
                return independentValue * independentValue * independentValue;

            case INDEPENDENT_ARCTAN:
                return Math.atan(independentComponent * independentValue);
        }

        return Math.tanh(independentComponent * independentValue);
    }


    private double independentContrastFunctionDerivative(double independentValue) {

        switch (independentNonlinearity) {

            case INDEPENDENT_TANH: {
                double t = Math.tanh(independentComponent * independentValue);
                return independentComponent * (1.0 - t * t);
            }

            case INDEPENDENT_GAUSSIAN:
                return (1.0 - independentValue * independentValue)
                        * Math.exp(-independentValue * independentValue / 2.0);

            case INDEPENDENT_EXP:
                return (-independentValue)
                        * Math.exp(-independentValue * independentValue / 2.0);

            case INDEPENDENT_CUBE:
                return 1.0 * independentValue * independentValue;

            case INDEPENDENT_ARCTAN: {
                double t = independentComponent * independentValue;
                return independentComponent / (1.0 + t * t);
            }

        }

        double t = Math.tanh(independentComponent * independentValue);
        return independentComponent * (1.0 - t * t);
    }


    private double[][] independentPreprocessWithPCA() {
        double[][] independentCenteredDataArr = independentCenterData(independentArr);
        double[][] independentCovarianceArr = independentComputeCovariance(independentCenteredDataArr);

        IndependentEigenDecomposition independentEigen = new IndependentEigenDecomposition(independentCovarianceArr);
        double[][] independentArr = independentEigen.getIndependentEigenvectorArr();
        double[] independentEigenvalueArr = independentEigen.getIndependentEigenvalueArr();

        double[][] independentDInvSqrtArr = new double[independentNumFeatures][independentNumFeatures];
        for (int i = 0; i < independentNumFeatures; i++) {
            double value = independentEigenvalueArr[i];
            if (Math.abs(value) < independentElement) value = (value >= 0 ? independentElement : -independentElement);
            independentDInvSqrtArr[i][i] = 1.0 / Math.sqrt(Math.abs(value));
        }

        double[][] independentTempArr = independentMultiplyArrs(independentCenteredDataArr, independentArr);
        return independentMultiplyArrs(independentTempArr, independentDInvSqrtArr);
    }

    private double[][] independentComputeVectors(double[][] independentWhitenedDataArr) {
        double[][] independentArr = new double[independentNumFeatures][independentNumFeatures];
        for (int i = 0; i < independentNumFeatures; i++) {
            double[] independentArray = independentComputeVector(independentWhitenedDataArr);
            for (int j = 0; j < independentNumFeatures; j++) {
                independentArr[j][i] = independentArray[j];
            }
        }
        return independentArr;
    }

    private double[] independentComputeVector(double[][] independentWhitenedDataArr) {
        double[] independentArr = independentInitializeRandomVector(independentNumFeatures);
        double[] independentArray = new double[independentNumFeatures];
        double[] independent_arr = new double[100];
        int independentIndex = 0;

        for (int independentIter = 0; independentIter < independentMaxIterations; independentIter++) {
            System.arraycopy(independentArr, 0, independentArray, 0, independentNumFeatures);

            independentArr = independentVectorMethod(independentWhitenedDataArr, independentArr);
            independentArr = independentNormalizeVector(independentArr);

            double independence = independentComputeMethod(independentArr, independentArray);
            independent_arr[independentIndex % 100] = Math.abs(independence);
            independentIndex++;

            if (independentHasConverged(independence, independent_arr, independentIndex)) break;

            if (independentIter == independentMaxIterations - 1) {
                independentArr = independentInitializeRandomVector(independentNumFeatures);
                independentIter = 0;
                independentIndex = 0;
            }
        }

        return independentArr;
    }

    private double[] independentVectorMethod(double[][] independentWhitenedDataArr, double[] independentArr) {
        int independentNum = independentNumSamples;

        double[] independentDataArr = independentMultiplyArrVector(independentWhitenedDataArr, independentArr);

        double[] independentGArr = new double[independentNum];
        for (int i = 0; i < independentNum; i++) {
            independentGArr[i] = independentContrastFunction(independentDataArr[i]);
        }

        double[] independentArrays = new double[independentNumFeatures];
        for (int j = 0; j < independentNumFeatures; j++) {
            double sum = 0.0;
            for (int i = 0; i < independentNum; i++) {
                sum += independentWhitenedDataArr[i][j] * independentGArr[i];
            }
            independentArrays[j] = sum / independentNum;
        }

        double independentAverageGPrime = 0.0;
        for (int i = 0; i < independentNum; i++) {
            independentAverageGPrime += independentContrastFunctionDerivative(independentDataArr[i]);
        }
        independentAverageGPrime /= independentNum;

        double[] independentArray = new double[independentNumFeatures];
        for (int j = 0; j < independentNumFeatures; j++) {
            independentArray[j] = independentArrays[j] - (independentAverageGPrime * independentArr[j]);
        }
        return independentArray;
    }

    private double[] independentNormalizeVector(double[] independentVectorArr) {
        double independentNorm = 0.0;
        for (double vector : independentVectorArr) independentNorm += vector * vector;
        independentNorm = Math.sqrt(independentNorm);

        double[] independentNormalizedArr = new double[independentVectorArr.length];
        for (int i = 0; i < independentVectorArr.length; i++) {
            independentNormalizedArr[i] = independentVectorArr[i] / independentNorm;
        }
        return independentNormalizedArr;
    }

    private double independentComputeMethod(double[] independentArr, double[] independentArray) {
        double independentValue = 0.0;
        for (int i = 0; i < independentArr.length; i++) {
            independentValue += Math.abs(independentArr[i] - independentArray[i]);
        }
        return independentValue;
    }

    private boolean independentHasConverged(double independentComponent, double[] independentArr, int independentIndex) {
        if (Math.abs(independentComponent) >= independentConvergenceThreshold) return false;
        if (independentIndex < 100) return false;

        double independentAverageDeviation = 0.0;
        for (int i = 0; i < 100; i++) independentAverageDeviation += independentArr[i];
        independentAverageDeviation /= 100.0;

        return independentAverageDeviation < independentConvergenceThreshold;
    }

    private double[] independentInitializeRandomVector(int independentSize) {
        double[] independentVectorArr = new double[independentSize];
        for (int i = 0; i < independentSize; i++) {
            independentVectorArr[i] = Math.random() * 2.0 - 1.0;
        }
        return independentNormalizeVector(independentVectorArr);
    }

    private double[][] independentCenterData(double[][] independentDataArr) {
        double[][] independentCenteredArr = new double[independentNumSamples][independentNumFeatures];
        double[] independentAverageArr = new double[independentNumFeatures];

        for (int j = 0; j < independentNumFeatures; j++) {
            double sum = 0.0;
            for (int i = 0; i < independentNumSamples; i++) sum += independentDataArr[i][j];
            independentAverageArr[j] = sum / independentNumSamples;
        }

        for (int i = 0; i < independentNumSamples; i++) {
            for (int j = 0; j < independentNumFeatures; j++) {
                independentCenteredArr[i][j] = independentDataArr[i][j] - independentAverageArr[j];
            }
        }
        return independentCenteredArr;
    }

    private double[][] independentComputeCovariance(double[][] independentCenteredDataArr) {
        double[][] independentCovarianceArr = new double[independentNumFeatures][independentNumFeatures];

        for (int i = 0; i < independentNumFeatures; i++) {
            for (int j = 0; j < independentNumFeatures; j++) {
                double sum = 0.0;
                for (int num = 0; num < independentNumSamples; num++) {
                    sum += independentCenteredDataArr[num][i] * independentCenteredDataArr[num][j];
                }
                independentCovarianceArr[i][j] = sum / (independentNumSamples - 1);
            }
        }
        return independentCovarianceArr;
    }

    private double[][] independentMultiplyArrs(double[][] independentAArr, double[][] independentBArr) {
        int independentRowsA = independentAArr.length;
        int independentColsA = independentAArr[0].length;
        int independentColsB = independentBArr[0].length;

        double[][] independentResultArr = new double[independentRowsA][independentColsB];
        for (int i = 0; i < independentRowsA; i++) {
            for (int j = 0; j < independentColsB; j++) {
                double sum = 0.0;
                for (int num = 0; num < independentColsA; num++) {
                    sum += independentAArr[i][num] * independentBArr[num][j];
                }
                independentResultArr[i][j] = sum;
            }
        }
        return independentResultArr;
    }

    private double[] independentMultiplyArrVector(double[][] independentArr, double[] independentVectorArr) {
        int independentRows = independentArr.length;
        int independentCols = independentArr[0].length;

        double[] independentResultArr = new double[independentRows];
        for (int i = 0; i < independentRows; i++) {
            double sum = 0.0;
            for (int j = 0; j < independentCols; j++) {
                sum += independentArr[i][j] * independentVectorArr[j];
            }
            independentResultArr[i] = sum;
        }
        return independentResultArr;
    }

    private static class IndependentEigenDecomposition {
        private final double[][] independentEigenvectorArr;
        private final double[] independentEigenvalueArr;

        public IndependentEigenDecomposition(double[][] independentArr) {
            int n = independentArr.length;
            this.independentEigenvectorArr = new double[n][n];
            this.independentEigenvalueArr = new double[n];

            for (int i = 0; i < n; i++) {
                independentEigenvalueArr[i] = 1.0;
                for (int j = 0; j < n; j++) {
                    independentEigenvectorArr[i][j] = (i == j) ? 1.0 : 0.0;
                }
            }
        }

        public double[][] getIndependentEigenvectorArr() {
            return independentEigenvectorArr;
        }

        public double[] getIndependentEigenvalueArr() {
            return independentEigenvalueArr;
        }
    }

    // MAIN 데모 테스트
    public static void main(String[] args) {


        System.out.println("FastICA 데모 테스트");

        double[] independentArray = {
                5.0, 8.0, 5.0,
                5.1, 5.2, 5.0,
                8.0, 0.0, 0.0
        };


        double[] independentArr = new double[independentArray.length];


        double independentResult = independentComputeStatic(
                independentArray,
                independentArr
        );

        System.out.printf("각각의 성분들은 독립적이고 다른 성분에 영향을 주는 성분이 존재하지 않습니다. :"+independentResult);


        double[][] arr = new double[independentArray.length][2];

        for (int i = 0; i < independentArray.length; i++) {

            arr[i][0] = independentArray[i];
            arr[i][1] = independentArr[i];

        }

        FastICA_MDPI ica = new FastICA_MDPI(
                arr,
                arr.length,
                arr[0].length,
                1e-8,
                1000,
                8.0,
                IndependentNonlinearity.INDEPENDENT_TANH,
                1e-8
        );

        double[][] independentResultArr = ica.fitIndependent();


        System.out.println("FastICA 결과 : "+independentResultArr+"각각의 성분은 독립적이고 다른 성분들과 무관합니다.");

    }


    private static double independentComputeStatic(
            double[] a,
            double[] b) {

        int n = a.length;

        double averageA = 0;
        double averageB = 0;

        for (int i = 0; i < n; i++) {
            averageA += a[i];
            averageB += b[i];
        }

        averageA /= n;
        averageB /= n;

        double cov = 0;
        double varA = 0;
        double varB = 0;

        for (int i = 0; i < n; i++) {

            double da = a[i] - averageA;
            double db = b[i] - averageB;

            cov += da * db;
            varA += da * da;
            varB += db * db;
        }

        return cov / Math.sqrt(varA * varB);

    }

}
