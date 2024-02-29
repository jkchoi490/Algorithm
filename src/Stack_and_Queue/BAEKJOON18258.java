import java.util.*;
import java.io.*;

public class BAEKJOON18258 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		Queue<Integer> q = new LinkedList<>();
		int value = 0;
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i<N; i++) {
			String[] command = br.readLine().split(" ");
			if(command.length>1) {
				if(command[0].equals("push")) {
					int x = Integer.parseInt(command[1]);
					value = x;
					q.add(x);
				}
			}
			else {
				if(command[0].equals("pop")) {
					if(q.isEmpty()) sb.append(-1).append("\n");
					else sb.append(q.poll()).append("\n");
				}
				else if(command[0].equals("size")) sb.append(q.size()).append("\n");
				else if(command[0].equals("empty")) {
					if(q.isEmpty())sb.append(1).append("\n");
					else sb.append(0).append("\n");
				}
				else if(command[0].equals("front")) {
					if(q.isEmpty())sb.append(-1).append("\n");
					else sb.append(q.peek()).append("\n");
				}
				else if(command[0].equals("back")) {
					if(q.isEmpty())sb.append(-1).append("\n");
					else {
						sb.append(value).append("\n");
					}
				}
			}
		}
		System.out.println(sb);
		
	}

}
