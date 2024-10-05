import java.util.*;

class Solution_이중우선순위큐 {
    public int[] solution(String[] operations) {
        int[] answer = new int[2];
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        PriorityQueue<Integer> maxpq = new PriorityQueue<>(Collections.reverseOrder());
        for(String oper : operations){
            String command = oper.split(" ")[0];
            int num = Integer.parseInt(oper.split(" ")[1]);
         
            if (pq.size() < 1 && command.equals("D"))
                continue;
            if(command.equals("I")) {
                pq.offer(num);
                maxpq.offer(num);
                continue;
            }

            if(command.equals("D")) {
                if(num == -1){
                    int min = pq.poll();
                    maxpq.remove(min);
                    continue;
                }
                else if(num == 1) {
                    int max = maxpq.poll();
                    pq.remove(max);
                  
                }
            }
        
        }
          if(pq.size()>0){
                answer[0] = maxpq.poll();
                answer[1] = pq.poll();
            }
        return answer;
    }
}