package Stack_and_Queue;

import java.util.*;

class Solution_Queue4 { //프로세스
    public int solution(int[] priorities, int location) {
        int answer = 1;
        PriorityQueue q = new PriorityQueue<>(Collections.reverseOrder());; // 우선순위 큐 생성 후 내림차순 정렬

        for(int i=0; i<priorities.length; i++){
            q.add(priorities[i]); 
        }
    

        while(!q.isEmpty()){
            for(int i=0; i<priorities.length; i++){
                if(priorities[i] == (int)q.peek()){ // 프로세스의 중요도와 큐의 맨 앞에 있는(제일 먼저 저장된) 요소가 같으면
                    if(i == location){ 
                        return answer;
                    }
                    q.poll(); //큐에서 꺼내고
                    answer++; //실행되는 순서 카운트 +1
                }
            }
        }

        return answer;
    }
}