package String;

import java.io.*;
import java.util.*;

//PlayStation이 아니에요
public class BAEKJOON32132 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine().trim());
        String s = br.readLine();

        StringBuilder sb = new StringBuilder(s);

        //다르게 받아들일 수 있는 오해를 풀기 위한 solution 함수 구현
        String answer = solution(sb);

        System.out.println(answer);
    }

    public static String solution(StringBuilder sb){
        boolean effort = true;
        while (effort) {
            effort = false;
            for (int i = 0; i < sb.length() - 2; i++) {
                if (sb.charAt(i) == 'P' && sb.charAt(i + 1) == 'S') {
                    char c2 = sb.charAt(i + 2);
                    //오해를 주지 않기 위해 PS5와 같은 문자열이 나타나지 않을 때까지 숫자를 지운다
                    if (c2 == '4' || c2 == '5') {
                        sb.deleteCharAt(i + 2);
                        effort = true;
                        break;
                    }
                }
            }
        }
        return sb.toString();
    }

}
