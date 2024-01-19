import java.io.*;
import java.util.*;

public class BAEKJOON1517 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] A = new int[N];
		int cnt = 0;
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0; i<N; i++) A[i] = Integer.parseInt(st.nextToken());
		for(int i = 0; i<A.length-1; i++) {
			for(int j = 0; j<A.length-i-1; j++) {
				if(A[j]>A[j+1]) {
					int tmp = A[j];
					A[j] = A[j+1];
					A[j+1] = tmp;
					cnt++;
				}
			}
		}
		System.out.println(cnt);
	}

}
