package Stack_and_Queue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;


public class Solution_Queue2 { // 교육과정 설계

	public static void main(String[] args) throws IOException { 
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String lectures = br.readLine();
		String input = br.readLine();
		System.out.println(solution(lectures, input));
	}

	private static String solution(String lectures, String input) {
		String ans = "YES";
		Queue<Character> q = new LinkedList<>();
		for(char x : lectures.toCharArray()) q.offer(x);
		for(char x : input.toCharArray()) {
			if(q.contains(x)) {
				if(x!=q.poll()) return "NO";
			}
		}
		if(!q.isEmpty()) return "NO";
		return ans;
	}

}
