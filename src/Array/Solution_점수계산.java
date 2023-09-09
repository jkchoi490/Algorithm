import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_점수계산 { // 점수 계산

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] arr = new int[N];
		int k = 0;
		int score = 0;
		for(int i = 0; i<N; i++) arr[i] = Integer.parseInt(st.nextToken());
		
		for(int x : arr) {
			if(x == 1) {
				k+=1;
				score+=k;
			}
			else k = 0;
		}

		System.out.println(score);
		
	}

}
