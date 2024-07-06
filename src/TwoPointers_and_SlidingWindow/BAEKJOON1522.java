import java.io.*;
import java.util.*;

public class BAEKJOON1522 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        int n = s.length();
        
        int totalA = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == 'a') {
                totalA++;
            }
        }
        
        int currentB = 0;
        for (int i = 0; i < totalA; i++) {
            if (s.charAt(i) == 'b') {
                currentB++;
            }
        }

        int minB = currentB;
        for (int i = 1; i < n; i++) {
           
            int start = (i - 1) % n;
            int end = (i + totalA - 1) % n;
           
            if (s.charAt(start) == 'b') {
                currentB--;
            }
            if (s.charAt(end) == 'b') {
                currentB++;
            }
            
            minB = Math.min(minB, currentB);
        }
        

        System.out.println(minB);
    }
}
