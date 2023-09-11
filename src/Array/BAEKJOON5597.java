import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BAEKJOON5597 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] arr = new int[31];
		for(int i = 0; i<28; i++) {
			int student = Integer.parseInt(br.readLine());
			arr[student] = 1;
		}
		for(int i = 1; i<=30; i++) {
			if(arr[i]==0) System.out.println(i);
		}
	}

}
