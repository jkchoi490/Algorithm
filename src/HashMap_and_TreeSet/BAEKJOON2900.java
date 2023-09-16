import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class BAEKJOON2900 {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		 long[] a = new long[1000001];
	     long[] sum = new long[1000001];
	        
		st = new StringTokenizer(br.readLine());
		Map<Integer, Integer> x_elements = new HashMap<>();
	    for (int i = 0; i < k; i++) {
	        int temp =Integer.parseInt(st.nextToken());
	        x_elements.put(temp, x_elements.getOrDefault(temp, 0) + 1);
	    }
		
	    
	    for (Map.Entry<Integer, Integer> entry : x_elements.entrySet()) {
            int jump = entry.getKey();
            int cnt = entry.getValue();
            something(jump, cnt, a, n);
        }
		
	    sum[0] = a[0];
        for (int i = 1; i < n; i++) {
            sum[i] = sum[i - 1] + a[i];
        }
        
        int q = Integer.parseInt(br.readLine());
        for (int i = 0; i < q; i++) {
        	st = new StringTokenizer(br.readLine());
			int l = Integer.parseInt(st.nextToken());
			int r = Integer.parseInt(st.nextToken());
            System.out.println(sum[r] - sum[l - 1]);
        }
        
    
	}

	private static void something(int jump, int cnt, long[] a, int n) {
		 int i = 0;
	        while (i < n) {
	            a[i] = a[i] + cnt;
	            i = i + jump;
	        }
		
	}
}
