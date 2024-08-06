import java.util.*;

class Solution_두큐합같게만들기 {
    public int solution(int[] queue1, int[] queue2) {
        int answer = -2;
        long total1 = 0, total2 =0, sum = 0;
        for(int i = 0; i<queue1.length; i++){
            total1 += queue1[i];
            total2 += queue2[i];
        }
        sum = (total1+total2)/2;
        Queue<Integer> q1 = new LinkedList<>();
        Queue<Integer> q2 = new LinkedList<>();
 
        for(int x1 : queue1) q1.offer(x1);
        for(int x2 : queue2) q2.offer(x2);
        int cnt = 0;
        while(true){
            if(cnt > (queue1.length + queue2.length) * 2) return -1;
            if(total1>total2){
                int p = q1.poll();
                total1-= p;
                total2+= p;
                q2.offer(p);
            }
            else if(total1<total2){
                int p1 = q2.poll();
                total2 -= p1;
                total1 += p1;
                q1.offer(p1);
            }
            else if(total1==sum){
                answer = cnt;
                break;
            }
            cnt++;
        }
        return answer;
    }
}