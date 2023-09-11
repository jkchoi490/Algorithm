import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BAEKJOON3052 {

	static int ans, cnt;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] arr = new int[10];
		for(int i = 0; i<10; i++) {
			int n = Integer.parseInt(br.readLine());
			arr[i] = n % 42;
		}
		ans = 0;
		
		for(int i = 0; i<10; i++) {
			cnt = 0;
			for(int j = i+1; j<10; j++) {
				if(arr[i] == arr[j]) cnt++;
			}
			if(cnt == 0) ans++;
			
		}

		System.out.println(Arrays.toString(arr));
		System.out.println(ans);
	}

}
