import java.io.*;
import java.util.*;

public class BAEKJOON7576 {

	static int M,N, day = 1;
	static int[][] arr;
	static boolean tomato = false;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	static Queue<int[]> q = new LinkedList<>();
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		arr = new int[N][M];
		for(int i = 0; i<N; i++) {
			 st = new StringTokenizer(br.readLine());
			for(int j = 0; j<M; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<M; j++) {
				if(arr[i][j] > 0) {
					q.offer(new int[] {i, j, 1});
				}
			}
		}
		
		BFS();
		int answer = 0;
		boolean flag = true;
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<M; j++) {
				if(arr[i][j] == 0) flag = false;
				answer = Math.max(answer, arr[i][j]);
			}
		}
		if(!flag) System.out.println(-1);
		else if(!tomato) System.out.println(0);
		else System.out.println(answer);
	}
	
	private static void BFS() {
		
		while(!q.isEmpty()) {
			int[] now = q.poll();
			for(int d = 0; d<4; d++) {
				int nx = now[0]+dx[d];
				int ny = now[1]+dy[d];
				int next_day = now[2]+1;
				if(nx>=0 && nx<N && ny>=0 && ny<M && arr[nx][ny]==0) {
					arr[nx][ny] = now[2];
					tomato = true;
					q.offer(new int[] {nx, ny, next_day});
				}
			}
		}
	
	}

}
