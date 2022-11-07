package String;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution3 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		System.out.println(Solution(str));
		System.out.println(Solution2(str));
	}

	private static String Solution(String str) {
		String answer = "";
		int m = Integer.MIN_VALUE; // ���� �ּҰ�
		String[] s = str.split(" "); //�������� ����Ʈ ����
		for(String x : s) { //����Ʈ s�� �ִ� ���� x �ϳ��� �޾ƿ���
			int len = x.length(); //���� �� �ܾ� ����ؾ��ϹǷ�
			if(len>m) { // len > m 
				m = len; //m�� x���� ����
				answer = x; 
			}
		}
		
		
		return answer;
	}

	private static String Solution2(String str) {
		String answer = "";
		int m = Integer.MIN_VALUE, pos;
		while((pos = str.indexOf(' ')) != -1) { //������ ���������� �ݺ�
			String tmp = str.substring(0, pos);
			int len = tmp.length();
			if(len>m) {
				m = len;
				answer = tmp;
			}
			str = str.substring(pos+1);
		}
		
		if(str.length()>m) answer = str;
		return answer;
	}
	
	
}
