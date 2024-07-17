import java.util.*;

class Solution_k진수에서소수개수구하기 {
    
    public String getJinsuNum(int n, int k){
        String result = "", num = "";
        while(true){
            if(n == 0) break;
            int v = n%k;
            n /= k;
            result += String.valueOf(v);
        }
        for(int i = result.length()-1; i>=0; i--){
            num += result.charAt(i);
        }
       
        return num;
    }
    public int CountPrimeNum(String[] str){
        int cnt = 0;
        for (String s : str) {
            if (s.equals("") || s.equals("1")) continue;
            long num = Long.parseLong(s);
            if (isPrime(num)) cnt++;
        }
        return cnt;
    }
    
    public boolean isPrime(long num) {
        if (num < 2) return false;
        if (num == 2) return true;
        if (num % 2 == 0) return false;
        for (long i = 3; i <= Math.sqrt(num); i += 2) {
            if (num % i == 0) return false;
        }
        return true;
    }
    
    public int solution(int n, int k) {
        int answer = -1;
        String jn = getJinsuNum(n,k);
        String[] str = jn.split("0");
        answer = CountPrimeNum(str);
   
        return answer;
    
    
    }
}