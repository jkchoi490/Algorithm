import java.io.*;
import java.util.*;

public class BAEKJOON2161 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		Queue<Integer> q = new LinkedList<>();
		StringBuilder sb = new StringBuilder();
		for(int i = 1; i<=N; i++) q.add(i);

		while(q.size()>1) {
			int a = q.poll();
			sb.append(a).append(" ");
			q.offer(q.poll());
		}
		sb.append(q.poll());
		System.out.println(sb);
	}

}
