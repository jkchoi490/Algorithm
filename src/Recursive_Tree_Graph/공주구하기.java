package Recursive_Tree_Graph;

// 공주 구하기
import java.util.*;

public class 공주구하기 {
    static class Island {
        int dist, power, usable;
        Island(int d, int p, int u) {
            dist = d;
            power = p;
            usable = u;
        }
    }

    static int N;
    static Island[] islands;
    static List<Integer>[] forwardGraph;
    static List<Integer>[] backwardGraph;
    static int MOD = 1000;
    static int[] memoForward;
    static int[] memoBackward;
    static boolean[] visitedForward;
    static boolean[] visitedBackward;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();

        islands = new Island[N];
        for (int i = 0; i < N; i++) {
            int d = sc.nextInt();
            int p = sc.nextInt();
            int u = sc.nextInt();
            islands[i] = new Island(d, p, u);
        }

        // 그래프 초기화
        forwardGraph = new ArrayList[N];
        backwardGraph = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            forwardGraph[i] = new ArrayList<>();
            backwardGraph[i] = new ArrayList<>();
        }

        // 그래프 구성
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                int distDiff = islands[j].dist - islands[i].dist;
                if (distDiff <= islands[i].power) {
                    forwardGraph[i].add(j); // 전진용 간선
                } else break; // 정렬되어 있으므로 더 멀면 불필요
            }

            // 공주 업은 상태에서 가능한 점프 (뒤로)
            if (islands[i].usable == 1) {
                for (int j = i - 1; j >= 0; j--) {
                    int distDiff = islands[i].dist - islands[j].dist;
                    if (distDiff <= islands[i].power) {
                        backwardGraph[i].add(j);
                    } else break;
                }
            }
        }

        // DFS + 메모이제이션
        memoForward = new int[N];
        memoBackward = new int[N];
        visitedForward = new boolean[N];
        visitedBackward = new boolean[N];

        int forwardWays = dfsForward(0);
        int backwardWays = dfsBackward(N - 1);

        System.out.println((forwardWays * backwardWays) % MOD);
    }

    // 유시섬 -> 후퍼섬
    static int dfsForward(int cur) {
        if (cur == N - 1) return 1;
        if (visitedForward[cur]) return memoForward[cur];
        visitedForward[cur] = true;

        int sum = 0;
        for (int next : forwardGraph[cur]) {
            sum = (sum + dfsForward(next)) % MOD;
        }

        return memoForward[cur] = sum;
    }

    // 후퍼섬 -> 유시섬
    static int dfsBackward(int cur) {
        if (cur == 0) return 1;
        if (visitedBackward[cur]) return memoBackward[cur];
        visitedBackward[cur] = true;

        int sum = 0;
        for (int prev : backwardGraph[cur]) {
            sum = (sum + dfsBackward(prev)) % MOD;
        }

        return memoBackward[cur] = sum;
    }
}
