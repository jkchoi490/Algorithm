import java.io.*;
import java.util.*;

public class BAEKJOON2630 {
	
	static int[] answer;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[][] arr = new int[N][N];
		for(int i = 0; i<N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j = 0; j<N; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		answer = new int[2];
		
		DFS(0, 0, arr, N);
		for(int ans : answer) System.out.println(ans);

	}
		
	public static void DFS(int x, int y, int[][] arr, int size) {
		if(checkAllSame(arr, x, y, size)) {
			answer[arr[x][y]]++;
			return;
		}else {
			int newSize = size/2;
			DFS(x, y, arr, newSize);
			DFS(x, y+newSize, arr, newSize);
			DFS(x+newSize, y, arr, newSize);
			DFS(x+newSize, y+newSize, arr, newSize);
			
		}
	}

	public static boolean checkAllSame(int[][] arr, int x, int y, int size) {
		int value = arr[x][y];
		
		for(int i = x; i<x+size; i++) {
			for(int j = y; j<y+size; j++) {
				if(arr[i][j] != value) {
					return false;
				}
			}
		}
		return true;
	}
}
