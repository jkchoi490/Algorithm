import java.io.*;
import java.util.*;

public class BAEKJOON28279 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		Deque<Integer> deque = new LinkedList<>();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i<N; i++) {
			String[] s = br.readLine().split(" ");
			int command = Integer.parseInt(s[0]);
			
			if(command == 1) {
				deque.offerFirst(Integer.parseInt(s[1]));
			}
			else if(command==2) {
				deque.offerLast(Integer.parseInt(s[1]));
			}
			
			else if(command==3) {
				if(!deque.isEmpty()) {
					int num = deque.pollFirst();
					sb.append(num).append("\n");	
				}
				else sb.append(-1).append("\n");
			}
			
			else if(command==4) {
				if(!deque.isEmpty()) {
					int num = deque.pollLast();
					sb.append(num).append("\n");	
				}
				else sb.append(-1).append("\n");
			}
			
			else if(command==5) {
				sb.append(deque.size()).append("\n");
			}
			
			else if(command==6) {
				if(deque.isEmpty()) sb.append(1).append("\n");
				else sb.append(0).append("\n");
			}
			
			else if(command==7) {
				if(!deque.isEmpty()) {
					sb.append(deque.peekFirst()).append("\n");	
				}
				else sb.append(-1).append("\n");
			}
			
			else if(command==8) {
				if(!deque.isEmpty()) {
					sb.append(deque.peekLast()).append("\n");	
				}
				else sb.append(-1).append("\n");
			}
		}
		System.out.println(sb);
	}

}
