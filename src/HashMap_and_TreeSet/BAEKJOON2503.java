import java.io.*;
import java.util.*;

public class BAEKJOON2503 {
	
	static int answer = 0;
	static HashMap<Integer, Integer> map;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		map = new HashMap<>();
		for(int i = 0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int num = Integer.parseInt(st.nextToken());
			int strike = Integer.parseInt(st.nextToken());
			int ball = Integer.parseInt(st.nextToken());
			solve(num, strike, ball);
		}
		
		for(int key : map.keySet()) {
			if(map.get(key) == n) {
				answer++;
			}
		}
		System.out.println(answer);
	}

	public static void solve(int num, int strike, int ball) {
		int[] nums = new int[3];
		String s = String.valueOf(num);
		for(int i = 0; i<3; i++) {
			nums[i] = (int)(s.charAt(i)-'0');
		}
		
		int[] input = new int[3];

		for(int i = 1; i<=9; i++) {
			for(int j = 1; j<=9; j++) {
				for(int k = 1; k<=9; k++) {
					if(i!=j && j!=k && i!=k) {
					input[0] = i;
					input[1] = j;
					input[2] = k;
					if(CountStrike(nums, input) == strike && CountBall(nums, input) == ball) {
						int cur = getNum(input);
						if(map.containsKey(cur)) {
							map.put(cur, map.getOrDefault(cur, map.get(cur))+1);
						}else {
							map.put(cur, 1);
						}
					}
				}
				}
			}
		}
		
	}

	public static int CountStrike(int[] nums, int[] input) {
		int cnt = 0;
		for(int i = 0; i<3; i++) {
			if(nums[i] == input[i]) cnt++;
		}
		return cnt;
	}

	public static int CountBall(int[] nums, int[] input) {
		int cnt = 0;
		for(int i = 0; i<3; i++) {
			for(int j = 0; j<3; j++) {
				if((i!=j) && nums[i] == input[j]) {
					cnt++;
				}
			}
		}
		return cnt;
	}

	public static int getNum(int[] input) {
		String num = "";
		for(int i = 0; i<3; i++) {
			num += String.valueOf(input[i]);
		}
		int result = Integer.parseInt(num);
		return result;
	}

}
