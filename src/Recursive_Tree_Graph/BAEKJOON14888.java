import java.io.*;
import java.util.*;

public class BAEKJOON14888 {

	static int n, max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
	static int[] A;
	static String[] cal;
	static boolean[] visited;
	static ArrayList<String> arr;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		cal = new String[n+n-1];
		arr = new ArrayList<>();
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0; i<n; i++) cal[i*2] = st.nextToken();
		st = new StringTokenizer(br.readLine());
		int plus_cnt = Integer.parseInt(st.nextToken());
		int minus_cnt = Integer.parseInt(st.nextToken());
		int mul_cnt = Integer.parseInt(st.nextToken());
		int div_cnt = Integer.parseInt(st.nextToken());
		
		for(int i = 0;i<plus_cnt; i++) {
			arr.add("+");
		}
		for(int i = 0;i<minus_cnt; i++) {
			arr.add("-");
		}
		for(int i = 0;i<mul_cnt; i++) {
			arr.add("x");
		}
		for(int i = 0;i<div_cnt; i++) {
			arr.add("/");
		}
		visited = new boolean[arr.size()];

		DFS(0);
		System.out.println(max);
		System.out.println(min);
	}
	public static void DFS(int L) {
		if(L == arr.size()) {
			calculate(cal);
			return;
		}else {
			for(int i = 0; i<arr.size(); i++) {
				if(!visited[i]) {
					visited[i] = true;
					cal[2*L+1] = arr.get(i);
					DFS(L+1);
					visited[i] = false;
				}
			}
		}
		
	}
	
	public static void calculate(String[] cal) {
		int value = Integer.parseInt(cal[0]);
		
		for(int i = 0; i<cal.length/2; i++) {
			if(cal[2*i+1].equals("+")) {
				value += Integer.parseInt(cal[2*i+2]);
			}
			else if(cal[2*i+1].equals("-")) {
				value -= Integer.parseInt(cal[2*i+2]);
			}
			else if(cal[2*i+1].equals("x")) {
				value *= Integer.parseInt(cal[2*i+2]);
			}
			if(cal[2*i+1].equals("/")) {
				value /= Integer.parseInt(cal[2*i+2]);
			}
		}
		max = Math.max(max, value);
		min = Math.min(min, value);

		
		
	}

}
