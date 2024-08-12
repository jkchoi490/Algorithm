import java.util.*;

class Solution_메뉴리뉴얼 {
    static HashMap<Integer, HashMap<String, Integer>> hm;
    static boolean[] visited;
    public ArrayList<String> solution(String[] orders, int[] course) {
        ArrayList<String>answer = new ArrayList<>();
        hm = new HashMap<>();
   
        for(int len : course){
            hm.put(len, new HashMap<String, Integer>());
            for(String order : orders){
                char[] orderArr = order.toCharArray();
                Arrays.sort(orderArr);
                DFS(orderArr, len, 0, new StringBuilder());
            }
        }
       // System.out.println(hm);
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0; i<course.length; i++){
            list.add(course[i]);
        }
        for(int key : hm.keySet()){
            HashMap<String, Integer> map = hm.get(key);
           
            if(map.isEmpty()) continue;
            int max = Collections.max(map.values());
            if(max < 2) continue;
             for(String str : map.keySet()){
                if(max == map.get(str)){
                    answer.add(str);
                }
            }   
                
              
        }
        Collections.sort(answer);
        return answer;
    }
    
    public void DFS(char[] order, int len, int start, StringBuilder sb){
        if(sb.length() == len){
            String menu = sb.toString();
            hm.get(len).put(menu, hm.get(len).getOrDefault(menu, 0)+1);
            //System.out.println(menu);
            return;
        }else{
            for(int i = start; i<order.length; i++){
                sb.append(order[i]);
                DFS(order, len, i+1, sb);
                sb.deleteCharAt(sb.length()-1);
            }
        }
    }
}