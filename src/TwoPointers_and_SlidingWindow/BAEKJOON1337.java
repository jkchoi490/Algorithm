import java.io.*;
import java.util.*;

public class BAEKJOON1337 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];
		for(int i = 0; i<N; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		int answer = 4;
		Arrays.sort(arr);
		for(int i = 0; i<N; i++) {
			int left = i;
			int right = N-1;
			while(left<right) {
				if(arr[right]- arr[left] >= 5) {
					right--;
				}
				else {
					int tmp = 4-(right-left);
					answer = Math.min(answer, tmp);
					break;
				}
			}
		}
		System.out.println(answer);
		
	}

}
