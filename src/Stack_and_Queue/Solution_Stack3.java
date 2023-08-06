package Stack_and_Queue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Solution_Stack3 { 	// 크레인 인형뽑기(카카오)

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[][] board = new int[N][N];
		StringTokenizer st;
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j <N; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		int M = Integer.parseInt(br.readLine());
		int[] moves = new int[M];
		st = new StringTokenizer(br.readLine());
		for(int k = 0; k < M; k++) moves[k] = Integer.parseInt(st.nextToken());
		
		System.out.println(solution(N, board, M, moves));
	}

	public static int solution(int n, int[][] board, int m, int[] moves) {
		int ans = 0;
		Stack<Integer> stack = new Stack<>();
		for(int pos : moves) { // 인형을 뽑을 위치 하나씩 꺼내오기
			for(int i = 0; i<board.length; i++) { // borad의 행 크기만큼 돌기
				if(board[i][pos-1]!=0) { //크레인 내림 -> 인형이 있으면
					int tmp = board[i][pos-1]; //인형 뽑음
					board[i][pos-1] = 0; //인형 뽑고 인형이 있던 위치 0으로
					if(!stack.isEmpty() && tmp==stack.peek()) { //스택에 인형이 있고 뽑은 인형이 스택의 맨위에 있는 인형과 같으면
						ans += 2; // ans에 값 더해줌
						stack.pop(); // 뽑은 인형 pop
					}
					else stack.push(tmp); //그게 아니면 뽑은 인형 push
					break; //크레인 작업 끝내면 break
					
				}
				
			}
		}
	return ans;
}

}
