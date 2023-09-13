package TimeComplexity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BAEKJOON24510 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int c = Integer.parseInt(br.readLine());
		int cnt = 0;
		int max_v = Integer.MIN_VALUE;
		for(int i = 0; i<c; i++) {
			String str = br.readLine();
			if(str.contains("for")) cnt++;
			if(str.contains("while")) cnt++;
			max_v = Math.max(max_v, cnt);
		
		}
		System.out.println(max_v);
		
	}

}
