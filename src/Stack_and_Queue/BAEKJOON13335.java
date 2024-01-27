import java.io.*;
import java.util.*;

public class BAEKJOON13335 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int w = Integer.parseInt(st.nextToken());
		int L = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		Queue<Integer> q = new LinkedList<>();
		int[] a = new int[n];
		int time = 0,sum = 0, trucks = 0;
		for(int i = 0; i<n; i++) {
			a[i] = Integer.parseInt(st.nextToken());
			trucks += a[i];
		}
		
		for(int truck : a) {
			while(true) {
				if(q.isEmpty()) {
					q.offer(truck);
					sum += truck;
					trucks -= truck;
					time++;
					break;
				}
				else {
					if(q.size() == w) { 
						sum -= q.poll();
					}
					else if(sum+truck <= L) {
						q.offer(truck);
						sum += truck;
						trucks -= truck;
						time++;
						break; 
					}
					else {
						q.offer(0);
						time++;
					}
				}
				if(trucks == 0) break;
			}
		}
		System.out.println(time+w);
	}

}
