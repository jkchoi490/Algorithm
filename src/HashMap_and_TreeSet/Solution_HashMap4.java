package HashMap_and_TreeSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Solution_HashMap4 { // 모든 아나그램 찾기

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String S = br.readLine();
		String T = br.readLine();
		System.out.println(solution(S, T));

	}
	
	public static int solution(String S, String T){
		int answer=0;
		HashMap<Character, Integer> sm=new HashMap<>();
		HashMap<Character, Integer> tm=new HashMap<>();
		for(char x : T.toCharArray()) {
			tm.put(x, tm.getOrDefault(x, 0)+1);
		}
		
		int L=T.length()-1;
		for(int i=0; i<L; i++) {
			sm.put(S.charAt(i), sm.getOrDefault(S.charAt(i), 0)+1);
		}
		int lt=0;
		for(int rt=L; rt<S.length(); rt++){
			sm.put(S.charAt(rt), sm.getOrDefault(S.charAt(rt), 0)+1);
			if(sm.equals(tm)) answer++;
			sm.put(S.charAt(lt), sm.get(S.charAt(lt))-1);
			if(sm.get(S.charAt(lt))==0) sm.remove(S.charAt(lt));
			lt++;
		}
		return answer;
	}
		

}
