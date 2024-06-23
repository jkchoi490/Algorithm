import java.io.*;
import java.util.*;

public class BAEKJOON11559 {

    static char[][] map;
    static boolean[][] visited;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        map = new char[12][6];
        for (int i = 0; i < 12; i++) {
            map[i] = br.readLine().toCharArray();
        }

        down();
        int cnt = 0;
        while(true) {
        	visited = new boolean[12][6];
        	boolean flag = false;
        	 for (int i = 0; i < 12; i++) {
                 for (int j = 0; j < 6; j++) {
                  if(map[i][j] !='.' && !visited[i][j]) {
                	  if(BFS(i,j,map[i][j])) {
                		  flag = true;
                	  }
                  	}  
                 }
           	}
        	 
        	if(!flag) break;
        	
        	down();
        	cnt++;
        }
        
        System.out.println(cnt);
       
    }


	public static boolean BFS(int x, int y,char color) {
		Queue<int[]> q = new LinkedList<>();
		ArrayList<int[]> puyos = new ArrayList<>();
		
		q.offer(new int[] {x, y});
		visited[x][y] = true;
		puyos.add(new int[] {x, y});
		
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			int cur_x = cur[0];
			int cur_y = cur[1];
			
			for(int d = 0; d<4; d++) {
				int nx = cur_x + dx[d];
				int ny = cur_y + dy[d];
				
				if(nx>=0 && nx<12 && ny>=0 && ny<6) {
					if(!visited[nx][ny] && map[nx][ny] == color) {
						visited[nx][ny] = true;
						q.offer(new int[] {nx, ny});
						puyos.add(new int[] {nx, ny});	
					}
				}
			}
		}
		
		if(puyos.size() >=4) {
			for(int[] puyo : puyos) {
				map[puyo[0]][puyo[1]] = '.';
			}
			return true;
		}
		return false;
	}

	public static void down() {
        for (int j = 0; j < 6; j++) { 
            for (int i = 11; i > 0; i--) {
                if (map[i][j] == '.') {
                    int k = i - 1;
                    while (k >= 0 && map[k][j] == '.') {
                        k--;
                    }
                    if (k >= 0) {
                        map[i][j] = map[k][j];
                        map[k][j] = '.';
                    }
                }
            }
        }
    }
}
