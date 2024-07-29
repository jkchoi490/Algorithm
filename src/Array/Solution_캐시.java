import java.util.*;

class Solution_캐시 {
 
    public int solution(int cacheSize, String[] cities) {
        int answer = 0;
        LinkedList<String> list = new LinkedList<>();
        if(cacheSize == 0){
            answer = cities.length * 5;
        }
        else{
        int total = 0;
        for(String city : cities){
            city = city.toLowerCase();
            if(list.contains(city)){
                list.remove(city);
                total+=1;
            }else{
                if(list.size() == cacheSize){
                    list.removeLast();
                }
                 total+=5;
            }
            list.addFirst(city);
        }
        
        answer = total;
        }
        return answer;
    }
}