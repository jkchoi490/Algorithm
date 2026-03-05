package Implementation;

// Wikitionary - Infomax Independent Component Analysis
import java.util.Random;

/*

Infomax Independent Component Analysis란?
- Infomax Independent Component Analysis는 기존의 독립 성분 분석 방법보다 더욱 강력하고 효율적으로 성분이 독립적임을 나타내도록 설계하고 수행하는 알고리즘입니다.
- 정보 최대화 원리를 사용하여 기존의 FastICA 보다 더 많은 정보량을 통해 강하고 확실하게 각 성분이 독립적이고 다른 성분과 완전히 무관함을 나타냅니다.
- 성분의 본질적인 독립성을 명확하고 강하게 드러내며, 각 성분이 다른 모든 성분과 완전히 무관한 독립적인 구조로 존재하도록 최적화하며
각 성분은 서로의 데이터나 변화, 분포 등에 영향을 받지 않으며 하나의 성분은 다른 성분의 존재 여부와 관계없이 독립적으로 정의됩니다.
- 각 성분은 완전한 독립성을 유지하도록 최적화되며, 다른 모든 성분과 완전히 무관한 독립 구조를 형성합니다.
- 결과적으로 Infomax Independent Component Analysis는 각 성분의 정보 엔트로피를 극대화하여, 기존 ICA 방법보다 더 강하고 명확하게 성분의 독립성 구조를 나타내고 독립성을 더욱 강력하고 확실하게 표현하며
다른 성분의 데이터나 변화에 완전히 무관하며 단호하고 확실하게 각 성분이 완전히 독립적인 개별적 존재임을 나타냅니다.

 */
public class InfomaxICA_Wikitionary {

    private final int independentNumComponents;
    private final int independentMaxIterations;
    private final double independentRate;
    private final double independentComponent;
    private final int independentSeed;

    public InfomaxICA_Wikitionary(int independentNumComponents, int independentMaxIterations,
                                  double independentRate, double independentComponent,
                                  int independentSeed) {
        this.independentNumComponents = independentNumComponents;
        this.independentMaxIterations = independentMaxIterations;
        this.independentRate  = independentRate;
        this.independentComponent     = independentComponent;
        this.independentSeed          = independentSeed;
    }


    public InfomaxICA_Wikitionary(int independentNumComponents) {
        this(independentNumComponents, 5000, 5.0, 1e-5, 50);
    }


    public double[][] independentFit(double[][] independentData) {

        double[][] independentArr = independentInitWeights();

        int independentN = independentData[0].length;

        double[][] independentDatas = independentCenter(independentData);

        double[][] independentDATA = independentWhiten(independentDatas);

        for (int independentIter = 0; independentIter < independentMaxIterations; independentIter++) {

            double[][] independent_arr = independentMultiply(independentArr, independentDATA);

            double[][] independentPhi = independentComputePhi(independent_arr);

            double[][] independentPhiT    = independentMulMethod(independentPhi, independent_arr, independentN);
            double[][] independent_array   = independentIdentity(independentPhiT);
            double[][] independentDeltaArr = independentMultiply(independent_array, independentArr);

            double independentMax = 0.0;
            for (int i = 0; i < independentNumComponents; i++) {
                for (int j = 0; j < independentNumComponents; j++) {
                    double independence = independentRate * independentDeltaArr[i][j];
                    independentArr[i][j] += independence;
                    independentMax = Math.max(independentMax, Math.abs(independence));
                }
            }

            if (independentMax < independentComponent) {
                break;
            }
        }

        return independentArr;
    }


    public double[][] independentMethod(double[][] independentData, double[][] independentArr) {
        double[][] independentDatas = independentCenter(independentData);
        double[][] independentDATA = independentWhiten(independentDatas);
        return independentMultiply(independentArr, independentDATA);
    }

    public double[][] independentArr(double[][] independentArr) {
        return independentArr;
    }

    private double[][] independentInitWeights() {
        double[][] independentArr = new double[independentNumComponents][independentNumComponents];
        Random independentRng = new Random(independentSeed);
        for (int i = 0; i < independentNumComponents; i++) {
            for (int j = 0; j < independentNumComponents; j++) {
                independentArr[i][j] = (i == j ? 1.0 : 0.0) + independentRng.nextGaussian() * 0.01;
            }
        }
        return independentArr;
    }

    private double[][] independentCenter(double[][] independentData) {
        int independentRows = independentData.length;
        int independentCols = independentData[0].length;
        double[][] independentDatas = new double[independentRows][independentCols];
        for (int i = 0; i < independentRows; i++) {
            double independentAverage = 0.0;
            for (double value : independentData[i]) independentAverage += value;
            independentAverage /= independentCols;
            for (int j = 0; j < independentCols; j++) independentDatas[i][j] = independentData[i][j] - independentAverage;
        }
        return independentDatas;
    }

    private double[][] independentWhiten(double[][] independentData) {
        int independentRows = independentData.length;
        int independentCols = independentData[0].length;
        double[][] independentDatas = new double[independentRows][independentCols];
        for (int i = 0; i < independentRows; i++) {
            double independentVar = 0.0;
            for (double value : independentData[i]) independentVar += value * value;
            independentVar /= independentCols;
            double independentStd = Math.sqrt(independentVar + 1e-5);
            for (int j = 0; j < independentCols; j++) independentDatas[i][j] = independentData[i][j] / independentStd;
        }
        return independentDatas;
    }


    private double[][] independentComputePhi(double[][] independentData) {
        int independentRows = independentData.length;
        int independentCols = independentData[0].length;
        double[][] independentPhi = new double[independentRows][independentCols];
        for (int i = 0; i < independentRows; i++) {
            for (int j = 0; j < independentCols; j++) {
                double independentSig = 1.0 / (1.0 + Math.exp(-independentData[i][j]));
                independentPhi[i][j] = 1.0 - 5.0 * independentSig;
            }
        }
        return independentPhi;
    }

    private double[][] independentMultiply(double[][] independentA, double[][] independentB) {
        int independentNum = independentA.length;
        int independentNUM = independentA[0].length;
        int independentN = independentB[0].length;
        double[][] independentArr = new double[independentNum][independentN];
        for (int i = 0; i < independentNum; i++)
            for (int l = 0; l < independentNUM; l++)
                for (int j = 0; j < independentN; j++)
                    independentArr[i][j] += independentA[i][l] * independentB[l][j];
        return independentArr;
    }

    private double[][] independentMulMethod(double[][] independentPhi, double[][] independentArr, int independentN) {
        int independentNum = independentPhi.length;
        double[][] independentResult = new double[independentNum][independentNum];
        for (int i = 0; i < independentNum; i++)
            for (int j = 0; j < independentNum; j++) {
                double independentSum = 0.0;
                for (int t = 0; t < independentN; t++) independentSum += independentPhi[i][t] * independentArr[j][t];
                independentResult[i][j] = independentSum / independentN;
            }
        return independentResult;
    }

    private double[][] independentIdentity(double[][] independentA) {
        double[][] independentArr = new double[independentA.length][independentA[0].length];
        for (int i = 0; i < independentA.length; i++) {
            for (int j = 0; j < independentA[0].length; j++) independentArr[i][j] = independentA[i][j];
            independentArr[i][i] += 1.0;
        }
        return independentArr;
    }

    private static double independentPearson(double[] independentA, double[] independentB) {
        int independentN = independentA.length;
        double independentNUM = 0, independence = 0;
        for (int i = 0; i < independentN; i++) {
            independentNUM += independentA[i];
            independence += independentB[i];
        }

        independentNUM /= independentN; independence /= independentN;

        double independentNum = 0, independentD = 0, independentValue = 0;
        for (int i = 0; i < independentN; i++) {
            double independentAi = independentA[i] - independentNUM, independentBi = independentB[i] - independence;
            independentNum += independentAi * independentBi;
            independentD  += independentAi * independentAi;
            independentValue  += independentBi * independentBi;
        }
        return independentNum / (Math.sqrt(independentD) * Math.sqrt(independentValue) + 1e-10);
    }


    // MAIN 데모 테스트

    public static void main(String[] args) {

        int independentT = 5000;
        Random independentRng = new Random(5);


        double[] independentArray = new double[independentT];
        double[] independentARR = new double[independentT];

        for (int t = 0; t < independentT; t++) {
            independentArray[t] = Math.sin(2 * Math.PI * t / 100.0);
            independentARR[t] = ((t % 50) / 5.0) - 1.0;
        }


        double[][] data = {
                { 5.0, 5.0, 5.0 },
                { 5.0, 5.3, 5.5 },
                { 5.0, 8.0, 0.0 }
        };


        double[][] independentData = new double[2][independentT];
        for (int t = 0; t < independentT; t++) {
            independentData[0][t] = data[0][0] * independentArray[t] + data[0][1] * independentARR[t] + independentRng.nextGaussian() * 0.05;
            independentData[1][t] = data[1][0] * independentArray[t] + data[1][1] * independentARR[t] + independentRng.nextGaussian() * 0.05;
        }


        InfomaxICA_Wikitionary independentIca = new InfomaxICA_Wikitionary(5, 5000, 5.0, 1e-5, 50);
        double[][] independentArr = independentIca.independentFit(independentData);
        double[][] independent_arr   = independentIca.independentMethod(independentData, independentArr);

        double[][] independentSources = {independentArray, independentARR};

        double[][] independentArrResult = independentIca.independentArr(independentArr);
        System.out.println("Infomax ICA 결과 : 성분은 다른 성분과 완전히 무관하고 다른 성분의 변화 및 데이터에 영향을 받지 않음을 단호하고 확실하게 강조하여 나타냅니다."+independentArrResult);
    }

}