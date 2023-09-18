import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class BAEKJOON14181 {

    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] a = new int[n + 1];
        int[] s = new int[n + 1];
        StringTokenizer st;
        for (int i = 1; i <= n; i++) {
        	st = new StringTokenizer(br.readLine());
            a[i] = Integer.parseInt(st.nextToken());
            s[i] = s[i - 1] + a[i];
        }
        int m = Integer.parseInt(br.readLine());
        for (int i = 0; i < m; i++) {
        	 st = new StringTokenizer(br.readLine());
        	 int x = Integer.parseInt(st.nextToken());
        	 int y = Integer.parseInt(st.nextToken());
        }
 
}
    }