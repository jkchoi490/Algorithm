import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BAEKJOON10811 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int[] arr = new int[n+1];
		for(int i = 1; i<=n; i++) {
			arr[i] = i;
		}
		for(int k = 0; k<m; k++) {
			st = new StringTokenizer(br.readLine());
			int i = Integer.parseInt(st.nextToken());
			int j = Integer.parseInt(st.nextToken());
			for(int h = i; h<=j; h++) {
				int x = j--;
				int tmp = arr[h];
				arr[h] = arr[x];
				arr[x] = tmp;
			}
		}
		
		for(int i = 1; i<=n; i++) System.out.print(arr[i]+" ");
		
	}

}
