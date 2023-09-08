import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution15 { // 시험점수
	static int S = 0;
	static int T = 0;
	public static void main(String[] args) throws IOException {
		  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	      StringTokenizer st = new StringTokenizer(br.readLine());
	      StringTokenizer st2 = new StringTokenizer(br.readLine());
	     
	      for(int i = 0; i<4; i++) {
	    	  S += Integer.parseInt(st.nextToken());
	    	  T += Integer.parseInt(st.nextToken());
	      }
	      
	      if(S >= T) System.out.println(S);
	      else System.out.println(T);
			
	}

}
