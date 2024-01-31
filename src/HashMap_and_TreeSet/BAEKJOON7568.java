import java.io.*;
import java.util.*;

public class BAEKJOON7568 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		HashMap<Integer,Integer> map = new HashMap<>();
		ArrayList<int[]> list = new ArrayList<>();
		
		for(int i = 0; i<N; i++) {
			StringTokenizer st= new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			list.add(new int[] {x,y});
		}
		
		for(int j = 0; j<list.size(); j++) {
			int cnt = 0;
			for(int k = 0; k<list.size(); k++) {
				if(list.get(j)[0]<list.get(k)[0] && list.get(j)[1]<list.get(k)[1]) {
					cnt++;
				}
			}
			map.put(j, cnt+1);
		}
		
		for(int v : map.values()) System.out.print(v+" ");
	}

}
