import java.util.*;

class Solution_튜플 {
    public ArrayList<Integer> solution(String s) {
        ArrayList<Integer>answer = new ArrayList<>();
        ArrayList<ArrayList<Integer>> tuples = new ArrayList<>();
        String[] strs = s.split("}");

        if(strs.length == 1){
            String str = strs[0];
            String nums = "";
            for(int i = 0; i<str.length(); i++){
                if(Character.isDigit(str.charAt(i))) nums+=str.charAt(i);
            }
            answer.add(Integer.parseInt(nums));
        }else{
            for(int i = 0; i<strs.length; i++){
                String str = strs[i];
                ArrayList<Integer> tuple = new ArrayList<>();
                str = str.replace("{","");
                str = str.replace(","," ");
                String[] str_arr = str.split(" ");
                for(int j = 0; j<str_arr.length; j++){
                	
                    if(str_arr[j].equals("")) continue;
                    int num = Integer.parseInt(str_arr[j]);
                    tuple.add(num);
                }
                tuples.add(tuple);
            }
            int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
            for(int i = 0; i<tuples.size(); i++){
                ArrayList<Integer> t = tuples.get(i);
                min = Math.min(min, t.size());
                max = Math.max(max, t.size());
            }
            
          for(int j = min; j<=max; j++){  
           for(int i = 0; i<tuples.size(); i++){
                ArrayList<Integer> t = tuples.get(i);
                if(t.size()==j){
                   for(int k = 0; k<t.size(); k++){
                       if(!answer.contains(t.get(k)))answer.add(t.get(k));
                        }
                    }
                
           		}
           }
            
            
        }
        
        
        return answer;
    }
}