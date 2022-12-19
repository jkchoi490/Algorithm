package Array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_Array10 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[][] map = new int[N+2][N+2];
		StringTokenizer st;
		for(int i = 1; i<N+1; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 1; j<N+1; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		System.out.println(searching_top(map,N));
			
	}

	private static int searching_top(int[][] map, int N) {
		int cnt = 0;
		int top = Integer.MIN_VALUE; 
		int l,r,u,d = 0;
		for(int x = 1; x<N+2; x++) {
			for(int y =1; y<N+2; y++) {
				if(map[x][y] == 0) continue;
				else {
					if((x-1<N+2) && (y-1<N+2) && (x+1<N+2) && (y+1<N+2)) {
						top = map[x][y];
						l = map[x-1][y];
						r = map[x+1][y];
						u = map[x][y-1];
						d = map[x][y+1];
						if(l<top && r<top && u<top && d<top) {
							//System.out.println("top: "+top+" x: "+x+" y: "+y);
							cnt++;
			
						}
					}
				}
					
			}
		}
		
		
		return cnt;
		
	}

}
