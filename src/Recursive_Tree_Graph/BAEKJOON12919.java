import java.io.*;
import java.util.*;

public class BAEKJOON12919 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String S = br.readLine();
		String T = br.readLine();
		
		if(canChange(S, T)) {
			System.out.println(1);
		}else {
			System.out.println(0);
		}
	}

	public static boolean canChange(String S, String T) {
		
		if(S.equals(T)) {
			return true;
		}
		
		if(T.length() < S.length()) {
			return false;
		}
		
		if(T.charAt(T.length()-1) == 'A') {
			if(canChange(S, T.substring(0, T.length()-1))) {
				return true;
			}
		}
		
		if(T.charAt(0) == 'B') {
			String reverse = new StringBuilder(T.substring(1)).reverse().toString();
			if(canChange(S, reverse)) {
				return true;
			}
		}
		
		return false;
	}

}
