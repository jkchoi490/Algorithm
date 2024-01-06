import java.io.*;
import java.util.*;

public class BAEKJOON1302 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		HashMap<String, Integer> map = new HashMap<>();
		for(int i = 0; i<N; i++) {
			String book = br.readLine();
			map.put(book, map.getOrDefault(book, 0)+1);
		}
		
		int max = Integer.MIN_VALUE;
		for(int x : map.values()) {
			max = Math.max(max, x);
		}
		ArrayList<String> books = new ArrayList<>();
				
		for(Map.Entry<String, Integer> entry : map.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			if(value==max) books.add(key);
		}
		Collections.sort(books);
		System.out.println(books.get(0));
	}

}
