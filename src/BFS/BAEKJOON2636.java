package BFS;

import java.io.*;
import java.util.*;

//[BOJ] 02636 치즈
public class BAEKJOON2636 {
    static int n, m; //n: 행(세로), m: 열(가로)
    static int[][] board; //치즈 보드
    static boolean[][] visited; // BFS를 위한 배열
    static int[] dx = {1, -1, 0, 0}; //x(행) 방향 벡터
    static int[] dy = {0, 0, 1, -1}; //y(행) 방향 벡터

    public static void main(String[] args) throws IOException {
        // 입력을 받기 위한 설정
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n과 m 입력
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        //입력받기
        board = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int time = 0;
        int cnt = 0;

        // 메인 시뮬레이션 루프
        while (true) {
            // solution 함수 실행
            int cheese = solution();

            if (cheese == 0) {
                break;
            }
            // 현재 치즈 개수를 cnt에 저장
            cnt = cheese;

            // BFS를 실행
            BFS();
            // 시간 증가
            time++;
        }

        // 결과 출력
        System.out.println(time); // 수행 시간
        System.out.println(cnt); // 치즈 개수
    }

    //solution 함수 : 현재 보드의 치즈 개수 카운팅
    static int solution() {
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == 1) cnt++; //치즈이면 카운트
            }
        }
        return cnt;
    }

    // BFS 함수
    static void BFS() {
        // 매 시간마다 새로운 BFS를 시작하므로 visited 배열을 초기화
        visited = new boolean[n][m];

        // 큐를 사용하여 BFS를 수행
        Queue<int[]> q = new LinkedList<>();
        // 시작점: (0, 0)은 항상 외곽 공기라고 가정하고 시작 (보드의 가장자리)
        q.add(new int[]{0, 0});
        visited[0][0] = true;

        // 상하좌우 네 방향 탐색
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0];
            int y = cur[1];

            for (int dir = 0; dir < 4; dir++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];
                // 보드의 경계를 벗어나는지 확인
                if (nx < 0 || ny < 0 || nx >= n || ny >= m) continue;
                // 이미 방문한 곳인지 확인
                if (visited[nx][ny]) continue;

                // 방문 처리
                visited[nx][ny] = true;
                //다음 위치(nx, ny)가 공기(0)인지 치즈(1)인지 확인
                if (board[nx][ny] == 0) {
                    // 공기인 경우
                    q.add(new int[]{nx, ny});
                } else if (board[nx][ny] == 1) {
                    // 치즈인 경우
                    board[nx][ny] = 0;
                }
            }
        }
    }
}
