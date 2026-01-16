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

     * 문제 해결 방법
       외부 공기 중심의 탐색
       가장 중요한 포인트는 '치즈 내부의 공간'과 '치즈 외부의 공기'를 구분하는 것입니다.
       시작점 (0, 0): 문제 조건상 판의 가장자리에는 치즈가 놓이지 않으므로, (0, 0)은 항상 외부 공기입니다.
       탐색 범위: (0, 0)에서 시작해 연결된 모든 공기를 탐색합니다.

   * 알고리즘 동작 설명
      1. (0, 0) 좌표부터 BFS를 시작
      2. 시간 측정 및 치즈 개수 기록

   * 성능 최적화(Performance Optimization)
   성능을 더 좋게 만들기 위한 추가 방향을 작성해보았습니다.
   성능을 더 향상시키기 위해 부족한 부분이나 필요한 부분을 추가적으로 작성할 계획입니다..!
      1. 치즈 개수를 캐싱할 수 있도록 추가
        기존 solution()은 유지하되, 추가로 cheeseCount를 두어 최적화 시킵니다.
        이를 통해 성능을 향상시킬 수 있습니다.
      2. 치즈 카운팅 최적화
        처음 입력 단계에서 전체 치즈 개수를 저장해두고 BFS를 실행합니다.
        특히 치즈가 많은 큰 입력에서 성능을 향상시킬 수 있습니다.
      3. 메인 시뮬레이션 루프 최적화
        BFS 수행 시의 치즈 개수를 반환하도록 하여 메인 시뮬레이션 루프를 최적화합니다.
        이렇게 하면 메인 루프에서 출력 값을 효율적으로 관리할 수 있습니다.
      4. BufferedWriter를 추가 사용하여 출력 성능 향상
        BufferedWriter를 사용하여 출력 성능을 향상시킵니다.
        출력 내용을 버퍼에 모아 출력할 수 있어 I/O 성능을 더 안정적으로 만듭니다.

     * 안정성 강화
     안정성을 더 향상시키기 위해 필요한 부분을 추가적으로 작성할 계획입니다
     1. 탐색 시작점 검증 로직 추가
      - BFS 시작 전 (0,0)이 외부 공기임을 명시적으로 검증하거나,조건이 변경될 경우를 대비해 외곽 공기 탐색 로직을 확장할 수 있습니다.
      이를 통해 입력 조건 변화에도 안정적으로 동작하는 구조를 확보할 수 있습니다.

    2.경계 처리 로직의 일반화
    - 현재는 보드 경계를 직접 비교하여 처리하고 있으나, 가상의 외곽 패딩 영역을 추가하는 방식으로 확장하면
       경계 조건 처리의 안정성과 가독성을 동시에 향상시킵니다.

    3. BFS 상태 관리 구조 확장
     - 방문 배열(visited)을 시간 단위로 명확히 분리하거나 상태 객체로 관리하도록 확장하여,
       디버깅 및 시뮬레이션 흐름 추적의 안정성을 강화합니다.

    4. 입력 데이터 검증 및 예외 처리 보완
     - 입력 크기, 값 범위, 형식에 대한 검증 로직을 추가함으로써 비정상 입력이나 경계 상황에서도 예외 없이
      안정적으로 실행되는 프로그램으로 확장합니다.



    * 문제를 풀 때 코드에서 발생할 수 있는 문제점들과
    잘못된 점, 놓친 점, 미처 생각하지 못했던 점들 등 여러 요소들을 신중하게 고려하여 문제를 풀고 주석을 계속 작성할 예정입니다..!

    * 유일성 및 일관성
    1. 치즈의 유일성
    - BFS 탐색을 진행하며 치즈를 한 시간에 한 번만 작업을 수행합니다.
    치즈를 발견하고 visited[nx][ny] = true를 수행합니다.

    2. 치즈 개수 기록의 유일성
    - solution()을 BFS 이전에 단 한 번 호출하여,
    해당 작업에 존재하는 치즈 개수를 정확히 기록합니다.

    3. 일관성 유지
    - 방문 배열 초기화, 탐색 순서, 상태 갱신 규칙을 고정된 구조로 유지함으로써
    매 반복마다 동일한 논리 흐름을 보장합니다.

    4. 단계적 시뮬레이션 구성
    - 메인 루프는 매 반복마다 하나의 시뮬레이션 단계를 형성하며,이전 단계에서 형성된 상태를 기반으로 탐색 조건을 추가합니다.
    이 구조는 전체 실행 흐름을 시간 축에 따라 일관되게 연결합니다.


    * */

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
