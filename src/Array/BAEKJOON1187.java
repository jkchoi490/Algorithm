import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BAEKJOON1187 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    int n = Integer.parseInt(br.readLine());
	    int[] arr = new int[(2*n)-1];
	    StringTokenizer st = new StringTokenizer(br.readLine());
	    for(int i = 0; i<(2*n-1); i++) arr[i] = Integer.parseInt(st.nextToken());
	    int k = 1;
	    int tmp = n;
	    while((tmp>>=1) != 1) {
	    	k++;
	    }	    
	}
}
