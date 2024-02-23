import java.io.*;
import java.util.*;

public class BAEKJOON1927 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i<N; i++) {
			int num = Integer.parseInt(br.readLine());
			if(num == 0 && pq.isEmpty()) sb.append(0).append("\n");
			else if(num == 0 && !pq.isEmpty()) {
				sb.append(pq.poll()).append("\n");
			}
			else {
				pq.offer(num);
			}
		}
		System.out.println(sb);

	}

}
