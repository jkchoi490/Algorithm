import java.io.*;
import java.util.*;

class CCTV{
	int x, y, num;
	int[] dir;
	public CCTV(int x, int y, int num, int[] dir) {
		this.x=x;
		this.y=y;
		this.num=num;
		this.dir=dir;
	}
}
public class BAEKJOON15683 {

	static int n,m, min = Integer.MAX_VALUE;
	static int[][] map;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	static int[][][] cctvs = {
			{{0}},
			{{0},{1},{2},{3}}, 
			{{1,3},{0,2}},
			{{0,1},{1,2},{2,3},{0,3}},
			{{1,3,0},{3,1,2},{0,1,2},{0,2,3}},
			{{0,1,2,3}}
	};
	static ArrayList<int[]> cctv_list;
	static ArrayList<CCTV>combi;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		map = new int[n][m];
		cctv_list = new ArrayList<>();
		combi = new ArrayList<>();
		for(int i = 0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<m; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(1<= map[i][j] && map[i][j]<=5) cctv_list.add(new int[] {map[i][j],i,j});
			}
		}
		
		DFS(0);
		System.out.println(min);
	}
	
	
	public static void DFS(int L) {
		if(L==cctv_list.size()) {
			checkArea(combi);
			return;
		}else {
			
			int[] cur = cctv_list.get(L);	
			
			for(int i = 0; i<cctvs[cur[0]].length; i++) {	
				combi.add(new CCTV(cur[1], cur[2], cur[0], cctvs[cur[0]][i]));
				DFS(L+1);
				combi.remove(combi.size()-1); 
			}
		}
		
	}
	private static void checkArea(ArrayList<CCTV> combi) {
		int[][] copyMap = new int[n][m];
		for(int i = 0; i<n; i++) {
			for(int j = 0; j<m; j++) {
				copyMap[i][j] =map[i][j];
			}
		}
		
		for(CCTV cctv : combi) {
			for(int i = 0; i<cctv.dir.length; i++) {
				int nx =cctv.x, ny = cctv.y;
				while(true) {
					nx += dx[cctv.dir[i]];
					ny += dy[cctv.dir[i]];
					if(nx<0 || nx>=n || ny<0 || ny>=m || map[nx][ny]==6) break;
					copyMap[nx][ny] = -1;
				}
			}
		}
		
		int area = 0;
		for(int i = 0; i<n; i++) {
			for(int j = 0; j<m; j++) {
				if(copyMap[i][j]==0) area++;
			}
		}
		min = Math.min(min, area);
		
	}

}
