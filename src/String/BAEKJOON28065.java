import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class BAEKJOON28065 {
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
        int n = Integer.parseInt(br.readLine()); 
        int num = 1;
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 1) num += (n - i);
            else num -= (n - i);
        }
    }
 }