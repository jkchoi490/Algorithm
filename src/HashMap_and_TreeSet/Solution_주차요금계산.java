import java.util.*;

class Car{
    int time;
    String isInOut;
    public Car(int time, String isInOut){
        this.time=time;
        this.isInOut=isInOut;
    }
}
class Solution_주차요금계산 {
    static int[] answer;
    public int getTime(String time){
        int h = Integer.parseInt(time.split(":")[0]);
        int m = Integer.parseInt(time.split(":")[1]);
        return h*60+m;
    }
    public int[] solution(int[] fees, String[] records) {
       
        HashMap<String,ArrayList<Car>> map = new HashMap<>();
        for(String record : records){
            String[] re = record.split(" ");
            int time = getTime(re[0]);
            String car = re[1];
            String state = re[2];
            if(!map.containsKey(car)){
                ArrayList<Car> list = new ArrayList<>();
                list.add(new Car(time, state));
                map.put(car, list);
            }else{
                map.get(car).add(new Car(time, state));
            }    
        }
        answer = new int[map.size()];
        HashMap<Integer, Integer> result = new HashMap<>();
        for(String key : map.keySet()){
            ArrayList<Car> now = map.get(key);
            if(now.get(now.size()-1).isInOut.equals("IN")) map.get(key).add(new Car(getTime("23:59"),"OUT"));
            int time = 0;
            for(int i = 1; i<now.size(); i+=2){
                time += now.get(i).time - now.get(i-1).time;
               //System.out.println(key+" "+now.get(i).time+" "+now.get(i).isInOut);
            }
            result.put(Integer.parseInt(key), time);
        }
       // System.out.println(result);
        getFee(result, fees);
        return answer;
    }
    
    public void getFee(HashMap<Integer, Integer> map, int[] fees){
        HashMap<Integer, Integer> result = new HashMap<>();
        for(int key : map.keySet()){
            int time = map.get(key);
            int fee = 0;
            if(time <= fees[0]){
                fee = fees[1];
            }else{
                double f = (time-fees[0]);
                fee = fees[1]+(int)Math.ceil(f/fees[2])*fees[3];
            }
            result.put(key, fee);
        }
        
        List<Integer> keylist = new ArrayList<>(result.keySet());
        Collections.sort(keylist);
        //System.out.println(result);
        int i = 0;
        for(int k: keylist){
           answer[i] = result.get(k);
            i++;
        }
    }
}