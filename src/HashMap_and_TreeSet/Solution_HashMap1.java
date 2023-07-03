package HashMap_and_TreeSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;


public class Solution_HashMap1 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		String str = br.readLine();
		char[] carr = str.toCharArray();
		//System.out.println(Arrays.toString(carr));
		HashMap<Character, Integer> map = new HashMap<>();
		System.out.println(solution(map, carr));
	}

	private static char solution(HashMap<Character, Integer> map, char[] carr) {
		char ans = 'a';
		for(char x : carr) {
			map.put(x, map.getOrDefault(x, 0)+1);
		}
		//System.out.println(map);
		
		int max = Integer.MIN_VALUE;
		//System.out.println(max);
		for(char key : map.keySet()) {
			if(max < map.get(key)) {
				max = map.get(key);
				ans = key;
			}
			
		}
		return ans;
	}
	
}
