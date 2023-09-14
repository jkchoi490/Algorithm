package String;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BAEKJOON11170 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;
		for(int i = 0; i<T; i++) {
			st = new StringTokenizer(br.readLine());
			int cnt = 0;
			int n = Integer.parseInt(st.nextToken());
			int m = Integer.parseInt(st.nextToken());
			for(int j = n; j<m+1; j++) {
				String s = String.valueOf(j);
				for(int k = 0; k<s.length(); k++) {
					if(s.charAt(k) == '0') cnt++;
				}
			}
			System.out.println(cnt);
		}

	}

}
