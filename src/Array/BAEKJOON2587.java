import java.io.*;
import java.util.*;

public class BAEKJOON2587 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] arr = new int[5];
		int sum = 0;
		for(int i = 0; i<5; i++) {
			int num = Integer.parseInt(br.readLine());
			arr[i] = num;
			sum += num;
		}
		Arrays.sort(arr);
		System.out.println(sum/5);
		System.out.println(arr[2]);
	}

}
