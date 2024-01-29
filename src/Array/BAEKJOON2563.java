import java.io.*;
import java.util.*;

public class BAEKJOON2563 {
	
	public static void main(String[] args) throws IOException{
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));    
	     int[][] board = new int[100][100];
		 int n = Integer.parseInt(br.readLine());  
		 for(int i = 0; i<n; i++) {
			 StringTokenizer st = new StringTokenizer(br.readLine());
		     int x = Integer.parseInt(st.nextToken());
		     int y = Integer.parseInt(st.nextToken());
		     for(int j = x; j<x+10; j++) {
		    	 for(int k = y; k<y+10; k++) {
		    		 board[j][k] = 1;
		    	 }
		     }
		 }
		 int sum = 0;
		 for(int l = 0; l<100; l++) {
	    	 for(int m = 0; m<100; m++) {
	    		 if(board[l][m]==1) sum += board[l][m];
	    	 }
	     }
		 System.out.println(sum);
	}
	

}
