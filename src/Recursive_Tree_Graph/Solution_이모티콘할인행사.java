import java.util.*;

class user{
    int idx;
    int cost;
    int plus;

    public user(int idx, int cost, int plus){
        this.idx=idx;
        this.cost=cost;
        this.plus=plus;
    }
}
class Solution_이모티콘할인행사 {
    static int[] combi;
    static int[] arr = {10, 20, 30, 40};
    static ArrayList<user> user_list = new ArrayList<>();
    
    public int[] solution(int[][] users, int[] emoticons) {
        int[] answer = new int[2];
        int n = emoticons.length;
        combi = new int[n];
        DFS(0, n, users, emoticons);
        
        Collections.sort(user_list, new Comparator<user>(){
           @Override
            public int compare(user o1, user o2){
                if(o1.plus == o2.plus){
                    return o2.cost-o1.cost;
                }
                return o2.plus - o1.plus;
            }
        });
        
        user ans = user_list.get(0);
        answer[0] = ans.plus;
        answer[1] = ans.cost;
        return answer;
    }
    
    public void DFS(int start, int n, int[][] users, int[] emoticons){
        if(start == n){
            //System.out.println(Arrays.toString(combi));
            buyEmoticon(combi, users, emoticons);
            return;
        }else{
            for(int i = 0; i<4; i++){
                combi[start] = arr[i];
                DFS(start+1, n, users, emoticons);
            }
        }
    }
    
    public void buyEmoticon(int[] combi, int[][] users, int[] emoticons){
        int totalservice = 0;
        int totalcost = 0;
        for(int i = 0; i<users.length; i++){
            int[] person = users[i];
            int percent = person[0];
            int money = person[1];
            
             int cost = 0;
            for(int j = 0; j<combi.length; j++){
               
                if(combi[j] >= percent){
                    cost += emoticons[j] * (100 - combi[j]) / 100;
                }
            }
            if (cost >= money) {
                totalservice++;
            } else {
                totalcost += cost;
            }
               
            }
            
             user_list.add(new user(0, totalcost, totalservice));

       
    }
}