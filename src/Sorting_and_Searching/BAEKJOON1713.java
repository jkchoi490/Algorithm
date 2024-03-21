import java.io.*;
import java.util.*;

public class BAEKJOON1713 { 

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int M = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		LinkedList<int[]> list = new LinkedList<>();
		
		for(int i = 0; i<M; i++) {
			boolean flag = true;
			int num = Integer.parseInt(st.nextToken());
			
			if(list.size()<N) {
				for(int j = 0; j<list.size(); j++) {
					if(list.get(j)[0] == num) {
						list.get(j)[1]++;
						flag = false;
						break;
					}
				}
				if(flag) list.add(new int[] {num, 1, i});
			}
			else {
				list.sort((a,b)-> a[1]==b[1]? a[2]-b[2]: a[1]-b[1]);
				flag = true;
				for(int j = 0; j<list.size(); j++) {
					if(list.get(j)[0] == num) {
						list.get(j)[1]++;
						flag = false;
						break;
					}
				}
				if(flag) {
					list.pollFirst();
					list.add(new int[] {num, 1, i});
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		list.sort((a,b) -> a[0]-b[0]);
		for(int[] arr : list) {
			sb.append(arr[0]).append(" ");
		}
		System.out.println(sb.toString());
	}

}
