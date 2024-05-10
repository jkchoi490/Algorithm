import java.io.*;
import java.util.*;

public class BAEKJOON9017 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		for(int tc = 1; tc<=T; tc++) {
			int N = Integer.parseInt(br.readLine());
			StringTokenizer st = new StringTokenizer(br.readLine());
			int[] arr = new int[N];
			for(int i = 0; i<N; i++) arr[i] = Integer.parseInt(st.nextToken());
			solve(N, arr);
		}

	}

	public static void solve(int n, int[] arr) {
		HashMap<Integer,ArrayList<Integer>> map = new HashMap<>();
		HashMap<Integer,ArrayList<Integer>> scores = new HashMap<>();
		
		HashMap<Integer,Integer> hm = new HashMap<>();
		for(int x : arr) {
			hm.put(x, hm.getOrDefault(x, 0)+1);
		}
		
		for(int key : hm.keySet()) {
			ArrayList<Integer> list = new ArrayList<>();
			map.put(key, list);
		}
		
		for(int i = 0; i<arr.length; i++) {
			if(map.containsKey(arr[i])) {
				map.get(arr[i]).add(i);
			}
		}
		int[] score = new int[n];
		boolean[] flag = new boolean[n];
		for(int k : hm.keySet()) {
			int v = hm.get(k);
			if(v >= 6) {
				flag[k] = true;
				ArrayList<Integer> list = new ArrayList<>();
				scores.put(k, list);
			}
		}
		int now_score = 1;
		for(int i = 0; i<n; i++) {
			if(flag[arr[i]] == true) {
				score[i] = now_score;
				now_score++;
			}
		}
		
		for(int key : scores.keySet()) {
			for(int value : map.get(key)) {
				scores.get(key).add(score[value]);
			}
		}

		ArrayList<int[]> result = new ArrayList<>();
		for(int team : scores.keySet()) {
			int sum = 0;
			for(int i = 0; i < 4; i++) {
				sum += scores.get(team).get(i);
			}
			int five = scores.get(team).get(4);
			result.add(new int[] {team, sum, five});
		}
	
		
		result.sort( (a,b) -> a[1]==b[1]? a[2]-b[2] : a[1]-b[1]);
		
		System.out.println(result.get(0)[0]);
	}

}
