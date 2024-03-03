import java.io.*;
import java.util.*;

public class BAEKJOON1926 {
	
	static int n,m, cnt = 0;
	static int[][] board;
	static ArrayList<Integer> pictures;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n  = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		board = new int[n][m];
		pictures = new ArrayList<>();
		int picture = 0;
		for(int i = 0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<m; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				if(board[i][j]==1) picture++;
			}
		}
		
		if(picture == 0) {
			System.out.println(pictures.size());
			System.out.println(0);
		}
		else {
			for(int i = 0; i<n; i++) {
			for(int j = 0; j<m; j++) {
				if(board[i][j] == 1) {
					cnt = 0;
					DFS(i,j);
					pictures.add(cnt);
				}
			}
		}
		
		Collections.sort(pictures);
		System.out.println(pictures.size());
		System.out.println(pictures.get(pictures.size()-1));
		}
	}
	private static void DFS(int x, int y) {
		cnt++;
		board[x][y] = 0;
		
		if(x==m-1 && y==n-1) return;
		else {
			for(int d = 0; d<4; d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				if(nx>=0 && nx<n && ny>=0 && ny<m && board[nx][ny]==1) {
					DFS(nx, ny);
				}
			}
		}
	}

}
