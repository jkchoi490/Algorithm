import java.io.*;
import java.util.*;

public class BAEKJOON1283 {
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		ArrayList<Character> list = new ArrayList<>();
				
		process : for(int i = 0; i<N; i++) {
			String word = br.readLine();
			String[] words = word.split(" ");
			for(int j = 0; j<words.length; j++) {
				char first = words[j].charAt(0);
				if(!list.contains(first)) {
					list.add(Character.toUpperCase(first));
					list.add(Character.toLowerCase(first));
					
					StringBuilder sb = new StringBuilder(words[j]);
					sb.insert(0, '[');
					sb.insert(2,']');
					words[j] = sb.toString();
					print(words);
					continue process;
				}
			}
		
		
			for(int k = 0; k<words.length; k++) {
				for(int l = 0; l<words[k].length(); l++) {
					char next = words[k].charAt(l);
					if(!list.contains(next)) {
						list.add(Character.toUpperCase(next));
						list.add(Character.toLowerCase(next));
						
						StringBuilder sb = new StringBuilder(words[k]);
						sb.insert(l, '[');
						sb.insert(l+2, ']');
						words[k] = sb.toString();
						print(words);
						continue process;
					}
				}
			}
			print(words);
		
		}
		
		
	}
	public static void print(String[] arr) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i<arr.length; i++) {
			if(i==arr.length -1) sb.append(arr[i]);
			else sb.append(arr[i]).append(" ");
		}
		System.out.println(sb.toString());
	}
}
