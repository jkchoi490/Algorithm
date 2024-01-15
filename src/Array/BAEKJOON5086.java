import java.io.*;
import java.util.*;

public class BAEKJOON5086 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		while(true) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			if(a == 0 && b == 0) break;
			if(b % a == 0) System.out.println("factor");
			else if(a % b == 0) System.out.println("multiple");
			else System.out.println("neither");
		}

	}

}
