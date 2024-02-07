import java.io.*;
import java.util.*;

public class BAEKJOON16173 {
	static int N;
	static int[][] board;
	static boolean[][] visited;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		board = new int[N][N];
		visited = new boolean[N][N];
		StringTokenizer st;
		for(int i = 0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<N; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		DFS(0,0);
		if(visited[N-1][N-1] == true) System.out.println("HaruHaru");
		else System.out.println("Hing");
	}
	public static void DFS(int x, int y) {
		visited[x][y] = true;
		int[] dx = {board[x][y], 0};
		int[] dy = {0, board[x][y]};
			for(int i = 0; i<2; i++) {
				int nx = x + dx[i];
				int ny = y + dy[i];
				
				if(nx>=0 && nx<N && ny>=0 && ny<N && visited[nx][ny]==false) {
					DFS(nx,ny);
				}
			}
		}

}
