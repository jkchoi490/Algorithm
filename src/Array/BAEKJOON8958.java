import java.io.*;
import java.util.*;

public class BAEKJOON8958 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int cnt = 0;
		int sum = 0;
		for(int i = 0; i<N; i++) {
			char[] result = br.readLine().toCharArray();
			int[] nums = new int[result.length];
			sum = 0;
			for(int j = 0; j<result.length; j++) {
				if(result[j]=='O') {
					cnt++;
					nums[j] = cnt;
				}
				else {
					cnt=0;
					nums[j] = 0;
				}
			}
			cnt =0;
			for(int x : nums) sum += x;
			System.out.println(sum);
		}
	}
}
