import java.io.*;
import java.util.*;

public class BAEKJOON10431 { 

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int P = Integer.parseInt(br.readLine());
		for(int tc = 1; tc<=P; tc++) {
			String[] str = br.readLine().split(" ");
			int test_case = Integer.parseInt(str[0]);
			int[] arr = new int[20];
			for(int i = 1; i<=20; i++) {
				arr[i-1] = Integer.parseInt(str[i]);
			}
			int answer = 0;
			for(int i = 0; i<arr.length-1; i++) {
				for(int j = 0; j<arr.length-i-1; j++) {
					if(arr[j] > arr[j+1] ) {
						int tmp = arr[j];
						arr[j] = arr[j+1];
						arr[j+1] = tmp;
						answer++;
					}
				}
			}
			
			System.out.println(test_case+" "+answer);
		}
	}

}
