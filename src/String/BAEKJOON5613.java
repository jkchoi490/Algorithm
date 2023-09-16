import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BAEKJOON5613 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int ans = Integer.parseInt(br.readLine());
		while(true) {
			
			String operator = br.readLine();
			if( operator.equals("=")) break;
			int num = Integer.parseInt(br.readLine());
			
			if(operator.equals("+")) ans += num;
			else if(operator.equals("-")) ans -= num; 
			else if(operator.equals("*")) ans *= num;
			else if(operator.equals("/")) ans /= num;
		}
		System.out.println(ans);

	}

}
