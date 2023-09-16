import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BAEKJOON17845 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		
		int[] lecture = new int[k+1];
		int[] time = new int[k+1];
		int[][] arr = new int[k+1][n+1];
		
		for(int i = 1; i<=k; i++) {
			st = new StringTokenizer(br.readLine());
			lecture[i] = Integer.parseInt(st.nextToken());
			time[i] = Integer.parseInt(st.nextToken());
		}
		
		for(int i = 1; i<=k; i++) {
			for(int j = 1; j<=n; j++) {
				if(time[i]<=j) arr[i][j] = Math.max(arr[i-1][j], arr[i-1][j-time[i]]+lecture[i]);
				else arr[i][j] = arr[i-1][j];
			}
		}
		System.out.println(arr[k][n]);
	}

}
