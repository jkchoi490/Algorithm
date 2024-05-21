import java.io.*;
import java.util.*;

public class BAEKJOON2467 {
	
	static int n;
	static long[] arr;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		arr = new long[n];
		for(int i = 0; i<n; i++) arr[i] = Long.parseLong(st.nextToken());

		int left = 0, right = n-1;
		
		int closestSum =(int) Math.abs(arr[left]+arr[right]);
		long bestLeft = arr[left], bestRight = arr[right];
		
		while(left < right) {
			int sum = (int) (arr[left]+arr[right]);
			
			if(Math.abs(sum) <closestSum) {
				closestSum = (int) Math.abs(sum);
                bestLeft = arr[left];
                bestRight = arr[right];
			}
			  if (sum < 0) {
	                left++;
	            } else {
	                right--;
	            }
		}
		 System.out.println(bestLeft + " " + bestRight);

	}

}
