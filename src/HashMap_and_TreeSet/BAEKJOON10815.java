import java.io.*;
import java.util.*;

public class BAEKJOON10815 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		HashMap<Integer, Integer> map = new HashMap<>();
		for(int i = 0; i<N; i++) {
			int num = Integer.parseInt(st.nextToken());
			map.put(num, 1);
		}
		int M = Integer.parseInt(br.readLine());
		int[] answer = new int[M];
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i<M; i++) {
			int number = Integer.parseInt(st.nextToken());
			if(map.containsKey(number)) answer[i] = 1;
			else answer[i] = 0;
		}
		
		for(int x : answer) System.out.print(x+" ");
	}

}
