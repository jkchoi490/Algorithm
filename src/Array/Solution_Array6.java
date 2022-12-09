package Array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution_Array6 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] arr = new int[N];
		for(int i = 0; i<N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		for(int x : solution_p(N, arr)) {
			System.out.print(x+" ");
		}
	}

	public static boolean isPrime(int num){
		if(num==1) return false;
		for(int i=2; i<num; i++){
			if(num%i==0) return false;
		}
		return true;
	}

	public static ArrayList<Integer> solution_p(int n, int[] arr){
		ArrayList<Integer> answer = new ArrayList<>();//배열 생성
		
		for(int i=0; i<n; i++){
			int tmp=arr[i]; //입력값에서 하나 받아옴
			int res=0; //뒤집은 수를 저장하기 위해 0으로 초기화
			while(tmp>0){ 
				int t=tmp%10;
				res=res*10+t;
				tmp=tmp/10;
			}
			if(isPrime(res)) answer.add(res);
		}
		return answer;
	}
	
	
	private static String solution(int[] arr, int N) {
		StringBuilder ans = new StringBuilder();
		String str = "";
		String reverse_str = "";
		int reverse_num = 0;
		int[] nums = new int[N]; // 약수 개수 배열
		
		int[] numbers = {2,3,5,7}; // 나누는  수
		int cnt = 1; // 약수의 개수
		
	
		
		for(int i = 0; i<arr.length; i++) {
			str = String.valueOf(arr[i]);
			StringBuilder sb = new StringBuilder(str);
			reverse_str = sb.reverse().toString();
			reverse_num = Integer.parseInt(reverse_str);
			
			for(int n = 0; n<numbers.length; n++) {
				if(reverse_num % numbers[i] == 0) {
					cnt++;
					System.out.println(reverse_num+" : "+cnt);
					if(reverse_num / numbers[i] != 1) {
						cnt++;
					}
				}
			}
		}

		return ans.toString();
	}
	
}
