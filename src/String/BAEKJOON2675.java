import java.io.*;
import java.util.*;

public class BAEKJOON2675 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;
		StringBuilder sb;
		for(int tc = 1; tc<=T; tc++) {
			st = new StringTokenizer(br.readLine());
			sb = new StringBuilder();
			int R = Integer.parseInt(st.nextToken());
			String S = st.nextToken();
			for(int i = 0; i<S.length(); i++) {
				for(int j = 0; j<R; j++) {
					sb.append(S.charAt(i));
				}
			}
			System.out.println(sb.toString());
		}
	}

}
