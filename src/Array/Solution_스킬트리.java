import java.util.*;

class SKILL{
    int idx;
    char c;
    public SKILL(int idx, char c){
        this.idx=idx;
        this.c=c;
    }
}

class Solution_스킬트리 {
    public int solution(String skill, String[] skill_trees) {
        int answer = 0;
        char[] sk = skill.toCharArray();
        
        for(String skills : skill_trees){
           ArrayList<SKILL> list = new ArrayList<>();
           for(int i = 0; i<skills.length(); i++){
               for(int j = 0; j<sk.length; j++){
                   if(sk[j] == skills.charAt(i)){
                       list.add(new SKILL(j, sk[j]));
                    
                   }
               }
           }

            int now = 0;

            boolean flag = true;
            for(int k = 0; k<list.size(); k++){
           
               if(list.get(k).idx == now){
                   now++;
               }
                else {
                    flag = false;
                    break;
                }
            } 
          if(flag) answer++;
        }
        
      
        return answer;
    }
}