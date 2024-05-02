import java.io.*;
import java.util.*;

public class BAEKJOON1515 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		int num = 0, idx = 0;
		while(idx<str.length()) {
			num++;
			String strNum = String.valueOf(num);
			for(int i = 0; i<strNum.length(); i++) {
				if(str.charAt(idx) == strNum.charAt(i)) {
					idx++;
				}
				if(idx == str.length()) {
					System.out.println(num);
					break;
				}
			}
		}
		

	}

}
