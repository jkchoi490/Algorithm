package Array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution_Array1 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());

		int[] nums = new int[N];
		for(int i = 0; i<nums.length; i++) {
			nums[i] = Integer.parseInt(st.nextToken());
		}
		System.out.println(solution(nums));
	}

	private static String solution(int[] nums) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(nums[0]).append(" "); //idx 초과 에러 발생할 수 있으므로 0번째 원소 먼저 삽입 
		for(int n = 1; n<nums.length; n++) {
			if(nums[n-1]<nums[n]) {
				sb.append(nums[n]).append(" ");
			}
		}
		return sb.toString();
	}
	
	public ArrayList<Integer> solution(int n, int[] arr){
		ArrayList<Integer> answer = new ArrayList<>();
		answer.add(arr[0]);
		for(int i=1; i<n; i++){
			if(arr[i]>arr[i-1]) answer.add(arr[i]);
		}
		return answer;
	}
	
	
	
}
