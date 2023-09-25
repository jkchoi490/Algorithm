import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BAEKJOON1850 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		long A = Long.parseLong(st.nextToken());
		long B = Long.parseLong(st.nextToken());
		long ans = solve(A,B);
		StringBuilder sb = new StringBuilder();
		for(long j = 0; j<ans; j++)sb.append("1");
		System.out.println(sb.toString());
	}

	private static long solve(long a, long b) {
		if(b == 0) return a;
		else return solve(b, a%b);
	}

}
