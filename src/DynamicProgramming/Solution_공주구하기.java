package DynamicProgramming;

import java.io.*;
import java.util.*;

/**
 *  Dovelet - 공주 구하기
 *
 * 풀이 요약:
 * - 문제를 "유시 -> 후퍼 -> 유시"의 왕복으로 보지 않고,
 * "두 명(또는 두 경로)이 0에서 N-1까지 가되 내부 정점은 겹치지 않는다" 로 치환.
 * (한 명은 '가러가는' 경로, 다른 한 명은 '돌아오는' 경로의 역순을 의미)
 * - 순서대로 인덱스가 증가하는 방향으로만 이동하므로 DAG 성격을 가짐. (다이나믹 프로그래밍 가능)
 * - dp[a][b] := 현재까지 한 경로는 a번째 섬까지, 다른 경로는 b번째 섬까지 정했을 때
 * 앞으로 남은 섬들을 할당하는 경우의 수 (단, a,b는 아직 N-1에 도달하지 않은 상태 가능)
 * - 다음으로 고려할 섬은 i = max(a,b) + 1 부터 N-1 까지.
 * 각 i 에 대해
 * - 만약 a에서 i로 갈 수 있다면 (a가 출발지인 경로에 i를 넣음) dp[a][b] += dp[i][b]
 * - 만약 i에서 b로 (역으로 돌아오는 경로에서) 올 수 있다면 (b가 출발지인 경로에 i를 넣음) dp[a][b] += dp[a][i]
 * - 기저: a==N-1 || b==N-1 인 경우에 각 경로가 N-1에 도달했는지, 그리고 마지막 섬에서의 조건(돌아올 때 사용하는 발판의 사용 가능성 등)을 검사.
 *
 * 모듈로는 1000.
 */

//Dovelet - 공주 구하기
public class Solution_공주구하기  {
    static final int MOD = 1000; // 결과값을 나눌 모듈로 값 (1000)

    // 섬 정보 구조체
    static class Island {
        int d; // distance from start (유시 섬, 0번 섬으로부터의 거리)
        int p; // spring power (최대 도달거리)
        int r; // can use while carrying princess? (1: yes, 0: no) (공주를 업고도 사용 가능한지)
    }

    static int N; // 섬의 개수
    static Island[] land; // 섬 배열
    static int[][] dp; // -1은 아직 계산되지 않았음을 의미하는 DP 테이블

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in); // 빠른 입력을 위한 FastScanner 사용

        N = fs.nextInt(); // 섬 개수 입력
        land = new Island[N];
        for (int i = 0; i < N; i++) { // N개의 섬 정보 입력
            land[i] = new Island();
            land[i].d = fs.nextInt(); // 거리 d
            land[i].p = fs.nextInt(); // 점프력 p
            land[i].r = fs.nextInt(); // 공주와 함께 사용 가능 여부 r
        }

        dp = new int[N][N];
        for (int i = 0; i < N; i++) Arrays.fill(dp[i], -1); // DP 테이블 -1로 초기화

        // 시작 상태: 두 경로 모두 섬 0(유시섬)에서 출발 => rec(0,0)
        int ans = rec(0, 0);
        System.out.println(ans % MOD); // 결과 출력 (MOD는 rec 내에서 이미 적용되지만, 최종 결과에도 적용)
    }

    /**
     * rec(a, b) : a는 '가러가는(전진) 경로'가 마지막으로 선택한 섬 인덱스,
     * b는 '돌아오는(역순으로 생각한) 경로'가 마지막으로 선택한 섬 인덱스.
     *
     * invariant: 이미 0..max(a,b) 까지는 "결정"되어 있고, 다음으로 고려할 섬은 max(a,b)+1 부터.
     */
    static int rec(int a, int b) {
        // 경로 중 하나라도 최종 섬 (N-1)에 도달한 경우, 기저 조건 검사
        if (a == N - 1 || b == N - 1) {

            boolean can;
            if (a == N - 1 && b == N - 1) {
                // 두 경로 모두 N-1에 도달: 유효한 단일 경로 완성
                can = true;
            } else if (a == N - 1) {
                // 전진 경로가 N-1에 도달했을 때,
                // 돌아오는 경로(b)가 N-1을 종착점으로 가질 수 있는지 조건 검사
                // 조건: (a에서 N-1로 점프 가능) && (N-1 섬은 공주와 함께 사용 가능) && (N-1에서 b로 역점프 가능)
                can = (land[N - 1].d <= land[a].d + land[a].p) && (land[N - 1].r == 1)
                        && (land[N - 1].d - land[N - 1].p <= land[b].d);
            } else { // b == N-1 (대칭적인 상황)
                // 돌아오는 경로가 N-1에 도달했을 때,
                // 전진 경로(a)가 N-1을 종착점으로 가질 수 있는지 조건 검사 (조건은 a==N-1과 동일한 형태로 검사)
                can = (land[N - 1].d <= land[a].d + land[a].p) && (land[N - 1].r == 1)
                        && (land[N - 1].d - land[N - 1].p <= land[b].d);
                // 이 조건은 a==N-1 또는 b==N-1 일 때 모두 동일하게 적용됨 (참조 풀이와 일치)
            }
            return can ? 1 : 0; // 유효하면 1, 아니면 0 반환
        }

        if (dp[a][b] != -1) return dp[a][b]; // 이미 계산된 값이 있으면 반환

        int ret = 0; // 경우의 수 합계
        // 다음 후보 섬 인덱스: max(a,b)보다 1 큰 인덱스부터 N-1까지
        int start = Math.min(N - 1, Math.max(a, b) + 1);
        for (int i = start; i < N; i++) {
            // Option 1: 섬 i를 전진 경로(현재 a에서 끝남)에 할당.
            // 유효 조건: 섬 a에서 섬 i로 점프할 수 있는가?
            // (i의 거리) <= (a의 거리) + (a의 점프력)
            if (land[i].d <= land[a].d + land[a].p) {
                ret += rec(i, b); // 다음 상태: (i, b)로 재귀 호출
                if (ret >= MOD) ret -= MOD; // 덧셈 후 MOD 적용
            }

            // Option 2: 섬 i를 돌아오는 경로(역순으로 현재 b에서 끝남)에 할당.
            // 유효 조건:
            // (1) 섬 i의 점프대(p)를 공주를 업고도 사용 가능한가? (land[i].r == 1)
            // (2) 섬 i에서 섬 b로 역방향 점프가 가능한가? (i의 최소 도달 거리) <= (b의 거리)
            // (i의 거리) - (i의 점프력) <= (b의 거리)
            if (land[i].r == 1 && land[i].d - land[i].p <= land[b].d) {
                ret += rec(a, i); // 다음 상태: (a, i)로 재귀 호출
                if (ret >= MOD) ret -= MOD; // 덧셈 후 MOD 적용
            }
        }

        dp[a][b] = ret % MOD; // 최종 결과를 DP 테이블에 저장 후 반환
        return dp[a][b];
    }

    // ---------- FastScanner for 빠른 입력 ----------
    static class FastScanner {
        private final InputStream in; // 입력 스트림
        private final byte[] buffer = new byte[1 << 16]; // 버퍼 크기 (65536 바이트)
        private int ptr = 0, len = 0; // 현재 포인터와 읽어온 길이

        FastScanner(InputStream is) { in = is; } // 생성자

        private int read() throws IOException {
            if (ptr >= len) { // 버퍼를 모두 사용한 경우
                len = in.read(buffer); // 새로운 데이터 읽기
                ptr = 0; // 포인터 초기화
                if (len <= 0) return -1; // 더 이상 읽을 데이터가 없으면 -1 반환
            }
            return buffer[ptr++]; // 현재 바이트 반환 및 포인터 증가
        }

        int nextInt() throws IOException {
            int c;
            while ((c = read()) <= ' ') { // 공백 건너뛰기
                if (c == -1) return Integer.MIN_VALUE; // 파일 끝이면 최소값 반환
            }
            boolean neg = false; // 음수 여부
            if (c == '-') { neg = true; c = read(); } // 음수 부호 처리
            int val = 0;
            while (c > ' ') { // 숫자 읽기
                val = val * 10 + (c - '0'); // 숫자로 변환
                c = read();
            }
            return neg ? -val : val; // 음수 처리하여 최종 값 반환
        }
    }
}