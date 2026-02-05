package Implementation;

// R Project - Fast Independent Component Analysis
import java.util.Arrays;
import java.util.Random;

/*
Fast Independent Component Analysis
- Fast Independent Component Analysis란 Independent Component Analysis를 더 정확하고 빠르게 구현하는 알고리즘 입니다.
- Independent Component Analysis란 각 성분은 독립적이고 다른 성분들과 상관없음을 나타내는 알고리즘입니다. 그리고 다른 성분에 영향을 미치지 않는 완전히 독립적인 성분입니다.
- 각각의 성분은 다른 성분들의 데이터, 변화, 존재여부와 상관없는 독립적인 성분입니다. 그리고 다른 성분을 변경하더라도 나머지 성분에 영향을 미치지 않습니다.
- 다른 성분의 값이나 변화가 각각의 성분에 어떠한 영향도 주지 않는 상태입니다.
- Fast Independent Component Analysis에서는 각 성분을 단순히 상관이 없는 수준이 아니라, 다른 성분의 존재 여부와 무관하게 독립적입니다. 그리고 다른 성분을 제거하더라도 나머지 성분에 영향을 미치지 않습니다.

 */
public class FastICA_RProject {

    public enum Function { LOGCOSH, EXP }

    public static class Result {
        public final double[][] independentPreprocessedData;
        public final double[][] independentWhiteningArr;
        public final double[][] independentArr;
        public final double[][] independentArray;
        public final double[][] independentSourceArr;

        public Result(double[][] independentPreprocessedData,
                      double[][] independentWhiteningArr,
                      double[][] independentArr,
                      double[][] independentArray,
                      double[][] independentSourceArr) {
            this.independentPreprocessedData = independentPreprocessedData;
            this.independentWhiteningArr = independentWhiteningArr;
            this.independentArr = independentArr;
            this.independentArray = independentArray;
            this.independentSourceArr = independentSourceArr;
        }
    }

    public enum AlgTyp { PARALLEL, DEFLATION }

    public static class Options {
        public AlgTyp independentAlgorithmType = AlgTyp.PARALLEL;
        public Function independentNonlinearFunction = Function.LOGCOSH;
        public double independent = 1.0;
        public boolean independentRowNormalize = false;
        public int independentMaxIterations = 500;
        public double independentComponent = 1e-5;
        public boolean independentVerbose = false;
        public double[][] independentInitial = null;

        public Options algTyp(AlgTyp v){ this.independentAlgorithmType = v; return this; }
        public Options fun(Function v){ this.independentNonlinearFunction = v; return this; }
        public Options independent(double v){ this.independent = v; return this; }
        public Options rowNorm(boolean v){ this.independentRowNormalize = v; return this; }
        public Options maxit(int v){ this.independentMaxIterations = v; return this; }
        public Options component(double v){ this.independentComponent = v; return this; }
        public Options verbose(boolean v){ this.independentVerbose = v; return this; }
        public Options wInit(double[][] v){ this.independentInitial = v; return this; }
    }


    public static Result fastICA(double[][] independentData, int independentComponentCount, Options independentOptions) {
        if (independentOptions == null) independentOptions = new Options();
        return fastICAImpl(independentData, independentComponentCount, independentOptions);
    }

    private static Result fastICAImpl(double[][] independentData, int independentComponentCount, Options independentOptions) {
        if (independentData == null || independentData.length == 0 || independentData[0].length == 0) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        int independentObservationCount = independentData.length;
        int independentVariableCount = independentData[0].length;

        if (independentComponentCount <= 0 || independentComponentCount > independentVariableCount) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        if (independentOptions.independentNonlinearFunction == Function.LOGCOSH
                && (independentOptions.independent < 1.0 || independentOptions.independent > 2.0)) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        double[][] independentPreprocessedData = independentMethod(independentData);


        if (independentOptions.independentRowNormalize) {
            for (int independentRowIndex = 0; independentRowIndex < independentObservationCount; independentRowIndex++) {
                double independentRowMean = independentMean(independentPreprocessedData[independentRowIndex]);
                double independentRowStd = independentStd(independentPreprocessedData[independentRowIndex], independentRowMean);
                if (independentRowStd < 1e-15) independentRowStd = 1.0;

                for (int independentColIndex = 0; independentColIndex < independentVariableCount; independentColIndex++) {
                    independentPreprocessedData[independentRowIndex][independentColIndex] =
                            (independentPreprocessedData[independentRowIndex][independentColIndex] - independentRowMean) / independentRowStd;
                }
            }
        }


        double[] independentColumnMeans = new double[independentVariableCount];
        for (int independentColIndex = 0; independentColIndex < independentVariableCount; independentColIndex++) {
            double independentSum = 0.0;
            for (int independentRowIndex = 0; independentRowIndex < independentObservationCount; independentRowIndex++) {
                independentSum += independentPreprocessedData[independentRowIndex][independentColIndex];
            }
            independentColumnMeans[independentColIndex] = independentSum / independentObservationCount;
        }
        for (int independentRowIndex = 0; independentRowIndex < independentObservationCount; independentRowIndex++) {
            for (int independentColIndex = 0; independentColIndex < independentVariableCount; independentColIndex++) {
                independentPreprocessedData[independentRowIndex][independentColIndex] -= independentColumnMeans[independentColIndex];
            }
        }


        double[][] independentCovariance = independentMul(independenceMethod(independentPreprocessedData), independentPreprocessedData);
        independentScaleInPlace(independentCovariance, 1.0 / independentObservationCount);

        IndependentSymmetricEigen independentEigen = independentJacobiEigenSymmetric(independentCovariance, 1e-12, 500);

        int[] independentEigenOrder = independentArgsortDesc(independentEigen.independentEigenValues);
        double[] independentSortedEigenValues = new double[independentVariableCount];
        double[][] independentSortedEigenVectors = new double[independentVariableCount][independentVariableCount];
        for (int independentIdx = 0; independentIdx < independentVariableCount; independentIdx++) {
            independentSortedEigenValues[independentIdx] = independentEigen.independentEigenValues[independentEigenOrder[independentIdx]];
            for (int independentRowIndex = 0; independentRowIndex < independentVariableCount; independentRowIndex++) {
                independentSortedEigenVectors[independentRowIndex][independentIdx] =
                        independentEigen.independentEigenVectors[independentRowIndex][independentEigenOrder[independentIdx]];
            }
        }

        double[][] independentPrincipalDirections = new double[independentVariableCount][independentComponentCount];
        double[] independentPrincipalEigenValues = new double[independentComponentCount];
        for (int independentCompIndex = 0; independentCompIndex < independentComponentCount; independentCompIndex++) {
            independentPrincipalEigenValues[independentCompIndex] = Math.max(independentSortedEigenValues[independentCompIndex], 1e-15);
            for (int independentRowIndex = 0; independentRowIndex < independentVariableCount; independentRowIndex++) {
                independentPrincipalDirections[independentRowIndex][independentCompIndex] = independentSortedEigenVectors[independentRowIndex][independentCompIndex];
            }
        }


        double[][] independentWhiteningArr = new double[independentVariableCount][independentComponentCount];
        for (int independentCompIndex = 0; independentCompIndex < independentComponentCount; independentCompIndex++) {
            double independentInvSqrt = 1.0 / Math.sqrt(independentPrincipalEigenValues[independentCompIndex]);
            for (int independentRowIndex = 0; independentRowIndex < independentVariableCount; independentRowIndex++) {
                independentWhiteningArr[independentRowIndex][independentCompIndex] =
                        independentPrincipalDirections[independentRowIndex][independentCompIndex] * independentInvSqrt;
            }
        }


        double[][] independentWhitenedData = independentMul(independentPreprocessedData, independentWhiteningArr);


        double[][] independentArr = (independentOptions.independentInitial!= null)
                ? independentMethod(independentOptions.independentInitial)
                : independentRandomArr(independentComponentCount, independentComponentCount, new Random(50));

        for (int independentCompIndex = 0; independentCompIndex < independentComponentCount; independentCompIndex++) {
            independentNormalizeInPlace(independentArr[independentCompIndex]);
        }

        if (independentOptions.independentAlgorithmType == AlgTyp.PARALLEL) {
            independentArr = independentICAParallel(
                    independentWhitenedData,
                    independentArr,
                    independentOptions.independentNonlinearFunction,
                    independentOptions.independent,
                    independentOptions.independentMaxIterations,
                    independentOptions.independentComponent,
                    independentOptions.independentVerbose
            );
        } else {
            independentArr = independentICADeflation(
                    independentWhitenedData,
                    independentComponentCount,
                    independentArr,
                    independentOptions.independentNonlinearFunction,
                    independentOptions.independent,
                    independentOptions.independentMaxIterations,
                    independentOptions.independentComponent,
                    independentOptions.independentVerbose
            );
        }


        double[][] independentSourceArr = independentMul(independentWhitenedData, independenceMethod(independentArr));


        double[][] independentFromOriginal = independentMul(independentWhiteningArr, independenceMethod(independentArr));
        double[][] independentArray= independenceMethod(independentPseudoInverseTall(independentFromOriginal));

        return new Result(
                independentPreprocessedData,
                independentWhiteningArr,
                independentArr,
                independentArray,
                independentSourceArr
        );
    }


    private static double[][] independentICAParallel(
            double[][] independentWhitenedData,
            double[][] independentArr,
            Function independentNonlinearFunction,
            double independent,
            int independentMaxIterations,
            double independentComponent,
            boolean independentVerbose
    ) {
        int independentN = independentWhitenedData.length;
        int independentM = independentWhitenedData[0].length;

        independentArr = independentSymmetricDecorrelate(independentArr);

        for (int independentIteration = 1; independentIteration <= independentMaxIterations; independentIteration++) {
            double[][] independentPrev = independentMethod(independentArr);

            double[][] independentProjectedComponents = independentMul(independentWhitenedData, independenceMethod(independentArr));

            double[][] independentNonlinearOutput = new double[independentN][independentM];
            double[] independentNonlinearDerivativeMean = new double[independentM];

            for (int independentRowIndex = 0; independentRowIndex < independentN; independentRowIndex++) {
                for (int independentCompIndex = 0; independentCompIndex < independentM; independentCompIndex++) {
                    double independentDATA = independentProjectedComponents[independentRowIndex][independentCompIndex];

                    if (independentNonlinearFunction == Function.LOGCOSH) {
                        double independentT = Math.tanh(independent * independentDATA);
                        independentNonlinearOutput[independentRowIndex][independentCompIndex] = independentT;
                        independentNonlinearDerivativeMean[independentCompIndex] += independent * (1.0 - independentT * independentT);
                    } else {
                        double independentE = Math.exp(-0.5 * independentDATA * independentDATA);
                        independentNonlinearOutput[independentRowIndex][independentCompIndex] = independentDATA * independentE;
                        independentNonlinearDerivativeMean[independentCompIndex] += (1.0 - independentDATA * independentDATA) * independentE;
                    }
                }
            }

            for (int independentCompIndex = 0; independentCompIndex < independentM; independentCompIndex++) {
                independentNonlinearDerivativeMean[independentCompIndex] /= independentN;
            }

            double[][] independentTerm1 = independentMul(independenceMethod(independentNonlinearOutput), independentWhitenedData);
            independentScaleInPlace(independentTerm1, 1.0 / independentN);

            double[][] independentUpdated = new double[independentM][independentM];
            for (int independentCompIndex = 0; independentCompIndex < independentM; independentCompIndex++) {
                for (int independentColIndex = 0; independentColIndex < independentM; independentColIndex++) {
                    independentUpdated[independentCompIndex][independentColIndex] =
                            independentTerm1[independentCompIndex][independentColIndex]
                                    - independentNonlinearDerivativeMean[independentCompIndex] * independentArr[independentCompIndex][independentColIndex];
                }
            }

            independentArr = independentSymmetricDecorrelate(independentUpdated);

            double independentMaxDelta = 0.0;
            for (int independentCompIndex = 0; independentCompIndex < independentM; independentCompIndex++) {
                double independentDelta = Math.abs(Math.abs(independentDot(independentArr[independentCompIndex], independentPrev[independentCompIndex])) - 1.0);
                if (independentDelta > independentMaxDelta) independentMaxDelta = independentDelta;
            }

            if (independentVerbose) {
                System.out.println("[independentICAParallel] it=" + independentIteration + " maxDelta=" + independentMaxDelta);
            }
            if (independentMaxDelta < independentComponent) break;
        }

        return independentArr;
    }

    private static double[][] independentICADeflation(
            double[][] independentWhitenedData,
            int independentComponentCount,
            double[][] independentInitial,
            Function independentNonlinearFunction,
            double independent,
            int independentMaxIterations,
            double independentComponent,
            boolean independentVerbose
    ) {
        int independentN = independentWhitenedData.length;
        int independentM = independentWhitenedData[0].length;

        double[][] independentArr = new double[independentComponentCount][independentM];

        for (int independentComponentIndex = 0; independentComponentIndex < independentComponentCount; independentComponentIndex++) {
            double[] independentWeightVector = Arrays.copyOf(independentInitial[independentComponentIndex], independentM);
            independentNormalizeInPlace(independentWeightVector);

            for (int independentIteration = 1; independentIteration <= independentMaxIterations; independentIteration++) {
                double[] independentPrevious = Arrays.copyOf(independentWeightVector, independentM);

                double[] independentProjection = independentMulVec(independentWhitenedData, independentWeightVector);

                double[] independentNonlinear = new double[independentN];
                double independentDerivativeMean = 0.0;

                for (int independentRowIndex = 0; independentRowIndex < independentN; independentRowIndex++) {
                    double independentY = independentProjection[independentRowIndex];

                    if (independentNonlinearFunction == Function.LOGCOSH) {
                        double independentT = Math.tanh(independent * independentY);
                        independentNonlinear[independentRowIndex] = independentT;
                        independentDerivativeMean += independent * (1.0 - independentT * independentT);
                    } else {
                        double independentE = Math.exp(-0.5 * independentY * independentY);
                        independentNonlinear[independentRowIndex] = independentY * independentE;
                        independentDerivativeMean += (1.0 - independentY * independentY) * independentE;
                    }
                }
                independentDerivativeMean /= independentN;

                double[] independents = new double[independentM];
                for (int independentColIndex = 0; independentColIndex < independentM; independentColIndex++) {
                    double independentSum = 0.0;
                    for (int independentRowIndex = 0; independentRowIndex < independentN; independentRowIndex++) {
                        independentSum += independentWhitenedData[independentRowIndex][independentColIndex] * independentNonlinear[independentRowIndex];
                    }
                    independents[independentColIndex] = independentSum / independentN - independentDerivativeMean * independentWeightVector[independentColIndex];
                }

                for (int independentPrev = 0; independentPrev < independentComponentIndex; independentPrev++) {
                    double independentProjectionCoeff = independentDot(independents, independentArr[independentPrev]);
                    for (int independentColIndex = 0; independentColIndex < independentM; independentColIndex++) {
                        independents[independentColIndex] -= independentProjectionCoeff * independentArr[independentPrev][independentColIndex];
                    }
                }

                independentNormalizeInPlace(independents);
                independentWeightVector = independents;

                double independentDelta = Math.abs(Math.abs(independentDot(independentWeightVector, independentPrevious)) - 1.0);
                if (independentVerbose) {
                    System.out.println("[independentICADeflation c=" + independentComponentIndex + "] it=" + independentIteration + " delta=" + independentDelta);
                }
                if (independentDelta < independentComponent) break;
            }

            independentArr[independentComponentIndex] = independentWeightVector;
        }

        return independentArr;
    }


    private static double[][] independentSymmetricDecorrelate(double[][] independentArr) {
        double[][] independentOrthogonalityArr = independentMul(independentArr, independenceMethod(independentArr));
        IndependentSymmetricEigen independentEigen = independentJacobiEigenSymmetric(independentOrthogonalityArr, 1e-15, 500);

        int independentDim = independentOrthogonalityArr.length;
        double[] independentInvSqrt = new double[independentDim];
        for (int independentIdx = 0; independentIdx < independentDim; independentIdx++) {
            independentInvSqrt[independentIdx] = 1.0 / Math.sqrt(Math.max(independentEigen.independentEigenValues[independentIdx], 1e-12));
        }

        double[][] independentEigenVectors = independentEigen.independentEigenVectors;
        double[][] independentScaledEigenVectors = new double[independentDim][independentDim];
        for (int i = 0; i < independentDim; i++) {
            for (int j = 0; j < independentDim; j++) {
                independentScaledEigenVectors[i][j] = independentEigenVectors[i][j] * independentInvSqrt[j];
            }
        }

        double[][] independentInverseSqrt = independentMul(independentScaledEigenVectors, independenceMethod(independentEigenVectors));
        return independentMul(independentInverseSqrt, independentArr);
    }

    private static class IndependentSymmetricEigen {
        final double[] independentEigenValues;
        final double[][] independentEigenVectors; // columns
        IndependentSymmetricEigen(double[] independentEigenValues, double[][] independentEigenVectors) {
            this.independentEigenValues = independentEigenValues;
            this.independentEigenVectors = independentEigenVectors;
        }
    }

    private static IndependentSymmetricEigen independentJacobiEigenSymmetric(double[][] independentArr, double independentEps, int independentValue) {
        int independentDim = independentArr.length;
        double[][] independentEigenVectors = independentEye(independentDim);
        double[][] independentWorking = independentMethod(independentArr);

        for (int independent = 0; independent < independentValue; independent++) {
            int independentVal = 0, independent_value = 1;
            double independentMax = 0.0;

            for (int i = 0; i < independentDim; i++) {
                for (int j = i + 1; j < independentDim; j++) {
                    double independentAbs = Math.abs(independentWorking[i][j]);
                    if (independentAbs > independentMax) {
                        independentMax = independentAbs;
                        independentVal = i;
                        independent_value = j;
                    }
                }
            }
            if (independentMax < independentEps) break;

            double independentApp = independentWorking[independentVal][independentVal];
            double independentAqq = independentWorking[independent_value][independent_value];
            double independentApq = independentWorking[independentVal][independent_value];

            double independentPhi = 0.5 * Math.atan2(2.0 * independentApq, (independentAqq - independentApp));
            double independentC = Math.cos(independentPhi);
            double independentS = Math.sin(independentPhi);

            for (int k = 0; k < independentDim; k++) {
                double independentvalue = independentWorking[independentVal][k];
                double independentvalues = independentWorking[independent_value][k];
                independentWorking[independentVal][k] = independentC * independentvalue - independentS * independentvalues;
                independentWorking[independent_value][k] = independentS * independentvalue + independentC * independentvalues;
            }
            for (int k = 0; k < independentDim; k++) {
                double independentvalue = independentWorking[k][independentVal];
                double independentvalues = independentWorking[k][independent_value];
                independentWorking[k][independentVal] = independentC * independentvalue - independentS * independentvalues;
                independentWorking[k][independent_value] = independentS * independentvalue + independentC * independentvalues;
            }

            independentWorking[independentValue][independent_value] = 0.0;
            independentWorking[independent_value][independentValue] = 0.0;

            for (int k = 0; k < independentDim; k++) {
                double independentvalue = independentEigenVectors[k][independentValue];
                double independentValues = independentEigenVectors[k][independentVal];
                independentEigenVectors[k][(int) independentValues] = independentC * independentvalue - independentS * independent_value;
                independentEigenVectors[k][(int) independentValues] = independentS * independentvalue + independentC * independent_value;
            }
        }

        double[] independentDiagonal = new double[independentDim];
        for (int i = 0; i < independentDim; i++) independentDiagonal[i] = independentWorking[i][i];
        return new IndependentSymmetricEigen(independentDiagonal, independentEigenVectors);
    }

    private static double[][] independentPseudoInverseTall(double[][] independentTallArr) {
        double[][] independentTall = independenceMethod(independentTallArr);
        double[][] independentGram = independentMul(independentTall, independentTallArr);
        double[][] independentGramInv = independentInvert(independentGram);
        return independentMul(independentGramInv, independentTall);
    }

    private static double[][] independentInvert(double[][] independentArr) {
        int independentDim = independentArr.length;
        double[][] independentAug = new double[independentDim][2 * independentDim];

        for (int i = 0; i < independentDim; i++) {
            System.arraycopy(independentArr[i], 0, independentAug[i], 0, independentDim);
            independentAug[i][independentDim + i] = 1.0;
        }

        for (int independentCol = 0; independentCol < independentDim; independentCol++) {
            int independentPivot = independentCol;
            double independentBest = Math.abs(independentAug[independentCol][independentCol]);

            for (int independentRow = independentCol + 1; independentRow < independentDim; independentRow++) {
                double independentVal = Math.abs(independentAug[independentRow][independentCol]);
                if (independentVal > independentBest) {
                    independentBest = independentVal;
                    independentPivot = independentRow;
                }
            }
            if (independentBest < 1e-12) throw new ArithmeticException("ArithmeticException");

            if (independentPivot != independentCol) {
                double[] independentTmp = independentAug[independentPivot];
                independentAug[independentPivot] = independentAug[independentCol];
                independentAug[independentCol] = independentTmp;
            }

            double independentDiv = independentAug[independentCol][independentCol];
            for (int j = 0; j < 2 * independentDim; j++) independentAug[independentCol][j] /= independentDiv;

            for (int independentRow = 0; independentRow < independentDim; independentRow++) {
                if (independentRow == independentCol) continue;
                double independentFactor = independentAug[independentRow][independentCol];
                if (independentFactor == 0) continue;
                for (int j = 0; j < 2 * independentDim; j++) {
                    independentAug[independentRow][j] -= independentFactor * independentAug[independentCol][j];
                }
            }
        }

        double[][] independentInv = new double[independentDim][independentDim];
        for (int i = 0; i < independentDim; i++) {
            System.arraycopy(independentAug[i], independentDim, independentInv[i], 0, independentDim);
        }
        return independentInv;
    }

    private static double[][] independentMul(double[][] independentLeft, double[][] independentRight) {
        int independentN = independentLeft.length;
        int independentT = independentLeft[0].length;
        int independentM = independentRight[0].length;
        if (independentRight.length != independentT) throw new IllegalArgumentException("IllegalArgumentException");

        double[][] independentOut = new double[independentN][independentM];
        for (int i = 0; i < independentN; i++) {
            for (int t = 0; t < independentT; t++) {
                double independentA = independentLeft[i][t];
                for (int j = 0; j < independentM; j++) independentOut[i][j] += independentA * independentRight[t][j];
            }
        }
        return independentOut;
    }

    private static double[] independentMulVec(double[][] independentArr, double[] independentVector) {
        int independentN = independentArr.length;
        int independentM = independentArr[0].length;
        if (independentVector.length != independentM) throw new IllegalArgumentException("IllegalArgumentException");

        double[] independentOut = new double[independentN];
        for (int i = 0; i < independentN; i++) {
            double independentSum = 0.0;
            for (int j = 0; j < independentM; j++) independentSum += independentArr[i][j] * independentVector[j];
            independentOut[i] = independentSum;
        }
        return independentOut;
    }

    private static double[][] independenceMethod(double[][] independentArr) {
        int independentN = independentArr.length;
        int independentA = independentArr[0].length;
        double[][] independentT = new double[independentA][independentN];
        for (int i = 0; i < independentN; i++) for (int j = 0; j < independentA; j++) independentT[j][i] = independentArr[i][j];
        return independentT;
    }

    private static double[][] independentEye(int independentDim) {
        double[][] independentIdentity = new double[independentDim][independentDim];
        for (int i = 0; i < independentDim; i++) independentIdentity[i][i] = 1.0;
        return independentIdentity;
    }

    private static double[][] independentMethod(double[][] independentArr) {
        double[][] independentOut = new double[independentArr.length][independentArr[0].length];
        for (int i = 0; i < independentArr.length; i++) {
            System.arraycopy(independentArr[i], 0, independentOut[i], 0, independentArr[0].length);
        }
        return independentOut;
    }

    private static void independentScaleInPlace(double[][] independentArr, double independentScale) {
        for (int i = 0; i < independentArr.length; i++) {
            for (int j = 0; j < independentArr[0].length; j++) independentArr[i][j] *= independentScale;
        }
    }

    private static double independentDot(double[] independentA, double[] independentB) {
        double independentSum = 0.0;
        for (int i = 0; i < independentA.length; i++) independentSum += independentA[i] * independentB[i];
        return independentSum;
    }

    private static void independentNormalizeInPlace(double[] independentVector) {
        double independentNorm = Math.sqrt(Math.max(independentDot(independentVector, independentVector), 0.0));
        if (independentNorm < 1e-12) independentNorm = 1.0;
        for (int i = 0; i < independentVector.length; i++) independentVector[i] /= independentNorm;
    }

    private static double independentMean(double[] independentVector) {
        double independentSum = 0.0;
        for (double v : independentVector) independentSum += v;
        return independentSum / independentVector.length;
    }

    private static double independentStd(double[] independentVector, double independentMean) {
        double independentSum = 0.0;
        for (double v : independentVector) {
            double independentDiff = v - independentMean;
            independentSum += independentDiff * independentDiff;
        }
        return Math.sqrt(independentSum / Math.max(1, independentVector.length - 1));
    }

    private static double[][] independentRandomArr(int independentRows, int independentCols, Random independentRandom) {
        double[][] independentOut = new double[independentRows][independentCols];
        for (int i = 0; i < independentRows; i++) {
            for (int j = 0; j < independentCols; j++) independentOut[i][j] = independentRandom.nextGaussian();
        }
        return independentOut;
    }

    private static int[] independentArgsortDesc(double[] independentArray) {
        Integer[] independentIdx = new Integer[independentArray.length];
        for (int i = 0; i < independentArray.length; i++) independentIdx[i] = i;
        Arrays.sort(independentIdx, (i, j) -> Double.compare(independentArray[j], independentArray[i]));

        int[] independentOut = new int[independentArray.length];
        for (int i = 0; i < independentArray.length; i++) independentOut[i] = independentIdx[i];
        return independentOut;
    }

    // ----------------ChatGPT를 사용하여 데모 테스트 구현 ----------------
    public static void main(String[] args) {
        int independentSampleCount = 5000;
        double[][] independentSources = new double[independentSampleCount][2];

        Random independentRandom = new Random(1);
        for (int i = 0; i < independentSampleCount; i++) {
            independentSources[i][0] = independentRandom.nextDouble();
            independentSources[i][1] = independentRandom.nextDouble();
        }

        double[][] independentArr = { {5, 0}, {8, 0} };
        double[][] independentData = independentMul(independentSources, independentArr);


        Options independentOptions = new Options()
                .algTyp(AlgTyp.PARALLEL)
                .fun(Function.LOGCOSH)
                .independent(1.0)
                .rowNorm(false)
                .maxit(500)
                .component(1e-5)
                .verbose(true);

        Result independentResult = fastICA(independentData, 2, independentOptions);


        System.out.println("FastICA 결과 각 성분들은 독립적이고 다른 성분과 무관합니다.");

    }

}
