import java.io.*;
import java.util.*;

public class BAEKJOON1138 {

	static int N;
	static int[] arr, result, perm, answer;
	static boolean[] visited;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		arr = new int[N];
		result = new int[N];
		perm = new int[N];
		visited = new boolean[N];
		answer = new int[N];
		for(int i = 0; i<N; i++) {
			result[i] = Integer.parseInt(st.nextToken());
			arr[i] = i;
		}

		DFS(0);
		for(int x : answer) System.out.print((x+1)+" ");
	}
	private static void DFS(int L) {
		if(L==N) {
			checkArr(perm);
			return;
		}else {
			for(int i = 0; i<N; i++) {
				if(!visited[i]) {
					visited[i] = true;
					perm[L] = arr[i];
					DFS(L+1);
					visited[i] = false;
				}
			}
		}
		
	}
	private static void checkArr(int[] perm) {
		int[] count = new int[N];
		for(int i = 0; i<N; i++) {
			int now_person = perm[i];
			for(int j = 0; j<i; j++) {
				if(perm[i] < perm[j]) count[now_person]++;
			}
		}

		int cnt = 0;
		for(int i = 0; i<N; i++) {
			if(result[i] == count[i]) cnt++;
		}
		if(cnt==N) answer = perm.clone();
		
	}

}
