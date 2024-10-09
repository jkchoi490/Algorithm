package DAY1009;

import java.util.*;

class Solution_예산 {
   
    public int solution(int[] d, int budget) {
        int answer = 0;
        Arrays.sort(d);
        for(int i = 0; i<d.length; i++){
            if(budget >= d[i]){
                budget -= d[i];
                answer++;   
            }
        }
        return answer;
    }
  
}