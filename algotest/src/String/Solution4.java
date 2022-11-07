package String;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Solution4 {

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
		ArrayList<String> answer = new ArrayList<>(); //���� ���
		for(String x : str) {
			char[] word = x.toCharArray(); //str �迭�� ���ҵ��� �ܾ� x��� �迭�� �����.
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