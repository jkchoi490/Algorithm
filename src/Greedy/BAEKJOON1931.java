import java.io.*;
import java.util.*;

public class BAEKJOON1931 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int[][] arr = new int[n][2];
		
		StringTokenizer st;
		  for (int i = 0; i < n; i++) {
	        st = new StringTokenizer(br.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken());  
	        arr[i][1] = Integer.parseInt(st.nextToken());
	    }
		  
		Arrays.sort(arr, (a,b) -> {
			if(a[1] ==b[1]) {
				return a[0]-b[0];
			}
			return a[1]-b[1];
		});
		
		int cnt = 0;
		int time = 0;
		for(int i = 0; i<n; i++) {
			if(arr[i][0] >= time) {
				time = arr[i][1];
				cnt++;
			}
		}
		
		System.out.println(cnt);
	}

}
