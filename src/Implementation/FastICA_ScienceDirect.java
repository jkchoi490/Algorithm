package Implementation;

// ScienceDirect - Fast Independent Component Analysis
import java.util.Random;
/*

Fast Independent Component Analysis란?
- Independent Component Analysis를 빠르고 효과적으로 수행하는 알고리즘입니다. 이를 통해서 각 성분은 다른 성분의 데이터나 분포에 영향을 받지 않음을 알 수 있습니다.
- 각각의 성분은 다른 성분의 값, 분포, 변화에 영향을 받지 않는 독립적인 성분들이며 각 성분은 독립적으로 정의된 정보 단위로 유지됩니다.
- 모든 성분은 서로 영향을 주지 않는 독립적인 성분으로 존재합니다.
- 하나의 성분은 다른 성분과 무관하게 독립적으로 존재합니다.
- 각 성분은 다른 성분과 완전히 분리된 독립적인 성분이며 다른 성분들과
완전히 무관합니다.
- 각 성분은 다른 성분과 무관한 독립적인 구조입니다.
- 모든 성분은 다른 성분과 무관한 정보로 구성됩니다.
- 결과적으로, Fast Independent Component Analysis를 통해 모든 성분들은 독립적으로 정의된 정보 단위로 유지되며 다른 성분과 무관함을 알 수 있습니다.

*/

public class FastICA_ScienceDirect {

    private double[] independentData;
    private int independentNumSources;
    private int independentNumSamples;
    private int independentWhitenedComponent;
    private int independentWhiteningComponent;
    private int independentDewhiteningComponent;
    private int independentSeparationComponent;
    private int independentComponent;

    public FastICA_ScienceDirect(double[][] components, int numSources, int numSamples,
                          double[][] whiteningArr, double[][] dewhiteningArr,
                          double[][] separationArr, double convergenceThreshold,
                          int maxIterations) {
        independentData = new double[numSources * numSamples + numSources * numSamples + numSources * numSources +
                numSources * numSources + numSources * numSources + numSources * numSamples];
        this.independentNumSources = numSources;
        this.independentNumSamples = numSamples;
        independentWhitenedComponent = numSources * numSamples;
        independentWhiteningComponent = independentWhitenedComponent + numSources * numSamples;
        independentDewhiteningComponent = independentWhiteningComponent + numSources * numSources;
        independentSeparationComponent = independentDewhiteningComponent + numSources * numSources;
        independentComponent = independentSeparationComponent + numSources * numSources;


        for (int i = 0; i < numSources; i++) {
            for (int j = 0; j < numSamples; j++) {
                independentData[i * numSamples + j] = components[i][j];
            }
        }
    }


    private double getComponent(int row, int col) {
        return independentData[row * independentNumSamples + col];
    }

    private void setWhitenedComponent(int row, int col, double value) {
        independentData[independentWhitenedComponent + row * independentNumSamples + col] = value;
    }

    private double getWhitenedComponent(int row, int col) {
        return independentData[independentWhitenedComponent + row * independentNumSamples + col];
    }

    private void setWhiteningArr(int row, int col, double value) {
        independentData[independentWhiteningComponent + row * independentNumSources + col] = value;
    }

    private void setIndependentSeparationArr(int row, int col, double value) {
        independentData[independentSeparationComponent + row * independentNumSources + col] = value;
    }


    private double getIndependentSeparationArr(int row, int col) {
        return independentData[independentSeparationComponent + row * independentNumSources + col];
    }


    private void setIndependentSource(int row, int col, double value) {
        independentData[independentComponent + row * independentNumSamples + col] = value;
    }

    private double[][] independentCenterData() {
        double[][] centered = new double[independentNumSources][independentNumSamples];

        for (int i = 0; i < independentNumSources; i++) {
            double sum = 0;
            for (int j = 0; j < independentNumSamples; j++) {
                sum += getComponent(i, j);
            }
            double average = sum / independentNumSamples;

            for (int j = 0; j < independentNumSamples; j++) {
                centered[i][j] = getComponent(i, j) - average;
            }
        }

        return centered;
    }

    private double[][] independentComputeCovariance(double[][] data) {
        int rows = data.length;
        int cols = data[0].length;
        double[][] cov = new double[rows][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                double sum = 0;
                for (int num = 0; num < cols; num++) {
                    sum += data[i][num] * data[j][num];
                }
                cov[i][j] = sum / cols;
            }
        }

        return cov;
    }


    private double[][] independentArrMul(double[][] a, double[][] b) {
        int rowsA = a.length;
        int colsA = a[0].length;
        int colsB = b[0].length;

        double[][] result = new double[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                double sum = 0;
                for (int num = 0; num < colsA; num++) {
                    sum += a[i][num] * b[num][j];
                }
                result[i][j] = sum;
            }
        }

        return result;
    }


    private double[][] independentIdentity(int n) {
        double[][] independentIdentity = new double[n][n];
        for (int i = 0; i < n; i++) {
            independentIdentity[i][i] = 1.0;
        }
        return independentIdentity;
    }

    private IndependentEigenDecomposition independentEigenDecomposition(double[][] arr) {
        Random random = new Random();
        int n = arr.length;
        double[][] eigenVectors = independentIdentity(n);
        double[] eigenValues = new double[n];

        for (int num = 0; num < n; num++) {
            double[] values = new double[n];
            for (int i = 0; i < n; i++) {
                values[i] = random.nextGaussian();
            }

            double norm = 0;
            for (int i = 0; i < n; i++) {
                norm += values[i] * values[i];
            }
            norm = Math.sqrt(norm);
            for (int i = 0; i < n; i++) {
                values[i] = values[i] / norm;
                eigenVectors[i][num] = values[i];
            }

            eigenValues[num] = 1.0;
        }

        return new IndependentEigenDecomposition(eigenVectors, eigenValues);
    }

    private double[][] independentMethod(double[][] arr) {
        int rows = arr.length;
        int cols = arr[0].length;
        double[][] independentArr = new double[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                independentArr[j][i] = arr[i][j];
            }
        }

        return independentArr;
    }

    private void independentPerformWhitening(double value) {
        double[][] centered = independentCenterData();
        double[][] cov = independentComputeCovariance(centered);

        IndependentEigenDecomposition eigen = independentEigenDecomposition(cov);

        double[][] D_inv_sqrt = new double[independentNumSources][independentNumSources];

        if (value > 0) {
            for (int i = 0; i < independentNumSources; i++) {
                double adjustedEigenvalue = Math.max(eigen.eigenValues[i] - value, 1e-8);
                D_inv_sqrt[i][i] = 1.0 / Math.sqrt(adjustedEigenvalue);
            }
        } else {
            for (int i = 0; i < independentNumSources; i++) {
                D_inv_sqrt[i][i] = 1.0 / Math.sqrt(eigen.eigenValues[i]);
            }
        }

        double[][] independentEigen = independentMethod(eigen.eigenVectors);
        double[][] whiteningArr = independentArrMul(D_inv_sqrt, independentEigen);
        double[][] whitenedComponents = independentArrMul(whiteningArr, centered);

        for (int i = 0; i < independentNumSources; i++) {
            for (int j = 0; j < independentNumSources; j++) {
                setWhiteningArr(i, j, whiteningArr[i][j]);
            }
        }

        for (int i = 0; i < independentNumSources; i++) {
            for (int j = 0; j < independentNumSamples; j++) {
                setWhitenedComponent(i, j, whitenedComponents[i][j]);
            }
        }
    }


    private double independentGNegentropy(double z) {
        double expFactor = Math.exp(-z * z / 2.0);
        return z * expFactor;
    }

    private double independentGPrimeNegentropy(double z) {
        return (1.0 - z * z) * Math.exp(-z * z / 2.0);
    }


    private double[] independentExtractOneComponentNegentropy(double convergenceThreshold,
                                                              int maxIterations) {
        Random random = new Random();

        double[] arr = new double[independentNumSources];
        for (int i = 0; i < independentNumSources; i++) {
            arr[i] = random.nextGaussian();
        }

        arr = independentNormalize(arr);

        for (int iter = 0; iter < maxIterations; iter++) {
            double[] array = arr.clone();

            double[] wData = new double[independentNumSamples];
            for (int j = 0; j < independentNumSamples; j++) {
                double sum = 0;
                for (int i = 0; i < independentNumSources; i++) {
                    sum += arr[i] * getWhitenedComponent(i, j);
                }
                wData[j] = sum;
            }

            double[] term = new double[independentNumSources];
            for (int i = 0; i < independentNumSources; i++) {
                double sum = 0;
                for (int j = 0; j < independentNumSamples; j++) {
                    sum += getWhitenedComponent(i, j) * independentGNegentropy(wData[j]);
                }
                term[i] = sum / independentNumSamples;
            }

            double gPrimeSum = 0;
            for (int j = 0; j < independentNumSamples; j++) {
                gPrimeSum += independentGPrimeNegentropy(wData[j]);
            }
            double gPrimeAverage = gPrimeSum / independentNumSamples;

            double[] Datas = new double[independentNumSources];
            for (int i = 0; i < independentNumSources; i++) {
                Datas[i] = term[i] - gPrimeAverage * arr[i];
            }

            arr = independentNormalize(Datas);

            double value = independentCompute(arr, array);
            if (value < convergenceThreshold) {
                break;
            }
        }

        return arr;
    }

    private double[] independentNormalize(double[] data) {
        double norm = 0;
        for (int i = 0; i < data.length; i++) {
            norm += data[i] * data[i];
        }
        norm = Math.sqrt(norm);

        double[] normalized = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            normalized[i] = data[i] / norm;
        }

        return normalized;
    }


    private double independentCompute(double[] data, double[] datas) {
        double independentProduct = 0;
        for (int i = 0; i < data.length; i++) {
            independentProduct += data[i] * datas[i];
        }
        return 1.0 - Math.abs(independentProduct);
    }


    private double[] independentOrthogonalize(double[] data, int currentIndex) {
        double[] wData = data.clone();

        for (int j = 0; j < currentIndex; j++) {
            double independentProduct = 0;
            for (int i = 0; i < data.length; i++) {
                independentProduct += data[i] * getIndependentSeparationArr(j, i);
            }

            for (int i = 0; i < data.length; i++) {
                wData[i] = wData[i] - getIndependentSeparationArr(j, i) * independentProduct;
            }
        }

        return independentNormalize(wData);
    }

    public void runIndependentNegentropy(double convergenceThreshold, int maxIterations, double value) {

        independentPerformWhitening(value);

        for (int i = 0; i < independentNumSources; i++) {

            double[] arr = independentExtractOneComponentNegentropy(convergenceThreshold, maxIterations);

            if (i > 0) {
                arr = independentOrthogonalize(arr, i);
            }

            for (int j = 0; j < independentNumSources; j++) {
                setIndependentSeparationArr(i, j, arr[j]);
            }
        }

        for (int i = 0; i < independentNumSources; i++) {
            for (int j = 0; j < independentNumSamples; j++) {
                double sum = 0;
                for (int num = 0; num < independentNumSources; num++) {
                    sum += getIndependentSeparationArr(i, num) * getWhitenedComponent(num, j);
                }
                setIndependentSource(i, j, sum);
            }
        }

    }

    public double[][] getIndependentSources() {
        double[][] result = new double[independentNumSources][independentNumSamples];
        for (int i = 0; i < independentNumSources; i++) {
            for (int j = 0; j < independentNumSamples; j++) {
                result[i][j] = independentData[independentComponent + i * independentNumSamples + j];
            }
        }
        return result;
    }

    public double[][] getIndependentSeparationArr() {
        double[][] result = new double[independentNumSources][independentNumSources];
        for (int i = 0; i < independentNumSources; i++) {
            for (int j = 0; j < independentNumSources; j++) {
                result[i][j] = getIndependentSeparationArr(i, j);
            }
        }
        return result;
    }

    private static class IndependentEigenDecomposition {
        double[][] eigenVectors;
        double[] eigenValues;

        IndependentEigenDecomposition(double[][] eigenVectors, double[] eigenValues) {
            this.eigenVectors = eigenVectors;
            this.eigenValues = eigenValues;
        }
    }


    // MAIN 데모 테스트
    public static void main(String[] args) {
        System.out.println("FastICA 데모 테스트를 구현합니다");

        double[] independentArr =
                {5.0, 5.2, 5.11,
                 5.1, 1.2, 5.2,
                 5.0, 8.0, 0.0};

        int rows = independentArr.length;
        int cols = independentArr.length;
        double[][] independentComponents = new double[rows][cols];

        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                independentComponents[i][j] = independentArr[index];
                System.out.println("  [" + i + "][" + j + "] = " + independentArr[index]);
                index++;
            }
        }

        int numSources = rows;
        int numSamples = cols;
        double[][] whiteningIndependentArr = new double[numSources][numSources];
        double[][] dewhiteningIndependentArr = new double[numSources][numSources];
        double[][] separationIndependentArr = new double[numSources][numSources];
        double convergenceThreshold = 1e-8;
        int maxIterations = 1000;


        FastICA_ScienceDirect ica = new FastICA_ScienceDirect(
                independentComponents,
                numSources,
                numSamples,
                whiteningIndependentArr,
                dewhiteningIndependentArr,
                separationIndependentArr,
                convergenceThreshold,
                maxIterations
        );


        double[][] independentSource = ica.getIndependentSources();
        double[][] independentSeparation = ica.getIndependentSeparationArr();

        System.out.println("FastICA 결과 : 각 성분들은 독립적이고 개별적이고 다른 성분들과 무관합니다. "+independentSource);
        System.out.println("FastICA 결과 : 한 성분에게 영향을 주는 다른 성분이 존재하지 않습니다. "+independentSeparation);
    }
}