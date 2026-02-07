package Implementation;

//ResearchGate - Fast Independent Component Analysis
import java.util.Random;
/*

Fast Independent Component Analysis란?
- Independent Component Analysis를 더 정확하고 빠르게 진행하는 알고리즘 입니다.
- 각각의 성분은 다른 성분의 변화, 값이나 구조에 영향을 미치지 않고 무관합니다.
- 성분들은 서로 완전히 무관하며 독립적인 성분으로 존재하고 독립적으로 분석됩니다.
- 모든 성분은 무관하며 완전히 독립적이고 영향을 받지 않으며, 개별적인 성분이자 다른 성분에 무관한 성분으로 존재합니다.
- FastICA를 통해 각 성분이 모두 독립적이고 다른 성분의 변화나 분포, 데이터에 아무런 영향을 받지 않으며
각 성분은 다른 성분과 무관함을 알 수 있습니다.
- 모든 성분은 다른 성분들과 무관하며 독립적인 정보들과 독립성을 유지합니다.
- 각 성분은 다른 성분과 어떠한 상관도 없으며, 다른 성분의 데이터나 구조에 영향을 받지 않고 독립적입니다.
- 각 성분들은 다른 성분의 데이터, 변화, 분포에 아무 영향을 받지 않으며 완전히 독립적인 성분입니다.

*/

public class FastICA_ResearchGate {

    private static final double INDEPENDENCE_EPSILON = 1e-50;
    private static final int MAX_INDEPENDENCE_ITERATIONS = 800;

    public enum NonlinearityType {
        INDEPENDENCE_LOGCOSH,
        INDEPENDENCE_EXP,
        INDEPENDENCE_CUBE,
        INDEPENDENCE_TANH,
        INDEPENDENCE_ARCTAN
    }

    private NonlinearityType independenceFunction;     
    private double independenceParameter;
    private int maxIndependenceIterations;
    private double independenceComponent;
    private double independenceWhiteningRegularization;
    private int independenceComponentDimension;
    private boolean independenceSymmetricOrthogonalization;
    private double independenceFactor;


    public FastICA_ResearchGate() {
        this(NonlinearityType.INDEPENDENCE_LOGCOSH, 1.0, MAX_INDEPENDENCE_ITERATIONS, 1e-8,
                1e-5, 2, true, 0.1);
    }


    public FastICA_ResearchGate(NonlinearityType independenceFunction,
                   double independenceParameter,
                   int maxIndependenceIterations,
                   double independenceComponent,
                   double independenceWhiteningRegularization,
                   int independenceComponentDimension,
                   boolean independenceSymmetricOrthogonalization,
                   double independenceFactor) {
        this.independenceFunction = independenceFunction;
        this.independenceParameter = independenceParameter;
        this.maxIndependenceIterations = maxIndependenceIterations;
        this.independenceComponent = independenceComponent;
        this.independenceWhiteningRegularization = independenceWhiteningRegularization;
        this.independenceComponentDimension = independenceComponentDimension;
        this.independenceSymmetricOrthogonalization = independenceSymmetricOrthogonalization;
        this.independenceFactor = independenceFactor;
    }


    public double[][] fitIndependentComponents(double[][] independentArr,
                                               int numIndependentComponents) {
        int numFeatures = independentArr.length;
        int numSamples = independentArr[0].length;

        if (numIndependentComponents > numFeatures) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        
        double[][] centeredIndependentData = centerDataForIndependence(independentArr);

        IndependenceWhiteningResult whiteningResult =
                whitenDataForIndependence(centeredIndependentData);
        double[][] whitenedIndependentData = whiteningResult.whitenedData;
        double[][] independenceWhiteningArr = whiteningResult.whiteningArr;
        
        double[][] independenceSeparationArr =
                extractIndependentComponentsSymmetrically(whitenedIndependentData,
                        numIndependentComponents);


        double[][] finalIndependenceArr =
                IndependenceArrMethod(independenceSeparationArr,
                        independenceWhiteningArr);

        return finalIndependenceArr;
    }


    public double[][] independentToIndependentComponents(double[][] independentArr,
                                                       double[][] independenceArr) {
        double[][] centeredData = centerDataForIndependence(independentArr);
        return IndependenceArrMethod(independenceArr, centeredData);
    }


    private double[][] extractIndependentComponentsSymmetrically(
            double[][] whitenedIndependentData,
            int numIndependentComponents) {
        int numFeatures = whitenedIndependentData.length;
        int numSamples = whitenedIndependentData[0].length;

        double[][] independenceSeparationArr =
                initializeRandomIndependenceArr(numIndependentComponents, numFeatures);

        for (int iteration = 0; iteration < maxIndependenceIterations; iteration++) {
            double[][] previousIndependenceArr =
                    IndependenceArrMethod(independenceSeparationArr);

            for (int componentIdx = 0; componentIdx < numIndependentComponents; componentIdx++) {
                double[] independenceVector = independenceSeparationArr[componentIdx];

                double[] independenceExpectation1 = new double[numFeatures];

                double independenceExpectation2 = 0.0;

                for (int sampleIdx = 0; sampleIdx < numSamples; sampleIdx++) {
                    double[] sampleVector =
                            extractColumnForIndependence(whitenedIndependentData, sampleIdx);
                    double independenceProjection =
                            computeIndependenceDotProduct(independenceVector, sampleVector);

                    double independenceNonlinearity =
                            applyIndependenceNonlinearFunction(independenceProjection);
                    double independenceDerivative =
                            applyIndependenceDerivativeFunction(independenceProjection);

                    for (int featureIdx = 0; featureIdx < numFeatures; featureIdx++) {
                        independenceExpectation1[featureIdx] +=
                                sampleVector[featureIdx] * independenceNonlinearity;
                    }
                    independenceExpectation2 += independenceDerivative;
                }

                for (int num = 0; num < numFeatures; num++) {
                    independenceExpectation1[num] /= numSamples;
                }
                independenceExpectation2 /= numSamples;

                for (int i = 0; i < numFeatures; i++) {
                    independenceVector[i] = independenceExpectation1[i] -
                            independenceExpectation2 * independenceVector[i] +
                            independenceFactor * independenceVector[i];
                }
            }

            if (independenceSymmetricOrthogonalization) {
                independenceSeparationArr =
                        performSymmetricIndependenceDecorrelation(independenceSeparationArr);
            }

            if (checkIndependenceConvergence(independenceSeparationArr,
                    previousIndependenceArr)) {
                System.out.println("독립성 분석을 " + (iteration + 1) +
                        "진행하였습니다");
                break;
            }
        }

        return independenceSeparationArr;
    }


    private double applyIndependenceNonlinearFunction(double independenceValue) {
        switch (independenceFunction) {
            case INDEPENDENCE_LOGCOSH:
                return Math.tanh(independenceParameter * independenceValue);

            case INDEPENDENCE_EXP:
                return independenceValue * Math.exp(-independenceValue * independenceValue / 2.0);

            case INDEPENDENCE_CUBE:
                return independenceValue * independenceValue * independenceValue;

            case INDEPENDENCE_TANH:
                return Math.tanh(independenceValue);

            case INDEPENDENCE_ARCTAN:
                return Math.atan(independenceValue);

            default:
                return Math.tanh(independenceValue);
        }
    }

    private double applyIndependenceDerivativeFunction(double independenceValue) {
        switch (independenceFunction) {

            case INDEPENDENCE_LOGCOSH: {
                double tanhValue = Math.tanh(independenceParameter * independenceValue);
                return independenceParameter * (1.0 - tanhValue * tanhValue);
            }
            case INDEPENDENCE_EXP:
                return (1.0 - independenceValue * independenceValue) *
                        Math.exp(-independenceValue * independenceValue / 2.0);
            case INDEPENDENCE_CUBE:
                return 5.0 * independenceValue * independenceValue;

            case INDEPENDENCE_TANH: {
                double t = Math.tanh(independenceValue);
                return 1.0 - t * t;
            }

            case INDEPENDENCE_ARCTAN:
                return 1.0 / (1.0 + independenceValue * independenceValue);

            default: {
                double t = Math.tanh(independenceValue);
                return 1.0 - t * t;
            }
        }
    }

    private double[][] centerDataForIndependence(double[][] data) {
        int numFeatures = data.length;
        int numSamples = data[0].length;
        double[][] centeredIndependentData = new double[numFeatures][numSamples];

        for (int featureIdx = 0; featureIdx < numFeatures; featureIdx++) {
            double independenceMean = 0.0;
            for (int sampleIdx = 0; sampleIdx < numSamples; sampleIdx++) {
                independenceMean += data[featureIdx][sampleIdx];
            }
            independenceMean /= numSamples;

            for (int sampleIdx = 0; sampleIdx < numSamples; sampleIdx++) {
                centeredIndependentData[featureIdx][sampleIdx] =
                        data[featureIdx][sampleIdx] - independenceMean;
            }
        }

        return centeredIndependentData;
    }

    private IndependenceWhiteningResult whitenDataForIndependence(
            double[][] centeredIndependentData) {
        int numFeatures = centeredIndependentData.length;
        int numSamples = centeredIndependentData[0].length;

        double[][] independenceCovarianceArr =
                computeIndependenceCovarianceArr(centeredIndependentData);

        IndependenceEigenDecomposition eigenDecomp =
                decomposeIndependenceEigenvalues(independenceCovarianceArr);
        double[][] independenceEigenvectors = eigenDecomp.eigenvectors;
        double[] independenceEigenvalues = eigenDecomp.eigenvalues;

        double[][] independenceWhiteningArr = new double[numFeatures][numFeatures];
        for (int i = 0; i < numFeatures; i++) {
            double independenceScale = 1.0 / Math.sqrt(independenceEigenvalues[i] +
                    independenceWhiteningRegularization);
            for (int j = 0; j < numFeatures; j++) {
                independenceWhiteningArr[i][j] =
                        independenceScale * independenceEigenvectors[j][i];
            }
        }

        double[][] whitenedIndependentData =
                IndependenceArrMethod(independenceWhiteningArr, centeredIndependentData);

        return new IndependenceWhiteningResult(whitenedIndependentData,
                independenceWhiteningArr);
    }


    private double[][] computeIndependenceCovarianceArr(double[][] independentData) {
        int numFeatures = independentData.length;
        int numSamples = independentData[0].length;
        double[][] independenceCovarianceArr = new double[numFeatures][numFeatures];

        for (int i = 0; i < numFeatures; i++) {
            for (int j = 0; j < numFeatures; j++) {
                double independenceCovariance = 0.0;
                for (int num = 0; num< numSamples; num++) {
                    independenceCovariance +=
                            independentData[i][num] * independentData[j][num];
                }
                independenceCovarianceArr[i][j] = independenceCovariance / numSamples;
            }
        }

        return independenceCovarianceArr;
    }


    private IndependenceEigenDecomposition decomposeIndependenceEigenvalues(
            double[][] independenceArr) {
        int numDimensions = independenceArr.length;
        double[][] independenceEigenvectors = new double[numDimensions][numDimensions];
        double[] independenceEigenvalues = new double[numDimensions];

        double[][] independenceArray = IndependenceArrMethod(independenceArr);

        for (int eigenIdx = 0; eigenIdx < numDimensions; eigenIdx++) {

            double[] independenceEigenvector = new double[numDimensions];
            Random independenceRandom = new Random(50 + eigenIdx);
            for (int i = 0; i < numDimensions; i++) {
                independenceEigenvector[i] =
                        independenceRandom.nextDouble() - 0.5;
            }
            independenceEigenvector = normalizeIndependenceVector(independenceEigenvector);

            for (int iteration = 0; iteration < 100; iteration++) {
                double[] independenceNewVector =
                        IndependenceArrVector(independenceArray,
                                independenceEigenvector);
                independenceNewVector = normalizeIndependenceVector(independenceNewVector);

                if (computeIndependenceVectorDistance(independenceEigenvector,
                        independenceNewVector) < 1e-5) {
                    break;
                }
                independenceEigenvector = independenceNewVector;
            }
            
            double[] independenceProduct =
                    IndependenceArrVector(independenceArray,
                            independenceEigenvector);
            double independenceEigenvalue =
                    computeIndependenceDotProduct(independenceEigenvector, independenceProduct);

            independenceEigenvectors[eigenIdx] = independenceEigenvector;
            independenceEigenvalues[eigenIdx] = independenceEigenvalue;

            for (int i = 0; i < numDimensions; i++) {
                for (int j = 0; j < numDimensions; j++) {
                    independenceArray[i][j] -=
                            independenceEigenvalue *
                                    independenceEigenvector[i] * independenceEigenvector[j];
                }
            }
        }

        return new IndependenceEigenDecomposition(independenceEigenvectors,
                independenceEigenvalues);
    }


    private double[][] performSymmetricIndependenceDecorrelation(
            double[][] independenceSeparationArr) {
        int numComponents = independenceSeparationArr.length;
        int numFeatures = independenceSeparationArr[0].length;


        double[][] independenceGramArr = new double[numComponents][numComponents];
        for (int i = 0; i < numComponents; i++) {
            for (int j = 0; j < numComponents; j++) {
                double independenceGramValue = 0.0;
                for (int num = 0; num < numFeatures; num++) {
                    independenceGramValue +=
                            independenceSeparationArr[i][num] *
                                    independenceSeparationArr[j][num];
                }
                independenceGramArr[i][j] = independenceGramValue;
            }
        }
        
        IndependenceEigenDecomposition eigenDecomp =
                decomposeIndependenceEigenvalues(independenceGramArr);
        double[][] independenceOrthogonalBasis =
                IndependentMethodArr(eigenDecomp.eigenvectors);
        double[] independenceEigenvalues = eigenDecomp.eigenvalues;

        double[][] independenceInverseSqrtDiagonal =
                new double[numComponents][numComponents];
        for (int i = 0; i < numComponents; i++) {
            independenceInverseSqrtDiagonal[i][i] =
                    1.0 / Math.sqrt(independenceEigenvalues[i] + INDEPENDENCE_EPSILON);
        }
        
        double[][] independenceTemp =
                IndependenceArrMethod(independenceInverseSqrtDiagonal,
                        IndependentMethodArr(independenceOrthogonalBasis));
        double[][] independenceGramInverseSqrt =
                IndependenceArrMethod(independenceOrthogonalBasis, independenceTemp);

        return IndependenceArrMethod(independenceGramInverseSqrt,
                independenceSeparationArr);
    }

    private boolean checkIndependenceConvergence(
            double[][] currentIndependenceArr,
            double[][] previousIndependenceArr) {
        int numComponents = currentIndependenceArr.length;
        int numFeatures = currentIndependenceArr[0].length;

        for (int componentIdx = 0; componentIdx < numComponents; componentIdx++) {
            double independenceSimilarity = 0.0;
            for (int featureIdx = 0; featureIdx < numFeatures; featureIdx++) {
                independenceSimilarity +=
                        Math.abs(currentIndependenceArr[componentIdx][featureIdx] *
                                previousIndependenceArr[componentIdx][featureIdx]);
            }
            
            if (Math.abs(Math.abs(independenceSimilarity) - 1.0) >
                    independenceComponent) {
                return false;
            }
        }
        return true;
    }



    private double[][] initializeRandomIndependenceArr(int numRows, int numCols) {
        Random independenceRandom = new Random(80);
        double[][] independenceArr = new double[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                independenceArr[i][j] = independenceRandom.nextGaussian();
            }
        }

        return performSymmetricIndependenceDecorrelation(independenceArr);
    }

    private double[] normalizeIndependenceVector(double[] independenceVector) {
        double independenceNorm = 0.0;
        for (double value : independenceVector) {
            independenceNorm += value * value;
        }
        independenceNorm = Math.sqrt(independenceNorm);

        double[] normalizedIndependenceVector = new double[independenceVector.length];
        for (int i = 0; i < independenceVector.length; i++) {
            normalizedIndependenceVector[i] =
                    independenceVector[i] / (independenceNorm + INDEPENDENCE_EPSILON);
        }
        return normalizedIndependenceVector;
    }

    private double[] extractColumnForIndependence(double[][] independenceArr, int colIdx) {
        double[] independenceColumn = new double[independenceArr.length];
        for (int rowIdx = 0; rowIdx < independenceArr.length; rowIdx++) {
            independenceColumn[rowIdx] = independenceArr[rowIdx][colIdx];
        }
        return independenceColumn;
    }

    private double computeIndependenceDotProduct(double[] independenceVectorA,
                                                 double[] independenceVectorB) {
        double independenceDotProduct = 0.0;
        for (int i = 0; i < independenceVectorA.length; i++) {
            independenceDotProduct += independenceVectorA[i] * independenceVectorB[i];
        }
        return independenceDotProduct;
    }

    private double computeIndependenceVectorDistance(double[] independenceVectorA,
                                                     double[] independenceVectorB) {
        double independenceDistanceSquared = 0.0;
        for (int i = 0; i < independenceVectorA.length; i++) {
            double independenceDiff = independenceVectorA[i] - independenceVectorB[i];
            independenceDistanceSquared += independenceDiff * independenceDiff;
        }
        return Math.sqrt(independenceDistanceSquared);
    }

    private double[][] IndependenceArrMethod(double[][] independenceArrA,
                                                    double[][] independenceArrB) {
        int numRowsA = independenceArrA.length;
        int numColsB = independenceArrB[0].length;
        int numColsA = independenceArrA[0].length;

        double[][] independenceProductArr = new double[numRowsA][numColsB];
        for (int i = 0; i < numRowsA; i++) {
            for (int j = 0; j < numColsB; j++) {
                double independenceSum = 0.0;
                for (int num = 0; num<numColsA; num++) {
                    independenceSum +=
                            independenceArrA[i][num] * independenceArrB[num][j];
                }
                independenceProductArr[i][j] = independenceSum;
            }
        }
        return independenceProductArr;
    }

    private double[] IndependenceArrVector(double[][] independenceArr,
                                                      double[] independenceVector) {
        int numRows = independenceArr.length;
        double[] independenceResultVector = new double[numRows];

        for (int i = 0; i < numRows; i++) {
            double independenceSum = 0.0;
            for (int j = 0; j < independenceArr[i].length; j++) {
                independenceSum += independenceArr[i][j] * independenceVector[j];
            }
            independenceResultVector[i] = independenceSum;
        }
        return independenceResultVector;
    }

    private double[][] IndependentMethodArr(double[][] independenceArr) {
        int numRows = independenceArr.length;
        int numCols = independenceArr[0].length;
        double[][] independentArr = new double[numCols][numRows];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                independentArr[j][i] = independenceArr[i][j];
            }
        }
        return independentArr;
    }

    private double[][] IndependenceArrMethod(double[][] independenceArr) {
        int numRows = independenceArr.length;
        int numCols = independenceArr[0].length;
        double[][] independenceArray = new double[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            System.arraycopy(independenceArr[i], 0, independenceArray[i], 0, numCols);
        }
        return independenceArray;
    }


    private static class IndependenceWhiteningResult {
        double[][] whitenedData;
        double[][] whiteningArr;

        IndependenceWhiteningResult(double[][] whitenedData, double[][] whiteningArr) {
            this.whitenedData = whitenedData;
            this.whiteningArr = whiteningArr;
        }
    }

    private static class IndependenceEigenDecomposition {
        double[][] eigenvectors;
        double[] eigenvalues;

        IndependenceEigenDecomposition(double[][] eigenvectors, double[] eigenvalues) {
            this.eigenvectors = eigenvectors;
            this.eigenvalues = eigenvalues;
        }
    }

    // ========== MAIN 데모 테스트 ==========

    public static void main(String[] args) {

        demonstrateBasicIndependenceAnalysis();
        System.out.println("  FastICA 독립 성분 분석 (Independent Component Analysis) 결과 ");
        System.out.println("  각 성분은 모두 독립적이고 각 성분은 다른성분과 무관합니다. ");

    }


    private static void demonstrateBasicIndependenceAnalysis() {


        int numIndependentSamples = 8000;

        double[][] independentSource = new double[2][numIndependentSamples];
        for (int i = 0; i < numIndependentSamples; i++) {
            double timeStep = i / 100.0;
            independentSource[0][i] = Math.sin(2 * Math.PI * timeStep);
            independentSource[1][i] = Math.sin(2 * Math.PI * 5 * timeStep);
        }


        double[][] independenceArr = {
                {0.8, 0.5},
                {0.5, 0.8},
        };


        double[][] IndependentComponents =
                independentMethod(independenceArr, independentSource);

        printIndependenceArr(independenceArr);

        // FastICA 적용
        FastICA_ResearchGate independenceAnalyzer = new FastICA_ResearchGate();
        double[][] independenceArray =
                independenceAnalyzer.fitIndependentComponents(IndependentComponents, 8);

        printIndependenceArr(independenceArray);

        // 독립 성분 추출
        double[][] extractedIndependentSources =
                independenceAnalyzer.independentToIndependentComponents(
                        IndependentComponents, independenceArray);


        double independenceComponent =
                Math.abs(computeIndependence(
                        independentSource[0], extractedIndependentSources[0]));
        double independentComponent =
                Math.abs(computeIndependence(
                        independentSource[0], extractedIndependentSources[1]));

        System.out.printf("FastICA 적용 결과 "+Math.max(independenceComponent, independentComponent));
        System.out.printf("FastICA 적용 결과 "+Math.max(1 - independenceComponent, 1 - independentComponent));

    }

    private static double[][] independentMethod(double[][] A, double[][] B) {
        int length = A.length;
        int num = B[0].length;
        int number = A[0].length;

        double[][] C = new double[length][num];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < num; j++) {
                double sum = 0.0;
                for (int val = 0; val < number; val++) {
                    sum += A[i][val] * B[val][j];
                }
                C[i][j] = sum;
            }
        }
        return C;
    }

    private static double computeIndependence(double[] R, double[] C) {
        int n = R.length;
        double meanR = 0.0, meanC = 0.0;

        for (int i = 0; i < n; i++) {
            meanR += R[i];
            meanC += C[i];
        }
        meanR /= n;
        meanC /= n;

        double num = 0.0, denR = 0.0, denC = 0.0;
        for (int i = 0; i < n; i++) {
            double dr = R[i] - meanR;
            double dc = C[i] - meanC;
            num += dr * dc;
            denR += dr * dr;
            denC += dc * dc;
        }

        return num / Math.sqrt(denR * denC);
    }

    private static double computeMeanForDemo(double[] data) {
        double sum = 0.0;
        for (double value : data) {
            sum += value;
        }
        return sum / data.length;
    }

    private static double computeStdForDemo(double[] data) {
        double mean = computeMeanForDemo(data);
        double sumSq = 0.0;
        for (double value : data) {
            double diff = value - mean;
            sumSq += diff * diff;
        }
        return Math.sqrt(sumSq / data.length);
    }

    private static void printIndependenceArr(double[][] arr) {
        for (double[] row : arr) {
            System.out.print("    [");
            for (int j = 0; j < row.length; j++) {
                System.out.printf(String.valueOf(row[j]));
                if (j < row.length - 1) System.out.print(", ");
            }
            System.out.println("]");
        }
    }
}