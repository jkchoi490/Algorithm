package Array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_Array11 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		StringTokenizer st;
		int[][] graph = new int[N+1][N+1];
		for(int i = 1; i<N+1; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 1; j<N; j++) {
				graph[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for(int i = 0; i<N+1; i++) {
			for(int j = 0; j<N+1; j++) {
				System.out.print(graph[i][j]);
			}
			System.out.print("\n");
		}
		
		
		class_monitor(graph,N);
	}

	private static int class_monitor(int[][] graph, int N) {
		int ans = 0;
		int[] check = new int[N+1];
		int[] cnt = new int[N+1];
		
		for(int x = 0; x<N; x++) {
			for(int y = 1; y<N+1; y++) {
				check[y] = graph[x][y];
				
			}
		}
		
		
		return ans;
	}

}
