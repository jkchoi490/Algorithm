import java.io.*;
import java.util.*;

public class BAEKJOON20291 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		HashMap<String, Integer>map = new HashMap<>();
		for(int i = 0; i<N; i++) {
			String str = br.readLine();
			String extension = "";
			for(int j = 0; j<str.length(); j++) {
				if(str.charAt(j) == '.') {
					extension = str.substring(j+1, str.length());
				}
			}
			map.put(extension, map.getOrDefault(extension, 0)+1);
		}
		
		List<String> keySet = new ArrayList<>(map.keySet());
		
		Collections.sort(keySet, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
			
		});
		
		for(String key : keySet) {
			System.out.println(key+" "+map.get(key));
		}
	}

}
