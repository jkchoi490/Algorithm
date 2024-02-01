import java.io.*;
import java.util.*;

public class BAEKJOON24511 { 

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] A = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0; i<N; i++)A[i] = Integer.parseInt(st.nextToken());
		int[] B = new int[N];
		 st = new StringTokenizer(br.readLine());
		 for(int i = 0; i<N; i++)B[i] = Integer.parseInt(st.nextToken());
		 int M = Integer.parseInt(br.readLine());
		 int[] C = new int[N];
		 st = new StringTokenizer(br.readLine());
		 for(int i = 0; i<M; i++)C[i] = Integer.parseInt(st.nextToken());
		 Deque<Integer> dq = new LinkedList<>();
		 for(int i = 0; i<N; i++) {
			 if(A[i]==0) dq.addFirst(B[i]);
		 }
		 for(int j = 0; j<M; j++) {
			 dq.offer(C[j]);
			 System.out.print(dq.pollFirst()+" ");
		 }
		
	}

}
