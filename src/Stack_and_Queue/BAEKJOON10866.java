import java.io.*;
import java.util.*;

public class BAEKJOON10866 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		Deque<Integer> deque = new LinkedList<>();
		for(int i = 0; i<N; i++) {
			String[] command = br.readLine().split(" ");
			if(command.length>1) {
				if(command[0].equals("push_front")) {
					deque.addFirst(Integer.parseInt(command[1]));
				}
				else if(command[0].equals("push_back")) {
					deque.addLast(Integer.parseInt(command[1]));
				}
					
			}else {
				if(command[0].equals("front")) {
					if(deque.isEmpty()) System.out.println(-1);
					else System.out.println(deque.peek());
				}
				
				else if(command[0].equals("back")) {
					if(deque.isEmpty()) System.out.println(-1);
					else System.out.println(deque.getLast());
				}
				
				else if(command[0].equals("empty")) {
					if(deque.isEmpty()) System.out.println(1);
					else System.out.println(0);
				}
				
				else if(command[0].equals("size")) {
					System.out.println(deque.size());
				}
				
				else if(command[0].equals("pop_front")) {
					if(deque.isEmpty()) System.out.println(-1);
					else{
						int front = deque.pollFirst();
						System.out.println(front);
					}
				}
				
				else if(command[0].equals("pop_back")) {
					if(deque.isEmpty()) System.out.println(-1);
					else{
						int back = deque.pollLast();
						System.out.println(back);
					}
				}
				
			}
		}
	}

}
