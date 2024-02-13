import java.io.*;
import java.util.*;

public class BAEKJOON1835 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		Deque<Integer> dq = new LinkedList<>();
		dq.addLast(N);
	
		for(int i = N-1; i>0; i--) {
			dq.addFirst(i);
			for(int j = 0; j<i; j++) {
				dq.addFirst(dq.pollLast());
			}
		}
		for(int num : dq) {
			System.out.print(num+" ");
		}
	}

}
