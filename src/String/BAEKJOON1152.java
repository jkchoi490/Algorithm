import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BAEKJOON1152 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] str = br.readLine().split(" ");
		int cnt = 0;
		for(String s : str) {
			if(!s.isBlank()) cnt++;
		}
		System.out.println(cnt);
	}

}
