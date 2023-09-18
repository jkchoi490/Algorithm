import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BAEKJOON10409 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
	
		int n = Integer.parseInt(st.nextToken());
		int t = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		int cnt = 0;
		int time = 0;
		for(int i = 0; i < n; i++) {
			int work = Integer.parseInt(st.nextToken());
			time += work;
			if(time <= t) cnt++;
		}
		System.out.println(cnt);

	}

}
