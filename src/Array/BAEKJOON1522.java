import java.io.*;
import java.util.*;

public class BAEKJOON1522 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		int Acnt = 0, answer = Integer.MAX_VALUE, Bcnt = 0;
		for(int i = 0; i<str.length(); i++) {
			if(str.charAt(i) == 'a') Acnt++;
		}
		
		int n = str.length();
		ArrayList<Character> list = new ArrayList<>();
		for(int i = 0; i<n; i++) {
			
			for(int j = 0; j<Acnt; j++) {
				list.add(str.charAt((i+j) % n));
				
			}
			
			for(char x : list) {
				if(x=='b') Bcnt++;
			}
			answer = Math.min(answer, Bcnt);
			list = new ArrayList<>();
			Bcnt = 0;
		}
		System.out.println(answer);
		
	}

}
