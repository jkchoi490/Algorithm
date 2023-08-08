package Stack_and_Queue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution_Queue1 { // 공주구하기

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		System.out.println(solution(N,K));
	}

	private static int solution(int n, int k) {
		int ans = 0;
		Queue<Integer> q = new LinkedList<>();
		for(int i = 1; i<=n; i++) q.add(i);
		
		while(!q.isEmpty()) {
			for(int m = 1; m<k; m++) {
				q.add(q.poll());
			}
			q.poll();
			if(q.size()==1) ans = q.poll();
			
		}
	
		return ans;
	}

}
 