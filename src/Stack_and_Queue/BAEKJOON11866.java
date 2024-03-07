import java.io.*;
import java.util.*;

public class BAEKJOON11866 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		Queue<Integer> q = new LinkedList<>();
		
		StringBuilder sb = new StringBuilder();
		sb.append("<");
		for(int i = 1; i<=N; i++) q.offer(i);
		int cnt = 0;
		while(!q.isEmpty()) {
			cnt = K-1;
			while(cnt != 0) {
				q.offer(q.poll());
				cnt--;
			}
				sb.append(q.poll()).append(",").append(" ");
				
		}
		sb.append(">");
		sb.delete(sb.length()-3, sb.length()-1);
		System.out.println(sb.toString());
	}
	
}
