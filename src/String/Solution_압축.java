import java.util.*;

class Solution_압축 {
   
      public int[] solution(String msg) {
        
         ArrayList<String> dict= new ArrayList<>();
          for(char c = 'A'; c<='Z'; c++){
              dict.add(String.valueOf(c));
          }
          int i = 0;
          ArrayList<Integer> result = new ArrayList<>();
          while(i<msg.length()){
              int j = i+1;
              while(j<=msg.length() && dict.contains(msg.substring(i, j))){
                  j++;
              }
              
              if(j<=msg.length()){
                  dict.add(msg.substring(i,j));
              }
              
              result.add(dict.indexOf(msg.substring(i, j-1)));
              i = j-1;
          }
          
          int[] answer = new int[result.size()];
          for(int k = 0; k<result.size(); k++){
              answer[k] = result.get(k)+1;
          }
        return answer;
    }
}