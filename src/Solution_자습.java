import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution_자습 {	// 자습 

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine().trim());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int[] A = new int[N];
		int[] B = new int[N];
		for(int i = 0; i<2; i++) {
			st = new StringTokenizer(br.readLine().trim());
			for(int j = 0; j<N; j++) {
				int score = Integer.parseInt(st.nextToken());
				System.out.println(score);
			
			}
		}


	}

}
