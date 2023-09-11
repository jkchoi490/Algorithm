import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BAEKJOON2562 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] arr = new int[9];
		int max_v = Integer.MIN_VALUE;
		int max_i = 0;
		for(int i = 0; i<9; i++) {
			arr[i] = Integer.parseInt(br.readLine());
			max_v = Math.max(max_v, arr[i]);
		}
			
		for(int i = 0; i<9; i++) {
			if(max_v == arr[i]) max_i = i; 
		}	
		System.out.println(max_v);
		System.out.println(max_i+1);
			
	}

}
