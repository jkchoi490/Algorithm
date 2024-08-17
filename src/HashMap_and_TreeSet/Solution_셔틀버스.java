import java.util.*;

class Solution_셔틀버스 {
    
    public int getTime(String time){
        String[] t = time.split(":");
        int h = Integer.parseInt(t[0]);
        int m = Integer.parseInt(t[1]);
        return h*60+m;
    }
    
    public String setTime(int time){
        StringBuilder sb = new StringBuilder();
        int h = time / 60;
        int m = time % 60;
        if(h<10 && m <10){
            sb.append("0").append(h).append(":").append("0").append(m);
        }else if(h<10 && m>=10){
            sb.append("0").append(h).append(":").append(m);
        }else if(h>=10 && m<10){
            sb.append(h).append(":").append("0").append(m);
        }else if(h>=10 && m>=10){
            sb.append(h).append(":").append(m);
        }
        
        return sb.toString();
    }
    public String solution(int n, int t, int m, String[] timetable) {
        String answer = "";
        ArrayList<Integer> list = new ArrayList<>();
        for(String person : timetable){
            list.add(getTime(person));
        }
        Collections.sort(list);
        
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        int time = getTime("09:00");
        int last_time = 0;
        for(int i = 0; i<n; i++){
            map.put(time, new ArrayList<Integer>());
            last_time = Math.max(last_time, time);
            time += t;
        }
        
        int cnt = 0;
         TreeMap<Integer, ArrayList<Integer>> sortedMap = new TreeMap<>(map);
        
        for(int i = 0; i<list.size(); i++){
            for(int key : sortedMap.keySet()){
                if(list.get(i) <= key && sortedMap.get(key).size() < m) {
                    sortedMap.get(key).add(list.get(i));
                    cnt++;
                    break;
                }
                else continue;
            }
        }
       
        if(sortedMap.get(last_time).isEmpty()){
           answer = setTime(last_time);
        }else{
            if(sortedMap.get(last_time).size() < m){
                answer = setTime(last_time);
            }
            else {
                int last = sortedMap.get(last_time).get(sortedMap.get(last_time).size()-1);
                answer = setTime(last - 1);
            }
        }
        return answer;
    }
}