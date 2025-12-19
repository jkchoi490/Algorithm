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

    /*
    * 코드의 기술적 설명

    - 자료구조 : Queue<int[]>를 활용하여 좌표 정보를 관리합니다.
    - 방문처리 : visited[][] 배열을 사용하여 공기 탐색 시 중복 방문 및 무한 루프를 방지합니다.
    - 방향 벡터 : dx, dy 배열을 통해 상하좌우 인접 칸을 깔끔하게 탐색합니다.
    - solution : BFS를 수행하기 전 solution을 먼저 호출하여 현재 개수를 기록합니다.

    * 문제 해결 로직
    전체적인 프로그램 흐름은 다음과 같은 반복 구조를 가집니다.

    - solution() 호출 (치즈 카운팅): * 현재 보드에 남아있는 치즈(1)의 개수를 세어 저장합니다.

    - BFS() 실행:
        (0, 0)부터 시작하여 사방으로 공기를 타고 나갑니다.
        0 이면 : 큐에 넣어 탐색을 계속합니다.
        1 이면 : 큐에 넣지는 않되, 값을 0으로 바꿉니다
        time++: 1시간이 경과했음을 표시하고 다시 1번으로 돌아갑니다.
    */

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
/*
    * BFS (Breadth-First Search) 알고리즘 이란?
    BFS, 즉 너비 우선 탐색은
    그래프(Graph)나 트리(Tree)와 같은 자료 구조에서 시작 노드에서 가까운
    노드들을 먼저 탐색하는 알고리즘입니다.
    이름 그대로 "너비(가로)"를 우선하여 같은 레벨(깊이)에 있는 노드들을 모두 탐색한 후, 다음 레벨로 넘어가는 방식입니다.

    * 핵심 원리 및 작동 방식
    BFS는 큐(Queue) 자료구조를 사용하여 탐색 순서를 관리합니다.
    시작 노드를 큐에 넣고 방문 처리합니다.
    큐가 빌 때까지 다음을 반복합니다:
	    큐에서 노드를 하나 꺼냅니다 (Dequeue).
	    꺼낸 노드에 연결된 모든 인접 노드들을 확인합니다.
	    아직 방문하지 않은 인접 노드가 있다면, 해당 노드를 방문 처리하고 큐에 넣습니다 (Enqueue).
    이러한 방식으로, 큐에는 항상 현재 탐색하고 있는 노드의 다음 레벨 노드들이 차례로 저장되므로, 너비 방향으로 탐색이 진행됩니다.
    * 주요 특징 및 장점
    - 최단 경로 보장: 가중치가 없는 그래프(Unweighted Graph)에서 시작 노드로부터 목표 노드까지의 최단 경로(Shortest Path)를 찾는 것을 보장합니다.
    이는 같은 깊이의 노드를 먼저 탐색하기 때문입니다.
*/
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
