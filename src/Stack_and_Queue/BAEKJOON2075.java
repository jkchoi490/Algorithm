import java.io.*;
import java.util.*;

public class BAEKJOON2075 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> b - a);
		StringTokenizer st;
		for(int i = 0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<N; j++) {
				int num = Integer.parseInt(st.nextToken());
				pq.offer(num);
			}
		}
		int number = 0;
		while(N-- > 0) {
			number = pq.poll();
		}
		System.out.println(number);
	}

}
