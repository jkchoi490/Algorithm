import java.io.*;
import java.util.*;

class Jewel{
	int weight, value;
	public Jewel(int weight, int value) {
		this.weight=weight;
		this.value=value;
	}
}


public class BAEKJOON1202 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); 
	
		int K = Integer.parseInt(st.nextToken()); 
		Jewel[] jewel = new Jewel[N];
		for(int i = 0; i<N; i++) {
			 st = new StringTokenizer(br.readLine());
			 int M = Integer.parseInt(st.nextToken()); 
			 int V = Integer.parseInt(st.nextToken()); 
			 jewel[i] = new Jewel(M, V);
		}
		int[] bags = new int[K];
		for (int i = 0; i < K; i++) {
	       bags[i] = Integer.parseInt(br.readLine());
	     }
		
		Arrays.sort(jewel, new Comparator<Jewel>() {

			@Override
			public int compare(Jewel o1, Jewel o2) {
				return o1.weight - o2.weight;
				
			}
			
		});
		
		Arrays.sort(bags);
		
		PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
		long max_value = 0;
		int jewelIndex = 0;

		for(int bag : bags) {

			while(jewelIndex < N && jewel[jewelIndex].weight <= bag) {
				pq.offer(jewel[jewelIndex].value);
				jewelIndex++;
			}
		
			if(!pq.isEmpty()) {
				max_value += pq.poll();
			}
			
		}
		
		System.out.println(max_value);
		
	}

}
