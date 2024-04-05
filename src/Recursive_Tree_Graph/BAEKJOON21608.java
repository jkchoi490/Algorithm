import java.io.*;
import java.util.*;

public class BAEKJOON21608 {

	static int N;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st;
		HashMap<Integer, int[]> map = new HashMap<>();
		for(int i = 0; i<N*N; i++) {
			st = new StringTokenizer(br.readLine());
			int student = Integer.parseInt(st.nextToken());
			int[] friends = new int[4];
			for(int j = 0; j<4; j++) {
				friends[j] = Integer.parseInt(st.nextToken());
			}
			map.put(student, friends);
		}
		
		
		
		
	}

}
