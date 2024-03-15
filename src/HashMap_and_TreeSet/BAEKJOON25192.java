import java.io.*;
import java.util.*;

public class BAEKJOON25192 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		HashSet<String> set = new HashSet<>();
		int cnt = 0;
		for(int i = 0; i<N; i++) {
			String s = br.readLine();
			if(s.equals("ENTER")) {
				cnt += set.size();
				set = new HashSet<>();
			}else {
				set.add(s);
			}
			
			
		}
		cnt += set.size();
		System.out.println(cnt);
	}

}
