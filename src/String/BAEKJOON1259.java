import java.io.*;
import java.util.*;

public class BAEKJOON1259 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while(true) {
			String s = br.readLine();
			int n = s.length();
			boolean flag = true;
			if(s.length()==1 && s.charAt(0)=='0') break;
			
			for(int i = 0; i<n/2; i++) {
			if(s.charAt(i)!=s.charAt(n-1-i)) 
				flag = false;
			}
			if(flag) System.out.println("yes");
			else System.out.println("no");
			
		}

	}

}
