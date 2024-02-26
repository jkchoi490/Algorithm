import java.io.*;
import java.util.*;

public class BAEKJOON11279 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		PriorityQueue<Integer> pq = new PriorityQueue<>((a,b)-> b - a);
		for(int i = 0; i<N; i++) {
			int x = Integer.parseInt(br.readLine());
			if(x == 0 && !pq.isEmpty()) {
				System.out.println(pq.poll());
			}
			else if(x == 0 && pq.isEmpty()) {
				System.out.println(0);
			}

			else {
				pq.offer(x);
			}
		}

	}

}
