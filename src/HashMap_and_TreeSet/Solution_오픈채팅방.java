import java.util.*;

class Chat{
    int idx;
    String state;
    String id;
    String nickname;
    public Chat(int idx, String state, String id, String nickname){
        this.idx=idx;
        this.state = state;
        this.id=id;
        this.nickname=nickname;
    }
}
class Solution_오픈채팅방 {
    public ArrayList<String> solution(String[] record) {
        ArrayList<Chat> list = new ArrayList<>();
        ArrayList<String> answer = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        for(int i = 0; i<record.length; i++){
            String[] r = record[i].split(" ");
            String state = r[0];
            String id = r[1];
            if(state.equals("Enter")){
                list.add(new Chat(i, "Enter", id, r[2]));
                map.put(id, r[2]);
            }
            
            else if(state.equals("Leave")){
                list.add(new Chat(i, "Leave", id, map.get(id)));
                
            }
            else if(state.equals("Change")){
            	map.put(id, r[2]);
                
            }
        }
        for(int i = 0; i<list.size(); i++){
            Chat now = list.get(i);
            if(now.state.equals("Enter")){
                answer.add(map.get(now.id)+"님이 들어왔습니다.");
            }
            else if(now.state.equals("Leave")){
                answer.add(map.get(now.id)+"님이 나갔습니다.");
            }
        }
        return answer;
    }
}