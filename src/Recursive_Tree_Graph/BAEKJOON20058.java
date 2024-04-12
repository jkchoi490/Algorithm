import java.io.*;
import java.util.*;

public class BAEKJOON20058 {

	static int N,Q;
	static int[][] A;
	static int[] L;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		A = new int[N][N];
		L = new int[Q];
		for(int i = 0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<N; j++) {
				A[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i<Q; i++) L[i] = Integer.parseInt(st.nextToken());
	
		while(Q-- > 0) {
			//
			
		}
	}

}
