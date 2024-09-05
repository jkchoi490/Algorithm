import java.util.*;

class Solution_모의고사 {
    static int m1, m2, m3;
    static int[] answer = new int[3]; 
    public int[] solution(int[] answers) {
        m1=m2=m3=0;
        
        int[] math1 = {1,2,3,4,5};
        int[] math2 = {2,1,2,3,2,4,2,5};
        int[] math3 = {3,3,1,1,2,2,4,4,5,5};
        int[] answer =  {0,0,0}; 
        for(int i = 0; i<answers.length; i++){
            int now = answers[i];
            if(now == math1[i%5]) m1++;
            if(now == math2[i%8]) m2++;
            if(now == math3[i%10]) m3++;
        }
        answer[0] = m1;
        answer[1] = m2;
        answer[2] = m3;
        
        int max = Math.max(answer[0], Math.max(answer[1], answer[2]));

        List<Integer> score = new ArrayList<Integer>();
        for(int i=0; i<answer.length; i++) if(max == answer[i]) score.add(i+1);
        
        int[] ans = new int[score.size()];
        for(int i=0; i<score.size(); i++){
            ans[i] = score.get(i);
        }

        return ans;
    }
}