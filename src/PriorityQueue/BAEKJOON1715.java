package PriorityQueue;

import java.io.*;
import java.util.*;

public class BAEKJOON1715 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] cards = new int[N];
		for(int i = 0; i<N; i++) {
			cards[i] = Integer.parseInt(br.readLine());
		}

		Arrays.sort(cards);
		PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> a-b);
		
		int answer = 0;
		for(int i = 0; i<N; i++) {
			pq.add(cards[i]);
		}
		
		while(pq.size()>1) {
			int A = pq.poll();
			int B = pq.poll();
			
			int sum = A+B;
			answer += sum;
			
			pq.offer(sum);
		}
		System.out.println(answer);
		
	}

}
