package String;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution8 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		System.out.println(Solution(str));
		//System.out.println(mySolutionerr(str));

	}
	private static String Solution(String str) {
		String answer = "NO";
		str=str.toUpperCase().replaceAll("[^A-Z]", "");
		String tmp=new StringBuilder(str).reverse().toString();
		if(str.equals(tmp)) answer="YES";
		return answer;
	}
	//found7, time: study; Yduts; emit, 7Dnuof
	private static String mySolutionerr(String str) { // split�� ����ؼ� �迭�� ��������ϸ� ������ ��
		String answer ="NO";
		str = str.toLowerCase().replaceAll("[^a-z]", " ");
		System.out.println(str);
		return answer;
	}

}
