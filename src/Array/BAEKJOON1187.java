import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.ArrayDeque;
import java.util.Deque;
class Num{
	int num;
	int[] ingredients;
	public Num(int num, int idx) {
		this.num = num;
		ingredients = new int[64];
	}
}
public class BAEKJOON1187 {
	
	static int N;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    int n = Integer.parseInt(br.readLine());
	    int[] arr = new int[(2*n)-1];
	    Deque<Num> dq = new ArrayDeque<>();
	    StringTokenizer st = new StringTokenizer(br.readLine());
	    for(int i = 0; i<(2*n-1); i++) arr[i] = Integer.parseInt(st.nextToken());
	    int k = 1;
	    int tmp = n;
	    while((tmp>>=1) != 1) {
	    	k++;
	    }
	    for(int i = 1; i<=k; i++) {
	    	int limit = dq.size()/2;
	    }
	}
	
}
