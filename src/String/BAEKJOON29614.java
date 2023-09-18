import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BAEKJOON29614 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s  = br.readLine();
		double score  = 0;
		if(s.equals("A+")) {
			score = 4.5;
		}
		
		if(s.equals("A")) {
			score = 4;
		}

		if(s.equals("B+")) {
			score = 3.5;
		}

		if(s.equals("B")) {
			score = 3;
		}

		if(s.equals("C+")) {
			score = 2.5;
		}

		if(s.equals("C")) {
			score = 2;
		}

		if(s.equals("D+")) {
			score = 1.5;
		}

		if(s.equals("D")) {
			score = 1;
		}
		else score = 0;

	}

}
