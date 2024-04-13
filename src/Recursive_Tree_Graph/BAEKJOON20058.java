import java.io.*;
import java.util.*;

public class BAEKJOON20058 {

	static int N,Q;
	static int[][] A, arr;
	static boolean[][] visited;
	static int[] L;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		N = (int)Math.pow(2, N);
		A = new int[N][N];
		arr = new int[N][N];
		visited = new boolean[N][N];
		L = new int[Q];
		for(int i = 0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<N; j++) {
				A[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i<Q; i++) L[i] = Integer.parseInt(st.nextToken());
	
		int answer = 0;
		int sum = 0;
		for(int l = 0; l<Q; l++) {
			move(L[l]);
			melt();		
		}
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				sum += A[i][j];
				if(visited[i][j] == false && A[i][j] > 0) {
					answer = Math.max(answer, BFS(i,j));
				}
			}
		}
		
		System.out.println(sum);
		System.out.println(answer);
		
	}
	
	
	public static int BFS(int i, int j) {
		Queue<int[]> q = new LinkedList<>();
		q.offer(new int[] {i, j});
		visited[i][j] = true;
		int cnt = 1;
		while(!q.isEmpty()) {
			int[] now = q.poll();
			for(int d = 0; d<4; d++) {
				int nx = now[0]+dx[d];
				int ny = now[1]+dy[d];
				if(nx>=0 && nx<N && ny>=0 && ny<N && A[nx][ny]!=0 && visited[nx][ny]==false) {
					visited[nx][ny] = true;
					q.offer(new int[] {nx, ny});
					cnt++;
				}
			}
		}
		
		return cnt;
	}
	public static void melt() {
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				int cnt = 0;
				for(int d = 0; d<4; d++) {
					int nx = i + dx[d];
					int ny = j + dy[d];
					if(nx>=0 && nx<N && ny>=0 && ny<N && A[nx][ny]>0) {
						cnt++;
					}
				}
				if(cnt < 3) A[i][j] -= 1;
			}
		}
		
	}
	public static void move(int L) {
		L = (int)Math.pow(2, L);
		for(int nx = 0; nx<N; nx+=L) {
			for(int ny = 0; ny<N; ny+=L) {
				for(int i = 0; i<L; i++) {
					for(int j = 0; j<L; j++) {
						arr[nx+i][ny+j] = A[nx+L-1-j][ny+i];
					}
				}
			}
		}
		
		A = arr;
	}

}
