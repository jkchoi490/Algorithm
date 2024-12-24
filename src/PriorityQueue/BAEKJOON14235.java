import java.io.*;
import java.util.*;

public class BAEKJOON14235 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i<n; i++) {
			String[] str = br.readLine().split(" ");
			int a = Integer.parseInt(str[0]);
			if(str.length == 1 && a == 0) {
				if(pq.isEmpty()) {
					sb.append(-1).append("\n");
				}else {
					sb.append(pq.poll()).append("\n");
				}
			}else {
				for(int j = 1; j<str.length; j++) {
					pq.offer(Integer.parseInt(str[j]));
				}
			}
			
		}
		
		System.out.println(sb.toString());

	}

}
