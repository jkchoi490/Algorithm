import java.util.*;

class Solution_디스크컨트롤러 {

    public int solution(int[][] jobs) {
        int answer = 0;
        Arrays.sort(jobs, (a,b)->a[0]-b[0]);
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[1]-b[1]);
        int n = jobs.length;
        int jobIndex = 0;
        int currentTime = 0;
        int totalWaitingTime = 0;
        while(jobIndex < n || !pq.isEmpty()){
            while(jobIndex < n && jobs[jobIndex][0] <= currentTime){
                pq.offer(jobs[jobIndex]);
                jobIndex++;
            }
            
            if(!pq.isEmpty()){
                int[] job = pq.poll();
                currentTime += job[1];
                totalWaitingTime += currentTime - job[0];
            }
            
            else{
                currentTime = jobs[jobIndex][0];
            }
        }
        
        answer = totalWaitingTime/n;
        return answer;
    }
   
    
}