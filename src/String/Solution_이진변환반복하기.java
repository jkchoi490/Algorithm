import java.util.*;

class Solution_이진변환반복하기 {
    static boolean flag = true;
    static int zero_cnt = 0;
    static String str = "";
    public int[] solution(String s) {
        int[] answer = new int[2];
        
        str = s;
        int turn =0;
        while(flag){
            removeZero();
            change();
            turn++;
        }
        answer[0] = turn;
        answer[1] = zero_cnt;
        
        return answer;
    }
    
    public void removeZero(){
        for(int i = 0; i<str.length(); i++){
            if(str.charAt(i) == '0'){
                zero_cnt++;
            }
        }
        str = str.replace("0","");
        if(str.equals("1")) flag = false;

    }
    public void change(){
        int len = str.length();
        String result = "";

        while(true){
            String a = String.valueOf(len % 2);
            len /= 2;
            result+=a;
            if(len == 0) break;
        }
        str = "";
        for(int i = result.length()-1; i>=0; i--){
            str += result.charAt(i);
        }
        if(str.equals("1")) flag = false;
    }
}