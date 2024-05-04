import java.io.*;
import java.util.*;

public class BAEKJOON13305 {

    static int N;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] distance = new int[N-1];
        for(int i = 0; i<N-1; i++) distance[i] = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        long[] city = new long[N]; 
        for(int i = 0; i<N; i++) {
            city[i] = Integer.parseInt(st.nextToken());
        }
        long result = 0;
        long minPrice = city[0]; 
        for(int i = 0; i<N-1; i++) {
            if(city[i] < minPrice) {
                minPrice = city[i];
            }
            result += minPrice * distance[i];
        }
        System.out.println(result);
    }

}
