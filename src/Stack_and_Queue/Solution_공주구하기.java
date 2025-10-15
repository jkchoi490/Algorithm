package Stack_and_Queue;

import com.sun.tools.javac.Main;

import java.util.*;

//인프런 - 공주구하기
public class Solution_공주구하기 {
    public int solution(int n, int k){
        int answer=0;
        Queue<Integer> Q=new LinkedList<>();
        for(int i=1; i<=n; i++) Q.offer(i);
        while(!Q.isEmpty()){
            for(int i=1; i<k; i++) Q.offer(Q.poll());
            Q.poll();
            if(Q.size()==1) answer=Q.poll();
        }
        return answer;
    }
    public static void main(String[] args){
        Solution_공주구하기 T = new Solution_공주구하기();
        Scanner kb = new Scanner(System.in);
        int n=kb.nextInt();
        int k=kb.nextInt();
        System.out.println(T.solution(n, k));
    }
}