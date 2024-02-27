import java.io.*;
import java.util.*;

public class BAEKJOON7785 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		HashMap<String, Integer> map = new HashMap<>();
		for(int i = 0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			String name = st.nextToken();
			String check = st.nextToken();
			if(check.equals("enter")) map.put(name, 1);
			else if(check.equals("leave")) {
				map.remove(name);
			}
		}
		List<String> keySet = new ArrayList<>(map.keySet());
		Collections.sort(keySet, Collections.reverseOrder());
		for(String key : keySet) {
			System.out.println(key);
		}

	}

}
