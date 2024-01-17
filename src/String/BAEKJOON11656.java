import java.io.*;
import java.util.*;

public class BAEKJOON11656 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String S = br.readLine();
		StringBuilder sb;
		ArrayList<String> arr = new ArrayList<>();
		for(int i = 0; i<S.length(); i++) {
			sb = new StringBuilder();
			for(int j = i; j<S.length(); j++) {
				sb.append(S.charAt(j));
			}
			arr.add(sb.toString());
		}
		
		Collections.sort(arr);
		for(String str : arr) System.out.println(str);

	}

}
