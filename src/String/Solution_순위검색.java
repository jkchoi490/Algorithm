import java.util.*;

class developer{
    String lang, job, year, food;
    String score;
    public developer(String lang, String job, String year, String food, String score){
        this.lang=lang;
        this.job=job;
        this.year=year;
        this.food=food;
        this.score=score;
    }
}
class Solution_순위검색 {
    public String[] getSplitArr(String q){
        String[] result = new String[5];
        String[] andsplit = q.split("and");
        String foodscore = andsplit[3];
        for(int i = 0; i<3; i++){
            String s = andsplit[i];
            String value = "";
            for(int j = 0; j<s.length(); j++){
                if(Character.isAlphabetic(s.charAt(j)) || s.charAt(j)=='-'){
                    value += s.charAt(j);
                }
            }
            result[i] = value;
        }
        
            String[] spacesplit = foodscore.split(" ");
            for(int i = 1; i<spacesplit.length; i++){
                String s = spacesplit[i];
            String value = "";
            for(int j = 0; j<s.length(); j++){
                if(Character.isAlphabetic(s.charAt(j)) || s.charAt(j)=='-' || Character.isDigit(s.charAt(j))){
                    value += s.charAt(j);
                }
            }
           result[i+2] = value;
        }
       
        return result;
    }
    public ArrayList<Integer> solution(String[] info, String[] query) {
        ArrayList<Integer> answer = new ArrayList<>();
        ArrayList<developer> list = new ArrayList<>();
        for(String inf : info){
            String[] inf_arr= inf.split(" ");
            list.add(new developer(inf_arr[0], inf_arr[1], inf_arr[2], inf_arr[3], inf_arr[4]));
        }
        for(String q : query){
            String[] q_arr = getSplitArr(q);
            ArrayList<developer> newlist = new ArrayList<>();
            for(int i = 0; i<list.size(); i++){
                developer d = list.get(i);
                if((d.lang.equals(q_arr[0]) || "-".equals(q_arr[0]))
                  && (d.job.equals(q_arr[1]) || "-".equals(q_arr[1]))
                   &&(d.year.equals(q_arr[2]) || "-".equals(q_arr[2]))
                   &&(d.food.equals(q_arr[3]) || "-".equals(q_arr[3]))
                   &&("-".equals(q_arr[4]) || Integer.parseInt(q_arr[4]) <= Integer.parseInt(d.score)
                  )){
                    newlist.add(d);
                   
                }  
                            }
              answer.add(newlist.size()); 
               
            }
     
        return answer;
        
        
        
    }
}