import java.io.*;
import java.util.*;

public class BAEKJOON20055 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	     StringTokenizer st = new StringTokenizer(br.readLine());
	     int N = Integer.parseInt(st.nextToken());
	     int K = Integer.parseInt(st.nextToken());
	     st = new StringTokenizer(br.readLine());
	     int[] A = new int[2*N];
	     int[] Robot = new int[N];
	     for(int i = 0; i<2*N; i++) A[i] = Integer.parseInt(st.nextToken());
	     int answer = 0;
	     int cnt = 0;
	     while(true) {
	    	 answer++;

	    	 int tmp = A[2*N-1];
	         for (int i = 2*N-1; i > 0; i--) {
	             A[i] = A[i - 1];
	         }
	    	 A[0] = tmp;
	  
	    	 for(int i = N-1; i>0; i--) {
	    		 Robot[i] = Robot[i-1];
	    	 }
	    	 Robot[0] = 0;
	   
	    	 Robot[N-1] = 0;
	    	 
	    	 for(int i = N-2; i>0; i--) {
	    		 if(Robot[i] == 1 && Robot[i+1] == 0 && A[i+1]>0) {
	    			 Robot[i] = 0;
	    			 Robot[i+1] = 1;
	    			 A[i+1] -= 1;
	    			 if(A[i+1] == 0) cnt++;
	    		 }
	    	 }

	    	 if(A[0]>0) {
	    		 Robot[0] = 1;
	    		 A[0] -= 1;
	    		 if(A[0] == 0) cnt++;
	    	 }
	    	 
	    	 if(cnt >= K) break;
	     }
	     System.out.println(answer);
	}

}
