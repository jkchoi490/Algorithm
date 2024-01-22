import java.io.*;
import java.util.*;

public class BAEKJOON1316 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int cnt = 0;
		for(int tc = 1; tc<=N; tc++) {
			String s = br.readLine();
			if(check(s)==true) {
				cnt++;
			}
		}
		System.out.println(cnt);
	}

	public static boolean check(String s) {
		boolean[] check = new boolean[26];
		int prev = 0;
		for(int i = 0; i<s.length(); i++) {
			int now = s.charAt(i);
			if(prev != now) {
				if(check[now - 'a'] == false) {
					check[now - 'a'] = true;
					prev = now;
				}
				else {
					return false;
				}
			}
		
		}
		return true;
	}

}
