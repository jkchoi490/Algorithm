import java.util.*;
import java.io.*;

public class BAEKJOON11650 {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		ArrayList<int[]> arr = new ArrayList<>();
		StringTokenizer st;
		for(int i = 0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			arr.add(new int[] {x,y});
		}
		arr.sort((a,b) -> a[0]==b[0]? a[1]-b[1]:a[0]-b[0]);
		for(int[] num : arr) {
			System.out.print(num[0]+" "+num[1]);
			System.out.println();
		}
	}
}
