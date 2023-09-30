import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BAEKJOON1849 {
	static int[] t;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		t = new int[4*n];
		int[] result = new int[n+1];
		init(1,n,1);
		
		for(int i = 1; i<=n; i++) {
			int x = query(1,n,1,Integer.parseInt(br.readLine()));
			result[x] = i;
			update(1,n,1,x);
		}
		StringBuilder sb = new StringBuilder();
		for(int i = 1; i<=n; i++) {
			sb.append(result[i]+"\n");
		}
		System.out.println(sb.toString());

	}

	public static void init(int start, int end, int node) {
		if(start == end) {
			t[node] = 1;
			return;
		}
		int mid = (start+end)/2;
		init(start, mid, node*2);
		init(mid+1, end, node*2+1);
		t[node] = t[node*2]+t[node*2+1];
		
	}

	public static void update(int start, int end, int node, int index) {
		if(index < start || end < index) return;
		if(start == end) {
			t[node] = 0;
			return;
		}
		int mid = (start+end)/2;
		update(start, mid, node*2, index);
		update(mid+1, end, node*2+1, index);
		t[node] -= 1;
	}
	
	public static int query(int start, int end, int node, int value) {
		if(start == end) return start;
		int mid = (start+end)/2;
		if(t[node*2] > value) {
			return query(start, mid, node*2, value);
		}
		else {
			return query(mid+1, end, node*2+1, value-t[node*2]);
		}
	}
	
	
}
