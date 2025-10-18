package Dijkstra;

import java.io.*;
import java.util.*;

//Escape Route
public class ojuz_EscapeRoute {
    static final long INF = Long.MAX_VALUE / 4; // 무한대 값 설정 (오버플로우 방지를 위해 Long.MAX_VALUE보다 작은 값 사용)

    // 간선(Edge) 정보를 저장하는 클래스
    static class Edge {
        int to; // 도착 노드
        long L; // 이동 시간 (Length of travel time)
        long C; // 마감 시간 (Cut-off time or deadline)
        public Edge(int to, long L, long C) {
            this.to = to;
            this.L = L;
            this.C = C;
        }
    }

    // 다익스트라 우선순위 큐에 사용될 노드 클래스
    static class Node implements Comparable<Node> {
        int v; // 현재 노드 인덱스
        long dist; // 쿼리 시작 시점으로부터 경과된 시간 (elapsed time)
        public Node(int v, long dist) { this.v = v; this.dist = dist; }
        // 우선순위 큐를 위해 dist를 기준으로 비교
        public int compareTo(Node o) { return Long.compare(this.dist, o.dist); }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in); // 빠른 입력을 위한 FastScanner 사용
        StringBuilder sb = new StringBuilder(); // 결과를 한 번에 출력하기 위한 StringBuilder

        int N = fs.nextInt(); // 노드(장소)의 개수
        int M = fs.nextInt(); // 간선(도로)의 개수
        long S = fs.nextLong(); // 주기의 길이 (S초마다 상황 반복)
        int Q = fs.nextInt(); // 쿼리의 개수

        // 그래프 인접 리스트 초기화 (인접 리스트의 배열)
        ArrayList<Edge>[] g = new ArrayList[N];
        for (int i = 0; i < N; i++) g[i] = new ArrayList<>();

        // 간선 정보를 저장할 배열
        int[] A = new int[M];
        int[] B = new int[M];
        long[] L = new long[M];
        long[] C = new long[M];

        for (int i = 0; i < M; i++) {
            int a = fs.nextInt(); // 시작 노드
            int b = fs.nextInt(); // 도착 노드
            long li = fs.nextLong(); // 이동 시간 L
            long ci = fs.nextLong(); // 마감 시간 C
            A[i] = a; B[i] = b; L[i] = li; C[i] = ci;

            // 양방향 그래프이므로 양쪽에 간선 추가
            g[a].add(new Edge(b, li, ci));
            g[b].add(new Edge(a, li, ci));
        }

        // 쿼리를 하나씩 처리
        for (int qi = 0; qi < Q; qi++) {
            int U = fs.nextInt(); // 시작 노드
            int V = fs.nextInt(); // 목표 노드
            long T = fs.nextLong(); // 절대 시작 시간 (0 <= T < S)

            // 단일 쿼리에 대한 다익스트라 실행
            long ans = dijkstraSingleQuery(N, g, S, U, V, T);
            sb.append(ans).append('\n'); // 결과 저장
        }

        System.out.print(sb.toString()); // 최종 결과 출력
    }

    // 단일 쿼리에 대해 다익스트라 실행
    // src: 시작 노드, target: 목표 노드, T0: 쿼리 시작 절대 시간 (mod S)
    static long dijkstraSingleQuery(int N, ArrayList<Edge>[] g, long S, int src, int target, long T0) {
        long[] dist = new long[N]; // 시작점으로부터의 최소 경과 시간
        Arrays.fill(dist, INF); // 거리 배열을 INF로 초기화
        PriorityQueue<Node> pq = new PriorityQueue<>(); // 우선순위 큐 초기화

        dist[src] = 0L; // 시작 노드의 경과 시간은 0
        pq.add(new Node(src, 0L)); // 큐에 시작 노드 추가

        while (!pq.isEmpty()) {
            Node cur = pq.poll(); // 최소 경과 시간을 가진 노드 추출
            int u = cur.v;
            long du = cur.dist; // 시작점으로부터 u까지의 최소 경과 시간

            if (du != dist[u]) continue; // 이미 더 짧은 경로를 찾았다면 스킵 (stale entry)
            if (u == target) {
                return du; // 목표 노드에 도달했으면 경과 시간 반환
            }

            // u에 도착한 절대 시간 = T0 + du
            long currentAbsolute = T0 + du;
            // 현재 절대 시간을 주기로 나눈 나머지 (현재 요일의 시간)
            long tmod = currentAbsolute % S;

            for (Edge e : g[u]) { // u의 모든 이웃(간선)에 대해 반복
                int v = e.to; // 도착 노드
                long li = e.L; // 이동 시간 L
                long ci = e.C; // 마감 시간 C

                // 해당 도로를 즉시 출발할 수 있는 최대 시간 (주기 내에서)
                // 출발 가능 시간 x는 [0, latestDepart] 이어야 함 (단, $x + L \le C$)
                long latestDepart = ci - li;
                long arrivalElapsed; // v에 도착할 때까지의 총 경과 시간

                if (tmod <= latestDepart) {
                    // 현재 시간(tmod)이 출발 가능 시간(latestDepart) 범위 내에 있다면,
                    // 즉시 출발 가능
                    arrivalElapsed = du + li; // u까지 경과 시간 + 이동 시간
                } else {
                    // 현재 시간(tmod)이 출발 가능 시간을 초과했다면,
                    // 다음 주기의 시작 시간(0)까지 기다려야 함
                    // 기다리는 시간 = S - tmod
                    long wait = S - tmod;
                    // 총 경과 시간 = u까지 경과 시간 + 기다리는 시간 + 이동 시간
                    arrivalElapsed = du + wait + li;
                }

                // v에 대한 새로운 경과 시간이 기존 값보다 작다면 갱신
                if (arrivalElapsed < dist[v]) {
                    dist[v] = arrivalElapsed;
                    pq.add(new Node(v, arrivalElapsed)); // 큐에 추가
                }
            }
        }
        // 목표 노드에 도달할 수 없다면 -1 반환 (문제 조건상 연결되어 있다고 가정될 수 있음)
        return -1;
    }

    // ---------- FastScanner for 빠른 입력 ----------
    static class FastScanner {
        private final InputStream in; // 입력 스트림
        private final byte[] buffer = new byte[1 << 16]; // 버퍼 크기
        private int ptr = 0, len = 0; // 포인터 및 길이
        FastScanner(InputStream is) { in = is; }

        private int read() throws IOException {
            if (ptr >= len) { // 버퍼를 모두 사용한 경우
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        // 토큰(공백으로 구분된 문자열)을 읽는 메서드
        String next() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = read()) <= 32) { // 공백(ASCII <= 32) 건너뛰기
                if (c == -1) return "";
            }
            while (c > 32) { // 토큰의 끝까지 읽기
                sb.append((char) c);
                c = read();
            }
            return sb.toString();
        }
    }
}