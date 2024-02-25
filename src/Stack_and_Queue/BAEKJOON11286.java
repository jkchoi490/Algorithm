import java.io.*;
import java.util.*;

public class BAEKJOON11286 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)-> a[0]==b[0] ? a[1]-b[1] :a[0]-b[0]);
		for(int i = 0; i<N; i++) {
			int x = Integer.parseInt(br.readLine());
			if(x == 0 && !pq.isEmpty()) {
				System.out.println(pq.poll()[1]);
			}
			else if(x == 0 && pq.isEmpty()) {
				System.out.println(0);
			}

			else {
				pq.offer(new int[] {Math.abs(x), x});
			}
		}

	}

}
