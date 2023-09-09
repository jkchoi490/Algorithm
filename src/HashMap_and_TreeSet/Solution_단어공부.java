import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Solution_단어공부 { // 단어 공부

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String alphabet = br.readLine().toUpperCase();
		HashMap<Character, Integer> map = new HashMap<>();
		for(int i = 0; i<alphabet.length(); i++) {
			char word = alphabet.charAt(i);
			map.put(word, map.getOrDefault(word, 0)+1);
		}
		int cnt = 0;
		int max_v = Integer.MIN_VALUE;
		char max_k = 'k';
		for(int v : map.values()) {
			max_v = Math.max(max_v, v);	
		}
		for(char k : map.keySet()) {
			if(map.get(k) == max_v) {
				max_k = k;
				cnt++;
			}
		}
		if(cnt == 1) System.out.println(max_k);
		else System.out.println("?");
		}

}
