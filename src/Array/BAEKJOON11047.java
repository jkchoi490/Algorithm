import java.io.*;
import java.util.*;

public class BAEKJOON11047 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		int[] arr = new int[N];
		for(int i = 0; i<N; i++) {
			int a = Integer.parseInt(br.readLine());
			arr[i] = a;
		}
		Arrays.sort(arr);
		int cnt = 0;
	
		for(int i = N-1; i>=0; i--) {
			while(K >= arr[i]) {
				K -= arr[i];
				cnt++;
			}
			

		}
		System.out.println(cnt);
	}

}
