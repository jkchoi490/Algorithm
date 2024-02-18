import java.io.*;
import java.util.*;

public class BAEKJOON1251 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String word = br.readLine();
		ArrayList<String> list = new ArrayList<>();
		for(int i = 1; i<word.length(); i++) {
				for(int j = i; j<word.length()-1; j++) {
					String w1 = word.substring(0,i);
					String w2 = word.substring(i, j+1);
					String w3 = word.substring(j+1, word.length());
					list.add(process(w1,w2,w3));
				}
			}
		Collections.sort(list);
		System.out.println(list.get(0));
	}

	public static String process(String w1, String w2, String w3) {
		String result = "";
		for(int i = 0; i<w1.length(); i++) {
			result += w1.charAt(w1.length()-1-i);
		}
		
		for(int j = 0; j<w2.length(); j++) {
			result += w2.charAt(w2.length()-1-j);
		}
		
		for(int k = 0; k<w3.length(); k++) {
			result += w3.charAt(w3.length()-1-k);
		}
		
		return result;
	}

}
