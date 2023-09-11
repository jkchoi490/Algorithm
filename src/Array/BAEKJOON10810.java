import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BAEKJOON10810 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int[] arr= new int[n+1];
		for(int x = 0; x<m; x++) {
			st = new StringTokenizer(br.readLine());
			int i = Integer.parseInt(st.nextToken());
			int j = Integer.parseInt(st.nextToken());
			int k = Integer.parseInt(st.nextToken());
			for(int idx = i; idx<=j; idx++) {
				arr[idx] = k;
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i=1; i<n+1; i++) sb.append(arr[i]).append(" ");
		System.out.println(sb.toString());
		

	}

}
