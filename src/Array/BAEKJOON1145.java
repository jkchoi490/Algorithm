import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BAEKJOON1145 {
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	
        int[] numbers = new int[5];
        StringTokenizer st;
        for (int i = 0; i < 5; i++) {
        	st = new StringTokenizer(br.readLine());
            numbers[i] = Integer.parseInt(st.nextToken());
        }


        int result = findMinimumLCM(numbers);
        System.out.println(result);
    }

   
    public static int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }


    public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static int findMinimumLCM(int[] numbers) {
        int minLCM = Integer.MAX_VALUE;

        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 5; j++) {
                for (int k = j + 1; k < 5; k++) {
                    int currentLCM = lcm(numbers[i], lcm(numbers[j], numbers[k]));
                    minLCM = Math.min(minLCM, currentLCM);
                }
            }
        }

        return minLCM;
    }
}