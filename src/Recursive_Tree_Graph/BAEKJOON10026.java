import java.io.*;
import java.util.*;

class RGB{
	int x, y;
	public RGB(int x, int y) {
		this.x=x;
		this.y=y;
	}
}
public class BAEKJOON10026 {

	static int N;
	static char[][] arr;
	static boolean[][] visited;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		arr = new char[N][N];
		visited = new boolean[N][N];
		for(int i = 0; i<N; i++) {
			arr[i] = br.readLine().toCharArray();
		}
		
		int[] result = solve(arr);
		System.out.println(result[0]+" "+result[1]);
		
	}
	
	private static int[] solve(char[][] arr) {
		int[] result = new int[2];
		
		result[0] = count(arr);
		
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				if(arr[i][j] == 'R') {
					arr[i][j] = 'G';
				}
			}
		}
		result[1] = count(arr);
		
		return result;
	}

	public static int count(char[][] arr) {
		int cnt = 0;
		
		visited = new boolean[N][N];
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				if(!visited[i][j]) {
					BFS(i,j,arr[i][j]);
					cnt++;
				}
			}
		}
		return cnt;
		
	}

	public static void BFS(int i, int j, char color) {
		Queue<RGB> q = new LinkedList<>();
		visited[i][j] = true;
		q.offer(new RGB(i, j));
		while(!q.isEmpty()) {
			RGB cur = q.poll();
			for(int d = 0; d<4; d++) {
				int nx = cur.x + dx[d];
				int ny = cur.y + dy[d];
				if(nx>=0 && nx<N && ny>=0 && ny<N && !visited[nx][ny] && arr[nx][ny] == color) {
					visited[nx][ny] = true;
					q.offer(new RGB(nx, ny));
				}
			}
		}
		
	}

}
