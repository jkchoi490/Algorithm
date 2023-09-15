import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BAEKJOON15897 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		n--;
		int ans = 0;
		for (int i = n+1; i!=0; i =n/((n/i) + 1)) {
			ans += (n/i+1) * (i - (n / ((n/i) + 1)));
		}
		System.out.println(ans);
	}

}
