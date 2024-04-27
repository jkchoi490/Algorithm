import java.io.*;
import java.util.*;

public class BAEKJOON15649 {

	static int N,M;
	static int[] arr, perm;
	static boolean[] visited;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		arr = new int[N+1];
		visited = new boolean[N+1];
		perm = new int[M];
		for(int i = 1; i<=N; i++) arr[i] = i;
		DFS(0);
	}
	private static void DFS(int L) {
		if(L == M) {
			for(int x : perm) {
				System.out.print(x+" ");
			}
			System.out.println();
			return;
		}else {
			for(int i = 1; i<=N; i++) {
				if(!visited[i]) {
					visited[i] = true;
					perm[L] = arr[i];
					DFS(L+1);
					visited[i] = false;
				}
			}
		}
		
	}

}
