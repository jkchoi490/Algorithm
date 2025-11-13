package Implementation;

import java.io.*;
import java.util.*;

// Contradiction Detection Algorithm (2-SAT + SCC)
public class ContradictionDetection {

    // ======================= SCC 클래스 =======================
    // Contradiction Detection Algorithm (2-SAT + SCC)
    // A → B 형태의 명제가 여러 개 주어졌을 때,
    // 전체가 모순인지 아닌지 판정하는 코드.
    // Strongly Connected Components (Kosaraju algorithm)
    // 2-SAT의 모순 판정에서, X와 ¬X가 같은 SCC에 존재하면 모순.
    static class SCC {
        int n;                  // 노드 개수 (2 * 변수 개수)
        List<Integer>[] g;      // 정방향 그래프
        List<Integer>[] rg;     // 역방향 그래프
        boolean[] used;         // dfs1 방문 체크
        List<Integer> order;    // 노드의 처리 순서
        int[] comp;             // comp[i] = i번 노드의 SCC 번호

        SCC(int n) {
            this.n = n;
            g = new ArrayList[n];
            rg = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                g[i] = new ArrayList<>();
                rg[i] = new ArrayList<>();
            }
            used = new boolean[n];
            order = new ArrayList<>();
            comp = new int[n];
        }

        // A → B (함의 그래프 간선 추가)
        void addEdge(int a, int b) {
            g[a].add(b);
            rg[b].add(a);   // 역방향 그래프에도 추가
        }

        // 1st DFS (정방향 그래프)
        // 종료 순서를 order 리스트에 저장한다.
        void dfs1(int v) {
            used[v] = true;
            for (int to : g[v]) {
                if (!used[to]) dfs1(to);
            }
            order.add(v); // 종료 시 push
        }

        // 2nd DFS (역방향 그래프) – 같은 SCC 번호 부여
        void dfs2(int v, int c) {
            comp[v] = c; // SCC 번호
            for (int to : rg[v]) {
                if (comp[to] == -1) dfs2(to, c);
            }
        }

        // Kosaraju 메인 함수
        int[] scc() {
            Arrays.fill(used, false);
            order.clear();

            // 1차 DFS: 정방향에서 postorder 저장
            for (int i = 0; i < n; i++) {
                if (!used[i]) dfs1(i);
            }

            // 2차 DFS: 역방향에서 SCC 구분
            Arrays.fill(comp, -1);
            int j = 0; // SCC 번호
            for (int i = n - 1; i >= 0; i--) {
                int v = order.get(i);
                if (comp[v] == -1) {
                    dfs2(v, j++);
                }
            }
            return comp;
        }
    }

    // ======================= 보조함수 =======================
    // 변수 번호 x의 "부정(¬x)" 노드 번호를 계산.
    // 2*i = x is True
    // 2*i+1 = x is False
    // x ^ 1 → 0→1, 1→0 True ↔ False 스위칭
    static int negate(int x) {
        return (x ^ 1);
    }

    // ======================= 메인 =======================
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String line = br.readLine();
            if (line == null || line.trim().isEmpty()) return;

            StringTokenizer st = new StringTokenizer(line);
            int n = Integer.parseInt(st.nextToken()); // 변수 개수
            int m = Integer.parseInt(st.nextToken()); // 명제 개수
            if (n == 0 && m == 0) return;             // 입력 종료 규칙

            // 변수는 0 ~ n-1
            // 각 변수 i는 두 개의 노드:
            // 2*i  = i가 True
            // 2*i+1 = i가 False
            SCC scc = new SCC(2 * n);

            // m개의 A → B 형태의 명제 입력
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int A = Integer.parseInt(st.nextToken());
                int B = Integer.parseInt(st.nextToken());

                // 논리 명제 A => B
                scc.addEdge(A, B);                       // A → B
                scc.addEdge(negate(B), negate(A));       // ¬B → ¬A (2-SAT 기본 변환)
            }

            // SCC 계산
            int[] comp = scc.scc();

            // ======================= 모순 판정 =======================
            boolean contradiction = false;

            for (int i = 0; i < n; i++) {
                // 같은 변수의 True/False 노드가 같은 SCC에 있다면 → 모순
                // 2*i = True 노드, 2*i+1 = False 노드
                if (comp[2*i] == comp[2*i + 1]) {
                    contradiction = true;
                    break;
                }
            }

            // 결과 출력
            if (contradiction) {
                System.out.println("CONTRADICTION");
            } else {
                System.out.println("NOT CONTRADICTORY");
            }
        }
    }
}
