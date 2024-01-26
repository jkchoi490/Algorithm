import java.io.*;
import java.util.*;

public class BAEKJOON15828 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		Deque<Integer> dq = new LinkedList<>();
		while(true) {
			int num = Integer.parseInt(br.readLine());
			if(num > 0) {
				dq.offer(num);
			}
			if(dq.size() > N) {
				dq.pollLast();
			}
			if(num == 0) {
				dq.poll();
			}
			
			if(num==-1) break;
		}
		
		if(dq.isEmpty()) System.out.println("empty");
		else {
			for(int x : dq) System.out.print(x+" ");
		}
	
	}

}
