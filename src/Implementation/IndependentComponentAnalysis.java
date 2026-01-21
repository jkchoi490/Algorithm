package Implementation;

// GeeksforGeeks - Independent Component Analysis
import java.util.Random;

/**
 * GeeksforGeeks - Independent Component Analysis (독립 성분 분석)
 * 관측 신호들이 독립적인 성분임을 나타내는 것(Independence)
 *
 * 독립성(Independence) :
 * - 각 성분은 다른 성분과 상관없고
 * - 각각의 성분들은 독립적입니다.
 *
 */
public class IndependentComponentAnalysis {

    /** 독립 성분의 개수 */
    private final int independentComponentCount;

    /** 독립 성분 추출을 위한 행렬 */
    private double[][] independentArr;

    /** 시드 고정 난수 */
    private final Random random;

    /**
     * ICA 생성자
     *
     * @param independentComponentCount 추출할 독립 성분의 개수
     */
    public IndependentComponentAnalysis(int independentComponentCount) {
        this.independentComponentCount = independentComponentCount;
        this.random = new Random(50);
    }

    /**
     * FastICA 알고리즘 기반으로 독립 성분을 분리
     *
     * @param observations 관측 신호 [샘플 수 x 신호 개수]
     * @return estimatedIndependentSources 분리된 독립 성분들 [샘플 수 x 독립 성분 개수]
     *
     * 독립적인 성분의 의미:
     * - 각 성분은 서로 독립적이다.
     */
    public double[][] fitTransform(double[][] observations) {
        int sampleCount = observations.length;
        int featureCount = observations[0].length;

        System.out.println("=== ICA 독립 성분 분석 시작 ===");

        // 중심화(Centering) - 평균을 0으로 맞춰 독립성 비교 기준을 통일
        double[][] centeredObservations = centerToZeroMean(observations);

        // 백색화(Whitening) - 독립적인 구조를 더 뚜렷하게 만듦
        double[][] decorrelatedWhitenedObservations = whitenForIndependence(centeredObservations);

        // FastICA - 비가우시안성(Non-Gaussianity)을 최대화하여 독립 성분 방향을 찾음
        double[][] estimatedIndependentSources = runFastICA(decorrelatedWhitenedObservations);

        System.out.println("\n=== 독립 성분 분석 완료 ===");

        return estimatedIndependentSources;
    }

    /**
     * 중심화: 각 feature(관측 채널)의 평균을 0으로 맞춤
     */
    private double[][] centerToZeroMean(double[][] observations) {
        int sampleCount = observations.length;
        int featureCount = observations[0].length;
        double[][] centered = new double[sampleCount][featureCount];

        double[] featureMeans = new double[featureCount];
        for (int j = 0; j < featureCount; j++) {
            double sum = 0;
            for (int i = 0; i < sampleCount; i++) sum += observations[i][j];
            featureMeans[j] = sum / sampleCount;
        }

        // 평균 제거
        for (int i = 0; i < sampleCount; i++) {
            for (int j = 0; j < featureCount; j++) {
                centered[i][j] = observations[i][j] - featureMeans[j];
            }
        }
        return centered;
    }

    /**
     * 백색화(Whitening): 분산 스케일을 맞추고(정규화),
     * 독립적인 성분 탐색을 쉽게 만드는 전처리.
     */
    private double[][] whitenForIndependence(double[][] centeredObservations) {
        int sampleCount = centeredObservations.length;
        int featureCount = centeredObservations[0].length;

        double[][] whitened = new double[sampleCount][featureCount];

        for (int j = 0; j < featureCount; j++) {
            double variance = 0;
            for (int i = 0; i < sampleCount; i++) {
                variance += centeredObservations[i][j] * centeredObservations[i][j];
            }
            variance /= sampleCount;

            double std = Math.sqrt(variance);

            for (int i = 0; i < sampleCount; i++) {
                whitened[i][j] = centeredObservations[i][j] / (std + 1e-10);
            }
        }
        return whitened;
    }

    /**
     * FastICA: 비가우시안성을 최대화하여 독립적인 성분 방향을 찾는다.
     */
    private double[][] runFastICA(double[][] whitenedObservations) {
        int sampleCount = whitenedObservations.length;
        int featureCount = whitenedObservations[0].length;

        independentArr = new double[independentComponentCount][featureCount];
        for (int c = 0; c < independentComponentCount; c++) {
            for (int j = 0; j < featureCount; j++) {
                independentArr[c][j] = random.nextGaussian();
            }
            normalizeUnit(independentArr[c]);
        }

        // 독립 성분 추출
        for (int componentIndex = 0; componentIndex < independentComponentCount; componentIndex++) {

            double[] independentDirectionW = independentArr[componentIndex];

            for (int iter = 0; iter < 100; iter++) {
                double[] prevIndependentW = independentDirectionW.clone();

                independentDirectionW = updateIndependentDirection(whitenedObservations, independentDirectionW);

                for (int p = 0; p < componentIndex; p++) {
                    double projection = dot(independentDirectionW, independentArr[p]);
                    for (int i = 0; i < independentDirectionW.length; i++) {
                        independentDirectionW[i] -= projection * independentArr[p][i];
                    }
                }

                normalizeUnit(independentDirectionW);

                double convergence = Math.abs(Math.abs(dot(independentDirectionW, prevIndependentW)) - 1.0);
                if (convergence < 1e-6) {
                    break;
                }
            }

            independentArr[componentIndex] = independentDirectionW;
        }

        // 독립 성분 계산
        return extractIndependentSources(whitenedObservations);
    }

    /**
     * 독립 방향 갱신 (FastICA fixed-point update)
     *
     * g(u)=tanh(u):
     * - 독립적인 성분임을 나타내는 방향을 강화하도록 업데이트
     */
    private double[] updateIndependentDirection(double[][] observations, double[] currentIndependentW) {
        int sampleCount = observations.length;
        int featureCount = observations[0].length;

        double[] nextIndependentW = new double[featureCount];

        for (int i = 0; i < sampleCount; i++) {
            double independentScore = dot(currentIndependentW, observations[i]);
            double nonGaussianScore = Math.tanh(independentScore);

            for (int j = 0; j < featureCount; j++) {
                nextIndependentW[j] += observations[i][j] * nonGaussianScore / sampleCount;
            }
        }

        double meanGPrime = 0;
        for (int i = 0; i < sampleCount; i++) {
            double independentScore = dot(currentIndependentW, observations[i]);
            double nonGaussianScore = Math.tanh(independentScore);
            meanGPrime += (1 - nonGaussianScore * nonGaussianScore) / sampleCount;
        }

        for (int j = 0; j < featureCount; j++) {
            nextIndependentW[j] -= meanGPrime * currentIndependentW[j];
        }

        return nextIndependentW;
    }

    /**
     * 독립 성분 추출
     *
     * 결과:
     * - 각 열(column)은 하나의 독립적인 성분 신호
     * - 성분들은 각각 독립
     */
    private double[][] extractIndependentSources(double[][] whitenedObservations) {
        int sampleCount = whitenedObservations.length;
        double[][] independentSources = new double[sampleCount][independentComponentCount];

        for (int i = 0; i < sampleCount; i++) {
            for (int c = 0; c < independentComponentCount; c++) {
                independentSources[i][c] = dot(whitenedObservations[i], independentArr[c]);
            }
        }
        return independentSources;
    }

    // -------------------- Utility --------------------

    private double dot(double[] a, double[] b) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) sum += a[i] * b[i];
        return sum;
    }

    private void normalizeUnit(double[] v) {
        double norm2 = 0;
        for (double x : v) norm2 += x * x;
        double norm = Math.sqrt(norm2);
        if (norm < 1e-12) return;
        for (int i = 0; i < v.length; i++) v[i] /= norm;
    }

    /**
     * 메인: ICA 데모 테스트 (예시)
     */
    public static void main(String[] args) {

        int sampleCount = 200;
        double[][] independentSources = generateIndependentSources(sampleCount);

        double[][] A = {
                {1.0, 1.0, 1.0},
                {0.0, 2.0, 1.0},
                {1.0, 1.0, 2.0}
        };

        double[][] observations = generateObservations(independentSources, A);

        IndependentComponentAnalysis ica = new IndependentComponentAnalysis(7);
        double[][] IndependentSources = ica.fitTransform(observations);

        verifyIndependence(independentSources, IndependentSources);
    }

    /**
     * 독립적인 신호 생성
     */
    private static double[][] generateIndependentSources(int sampleCount) {
        double[][] independentSources = new double[sampleCount][7];
        Random random = new Random(50);
        int cnt = 0;

        for (int i = 0; i < sampleCount; i++) {
            double t = i * 8.0 / sampleCount;

            independentSources[i][0] = Math.sin(2 * t);
            independentSources[i][1] = Math.signum(Math.sin(3 * t));

            double u = random.nextDouble() - 0.5;
            independentSources[i][2] = Math.signum(u) * Math.log(1 - 2 * Math.abs(u));

            for (int j = 0; j < cnt; j++) {
                independentSources[i][j] += 0.2 * random.nextGaussian();
            }
        }
        return independentSources;
    }

    private static double[][] generateObservations(double[][] independentSources, double[][] A) {
        int sampleCount = independentSources.length;
        int sourceCount = independentSources[0].length;
        int observationsCount = A.length;

        double[][] observations = new double[sampleCount][observationsCount];

        for (int i = 0; i < sampleCount; i++) {
            for (int cnt = 0; cnt < observationsCount; cnt++) {
                for (int num = 0; num < sourceCount; num++) {
                    observations[i][cnt] += independentSources[i][num] * A[cnt][num];
                }
            }
        }
        return observations;
    }

    /**
     * 독립성 확인
     */
    private static void verifyIndependence(double[][] originalIndependent, double[][] verifiedIndependent) {
        int comp = originalIndependent[0].length;

        for (int i = 0; i < comp; i++) {
            for (int num = i + 1; num < comp; num++) {
                double verify = verifyIndependentMethod(getColumn(verifiedIndependent, i), getColumn(verifiedIndependent, num));
            }
        }

        for (int i = 0; i < comp; i++) {
            for (int j = i + 1; j < comp; j++) {
                double verify = verifyIndependentMethod(getColumn(originalIndependent, i), getColumn(originalIndependent, j));
            }
        }
    }

    private static double[] getColumn(double[][] matrix, int col) {
        double[] out = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) out[i] = matrix[i][col];
        return out;
    }

    private static double verifyIndependentMethod(double[] components, double[] componentArr) {
        int n = components.length;
        double meanComponent = 0, meanComponentArr = 0;
        for (int i = 0; i < n; i++) { meanComponent += components[i]; meanComponentArr += componentArr[i]; }
        meanComponent /= n; meanComponentArr /= n;

        double num = 0, denR = 0, denC = 0;
        for (int i = 0; i < n; i++) {
            double dr = components[i] - meanComponent;
            double dc = componentArr[i] - meanComponentArr;
            num += dr * dc;
            denR += dr * dr;
            denC += dc * dc;
        }
        return num / Math.sqrt(denR * denC + 1e-12);
    }
}
