import java.util.*;
import java.io.*;

public class BAEKJOON2566 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[][] arr = new int[9][9];
		int max = Integer.MIN_VALUE;
		int max_i = 0, max_j = 0;
		StringTokenizer st;
		for(int i = 0; i<9; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<9; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
				max = Math.max(max, arr[i][j]);
				if(max == arr[i][j]) {
					max_i = i+1;
					max_j = j+1;
				}
			}
		}
		System.out.println(max);
		System.out.print(max_i+" "+max_j);
		
	}

}
