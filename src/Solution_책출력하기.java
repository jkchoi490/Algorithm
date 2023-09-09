import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution_책출력하기 { //책 출력하기
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int C = Integer.parseInt(br.readLine().trim());
		for(int test_case = 1; test_case<=C; test_case++) {
			int M = Integer.parseInt(br.readLine().trim());
			int N = Integer.parseInt(br.readLine().trim());
			for(int i = 0; i<N; i++) {
				int word = Integer.parseInt(br.readLine().trim());
				System.out.println(word);
			}
		}
		
	}
}
