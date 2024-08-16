import java.util.*;

class Solution_개인정보수집유효기간 {
    static HashMap<String, Integer> map;
    public ArrayList<Integer> solution(String today, String[] terms, String[] privacies) {
        int n = privacies.length;
        ArrayList<Integer> answers = new ArrayList<>();
        map = new HashMap<>();
        for(String term : terms){
            String[] t = term.split(" ");
            String x = t[0];
            int num = Integer.parseInt(t[1]);
            map.put(x, num);
        }
        int now = getTime(today);
        for(int i = 0; i<n; i++){
            String[] d = privacies[i].split(" ");
            String date = d[0];
            String x = d[1];
            int start = getTime(date);
            int end = start + getSaveTime(x);
            //System.out.println("now: "+now+" end : "+end);
            if(end <= now) answers.add(i+1);//answer[i+1] = 1;
        }
        
        return answers;
    }
    
    public int getSaveTime(String x){
        int time = 0;
        int idx = 0;
        for(String key : map.keySet()){
            if(key.equals(x)) time = map.get(key);   
        }
        return time*28;
    }
    public int getTime(String date){
        int year = 0, month = 0, day = 0;
        ArrayList<Integer> dot = new ArrayList<>();
        for(int i = 0; i<date.length(); i++){
            if(date.charAt(i) == '.')  dot.add(i);
        }
        year = Integer.parseInt(date.substring(0, dot.get(0)));
        month = Integer.parseInt(date.substring(dot.get(0)+1, dot.get(1)));
        day = Integer.parseInt(date.substring(dot.get(1)+1, date.length()));
        //System.out.print(year+" "+month+" "+day+"\n");
        return year*(12*28)+(month*28)+day;
    }
}