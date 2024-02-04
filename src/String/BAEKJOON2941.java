import java.io.*;
import java.util.*;

public class BAEKJOON2941 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = br.readLine();
		int cnt = 0;
		int len = input.length();
		for(int i = 0; i<len; i++) {
			char tmp = input.charAt(i);
			if(tmp =='c' && i< len -1) { 
				if(input.charAt(i+1) == '=' || input.charAt(i+1)=='-') {
					i++;
				}
			}
			
			else if(tmp =='d' && i <len) {
				if(input.charAt(i+1) == '-') { 
					i++;
				}
				else if(input.charAt(i+1) =='z' && i <len-2) {
					if(input.charAt(i+2)=='=') {
						i+=2;
					}
				}
			}
			
			else if((tmp=='l' || tmp=='n') && i <len-1) {
				if(input.charAt(i+1)=='j') {
					i++;
				}
			}
			else if((tmp=='s' || tmp=='z') && i < len-1) {
				if(input.charAt(i+1)=='=') { 
					i++;
				}
			}
			cnt++;
		}
		System.out.println(cnt);
	}

}
