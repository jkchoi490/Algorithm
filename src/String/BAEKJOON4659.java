import java.io.*;
import java.util.*;

public class BAEKJOON4659 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
		while(true) {
			String password = br.readLine();
			if(password.equals("end")) break;
			boolean check = true;
			if(!check1(password)) check = false;
			else if(!check2(password)) check = false;
			else if(!check3(password)) check = false;
			
			if(check) System.out.println("<"+password+"> is acceptable.");
			else System.out.println("<"+password+"> is not acceptable.");
 		}
	}

	public static boolean check1(String password) {
		char[] moeum = {'a','e','i','o','u'};
		for(int i = 0; i<password.length(); i++) {
			for(int j = 0; j<moeum.length; j++) {
				if(password.charAt(i) == moeum[j]) {
					return true;
				} 
			}
		}
		return false;
	}

	public static boolean check2(String password) {
		char[] moeum = {'a','e','i','o','u'};
		boolean[] ch = new boolean[password.length()];
		for(int i = 0; i<password.length(); i++) {
			for(int j = 0; j<moeum.length; j++) {
				if(password.charAt(i) == moeum[j]) ch[i] = true;
			}
		}

		for(int k = 0; k<password.length()-2; k++) {
			if(ch[k] == ch[k+1] && ch[k+1] == ch[k+2]) return false;
		}
		return true;
	}

	public static boolean check3(String password) {
		boolean flag = true;
		for(int k = 0; k<password.length()-1; k++) {
			if(password.charAt(k) == password.charAt(k+1)) {
				if(password.charAt(k) == 'e' || password.charAt(k) == 'o') flag = true;
				else flag = false;
			}
		}
		return flag;
	}

}
