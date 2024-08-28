import java.util.*;

class Solution_주식가격 {
    static Queue<Integer> q;
    public int[] solution(int[] prices) {
        int[] answer = new int[prices.length];
        for(int i = 0; i<prices.length; i++){
            q = new LinkedList<>();
            for(int j = i+1; j<prices.length; j++){
                q.offer(prices[j]);
                if(prices[i]>prices[j]) break;
            }
            answer[i] = q.size();
        }
        return answer;
    }
}