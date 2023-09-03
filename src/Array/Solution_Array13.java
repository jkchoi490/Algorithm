package Array;

import java.util.*;

class Solution { // 프로그래머스 - 할 일 목록
    public ArrayList<String> solution(String[] todo_list, boolean[] finished) {
        ArrayList<String> ans = new ArrayList<>();
        for(int i = 0; i<todo_list.length; i++){
            if(finished[i]==false){
                ans.add(todo_list[i]);
            }
        }
        return ans;
    }
}