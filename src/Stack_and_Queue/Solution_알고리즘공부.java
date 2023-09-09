import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution_알고리즘공부 { //알고리즘 공부

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		Queue<Integer> q = new LinkedList<>(); 
		int[] visited = new int[100005]; // 
		
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		int[] algo = new int[100005];
		
		for(int k = 0; k<N; k++) algo[k] = Integer.parseInt(st.nextToken());
		int R = Integer.parseInt(br.readLine());
		for(int i =0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			int D = Integer.parseInt(st.nextToken());
			
			
		}

	}

}
