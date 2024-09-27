import java.util.*;

class Solution_n진수게임 {

    public String getChangeNum(int num, int n) {
        StringBuilder result = new StringBuilder();
        while (num > 0) {
            int v = num % n;
            if (v < 10) {
                result.append(v);
            } else {
                result.append((char) (v - 10 + 'A')); 
            }
            num /= n;
        }
        if (result.length() == 0) {
            return "0"; 
        }
        return result.reverse().toString(); 
    }

    public String solution(int n, int t, int m, int p) {
        StringBuilder totalString = new StringBuilder();
        int num = 0;
        
        while (totalString.length() < t * m) { 
            totalString.append(getChangeNum(num++, n));
        }

        StringBuilder answer = new StringBuilder();

        for (int i = 0; i < t; i++) {
            answer.append(totalString.charAt(i * m + p - 1));
        }
        
        return answer.toString();
    }
}
