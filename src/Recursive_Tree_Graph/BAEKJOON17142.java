import java.io.*;
import java.util.*;

class Virus{
	int x, y, time;
	public Virus(int x, int y, int time) {
		this.x=x;
		this.y=y;
		this.time=time;
	}
}
public class BAEKJOON17142 {
	
	static int n, m, emptySpace,min =Integer.MAX_VALUE;
	static int[][] map;
	static ArrayList<Virus> virus;
	static Virus[] combi;
	static boolean[] ch;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		map = new int[n][n];
		combi = new Virus[m];
		virus = new ArrayList<>();
		 emptySpace = 0;
		for(int i = 0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 2) virus.add(new Virus(i,j,0));
				if(map[i][j] == 0) emptySpace++;
			}
		}
		ch = new boolean[virus.size()];
		
		DFS(0,0);

		System.out.println(min==Integer.MAX_VALUE ? -1 : min);
		
	}

	public static void DFS(int L, int start) {
		if(L == m) {
			BFS();
			return;
		}else {
			for(int i = start; i<virus.size(); i++) {
				if(!ch[i]) {
					ch[i] = true;
					combi[L] = virus.get(i);
					DFS(L+1, i+1);
					ch[i] = false;
				}
			}
		}
		
	}

	public static void BFS() {
		Queue<Virus> q = new LinkedList<>();
		boolean[][] visited = new boolean[n][n];
		int copyEmptySpace = emptySpace;
		int[][] copyMap = new int[n][n];
		for(int i = 0; i<n; i++) {
			for(int j = 0; j<n; j++) {
				copyMap[i][j] = map[i][j];
			}
		}
		for(Virus v : combi) {
			visited[v.x][v.y] = true;
			q.offer(new Virus(v.x, v.y, 0));
		}
		int maxTime = 0;
		while(!q.isEmpty()) {
			Virus cur = q.poll();
			for(int d = 0; d<4; d++) {
				int nx = cur.x+dx[d];
				int ny = cur.y+dy[d];
				
				if(nx>=0 && nx<n && ny>=0 && ny<n && !visited[nx][ny] && map[nx][ny] != 1) {
					visited[nx][ny] = true;
					if(map[nx][ny] == 0) {
						copyEmptySpace--;
						maxTime = cur.time+1;
					}
					q.offer(new Virus(nx, ny, cur.time+1));
				}
				
			}
		}
		
		if(copyEmptySpace == 0) {
			min = Math.min(min, maxTime);
		}

	}

}
