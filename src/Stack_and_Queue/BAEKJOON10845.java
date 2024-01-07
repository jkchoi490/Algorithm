import java.util.*;
import java.io.*;

public class BAEKJOON10845 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		Queue<Integer> q = new LinkedList<>();
		int value = 0;
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
					if(q.isEmpty()) System.out.println(-1);
					else System.out.println(q.poll());
				}
				else if(command[0].equals("size")) System.out.println(q.size());
				else if(command[0].equals("empty")) {
					if(q.isEmpty())System.out.println(1);
					else System.out.println(0);
				}
				else if(command[0].equals("front")) {
					if(q.isEmpty())System.out.println(-1);
					else System.out.println(q.peek());
				}
				else if(command[0].equals("back")) {
					if(q.isEmpty())System.out.println(-1);
					else {
						System.out.println(value);
					}
				}
			}
		}
		
	}

}
