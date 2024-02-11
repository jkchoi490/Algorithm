import java.io.*;
import java.util.*;

public class BAEKJOON2828 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int n = Integer.parseInt(st.nextToken());  
		int m = Integer.parseInt(st.nextToken());  
		int apple = Integer.parseInt(br.readLine());  
		
		int left = 1;  
		int right = m; 
		int sum = 0; 
		
		for(int i = 0; i < apple; i++) {
			int where = Integer.parseInt(br.readLine());
			
			if(left <= where && where <= right) {
				continue;
			}
			
			if(left > where) {
				sum += (left - where);
				right -= (left - where);
				left = where;
			}

			else{
				sum += (where - right);
				left += (where - right);
				right = where;
			}
		}
		
		System.out.println(sum);

	}
}
