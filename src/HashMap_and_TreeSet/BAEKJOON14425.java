package HashMap_and_TreeSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class BAEKJOON14425 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		HashMap<String, Integer> map = new HashMap<>();
		for(int i = 0; i<n; i++) {
			String str = br.readLine();
			map.put(str, 0);
		}
		int cnt = 0;
		for(int i = 0; i<m; i++) {
			String word = br.readLine();
			for(String key : map.keySet()){
				if(key.equals(word)) cnt++;
			}
			
		}
		
		System.out.println(cnt);
	}

}
