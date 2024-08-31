import java.util.*;

class Solution_올바른괄호 {
    boolean solution(String s) {
        boolean answer = true;
        Stack<Character> stack = new Stack<>();
        for(char c : s.toCharArray()){
           if(c=='(') stack.push(c);
           else  {
               if(stack.isEmpty()) return false;
               stack.pop();
            }
       }
        if(!stack.isEmpty()) return false;
        return answer;
    }
}