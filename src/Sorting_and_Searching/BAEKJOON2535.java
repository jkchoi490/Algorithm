import java.io.*;
import java.util.*;

public class BAEKJOON2535 {
	static int N;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->b[2]-a[2]);
		for(int i = 0; i<N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int country = Integer.parseInt(st.nextToken());
			int num = Integer.parseInt(st.nextToken());
			int score = Integer.parseInt(st.nextToken());
			pq.offer(new int[] {country, num, score});
		}
		ArrayList<int[]> countrys = new ArrayList<>();
		HashMap<Integer, Integer> map = new HashMap<>();
		
		int cnt = 0;
		while(true) {
			int[] x = pq.poll();
			map.put(x[0], map.getOrDefault(x[0], 0)+1);
			if(map.get(x[0]) > 2) {
				continue;
			}
			else {
				countrys.add(x);
				cnt++;
			}
			if(cnt == 3) break;
		}
		
		for(int[] c : countrys) {
			System.out.println(c[0]+" "+c[1]);
		}

	}

}
