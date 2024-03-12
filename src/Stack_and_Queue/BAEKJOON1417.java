import java.io.*;
import java.util.*;

public class BAEKJOON1417 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		PriorityQueue<Integer> pq = new PriorityQueue<>((a,b)->b-a);
		int d = Integer.parseInt(br.readLine());
		for(int i = 0; i<N-1; i++) {
			pq.offer(Integer.parseInt(br.readLine()));
		}
		int cnt = 0;
		while(!pq.isEmpty()) {
			int x = pq.poll();
			if(x < d) break;
			
			d += 1;
			cnt++;
			pq.offer(x-1);
		}
		System.out.println(cnt);
		
	}

}
