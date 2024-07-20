import java.util.*;

class Solution_기능개발 {
    public ArrayList<Integer> solution(int[] progresses, int[] speeds) {
         ArrayList<Integer> answer = new ArrayList<>();
       Queue<Integer> q = new LinkedList<>();
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0; i<progresses.length; i++){
             int result = (100 - progresses[i])/speeds[i];
            if((100 - progresses[i])%speeds[i]>0) {
                result+=1;
                list.add(result);
            }
            else{
               list.add(result);
            }
        }
        
        for(int x : list) q.offer(x);
        int now = q.poll();
        int cnt = 1;
        while(!q.isEmpty()){
            if(now >= q.peek()){
                cnt++;
                q.poll();
            }else{
                answer.add(cnt);
                now = q.poll();
                cnt = 1;
            }
          
        }
        answer.add(cnt);
        return answer;
    }
}