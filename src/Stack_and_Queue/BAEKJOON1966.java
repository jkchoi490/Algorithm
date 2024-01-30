import java.io.*;
import java.util.*;

public class BAEKJOON1966 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		for(int tc= 1; tc<=T; tc++) {
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
		
			LinkedList<int[]> Q = new LinkedList<>(); 
			st = new StringTokenizer(br.readLine());
			for(int i = 0; i<N; i++) {
				Q.add(new int[] {i,Integer.parseInt(st.nextToken())});
			}
	
			int cnt = 0;
			while(!Q.isEmpty()) {
				int[] now = Q.poll();
				boolean isMax = true; 
				
				for(int i = 0; i<Q.size(); i++) {
					if(now[1] < Q.get(i)[1]) {
						Q.offer(now);
						for(int j = 0; j<i; j++) {
							Q.offer(Q.poll());
						}
						isMax = false;
						break;
					}
				}
				if(isMax == false) {
					continue;
				}
				cnt++;
				if(now[0]==M) {
					break;
				}
			}
			sb.append(cnt).append('\n');
		}
		System.out.println(sb);
	}


}
