package Array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution_Array2 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		ArrayList<Integer> students = new ArrayList<>();
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0; i<N; i++) {
			students.add(Integer.parseInt(st.nextToken()));
		}
		
		System.out.println(solution(students));
	}

	//이중포문 돌면 터짐 -> (5<=N<=100,000) -> O(N^2)
	
	private static int solution(ArrayList<Integer> students) {
		int cnt = 1; 
		int target = students.get(0);
		for(int n = 1; n<students.size(); n++) {
			if(target<students.get(n)) {
				target = students.get(n);
				cnt++;
			}
		}
		
		return cnt;
	}

	public int solution_T(int n, int[] arr){
		int answer=1, max=arr[0];
		for(int i=1; i<n; i++){
			if(arr[i]>max){
				max=arr[i];
				answer++;
			}
		}
		return answer;
	}
}
