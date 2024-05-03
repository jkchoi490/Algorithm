import java.io.*;
import java.util.*;

public class BAEKJOON14719 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int H = Integer.parseInt(st.nextToken());
		int W = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		int[] arr = new int[W];
		for(int i = 0; i<W; i++) arr[i] = Integer.parseInt(st.nextToken());
	
		int[] result = new int[W];
		result[0] = 0; result[W-1] = 0;
		for(int i = 1; i<W-1; i++) {
			int cur = arr[i];
			int left_max = 0, right_max = 0;
			for(int j = 0; j<i; j++){
				left_max = Math.max(left_max, arr[j]);
			}
			for(int k = i; k<W; k++) {
				right_max = Math.max(right_max, arr[k]);
			}
			
			int value = Math.min(left_max, right_max)-cur;
			if(value < 0) result[i] = 0;
			else result[i] = value;
		}
		int answer = 0;
		for(int x : result) {
			answer += x;
		}
		System.out.println(answer);
	}

}
