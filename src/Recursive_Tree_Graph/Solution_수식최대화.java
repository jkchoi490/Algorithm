import java.util.*;

class Solution_수식최대화 {
   
    static ArrayList<Character> list;
    static char[] carr;
    static boolean[] visited;
    static long max = Long.MIN_VALUE;
    
    
    public static void main(String[] args) {
		System.out.println(solution("100-200*300-500+20"));
		System.out.println(solution("50*6-3*2"));
	}
    
    
    
    public static long solution(String expression) {
        list = new ArrayList<>();
        for(int i = 0; i < expression.length(); i++){
            char c = expression.charAt(i);
            if(c == '-' || c == '*' || c == '+'){
                if(!list.contains(c)){
                    list.add(c);
                }
            }
        }
        
        visited = new boolean[list.size()];
        carr = new char[list.size()];
        DFS(0, expression);
        
        return max;
    }
    
    public static void getAnswer(char[] carr, String expression){
        List<String> expList = new ArrayList<>();
        StringBuilder num = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == '-' || c == '*' || c == '+') {
                expList.add(num.toString());
                expList.add(String.valueOf(c));
                num = new StringBuilder();
            } else {
                num.append(c);
            }
        }
    
        expList.add(num.toString());
        
        for (char op : carr) {
            for (int i = 0; i < expList.size(); i++) {
                if (expList.get(i).equals(String.valueOf(op))) {
                    long num1 = Long.parseLong(expList.get(i - 1));
                    long num2 = Long.parseLong(expList.get(i + 1));
                    long result = 0;
                    if (op == '+') {
                        result = num1 + num2;
                    } else if (op == '-') {
                        result = num1 - num2;
                    } else if (op == '*') {
                        result = num1 * num2;
                    }
                    expList.remove(i - 1);
                    expList.remove(i - 1);
                    expList.set(i - 1, String.valueOf(result));
                    
                    //for(int j = 0; j<expList.size(); j++) {
                    //	System.out.print(expList.get(j)+" ");
                    //}
                   // System.out.println();
                    i--;
                }
            }
        }
        
        max = Math.max(max, Math.abs(Long.parseLong(expList.get(0))));
    }
    
    public static void DFS(int L, String expression){
        if (L == carr.length) {
            getAnswer(carr, expression);
            return;
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (!visited[i]) {
                    visited[i] = true;
                    carr[L] = list.get(i);
                    DFS(L + 1, expression);
                    visited[i] = false;
                }
            }
        }
    }
}
