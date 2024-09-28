import java.util.*;

class Solution_최소직사각형 {
    public int solution(int[][] sizes) {
        int answer = 0;
        ArrayList<int[]> arr = new ArrayList<>();
        
        for(int i = 0; i<sizes.length; i++){
            int w = sizes[i][0];
            int h = sizes[i][1];
            if(w > h){
                arr.add(new int[]{h, w});
            }else{
                arr.add(new int[]{w, h});
            }
        }
      
        int max_w = 0;
        int max_h = 0;
        for(int i = 0; i<arr.size(); i++){
            max_w = Math.max(max_w, arr.get(i)[0]);    
            max_h = Math.max(max_h, arr.get(i)[1]);    
        }
        answer = max_w * max_h;
        return answer;
    }
}