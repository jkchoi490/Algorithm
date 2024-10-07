import java.io.*;
import java.util.*;

public class BAEKJOON11478 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		
		HashMap<String, Integer> map = new HashMap<>();
		for(int i = 0; i<str.length(); i++) {
			for(int j = i+1; j<=str.length(); j++) {
				String s = str.substring(i, j);
				if(!map.containsKey(s)) map.put(s, 1);
				else if(map.containsKey(s)) map.put(s, map.get(s)+1);
			}
		}
		
		System.out.println(map.size());
	}

}
