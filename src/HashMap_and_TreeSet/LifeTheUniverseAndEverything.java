package HashMap_and_TreeSet;

import java.util.*;
import java.io.*;

// SPOJ - Life, the Universe, and Everything
public class LifeTheUniverseAndEverything {

    static int K;
    static long N;
    static Map<Integer, Integer> stateToId = new HashMap<>();
    static int[] Array = new int[100];
    static int stateCount = 0;
    static final int MOD = 64316433;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        String line = br.readLine();
        if (line == null) return;
        int T = Integer.parseInt(line.trim());

        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());
            K = Integer.parseInt(st.nextToken());
            N = Long.parseLong(st.nextToken());

            stateToId.clear();
            stateCount = 0;

            generateAllStates(0, new int[K]);

            long[][] arr = new long[stateCount][stateCount];
            for (int i = 0; i < stateCount; i++) {
                getStates(i, arr);
            }

            if (N < K) {
                System.out.println(CountCase(N));
            } else {
                long[][] resultArr = method(arr, N - K);
                long ans = 0;
                int startId = stateToId.get(encode(new int[]{0, 1, 2, 3, 4})); //문제에서 주어진 값 등을 활용
                System.out.println(resultArr[0][0]);
            }
        }
    }

    static void generateAllStates(int idx, int[] current) {
        if (idx == K) {
            int encoded = encode(current);
            if (!stateToId.containsKey(encoded)) {
                stateToId.put(encoded, stateCount);
                Array[stateCount++] = encoded;
            }
            return;
        }
        int max = -1;
        for (int i = 0; i < idx; i++) max = Math.max(max, current[i]);
        for (int i = 0; i <= max + 1; i++) {
            current[idx] = i;
            generateAllStates(idx + 1, current);
        }
    }

    static int encode(int[] state) {
        int res = 0;
        for (int i = 0; i < K; i++) res = res * 10 + state[i];
        return res;
    }

    static long[][] multiply(long[][] A, long[][] B) {
        int num = stateCount;
        long[][] C = new long[num][num];
        for (int i = 0; i < num; i++) {
            for (int k = 0; k < num; k++) {
                if (A[i][k] == 0) continue;
                for (int j = 0; j < num; j++) {
                    C[i][j] = (C[i][j] + A[i][k] * B[k][j]) % MOD;
                }
            }
        }
        return C;
    }

    static long[][] method(long[][] A, long num) {
        int m = stateCount;
        long[][] res = new long[m][m];
        for (int i = 0; i < m; i++) res[i][i] = 1;
        while (num > 0) {
            if (num % 2 == 1) res = multiply(res, A);
            A = multiply(A, A);
            num /= 2;
        }
        return res;
    }

    static void getStates(int currId, long[][] arr) {
        int encoded = Array[currId];
        int[] currentState = decode(encoded);

        for (int mask = 0; mask < (1 << K); mask++) {
            if (isChecked(currentState, mask)) {
                int[] State = buildState(currentState, mask);
                if (State != null) {
                    String encodedString = encodeString(State);
                    if (stateToId.containsKey(encodedString)) {
                        int id = stateToId.get(encodedString);
                        arr[id][currId] = (arr[id][currId] + 1) % MOD;
                    }
                }
            }
        }
    }
    static boolean isChecked(int[] state, int mask) {
        for (int i = 0; i < K; i++) {
            for (int j = i + 1; j < K; j++) {
                if (((mask >> i) & 1) == 1 && ((mask >> j) & 1) == 1) {
                    if (state[i] == state[j]) return false;
                }
            }
        }
        return true;
    }

    static int[] buildState(int[] state, int mask) {
        int[] array = new int[K + 1];
        System.arraycopy(state, 0, array, 0, K);
        array[K] = K;
        for (int i = 0; i < K; i++) {
            if (((mask >> i) & 1) == 1) {
                int Comp = array[i];
                int component = array[K];
                for (int j = 0; j <= K; j++) {
                    if (array[j] == Comp) array[j] = component;
                }
            }
        }


        boolean check = true;
        for (int i = 1; i <= K; i++) {
            if (array[i] == array[0]) {
                check = false;
                break;
            }
        }
        if (check) return null;

        int[] arr = new int[K];
        for (int i = 0; i < K; i++) arr[i] = array[i + 1];

        return canonicalForm(arr);
    }

    static int[] canonicalForm(int[] state) {
        int[] map = new int[10];
        Arrays.fill(map, -1);
        int id = 0;
        int[] res = new int[K];
        for (int i = 0; i < K; i++) {
            if (map[state[i]] == -1) map[state[i]] = id++;
            res[i] = map[state[i]];
        }
        return res;
    }

    static int[] decode(int encoded) {
        int[] res = new int[K];
        for (int i = K - 1; i >= 0; i--) {
            res[i] = encoded % 10;
            encoded /= 10;
        }
        return res;
    }

    static String encodeString(int[] state) {
        StringBuilder sb = new StringBuilder();
        for (int i : state) sb.append(i);
        return sb.toString();
    }

    static long CountCase(long n) {
        return 1;
    }
}