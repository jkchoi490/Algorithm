import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BAEKJOON1152 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		int cnt = 0;
		for(String word : str.split(" ")) {
			if(word.equals("")) continue;
			else cnt++;
		}
		System.out.println(cnt);

	}

}
