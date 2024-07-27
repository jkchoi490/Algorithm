import java.util.*;

class Solution_괄호회전하기 {
    public int solution(String s) {
        
        int answer = 0;
        Queue<Character> q = new LinkedList<>();
        Stack<Character> stack = new Stack<>();
       
        for(char c : s.toCharArray()){
            q.offer(c);
        }
        int cnt = 0;
        for(int i = 0; i<s.length(); i++){
            stack = new Stack<>();
            
            for(char x : q){
                if(stack.isEmpty()){
                    stack.push(x);
                }
                else if(x ==')' && stack.peek() == '('){
                    stack.pop();
                }
                else if(x =='}' && stack.peek() == '{'){
                    stack.pop();
                }
                else if(x ==']' && stack.peek() == '['){
                    stack.pop();
                }else{
                    stack.push(x);
                }
            }
            if(stack.isEmpty()){
                answer++;
            }
            q.offer(q.poll());
        }
        
        return answer;
    }
}