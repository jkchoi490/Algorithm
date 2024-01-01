import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BAEKJOON1780 {
	
	static int N;
	static int[][] graph;
	static int[] count;

	private static void divide(int x, int y, int n) {
		if(check(x, y, n)) {
			count[graph[x][y]+1]++;
		}else {
			int s = n/3;
			for(int i = 0; i<3; i++) {
				for(int j = 0; j<3; j++) {
					divide(x+s*i, y+s*j, s);
				}
			}
		}	
	}
	
	private static boolean check(int x, int y, int n) {
		int now = graph[x][y];
		for(int i = x; i<x+n; i++) {
			for(int j = y; j<y+n; j++) {
				if(now !=graph[i][j]) return false;
			}	
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		graph = new int[N][N];
		count = new int[3];
		StringTokenizer st;
		for(int i = 0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<N; j++) {
				graph[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		divide(0,0,N);
		System.out.println(count[0]);
		System.out.println(count[1]);
		System.out.println(count[2]);
	}

}