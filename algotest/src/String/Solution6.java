package String;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution6 { //charAt() �Լ��� ���ڿ����� Ư�� �ε����� ��ġ�ϴ� �����ڵ� ���Ϲ��ڸ� ��ȯ�մϴ�.

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		System.out.println(Solution(str));

	}

	private static String Solution(String str) {
		StringBuilder sb = new StringBuilder();
		//char[] cArr = input_str.toCharArray(); -> indexOf ������ 
		for(int i = 0; i<str.length(); i++) {
			if(str.indexOf(str.charAt(i)) == i) sb.append(str.charAt(i));
		}
		
		
		return sb.toString();
	}

}
