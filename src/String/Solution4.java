package String;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Solution4 { //4. 단어 뒤집기

	/*
	 * 
	N개의 단어가 주어지면 각 단어를 뒤집어 출력하는 프로그램을 작성하세요.

	입력
	3
	good
	Time
	Big

	출력
	doog
	emiT
	giB

	 * 
	 * 
	 * 
	 * */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		String[] str = new String[N];
		for(int i = 0; i<N; i++) {
			str[i] = br.readLine();
		}
		//System.out.println(Arrays.toString(str));
		for(String x : Solution(str)) {
			System.out.println(x);
		}
	}

	private static ArrayList<String> Solution(String[] str) {
		ArrayList<String> answer = new ArrayList<>(); //정답 출력
		for(String x : str) {
			char[] word = x.toCharArray(); //str 배열의 원소들인 단어 x들로 배열을 만든다.
			int lt = 0;
			int rt = word.length-1;
			while(lt<rt) {
				char tmp = word[lt];
				word[lt] = word[rt];
				word[rt] = tmp;
				lt++;
				rt--;
			}
			String tmp = String.valueOf(word);
			answer.add(tmp);
		}
		return answer;
	}

}