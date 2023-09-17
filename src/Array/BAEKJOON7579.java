import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BAEKJOON7579 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
		StringTokenizer st= new StringTokenizer(br.readLine());  
		int n = Integer.parseInt(st.nextToken()); 
		int m = Integer.parseInt(st.nextToken()); 
		int[] memory = new int[101];
		int[] cost = new int[101];
		int[] dp = new int[100*100+1];
		st= new StringTokenizer(br.readLine()); 
		for (int i=1; i<=n; i++) { 
			memory[i] = Integer.parseInt(st.nextToken());
		}
		st= new StringTokenizer(br.readLine());  
		for (int i=1; i<=n; i++) {			
			cost[i] = Integer.parseInt(st.nextToken());
		}
		
		for (int i=1; i<=n; i++) {
			for (int j = 100 * 100; j >= 0; j--) {
				if(j < cost[i]) break;
				dp[j] = Math.max(dp[j], dp[j-cost[i]]+memory[i]);
		}
	}
		
		for(int i = 0; i<=100*100; i++) {
			if(dp[i] >= m) {
				System.out.println(i);
				break;
			}
		}
		
		
	}

}
