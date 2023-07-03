package HashMap_and_TreeSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

public class Solution_HashMap2 {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		char[] arr1 = br.readLine().toCharArray();
		char[] arr2 = br.readLine().toCharArray();
		System.out.println(solution(arr1,arr2));
	}

	private static String solution(char[] arr1, char[] arr2) {
		String ans = "NO";
		HashMap<Character, Integer> map1 = new HashMap<>();
		HashMap<Character, Integer> map2 = new HashMap<>();
		Arrays.sort(arr1);
		Arrays.sort(arr2);
		for(char x : arr1) {
			map1.put(x,map1.getOrDefault(x, 0)+1);
		}
		for(char x2 : arr2) {
			map2.put(x2, map2.getOrDefault(x2, 0)+1);
		}
		
		if(map1.equals(map2)) {
			ans = "YES";
			return ans;
		}
		else return ans;
	}


}
