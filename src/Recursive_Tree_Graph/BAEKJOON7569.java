import java.io.*;
import java.util.*;

public class BAEKJOON7569 {

	static int M,N,H;
	static int[][][] box;
	static Queue<int[]> q = new LinkedList<>();
	static int[] dx = {-1, 0, 1, 0, 0, 0};
	static int[] dy = {0, 1, 0, -1, 0, 0};
	static int[] dz = {0, 0, 0, 0, 1, -1};
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		box = new int[H][N][M];
	
		for(int i = 0; i<H; i++) {
			for(int j = 0; j<N; j++) {
				st = new StringTokenizer(br.readLine());
				for(int k = 0; k<M; k++) {
					box[i][j][k] = Integer.parseInt(st.nextToken());
					if(box[i][j][k] == 1) q.offer(new int[] {j, k,i});
				}
			}
		}
		int day = BFS();	
		System.out.println(day);

	}
	private static int BFS() {
		
		int day = 0;
		while(!q.isEmpty()) {
			int size = q.size();
			for(int i = 0; i<size; i++) {
			int[] now = q.poll();
			for(int d = 0; d<6; d++) {
				int nx = now[0]+dx[d];
				int ny = now[1]+dy[d];
				int nz = now[2]+dz[d];
				if(nx>=0 && nx<N && ny>=0 && ny<M && nz>=0 && nz<H && box[nz][nx][ny] == 0) {
					box[nz][nx][ny] = 1;
					q.offer(new int[] {nx, ny, nz});
				}
			}
		}
		day++;
		}
		
		if(tomato(box)) return day-1;
		else return -1;
	}
	
	private static boolean tomato(int[][][] box) {
		for(int k = 0; k<H; k++) {
			for(int i = 0; i<N; i++) {
				for(int j = 0; j<M; j++) {
					if(box[k][i][j]==0) return false;
				}
			}
		}
		return true;
	}

}
