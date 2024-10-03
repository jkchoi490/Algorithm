import java.io.*;
import java.util.*;

public class BAEKJOON2012 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N+1];
		for(int i = 0; i<N; i++) {
			arr[i+1] = Integer.parseInt(br.readLine());
		}
		
		Arrays.sort(arr);

		long min = 0;
		for(int idx = 1; idx<=N; idx++) {
			min += Math.abs(idx-arr[idx]);
		}
		System.out.println(min);

	}

}
